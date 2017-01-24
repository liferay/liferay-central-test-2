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

package com.liferay.sync.internal.messaging;

import com.liferay.document.library.kernel.model.DLSyncEvent;
import com.liferay.document.library.kernel.service.DLSyncEventLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseSchedulerEntryMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.sync.service.SyncDLFileVersionDiffLocalService;
import com.liferay.sync.service.SyncDLObjectLocalService;
import com.liferay.sync.service.configuration.SyncServiceConfigurationValues;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dennis Ju
 */
@Component(immediate = true, service = SyncMaintenanceMessageListener.class)
public class SyncMaintenanceMessageListener
	extends BaseSchedulerEntryMessageListener {

	public static final String DESTINATION_NAME =
		"liferay/sync_maintenance_processor";

	@Activate
	protected void activate() {
		schedulerEntryImpl.setTrigger(
			TriggerFactoryUtil.createTrigger(
				getEventListenerClass(), getEventListenerClass(), 1,
				TimeUnit.HOUR));

		_schedulerEngineHelper.register(
			this, schedulerEntryImpl, DESTINATION_NAME);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		_syncDLFileVersionDiffLocalService.
			deleteExpiredSyncDLFileVersionDiffs();

		if (SyncServiceConfigurationValues.SYNC_FILE_DIFF_CACHE_ENABLED) {
			try {
				_syncDLFileVersionDiffLocalService.
					deleteExpiredSyncDLFileVersionDiffs();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		try {
			ActionableDynamicQuery actionableDynamicQuery =
				_dlSyncEventLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setAddCriteriaMethod(
				new ActionableDynamicQuery.AddCriteriaMethod() {

					@Override
					public void addCriteria(DynamicQuery dynamicQuery) {
						Property modifiedTime = PropertyFactoryUtil.forName(
							"modifiedTime");

						long latestModifiedTime =
							_syncDLObjectLocalService.getLatestModifiedTime();

						dynamicQuery.add(
							modifiedTime.le(latestModifiedTime - Time.HOUR));
					}

				});
			actionableDynamicQuery.setPerformActionMethod(
				new ActionableDynamicQuery.PerformActionMethod<DLSyncEvent>() {

					@Override
					public void performAction(DLSyncEvent dlSyncEvent)
						throws PortalException {

						_dlSyncEventLocalService.deleteDLSyncEvent(dlSyncEvent);
					}

				});

			actionableDynamicQuery.performActions();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Reference(unbind = "-")
	protected void setDLSyncEventLocalService(
		DLSyncEventLocalService dlSyncEventLocalService) {

		_dlSyncEventLocalService = dlSyncEventLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setSchedulerEngineHelper(
		SchedulerEngineHelper schedulerEngineHelper) {

		_schedulerEngineHelper = schedulerEngineHelper;
	}

	@Reference(unbind = "-")
	protected void setSyncDLFileVersionDiffLocalService(
		SyncDLFileVersionDiffLocalService syncDLFileVersionDiffLocalService) {

		_syncDLFileVersionDiffLocalService = syncDLFileVersionDiffLocalService;
	}

	@Reference(unbind = "-")
	protected void setSyncDLObjectLocalService(
		SyncDLObjectLocalService syncDLObjectLocalService) {

		_syncDLObjectLocalService = syncDLObjectLocalService;
	}

	@Reference(unbind = "-")
	protected void setTriggerFactory(TriggerFactory triggerFactory) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SyncMaintenanceMessageListener.class);

	private DLSyncEventLocalService _dlSyncEventLocalService;
	private SchedulerEngineHelper _schedulerEngineHelper;
	private SyncDLFileVersionDiffLocalService
		_syncDLFileVersionDiffLocalService;
	private SyncDLObjectLocalService _syncDLObjectLocalService;

}