<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

String title = wikiPage.getTitle();

String tab = ParamUtil.getString(request, "tab");

String tabsNames = "page-info,page-history,page-links,attachments";

PortletURL viewPageInfoURL = renderResponse.createRenderURL();

viewPageInfoURL.setParameter("struts_action", "/wiki/view_page_info");
viewPageInfoURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
viewPageInfoURL.setParameter("title", wikiPage.getTitle());

PortletURL viewPageHistoryURL = renderResponse.createRenderURL();

viewPageHistoryURL.setParameter("struts_action", "/wiki/view_page_history");
viewPageHistoryURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
viewPageHistoryURL.setParameter("title", wikiPage.getTitle());

PortletURL viewPageLinksURL = renderResponse.createRenderURL();

viewPageLinksURL.setParameter("struts_action", "/wiki/view_page_links");
viewPageLinksURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
viewPageLinksURL.setParameter("title", wikiPage.getTitle());

PortletURL attachmentsURL = renderResponse.createRenderURL();

attachmentsURL.setParameter("struts_action", "/wiki/view_page_attachments");
attachmentsURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
attachmentsURL.setParameter("title", wikiPage.getTitle());

%>

<%@ include file="/html/portlet/wiki/page_name.jspf" %>

<liferay-ui:tabs names="<%= tabsNames %>"
	value="<%= tab %>"
	url0="<%= viewPageInfoURL.toString() %>"
	url1="<%= viewPageHistoryURL.toString() %>"
	url2="<%= viewPageLinksURL.toString() %>"
	url3="<%= attachmentsURL.toString() %>"/>