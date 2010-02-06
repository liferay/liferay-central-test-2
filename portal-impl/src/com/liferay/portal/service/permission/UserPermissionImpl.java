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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

/**
 * <a href="UserPermissionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Charles May
 * @author Jorge Ferrer
 */
public class UserPermissionImpl implements UserPermission {

	public void check(
			PermissionChecker permissionChecker, long userId, String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, userId, actionId)) {
			throw new PrincipalException();
		}
	}

	/**
	 * @deprecated
	 */
	public void check(
			PermissionChecker permissionChecker, long userId,
			long organizationId, long locationId, String actionId)
		throws PrincipalException {

		check(
			permissionChecker, userId, new long[] {organizationId, locationId},
			actionId);
	}

	public void check(
			PermissionChecker permissionChecker, long userId,
			long[] organizationIds, String actionId)
		throws PrincipalException {

		if (!contains(
				permissionChecker, userId, organizationIds, actionId)) {

			throw new PrincipalException();
		}
	}

	public boolean contains(
		PermissionChecker permissionChecker, long userId, String actionId) {

		return contains(permissionChecker, userId, null, actionId);
	}

	/**
	 * @deprecated
	 */
	public boolean contains(
		PermissionChecker permissionChecker, long userId, long organizationId,
		long locationId, String actionId) {

		return contains(
			permissionChecker, userId, new long[] {organizationId, locationId},
			actionId);
	}

	public boolean contains(
		PermissionChecker permissionChecker, long userId,
		long[] organizationIds, String actionId) {

		if (((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5 ||
			  PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) &&
			 (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(), User.class.getName(), userId,
				userId, actionId))) ||
			(permissionChecker.getUserId() == userId)) {

			return true;
		}
		else if (permissionChecker.hasPermission(
					0, User.class.getName(), userId, actionId)) {

			return true;
		}
		else if (userId != ResourceConstants.PRIMKEY_DNE) {
			try {
				if (organizationIds == null) {
					User user = UserLocalServiceUtil.getUserById(userId);

					organizationIds = user.getOrganizationIds();
				}

				for (int i = 0; i < organizationIds.length; i++) {
					long organizationId = organizationIds[i];

					if (OrganizationPermissionUtil.contains(
							permissionChecker, organizationId,
							ActionKeys.MANAGE_USERS)) {

						return true;
					}
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(UserPermissionImpl.class);

}