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
Group liveGroup = (Group)request.getAttribute("site.liveGroup");

long groupId = scopeGroupId;

UnicodeProperties groupTypeSettings = null;

if (liveGroup != null) {
	groupId = liveGroup.getGroupId();

	groupTypeSettings = liveGroup.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new UnicodeProperties();
}

List<Role> defaultSiteRoles = new ArrayList();

long[] defaultSiteRoleIds = StringUtil.split(groupTypeSettings.getProperty("defaultSiteRoleIds"), 0L);

for (long defaultSiteRoleId : defaultSiteRoleIds) {
	Role role = RoleLocalServiceUtil.getRole(defaultSiteRoleId);

	defaultSiteRoles.add(role);
}

List<Team> defaultTeams = new ArrayList();

long[] defaultTeamIds = StringUtil.split(groupTypeSettings.getProperty("defaultTeamIds"), 0L);

for (long defaultTeamId : defaultTeamIds) {
	Team team = TeamLocalServiceUtil.getTeam(defaultTeamId);

	defaultTeams.add(team);
}
%>

<liferay-util:buffer var="removeRoleIcon">
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<p class="text-muted">
	<liferay-ui:message key="select-the-default-roles-and-teams-for-new-members" />
</p>

<h4 class="text-default"><liferay-ui:message key="site-roles" /> <liferay-ui:icon-help message="default-site-roles-assignment-help" /></h4>

<p class="text-muted <%= defaultSiteRoles.isEmpty() ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />siteRolesEmptyResultMessage">
	<%= StringUtil.lowerCase(LanguageUtil.get(request, "none")) %>
</p>

<liferay-ui:search-container
	headerNames="title,null"
	id="siteRolesSearchContainer"
	total="<%= defaultSiteRoles.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= defaultSiteRoles %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Role"
		keyProperty="roleId"
		modelVar="role"
	>
		<liferay-ui:search-container-column-text
			name="title"
			value="<%= HtmlUtil.escape(role.getTitle(locale)) %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="list-group-item-field"
		>
			<a class="modify-link" data-rowId="<%= role.getRoleId() %>" href="javascript:;"><%= removeRoleIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator markupView="lexicon" paginate="<%= false %>" />
</liferay-ui:search-container>

<div class="button-holder">
	<aui:button cssClass="btn-lg modify-link" id="selectSiteRoleLink" value="select" />
</div>

<h4 class="text-default"><liferay-ui:message key="teams" /> <liferay-ui:icon-help message="default-teams-assignment-help" /></h4>

<p class="text-muted <%= defaultTeams.isEmpty() ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />teamsEmptyResultMessage">
	<%= StringUtil.lowerCase(LanguageUtil.get(request, "none")) %>
</p>

<liferay-ui:search-container
	headerNames="title,null"
	id="teamsSearchContainer"
	total="<%= defaultTeams.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= defaultTeams %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Team"
		keyProperty="teamId"
		modelVar="team"
	>
		<liferay-ui:search-container-column-text
			name="title"
			value="<%= HtmlUtil.escape(team.getName()) %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="list-group-item-field"
		>
			<a class="modify-link" data-rowId="<%= team.getTeamId() %>" href="javascript:;"><%= removeRoleIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator markupView="lexicon" paginate="<%= false %>" />
</liferay-ui:search-container>

<aui:button-row>
	<aui:button cssClass="btn-lg modify-link" id="selectTeamLink" value="select" />
</aui:button-row>

<aui:script use="liferay-search-container,escape">
	var bindModifyLink = function(config) {
		var searchContainer = config.searchContainer;

		searchContainer.get('contentBox').delegate(
			'click',
			function(event) {
				var link = event.currentTarget;

				searchContainer.deleteRow(link.ancestor('tr'), link.getAttribute('data-rowId'));

				if (searchContainer.getSize() <= 0) {
					A.one(config.emptyResultMessageId).show();
				}
			},
			'.modify-link'
		);
	}

	var bindSelectLink = function(config) {
		var searchContainer = config.searchContainer;

		A.one(config.linkId).on(
			'click',
			function(event) {
				Liferay.Util.selectEntity(
					{
						dialog: {
							constrain: true,
							modal: true
						},
						id: config.id,
						title: config.title,
						uri: config.uri
					},
					function(event) {
						var rowColumns = [
							A.Escape.html(event[config.titleAttr]),
							'<a class="modify-link" data-rowId="' + event[config.idAttr] + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>'
						];

						searchContainer.addRow(rowColumns, event[config.idAttr]);

						searchContainer.updateDataStore();

						A.one(config.emptyResultMessageId).hide();
					}
				);
			}
		);
	}

	<%
	PortletURL selectSiteRoleURL = PortletProviderUtil.getPortletURL(request, Role.class.getName(), PortletProvider.Action.BROWSE);

	selectSiteRoleURL.setParameter("roleType", String.valueOf(RoleConstants.TYPE_SITE));
	selectSiteRoleURL.setParameter("step", "2");
	selectSiteRoleURL.setParameter("groupId", String.valueOf(groupId));
	selectSiteRoleURL.setParameter("eventName", liferayPortletResponse.getNamespace() + "selectSiteRole");
	selectSiteRoleURL.setWindowState(LiferayWindowState.POP_UP);
	%>

	var siteRolesConfig = {
		emptyResultMessageId: '#<portlet:namespace />siteRolesEmptyResultMessage',
		id: '<portlet:namespace />selectSiteRole',
		idAttr: 'roleid',
		linkId: '#<portlet:namespace />selectSiteRoleLink',
		searchContainer: Liferay.SearchContainer.get('<portlet:namespace />siteRolesSearchContainer'),
		title: '<liferay-ui:message arguments="site-role" key="select-x" />',
		titleAttr: 'roletitle',
		uri: '<%= selectSiteRoleURL.toString() %>'
	};

	bindModifyLink(siteRolesConfig);
	bindSelectLink(siteRolesConfig);

	<%
	PortletURL selectTeamURL = PortletProviderUtil.getPortletURL(request, Team.class.getName(), PortletProvider.Action.BROWSE);

	selectTeamURL.setParameter("groupId", String.valueOf(groupId));
	selectTeamURL.setParameter("eventName", liferayPortletResponse.getNamespace() + "selectTeam");
	selectTeamURL.setWindowState(LiferayWindowState.POP_UP);
	%>

	var teamsConfig = {
		emptyResultMessageId: '#<portlet:namespace />teamsEmptyResultMessage',
		id: '<portlet:namespace />selectTeam',
		idAttr: 'teamid',
		linkId: '#<portlet:namespace />selectTeamLink',
		searchContainer: Liferay.SearchContainer.get('<portlet:namespace />teamsSearchContainer'),
		title: '<liferay-ui:message arguments="team" key="select-x" />',
		titleAttr: 'teamname',
		uri: '<%= selectTeamURL.toString() %>'
	};

	bindModifyLink(teamsConfig);
	bindSelectLink(teamsConfig);
</aui:script>