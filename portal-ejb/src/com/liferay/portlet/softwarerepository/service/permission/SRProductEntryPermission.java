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

package com.liferay.portlet.softwarerepository.service.permission;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.permission.PortletPermission;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.softwarerepository.model.SRProductEntry;
import com.liferay.portlet.softwarerepository.service.SRProductEntryLocalServiceUtil;

/**
 * <a href="SRProductEntryPermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
 */
public class SRProductEntryPermission {

	public static void check(
			PermissionChecker permissionChecker, String plid, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, plid, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long productEntryId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, productEntryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, SRProductEntry productEntry,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, productEntry, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, String plid, String actionId)
		throws PortalException, SystemException {

		return PortletPermission.contains(
			permissionChecker, plid, PortletKeys.SOFTWARE_REPOSITORY, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long productEntryId,
			String actionId)
		throws PortalException, SystemException {

		SRProductEntry productEntry =
			SRProductEntryLocalServiceUtil.getProductEntry(productEntryId);

		return contains(permissionChecker, productEntry, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, SRProductEntry productEntry,
			String actionId)
		throws PortalException, SystemException {

		return permissionChecker.hasPermission(
			productEntry.getGroupId(), SRProductEntry.class.getName(),
			productEntry.getPrimaryKey(), actionId);
	}

}