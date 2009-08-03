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

long fileShortcutId = BeanParamUtil.getLong(fileShortcut, request, "fileShortcutId");
long folderId = BeanParamUtil.getLong(fileShortcut, request, "folderId");
long toGroupId = ParamUtil.getLong(request, "toGroupId");
long toFolderId = BeanParamUtil.getLong(fileShortcut, request, "toFolderId");
String toName = BeanParamUtil.getString(fileShortcut, request, "toName");

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

if ((toGroup == null) && (toGroupId > 0)) {
	try {
		toGroup = GroupLocalServiceUtil.getGroup(toGroupId);
	}
	catch (Exception e) {
	}
}

if (toGroup != null) {
	toGroup = toGroup.toEscapedModel();

	toGroupId = toGroup.getGroupId();
}

if (toFileEntry != null) {
	toFileEntry = toFileEntry.toEscapedModel();
}

Boolean isLocked = Boolean.TRUE;
Boolean hasLock = Boolean.FALSE;

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", strutsAction);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("fileShortcutId", String.valueOf(fileShortcutId));
%>

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="name" />
	</td>
	<td>
		<a href="<%= themeDisplay.getPathMain() %>/document_library/get_file?p_l_id=<%= themeDisplay.getPlid() %>&fileShortcutId=<%= fileShortcutId %>">
		<%= toFileEntry.getTitle() %>
		</a>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="version" />
	</td>
	<td>
		<%= toFileEntry.getVersion() %>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="size" />
	</td>
	<td>
		<%= TextFormatter.formatKB(toFileEntry.getSize(), locale) %>k
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="downloads" />
	</td>
	<td>
		<%= toFileEntry.getReadCount() %>
	</td>
</tr>
<tr>
	<td colspan="3">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="url" />
	</td>
	<td>
		<liferay-ui:input-resource
			url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathMain() + "/document_library/get_file?p_l_id=" + themeDisplay.getPlid() + "&fileShortcutId=" + fileShortcutId %>'
		/>
	</td>
</tr>
</table>

<br /><br />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="community" />
	</td>
	<td>

		<%
		String toGroupName = BeanPropertiesUtil.getString(toGroup, "name");
		%>

		<span id="<portlet:namespace />toGroupName">
		<%= toGroupName %>
		</span>
	</td>
</tr>
<tr>
	<td class="lfr-label">
		<liferay-ui:message key="document" />
	</td>
	<td>

		<%
		String toFileEntryTitle = BeanPropertiesUtil.getString(toFileEntry, "title");
		%>

		<span id="<portlet:namespace />toFileEntryTitle">
		<%= toFileEntryTitle %>
		</span>
	</td>
</tr>

<br />

<liferay-ui:ratings
	className="<%= DLFileEntry.class.getName() %>"
	classPK="<%= toFileEntry.getFileEntryId() %>"
/>

<br />

<%
String tabs2Names = "version-history,comments";

if (!PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED || !DLFileShortcutPermission.contains(permissionChecker, fileShortcut, ActionKeys.ADD_DISCUSSION)) {
	tabs2Names = "version-history";
}
%>

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

<%
DLUtil.addPortletBreadcrumbEntries(fileShortcut, request, renderResponse);
%>