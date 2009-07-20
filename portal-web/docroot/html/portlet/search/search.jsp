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

<%@ include file="/html/portlet/search/init.jsp" %>

<%
String primarySearch = ParamUtil.getString(request, "primarySearch");

if (Validator.isNotNull(primarySearch)) {
	portalPrefs.setValue(PortletKeys.SEARCH, "primary-search", primarySearch);
}
else {
	primarySearch = portalPrefs.getValue(PortletKeys.SEARCH, "primary-search", StringPool.BLANK);
}

long groupId = ParamUtil.getLong(request, "groupId");

String keywords = ParamUtil.getString(request, "keywords");

String format = ParamUtil.getString(request, "format");
%>

<script type="text/javascript">
	function <portlet:namespace />search() {
		var keywords = document.<portlet:namespace />fm.<portlet:namespace />keywords.value;

		keywords = keywords.replace(/^\s+|\s+$/, '');

		if (keywords != '') {
			document.<portlet:namespace />fm.submit();
		}
	}
</script>

<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/search/search" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />search(); return false;">
<input name="<portlet:namespace />keywords" size="30" type="text" value="<%= HtmlUtil.escapeAttribute(keywords) %>" />
<input name="<portlet:namespace />format" type="hidden" value="<%= HtmlUtil.escapeAttribute(format) %>" />

<input type="submit" value="<liferay-ui:message key="search" />" />

<input type="button" value="<liferay-ui:message key="add-liferay-as-a-search-provider" />" onClick='window.external.AddSearchProvider("http://localhost:8080/c/search/open_search_description.xml");' />

<%
List<Portlet> portlets = PortletLocalServiceUtil.getPortlets(company.getCompanyId(), false, false);

portlets = ListUtil.sort(portlets, new PortletTitleComparator(application, locale));

Iterator itr = portlets.iterator();

StringBuilder sb = new StringBuilder();

while (itr.hasNext()) {
	Portlet portlet = (Portlet)itr.next();

	if (Validator.isNull(portlet.getOpenSearchClass())) {
		itr.remove();

		continue;
	}

	OpenSearch openSearch = portlet.getOpenSearchInstance();

	if (!openSearch.isEnabled()) {
		itr.remove();

		continue;
	}

	sb.append(PortalUtil.getPortletTitle(portlet, application, locale) + StringPool.COMMA_AND_SPACE);
}

if (Validator.isNotNull(primarySearch)) {
	for (int i = 0; i < portlets.size(); i++) {
		Portlet portlet = (Portlet)portlets.get(i);

		if (portlet.getOpenSearchClass().equals(primarySearch)) {
			if (i != 0) {
				portlets.remove(i);
				portlets.add(0, portlet);
			}

			break;
		}
	}
}
%>

<c:if test="<%= sb.length() > 0 %>">
	<div class="search-msg">
		<liferay-ui:message key="searched" /> <%= sb.substring(0, sb.length() - 2) %>
	</div>
</c:if>

<%
int totalResults = 0;

