<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.AccountNameException" %>
<%@ page import="com.liferay.portal.AddressCityException" %>
<%@ page import="com.liferay.portal.AddressStreetException" %>
<%@ page import="com.liferay.portal.AddressZipException" %>
<%@ page import="com.liferay.portal.CompanyMaxUsersException" %>
<%@ page import="com.liferay.portal.CompanyMxException" %>
<%@ page import="com.liferay.portal.CompanyVirtualHostException" %>
<%@ page import="com.liferay.portal.ContactBirthdayException" %>
<%@ page import="com.liferay.portal.ContactFirstNameException" %>
<%@ page import="com.liferay.portal.ContactFullNameException" %>
<%@ page import="com.liferay.portal.ContactLastNameException" %>
<%@ page import="com.liferay.portal.DuplicateOrganizationException" %>
<%@ page import="com.liferay.portal.DuplicatePasswordPolicyException" %>
<%@ page import="com.liferay.portal.DuplicateRoleException" %>
<%@ page import="com.liferay.portal.DuplicateUserEmailAddressException" %>
<%@ page import="com.liferay.portal.DuplicateUserGroupException" %>
<%@ page import="com.liferay.portal.DuplicateUserIdException" %>
<%@ page import="com.liferay.portal.DuplicateUserScreenNameException" %>
<%@ page import="com.liferay.portal.EmailAddressException" %>
<%@ page import="com.liferay.portal.GroupFriendlyURLException" %>
<%@ page import="com.liferay.portal.ImageTypeException" %>
<%@ page import="com.liferay.portal.NoSuchListTypeException" %>
<%@ page import="com.liferay.portal.NoSuchCountryException" %>
<%@ page import="com.liferay.portal.NoSuchOrganizationException" %>
<%@ page import="com.liferay.portal.NoSuchRegionException" %>
<%@ page import="com.liferay.portal.NoSuchRoleException" %>
<%@ page import="com.liferay.portal.NoSuchUserGroupException" %>
<%@ page import="com.liferay.portal.NoSuchUserException" %>
<%@ page import="com.liferay.portal.OrganizationNameException" %>
<%@ page import="com.liferay.portal.OrganizationParentException" %>
<%@ page import="com.liferay.portal.PasswordPolicyNameException" %>
<%@ page import="com.liferay.portal.PhoneNumberException" %>
<%@ page import="com.liferay.portal.RequiredOrganizationException" %>
<%@ page import="com.liferay.portal.RequiredRoleException" %>
<%@ page import="com.liferay.portal.RequiredUserException" %>
<%@ page import="com.liferay.portal.RequiredUserGroupException" %>
<%@ page import="com.liferay.portal.ReservedUserIdException" %>
<%@ page import="com.liferay.portal.ReservedUserEmailAddressException" %>
<%@ page import="com.liferay.portal.ReservedUserScreenNameException" %>
<%@ page import="com.liferay.portal.RoleAssignmentException" %>
<%@ page import="com.liferay.portal.RoleNameException" %>
<%@ page import="com.liferay.portal.RolePermissionsException" %>
<%@ page import="com.liferay.portal.UserEmailAddressException" %>
<%@ page import="com.liferay.portal.UserGroupNameException" %>
<%@ page import="com.liferay.portal.UserIdException" %>
<%@ page import="com.liferay.portal.UserPasswordException" %>
<%@ page import="com.liferay.portal.UserPortraitSizeException" %>
<%@ page import="com.liferay.portal.UserPortraitTypeException" %>
<%@ page import="com.liferay.portal.UserScreenNameException" %>
<%@ page import="com.liferay.portal.UserSmsException" %>
<%@ page import="com.liferay.portal.WebsiteURLException" %>
<%@ page import="com.liferay.portal.kernel.facebook.FacebookConnectUtil" %>
<%@ page import="com.liferay.portal.kernel.plugin.PluginPackage" %>
<%@ page import="com.liferay.portal.kernel.servlet.PortalSessionContext" %>
<%@ page import="com.liferay.portal.liveusers.LiveUsers" %>
<%@ page import="com.liferay.portal.security.auth.AuthSettingsUtil" %>
<%@ page import="com.liferay.portal.security.ldap.LDAPSettingsUtil" %>
<%@ page import="com.liferay.portal.security.ldap.PortalLDAPUtil" %>
<%@ page import="com.liferay.portal.security.permission.comparator.ActionComparator" %>
<%@ page import="com.liferay.portal.security.permission.comparator.ModelResourceComparator" %>
<%@ page import="com.liferay.portal.service.permission.OrganizationPermissionUtil" %>
<%@ page import="com.liferay.portal.service.permission.PasswordPolicyPermissionUtil" %>
<%@ page import="com.liferay.portal.service.permission.PortalPermissionUtil" %>
<%@ page import="com.liferay.portal.service.permission.RolePermissionUtil" %>
<%@ page import="com.liferay.portal.service.permission.UserGroupPermissionUtil" %>
<%@ page import="com.liferay.portal.service.permission.UserPermissionUtil" %>
<%@ page import="com.liferay.portal.servlet.filters.sso.opensso.OpenSSOUtil" %>
<%@ page import="com.liferay.portal.util.comparator.RoleRoleIdComparator" %>
<%@ page import="com.liferay.portal.util.comparator.UserTrackerModifiedDateComparator" %>
<%@ page import="com.liferay.portlet.announcements.model.AnnouncementsDelivery" %>
<%@ page import="com.liferay.portlet.announcements.model.AnnouncementsEntryConstants" %>
<%@ page import="com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl" %>
<%@ page import="com.liferay.portlet.announcements.service.AnnouncementsDeliveryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.passwordpoliciesadmin.search.OrganizationPasswordPolicyChecker" %>
<%@ page import="com.liferay.portlet.passwordpoliciesadmin.search.PasswordPolicyDisplayTerms" %>
<%@ page import="com.liferay.portlet.passwordpoliciesadmin.search.PasswordPolicySearch" %>
<%@ page import="com.liferay.portlet.passwordpoliciesadmin.search.PasswordPolicySearchTerms" %>
<%@ page import="com.liferay.portlet.passwordpoliciesadmin.search.UserPasswordPolicyChecker" %>
<%@ page import="com.liferay.portlet.rolesadmin.search.GroupRoleChecker" %>
<%@ page import="com.liferay.portlet.rolesadmin.search.OrganizationRoleChecker" %>
<%@ page import="com.liferay.portlet.rolesadmin.search.ResourceActionRowChecker" %>
<%@ page import="com.liferay.portlet.rolesadmin.search.RoleDisplayTerms" %>
<%@ page import="com.liferay.portlet.rolesadmin.search.RoleSearch" %>
<%@ page import="com.liferay.portlet.rolesadmin.search.RoleSearchTerms" %>
<%@ page import="com.liferay.portlet.rolesadmin.search.UserGroupRoleChecker" %>
<%@ page import="com.liferay.portlet.rolesadmin.search.UserRoleChecker" %>
<%@ page import="com.liferay.portlet.rolesadmin.util.RolesAdminUtil" %>
<%@ page import="com.liferay.portlet.sitesadmin.search.TeamDisplayTerms" %>
<%@ page import="com.liferay.portlet.sitesadmin.search.TeamSearch" %>
<%@ page import="com.liferay.portlet.usersadmin.search.GroupDisplayTerms" %>
<%@ page import="com.liferay.portlet.usersadmin.search.GroupSearch" %>
<%@ page import="com.liferay.portlet.usersadmin.search.GroupSearchTerms" %>
<%@ page import="com.liferay.portlet.usersadmin.search.OrganizationDisplayTerms" %>
<%@ page import="com.liferay.portlet.usersadmin.search.OrganizationSearch" %>
<%@ page import="com.liferay.portlet.usersadmin.search.OrganizationSearchTerms" %>
<%@ page import="com.liferay.portlet.usersadmin.search.RoleUserChecker" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserDisplayTerms" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserGroupDisplayTerms" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserGroupGroupChecker" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserGroupSearch" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserGroupSearchTerms" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserOrganizationChecker" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserSearch" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserSearchTerms" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserUserGroupChecker" %>
<%@ page import="com.liferay.portlet.usersadmin.util.UsersAdminUtil" %>
<%@ page import="com.liferay.util.ldap.LDAPUtil" %>

