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

<%@ page import="com.liferay.portal.service.permission.PortalPermissionUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.EntryDDMStructureIdException" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.EntryDuplicateEntryKeyException" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.EntryEntryKeyException" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.EntryNameException" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.NoSuchEntryException" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.model.DDLEntry" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.model.DDLEntryItem" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.search.EntryDisplayTerms" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.search.EntrySearch" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.search.EntrySearchTerms" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.service.DDLEntryItemLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.service.DDLEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatalists.service.permission.DDLEntryPermission" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.model.DDMStructure" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.NoSuchStructureException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.search.StructureSearch" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.search.StructureSearchTerms" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.storage.Fields" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.util.DDMFieldConstants" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.util.DDMXSDUtil" %>

<%
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>