/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.enterpriseadmin.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.permission.PermissionChecker;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Julio Camarero
 */
public class EnterpriseAdminUtil {

	public static final String CUSTOM_QUESTION = "write-my-own-question";

	public static void addPortletBreadcrumbEntries(
			Organization organization, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		getEnterpriseAdmin().addPortletBreadcrumbEntries(
			organization, request, renderResponse);
	}

	public static String getCssClassName(Role role) {
		return getEnterpriseAdmin().getCssClassName(role);
	}

	public static long[] addRequiredRoles(long userId, long[] roleIds)
		throws PortalException, SystemException {

		return getEnterpriseAdmin().addRequiredRoles(userId, roleIds);
	}

	public static long[] addRequiredRoles(User user, long[] roleIds)
		throws PortalException, SystemException {

		return getEnterpriseAdmin().addRequiredRoles(user, roleIds);
	}

	public static List<Role> filterGroupRoles(
			PermissionChecker permissionChecker, long groupId, List<Role> roles)
		throws PortalException, SystemException {

		return getEnterpriseAdmin().filterGroupRoles(
			permissionChecker, groupId, roles);
	}

	public static List<Group> filterGroups(
			PermissionChecker permissionChecker, List<Group> groups)
		throws PortalException, SystemException {

		return getEnterpriseAdmin().filterGroups(permissionChecker, groups);
	}

	public static List<Organization> filterOrganizations(
			PermissionChecker permissionChecker,
			List<Organization> organizations)
		throws PortalException, SystemException {

		return getEnterpriseAdmin().filterOrganizations(
			permissionChecker, organizations);
	}

	public static List<Role> filterRoles(
		PermissionChecker permissionChecker, List<Role> roles) {

		return getEnterpriseAdmin().filterRoles(permissionChecker, roles);
	}

	public static List<UserGroupRole> filterUserGroupRoles(
			PermissionChecker permissionChecker,
			List<UserGroupRole> userGroupRoles)
		throws PortalException, SystemException {

		return getEnterpriseAdmin().filterUserGroupRoles(
			permissionChecker, userGroupRoles);
	}

	public static List<UserGroup> filterUserGroups(
		PermissionChecker permissionChecker, List<UserGroup> userGroups) {

		return getEnterpriseAdmin().filterUserGroups(
			permissionChecker, userGroups);
	}

	public static List<Address> getAddresses(ActionRequest actionRequest) {
		return getEnterpriseAdmin().getAddresses(actionRequest);
	}

	public static List<EmailAddress> getEmailAddresses(
		ActionRequest actionRequest) {

		return getEnterpriseAdmin().getEmailAddresses(actionRequest);
	}

	public static OrderByComparator getGroupOrderByComparator(
		String orderByCol, String orderByType) {

		return getEnterpriseAdmin().getGroupOrderByComparator(
			orderByCol, orderByType);
	}

	public static Long[][] getLeftAndRightOrganizationIds(long organizationId)
		throws PortalException, SystemException {

		return getEnterpriseAdmin().getLeftAndRightOrganizationIds(
			organizationId);
	}

	public static Long[][] getLeftAndRightOrganizationIds(
		Organization organization) {

		return getEnterpriseAdmin().getLeftAndRightOrganizationIds(
			organization);
	}

	public static Long[][] getLeftAndRightOrganizationIds(
		List<Organization> organizations) {

		return getEnterpriseAdmin().getLeftAndRightOrganizationIds(
			organizations);
	}

	public static Long[] getOrganizationIds(List<Organization> organizations) {
		return getEnterpriseAdmin().getOrganizationIds(organizations);
	}

	public static OrderByComparator getOrganizationOrderByComparator(
		String orderByCol, String orderByType) {

		return getEnterpriseAdmin().getOrganizationOrderByComparator(
			orderByCol, orderByType);
	}

	public static List<OrgLabor> getOrgLabors(ActionRequest actionRequest) {
		return getEnterpriseAdmin().getOrgLabors(actionRequest);
	}

	public static OrderByComparator getPasswordPolicyOrderByComparator(
		String orderByCol, String orderByType) {

		return getEnterpriseAdmin().getPasswordPolicyOrderByComparator(
			orderByCol, orderByType);
	}

	public static List<Phone> getPhones(ActionRequest actionRequest) {
		return getEnterpriseAdmin().getPhones(actionRequest);
	}

	public static OrderByComparator getRoleOrderByComparator(
		String orderByCol, String orderByType) {

		return getEnterpriseAdmin().getRoleOrderByComparator(
			orderByCol, orderByType);
	}

	public static OrderByComparator getUserGroupOrderByComparator(
		String orderByCol, String orderByType) {

		return getEnterpriseAdmin().getUserGroupOrderByComparator(
			orderByCol, orderByType);
	}

	public static List<UserGroupRole> getUserGroupRoles(
			PortletRequest portletRequest)
		throws SystemException, PortalException {

		return getEnterpriseAdmin().getUserGroupRoles(portletRequest);
	}

	public static OrderByComparator getUserOrderByComparator(
		String orderByCol, String orderByType) {

		return getEnterpriseAdmin().getUserOrderByComparator(
			orderByCol, orderByType);
	}

	public static List<Website> getWebsites(ActionRequest actionRequest) {
		return getEnterpriseAdmin().getWebsites(actionRequest);
	}

	public static boolean hasUpdateEmailAddress(
			PermissionChecker permissionChecker, User user)
		throws PortalException, SystemException {

		return getEnterpriseAdmin().hasUpdateEmailAddress(
			permissionChecker, user);
	}

	public static boolean hasUpdateScreenName(
			PermissionChecker permissionChecker, User user)
		throws PortalException, SystemException {

		return getEnterpriseAdmin().hasUpdateScreenName(
			permissionChecker, user);
	}

	public static long[] removeRequiredRoles(long userId, long[] roleIds)
		throws PortalException, SystemException {

		return getEnterpriseAdmin().removeRequiredRoles(userId, roleIds);
	}

	public static long[] removeRequiredRoles(User user, long[] roleIds)
		throws PortalException, SystemException {

		return getEnterpriseAdmin().removeRequiredRoles(user, roleIds);
	}

	public static void updateAddresses(
			String className, long classPK, List<Address> addresses)
		throws PortalException, SystemException {

		getEnterpriseAdmin().updateAddresses(className, classPK, addresses);
	}

	public static void updateEmailAddresses(
			String className, long classPK, List<EmailAddress> emailAddresses)
		throws PortalException, SystemException {

		getEnterpriseAdmin().updateEmailAddresses(
			className, classPK, emailAddresses);
	}

	public static void updateOrgLabors(long classPK, List<OrgLabor> orgLabors)
		throws PortalException, SystemException {

		getEnterpriseAdmin().updateOrgLabors(classPK, orgLabors);
	}

	public static void updatePhones(
			String className, long classPK, List<Phone> phones)
		throws PortalException, SystemException {

		getEnterpriseAdmin().updatePhones(className, classPK, phones);
	}

	public static void updateWebsites(
			String className, long classPK, List<Website> websites)
		throws PortalException, SystemException {

		getEnterpriseAdmin().updateWebsites(className, classPK, websites);
	}

	public static EnterpriseAdmin getEnterpriseAdmin() {
		return _enterpriseAdmin;
	}

	public void setEnterpriseAdmin(EnterpriseAdmin enterpriseAdmin) {
		_enterpriseAdmin = enterpriseAdmin;
	}

	private static EnterpriseAdmin _enterpriseAdmin;

}