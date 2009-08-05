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

DLFileShortcut fileShortcut = (DLFileShortcut)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUT);

fileShortcut = fileShortcut.toEscapedModel();

long fileShortcutId = fileShortcut.getFileShortcutId();
long toFolderId = fileShortcut.getToFolderId();
String toName = fileShortcut.getToName();

Group toGroup = null;
DLFolder toFolder = null;
DLFileEntry toFileEntry = null;

if ((toFolderId > 0) && Validator.isNotNull(toName)) {
	try {
		toFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(toFolderId, toName);
		toFolder = DLFolderLocalServiceUtil.getFolder(toFolderId);
		toGroup = GroupLocalServiceUtil.getGroup(toFolder.getGroupId());
	}
	catch (Exception e) {
	}
}
else if ((toFolderId > 0)) {
	try {
		toFolder = DLFolderLocalServiceUtil.getFolder(toFolderId);
		toGroup = GroupLocalServiceUtil.getGroup(toFolder.getGroupId());
	}
	catch (Exception e) {
	}
}

if (toGroup != null) {
	toGroup = toGroup.toEscapedModel();
}

toFileEntry = toFileEntry.toEscapedModel();

String extension = null;

if (Validator.isNotNull(toFileEntry.getName())) {
	extension = FileUtil.getExtension(toFileEntry.getName());
}

String[] conversions = new String[0];

if (PrefsPropsUtil.getBoolean(PropsKeys.OPENOFFICE_SERVER_ENABLED, PropsValues.OPENOFFICE_SERVER_ENABLED)) {
	conversions = (String[])DocumentConversionUtil.getConversions(extension);
}

Lock lock = null;
Boolean isLocked = Boolean.FALSE;
Boolean hasLock = Boolean.FALSE;

try {
	lock = LockServiceUtil.getLock(DLFileEntry.class.getName(), DLUtil.getLockId(toFileEntry.getFolderId(), toFileEntry.getName()));

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
portletURL.setParameter("fileShortcutId", String.valueOf(fileShortcutId));

request.setAttribute("view_file_shortcut.jsp-fileShortcut", fileShortcut);
%>

<div class="aui-column aui-w75 aui-column-first file-entry-column file-entry-column-first">
	<div class="aui-column-content file-entry-column-content">
		<h3><%= LanguageUtil.format(pageContext, "shortcut-to-x", toFileEntry.getTitle() + " (" + toFileEntry.getVersion() + ")") %></h3>

		<div class="file-entry-categories">
			<liferay-ui:asset-categories-summary
				className="<%= DLFileEntry.class.getName() %>"
				classPK="<%= toFileEntry.getFileEntryId() %>"
			/>
		</div>

		<div class="file-entry-tags">
			<liferay-ui:asset-tags-summary
				className="<%= DLFileEntry.class.getName() %>"
				classPK="<%= toFileEntry.getFileEntryId() %>"
				message="tags"
			/>
		</div>

		<div class="file-entry-description">
			<%= toFileEntry.getDescription() %>
		</div>

		<div class="custom-attributes">
			<liferay-ui:custom-attributes-available className="<%= DLFileEntry.class.getName() %>">
				<liferay-ui:custom-attribute-list
					className="<%= DLFileEntry.class.getName() %>"
					classPK="<%= (toFileEntry != null) ? toFileEntry.getFileEntryId() : 0 %>"
					editable="<%= false %>"
					label="<%= true %>"
				/>
			</liferay-ui:custom-attributes-available>
		</div>

		<div class="file-entry-author">
			<%= LanguageUtil.format(pageContext, "last-updated-by-x", PortalUtil.getUserName(toFileEntry.getUserId(), toFileEntry.getUserName())) %>
		</div>

		<div class="file-entry-date">
			<%= dateFormatDateTime.format(toFileEntry.getModifiedDate()) %>
		</div>

		<div class="file-entry-downloads">
			<%= toFileEntry.getReadCount() %> <liferay-ui:message key="downloads" />
		</div>

		<div class="file-entry-ratings">
			<liferay-ui:ratings
				className="<%= DLFileEntry.class.getName() %>"
				classPK="<%= toFileEntry.getFileEntryId() %>"
			/>
		</div>

		<div class="file-entry-field">
			<label><liferay-ui:message key="url" /></label>

			<liferay-ui:input-resource
				url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathMain() + "/document_library/get_file?p_l_id=" + themeDisplay.getPlid() + "&fileShortcutId=" + fileShortcutId %>'
			/>
		</div>

		<div class="file-entry-field">
			<label><liferay-ui:message key="community" /></label>

			<%= toGroup.getDescriptiveName() %>
		</div>
	</div>
</div>

<div class="aui-column aui-w25 aui-column-last file-entry-column file-entry-column-last">
	<div class="aui-column-content file-entry-column-content">
		<img alt="" class="shortcut-icon" src="<%= themeDisplay.getPathThemeImages() %>/document_library/overlay_link.png">

		<c:if test="<%= isLocked %>">
			<img alt="" class="locked-icon" src="<%= themeDisplay.getPathThemeImages() %>/document_library/overlay_lock.png">
		</c:if>

		<div class="file-entry-download">
			<liferay-ui:icon
				image='<%= "../document_library/" + DLUtil.getGenericName(extension) %>'
				cssClass="file-entry-avatar"
				message='download'
				url='<%= themeDisplay.getPathMain() + "/document_library/get_file?p_l_id=" + themeDisplay.getPlid() + "&fileShortcutId=" + fileShortcutId %>'
			/>

			<div class="file-entry-name">
				<a href="<%= themeDisplay.getPathMain() + "/document_library/get_file?p_l_id=" + themeDisplay.getPlid() + "&fileShortcutId=" + fileShortcutId %>">
					<%= toFileEntry.getTitleWithExtension() %>
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
							url='<%= themeDisplay.getPathMain() + "/document_library/get_file?p_l_id=" + themeDisplay.getPlid() + "&folderId=" + toFolder.getFolderId() + "&name=" + HttpUtil.encodeURL(toFileEntry.getName()) + "&targetExtension=" + conversion %>'
							label="<%= true %>"
						/>

					<%
					}
					%>

				</div>
			</c:if>
		</div>

		<liferay-util:include page="/html/portlet/document_library/file_entry_action.jsp" />
	</div>
