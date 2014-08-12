<%--
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
--%>

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.lar.ExportImportDateUtil" %><%@
page import="com.liferay.portal.kernel.lar.ManifestSummary" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataContext" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataHandlerBoolean" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataHandlerControl" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataHandlerKeys" %><%@
page import="com.liferay.portal.kernel.util.DateRange" %><%@
page import="com.liferay.portal.lar.LayoutExporter" %>

<liferay-staging:defineObjects />

<%
boolean disableInputs = GetterUtil.getBoolean(request.getAttribute("liferay-staging:content:disableInputs"), false);
PortletRequest renderRequest = (PortletRequest)request.getAttribute("liferay-staging:content:renderRequest");
Map<String, String[]> parameterMap = (Map<String, String[]>)GetterUtil.getObject(request.getAttribute("liferay-staging:content:parameterMap"), Collections.emptyMap());
String type = GetterUtil.getString(request.getAttribute("liferay-staging:content:type"));

DateRange dateRange = null;

if (type.equals(Constants.EXPORT)) {
	dateRange = ExportImportDateUtil.getDateRange(renderRequest, liveGroupId, privateLayout, 0, null, ExportImportDateUtil.RANGE_ALL);
}
else {
	dateRange = ExportImportDateUtil.getDateRange(renderRequest, stagingGroupId, privateLayout, 0, null, ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE);
}

Date startDate = dateRange.getStartDate();
Date endDate = dateRange.getEndDate();

List<Portlet> dataSiteLevelPortlets = LayoutExporter.getDataSiteLevelPortlets(company.getCompanyId(), false);
%>