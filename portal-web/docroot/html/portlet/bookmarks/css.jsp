<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
%>

<%@ include file="/html/portlet/css_init.jsp" %>

<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.util.StringUtil" %>

<liferay-util:buffer var="html">
	<liferay-util:include page="/html/portlet/document_library/css.jsp" />
</liferay-util:buffer>

<%
html = StringUtil.replace(html, "documentLibraryPanelContainer", "bookmarksPanelContainer");
html = StringUtil.replace(html, "file-entries", "entries");
html = StringUtil.replace(html, "file-entry", "entry");
html = StringUtil.replace(html, "portlet-document-library", "portlet-bookmarks");
%>

<%= html %>

.portlet-bookmarks .entry-url {
	margin: 1em 0 0.3em;
}