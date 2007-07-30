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

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Organization;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.OrganizationService;
import com.liferay.portal.service.permission.GroupPermission;
import com.liferay.portal.service.permission.LocationPermission;
import com.liferay.portal.service.permission.OrganizationPermission;
import com.liferay.portal.service.permission.PortalPermission;
import com.liferay.portal.service.persistence.OrganizationUtil;

import java.util.List;

/**
 * <a href="OrganizationServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class OrganizationServiceImpl extends PrincipalBean
	implements OrganizationService {

	public void addGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException, SystemException {

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		OrganizationLocalServiceUtil.addGroupOrganizations(
			groupId, organizationIds);
	}

	public void addPasswordPolicyOrganizations(
			long passwordPolicyId, long[] organizationIds)
		throws PortalException, SystemException {

		//PasswordPolicyPermission.check(
		//	getPermissionChecker(), passwordPolicyId, ActionKeys.UPDATE);

		OrganizationLocalServiceUtil.addPasswordPolicyOrganizations(
			passwordPolicyId, organizationIds);
	}

	public Organization addOrganization(
			long parentOrganizationId, String name, boolean location,
			boolean recursable, long regionId, long countryId, int statusId)
		throws PortalException, SystemException {

		if (location) {
			OrganizationPermission.check(
				getPermissionChecker(), parentOrganizationId,
				ActionKeys.ADD_LOCATION);
		}
		else {
			PortalPermission.check(
				getPermissionChecker(), ActionKeys.ADD_ORGANIZATION);
		}

		return OrganizationLocalServiceUtil.addOrganization(
			getUserId(), parentOrganizationId, name, location, recursable,
			regionId, countryId, statusId);
	}

	public void deleteOrganization(long organizationId)
		throws PortalException, SystemException {

		checkPermission(organizationId, ActionKeys.DELETE);

		OrganizationLocalServiceUtil.deleteOrganization(organizationId);
	}

	public Organization getOrganization(long organizationId)
		throws PortalException, SystemException {

		checkPermission(organizationId, ActionKeys.VIEW);

		return OrganizationLocalServiceUtil.getOrganization(organizationId);
	}

	public long getOrganizationId(long companyId, String name)
		throws PortalException, SystemException {

		return OrganizationLocalServiceUtil.getOrganizationId(companyId, name);
	}

	public List getUserOrganizations(long userId)
		throws PortalException, SystemException {

		return OrganizationLocalServiceUtil.getUserOrganizations(userId);
	}

	public void setGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException, SystemException {

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		OrganizationLocalServiceUtil.setGroupOrganizations(
			groupId, organizationIds);
	}

	public void unsetGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException, SystemException {

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		OrganizationLocalServiceUtil.unsetGroupOrganizations(
			groupId, organizationIds);
	}

	public void unsetPasswordPolicyOrganizations(
			long passwordPolicyId, long[] organizationIds)
		throws PortalException, SystemException {

		//PasswordPolicyPermission.check(
		//	getPermissionChecker(), passwordPolicyId, ActionKeys.UPDATE);

		OrganizationLocalServiceUtil.unsetPasswordPolicyOrganizations(
			passwordPolicyId, organizationIds);
	}

	public Organization updateOrganization(
			long organizationId, long parentOrganizationId, String name,
			boolean location, boolean recursable, long regionId, long countryId,
			int statusId)
		throws PortalException, SystemException {

		checkPermission(organizationId, ActionKeys.UPDATE);

		return OrganizationLocalServiceUtil.updateOrganization(
			getUser().getCompanyId(), organizationId, parentOrganizationId,
			name, location, recursable, regionId, countryId, statusId);
	}

	public Organization updateOrganization(long organizationId, String comments)
		throws PortalException, SystemException {

		checkPermission(organizationId, ActionKeys.UPDATE);

		return OrganizationLocalServiceUtil.updateOrganization(
			organizationId, comments);
	}

	protected void checkPermission(long organizationId, String actionId)
		throws PortalException, SystemException {

		Organization organization =
			OrganizationUtil.findByPrimaryKey(organizationId);

		if (!organization.isLocation()) {
			OrganizationPermission.check(
				getPermissionChecker(), organizationId, actionId);
		}
		else {
			LocationPermission.check(
				getPermissionChecker(), organizationId, actionId);
		}
	}

}