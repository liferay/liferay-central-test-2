<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

<%@ include file="/init.jsp" %>

<%
Group group = (Group)request.getAttribute("edit_site_assignments.jsp-group");

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_site_assignments.jsp-portletURL");

PortletURL viewOrganizationsURL = renderResponse.createRenderURL();

viewOrganizationsURL.setParameter("mvcPath", "/view.jsp");
viewOrganizationsURL.setParameter("tabs1", "organizations");
viewOrganizationsURL.setParameter("tabs2", "current");
viewOrganizationsURL.setParameter("redirect", currentURL);
viewOrganizationsURL.setParameter("groupId", String.valueOf(group.getGroupId()));

OrganizationSearch organizationSearch = new OrganizationSearch(renderRequest, PortletURLUtil.clone(viewOrganizationsURL, renderResponse));

organizationSearch.setEmptyResultsMessage("no-organization-was-found-that-is-a-member-of-this-site");

RowChecker rowChecker = new EmptyOnClickRowChecker(renderResponse);

OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)organizationSearch.getSearchTerms();

long parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;

LinkedHashMap<String, Object> organizationParams = new LinkedHashMap<String, Object>();

organizationParams.put("groupOrganization", Long.valueOf(group.getGroupId()));
organizationParams.put("organizationsGroups", Long.valueOf(group.getGroupId()));

int organizationsCount = OrganizationLocalServiceUtil.searchCount(company.getCompanyId(), parentOrganizationId, searchTerms.getKeywords(), searchTerms.getType(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams);

organizationSearch.setTotal(organizationsCount);

List<Organization> organizations = OrganizationLocalServiceUtil.search(company.getCompanyId(), parentOrganizationId, searchTerms.getKeywords(), searchTerms.getType(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams, organizationSearch.getStart(), organizationSearch.getEnd(), organizationSearch.getOrderByComparator());

organizationSearch.setResults(organizations);
%>

<c:if test="<%= organizationsCount > 0 %>">
	<liferay-frontend:management-bar
		checkBoxContainerId="organizationsSearchContainer"
		includeCheckBox="<%= true %>"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
				portletURL="<%= PortletURLUtil.clone(viewOrganizationsURL, renderResponse) %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= PortletURLUtil.clone(viewOrganizationsURL, renderResponse) %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= orderByCol %>"
				orderByType="<%= orderByType %>"
				orderColumns='<%= new String[] {"name", "type"} %>'
				portletURL="<%= PortletURLUtil.clone(viewOrganizationsURL, renderResponse) %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button href="javascript:;" iconCssClass="icon-trash" id="deleteSelectedOrganizations" />
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>
</c:if>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="organizations" />
	<aui:input name="tabs2" type="hidden" value="current" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="groupId" type="hidden" value="<%= String.valueOf(group.getGroupId()) %>" />
	<aui:input name="addOrganizationIds" type="hidden" />
	<aui:input name="removeOrganizationIds" type="hidden" />

	<liferay-ui:search-container
		id="organizations"
		rowChecker="<%= rowChecker %>"
		searchContainer="<%= organizationSearch %>"
	>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.Organization"
			cssClass="selectable"
			escapedModel="<%= true %>"
			keyProperty="organizationId"
			modelVar="organization"
		>

			<%
			boolean selectOrganizations = false;
			%>

			<%@ include file="/organization_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">

	<%
	viewOrganizationsURL.setParameter("tabs2", "available");
	%>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "assign-organizations") %>' url="<%= viewOrganizationsURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script sandbox="<%= true %>">
	var Util = Liferay.Util;

	var form = $(document.<portlet:namespace />fm);

	$('#<portlet:namespace />deleteSelectedOrganizations').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				<portlet:actionURL name="editGroupOrganizations" var="deleteOrganizationsURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:actionURL>

				form.fm('removeOrganizationIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

				submitForm(form, '<%= deleteOrganizationsURL %>');
			}
		}
	);
</aui:script>