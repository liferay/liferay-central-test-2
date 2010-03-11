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
		%>

		<%@ include file="/html/portlet/portlet_configuration/edit_permissions_algorithm_5_to_6_roles.jspf" %>

		<br />

		<%--<%@ include file="/html/portlet/portlet_configuration/edit_permissions_algorithm_5_to_6_teams.jspf" %>

		<br />--%>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button onClick="<%= redirect %>" type="cancel" />
	 	</aui:button-row>
	</aui:form>
</div>