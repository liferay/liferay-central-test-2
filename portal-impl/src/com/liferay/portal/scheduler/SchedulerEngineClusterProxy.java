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

import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterEventType;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineClusterManager;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Tina Tian
 */
public class SchedulerEngineClusterProxy
	implements SchedulerEngine, SchedulerEngineClusterManager {

	public void delete(String groupName) throws SchedulerException {
		_schedulerEngine.delete(groupName);
	}

	public void delete(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.delete(jobName, groupName);
	}

	public SchedulerResponse getScheduledJob(String jobName, String groupName)
		throws SchedulerException {

		return _schedulerEngine.getScheduledJob(jobName, groupName);
	}

	public List<SchedulerResponse> getScheduledJobs()
		throws SchedulerException {

		return _schedulerEngine.getScheduledJobs();
	}

	public List<SchedulerResponse> getScheduledJobs(String groupName)
		throws SchedulerException {

		return _schedulerEngine.getScheduledJobs(groupName);
	}

	public void pause(String groupName) throws SchedulerException {
		_schedulerEngine.pause(groupName);
	}

	public void pause(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.pause(jobName, groupName);
	}

	public void resume(String groupName) throws SchedulerException {
		_schedulerEngine.resume(groupName);
	}

	public void resume(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.resume(jobName, groupName);
	}

	public void schedule(
			Trigger trigger, String description, String destinationName,
			Message message)
		throws SchedulerException {

		_schedulerEngine.schedule(
			trigger, description, destinationName, message);
	}

	public void setSchedulerEngine (SchedulerEngine schedulerEngine) {
		_schedulerEngine = schedulerEngine;
	}

	public void shutdown() throws SchedulerException {
		try {
			if (PropsValues.CLUSTER_LINK_ENABLED) {
				ClusterExecutorUtil.removeClusterEventListener(
					_memeorySchedulerClusterEventListener);

				LockLocalServiceUtil.unlock(
					_classNameForLock, _classNameForLock, _localClusterNodeId,
					_retrieveFromCache);
			}
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to shutdown scheduler", e);
		}

		_schedulerEngine.shutdown();
	}

	public void start() throws SchedulerException {
		try {
			if (PropsValues.CLUSTER_LINK_ENABLED) {
				_memeorySchedulerClusterEventListener =
					new MemorySchedulerClusterEventListener();

				ClusterExecutorUtil.addClusterEventListener(
					_memeorySchedulerClusterEventListener);

				lockMemorySchedulerCluster(false, null);
			}
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to start scheduler", e);
		}

		_schedulerEngine.start();
	}

	public void suppressError(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.suppressError(jobName, groupName);
	}

	public void unschedule(String groupName) throws SchedulerException {
		_schedulerEngine.unschedule(groupName);
	}

	public void unschedule(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.unschedule(jobName, groupName);
	}

	/**
	 * @deprecated {@link #unschedule(String, String)}
	 */
	public void unschedule(Trigger trigger) throws SchedulerException {
		_schedulerEngine.unschedule(trigger);
	}

	public void update(Trigger trigger) throws SchedulerException {
		_schedulerEngine.update(trigger);
	}

	public void updateMemorySchedulerClusterMaster() throws SchedulerException {
		try {
			Lock lock = lockMemorySchedulerCluster(false, null);

			if (ClusterExecutorUtil.isClusterNodeAlive(lock.getOwner())) {
				return;
			}

			lockMemorySchedulerCluster(true, lock.getOwner());
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to updae memory scheduler cluster master", e);
		}
	}

	protected boolean isMemorySchedulerClusterLockOwner(Lock lock)
		throws Exception {

		String localClusterNodeId =
			ClusterExecutorUtil.getLocalClusterNode().getClusterNodeId();

		if (localClusterNodeId.equals(lock.getOwner())) {
			return true;
		}

		return false;
	}

	protected Lock lockMemorySchedulerCluster(
			boolean replaceOldLock, String oldOwner)
		throws Exception {

		if (_localClusterNodeId == null) {
			_localClusterNodeId =
				ClusterExecutorUtil.getLocalClusterNode().getClusterNodeId();
		}

		boolean[] isNewLock = new boolean[1];

		Lock lock = LockLocalServiceUtil.lock(
			_classNameForLock, _classNameForLock, _localClusterNodeId,
			_retrieveFromCache, replaceOldLock, isNewLock, oldOwner);

		return lock;
	}

	private static Log _log = LogFactoryUtil.getLog(
		SchedulerEngineClusterProxy.class);

	private static String _classNameForLock = SchedulerEngine.class.getName();
	private static String _localClusterNodeId;
	private ClusterEventListener _memeorySchedulerClusterEventListener;
	private boolean _retrieveFromCache;
	private SchedulerEngine _schedulerEngine;

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
			catch (Exception ex) {
				if (_log.isErrorEnabled()) {
					_log.error(
						"Update memeory scheduler cluster lock failed",
						ex);
				}
			}
		}

	}

}