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

package com.liferay.sync.web.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.sync.service.SyncDeviceLocalService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
public abstract class BaseSyncPortlet extends MVCPortlet {

	public void deleteDevice(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long syncDeviceId = ParamUtil.getLong(actionRequest, "syncDeviceId");

		syncDeviceLocalService.deleteSyncDevice(syncDeviceId);
	}

	public void updateDevice(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long syncDeviceId = ParamUtil.getLong(actionRequest, "syncDeviceId");

		int status = ParamUtil.getInteger(actionRequest, "status");

		syncDeviceLocalService.updateStatus(syncDeviceId, status);
	}

	@Reference(unbind = "-")
	protected void setSyncDeviceLocalService(
		SyncDeviceLocalService syncDeviceLocalService) {

		this.syncDeviceLocalService = syncDeviceLocalService;
	}

	protected SyncDeviceLocalService syncDeviceLocalService;

}