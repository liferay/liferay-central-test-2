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

package com.liferay.portlet.enterpriseadmin.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.AddressServiceUtil;
import com.liferay.portal.service.EmailAddressServiceUtil;
import com.liferay.portal.service.OrgLaborServiceUtil;
import com.liferay.portal.service.OrganizationServiceUtil;
import com.liferay.portal.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.service.PhoneServiceUtil;
import com.liferay.portal.service.RoleServiceUtil;
import com.liferay.portal.service.UserGroupServiceUtil;
import com.liferay.portal.service.WebsiteServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="ActionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class ActionUtil {

	public static void getAddress(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getAddress(request);
	}

	public static void getAddress(RenderRequest renderRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getAddress(request);
	}

	public static void getAddress(HttpServletRequest request)
		throws Exception {

		long addressId = ParamUtil.getLong(request, "addressId");

		Address address = null;

		if (addressId > 0) {
			address = AddressServiceUtil.getAddress(addressId);
		}

		request.setAttribute(WebKeys.ADDRESS, address);
	}

	public static void getEmailAddress(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getEmailAddress(request);
	}

	public static void getEmailAddress(RenderRequest renderRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getEmailAddress(request);
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

	public static void getPhone(ActionRequest actionRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getPhone(request);
	}

	public static void getPhone(RenderRequest renderRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getPhone(request);
	}

	public static void getPhone(HttpServletRequest request) throws Exception {
		long phoneId = ParamUtil.getLong(request, "phoneId");

		Phone phone = null;

		if (phoneId > 0) {
			phone = PhoneServiceUtil.getPhone(phoneId);
		}

		request.setAttribute(WebKeys.PHONE, phone);
	}

	public static void getOrganization(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getOrganization(request);
	}

	public static void getOrganization(RenderRequest renderRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getOrganization(request);
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

	public static void getOrgLabor(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getOrgLabor(request);
	}

	public static void getOrgLabor(RenderRequest renderRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getOrgLabor(request);
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

	public static void getPasswordPolicy(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getPasswordPolicy(request);
	}

	public static void getPasswordPolicy(RenderRequest renderRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getPasswordPolicy(request);
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

	public static void getRole(ActionRequest actionRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getRole(request);
	}

	public static void getRole(RenderRequest renderRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getRole(request);
	}

	public static void getRole(HttpServletRequest request)
		throws Exception {

		long roleId = ParamUtil.getLong(request, "roleId");

		Role role = null;

		if (roleId > 0) {
			role = RoleServiceUtil.getRole(roleId);
		}

		request.setAttribute(WebKeys.ROLE, role);
	}

	public static void getUserGroup(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getUserGroup(request);
	}

	public static void getUserGroup(RenderRequest renderRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getUserGroup(request);
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

	public static void getWebsite(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getWebsite(request);
	}

	public static void getWebsite(RenderRequest renderRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getWebsite(request);
	}

	public static void getWebsite(HttpServletRequest request) throws Exception {
		long websiteId = ParamUtil.getLong(request, "websiteId");

		Website website = null;

		if (websiteId > 0) {
			website = WebsiteServiceUtil.getWebsite(websiteId);
		}

		request.setAttribute(WebKeys.WEBSITE, website);
	}

}