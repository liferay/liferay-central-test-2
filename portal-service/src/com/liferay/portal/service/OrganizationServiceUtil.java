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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="OrganizationServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link OrganizationService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrganizationService
 * @generated
 */
public class OrganizationServiceUtil {
	public static void addGroupOrganizations(long groupId,
		long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addGroupOrganizations(groupId, organizationIds);
	}

	public static void addPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addPasswordPolicyOrganizations(passwordPolicyId, organizationIds);
	}

	public static com.liferay.portal.model.Organization addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addOrganization(parentOrganizationId, name, type,
			recursable, regionId, countryId, statusId, comments, serviceContext);
	}

	public static com.liferay.portal.model.Organization addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.OrgLabor> orgLabors,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addOrganization(parentOrganizationId, name, type,
			recursable, regionId, countryId, statusId, comments, addresses,
			emailAddresses, orgLabors, phones, websites, serviceContext);
	}

	public static void deleteLogo(long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLogo(organizationId);
	}

	public static void deleteOrganization(long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteOrganization(organizationId);
	}

	public static java.util.List<com.liferay.portal.model.Organization> getManageableOrganizations(
		java.lang.String actionId, int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getManageableOrganizations(actionId, max);
	}

	public static com.liferay.portal.model.Organization getOrganization(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getOrganization(organizationId);
	}

	public static long getOrganizationId(long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getOrganizationId(companyId, name);
	}

	public static java.util.List<com.liferay.portal.model.Organization> getUserOrganizations(
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserOrganizations(userId);
	}

	public static java.util.List<com.liferay.portal.model.Organization> getUserOrganizations(
		long userId, boolean inheritUserGroups)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserOrganizations(userId, inheritUserGroups);
	}

	public static void setGroupOrganizations(long groupId,
		long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().setGroupOrganizations(groupId, organizationIds);
	}

	public static void unsetGroupOrganizations(long groupId,
		long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsetGroupOrganizations(groupId, organizationIds);
	}

	public static void unsetPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.unsetPasswordPolicyOrganizations(passwordPolicyId, organizationIds);
	}

	public static com.liferay.portal.model.Organization updateOrganization(
		long organizationId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateOrganization(organizationId, parentOrganizationId,
			name, type, recursable, regionId, countryId, statusId, comments,
			serviceContext);
	}

	public static com.liferay.portal.model.Organization updateOrganization(
		long organizationId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.OrgLabor> orgLabors,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateOrganization(organizationId, parentOrganizationId,
			name, type, recursable, regionId, countryId, statusId, comments,
			addresses, emailAddresses, orgLabors, phones, websites,
			serviceContext);
	}

	public static OrganizationService getService() {
		if (_service == null) {
			_service = (OrganizationService)PortalBeanLocatorUtil.locate(OrganizationService.class.getName());
		}

		return _service;
	}

	public void setService(OrganizationService service) {
		_service = service;
	}

	private static OrganizationService _service;
}