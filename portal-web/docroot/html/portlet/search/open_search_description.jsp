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

<%@ include file="/html/common/init.jsp" %>

<%
long targetPlid = ParamUtil.getLong(request, "targetPlid");
long targetGroupId = ParamUtil.getLong(request, "targetGroupId");

response.setContentType(ContentTypes.TEXT_XML_UTF8);
%>

<?xml version="1.0" encoding="UTF-8"?>

<OpenSearchDescription xmlns="http://a9.com/-/spec/opensearch/1.1/">
	<ShortName><%= LanguageUtil.format(pageContext, "x-search", company.getName(), false) %></ShortName>
	<Description><%= LanguageUtil.format(pageContext, "x-search-provider", company.getName(), false) %></Description>

	<c:if test="<%= targetPlid > 0 %>">

		<%
		PortletURL searchURL = new PortletURLImpl(request, PortletKeys.SEARCH, targetPlid, PortletRequest.RENDER_PHASE);

		searchURL.setWindowState(WindowState.MAXIMIZED);

		searchURL.setParameter("struts_action", "/search/search");
		searchURL.setParameter("groupId", String.valueOf(targetGroupId));

		String templateURL = StringUtil.replace(searchURL.toString(), StringPool.AMPERSAND, StringPool.AMPERSAND_ENCODED);
		%>

		<Url type="text/html" template="<%= templateURL %>&amp;keywords={searchTerms}" />
	</c:if>

	<Url type="application/atom+xml" template="<%= themeDisplay.getPortalURL() %><%= PortalUtil.getPathMain() %>/search/open_search?keywords={searchTerms}&amp;p={startPage?}&amp;c={count?}&amp;format=atom" />
	<Url type="application/rss+xml" template="<%= themeDisplay.getPortalURL() %><%= PortalUtil.getPathMain() %>/search/open_search?keywords={searchTerms}&amp;p={startPage?}&amp;c={count?}&amp;format=rss" />
</OpenSearchDescription>