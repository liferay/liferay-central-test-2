<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/directory/init.jsp" %>

<%
PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");
%>

<liferay-ui:search-container
	searchContainer="<%= new OrganizationSearch(renderRequest, portletURL) %>"
>
	<input name="<portlet:namespace />organizationsRedirect" type="hidden" value="<%= portletURL.toString() %>" />

	<liferay-ui:search-form
		page="/html/portlet/directory/organization_search.jsp"
	/>

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">

		<%
		OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)searchContainer.getSearchTerms();

		LinkedHashMap organizationParams = new LinkedHashMap();

		long parentOrganizationId = ParamUtil.getLong(request, "parentOrganizationId", OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);

		if (parentOrganizationId <= 0) {
			parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;
		}
		%>

		<liferay-ui:search-container-results>
			<%@ include file="/html/portlet/enterprise_admin/organization_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.Organization"
			keyProperty="organizationId"
			modelVar="organization"
		>
			<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="rowURL">
				<portlet:param name="struts_action" value="/directory/edit_organization" />
				<portlet:param name="redirect" value="<%= searchContainer.getIteratorURL().toString() %>" />
				<portlet:param name="organizationId" value="<%= String.valueOf(organization.getOrganizationId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="name"
				orderable="<%= true %>"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				buffer="buffer"
				href="<%= rowURL %>"
				name="parent-organization"
			>

				<%
				if (organization.getParentOrganizationId() > 0) {
					try {
						Organization parentOrganization = OrganizationLocalServiceUtil.getOrganization(organization.getParentOrganizationId());

						buffer.append(parentOrganization.getName());
					}
					catch (Exception e) {
					}
				}
				%>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="type"
				orderable="<%= true %>"
				value="<%= LanguageUtil.get(pageContext, organization.getType()) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="city"
				value="<%= organization.getAddress().getCity() %>"
			/>

			<liferay-ui:search-container-column-text
				buffer="buffer"
				href="<%= rowURL %>"
				name="region"
			>

				<%
				Address address = organization.getAddress();

				Region region = address.getRegion();

				String regionName = region.getName();

				if (Validator.isNull(regionName)) {
					try {
						region = RegionServiceUtil.getRegion(organization.getRegionId());

						regionName = LanguageUtil.get(pageContext, region.getName());
					}
					catch (NoSuchRegionException nsce) {
					}
				}

				buffer.append(regionName);
				%>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				buffer="buffer"
				href="<%= rowURL %>"
				name="country"
			>

				<%
				Address address = organization.getAddress();

				Country country = address.getCountry();

				String countryName = country.getName();

				if (Validator.isNull(countryName)) {
					try {
						country = CountryServiceUtil.getCountry(organization.getCountryId());

						countryName = LanguageUtil.get(pageContext, country.getName());
					}
					catch (NoSuchCountryException nsce) {
					}
				}

				buffer.append(countryName);
				%>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/html/portlet/directory/organization_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<div class="separator"><!-- --></div>

		<liferay-ui:search-iterator />
	</c:if>
</liferay-ui:search-container>