<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DLFileEntry fileEntry = null;
DLFileShortcut fileShortcut = null;

boolean view = false;

if (row != null) {
	Object result = row.getObject();

	if (result instanceof DLFileEntry) {
		fileEntry = (DLFileEntry)result;
	}
	else {
		fileShortcut = (DLFileShortcut)result;
	}
}
else {
	if (request.getAttribute("view_file_entry.jsp-fileEntry") != null) {
		fileEntry = (DLFileEntry)request.getAttribute("view_file_entry.jsp-fileEntry");
	}
	else {
		fileShortcut = (DLFileShortcut)request.getAttribute("view_file_shortcut.jsp-fileShortcut");
	}

	view = true;
}

long folderId = 0;

if (fileEntry != null) {
	folderId = fileEntry.getFolderId();
}
else if (fileShortcut != null) {
	folderId = fileShortcut.getFolderId();
}

PortletURL viewFolderURL = renderResponse.createRenderURL();

viewFolderURL.setWindowState(WindowState.MAXIMIZED);

viewFolderURL.setParameter("struts_action", "/document_library/view");
viewFolderURL.setParameter("folderId", String.valueOf(folderId));
%>

<liferay-ui:icon-menu showExpanded="<%= view %>">
	<c:choose>
		<c:when test="<%= fileEntry != null %>">
			<c:if test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.VIEW) %>">
				<liferay-ui:icon
					image="download"
					url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/document/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + fileEntry.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(fileEntry.getTitle()) %>'
					message='<%= LanguageUtil.get(pageContext, "download") + " (" + TextFormatter.formatKB(fileEntry.getSize(), locale) + "k)" %>'
				/>
			</c:if>

			<%@ include file="/html/portlet/document_library/file_entry_action_open_document.jspf" %>

			<c:if test="<%= !view && DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE) %>">
				<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editURL">
					<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(fileEntry.getFolderId()) %>" />
					<portlet:param name="name" value="<%= HtmlUtil.unescape(fileEntry.getName()) %>" />
				</portlet:renderURL>

				<liferay-ui:icon image="edit" url="<%= editURL %>" />
			</c:if>

			<c:if test="<%= !view %>">
				<%@ include file="/html/portlet/document_library/file_entry_action_lock.jspf" %>
			</c:if>

			<c:if test="<%= !view && DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.PERMISSIONS) %>">
				<liferay-security:permissionsURL
					modelResource="<%= DLFileEntry.class.getName() %>"
					modelResourceDescription="<%= HtmlUtil.unescape(fileEntry.getTitle()) %>"
					resourcePrimKey="<%= String.valueOf(fileEntry.getFileEntryId()) %>"
					var="permissionsURL"
				/>

				<liferay-ui:icon image="permissions" url="<%= permissionsURL %>" />
			</c:if>

			<c:if test="<%= !view && DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.DELETE) %>">
				<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="deleteURL">
					<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
					<portlet:param name="redirect" value="<%= viewFolderURL.toString() %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(fileEntry.getFolderId()) %>" />
					<portlet:param name="name" value="<%= HtmlUtil.unescape(fileEntry.getName()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon-delete url="<%= deleteURL %>" />
			</c:if>
		</c:when>
		<c:otherwise>

			<%
			fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(fileShortcut.getGroupId(), fileShortcut.getToFolderId(), HtmlUtil.unescape(fileShortcut.getToName()));
			%>

			<c:if test="<%= DLFileShortcutPermission.contains(permissionChecker, fileShortcut, ActionKeys.VIEW) %>">
				<liferay-ui:icon
					image="download"
					url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/document/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + fileEntry.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(fileEntry.getTitle()) %>'
					message='<%= LanguageUtil.get(pageContext, "download") + " (" + TextFormatter.formatKB(fileEntry.getSize(), locale) + "k)" %>'
				/>
			</c:if>

			<%@ include file="/html/portlet/document_library/file_entry_action_open_document.jspf" %>

			<c:if test="<%= !view && DLFileShortcutPermission.contains(permissionChecker, fileShortcut, ActionKeys.VIEW) %>">
				<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="viewShortcutURL">
					<portlet:param name="struts_action" value="/document_library/view_file_shortcut" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="fileShortcutId" value="<%= String.valueOf(fileShortcut.getFileShortcutId()) %>" />
				</portlet:renderURL>

				<liferay-ui:icon image="view" url="<%= viewShortcutURL %>" />
			</c:if>

			<c:if test="<%= view && DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE) %>">
				<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="viewOriginalFileURL">
					<portlet:param name="struts_action" value="/document_library/view_file_entry" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(fileShortcut.getToFolderId()) %>" />
					<portlet:param name="name" value="<%= HtmlUtil.unescape(fileShortcut.getToName()) %>" />
				</portlet:renderURL>

				<liferay-ui:icon image="view" message="view-original-file" url="<%= viewOriginalFileURL %>" />
			</c:if>

			<c:if test="<%= !view %>">
				<%@ include file="/html/portlet/document_library/file_entry_action_lock.jspf" %>
			</c:if>

			<c:if test="<%= !view && DLFileShortcutPermission.contains(permissionChecker, fileShortcut, ActionKeys.UPDATE) %>">
				<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editShortcutURL">
					<portlet:param name="struts_action" value="/document_library/edit_file_shortcut" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="fileShortcutId" value="<%= String.valueOf(fileShortcut.getFileShortcutId()) %>" />
				</portlet:renderURL>

				<liferay-ui:icon image="edit" url="<%= editShortcutURL %>" />
			</c:if>

			<c:if test="<%= !view && DLFileShortcutPermission.contains(permissionChecker, fileShortcut, ActionKeys.PERMISSIONS) %>">
				<liferay-security:permissionsURL
					modelResource="<%= DLFileShortcut.class.getName() %>"
					modelResourceDescription="<%= fileEntry.getTitle() %>"
					resourcePrimKey="<%= String.valueOf(fileShortcut.getFileShortcutId()) %>"
					var="shortcutPermissionsURL"
				/>

				<liferay-ui:icon image="permissions" url="<%= shortcutPermissionsURL %>" />
			</c:if>

			<c:if test="<%= !view && DLFileShortcutPermission.contains(permissionChecker, fileShortcut, ActionKeys.DELETE) %>">
				<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="deleteShortcutURL">
					<portlet:param name="struts_action" value="/document_library/edit_file_shortcut" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="fileShortcutId" value="<%= String.valueOf(fileShortcut.getFileShortcutId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon-delete url="<%= deleteShortcutURL %>" />
			</c:if>
		</c:otherwise>
	</c:choose>
</liferay-ui:icon-menu>