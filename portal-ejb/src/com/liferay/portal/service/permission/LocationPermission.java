/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Organization;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.spring.OrganizationLocalServiceUtil;
import com.liferay.util.Validator;

/**
 * <a href="LocationPermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Charles May
 *
 */
public class LocationPermission {

	public static void check(
			PermissionChecker permissionChecker, String organizationId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, organizationId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, String organizationId,
			String actionId)
		throws PortalException, SystemException {

		String name = "com.liferay.portal.model.Location";

		if (permissionChecker.hasPermission(
				null, name, organizationId, actionId)) {
			return true;
		}
		else if (Validator.isNotNull(organizationId) &&
				 actionId.endsWith("_USER")){

			Organization location =
				OrganizationLocalServiceUtil.getOrganization(organizationId);

			String parentOrganizationId =
				location.getParentOrganizationId();

			name = Organization.class.getName();

			if (permissionChecker.hasPermission(
					null, name, parentOrganizationId, actionId)) {

				return true;
			}
			else {
				return false;
			}
		}

		return false;
	}

}