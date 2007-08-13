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

boolean organizationsTab = tabs1.equals("organizations");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/enterprise_admin/view");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("tabs3", tabs3);
%>

<script type="text/javascript">
	function <portlet:namespace />deleteOrganizations() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-" + (organizationsTab ? "organizations" : "locations")) %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
			document.<portlet:namespace />fm.<portlet:namespace />deleteOrganizationIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_organization" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>");
		}
	}

	function <portlet:namespace />deleteUserGroups() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-user-groups") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
			document.<portlet:namespace />fm.<portlet:namespace />deleteUserGroupIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_user_group" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>");
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
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;
			document.<portlet:namespace />fm.<portlet:namespace />deleteUserIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_user" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>");
		}
	}

	function <portlet:namespace />saveCompany(cmd) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/view" /><portlet:param name="tabs1" value="<%= tabs1 %>" /><portlet:param name="tabs2" value="<%= tabs2 %>" /><portlet:param name="tabs3" value="<%= tabs3 %>" /></portlet:renderURL>";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_company" /></portlet:actionURL>");
	}

	function <portlet:namespace />saveSettings(cmd) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/view" /><portlet:param name="tabs1" value="<%= tabs1 %>" /><portlet:param name="tabs2" value="<%= tabs2 %>" /><portlet:param name="tabs3" value="<%= tabs3 %>" /></portlet:renderURL>";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_settings" /></portlet:actionURL>");
	}

	function <portlet:namespace />updateDefaultLdap() {
		var baseProviderURL = "";
		var baseDN = "";
		var principal = "";
		var credentials = "";
		var searchFilter = "";
		var userMappings = "";

		var ldapType = document.<portlet:namespace />fm.<portlet:namespace />defaultLdap.selectedIndex;

		if (ldapType == 1) {
			baseProviderURL = "ldap://localhost:10389";
			baseDN = "dc=example,dc=com";
			principal = "uid=admin,ou=system";
			credentials = "secret";
			searchFilter = "(mail=@email_address@)";
			userMappings = "screenName=cn\npassword=userPassword\nemailAddress=mail\nfirstName=givenName\nlastName=sn\njobTitle=title";
		}
		else if (ldapType == 2) {
			baseProviderURL = "ldap://localhost:389";
			baseDN = "dc=example,dc=com";
			principal = "admin";
			credentials = "secret";
			searchFilter = "(&(objectCategory=person)(sAMAccountName=@user_id@))";
			userMappings = "fullName=cn\nscreenName=sAMAccountName\nemailAddress=userprincipalname";
		}
		else if (ldapType == 3) {
			url = "ldap://localhost:389";
			baseDN = "";
			principal = "cn=admin,ou=test";
			credentials = "secret";
			searchFilter = "(mail=@email_address@)";
			userMappings = "screenName=cn\npassword=userPassword\nemailAddress=mail\nfirstName=givenName\nlastName=sn\njobTitle=title";
		}

		if ((ldapType >= 1) && (ldapType <= 3)) {
			document.<portlet:namespace />fm.<portlet:namespace />baseProviderURL.value = baseProviderURL;
			document.<portlet:namespace />fm.<portlet:namespace />baseDN.value = baseDN;
			document.<portlet:namespace />fm.<portlet:namespace />principal.value = principal;
			document.<portlet:namespace />fm.<portlet:namespace />credentials.value = credentials;
			document.<portlet:namespace />fm.<portlet:namespace />searchFilter.value = searchFilter;
			document.<portlet:namespace />fm.<portlet:namespace />userMappings.value = userMappings;
		}
	}

	jQuery(
		function() {
			Liferay.Util.toggleBoxes('<portlet:namespace />importEnabledCheckbox', '<portlet:namespace />importEnabledSettings');
		}
	);
</script>

<form action="<%= portletURL.toString() %>" method="post" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />tabs1" type="hidden" value="<%= tabs1 %>" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= tabs2 %>" />
<input name="<portlet:namespace />tabs3" type="hidden" value="<%= tabs3 %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= currentURL %>" />

<liferay-util:include page="/html/portlet/enterprise_admin/tabs1.jsp" />

