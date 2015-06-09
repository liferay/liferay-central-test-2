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

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.LayoutPrototypeException" %><%@
page import="com.liferay.portal.NoSuchGroupException" %><%@
page import="com.liferay.portal.PortletIdException" %><%@
page import="com.liferay.portal.RemoteOptionsException" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistryUtil" %><%@
page import="com.liferay.portal.kernel.lar.ExportImportHelper" %><%@
page import="com.liferay.portal.kernel.lar.ExportImportHelperUtil" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataException" %><%@
page import="com.liferay.portal.kernel.lar.PortletDataHandlerChoice" %><%@
page import="com.liferay.portal.kernel.lar.UserIdStrategy" %><%@
page import="com.liferay.portal.kernel.lar.exportimportconfiguration.ExportImportConfigurationConstants" %><%@
page import="com.liferay.portal.kernel.lock.DuplicateLockException" %><%@
page import="com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil" %><%@
page import="com.liferay.portal.kernel.scheduler.StorageType" %><%@
page import="com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse" %><%@
page import="com.liferay.portal.lar.LayoutExporter" %><%@
page import="com.liferay.portal.lar.backgroundtask.LayoutExportBackgroundTaskExecutor" %><%@
page import="com.liferay.portal.lar.backgroundtask.LayoutImportBackgroundTaskExecutor" %><%@
page import="com.liferay.portal.lar.backgroundtask.LayoutRemoteStagingBackgroundTaskExecutor" %><%@
page import="com.liferay.portal.lar.backgroundtask.PortletExportBackgroundTaskExecutor" %><%@
page import="com.liferay.portal.lar.backgroundtask.PortletImportBackgroundTaskExecutor" %><%@
page import="com.liferay.portal.lar.backgroundtask.PortletStagingBackgroundTaskExecutor" %><%@
page import="com.liferay.portal.security.auth.RemoteAuthException" %><%@
page import="com.liferay.portlet.backgroundtask.util.comparator.BackgroundTaskComparatorFactoryUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StructureDuplicateStructureKeyException" %><%@
page import="com.liferay.portlet.exportimport.LARFileException" %><%@
page import="com.liferay.portlet.exportimport.LARFileNameException" %><%@
page import="com.liferay.portlet.exportimport.LARFileSizeException" %><%@
page import="com.liferay.portlet.exportimport.LARTypeException" %><%@
page import="com.liferay.portlet.exportimport.LayoutImportException" %><%@
page import="com.liferay.portlet.exportimport.action.ExportImportAction" %><%@
page import="com.liferay.portlet.exportimport.model.ExportImportConfiguration" %><%@
page import="com.liferay.portlet.exportimport.search.ExportImportConfigurationDisplayTerms" %><%@
page import="com.liferay.portlet.exportimport.search.ExportImportConfigurationSearchTerms" %><%@
page import="com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalServiceUtil" %><%@
page import="com.liferay.portlet.layoutsadmin.context.GroupDisplayContextHelper" %>

<%
PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(request);

String portletResource = ParamUtil.getString(request, "portletResource");

Portlet selPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/export_import/init-ext.jsp" %>