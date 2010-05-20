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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<liferay-ui:tabs names="error" backURL="javascript:history.go(-1);" />

<liferay-ui:error key="<%= DuplicateLockException.class.getName() + DLFileEntry.class.getName() %>">

	<%
	Lock lock = (Lock)errorException;
	%>

	<%= LanguageUtil.format(pageContext, "you-cannot-modify-this-document-because-it-was-locked-by-x-on-x", new Object[] {HtmlUtil.escape(PortalUtil.getUserName(lock.getUserId(), String.valueOf(lock.getUserId()))), dateFormatDateTime.format(lock.getCreateDate())}, false) %>
</liferay-ui:error>

<liferay-ui:error key="<%= DuplicateLockException.class.getName() + DLFolder.class.getName() %>" message="you-cannot-delete-this-folder-because-it-contains-locked-files" />
<liferay-ui:error exception="<%= NoSuchDirectoryException.class %>" message="the-folder-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchFileException.class %>" message="the-document-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchFileEntryException.class %>" message="the-document-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchFolderException.class %>" message="the-folder-could-not-be-found" />
<liferay-ui:error exception="<%= PrincipalException.class %>" message="you-do-not-have-the-required-permissions" />