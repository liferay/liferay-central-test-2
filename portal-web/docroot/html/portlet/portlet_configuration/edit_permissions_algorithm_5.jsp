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
	resource = ResourceLocalServiceUtil.getResource(company.getCompanyId(), selResource, ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);
}
catch (NoSuchResourceException nsre) {
	boolean portletActions = Validator.isNull(modelResource);

	ResourceLocalServiceUtil.addResources(company.getCompanyId(), groupId, 0, selResource, resourcePrimKey, portletActions, true, true);

	resource = ResourceLocalServiceUtil.getResource(company.getCompanyId(), selResource, ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);
}

PortletURL portletURL = renderResponse.createActionURL();

portletURL.setParameter("struts_action", "/portlet_configuration/edit_permissions");
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
portletURL.setParameter("portletResource", portletResource);
portletURL.setParameter("modelResource", modelResource);
portletURL.setParameter("modelResourceDescription", modelResourceDescription);
portletURL.setParameter("resourcePrimKey", resourcePrimKey);
%>

<div class="edit-permissions">
	<form action="<%= portletURL.toString() %>" method="post" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">
	<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="role_permissions" />
	<input name="<portlet:namespace />resourceId" type="hidden" value="<%= resource.getResourceId() %>" />

	<c:choose>
		<c:when test="<%= Validator.isNull(modelResource) %>">
			<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
				<liferay-util:param name="tabs1" value="permissions" />
			</liferay-util:include>
		</c:when>
		<c:otherwise>
			<div>
				<liferay-ui:message key="edit-permissions-for" /> <%= selResourceName %>: <a href="<%= HtmlUtil.escape(redirect) %>"><%= selResourceDescription %></a>
			</div>

			<br />
		</c:otherwise>
	</c:choose>


	<c:if test="<%= Validator.isNotNull(modelResource) %>">
		<liferay-ui:tabs
			names="permissions"
			param="tabs2"
			url="<%= portletURL.toString() %>"
			backURL="<%= redirect %>"
		/>
	</c:if>

	<%
	List<String> actions = ResourceActionsUtil.getResourceActions(portletResource, modelResource);
	List<String> actionsNames = ResourceActionsUtil.getActionsNames(pageContext, actions);

	RoleSearch searchContainer = new RoleSearch(renderRequest, portletURL);

	List<String> headerNames = new ArrayList<String>();

	headerNames.add("role");

	for (String actionName : actionsNames) {
		headerNames.add(actionName);
	}

	searchContainer.setHeaderNames(headerNames);

	List<Role> results = ResourceActionsUtil.getRoles(group, modelResource);

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		Role role = results.get(i);

		if (role.getName().equals(RoleConstants.ADMINISTRATOR)) {
			continue;
		}

		ResultRow row = new ResultRow(role, role.getRoleId(), i);

		// Name

		row.addText(role.getTitle(locale));

		// Actions

		List permissions = PermissionLocalServiceUtil.getRolePermissions(role.getRoleId(), resource.getResourceId());

		List currentActions = ResourceActionsUtil.getActions(permissions);

		List guestUnsupportedActions = ResourceActionsUtil.getResourceGuestUnsupportedActions(portletResource, modelResource);

		for (String action : actions) {
			StringBuilder sb = new StringBuilder();

			sb.append("<input ");

			if (currentActions.contains(action)) {
				sb.append("checked ");
			}

			if (role.getName().equals(RoleConstants.GUEST) && guestUnsupportedActions.contains(action)) {
				sb.append("disabled ");
			}

			sb.append("name=\"");
			sb.append(role.getRoleId() + "_ACTION_" + action);
			sb.append("\" type=\"checkbox\" />");

			row.addText(sb.toString());
		}

		// CSS

		row.setClassName(EnterpriseAdminUtil.getCssClassName(role));
		row.setClassHoverName(EnterpriseAdminUtil.getCssClassName(role));

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />

	<br />

	<input type="submit" value="<liferay-ui:message key="submit" />" />
	</form>
</div>