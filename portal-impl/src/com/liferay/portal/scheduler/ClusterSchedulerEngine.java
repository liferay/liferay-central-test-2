/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.scheduler;

import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterEventType;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineClusterManager;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Tina Tian
 */
public class ClusterSchedulerEngine
	implements SchedulerEngine, SchedulerEngineClusterManager,
		IdentifiableBean {

	public static SchedulerEngine createClusterSchedulerEngine(
		SchedulerEngine schedulerEngine) {

		if (PropsValues.CLUSTER_LINK_ENABLED) {
			schedulerEngine = new ClusterSchedulerEngine(schedulerEngine);
		}

		return schedulerEngine;
	}

	public ClusterSchedulerEngine(SchedulerEngine schedulerEngine) {
		_schedulerEngine = schedulerEngine;
	}

	@Clusterable
	public void delete(String groupName) throws SchedulerException {
		StorageType storageType = getStorageType(groupName);

		try {
			if ((storageType == StorageType.MEMORY_SINGLE_INSTANCE) &&
				!isMemorySchedulerClusterLockOwner(
					lockMemorySchedulerCluster(null))) {

				Iterator<Map.Entry<String, ObjectValuePair<
					SchedulerResponse, TriggerState>>> iterator =
						_memorySingleInstanceJobs.entrySet().iterator();

				while (iterator.hasNext()) {
					SchedulerResponse schedulerResponse =
						iterator.next().getValue().getKey();

					if (schedulerResponse.getGroupName().equals(groupName)) {
						iterator.remove();
					}
				}

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to delete jobs in group {" + groupName + "}", e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.delete(groupName);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(storageType);
	}

	@Clusterable
	public void delete(String jobName, String groupName)
		throws SchedulerException {

		StorageType storageType = getStorageType(groupName);

		try {
			if ((storageType == StorageType.MEMORY_SINGLE_INSTANCE) &&
				!isMemorySchedulerClusterLockOwner(
					lockMemorySchedulerCluster(null))) {

				_memorySingleInstanceJobs.remove(
					getFullName(jobName, groupName));

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to delete job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.delete(jobName, groupName);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(storageType);
	}

	public SchedulerResponse getScheduledJob(String jobName, String groupName)
		throws SchedulerException {

		StorageType storageType = getStorageType(groupName);

		try {
			if ((storageType == StorageType.MEMORY_SINGLE_INSTANCE) &&
				!isMemorySchedulerClusterLockOwner(
					lockMemorySchedulerCluster(null))) {

				return (SchedulerResponse)callMaster(
					_getScheduledJobByJobNameGroupName, jobName, groupName);
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to get job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}

		_readLock.lock();

		try {
			return _schedulerEngine.getScheduledJob(jobName, groupName);
		}
		finally {
			_readLock.unlock();
		}
	}

	public List<SchedulerResponse> getScheduledJobs()
		throws SchedulerException {

		try {
			if (!isMemorySchedulerClusterLockOwner(
				lockMemorySchedulerCluster(null))) {

				return (List<SchedulerResponse>)callMaster(_getScheduledJobs);
			}
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to get jobs", e);
		}

		_readLock.lock();

		try {
			return _schedulerEngine.getScheduledJobs();
		}
		finally {
			_readLock.unlock();
		}
	}

	public List<SchedulerResponse> getScheduledJobs(String groupName)
		throws SchedulerException {

		StorageType storageType = getStorageType(groupName);

		try {
			if ((storageType == StorageType.MEMORY_SINGLE_INSTANCE) &&
				!isMemorySchedulerClusterLockOwner(
					lockMemorySchedulerCluster(null))) {

				return (List<SchedulerResponse>)callMaster(
					_getScheduledJobByGroupName, groupName);
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to get jobs in group {" + groupName + "}", e);
		}

		_readLock.lock();

		try {
			return _schedulerEngine.getScheduledJobs(groupName);
		}
		finally {
			_readLock.unlock();
		}
	}

	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	@Clusterable
	public void pause(String groupName) throws SchedulerException {
		StorageType storageType = getStorageType(groupName);

		try {
			if ((storageType == StorageType.MEMORY_SINGLE_INSTANCE) &&
				!isMemorySchedulerClusterLockOwner(
					lockMemorySchedulerCluster(null))) {

				for(ObjectValuePair<SchedulerResponse, TriggerState> value :
					_memorySingleInstanceJobs.values()) {

					SchedulerResponse schedulerResponse = value.getKey();

					if (schedulerResponse.getGroupName().equals(groupName)) {
						value.setValue(TriggerState.PAUSED);
					}
				}

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to pause jobs in group {" + groupName + "}", e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.pause(groupName);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(storageType);
	}

	@Clusterable
	public void pause(String jobName, String groupName)
		throws SchedulerException {

		StorageType storageType = getStorageType(groupName);

		try {
			if ((storageType == StorageType.MEMORY_SINGLE_INSTANCE) &&
				!isMemorySchedulerClusterLockOwner(
					lockMemorySchedulerCluster(null))) {

				ObjectValuePair<SchedulerResponse, TriggerState>
					memorySingleInstanceJob =
						_memorySingleInstanceJobs.get(
							getFullName(jobName, groupName));

				if (memorySingleInstanceJob != null) {
					memorySingleInstanceJob.setValue(TriggerState.PAUSED);
				}

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to pause job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.pause(jobName, groupName);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(storageType);
	}

	@Clusterable
	public void resume(String groupName) throws SchedulerException {
		StorageType storageType = getStorageType(groupName);

		try {
			if ((storageType == StorageType.MEMORY_SINGLE_INSTANCE) &&
				!isMemorySchedulerClusterLockOwner(
					lockMemorySchedulerCluster(null))) {

				for(ObjectValuePair<SchedulerResponse, TriggerState> value :
					_memorySingleInstanceJobs.values()) {

					SchedulerResponse schedulerResponse = value.getKey();

					if (schedulerResponse.getGroupName().equals(groupName)) {
						value.setValue(TriggerState.NORMAL);
					}
				}

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to resume jobs in group {" + groupName + "}", e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.resume(groupName);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(storageType);
	}

	@Clusterable
	public void resume(String jobName, String groupName)
		throws SchedulerException {

		StorageType storageType = getStorageType(groupName);

		try {
			if ((storageType == StorageType.MEMORY_SINGLE_INSTANCE) &&
				!isMemorySchedulerClusterLockOwner(
					lockMemorySchedulerCluster(null))) {

				ObjectValuePair<SchedulerResponse, TriggerState>
					memorySingleInstanceJob =
						_memorySingleInstanceJobs.get(
							getFullName(jobName, groupName));

				if (memorySingleInstanceJob != null) {
					memorySingleInstanceJob.setValue(TriggerState.NORMAL);
				}

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to resume job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.resume(jobName, groupName);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(storageType);
	}

	@Clusterable
	public void schedule(
			Trigger trigger, String description, String destinationName,
			Message message)
		throws SchedulerException {

		String groupName = trigger.getGroupName();
		String jobName = trigger.getJobName();

		StorageType storageType = getStorageType(groupName);

		try {
			if ((storageType == StorageType.MEMORY_SINGLE_INSTANCE) &&
				!isMemorySchedulerClusterLockOwner(
					lockMemorySchedulerCluster(null))) {

				SchedulerResponse schedulerResponse = new SchedulerResponse();

				schedulerResponse.setDescription(description);
				schedulerResponse.setDestinationName(destinationName);
				schedulerResponse.setGroupName(groupName);
				schedulerResponse.setJobName(jobName);
				schedulerResponse.setMessage(message);
				schedulerResponse.setTrigger(trigger);

				_memorySingleInstanceJobs.put(
					getFullName(jobName, groupName),
					new ObjectValuePair<SchedulerResponse, TriggerState>(
						schedulerResponse, TriggerState.NORMAL));

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to schedule job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.schedule(
				trigger, description, destinationName, message);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(storageType);
	}

	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	public void shutdown() throws SchedulerException {
		try {
			ClusterExecutorUtil.removeClusterEventListener(
				_memorySchedulerClusterEventListener);

			LockLocalServiceUtil.unlock(
				_LOCK_CLASS_NAME, _LOCK_CLASS_NAME, _localClusterNodeAddress,
				PropsValues.MEMORY_CLUSTER_SCHEDULER_LOCK_CACHE_ENABLED);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to shutdown scheduler", e);
		}

		_schedulerEngine.shutdown();
	}

	public void start() throws SchedulerException {
		try {
			ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

			_readLock = readWriteLock.readLock();
			_writeLock = readWriteLock.writeLock();

			_localClusterNodeAddress = getSerializedString(
				ClusterExecutorUtil.getLocalClusterNodeAddress());

			_memorySchedulerClusterEventListener =
				new MemorySchedulerClusterEventListener();

			ClusterExecutorUtil.addClusterEventListener(
				_memorySchedulerClusterEventListener);

			if (!isMemorySchedulerClusterLockOwner(
				lockMemorySchedulerCluster(null))) {

				initMemorySingleInstanceJobs();
			}
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to start scheduler", e);
		}

		_schedulerEngine.start();
	}

	@Clusterable
	public void suppressError(String jobName, String groupName)
		throws SchedulerException {

		StorageType storageType = getStorageType(groupName);

		try {
			if ((storageType == StorageType.MEMORY_SINGLE_INSTANCE) &&
				!isMemorySchedulerClusterLockOwner(
					lockMemorySchedulerCluster(null))) {

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to suppress error for job {jobName=" + jobName +
					", groupName=" + groupName + "}",
				e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.suppressError(jobName, groupName);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(storageType);
	}

	@Clusterable
	public void unschedule(String groupName) throws SchedulerException {
		StorageType storageType = getStorageType(groupName);

		try {
			if ((storageType == StorageType.MEMORY_SINGLE_INSTANCE) &&
				!isMemorySchedulerClusterLockOwner(
					lockMemorySchedulerCluster(null))) {

				Iterator<Map.Entry<String, ObjectValuePair<
					SchedulerResponse, TriggerState>>> iterator =
						_memorySingleInstanceJobs.entrySet().iterator();

				while (iterator.hasNext()) {
					Map.Entry<String, ObjectValuePair<
						SchedulerResponse, TriggerState>> entry =
							iterator.next();

					SchedulerResponse schedulerResponse =
						entry.getValue().getKey();

					if (schedulerResponse.getGroupName().equals(groupName)) {
						iterator.remove();
					}
				}

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to unschedule jobs in group {" + groupName + "}", e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.unschedule(groupName);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(storageType);
	}

	@Clusterable
	public void unschedule(String jobName, String groupName)
		throws SchedulerException {

		StorageType storageType = getStorageType(groupName);

		try {
			if ((storageType == StorageType.MEMORY_SINGLE_INSTANCE) &&
				!isMemorySchedulerClusterLockOwner(
					lockMemorySchedulerCluster(null))) {

				_memorySingleInstanceJobs.remove(
					getFullName(jobName, groupName));

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to unschedule job {jobName=" + jobName +
					", groupName=" + groupName + "}",
				e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.unschedule(jobName, groupName);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(storageType);
	}

	/**
	 * @deprecated {@link #unschedule(String, String)}
	 */
	@Clusterable
	public void unschedule(Trigger trigger) throws SchedulerException {
		if (_log.isWarnEnabled()) {
			_log.warn("");
		}
	}

	@Clusterable
	public void update(Trigger trigger) throws SchedulerException {
		String jobName = trigger.getJobName();
		String groupName = trigger.getGroupName();

		StorageType storageType = getStorageType(groupName);

		try {
			if ((storageType == StorageType.MEMORY_SINGLE_INSTANCE) &&
				!isMemorySchedulerClusterLockOwner(
					lockMemorySchedulerCluster(null))) {

				for(ObjectValuePair<SchedulerResponse, TriggerState> value :
					_memorySingleInstanceJobs.values()) {

					SchedulerResponse schedulerResponse = value.getKey();

					if (schedulerResponse.getGroupName().equals(groupName) &&
						schedulerResponse.getJobName().equals(jobName)) {

						schedulerResponse.setTrigger(trigger);

						return;
					}
				}

				throw new Exception(
					"Unable to update trigger for memory single instance job");
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to update job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.update(trigger);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(storageType);
	}

	public Lock updateMemorySchedulerClusterMaster() throws SchedulerException {
		try {
			Lock lock = lockMemorySchedulerCluster(null);

			Address address = (Address)getDeserializedObject(lock.getOwner());

			if (ClusterExecutorUtil.isClusterNodeAlive(address)) {
				return lock;
			}

			return lockMemorySchedulerCluster(lock.getOwner());
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to update memory scheduler cluster master", e);
		}
	}

	protected Object callMaster(MethodKey methodKey, Object... arguments)
 		throws Exception {

		MethodHandler methodHandler = new MethodHandler(methodKey, arguments);

		Address masterAddress = (Address)getDeserializedObject(
			updateMemorySchedulerClusterMaster().getOwner());

		ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
			methodHandler, masterAddress);

		clusterRequest.setBeanIdentifier(_beanIdentifier);

		ClusterNodeResponses responses = ClusterExecutorUtil.execute(
			clusterRequest).get();

		return responses.getClusterResponse(masterAddress).getResult();
	}

	protected Object getDeserializedObject(String string) throws Exception {
		byte[] bytes = Base64.decode(string);

		UnsyncByteArrayInputStream byteArrayInputStream =
			new UnsyncByteArrayInputStream(bytes);

		ObjectInputStream objectInputStream = new ObjectInputStream(
			byteArrayInputStream);

		Object object = objectInputStream.readObject();

		objectInputStream.close();

		return object;
	}

	protected String getFullName(String jobName, String groupName) {
		return groupName.concat(StringPool.PERIOD).concat(jobName);
	}

	protected String getSerializedString(Object object) throws Exception {
		UnsyncByteArrayOutputStream byteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
			byteArrayOutputStream);

		objectOutputStream.writeObject(object);
		objectOutputStream.close();

		byte[] bytes = byteArrayOutputStream.toByteArray();

		return Base64.encode(bytes);
	}

	protected StorageType getStorageType(String groupName) {
		int delimiterIndex = groupName.indexOf(CharPool.POUND);

		String storageTypeString = groupName.substring(0, delimiterIndex);

		return StorageType.valueOf(storageTypeString);
	}

	protected void initMemorySingleInstanceJobs() throws Exception {
		List<SchedulerResponse> schedulerResponses =
			(List<SchedulerResponse>)callMaster(
				_getScheduledJobsByType,
				StorageType.MEMORY_SINGLE_INSTANCE);

		for (SchedulerResponse schedulerResponse : schedulerResponses) {
			String jobName = schedulerResponse.getJobName();
			String groupName =
				StorageType.MEMORY_SINGLE_INSTANCE.toString().concat(
				StringPool.UNDERLINE).concat(
				schedulerResponse.getGroupName());

			Trigger oldTrigger = schedulerResponse.getTrigger();

			Trigger newTrigger = TriggerFactoryUtil.buildTrigger(
				oldTrigger.getTriggerType(), jobName, groupName,
				oldTrigger.getStartDate(), oldTrigger.getEndDate(),
				oldTrigger.getTriggerContent());

			schedulerResponse.setTrigger(newTrigger);

			TriggerState triggerState = SchedulerEngineUtil.getJobState(
				schedulerResponse);

			schedulerResponse.getMessage().put(JOB_STATE, null);

			_memorySingleInstanceJobs.put(
				getFullName(jobName, groupName),
				new ObjectValuePair<SchedulerResponse,TriggerState>(
					schedulerResponse, triggerState));
		}

	}

	protected boolean isMemorySchedulerClusterLockOwner(Lock lock)
		throws Exception {

		boolean isMaster = _localClusterNodeAddress.equals(lock.getOwner());

		if (isMaster != _wasMaster) {
			if (_wasMaster) {
				_localClusterNodeAddress = getSerializedString(
					ClusterExecutorUtil.getLocalClusterNodeAddress());

				for (ObjectValuePair<SchedulerResponse, TriggerState>
					memorySingleInstanceJob :
						_memorySingleInstanceJobs.values()) {

					SchedulerResponse schedulerResponse =
						memorySingleInstanceJob.getKey();

					_schedulerEngine.delete(
						schedulerResponse.getJobName(),
						schedulerResponse.getGroupName());
				}

				initMemorySingleInstanceJobs();

				_log.info("Master of memory scheduler has been changed");
			}
		}

		_wasMaster = isMaster;

		return isMaster;
	}

	protected Lock lockMemorySchedulerCluster(String owner) throws Exception {
		Lock lock = null;

		if (owner == null) {
			lock = LockLocalServiceUtil.lock(
				_LOCK_CLASS_NAME, _LOCK_CLASS_NAME, _localClusterNodeAddress,
				PropsValues.MEMORY_CLUSTER_SCHEDULER_LOCK_CACHE_ENABLED);
		}
		else {
			lock = LockLocalServiceUtil.lock(
				_LOCK_CLASS_NAME, _LOCK_CLASS_NAME, owner,
				_localClusterNodeAddress,
				PropsValues.MEMORY_CLUSTER_SCHEDULER_LOCK_CACHE_ENABLED);
		}

		if (lock.isNew()) {
			_writeLock.lock();

			ProxyModeThreadLocal.setForceSync(true);

			try {
				for (ObjectValuePair<SchedulerResponse, TriggerState>
					memorySingleInstanceJob :
						_memorySingleInstanceJobs.values()) {

					SchedulerResponse schedulerResponse =
						memorySingleInstanceJob.getKey();

					_schedulerEngine.schedule(
						schedulerResponse.getTrigger(),
						schedulerResponse.getDescription(),
						schedulerResponse.getDestinationName(),
						schedulerResponse.getMessage());

					TriggerState triggerState =
						memorySingleInstanceJob.getValue();

					if (triggerState.equals(TriggerState.PAUSED)) {
						_schedulerEngine.pause(
							schedulerResponse.getJobName(),
							schedulerResponse.getGroupName());
					}
				}
			}
			finally {
				ProxyModeThreadLocal.setForceSync(false);

				_writeLock.unlock();
			}
		}

		return lock;
	}

	protected void skipClusterInvoking(StorageType storageType)
		throws SchedulerException {

		if (storageType == StorageType.PERSISTED) {
			SchedulerException exception = new SchedulerException();

			exception.setSwallowable(true);

			throw exception;
		}
	}

	private static final String _LOCK_CLASS_NAME =
		SchedulerEngine.class.getName();

	private static Log _log = LogFactoryUtil.getLog(
		ClusterSchedulerEngine.class);

	private static MethodKey _getScheduledJobByGroupName = new MethodKey(
		SchedulerEngine.class.getName(), "getScheduledJobs", String.class);
	private static MethodKey _getScheduledJobByJobNameGroupName = new MethodKey(
		SchedulerEngine.class.getName(), "getScheduledJob", String.class,
		String.class);
	private static MethodKey _getScheduledJobs = new MethodKey(
		SchedulerEngine.class.getName(), "getScheduledJobs");
	private static MethodKey _getScheduledJobsByType = new MethodKey(
		SchedulerEngineUtil.class.getName(), "getScheduledJobs",
		StorageType.class);

	private String _beanIdentifier;
	private volatile String _localClusterNodeAddress;
	private ClusterEventListener _memorySchedulerClusterEventListener;
	private Map<String, ObjectValuePair<SchedulerResponse, TriggerState>>
		_memorySingleInstanceJobs =
			new ConcurrentHashMap<String,
				ObjectValuePair<SchedulerResponse, TriggerState>>();
	private java.util.concurrent.locks.Lock _readLock;
	private SchedulerEngine _schedulerEngine;
	private volatile boolean _wasMaster;
	private java.util.concurrent.locks.Lock _writeLock;

	private class MemorySchedulerClusterEventListener
		implements ClusterEventListener {

		public void processClusterEvent(ClusterEvent clusterEvent) {
			ClusterEventType clusterEventType =
				clusterEvent.getClusterEventType();

			if (!clusterEventType.equals(ClusterEventType.DEPART)) {
				return;
			}

			try {
				updateMemorySchedulerClusterMaster();
			}
			catch (Exception e) {
				_log.error("Unable to update memory scheduler cluster lock", e);
			}
		}

	}

}