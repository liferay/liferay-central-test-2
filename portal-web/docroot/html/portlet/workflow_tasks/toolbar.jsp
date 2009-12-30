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

<%@ include file="/html/portlet/workflow_tasks/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "all");
String backURL = ParamUtil.getString(request, "backURL");
%>

<div class="lfr-portlet-toolbar">
	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="viewAllURL">
		<portlet:param name="struts_action" value="/workflow_tasks/view" />
		<portlet:param name="toolbarItem" value="all" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("all") ? "current" : StringPool.BLANK %>">
		<a href="<%= viewAllURL %>"><liferay-ui:message key="all" /></a>
	</span>

	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="assignedToMeURL">
		<portlet:param name="struts_action" value="/workflow_tasks/view" />
		<portlet:param name="toolbarItem" value="assigned-to-me" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button assigned-to-me <%= toolbarItem.equals("assigned-to-me") ? "current" : StringPool.BLANK %>">
		<a href="<%= assignedToMeURL %>"><liferay-ui:message key="assigned-to-me" /></a>
	</span>

	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="assignedToMyRolesURL">
		<portlet:param name="struts_action" value="/workflow_tasks/view" />
		<portlet:param name="toolbarItem" value="assigned-to-my-roles" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button assigned-to-my-role <%= toolbarItem.equals("assigned-to-my-roles") ? "current" : StringPool.BLANK %>">
		<a href="<%= assignedToMyRolesURL %>"><liferay-ui:message key="assigned-to-my-roles" /></a>
	</span>

	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="completedURL">
		<portlet:param name="struts_action" value="/workflow_tasks/view" />
		<portlet:param name="toolbarItem" value="my-completed-tasks" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button completed-button <%= toolbarItem.equals("my-completed-tasks") ? "current" : StringPool.BLANK %>">
		<a href="<%= completedURL %>"><liferay-ui:message key="my-completed-tasks" /></a>
	</span>
</div>