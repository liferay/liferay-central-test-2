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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="com.liferay.asset.categories.admin.web.util.AssetCategoryUtil" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.RowChecker" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PredicateFilter" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.security.auth.PrincipalException" %><%@
page import="com.liferay.portal.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portal.service.permission.GroupPermissionUtil" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portlet.PortletURLUtil" %><%@
page import="com.liferay.portlet.asset.AssetCategoryNameException" %><%@
page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil" %><%@
page import="com.liferay.portlet.asset.DuplicateCategoryException" %><%@
page import="com.liferay.portlet.asset.DuplicateVocabularyException" %><%@
page import="com.liferay.portlet.asset.NoSuchCategoryException" %><%@
page import="com.liferay.portlet.asset.NoSuchVocabularyException" %><%@
page import="com.liferay.portlet.asset.VocabularyNameException" %><%@
page import="com.liferay.portlet.asset.model.AssetCategory" %><%@
page import="com.liferay.portlet.asset.model.AssetCategoryConstants" %><%@
page import="com.liferay.portlet.asset.model.AssetCategoryDisplay" %><%@
page import="com.liferay.portlet.asset.model.AssetCategoryProperty" %><%@
page import="com.liferay.portlet.asset.model.AssetRendererFactory" %><%@
page import="com.liferay.portlet.asset.model.AssetVocabulary" %><%@
page import="com.liferay.portlet.asset.model.AssetVocabularyDisplay" %><%@
page import="com.liferay.portlet.asset.model.ClassType" %><%@
page import="com.liferay.portlet.asset.model.ClassTypeReader" %><%@
page import="com.liferay.portlet.asset.model.impl.AssetCategoryPropertyImpl" %><%@
page import="com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetCategoryPropertyServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetCategoryServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetVocabularyServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.permission.AssetCategoryPermission" %><%@
page import="com.liferay.portlet.asset.service.permission.AssetPermission" %><%@
page import="com.liferay.portlet.asset.service.permission.AssetVocabularyPermission" %><%@
page import="com.liferay.portlet.asset.util.AssetVocabularySettingsHelper" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Collections" %><%@
page import="java.util.List" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);

String currentURL = currentURLObj.toString();
%>

<%@ include file="/init-ext.jsp" %>