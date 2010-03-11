<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ include file="/html/portlet/blogs/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

MBMessage message = (MBMessage)row.getObject();

long classPK = message.getClassPK();
String className = BlogsEntry.class.getName();
%>

<liferay-ui:icon-menu>
	<c:if test="<%= MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, className, classPK, user.getUserId(), ActionKeys.UPDATE_DISCUSSION) %>">
		<c:if test="<%= message.getStatus() != StatusConstants.APPROVED %>">
			<portlet:actionURL var="approveURL">
				<portlet:param name="struts_action" value="/blogs/edit_entry_discussion" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.APPROVE %>" />
				<portlet:param name="className" value="<%= className %>" />
				<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon image="approve" label="<%= true %>" url="<%= approveURL %>" />
		</c:if>

		<c:if test="<%= message.getStatus() != StatusConstants.PENDING %>">
			<portlet:actionURL var="unapproveURL">
				<portlet:param name="struts_action" value="/blogs/edit_entry_discussion" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNAPPROVE %>" />
				<portlet:param name="className" value="<%= className %>" />
				<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon image="undo" label="<%= true %>" message="unapprove" url="<%= unapproveURL %>" />
		</c:if>

		<c:if test="<%= message.getStatus() != StatusConstants.DENIED %>">
			<portlet:actionURL var="denyURL">
				<portlet:param name="struts_action" value="/blogs/edit_entry_discussion" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DENY %>" />
				<portlet:param name="className" value="<%= className %>" />
				<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon image="deny" label="<%= true %>" message="mark-as-spam" url="<%= denyURL %>" />
		</c:if>
	</c:if>

	<c:if test="<%= MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, className, classPK, user.getUserId(), ActionKeys.DELETE_DISCUSSION) %>">
		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/blogs/edit_entry_discussion" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="className" value="<%= className %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
			<portlet:param name="permissionClassName" value="<%= className %>" />
			<portlet:param name="permissionClassPK" value="<%= String.valueOf(classPK) %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteURL %>" />
	</c:if>
</liferay-ui:icon-menu>