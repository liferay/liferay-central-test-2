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
String strutsAction = ParamUtil.getString(request, "struts_action");

String tabs2 = ParamUtil.getString(request, "tabs2", "version-history");

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

String uploadProgressId = "dlFileEntryUploadProgress";

FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

long fileEntryId = BeanParamUtil.getLong(fileEntry, request, "fileEntryId");

long repositoryId = BeanParamUtil.getLong(fileEntry, request, "repositoryId");
long folderId = BeanParamUtil.getLong(fileEntry, request, "folderId");
String extension = BeanParamUtil.getString(fileEntry, request, "extension");

Folder folder = null;

if (fileEntry != null) {
	folder = fileEntry.getFolder();
}

FileVersion fileVersion = null;

if (fileEntry != null) {
	fileVersion = fileEntry.getLatestFileVersion();
}

long assetClassPK = 0;

if ((fileVersion != null) && !fileVersion.isApproved() && !fileVersion.getVersion().equals(DLFileEntryConstants.DEFAULT_VERSION)) {
	assetClassPK = fileVersion.getFileVersionId();
}
else if (fileEntry != null) {
	assetClassPK = fileEntry.getFileEntryId();
}

Boolean isLocked = Boolean.FALSE;
Boolean hasLock = Boolean.FALSE;
Lock lock = null;

if (fileEntry != null) {
	isLocked = fileEntry.isLocked();
	hasLock = fileEntry.hasLock();
	lock = fileEntry.getLock();
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", strutsAction);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("fileEntryId", String.valueOf(fileEntryId));
%>

<c:if test="<%= Validator.isNull(referringPortletResource) %>">
	<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />
</c:if>

<c:if test="<%= isLocked %>">
	<c:choose>
		<c:when test="<%= hasLock %>">
			<div class="portlet-msg-success">
				<c:choose>
					<c:when test="<%= lock.isNeverExpires() %>">
						<liferay-ui:message key="you-now-have-an-indefinite-lock-on-this-document" />
					</c:when>
					<c:otherwise>

						<%
						String lockExpirationTime = LanguageUtil.getTimeDescription(pageContext, DLFileEntryConstants.LOCK_EXPIRATION_TIME).toLowerCase();
						%>

						<%= LanguageUtil.format(pageContext, "you-now-have-a-lock-on-this-document", lockExpirationTime, false) %>
					</c:otherwise>
				</c:choose>
			</div>
		</c:when>
		<c:otherwise>
			<div class="portlet-msg-error">
				<%= LanguageUtil.format(pageContext, "you-cannot-modify-this-document-because-it-was-locked-by-x-on-x", new Object[] {HtmlUtil.escape(PortalUtil.getUserName(lock.getUserId(), String.valueOf(lock.getUserId()))), dateFormatDateTime.format(lock.getCreateDate())}, false) %>
			</div>
		</c:otherwise>
	</c:choose>
</c:if>

<liferay-ui:header
	backURL="<%= backURL %>"
	title='<%= (fileEntry != null) ? fileEntry.getTitle() : "new-document" %>'
/>

<c:if test="<%= fileEntry == null %>">
	<div class="lfr-dynamic-uploader">
		<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
	</div>

	<div class="lfr-fallback aui-helper-hidden" id="<portlet:namespace />fallback">

	<aui:script use="liferay-upload">
		new Liferay.Upload(
			{
				allowedFileTypes: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
				container: '#<portlet:namespace />fileUpload',
				fileDescription: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
				fallbackContainer: '#<portlet:namespace />fallback',
				maxFileSize: <%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) %> / 1024,
				namespace: '<portlet:namespace />',
				uploadFile: '<liferay-portlet:actionURL doAsUserId="<%= user.getUserId() %>"><portlet:param name="struts_action" value="/document_library/edit_file_entry" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" /><portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></liferay-portlet:actionURL><liferay-ui:input-permissions-params modelName="<%= DLFileEntryConstants.getClassName() %>" />'
			}
		);
	</aui:script>
</c:if>

<portlet:actionURL var="editFileEntryURL">
	<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
	<portlet:param name="uploader" value="classic" />
</portlet:actionURL>

