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

<%@ include file="/html/portlet/my_organizations/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "my-organizations");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/my_organizations/view");
portletURL.setParameter("tabs1", tabs1);

pageContext.setAttribute("portletURL", portletURL);
%>

<aui:form action="<%= portletURL.toString() %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />

	<liferay-ui:tabs
		names="my-organizations,available-organizations"
		url="<%= portletURL.toString() %>"
	/>

	<%
	OrganizationSearch searchContainer = new OrganizationSearch(renderRequest, portletURL);
	%>

	<liferay-ui:search-form
		page="/html/portlet/enterprise_admin/organization_search.jsp"
		searchContainer="<%= searchContainer %>"
	/>

	<%
	OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)searchContainer.getSearchTerms();

	LinkedHashMap organizationParams = new LinkedHashMap();

	if (tabs1.equals("my-organizations")) {
		organizationParams.put("usersOrgs", new Long(user.getUserId()));
	}

	List<Organization> results = new ArrayList<Organization>();

	if (searchTerms.isAdvancedSearch()) {
		results = OrganizationLocalServiceUtil.search(company.getCompanyId(), OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, searchTerms.getName(), searchTerms.getType(), searchTerms.getStreet(), searchTerms.getCity(), searchTerms.getZip(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams, searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
	}
	else {
		results = OrganizationLocalServiceUtil.search(company.getCompanyId(), OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, searchTerms.getKeywords(), searchTerms.getType(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
	}

	int total = results.size();

	searchContainer.setTotal(total);
	searchContainer.setResults(results);
	%>

	<div class="separator"><!-- --></div>

	<%
	List<String> headerNames = new ArrayList<String>();

	headerNames.add("name");

	searchContainer.setHeaderNames(headerNames);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		Organization organization = results.get(i);

		organization = organization.toEscapedModel();
		Group group = organization.getGroup();

		ResultRow row = new ResultRow(new Object[] {organization, tabs1}, group.getGroupId(), i);

		PortletURL rowURL = renderResponse.createActionURL();

		// Name

		StringBundler sb = new StringBundler();

		sb.append(HtmlUtil.escape(organization.getName()));

		int publicLayoutsPageCount = group.getPublicLayoutsPageCount();
		int privateLayoutsPageCount = group.getPrivateLayoutsPageCount();

		Group stagingGroup = null;

		if (group.hasStagingGroup()) {
			stagingGroup = group.getStagingGroup();
		}

		if ((tabs1.equals("my-organizations") || tabs1.equals("available-organizations")) &&
			((publicLayoutsPageCount > 0) || (privateLayoutsPageCount > 0))) {

			sb.append("<br />");

			if (publicLayoutsPageCount > 0) {
				rowURL.setParameter("groupId", String.valueOf(group.getGroupId()));
				rowURL.setParameter("privateLayout", Boolean.FALSE.toString());

				sb.append("<a href=\"");
				sb.append(rowURL.toString());
				sb.append("\">");
				sb.append(LanguageUtil.get(pageContext, "public-pages"));
				sb.append(" - ");
				sb.append(LanguageUtil.get(pageContext, "live"));
				sb.append(" (");
				sb.append(publicLayoutsPageCount);
				sb.append(")");
				sb.append("</a>");
			}
			else {
				sb.append(LanguageUtil.get(pageContext, "public-pages"));
				sb.append(" (0)");
			}

			if ((stagingGroup != null) && (tabs1.equals("my-organizations")) && GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.MANAGE_LAYOUTS)) {
				rowURL.setParameter("groupId", String.valueOf(stagingGroup.getGroupId()));
				rowURL.setParameter("privateLayout", Boolean.FALSE.toString());

				if (stagingGroup.getPublicLayoutsPageCount() > 0) {
					sb.append(" / ");
					sb.append("<a href=\"");
					sb.append(rowURL.toString());
					sb.append("\">");
					sb.append(LanguageUtil.get(pageContext, "staging"));
					sb.append("</a>");
				}
			}

			sb.append("<br />");

			if (tabs1.equals("my-organizations")) {
				if (privateLayoutsPageCount > 0) {
					rowURL.setParameter("groupId", String.valueOf(organization.getGroup().getGroupId()));
					rowURL.setParameter("privateLayout", Boolean.TRUE.toString());

					sb.append("<a href=\"");
					sb.append(rowURL.toString());
					sb.append("\">");
					sb.append(LanguageUtil.get(pageContext, "private-pages"));
					sb.append(" - ");
					sb.append(LanguageUtil.get(pageContext, "live"));
					sb.append(" (");
					sb.append(organization.getPrivateLayoutsPageCount());
					sb.append(")");
					sb.append("</a>");
				}
				else {
					sb.append(LanguageUtil.get(pageContext, "private-pages"));
					sb.append(" (0)");
				}
			}

			if ((stagingGroup != null) && (tabs1.equals("my-organizations")) && GroupPermissionUtil.contains(permissionChecker, organization.getGroup().getGroupId(), ActionKeys.MANAGE_LAYOUTS)) {
				rowURL.setParameter("groupId", String.valueOf(stagingGroup.getGroupId()));
				rowURL.setParameter("privateLayout", Boolean.TRUE.toString());

				if (stagingGroup.getPrivateLayoutsPageCount() > 0) {
					sb.append(" / ");
					sb.append("<a href=\"");
					sb.append(rowURL.toString());
					sb.append("\">");
					sb.append(LanguageUtil.get(pageContext, "staging"));
					sb.append("</a>");
				}
			}
		}

		row.addText(sb.toString());

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>