<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
%>

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String strutsAction = ParamUtil.getString(request, "struts_action");

String tabs2 = ParamUtil.getString(request, "tabs2", "version-history");

String redirect = ParamUtil.getString(request, "redirect");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

String uploadProgressId = "dlFileEntryUploadProgress";

DLFileEntry fileEntry = (DLFileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

long fileEntryId = BeanParamUtil.getLong(fileEntry, request, "fileEntryId");

long folderId = BeanParamUtil.getLong(fileEntry, request, "folderId");
String name = BeanParamUtil.getString(fileEntry, request, "name");

String extension = StringPool.BLANK;

if (Validator.isNotNull(name)) {
	extension = FileUtil.getExtension(name);
}

String titleWithExtension = BeanParamUtil.getString(fileEntry, request, "titleWithExtension");

String assetTagNames = ParamUtil.getString(request, "assetTagNames");

String[] conversions = new String[0];

if (PrefsPropsUtil.getBoolean(PropsKeys.OPENOFFICE_SERVER_ENABLED, PropsValues.OPENOFFICE_SERVER_ENABLED)) {
	conversions = (String[])DocumentConversionUtil.getConversions(extension);
}

DLFolder folder = null;

Lock lock = null;
Boolean isLocked = Boolean.FALSE;
Boolean hasLock = Boolean.FALSE;

if (fileEntry != null) {
	folder = fileEntry.getFolder();

	try {
		lock = LockServiceUtil.getLock(DLFileEntry.class.getName(), DLUtil.getLockId(fileEntry.getFolderId(), fileEntry.getName()));

		isLocked = Boolean.TRUE;

		if (lock.getUserId() == user.getUserId()) {
			hasLock = Boolean.TRUE;
		}
	}
	catch (Exception e) {
	}
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", strutsAction);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("folderId", String.valueOf(folderId));
portletURL.setParameter("name", name);
%>

<script type="text/javascript">
	function <portlet:namespace />lock() {
		submitForm(document.hrefFm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/edit_file_entry" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.LOCK %>" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /><portlet:param name="name" value="<%= name %>" /></portlet:actionURL>");
	}

	function <portlet:namespace />saveFileEntry() {
		<%= HtmlUtil.escape(uploadProgressId) %>.startProgress();

		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= fileEntry == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectFolder(folderId, folderName) {
		if (document.<portlet:namespace />fm.<portlet:namespace />folderId.value <= 0) {
			document.<portlet:namespace />fm.<portlet:namespace />folderId.value = folderId;
		}

		document.<portlet:namespace />fm.<portlet:namespace />newFolderId.value = folderId;

		var nameEl = document.getElementById("<portlet:namespace />folderName");

		nameEl.href = "javascript:location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/view" /></portlet:renderURL>&<portlet:namespace />folderId=" + folderId + "'; void('');";
		nameEl.innerHTML = folderName + "&nbsp;";
	}

	function <portlet:namespace />unlock() {
		submitForm(document.hrefFm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/edit_file_entry" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNLOCK %>" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /><portlet:param name="name" value="<%= name %>" /></portlet:actionURL>");
	}
</script>

<c:if test="<%= isLocked.booleanValue() %>">
	<c:choose>
		<c:when test="<%= hasLock.booleanValue() %>">

			<%
			String lockExpirationTime = LanguageUtil.getTimeDescription(pageContext, DLFileEntryImpl.LOCK_EXPIRATION_TIME).toLowerCase();
			%>

			<span class="portlet-msg-success">
				<%= LanguageUtil.format(pageContext, "you-now-have-a-lock-on-this-document", lockExpirationTime, false) %>
			</span>
		</c:when>
		<c:otherwise>
			<span class="portlet-msg-error">
				<%= LanguageUtil.format(pageContext, "you-cannot-modify-this-document-because-it-was-locked-by-x-on-x", new Object[] {PortalUtil.getUserName(lock.getUserId(), String.valueOf(lock.getUserId())), dateFormatDateTime.format(lock.getDate())}, false) %>
			</span>
		</c:otherwise>
	</c:choose>
</c:if>

<c:if test="<%= fileEntry == null %>">
	<script type="text/javascript">
		jQuery(
			function() {
				new Liferay.Upload(
					{
						allowedFileTypes: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
						container: '#<portlet:namespace />fileUpload',
						fileDescription: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
						fallbackContainer: '#<portlet:namespace />fallback',
						maxFileSize: <%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) %> / 1024,
						namespace: '<portlet:namespace />',
						uploadFile: '<liferay-portlet:actionURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" doAsUserId="<%= user.getUserId() %>"><portlet:param name="struts_action" value="/document_library/edit_file_entry" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></liferay-portlet:actionURL><liferay-ui:input-permissions-params modelName="<%= DLFileEntry.class.getName() %>" />'
					}
				);
			}
		);
	</script>

	<div class="lfr-dynamic-uploader">
		<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
	</div>

	<div class="lfr-fallback" id="<portlet:namespace />fallback">
</c:if>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_file_entry" /></portlet:actionURL>" enctype="multipart/form-data" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveFileEntry(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />
<input name="<portlet:namespace />referringPortletResource" type="hidden" value="<%= HtmlUtil.escapeAttribute(referringPortletResource) %>" />
<input name="<portlet:namespace />uploadProgressId" type="hidden" value="<%= HtmlUtil.escapeAttribute(uploadProgressId) %>" />
<input name="<portlet:namespace />folderId" type="hidden" value="<%= folderId %>" />
<input name="<portlet:namespace />newFolderId" type="hidden" value="" />
<input name="<portlet:namespace />name" type="hidden" value="<%= HtmlUtil.escapeAttribute(name) %>" />

<liferay-ui:error exception="<%= DuplicateFileException.class %>" message="please-enter-a-unique-document-name" />
<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="please-enter-a-unique-document-name" />

<liferay-ui:error exception="<%= FileNameException.class %>">
	<liferay-ui:message key="document-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA), StringPool.COMMA_AND_SPACE) %>.
</liferay-ui:error>

<liferay-ui:error exception="<%= NoSuchFolderException.class %>" message="please-enter-a-valid-folder" />

<liferay-ui:error exception="<%= SourceFileNameException.class %>">
	<liferay-ui:message key="document-extensions-does-not-match" />
</liferay-ui:error>

<liferay-ui:error exception="<%= FileSizeException.class %>" message="please-enter-a-file-with-a-valid-file-size" />

<liferay-ui:asset-tags-error />

<%
String fileMaxSize = String.valueOf(PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) / 1024);
%>

<c:if test='<%= !fileMaxSize.equals("0") %>'>
	<%= LanguageUtil.format(pageContext, "upload-documents-no-larger-than-x-k", fileMaxSize, false) %>

	<br /><br />
</c:if>

<table class="lfr-table">

<c:if test="<%= (fileEntry != null) || (folderId <= 0) %>">
	<tr>
		<td class="lfr-label">
			<liferay-ui:message key="folder" />
		</td>
		<td>

			<%
			String folderName = StringPool.BLANK;

			if (folderId > 0) {
				folder = DLFolderLocalServiceUtil.getFolder(folderId);

				folder = folder.toEscapedModel();

				folderId = folder.getFolderId();
				folderName = folder.getName();
			}

			%>

			<a href="javascript:window.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/view" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></portlet:renderURL>'; void('');" id="<portlet:namespace />folderName">
			<%= folderName %></a>

			<input type="button" value="<liferay-ui:message key="select" />" onClick="var folderWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/document_library/select_folder" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></portlet:renderURL>', 'folder', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); folderWindow.focus();" />
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
</c:if>

<tr>
	<td class="lfr-label">
		<liferay-ui:message key="file" />
	</td>
	<td>
		<input class="lfr-input-text" id="<portlet:namespace />file" name="<portlet:namespace />file" type="file" />
	</td>
</tr>
<tr>
	<td class="lfr-label">
		<liferay-ui:message key="title" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= DLFileEntry.class %>" bean="<%= fileEntry %>" field="title" /><%= extension %>
	</td>
</tr>
<tr>
	<td class="lfr-label">
		<liferay-ui:message key="description" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= DLFileEntry.class %>" bean="<%= fileEntry %>" field="description" />
	</td>
</tr>

<liferay-ui:custom-attributes-available className="<%= DLFileEntry.class.getName() %>">
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<liferay-ui:custom-attribute-list
				className="<%= DLFileEntry.class.getName() %>"
				classPK="<%= (fileEntry != null) ? fileEntry.getFileEntryId() : 0 %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</td>
	</tr>
</liferay-ui:custom-attributes-available>

<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td class="lfr-label">
		<liferay-ui:message key="categories" />
	</td>
	<td>
		<liferay-ui:asset-categories-selector
			className="<%= DLFileEntry.class.getName() %>"
			classPK="<%= fileEntryId %>"
		/>
	</td>
</tr>
<tr>
	<td class="lfr-label">
		<liferay-ui:message key="tags" />
	</td>
	<td>
		<liferay-ui:asset-tags-selector
			className="<%= DLFileEntry.class.getName() %>"
			classPK="<%= fileEntryId %>"
		/>
	</td>
</tr>

<%
if (fileEntry == null) {
	request.setAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY, new DLFileEntryImpl());
}
%>

<%@ include file="/html/portlet/document_library/edit_file_entry_form_extra_fields.jsp" %>

<%
if (fileEntry == null) {
	request.removeAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);
}
%>

<c:if test="<%= fileEntry == null %>">
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td class="lfr-label">
			<liferay-ui:message key="permissions" />
		</td>
		<td>
			<liferay-ui:input-permissions
				modelName="<%= DLFileEntry.class.getName() %>"
			/>
		</td>
	</tr>
</c:if>

</table>

<br />

<c:if test="<%= !(isLocked.booleanValue() && !hasLock.booleanValue()) %>">
	<input type="submit" value="<liferay-ui:message key="save" />">
</c:if>

<c:if test="<%= (fileEntry != null) && ((isLocked.booleanValue() && hasLock.booleanValue()) || !isLocked.booleanValue()) %>">
	<c:choose>
		<c:when test="<%= !hasLock.booleanValue() %>">
			<input type="button" value="<liferay-ui:message key="lock" />" onClick="<portlet:namespace />lock();" />
		</c:when>
		<c:otherwise>
			<input type="button" value="<liferay-ui:message key="unlock" />" onClick="<portlet:namespace />unlock();" />
		</c:otherwise>
	</c:choose>
</c:if>

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location = '<%= HtmlUtil.escape(redirect) %>';" />

</form>

<c:if test="<%= fileEntry == null %>">
	<br />
</c:if>

<script type="text/javascript">
	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />file);
	</c:if>

	jQuery(
		function() {
			jQuery("#<portlet:namespace />file").change(
				function() {
					var value = jQuery(this).val();

					if ((value != null) && (value != "")) {
						var extension = value.substring(value.lastIndexOf(".")).toLowerCase();

						var validExtensions = new Array('<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA), "', '") %>');

						if ((jQuery.inArray("*", validExtensions) == -1) && (jQuery.inArray(extension, validExtensions) == -1)) {
							alert('<%= UnicodeLanguageUtil.get(pageContext, "document-names-must-end-with-one-of-the-following-extensions") %> <%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA), StringPool.COMMA_AND_SPACE) %>');

							jQuery(this).val("");
						}
					}
				}
			).change();
		}
	);
</script>

<liferay-ui:upload-progress
	id="<%= uploadProgressId %>"
	message="uploading"
	redirect="<%= redirect %>"
/>

<c:if test="<%= fileEntry == null %>">
	</div>
</c:if>

<%
if (fileEntry != null) {
	DLUtil.addPortletBreadcrumbEntries(fileEntry, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "update-file-entry"), currentURL);
}
else {
	DLUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-file-entry"), currentURL);
}
%>