<aui:form action="<%= editFileEntryURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveFileEntry(false);" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
	<aui:input name="uploadProgressId" type="hidden" value="<%= uploadProgressId %>" />
	<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
	<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
	<aui:input name="fileEntryId" type="hidden" value="<%= fileEntryId %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

	<liferay-ui:error exception="<%= DuplicateFileException.class %>" message="please-enter-a-unique-document-name" />
	<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="please-enter-a-unique-document-name" />

	<liferay-ui:error exception="<%= FileNameException.class %>">
		<liferay-ui:message key="document-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA), StringPool.COMMA_AND_SPACE) %>.
	</liferay-ui:error>

	<liferay-ui:error exception="<%= NoSuchFolderException.class %>" message="please-enter-a-valid-folder" />

	<liferay-ui:error exception="<%= SourceFileNameException.class %>">
		<liferay-ui:message key="the-source-file-does-not-have-the-same-extension-as-the-original-file" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= FileSizeException.class %>" message="please-enter-a-file-with-a-valid-file-size" />

	<liferay-ui:asset-tags-error />

	<aui:model-context bean="<%= fileVersion %>" model="<%= DLFileVersion.class %>" />

	<c:if test="<%= fileVersion != null %>">
		<aui:workflow-status status="<%= fileVersion.getStatus() %>" version="<%= fileVersion.getVersion() %>" />
	</c:if>

	<aui:fieldset>
		<aui:field-wrapper>

			<%
			long fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) / 1024;
			%>

			<c:if test="<%= fileMaxSize != 0 %>">
				<div class="portlet-msg-info">
					<%= LanguageUtil.format(pageContext, "upload-documents-no-larger-than-x-k", String.valueOf(fileMaxSize), false) %>
				</div>
			</c:if>
		</aui:field-wrapper>

		<%
		String folderName = StringPool.BLANK;

		if (folderId > 0) {
			folder = DLAppLocalServiceUtil.getFolder(folderId);

			folder = folder.toEscapedModel();

			folderId = folder.getFolderId();
			folderName = folder.getName();
		}
		else {
			folderName = LanguageUtil.get(pageContext, "documents-home");
		}

		%>

		<portlet:renderURL var="viewFolderURL">
			<portlet:param name="struts_action" value="/document_library/view" />
			<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		</portlet:renderURL>

		<c:choose>
			<c:when test="<%= (folderId <= 0) %>">
				<aui:field-wrapper label="folder">
					<aui:a href="<%= viewFolderURL %>" id="folderName"><%= folderName %></aui:a>

					<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="selectFolderURL">
						<portlet:param name="struts_action" value="/document_library/select_folder" />
						<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
					</portlet:renderURL>

					<%
					String taglibOpenFolderWindow = "var folderWindow = window.open('" + selectFolderURL + "','folder', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); folderWindow.focus();";
					%>

					<aui:button onClick='<%= taglibOpenFolderWindow %>' value="select" />

					<aui:button name="removeFolderButton" onClick='<%= renderResponse.getNamespace() + "removeFolder();" %>' value="remove" />
				</aui:field-wrapper>
			</c:when>
			<c:otherwise>
				<aui:field-wrapper label="folder">
					<aui:a href="<%= viewFolderURL %>"><%= folderName %></aui:a>
				</aui:field-wrapper>
			</c:otherwise>
		</c:choose>

		<aui:input name="file" type="file">
			<aui:validator name="acceptFiles">
				'<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>'
			</aui:validator>
		</aui:input>

		<aui:input name="title" />

		<c:if test="<%= folder != null %>">
			<c:if test="<%= folder.isSupportsMetadata() %>">
				<aui:input name="description" />

				<liferay-ui:custom-attributes-available className="<%= DLFileEntryConstants.getClassName() %>">
					<liferay-ui:custom-attribute-list
						className="<%= DLFileEntryConstants.getClassName() %>"
						classPK="<%= (fileVersion != null) ? fileVersion.getFileVersionId() : 0 %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</liferay-ui:custom-attributes-available>
			</c:if>

			<c:if test="<%= folder.isSupportsSocial() %>">
				<aui:input classPK="<%= assetClassPK %>" model="<%= DLFileEntry.class %>" name="categories" type="assetCategories" />

				<aui:input classPK="<%= assetClassPK %>" model="<%= DLFileEntry.class %>" name="tags" type="assetTags" />
			</c:if>
		</c:if>

		<c:if test="<%= fileEntry == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= DLFileEntryConstants.getClassName() %>"
				/>
			</aui:field-wrapper>
		</c:if>

		<%
		boolean approved = false;
		boolean draft = false;
		boolean pending = false;

		if (fileVersion != null) {
			approved = fileVersion.isApproved();
			draft = fileVersion.isDraft();
			pending = fileVersion.isPending();
		}
		%>

		<c:if test="<%= approved %>">
			<div class="portlet-msg-info">
				<liferay-ui:message key="a-new-version-will-be-created-automatically-if-this-content-is-modified" />
			</div>
		</c:if>

		<c:if test="<%= pending %>">
			<div class="portlet-msg-info">
				<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
			</div>
		</c:if>

		<aui:button-row>

			<%
			String saveButtonLabel = "save";

			if ((fileVersion == null) || draft || approved) {
				saveButtonLabel = "save-as-draft";
			}

			String publishButtonLabel = "publish";

			if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, DLFileEntryConstants.getClassName())) {
				publishButtonLabel = "submit-for-publication";
			}

			if (pending && !PropsValues.DL_FILE_ENTRY_DRAFTS_ENABLED) {
				publishButtonLabel = "save";
			}
			%>

			<c:if test="<%= PropsValues.DL_FILE_ENTRY_DRAFTS_ENABLED %>">
				<aui:button disabled="<%= isLocked && !hasLock %>" onClick='<%= renderResponse.getNamespace() + "saveFileEntry(true);" %>' name="saveButton" value="<%= saveButtonLabel %>" />
			</c:if>

			<aui:button disabled="<%= isLocked && !hasLock || (pending && PropsValues.DL_FILE_ENTRY_DRAFTS_ENABLED) %>" name="publishButton" type="submit" value="<%= publishButtonLabel %>" />

			<c:if test="<%= (fileEntry != null) && ((isLocked && hasLock) || !isLocked) %>">
				<c:choose>
					<c:when test="<%= !hasLock %>">
						<aui:button onClick='<%= renderResponse.getNamespace() + "lock();" %>' value="lock" />
					</c:when>
					<c:otherwise>
						<aui:button onClick='<%= renderResponse.getNamespace() + "unlock();" %>' value="unlock" />
					</c:otherwise>
				</c:choose>
			</c:if>

			<aui:button onClick="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<liferay-ui:upload-progress
	id="<%= uploadProgressId %>"
	message="uploading"
	redirect="<%= redirect %>"
