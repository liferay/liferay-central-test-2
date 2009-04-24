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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
Role role = (Role)request.getAttribute("edit_role_permissions.jsp-role");

String portletResource = (String)request.getAttribute("edit_role_permissions.jsp-portletResource");

String curPortletResource = (String)request.getAttribute("edit_role_permissions.jsp-curPortletResource");
String curModelResource = (String)request.getAttribute("edit_role_permissions.jsp-curModelResource");
String curModelResourceName = (String)request.getAttribute("edit_role_permissions.jsp-curModelResourceName");

List curActions = ResourceActionsUtil.getResourceActions(curPortletResource, curModelResource);

curActions = ListUtil.sort(curActions, new ActionComparator(company.getCompanyId(), locale));

List guestUnsupportedActions = ResourceActionsUtil.getResourceGuestUnsupportedActions(curPortletResource, curModelResource);
%>

<table class="lfr-table">
<tr>
	<th>
		<liferay-ui:message key="action" />
	</th>
	<th>
		<c:choose>
			<c:when test="<%= role.getType() == RoleConstants.TYPE_REGULAR %>">
				<liferay-ui:message key="scope" />
			</c:when>
		</c:choose>
	</th>
	<th></th>
</tr>

<%
for (int i = 0; i < curActions.size(); i++) {
	String actionId = (String)curActions.get(i);

	String curResource = null;

	if (Validator.isNull(curModelResource)) {
		curResource = curPortletResource;
	}
	else {
		curResource = curModelResource;
	}

	String target = curResource + actionId;

	int scopeParam = ParamUtil.getInteger(renderRequest, "scope" + target);

	boolean hasCompanyScope = false;
	boolean hasGroupTemplateScope = false;
	boolean hasGroupScope = false;

	if (scopeParam > 0) {
		hasCompanyScope = (scopeParam == ResourceConstants.SCOPE_COMPANY);
		hasGroupTemplateScope = (scopeParam == ResourceConstants.SCOPE_GROUP_TEMPLATE);
		hasGroupScope = (scopeParam == ResourceConstants.SCOPE_GROUP);
	}
	else {
		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			hasCompanyScope = (role.getType() == RoleConstants.TYPE_REGULAR) && ResourcePermissionLocalServiceUtil.hasScopeResourcePermission(company.getCompanyId(), curResource, ResourceConstants.SCOPE_COMPANY, role.getRoleId(), actionId);
			hasGroupTemplateScope = ((role.getType() == RoleConstants.TYPE_COMMUNITY) || (role.getType() == RoleConstants.TYPE_ORGANIZATION)) && ResourcePermissionLocalServiceUtil.hasScopeResourcePermission(company.getCompanyId(), curResource, ResourceConstants.SCOPE_GROUP_TEMPLATE, role.getRoleId(), actionId);
			hasGroupScope = (role.getType() == RoleConstants.TYPE_REGULAR) && ResourcePermissionLocalServiceUtil.hasScopeResourcePermission(company.getCompanyId(), curResource, ResourceConstants.SCOPE_GROUP, role.getRoleId(), actionId);
		}
		else {
			hasCompanyScope = (role.getType() == RoleConstants.TYPE_REGULAR) && PermissionLocalServiceUtil.hasRolePermission(role.getRoleId(), company.getCompanyId(), curResource, ResourceConstants.SCOPE_COMPANY, actionId);
			hasGroupTemplateScope = ((role.getType() == RoleConstants.TYPE_COMMUNITY) || (role.getType() == RoleConstants.TYPE_ORGANIZATION)) && PermissionLocalServiceUtil.hasRolePermission(role.getRoleId(), company.getCompanyId(), curResource, ResourceConstants.SCOPE_GROUP_TEMPLATE, actionId);
			hasGroupScope = (role.getType() == RoleConstants.TYPE_REGULAR) && PermissionLocalServiceUtil.hasRolePermission(role.getRoleId(), company.getCompanyId(), curResource, ResourceConstants.SCOPE_GROUP, actionId);
		}
	}