<c:choose>
	<c:when test='<%= tabs1.equals("users") %>'>
		<input name="<portlet:namespace />deleteUserIds" type="hidden" value="" />

		<liferay-ui:error exception="<%= RequiredUserException.class %>" message="you-cannot-delete-or-deactivate-yourself" />

		<%
		UserSearch searchContainer = new UserSearch(renderRequest, portletURL);

		List headerNames = searchContainer.getHeaderNames();

		headerNames.add(StringPool.BLANK);

		if (portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.LOCATION_ADMIN) || portletName.equals(PortletKeys.ORGANIZATION_ADMIN)) {
			RowChecker rowChecker = new RowChecker(renderResponse);
			//RowChecker rowChecker = new RowChecker(renderResponse, RowChecker.FORM_NAME, null, RowChecker.ROW_IDS);

			searchContainer.setRowChecker(rowChecker);
		}
		%>

		<liferay-ui:search-form
			page="/html/portlet/enterprise_admin/user_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<c:if test="<%= renderRequest.getWindowState().equals(WindowState.MAXIMIZED) %>">

			<%
			UserSearchTerms searchTerms = (UserSearchTerms)searchContainer.getSearchTerms();

			long organizationId = searchTerms.getOrganizationId();
			long roleId = searchTerms.getRoleId();
			long userGroupId = searchTerms.getUserGroupId();

			if (portletName.equals(PortletKeys.LOCATION_ADMIN)) {
				long locationId = user.getLocation().getOrganizationId();

				organizationId = locationId;
			}
			else if (portletName.equals(PortletKeys.ORGANIZATION_ADMIN)) {
				long parentOrganizationId = user.getOrganization().getOrganizationId();

				if ((organizationId <= 0) || (organizationId == parentOrganizationId)) {
					organizationId = parentOrganizationId;
				}
				else {
					try {
						Organization location = OrganizationLocalServiceUtil.getOrganization(organizationId);

						if (location.getParentOrganizationId() != parentOrganizationId) {
							organizationId = parentOrganizationId;
						}
					}
					catch (Exception e) {
						organizationId = parentOrganizationId;
					}
				}
			}

			LinkedHashMap userParams = new LinkedHashMap();

			userParams.put("usersOrgs", new Long(organizationId));

			if (roleId > 0) {
				userParams.put("usersRoles", new Long(roleId));
			}

			if (userGroupId > 0) {
				userParams.put("usersUserGroups", new Long(userGroupId));
			}

			int total = UserLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getFirstName(), searchTerms.getMiddleName(), searchTerms.getLastName(), searchTerms.getScreenName(), searchTerms.getEmailAddress(), searchTerms.getActiveObj(), userParams, searchTerms.isAndOperator());

			searchContainer.setTotal(total);

			List results = UserLocalServiceUtil.search(company.getCompanyId(), searchTerms.getFirstName(), searchTerms.getMiddleName(), searchTerms.getLastName(), searchTerms.getScreenName(), searchTerms.getEmailAddress(), searchTerms.getActiveObj(), userParams, searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd(), new ContactLastNameComparator(true));

			searchContainer.setResults(results);

			Organization organization = null;

			if ((organizationId > 0)) {
				try {
					organization = OrganizationLocalServiceUtil.getOrganization(organizationId);
				}
				catch (NoSuchOrganizationException nsoe) {
				}
			}

			Role role = null;

			if (roleId > 0) {
				try {
					role = RoleLocalServiceUtil.getRole(roleId);
				}
				catch (NoSuchRoleException nsre) {
				}
			}

			UserGroup userGroup = null;

			if (userGroupId > 0) {
				try {
					userGroup = UserGroupLocalServiceUtil.getUserGroup(userGroupId);
				}
				catch (NoSuchUserGroupException nsuge) {
				}
			}
			%>

			<c:if test="<%= (organization != null) || (role != null) || (userGroup != null) %>">
				<br />
			</c:if>

			<c:if test="<%= organization != null %>">
				<input name="<portlet:namespace /><%= UserDisplayTerms.ORGANIZATION_ID %>" type="hidden" value="<%= organization.getOrganizationId() %>" />

				<%= LanguageUtil.get(pageContext, "filter-by-" + (organization.isLocation() ? "location" : "organization")) %>: <%= organization.getName() %><br />
			</c:if>

			<c:if test="<%= role != null %>">
				<input name="<portlet:namespace /><%= UserDisplayTerms.ROLE_ID %>" type="hidden" value="<%= role.getRoleId() %>" />

				<liferay-ui:message key="filter-by-role" />: <%= role.getName() %><br />
			</c:if>

			<c:if test="<%= userGroup != null %>">
				<input name="<portlet:namespace /><%= UserDisplayTerms.USER_GROUP_ID %>" type="hidden" value="<%= userGroup.getUserGroupId() %>" />

				<liferay-ui:message key="filter-by-user-group" />: <%= userGroup.getName() %><br />
			</c:if>

			<div class="separator"><!-- --></div>

			<c:if test="<%= portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.LOCATION_ADMIN) || portletName.equals(PortletKeys.ORGANIZATION_ADMIN) %>">
				<c:if test="<%= searchTerms.isActive() || (!searchTerms.isActive() && GetterUtil.getBoolean(PropsUtil.get(PropsUtil.USERS_DELETE))) %>">
					<input type="button" value='<%= LanguageUtil.get(pageContext, (searchTerms.isActive() ? Constants.DEACTIVATE : Constants.DELETE)) %>' onClick="<portlet:namespace />deleteUsers('<%= searchTerms.isActive() ? Constants.DEACTIVATE : Constants.DELETE %>');" />
				</c:if>

				<c:if test="<%= !searchTerms.isActive() %>">
					<input type="button" value="<liferay-ui:message key="restore" />" onClick="<portlet:namespace />deleteUsers('<%= Constants.RESTORE %>');" />
				</c:if>

				<br /><br />
			</c:if>

			<%
			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				User user2 = (User)results.get(i);

				ResultRow row = new ResultRow(user2, user2.getUserId(), i);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setWindowState(WindowState.MAXIMIZED);

				rowURL.setParameter("struts_action", "/enterprise_admin/edit_user");
				rowURL.setParameter("p_u_i_d", String.valueOf(user2.getUserId()));

				// Name

				row.addText(user2.getFullName(), rowURL);

				// Screen name

				row.addText(user2.getScreenName(), rowURL);

				// Email address

				row.addText(user2.getEmailAddress(), rowURL);

				// Job title

				Contact contact2 = user2.getContact();

				row.addText(contact2.getJobTitle(), rowURL);

				// Organization

				Organization userOrg = user2.getOrganization();

				row.addText(userOrg.getName(), rowURL);

				// Location

				Organization location = user2.getLocation();

				row.addText(location.getName(), rowURL);

				// City

				Address address = location.getAddress();

				//row.addText(address.getCity(), rowURL);

				// Region

				String regionName = address.getRegion().getName();

				if (Validator.isNull(regionName)) {
					try {
						Region region = RegionServiceUtil.getRegion(location.getRegionId());

						regionName = LanguageUtil.get(pageContext, region.getName());
					}
					catch (NoSuchRegionException nsce) {
					}
				}

				//row.addText(regionName, rowURL);

				// Country

				String countryName = address.getCountry().getName();

				if (Validator.isNull(countryName)) {
					try {
						Country country = CountryServiceUtil.getCountry(location.getCountryId());

						countryName = LanguageUtil.get(pageContext, country.getName());
					}
					catch (NoSuchCountryException nsce) {
					}
				}

				//row.addText(countryName, rowURL);

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/enterprise_admin/user_action.jsp");

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
		</c:if>
	</c:when>
	<c:when test='<%= tabs1.equals("organizations") || tabs1.equals("locations") %>'>
		<input name="<portlet:namespace />deleteOrganizationIds" type="hidden" value="" />

		<c:choose>
			<c:when test="<%= organizationsTab %>">
				<liferay-ui:error exception="<%= RequiredOrganizationException.class %>" message="you-cannot-delete-organizations-that-have-locations-or-users" />
			</c:when>
			<c:otherwise>
				<liferay-ui:error exception="<%= RequiredOrganizationException.class %>" message="you-cannot-delete-locations-that-have-users" />
			</c:otherwise>
		</c:choose>

		<%
		boolean showSearch = false;

		if (organizationsTab && (portletName.equals(PortletKeys.LOCATION_ADMIN) || portletName.equals(PortletKeys.ORGANIZATION_ADMIN))) {
		}
		else if (!organizationsTab && portletName.equals(PortletKeys.LOCATION_ADMIN)) {
		}
		else {
			showSearch = true;
		}

		boolean showButtons = false;

		if (portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.ORGANIZATION_ADMIN)) {
			if (organizationsTab && portletName.equals(PortletKeys.ORGANIZATION_ADMIN)) {
			}
			else {
				showButtons = true;
			}
		}

		OrganizationSearch searchContainer = new OrganizationSearch(renderRequest, portletURL);

		List headerNames = new ArrayList();

		headerNames.add("name");

		if (organizationsTab) {
			headerNames.add("parent-organization");
		}
		else {
			headerNames.add("organization");
		}

		headerNames.add("city");
		headerNames.add("region");
		headerNames.add("country");

		searchContainer.setHeaderNames(headerNames);

		headerNames.add(StringPool.BLANK);

		if (showButtons) {
			RowChecker rowChecker = new RowChecker(renderResponse);

			searchContainer.setRowChecker(rowChecker);
		}
		%>

		<c:if test="<%= showSearch %>">
			<liferay-ui:search-form
				page="/html/portlet/enterprise_admin/organization_search.jsp"
				searchContainer="<%= searchContainer %>"
			/>
		</c:if>

		<c:if test="<%= renderRequest.getWindowState().equals(WindowState.MAXIMIZED) || !showSearch %>">

			<%
			OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)searchContainer.getSearchTerms();

			int total = 0;
			List results = null;

			if (organizationsTab && (portletName.equals(PortletKeys.LOCATION_ADMIN) || portletName.equals(PortletKeys.ORGANIZATION_ADMIN))) {
				total = 1;

				searchContainer.setTotal(total);

				results = new ArrayList();

				results.add(user.getOrganization());
			}
			else if (!organizationsTab && portletName.equals(PortletKeys.LOCATION_ADMIN)) {
				total = 1;

				searchContainer.setTotal(total);

				results = new ArrayList();

				results.add(user.getLocation());
			}
			else {
				OrganizationDisplayTerms displayTerms = (OrganizationDisplayTerms)searchContainer.getDisplayTerms();

				long parentOrganizationId = displayTerms.getParentOrganizationId();

				if (organizationsTab) {
					parentOrganizationId = ParamUtil.getLong(request, "parentOrganizationId", OrganizationImpl.DEFAULT_PARENT_ORGANIZATION_ID);

					if (parentOrganizationId <= 0) {
						parentOrganizationId = OrganizationImpl.ANY_PARENT_ORGANIZATION_ID;
					}
				}
				else {
					if (portletName.equals(PortletKeys.ORGANIZATION_ADMIN)) {
						parentOrganizationId = user.getOrganization().getOrganizationId();
					}
					else {
						parentOrganizationId = ParamUtil.getLong(request, "parentOrganizationId", OrganizationImpl.DEFAULT_PARENT_ORGANIZATION_ID);

						if (parentOrganizationId <= 0) {
							parentOrganizationId = OrganizationImpl.ANY_PARENT_ORGANIZATION_ID;
						}
					}
				}

				total = OrganizationLocalServiceUtil.searchCount(company.getCompanyId(), parentOrganizationId, searchTerms.getName(), !organizationsTab, searchTerms.getStreet(), searchTerms.getCity(), searchTerms.getZip(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), null, searchTerms.isAndOperator());

				searchContainer.setTotal(total);

				results = OrganizationLocalServiceUtil.search(company.getCompanyId(), parentOrganizationId, searchTerms.getName(), !organizationsTab, searchTerms.getStreet(), searchTerms.getCity(), searchTerms.getZip(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), null, searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd());
			}

			searchContainer.setResults(results);

			if (!organizationsTab) {
				searchContainer.setEmptyResultsMessage(OrganizationSearch.EMPTY_RESULTS_MESSAGE_2);
			}
			%>

			<c:if test="<%= showSearch %>">
				<div class="separator"><!-- --></div>
			</c:if>

			<c:if test="<%= showButtons %>">
				<input type="button" value="<liferay-ui:message key="delete" />" onClick="<portlet:namespace />deleteOrganizations();" />

				<br /><br />
			</c:if>

			<%
			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				Organization organization = (Organization)results.get(i);

				ResultRow row = new ResultRow(organization, organization.getOrganizationId(), i);

				String strutsAction = "/enterprise_admin/edit_organization";

				if (organization.isLocation()) {
					strutsAction = "/enterprise_admin/edit_location";
				}

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setWindowState(WindowState.MAXIMIZED);

				rowURL.setParameter("struts_action", strutsAction);
				rowURL.setParameter("organizationId", String.valueOf(organization.getOrganizationId()));

				// Name

				row.addText(organization.getName(), rowURL);

				// Parent organization

				String parentOrganizationName = StringPool.BLANK;

				if (organization.getParentOrganizationId() > 0) {
					try {
						Organization parentOrganization = OrganizationLocalServiceUtil.getOrganization(organization.getParentOrganizationId());

						parentOrganizationName = parentOrganization.getName();
					}
					catch (Exception e) {
					}
				}

				row.addText(parentOrganizationName);

				// City

				Address address = organization.getAddress();

				row.addText(address.getCity(), rowURL);

				// Region

				String regionName = address.getRegion().getName();

				if (Validator.isNull(regionName)) {
					try {
						Region region = RegionServiceUtil.getRegion(organization.getRegionId());

						regionName = LanguageUtil.get(pageContext, region.getName());
					}
					catch (NoSuchRegionException nsce) {
					}
				}

				row.addText(regionName, rowURL);

				// Country

				String countryName = address.getCountry().getName();

				if (Validator.isNull(countryName)) {
					try {
						Country country = CountryServiceUtil.getCountry(organization.getCountryId());

						countryName = LanguageUtil.get(pageContext, country.getName());
					}
					catch (NoSuchCountryException nsce) {
					}
				}

				row.addText(countryName, rowURL);

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/enterprise_admin/organization_action.jsp");

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
		</c:if>
	</c:when>
	<c:when test='<%= tabs1.equals("user-groups") %>'>
		<input name="<portlet:namespace />deleteUserGroupIds" type="hidden" value="" />

		<liferay-ui:error exception="<%= RequiredUserGroupException.class %>" message="you-cannot-delete-user-groups-that-have-users" />

		<%
		UserGroupSearch searchContainer = new UserGroupSearch(renderRequest, portletURL);

		List headerNames = searchContainer.getHeaderNames();

		headerNames.add(StringPool.BLANK);

		if (portletName.equals(PortletKeys.ENTERPRISE_ADMIN) && PortalPermission.contains(permissionChecker, ActionKeys.ADD_USER_GROUP)) {
			RowChecker rowChecker = new RowChecker(renderResponse);

			searchContainer.setRowChecker(rowChecker);
		}
		%>

		<liferay-ui:search-form
			page="/html/portlet/enterprise_admin/user_group_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<c:if test="<%= renderRequest.getWindowState().equals(WindowState.MAXIMIZED) %>">

			<%
			UserGroupSearchTerms searchTerms = (UserGroupSearchTerms)searchContainer.getSearchTerms();

			int total = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), null);

			searchContainer.setTotal(total);

			List results = UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), null, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);
			%>

			<div class="separator"><!-- --></div>

			<c:if test="<%= portletName.equals(PortletKeys.ENTERPRISE_ADMIN) && PortalPermission.contains(permissionChecker, ActionKeys.ADD_USER_GROUP) %>">
				<input type="button" value="<liferay-ui:message key="delete" />" onClick="<portlet:namespace />deleteUserGroups();" />
			</c:if>

			<br /><br />

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

			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
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

		<c:if test="<%= renderRequest.getWindowState().equals(WindowState.MAXIMIZED) %>">

			<%
			RoleSearchTerms searchTerms = (RoleSearchTerms)searchContainer.getSearchTerms();

			int total = RoleLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), searchTerms.getTypeObj());

			searchContainer.setTotal(total);

			List results = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), searchTerms.getTypeObj(), searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);
			%>

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

				row.addText(LanguageUtil.get(pageContext, (role.getType() == RoleImpl.TYPE_REGULAR) ? "regular" : "community"), rowURL);

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/enterprise_admin/role_action.jsp");

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
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

		RowChecker rowChecker = new RowChecker(renderResponse);

		searchContainer.setRowChecker(rowChecker);
		%>

		<c:if test="<%= !passwordPolicyEnabled %>">
			<liferay-ui:search-form
				page="/html/portlet/enterprise_admin/password_policy_search.jsp"
				searchContainer="<%= searchContainer %>"
			/>
		</c:if>

		<c:if test="<%= !passwordPolicyEnabled && renderRequest.getWindowState().equals(WindowState.MAXIMIZED) %>">

			<%
			PasswordPolicySearchTerms searchTerms = (PasswordPolicySearchTerms)searchContainer.getSearchTerms();

			int total = PasswordPolicyLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName());

			searchContainer.setTotal(total);

			List results = PasswordPolicyLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);
			%>

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

			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
		</c:if>
	</c:when>
	<c:when test='<%= tabs1.equals("settings") %>'>
		<liferay-ui:tabs
			names="general,authentication,default-user-associations,reserved-screen-names,mail-host-names,email-notifications"
			param="tabs2"
			url="<%= portletURL.toString() %>"
		/>

		<c:choose>
			<c:when test='<%= tabs2.equals("authentication") %>'>
				<liferay-ui:tabs
					names="general,ldap,cas,open-id"
					param="tabs3"
					url="<%= portletURL.toString() %>"
				/>

				<liferay-ui:error key="ldapAuthentication" message="failed-to-bind-to-the-ldap-server-with-given-values" />

				<c:choose>
					<c:when test='<%= tabs3.equals("ldap") %>'>
						<liferay-ui:tabs
							names="connection-settings"
							param="tabs1"
							refresh="<%= false %>"
						>
							<liferay-ui:section>
								<table class="liferay-table">
								<tr>
									<td>
										<liferay-ui:message key="enabled" />
									</td>
									<td>
										<liferay-ui:input-checkbox param="enabled" defaultValue='<%= ParamUtil.getBoolean(request, "enabled", PortalLDAPUtil.isAuthEnabled(company.getCompanyId())) %>' />
									</td>
								</tr>
								<tr>
									<td>
										<liferay-ui:message key="required" />
									</td>
									<td>
										<liferay-ui:input-checkbox param="required" defaultValue='<%= ParamUtil.getBoolean(request, "required", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsUtil.LDAP_AUTH_REQUIRED)) %>' />
									</td>
								</tr>
								<tr>
									<td>
										<liferay-ui:message key="ntlm-enabled" />
									</td>
									<td>
										<liferay-ui:input-checkbox param="ntlmEnabled" defaultValue='<%= ParamUtil.getBoolean(request, "ntlmEnabled", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsUtil.NTLM_AUTH_ENABLED)) %>' />
									</td>
								</tr>
								</table>

								<br />

								<liferay-ui:message key="the-ldap-url-format-is" />

								<br /><br />

								<table class="liferay-table">
								<tr>
									<td>
										<liferay-ui:message key="base-provider-url" />
									</td>
									<td>
										<input class="liferay-input-text" name="<portlet:namespace />baseProviderURL" type="text" value='<%= ParamUtil.getString(request, "baseProviderURL", PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.LDAP_BASE_PROVIDER_URL)) %>' />
									</td>
								</tr>
								<tr>
									<td>
										<liferay-ui:message key="base-dn" />
									</td>
									<td>
										<input class="liferay-input-text" name="<portlet:namespace />baseDN" type="text" value='<%= ParamUtil.getString(request, "baseDN", PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.LDAP_BASE_DN)) %>' />
									</td>
								</tr>
								<tr>
									<td>
										<liferay-ui:message key="principal" />
									</td>
									<td>
										<input class="liferay-input-text" name="<portlet:namespace />principal" type="text" value='<%= ParamUtil.getString(request, "principal", PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.LDAP_SECURITY_PRINCIPAL)) %>' />
									</td>
								</tr>
								<tr>
									<td>
										<liferay-ui:message key="credentials" />
									</td>
									<td>
										<input class="liferay-input-text" name="<portlet:namespace />credentials" type="password" value='<%= ParamUtil.getString(request, "credentials", PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.LDAP_SECURITY_CREDENTIALS)) %>' />
									</td>
								</tr>
								</table>

								<br />

								<liferay-ui:message key="enter-the-search-filter-that-will-be-used-to-test-the-validity-of-a-user" />

								<br /><br />

								<textarea class="liferay-textarea" name="<portlet:namespace />searchFilter"><%= ParamUtil.getString(request, "searchFilter", PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.LDAP_AUTH_SEARCH_FILTER)) %></textarea>

								<br /><br />

								<liferay-ui:message key="enter-the-encryption-algorithm-used-for-passwords-stored-in-the-ldap-server" />

								<br /><br />

								<select name="<portlet:namespace />passwordEncryptionAlgorithm">
									<option value=""></option>

									<%
									String passwordEncryptionAlgorithm = PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.LDAP_AUTH_PASSWORD_ENCRYPTION_ALGORITHM);

									String[] algorithmTypes = PropsUtil.getArray(PropsUtil.LDAP_AUTH_PASSWORD_ENCRYPTION_ALGORITHM_TYPES);

									for (int i = 0; i < algorithmTypes.length; i++) {
									%>

										<option <%= passwordEncryptionAlgorithm.equals(algorithmTypes[i]) ? "selected" : "" %> value="<%= algorithmTypes[i] %>"><%= algorithmTypes[i] %></option>

									<%
									}
									%>

								</select>

								<br /><br />

								<liferay-ui:message key="if-the-user-is-valid-and-the-user-exists-in-the-ldap-server-but-not-in-liferay" />

								<br /><br />

								<textarea class="liferay-textarea" name="<portlet:namespace />userMappings"><%= ParamUtil.getString(request, "userMappings", PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.LDAP_USER_MAPPINGS)) %></textarea>

								<br /><br />

								<table class="liferay-table">
								<tr>
									<td>
										<select name="<portlet:namespace />defaultLdap">
											<option></option>
											<option>Apache Directory Server</option>
											<option>Microsoft Active Directory Server</option>
											<option>Novell eDirectory</option>
										</select>
									</td>
									<td>
										<input type="button" value="<liferay-ui:message key="reset-values" />" onClick="<portlet:namespace />updateDefaultLdap();" />
									</td>
								</tr>
								</table>
							</liferay-ui:section>
						</liferay-ui:tabs>

						<br />

						<liferay-ui:tabs
							names="import-settings"
							param="tabs1"
							refresh="<%= false %>"
						>
							<liferay-ui:section>
								<table class="liferay-table">
								<tr>
									<td>
										<liferay-ui:message key="import-enabled" />
									</td>
									<td>
										<liferay-ui:input-checkbox param="importEnabled" defaultValue='<%= ParamUtil.getBoolean(request, "importEnabled", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsUtil.LDAP_IMPORT_ENABLED)) %>' />
									</td>
								</tr>
								<tbody id="<portlet:namespace />importEnabledSettings">
									<tr>
										<td>
											<liferay-ui:message key="import-on-startup-enabled" />
										</td>
										<td>
											<liferay-ui:input-checkbox param="importOnStartup" defaultValue='<%= ParamUtil.getBoolean(request, "importOnStartup", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsUtil.LDAP_IMPORT_ON_STARTUP)) %>' />
										</td>
									</tr>
									<tr>
										<td>
											<liferay-ui:message key="import-interval" />
										</td>
										<td>

											<%
											long importInterval = ParamUtil.getLong(request, "importInterval", PrefsPropsUtil.getLong(company.getCompanyId(), PropsUtil.LDAP_IMPORT_INTERVAL));
											%>

											<select name="<portlet:namespace />importInterval">
												<option value="0" <%= (importInterval == 0) ? " selected " : "" %>><liferay-ui:message key="disabled" /></option>
												<option value="5" <%= (importInterval == 5) ? " selected " : "" %>>5 <liferay-ui:message key="minutes" /></option>
												<option value="10" <%= (importInterval == 10) ? " selected " : "" %>>10 <liferay-ui:message key="minutes" /></option>
												<option value="30" <%= (importInterval == 30) ? " selected " : "" %>>30 <liferay-ui:message key="minutes" /></option>
												<option value="60" <%= (importInterval == 60) ? " selected " : "" %>>1 <liferay-ui:message key="hour" /></option>
												<option value="120" <%= (importInterval == 120) ? " selected " : "" %>>2 <liferay-ui:message key="hours" /></option>
												<option value="180" <%= (importInterval == 180) ? " selected " : "" %>>3 <liferay-ui:message key="hours" /></option>
											</select>
										</td>
									</tr>
									<tr>
										<td>
											<liferay-ui:message key="import-user-search-filter" />
										</td>
										<td>
											<input class="liferay-input-text" name="<portlet:namespace />importUserSearchFilter" type="text" value='<%= ParamUtil.getString(request, "importUserSearchFilter", PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.LDAP_IMPORT_USER_SEARCH_FILTER)) %>' />
										</td>
									</tr>
									<tr>
										<td>
											<liferay-ui:message key="import-group-search-filter" />
										</td>
										<td>
											<input class="liferay-input-text" name="<portlet:namespace />importGroupSearchFilter" type="text" value='<%= ParamUtil.getString(request, "importGroupSearchFilter", PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.LDAP_IMPORT_GROUP_SEARCH_FILTER)) %>' />
										</td>
									</tr>
								</tbody>
								</table>
							</liferay-ui:section>
						</liferay-ui:tabs>

						<br />

						<liferay-ui:tabs
							names="export-settings"
							param="tabs1"
							refresh="<%= false %>"
						>
							<liferay-ui:section>
								<table class="liferay-table">
								<tr>
									<td>
										<liferay-ui:message key="export-enabled" />
									</td>
									<td>
										<liferay-ui:input-checkbox param="exportEnabled" defaultValue='<%= ParamUtil.getBoolean(request, "exportEnabled", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsUtil.LDAP_EXPORT_ENABLED)) %>' />
									</td>
								</tr>
								<tr>
									<td>
										<liferay-ui:message key="users-dn" />
									</td>
									<td>
										<input class="liferay-input-text" name="<portlet:namespace />usersDn" type="text" value='<%= ParamUtil.getString(request, "usersDn", PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.LDAP_USERS_DN)) %>' />
									</td>
								</tr>
								<tr>
									<td>
										<liferay-ui:message key="user-default-object-classes" />
									</td>
									<td>
										<input class="liferay-input-text" name="<portlet:namespace />userDefaultObjectClasses" type="text" value='<%= ParamUtil.getString(request, "userDefaultObjectClasses", PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.LDAP_USER_DEFAULT_OBJECT_CLASSES)) %>' />
									</td>
								</tr>
								</table>
							</liferay-ui:section>
						</liferay-ui:tabs>

						<br />

						<liferay-ui:tabs
							names="password-policy"
							param="tabs1"
							refresh="<%= false %>"
						>
							<liferay-ui:section>
								<table class="liferay-table">
								<tr>
									<td>
										<liferay-ui:message key="use-ldap-password-policy" />
									</td>
									<td>
										<liferay-ui:input-checkbox param="passwordPolicyEnabled" defaultValue='<%= ParamUtil.getBoolean(request, "passwordPolicyEnabled", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsUtil.LDAP_PASSWORD_POLICY_ENABLED)) %>' />
									</td>
								</tr>
								</table>
							</liferay-ui:section>
						</liferay-ui:tabs>

						<br />

						<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveSettings('updateLdap');" />
					</c:when>
					<c:when test='<%= tabs3.equals("cas") %>'>
						<table class="liferay-table">
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
								<input class="liferay-input-text" name="<portlet:namespace />loginUrl" type="text" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.CAS_LOGIN_URL) %>" />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="logout-url" />
							</td>
							<td>
								<input class="liferay-input-text" name="<portlet:namespace />logoutUrl" type="text" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.CAS_LOGOUT_URL) %>" />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="service-url" />
							</td>
							<td>
								<input class="liferay-input-text" name="<portlet:namespace />serviceUrl" type="text" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.CAS_SERVICE_URL) %>" />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="validate-url" />
							</td>
							<td>
								<input class="liferay-input-text" name="<portlet:namespace />validateUrl" type="text" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.CAS_VALIDATE_URL) %>" />
							</td>
						</tr>
						</table>

						<br />

						<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveSettings('updateCAS');" />
					</c:when>
					<c:when test='<%= tabs3.equals("open-id") %>'>
						<table class="liferay-table">
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
					<c:otherwise>
						<table class="liferay-table">
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
							<td>
								<liferay-ui:message key="allow-strangers-to-create-accounts" />
							</td>
							<td>
								<liferay-ui:input-checkbox param="strangers" defaultValue="<%= company.isStrangers() %>" />
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

				<textarea class="liferay-textarea" name="<portlet:namespace />defaultGroupNames"><%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.ADMIN_DEFAULT_GROUP_NAMES) %></textarea>

				<br /><br />

				<liferay-ui:message key="enter-the-default-role-names-per-line-that-are-associated-with-newly-created-users" />

				<br /><br />

				<textarea class="liferay-textarea" name="<portlet:namespace />defaultRoleNames"><%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.ADMIN_DEFAULT_ROLE_NAMES) %></textarea>

				<br /><br />

				<liferay-ui:message key="enter-the-default-user-group-names-per-line-that-are-associated-with-newly-created-users" />

				<br /><br />

				<textarea class="liferay-textarea" name="<portlet:namespace />defaultUserGroupNames"><%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.ADMIN_DEFAULT_USER_GROUP_NAMES) %></textarea>

				<br /><br />

				<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveSettings('updateDefaultGroupsAndRoles');" />
			</c:when>
			<c:when test='<%= tabs2.equals("reserved-screen-names") %>'>
				<liferay-ui:message key="enter-one-screen-name-per-line-to-reserve-the-screen-name" />

				<br /><br />

				<textarea class="liferay-textarea" name="<portlet:namespace />reservedScreenNames"><%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.ADMIN_RESERVED_SCREEN_NAMES) %></textarea>

				<br /><br />

				<liferay-ui:message key="enter-one-user-email-address-per-line-to-reserve-the-user-email-address" />

				<br /><br />

				<textarea class="liferay-textarea" name="<portlet:namespace />reservedEmailAddresses"><%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.ADMIN_RESERVED_EMAIL_ADDRESSES) %></textarea>

				<br /><br />

				<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveSettings('updateReservedUsers');" />
			</c:when>
			<c:when test='<%= tabs2.equals("mail-host-names") %>'>
				<%= LanguageUtil.format(pageContext, "enter-one-mail-host-name-per-line-for-all-additional-mail-host-names-besides-x", company.getMx(), false) %>

				<br /><br />

				<textarea class="liferay-textarea" name="<portlet:namespace />mailHostNames"><%= PrefsPropsUtil.getString(company.getCompanyId(), PropsUtil.ADMIN_MAIL_HOST_NAMES) %></textarea>

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
					url="<%= portletURL.toString() %>"
				/>

				<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
				<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
				<liferay-ui:error key="emailPasswordSentBody" message="please-enter-a-valid-body" />
				<liferay-ui:error key="emailPasswordSentSubject" message="please-enter-a-valid-subject" />
				<liferay-ui:error key="emailUserAddedBody" message="please-enter-a-valid-body" />
				<liferay-ui:error key="emailUserAddedSubject" message="please-enter-a-valid-subject" />

				<c:choose>
					<c:when test='<%= tabs3.endsWith("-notification") %>'>
						<table class="liferay-table">
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
										<input class="liferay-input-text" name="<portlet:namespace />emailUserAddedSubject" type="text" value="<%= emailUserAddedSubject %>" />
									</c:when>
									<c:when test='<%= tabs3.equals("password-changed-notification") %>'>
										<input class="liferay-input-text" name="<portlet:namespace />emailPasswordSentSubject" type="text" value="<%= emailPasswordSentSubject %>" />
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

						<table class="liferay-table">
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
						<table class="liferay-table">
						<tr>
							<td>
								<liferay-ui:message key="name" />
							</td>
							<td>
								<input class="liferay-input-text" name="<portlet:namespace />emailFromName" type="text" value="<%= emailFromName %>" />
							</td>
						</tr>
						<tr>
							<td>
								<liferay-ui:message key="address" />
							</td>
							<td>
								<input class="liferay-input-text" name="<portlet:namespace />emailFromAddress" type="text" value="<%= emailFromAddress %>" />
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
					url="<%= portletURL.toString() %>"
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

<c:if test="<%= renderRequest.getWindowState().equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		if (document.<portlet:namespace />fm.<portlet:namespace />firstName) {
			Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />firstName);
		}
		else if (document.<portlet:namespace />fm.<portlet:namespace />name) {
			Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
		}
	</script>
</c:if>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.enterprise_admin.view.jsp";
private static final long[] _DURATIONS = {300, 600, 1800, 3600, 7200, 10800, 21600};
%>