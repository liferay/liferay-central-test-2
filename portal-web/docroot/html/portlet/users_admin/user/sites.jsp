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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
User selUser = (User)request.getAttribute("user.selUser");
List<Group> groups = (List<Group>)request.getAttribute("user.groups");
List<Group> inheritedSites = (List<Group>)request.getAttribute("user.inheritedSites");

currentURLObj.setParameter("historyKey", renderResponse.getNamespace() + "sites");
%>

<liferay-ui:error-marker key="errorSection" value="sites" />

<liferay-ui:membership-policy-error />

<liferay-util:buffer var="removeGroupIcon">
	<liferay-ui:icon
		iconCssClass="icon-remove"
		label="<%= true %>"
		message="remove"
	/>
</liferay-util:buffer>

<h3><liferay-ui:message key="sites" /></h3>

<liferay-ui:search-container
	curParam="sitesCur"
	headerNames="name,roles,null"
	iteratorURL="<%= currentURLObj %>"
	total="<%= groups.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= groups.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Group"
		escapedModel="<%= true %>"
		keyProperty="groupId"
		modelVar="group"
		rowIdProperty="friendlyURL"
	>
		<liferay-ui:search-container-column-text
			name="name"
			value="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>"
		/>

		<%
		List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(selUser.getUserId(), group.getGroupId());
		%>

		<liferay-ui:search-container-column-text
			name="roles"
			value="<%= ListUtil.toString(userGroupRoles, UsersAdmin.USER_GROUP_ROLE_TITLE_ACCESSOR, StringPool.COMMA_AND_SPACE) %>"
		/>

		<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) && !SiteMembershipPolicyUtil.isMembershipRequired(selUser.getUserId(), group.getGroupId()) && !SiteMembershipPolicyUtil.isMembershipProtected(permissionChecker, selUser.getUserId(), group.getGroupId()) %>">
			<liferay-ui:search-container-column-text>
				<c:if test="<%= group.isManualMembership() %>">
					<a class="modify-link" data-rowId="<%= group.getGroupId() %>" href="javascript:;"><%= removeGroupIcon %></a>
				</c:if>
			</liferay-ui:search-container-column-text>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
	<liferay-ui:icon
		cssClass="modify-link"
		iconCssClass="icon-search"
		id="selectSiteLink"
		label="<%= true %>"
		linkCssClass="btn"
		message="select"
		url="javascript:;"
	/>

	<aui:script use="liferay-search-container">
		var Util = Liferay.Util;

		A.one('#<portlet:namespace />selectSiteLink').on(
			'click',
			function(event) {
				Util.selectEntity(
					{
						dialog: {
							constrain: true,
							modal: true,
							width: 600
						},
						id: '<portlet:namespace />selectGroup',
						title: '<liferay-ui:message arguments="site" key="select-x" />',

						<portlet:renderURL var="groupSelectorURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
							<portlet:param name="struts_action" value="/users_admin/select_site" />
							<portlet:param name="p_u_i_d" value="<%= String.valueOf(selUser.getUserId()) %>" />
						</portlet:renderURL>

						uri: '<%= groupSelectorURL.toString() %>'
					},
					function(event) {
						var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />groupsSearchContainer');

						var rowColumns = [];

						rowColumns.push(event.groupdescriptivename);
						rowColumns.push('');
						rowColumns.push('<a class="modify-link" data-rowId="' + event.groupid + '" href="javascript:;"><%= UnicodeFormatter.toString(removeGroupIcon) %></a>');

						searchContainer.addRow(rowColumns, event.groupid);
						searchContainer.updateDataStore();
					}
				);
			}
		);

		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />groupsSearchContainer');

		var searchContainerContentBox = searchContainer.get('contentBox');

		searchContainerContentBox.delegate(
			'click',
			function(event) {
				var link = event.currentTarget;

				var tr = link.ancestor('tr');
				var rowId = link.attr('data-rowId');

				var selectGroup = Util.getWindow('<portlet:namespace />selectGroup');

				if (selectGroup) {
					var selectButton = selectGroup.iframe.node.get('contentWindow.document').one('.selector-button[data-groupid="' + rowId + '"]');

					Util.toggleDisabled(selectButton, false);
				}

				searchContainer.deleteRow(tr, rowId);
			},
			'.modify-link'
		);

		Liferay.on(
			'<portlet:namespace />enableRemovedSites',
			function(event) {
				event.selectors.each(
					function(item, index, collection) {
						var modifyLink = searchContainerContentBox.one('.modify-link[data-rowid="' + item.attr('data-groupid') + '"]');

						if (!modifyLink) {
							Util.toggleDisabled(item, false);
						}
					}
				);
			}
		);
	</aui:script>
</c:if>

<h3><liferay-ui:message key="inherited-sites" /></h3>

<c:if test="<%= inheritedSites.isEmpty() %>">
	<liferay-ui:message key="this-user-does-not-have-any-inherited-sites" />
</c:if>

<liferay-ui:search-container
	curParam="inheritedSitesCur"
	headerNames="name,roles"
	iteratorURL="<%= currentURLObj %>"
	total="<%= inheritedSites.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= inheritedSites.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Group"
		escapedModel="<%= true %>"
		keyProperty="groupId"
		modelVar="inheritedSite"
		rowIdProperty="friendlyURL"
	>
		<liferay-ui:search-container-column-text
			name="name"
			value="<%= HtmlUtil.escape(inheritedSite.getDescriptiveName(locale)) %>"
		/>

		<%
		List<Role> inheritedRoles = RoleLocalServiceUtil.getUserGroupGroupRoles(selUser.getUserId(), inheritedSite.getGroupId());
		%>

		<liferay-ui:search-container-column-text
			name="roles"
			value="<%= ListUtil.toString(inheritedRoles, Role.TITLE_ACCESSOR, StringPool.COMMA_AND_SPACE) %>"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>