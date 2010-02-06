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

package com.liferay.portlet.expando.service.permission;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;

/**
 * <a href="ExpandoColumnPermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class ExpandoColumnPermission {

	public static void check(
			PermissionChecker permissionChecker, String className,
			String tableName, String columnName, String actionId)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, className, tableName, columnName,
				actionId)) {

			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long columnId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, columnId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, ExpandoColumn column,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, column, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, String className,
			String tableName, String columnName, String actionId)
		throws SystemException {

		ExpandoColumn column = ExpandoColumnLocalServiceUtil.getColumn(
			className, tableName, columnName);

		return contains(permissionChecker, column, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long columnId, String actionId)
		throws PortalException, SystemException {

		ExpandoColumn column = ExpandoColumnLocalServiceUtil.getColumn(
			columnId);

		return contains(permissionChecker, column, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, ExpandoColumn column,
		String actionId) {

		return permissionChecker.hasPermission(
			0, ExpandoColumn.class.getName(), column.getColumnId(), actionId);
	}

}