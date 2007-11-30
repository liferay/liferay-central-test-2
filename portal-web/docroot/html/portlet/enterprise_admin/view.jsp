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
String tabs2 = ParamUtil.getString(request, "tabs2");
String tabs3 = ParamUtil.getString(request, "tabs3");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/enterprise_admin/view");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("tabs3", tabs3);

pageContext.setAttribute("portletURL", portletURL);

String portletURLString = portletURL.toString();

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<script type="text/javascript">
	function <portlet:namespace />deleteOrganizations() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-organizations") %>')) {
			document.<portlet:namespace />fm.method = "post";
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = document.<portlet:namespace />fm.<portlet:namespace />organizationsRedirect.value;
			document.<portlet:namespace />fm.<portlet:namespace />deleteOrganizationIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_organization" /></portlet:actionURL>");
		}
	}

	function <portlet:namespace />deleteUserGroups() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-user-groups") %>')) {
			document.<portlet:namespace />fm.method = "post";
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = document.<portlet:namespace />fm.<portlet:namespace />userGroupsRedirect.value;
			document.<portlet:namespace />fm.<portlet:namespace />deleteUserGroupIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_user_group" /></portlet:actionURL>");
		}
	}

	function <portlet:namespace />deleteUsers(cmd) {
		var deleteUsers = true;

		if (cmd == "<%= Constants.DEACTIVATE %>") {
			if (!confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-deactivate-the-selected-users") %>')) {
				deleteUsers = false;
			}
		}
		else if (cmd == "<%= Constants.DELETE %>") {
			if (!confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-permanently-delete-the-selected-users") %>')) {
				deleteUsers = false;
			}
		}

		if (deleteUsers) {
			document.<portlet:namespace />fm.method = "post";
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;
			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = document.<portlet:namespace />fm.<portlet:namespace />usersRedirect.value;
			document.<portlet:namespace />fm.<portlet:namespace />deleteUserIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_user" /></portlet:actionURL>");
		}
	}

	function <portlet:namespace />saveCompany(cmd) {
		document.<portlet:namespace />fm.method = "post";
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/view" /><portlet:param name="tabs1" value="<%= tabs1 %>" /><portlet:param name="tabs2" value="<%= tabs2 %>" /><portlet:param name="tabs3" value="<%= tabs3 %>" /></portlet:renderURL>";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_company" /></portlet:actionURL>");
	}

	function <portlet:namespace />saveSettings(cmd) {
		document.<portlet:namespace />fm.method = "post";
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/view" /><portlet:param name="tabs1" value="<%= tabs1 %>" /><portlet:param name="tabs2" value="<%= tabs2 %>" /><portlet:param name="tabs3" value="<%= tabs3 %>" /></portlet:renderURL>";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_settings" /></portlet:actionURL>");
	}
</script>

<form action="<%= portletURLString %>" method="get" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">
<liferay-portlet:renderURLParams varImpl="portletURL" />
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />tabs1" type="hidden" value="<%= tabs1 %>" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= tabs2 %>" />
<input name="<portlet:namespace />tabs3" type="hidden" value="<%= tabs3 %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="" />

<liferay-util:include page="/html/portlet/enterprise_admin/tabs1.jsp" />

