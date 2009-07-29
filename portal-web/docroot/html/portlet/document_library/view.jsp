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
String tabs1 = ParamUtil.getString(request, "tabs1", "folders");

DLFolder folder = (DLFolder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long defaultFolderId = GetterUtil.getLong(preferences.getValue("rootFolderId", StringPool.BLANK), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", defaultFolderId);

if ((folder == null) && (defaultFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = DLFolderLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/document_library/view");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("folderId", String.valueOf(folderId));
%>

<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" varImpl="searchURL"><portlet:param name="struts_action" value="/document_library/search" /></liferay-portlet:renderURL>

<form action="<%= searchURL %>" method="get" name="<portlet:namespace />fm1" onSubmit="submitForm(this); return false;">
<liferay-portlet:renderURLParams varImpl="searchURL" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(currentURL) %>" />
<input name="<portlet:namespace />breadcrumbsFolderId" type="hidden" value="<%= folderId %>" />
<input name="<portlet:namespace />searchFolderIds" type="hidden" value="<%= folderId %>" />

<liferay-util:include page="/html/portlet/document_library/tabs1.jsp" />

<c:choose>
	<c:when test='<%= tabs1.equals("folders") %>'>
		<c:if test="<%= showSubfolders %>">
			<liferay-ui:search-container
				curParam="cur1"
				delta="<%= foldersPerPage %>"
				headerNames="<%= StringUtil.merge(folderColumns) %>"
				iteratorURL="<%= portletURL %>"
			>
				<liferay-ui:search-container-results
					results="<%= DLFolderLocalServiceUtil.getFolders(scopeGroupId, folderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
					total="<%= DLFolderLocalServiceUtil.getFoldersCount(scopeGroupId, folderId) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portlet.documentlibrary.model.DLFolder"
					escapedModel="<%= true %>"
					keyProperty="folderId"
					modelVar="curFolder"
				>
					<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" varImpl="rowURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
					</liferay-portlet:renderURL>

					<%@ include file="/html/portlet/document_library/folder_columns.jspf" %>
				</liferay-ui:search-container-row>

				<%
				showAddFolderButton = showAddFolderButton && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER);
				boolean showCurFolderSearch = showFoldersSearch && (results.size() > 0);
				boolean showPermissionsButton = GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
				%>

				<c:if test="<%= showAddFolderButton || showCurFolderSearch || showPermissionsButton %>">
					<div>
						<c:if test="<%= showCurFolderSearch %>">
							<label for="<portlet:namespace />keywords1"><liferay-ui:message key="search" /></label>

							<input id="<portlet:namespace />keywords1" name="<portlet:namespace />keywords" size="30" type="text" />

							<input type="submit" value="<liferay-ui:message key="search-folders" />" />
						</c:if>

						<c:if test="<%= showAddFolderButton %>">
							<input type="button" value="<liferay-ui:message key='<%= (folder == null) ? "add-folder" : "add-subfolder" %>' />" onClick="<portlet:namespace />addFolder();" />
						</c:if>

						<c:if test="<%= showPermissionsButton %>">

							<%
							String modelResource = "com.liferay.portlet.documentlibrary";
							String modelResourceDescription = themeDisplay.getScopeGroupName();
							String resourcePrimKey = String.valueOf(scopeGroupId);

							if (folder != null) {
								modelResource = DLFolder.class.getName();
								modelResourceDescription = folder.getName();
								resourcePrimKey = String.valueOf(folder.getFolderId());
							}
							%>

							<liferay-security:permissionsURL
								modelResource="<%= modelResource %>"
								modelResourceDescription="<%= HtmlUtil.escape(modelResourceDescription) %>"
								resourcePrimKey="<%= resourcePrimKey %>"
								var="permissionsURL"
							/>

							<input type="button" value="<liferay-ui:message key="permissions" />" onClick="location.href = '<%= permissionsURL %>';" />
						</c:if>
					</div>

					<br />
				</c:if>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>
		</c:if>

		</form>

		<script type="text/javascript">
			function <portlet:namespace />addFolder() {
				var url = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/edit_folder" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" /></portlet:renderURL>';

				if (document.<portlet:namespace />fm1.<portlet:namespace />keywords) {
					url += '&<portlet:namespace />name=' + document.<portlet:namespace />fm1.<portlet:namespace />keywords.value;
				}

				submitForm(document.hrefFm, url);
			}

			<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />keywords);
			</c:if>
		</script>

		<c:if test="<%= folder != null %>">
			<c:if test="<%= showSubfolders %>">
				<br />
			</c:if>

			<form action="<%= searchURL %>" method="get" name="<portlet:namespace />fm2" onSubmit="submitForm(this); return false;">
			<liferay-portlet:renderURLParams varImpl="searchURL" />
			<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(currentURL) %>" />
			<input name="<portlet:namespace />breadcrumbsFolderId" type="hidden" value="<%= folderId %>" />
			<input name="<portlet:namespace />searchFolderId" type="hidden" value="<%= folderId %>" />

			<c:if test="<%= showTabs && showSubfolders %>">
				<liferay-ui:tabs names="documents" />
			</c:if>

			<liferay-ui:search-container
				curParam="cur2"
				delta="<%= fileEntriesPerPage %>"
				headerNames="<%= StringUtil.merge(fileEntryColumns) %>"
				iteratorURL="<%= portletURL %>"
			>
				<liferay-ui:search-container-results>

					<%
					if (mergedView) {
						results = DLFolderLocalServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(folder.getFolderId(), searchContainer.getStart(), searchContainer.getEnd());
					}
					else {
						results = DLFolderLocalServiceUtil.getFileEntriesAndFileShortcuts(folder.getFolderId(), searchContainer.getStart(), searchContainer.getEnd());
					}

					if (mergedView) {
						total = DLFolderLocalServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(folder.getFolderId());
					}
					else {
						total = DLFolderLocalServiceUtil.getFileEntriesAndFileShortcutsCount(folder.getFolderId());
					}

					pageContext.setAttribute("results", results);
					pageContext.setAttribute("total", total);
					%>

				</liferay-ui:search-container-results>

				<liferay-ui:search-container-row
					className="com.liferay.portal.model.BaseModel"
					keyProperty="primaryKeyObj"
					modelVar="result"
				>
					<%@ include file="/html/portlet/document_library/cast_result.jspf" %>

					<%
					if (curFolder != null) {
					%>

						<liferay-portlet:renderURL varImpl="rowURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
						</liferay-portlet:renderURL>

						<%@ include file="/html/portlet/document_library/folder_columns.jspf" %>

					<%
					}
					else {
						String rowHREF = null;

						if (fileShortcut == null) {
							rowHREF = themeDisplay.getPathMain() + "/document_library/get_file?p_l_id=" + themeDisplay.getPlid() + "&folderId=" + fileEntry.getFolderId() + "&name=" + HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getName()));
						}
						else {
							rowHREF = themeDisplay.getPathMain() + "/document_library/get_file?p_l_id=" + themeDisplay.getPlid() + "&fileShortcutId=" + fileShortcut.getFileShortcutId();
						}
					%>

						<%@ include file="/html/portlet/document_library/file_entry_columns.jspf" %>

					<%
					}
					%>

				</liferay-ui:search-container-row>

				<%
				showAddFileEntryButton = showAddFileEntryButton && DLFolderPermission.contains(permissionChecker, folder, ActionKeys.ADD_DOCUMENT);
				showAddFileShortcutButton = showAddFileShortcutButton && DLFolderPermission.contains(permissionChecker, folder, ActionKeys.ADD_SHORTCUT);
				showAddFolderButton = mergedView && showAddFolderButton && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER);
				boolean showCurDocumentSearch = showFileEntriesSearch && (results.size() > 0);
				%>

				<c:if test="<%= showAddFileEntryButton || showAddFileShortcutButton || showCurDocumentSearch %>">
					<div>
						<c:if test="<%= showCurDocumentSearch %>">
							<label for="<portlet:namespace />keywords2"><liferay-ui:message key="search" /></label>

							<input id="<portlet:namespace />keywords2" name="<portlet:namespace />keywords" size="30" type="text" />

							<input type="submit" value="<liferay-ui:message key="search-this-folder" />" />
						</c:if>

						<c:if test="<%= showAddFolderButton %>">
							<input type="button" value="<liferay-ui:message key="add-folder" />" onClick="<portlet:namespace />addFolder();" />
						</c:if>

						<c:if test="<%= showAddFileEntryButton || showAddFileShortcutButton %>">
							<c:if test="<%= showAddFileEntryButton %>">
								<input type="button" value="<liferay-ui:message key="add-document" />" onClick="location.href = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/edit_file_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></portlet:renderURL>';" />
							</c:if>

							<c:if test="<%= showAddFileShortcutButton %>">
								<input type="button" value="<liferay-ui:message key="add-shortcut" />" onClick="location.href = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/edit_file_shortcut" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></portlet:renderURL>';" />
							</c:if>
						</c:if>
					</div>

					<br />
				</c:if>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>

			</form>

			<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
				<script type="text/javascript">
					Liferay.Util.focusFormField(document.<portlet:namespace />fm2.<portlet:namespace />keywords);
					Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />keywords);
				</script>
			</c:if>
		</c:if>

		<br />

		<%
		StringBuffer sb = new StringBuffer();

		if (folder != null) {
			DLFolder curFolder = folder;

			while (true) {
				sb.insert(0, WebDAVUtil.encodeURL(curFolder.getName()));
				sb.insert(0, StringPool.SLASH);

				if (curFolder.getParentFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
					break;
				}
				else {
					curFolder = DLFolderLocalServiceUtil.getFolder(curFolder.getParentFolderId());
				}
			}

			if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY) && (folder.getParentFolderId() != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
				PortalUtil.setPageSubtitle(folder.getName(), request);
				PortalUtil.setPageDescription(folder.getDescription(), request);
			}
		}
		%>

		<liferay-ui:webdav path='<%= "/document_library" + sb.toString() %>' />

		<%
		if ((folder!= null) && ((folder.getParentFolderId() != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) || windowState.equals(WindowState.MAXIMIZED))) {
			DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);
		}
		%>

	</c:when>
	<c:when test='<%= tabs1.equals("my-documents") || tabs1.equals("recent-documents") %>'>

		<%
		long groupFileEntriesUserId = 0;

		if (tabs1.equals("my-documents") && themeDisplay.isSignedIn()) {
			groupFileEntriesUserId = user.getUserId();
		}
		%>

		<liferay-ui:search-container
			delta="<%= fileEntriesPerPage %>"
			iteratorURL="<%= portletURL %>"
		>
			<liferay-ui:search-container-results
				results="<%= DLFileEntryLocalServiceUtil.getGroupFileEntries(scopeGroupId, groupFileEntriesUserId, searchContainer.getStart(), searchContainer.getEnd()) %>"
				total="<%= DLFileEntryLocalServiceUtil.getGroupFileEntriesCount(scopeGroupId, groupFileEntriesUserId) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portlet.documentlibrary.model.DLFileEntry"
				escapedModel="<%= true %>"
				keyProperty="fileEntryId"
				modelVar="fileEntry"
			>

				<%
				String rowHREF = themeDisplay.getPathMain() + "/document_library/get_file?p_l_id=" + themeDisplay.getPlid() + "&folderId=" + fileEntry.getFolderId() + "&name=" + HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getName()));
				%>

				<%@ include file="/html/portlet/document_library/file_entry_columns.jspf" %>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>

		</form>

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, tabs1), currentURL);

		PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, tabs1), request);
		%>

	</c:when>
</c:choose>