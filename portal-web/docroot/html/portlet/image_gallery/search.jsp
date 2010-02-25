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

<%@ include file="/html/portlet/image_gallery/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long breadcrumbsFolderId = ParamUtil.getLong(request, "breadcrumbsFolderId");

long searchFolderId = ParamUtil.getLong(request, "searchFolderId");
long searchFolderIds = ParamUtil.getLong(request, "searchFolderIds");

long[] folderIdsArray = null;

if (searchFolderId > 0) {
	folderIdsArray = new long[] {searchFolderId};
}
else {
	List folderIds = new ArrayList();

	folderIds.add(new Long(searchFolderIds));

	IGFolderLocalServiceUtil.getSubfolderIds(folderIds, scopeGroupId, searchFolderIds);

	folderIdsArray = StringUtil.split(StringUtil.merge(folderIds), 0L);
}

String keywords = ParamUtil.getString(request, "keywords");
%>

<liferay-portlet:renderURL varImpl="searchURL"><portlet:param name="struts_action" value="/image_gallery/search" /></liferay-portlet:renderURL>

<aui:form action="<%= searchURL %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="searchURL" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="breadcrumbsFolderId" type="hidden" value="<%= breadcrumbsFolderId %>" />
	<aui:input name="searchFolderId" type="hidden" value="<%= searchFolderId %>" />
	<aui:input name="searchFolderIds" type="hidden" value="<%= searchFolderIds %>" />

	<liferay-ui:tabs
		names="search"
		backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
	/>

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/image_gallery/search");
	portletURL.setParameter("redirect", redirect);
	portletURL.setParameter("breadcrumbsFolderId", String.valueOf(breadcrumbsFolderId));
	portletURL.setParameter("searchFolderId", String.valueOf(searchFolderId));
	portletURL.setParameter("searchFolderIds", String.valueOf(searchFolderIds));
	portletURL.setParameter("keywords", keywords);

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, LanguageUtil.format(pageContext, "no-entries-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>"));

	try {
		Indexer indexer = IndexerRegistryUtil.getIndexer(IGImage.class);

		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setFolderIds(folderIdsArray);
		searchContext.setKeywords(keywords);
		searchContext.setStart(searchContainer.getStart());

		Hits hits = indexer.search(searchContext);

		int total = hits.getLength();

		searchContainer.setTotal(total);

		List results = new ArrayList(hits.getDocs().length);
		List scores = new ArrayList(hits.getDocs().length);

		for (int i = 0; i < hits.getDocs().length; i++) {
			Document doc = hits.doc(i);

			long imageId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			try {
				IGImage image = IGImageLocalServiceUtil.getImage(imageId);

				results.add(image);
				scores.add(new Double(hits.score(i)));
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Image gallery search index is stale and contains image " + imageId);
				}
			}
		}
	%>

		<aui:input inlineField="<%= true %>" label="" name="keywords" size="30" type="text" value="<%= keywords %>" />

		<aui:button type="submit" value="search" />

		<br /><br />

		<%@ include file="/html/portlet/image_gallery/view_images.jspf" %>

	<%
	}
	catch (Exception e) {
		_log.error(e.getMessage());
	}
	%>

</aui:form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />keywords);
	</aui:script>
</c:if>

<%
if (searchFolderId > 0) {
	IGUtil.addPortletBreadcrumbEntries(searchFolderId, request, renderResponse);
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "search") + ": " + keywords, currentURL);
%>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.image_gallery.search.jsp");
%>