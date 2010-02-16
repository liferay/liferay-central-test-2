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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.OrganizationLocalServiceUtil;

/**
 * <a href="OrganizationPermissionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Charles May
 * @author Jorge Ferrer
 */
public class OrganizationPermissionImpl implements OrganizationPermission {

	public void check(
			PermissionChecker permissionChecker, long organizationId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, organizationId, actionId)) {
			throw new PrincipalException();
		}
	}

	public void check(
			PermissionChecker permissionChecker, Organization organization,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, organization, actionId)) {
			throw new PrincipalException();
		}
	}

	public boolean contains(
			PermissionChecker permissionChecker, long organizationId,
			String actionId)
		throws PortalException, SystemException {

		if (organizationId > 0) {
			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(organizationId);

			return contains(permissionChecker, organization, actionId);
		}
		else {
			return false;
		}
	}

	public boolean contains(
			PermissionChecker permissionChecker, Organization organization,
			String actionId)
		throws PortalException, SystemException {

		Group group = organization.getGroup();

		long groupId = group.getGroupId();

		if (contains(permissionChecker, groupId, organization, actionId)) {
			return true;
		}

		while (!organization.isRoot()) {
			Organization parentOrganization =
				organization.getParentOrganization();

			Group parentGroup = parentOrganization.getGroup();

			groupId = parentGroup.getGroupId();

			if (contains(
					permissionChecker, groupId, parentOrganization,
					ActionKeys.MANAGE_SUBORGANIZATIONS)) {

				return true;
			}

			organization = parentOrganization;
		}

		return false;
	}

	protected boolean contains(
			PermissionChecker permissionChecker, long groupId,
			Organization organization, String actionId)
		throws PortalException, SystemException {

		while ((organization != null) &&
			   (organization.getOrganizationId() !=
					OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID)) {

			if (permissionChecker.hasPermission(
					groupId, Organization.class.getName(),
					organization.getOrganizationId(), actionId)) {

				return true;
			}

			organization = organization.getParentOrganization();
		}

		return false;
	}

}