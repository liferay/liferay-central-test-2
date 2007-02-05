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

package com.liferay.portal.service;

/**
 * <a href="OrganizationLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class OrganizationLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.dynamicQuery(queryInitializer, begin,
			end);
	}

	public static void addGroupOrganizations(long groupId,
		java.lang.String[] organizationIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();
		organizationLocalService.addGroupOrganizations(groupId, organizationIds);
	}

	public static com.liferay.portal.model.Organization addOrganization(
		java.lang.String userId, java.lang.String parentOrganizationId,
		java.lang.String name, java.lang.String regionId,
		java.lang.String countryId, int statusId, boolean location)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.addOrganization(userId,
			parentOrganizationId, name, regionId, countryId, statusId, location);
	}

	public static void addOrganizationResources(java.lang.String userId,
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();
		organizationLocalService.addOrganizationResources(userId, organization);
	}

	public static void deleteOrganization(java.lang.String organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();
		organizationLocalService.deleteOrganization(organizationId);
	}

	public static void deleteOrganization(
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();
		organizationLocalService.deleteOrganization(organization);
	}

	public static java.util.List getGroupOrganizations(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.getGroupOrganizations(groupId);
	}

	public static com.liferay.portal.model.Organization getOrganization(
		java.lang.String organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.getOrganization(organizationId);
	}

	public static java.lang.String getOrganizationId(
		java.lang.String companyId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.getOrganizationId(companyId, name);
	}

	public static java.util.List getUserOrganizations(java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.getUserOrganizations(userId);
	}

	public static boolean hasGroupOrganization(long groupId,
		java.lang.String organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.hasGroupOrganization(groupId,
			organizationId);
	}

	public static java.util.List search(java.lang.String companyId,
		java.lang.String parentOrganizationId,
		java.lang.String parentOrganizationComparator, java.lang.String name,
		java.lang.String street, java.lang.String city, java.lang.String zip,
		java.lang.String regionId, java.lang.String countryId,
		java.util.LinkedHashMap params, boolean andOperator, int begin, int end)
		throws com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.search(companyId, parentOrganizationId,
			parentOrganizationComparator, name, street, city, zip, regionId,
			countryId, params, andOperator, begin, end);
	}

	public static int searchCount(java.lang.String companyId,
		java.lang.String parentOrganizationId,
		java.lang.String parentOrganizationComparator, java.lang.String name,
		java.lang.String street, java.lang.String city, java.lang.String zip,
		java.lang.String regionId, java.lang.String countryId,
		java.util.LinkedHashMap params, boolean andOperator)
		throws com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.searchCount(companyId,
			parentOrganizationId, parentOrganizationComparator, name, street,
			city, zip, regionId, countryId, params, andOperator);
	}

	public static void setGroupOrganizations(long groupId,
		java.lang.String[] organizationIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();
		organizationLocalService.setGroupOrganizations(groupId, organizationIds);
	}

	public static void unsetGroupOrganizations(long groupId,
		java.lang.String[] organizationIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();
		organizationLocalService.unsetGroupOrganizations(groupId,
			organizationIds);
	}

	public static com.liferay.portal.model.Organization updateOrganization(
		java.lang.String companyId, java.lang.String organizationId,
		java.lang.String parentOrganizationId, java.lang.String name,
		java.lang.String regionId, java.lang.String countryId, int statusId,
		boolean location)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.updateOrganization(companyId,
			organizationId, parentOrganizationId, name, regionId, countryId,
			statusId, location);
	}

	public static com.liferay.portal.model.Organization updateOrganization(
		java.lang.String organizationId, java.lang.String comments)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.updateOrganization(organizationId,
			comments);
	}
}