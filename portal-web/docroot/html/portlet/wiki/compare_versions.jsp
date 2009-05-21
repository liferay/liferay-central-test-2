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
String backURL = ParamUtil.getString(request, "backURL");
String type = ParamUtil.getString(request, "type");
%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<liferay-util:include page="/html/portlet/wiki/page_tabs.jsp">
	<liferay-util:param name="tabs1" value="history" />
</liferay-util:include>

<liferay-util:include page="/html/portlet/wiki/history_navigation.jsp">
	<liferay-util:param name="mode" value="<%= type %>" />
</liferay-util:include>

<c:choose>
	<c:when test='<%= Validator.equals ("html", type) %>'>

		<%
		String diffHtmlResults = (String)renderRequest.getAttribute(WebKeys.DIFF_HTML_RESULTS);
		%>

		<liferay-ui:diff-html diffHtmlResults="<%= diffHtmlResults %>" />
	</c:when>
	<c:otherwise>

		<%
		double sourceVersion = (Double)renderRequest.getAttribute(WebKeys.SOURCE_VERSION);
		double targetVersion = (Double)renderRequest.getAttribute(WebKeys.TARGET_VERSION);
		String title = (String)renderRequest.getAttribute(WebKeys.TITLE);
		List[] diffResults = (List[])renderRequest.getAttribute(WebKeys.DIFF_RESULTS);
		%>

		<liferay-ui:diff
			sourceName="<%= title + StringPool.SPACE + sourceVersion %>"
			targetName="<%= title + StringPool.SPACE + targetVersion %>"
			diffResults="<%= diffResults %>"
		/>
	</c:otherwise>
</c:choose>