/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.scheduler.internal;

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.Duration;
import com.liferay.portal.kernel.cal.Recurrence;
import com.liferay.portal.kernel.cal.RecurrenceSerializer;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.cluster.ClusterableProxyFactory;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.scheduler.JobState;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.StorageTypeAware;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListener;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListenerWrapper;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.util.PortalUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = SchedulerEngineHelper.class)
public class SchedulerEngineHelperImpl implements SchedulerEngineHelper {

	@Override
	public void addScriptingJob(
			Trigger trigger, StorageType storageType, String description,
			String language, String script, int exceptionsMaxSize)
		throws SchedulerException {

		Message message = new Message();

		message.put(SchedulerEngine.LANGUAGE, language);
		message.put(SchedulerEngine.SCRIPT, script);

		schedule(
			trigger, storageType, description,
			DestinationNames.SCHEDULER_SCRIPTING, message, exceptionsMaxSize);
	}

	@Override
	public void auditSchedulerJobs(Message message, TriggerState triggerState)
		throws SchedulerException {

		if (!_auditMessageSchedulerJob || (_auditRouter == null)) {
			return;
		}

		try {
			AuditMessage auditMessage = new AuditMessage(
				SchedulerEngine.SCHEDULER, CompanyConstants.SYSTEM, 0,
				StringPool.BLANK, SchedulerEngine.class.getName(), "0",
				triggerState.toString(), new Date(),
				_jsonFactory.createJSONObject(_jsonFactory.serialize(message)));

			auditMessage.setServerName(InetAddressUtil.getLocalHostName());
			auditMessage.setServerPort(PortalUtil.getPortalLocalPort(false));

			_auditRouter.route(auditMessage);
		}
		catch (Exception e) {
			throw new SchedulerException(e);
		}
	}

	@Override
	public void delete(SchedulerEntry schedulerEntry, StorageType storageType)
		throws SchedulerException {

		Trigger trigger = schedulerEntry.getTrigger();

		delete(trigger.getJobName(), trigger.getGroupName(), storageType);
	}

	@Override
	public void delete(String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngine.delete(groupName, storageType);
	}

	@Override
	public void delete(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngine.delete(jobName, groupName, storageType);
	}

	@Override
	public String getCronText(Calendar calendar, boolean timeZoneSensitive) {
		return getCronText(
			null, calendar, timeZoneSensitive, Recurrence.NO_RECURRENCE);
	}

