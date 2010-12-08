/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.staging;

import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.ServiceContext;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Raymond Augé
 */
public interface Staging {

	public void copyFromLive(PortletRequest PortletRequest) throws Exception;

	public void copyFromLive(PortletRequest PortletRequest, Portlet portlet)
		throws Exception;

	public void copyPortlet(
			PortletRequest PortletRequest, long sourceGroupId,
			long targetGroupId, long sourcePlid, long targetPlid,
			String portletId)
		throws Exception;

	public void copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap,
			Map<String, String[]> exportParameterMap, String remoteAddress,
			int remotePort, boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout,
			Map<String, String[]> importParameterMap, Date startDate,
			Date endDate)
		throws Exception;

	public void disableStaging(
			long scopeGroupId, long liveGroupId, ServiceContext serviceContext)
		throws Exception;

	public void disableStaging(
			PortletRequest portletRequest, long scopeGroupId, long liveGroupId,
			ServiceContext serviceContext)
		throws Exception;

	public void enableLocalStaging(
			long userId, long scopeGroupId, long liveGroupId,
			boolean branchingPublic, boolean branchingPrivate,
			ServiceContext serviceContext)
		throws Exception;

	public void enableRemoteStaging(
			long userId, long scopeGroupId, long liveGroupId,
			boolean branchingPublic, boolean branchingPrivate,
			String remoteAddress, long remoteGroupId, int remotePort,
			boolean secureConnection, ServiceContext serviceContext)
		throws Exception;

	public List<Layout> getMissingParentLayouts(Layout layout, long liveGroupId)
		throws Exception;

	public String getSchedulerGroupName(String destinationName, long groupId);

	public Map<String, String[]> getStagingParameters();

	public Map<String, String[]> getStagingParameters(
		PortletRequest PortletRequest);

	public void publishLayout(
			long userId, long plid, long liveGroupId, boolean includeChildren)
		throws Exception;

	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception;

	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<Long, Boolean> layoutIdMap,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception;

	public void publishLayouts(
			long userId, long sourceGroupId, long targetGroupId,
			boolean privateLayout, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws Exception;

	public void publishToLive(PortletRequest PortletRequest) throws Exception;

	public void publishToLive(PortletRequest PortletRequest, Portlet portlet)
		throws Exception;

	public void publishToRemote(PortletRequest PortletRequest) throws Exception;

	public void scheduleCopyFromLive(PortletRequest PortletRequest)
		throws Exception;

	public void schedulePublishToLive(PortletRequest PortletRequest)
		throws Exception;

	public void schedulePublishToRemote(PortletRequest PortletRequest)
		throws Exception;

	public void unscheduleCopyFromLive(PortletRequest PortletRequest)
		throws Exception;

	public void unschedulePublishToLive(PortletRequest PortletRequest)
		throws Exception;

	public void unschedulePublishToRemote(PortletRequest PortletRequest)
		throws Exception;

	public void updateStaging(PortletRequest PortletRequest) throws Exception;

}