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
int step = ParamUtil.getInteger(request, "step");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/enterprise_admin/select_organization_role");

User selUser = null;
long uniqueOrganizationId = 0;

List<Organization> organizations = null;

if (step == 1) {
	long[] organizationIds = StringUtil.split(ParamUtil.getString(request, "organizationIds"), 0L);

	organizations = OrganizationLocalServiceUtil.getOrganizations(organizationIds);

	if (filterManageableOrganizations) {
		organizations = EnterpriseAdminUtil.filterOrganizations(permissionChecker, organizations);
	}

	if (organizations.size() == 1) {
		step = 2;

		uniqueOrganizationId = organizations.get(0).getOrganizationId();
	}
}
%>

<aui:form method="post" name="fm">
	<c:choose>
		<c:when test="<%= step == 1 %>">
			<aui:script>
				function <portlet:namespace />selectOrganization(organizationId) {
					document.<portlet:namespace />fm.<portlet:namespace />organizationId.value = organizationId;

					submitForm(document.<portlet:namespace />fm, "<%= portletURL.toString() %>");
				}
			</aui:script>

			<aui:input name="step" type="hidden" value="2" />
			<aui:input name="organizationId" type="hidden" />

			<liferay-ui:tabs names="organization-roles" />

			<div class="portlet-msg-info">
				<liferay-ui:message key="please-select-an-organization-to-which-you-will-assign-an-organization-role" />
			</div>

			<liferay-ui:search-container
				searchContainer="<%= new OrganizationSearch(renderRequest, portletURL) %>"
			>
				<liferay-ui:search-container-results>

					<%
					total = organizations.size();
					results = ListUtil.subList(organizations, searchContainer.getStart(), searchContainer.getEnd());

					pageContext.setAttribute("results", results);
					pageContext.setAttribute("total", total);
					%>

				</liferay-ui:search-container-results>

				<liferay-ui:search-container-row
					className="com.liferay.portal.model.Organization"
					escapedModel="<%= true %>"
					keyProperty="organizationId"
					modelVar="organization"
				>

					<%
					StringBuilder sb = new StringBuilder();

					sb.append("javascript:");
					sb.append(renderResponse.getNamespace());
					sb.append("selectOrganization('");
					sb.append(organization.getOrganizationId());
					sb.append("');");

					String rowHREF = sb.toString();
					%>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="name"
						orderable="<%= true %>"
						property="name"
					/>

					<liferay-ui:search-container-column-text
						buffer="buffer"
						href="<%= rowHREF %>"
						name="parent-organization"
					>
						<%
						String parentOrganizationName = StringPool.BLANK;

						if (organization.getParentOrganizationId() > 0) {
							try {
								Organization parentOrganization = OrganizationLocalServiceUtil.getOrganization(organization.getParentOrganizationId());

								parentOrganizationName = parentOrganization.getName();
							}
							catch (Exception e) {
							}
						}

						buffer.append(HtmlUtil.escape(parentOrganizationName));
						%>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="type"
						orderable="<%= true %>"
						value="<%= LanguageUtil.get(pageContext, organization.getType()) %>"
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="city"
						property="address.city"
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="region"
						property="address.region.name"
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="country"
						property="address.country.name"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>
		</c:when>

		<c:when test="<%= step == 2 %>">
			<liferay-ui:tabs names="organization-roles" />

			<%
			long organizationId = ParamUtil.getLong(request, "organizationId", uniqueOrganizationId);

			Organization organization = OrganizationServiceUtil.getOrganization(organizationId);

			portletURL.setParameter("step", "1");

			String breadcrumbs = "<a href=\"" + portletURL.toString() + "\">" + LanguageUtil.get(pageContext, "organizations") + "</a> &raquo; " + HtmlUtil.escape(organization.getName());
			%>

			<div class="breadcrumbs">
				<%= breadcrumbs %>
			</div>

			<liferay-ui:search-container
				headerNames="name"
				searchContainer="<%= new RoleSearch(renderRequest, portletURL) %>"
			>
				<liferay-ui:search-form
					page="/html/portlet/enterprise_admin/role_search.jsp"
				/>

				<%
				RoleSearchTerms searchTerms = (RoleSearchTerms)searchContainer.getSearchTerms();
				%>

				<liferay-ui:search-container-results>

					<%
					if (filterManageableRoles) {

						List<Role> roles = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), RoleConstants.TYPE_ORGANIZATION, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

						roles = EnterpriseAdminUtil.filterRoles(permissionChecker, roles);

						total = roles.size();
						results = ListUtil.subList(roles, searchContainer.getStart(), searchContainer.getEnd());
					}
					else {
						results = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), RoleConstants.TYPE_ORGANIZATION, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
						total = RoleLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), RoleConstants.TYPE_ORGANIZATION);
					}

					pageContext.setAttribute("results", results);
					pageContext.setAttribute("total", total);
					%>

				</liferay-ui:search-container-results>

				<liferay-ui:search-container-row
					className="com.liferay.portal.model.Role"
					escapedModel="<%= false %>"
					keyProperty="roleId"
					modelVar="role"
				>
					<liferay-util:param name="className" value="<%= EnterpriseAdminUtil.getCssClassName(role) %>" />
					<liferay-util:param name="classHoverName" value="<%= EnterpriseAdminUtil.getCssClassName(role) %>" />

					<%
					StringBuilder sb = new StringBuilder();

					sb.append("javascript:opener.");
					sb.append(renderResponse.getNamespace());
					sb.append("selectRole('");
					sb.append(role.getRoleId());
					sb.append("', '");
					sb.append(UnicodeFormatter.toString(role.getTitle(locale)));
					sb.append("', '");
					sb.append("organizationRoles");
					sb.append("', '");
					sb.append(UnicodeFormatter.toString(organization.getGroup().getDescriptiveName()));
					sb.append("', '");
					sb.append(organization.getGroup().getGroupId());
					sb.append("');");
					sb.append("window.close();");

					String rowHREF = sb.toString();
					%>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="title"
						value="<%= HtmlUtil.escape(role.getTitle(locale)) %>"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>

			<aui:script>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
			</aui:script>
		</c:when>
	</c:choose>
</aui:form>