for (int i = 0; i < portlets.size(); i++) {
	Portlet portlet = (Portlet)portlets.get(i);

	OpenSearch openSearch = portlet.getOpenSearchInstance();

	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setWindowState(WindowState.MAXIMIZED);

	portletURL.setParameter("struts_action", "/search/search");
	portletURL.setParameter("keywords", keywords);
	portletURL.setParameter("format", format);

	//List<String> headerNames = new ArrayList<String>();

	//headerNames.add("#");
	//headerNames.add("summary");
	//headerNames.add("tags");
	//headerNames.add("score");

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM + i, 5, portletURL, null, LanguageUtil.format(pageContext, "no-results-were-found-that-matched-the-keywords-x", "<b>" + HtmlUtil.escape(keywords) + "</b>"));

	if (Validator.isNotNull(primarySearch) && portlet.getOpenSearchClass().equals(primarySearch)) {
		searchContainer.setDelta(SearchContainer.DEFAULT_DELTA);
	}

	String portletTitle = PortalUtil.getPortletTitle(portlet, application, locale);

	List resultRows = new ArrayList();

	try {
		String xml = openSearch.search(request, groupId, themeDisplay.getUserId(), keywords, searchContainer.getCur(), searchContainer.getDelta(), format);

		Document doc = SAXReaderUtil.read(xml);

		Element root = doc.getRootElement();

		//portletTitle = root.elementText("title");

		String[] queryTerms = StringUtil.split(root.elementText("queryTerms"), StringPool.COMMA_AND_SPACE);

		List<Element> entries = root.elements("entry");

		int total = GetterUtil.getInteger(root.elementText(OpenSearchUtil.getQName("totalResults", OpenSearchUtil.OS_NAMESPACE)));

		searchContainer.setTotal(total);

		resultRows = searchContainer.getResultRows();

		for (int j = 0; j < entries.size(); j++) {
			Element el = (Element)entries.get(j);

			ResultRow row = new ResultRow(doc, String.valueOf(j), j);

			// Position

			//row.addText(SearchEntry.DEFAULT_ALIGN, "top", searchContainer.getStart() + j + 1 + StringPool.PERIOD);

			// Summary

			String entryTitle = el.elementText("title");
			String entryHref = el.element("link").attributeValue("href");
			String summary = el.elementText("summary");

			if (portlet.getPortletId().equals(PortletKeys.DOCUMENT_LIBRARY)) {
				long folderId = GetterUtil.getLong(HttpUtil.getParameter(entryHref, "_20_folderId", false));
				String name = GetterUtil.getString(HttpUtil.getParameter(entryHref, "_20_name", false));

				DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(folderId, name);

				entryTitle = fileEntry.getTitle();
			}

			StringBuilder rowSB = new StringBuilder();

			if (portlet.getPortletId().equals(PortletKeys.JOURNAL)) {
				rowSB.append("<a class=\"entry-title\" href=\"");
				rowSB.append(entryHref);
				rowSB.append("\" target=\"_blank\">");
			}
			else {
				rowSB.append("<a class=\"entry-title\" href=\"");
				rowSB.append(entryHref);
				rowSB.append("\">");
			}

			rowSB.append(StringUtil.highlight(entryTitle, queryTerms));
			rowSB.append("</a>");

			if (Validator.isNotNull(summary)) {
				rowSB.append("<br />");
				rowSB.append(StringUtil.highlight(summary, queryTerms));
			}

			rowSB.append("<br />");

			// Tags

			String[] tags = StringUtil.split(el.elementText("tags"));

			String[] tagsQueryTerms = queryTerms;

			if (StringUtil.startsWith(keywords, Field.ASSET_TAG_NAMES + StringPool.COLON)) {
				tagsQueryTerms = new String[] {StringUtil.replace(keywords, Field.ASSET_TAG_NAMES + StringPool.COLON, StringPool.BLANK)};
			}

			for (int k = 0; k < tags.length; k++) {
				String tag = tags[k];

				PortletURL tagURL = PortletURLUtil.clone(portletURL, renderResponse);

				tagURL.setParameter("keywords", Field.ASSET_TAG_NAMES + StringPool.COLON + tag);
				tagURL.setParameter("format", format);

				if (k == 0) {
					rowSB.append("<div class=\"entry-tags\">");
					rowSB.append("<div class=\"taglib-asset-tags-summary\">");
				}

				rowSB.append("<a class=\"tag\" href=\"");
				rowSB.append(tagURL.toString());
				rowSB.append("\">");
				rowSB.append(StringUtil.highlight(tag, tagsQueryTerms));
				rowSB.append("</a>");

				if ((k + 1) == tags.length) {
					rowSB.append("</div>");
					rowSB.append("</div>");
				}
			}

			row.addText(rowSB.toString());

			// Ratings

			//String ratings = el.elementText("ratings");

			//row.addText(ratings);

			// Score

			//String score = el.elementText(OpenSearchUtil.getQName("score", OpenSearchUtil.RELEVANCE_NAMESPACE));

			//row.addText(score);

			// Add result row

			resultRows.add(row);
		}
	}
	catch (Exception e) {
		_log.error(portlet.getOpenSearchClass() + " " + e.getMessage());
	}
%>

	<c:if test="<%= !resultRows.isEmpty() %>">

		<%
		totalResults = totalResults + searchContainer.getTotal();
		%>

		<div class="section-title">
			<%= portletTitle %> (<%= searchContainer.getTotal() %>)
		</div>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />

		<c:choose>
			<c:when test="<%= Validator.isNotNull(primarySearch) && portlet.getOpenSearchClass().equals(primarySearch) %>">
				<div class="search-paginator-container">
					<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
				</div>
			</c:when>
			<c:otherwise>
				<div class="more-results">
					<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/search/search" /><portlet:param name="primarySearch" value="<%= portlet.getOpenSearchClass() %>" /><portlet:param name="keywords" value="<%= HtmlUtil.escape(keywords) %>" /><portlet:param name="format" value="<%= format %>" /></portlet:renderURL>"><%= LanguageUtil.format(pageContext, "more-x-results", portletTitle) %> &raquo;</a>
				</div>
			</c:otherwise>
		</c:choose>
	</c:if>

<%
}
%>

<c:if test="<%= totalResults == 0 %>">
	<div class="no-results">
		<%= LanguageUtil.format(pageContext, "no-results-were-found-that-matched-the-keywords-x", "<b>" + HtmlUtil.escape(keywords) + "</b>") %>
	</div>
</c:if>

</form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />keywords);
	</script>
</c:if>

<%
String pageSubtitle = LanguageUtil.get(pageContext, "search-results");
String pageDescription = LanguageUtil.get(pageContext, "searched") + StringPool.SPACE + sb.substring(0, sb.length() - 2);
String pageKeywords = LanguageUtil.get(pageContext, "search");

if (Validator.isNotNull(keywords)) {
	pageKeywords = keywords;

	if (StringUtil.startsWith(pageKeywords, Field.ASSET_TAG_NAMES + StringPool.COLON)) {
		pageKeywords = StringUtil.replace(pageKeywords, Field.ASSET_TAG_NAMES + StringPool.COLON, StringPool.BLANK);
	}
}

PortalUtil.setPageSubtitle(pageSubtitle, request);
PortalUtil.setPageDescription(pageDescription, request);
PortalUtil.setPageKeywords(pageKeywords, request);
%>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.search.search.jsp");
%>