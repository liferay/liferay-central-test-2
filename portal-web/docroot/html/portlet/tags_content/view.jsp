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

<%@ include file="/html/portlet/tags_content/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, null);

long[] entryIds = TagsEntryLocalServiceUtil.getEntryIds(company.getCompanyId(), tagsEntries);

int total = TagsAssetLocalServiceUtil.getAssetsCount(entryIds, andOperator);

searchContainer.setTotal(total);

List results = TagsAssetLocalServiceUtil.getAssets(entryIds, andOperator, searchContainer.getStart(), searchContainer.getEnd());

searchContainer.setResults(results);

for (int i = 0; i < results.size(); i++) {
	TagsAsset asset = (TagsAsset)results.get(i);

	String className = GetterUtil.getString(asset.getClassName());
	String classPK = GetterUtil.getString(asset.getClassPK());
%>

	<div>
		<c:choose>
			<c:when test="<%= className.equals(BookmarksEntry.class.getName()) %>">

				<%
				String entryId = classPK;

				BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(entryId);
				%>

				<a href="<%= entry.getUrl() %>"><%= entry.getName() %></a>
			</c:when>
			<c:when test="<%= className.equals(DLFileEntry.class.getName()) %>">

				<%
				PKParser pkParser = new PKParser(classPK);

				String folderId = pkParser.getString("folderId");
				String name = pkParser.getString("name");

				DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
					folderId, name);
				%>

				<a href="<%= themeDisplay.getPathMain() %>/document_library/get_file?folderId=<%= fileEntry.getFolderId() %>&name=<%= Http.encodeURL(fileEntry.getName()) %>">
				<img align="left" border="0" src="<%= themeDisplay.getPathThemeImage() %>/document_library/<%= DLUtil.getFileExtension(fileEntry.getName()) %>.png"><%= fileEntry.getTitle() %>
				</a>
			</c:when>
			<c:when test="<%= className.equals(IGImage.class.getName()) %>">

				<%
				PKParser pkParser = new PKParser(classPK);

				String companyId = pkParser.getString("companyId");
				String imageId = pkParser.getString("imageId");
				%>

				<img border="1" src="<%= themeDisplay.getPathImage() %>/image_gallery?img_id=<%= imageId %>&large=1">
			</c:when>
			<c:when test="<%= className.equals(JournalArticle.class.getName()) %>">

				<%
				PKParser pkParser = new PKParser(classPK);

				String companyId = pkParser.getString("companyId");
				long groupId = pkParser.getLong("groupId");
				String articleId = pkParser.getString("articleId");
				double version = pkParser.getDouble("version");
				String languageId = LanguageUtil.getLanguageId(request);

				String content = JournalArticleLocalServiceUtil.getArticleContent(
					companyId, groupId, articleId, version, languageId, themeDisplay);
				%>

				<%= content %>
			</c:when>
			<c:otherwise>
				<%= className %> is not a valid type.
			</c:otherwise>
		</c:choose>
	</div>

	<br>

<%
}
%>

<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />