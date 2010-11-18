<%--
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
--%>

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

Organization organization = (Organization)objArray[0];
Group group  = organization.getGroup();
String tabs1 = (String)objArray[1];
%>

<liferay-ui:icon-menu>
	<c:if test='<%= organization.isMembershipTypeWeak() && tabs1.equals("available-organizations") %>'>
		<c:choose>
			<c:when test="<%= OrganizationLocalServiceUtil.hasUserOrganization(user.getUserId(), organization.getOrganizationId()) %>">
				<portlet:actionURL var="leaveURL">
					<portlet:param name="struts_action" value="/my_organizations/edit_organization_assignments" />
					<portlet:param name="<%= Constants.CMD %>" value="group_users" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
					<portlet:param name="removeUserIds" value="<%= String.valueOf(user.getUserId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					image="leave"
					url="<%= leaveURL %>"
				/>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="<%= group.getType() == GroupConstants.TYPE_COMMUNITY_OPEN %>">
						<portlet:actionURL var="joinURL">
							<portlet:param name="struts_action" value="/my_organizations/edit_organization_assignments" />
							<portlet:param name="<%= Constants.CMD %>" value="group_users" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
							<portlet:param name="addUserIds" value="<%= String.valueOf(user.getUserId()) %>" />
						</portlet:actionURL>

						<liferay-ui:icon
							image="join"
							url="<%= joinURL %>"
						/>
					</c:when>
					<c:otherwise>
						<c:if test="<%= group.getType() == GroupConstants.TYPE_COMMUNITY_RESTRICTED %>">
							<portlet:renderURL var="membershipRequestURL">
								<portlet:param name="struts_action" value="/my_organizations/post_membership_request" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
							</portlet:renderURL>

							<liferay-ui:icon
								image="post"
								message="request-membership"
								url="<%= membershipRequestURL %>"
							/>
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:if test='<%= organization.isMembershipTypeWeak() && tabs1.equals("my-organizations") && (group.getType() == GroupConstants.TYPE_COMMUNITY_RESTRICTED) && GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_MEMBERS) %>'>
		<portlet:renderURL var="viewMembershipRequestsURL">
		<portlet:param name="struts_action" value="/my_organizations/view_membership_requests" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="manage_task"
			message="view-membership-requests"
			url="<%= viewMembershipRequestsURL %>"
		/>
	</c:if>

	<c:if test='<%= organization.isMembershipTypeWeak() && tabs1.equals("my-organizations") %>'>
		<portlet:actionURL var="leaveURL">
			<portlet:param name="struts_action" value="/my_organizations/edit_organization_assignments" />
			<portlet:param name="<%= Constants.CMD %>" value="group_users" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="removeUserIds" value="<%= String.valueOf(user.getUserId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			image="leave"
			url="<%= leaveURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>