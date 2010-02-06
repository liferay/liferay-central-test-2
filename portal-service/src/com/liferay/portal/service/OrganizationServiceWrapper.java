/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
 * <a href="OrganizationServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link OrganizationService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrganizationService
 * @generated
 */
public class OrganizationServiceWrapper implements OrganizationService {
	public OrganizationServiceWrapper(OrganizationService organizationService) {
		_organizationService = organizationService;
	}

	public void addGroupOrganizations(long groupId, long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_organizationService.addGroupOrganizations(groupId, organizationIds);
	}

	public void addPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_organizationService.addPasswordPolicyOrganizations(passwordPolicyId,
			organizationIds);
	}

	public com.liferay.portal.model.Organization addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationService.addOrganization(parentOrganizationId, name,
			type, recursable, regionId, countryId, statusId, comments,
			serviceContext);
	}

	public com.liferay.portal.model.Organization addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.OrgLabor> orgLabors,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationService.addOrganization(parentOrganizationId, name,
			type, recursable, regionId, countryId, statusId, comments,
			addresses, emailAddresses, orgLabors, phones, websites,
			serviceContext);
	}

	public void deleteLogo(long organizationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_organizationService.deleteLogo(organizationId);
	}

	public void deleteOrganization(long organizationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_organizationService.deleteOrganization(organizationId);
	}

	public java.util.List<com.liferay.portal.model.Organization> getManageableOrganizations(
		java.lang.String actionId, int max)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationService.getManageableOrganizations(actionId, max);
	}

	public com.liferay.portal.model.Organization getOrganization(
		long organizationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationService.getOrganization(organizationId);
	}

	public long getOrganizationId(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return _organizationService.getOrganizationId(companyId, name);
	}

	public java.util.List<com.liferay.portal.model.Organization> getUserOrganizations(
		long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationService.getUserOrganizations(userId);
	}

	public java.util.List<com.liferay.portal.model.Organization> getUserOrganizations(
		long userId, boolean inheritUserGroups)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationService.getUserOrganizations(userId,
			inheritUserGroups);
	}

	public void setGroupOrganizations(long groupId, long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_organizationService.setGroupOrganizations(groupId, organizationIds);
	}

	public void unsetGroupOrganizations(long groupId, long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_organizationService.unsetGroupOrganizations(groupId, organizationIds);
	}

	public void unsetPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_organizationService.unsetPasswordPolicyOrganizations(passwordPolicyId,
			organizationIds);
	}

	public com.liferay.portal.model.Organization updateOrganization(
		long organizationId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationService.updateOrganization(organizationId,
			parentOrganizationId, name, type, recursable, regionId, countryId,
			statusId, comments, serviceContext);
	}

	public com.liferay.portal.model.Organization updateOrganization(
		long organizationId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.OrgLabor> orgLabors,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationService.updateOrganization(organizationId,
			parentOrganizationId, name, type, recursable, regionId, countryId,
			statusId, comments, addresses, emailAddresses, orgLabors, phones,
			websites, serviceContext);
	}

	public OrganizationService getWrappedOrganizationService() {
		return _organizationService;
	}

	private OrganizationService _organizationService;
}