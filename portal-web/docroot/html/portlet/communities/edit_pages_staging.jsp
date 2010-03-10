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
Group liveGroup = (Group)request.getAttribute("edit_pages.jsp-liveGroup");
Group stagingGroup = (Group)request.getAttribute("edit_pages.jsp-stagingGroup");
long liveGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();

boolean workflowEnabled = ((Boolean)request.getAttribute("edit_pages.jsp-workflowEnabled")).booleanValue();
int workflowStages = ((Integer)request.getAttribute("edit_pages.jsp-workflowStages")).intValue();
String[] workflowRoleNames = (String[])request.getAttribute("edit_pages.jsp-workflowRoleNames");
%>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.MANAGE_STAGING) %>">
	<table class="lfr-table">
	<tr>
		<td>
			<liferay-ui:message key="activate-staging" />
		</td>
		<td>
			<input <%= (stagingGroup != null) ? "checked" : "" %> <%= (stagingGroup != null) && (stagingGroup.getGroupId() == themeDisplay.getDoAsGroupId()) ? "disabled" : "" %> name="<portlet:namespace />stagingEnabled" type="checkbox" onClick="<portlet:namespace />updateStaging();">
		</td>
	</tr>

	<c:if test="<%= stagingGroup != null %>">
		<tr>
			<td>
				<liferay-ui:message key="activate-workflow" />
			</td>
			<td>
				<input <%= workflowEnabled ? "checked" : "" %> name="<portlet:namespace />workflowEnabled" type="checkbox" onClick="<portlet:namespace />updateWorkflow();">
			</td>
		</tr>
	</c:if>

	</table>

	<c:if test="<%= workflowEnabled %>">
		<br />

		<div class="portlet-msg-info">
			<c:choose>
				<c:when test="<%= liveGroup.isCommunity() %>">
					<liferay-ui:message key="stage-community-permissions-reference-help" />
				</c:when>
				<c:otherwise>
					<liferay-ui:message key="stage-organization-permissions-reference-help" />
				</c:otherwise>
			</c:choose>
		</div>

		<fieldset>
			<legend><liferay-ui:message key="workflow" /></legend>

			<table class="lfr-table">
			<tr>
				<td>
					<liferay-ui:message key="number-of-stages" />
				</td>
				<td>
					<select name="<portlet:namespace />workflowStages" onChange="<portlet:namespace />changeWorkflowStages();">

						<%
						for (int i = 3; i <= 6; i++) {
						%>

							<option <%= (i == (workflowStages + 1)) ? "selected" : "" %> value="<%= (i - 1) %>"><%= i %></option>

						<%
						}
						%>

					</select>
				</td>
			</tr>
			</table>

			<br />

			<table class="lfr-table">
			<tr>
				<td>
					<%= LanguageUtil.format(pageContext, "stage-x-role", "1") %>
				</td>
				<td>
					<liferay-ui:message key="content-creators" />

					<liferay-ui:icon-help message="stage-1-role-help" />
				</td>
			</tr>

			<%
			int roleType = liveGroup.isCommunity() ? RoleConstants.TYPE_COMMUNITY : RoleConstants.TYPE_ORGANIZATION;

			List<Role> workflowRoles = RoleLocalServiceUtil.search(company.getCompanyId(), null, null, new Integer[] {roleType}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			for (int i = 1; i <= workflowStages; i++) {
			%>

				<tr>
					<td>
						<%= LanguageUtil.format(pageContext, "stage-x-role", String.valueOf(i + 1)) %>
					</td>
					<td>
						<select name="<portlet:namespace />workflowRoleName_<%= i %>">

							<%
							for (Role workflowRole : workflowRoles) {
							%>

								<option <%= (((i - 1) < workflowRoleNames.length) && workflowRoleNames[i - 1].equals(workflowRole.getName())) ? "selected" : "" %> value="<%= HtmlUtil.escape(workflowRole.getName()) %>"><%= HtmlUtil.escape(workflowRole.getTitle(locale)) %></option>

							<%
							}
							%>

						</select>

						<c:choose>
							<c:when test="<%= i == 1 %>">
								<liferay-ui:icon-help message="stage-2-role-help" />
							</c:when>
							<c:when test="<%= i == workflowStages %>">
								<liferay-ui:icon-help message="stage-last-role-help" />
							</c:when>
							<c:otherwise>
								<liferay-ui:icon-help message="stage-review-role-help" />
							</c:otherwise>
						</c:choose>
					</td>
				</tr>

			<%
			}
			%>

			</table>
		</fieldset>

		<br />

		<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveWorkflowStages();" />
	</c:if>
</c:if>