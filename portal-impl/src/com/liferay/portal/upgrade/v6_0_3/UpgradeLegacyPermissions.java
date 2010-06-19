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

package com.liferay.portal.upgrade.v6_0_3;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * <a href="UpgradeLegacyPermissions.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class UpgradeLegacyPermissions extends UpgradeProcess {

	protected void addRole(
			long companyId, long classNameId, String name, int type)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			long roleId = CounterLocalServiceUtil.increment();

			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into Role_ " +
					"(roleId, companyId, classNameId, classPK, name, type_) " +
						"values (?, ?, ?, ?, ?, ?)");

			ps.setLong(1, roleId);
			ps.setLong(2, companyId);
			ps.setLong(3, classNameId);
			ps.setLong(4, roleId);
			ps.setString(5, name);
			ps.setInt(6, type);

			ps.executeUpdate();
		}
		catch (SQLException sqle) {
			_log.warn(sqle.getMessage());
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addUserGroupRole(
			Map<String, Object> userMap, Map<String, Object> groupMap,
			Map<String, Object> roleMap)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into UserGroupRole (userId, groupId, roleId) values " +
					"(?, ?, ?)");

			ps.setLong(1, (Long)userMap.get("userId"));
			ps.setLong(2, (Long)groupMap.get("groupId"));
			ps.setLong(3, (Long)roleMap.get("roleId"));

			ps.executeUpdate();

			_log.error(
				"" + userMap.get("userId") + " " + groupMap.get("groupId") +
					" " + roleMap.get("roleId") + " " +
						roleMap.get("name"));
		}
		catch (SQLException sqle) {
			_log.warn(sqle.getMessage());
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addUserRole(
			Map<String, Object> userMap, Map<String, Object> roleMap)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into Users_Roles (userId, roleId) values (?, ?)");

			ps.setLong(1, (Long)userMap.get("userId"));
			ps.setLong(2, (Long)roleMap.get("roleId"));

			ps.executeUpdate();

			_log.error(
				"" + userMap.get("userId") + " " + roleMap.get("roleId") + " " +
					roleMap.get("name"));
		}
		catch (SQLException sqle) {
			_log.warn(sqle.getMessage());
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addSingleApproverWorkflowRoles() throws Exception {
		long classNameId = PortalUtil.getClassNameId(Role.class.getName());

		List<Map<String, Object>> companyMaps = getMaps(
			"select * from Company");

		for (Map<String, Object> companyMap : companyMaps) {
			long companyId = (Long)companyMap.get("companyId");

			addRole(
				companyId, classNameId, _ROLE_COMMUNITY_CONTENT_REVIEWER,
				RoleConstants.TYPE_COMMUNITY);

			addRole(
				companyId, classNameId, _ROLE_ORGANIZATION_CONTENT_REVIEWER,
				RoleConstants.TYPE_ORGANIZATION);

			addRole(
				companyId, classNameId, _ROLE_PORTAL_CONTENT_REVIEWER,
				RoleConstants.TYPE_REGULAR);
		}
	}

	protected void deleteLegacyPermissions_5() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"delete from Roles_Permissions where permissionId in (" +
					"select permissionId from Permission_ where " +
						"actionId = 'APPROVE_ARTICLE')");

			ps.executeUpdate();

			ps = con.prepareStatement(
				"delete from Permission_ where actionId = 'APPROVE_ARTICLE'");

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void doUpgrade() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Upgrading legacy journal permissions");
		}

		addSingleApproverWorkflowRoles();

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			processPermissions_5();
		}
		else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			processPermissions_6();
		}
	}

	protected Map<String, Object> getMap(String query, Object... values)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Map<String, Object> objectMap = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(query);

			for (int i = 0; i < values.length; i++) {
				ps.setObject(i + 1, values[i]);
			}

			rs = ps.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();

			if (rs.next()) {
				objectMap = resultSetToObjectMap(metaData, rs);
			}

			return objectMap;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected List<Map<String, Object>> getMaps(String query, Object... values)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Map<String, Object>> list =
			new ArrayList<Map<String, Object>>();

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(query);

			for (int i = 0; i < values.length; i++) {
				ps.setObject(i + 1, values[i]);
			}

			rs = ps.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();

			while (rs.next()) {
				list.add(resultSetToObjectMap(metaData, rs));
			}

			return list;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void processPermissions_5() throws Exception {
		List<Map<String, Object>> maps = getMaps(
			"SELECT " +
				"rc.companyId, rp.roleId, r.primKey " +
			"FROM " +
				"Resource_ r, " +
				"ResourceCode rc, " +
				"Permission_ p, " +
				"Roles_Permissions rp " +
			"WHERE " +
				"r.codeId = rc.codeId AND " +
				"rc.name = 'com.liferay.portlet.journal' AND " +
				"rc.scope = 4 AND " +
				"r.resourceId = p.resourceId AND " +
				"p.actionId = 'APPROVE_ARTICLE' AND " +
				"p.permissionId = rp.permissionId;");

		for (Map<String, Object> projectionMap : maps) {
			long companyId = (Long)projectionMap.get("companyId");
			long roleId = (Long)projectionMap.get("roleId");
			long groupId = GetterUtil.getLong(
				(String)projectionMap.get("primKey"));

			processRoleUsers(companyId, roleId, groupId);
		}

		//deleteLegacyPermissions_5();
	}

	protected void processPermissions_6() throws Exception {
		List<Map<String, Object>> projectionMaps = getMaps(
			"SELECT " +
				"rp.companyId, rp.roleId, rp.primKey " +
			"FROM " +
				"ResourcePermission rp, ResourceAction ra " +
			"WHERE " +
				"ra.name = 'com.liferay.portlet.journal' AND " +
				"ra.name = rp.name AND " +
				"ra.actionId = 'APPROVE_ARTICLE' AND " +
				"rp.scope = 4 AND " +
				"rp.actionIds >= ra.bitwiseValue AND " +
				"rp.actionIds/ra.bitwiseValue % 2 = 1");

		for (Map<String, Object> projectionMap : projectionMaps) {
			long companyId = (Long)projectionMap.get("companyId");
			long roleId = (Long)projectionMap.get("roleId");
			long groupId = GetterUtil.getLong(
				(String)projectionMap.get("primKey"));

			processRoleUsers(companyId, roleId, groupId);
		}

		// there is no need to delete permissions in this case
	}

	protected void processRoleUsers(long companyId, long roleId, long groupId)
		throws Exception {

		Map<String, Object> roleMap = getMap(
			"SELECT * FROM Role_ WHERE roleId = ?", roleId);
		Map<String, Object> groupMap = getMap(
			"SELECT * FROM Group_ WHERE groupId = ?", groupId);

		if (roleMap == null || groupMap == null) {
			return;
		}

		List<Map<String, Object>> userMaps = getMaps(
			"(SELECT " +
				"u.* " +
			"FROM " +
				"User_ u, Users_Roles ur " +
			"WHERE " +
				"u.userId = ur.userId AND " +
				"ur.roleId = ?) UNION ALL " +
			"(SELECT " +
				"u.* " +
			"FROM " +
				"User_ u, UserGroupRole ugr " +
			"WHERE " +
				"u.userId = ugr.userId AND " +
				"ugr.roleId = ?)", roleId, roleId);

		String className = PortalUtil.getClassName(
			(Long)groupMap.get("classNameId"));

		for (Map<String, Object> userMap : userMaps) {
			Map<String, Object> targetRoleMap = null;

			if (className.equals(Group.class.getName())) {
				targetRoleMap = getMap(
					"SELECT * FROM Role_ WHERE companyId = ? and name = ?",
					companyId, _ROLE_COMMUNITY_CONTENT_REVIEWER);

				addUserGroupRole(userMap, groupMap, targetRoleMap);
			}
			else if (className.equals(Organization.class.getName())) {
				targetRoleMap = getMap(
					"SELECT * FROM Role_ WHERE companyId = ? and name = ?",
					companyId, _ROLE_ORGANIZATION_CONTENT_REVIEWER);

				addUserGroupRole(userMap, groupMap, targetRoleMap);
			}
			else if (className.equals(Company.class.getName())) {
				targetRoleMap = getMap(
					"SELECT * FROM Role_ WHERE companyId = ? and name = ?",
					companyId, _ROLE_PORTAL_CONTENT_REVIEWER);

				addUserRole(userMap, targetRoleMap);
			}
		}
	}

	protected Map<String, Object> resultSetToObjectMap(
			ResultSetMetaData metaData, ResultSet rs)
		throws Exception {

		Map<String, Object> objectMap = new HashMap<String, Object>();

		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			objectMap.put(metaData.getColumnName(i), rs.getObject(i));
		}

		return objectMap;
	}

	private static final String _ROLE_COMMUNITY_CONTENT_REVIEWER =
		"Community Content Reviewer";
	private static final String _ROLE_ORGANIZATION_CONTENT_REVIEWER =
		"Organization Content Reviewer";
	private static final String _ROLE_PORTAL_CONTENT_REVIEWER =
		"Portal Content Reviewer";

	private static Log _log = LogFactoryUtil.getLog(
		UpgradeLegacyPermissions.class);

}