<%
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
%>

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "users");
String tabs2 = ParamUtil.getString(request, "tabs2", "current");

String cur = ParamUtil.getString(request, "cur");

String redirect = ParamUtil.getString(request, "redirect");

Team team = (Team)request.getAttribute(WebKeys.TEAM);

User selUser = PortalUtil.getSelectedUser(request, false);

Group group = GroupServiceUtil.getGroup(team.getGroupId());

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/communities/edit_team_assignments");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("teamId", String.valueOf(team.getTeamId()));

request.setAttribute("edit_team_assignments.jsp-tabs2", tabs2);

request.setAttribute("edit_team_assignments.jsp-cur", cur);

request.setAttribute("edit_team_assignments.jsp-team", team);

request.setAttribute("edit_team_assignments.jsp-portletURL", portletURL);
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="teamId" type="hidden" value="<%= String.valueOf(team.getTeamId()) %>" />

	<div>
		<liferay-ui:message key="manage-teams-for-community" />: <%= group.getDescriptiveName() %><br />
		<liferay-ui:message key="edit-assignments-for-team" />: <%= team.getName() %>
	</div>

	<h3><liferay-ui:message key="teams" /></h3>

	<liferay-ui:tabs
		names="users"
		param="tabs1"
		url="<%= portletURL.toString() %>"
		backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
	/>

	<liferay-util:include page="/html/portlet/communities/edit_team_assignments_users.jsp" />
</aui:form>

<aui:script>
	function <portlet:namespace />updateTeamUsers(assignmentsRedirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "team_users";
		document.<portlet:namespace />fm.<portlet:namespace />assignmentsRedirect.value = assignmentsRedirect;
		document.<portlet:namespace />fm.<portlet:namespace />addUserIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		document.<portlet:namespace />fm.<portlet:namespace />removeUserIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/communities/edit_team_assignments" /></portlet:actionURL>");
	}
</aui:script>