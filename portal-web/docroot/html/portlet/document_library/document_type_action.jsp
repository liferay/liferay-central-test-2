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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DLDocumentType documentType = (DLDocumentType)row.getObject();
%>

<liferay-ui:icon-menu showExpanded="<%= false %>" showWhenSingleIcon="<%= false %>">
	<c:if test="<%= DLDocumentTypePermission.contains(permissionChecker, documentType, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="struts_action" value="/document_library/edit_document_type" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="documentTypeId" value="<%= String.valueOf(documentType.getDocumentTypeId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= DLDocumentTypePermission.contains(permissionChecker, documentType, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= DLDocumentType.class.getName() %>"
			modelResourceDescription="<%= documentType.getName() %>"
			resourcePrimKey="<%= String.valueOf(documentType.getDocumentTypeId()) %>"
			var="permissionsURL"
		/>

		<liferay-ui:icon
			image="permissions"
			url="<%= permissionsURL %>"
		/>
	</c:if>

	<c:if test="<%= DLDocumentTypePermission.contains(permissionChecker, documentType, ActionKeys.DELETE) %>">
		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/document_library/edit_document_type" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="documentTypeId" value="<%= String.valueOf(documentType.getDocumentTypeId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteURL %>" />
	</c:if>
</liferay-ui:icon-menu>