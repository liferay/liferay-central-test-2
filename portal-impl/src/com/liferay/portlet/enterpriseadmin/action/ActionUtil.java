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

package com.liferay.portlet.enterpriseadmin.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.AddressServiceUtil;
import com.liferay.portal.service.EmailAddressServiceUtil;
import com.liferay.portal.service.OrgLaborServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.OrganizationServiceUtil;
import com.liferay.portal.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.service.PhoneServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.RoleServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupServiceUtil;
import com.liferay.portal.service.WebsiteServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class ActionUtil {

	public static void getAddress(HttpServletRequest request)
		throws Exception {

		long addressId = ParamUtil.getLong(request, "addressId");

		Address address = null;

		if (addressId > 0) {
			address = AddressServiceUtil.getAddress(addressId);
		}

		request.setAttribute(WebKeys.ADDRESS, address);
	}

	public static void getAddress(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getAddress(request);
	}

	public static void getEmailAddress(HttpServletRequest request)
		throws Exception {

		long emailAddressId = ParamUtil.getLong(request, "emailAddressId");

		EmailAddress emailAddress = null;

		if (emailAddressId > 0) {
			emailAddress =
				EmailAddressServiceUtil.getEmailAddress(emailAddressId);
		}

		request.setAttribute(WebKeys.EMAIL_ADDRESS, emailAddress);
	}

	public static void getEmailAddress(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getEmailAddress(request);
	}

	public static void getOrganization(HttpServletRequest request)
		throws Exception {

		long organizationId = ParamUtil.getLong(request, "organizationId");

		Organization organization = null;

		if (organizationId > 0) {
			organization =
				OrganizationServiceUtil.getOrganization(organizationId);
		}

		request.setAttribute(WebKeys.ORGANIZATION, organization);
	}

	public static void getOrganization(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getOrganization(request);
	}

	public static void getOrgLabor(HttpServletRequest request)
		throws Exception {

		long orgLaborId = ParamUtil.getLong(request, "orgLaborId");

		OrgLabor orgLabor = null;

		if (orgLaborId > 0) {
			orgLabor = OrgLaborServiceUtil.getOrgLabor(orgLaborId);
		}

		request.setAttribute(WebKeys.ORG_LABOR, orgLabor);
	}

	public static void getOrgLabor(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getOrgLabor(request);
	}

	public static void getPasswordPolicy(HttpServletRequest request)
		throws Exception {

		long passwordPolicyId = ParamUtil.getLong(request, "passwordPolicyId");

		PasswordPolicy passwordPolicy = null;

		if (passwordPolicyId > 0) {
			passwordPolicy = PasswordPolicyLocalServiceUtil.getPasswordPolicy(
				passwordPolicyId);
		}

		request.setAttribute(WebKeys.PASSWORD_POLICY, passwordPolicy);
	}

	public static void getPasswordPolicy(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getPasswordPolicy(request);
	}

	public static void getPhone(HttpServletRequest request) throws Exception {
		long phoneId = ParamUtil.getLong(request, "phoneId");

		Phone phone = null;

		if (phoneId > 0) {
			phone = PhoneServiceUtil.getPhone(phoneId);
		}

		request.setAttribute(WebKeys.PHONE, phone);
	}

	public static void getPhone(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getPhone(request);
	}

	public static void getRole(HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long roleId = ParamUtil.getLong(request, "roleId");

		Role role = null;

		Group group = (Group)request.getAttribute(WebKeys.GROUP);

		if ((group != null) && group.isCommunity()) {
			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					themeDisplay.getUserId(), group.getGroupId(),
					RoleConstants.COMMUNITY_ADMINISTRATOR, true) ||
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					themeDisplay.getUserId(), group.getGroupId(),
					RoleConstants.COMMUNITY_OWNER, true)) {

				if (roleId > 0) {
					role = RoleLocalServiceUtil.getRole(roleId);
				}
			}
			else {
				if (roleId > 0) {
					role = RoleServiceUtil.getRole(roleId);
				}
			}
		}
		else if ((group != null) && group.isOrganization()) {
			long organizationId = group.getOrganizationId();

			while (organizationId !=
						OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

				Organization organization =
					OrganizationLocalServiceUtil.getOrganization(
						organizationId);

				Group organizationGroup = organization.getGroup();

				long organizationGroupId = organizationGroup.getGroupId();

				if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
						themeDisplay.getUserId(), organizationGroupId,
						RoleConstants.ORGANIZATION_ADMINISTRATOR, true) ||
					UserGroupRoleLocalServiceUtil.hasUserGroupRole(
						themeDisplay.getUserId(), organizationGroupId,
						RoleConstants.ORGANIZATION_OWNER, true)) {

					if (roleId > 0) {
						role = RoleLocalServiceUtil.getRole(roleId);
					}

					break;
				}

				organizationId = organization.getParentOrganizationId();
			}

			if (roleId > 0 && (role == null)) {
				role = RoleServiceUtil.getRole(roleId);
			}
		}
		else {
			if (roleId > 0) {
				role = RoleServiceUtil.getRole(roleId);
			}
		}

		request.setAttribute(WebKeys.ROLE, role);
	}

	public static void getRole(PortletRequest portletRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getRole(request);
	}

	public static void getUserGroup(HttpServletRequest request)
		throws Exception {

		long userGroupId = ParamUtil.getLong(request, "userGroupId");

		UserGroup userGroup = null;

		if (userGroupId > 0) {
			userGroup = UserGroupServiceUtil.getUserGroup(userGroupId);
		}

		request.setAttribute(WebKeys.USER_GROUP, userGroup);
	}

	public static void getUserGroup(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getUserGroup(request);
	}

	public static void getWebsite(HttpServletRequest request) throws Exception {
		long websiteId = ParamUtil.getLong(request, "websiteId");

		Website website = null;

		if (websiteId > 0) {
			website = WebsiteServiceUtil.getWebsite(websiteId);
		}

		request.setAttribute(WebKeys.WEBSITE, website);
	}

	public static void getWebsite(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getWebsite(request);
	}

}