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
String redirect = ParamUtil.getString(request, "redirect");

Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId");

long repositoryId = BeanParamUtil.getLong(folder, request, "repositoryId");
long parentFolderId = BeanParamUtil.getLong(folder, request, "parentFolderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
%>

<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />

<portlet:actionURL var="editFolderURL">
	<portlet:param name="struts_action" value="/document_library/edit_folder" />
</portlet:actionURL>

<aui:form action="<%= editFolderURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (folder == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
	<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
	<aui:input name="parentFolderId" type="hidden" value="<%= parentFolderId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title='<%= (folder != null) ? folder.getName() : "new-folder" %>'
	/>

	<liferay-ui:error exception="<%= DuplicateFileException.class %>" message="please-enter-a-unique-folder-name" />
	<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="please-enter-a-unique-folder-name" />
	<liferay-ui:error exception="<%= FolderNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= folder %>" model="<%= DLFolder.class %>" />

	<aui:fieldset>
		<c:if test="<%= folder != null %>">
			<aui:field-wrapper label="parent-folder">

				<%
				String parentFolderName = LanguageUtil.get(pageContext, "documents-home");

				try {
					if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
						Folder parentFolder = DLAppLocalServiceUtil.getFolder(parentFolderId);

						parentFolderName = parentFolder.getName();
					}
				}
				catch (NoSuchFolderException nscce) {
				}
				%>

				<portlet:renderURL var="viewFolderURL">
					<portlet:param name="struts_action" value="/document_library/view" />
					<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
				</portlet:renderURL>

				<aui:a href="<%= viewFolderURL %>" id="parentFolderName"><%= parentFolderName %></aui:a>
			</aui:field-wrapper>
		</c:if>

		<aui:input name="name" />

		<aui:input name="description" />

		<liferay-ui:custom-attributes-available className="<%= DLFolderConstants.getClassName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= DLFolderConstants.getClassName() %>"
				classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

		<c:if test="<%= folder == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= DLFolderConstants.getClassName() %>"
				/>
			</aui:field-wrapper>
		</c:if>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button onClick="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<%
if (folder == null) {
	DLUtil.addPortletBreadcrumbEntries(parentFolderId, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-folder"), currentURL);
}
else {
	DLUtil.addPortletBreadcrumbEntries(folder.getFolderId(), request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
%>