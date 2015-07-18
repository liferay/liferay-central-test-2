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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/staging" prefix="liferay-staging" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.exportimport.web.constants.ExportImportWebKeys" %><%@
page import="com.liferay.exportimport.web.portlet.action.ExportImportMVCActionCommand" %><%@
page import="com.liferay.exportimport.web.search.ExportImportConfigurationDisplayTerms" %><%@
page import="com.liferay.exportimport.web.search.ExportImportConfigurationSearchTerms" %><%@
page import="com.liferay.portal.LayoutPrototypeException" %><%@
page import="com.liferay.portal.LocaleException" %><%@
page import="com.liferay.portal.NoSuchGroupException" %><%@
page import="com.liferay.portal.NoSuchLayoutException" %><%@
page import="com.liferay.portal.NoSuchRoleException" %><%@
page import="com.liferay.portal.PortletIdException" %><%@
page import="com.liferay.portal.RemoteOptionsException" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistryUtil" %><%@
page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.exception.SystemException" %><%@
page import="com.liferay.portal.kernel.json.JSONArray" %><%@
page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %><%@
page import="com.liferay.portal.kernel.json.JSONObject" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.lock.DuplicateLockException" %><%@
page import="com.liferay.portal.kernel.messaging.DestinationNames" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.repository.model.FileEntry" %><%@
page import="com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil" %><%@
page import="com.liferay.portal.kernel.scheduler.StorageType" %><%@
page import="com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse" %><%@
page import="com.liferay.portal.kernel.search.BaseModelSearchResult" %><%@
page import="com.liferay.portal.kernel.search.Sort" %><%@
page import="com.liferay.portal.kernel.search.SortFactoryUtil" %><%@
page import="com.liferay.portal.kernel.servlet.SessionMessages" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.DateRange" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.MapUtil" %><%@
page import="com.liferay.portal.kernel.util.OrderByComparator" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsPropsUtil" %><%@
page import="com.liferay.portal.kernel.util.PropsKeys" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.Time" %><%@
page import="com.liferay.portal.kernel.util.Tuple" %><%@
page import="com.liferay.portal.kernel.util.UnicodeProperties" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.model.BackgroundTask" %><%@
page import="com.liferay.portal.model.Group" %><%@
page import="com.liferay.portal.model.Layout" %><%@
page import="com.liferay.portal.model.LayoutConstants" %><%@
page import="com.liferay.portal.model.LayoutSet" %><%@
page import="com.liferay.portal.model.LayoutSetBranch" %><%@
page import="com.liferay.portal.model.LayoutType" %><%@
page import="com.liferay.portal.model.LayoutTypePortlet" %><%@
page import="com.liferay.portal.model.Portlet" %><%@
page import="com.liferay.portal.model.Ticket" %><%@
page import="com.liferay.portal.model.TicketConstants" %><%@
page import="com.liferay.portal.model.User" %><%@
page import="com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil" %><%@
page import="com.liferay.portal.security.auth.AuthException" %><%@
page import="com.liferay.portal.security.auth.RemoteAuthException" %><%@
page import="com.liferay.portal.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portal.service.BackgroundTaskLocalServiceUtil" %><%@
page import="com.liferay.portal.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutLocalServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutSetBranchLocalServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutSetLocalServiceUtil" %><%@
page import="com.liferay.portal.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portal.service.ServiceContext" %><%@
page import="com.liferay.portal.service.TicketLocalServiceUtil" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portal.util.PortletKeys" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.portal.util.SessionClicks" %><%@
page import="com.liferay.portal.util.comparator.PortletTitleComparator" %><%@
page import="com.liferay.portlet.PortalPreferences" %><%@
page import="com.liferay.portlet.PortletPreferencesFactoryUtil" %><%@
page import="com.liferay.portlet.PortletURLUtil" %><%@
page import="com.liferay.portlet.backgroundtask.util.comparator.BackgroundTaskComparatorFactoryUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StructureDuplicateStructureKeyException" %><%@
page import="com.liferay.portlet.exportimport.LARFileException" %><%@
page import="com.liferay.portlet.exportimport.LARFileNameException" %><%@
page import="com.liferay.portlet.exportimport.LARFileSizeException" %><%@
page import="com.liferay.portlet.exportimport.LARTypeException" %><%@
page import="com.liferay.portlet.exportimport.LayoutImportException" %><%@
page import="com.liferay.portlet.exportimport.RemoteExportException" %><%@
page import="com.liferay.portlet.exportimport.backgroundtask.LayoutExportBackgroundTaskExecutor" %><%@
page import="com.liferay.portlet.exportimport.backgroundtask.LayoutImportBackgroundTaskExecutor" %><%@
page import="com.liferay.portlet.exportimport.backgroundtask.LayoutRemoteStagingBackgroundTaskExecutor" %><%@
page import="com.liferay.portlet.exportimport.backgroundtask.LayoutStagingBackgroundTaskExecutor" %><%@
page import="com.liferay.portlet.exportimport.backgroundtask.PortletExportBackgroundTaskExecutor" %><%@
page import="com.liferay.portlet.exportimport.backgroundtask.PortletImportBackgroundTaskExecutor" %><%@
page import="com.liferay.portlet.exportimport.backgroundtask.PortletStagingBackgroundTaskExecutor" %><%@
page import="com.liferay.portlet.exportimport.configuration.ExportImportConfigurationConstants" %><%@
page import="com.liferay.portlet.exportimport.lar.ExportImportDateUtil" %><%@
page import="com.liferay.portlet.exportimport.lar.ExportImportHelper" %><%@
page import="com.liferay.portlet.exportimport.lar.ExportImportHelperUtil" %><%@
page import="com.liferay.portlet.exportimport.lar.ManifestSummary" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataContext" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataContextFactoryUtil" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataException" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataHandler" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataHandlerBoolean" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataHandlerChoice" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataHandlerControl" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataHandlerKeys" %><%@
page import="com.liferay.portlet.exportimport.lar.UserIdStrategy" %><%@
page import="com.liferay.portlet.exportimport.model.ExportImportConfiguration" %><%@
page import="com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalServiceUtil" %><%@
page import="com.liferay.portlet.exportimport.staging.LayoutStagingUtil" %><%@
page import="com.liferay.portlet.exportimport.staging.StagingUtil" %><%@
page import="com.liferay.portlet.layoutsadmin.context.GroupDisplayContextHelper" %><%@
page import="com.liferay.portlet.trash.util.TrashUtil" %><%@
page import="com.liferay.taglib.search.ResultRow" %><%@
page import="com.liferay.taglib.search.SearchEntry" %><%@
page import="com.liferay.taglib.ui.util.SessionTreeJSClicks" %>

<%@ page import="java.io.Serializable" %>

<%@ page import="java.text.DecimalFormatSymbols" %><%@
page import="java.text.Format" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Calendar" %><%@
page import="java.util.Collections" %><%@
page import="java.util.Date" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.HashSet" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.ActionRequest" %><%@
page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<portlet:defineObjects />

<liferay-theme:defineObjects />

<%
WindowState windowState = liferayPortletRequest.getWindowState();

PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);

String currentURL = currentURLObj.toString();

PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(request);

String portletResource = ParamUtil.getString(request, "portletResource");

Portlet selPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/init-ext.jsp" %>