</div>

<%
String tabs2Names = "version-history,comments";

if (!PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED || !DLFileShortcutPermission.contains(permissionChecker, fileShortcut, ActionKeys.ADD_DISCUSSION)) {
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
			SearchContainer searchContainer = new SearchContainer();

			List<String> headerNames = new ArrayList<String>();

			headerNames.add("version");
			headerNames.add("date");
			headerNames.add("size");
			headerNames.add(StringPool.BLANK);

			searchContainer.setHeaderNames(headerNames);

			List results = DLFileVersionLocalServiceUtil.getFileVersions(toFileEntry.getFolderId(), toFileEntry.getName());
			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				DLFileVersion fileVersion = (DLFileVersion)results.get(i);

				ResultRow row = new ResultRow(new Object[] {toFileEntry, fileVersion, portletURL, isLocked, hasLock}, fileVersion.getFileVersionId(), i);

				StringBuilder sb = new StringBuilder();

				sb.append(themeDisplay.getPathMain());
				sb.append("/document_library/get_file?p_l_id=");
				sb.append(themeDisplay.getPlid());
				sb.append("&fileShortcutId=");
				sb.append(fileShortcutId);
				sb.append("&version=");
				sb.append(String.valueOf(fileVersion.getVersion()));

				String rowHREF = sb.toString();

				// Statistics

				row.addText(String.valueOf(fileVersion.getVersion()), rowHREF);
				row.addText(dateFormatDateTime.format(fileVersion.getCreateDate()), rowHREF);
				row.addText(TextFormatter.formatKB(fileVersion.getSize(), locale) + "k", rowHREF);

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/document_library/file_version_action.jsp");

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		</c:when>
		<c:when test='<%= tabs2.equals("comments") %>'>
			<c:if test="<%= DLFileShortcutPermission.contains(permissionChecker, fileShortcut, ActionKeys.ADD_DISCUSSION) %>">
				<portlet:actionURL var="discussionURL">
					<portlet:param name="struts_action" value="/document_library/edit_file_entry_discussion" />
				</portlet:actionURL>

				<liferay-ui:discussion
					formName="fm2"
					formAction="<%= discussionURL %>"
					className="<%= DLFileEntry.class.getName() %>"
					classPK="<%= toFileEntry.getFileEntryId() %>"
					userId="<%= toFileEntry.getUserId() %>"
					subject="<%= toFileEntry.getTitle() %>"
					redirect="<%= currentURL %>"
					ratingsEnabled="<%= enableCommentRatings %>"
				/>
			</c:if>
		</c:when>
	</c:choose>
</div>

<%
DLUtil.addPortletBreadcrumbEntries(fileShortcut, request, renderResponse);
%>