<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.net.MalformedURLException" %>
<%@ page import="java.net.URL" %>

<%@ page import="javax.naming.directory.Attribute" %>
<%@ page import="javax.naming.directory.Attributes" %>
<%@ page import="javax.naming.directory.SearchResult" %>
<%@ page import="javax.naming.ldap.LdapContext" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1");

boolean showTabs1 = false;
boolean showActiveUserSelect = true;

if (portletName.equals(PortletKeys.USERS_ADMIN)) {
	tabs1 = "users";
}
else if (portletName.equals(PortletKeys.ROLES_ADMIN) && !tabs1.equals("users")) {
	tabs1 = "roles";
}
else if (portletName.equals(PortletKeys.PASSWORD_POLICIES_ADMIN)) {
	tabs1 = "password-policies";
}
else if (portletName.equals(PortletKeys.PORTAL_SETTINGS)) {
	tabs1 = "settings";
}
else if (portletName.equals(PortletKeys.MONITORING)) {
	tabs1 = "monitoring";
}
else if (portletName.equals(PortletKeys.PLUGINS_ADMIN)) {
	tabs1 = "plugins";
}
else {
	showActiveUserSelect = false;

	if (tabs1.equals("roles") || tabs1.equals("password-policies") || tabs1.equals("settings") || tabs1.equals("monitoring") || tabs1.equals("plugins")) {
		tabs1 = "users";
	}
}

if (Validator.isNull(tabs1)) {
	tabs1 = "users";
}

boolean filterManageableGroups = true;
boolean filterManageableOrganizations = true;
boolean filterManageableRoles = true;
boolean filterManageableUserGroupRoles = true;
boolean filterManageableUserGroups = true;

if (portletName.equals(PortletKeys.MY_ACCOUNT)) {
	filterManageableGroups = false;
	filterManageableOrganizations = false;
	filterManageableRoles = false;
	filterManageableUserGroupRoles = false;
	filterManageableUserGroups = false;
}
else if (permissionChecker.isCompanyAdmin()) {
	filterManageableGroups = false;
	filterManageableOrganizations = false;
	filterManageableUserGroups = false;
}

boolean includeSystemPortlets = true;

int maxNumberOfRolesChecked = 500;

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/enterprise_admin/init-ext.jsp" %>