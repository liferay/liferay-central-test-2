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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
long defaultFolderId = GetterUtil.getLong(preferences.getValue("rootFolderId", StringPool.BLANK), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

Folder folder = (Folder)request.getAttribute("view.jsp-folder");

boolean showDocumentTypes = true;

if ((folder != null) && !folder.isDefaultRepository()) {
	showDocumentTypes = false;
}
%>

<c:if test='<%= showDocumentTypes %>'>
	<ul>

		<%
		int start = ParamUtil.getInteger(request, "start");
		int end = ParamUtil.getInteger(request, "end", SearchContainer.DEFAULT_DELTA);

		List<DLDocumentType> documentTypes = DLDocumentTypeServiceUtil.getDocumentTypes(scopeGroupId, start, end);

		for (DLDocumentType documentType : documentTypes) {
		%>

			<portlet:renderURL var="editDocumentTypeURL">
				<portlet:param name="struts_action" value="/document_library/edit_document_type" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="documentTypeId" value="<%= String.valueOf(documentType.getDocumentTypeId()) %>" />
			</portlet:renderURL>

			<li class="document-type">
				<a href="<%= editDocumentTypeURL.toString() %>">
					<liferay-ui:icon image="copy" />

					<%= documentType.getName() %>
				</a>
			</li>

		<%
		}
		%>

	</ul>
</c:if>