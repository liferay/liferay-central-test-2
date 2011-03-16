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

<%@ page import="com.liferay.portlet.dynamicdatamapping.NoSuchStructureException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.StructureDuplicateElementException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.StructureDuplicateStructureKeyException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.StructureNameException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.StructureStructureKeyException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.StructureXsdException" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.model.DDMStructure" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.util.DDMXSDUtil" %>