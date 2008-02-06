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

boolean print = ParamUtil.get(request, Constants.PRINT, false);

PortletURL tabs1URL = renderResponse.createRenderURL();

tabs1URL.setParameter("struts_action", "/wiki/view");
tabs1URL.setParameter("nodeId", String.valueOf(node.getNodeId()));
tabs1URL.setParameter("title", WikiPageImpl.FRONT_PAGE);

List nodes = WikiNodeLocalServiceUtil.getNodes(portletGroupId.longValue());

List allowedNodes = new ArrayList();

for (int i = 0; i < nodes.size(); i++) {
	WikiNode node2 = (WikiNode)nodes.get(i);

	if (WikiNodePermission.contains(permissionChecker, node2.getNodeId(), ActionKeys.VIEW)) {

		allowedNodes.add(node2);
	}
}

String tabs1Names = ListUtil.toString(allowedNodes, "name");
String tabs1Values = ListUtil.toString(allowedNodes, "nodeId");
%>

<c:if test="<%= !print %>">
	<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" varImpl="searchURL"><portlet:param name="struts_action" value="/wiki/search" /></liferay-portlet:renderURL>

	<form action="<%= searchURL %>" method="get" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">
	<liferay-portlet:renderURLParams varImpl="searchURL" />
	<input name="<portlet:namespace />redirect" type="hidden" value="<%= currentURL %>" />
	<input name="<portlet:namespace />nodeId" type="hidden" value="<%= node.getNodeId() %>" />

	<span class="wiki-search">
		<input name="<portlet:namespace />keywords" size="30" type="text" />

		<input type="submit" value="<liferay-ui:message key="search" />" />
	</span>

	</form>
</c:if>

<liferay-ui:tabs
	param="nodeId"
	names="<%= tabs1Names %>"
	tabsValues="<%= tabs1Values %>"
	portletURL="<%= tabs1URL %>"
/>

<c:if test="<%= !print %>">
	<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />
</c:if>
