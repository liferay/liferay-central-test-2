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

<%@ include file="/html/portlet/document_library_display/init.jsp" %>

<%
String strutsAction = ParamUtil.getString(request, "struts_action");

String tabs2 = ParamUtil.getString(request, "tabs2", "version-history");

String redirect = ParamUtil.getString(request, "redirect");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

List<FileEntry> fileEntries  = (List<FileEntry>)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRIES);

long[] fileEntryIds = null;

long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

if (fileEntries != null) {
	folderId = fileEntries.get(0).getFolderId();
}

List<FileEntry> moveFileEntries  = new ArrayList<FileEntry>();
List<FileEntry> errorMoveFileEntries  = new ArrayList<FileEntry>();

for (FileEntry fileEntry : fileEntries) {
	boolean movePermission = DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE) && (!fileEntry.isLocked() || fileEntry.hasLock());

	if (movePermission) {
		moveFileEntries.add(fileEntry);
	}
	else {
		errorMoveFileEntries.add(fileEntry);
	}
}
%>

<c:if test="<%= Validator.isNull(referringPortletResource) %>">
	<liferay-util:include page="/html/portlet/document_library_display/top_links.jsp" />
</c:if>

<portlet:actionURL var="moveFileEntryURL">
	<portlet:param name="struts_action" value="/document_library_display/move_file_entry" />
</portlet:actionURL>

<aui:form action="<%= moveFileEntryURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveFileEntry(false);" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.MOVE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="newFolderId" type="hidden" value="<%= folderId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title='<%= LanguageUtil.get(pageContext, "move-files") %>'
	/>

	<liferay-ui:error exception="<%= DuplicateFileException.class %>" message="the-folder-you-selected-already-has-an-entry-with-this-name.-please-select-a-different-folder" />
	<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="the-folder-you-selected-already-has-an-entry-with-this-name.-please-select-a-different-folder" />
	<liferay-ui:error exception="<%= NoSuchFolderException.class %>" message="please-enter-a-valid-folder" />

	<c:if test="<%= moveFileEntries.size() > 0 %>">
		<div class="move-list-info">
			<h4><%= LanguageUtil.format(pageContext, "x-files-ready-to-be-moved", moveFileEntries.size()) %></h4>
		</div>

		<div class="move-list">
			<ul class="lfr-component">

				<%
				fileEntryIds = new long[moveFileEntries.size()];

				for (int i = 0; i < moveFileEntries.size(); i++) {
					FileEntry fileEntry = moveFileEntries.get(i);

					fileEntryIds[i] = fileEntry.getFileEntryId();
				%>

					<li class="move-file">
						<span class=file-title>
							<%= fileEntry.getTitle() %>
						</span>
					</li>

				<%
				}
				%>

			</ul>
		</div>
	</c:if>

	<c:if test="<%= errorMoveFileEntries.size() > 0 %>">
		<div class="move-list-info">
			<h4><%= LanguageUtil.format(pageContext, "x-files-cannot-be-moved", errorMoveFileEntries.size()) %></h4>
		</div>

		<div class="move-list">
			<ul class="lfr-component">

				<%
				for (FileEntry fileEntry : errorMoveFileEntries) {
					Lock lock = fileEntry.getLock();
				%>
					<li class="move-file move-error">
						<span class=file-title>
							<%= fileEntry.getTitle() %>
						</span>

						<span class="error-message">
							<c:choose>
								<c:when test="<%= fileEntry.isLocked() && !fileEntry.hasLock() %>">
									<%= LanguageUtil.format(pageContext, "you-cannot-modify-this-document-because-it-was-locked-by-x-on-x", new Object[] {HtmlUtil.escape(PortalUtil.getUserName(lock.getUserId(), String.valueOf(lock.getUserId()))), dateFormatDateTime.format(lock.getCreateDate())}, false) %>
								</c:when>
								<c:otherwise>
									<%= LanguageUtil.get(pageContext, "you-do-not-have-the-required-permissions") %>
								</c:otherwise>
							</c:choose>
						</span>
					</li>

				<%
				}
				%>

			</ul>
		</div>
	</c:if>

	<aui:input name="fileEntryIds" type="hidden" value="<%= StringUtil.merge(fileEntryIds, StringPool.COMMA) %>" />

	<aui:fieldset>

		<%
		String folderName = StringPool.BLANK;

		if (folderId > 0) {
			Folder folder = DLAppLocalServiceUtil.getFolder(folderId);

			folder = folder.toEscapedModel();

			folderId = folder.getFolderId();
			folderName = folder.getName();
		}
		else {
			folderName = LanguageUtil.get(pageContext, "documents-home");
		}

		%>

		<portlet:renderURL var="viewFolderURL">
			<portlet:param name="struts_action" value="/document_library_display/view" />
			<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		</portlet:renderURL>

		<aui:field-wrapper label="current-folder">
			<liferay-ui:icon
				image="folder"
				label="true"
				message="<%= folderName %>"
				url="<%= viewFolderURL %>"
			/>
		</aui:field-wrapper>

		<aui:field-wrapper label="new-folder">
			<aui:a href="<%= viewFolderURL %>" id="folderName"><%= folderName %></aui:a>

			<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="selectFolderURL">
				<portlet:param name="struts_action" value="/document_library_display/select_folder" />
				<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
			</portlet:renderURL>

			<%
			String taglibOpenFolderWindow = "var folderWindow = window.open('" + selectFolderURL + "','folder', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); folderWindow.focus();";
			%>

			<aui:button onClick='<%= taglibOpenFolderWindow %>' value="select" />
		</aui:field-wrapper>

		<aui:button-row>
			<aui:button type="submit" value="move" />

			<aui:button onClick="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<aui:script>
	function <portlet:namespace />saveFileEntry() {
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectFolder(folderId, folderName) {
		document.<portlet:namespace />fm.<portlet:namespace />newFolderId.value = folderId;

		var nameEl = document.getElementById("<portlet:namespace />folderName");

		nameEl.href = "javascript:location = '<portlet:renderURL><portlet:param name="struts_action" value="/document_library_display/view" /></portlet:renderURL>&<portlet:namespace />folderId=" + folderId + "'; void('');";
		nameEl.innerHTML = folderName + "&nbsp;";
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />file);
	</c:if>
</aui:script>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "move-files"), currentURL);
%>