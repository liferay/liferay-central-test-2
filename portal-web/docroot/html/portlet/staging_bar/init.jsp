<%--
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
--%>

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.staging.LayoutStagingUtil" %>
<%@ page import="com.liferay.portal.kernel.staging.StagingUtil" %>
<%@ page import="com.liferay.portal.kernel.util.SessionParamUtil" %>
<%@ page import="com.liferay.portal.LayoutSetBranchNameException" %>
<%@ page import="com.liferay.portal.service.LayoutSetBranchLocalServiceUtil" %>
<%@ page import="com.liferay.portal.service.permission.LayoutSetBranchPermissionUtil" %>
<%@ page import="com.liferay.portal.util.comparator.LayoutRevisionIdComparator" %>

<%
Group group = null;
Group liveGroup = null;
Group stagingGroup = null;

boolean privateLayout = false;

if (layout != null) {
	group = layout.getGroup();

	if (group.isStagingGroup()) {
		liveGroup = group.getLiveGroup();
		stagingGroup = group;
	}
	else if (group.isStaged()) {
		if (group.isStagedRemotely()) {
			stagingGroup = group;
		}
		else {
			liveGroup = group;
			stagingGroup = group.getStagingGroup();
		}
	}

	privateLayout = layout.isPrivateLayout();
}
%>