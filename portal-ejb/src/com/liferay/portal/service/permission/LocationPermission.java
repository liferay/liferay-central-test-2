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
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCheckerImpl;
import com.liferay.portal.service.spring.OrganizationLocalServiceUtil;

/**
 * <a href="LocationPermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Charles May
 *
 */
public class LocationPermission {

	public static void check(
			PermissionChecker permissionChecker, String locationId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, locationId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, String locationId,
			String actionId)
		throws PortalException, SystemException {

		PermissionCheckerImpl permissionCheckerImpl =
			(PermissionCheckerImpl)permissionChecker;

		if (permissionChecker.hasPermission(
				null, "com.liferay.portal.model.Location", locationId,
				actionId)) {

			return true;
		}
		else if (actionId.equals(ActionKeys.VIEW)) {
			User user = permissionCheckerImpl.getUser();

			Organization location = user.getLocation();

			if (locationId.equals(location.getOrganizationId())) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (actionId.endsWith("_USER")){
			Organization location =
				OrganizationLocalServiceUtil.getOrganization(locationId);

			String parentOrganizationId = location.getParentOrganizationId();

			if (permissionChecker.hasPermission(
					null, Organization.class.getName(), parentOrganizationId,
					actionId)) {

				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

}