<c:choose>
	<c:when test='<%= tabs1.equals("users") %>'>
		<liferay-util:include page="/html/portlet/enterprise_admin/view_users.jsp" />
	</c:when>
	<c:when test='<%= tabs1.equals("organizations") %>'>
		<liferay-util:include page="/html/portlet/enterprise_admin/view_organizations.jsp" />
	</c:when>
	<c:when test='<%= tabs1.equals("user-groups") %>'>
		<input name="<portlet:namespace />deleteUserGroupIds" type="hidden" value="" />

		<liferay-ui:error exception="<%= RequiredUserGroupException.class %>" message="you-cannot-delete-user-groups-that-have-users" />

		<%
		UserGroupSearch searchContainer = new UserGroupSearch(renderRequest, portletURL);

		List headerNames = searchContainer.getHeaderNames();

		headerNames.add(StringPool.BLANK);

		if (portletName.equals(PortletKeys.ENTERPRISE_ADMIN) && PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_USER_GROUP)) {
			RowChecker rowChecker = new RowChecker(renderResponse);

			searchContainer.setRowChecker(rowChecker);
		}
		%>

		<liferay-ui:search-form
			page="/html/portlet/enterprise_admin/user_group_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">

			<%
			UserGroupSearchTerms searchTerms = (UserGroupSearchTerms)searchContainer.getSearchTerms();

			int total = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), null);

			searchContainer.setTotal(total);

			List results = UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), null, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);

			portletURL.setParameter(searchContainer.getCurParam(), String.valueOf(searchContainer.getCurValue()));
			%>

			<input name="<portlet:namespace />userGroupsRedirect" type="hidden" value="<%= portletURL.toString() %>" />

			<div class="separator"><!-- --></div>

			<c:if test="<%= portletName.equals(PortletKeys.ENTERPRISE_ADMIN) && PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_USER_GROUP) %>">
				<input type="button" value="<liferay-ui:message key="delete" />" onClick="<portlet:namespace />deleteUserGroups();" />

				<br /><br />
			</c:if>

			<%
			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				UserGroup userGroup = (UserGroup)results.get(i);

				ResultRow row = new ResultRow(userGroup, userGroup.getUserGroupId(), i);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setWindowState(WindowState.MAXIMIZED);

				rowURL.setParameter("struts_action", "/enterprise_admin/edit_user_group");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("userGroupId", String.valueOf(userGroup.getUserGroupId()));

				// Name

				row.addText(userGroup.getName(), rowURL);

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/enterprise_admin/user_group_action.jsp");

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		</c:if>
	</c:when>
	<c:when test='<%= tabs1.equals("roles") %>'>
		<liferay-ui:error exception="<%= RequiredRoleException.class %>" message="you-cannot-delete-a-system-role" />

		<%
		RoleSearch searchContainer = new RoleSearch(renderRequest, portletURL);

		List headerNames = searchContainer.getHeaderNames();

		headerNames.add("type");
		headerNames.add(StringPool.BLANK);
		%>

		<liferay-ui:search-form
			page="/html/portlet/enterprise_admin/role_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">

			<%
			RoleSearchTerms searchTerms = (RoleSearchTerms)searchContainer.getSearchTerms();

			int total = RoleLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), searchTerms.getTypeObj());

			searchContainer.setTotal(total);

			List results = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), searchTerms.getTypeObj(), searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);

			portletURL.setParameter(searchContainer.getCurParam(), String.valueOf(searchContainer.getCurValue()));
			%>

			<input name="<portlet:namespace />rolesRedirect" type="hidden" value="<%= portletURL.toString() %>" />

			<div class="separator"><!-- --></div>

			<%
			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				Role role = (Role)results.get(i);

				ResultRow row = new ResultRow(role, role.getRoleId(), i);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setWindowState(WindowState.MAXIMIZED);

				rowURL.setParameter("struts_action", "/enterprise_admin/edit_role");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("roleId", String.valueOf(role.getRoleId()));

				// Name

				row.addText(role.getName(), rowURL);

				// Type

				row.addText(LanguageUtil.get(pageContext, role.getTypeLabel()), rowURL);

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/enterprise_admin/role_action.jsp");

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		</c:if>
	</c:when>
	<c:when test='<%= tabs1.equals("password-policies") %>'>

		<%
		boolean passwordPolicyEnabled = PortalLDAPUtil.isPasswordPolicyEnabled(company.getCompanyId());
		%>

		<c:if test="<%= passwordPolicyEnabled %>">
			<liferay-ui:message key="you-are-using-ldaps-password-policy" />
		</c:if>

		<%
		PasswordPolicySearch searchContainer = new PasswordPolicySearch(renderRequest, portletURL);

		List headerNames = searchContainer.getHeaderNames();

		headerNames.add("description");
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

			List results = PasswordPolicyLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);

			PortletURL passwordPoliciesRedirect = PortletURLUtil.clone(portletURL, renderResponse);

			passwordPoliciesRedirect.setParameter(searchContainer.getCurParam(), String.valueOf(searchContainer.getCurValue()));
			%>

			<input name="<portlet:namespace />passwordPoliciesRedirect" type="hidden" value="<%= passwordPoliciesRedirect.toString() %>" />

			<div class="separator"><!-- --></div>

			<%
			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				PasswordPolicy passwordPolicy = (PasswordPolicy)results.get(i);

				ResultRow row = new ResultRow(passwordPolicy, passwordPolicy.getPasswordPolicyId(), i);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setWindowState(WindowState.MAXIMIZED);

				rowURL.setParameter("struts_action", "/enterprise_admin/edit_password_policy");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("passwordPolicyId", String.valueOf(passwordPolicy.getPasswordPolicyId()));

				// Name

				row.addText(passwordPolicy.getName(), rowURL);

				// Description

				row.addText(passwordPolicy.getDescription(), rowURL);

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/enterprise_admin/password_policy_action.jsp");

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		</c:if>
	</c:when>
	<c:when test='<%= tabs1.equals("settings") %>'>
		<liferay-ui:tabs
			names="general,authentication,default-user-associations,reserved-screen-names,mail-host-names,email-notifications"
			param="tabs2"
			url="<%= portletURLString %>"
		/>

		<c:choose>
			<c:when test='<%= tabs2.equals("authentication") %>'>
				<liferay-ui:tabs
					names="general,ldap,cas,ntlm,open-id,open-sso"
					param="tabs3"
					url="<%= portletURLString %>"
				/>

				<c:choose>
					<c:when test='<%= tabs3.equals("ldap") %>'>
						<%@ include file="/html/portlet/enterprise_admin/authentication_ldap.jspf" %>
					</c:when>
					<c:when test='<%= tabs3.equals("cas") %>'>
						<table class="lfr-table">
						<tr>
							<td>
								<liferay-ui:message key="enabled" />
							</td>
							<td>
								<liferay-ui:input-checkbox param="enabled" defaultValue='<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsUtil.CAS_AUTH_ENABLED) %>' />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="import-cas-users-from-ldap" />

								<liferay-ui:icon-help message="import-cas-users-from-ldap-help" />
							</td>
							<td>
								<liferay-ui:input-checkbox param="importFromLdap" defaultValue='<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsUtil.CAS_IMPORT_FROM_LDAP) %>' />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="login-url" />
							</td>
							<td>
								<input class="lfr-input-text" name="<portlet:namespace />loginUrl" type="text" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.CAS_LOGIN_URL) %>" />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="logout-url" />
							</td>
							<td>
								<input class="lfr-input-text" name="<portlet:namespace />logoutUrl" type="text" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.CAS_LOGOUT_URL) %>" />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="service-url" />
							</td>
							<td>
								<input class="lfr-input-text" name="<portlet:namespace />serviceUrl" type="text" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.CAS_SERVICE_URL) %>" />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="validate-url" />
							</td>
							<td>
								<input class="lfr-input-text" name="<portlet:namespace />validateUrl" type="text" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.CAS_VALIDATE_URL) %>" />
							</td>
						</tr>
						</table>

						<br />

						<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveSettings('updateCAS');" />
					</c:when>
					<c:when test='<%= tabs3.equals("ntlm") %>'>
						<table class="lfr-table">
						<tr>
							<td>
								<liferay-ui:message key="enabled" />
							</td>
							<td>
								<liferay-ui:input-checkbox param="enabled" defaultValue='<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsUtil.NTLM_AUTH_ENABLED) %>' />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="domain-controller" />
							</td>
							<td>
								<input class="lfr-input-text" name="<portlet:namespace />domainController" type="text" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.NTLM_DOMAIN_CONTROLLER) %>" />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="domain" />
							</td>
							<td>
								<input class="lfr-input-text" name="<portlet:namespace />domain" type="text" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.NTLM_DOMAIN) %>" />
							</td>
						</tr>
						</table>

						<br />

						<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveSettings('updateNtlm');" />
					</c:when>
					<c:when test='<%= tabs3.equals("open-id") %>'>
						<table class="lfr-table">
						<tr>
							<td>
								<liferay-ui:message key="enabled" />
							</td>
							<td>
								<liferay-ui:input-checkbox param="enabled" defaultValue='<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsUtil.OPEN_ID_AUTH_ENABLED) %>' />
							</td>
						</tr>
						</table>

						<br />

						<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveSettings('updateOpenId');" />
					</c:when>
					<c:when test='<%= tabs3.equals("open-sso") %>'>
						<table class="lfr-table">
						<tr>
							<td>
								<liferay-ui:message key="enabled" />
							</td>
							<td>
								<liferay-ui:input-checkbox param="enabled" defaultValue='<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsUtil.OPEN_SSO_AUTH_ENABLED) %>' />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="login-url" />
							</td>
							<td>
								<input class="lfr-input-text" name="<portlet:namespace />loginUrl" type="text" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.OPEN_SSO_LOGIN_URL) %>" />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="logout-url" />
							</td>
							<td>
								<input class="lfr-input-text" name="<portlet:namespace />logoutUrl" type="text" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.OPEN_SSO_LOGOUT_URL) %>" />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="service-url" />
							</td>
							<td>
								<input class="lfr-input-text" name="<portlet:namespace />serviceUrl" type="text" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.OPEN_SSO_SERVICE_URL) %>" />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="cookie-name" />
							</td>
							<td>
								<input class="lfr-input-text" name="<portlet:namespace />subjectCookieName" type="text" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.OPEN_SSO_SUBJECT_COOKIE_NAME) %>" />
							</td>
						</tr>
						</table>

						<br />

						<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveSettings('updateOpenSSO');" />
					</c:when>
					<c:otherwise>
						<table class="lfr-table">
						<tr>
							<td>
								<liferay-ui:message key="how-do-users-authenticate" />
							</td>
							<td>
								<select name="<portlet:namespace />authType">
									<option <%= company.getAuthType().equals(CompanyImpl.AUTH_TYPE_EA) ? "selected" : "" %> value="<%= CompanyImpl.AUTH_TYPE_EA %>"><liferay-ui:message key="by-email-address" /></option>
									<option <%= company.getAuthType().equals(CompanyImpl.AUTH_TYPE_SN) ? "selected" : "" %> value="<%= CompanyImpl.AUTH_TYPE_SN %>"><liferay-ui:message key="by-screen-name" /></option>
									<option <%= company.getAuthType().equals(CompanyImpl.AUTH_TYPE_ID) ? "selected" : "" %> value="<%= CompanyImpl.AUTH_TYPE_ID %>"><liferay-ui:message key="by-user-id" /></option>
								</select>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<br />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="allow-users-to-automatically-login" />
							</td>
							<td>
								<liferay-ui:input-checkbox param="autoLogin" defaultValue="<%= company.isAutoLogin() %>" />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="allow-users-to-request-forgotten-passwords" />
							</td>
							<td>
								<liferay-ui:input-checkbox param="sendPassword" defaultValue="<%= company.isSendPassword() %>" />
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<br />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="allow-strangers-to-create-accounts" />
							</td>
							<td>
								<liferay-ui:input-checkbox param="strangers" defaultValue="<%= company.isStrangers() %>" />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="allow-strangers-to-create-accounts-with-a-company-email-address" />
							</td>
							<td>
								<liferay-ui:input-checkbox param="strangersWithMx" defaultValue="<%= company.isStrangersWithMx() %>" />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="require-strangers-to-verify-their-email-address" />
							</td>
							<td>
								<liferay-ui:input-checkbox param="strangersVerify" defaultValue="<%= company.isStrangersVerify() %>" />
							</td>
						</tr>
						</table>

						<br />

						<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveSettings('updateSecurity');" />
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:when test='<%= tabs2.equals("default-user-associations") %>'>
				<liferay-ui:message key="enter-the-default-community-names-per-line-that-are-associated-with-newly-created-users" />

				<br /><br />

				<textarea class="lfr-textarea" name="<portlet:namespace />defaultGroupNames"><%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.ADMIN_DEFAULT_GROUP_NAMES) %></textarea>

				<br /><br />

				<liferay-ui:message key="enter-the-default-role-names-per-line-that-are-associated-with-newly-created-users" />

				<br /><br />

				<textarea class="lfr-textarea" name="<portlet:namespace />defaultRoleNames"><%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.ADMIN_DEFAULT_ROLE_NAMES) %></textarea>

				<br /><br />

				<liferay-ui:message key="enter-the-default-user-group-names-per-line-that-are-associated-with-newly-created-users" />

				<br /><br />

				<textarea class="lfr-textarea" name="<portlet:namespace />defaultUserGroupNames"><%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.ADMIN_DEFAULT_USER_GROUP_NAMES) %></textarea>

				<br /><br />

				<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveSettings('updateDefaultGroupsAndRoles');" />
			</c:when>
			<c:when test='<%= tabs2.equals("reserved-screen-names") %>'>
				<liferay-ui:message key="enter-one-screen-name-per-line-to-reserve-the-screen-name" />

				<br /><br />

				<textarea class="lfr-textarea" name="<portlet:namespace />reservedScreenNames"><%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.ADMIN_RESERVED_SCREEN_NAMES) %></textarea>

				<br /><br />

				<liferay-ui:message key="enter-one-user-email-address-per-line-to-reserve-the-user-email-address" />

				<br /><br />

				<textarea class="lfr-textarea" name="<portlet:namespace />reservedEmailAddresses"><%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.ADMIN_RESERVED_EMAIL_ADDRESSES) %></textarea>

				<br /><br />

				<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveSettings('updateReservedUsers');" />
			</c:when>
			<c:when test='<%= tabs2.equals("mail-host-names") %>'>
				<%= LanguageUtil.format(pageContext, "enter-one-mail-host-name-per-line-for-all-additional-mail-host-names-besides-x", company.getMx(), false) %>

				<br /><br />

				<textarea class="lfr-textarea" name="<portlet:namespace />mailHostNames"><%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.ADMIN_MAIL_HOST_NAMES) %></textarea>

				<br /><br />

				<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveSettings('updateMailHostNames');" />
			</c:when>
			<c:when test='<%= tabs2.equals("email-notifications") %>'>
				<script type="text/javascript">

					<%
					String emailFromName = ParamUtil.getString(request, "emailFromName", PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.ADMIN_EMAIL_FROM_NAME));
					String emailFromAddress = ParamUtil.getString(request, "emailFromAddress", PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.ADMIN_EMAIL_FROM_ADDRESS));

					String emailUserAddedSubject = ParamUtil.getString(request, "emailUserAddedSubject", PrefsPropsUtil.getContent(company.getCompanyId(), PropsUtil.ADMIN_EMAIL_USER_ADDED_SUBJECT));
					String emailUserAddedBody = ParamUtil.getString(request, "emailUserAddedBody", PrefsPropsUtil.getContent(company.getCompanyId(), PropsUtil.ADMIN_EMAIL_USER_ADDED_BODY));

					String emailPasswordSentSubject = ParamUtil.getString(request, "emailPasswordSentSubject", PrefsPropsUtil.getContent(company.getCompanyId(), PropsUtil.ADMIN_EMAIL_PASSWORD_SENT_SUBJECT));
					String emailPasswordSentBody = ParamUtil.getString(request, "emailPasswordSentBody", PrefsPropsUtil.getContent(company.getCompanyId(), PropsUtil.ADMIN_EMAIL_PASSWORD_SENT_BODY));

					String editorParam = "";
					String editorContent = "";

					if (tabs3.equals("account-created-notification")) {
						editorParam = "emailUserAddedBody";
						editorContent = emailUserAddedBody;
					}
					else if (tabs3.equals("password-changed-notification")) {
						editorParam = "emailPasswordSentBody";
						editorContent = emailPasswordSentBody;
					}
					%>

					function initEditor() {
						return "<%= UnicodeFormatter.toString(editorContent) %>";
					}

					function <portlet:namespace />saveEmails() {
						document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "updateEmails";

						<c:if test='<%= tabs3.endsWith("-notification") %>'>
							document.<portlet:namespace />fm.<portlet:namespace /><%= editorParam %>.value = parent.<portlet:namespace />editor.getHTML();
						</c:if>

						submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_settings" /></portlet:actionURL>");
					}
				</script>

				<liferay-ui:tabs
					names="general,account-created-notification,password-changed-notification"
					param="tabs3"
					url="<%= portletURLString %>"
				/>

				<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
				<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
				<liferay-ui:error key="emailPasswordSentBody" message="please-enter-a-valid-body" />
				<liferay-ui:error key="emailPasswordSentSubject" message="please-enter-a-valid-subject" />
				<liferay-ui:error key="emailUserAddedBody" message="please-enter-a-valid-body" />
				<liferay-ui:error key="emailUserAddedSubject" message="please-enter-a-valid-subject" />

				<c:choose>
					<c:when test='<%= tabs3.endsWith("-notification") %>'>
						<table class="lfr-table">
						<tr>
							<td>
								<liferay-ui:message key="enabled" />
							</td>
							<td>
								<c:choose>
									<c:when test='<%= tabs3.equals("account-created-notification") %>'>
										<liferay-ui:input-checkbox param="emailUserAddedEnabled" defaultValue="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsUtil.ADMIN_EMAIL_USER_ADDED_ENABLED) %>" />
									</c:when>
									<c:when test='<%= tabs3.equals("password-changed-notification") %>'>
										<liferay-ui:input-checkbox param="emailPasswordSentEnabled" defaultValue="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsUtil.ADMIN_EMAIL_PASSWORD_SENT_ENABLED) %>" />
									</c:when>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<br />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="subject" />
							</td>
							<td>
								<c:choose>
									<c:when test='<%= tabs3.equals("account-created-notification") %>'>
										<input class="lfr-input-text" name="<portlet:namespace />emailUserAddedSubject" type="text" value="<%= emailUserAddedSubject %>" />
									</c:when>
									<c:when test='<%= tabs3.equals("password-changed-notification") %>'>
										<input class="lfr-input-text" name="<portlet:namespace />emailPasswordSentSubject" type="text" value="<%= emailPasswordSentSubject %>" />
									</c:when>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<br />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="body" />
							</td>
							<td>
								<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />

								<input name="<portlet:namespace /><%= editorParam %>" type="hidden" value="" />
							</td>
						</tr>
						</table>

						<br />

						<b><liferay-ui:message key="definition-of-terms" /></b>

						<br /><br />

						<table class="lfr-table">
						<tr>
							<td>
								<b>[$FROM_ADDRESS$]</b>
							</td>
							<td>
								<%= emailFromAddress %>
							</td>
						</tr>
						<tr>
							<td>
								<b>[$FROM_NAME$]</b>
							</td>
							<td>
								<%= emailFromName %>
							</td>
						</tr>
						<tr>
							<td>
								<b>[$PORTAL_URL$]</b>
							</td>
							<td>
								<%= company.getVirtualHost() %>
							</td>
						</tr>

						<c:if test='<%= tabs3.equals("password-changed-notification") %>'>
							<tr>
								<td>
									<b>[$REMOTE_ADDRESS$]</b>
								</td>
								<td>
									The browser's remote address
								</td>
							</tr>
							<tr>
								<td>
									<b>[$REMOTE_HOST$]</b>
								</td>
								<td>
									The browser's remote host
								</td>
							</tr>
						</c:if>

						<tr>
							<td>
								<b>[$TO_ADDRESS$]</b>
							</td>
							<td>
								The address of the email recipient
							</td>
						</tr>
						<tr>
							<td>
								<b>[$TO_NAME$]</b>
							</td>
							<td>
								The name of the email recipient
							</td>
						</tr>

						<c:if test='<%= tabs3.equals("password-changed-notification") %>'>
							<tr>
								<td>
									<b>[$USER_AGENT$]</b>
								</td>
								<td>
									The browser's user agent
								</td>
							</tr>
						</c:if>

						<tr>
							<td>
								<b>[$USER_ID$]</b>
							</td>
							<td>
								The user ID
							</td>
						</tr>
						<tr>
							<td>
								<b>[$USER_PASSWORD$]</b>
							</td>
							<td>
								The user password
							</td>
						</tr>
						</table>
					</c:when>
					<c:otherwise>
						<table class="lfr-table">
						<tr>
							<td>
								<liferay-ui:message key="name" />
							</td>
							<td>
								<input class="lfr-input-text" name="<portlet:namespace />emailFromName" type="text" value="<%= emailFromName %>" />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="address" />
							</td>
							<td>
								<input class="lfr-input-text" name="<portlet:namespace />emailFromAddress" type="text" value="<%= emailFromAddress %>" />
							</td>
						</tr>
						</table>
					</c:otherwise>
				</c:choose>

				<br />

				<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveEmails();" />
			</c:when>
			<c:otherwise>
				<%@ include file="/html/portlet/enterprise_admin/company.jspf" %>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test='<%= tabs1.equals("monitoring") %>'>
		<c:choose>
			<c:when test="<%= GetterUtil.getBoolean(PropsUtil.get(PropsUtil.SESSION_TRACKER_MEMORY_ENABLED)) %>">
				<liferay-ui:tabs
					names="live-sessions"
					param="tabs2"
					url="<%= portletURLString %>"
				/>

				<%
				SearchContainer searchContainer = new SearchContainer();

				List headerNames = new ArrayList();

				headerNames.add("session-id");
				headerNames.add("user-id");
				headerNames.add("name");
				headerNames.add("email-address");
				headerNames.add("last-request");
				headerNames.add("num-of-hits");

				searchContainer.setHeaderNames(headerNames);
				searchContainer.setEmptyResultsMessage("there-are-no-live-sessions");

				List results = new ArrayList();

				Iterator itr = LiveUsers.getSessionUsers().entrySet().iterator();

				while (itr.hasNext()) {
					Map.Entry entry = (Map.Entry)itr.next();

					results.add(entry.getValue());
				}

				Collections.sort(results, new UserTrackerModifiedDateComparator());

				List resultRows = searchContainer.getResultRows();

				for (int i = 0; i < results.size(); i++) {
					UserTracker userTracker = (UserTracker)results.get(i);

					ResultRow row = new ResultRow(userTracker, userTracker.getUserTrackerId(), i);

					PortletURL rowURL = renderResponse.createRenderURL();

					rowURL.setWindowState(WindowState.MAXIMIZED);

					rowURL.setParameter("struts_action", "/enterprise_admin/edit_session");
					rowURL.setParameter("redirect", currentURL);
					rowURL.setParameter("sessionId", userTracker.getSessionId());

					User user2 = null;

					try {
						user2 = UserLocalServiceUtil.getUserById(userTracker.getUserId());
					}
					catch (NoSuchUserException nsue) {
					}

					// Session ID

					row.addText(userTracker.getSessionId(), rowURL);

					// User ID

					row.addText(String.valueOf(userTracker.getUserId()), rowURL);

					// Name

					row.addText(((user2 != null) ? user2.getFullName() : LanguageUtil.get(pageContext, "not-available")), rowURL);

					// Email Address

					row.addText(((user2 != null) ? user2.getEmailAddress() : LanguageUtil.get(pageContext, "not-available")), rowURL);

					// Last Request

					row.addText(dateFormatDateTime.format(userTracker.getModifiedDate()), rowURL);

					// # of Hits

					row.addText(String.valueOf(userTracker.getHits()), rowURL);

					// Add result row

					resultRows.add(row);
				}
				%>

				<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
			</c:when>
			<c:otherwise>
				<%= LanguageUtil.format(pageContext, "display-of-live-session-data-is-disabled", PropsUtil.SESSION_TRACKER_MEMORY_ENABLED) %>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test='<%= tabs1.equals("plugins") %>'>

		<%
		boolean installable = false;

		PortletURL installPluginsURL = null;
		%>

		<%@ include file="/html/portlet/enterprise_admin/plugins.jspf" %>
	</c:when>
</c:choose>

</form>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.enterprise_admin.view.jsp";
private static final long[] _DURATIONS = {300, 600, 1800, 3600, 7200, 10800, 21600};
%>