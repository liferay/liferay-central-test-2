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

import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCheckerImpl;
import com.liferay.util.Validator;

/**
 * <a href="UserPermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Charles May
 *
 */
public class UserPermission {

	public static void check(
			PermissionChecker permissionChecker, String userId,
			String organizationId, String locationId, String actionId)
		throws PrincipalException {

		if (!contains(
				permissionChecker, userId, organizationId, locationId,
				actionId)) {

			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, String userId,
		String organizationId, String locationId, String actionId) {

		PermissionCheckerImpl permissionCheckerImpl =
			(PermissionCheckerImpl)permissionChecker;

		String organizationActionId = actionId + "_USER";

		if (actionId.equals(ActionKeys.ADD_USER)) {
			organizationActionId = actionId;
		}

		if (permissionCheckerImpl.getUser().getUserId().equals(userId)) {
			return true;
		}
		else if (permissionChecker.hasPermission(
					null, User.class.getName(), userId, actionId)) {

			return true;
		}
		else if (Validator.isNotNull(organizationId) &&
				 permissionChecker.hasPermission(
					null, Organization.class.getName(), organizationId,
					organizationActionId)) {

			return true;
		}
		else if (Validator.isNotNull(locationId) &&
				 permissionChecker.hasPermission(
					null, "com.liferay.portal.model.Location", locationId,
					organizationActionId)) {

			return true;
		}

		return false;
	}

}