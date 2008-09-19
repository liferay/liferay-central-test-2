<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String exportProgressId = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);

PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");

List manageableOrganizations = null;

Long[] manageableOrganizationIds = null;

if (filterManageableOrganizations) {
	manageableOrganizations = OrganizationLocalServiceUtil.getManageableOrganizations(user.getUserId());

	manageableOrganizationIds = EnterpriseAdminUtil.getOrganizationIds(manageableOrganizations);
}
%>

<input name="<portlet:namespace />deleteUserIds" type="hidden" value="" />

<liferay-ui:error exception="<%= RequiredUserException.class %>" message="you-cannot-delete-or-deactivate-yourself" />

<%
UserSearch userSearchContainer = new UserSearch(renderRequest, portletURL);

portletURL.setParameter(userSearchContainer.getCurParam(), String.valueOf(userSearchContainer.getCurValue()));
%>

<input name="<portlet:namespace />usersRedirect" type="hidden" value="<%= portletURL.toString() %>" />

<liferay-ui:search-container
	searchContainer="<%= userSearchContainer %>"
	rowChecker="<%= new RowChecker(renderResponse) %>"
>

	<liferay-ui:search-form
		page="/html/portlet/enterprise_admin/user_search.jsp"
		searchContainer="<%= searchContainer %>"
		showAddButton="<%= true %>"
	/>

	<%
	UserSearchTerms searchTerms = (UserSearchTerms)searchContainer.getSearchTerms();

	long organizationId = searchTerms.getOrganizationId();
	long roleId = searchTerms.getRoleId();
	long userGroupId = searchTerms.getUserGroupId();

	LinkedHashMap userParams = new LinkedHashMap();

	if (organizationId > 0) {
		userParams.put("usersOrgs", new Long(organizationId));
	}
	else {
		if (filterManageableOrganizations) {
			userParams.put("usersOrgs", manageableOrganizationIds);
		}
	}

	if (roleId > 0) {
		userParams.put("usersRoles", new Long(roleId));
	}

	if (userGroupId > 0) {
		userParams.put("usersUserGroups", new Long(userGroupId));
	}
	%>

	<%@ include file="/html/portlet/enterprise_admin/user_search_results.jspf" %>

	<%@ include file="/html/portlet/enterprise_admin/user_search_details.jspf" %>

	<liferay-ui:search-container-results
		total="<%= total %>"
		results="<%= results %>"
	/>

	<br /><br />

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.User"
		keyProperty="userId"
		modelVar="user2"
	>
		<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" varImpl="rowURL">
			<portlet:param name="struts_action" value="/enterprise_admin/edit_user" />
			<portlet:param name="redirect" value="<%= searchContainer.getIteratorURL().toString() %>" />
			<portlet:param name="p_u_i_d" value="<%= String.valueOf(user2.getUserId()) %>" />
		</liferay-portlet:renderURL>

		<%@ include file="/html/portlet/enterprise_admin/templates/user_list_columns.jspf" %>

		<liferay-ui:search-container-column-jsp
			align="right"
			path="/html/portlet/enterprise_admin/user_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

</liferay-ui:search-container>