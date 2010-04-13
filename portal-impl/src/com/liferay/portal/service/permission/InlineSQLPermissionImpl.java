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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.dao.orm.CustomSQLUtil;

import com.sun.star.lang.IllegalArgumentException;

/**
 * <a href="InlineSQLPermissionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Auge
 */
public class InlineSQLPermissionImpl implements InlineSQLPermission {

	public String replacePermissionCheck(
			String sql, String className, String classPKField,
			String classUserIdField)
		throws SystemException {

		return replacePermissionCheck(
			sql, className, classPKField, classUserIdField, 0, null);
	}

	public String replacePermissionCheck(
			String sql, String className, String classPKField,
			String classUserIdField, String bridgeJoin)
		throws SystemException {

		return replacePermissionCheck(
			sql, className, classPKField, classUserIdField, 0, bridgeJoin);
	}

	public String replacePermissionCheck(
			String sql, String className, String classPKField,
			String classUserIdField, long groupId)
		throws SystemException {

		return replacePermissionCheck(
			sql, className, classPKField, classUserIdField, groupId, null);
	}

	public String replacePermissionCheck(
			String sql, String className, String classPKField,
			String classUserIdField, long groupId, String bridgeJoin)
		throws SystemException {

		if (Validator.isNull(sql)) {
			return sql;
		}

		String inlineViewCheck = StringPool.BLANK;

		if (Validator.isNull(className)) {
			throw new SystemException(
				new IllegalArgumentException("className cannot be null"));
		}

		if (Validator.isNull(classPKField)) {
			throw new SystemException(
				new IllegalArgumentException("classPKField cannot be null"));
		}

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (permissionChecker.isCommunityOwner(groupId) ||
				permissionChecker.isCommunityAdmin(groupId)) {

				return StringUtil.replace(
					sql, "[$PERMISSION_JOIN$]", inlineViewCheck);
			}

			long[] roleIds = getRoleIds(groupId);
			long userId = getUserId();
			StringBundler ownershipCheck = new StringBundler(5);

			if (Validator.isNotNull(bridgeJoin)) {
				inlineViewCheck = bridgeJoin;
			}

			if (Validator.isNotNull(classUserIdField)) {
				ownershipCheck.append(" OR (");
				ownershipCheck.append(classUserIdField);
				ownershipCheck.append(" = ");
				ownershipCheck.append(String.valueOf(userId));
				ownershipCheck.append(")");
			}

			inlineViewCheck += CustomSQLUtil.get(_INLINE_CHECK);

			inlineViewCheck = StringUtil.replace(
				inlineViewCheck,
				new String[] {
					"[$CLASS_NAME$]",
					"[$CLASS_PK_FIELD$]",
					"[$OWNERSHIP_CHECK$]",
					"[$ROLE_IDS$]"
				},
				new String[] {
					className,
					classPKField,
					ownershipCheck.toString(),
					StringUtil.merge(roleIds)
				});
		}

		return StringUtil.replace(sql, "[$PERMISSION_JOIN$]", inlineViewCheck);
	}

	public long[] getRoleIds(long groupId) {
		long[] roleIds = PermissionChecker.NO_ROLES;

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker != null) {
			roleIds = permissionChecker.getRoleIds(
				permissionChecker.getUserId(), groupId);
		}

		return roleIds;
	}

	public long getUserId() {
		long userId = 0;

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker != null) {
			userId = permissionChecker.getUserId();
		}

		return userId;
	}

	private static final String _INLINE_CHECK =
		"permissions.user.check.algorithm.6.inline.check";

}