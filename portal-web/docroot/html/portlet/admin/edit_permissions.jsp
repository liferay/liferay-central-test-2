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

<%@ include file="/html/portlet/admin/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "merge-redundant-roles");
String tabs2 = ParamUtil.getString(request, "tabs2", "organizations");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/admin_server/edit_permissions");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);

request.setAttribute("edit_permissions.jsp-tabs2", tabs2);

request.setAttribute("edit_permissions.jsp-portletURL", portletURL);
%>

<aui:form method="post" name="fm">
	<liferay-ui:tabs
		names="merge-redundant-roles,reassign-to-system-role"
		param="tabs1"
		url="<%= portletURL.toString() %>"
	/>

	<liferay-ui:tabs
		names="organizations,communities,users"
		param="tabs2"
		url="<%= portletURL.toString() %>"
	/>

	<c:choose>
		<c:when test='<%= tabs1.equals("merge-redundant-roles") %>'>
			<liferay-util:include page="/html/portlet/admin/edit_permissions_merge.jsp" />
		</c:when>
		<c:otherwise>
			<liferay-util:include page="/html/portlet/admin/edit_permissions_reassign.jsp" />
		</c:otherwise>
	</c:choose>
</aui:form>

<aui:script>
	function <portlet:namespace />invoke(link) {
		submitForm(document.<portlet:namespace />fm, link);
	}
</aui:script>