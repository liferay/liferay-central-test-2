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
PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");

boolean passwordPolicyEnabled = LDAPSettingsUtil.isPasswordPolicyEnabled(company.getCompanyId());
%>

<c:if test="<%= passwordPolicyEnabled %>">
	<div class="portlet-msg-info">
		<liferay-ui:message key="you-are-using-ldaps-password-policy" />
	</div>
</c:if>

<liferay-util:include page="/html/portlet/enterprise_admin/password_policy/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value="view-all" />
</liferay-util:include>

<%
PasswordPolicySearch searchContainer = new PasswordPolicySearch(renderRequest, portletURL);

List headerNames = searchContainer.getHeaderNames();

headerNames.add(StringPool.BLANK);
%>

<c:if test="<%= !passwordPolicyEnabled %>">
	<liferay-ui:search-form
		page="/html/portlet/enterprise_admin/password_policy_search.jsp"
		searchContainer="<%= searchContainer %>"
	/>
</c:if>

<c:if test="<%= !passwordPolicyEnabled && windowState.equals(WindowState.MAXIMIZED) %>">

	<%
	PasswordPolicySearchTerms searchTerms = (PasswordPolicySearchTerms)searchContainer.getSearchTerms();

	int total = PasswordPolicyLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName());

	searchContainer.setTotal(total);

	List results = PasswordPolicyLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

	searchContainer.setResults(results);

	PortletURL passwordPoliciesRedirect = PortletURLUtil.clone(portletURL, renderResponse);

	passwordPoliciesRedirect.setParameter(searchContainer.getCurParam(), String.valueOf(searchContainer.getCur()));
	%>

	<aui:input name="passwordPoliciesRedirect" type="hidden" value="<%= passwordPoliciesRedirect.toString() %>" />

	<div class="separator"><!-- --></div>

	<%
	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		PasswordPolicy passwordPolicy = (PasswordPolicy)results.get(i);

		ResultRow row = new ResultRow(passwordPolicy, passwordPolicy.getPasswordPolicyId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setWindowState(WindowState.MAXIMIZED);

		rowURL.setParameter("struts_action", "/enterprise_admin/edit_password_policy");
		rowURL.setParameter("redirect", searchContainer.getIteratorURL().toString());
		rowURL.setParameter("passwordPolicyId", String.valueOf(passwordPolicy.getPasswordPolicyId()));

		// Name

		row.addText(HtmlUtil.escape(passwordPolicy.getName()), rowURL);

		// Description

		row.addText(HtmlUtil.escape(passwordPolicy.getDescription()), rowURL);

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/enterprise_admin/password_policy_action.jsp");

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</c:if>