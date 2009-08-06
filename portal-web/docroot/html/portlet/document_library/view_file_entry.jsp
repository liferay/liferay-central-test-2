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

fileEntry = fileEntry.toEscapedModel();

long fileEntryId = fileEntry.getFileEntryId();
long folderId = fileEntry.getFolderId();
String name = fileEntry.getName();

String extension = StringPool.BLANK;

if (Validator.isNotNull(name)) {
	extension = FileUtil.getExtension(name);
}

String titleWithExtension = fileEntry.getTitleWithExtension();

String[] conversions = new String[0];

if (PrefsPropsUtil.getBoolean(PropsKeys.OPENOFFICE_SERVER_ENABLED, PropsValues.OPENOFFICE_SERVER_ENABLED)) {
	conversions = (String[])DocumentConversionUtil.getConversions(extension);
}

DLFolder folder = fileEntry.getFolder();

Lock lock = null;
Boolean isLocked = Boolean.FALSE;
Boolean hasLock = Boolean.FALSE;

try {
	lock = LockServiceUtil.getLock(DLFileEntry.class.getName(), DLUtil.getLockId(fileEntry.getFolderId(), fileEntry.getName()));

	isLocked = Boolean.TRUE;

	if (lock.getUserId() == user.getUserId()) {
		hasLock = Boolean.TRUE;
	}
}
catch (Exception e) {
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", strutsAction);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("folderId", String.valueOf(folderId));
portletURL.setParameter("name", name);

request.setAttribute("view_file_entry.jsp-fileEntry", fileEntry);
%>

<script type="text/javascript">
	function <portlet:namespace />compare() {
		var rowIds = jQuery('input[name=<portlet:namespace />rowIds]:checked');
		var sourceVersion = jQuery('input[name="<portlet:namespace />sourceVersion"]');
		var targetVersion = jQuery('input[name="<portlet:namespace />targetVersion"]');

		if (rowIds.length == 1) {
			sourceVersion.val(rowIds[0].value);
		}
		else if (rowIds.length == 2) {
			sourceVersion.val(rowIds[1].value);
			targetVersion.val(rowIds[0].value);
		}

		submitForm(document.<portlet:namespace />fm1);
	}

	function <portlet:namespace />initRowsChecked() {
		var rowIds = jQuery('input[name=<portlet:namespace />rowIds]');

		var found = 0;

		for (i = 0; i < rowIds.length; i++) {
			if (rowIds[i].checked && (found < 2)) {
				found++;
			}
			else {
				rowIds[i].checked = false;
			}
		}
	}

	function <portlet:namespace />updateRowsChecked(element) {
		var rowsChecked = jQuery('input[name=<portlet:namespace />rowIds]:checked');

		if (rowsChecked.length > 2) {
			if (rowsChecked[2] == element) {
				rowsChecked[1].checked = false;
			}
			else {
				rowsChecked[2].checked = false;
			}
		}
	}

	jQuery(
		function() {
			<portlet:namespace />initRowsChecked();

			jQuery('input[name=<portlet:namespace />rowIds]').click(
				function() {
					<portlet:namespace />updateRowsChecked(this);
				}
			);
		}
	);
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

<aui:column columnWidth="75" cssClass="file-entry-column file-entry-column-first" first="<%= true %>">
	<h3><%= fileEntry.getTitle() + " (" + fileEntry.getVersion() + ")" %></h3>

	<div class="file-entry-categories">
		<liferay-ui:asset-categories-summary
			className="<%= DLFileEntry.class.getName() %>"
			classPK="<%= fileEntryId %>"
		/>
	</div>

	<div class="file-entry-tags">
		<liferay-ui:asset-tags-summary
			className="<%= DLFileEntry.class.getName() %>"
			classPK="<%= fileEntryId %>"
			message="tags"
		/>
	</div>

	<div class="file-entry-description">
		<%= fileEntry.getDescription() %>
	</div>

	<div class="custom-attributes">
		<liferay-ui:custom-attributes-available className="<%= DLFileEntry.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= DLFileEntry.class.getName() %>"
				classPK="<%= (fileEntry != null) ? fileEntry.getFileEntryId() : 0 %>"
				editable="<%= false %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>
	</div>

	<div class="file-entry-author">
		<%= LanguageUtil.format(pageContext, "last-updated-by-x", PortalUtil.getUserName(fileEntry.getUserId(), fileEntry.getUserName())) %>
	</div>

	<div class="file-entry-date">
		<%= dateFormatDateTime.format(fileEntry.getModifiedDate()) %>
	</div>

	<div class="file-entry-downloads">
		<%= fileEntry.getReadCount() %> <liferay-ui:message key="downloads" />
	</div>

	<div class="file-entry-ratings">
		<liferay-ui:ratings
			className="<%= DLFileEntry.class.getName() %>"
			classPK="<%= fileEntryId %>"
		/>
	</div>

	<div class="file-entry-field">
		<label><liferay-ui:message key="url" /></label>

		<liferay-ui:input-resource
			url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathMain() + "/document_library/get_file?uuid=" + fileEntry.getUuid() + "&groupId=" + folder.getGroupId() %>'
		/>
	</div>

	<div class="file-entry-field">
		<label><liferay-ui:message key="webdav-url" /></label>

		<%
		StringBuffer sbf = new StringBuffer();

		DLFolder curFolder = DLFolderLocalServiceUtil.getFolder(folderId);

		while (true) {
			sbf.insert(0, WebDAVUtil.encodeURL(curFolder.getName()));
			sbf.insert(0, StringPool.SLASH);

			if (curFolder.getParentFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				break;
			}
			else {
				curFolder = DLFolderLocalServiceUtil.getFolder(curFolder.getParentFolderId());
			}
		}

		sbf.append(StringPool.SLASH);
		sbf.append(WebDAVUtil.encodeURL(titleWithExtension));

		Group group = layout.getGroup();
		%>

		<liferay-ui:input-resource
			url='<%= themeDisplay.getPortalURL() + "/tunnel-web/secure/webdav/" + company.getWebId() + group.getFriendlyURL() + "/document_library" + sbf.toString() %>'
		/>
	</div>
</aui:column>

<aui:column columnWidth="25" cssClass="file-entry-column file-entry-column-last" last="<%= true %>">
	<c:if test="<%= isLocked %>">
		<img alt="" class="locked-icon" src="<%= themeDisplay.getPathThemeImages() %>/document_library/overlay_lock.png">
	</c:if>

	<div class="file-entry-download">
		<liferay-ui:icon
			image='<%= "../document_library/" + DLUtil.getGenericName(extension) %>'
			message='download'
			url='<%= themeDisplay.getPathMain() + "/document_library/get_file?p_l_id=" + themeDisplay.getPlid() + "&folderId=" + folderId + "&name=" + HttpUtil.encodeURL(name) %>'
			cssClass="file-entry-avatar"
		/>

		<div class="file-entry-name">
			<a href="<%= themeDisplay.getPathMain() + "/document_library/get_file?p_l_id=" + themeDisplay.getPlid() + "&folderId=" + folderId + "&name=" + HttpUtil.encodeURL(name) %>">
				<%= titleWithExtension %>
			</a>
		</div>

		<c:if test="<%= conversions.length > 0 %>">
			<div class="file-entry-field file-entry-conversions">
				<label><liferay-ui:message key="other-available-formats" /></label>

				<%
				for (int i = 0; i < conversions.length; i++) {
					String conversion = conversions[i];
				%>

					<liferay-ui:icon
						image='<%= "../document_library/" + conversion %>'
						message="<%= conversion.toUpperCase() %>"
						url='<%= themeDisplay.getPathMain() + "/document_library/get_file?p_l_id=" + themeDisplay.getPlid() + "&folderId=" + folderId + "&name=" + HttpUtil.encodeURL(name) + "&targetExtension=" + conversion %>'
						label="<%= true %>"
					/>

				<%
				}
				%>

			</div>
		</c:if>
	</div>

	<liferay-util:include page="/html/portlet/document_library/file_entry_action.jsp" />
</aui:column>

<%
String tabs2Names = "version-history,comments";

if (!PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED || !DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.ADD_DISCUSSION)) {
	tabs2Names = "version-history";
}
%>

