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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String cmd = ParamUtil.getString(request, Constants.CMD);

tabs1 = "roles";
String tabs2 = ParamUtil.getString(request, "tabs2", "current");

String cur = ParamUtil.getString(request, "cur");

Role role = (Role)request.getAttribute(WebKeys.ROLE);

String portletResource = ParamUtil.getString(request, "portletResource");
String modelResource = ParamUtil.getString(request, "modelResource");

String portletResourceName = null;

if (Validator.isNotNull(portletResource)) {
	Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

	portletResourceName = PortalUtil.getPortletTitle(portlet, application, locale);
}

String modelResourceName = ResourceActionsUtil.getModelResource(pageContext, modelResource);

List modelResources = null;

if (Validator.isNotNull(portletResource) && Validator.isNull(modelResource)) {
	modelResources = ResourceActionsUtil.getPortletModelResources(portletResource);
}

String selResource = modelResource;
String selResourceName = modelResourceName;

if (Validator.isNull(modelResource)) {
	selResource = portletResource;
	selResourceName = portletResourceName;
}

int groupScopePos = ParamUtil.getInteger(request, "groupScopePos");
String groupScopeActionIds = ParamUtil.getString(request, "groupScopeActionIds");
String[] groupScopeActionIdsArray = StringUtil.split(groupScopeActionIds);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/enterprise_admin/edit_role_permissions");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("roleId", String.valueOf(role.getRoleId()));
portletURL.setParameter("portletResource", portletResource);
portletURL.setParameter("modelResource", modelResource);

PortletURL addPermissionsURL = renderResponse.createRenderURL();

addPermissionsURL.setWindowState(WindowState.MAXIMIZED);

addPermissionsURL.setParameter("struts_action", "/enterprise_admin/edit_role_permissions");
addPermissionsURL.setParameter(Constants.CMD, Constants.EDIT);
addPermissionsURL.setParameter("tabs1", "roles");
addPermissionsURL.setParameter("redirect", currentURL);
addPermissionsURL.setParameter("roleId", String.valueOf(role.getRoleId()));

boolean editPortletPermissions = ParamUtil.getBoolean(request, "editPortletPermissions");

if ((modelResources != null) && (modelResources.size() == 0)) {
	editPortletPermissions = true;
}

int totalSteps = 0;

if (portletResource.equals(PortletKeys.PORTAL)) {
	totalSteps = 1;
}
else if (role.getType() == RoleImpl.TYPE_REGULAR) {
	totalSteps = 4;
}
else if (role.getType() == RoleImpl.TYPE_COMMUNITY) {
	totalSteps = 3;
}

// Breadcrumbs

PortletURL breadcrumbsURL = renderResponse.createRenderURL();

breadcrumbsURL.setWindowState(WindowState.MAXIMIZED);

breadcrumbsURL.setParameter("struts_action", "/enterprise_admin/view");
breadcrumbsURL.setParameter("tabs1", tabs1);
breadcrumbsURL.setParameter("roleId", String.valueOf(role.getRoleId()));

String breadcrumbs = "<a href=\"" + breadcrumbsURL.toString() + "\">" + LanguageUtil.get(pageContext, "roles") + "</a> &raquo; ";

breadcrumbsURL.setParameter("struts_action", "/enterprise_admin/edit_role_permissions");
breadcrumbsURL.setParameter(Constants.CMD, Constants.VIEW);

breadcrumbs += "<a href=\"" + breadcrumbsURL.toString() + "\">" + role.getName() + "</a>";

breadcrumbsURL.setParameter(Constants.CMD, Constants.EDIT);

if (!cmd.equals(Constants.VIEW) && Validator.isNotNull(portletResource)) {
	breadcrumbsURL.setParameter("portletResource", portletResource);

	breadcrumbs += " &raquo; <a href=\"" + breadcrumbsURL.toString() + "\">" + portletResourceName + "</a>";
}

if (!cmd.equals(Constants.VIEW) && Validator.isNotNull(modelResource)) {
	breadcrumbsURL.setParameter("modelResource", modelResource);

	breadcrumbs += " &raquo; <a href=\"" + breadcrumbsURL.toString() + "\">" + modelResourceName + "</a>";
}
%>