/>

<c:if test="<%= fileEntry == null %>">
	</div>
</c:if>

<aui:script>
	function <portlet:namespace />lock() {
		submitForm(document.hrefFm, "<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_file_entry" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.LOCK %>" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntryId) %>" /></portlet:actionURL>");
	}

	function <portlet:namespace />removeFolder() {
		document.<portlet:namespace />fm.<portlet:namespace />folderId.value = "<%= rootFolderId %>";

		var nameEl = document.getElementById("<portlet:namespace />folderName");

		nameEl.href = "";
		nameEl.innerHTML = "";
	}

	function <portlet:namespace />saveFileEntry(draft) {
		<%= HtmlUtil.escape(uploadProgressId) %>.startProgress();

		if (draft) {
			document.<portlet:namespace />fm.<portlet:namespace />workflowAction.value = "<%= WorkflowConstants.ACTION_SAVE_DRAFT %>";
		}

		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (fileEntry == null) ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectFolder(folderId, folderName) {
		document.<portlet:namespace />fm.<portlet:namespace />folderId.value = folderId;

		var nameEl = document.getElementById("<portlet:namespace />folderName");

		nameEl.href = "javascript:location = '<portlet:renderURL><portlet:param name="struts_action" value="/document_library/view" /></portlet:renderURL>&<portlet:namespace />folderId=" + folderId + "'; void('');";
		nameEl.innerHTML = folderName + "&nbsp;";
	}

	function <portlet:namespace />unlock() {
		submitForm(document.hrefFm, "<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_file_entry" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNLOCK %>" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntryId) %>" /></portlet:actionURL>");
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />file);
	</c:if>
</aui:script>

<%
if (fileEntry != null) {
	DLUtil.addPortletBreadcrumbEntries(fileEntry, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	DLUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-file-entry"), currentURL);
}
%>