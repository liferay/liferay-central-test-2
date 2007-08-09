<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

long defaultFolderId = GetterUtil.getLong(prefs.getValue("rootFolderId", StringPool.BLANK), DLFolderImpl.DEFAULT_PARENT_FOLDER_ID);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", defaultFolderId);

if ((folder == null) && (defaultFolderId != DLFolderImpl.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = DLFolderLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = DLFolderImpl.DEFAULT_PARENT_FOLDER_ID;
	}
}

List folderIds = new ArrayList();

folderIds.add(new Long(folderId));

DLFolderLocalServiceUtil.getSubfolderIds(folderIds, portletGroupId.longValue(), folderId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/document_library/view");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("folderId", String.valueOf(folderId));
%>

<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/search" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm1" onSubmit="submitForm(this); return false;">
<input name="<portlet:namespace />breadcrumbsFolderId" type="hidden" value="<%= folderId %>" />
<input name="<portlet:namespace />folderIds" type="hidden" value="<%= StringUtil.merge(folderIds) %>" />

<c:choose>
	<c:when test="<%= showTabs && (rootFolder == null) %>">
		<liferay-ui:tabs
			names="folders,my-documents,recent-documents"
			url="<%= portletURL.toString() %>"
		/>
	</c:when>
	<c:when test="<%= showTabs && showSubfolders %>">
		<liferay-ui:tabs names="folders" />
	</c:when>
</c:choose>

<c:choose>
	<c:when test='<%= tabs1.equals("folders") %>'>
		<c:if test="<%= showSubfolders %>">
			<c:if test="<%= showBreadcrumbs && (folder != null) %>">
				<div class="breadcrumbs">
					<%= DLUtil.getBreadcrumbs(folder, null, pageContext, renderRequest, renderResponse) %>
				</div>
			</c:if>

			<%
			List headerNames = new ArrayList();

			for (int i = 0; i < folderColumns.length; i++) {
				String folderColumn = folderColumns[i];

				if (folderColumn.equals("action")) {
					folderColumn = StringPool.BLANK;
				}

				headerNames.add(folderColumn);
			}

			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur1", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

			int total = DLFolderLocalServiceUtil.getFoldersCount(portletGroupId.longValue(), folderId);

			searchContainer.setTotal(total);

			List results = DLFolderLocalServiceUtil.getFolders(portletGroupId.longValue(), folderId, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);

			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				DLFolder curFolder = (DLFolder)results.get(i);

				ResultRow row = new ResultRow(curFolder, curFolder.getFolderId(), i);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setWindowState(WindowState.MAXIMIZED);

				rowURL.setParameter("struts_action", "/document_library/view");
				rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

				List subfolderIds = new ArrayList();

				subfolderIds.add(new Long(curFolder.getFolderId()));

				DLFolderLocalServiceUtil.getSubfolderIds(subfolderIds, portletGroupId.longValue(), curFolder.getFolderId());

				int foldersCount = subfolderIds.size() - 1;
				int fileEntriesCount = DLFileEntryLocalServiceUtil.getFileEntriesAndShortcutsCount(subfolderIds);
			%>

				<%@ include file="/html/portlet/document_library/folder_columns.jspf" %>

			<%

				// Add result row

				resultRows.add(row);
			}

			boolean showAddFolderButton = showButtons && DLFolderPermission.contains(permissionChecker, plid.longValue(), folderId, ActionKeys.ADD_FOLDER);
			boolean showCurFolderSearch = showFoldersSearch && (results.size() > 0);
			%>

			<c:if test="<%= showAddFolderButton || showCurFolderSearch %>">
				<div>
					<c:if test="<%= showCurFolderSearch %>">
						<input name="<portlet:namespace />keywords" size="30" type="text" />

						<input type="submit" value="<liferay-ui:message key="search-folders" />" />
					</c:if>

					<c:if test="<%= showAddFolderButton %>">
						<input type="button" value="<liferay-ui:message key="add-folder" />" onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/edit_folder" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" /></portlet:renderURL>';" />
					</c:if>
				</div>

				<c:if test="<%= results.size() > 0 %>">
					<br />
				</c:if>
			</c:if>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

			<c:if test="<%= (folder != null) && (showAddFolderButton || showCurFolderSearch || (results.size() > 0)) %>">
				<br />
			</c:if>

			</form>

			<c:if test="<%= renderRequest.getWindowState().equals(WindowState.MAXIMIZED) %>">
				<script type="text/javascript">
					if (document.<portlet:namespace />fm1.<portlet:namespace />keywords) {
						document.<portlet:namespace />fm1.<portlet:namespace />keywords.focus();
					}
				</script>
			</c:if>
		</c:if>

		<c:if test="<%= folder != null %>">
			<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/search" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm2" onSubmit="submitForm(this); return false;">
			<input name="<portlet:namespace />breadcrumbsFolderId" type="hidden" value="<%= folderId %>" />
			<input name="<portlet:namespace />folderIds" type="hidden" value="<%= folderId %>" />

			<c:if test="<%= showTabs && showSubfolders %>">
				<liferay-ui:tabs names="documents" />
			</c:if>

			<%
			List headerNames = new ArrayList();

			for (int i = 0; i < fileEntryColumns.length; i++) {
				String fileEntryColumn = fileEntryColumns[i];

				if (fileEntryColumn.equals("action")) {
					fileEntryColumn = StringPool.BLANK;
				}

				headerNames.add(fileEntryColumn);
			}

			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur2", fileEntriesPerPage, portletURL, headerNames, null);

			int total = DLFileEntryLocalServiceUtil.getFileEntriesAndShortcutsCount(folder.getFolderId());

			searchContainer.setTotal(total);

			List results = DLFileEntryLocalServiceUtil.getFileEntriesAndShortcuts(folder.getFolderId(), searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);

			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				Object result = results.get(i);

				long primaryKey = 0;

				DLFileEntry fileEntry = null;
				DLFileShortcut fileShortcut = null;

				if (result instanceof DLFileEntry) {
					fileEntry = (DLFileEntry)result;

					primaryKey = fileEntry.getFileEntryId();
				}
				else {
					fileShortcut = (DLFileShortcut)result;
					fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(fileShortcut.getToFolderId(), fileShortcut.getToName());

					primaryKey = fileShortcut.getFileShortcutId();
				}

				ResultRow row = new ResultRow(result, primaryKey, i);

				String rowHREF = null;

				if (fileShortcut == null) {
					rowHREF = themeDisplay.getPathMain() + "/document_library/get_file?folderId=" + fileEntry.getFolderId() + "&name=" + Http.encodeURL(fileEntry.getName());
				}
				else {
					rowHREF = themeDisplay.getPathMain() + "/document_library/get_file?fileShortcutId=" + fileShortcut.getFileShortcutId();
				}
			%>

				<%@ include file="/html/portlet/document_library/file_entry_columns.jspf" %>

			<%

				// Add result row

				resultRows.add(row);
			}

			boolean showAddFileEntryButton = showButtons && DLFolderPermission.contains(permissionChecker, folder, ActionKeys.ADD_DOCUMENT);
			boolean showAddFileShortcutButton = showButtons && DLFolderPermission.contains(permissionChecker, folder, ActionKeys.ADD_SHORTCUT);
			boolean showCurDocumentSearch = showFileEntriesSearch && (results.size() > 0);
			%>

			<c:if test="<%= showAddFileEntryButton || showAddFileShortcutButton || showCurDocumentSearch %>">
				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<c:if test="<%= showAddFileEntryButton || showAddFileShortcutButton %>">
						<td>
							<c:if test="<%= showAddFileEntryButton %>">
								<input type="button" value="<liferay-ui:message key="add-document" />" onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/edit_file_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></portlet:renderURL>';" />
							</c:if>

							<c:if test="<%= showAddFileShortcutButton %>">
								<input type="button" value="<liferay-ui:message key="add-shortcut" />" onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/edit_file_shortcut" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></portlet:renderURL>';" />
							</c:if>
						</td>
						<td style="padding-left: 30px;"></td>
					</c:if>

					<c:if test="<%= showCurDocumentSearch %>">
						<td>
							<input name="<portlet:namespace />keywords" size="30" type="text" />

							<input type="submit" value="<liferay-ui:message key="search-folder" />" />
						</td>
					</c:if>
				</tr>
				</table>

				<c:if test="<%= results.size() > 0 %>">
					<br />
				</c:if>
			</c:if>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

			</form>

			<c:if test="<%= renderRequest.getWindowState().equals(WindowState.MAXIMIZED) %>">
				<script type="text/javascript">
					if (document.<portlet:namespace />fm1.<portlet:namespace />keywords) {
						document.<portlet:namespace />fm1.<portlet:namespace />keywords.focus();
					}
					else if (document.<portlet:namespace />fm2.<portlet:namespace />keywords) {
						document.<portlet:namespace />fm2.<portlet:namespace />keywords.focus();
					}
				</script>
			</c:if>
		</c:if>
	</c:when>
	<c:when test='<%= tabs1.equals("my-documents") || tabs1.equals("recent-documents") %>'>

		<%
		long groupFileEntriesUserId = 0;

		if (tabs1.equals("my-documents") && themeDisplay.isSignedIn()) {
			groupFileEntriesUserId = user.getUserId();
		}

		List headerNames = new ArrayList();

		for (int i = 0; i < fileEntryColumns.length; i++) {
			String fileEntryColumn = fileEntryColumns[i];

			if (fileEntryColumn.equals("action")) {
				fileEntryColumn = StringPool.BLANK;
			}

			headerNames.add(fileEntryColumn);
		}

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, fileEntriesPerPage, portletURL, headerNames, null);

		int total = DLFileEntryLocalServiceUtil.getGroupFileEntriesCount(portletGroupId.longValue(), groupFileEntriesUserId);

		searchContainer.setTotal(total);

		List results = DLFileEntryLocalServiceUtil.getGroupFileEntries(portletGroupId.longValue(), groupFileEntriesUserId, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			DLFileEntry fileEntry = (DLFileEntry)results.get(i);

			ResultRow row = new ResultRow(fileEntry, fileEntry.getFileEntryId(), i);

			String rowHREF = themeDisplay.getPathMain() + "/document_library/get_file?folderId=" + fileEntry.getFolderId() + "&name=" + Http.encodeURL(fileEntry.getName());
		%>

			<%@ include file="/html/portlet/document_library/file_entry_columns.jspf" %>

		<%

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</c:when>
</c:choose>