	@Override
	public String getCronText(
		PortletRequest portletRequest, Calendar calendar,
		boolean timeZoneSensitive, int recurrenceType) {

		Calendar recurrenceCalendar = null;

		if (timeZoneSensitive) {
			recurrenceCalendar = CalendarFactoryUtil.getCalendar();

			recurrenceCalendar.setTime(calendar.getTime());
		}
		else {
			recurrenceCalendar = (Calendar)calendar.clone();
		}

		Recurrence recurrence = new Recurrence(
			recurrenceCalendar, new Duration(1, 0, 0, 0), recurrenceType);

		recurrence.setWeekStart(Calendar.SUNDAY);

		if (recurrenceType == Recurrence.DAILY) {
			int dailyType = ParamUtil.getInteger(portletRequest, "dailyType");

			if (dailyType == 0) {
				int dailyInterval = ParamUtil.getInteger(
					portletRequest, "dailyInterval", 1);

				recurrence.setInterval(dailyInterval);
			}
			else {
				DayAndPosition[] dayPos = {
					new DayAndPosition(Calendar.MONDAY, 0),
					new DayAndPosition(Calendar.TUESDAY, 0),
					new DayAndPosition(Calendar.WEDNESDAY, 0),
					new DayAndPosition(Calendar.THURSDAY, 0),
					new DayAndPosition(Calendar.FRIDAY, 0)
				};

				recurrence.setByDay(dayPos);
			}
		}
		else if (recurrenceType == Recurrence.WEEKLY) {
			int weeklyInterval = ParamUtil.getInteger(
				portletRequest, "weeklyInterval", 1);

			recurrence.setInterval(weeklyInterval);

			List<DayAndPosition> dayPos = new ArrayList<>();

			addWeeklyDayPos(portletRequest, dayPos, Calendar.SUNDAY);
			addWeeklyDayPos(portletRequest, dayPos, Calendar.MONDAY);
			addWeeklyDayPos(portletRequest, dayPos, Calendar.TUESDAY);
			addWeeklyDayPos(portletRequest, dayPos, Calendar.WEDNESDAY);
			addWeeklyDayPos(portletRequest, dayPos, Calendar.THURSDAY);
			addWeeklyDayPos(portletRequest, dayPos, Calendar.FRIDAY);
			addWeeklyDayPos(portletRequest, dayPos, Calendar.SATURDAY);

			if (dayPos.isEmpty()) {
				dayPos.add(new DayAndPosition(Calendar.MONDAY, 0));
			}

			recurrence.setByDay(dayPos.toArray(new DayAndPosition[0]));
		}
		else if (recurrenceType == Recurrence.MONTHLY) {
			int monthlyType = ParamUtil.getInteger(
				portletRequest, "monthlyType");

			if (monthlyType == 0) {
				int monthlyDay = ParamUtil.getInteger(
					portletRequest, "monthlyDay0", 1);

				recurrence.setByMonthDay(new int[] {monthlyDay});

				int monthlyInterval = ParamUtil.getInteger(
					portletRequest, "monthlyInterval0", 1);

				recurrence.setInterval(monthlyInterval);
			}
			else {
				int monthlyPos = ParamUtil.getInteger(
					portletRequest, "monthlyPos");
				int monthlyDay = ParamUtil.getInteger(
					portletRequest, "monthlyDay1");

				DayAndPosition[] dayPos = {
					new DayAndPosition(monthlyDay, monthlyPos)
				};

				recurrence.setByDay(dayPos);

				int monthlyInterval = ParamUtil.getInteger(
					portletRequest, "monthlyInterval1", 1);

				recurrence.setInterval(monthlyInterval);
			}
		}
		else if (recurrenceType == Recurrence.YEARLY) {
			int yearlyType = ParamUtil.getInteger(portletRequest, "yearlyType");

			if (yearlyType == 0) {
				int yearlyMonth = ParamUtil.getInteger(
					portletRequest, "yearlyMonth0");
				int yearlyDay = ParamUtil.getInteger(
					portletRequest, "yearlyDay0", 1);

				recurrence.setByMonth(new int[] {yearlyMonth});
				recurrence.setByMonthDay(new int[] {yearlyDay});

				int yearlyInterval = ParamUtil.getInteger(
					portletRequest, "yearlyInterval0", 1);

				recurrence.setInterval(yearlyInterval);
			}
			else {
				int yearlyPos = ParamUtil.getInteger(
					portletRequest, "yearlyPos");
				int yearlyDay = ParamUtil.getInteger(
					portletRequest, "yearlyDay1");
				int yearlyMonth = ParamUtil.getInteger(
					portletRequest, "yearlyMonth1");

				DayAndPosition[] dayPos = {
					new DayAndPosition(yearlyDay, yearlyPos)
				};

				recurrence.setByDay(dayPos);

				recurrence.setByMonth(new int[] {yearlyMonth});

				int yearlyInterval = ParamUtil.getInteger(
					portletRequest, "yearlyInterval1", 1);

				recurrence.setInterval(yearlyInterval);
			}
		}

		return RecurrenceSerializer.toCronText(recurrence);
	}

	@Override
	public Date getEndTime(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		if (triggerState.equals(TriggerState.NORMAL) ||
			triggerState.equals(TriggerState.PAUSED)) {

			return (Date)message.get(SchedulerEngine.END_TIME);
		}
		else {
			return jobState.getTriggerDate(SchedulerEngine.END_TIME);
		}
	}

	@Override
	public Date getEndTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getScheduledJob(
			jobName, groupName, storageType);

		if (schedulerResponse != null) {
			return getEndTime(schedulerResponse);
		}

