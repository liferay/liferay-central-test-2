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

<%@ include file="/html/portlet/directory/init.jsp" %>

<%
PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");
%>

<liferay-ui:search-container
	searchContainer="<%= new OrganizationSearch(renderRequest, portletURL) %>"
>
	<aui:input name="organizationsRedirect" type="hidden" value="<%= portletURL.toString() %>" />

	<liferay-ui:search-form
		page="/html/portlet/directory/organization_search.jsp"
	/>

	<%
	OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)searchContainer.getSearchTerms();

	LinkedHashMap organizationParams = new LinkedHashMap();

	long parentOrganizationId = ParamUtil.getLong(request, "parentOrganizationId", OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);

	if (parentOrganizationId <= 0) {
		parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;
	}
	%>

	<liferay-ui:search-container-results>
		<c:choose>
			<c:when test="<%= PropsValues.ORGANIZATIONS_SEARCH_WITH_INDEX %>">
				<%@ include file="/html/portlet/enterprise_admin/organization_search_results_index.jspf" %>
			</c:when>
			<c:otherwise>
				<%@ include file="/html/portlet/enterprise_admin/organization_search_results_database.jspf" %>
			</c:otherwise>
		</c:choose>
	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Organization"
		escapedModel="<%= true %>"
		keyProperty="organizationId"
		modelVar="organization"
	>
		<portlet:renderURL var="rowURL">
			<portlet:param name="struts_action" value="/directory/view_organization" />
			<portlet:param name="tabs1" value="<%= HtmlUtil.escape(tabs1) %>" />
			<portlet:param name="redirect" value="<%= searchContainer.getIteratorURL().toString() %>" />
			<portlet:param name="organizationId" value="<%= String.valueOf(organization.getOrganizationId()) %>" />
		</portlet:renderURL>

		<%@ include file="/html/portlet/directory/organization/search_columns.jspf" %>
	</liferay-ui:search-container-row>

	<div class="separator"><!-- --></div>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

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

private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.enterprise_admin.organization_search_results_index.jspf");
%>