<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "regular-roles");

String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

String portletResource = ParamUtil.getString(request, "portletResource");

String modelResource = ParamUtil.getString(request, "modelResource");
String modelResourceDescription = ParamUtil.getString(request, "modelResourceDescription");
String modelResourceName = ResourceActionsUtil.getModelResource(pageContext, modelResource);

String resourcePrimKey = ParamUtil.getString(request, "resourcePrimKey");

if (Validator.isNull(resourcePrimKey)) {
	throw new ResourcePrimKeyException();
}

String selResource = modelResource;
String selResourceDescription = modelResourceDescription;
String selResourceName = modelResourceName;

if (Validator.isNull(modelResource)) {
	PortletURL portletURL = new PortletURLImpl(request, portletResource, plid, PortletRequest.ACTION_PHASE);

	portletURL.setWindowState(WindowState.NORMAL);
	portletURL.setPortletMode(PortletMode.VIEW);

	redirect = portletURL.toString();

	Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

	selResource = portlet.getRootPortletId();
	selResourceDescription = PortalUtil.getPortletTitle(portlet, application, locale);
	selResourceName = LanguageUtil.get(pageContext, "portlet");
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, selResourceDescription, null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "permissions"), currentURL);
}

Group group = themeDisplay.getScopeGroup();
long groupId = group.getGroupId();

Layout selLayout = null;

if (modelResource.equals(Layout.class.getName())) {
	selLayout = LayoutLocalServiceUtil.getLayout(GetterUtil.getLong(resourcePrimKey));

	group = selLayout.getGroup();
	groupId = group.getGroupId();
}

Resource resource = null;

try {
	if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
		if (ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(company.getCompanyId(), selResource, ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey) == 0) {
			throw new NoSuchResourceException();
		}
	}

	resource = ResourceLocalServiceUtil.getResource(company.getCompanyId(), selResource, ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);
}
catch (NoSuchResourceException nsre) {
	boolean portletActions = Validator.isNull(modelResource);

	ResourceLocalServiceUtil.addResources(company.getCompanyId(), groupId, 0, selResource, resourcePrimKey, portletActions, true, true);

	resource = ResourceLocalServiceUtil.getResource(company.getCompanyId(), selResource, ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);
}

PortletURL actionPortletURL = renderResponse.createActionURL();

actionPortletURL.setParameter("struts_action", "/portlet_configuration/edit_permissions");
actionPortletURL.setParameter("tabs2", tabs2);
actionPortletURL.setParameter("redirect", redirect);
actionPortletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
actionPortletURL.setParameter("portletResource", portletResource);
actionPortletURL.setParameter("modelResource", modelResource);
actionPortletURL.setParameter("modelResourceDescription", modelResourceDescription);
actionPortletURL.setParameter("resourcePrimKey", resourcePrimKey);

PortletURL renderPortletURL = renderResponse.createRenderURL();

renderPortletURL.setParameter("struts_action", "/portlet_configuration/edit_permissions");
renderPortletURL.setParameter("tabs2", tabs2);
renderPortletURL.setParameter("redirect", redirect);
renderPortletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
renderPortletURL.setParameter("portletResource", portletResource);
renderPortletURL.setParameter("modelResource", modelResource);
renderPortletURL.setParameter("modelResourceDescription", modelResourceDescription);
renderPortletURL.setParameter("resourcePrimKey", resourcePrimKey);

Group controlPanelGroup = GroupLocalServiceUtil.getGroup(company.getCompanyId(), GroupConstants.CONTROL_PANEL);

long controlPanelPlid = LayoutLocalServiceUtil.getDefaultPlid(controlPanelGroup.getGroupId(), true);

PortletURLImpl definePermissionsURL = new PortletURLImpl(request, PortletKeys.ENTERPRISE_ADMIN_ROLES, controlPanelPlid, PortletRequest.RENDER_PHASE);

definePermissionsURL.setWindowState(WindowState.MAXIMIZED);
definePermissionsURL.setPortletMode(PortletMode.VIEW);

definePermissionsURL.setRefererPlid(plid);

definePermissionsURL.setParameter("struts_action", "/enterprise_admin_roles/edit_role_permissions");
definePermissionsURL.setParameter(Constants.CMD, Constants.VIEW);
%>

