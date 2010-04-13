/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.SystemException;

/**
 * <a href="InlineSQLPermissionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Auge
 */
public class InlineSQLPermissionUtil {

	public static String replacePermissionCheck(
			String sql, String className, String classPKField,
			String classUserIdField)
		throws SystemException {

		return getInlineSQLPermission().replacePermissionCheck(
			sql, className, classPKField, classUserIdField);
	}

	public static String replacePermissionCheck(
			String sql, String className, String classPKField,
			String classUserIdField, String bridgeJoin)
		throws SystemException {

		return getInlineSQLPermission().replacePermissionCheck(
			sql, className, classPKField, classUserIdField, bridgeJoin);
	}

	public static String replacePermissionCheck(
			String sql, String className, String classPKField,
			String classUserIdField, long groupId)
		throws SystemException {

		return getInlineSQLPermission().replacePermissionCheck(
			sql, className, classPKField, classUserIdField, groupId);
	}

	public static String replacePermissionCheck(
			String sql, String className, String classPKField,
			String classUserIdField, long groupId, String bridgeJoin)
		throws SystemException {

		return getInlineSQLPermission().replacePermissionCheck(
			sql, className, classPKField, classUserIdField, groupId,
			bridgeJoin);
	}

	public static InlineSQLPermission getInlineSQLPermission() {
		return _inlineSQLPermission;
	}

	public void setInlineSQLPermission(
		InlineSQLPermission inlineSQLPermission) {

		_inlineSQLPermission = inlineSQLPermission;
	}

	private static InlineSQLPermission _inlineSQLPermission;

}