<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String tabs1 = ParamUtil.getString(request, "tabs1", "pending");

int statusId = -1;

if (tabs1.equals("approved")) {
	statusId = MembershipRequestConstants.STATUS_APPROVED;
}
else if (tabs1.equals("denied")) {
	statusId = MembershipRequestConstants.STATUS_DENIED;
}
else {
	statusId = MembershipRequestConstants.STATUS_PENDING;
}

Group group = (Group)request.getAttribute(WebKeys.GROUP);
%>

<liferay-ui:success key="membershipReplySent" message="your-reply-will-be-sent-to-the-user-by-email" />

<c:if test="<%= !layout.isTypeControlPanel() %>">
	<liferay-ui:header
		backURL="<%= redirect %>"
		escapeXml="<%= false %>"
		localizeTitle="<%= false %>"
		title='<%= HtmlUtil.escape(group.getDescriptiveName(locale)) + StringPool.COLON + StringPool.SPACE + LanguageUtil.get(pageContext, "manage-memberships") %>'
	/>
</c:if>

<liferay-util:include page="/html/portlet/sites_admin/edit_site_assignments_toolbar.jsp">
	<liferay-util:param name="toolbarItem" value="view-membership-requests" />
</liferay-util:include>

<liferay-ui:tabs
	names="pending,approved,denied"
	url="<%= currentURL %>"
/>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/sites_admin/view_membership_requests");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("groupId", String.valueOf(group.getGroupId()));
%>

<liferay-ui:search-container
	emptyResultsMessage="no-requests-were-found"
	iteratorURL="<%= portletURL %>"
	total="<%= MembershipRequestLocalServiceUtil.searchCount(group.getGroupId(), statusId) %>"
>

	<liferay-ui:search-container-results
		results="<%= MembershipRequestLocalServiceUtil.search(group.getGroupId(), statusId, searchContainer.getStart(), searchContainer.getEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.MembershipRequest"
		modelVar="membershipRequest"
	>

		<%
		User user2 = UserLocalServiceUtil.getUserById(membershipRequest.getUserId());

		row.setObject(new Object[] {user2, group, membershipRequest});
		%>

		<liferay-ui:search-container-column-date
			name="date"
			value="<%= membershipRequest.getCreateDate() %>"
		/>

		<liferay-ui:search-container-column-text
			name="user"
		>
			<%= HtmlUtil.escape(user2.getFullName()) %> (<%= user2.getEmailAddress() %>)
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			name="user-comments"
			value="<%= HtmlUtil.escape(membershipRequest.getComments()) %>"
		/>

		<c:if test='<%= !tabs1.equals("pending") %>'>
			<liferay-ui:search-container-column-date
				name="reply-date"
				value="<%= membershipRequest.getReplyDate() %>"
			/>

			<%
			User user3 = UserLocalServiceUtil.getUserById(membershipRequest.getReplierUserId());
			%>

			<liferay-ui:search-container-column-text
				name="replier"
			>
				<c:choose>
					<c:when test="<%= user3.isDefaultUser() %>">

						<%
						Company user3Company = CompanyLocalServiceUtil.getCompanyById(user3.getCompanyId());
						%>

						<%= HtmlUtil.escape(user3Company.getName()) %>
					</c:when>
					<c:otherwise>
						<%= HtmlUtil.escape(user3.getFullName()) %>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="reply-comments"
				value="<%= HtmlUtil.escape(user3.getFullName()) %>"
			/>
		</c:if>

		<liferay-ui:search-container-column-jsp
			cssClass="entry-action"
			path="/html/portlet/sites_admin/membership_request_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</liferay-ui:search-container>