<div class="edit-permissions">
	<aui:form action="<%= actionPortletURL.toString() %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="role_permissions" />
		<aui:input name="resourceId" type="hidden" value="<%= resource.getResourceId() %>" />

		<c:choose>
			<c:when test="<%= Validator.isNull(modelResource) %>">
				<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
					<liferay-util:param name="tabs1" value="permissions" />
				</liferay-util:include>
			</c:when>
			<c:otherwise>
				<div>
					<liferay-ui:message key="edit-permissions-for" /> <%= selResourceName %>: <a href="<%= HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) %>"><%= selResourceDescription %></a>
				</div>

				<br />
			</c:otherwise>
		</c:choose>


		<c:if test="<%= Validator.isNotNull(modelResource) %>">
			<liferay-ui:tabs
				names="permissions"
				param="tabs2"
				url="<%= renderPortletURL.toString() %>"
				backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
			/>
		</c:if>

		<%
		List<String> actions = ResourceActionsUtil.getResourceActions(portletResource, modelResource);
		List<String> actionsNames = ResourceActionsUtil.getActionsNames(pageContext, actions);

		RoleSearch searchContainer = new RoleSearch(renderRequest, renderPortletURL);

		searchContainer.setId("rolesSearchContainer");

		List<String> headerNames = new ArrayList<String>();

		headerNames.add("role");

		for (String actionName : actionsNames) {
			headerNames.add(actionName);
		}

		searchContainer.setHeaderNames(headerNames);

		List<Role> allRoles = ResourceActionsUtil.getRoles(group, modelResource);

		Role administrator = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleConstants.ADMINISTRATOR);

		allRoles.remove(administrator);

		if (group.isCommunity()) {
			Role communityAdministrator = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleConstants.COMMUNITY_ADMINISTRATOR);
			Role communityOwner = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleConstants.COMMUNITY_OWNER);

			allRoles.remove(communityAdministrator);
			allRoles.remove(communityOwner);
		}
		else if (group.isOrganization()) {
			Role organizationAdministrator = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleConstants.ORGANIZATION_ADMINISTRATOR);
			Role organizationOwner = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

			allRoles.remove(organizationAdministrator);
			allRoles.remove(organizationOwner);
		}

		searchContainer.setTotal(allRoles.size());

		List<Role> results = ListUtil.subList(allRoles, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			Role role = results.get(i);

			role = role.toEscapedModel();

			String name = role.getName();

			String definePermissionsHREF = null;

			if (!name.equals(RoleConstants.ADMINISTRATOR) && !name.equals(RoleConstants.COMMUNITY_ADMINISTRATOR) && !name.equals(RoleConstants.COMMUNITY_OWNER) && !name.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) && !name.equals(RoleConstants.ORGANIZATION_OWNER) && !name.equals(RoleConstants.OWNER) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.DEFINE_PERMISSIONS)) {
				definePermissionsURL.setParameter("roleId", String.valueOf(role.getRoleId()));

				definePermissionsHREF = definePermissionsURL.toString();
			}

			ResultRow row = new ResultRow(role, role.getRoleId(), i);

			// Name

			row.addText(role.getTitle(locale), definePermissionsHREF);

			// Actions

			List<String> currentIndividualActions = null;
			List<String> currentGroupActions = null;
			List<String> currentGroupTemplateActions = null;
			List<String> currentCompanyActions = null;

			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				currentIndividualActions = ResourcePermissionLocalServiceUtil.getAvailableResourcePermissionActionIds(resource.getCompanyId(), resource.getName(), resource.getScope(), resource.getPrimKey(), role.getRoleId(), actions);
				currentGroupActions = ResourcePermissionLocalServiceUtil.getAvailableResourcePermissionActionIds(resource.getCompanyId(), resource.getName(), ResourceConstants.SCOPE_GROUP, String.valueOf(groupId), role.getRoleId(), actions);
				currentGroupTemplateActions = ResourcePermissionLocalServiceUtil.getAvailableResourcePermissionActionIds(resource.getCompanyId(), resource.getName(), ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", role.getRoleId(), actions);
				currentCompanyActions = ResourcePermissionLocalServiceUtil.getAvailableResourcePermissionActionIds(resource.getCompanyId(), resource.getName(), ResourceConstants.SCOPE_COMPANY, String.valueOf(resource.getCompanyId()), role.getRoleId(), actions);
			}
			else {
				List<Permission> permissions = PermissionLocalServiceUtil.getRolePermissions(role.getRoleId(), resource.getResourceId());

				currentIndividualActions = ResourceActionsUtil.getActions(permissions);

				try {
					Resource groupResource = ResourceLocalServiceUtil.getResource(resource.getCompanyId(), resource.getName(), ResourceConstants.SCOPE_GROUP, String.valueOf(groupId));

					permissions = PermissionLocalServiceUtil.getRolePermissions(role.getRoleId(), groupResource.getResourceId());

					currentGroupActions = ResourceActionsUtil.getActions(permissions);
				}
				catch (NoSuchResourceException nsre) {
					currentGroupActions = new ArrayList<String>();
				}

				try {
					Resource groupTemplateResource = ResourceLocalServiceUtil.getResource(resource.getCompanyId(), resource.getName(), ResourceConstants.SCOPE_GROUP_TEMPLATE, "0");

					permissions = PermissionLocalServiceUtil.getRolePermissions(role.getRoleId(), groupTemplateResource.getResourceId());

					currentGroupTemplateActions = ResourceActionsUtil.getActions(permissions);
				}
				catch (NoSuchResourceException nsre) {
					currentGroupTemplateActions = new ArrayList();
				}

				try {
					Resource companyResource = ResourceLocalServiceUtil.getResource(resource.getCompanyId(), resource.getName(), ResourceConstants.SCOPE_COMPANY, String.valueOf(resource.getCompanyId()));

					permissions = PermissionLocalServiceUtil.getRolePermissions(role.getRoleId(), companyResource.getResourceId());

					currentCompanyActions = ResourceActionsUtil.getActions(permissions);
				}
				catch (NoSuchResourceException nsre) {
					currentCompanyActions = new ArrayList();
				}
			}

			List<String> currentActions = new ArrayList<String>();

			currentActions.addAll(currentIndividualActions);
			currentActions.addAll(currentGroupActions);
			currentActions.addAll(currentGroupTemplateActions);
			currentActions.addAll(currentCompanyActions);

			List<String> guestUnsupportedActions = ResourceActionsUtil.getResourceGuestUnsupportedActions(portletResource, modelResource);

			for (String action : actions) {
				boolean checked = false;
				boolean disabled = false;
				String preselectedMsg = StringPool.BLANK;

				if (currentIndividualActions.contains(action)) {
					checked = true;
				}

				if (currentGroupActions.contains(action) || currentGroupTemplateActions.contains(action)) {
					checked = true;
					preselectedMsg = "x-is-allowed-to-do-action-x-in-all-items-of-type-x-in-x";
				}

				if (currentCompanyActions.contains(action)) {
					checked = true;
					preselectedMsg = "x-is-allowed-to-do-action-x-in-all-items-of-type-x-in-this-portal-instance";
				}

				if (name.equals(RoleConstants.GUEST) && guestUnsupportedActions.contains(action)) {
					disabled = true;
				}

				StringBundler sb = new StringBundler();

				sb.append("<input ");

				if (checked) {
					sb.append("checked ");
				}

				if (Validator.isNotNull(preselectedMsg)) {
					sb.append("class=\"lfr-checkbox-preselected\" ");
				}

				if (disabled) {
					sb.append("disabled ");
				}

				sb.append("name=\"");
				sb.append(role.getRoleId());

				if (Validator.isNotNull(preselectedMsg)) {
					sb.append("_PRESELECTED_");
				}
				else {
					sb.append("_ACTION_");
				}

				sb.append(action);
				sb.append("\" ");

				if (Validator.isNotNull(preselectedMsg)) {
					sb.append("onclick=\"return false;\" onmouseover=\"Liferay.Portal.ToolTip.show(this, '");
					sb.append(UnicodeLanguageUtil.format(pageContext, preselectedMsg, new Object[] {role.getTitle(locale), ResourceActionsUtil.getAction(pageContext, action), LanguageUtil.get(pageContext, ResourceActionsUtil.MODEL_RESOURCE_NAME_PREFIX + resource.getName()), group.getDescriptiveName()}));
					sb.append("'); return false;\" ");
				}

				sb.append("type=\"checkbox\" />");

				row.addText(sb.toString());
			}

			// CSS

			row.setClassName(EnterpriseAdminUtil.getCssClassName(role));
			row.setClassHoverName(EnterpriseAdminUtil.getCssClassName(role));

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<br />

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button onClick="<%= redirect %>" type="cancel" />
	 	</aui:button-row>
	</aui:form>
</div>