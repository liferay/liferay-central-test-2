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
themeDisplay.setIncludeServiceJs(true);

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

User selUser = PortalUtil.getSelectedUser(request);

Contact selContact = null;

if (selUser != null) {
	selContact = selUser.getContact();
}

PasswordPolicy passwordPolicy = null;

if (selUser == null) {
	passwordPolicy = PasswordPolicyLocalServiceUtil.getDefaultPasswordPolicy(company.getCompanyId());
}
else {
	passwordPolicy = selUser.getPasswordPolicy();
}

String groupIds = ParamUtil.getString(request, "groupsSearchContainerPrimaryKeys");

List<Group> groups = Collections.EMPTY_LIST;

if (Validator.isNotNull(groupIds)) {
	long[] groupIdsArray = StringUtil.split(groupIds, 0L);

	groups = GroupLocalServiceUtil.getGroups(groupIdsArray);
}
else if (selUser != null) {
	groups = selUser.getGroups();

	if (filterManageableGroups) {
		groups = EnterpriseAdminUtil.filterGroups(permissionChecker, groups);
	}
}

String organizationIds = ParamUtil.getString(request, "organizationsSearchContainerPrimaryKeys");

List<Organization> organizations = Collections.EMPTY_LIST;

if (Validator.isNotNull(organizationIds)) {
	long[] organizationIdsArray = StringUtil.split(organizationIds, 0L);

	organizations = OrganizationLocalServiceUtil.getOrganizations(organizationIdsArray);
}
else {
	if (selUser != null) {
		organizations = selUser.getOrganizations();
	}

	if (filterManageableOrganizations) {
		organizations = EnterpriseAdminUtil.filterOrganizations(permissionChecker, organizations);
	}
}

String roleIds = ParamUtil.getString(request, "rolesSearchContainerPrimaryKeys");

List<Role> roles = Collections.EMPTY_LIST;

if (Validator.isNotNull(roleIds)) {
	long[] roleIdsArray = StringUtil.split(roleIds, 0L);

	roles = RoleLocalServiceUtil.getRoles(roleIdsArray);
}
else if (selUser != null) {
	roles = selUser.getRoles();

	if (filterManageableRoles) {
		roles = EnterpriseAdminUtil.filterRoles(permissionChecker, roles);
	}
}

List<UserGroupRole> userGroupRoles = EnterpriseAdminUtil.getUserGroupRoles(renderRequest);

List<UserGroupRole> communityRoles = new ArrayList<UserGroupRole>();
List<UserGroupRole> organizationRoles = new ArrayList<UserGroupRole>();

if (userGroupRoles.isEmpty() && selUser != null) {
	userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(selUser.getUserId());

	if (filterManageableUserGroupRoles) {
		userGroupRoles = EnterpriseAdminUtil.filterUserGroupRoles(permissionChecker, userGroupRoles);
	}
}

for (UserGroupRole userGroupRole : userGroupRoles) {
	int roleType = userGroupRole.getRole().getType();

	if (roleType == RoleConstants.TYPE_COMMUNITY) {
		communityRoles.add(userGroupRole);
	}
	else if (roleType == RoleConstants.TYPE_ORGANIZATION) {
		organizationRoles.add(userGroupRole);
	}
}

String userGroupIds = ParamUtil.getString(request, "userGroupsSearchContainerPrimaryKeys");

List<UserGroup> userGroups = Collections.EMPTY_LIST;

if (Validator.isNotNull(userGroupIds)) {
	long[] userGroupIdsArray = StringUtil.split(userGroupIds, 0L);

	userGroups = UserGroupLocalServiceUtil.getUserGroups(userGroupIdsArray);
}
else if (selUser != null) {
	userGroups = selUser.getUserGroups();

	if (filterManageableUserGroups) {
		userGroups = EnterpriseAdminUtil.filterUserGroups(permissionChecker, userGroups);
	}
}

String[] mainSections = PropsValues.USERS_FORM_ADD_MAIN;
String[] identificationSections = PropsValues.USERS_FORM_ADD_IDENTIFICATION;
String[] miscellaneousSections = PropsValues.USERS_FORM_ADD_MISCELLANEOUS;

if (selUser != null) {
	if (portletName.equals(PortletKeys.MY_ACCOUNT)) {
		mainSections = PropsValues.USERS_FORM_MY_ACCOUNT_MAIN;
		identificationSections = PropsValues.USERS_FORM_MY_ACCOUNT_IDENTIFICATION;
		miscellaneousSections = PropsValues.USERS_FORM_MY_ACCOUNT_MISCELLANEOUS;
	}
	else {
		mainSections = PropsValues.USERS_FORM_UPDATE_MAIN;
		identificationSections = PropsValues.USERS_FORM_UPDATE_IDENTIFICATION;
		miscellaneousSections = PropsValues.USERS_FORM_UPDATE_MISCELLANEOUS;
	}
}

String[] allSections = ArrayUtil.append(mainSections, ArrayUtil.append(identificationSections, miscellaneousSections));

String[][] categorySections = {mainSections, identificationSections, miscellaneousSections};

