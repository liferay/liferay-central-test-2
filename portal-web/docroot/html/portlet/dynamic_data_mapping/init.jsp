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
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.model.DDMList" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.model.DDMStructure" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.NoSuchStructureException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.search.DDMStructureDisplayTerms" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.search.DDMStructureSearch" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.search.DDMStructureSearchTerms" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.DDMStorageLinkLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.DDMStructureLinkLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.permission.DDMStructurePermission" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.storage.StorageType" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.StructureDuplicateElementException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.StructureDuplicateStructureKeyException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.StructureNameException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.StructureStructureKeyException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.StructureXsdException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.util.DDMXSDUtil" %>
<%@ page import="javax.portlet.PortletURL" %>