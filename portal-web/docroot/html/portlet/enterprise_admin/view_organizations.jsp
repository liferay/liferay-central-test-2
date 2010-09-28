<%--
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
--%>

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");

long organizationId = GetterUtil.getLong(request.getParameter("organizationId"), OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);
long defaultOrganizationId = GetterUtil.getLong(preferences.getValue("rootOrganizationId", StringPool.BLANK), OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);

String organizationViewPref = GetterUtil.getString(request.getParameter("organizationViewPref"));

if (Validator.isNotNull(organizationViewPref) && ArrayUtil.contains(PropsValues.ORGANIZATIONS_VIEWS, organizationViewPref)) {
	portalPreferences.setValue(PortletKeys.ENTERPRISE_ADMIN_ORGANIZATIONS, "organization-view", organizationViewPref);
}
else if (Validator.isNotNull(organizationViewPref) && !ArrayUtil.contains(PropsValues.ORGANIZATIONS_VIEWS, organizationViewPref)){
	organizationViewPref = PropsValues.ORGANIZATIONS_VIEWS_DEFAULT;

	portalPreferences.setValue(PortletKeys.ENTERPRISE_ADMIN_ORGANIZATIONS, "organization-view", organizationViewPref);
}

String viewOrganizationsRedirect = ParamUtil.getString(request, "viewOrganizationsRedirect");

if (Validator.isNotNull(viewOrganizationsRedirect)) {
	portletURL.setParameter("viewOrganizationsRedirect", viewOrganizationsRedirect);
}

renderRequest.setAttribute("tabs1", "organizations");
%>

<liferay-ui:error exception="<%= RequiredOrganizationException.class %>" message="you-cannot-delete-organizations-that-have-suborganizations-or-users" />

<liferay-util:include page="/html/portlet/enterprise_admin/organization/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value="view-all" />
</liferay-util:include>

<c:if test="<%= Validator.isNotNull(viewOrganizationsRedirect) %>">
	<aui:input name="viewOrganizationsRedirect" type="hidden" value="<%= viewOrganizationsRedirect %>" />
</c:if>

<c:choose>
	<c:when test="<%= organizationView.equals(OrganizationConstants.ORGANIZATION_VIEW_FLAT) %>">
		<%@ include file="/html/portlet/enterprise_admin/organization/view_organizations_flat.jspf" %>
	</c:when>
	<c:otherwise>
		<%@ include file="/html/portlet/enterprise_admin/organization/view_organizations_tree.jspf" %>
	</c:otherwise>
</c:choose>

<%!
private static List<Organization> _getResults(Hits hits) throws Exception {
	List<Organization> organizations = new ArrayList<Organization>();

	List<Document> hitsList = hits.toList();

	for (Document doc : hitsList) {
		long organizationId = GetterUtil.getLong(doc.get(Field.ORGANIZATION_ID));

		try {
			organizations.add(OrganizationLocalServiceUtil.getOrganization(organizationId));
		}
		catch (NoSuchOrganizationException nsoe) {
			_log.error("Organization " + organizationId + " does not exist in the search index. Please reindex.");
		}
	}

	return organizations;
}

private static Sort _getSort(String orderByCol, String orderByType) {
	String sortField = "name";

	if (Validator.isNotNull(orderByCol)) {
		if (orderByCol.equals("name")) {
			sortField = "name";
		}
		else if (orderByCol.equals("type")) {
			sortField = "type";
		}
		else {
			sortField = orderByCol;
		}
	}

	if (Validator.isNull(orderByType)) {
		orderByType = "asc";
	}

	return new Sort(sortField, Sort.STRING_TYPE, !orderByType.equalsIgnoreCase("asc"));
}

private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.enterprise_admin.view_organizations.jsp");
%>