<div class="file-entry-tabs">
	<liferay-ui:tabs
		names="<%= tabs2Names %>"
		param="tabs2"
		url="<%= portletURL.toString() %>"
	/>

	<c:choose>
		<c:when test='<%= tabs2.equals("version-history") %>'>

			<%
			boolean comparableFileEntry = false;

			String[] comparableFileExtensions = PropsValues.DL_COMPARABLE_FILE_EXTENSIONS;

			for (int i = 0; i < comparableFileExtensions.length; i++) {
				if (StringPool.STAR.equals(comparableFileExtensions[i]) ||
					StringUtil.endsWith(name, comparableFileExtensions[i])) {

					comparableFileEntry = true;

					break;
				}
			}

			SearchContainer searchContainer = new SearchContainer();

			List<String> headerNames = new ArrayList<String>();

			headerNames.add("version");
			headerNames.add("date");
			headerNames.add("size");
			headerNames.add("download");

			if (conversions.length > 0) {
				headerNames.add("convert-to");
			}

			if (strutsAction.equals("/document_library/edit_file_entry")) {
				headerNames.add(StringPool.BLANK);
			}

			searchContainer.setHeaderNames(headerNames);

			if (comparableFileEntry) {
				searchContainer.setRowChecker(new RowChecker(renderResponse, RowChecker.ALIGN, RowChecker.VALIGN, RowChecker.FORM_NAME, null, RowChecker.ROW_IDS));
			}

			List results = DLFileVersionLocalServiceUtil.getFileVersions(folderId, name);
			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				DLFileVersion fileVersion = (DLFileVersion)results.get(i);

				ResultRow row = new ResultRow(new Object[] {fileEntry, fileVersion, conversions, portletURL, isLocked, hasLock}, String.valueOf(fileVersion.getVersion()), i);

				StringBuilder sb = new StringBuilder();

				sb.append(themeDisplay.getPathMain());
				sb.append("/document_library/get_file?p_l_id=");
				sb.append(themeDisplay.getPlid());
				sb.append("&folderId=");
				sb.append(folderId);
				sb.append("&name=");
				sb.append(HttpUtil.encodeURL(name));
				sb.append("&version=");
				sb.append(String.valueOf(fileVersion.getVersion()));

				String rowHREF = sb.toString();

				// Statistics

				row.addText(String.valueOf(fileVersion.getVersion()), rowHREF);
				row.addText(dateFormatDateTime.format(fileVersion.getCreateDate()), rowHREF);
				row.addText(TextFormatter.formatKB(fileVersion.getSize(), locale) + "k", rowHREF);

				// Download

				row.addJSP("/html/portlet/document_library/file_version_download.jsp");

				// Convert to

				if (conversions.length > 0) {
					row.addJSP("/html/portlet/document_library/file_version_convert_to.jsp");
				}

				// Action

				if (strutsAction.equals("/document_library/edit_file_entry")) {
					row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/document_library/file_version_action.jsp");
				}

				// Add result row

				resultRows.add(row);
			}

			if (comparableFileEntry && (results.size() > 0)) {
				DLFileVersion fileVersion = (DLFileVersion)results.get(0);
			%>

			<portlet:actionURL var="compareVersionsURL">
				<portlet:param name="struts_action" value="/document_library/compare_versions" />
			</portlet:actionURL>

			<aui:form action="<%= compareVersionsURL %>" method="post" name="fm1" onSubmit='<%= renderResponse.getNamespace() + "compare(); return false;" %>'>
				<aui:input name="backURL" type="hidden" value="<%= currentURL %>" />
				<aui:input name="fileEntryId" type="hidden" value="<%= fileEntryId %>" />
				<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
				<aui:input name="name" type="hidden" value="<%= name %>" />
				<aui:input name="titleWithExtension" type="hidden" value="<%= titleWithExtension %>" />
				<aui:input name="sourceVersion" type="hidden" value="<%= fileVersion.getVersion() %>" />
				<aui:input name="targetVersion" type="hidden" value="<%= fileEntry.getVersion() %>" />

				<aui:button type="submit" value="compare-versions" />
			</aui:form>

			<%
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />
		</c:when>
		<c:when test='<%= tabs2.equals("comments") %>'>
			<c:if test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.ADD_DISCUSSION) %>">
				<portlet:actionURL var="discussionURL">
					<portlet:param name="struts_action" value="/document_library/edit_file_entry_discussion" />
				</portlet:actionURL>

				<liferay-ui:discussion
					formName="fm2"
					formAction="<%= discussionURL %>"
					className="<%= DLFileEntry.class.getName() %>"
					classPK="<%= fileEntryId %>"
					userId="<%= fileEntry.getUserId() %>"
					subject="<%= fileEntry.getTitle() %>"
					redirect="<%= currentURL %>"
					ratingsEnabled="<%= enableCommentRatings %>"
				/>
			</c:if>
		</c:when>
	</c:choose>
</div>

<%
DLUtil.addPortletBreadcrumbEntries(fileEntry, request, renderResponse);
%>