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
String randomNamespace = DeterminateKeyGenerator.generate("portlet_document_library_folder_action") + StringPool.UNDERLINE;

String redirect = currentURL;

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DLFolder folder = null;

long folderId = 0;

if (row != null) {
	folder = (DLFolder)row.getObject();

	folderId = folder.getFolderId();
}
else {
	folder = (DLFolder)request.getAttribute("view.jsp-folder");

	folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));
}

String modelResource = null;
String modelResourceDescription = null;
String resourcePrimKey = null;

boolean showPermissionsURL = false;

if (folder != null) {
	modelResource = DLFolder.class.getName();
	modelResourceDescription = folder.getName();
	resourcePrimKey = String.valueOf(folderId);

	showPermissionsURL = DLFolderPermission.contains(permissionChecker, folder, ActionKeys.PERMISSIONS);
}
else {
	modelResource = "com.liferay.portlet.documentlibrary";
	modelResourceDescription = themeDisplay.getScopeGroupName();
	resourcePrimKey = String.valueOf(scopeGroupId);

	showPermissionsURL = GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
}

boolean view = false;

if (row == null) {
	view = true;
}
%>

<liferay-ui:icon-menu showExpanded="<%= view %>">
	<c:if test="<%= (folder != null) && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.UPDATE) %>">
		<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editURL">
			<portlet:param name="struts_action" value="/document_library/edit_folder" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon image="edit" url="<%= editURL %>" />
	</c:if>

	<c:if test="<%= showPermissionsURL %>">
		<liferay-security:permissionsURL
			modelResource="<%= modelResource %>"
			modelResourceDescription="<%= HtmlUtil.escape(modelResourceDescription) %>"
			resourcePrimKey="<%= resourcePrimKey %>"
			var="permissionsURL"
		/>

		<liferay-ui:icon image="permissions" url="<%= permissionsURL %>" />
	</c:if>

	<c:if test="<%= (folder != null) && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.DELETE) %>">
		<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="deleteURL">
			<portlet:param name="struts_action" value="/document_library/edit_folder" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteURL %>" />
	</c:if>

	<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER) %>">
		<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="addFolderURL">
			<portlet:param name="struts_action" value="/document_library/edit_folder" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon image="add_folder" message='<%= folder != null ? "add-subfolder" : "add-folder" %>' url="<%= addFolderURL %>" />
	</c:if>

	<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_DOCUMENT) %>">
		<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editFileEntryURL">
			<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon image="../document_library/add_document" message="add-document" url="<%= editFileEntryURL %>" />
	</c:if>

	<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_SHORTCUT) %>">
		<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editFileShortcutURL">
			<portlet:param name="struts_action" value="/document_library/edit_file_shortcut" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon image="add_instance" message="add-shortcut" url="<%= editFileShortcutURL %>" />
	</c:if>

	<c:if test="<%= portletDisplay.isWebDAVEnabled() && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.VIEW) %>">
		<liferay-ui:icon cssClass='<%= randomNamespace + "-webdav-action" %>' image="desktop" message="access-from-my-desktop" url="javascript:;" />
	</c:if>
</liferay-ui:icon-menu>

<div id="<%= randomNamespace %>webDav" style="display: none;">
	<div class="portlet-document-library">

		<%
		String webDavHelpMessage = null;

		if (BrowserSnifferUtil.isWindows(request)) {
			webDavHelpMessage = LanguageUtil.format(pageContext, "webdav-windows-help", new Object[] {"http://www.microsoft.com/downloads/details.aspx?FamilyId=17C36612-632E-4C04-9382-987622ED1D64", "http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/WebDAV"});
		}
		else {
			webDavHelpMessage = LanguageUtil.format(pageContext, "webdav-help", "http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/WebDAV");
		}
		%>

		<liferay-ui:message key="<%= webDavHelpMessage %>" />

		<br /><br />

		<div class="file-entry-field">
			<label><liferay-ui:message key="webdav-url" /></label>

			<%
			StringBuilder sb = new StringBuilder();

			if (folder != null) {
				DLFolder curFolder = folder;

				while (true) {
					sb.insert(0, HttpUtil.encodeURL(curFolder.getName(), true));
					sb.insert(0, StringPool.SLASH);

					if (curFolder.getParentFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
						break;
					}
					else {
						curFolder = DLFolderLocalServiceUtil.getFolder(curFolder.getParentFolderId());
					}
				}
			}

			Group group = themeDisplay.getScopeGroup();
			%>

			<liferay-ui:input-resource
				url='<%= themeDisplay.getPortalURL() + "/tunnel-web/secure/webdav/" + company.getWebId() + group.getFriendlyURL() + "/document_library" + sb.toString() %>'
			/>
		</div>
	</div>
</div>

<aui:script use="dialog">
	A.on(
		'click',
		function(event) {
			var popup = new A.Dialog(
				{
					bodyContent: A.get('#<%= randomNamespace %>webDav').html(),
					centered: true,
					destroyOnClose: true,
					modal: true,
					title: '<liferay-ui:message key="access-from-my-desktop" />',
					width: 500
				}
			)
			.render();

			event.preventDefault();
		},
		'.<%= randomNamespace %>-webdav-action'
	);
</aui:script>