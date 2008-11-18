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
String tabs2 = ParamUtil.getString(request, "tabs2", "users");
String tabs3 = ParamUtil.getString(request, "tabs3", "current");

String cur = ParamUtil.getString(request, "cur");

String redirect = ParamUtil.getString(request, "redirect");

Role role = (Role)request.getAttribute(WebKeys.ROLE);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/enterprise_admin/edit_role_assignments");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("tabs3", tabs3);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("roleId", String.valueOf(role.getRoleId()));
%>

<script type="text/javascript">
	function <portlet:namespace />updateRoleGroups(assignmentsRedirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "role_groups";
		document.<portlet:namespace />fm.<portlet:namespace />assignmentsRedirect.value = assignmentsRedirect;
		document.<portlet:namespace />fm.<portlet:namespace />addGroupIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		document.<portlet:namespace />fm.<portlet:namespace />removeGroupIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateRoleUsers(assignmentsRedirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "role_users";
		document.<portlet:namespace />fm.<portlet:namespace />assignmentsRedirect.value = assignmentsRedirect;
		document.<portlet:namespace />fm.<portlet:namespace />addUserIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		document.<portlet:namespace />fm.<portlet:namespace />removeUserIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_role_assignments" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />tabs1" type="hidden" value="<%= HtmlUtil.escape(tabs1) %>" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= HtmlUtil.escape(tabs2) %>" />
<input name="<portlet:namespace />tabs3" type="hidden" value="<%= HtmlUtil.escape(tabs3) %>" />
<input name="<portlet:namespace />assignmentsRedirect" type="hidden" value="" />
<input name="<portlet:namespace />roleId" type="hidden" value="<%= role.getRoleId() %>" />

<liferay-ui:message key="edit-assignments-for-role" />: <%= role.getTitle(locale) %>

<br /><br />

<liferay-ui:tabs
	names="users,communities,organizations,user-groups"
	param="tabs2"
	url="<%= portletURL.toString() %>"
	backURL="<%= redirect %>"
/>

<c:choose>
	<c:when test='<%= tabs2.equals("users") %>'>
		<input name="<portlet:namespace />addUserIds" type="hidden" value="" />
		<input name="<portlet:namespace />removeUserIds" type="hidden" value="" />

		<liferay-ui:tabs
			names="current,available"
			param="tabs3"
			url="<%= portletURL.toString() %>"
		/>

		<liferay-ui:search-container
			rowChecker="<%= new UserRoleChecker(renderResponse, role) %>"
			searchContainer="<%= new UserSearch(renderRequest, portletURL) %>"
		>
			<liferay-ui:search-form
				page="/html/portlet/enterprise_admin/user_search.jsp"
			/>

			<%
			UserSearchTerms searchTerms = (UserSearchTerms)searchContainer.getSearchTerms();

			LinkedHashMap userParams = new LinkedHashMap();

			if (tabs3.equals("current")) {
				userParams.put("usersRoles", new Long(role.getRoleId()));
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

			<input type="button" value="<liferay-ui:message key="update-associations" />" onClick="<portlet:namespace />updateRoleUsers('<%= portletURL.toString() %>&<portlet:namespace />cur=<%= cur %>');" />

			<br /><br />

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</c:when>
	<c:when test='<%= tabs2.equals("communities") %>'>
		<input name="<portlet:namespace />addGroupIds" type="hidden" value="" />
		<input name="<portlet:namespace />removeGroupIds" type="hidden" value="" />

		<liferay-ui:tabs
			names="current,available"
			param="tabs3"
			url="<%= portletURL.toString() %>"
		/>

		<%
		GroupSearch searchContainer = new GroupSearch(renderRequest, portletURL);

		searchContainer.setRowChecker(new GroupRoleChecker(renderResponse, role));
		%>

		<liferay-ui:search-form
			page="/html/portlet/enterprise_admin/group_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<%
		GroupSearchTerms searchTerms = (GroupSearchTerms)searchContainer.getSearchTerms();

		LinkedHashMap groupParams = new LinkedHashMap();

		if (tabs3.equals("current")) {
			groupParams.put("groupsRoles", new Long(role.getRoleId()));
		}

		int total = GroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), groupParams);

		searchContainer.setTotal(total);

		List results = GroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), groupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

		searchContainer.setResults(results);
		%>

		<div class="separator"><!-- --></div>

		<input type="button" value="<liferay-ui:message key="update-associations" />" onClick="<portlet:namespace />updateRoleGroups('<%= portletURL.toString() %>&<portlet:namespace />cur=<%= cur %>');" />

		<br /><br />

		<%
		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			Group group = (Group)results.get(i);

			ResultRow row = new ResultRow(group, group.getGroupId(), i);

			// Name

			row.addText(group.getName());

			// Type

			row.addText(LanguageUtil.get(pageContext, group.getTypeLabel()));

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</c:when>
	<c:when test='<%= tabs2.equals("organizations") %>'>
		<input name="<portlet:namespace />addGroupIds" type="hidden" value="" />
		<input name="<portlet:namespace />removeGroupIds" type="hidden" value="" />

		<liferay-ui:tabs
			names="current,available"
			param="tabs3"
			url="<%= portletURL.toString() %>"
		/>

		<liferay-ui:search-container
			rowChecker="<%= new OrganizationRoleChecker(renderResponse, role) %>"
			searchContainer="<%= new OrganizationSearch(renderRequest, portletURL) %>"
		>
			<liferay-ui:search-form
				page="/html/portlet/enterprise_admin/organization_search.jsp"
			/>

			<%
			OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)searchContainer.getSearchTerms();

			long parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;

			LinkedHashMap organizationParams = new LinkedHashMap();

			if (tabs3.equals("current")) {
				organizationParams.put("organizationsRoles", new Long(role.getRoleId()));
			}
			%>

			<liferay-ui:search-container-results>
				<%@ include file="/html/portlet/enterprise_admin/organization_search_results.jspf" %>
			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.Organization"
				escapedModel="<%= true %>"
				keyProperty="organizationId"
				modelVar="organization"
			>
				<liferay-ui:search-container-column-text
					name="name"
					orderable="<%= true %>"
					property="name"
				/>

				<liferay-ui:search-container-column-text
					buffer="buffer"
					name="parent-organization"
				>

					<%
					if (organization.getParentOrganizationId() > 0) {
						try {
							Organization parentOrganization = OrganizationLocalServiceUtil.getOrganization(organization.getParentOrganizationId());

							buffer.append(parentOrganization.getName());
						}
						catch (Exception e) {
						}
					}
					%>

				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="type"
					orderable="<%= true %>"
					value="<%= LanguageUtil.get(pageContext, HtmlUtil.unescape(organization.getType())) %>"
				/>

				<liferay-ui:search-container-column-text
					name="city"
					value="<%= organization.getAddress().getCity() %>"
				/>

				<liferay-ui:search-container-column-text
					name="region"
				>
					<liferay-ui:write bean="<%= organization %>" property="region" />
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="country"
				>
					<liferay-ui:write bean="<%= organization %>" property="country" />
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<div class="separator"><!-- --></div>

			<input type="button" value="<liferay-ui:message key="update-associations" />" onClick="<portlet:namespace />updateRoleGroups('<%= portletURL.toString() %>&<portlet:namespace />cur=<%= cur %>');" />

			<br /><br />

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</c:when>
	<c:when test='<%= tabs2.equals("user-groups") %>'>
		<input name="<portlet:namespace />addGroupIds" type="hidden" value="" />
		<input name="<portlet:namespace />removeGroupIds" type="hidden" value="" />

		<liferay-ui:tabs
			names="current,available"
			param="tabs3"
			url="<%= portletURL.toString() %>"
		/>

		<liferay-ui:search-container
			rowChecker="<%= new UserGroupRoleChecker(renderResponse, role) %>"
			searchContainer="<%= new UserGroupSearch(renderRequest, portletURL) %>"
		>
			<liferay-ui:search-form
				page="/html/portlet/enterprise_admin/user_group_search.jsp"
			/>

			<%
			UserGroupSearchTerms searchTerms = (UserGroupSearchTerms)searchContainer.getSearchTerms();

			LinkedHashMap userGroupParams = new LinkedHashMap();

			if (tabs3.equals("current")) {
				List userGroupsRoles = new ArrayList();

				userGroupParams.put("userGroupsRoles", new Long(role.getRoleId()));
			}
			%>

			<liferay-ui:search-container-results
				results="<%= UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), userGroupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
				total="<%= UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), userGroupParams) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.UserGroup"
				escapedModel="<%= true %>"
				keyProperty="group.userGroupId"
				modelVar="userGroup"
			>
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
			</liferay-ui:search-container-row>

			<div class="separator"><!-- --></div>

			<input type="button" value="<liferay-ui:message key="update-associations" />" onClick="<portlet:namespace />updateRoleGroups('<%= portletURL.toString() %>&<portlet:namespace />cur=<%= cur %>');" />

			<br /><br />

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</c:when>
</c:choose>

</form>