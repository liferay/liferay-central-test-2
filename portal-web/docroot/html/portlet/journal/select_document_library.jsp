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
DLFolder folder = (DLFolder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long groupId = ParamUtil.getLong(request, "groupId");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/select_document_library");
portletURL.setParameter("folderId", String.valueOf(folderId));
portletURL.setParameter("groupId", String.valueOf(groupId));
%>

<form method="post" name="<portlet:namespace />">

<liferay-ui:tabs names="folders" />

<c:if test="<%= folder != null %>">

	<%
	String breadcrumbs = DLUtil.getBreadcrumbs(folder, null, rootFolderId, pageContext, renderRequest, renderResponse);

	breadcrumbs = StringUtil.replace(breadcrumbs, "document_library%2Fselect_folder", "journal%2Fselect_document_library&" + renderResponse.getNamespace() + "groupId=" + groupId);
	%>

	<div class="breadcrumbs">
		<%= breadcrumbs %>
	</div>
</c:if>

<%
List<String> headerNames = new ArrayList<String>();

headerNames.add("folder");
headerNames.add("num-of-folders");
headerNames.add("num-of-documents");

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur1", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

int total = DLFolderLocalServiceUtil.getFoldersCount(groupId, folderId);

searchContainer.setTotal(total);

List results = DLFolderLocalServiceUtil.getFolders(groupId, folderId, searchContainer.getStart(), searchContainer.getEnd());

searchContainer.setResults(results);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	DLFolder curFolder = (DLFolder)results.get(i);

	ResultRow row = new ResultRow(curFolder, curFolder.getFolderId(), i);

	PortletURL rowURL = renderResponse.createRenderURL();

	rowURL.setParameter("struts_action", "/journal/select_document_library");
	rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
	rowURL.setParameter("groupId", String.valueOf(groupId));

	// Name

	StringBuilder sb = new StringBuilder();

	sb.append("<img align=\"left\" border=\"0\" src=\"");
	sb.append(themeDisplay.getPathThemeImages());
	sb.append("/common/folder.png\">");
	sb.append(curFolder.getName());

	row.addText(sb.toString(), rowURL);

	// Statistics

	List subfolderIds = new ArrayList();

	subfolderIds.add(new Long(curFolder.getFolderId()));

	DLFolderLocalServiceUtil.getSubfolderIds(subfolderIds, groupId, curFolder.getFolderId());

	int foldersCount = subfolderIds.size() - 1;
	int fileEntriesCount = DLFileEntryLocalServiceUtil.getFoldersFileEntriesCount(subfolderIds);

	row.addText(String.valueOf(foldersCount), rowURL);
	row.addText(String.valueOf(fileEntriesCount), rowURL);

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

<c:if test="<%= folder != null %>">
	<br />

	<liferay-ui:tabs names="documents" />

	<%
	headerNames.clear();

	headerNames.add("document");
	headerNames.add("size");
	headerNames.add("downloads");
	headerNames.add("locked");
	headerNames.add(StringPool.BLANK);

	searchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

	total = DLFileEntryLocalServiceUtil.getFileEntriesCount(folder.getFolderId());

	searchContainer.setTotal(total);

	results = DLFileEntryLocalServiceUtil.getFileEntries(folder.getFolderId(), searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);

	resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		DLFileEntry fileEntry = (DLFileEntry)results.get(i);

		ResultRow row = new ResultRow(fileEntry, fileEntry.getFileEntryId(), i);

		StringBuilder sb = new StringBuilder();

		sb.append(themeDisplay.getPathMain());
		sb.append("/document_library/get_file?p_l_id=");
		sb.append(themeDisplay.getPlid());
		sb.append("&folderId=");
		sb.append(folder.getFolderId());
		sb.append("&name=");
		sb.append(HttpUtil.encodeURL(fileEntry.getName()));

		String rowHREF = sb.toString();

		// Title

		sb = new StringBuilder();

		sb.append("<img align=\"left\" border=\"0\" src=\"");
		sb.append(themeDisplay.getPathThemeImages());
		sb.append("/document_library/");
		sb.append(DLUtil.getFileExtension(fileEntry.getName()));
		sb.append(".png\">");
		sb.append(fileEntry.getTitle());

		row.addText(sb.toString(), rowHREF);

		// Statistics

		row.addText(TextFormatter.formatKB(fileEntry.getSize(), locale) + "k", rowHREF);
		row.addText(String.valueOf(fileEntry.getReadCount()), rowHREF);

		// Locked

		boolean isLocked = LockServiceUtil.isLocked(DLFileEntry.class.getName(), DLUtil.getLockId(fileEntry.getFolderId(), fileEntry.getName()));

		row.addText(LanguageUtil.get(pageContext, isLocked ? "yes" : "no"), rowHREF);

		// Action

		sb = new StringBuilder();

		sb.append("opener.");
		sb.append(renderResponse.getNamespace());
		sb.append("selectDocumentLibrary('");
		sb.append(themeDisplay.getPathMain());
		sb.append("/document_library/get_file?uuid=");
		sb.append(fileEntry.getUuid());
		sb.append("&groupId=");
		sb.append(folder.getGroupId());
		sb.append("'); window.close();");

		row.addButton("right", SearchEntry.DEFAULT_VALIGN, LanguageUtil.get(pageContext, "choose"), sb.toString());

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</c:if>

</form>