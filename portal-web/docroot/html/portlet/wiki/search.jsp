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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = null;

long nodeId = BeanParamUtil.getLong(node, request, "nodeId");

long[] nodeIds = null;

if (node != null) {
	nodeIds = new long[] {nodeId};
}

String keywords = ParamUtil.getString(request, "keywords");

boolean createNewPage = true;
%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="struts_action" value="/wiki/search" />
</liferay-portlet:renderURL>

<aui:form action="<%= searchURL %>" method="get" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="nodeId" type="hidden" value="<%= nodeId %>" />

	<h1 class="page-title"><liferay-ui:message key="search-results" /></h1>

	<%
	PortletURL addPageURL = renderResponse.createRenderURL();

	addPageURL.setParameter("struts_action", "/wiki/edit_page");
	addPageURL.setParameter("redirect", redirect);
	addPageURL.setParameter("nodeId", String.valueOf(nodeId));
	addPageURL.setParameter("title", keywords);
	addPageURL.setParameter("editTitle", "1");

	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/wiki/search");
	portletURL.setParameter("redirect", redirect);
	portletURL.setParameter("nodeId", String.valueOf(nodeId));
	portletURL.setParameter("keywords", keywords);

	List<String> headerNames = new ArrayList<String>();

	headerNames.add("#");
	headerNames.add("wiki");
	headerNames.add("page");
	headerNames.add("score");

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, LanguageUtil.format(pageContext, "no-pages-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>"));

	try {
		Hits results = WikiNodeLocalServiceUtil.search(company.getCompanyId(), scopeGroupId, themeDisplay.getUserId(), nodeIds, keywords, searchContainer.getStart(), searchContainer.getEnd());

		int total = results.getLength();

		searchContainer.setTotal(total);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.getDocs().length; i++) {
			Document doc = results.doc(i);

			ResultRow row = new ResultRow(doc, i, i);

			// Position

			row.addText(searchContainer.getStart() + i + 1 + StringPool.PERIOD);

			// Node and page

			long curNodeId = GetterUtil.getLong(doc.get("nodeId"));
			String title = doc.get("title");

			if (title.equalsIgnoreCase(keywords)) {
				createNewPage = false;
			}

			WikiNode curNode = null;

			try {
				curNode = WikiNodeLocalServiceUtil.getNode(curNodeId);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Wiki search index is stale and contains node " + curNodeId);
				}

				continue;
			}

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("struts_action", "/wiki/view");
			rowURL.setParameter("nodeName", node.getName());
			rowURL.setParameter("title", title);

			row.addText(curNode.getName(), rowURL);

			row.addText(title, rowURL);

			// Score

			row.addScore(results.score(i));

			// Add result row

			resultRows.add(row);
		}
	%>

		<c:if test="<%= createNewPage %>">
			<strong><aui:a cssClass="new-page" href="<%= addPageURL.toString() %>" label="create-a-new-page-on-this-topic" /></strong>
		</c:if>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

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

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.wiki.search.jsp");
%>