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

<%@ page import="com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.model.DDLRecordSet" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.model.DDMStructure" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.model.DDMTemplate" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.NoSuchStructureException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.search.StructureDisplayTerms" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.search.StructureSearch" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.search.StructureSearchTerms" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.search.TemplateDisplayTerms" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.search.TemplateSearch" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.search.TemplateSearchTerms" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.DDMStorageLinkLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.DDMStructureLinkLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.permission.DDMPermission" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.permission.DDMStructurePermission" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.permission.DDMTemplatePermission" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.storage.StorageType" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.StructureDuplicateElementException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.StructureDuplicateStructureKeyException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.StructureNameException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.StructureStructureKeyException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.StructureXsdException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.TemplateNameException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.TemplateScriptException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.util.DDMXSDUtil" %>

<%
PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(request);

boolean showManageTemplates = ParamUtil.getBoolean(request, "showManageTemplates", true);
boolean showToolbar = ParamUtil.getBoolean(request, "showToolbar", true);

String scopeStorageType = ParamUtil.getString(request, "scopeStorageType");
String scopeStructureName = ParamUtil.getString(request, "scopeStructureName");
String scopeStructureType = ParamUtil.getString(request, "scopeStructureType");

String chooseCallback = ParamUtil.getString(request, "chooseCallback");
String saveCallback = ParamUtil.getString(request, "saveCallback");

long classNameId = PortalUtil.getClassNameId(scopeStructureType);

String storageTypeValue = StringPool.BLANK;

if (scopeStorageType.equals("expando")) {
	storageTypeValue = StorageType.EXPANDO.getValue();
}
else if (scopeStorageType.equals("xml")) {
	storageTypeValue = StorageType.XML.getValue();
}

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>