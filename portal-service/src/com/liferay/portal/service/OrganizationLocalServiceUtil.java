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
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portal.service.OrganizationLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.OrganizationLocalServiceFactory</code> is responsible
 * for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.OrganizationLocalService
 * @see com.liferay.portal.service.OrganizationLocalServiceFactory
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
		long[] organizationIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();
		organizationLocalService.addGroupOrganizations(groupId, organizationIds);
	}

	public static com.liferay.portal.model.Organization addOrganization(
		long userId, long parentOrganizationId, java.lang.String name,
		boolean location, boolean recursable, long regionId, long countryId,
		int statusId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.addOrganization(userId,
			parentOrganizationId, name, location, recursable, regionId,
			countryId, statusId);
	}

	public static void addOrganizationResources(long userId,
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();
		organizationLocalService.addOrganizationResources(userId, organization);
	}

	public static void addPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();
		organizationLocalService.addPasswordPolicyOrganizations(passwordPolicyId,
			organizationIds);
	}

	public static void deleteOrganization(long organizationId)
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

	public static java.util.List getAncestorOrganizations(long organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.getAncestorOrganizations(organizationId);
	}

	public static java.util.List getGroupOrganizations(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.getGroupOrganizations(groupId);
	}

	public static com.liferay.portal.model.Organization getOrganization(
		long organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.getOrganization(organizationId);
	}

	public static long getOrganizationId(long companyId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.getOrganizationId(companyId, name);
	}

	public static java.util.List getRecursableAncestorOrganizations(
		long organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.getRecursableAncestorOrganizations(organizationId);
	}

	public static java.util.List getUserOrganizations(long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.getUserOrganizations(userId);
	}

	public static boolean hasGroupOrganization(long groupId, long organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.hasGroupOrganization(groupId,
			organizationId);
	}

	public static boolean hasUserOrganization(long userId, long organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.hasUserOrganization(userId,
			organizationId);
	}

	public static boolean hasPasswordPolicyOrganization(long passwordPolicyId,
		long organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.hasPasswordPolicyOrganization(passwordPolicyId,
			organizationId);
	}

	public static boolean isAncestor(long locationId,
		long ancestorOrganizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.isAncestor(locationId,
			ancestorOrganizationId);
	}

	public static java.util.List search(long companyId,
		long parentOrganizationId, java.lang.String name, boolean location,
		java.lang.String street, java.lang.String city, java.lang.String zip,
		java.lang.Long regionId, java.lang.Long countryId,
		java.util.LinkedHashMap params, boolean andOperator, int begin, int end)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.search(companyId, parentOrganizationId,
			name, location, street, city, zip, regionId, countryId, params,
			andOperator, begin, end);
	}

	public static int searchCount(long companyId, long parentOrganizationId,
		java.lang.String name, boolean location, java.lang.String street,
		java.lang.String city, java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId, java.util.LinkedHashMap params,
		boolean andOperator)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.searchCount(companyId,
			parentOrganizationId, name, location, street, city, zip, regionId,
			countryId, params, andOperator);
	}

	public static void setGroupOrganizations(long groupId,
		long[] organizationIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();
		organizationLocalService.setGroupOrganizations(groupId, organizationIds);
	}

	public static void unsetGroupOrganizations(long groupId,
		long[] organizationIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();
		organizationLocalService.unsetGroupOrganizations(groupId,
			organizationIds);
	}

	public static void unsetPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();
		organizationLocalService.unsetPasswordPolicyOrganizations(passwordPolicyId,
			organizationIds);
	}

	public static com.liferay.portal.model.Organization updateOrganization(
		long companyId, long organizationId, long parentOrganizationId,
		java.lang.String name, boolean location, boolean recursable,
		long regionId, long countryId, int statusId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.updateOrganization(companyId,
			organizationId, parentOrganizationId, name, location, recursable,
			regionId, countryId, statusId);
	}

	public static com.liferay.portal.model.Organization updateOrganization(
		long organizationId, java.lang.String comments)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalService organizationLocalService = OrganizationLocalServiceFactory.getService();

		return organizationLocalService.updateOrganization(organizationId,
			comments);
	}
}