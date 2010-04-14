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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.dao.orm.CustomSQLUtil;

/**
 * <a href="InlineSQLHelperImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class InlineSQLHelperImpl implements InlineSQLHelper {

	public static final String JOIN_RESOURCE_PERMISSION =
		InlineSQLHelper.class.getName() + ".joinResourcePermission";

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField) {

		return replacePermissionCheck(
			sql, className, classPKField, userIdField, 0, null);
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long groupId) {

		return replacePermissionCheck(
			sql, className, classPKField, userIdField, groupId, null);
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long groupId, String bridgeJoin) {

		if (Validator.isNull(className)) {
			new IllegalArgumentException("className is null");
		}

		if (Validator.isNull(classPKField)) {
			new IllegalArgumentException("classPKField is null");
		}

		if (Validator.isNull(sql)) {
			return sql;
		}

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6) {
			return StringUtil.replace(
				sql, "[$PERMISSION_JOIN$]", StringPool.BLANK);
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker.isCommunityAdmin(groupId) ||
			permissionChecker.isCommunityOwner(groupId)) {

			return StringUtil.replace(
				sql, "[$PERMISSION_JOIN$]", StringPool.BLANK);
		}

		String permissionJoin = StringPool.BLANK;

		if (Validator.isNotNull(bridgeJoin)) {
			permissionJoin = bridgeJoin;
		}

		permissionJoin += CustomSQLUtil.get(JOIN_RESOURCE_PERMISSION);

		StringBundler ownerSQL = new StringBundler(5);

		if (Validator.isNotNull(userIdField)) {
			ownerSQL.append(" OR (");
			ownerSQL.append(userIdField);
			ownerSQL.append(" = ");
			ownerSQL.append(String.valueOf(getUserId()));
			ownerSQL.append(")");
		}

		permissionJoin = StringUtil.replace(
			permissionJoin,
			new String[] {
				"[$CLASS_NAME$]",
				"[$CLASS_PK_FIELD$]",
				"[$OWNER_CHECK$]",
				"[$ROLE_IDS$]"
			},
			new String[] {
				className,
				classPKField,
				ownerSQL.toString(),
				StringUtil.merge(getRoleIds(groupId))
			});

		return StringUtil.replace(sql, "[$PERMISSION_JOIN$]", permissionJoin);
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		String bridgeJoin) {

		return replacePermissionCheck(
			sql, className, classPKField, userIdField, 0, bridgeJoin);
	}

	protected long[] getRoleIds(long groupId) {
		long[] roleIds = PermissionChecker.DEFAULT_ROLE_IDS;

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker != null) {
			roleIds = permissionChecker.getRoleIds(
				permissionChecker.getUserId(), groupId);
		}

		return roleIds;
	}

	protected long getUserId() {
		long userId = 0;

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker != null) {
			userId = permissionChecker.getUserId();
		}

		return userId;
	}

}