String curSection = mainSections[0];
%>

<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
	<liferay-util:include page="/html/portlet/enterprise_admin/user/toolbar.jsp">
		<liferay-util:param name="toolbarItem" value='<%= (selUser == null) ? "add" : "view-all" %>' />
		<liferay-util:param name="backURL" value="<%= backURL %>" />
	</liferay-util:include>
</c:if>

<%
String taglibOnSubmit = renderResponse.getNamespace() + "saveUser('" + ((selUser == null) ? Constants.ADD : Constants.UPDATE) + "');";
%>

<aui:form method="post" name="fm" onSubmit="<%= taglibOnSubmit %>">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="p_u_i_d" type="hidden" value="<%= (selUser != null) ? selUser.getUserId() : 0 %>" />

	<div id="<portlet:namespace />sectionsContainer">
		<table class="user-table" width="100%">
		<tr>
			<td>

				<%
				request.setAttribute("user.selUser", selUser);
				request.setAttribute("user.selContact", selContact);
				request.setAttribute("user.passwordPolicy", passwordPolicy);
				request.setAttribute("user.groups", groups);
				request.setAttribute("user.organizations", organizations);
				request.setAttribute("user.roles", roles);
				request.setAttribute("user.communityRoles", communityRoles);
				request.setAttribute("user.organizationRoles", organizationRoles);
				request.setAttribute("user.userGroups", userGroups);

				request.setAttribute("addresses.className", Contact.class.getName());
				request.setAttribute("emailAddresses.className", Contact.class.getName());
				request.setAttribute("phones.className", Contact.class.getName());
				request.setAttribute("websites.className", Contact.class.getName());

				if (selContact != null) {
					request.setAttribute("addresses.classPK", selContact.getContactId());
					request.setAttribute("emailAddresses.classPK", selContact.getContactId());
					request.setAttribute("phones.classPK", selContact.getContactId());
					request.setAttribute("websites.classPK", selContact.getContactId());
				}
				else {
					request.setAttribute("addresses.classPK", 0L);
					request.setAttribute("emailAddresses.classPK", 0L);
					request.setAttribute("phones.classPK", 0L);
					request.setAttribute("websites.classPK", 0L);
				}

				for (String section : allSections) {
					String sectionId = _getSectionId(section);
					String sectionJsp = "/html/portlet/enterprise_admin/user/" + _getSectionJsp(section) + ".jsp";
				%>

					<div class="form-section <%= curSection.equals(section)? "selected" : StringPool.BLANK %>" id="<%= sectionId %>">
						<liferay-util:include page="<%= sectionJsp %>" />
					</div>

				<%
				}
				%>

				<div class="lfr-component form-navigation">
					<div class="user-info">
						<p class="float-container">
							<c:if test="<%= selUser != null %>">
								<img alt="<%= selUser.getFullName() %>" class="avatar" src="<%= themeDisplay.getPathImage() %>/user_<%= selUser.isFemale() ? "female" : "male" %>_portrait?img_id=<%= selUser.getPortraitId() %>&t=<%= ImageServletTokenUtil.getToken(selUser.getPortraitId()) %>" />

								<span><%= selUser.getFullName() %></span>
							</c:if>
						</p>
					</div>

					<%
					String[] categoryNames = _CATEGORY_NAMES;
					%>

					<%@ include file="/html/portlet/enterprise_admin/categories_navigation.jspf" %>

					<aui:button-row>
						<aui:button type="submit" />

						<aui:button onClick="<%= backURL %>" type="cancel" />
					</aui:button-row>

					<c:if test="<%= (selUser != null) && (passwordPolicy != null) && selUser.getLockout() %>">
						<aui:button-row>
							<div class="portlet-msg-alert"><liferay-ui:message key="this-user-account-has-been-locked-due-to-excessive-failed-login-attempts" /></div>

							<%
							String taglibOnClick = renderResponse.getNamespace() + "saveUser('unlock');";
							%>

							<aui:button onClick="<%= taglibOnClick %>" value="unlock" />
						</aui:button-row>
					</c:if>
				</div>
			</td>
		</tr>
		</table>
	</div>
</aui:form>

<%
if (selUser != null) {
	PortalUtil.setPageSubtitle(selUser.getFullName(), request);
}
%>

<aui:script>
	function <portlet:namespace />createURL(href, value, onclick) {
		return '<a href="' + href + '"' + (onclick ? ' onclick="' + onclick + '" ' : '') + '>' + value + '</a>';
	};

	function <portlet:namespace />saveUser(cmd) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;

		var redirect = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_user" /><portlet:param name="backURL" value="<%= backURL %>"></portlet:param></portlet:renderURL>";

		if (location.hash) {
			redirect += location.hash;
		}

		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;

		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_user" /></portlet:actionURL>");
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />screenName);
	</c:if>
</aui:script>

<%
if (selUser != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, selUser.getFullName(), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-user"), currentURL);
}
%>

<%!
private static String[] _CATEGORY_NAMES = {"user-information", "identification", "miscellaneous"};
%>