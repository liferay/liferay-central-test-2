<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
String tabs1 = ParamUtil.getString(request, "tabs1", "current");

String cur = ParamUtil.getString(request, "cur");

String redirect = ParamUtil.getString(request, "redirect");

Group group = (Group)request.getAttribute(WebKeys.GROUP);

Role role = (Role)request.getAttribute(WebKeys.ROLE);

String roleId = BeanParamUtil.getString(role, request, "roleId");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/communities/edit_user_roles");
//portletURL.setParameter("redirect", redirect);
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("groupId", String.valueOf(group.getGroupId()));
portletURL.setParameter("roleId", roleId);

// Breadcrumbs

PortletURL breadcrumbsURL = renderResponse.createRenderURL();

breadcrumbsURL.setWindowState(WindowState.MAXIMIZED);

breadcrumbsURL.setParameter("struts_action", "/communities/view");
breadcrumbsURL.setParameter("redirect", redirect);
breadcrumbsURL.setParameter("tabs1", tabs1);
breadcrumbsURL.setParameter("groupId", String.valueOf(group.getGroupId()));

String breadcrumbs = "<a href=\"" + breadcrumbsURL.toString() + "\">" + LanguageUtil.get(pageContext, "communities") + "</a> &raquo; ";

breadcrumbsURL.setParameter("struts_action", "/communities/edit_user_roles");

breadcrumbs += "<a href=\"" + breadcrumbsURL.toString() + "\">" + group.getName() + "</a>";

if (role != null) {
	breadcrumbsURL.setParameter("roleId", roleId);

	breadcrumbs += " &raquo; <a href=\"" + breadcrumbsURL.toString() + "\">" + role.getName() + "</a>";
}
%>

<script type="text/javascript">
	function <portlet:namespace />updateUserGroupRoleUsers(redirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "user_group_role_users";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
		document.<portlet:namespace />fm.<portlet:namespace />addUserIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		document.<portlet:namespace />fm.<portlet:namespace />removeUserIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/communities/edit_user_roles" /></portlet:actionURL>");
	}
</script>

<form action="<%= portletURL.toString() %>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />tabs1" type="hidden" value="<%= tabs1 %>">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>">
<input name="<portlet:namespace />groupId" type="hidden" value="<%= String.valueOf(group.getGroupId()) %>">
<input name="<portlet:namespace />roleId" type="hidden" value="<%= roleId %>">

Assign Community roles to users.

<br><br>

<c:choose>
	<c:when test="<%= role == null %>">
		<div class="portlet-section-body" style="border: 1px solid <%= colorScheme.getPortletFontDim() %>; padding: 5px;">
			Step 1 of 2: Choose a role.
		</div>

		<br>

		<div class="breadcrumbs">
			<%= breadcrumbs %>
		</div>

		<liferay-ui:tabs
			names="roles"
		/>

		<%
		RoleSearch searchContainer = new RoleSearch(renderRequest, portletURL);

		List headerNames = searchContainer.getHeaderNames();

		headerNames.add("type");
		%>

		<liferay-ui:search-form
			page="/html/portlet/enterprise_admin/role_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<c:if test="<%= renderRequest.getWindowState().equals(WindowState.MAXIMIZED) %>">

			<%
			RoleSearchTerms searchTerms = (RoleSearchTerms)searchContainer.getSearchTerms();

			int total = RoleLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), new Integer(RoleImpl.TYPE_COMMUNITY));

			searchContainer.setTotal(total);

			List results = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), new Integer(RoleImpl.TYPE_COMMUNITY), searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);
			%>

			<br><div class="separator"></div><br>

			<%
			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				Role curRole = (Role)results.get(i);

				ResultRow row = new ResultRow(curRole, curRole.getPrimaryKey().toString(), i);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setWindowState(WindowState.MAXIMIZED);

				rowURL.setParameter("struts_action", "/communities/edit_user_roles");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("groupId", String.valueOf(group.getGroupId()));
				rowURL.setParameter("roleId", curRole.getRoleId());

				// Name

				row.addText(curRole.getName(), rowURL);

				// Type

				row.addText(LanguageUtil.get(pageContext, (curRole.getType() == RoleImpl.TYPE_REGULAR) ? "regular" : "community"), rowURL);

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
		</c:if>
	</c:when>
	<c:otherwise>
		<input name="<portlet:namespace />addUserIds" type="hidden" value="">
		<input name="<portlet:namespace />removeUserIds" type="hidden" value="">

		<div class="portlet-section-body" style="border: 1px solid <%= colorScheme.getPortletFontDim() %>; padding: 5px;">
			Step 2 of 2: Assign Community roles to users. <i>Current</i> signifies current users associated with the <i><%= role.getName() %></i> role. <i>Available</i> signifies all users associated with the <i><%= group.getName() %></i> community.
		</div>

		<br>

		<div class="breadcrumbs">
			<%= breadcrumbs %>
		</div>

		<liferay-ui:tabs
			names="users"
		/>

		<liferay-ui:tabs
			names="current,available"
			param="tabs1"
			url="<%= portletURL.toString() %>"
		/>

		<%
		UserSearch searchContainer = new UserSearch(renderRequest, portletURL);

		searchContainer.setRowChecker(new UserGroupRoleUserChecker(renderResponse, group, role));
		%>

		<liferay-ui:search-form
			page="/html/portlet/enterprise_admin/user_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<%
		UserSearchTerms searchTerms = (UserSearchTerms)searchContainer.getSearchTerms();

		LinkedHashMap userParams = new LinkedHashMap();

		userParams.put("usersGroups", new Long(group.getGroupId()));

		if (tabs1.equals("current")) {
			userParams.put("userGroupRole", new String[] {String.valueOf(group.getGroupId()), roleId});
		}

		int total = UserLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getFirstName(), searchTerms.getMiddleName(), searchTerms.getLastName(), searchTerms.getScreenName(), searchTerms.getEmailAddress(), searchTerms.isActive(), userParams, searchTerms.isAndOperator());

		searchContainer.setTotal(total);

		List results = UserLocalServiceUtil.search(company.getCompanyId(), searchTerms.getFirstName(), searchTerms.getMiddleName(), searchTerms.getLastName(), searchTerms.getScreenName(), searchTerms.getEmailAddress(), searchTerms.isActive(), userParams, searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd(), new ContactLastNameComparator(true));

		searchContainer.setResults(results);
		%>

		<br><div class="separator"></div><br>

		<input type="button" value='<%= LanguageUtil.get(pageContext, "update-associations") %>' onClick="<portlet:namespace />updateUserGroupRoleUsers('<%= portletURL.toString() %>&<portlet:namespace />cur=<%= cur %>');">

		<br><br>

		<%
		List headerNames = new ArrayList();

		headerNames.add("name");
		headerNames.add("screen-name");
		headerNames.add("email-address");

		searchContainer.setHeaderNames(headerNames);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			User user2 = (User)results.get(i);

			ResultRow row = new ResultRow(user2, user2.getPrimaryKey(), i);

			// Name, screen name, and email address

			row.addText(user2.getFullName());
			row.addText(user2.getScreenName());
			row.addText(user2.getEmailAddress());

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</c:otherwise>
</c:choose>

</form>