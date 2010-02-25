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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objects = (Object[])row.getObject();

PortletItem portletItem = (PortletItem)objects[0];
String portletResource = (String)objects[1];
%>

<liferay-ui:icon-menu>
	<portlet:actionURL var="restoreURL">
		<portlet:param name="struts_action" value="/portlet_configuration/edit_archived_setups" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="portletResource" value="<%= portletResource %>" />
		<portlet:param name="name" value="<%= portletItem.getName() %>" />
	</portlet:actionURL>

	<liferay-ui:icon image="undo" message="restore" url="<%= restoreURL %>" />

	<portlet:actionURL var="deleteURL">
		<portlet:param name="struts_action" value="/portlet_configuration/edit_archived_setups" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="portletResource" value="<%= portletResource %>" />
		<portlet:param name="portletItemId" value="<%= String.valueOf(portletItem.getPortletItemId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete url="<%= deleteURL %>" />
</liferay-ui:icon-menu>