%>

	<tr>
		<td>
			<%= ResourceActionsUtil.getAction(pageContext, actionId) %>
		</td>
		<td>
			<c:choose>
				<c:when test="<%= role.getType() == RoleConstants.TYPE_REGULAR %>">

					<%
					boolean disabled = false;

					if (role.getName().equals(RoleConstants.GUEST) && guestUnsupportedActions.contains(actionId)) {
						disabled = true;
					}
					%>

					<select <%= disabled ? "disabled" : "" %> name="<portlet:namespace />scope<%= target %>" onchange="<portlet:namespace/>toggleGroupDiv('<%= target %>');">
						<option value=""></option>
							<option <%= hasCompanyScope ? "selected" : "" %> value="<%= ResourceConstants.SCOPE_COMPANY %>"><liferay-ui:message key="enterprise" /></option>

							<c:if test="<%= !portletResource.equals(PortletKeys.ENTERPRISE_ADMIN) && !portletResource.equals(PortletKeys.PORTAL) %>">
								<option <%= (hasGroupScope) ? "selected" : "" %> value="<%= ResourceConstants.SCOPE_GROUP %>"><liferay-ui:message key="communities" /></option>
							</c:if>
					</select>
				</c:when>
				<c:when test="<%= (role.getType() == RoleConstants.TYPE_COMMUNITY) || (role.getType() == RoleConstants.TYPE_ORGANIZATION) %>">

					<%
					String taglibScopeOnClick = "document.getElementById('" + renderResponse.getNamespace() + "scope" + target + "').value = (this.checked ? '" + ResourceConstants.SCOPE_GROUP + "' : '');";

					boolean disabled = portletResource.equals(PortletKeys.ENTERPRISE_ADMIN) || portletResource.equals(PortletKeys.PORTAL);

					if ((role.getType() == RoleConstants.TYPE_ORGANIZATION) && Validator.isNotNull(curModelResource) && curModelResource.equals(Organization.class.getName())) {
						disabled = false;
					}
					%>

					<liferay-ui:input-checkbox
						param='<%= "scope" + target %>'
						defaultValue="<%= hasGroupTemplateScope %>"
						onClick="<%= taglibScopeOnClick %>"
						disabled="<%= disabled %>"
					/>

					<c:if test="<%= hasGroupTemplateScope %>">
						<script type="text/javascript">
							document.getElementById("<%= renderResponse.getNamespace() %>scope<%= target %>").value =	"<%= ResourceConstants.SCOPE_GROUP %>";
						</script>
					</c:if>
				</c:when>
			</c:choose>
		</td>
		<td>

			<%
			StringBuilder groupsHTML = new StringBuilder();

			String groupIds = ParamUtil.getString(request, "groupIds" + target, null);
			long[] groupIdsArray = StringUtil.split(groupIds, 0L);

			List groupNames = new ArrayList();
			%>

			<c:if test="<%= hasGroupScope %>">

				<%
				LinkedHashMap groupParams = new LinkedHashMap();

				List rolePermissions = new ArrayList();

				rolePermissions.add(curResource);
				rolePermissions.add(new Integer(ResourceConstants.SCOPE_GROUP));
				rolePermissions.add(actionId);
				rolePermissions.add(new Long(role.getRoleId()));

				groupParams.put("rolePermissions", rolePermissions);

				List groups = GroupLocalServiceUtil.search(company.getCompanyId(), null, null, groupParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				groupIdsArray = new long[groups.size()];

				for (int j = 0; j < groups.size(); j++) {
					Group group = (Group)groups.get(j);

					groupIdsArray[j] = group.getGroupId();

					groupNames.add(group.getName());

					groupsHTML.append("<span>");
					groupsHTML.append(group.getName());

					groupsHTML.append("&nbsp;[<a href=\"javascript: ");
					groupsHTML.append(renderResponse.getNamespace());
					groupsHTML.append("removeGroup(");
					groupsHTML.append(j);
					groupsHTML.append(", '");
					groupsHTML.append(target);
					groupsHTML.append("');\">x</a>]");

					groupsHTML.append("</span>");

					if ((j + 1) != groups.size()) {
						groupsHTML.append(",&nbsp;");
					}
				}
				%>

			</c:if>

			<input name="<portlet:namespace />groupIds<%= target %>" type="hidden" value="<%= StringUtil.merge(groupIdsArray) %>" />
			<input name="<portlet:namespace />groupNames<%= target %>" type="hidden" value='<%= StringUtil.merge(groupNames, "@@") %>' />

			<div id="<portlet:namespace />groupDiv<%= target %>" <%= hasGroupScope ? "" : "style=\"display: none\"" %>>
				<span id="<portlet:namespace />groupHTML<%= target %>">
					<%= groupsHTML.toString() %>
				</span>

				<input type="button" value="<liferay-ui:message key="select" />" onclick="var groupWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/select_community" /><portlet:param name="target" value="<%= target %>" /></portlet:renderURL>', 'community', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); groupWindow.focus();" />
			</div>
		</td>
	</tr>

<%
}
%>

</table>