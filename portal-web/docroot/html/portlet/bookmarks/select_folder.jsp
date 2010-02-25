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

<%@ include file="/html/portlet/bookmarks/init.jsp" %>

<%
BookmarksFolder folder = (BookmarksFolder)request.getAttribute(WebKeys.BOOKMARKS_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

if (folder != null) {
	BookmarksUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);
}
%>

<aui:form method="post" name="fm">
	<liferay-ui:tabs names="bookmarks-home" />

	<liferay-ui:breadcrumb showGuestGroup="<%= false %>" showParentGroups="<%= false %>" showLayout="<%= false %>" />

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/bookmarks/select_folder");
	portletURL.setParameter("folderId", String.valueOf(folderId));

	List<String> headerNames = new ArrayList<String>();

	headerNames.add("folder");
	headerNames.add("num-of-folders");
	headerNames.add("num-of-entries");
	headerNames.add(StringPool.BLANK);

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

	int total = BookmarksFolderLocalServiceUtil.getFoldersCount(scopeGroupId, folderId);

	searchContainer.setTotal(total);

	List results = BookmarksFolderLocalServiceUtil.getFolders(scopeGroupId, folderId, searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		BookmarksFolder curFolder = (BookmarksFolder)results.get(i);

		ResultRow row = new ResultRow(curFolder, curFolder.getFolderId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("struts_action", "/bookmarks/select_folder");
		rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

		// Name

		row.addText(curFolder.getName(), rowURL);

		// Statistics

		List subfolderIds = new ArrayList();

		subfolderIds.add(new Long(curFolder.getFolderId()));

		BookmarksFolderLocalServiceUtil.getSubfolderIds(subfolderIds, scopeGroupId, curFolder.getFolderId());

		int foldersCount = subfolderIds.size() - 1;
		int entriesCount = BookmarksEntryLocalServiceUtil.getFoldersEntriesCount(scopeGroupId, subfolderIds);

		row.addText(String.valueOf(foldersCount), rowURL);
		row.addText(String.valueOf(entriesCount), rowURL);

		// Action

		StringBundler sb = new StringBundler(7);

		sb.append("opener.");
		sb.append(renderResponse.getNamespace());
		sb.append("selectFolder('");
		sb.append(curFolder.getFolderId());
		sb.append("', '");
		sb.append(UnicodeFormatter.toString(curFolder.getName()));
		sb.append("'); window.close();");

		row.addButton("right", SearchEntry.DEFAULT_VALIGN, LanguageUtil.get(pageContext, "choose"), sb.toString());

		// Add result row

		resultRows.add(row);
	}

	boolean showAddFolderButton = BookmarksFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER);
	%>

	<c:if test="<%= showAddFolderButton %>">
		<portlet:renderURL var="editFolderURL">
			<portlet:param name="struts_action" value="/bookmarks/edit_folder" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
		</portlet:renderURL>

		<aui:button-row>
			<aui:button onClick="<%= editFolderURL %>" value='<%= (folder == null) ? "add-folder" : "add-subfolder" %>' />
		</aui:button-row>

		<c:if test="<%= !results.isEmpty() %>">
			<br />
		</c:if>
	</c:if>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>