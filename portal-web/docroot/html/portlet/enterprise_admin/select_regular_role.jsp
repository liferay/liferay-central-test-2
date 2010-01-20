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

<aui:form method="post" name="fm">
	<liferay-ui:tabs names="roles" />

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/enterprise_admin/select_regular_role");
	%>

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
				List<Role> roles = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), RoleConstants.TYPE_REGULAR, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

				roles = EnterpriseAdminUtil.filterRoles(permissionChecker, roles);

				total = roles.size();
				results = ListUtil.subList(roles, searchContainer.getStart(), searchContainer.getEnd());
			}
			else {
				results = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), RoleConstants.TYPE_REGULAR, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
				total = RoleLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), RoleConstants.TYPE_REGULAR);
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
			sb.append("roles");
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
</aui:form>

<aui:script>
	Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
</aui:script>