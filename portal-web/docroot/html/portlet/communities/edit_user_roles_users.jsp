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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
String tabs1 = (String)request.getAttribute("edit_user_roles.jsp-tabs1");

String cur = (String)request.getAttribute("edit_user_roles.jsp-cur");

Group group = (Group)request.getAttribute("edit_user_roles.jsp-group");
String groupName = (String)request.getAttribute("edit_user_roles.jsp-groupName");
Role role = (Role)request.getAttribute("edit_user_roles.jsp-role");
long roleId = (Long)request.getAttribute("edit_user_roles.jsp-roleId");
Organization organization = (Organization)request.getAttribute("edit_user_roles.jsp-organization");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_user_roles.jsp-portletURL");
%>

<input name="<portlet:namespace />addUserIds" type="hidden" value="" />
<input name="<portlet:namespace />removeUserIds" type="hidden" value="" />

<div class="portlet-section-body results-row" style="border: 1px solid; padding: 5px;">
	<%= LanguageUtil.format(pageContext, "step-x-of-x", new String[] {"2", "2"}) %>

	<%= LanguageUtil.get(pageContext, "assign-" + (group.isOrganization() ? "organization" : "community") + "-roles-to-users") %>

	<i>Current</i> signifies current users associated with the <i><%= HtmlUtil.escape(role.getTitle(locale)) %></i> role. <i>Available</i> signifies all users associated with the <i><%= HtmlUtil.escape(groupName) %></i> <%= (group.isOrganization()) ? "organization" : "community" %>.
</div>

<br />

<liferay-ui:tabs
	names="users"
/>

<liferay-ui:tabs
	names="current,available"
	param="tabs1"
	url="<%= portletURL.toString() %>"
/>

<liferay-ui:search-container
	rowChecker="<%= new UserGroupRoleUserChecker(renderResponse, group, role) %>"
	searchContainer="<%= new UserSearch(renderRequest, portletURL) %>"
>
	<liferay-ui:search-form
		page="/html/portlet/enterprise_admin/user_search.jsp"
	/>

	<%
	UserSearchTerms searchTerms = (UserSearchTerms)searchContainer.getSearchTerms();

	LinkedHashMap userParams = new LinkedHashMap();

	if (group.isOrganization()) {
		userParams.put("usersOrgs", new Long(organization.getOrganizationId()));
	}
	else {
		userParams.put("usersGroups", new Long(group.getGroupId()));
	}

	if (tabs1.equals("current")) {
		userParams.put("userGroupRole", new Long[] {new Long(group.getGroupId()), new Long(roleId)});
	}
	%>

	<liferay-ui:search-container-results>
		<%@ include file="/html/portlet/enterprise_admin/user_search_results.jspf" %>
	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.User"
		escapedModel="<%= true %>"
		keyProperty="userId"
		modelVar="user2"
	>
		<liferay-ui:search-container-column-text
			name="name"
			property="fullName"
		/>

		<liferay-ui:search-container-column-text
			name="screen-name"
			property="screenName"
		/>
	</liferay-ui:search-container-row>

	<div class="separator"><!-- --></div>

	<input type="button" value="<liferay-ui:message key="update-associations" />" onClick="<portlet:namespace />updateUserGroupRoleUsers('<%= portletURL.toString() %>&<portlet:namespace />cur=<%= cur %>');" />

	<br /><br />

	<liferay-ui:search-iterator />
</liferay-ui:search-container>