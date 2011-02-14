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

<%@ page import="com.liferay.portlet.forms.NoSuchStructureEntryException" %>
<%@ page import="com.liferay.portlet.forms.StructureEntryDuplicateElementException" %>
<%@ page import="com.liferay.portlet.forms.StructureEntryDuplicateStructureIdException" %>
<%@ page import="com.liferay.portlet.forms.StructureEntryNameException" %>
<%@ page import="com.liferay.portlet.forms.StructureEntryStructureIdException" %>
<%@ page import="com.liferay.portlet.forms.StructureEntryXsdException" %>
<%@ page import="com.liferay.portlet.forms.model.FormsStructureEntry" %>
<%@ page import="com.liferay.portlet.forms.util.FormsXSDUtil" %>