<script type="text/javascript">
	function <portlet:namespace />addPermissions(type) {
		var addPermissionsURL = "<%= addPermissionsURL.toString() %>";

		if (type == "portal") {
			addPermissionsURL += "&<portlet:namespace />portletResource=<%= PortletKeys.PORTAL %>";
		}

		self.location = addPermissionsURL;
	}

	function <portlet:namespace />updateActions() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "actions";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<%= portletURL.toString() %>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateGroupPermissions(groupScopePos) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "group_permissions";

		var redirect = "";

		if (<%= cmd.equals(Constants.VIEW) %>) {
			redirect = "<%= portletURL.toString() %>";
		}
		else if (groupScopePos == <%= groupScopeActionIdsArray.length %>) {
			//redirect = "<%= portletURL.toString() %>&<portlet:namespace />groupScopePos=-1";
			redirect = "<%= portletURL.toString() %>&<portlet:namespace /><%= Constants.CMD %>=<%= Constants.VIEW %>";
		}
		else {
			redirect = "<%= portletURL.toString() %>&<portlet:namespace />groupScopePos=" +
				groupScopePos + "&<portlet:namespace />groupScopeActionIds=<%= groupScopeActionIds %>";

			if (groupScopePos == <%= groupScopePos %>) {
				redirect += "&<portlet:namespace />cur=<%= cur %>";
			}
		}

		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
		document.<portlet:namespace />fm.<portlet:namespace />addGroupIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		document.<portlet:namespace />fm.<portlet:namespace />removeGroupIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_role_permissions" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= tabs2 %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="" />
<input name="<portlet:namespace />roleId" type="hidden" value="<%= role.getRoleId() %>" />
<input name="<portlet:namespace />portletResource" type="hidden" value="<%= portletResource %>" />
<input name="<portlet:namespace />modelResource" type="hidden" value="<%= modelResource %>" />
<input name="<portlet:namespace />groupScopePos" type="hidden" value="<%= groupScopePos %>" />
<input name="<portlet:namespace />groupScopeActionIds" type="hidden" value="<%= groupScopeActionIds %>" />

<liferay-util:include page="/html/portlet/enterprise_admin/tabs1.jsp">
	<liferay-util:param name="tabs1" value="<%= tabs1 %>" />
</liferay-util:include>

