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
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.layout.admin.web.display.context.LayoutsAdminDisplayContext" %><%@
page import="com.liferay.mobile.device.rules.model.MDRAction" %><%@
page import="com.liferay.mobile.device.rules.model.MDRRuleGroup" %><%@
page import="com.liferay.mobile.device.rules.model.MDRRuleGroupInstance" %><%@
page import="com.liferay.mobile.device.rules.service.MDRActionLocalServiceUtil" %><%@
page import="com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceServiceUtil" %><%@
page import="com.liferay.mobile.device.rules.service.MDRRuleGroupLocalServiceUtil" %><%@
page import="com.liferay.mobile.device.rules.service.permission.MDRPermission" %><%@
page import="com.liferay.mobile.device.rules.service.permission.MDRRuleGroupInstancePermission" %><%@
page import="com.liferay.mobile.device.rules.util.comparator.RuleGroupInstancePriorityComparator" %><%@
page import="com.liferay.portal.ImageTypeException" %><%@
page import="com.liferay.portal.LayoutFriendlyURLException" %><%@
page import="com.liferay.portal.LayoutFriendlyURLsException" %><%@
page import="com.liferay.portal.LayoutNameException" %><%@
page import="com.liferay.portal.LayoutTypeException" %><%@
page import="com.liferay.portal.NoSuchGroupException" %><%@
page import="com.liferay.portal.NoSuchLayoutException" %><%@
page import="com.liferay.portal.NoSuchLayoutSetBranchException" %><%@
page import="com.liferay.portal.NoSuchRoleException" %><%@
page import="com.liferay.portal.RequiredLayoutException" %><%@
page import="com.liferay.portal.SitemapChangeFrequencyException" %><%@
page import="com.liferay.portal.SitemapIncludeException" %><%@
page import="com.liferay.portal.SitemapPagePriorityException" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.RowChecker" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.plugin.PluginPackage" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.servlet.DynamicServletRequest" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants" %><%@
page import="com.liferay.portal.kernel.template.StringTemplateResource" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.HttpUtil" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsPropsUtil" %><%@
page import="com.liferay.portal.kernel.util.PropertiesParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PropsKeys" %><%@
page import="com.liferay.portal.kernel.util.ResourceBundleUtil" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.UnicodeProperties" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.layoutconfiguration.util.RuntimePageUtil" %><%@
page import="com.liferay.portal.model.ColorScheme" %><%@
page import="com.liferay.portal.model.Group" %><%@
page import="com.liferay.portal.model.GroupConstants" %><%@
page import="com.liferay.portal.model.Layout" %><%@
page import="com.liferay.portal.model.LayoutBranch" %><%@
page import="com.liferay.portal.model.LayoutConstants" %><%@
page import="com.liferay.portal.model.LayoutPrototype" %><%@
page import="com.liferay.portal.model.LayoutRevision" %><%@
page import="com.liferay.portal.model.LayoutSet" %><%@
page import="com.liferay.portal.model.LayoutSetBranch" %><%@
page import="com.liferay.portal.model.LayoutSetPrototype" %><%@
page import="com.liferay.portal.model.LayoutTemplate" %><%@
page import="com.liferay.portal.model.LayoutTemplateConstants" %><%@
page import="com.liferay.portal.model.LayoutType" %><%@
page import="com.liferay.portal.model.LayoutTypeController" %><%@
page import="com.liferay.portal.model.LayoutTypePortlet" %><%@
page import="com.liferay.portal.model.Portlet" %><%@
page import="com.liferay.portal.model.Theme" %><%@
page import="com.liferay.portal.model.ThemeSetting" %><%@
page import="com.liferay.portal.model.UserGroup" %><%@
page import="com.liferay.portal.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portal.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutLocalServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutPrototypeLocalServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutPrototypeServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutSetBranchLocalServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutSetLocalServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil" %><%@
page import="com.liferay.portal.service.LayoutTemplateLocalServiceUtil" %><%@
page import="com.liferay.portal.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portal.service.ThemeLocalServiceUtil" %><%@
page import="com.liferay.portal.service.UserGroupLocalServiceUtil" %><%@
page import="com.liferay.portal.service.permission.GroupPermissionUtil" %><%@
page import="com.liferay.portal.service.permission.LayoutPermissionUtil" %><%@
page import="com.liferay.portal.util.LayoutTypeControllerTracker" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portal.util.PortletKeys" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.portal.util.RobotsUtil" %><%@
page import="com.liferay.portal.util.WebKeys" %><%@
page import="com.liferay.portal.webserver.WebServerServletTokenUtil" %><%@
page import="com.liferay.portlet.PortletURLUtil" %><%@
page import="com.liferay.portlet.documentlibrary.FileSizeException" %><%@
page import="com.liferay.portlet.exportimport.lar.PortletDataHandlerKeys" %><%@
page import="com.liferay.portlet.exportimport.staging.LayoutStagingUtil" %><%@
page import="com.liferay.portlet.exportimport.staging.StagingUtil" %><%@
page import="com.liferay.portlet.sites.util.SitesUtil" %>

<%@ page import="java.util.Collections" %><%@
page import="java.util.HashSet" %><%@
page import="java.util.List" %><%@
page import="java.util.Locale" %><%@
page import="java.util.Map" %><%@
page import="java.util.ResourceBundle" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<portlet:defineObjects />

<liferay-theme:defineObjects />

<%
WindowState windowState = liferayPortletRequest.getWindowState();

PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);

String currentURL = currentURLObj.toString();

LayoutsAdminDisplayContext layoutsAdminDisplayContext = new LayoutsAdminDisplayContext(request, liferayPortletResponse);
%>

<%@ include file="/init-ext.jsp" %>