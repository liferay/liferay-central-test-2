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

<%@ include file="/init.jsp" %>

<%
Group group = (Group)request.getAttribute("edit_site_assignments.jsp-group");

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_site_assignments.jsp-portletURL");

PortletURL viewUserGroupsURL = renderResponse.createRenderURL();

viewUserGroupsURL.setParameter("mvcPath", "/view.jsp");
viewUserGroupsURL.setParameter("tabs1", "user-groups");
viewUserGroupsURL.setParameter("tabs2", "current");
viewUserGroupsURL.setParameter("redirect", currentURL);
viewUserGroupsURL.setParameter("groupId", String.valueOf(group.getGroupId()));

UserGroupSearch userGroupSearch = new UserGroupSearch(renderRequest, PortletURLUtil.clone(viewUserGroupsURL, renderResponse));

userGroupSearch.setEmptyResultsMessage("no-user-group-was-found-that-is-a-member-of-this-site");

RowChecker rowChecker = new EmptyOnClickRowChecker(renderResponse);

UserGroupDisplayTerms searchTerms = (UserGroupDisplayTerms)userGroupSearch.getSearchTerms();

LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<String, Object>();

userGroupParams.put("userGroupsGroups", Long.valueOf(group.getGroupId()));

int userGroupsCount = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams);

userGroupSearch.setTotal(userGroupsCount);

List<UserGroup> userGroups = UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams, userGroupSearch.getStart(), userGroupSearch.getEnd(), userGroupSearch.getOrderByComparator());

userGroupSearch.setResults(userGroups);
%>

<c:if test="<%= userGroupsCount > 0 %>">
	<liferay-frontend:management-bar
		checkBoxContainerId="userGroupsSearchContainer"
		includeCheckBox="<%= true %>"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
				portletURL="<%= PortletURLUtil.clone(viewUserGroupsURL, renderResponse) %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= PortletURLUtil.clone(viewUserGroupsURL, renderResponse) %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= orderByCol %>"
				orderByType="<%= orderByType %>"
				orderColumns='<%= new String[] {"name", "description"} %>'
				portletURL="<%= PortletURLUtil.clone(viewUserGroupsURL, renderResponse) %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button href="javascript:;" iconCssClass="icon-trash" id="deleteSelectedUserGroups" />
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>
</c:if>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="user-groups" />
	<aui:input name="tabs2" type="hidden" value="current" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="groupId" type="hidden" value="<%= String.valueOf(group.getGroupId()) %>" />
	<aui:input name="addUserGroupIds" type="hidden" />
	<aui:input name="removeUserGroupIds" type="hidden" />

	<liferay-ui:search-container
		id="userGroups"
		rowChecker="<%= rowChecker %>"
		searchContainer="<%= userGroupSearch %>"
	>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.UserGroup"
			cssClass="selectable"
			escapedModel="<%= true %>"
			keyProperty="userGroupId"
			modelVar="userGroup"
		>

			<%
			boolean selectUserGroup = false;
			%>

			<%@ include file="/user_group_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">

	<%
	viewUserGroupsURL.setParameter("tabs2", "available");
	%>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "assign-user-groups") %>' url="<%= viewUserGroupsURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script sandbox="<%= true %>">
	var Util = Liferay.Util;

	var form = $(document.<portlet:namespace />fm);

	$('#<portlet:namespace />deleteSelectedUserGroups').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				<portlet:actionURL name="editGroupUserGroups" var="deleteUserGroupsURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:actionURL>

				form.fm('removeUserGroupIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

				submitForm(form, '<%= deleteUserGroupsURL %>');
			}
		}
	);
</aui:script>