		return null;
	}

	@Override
	public Date getFinalFireTime(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		if (triggerState.equals(TriggerState.NORMAL) ||
			triggerState.equals(TriggerState.PAUSED)) {

			return (Date)message.get(SchedulerEngine.FINAL_FIRE_TIME);
		}
		else {
			return jobState.getTriggerDate(SchedulerEngine.FINAL_FIRE_TIME);
		}
	}

	@Override
	public Date getFinalFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getScheduledJob(
			jobName, groupName, storageType);

		if (schedulerResponse != null) {
			return getFinalFireTime(schedulerResponse);
		}

		return null;
	}

	@Override
	public ObjectValuePair<Exception, Date>[] getJobExceptions(
		SchedulerResponse schedulerResponse) {

		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		return jobState.getExceptions();
	}

	@Override
	public ObjectValuePair<Exception, Date>[] getJobExceptions(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getScheduledJob(
			jobName, groupName, storageType);

		if (schedulerResponse != null) {
			return getJobExceptions(schedulerResponse);
		}

		return null;
	}

	@Override
	public TriggerState getJobState(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		return jobState.getTriggerState();
	}

	@Override
	public TriggerState getJobState(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getScheduledJob(
			jobName, groupName, storageType);

		if (schedulerResponse != null) {
			return getJobState(schedulerResponse);
		}

		return null;
	}

	@Override
	public Date getNextFireTime(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		if (triggerState.equals(TriggerState.NORMAL) ||
			triggerState.equals(TriggerState.PAUSED)) {

			return (Date)message.get(SchedulerEngine.NEXT_FIRE_TIME);
		}
		else {
			return jobState.getTriggerDate(SchedulerEngine.NEXT_FIRE_TIME);
		}
	}

	@Override
	public Date getNextFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getScheduledJob(
			jobName, groupName, storageType);

		if (schedulerResponse != null) {
			return getNextFireTime(schedulerResponse);
		}

		return null;
	}

	@Override
	public Date getPreviousFireTime(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		if (triggerState.equals(TriggerState.NORMAL) ||
			triggerState.equals(TriggerState.PAUSED)) {

			return (Date)message.get(SchedulerEngine.PREVIOUS_FIRE_TIME);
		}
		else {
			return jobState.getTriggerDate(SchedulerEngine.PREVIOUS_FIRE_TIME);
		}
	}

	@Override
	public Date getPreviousFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getScheduledJob(
			jobName, groupName, storageType);

		if (schedulerResponse != null) {
			return getPreviousFireTime(schedulerResponse);
		}

		return null;
	}

	@Override
	public SchedulerResponse getScheduledJob(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngine.getScheduledJob(
			jobName, groupName, storageType);
	}

	@Override
	public List<SchedulerResponse> getScheduledJobs()
		throws SchedulerException {

		return _schedulerEngine.getScheduledJobs();
	}

	@Override
	public List<SchedulerResponse> getScheduledJobs(StorageType storageType)
		throws SchedulerException {

		return _schedulerEngine.getScheduledJobs(storageType);
	}

	@Override
	public List<SchedulerResponse> getScheduledJobs(
			String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngine.getScheduledJobs(groupName, storageType);
	}

	@Override
	public Date getStartTime(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		if (triggerState.equals(TriggerState.NORMAL) ||
			triggerState.equals(TriggerState.PAUSED)) {

			return (Date)message.get(SchedulerEngine.START_TIME);
		}
		else {
			return jobState.getTriggerDate(SchedulerEngine.START_TIME);
		}
	}

	@Override
	public Date getStartTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getScheduledJob(
			jobName, groupName, storageType);

		if (schedulerResponse != null) {
			return getStartTime(schedulerResponse);
		}

		return null;
	}

	@Override
	public boolean isClusteredSchedulerEngine() {
		if (_schedulerEngine instanceof ClusterSchedulerEngine) {
			return true;
		}

		return false;
	}

	@Override
	public void pause(String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngine.pause(groupName, storageType);
	}

	@Override
	public void pause(String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngine.pause(jobName, groupName, storageType);
	}

	@Override
	public String register(
		MessageListener messageListener, SchedulerEntry schedulerEntry) {

		SchedulerEventMessageListenerWrapper
			schedulerEventMessageListenerWrapper =
				new SchedulerEventMessageListenerWrapper();

		schedulerEventMessageListenerWrapper.setMessageListener(
			messageListener);

		schedulerEventMessageListenerWrapper.setSchedulerEntry(schedulerEntry);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("destination.name", DestinationNames.SCHEDULER_DISPATCH);

		ServiceRegistration<SchedulerEventMessageListener> serviceRegistration =
			_bundleContext.registerService(
				SchedulerEventMessageListener.class,
				schedulerEventMessageListenerWrapper, properties);

		synchronized (_serviceRegistrations) {
			_serviceRegistrations.put(messageListener, serviceRegistration);
		}

		return schedulerEventMessageListenerWrapper.getMessageListenerUUID();
	}

	@Override
	public void resume(String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngine.resume(groupName, storageType);
	}

	@Override
	public void resume(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngine.resume(jobName, groupName, storageType);
	}

	@Override
	public void schedule(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Message message, int exceptionsMaxSize)
		throws SchedulerException {

		if (message == null) {
			message = new Message();
		}

		message.put(SchedulerEngine.EXCEPTIONS_MAX_SIZE, exceptionsMaxSize);

		_schedulerEngine.schedule(
			trigger, description, destinationName, message, storageType);
	}

	@Override
	public void schedule(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Object payload, int exceptionsMaxSize)
		throws SchedulerException {

		Message message = new Message();

		message.setPayload(payload);

		schedule(
			trigger, storageType, description, destinationName, message,
			exceptionsMaxSize);
	}

	@Override
	public void shutdown() throws SchedulerException {
		_schedulerEngine.shutdown();
	}

	@Override
	public void start() throws SchedulerException {
		_schedulerEngine.start();
	}

	@Override
	public void suppressError(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngine.suppressError(jobName, groupName, storageType);
	}

	@Override
	public void unregister(MessageListener messageListener) {
		synchronized (_serviceRegistrations) {
			ServiceRegistration<SchedulerEventMessageListener>
				serviceRegistration = _serviceRegistrations.remove(
					messageListener);

			serviceRegistration.unregister();
		}
	}

	@Override
	public void unschedule(
			SchedulerEntry schedulerEntry, StorageType storageType)
		throws SchedulerException {

		Trigger trigger = schedulerEntry.getTrigger();

		unschedule(trigger.getJobName(), trigger.getGroupName(), storageType);
	}

	@Override
	public void unschedule(String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngine.unschedule(groupName, storageType);
	}

	@Override
	public void unschedule(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngine.unschedule(jobName, groupName, storageType);
	}

	@Override
	public void update(
			String jobName, String groupName, StorageType storageType,
			String description, String language, String script,
			int exceptionsMaxSize)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getScheduledJob(
			jobName, groupName, storageType);

		if (schedulerResponse == null) {
			return;
		}

		Trigger trigger = schedulerResponse.getTrigger();

		if (trigger == null) {
			return;
		}

		Message message = schedulerResponse.getMessage();

		if (message == null) {
			return;
		}

		addScriptingJob(
			trigger, storageType, description, language, script,
			exceptionsMaxSize);
	}

	@Override
	public void update(Trigger trigger, StorageType storageType)
		throws SchedulerException {

		_schedulerEngine.update(trigger, storageType);
	}

	@Activate
	protected void activate(ComponentContext componentContext)
		throws Exception {

		_auditMessageSchedulerJob = GetterUtil.getBoolean(
			_props.get(PropsKeys.AUDIT_MESSAGE_SCHEDULER_JOB));

		_bundleContext = componentContext.getBundleContext();

		if (_clusterLink.isEnabled() &&
			GetterUtil.getBoolean(_props.get(PropsKeys.SCHEDULER_ENABLED))) {

			ClusterSchedulerEngine clusterSchedulerEngine =
				new ClusterSchedulerEngine(_schedulerEngine);

			clusterSchedulerEngine.setClusterMasterExecutor(
				_clusterMasterExecutor);
			clusterSchedulerEngine.setProps(_props);
			clusterSchedulerEngine.setSchedulerEngineHelper(this);

			_serviceRegistration = _bundleContext.registerService(
				IdentifiableOSGiService.class, clusterSchedulerEngine,
				new HashMapDictionary<String, Object>());

			_schedulerEngine = ClusterableProxyFactory.createClusterableProxy(
				clusterSchedulerEngine);
		}

		if (GetterUtil.getBoolean(_props.get(PropsKeys.SCHEDULER_ENABLED))) {
			Filter filter = _bundleContext.createFilter(
				"(objectClass=" +
					SchedulerEventMessageListener.class.getName() + ")");

			_serviceTracker = new ServiceTracker<>(
				_bundleContext, filter,
				new SchedulerEventMessageListenerServiceTrackerCustomizer());

			_serviceTracker.open();
		}
	}

	protected void addWeeklyDayPos(
		PortletRequest portletRequest, List<DayAndPosition> list, int day) {

		if (ParamUtil.getBoolean(portletRequest, "weeklyDayPos" + day)) {
			list.add(new DayAndPosition(day, 0));
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceTracker != null) {
			_serviceTracker.close();

			_serviceTracker = null;
		}

		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}

		try {
			shutdown();
		}
		catch (SchedulerException e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to shutdown scheduler", e);
			}
		}

		for (ServiceRegistration<SchedulerEventMessageListener>
				serviceRegistration : _serviceRegistrations.values()) {

			serviceRegistration.unregister();
		}

		_bundleContext = null;
	}

	protected SchedulerEngine getSchedulerEngine() {
		return _schedulerEngine;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setAuditRouter(AuditRouter auditRouter) {
		_auditRouter = auditRouter;
	}

	@Reference(unbind = "-")
	protected void setClusterLink(ClusterLink clusterLink) {
		_clusterLink = clusterLink;
	}

	@Reference(unbind = "-")
	protected void setClusterMasterExecutor(
		ClusterMasterExecutor clusterMasterExecutor) {

		_clusterMasterExecutor = clusterMasterExecutor;
	}

	@Reference(unbind = "-")
	protected void setJsonFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_props = props;
	}

	@Reference(target = "(bean.id=*.SchedulerEngineProxyBean)", unbind = "-")
	protected void setSchedulerEngine(SchedulerEngine schedulerEngine) {
		_schedulerEngine = schedulerEngine;
	}

	protected void unsetAuditRouter(AuditRouter auditRouter) {
		_auditRouter = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SchedulerEngineHelperImpl.class);

	private boolean _auditMessageSchedulerJob;
	private volatile AuditRouter _auditRouter;
	private volatile BundleContext _bundleContext;
	private ClusterLink _clusterLink;
	private ClusterMasterExecutor _clusterMasterExecutor;
	private JSONFactory _jsonFactory;
	private final Map<String, ServiceRegistration<MessageListener>>
		_messageListenerServiceRegistrations = new HashMap<>();
	private Props _props;
	private SchedulerEngine _schedulerEngine;
	private ServiceRegistration<IdentifiableOSGiService> _serviceRegistration;
	private final Map
		<MessageListener, ServiceRegistration<SchedulerEventMessageListener>>
			_serviceRegistrations = new HashMap<>();
	private volatile ServiceTracker
			<SchedulerEventMessageListener, SchedulerEventMessageListener>
		_serviceTracker;

	private class SchedulerEventMessageListenerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<SchedulerEventMessageListener, SchedulerEventMessageListener> {

		@Override
		public SchedulerEventMessageListener addingService(
			ServiceReference<SchedulerEventMessageListener> serviceReference) {

			Bundle bundle = serviceReference.getBundle();

			BundleContext bundleContext = bundle.getBundleContext();

			SchedulerEventMessageListener schedulerEventMessageListener =
				bundleContext.getService(serviceReference);

			SchedulerEntry schedulerEntry =
				schedulerEventMessageListener.getSchedulerEntry();

			StorageType storageType = StorageType.MEMORY_CLUSTERED;

			if (schedulerEntry == null) {
				return null;
			}

			if (schedulerEntry instanceof StorageTypeAware) {
				StorageTypeAware storageTypeAware =
					(StorageTypeAware)schedulerEntry;

				storageType = storageTypeAware.getStorageType();
			}

			String destinationName = (String)serviceReference.getProperty(
				"destination.name");

			if (Validator.isNull(destinationName)) {
				destinationName = DestinationNames.SCHEDULER_DISPATCH;
			}

			try {
				Message message = new Message();

				message.put(
					SchedulerEngine.MESSAGE_LISTENER_UUID,
					schedulerEventMessageListener.getMessageListenerUUID());

				schedule(
					schedulerEntry.getTrigger(), storageType,
					schedulerEntry.getDescription(),
					DestinationNames.SCHEDULER_DISPATCH, message, 0);

				Dictionary<String, Object> properties =
					new HashMapDictionary<>();

				properties.put("destination.name", destinationName);

				ServiceRegistration<MessageListener> serviceRegistration =
					bundleContext.registerService(
						MessageListener.class, schedulerEventMessageListener,
						properties);

				_messageListenerServiceRegistrations.put(
					schedulerEntry.getEventListenerClass(),
					serviceRegistration);

				return schedulerEventMessageListener;
			}
			catch (SchedulerException se) {
				_log.error(se, se);
			}

			return null;
		}

		@Override
		public void modifiedService(
			ServiceReference<SchedulerEventMessageListener> serviceReference,
			SchedulerEventMessageListener schedulerEventMessageListener) {
		}

		@Override
		public void removedService(
			ServiceReference<SchedulerEventMessageListener> serviceReference,
			SchedulerEventMessageListener schedulerEntryMessageListener) {

			Bundle bundle = serviceReference.getBundle();

			BundleContext bundleContext = bundle.getBundleContext();

			bundleContext.ungetService(serviceReference);

			StorageType storageType = StorageType.MEMORY_CLUSTERED;

			SchedulerEntry schedulerEntry =
				schedulerEntryMessageListener.getSchedulerEntry();

			if (schedulerEntry == null) {
				return;
			}

			if (schedulerEntry instanceof StorageTypeAware) {
				StorageTypeAware storageTypeAware =
					(StorageTypeAware)schedulerEntry;

				storageType = storageTypeAware.getStorageType();
			}

			try {
				unschedule(schedulerEntry, storageType);
			}
			catch (SchedulerException se) {
				_log.error(se, se);
			}

			ServiceRegistration<MessageListener>
				messageListenerServiceRegistration =
					_messageListenerServiceRegistrations.get(
						schedulerEntry.getEventListenerClass());

			messageListenerServiceRegistration.unregister();
		}

	}

}