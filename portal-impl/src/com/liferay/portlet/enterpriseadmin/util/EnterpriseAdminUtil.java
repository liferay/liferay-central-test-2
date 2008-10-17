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

package com.liferay.portlet.enterpriseadmin.util;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Website;
import com.liferay.portal.model.impl.WebsiteImpl;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.WebsiteServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.service.permission.RolePermissionUtil;
import com.liferay.portal.util.comparator.ContactFirstNameComparator;
import com.liferay.portal.util.comparator.ContactJobTitleComparator;
import com.liferay.portal.util.comparator.ContactLastNameComparator;
import com.liferay.portal.util.comparator.GroupNameComparator;
import com.liferay.portal.util.comparator.GroupTypeComparator;
import com.liferay.portal.util.comparator.OrganizationNameComparator;
import com.liferay.portal.util.comparator.OrganizationTypeComparator;
import com.liferay.portal.util.comparator.PasswordPolicyDescriptionComparator;
import com.liferay.portal.util.comparator.PasswordPolicyNameComparator;
import com.liferay.portal.util.comparator.RoleDescriptionComparator;
import com.liferay.portal.util.comparator.RoleNameComparator;
import com.liferay.portal.util.comparator.RoleTypeComparator;
import com.liferay.portal.util.comparator.UserEmailAddressComparator;
import com.liferay.portal.util.comparator.UserGroupDescriptionComparator;
import com.liferay.portal.util.comparator.UserGroupNameComparator;
import com.liferay.portal.util.comparator.UserScreenNameComparator;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementsEntryImpl;
import com.liferay.portlet.announcements.service.AnnouncementsDeliveryServiceUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.ActionRequest;

