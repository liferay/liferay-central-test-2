<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
String tabs2 = (String)request.getAttribute("edit_community_assignments.jsp-tabs2");

int cur = (Integer)request.getAttribute("edit_community_assignments.jsp-cur");

Group group = (Group)request.getAttribute("edit_community_assignments.jsp-group");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_community_assignments.jsp-portletURL");
%>

<aui:input name="addUserGroupIds" type="hidden" />
<aui:input name="removeUserGroupIds" type="hidden" />

<liferay-ui:tabs
	names="current,available"
	param="tabs2"
	url="<%= portletURL.toString() %>"
/>

<liferay-ui:search-container
	rowChecker="<%= new UserGroupGroupChecker(renderResponse, group) %>"
	searchContainer="<%= new UserGroupSearch(renderRequest, portletURL) %>"
>
	<liferay-ui:search-form
		page="/html/portlet/enterprise_admin/user_group_search.jsp"
	/>

	<%
	UserGroupSearchTerms searchTerms = (UserGroupSearchTerms)searchContainer.getSearchTerms();

	LinkedHashMap userGroupParams = new LinkedHashMap();

	if (tabs2.equals("current")) {
		userGroupParams.put("userGroupsGroups", new Long(group.getGroupId()));
	}
	%>

	<liferay-ui:search-container-results
		results="<%= UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), userGroupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
		total="<%= UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), userGroupParams) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.UserGroup"
		escapedModel="<%= true %>"
		keyProperty="userGroupId"
		modelVar="userGroup"
	>
		<liferay-ui:search-container-row-parameter
			name="group"
			value="<%= group %>"
		/>

		<liferay-ui:search-container-column-text
			name="name"
			orderable="<%= true %>"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			name="description"
			orderable="<%= true %>"
			property="description"
		/>

		<c:if test='<%= tabs2.equals("current") %>'>
			<liferay-ui:search-container-column-text
				buffer="buffer"
				name="site-roles"
			>

				<%
				List<UserGroupGroupRole> userGroupGroupRoles = UserGroupGroupRoleLocalServiceUtil.getUserGroupGroupRoles(userGroup.getUserGroupId(), group.getGroupId());

				for (int i = 0; i < userGroupGroupRoles.size(); i++) {
					UserGroupGroupRole userGroupGroupRole = userGroupGroupRoles.get(i);

					Role role = RoleLocalServiceUtil.getRole(userGroupGroupRole.getRoleId());

					buffer.append(HtmlUtil.escape(role.getTitle(locale)));

					if ((i + 1) < userGroupGroupRoles.size()) {
						buffer.append(StringPool.COMMA_AND_SPACE);
					}
				}
				%>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/html/portlet/sites_admin/user_group_action.jsp"
			/>
		</c:if>
	</liferay-ui:search-container-row>

	<div class="separator"><!-- --></div>

	<%
	String taglibOnClick = renderResponse.getNamespace() + "updateGroupUserGroups('" + portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur + "');";
	%>

	<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

	<br /><br />

	<liferay-ui:search-iterator />
</liferay-ui:search-container>