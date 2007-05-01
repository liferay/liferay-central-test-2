<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
String tabs2 = ParamUtil.getString(request, "tabs2", "users");
String tabs3 = ParamUtil.getString(request, "tabs3", "current");

String cur = ParamUtil.getString(request, "cur");

PasswordPolicy passwordPolicy = (PasswordPolicy)request.getAttribute(WebKeys.PASSWORD_POLICY);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/enterprise_admin/edit_password_policy_assignments");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("tabs3", tabs3);
portletURL.setParameter("passwordPolicyId", String.valueOf(passwordPolicy.getPasswordPolicyId()));
%>

<script type="text/javascript">
	function <portlet:namespace />updatePasswordPolicyOrganizations(redirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "password_policy_organizations";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
		document.<portlet:namespace />fm.<portlet:namespace />addOrganizationIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		document.<portlet:namespace />fm.<portlet:namespace />removeOrganizationIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updatePasswordPolicyUsers(redirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "password_policy_users";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
		document.<portlet:namespace />fm.<portlet:namespace />addUserIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		document.<portlet:namespace />fm.<portlet:namespace />removeUserIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_password_policy_assignments" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />tabs1" type="hidden" value="<%= tabs1 %>">
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= tabs2 %>">
<input name="<portlet:namespace />tabs3" type="hidden" value="<%= tabs3 %>">
<input name="<portlet:namespace />redirect" type="hidden" value="">
<input name="<portlet:namespace />passwordPolicyId" type="hidden" value="<%= String.valueOf(passwordPolicy.getPasswordPolicyId()) %>">

<liferay-util:include page="/html/portlet/enterprise_admin/tabs1.jsp">
	<liferay-util:param name="tabs1" value="password-policies" />
</liferay-util:include>

<%= LanguageUtil.get(pageContext, "edit-assignments-for-password-policy") %>: <%= passwordPolicy.getName() %>

<br /><br />

<liferay-ui:tabs
	names="users,organizations,locations"
	param="tabs2"
	url="<%= portletURL.toString() %>"
/>

<c:choose>
	<c:when test='<%= tabs2.equals("users") %>'>
		<input name="<portlet:namespace />addUserIds" type="hidden" value="">
		<input name="<portlet:namespace />removeUserIds" type="hidden" value="">

		<liferay-ui:tabs
			names="current,available"
			param="tabs3"
			url="<%= portletURL.toString() %>"
		/>

		<%
		UserSearch searchContainer = new UserSearch(renderRequest, portletURL);

		searchContainer.setRowChecker(new UserPasswordPolicyChecker(renderResponse, passwordPolicy));
		%>

		<liferay-ui:search-form
			page="/html/portlet/enterprise_admin/user_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<%
		UserSearchTerms searchTerms = (UserSearchTerms)searchContainer.getSearchTerms();

		LinkedHashMap userParams = new LinkedHashMap();

		if (tabs3.equals("current")) {
			userParams.put("usersPasswordPolicies", new Long(passwordPolicy.getPasswordPolicyId()));
		}

		int total = UserLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getFirstName(), searchTerms.getMiddleName(), searchTerms.getLastName(), searchTerms.getScreenName(), searchTerms.getEmailAddress(), searchTerms.isActive(), userParams, searchTerms.isAndOperator());

		searchContainer.setTotal(total);

		List results = UserLocalServiceUtil.search(company.getCompanyId(), searchTerms.getFirstName(), searchTerms.getMiddleName(), searchTerms.getLastName(), searchTerms.getScreenName(), searchTerms.getEmailAddress(), searchTerms.isActive(), userParams, searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd(), new ContactLastNameComparator(true));

		searchContainer.setResults(results);
		%>

		<div class="separator"></div>

		<input type="button" value='<%= LanguageUtil.get(pageContext, "update-associations") %>' onClick="<portlet:namespace />updatePasswordPolicyUsers('<%= portletURL.toString() %>&<portlet:namespace />cur=<%= cur %>');">

		<br /><br />

		<%
		List headerNames = new ArrayList();

		headerNames.add("name");
		headerNames.add("screen-name");
		headerNames.add("email-address");

		searchContainer.setHeaderNames(headerNames);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			User user2 = (User)results.get(i);

			ResultRow row = new ResultRow(user2, user2.getPrimaryKey(), i);

			// Name, screen name, and email address

			row.addText(user2.getFullName());
			row.addText(user2.getScreenName());
			row.addText(user2.getEmailAddress());

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</c:when>
	<c:when test='<%= tabs2.equals("organizations") || tabs2.equals("locations") %>'>
		<input name="<portlet:namespace />addOrganizationIds" type="hidden" value="">
		<input name="<portlet:namespace />removeOrganizationIds" type="hidden" value="">

		<liferay-ui:tabs
			names="current,available"
			param="tabs3"
			url="<%= portletURL.toString() %>"
		/>

		<%
		OrganizationSearch searchContainer = new OrganizationSearch(renderRequest, portletURL);

		searchContainer.setRowChecker(new OrganizationPasswordPolicyChecker(renderResponse, passwordPolicy));
		%>

		<liferay-ui:search-form
			page="/html/portlet/enterprise_admin/organization_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<%
		boolean rootOrganization = tabs2.equals("organizations");

		OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)searchContainer.getSearchTerms();

		String parentOrganizationId = OrganizationImpl.DEFAULT_PARENT_ORGANIZATION_ID;
		String parentOrganizationComparator = StringPool.EQUAL;

		if (!rootOrganization) {
			parentOrganizationComparator = StringPool.NOT_EQUAL;
		}

		LinkedHashMap organizationParams = new LinkedHashMap();

		if (tabs3.equals("current")) {
			organizationParams.put("organizationsPasswordPolicies", new Long(passwordPolicy.getPasswordPolicyId()));
		}

		int total = OrganizationLocalServiceUtil.searchCount(company.getCompanyId(), parentOrganizationId, parentOrganizationComparator, searchTerms.getName(), searchTerms.getStreet(), searchTerms.getCity(), searchTerms.getZip(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams, searchTerms.isAndOperator());

		searchContainer.setTotal(total);

		List results = OrganizationLocalServiceUtil.search(company.getCompanyId(), parentOrganizationId, parentOrganizationComparator, searchTerms.getName(), searchTerms.getStreet(), searchTerms.getCity(), searchTerms.getZip(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams, searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);
		%>

		<div class="separator"></div>

		<input type="button" value='<%= LanguageUtil.get(pageContext, "update-associations") %>' onClick="<portlet:namespace />updatePasswordPolicyOrganizations('<%= portletURL.toString() %>&<portlet:namespace />cur=<%= cur %>');">

		<br /><br />

		<%
		List headerNames = new ArrayList();

		headerNames.add("name");
		headerNames.add("city");

		searchContainer.setHeaderNames(headerNames);

		if (!rootOrganization) {
			searchContainer.setEmptyResultsMessage(OrganizationSearch.EMPTY_RESULTS_MESSAGE_2);
		}

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			Organization organization = (Organization)results.get(i);

			ResultRow row = new ResultRow(organization, organization.getPrimaryKey().toString(), i);

			// Name

			row.addText(organization.getName());

			// Address

			Address address = organization.getAddress();

			row.addText(address.getCity());

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</c:when>
</c:choose>

</form>