/**
 * <a href="EnterpriseAdminUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class EnterpriseAdminUtil {

	public static List<Group> filterCommunities(
			PermissionChecker permissionChecker, List<Group> communities)
		throws PortalException, SystemException {

		if (permissionChecker.isCompanyAdmin()) {
			return communities;
		}

		List<Group> filteredCommunities = new ArrayList<Group>();

		for (Group community : communities) {
			if (community.getName().equals(GroupConstants.CONTROL_PANEL)) {
				continue;
			}

			if (!GroupPermissionUtil.contains(
					permissionChecker, community.getGroupId(),
					ActionKeys.ASSIGN_MEMBERS)) {
				continue;
			}

			filteredCommunities.add(community);
		}

		return filteredCommunities;
	}

	public static List<Organization> filterOrganizations(
			PermissionChecker permissionChecker,
			List<Organization> organizations)
		throws PortalException, SystemException {

		if (permissionChecker.isCompanyAdmin()) {
			return organizations;
		}

		List<Organization> filteredOrganizations =
			new ArrayList<Organization>();

		for (Organization organization : organizations) {
			if (!OrganizationPermissionUtil.contains(
					permissionChecker, organization.getOrganizationId(),
					ActionKeys.ASSIGN_MEMBERS)) {
				continue;
			}

			filteredOrganizations.add(organization);
		}

		return filteredOrganizations;
	}

	public static List<Role> filterRoles(
		PermissionChecker permissionChecker, List<Role> roles) {

		if (permissionChecker.isCompanyAdmin()) {
			return roles;
		}

		List<Role> filteredRoles = new ArrayList<Role>();

		for (Role role : roles) {
			if (role.getName().equals(RoleConstants.GUEST) ||
				role.getName().equals(RoleConstants.OWNER) ||
				role.getName().equals(RoleConstants.USER)) {
				continue;
			}

			if (!RolePermissionUtil.contains(
					permissionChecker, role.getRoleId(),
					ActionKeys.ASSIGN_MEMBERS)) {
				continue;
			}

			filteredRoles.add(role);
		}

		return filteredRoles;
	}

	public static ArrayList<AnnouncementsDelivery> getAnnouncementsDeliveries(
		ActionRequest actionRequest) {

		ArrayList<AnnouncementsDelivery> deliveries =
			new ArrayList<AnnouncementsDelivery>();

		for (String type : AnnouncementsEntryImpl.TYPES) {
			boolean email = ParamUtil.getBoolean(
				actionRequest, "announcementsType" + type + "Email");
			boolean sms = ParamUtil.getBoolean(
				actionRequest, "announcementsType" + type + "Sms");
			boolean website = ParamUtil.getBoolean(
				actionRequest, "announcementsType" + type + "Website");

			AnnouncementsDelivery delivery = new AnnouncementsDeliveryImpl();

			delivery.setEmail(email);
			delivery.setSms(sms);
			delivery.setWebsite(website);

			deliveries.add(delivery);
		}

		return deliveries;
	}

	public static OrderByComparator getGroupOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new GroupNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("type")) {
			orderByComparator = new GroupTypeComparator(orderByAsc);
		}
		else {
			orderByComparator = new GroupNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static String getIdName(String name) {
		int pos = name.indexOf(StringPool.DASH);

		if (pos == -1) {
			return name;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(name.substring(0, pos));
		sb.append(name.substring(pos + 1, pos + 2).toUpperCase());
		sb.append(name.substring(pos + 2));

		return getIdName(sb.toString());
	}

	public static String getJspName(String name) {
		return StringUtil.replace(name, StringPool.DASH, StringPool.UNDERLINE);
	}

	public static Long[] getOrganizationIds(List<Organization> organizations) {
		Long[] organizationIds = new Long[organizations.size()];

		for (int i = 0; i < organizations.size(); i++) {
			Organization organization = organizations.get(i);

			organizationIds[i] = new Long(organization.getOrganizationId());
		}

		return organizationIds;
	}

	public static OrderByComparator getOrganizationOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new OrganizationNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("type")) {
			orderByComparator = new OrganizationTypeComparator(orderByAsc);
		}
		else {
			orderByComparator = new OrganizationNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator getPasswordPolicyOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new PasswordPolicyNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("description")) {
			orderByComparator = new PasswordPolicyDescriptionComparator(
				orderByAsc);
		}
		else {
			orderByComparator = new PasswordPolicyNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static long[] getPrimaryKeys(
		ActionRequest actionRequest, String name) {

		long[] primKeys = StringUtil.split(ParamUtil.getString(
			actionRequest, name + "PrimaryKeys"),  0L);

		// Temporal fix until the frontend UI gets fixed

		List<Long> filteredKeys = new ArrayList<Long>();

		for (long primKey : primKeys) {
			if (primKey > 0) {
				filteredKeys.add(primKey);
			}
		}

		primKeys = ArrayUtil.toArray(
			filteredKeys.toArray(new Long[filteredKeys.size()]));

		return primKeys;
	}
	public static OrderByComparator getRoleOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new RoleNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("description")) {
			orderByComparator = new RoleDescriptionComparator(orderByAsc);
		}
		else if (orderByCol.equals("type")) {
			orderByComparator = new RoleTypeComparator(orderByAsc);
		}
		else {
			orderByComparator = new RoleNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator getUserGroupOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new UserGroupNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("description")) {
			orderByComparator = new UserGroupDescriptionComparator(orderByAsc);
		}
		else {
			orderByComparator = new UserGroupNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator getUserOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("email-address")) {
			orderByComparator = new UserEmailAddressComparator(orderByAsc);
		}
		else if (orderByCol.equals("first-name")) {
			orderByComparator = new ContactFirstNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("job-title")) {
			orderByComparator = new ContactJobTitleComparator(orderByAsc);
		}
		else if (orderByCol.equals("last-name")) {
			orderByComparator = new ContactLastNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("screen-name")) {
			orderByComparator = new UserScreenNameComparator(orderByAsc);
		}
		else {
			orderByComparator = new ContactLastNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static ArrayList<Website> getWebsites(ActionRequest actionRequest) {
		ArrayList<Website> websites = new ArrayList<Website>();

		int[] websiteIndexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "websiteIndexes"), 0);

		int websitePrimary = ParamUtil.getInteger(
			actionRequest, "websitePrimary");

		for (int websiteIndex : websiteIndexes) {
			long websiteId = ParamUtil.getLong(
				actionRequest, "websiteId" + websiteIndex);

			String url = ParamUtil.getString(
				actionRequest, "websiteUrl" + websiteIndex);

			if (Validator.isNull(url)){
				continue;
			}

			int typeId = ParamUtil.getInteger(
				actionRequest, "websiteTypeId" + websiteIndex);

			boolean primary = false;

			if (websiteIndex == websitePrimary){
				primary = true;
			}

			Website website = new WebsiteImpl();

			website.setWebsiteId(websiteId);
			website.setUrl(url);
			website.setTypeId(typeId);
			website.setPrimary(primary);

			websites.add(website);
		}

		return websites;
	}

	public static void updateWebsites(
			String className, long classPK, List<Website> websites)
		throws PortalException, SystemException {

		Set<Long> websiteIds = new HashSet<Long>();

		for (Website website : websites) {
			long websiteId = website.getWebsiteId();

			String url = website.getUrl();
			int typeId = website.getTypeId();
			boolean primary = website.isPrimary();

			if (websiteId <= 0) {
				website = WebsiteServiceUtil.addWebsite(
					className, classPK, url, typeId, primary);

				websiteId = website.getWebsiteId();
			}
			else {
				WebsiteServiceUtil.updateWebsite(
					websiteId, url, typeId, primary);
			}

			websiteIds.add(websiteId);
		}

		websites = WebsiteServiceUtil.getWebsites(className, classPK);

		for (Website website : websites) {
			if (!websiteIds.contains(website.getWebsiteId())) {
				WebsiteServiceUtil.deleteWebsite(website.getWebsiteId());
			}
		}
	}

	public static void updateAnnouncementsDeliveries(
			long userId, List<AnnouncementsDelivery> deliveries)
		throws PortalException, SystemException {

		for (AnnouncementsDelivery delivery : deliveries) {
			AnnouncementsDeliveryServiceUtil.updateDelivery(
				userId, delivery.getType(), delivery.getEmail(),
				delivery.getSms(), delivery.getWebsite());
		}
	}

}