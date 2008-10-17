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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<form method="post" name="<portlet:namespace />fm">

<liferay-ui:tabs names="roles" />

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/enterprise_admin/select_role");

RoleSearch searchContainer = new RoleSearch(renderRequest, portletURL);

List<String> headerNames = new ArrayList<String>();

headerNames.add("name");

searchContainer.setHeaderNames(headerNames);

searchContainer.setEmptyResultsMessage("there-is-no-role-available-for-assignment");

RoleSearchTerms searchTerms = (RoleSearchTerms)searchContainer.getSearchTerms();

int total = RoleLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), searchTerms.getTypeObj());

searchContainer.setTotal(total);

List<Role> results = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), searchTerms.getTypeObj(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

results = EnterpriseAdminUtil.filterRoles(permissionChecker, results);

searchContainer.setResults(results);
%>

<c:if test="<%= results.size() >0 %>">
	<liferay-ui:search-form
		page="/html/portlet/enterprise_admin/role_search.jsp"
		searchContainer="<%= searchContainer %>"
	/>

	<div class="separator"><!-- --></div>
</c:if>

<%
List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	Role role = results.get(i);

	ResultRow row = new ResultRow(role, role.getRoleId(), i);

	StringBuilder sb = new StringBuilder();

	sb.append("javascript: opener.");
	sb.append(renderResponse.getNamespace());
	sb.append("selectRole('");
	sb.append(role.getRoleId());
	sb.append("', '");
	sb.append(UnicodeFormatter.toString(role.getName()));
	sb.append("');");
	sb.append("window.close();");

	String rowHREF = sb.toString();

	// Name

	row.addText(role.getName(), rowHREF);

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

</form>

<script type="text/javascript">
	Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
</script>