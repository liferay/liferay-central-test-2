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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.dao.orm.CustomSQLUtil;

/**
 * @author Raymond Augé
 */
public class InlineSQLHelperImpl implements InlineSQLHelper {

	public static final String JOIN_RESOURCE_PERMISSION =
		InlineSQLHelper.class.getName() + ".joinResourcePermission";

	public boolean isEnabled() {
		return isEnabled(0);
	}

	public boolean isEnabled(long groupId) {
		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6) {
			return false;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			return false;
		}

		if (groupId > 0) {
			if (permissionChecker.isCommunityAdmin(groupId) ||
				permissionChecker.isCommunityOwner(groupId)) {

				return false;
			}
		}
		else {
			if (permissionChecker.isCompanyAdmin()) {
				return false;
			}
		}

		return true;
	}

	public boolean isEnabled(long[] groupIds) {
		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6) {
			return false;
		}

		for (long groupId : groupIds) {
			if (!isEnabled(groupId)) {
				return false;
			}
		}

		return true;
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField) {

		return replacePermissionCheck(
			sql, className, classPKField, userIdField, new long[] {0}, null);
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long groupId) {

		return replacePermissionCheck(
			sql, className, classPKField, userIdField, new long[] {groupId},
			null);
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long groupId, String bridgeJoin) {

		return replacePermissionCheck(
			sql, className, classPKField, userIdField, new long[] {groupId},
			bridgeJoin);
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long[] groupIds) {

		return replacePermissionCheck(
			sql, className, classPKField, userIdField, groupIds, null);
	}

	public String replacePermissionCheck(
		String sql, String className, String classPKField, String userIdField,
		long[] groupIds, String bridgeJoin) {

		if (!isEnabled(groupIds)) {
			return sql;
		}

		if (Validator.isNull(className)) {
			throw new IllegalArgumentException("className is null");
		}

		if (Validator.isNull(classPKField)) {
			throw new IllegalArgumentException("classPKField is null");
		}

		if (Validator.isNull(sql)) {
			return sql;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		String permissionJoin = StringPool.BLANK;

		if (Validator.isNotNull(bridgeJoin)) {
			permissionJoin = bridgeJoin;
		}

		permissionJoin += CustomSQLUtil.get(JOIN_RESOURCE_PERMISSION);

		StringBundler ownerSQL = new StringBundler(5);

		if (Validator.isNotNull(userIdField)) {
			ownerSQL.append("(");
			ownerSQL.append(userIdField);
			ownerSQL.append(" = ");
			ownerSQL.append(String.valueOf(getUserId()));
			ownerSQL.append(") OR ");
		}

		permissionJoin = StringUtil.replace(
			permissionJoin,
			new String[] {
				"[$CLASS_NAME$]",
				"[$CLASS_PK_FIELD$]",
				"[$COMPANY_ID$]",
				"[$GROUP_IDS$]",
				"[$OWNER_CHECK$]",
				"[$ROLE_IDS$]"
			},
			new String[] {
				className,
				classPKField,
				String.valueOf(permissionChecker.getCompanyId()),
				StringUtil.merge(groupIds, "','"),
				ownerSQL.toString(),
				StringUtil.merge(getRoleIds(groupIds))
			});

		int pos = sql.indexOf(_WHERE_CLAUSE);

		if (pos != -1) {
			return sql.substring(0, pos + 1).concat(permissionJoin).concat(
				sql.substring(pos + 1));
		}

		pos = sql.indexOf(_ORDER_BY_CLAUSE);

		if (pos != -1) {
			return sql.substring(0, pos + 1).concat(permissionJoin).concat(
				sql.substring(pos + 1));
		}

		return sql.concat(StringPool.SPACE).concat(permissionJoin);
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

	protected long[] getRoleIds(long[] groupIds) {
		long[] roleIds = PermissionChecker.DEFAULT_ROLE_IDS;

		for (long groupId : groupIds) {
			for (long roleId : getRoleIds(groupId)) {
				if (!ArrayUtil.contains(roleIds, roleId)) {
					roleIds = ArrayUtil.append(roleIds, roleId);
				}
			}
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

	private static final String _ORDER_BY_CLAUSE = " ORDER BY ";

	private static final String _WHERE_CLAUSE = " WHERE ";

}