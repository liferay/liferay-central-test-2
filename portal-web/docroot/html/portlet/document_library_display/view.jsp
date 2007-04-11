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

<%@ include file="/html/portlet/document_library_display/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "folders");

DLFolder folder = (DLFolder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

String defaultFolderId = prefs.getValue("rootFolderId", DLFolderImpl.DEFAULT_PARENT_FOLDER_ID);
String folderId = BeanParamUtil.getString(folder, request, "folderId", defaultFolderId);

if ((folder == null) && (!defaultFolderId.equals(DLFolderImpl.DEFAULT_PARENT_FOLDER_ID))) {
	folder = DLFolderLocalServiceUtil.getFolder(folderId);
}

if (folder != null) {
	renderResponse.setTitle(folder.getName());
}

List folderIds = new ArrayList();

folderIds.add(folderId);

DLFolderLocalServiceUtil.getSubfolderIds(folderIds, portletGroupId, folderId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/document_library_display/view");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("folderId", folderId);
%>

<form action='<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library_display/search" /></portlet:renderURL>' method="post" name="<portlet:namespace />fm1" onSubmit="submitForm(this); return false;"  method="post">
<input name="<portlet:namespace />breadcrumbsFolderId" type="hidden" value="<%= folderId %>">
<input name="<portlet:namespace />folderIds" type="hidden" value="<%= StringUtil.merge(folderIds) %>">

<c:if test="<%= showBreadcrumbs && (folder != null) %>">
	<%= StringUtil.replace(DLUtil.getBreadcrumbs(folder, null, pageContext, renderRequest, renderResponse, "/document_library_display/view", false)) %>

	<br><br>
</c:if>

<c:if test="<%= showSearch %>">
		<input name="<portlet:namespace />keywords" size="30" type="text">

		<input type="submit" value="<%= LanguageUtil.get(pageContext, "search") %>">
</c:if>

<%
List headerNames = new ArrayList();

headerNames.add("folder");
headerNames.add("num-of-folders");
headerNames.add("num-of-documents");

SearchContainer searchContainer = null;
List results = null;
List resultRows = null;
int total = 0;
%>

<c:if test="<%= showSubfolders %>">
<%
searchContainer = new SearchContainer(renderRequest, null, null, "cur1", numberOfDocumentsPerPage, portletURL, headerNames, null);

total = DLFolderLocalServiceUtil.getFoldersCount(portletGroupId.longValue(), folderId);

searchContainer.setTotal(total);

results = DLFolderLocalServiceUtil.getFolders(portletGroupId.longValue(), folderId, searchContainer.getStart(), searchContainer.getEnd());

searchContainer.setResults(results);

resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	DLFolder curFolder = (DLFolder)results.get(i);

	ResultRow row = new ResultRow(curFolder, curFolder.getPrimaryKey().toString(), i);

	PortletURL rowURL = renderResponse.createRenderURL();

	rowURL.setWindowState(WindowState.MAXIMIZED);

	rowURL.setParameter("struts_action", "/document_library_display/view");
	rowURL.setParameter("folderId", curFolder.getFolderId());

	// Name

	StringBuffer sb = new StringBuffer();

	sb.append("<a href=\"");
	sb.append(rowURL);
	sb.append("\"><b>");
	sb.append(curFolder.getName());
	sb.append("</b>");

	sb.append("</a>");

	row.addText(sb.toString());

	// Statistics

	List subfolderIds = new ArrayList();

	subfolderIds.add(curFolder.getFolderId());

	DLFolderLocalServiceUtil.getSubfolderIds(subfolderIds, portletGroupId, curFolder.getFolderId());

	int foldersCount = subfolderIds.size() - 1;
	int fileEntriesCount = DLFileEntryLocalServiceUtil.getFileEntriesAndShortcutsCount(subfolderIds);

	row.addText(Integer.toString(foldersCount), rowURL);
	row.addText(Integer.toString(fileEntriesCount), rowURL);

	// Add result row

	resultRows.add(row);
}
%>
<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

	<br>
</c:if>

</form>

<c:if test="<%= portletDisplay.isStateMax() %>">
	<script type="text/javascript">
		if (document.<portlet:namespace />fm1.<portlet:namespace />keywords) {
			document.<portlet:namespace />fm1.<portlet:namespace />keywords.focus();
		}
	</script>
</c:if>

<c:if test="<%= folder != null %>">
	<%
	headerNames.clear();

	headerNames.add("document");
	if (showColumnDownloads) {
		headerNames.add("downloads");
	}
	if (showColumnLocked) {
		headerNames.add("locked");
	}
	if (showColumnDate) {
		headerNames.add("date");
	}
	if (showColumnSize) {
		headerNames.add("size");
	}

	searchContainer = new SearchContainer(renderRequest, null, null, "cur2", numberOfDocumentsPerPage, portletURL, headerNames, null);

	total = DLFileEntryLocalServiceUtil.getFileEntriesAndShortcutsCount(folder.getFolderId());

	searchContainer.setTotal(total);

	results = DLFileEntryLocalServiceUtil.getFileEntriesAndShortcuts(folder.getFolderId(), searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);


	resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		Object result = results.get(i);

		String primaryKey = null;

		DLFileEntry fileEntry = null;
		DLFileShortcut fileShortcut = null;

		if (result instanceof DLFileEntry) {
			fileEntry = (DLFileEntry)result;

			primaryKey = fileEntry.getPrimaryKey().toString();
		}
		else {
			fileShortcut = (DLFileShortcut)result;
			fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(fileShortcut.getToFolderId(), fileShortcut.getToName());

			primaryKey = String.valueOf(fileShortcut.getPrimaryKey());
		}

		ResultRow row = new ResultRow(result, primaryKey, i);

		String rowHREF = null;

		if (fileShortcut == null) {
			rowHREF = themeDisplay.getPathMain() + "/document_library/get_file?folderId=" + fileEntry.getFolderId() + "&name=" + Http.encodeURL(fileEntry.getName());
		}
		else {
			rowHREF = themeDisplay.getPathMain() + "/document_library/get_file?fileShortcutId=" + fileShortcut.getFileShortcutId();
		}

		// Title and description

		StringBuffer sb = new StringBuffer();

		sb.append("<img align=\"left\" border=\"0\" src=\"");
		sb.append(themeDisplay.getPathThemeImage());
		sb.append("/document_library/");
		sb.append(DLUtil.getFileExtension(fileEntry.getName()));
		sb.append(".png\">");
		sb.append(fileEntry.getTitle());

		row.addText(sb.toString(), rowHREF);

		// Downloads

		if (showColumnDownloads) {
			row.addText(Integer.toString(fileEntry.getReadCount()), rowHREF);
		}

		// Locked

		if (showColumnLocked) {
			boolean isLocked = LockServiceUtil.isLocked(DLFileEntry.class.getName(), fileEntry.getPrimaryKey());

			row.addText(LanguageUtil.get(pageContext, isLocked ? "yes" : "no"), rowHREF);
		}

		// Date

		if (showColumnDate) {
			row.addText(dateFormatDateTime.format(fileEntry.getModifiedDate()), rowHREF);
		}

		// Size

		if (showColumnSize) {
			row.addText(TextFormatter.formatKB(fileEntry.getSize(), locale) + "k", rowHREF);
		}

		// Add result row

		resultRows.add(row);
	}

	%>

	<c:if test="<%= (results.size() == 0) %>">
		<%= LanguageUtil.get(pageContext, "there-are-no-documents-available") %>
	</c:if>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

	<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

</c:if>