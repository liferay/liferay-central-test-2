<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

MBTopic topic = (MBTopic)row.getObject();
%>

<c:if test="<%= MBTopicPermission.contains(permissionChecker, topic, ActionKeys.UPDATE) %>">
	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
		<portlet:param name="struts_action" value="/message_boards/edit_topic" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="topicId" value="<%= topic.getTopicId() %>" />
	</portlet:renderURL>

	<liferay-ui:icon image="edit" url="<%= portletURL %>" />
</c:if>

<c:if test="<%= MBTopicPermission.contains(permissionChecker, topic, ActionKeys.PERMISSIONS) %>">
	<liferay-security:permissionsURL
		modelResource="<%= MBTopic.class.getName() %>"
		modelResourceDescription="<%= topic.getName() %>"
		resourcePrimKey="<%= topic.getPrimaryKey().toString() %>"
		var="portletURL"
	/>

	<liferay-ui:icon image="permissions" url="<%= portletURL %>" />
</c:if>

<liferay-ui:icon image="rss" url='<%= themeDisplay.getPathMain() + "/message_boards/rss?topicId=" + topic.getTopicId() %>' target="_blank" />

<c:if test="<%= MBTopicPermission.contains(permissionChecker, topic, ActionKeys.DELETE) %>">
	<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
		<portlet:param name="struts_action" value="/message_boards/edit_topic" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="topicId" value="<%= topic.getTopicId() %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete url="<%= portletURL %>" />
</c:if>