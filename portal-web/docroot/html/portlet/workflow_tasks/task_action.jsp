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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

TaskInstanceInfo taskInstanceInfo = (TaskInstanceInfo)row.getParameter("taskInstanceInfo");

List<String> activityNames = TaskInstanceManagerUtil.getPossibleNextActivityNames(taskInstanceInfo.getTaskInstanceId(), user.getUserId());
%>

<liferay-ui:icon-menu>

	<%
	for (String activityName : activityNames) {
		String message = "proceed";

		if (Validator.isNotNull(activityName)) {
			message = activityName;
		}
	%>

		<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editURL">
			<portlet:param name="struts_action" value="/workflow_tasks/edit_task" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="taskIntanceId" value="<%= StringUtil.valueOf(taskInstanceInfo.getTaskInstanceId()) %>" />

			<c:if test="<%= activityName != null %>">
				<portlet:param name="activityName" value="<%= activityName %>" />
			</c:if>
		</portlet:actionURL>

		<liferay-ui:icon image="edit" message="<%= message %>" url="<%= editURL %>" />

	<%
	}
	%>

</liferay-ui:icon-menu>