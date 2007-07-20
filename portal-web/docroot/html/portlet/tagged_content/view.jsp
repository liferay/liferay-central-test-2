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

<%@ include file="/html/portlet/tagged_content/init.jsp" %>

<%

// Merge URL tags

String[] compilerEntries = (String[])request.getAttribute(WebKeys.TAGS_COMPILER_ENTRIES);

if ((compilerEntries != null) && (compilerEntries.length > 0)) {
	String[] newEntries = ArrayUtil.append(entries, compilerEntries);

	entries = newEntries;
}

if (themeDisplay.isSignedIn()) {

	// Merge my global tags

	PortalPreferences myGlobalPrefs = PortletPreferencesFactory.getPortalPreferences(request);

	String[] myGlobalEntries = myGlobalPrefs.getValues(PortletKeys.MY_GLOBAL_TAGS, "entries", new String[0]);

	if ((myGlobalEntries != null) && (myGlobalEntries.length > 0)) {
		String[] newEntries = ArrayUtil.append(entries, myGlobalEntries);

		entries = newEntries;
	}

	// Merge my community tags

	PortletPreferences myCommunityPrefs = PortletPreferencesFactory.getPortletPreferences(request, PortletKeys.MY_COMMUNITY_TAGS);

	String[] myCommunityEntries = myCommunityPrefs.getValues("entries", new String[0]);

	if ((myCommunityEntries != null) && (myCommunityEntries.length > 0)) {
		String[] newEntries = ArrayUtil.append(entries, myCommunityEntries);

		entries = newEntries;
	}
}

// Display content

PortletURL portletURL = renderResponse.createRenderURL();

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, null);

long[] entryIds = TagsEntryLocalServiceUtil.getEntryIds(company.getCompanyId(), entries);
long[] notEntryIds = TagsEntryLocalServiceUtil.getEntryIds(company.getCompanyId(), notEntries);

int total = TagsAssetLocalServiceUtil.getAssetsCount(entryIds, notEntryIds, andOperator);

searchContainer.setTotal(total);

List results = TagsAssetLocalServiceUtil.getAssets(entryIds, notEntryIds, andOperator, searchContainer.getStart(), searchContainer.getEnd());

searchContainer.setResults(results);

for (int i = 0; i < results.size(); i++) {
	TagsAsset asset = (TagsAsset)results.get(i);

	String className = PortalUtil.getClassName(asset.getClassNameId());
	long classPK = asset.getClassPK();
%>

	<div>
		<c:choose>
			<c:when test="<%= className.equals(BlogsEntry.class.getName()) %>">

				<%
				BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(classPK);
				%>

				<%= entry.getContent() %>
			</c:when>
			<c:when test="<%= className.equals(BookmarksEntry.class.getName()) %>">

				<%
				BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);
				%>

				<a href="<%= entry.getUrl() %>"><%= entry.getName() %></a>
			</c:when>
			<c:when test="<%= className.equals(DLFileEntry.class.getName()) %>">

				<%
				DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(classPK);
				%>

				<a href="<%= themeDisplay.getPathMain() %>/document_library/get_file?folderId=<%= fileEntry.getFolderId() %>&name=<%= Http.encodeURL(fileEntry.getName()) %>">
				<img align="left" border="0" src="<%= themeDisplay.getPathThemeImages() %>/document_library/<%= DLUtil.getFileExtension(fileEntry.getName()) %>.png" /><%= fileEntry.getTitle() %>
				</a>
			</c:when>
			<c:when test="<%= className.equals(IGImage.class.getName()) %>">

				<%
				IGImage image = IGImageLocalServiceUtil.getImage(classPK);
				%>

				<img border="1" src="<%= themeDisplay.getPathImage() %>/image_gallery?img_id=<%= image.getLargeImageId() %>" />
			</c:when>
			<c:when test="<%= className.equals(JournalArticle.class.getName()) %>">

				<%
				JournalArticleResource articleResource = JournalArticleResourceLocalServiceUtil.getArticleResource(classPK);

				String languageId = LanguageUtil.getLanguageId(request);

				JournalArticleDisplay articleDisplay = JournalArticleLocalServiceUtil.getArticleDisplay(articleResource.getGroupId(), articleResource.getArticleId(), languageId, themeDisplay);
				%>

				<c:if test="<%= articleDisplay != null %>">
					<%= articleDisplay.getContent() %>

					<div>
						<br />

						<c:if test="<%= JournalArticlePermission.contains(permissionChecker, articleDisplay.getGroupId(), articleDisplay.getArticleId(), ActionKeys.UPDATE) %>">
							<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editURL" portletName="<%= PortletKeys.JOURNAL %>">
								<liferay-portlet:param name="struts_action" value="/journal/edit_article" />
								<liferay-portlet:param name="redirect" value="<%= currentURL %>" />
								<liferay-portlet:param name="groupId" value="<%= String.valueOf(articleDisplay.getGroupId()) %>" />
								<liferay-portlet:param name="articleId" value="<%= articleDisplay.getArticleId() %>" />
								<liferay-portlet:param name="version" value="<%= String.valueOf(articleDisplay.getVersion()) %>" />
							</liferay-portlet:renderURL>

							<liferay-ui:icon image="edit" message="edit-article" url="<%= editURL %>" />
						</c:if>
					</div>
				</c:if>
			</c:when>
			<c:when test="<%= className.equals(WikiPage.class.getName()) %>">

				<%
				WikiPageResource pageResource = WikiPageResourceLocalServiceUtil.getPageResource(classPK);

				WikiPage wikiPage = WikiPageLocalServiceUtil.getPage(pageResource.getNodeId(), pageResource.getTitle());

				String content = wikiPage.getContent();
				%>

				<%= content %>
			</c:when>
			<c:otherwise>
				<%= className %> is not a valid type.
			</c:otherwise>
		</c:choose>
	</div>

	<c:if test="<%= (i + 1) < results.size() %>">
		<br />
	</c:if>

<%
}
%>

<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />