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

PortletURL viewPageGeneralURL = renderResponse.createRenderURL();

viewPageGeneralURL.setParameter("struts_action", "/wiki/view_page_general");
viewPageGeneralURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
viewPageGeneralURL.setParameter("title", wikiPage.getTitle());

PortletURL viewPageHistoryURL = PortletURLUtil.clone(viewPageGeneralURL, renderResponse);

viewPageHistoryURL.setParameter("struts_action", "/wiki/view_page_history");

PortletURL viewPageIncomingLinksURL = PortletURLUtil.clone(viewPageGeneralURL, renderResponse);

viewPageIncomingLinksURL.setParameter("struts_action", "/wiki/view_page_incoming_links");

PortletURL viewPageOutgoingLinksURL = PortletURLUtil.clone(viewPageGeneralURL, renderResponse);

viewPageOutgoingLinksURL.setParameter("struts_action", "/wiki/view_page_outgoing_links");

PortletURL attachmentsURL = PortletURLUtil.clone(viewPageGeneralURL, renderResponse);

attachmentsURL.setParameter("struts_action", "/wiki/view_page_attachments");
%>

<%@ include file="/html/portlet/wiki/page_name.jspf" %>

<liferay-ui:tabs
	names="general,history,incoming-links,outgoing-links,attachments"
	url0="<%= viewPageGeneralURL.toString() %>"
	url1="<%= viewPageHistoryURL.toString() %>"
	url2="<%= viewPageIncomingLinksURL.toString() %>"
	url3="<%= viewPageOutgoingLinksURL.toString() %>"
	url4="<%= attachmentsURL.toString() %>"
/>