<c:choose>
	<c:when test="<%= cmd.equals(Constants.VIEW) %>">
		<div class="breadcrumbs">
			<%= breadcrumbs %>
		</div>

		<liferay-ui:success key="permissionDeleted" message="the-permission-was-deleted" />
		<liferay-ui:success key="permissionsUpdated" message="the-role-permissions-were-updated" />

		<%
		List headerNames = new ArrayList();

		headerNames.add("resource");
		headerNames.add("action");

		if ((role.getType() == RoleImpl.TYPE_REGULAR)) {
			headerNames.add("scope");
			headerNames.add("communities");
		}

		headerNames.add(StringPool.BLANK);

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "this-role-does-have-any-permissions");

		List permissions = PermissionLocalServiceUtil.getRolePermissions(role.getRoleId());

		List permissionsDisplay = new ArrayList(permissions.size());

		for (int i = 0; i < permissions.size(); i++) {
			Permission permission = (Permission)permissions.get(i);

			Resource resource = ResourceLocalServiceUtil.getResource(permission.getResourceId());

			String resourceName = resource.getName();
			String resourceNameParam = null;
			String resourceLabel = null;
			String actionId = permission.getActionId();
			String actionLabel = ResourceActionsUtil.getAction(pageContext, actionId);

			if (PortletLocalServiceUtil.hasPortlet(company.getCompanyId(), resourceName)) {
				Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), resourceName);

				resourceLabel = PortalUtil.getPortletTitle(portlet, application, locale);
				resourceNameParam = "portletResource";
			}
			else {
				resourceLabel = ResourceActionsUtil.getModelResource(pageContext, resourceName);
				resourceNameParam = "modelResource";
			}

			permissionsDisplay.add(new PermissionDisplay(permission, resource, resourceName, resourceNameParam, resourceLabel, actionId, actionLabel));
		}

		Collections.sort(permissionsDisplay);

		int total = permissionsDisplay.size();

		searchContainer.setTotal(total);

		List results = ListUtil.subList(permissionsDisplay, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			PermissionDisplay permissionDisplay = (PermissionDisplay)results.get(i);

			Permission permission = permissionDisplay.getPermission();
			Resource resource = permissionDisplay.getResource();
			String resourceName = permissionDisplay.getResourceName();
			String resourceNameParam = permissionDisplay.getResourceNameParam();
			String resourceLabel = permissionDisplay.getResourceLabel();
			String actionId = permissionDisplay.getActionId();
			String actionLabel = permissionDisplay.getActionLabel();

			ResultRow row = new ResultRow(new Object[] {permission, role}, actionId, i);

			boolean hasCompanyScope = (role.getType() == RoleImpl.TYPE_REGULAR) && PermissionLocalServiceUtil.hasRolePermission(role.getRoleId(), company.getCompanyId(), resourceName, ResourceImpl.SCOPE_COMPANY, actionId);
			boolean hasGroupTemplateScope = (role.getType() == RoleImpl.TYPE_COMMUNITY) && PermissionLocalServiceUtil.hasRolePermission(role.getRoleId(), company.getCompanyId(), resourceName, ResourceImpl.SCOPE_GROUP_TEMPLATE, actionId);
			boolean hasGroupScope = (role.getType() == RoleImpl.TYPE_REGULAR) && PermissionLocalServiceUtil.hasRolePermission(role.getRoleId(), company.getCompanyId(), resourceName, ResourceImpl.SCOPE_GROUP, actionId);

			PortletURL editResourcePermissionsURL = renderResponse.createRenderURL();

			editResourcePermissionsURL.setWindowState(WindowState.MAXIMIZED);

			editResourcePermissionsURL.setParameter("struts_action", "/enterprise_admin/edit_role_permissions");
			editResourcePermissionsURL.setParameter("tabs1", "roles");
			editResourcePermissionsURL.setParameter("redirect", currentURL);
			editResourcePermissionsURL.setParameter("roleId", String.valueOf(role.getRoleId()));

			if (resourceNameParam.equals("modelResource")) {
				List portletResources = ResourceActionsUtil.getModelPortletResources(resourceName);

				if (portletResources.size() > 0) {
					editResourcePermissionsURL.setParameter("portletResource", (String)portletResources.get(0));
				}
			}

			editResourcePermissionsURL.setParameter(resourceNameParam, resourceName);

			row.addText(resourceLabel, editResourcePermissionsURL);
			row.addText(actionLabel);

			if (hasCompanyScope) {
				row.addText(LanguageUtil.get(pageContext, "enterprise"));
				row.addText(LanguageUtil.get(pageContext, "not-available"));
			}
			else if (hasGroupTemplateScope) {
			}
			else if (hasGroupScope) {
				row.addText(LanguageUtil.get(pageContext, "community"));

				StringMaker sm = new StringMaker();

				LinkedHashMap groupParams = new LinkedHashMap();

				List rolePermissions = new ArrayList();

				rolePermissions.add(resourceName);
				rolePermissions.add(new Integer(ResourceImpl.SCOPE_GROUP));
				rolePermissions.add(actionId);
				rolePermissions.add(new Long(role.getRoleId()));

				groupParams.put("rolePermissions", rolePermissions);

				List groups = GroupLocalServiceUtil.search(company.getCompanyId(), null, null, groupParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (int j = 0; j < groups.size(); j++) {
					Group group = (Group)groups.get(j);

					sm.append(group.getName());

					if ((j + 1) != groups.size()) {
						sm.append(", ");
					}
				}

				row.addText(sm.toString());
			}

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/enterprise_admin/permission_action.jsp");

			if (hasCompanyScope || hasGroupTemplateScope || hasGroupScope) {
				resultRows.add(row);
			}
		}
		%>

		<input type="button" value="<liferay-ui:message key="add-portlet-permissions" />" onClick="<portlet:namespace />addPermissions('portlet');" />

		<input type="button" value="<liferay-ui:message key="add-portal-permissions" />" onClick="<portlet:namespace />addPermissions('portal');" />

		<br /><br />

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</c:when>
	<c:when test="<%= (groupScopePos >= 0) && (groupScopeActionIdsArray.length > 0) %>">
		<input name="<portlet:namespace />addGroupIds" type="hidden" value="" />
		<input name="<portlet:namespace />removeGroupIds" type="hidden" value="" />

		<%
		String actionId = groupScopeActionIdsArray[groupScopePos];

		portletURL.setParameter("groupScopePos", String.valueOf(groupScopePos));
		portletURL.setParameter("groupScopeActionIds", groupScopeActionIds);
		%>

		<div class="portlet-section-body" style="border: 1px solid <%= colorScheme.getPortletFontDim() %>; padding: 5px;">
			<%= LanguageUtil.format(pageContext, "step-x-of-x", new String[] {"4", String.valueOf(totalSteps)}) %>

			<c:choose>
				<c:when test="<%= Validator.isNotNull(modelResource) %>">
					<%= LanguageUtil.format(pageContext, "select-the-communities-where-this-role-can-perform-the-x-action-on-the-x-resource", new String[] {ResourceActionsUtil.getAction(pageContext, actionId), modelResourceName}) %>
				</c:when>
				<c:otherwise>
					<%= LanguageUtil.format(pageContext, "select-the-communities-where-this-role-can-perform-the-x-action-on-the-x-portlet", new String[] {ResourceActionsUtil.getAction(pageContext, actionId), portletResourceName}) %>
				</c:otherwise>
			</c:choose>
		</div>

		<br />

		<div class="breadcrumbs">
			<%= breadcrumbs %>
		</div>

		<liferay-ui:tabs
			names="current,available"
			param="tabs2"
			url="<%= portletURL.toString() %>"
		/>

		<%
		GroupSearch searchContainer = new GroupSearch(renderRequest, portletURL);

		searchContainer.setRowChecker(new GroupPermissionChecker(renderResponse, role, selResource, actionId));
		%>

		<liferay-ui:search-form
			page="/html/portlet/enterprise_admin/group_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<%
		GroupSearchTerms searchTerms = (GroupSearchTerms)searchContainer.getSearchTerms();

		LinkedHashMap groupParams = new LinkedHashMap();

		if (tabs2.equals("current")) {
			List rolePermissions = new ArrayList();

			rolePermissions.add(selResource);
			rolePermissions.add(new Integer(ResourceImpl.SCOPE_GROUP));
			rolePermissions.add(actionId);
			rolePermissions.add(new Long(role.getRoleId()));

			groupParams.put("rolePermissions", rolePermissions);
		}

		int total = GroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), groupParams);

		searchContainer.setTotal(total);

		List results = GroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), groupParams, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);
		%>

		<div class="separator"><!-- --></div>

		<input type="button" value="<liferay-ui:message key="update-associations" />" onClick="<portlet:namespace />updateGroupPermissions(<%= groupScopePos %>);" />

		<br /><br />

		<%
		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			Group group = (Group)results.get(i);

			ResultRow row = new ResultRow(group, group.getGroupId(), i);

			// Name

			row.addText(group.getName());

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

		<br />

		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td>
				<input type="button" value="<liferay-ui:message key="previous" />" onClick="<portlet:namespace />updateGroupPermissions(<%= groupScopePos - 1 %>);" />

				<input type="button" value="<liferay-ui:message key="next" />" onClick="<portlet:namespace />updateGroupPermissions(<%= groupScopePos + 1 %>);" />
			</td>
		</tr>
		</table>
	</c:when>
	<c:when test="<%= editPortletPermissions || Validator.isNotNull(modelResource) %>">

		<%
		List actions = ResourceActionsUtil.getResourceActions(company.getCompanyId(), portletResource, modelResource);

		Collections.sort(actions, new ActionComparator(company.getCompanyId(), locale));
		%>

		<div class="portlet-section-body" style="border: 1px solid <%= colorScheme.getPortletFontDim() %>; padding: 5px;">
			<c:choose>
				<c:when test="<%= portletResource.equals(PortletKeys.PORTAL) %>">
					<%= LanguageUtil.format(pageContext, "step-x-of-x", new String[] {"1", String.valueOf(totalSteps)}) %>

					<%= LanguageUtil.format(pageContext, "select-the-scope-of-the-action-that-this-role-can-perform-on-the-x", portletResourceName) %>

					<c:if test="<%= actions.size() > 0 %>">
						<%= LanguageUtil.get(pageContext, "you-can-choose-more-than-one") %>
					</c:if>
				</c:when>
				<c:when test="<%= role.getType() == RoleImpl.TYPE_REGULAR %>">
					<%= LanguageUtil.format(pageContext, "step-x-of-x", new String[] {"3", String.valueOf(totalSteps)}) %>

					<c:choose>
						<c:when test="<%= Validator.isNotNull(modelResource) %>">
							<%= LanguageUtil.format(pageContext, "select-the-scope-of-the-action-that-this-role-can-perform-on-the-x-resource", modelResourceName) %>
						</c:when>
						<c:otherwise>
							<%= LanguageUtil.format(pageContext, "select-the-scope-of-the-action-that-this-role-can-perform-on-the-x-portlet", portletResourceName) %>
						</c:otherwise>
					</c:choose>

					<c:if test="<%= actions.size() > 0 %>">
						<%= LanguageUtil.get(pageContext, "you-can-choose-more-than-one") %>
					</c:if>
				</c:when>
				<c:otherwise>
					<%= LanguageUtil.format(pageContext, "step-x-of-x", new String[] {"3", String.valueOf(totalSteps)}) %>

					<c:choose>
						<c:when test="<%= Validator.isNotNull(modelResource) %>">
							<%= LanguageUtil.format(pageContext, "select-the-action-that-this-role-can-perform-on-the-x-resource", modelResourceName) %>
						</c:when>
						<c:otherwise>
							<%= LanguageUtil.format(pageContext, "select-the-action-that-this-role-can-perform-on-the-x-portlet", portletResourceName) %>
						</c:otherwise>
					</c:choose>

					<c:if test="<%= actions.size() > 0 %>">
						<%= LanguageUtil.get(pageContext, "you-can-choose-more-than-one") %>
					</c:if>
				</c:otherwise>
			</c:choose>
		</div>

		<br />

		<div class="breadcrumbs">
			<%= breadcrumbs %>
		</div>

		<table class="liferay-table">
		<tr>
			<th>
				<liferay-ui:message key="action" />
			</th>
			<th>
				<%= LanguageUtil.get(pageContext, (role.getType() == RoleImpl.TYPE_REGULAR) ? "scope" : "") %>
			</th>
		</tr>

		<%
		for (int i = 0; i < actions.size(); i++) {
			String actionId = (String)actions.get(i);

			boolean hasCompanyScope = (role.getType() == RoleImpl.TYPE_REGULAR) && PermissionLocalServiceUtil.hasRolePermission(role.getRoleId(), company.getCompanyId(), selResource, ResourceImpl.SCOPE_COMPANY, actionId);
			boolean hasGroupTemplateScope = (role.getType() == RoleImpl.TYPE_COMMUNITY) && PermissionLocalServiceUtil.hasRolePermission(role.getRoleId(), company.getCompanyId(), selResource, ResourceImpl.SCOPE_GROUP_TEMPLATE, actionId);
			boolean hasGroupScope = (role.getType() == RoleImpl.TYPE_REGULAR) && PermissionLocalServiceUtil.hasRolePermission(role.getRoleId(), company.getCompanyId(), selResource, ResourceImpl.SCOPE_GROUP, actionId);
		%>

			<tr>
				<td>
					<%= ResourceActionsUtil.getAction(pageContext, actionId) %>
				</td>
				<td>
					<c:choose>
						<c:when test="<%= role.getType() == RoleImpl.TYPE_REGULAR %>">
							<select name="<portlet:namespace />scope<%= actionId %>">
								<option value=""></option>
									<option <%= hasCompanyScope ? "selected" : "" %> value="<%= ResourceImpl.SCOPE_COMPANY %>"><liferay-ui:message key="enterprise" /></option>

									<c:if test="<%= !portletResource.equals(PortletKeys.ENTERPRISE_ADMIN) && !portletResource.equals(PortletKeys.PORTAL) %>">
										<option <%= (hasGroupScope) ? "selected" : "" %> value="<%= ResourceImpl.SCOPE_GROUP %>"><liferay-ui:message key="communities" /></option>
									</c:if>
							</select>
						</c:when>
						<c:when test="<%= role.getType() == RoleImpl.TYPE_COMMUNITY %>">
							<liferay-ui:input-checkbox
								param='<%= "scope" + actionId %>'
								defaultValue="<%= hasGroupTemplateScope %>"
								onClick='<%= "document.getElementById('" + renderResponse.getNamespace() + "scope" + actionId + "').value = (this.checked ? '" + ResourceImpl.SCOPE_GROUP + "' : '');" %>'
							/>

							<c:if test="<%= hasGroupTemplateScope %>">
								<script type="text/javascript">
									document.getElementById("<%= renderResponse.getNamespace() %>scope<%= actionId %>").value =	"<%= ResourceImpl.SCOPE_GROUP %>";
								</script>
							</c:if>
						</c:when>
					</c:choose>
				</td>
			</tr>

		<%
		}
		%>

		</table>

		<br />

		<input type="button" value="<liferay-ui:message key="next" />" onClick="<portlet:namespace />updateActions();" />
	</c:when>
	<c:when test="<%= Validator.isNotNull(portletResource) %>">
		<div class="portlet-section-body" style="border: 1px solid <%= colorScheme.getPortletFontDim() %>; padding: 5px;">
			<%= LanguageUtil.format(pageContext, "step-x-of-x", new String[] {"2", String.valueOf(totalSteps)}) %>

			<%= LanguageUtil.get(pageContext, "choose-a-resource-or-proceed-to-the-next-step") %>
		</div>

		<br />

		<div class="breadcrumbs">
			<%= breadcrumbs %>
		</div>

		<%= LanguageUtil.format(pageContext, "proceed-to-the-next-step-to-define-permissions-on-the-x-portlet-itself", portletResourceName) %>

		<br /><br />

		<input type="button" value="<liferay-ui:message key="next" />" onClick="self.location = '<%= portletURL.toString() %>&editPortletPermissions=1';" />

		<c:if test="<%= modelResources.size() > 0 %>">
			<br /><br />

			<liferay-ui:tabs names="resources" />

			<%= LanguageUtil.format(pageContext, "define-permissions-on-a-resource-that-belongs-to-the-x-portlet", portletResourceName) %>

			<br /><br />

			<%
			SearchContainer searchContainer = new SearchContainer();

			List headerNames = new ArrayList();

			headerNames.add("name");

			searchContainer.setHeaderNames(headerNames);

			Collections.sort(modelResources, new ModelResourceComparator(company.getCompanyId(), locale));

			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < modelResources.size(); i++) {
				String curModelResource = (String)modelResources.get(i);

				ResultRow row = new ResultRow(curModelResource, curModelResource, i);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setWindowState(WindowState.MAXIMIZED);

				rowURL.setParameter("struts_action", "/enterprise_admin/edit_role_permissions");
				rowURL.setParameter("roleId", String.valueOf(role.getRoleId()));
				rowURL.setParameter("portletResource", portletResource);
				rowURL.setParameter("modelResource", curModelResource);

				// Name

				row.addText(ResourceActionsUtil.getModelResource(pageContext, curModelResource), rowURL);

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		</c:if>
	</c:when>
	<c:otherwise>

		<%
		List headerNames = new ArrayList();

		headerNames.add("portlet");

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

		List portlets = PortletLocalServiceUtil.getPortlets(company.getCompanyId(), false, false);

		Collections.sort(portlets, new PortletTitleComparator(application, locale));

		int total = portlets.size();

		searchContainer.setTotal(total);

		List results = ListUtil.subList(portlets, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			Portlet portlet = (Portlet)results.get(i);

			ResultRow row = new ResultRow(portlet, portlet.getId(), i);

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setWindowState(WindowState.MAXIMIZED);

			rowURL.setParameter("struts_action", "/enterprise_admin/edit_role_permissions");
			rowURL.setParameter("roleId", String.valueOf(role.getRoleId()));
			rowURL.setParameter("portletResource", portlet.getPortletId());

			// Name

			row.addText(PortalUtil.getPortletTitle(portlet, application, locale), rowURL);

			// Add result row

			resultRows.add(row);
		}
		%>

		<div class="portlet-section-body" style="border: 1px solid <%= colorScheme.getPortletFontDim() %>; padding: 5px;">
			<%= LanguageUtil.format(pageContext, "step-x-of-x", new String[] {"1", String.valueOf(totalSteps)}) %>

			<%= LanguageUtil.get(pageContext, "choose-a-portlet") %>
		</div>

		<br />

		<div class="breadcrumbs">
			<%= breadcrumbs %>
		</div>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</c:otherwise>
</c:choose>

</form>