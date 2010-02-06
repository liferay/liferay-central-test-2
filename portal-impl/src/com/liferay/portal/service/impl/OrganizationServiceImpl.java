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

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.OrganizationServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.service.permission.PasswordPolicyPermissionUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portlet.enterpriseadmin.util.EnterpriseAdminUtil;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <a href="OrganizationServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Julio Camarero
 */
public class OrganizationServiceImpl extends OrganizationServiceBaseImpl {

	public void addGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);

		organizationLocalService.addGroupOrganizations(
			groupId, organizationIds);
	}

	public void addPasswordPolicyOrganizations(
			long passwordPolicyId, long[] organizationIds)
		throws PortalException, SystemException {

		PasswordPolicyPermissionUtil.check(
			getPermissionChecker(), passwordPolicyId, ActionKeys.UPDATE);

		organizationLocalService.addPasswordPolicyOrganizations(
			passwordPolicyId, organizationIds);
	}

	public Organization addOrganization(
			long parentOrganizationId, String name, String type,
			boolean recursable, long regionId, long countryId, int statusId,
			String comments, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!OrganizationPermissionUtil.contains(
				getPermissionChecker(), parentOrganizationId,
				ActionKeys.MANAGE_SUBORGANIZATIONS) &&
			!PortalPermissionUtil.contains(
				getPermissionChecker(), ActionKeys.ADD_ORGANIZATION)) {

			throw new PrincipalException(
				"User " + getUserId() + " does not have permissions to add " +
					"an organization with parent " + parentOrganizationId);
		}

		return organizationLocalService.addOrganization(
			getUserId(), parentOrganizationId, name, type, recursable,
			regionId, countryId, statusId, comments, serviceContext);
	}

	public Organization addOrganization(
			long parentOrganizationId, String name, String type,
			boolean recursable, long regionId, long countryId, int statusId,
			String comments, List<Address> addresses,
			List<EmailAddress> emailAddresses, List<OrgLabor> orgLabors,
			List<Phone> phones, List<Website> websites,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Organization organization = addOrganization(
			parentOrganizationId, name, type, recursable, regionId, countryId,
			statusId, comments, serviceContext);

		EnterpriseAdminUtil.updateAddresses(
			Organization.class.getName(), organization.getOrganizationId(),
			addresses);

		EnterpriseAdminUtil.updateEmailAddresses(
			Organization.class.getName(), organization.getOrganizationId(),
			emailAddresses);

		EnterpriseAdminUtil.updateOrgLabors(organization.getOrganizationId(),
			orgLabors);

		EnterpriseAdminUtil.updatePhones(
			Organization.class.getName(), organization.getOrganizationId(),
			phones);

		EnterpriseAdminUtil.updateWebsites(
			Organization.class.getName(), organization.getOrganizationId(),
			websites);

		return organization;
	}

	public void deleteLogo(long organizationId)
		throws PortalException, SystemException {

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.UPDATE);

		organizationLocalService.deleteLogo(organizationId);
	}

	public void deleteOrganization(long organizationId)
		throws PortalException, SystemException {

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.DELETE);

		organizationLocalService.deleteOrganization(organizationId);
	}

	public List<Organization> getManageableOrganizations(
			String actionId, int max)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (permissionChecker.isCompanyAdmin()) {
			return organizationLocalService.search(
				permissionChecker.getCompanyId(),
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null, null,
				null, null, null, 0, max);
		}

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		List<Organization> userOrganizations =
			organizationLocalService.getUserOrganizations(
				permissionChecker.getUserId());

		Long[][] leftAndRightOrganizationIds =
			EnterpriseAdminUtil.getLeftAndRightOrganizationIds(
				userOrganizations);

		params.put("organizationsTree", leftAndRightOrganizationIds);

		List<Organization> manageableOrganizations =
			organizationLocalService.search(
				permissionChecker.getCompanyId(),
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null, null,
				null, null, params, 0, max);

		manageableOrganizations = ListUtil.copy(manageableOrganizations);

		Iterator<Organization> itr = manageableOrganizations.iterator();

		while (itr.hasNext()) {
			Organization organization = itr.next();

			if (!OrganizationPermissionUtil.contains(
					permissionChecker, organization, actionId)) {

				itr.remove();
			}
		}

		return manageableOrganizations;
	}

	public Organization getOrganization(long organizationId)
		throws PortalException, SystemException {

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.VIEW);

		return organizationLocalService.getOrganization(organizationId);
	}

	public long getOrganizationId(long companyId, String name)
		throws SystemException {

		return organizationLocalService.getOrganizationId(companyId, name);
	}

	public List<Organization> getUserOrganizations(long userId)
		throws PortalException, SystemException {

		return organizationLocalService.getUserOrganizations(userId);
	}

	public List<Organization> getUserOrganizations(
			long userId, boolean inheritUserGroups)
		throws PortalException, SystemException {

		return organizationLocalService.getUserOrganizations(
			userId, inheritUserGroups);
	}

	public void setGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);

		organizationLocalService.setGroupOrganizations(
			groupId, organizationIds);
	}

	public void unsetGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);

		organizationLocalService.unsetGroupOrganizations(
			groupId, organizationIds);
	}

	public void unsetPasswordPolicyOrganizations(
			long passwordPolicyId, long[] organizationIds)
		throws PortalException, SystemException {

		PasswordPolicyPermissionUtil.check(
			getPermissionChecker(), passwordPolicyId, ActionKeys.UPDATE);

		organizationLocalService.unsetPasswordPolicyOrganizations(
			passwordPolicyId, organizationIds);
	}

	public Organization updateOrganization(
			long organizationId, long parentOrganizationId, String name,
			String type, boolean recursable, long regionId, long countryId,
			int statusId, String comments, ServiceContext serviceContext)
		throws PortalException, SystemException {

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.UPDATE);

		return organizationLocalService.updateOrganization(
			getUser().getCompanyId(), organizationId, parentOrganizationId,
			name, type, recursable, regionId, countryId, statusId, comments,
			serviceContext);
	}

	public Organization updateOrganization(
			long organizationId, long parentOrganizationId, String name,
			String type, boolean recursable, long regionId, long countryId,
			int statusId, String comments, List<Address> addresses,
			List<EmailAddress> emailAddresses, List<OrgLabor> orgLabors,
			List<Phone> phones, List<Website> websites,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Organization organization = updateOrganization(
			organizationId, parentOrganizationId, name, type, recursable,
			regionId, countryId, statusId, comments, serviceContext);

		EnterpriseAdminUtil.updateAddresses(
			Organization.class.getName(), organizationId, addresses);

		EnterpriseAdminUtil.updateEmailAddresses(
			Organization.class.getName(), organizationId, emailAddresses);

		EnterpriseAdminUtil.updateOrgLabors(organizationId, orgLabors);

		EnterpriseAdminUtil.updatePhones(
			Organization.class.getName(), organizationId, phones);

		EnterpriseAdminUtil.updateWebsites(
			Organization.class.getName(), organizationId, websites);

		return organization;
	}

}