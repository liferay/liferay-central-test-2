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
import com.liferay.portlet.softwarerepository.model.SRProductEntry;
import com.liferay.portlet.softwarerepository.model.SRProductVersion;
import com.liferay.portlet.softwarerepository.service.SRProductVersionLocalServiceUtil;

/**
 * <a href="SRProductVersionPermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
 */
public class SRProductVersionPermission {

	public static void check(
			PermissionChecker permissionChecker, long productVersionId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, productVersionId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker,
			SRProductVersion productVersion, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, productVersion, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long productVersionId,
			String actionId)
		throws PortalException, SystemException {

		SRProductVersion productVersion =
			SRProductVersionLocalServiceUtil.getProductVersion(
				productVersionId);

		return contains(permissionChecker, productVersion, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			SRProductVersion productVersion, String actionId)
		throws PortalException, SystemException {

		SRProductEntry productEntry = productVersion.getProductEntry();

		return permissionChecker.hasPermission(
			productEntry.getGroupId(), SRProductVersion.class.getName(),
			productVersion.getPrimaryKey(), actionId);
	}

}