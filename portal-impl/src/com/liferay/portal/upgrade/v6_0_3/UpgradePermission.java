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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradePermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class UpgradePermission extends UpgradeProcess {

	protected void addRole(
			long roleId, long companyId, long classNameId, long classPK,
			String name, int type)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into Role_ (roleId, companyId, classNameId, classPK, " +
					"name, type_) values (?, ?, ?, ?, ?, ?)");

			ps.setLong(1, roleId);
			ps.setLong(2, companyId);
			ps.setLong(3, classNameId);
			ps.setLong(4, classPK);
			ps.setString(5, name);
			ps.setInt(6, type);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addUserGroupRole(long userId, long groupId, long roleId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into UserGroupRole (userId, groupId, roleId) values " +
					"(?, ?, ?)");

			ps.setLong(1, userId);
			ps.setLong(2, groupId);
			ps.setLong(3, roleId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addUserRole(long userId, long roleId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into Users_Roles (userId, roleId) values (?, ?)");

			ps.setLong(1, userId);
			ps.setLong(2, roleId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addSingleApproverWorkflowRoles() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement("select * from Company");

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long classNameId = PortalUtil.getClassNameId(
					Role.class.getName());
				long roleId = increment();

				addRole(
					roleId, companyId, classNameId, roleId,
					_ROLE_COMMUNITY_CONTENT_REVIEWER,
					RoleConstants.TYPE_COMMUNITY);

				classNameId = PortalUtil.getClassNameId(
					Organization.class.getName());
				roleId = increment();

				addRole(
					roleId, companyId, classNameId, roleId,
					_ROLE_ORGANIZATION_CONTENT_REVIEWER,
					RoleConstants.TYPE_ORGANIZATION);

				classNameId = PortalUtil.getClassNameId(
					Company.class.getName());
				roleId = increment();

				addRole(
					roleId, companyId, classNameId, roleId,
					_ROLE_PORTAL_CONTENT_REVIEWER,
					RoleConstants.TYPE_REGULAR);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
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

	protected long getRoleId(long companyId, String name) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"SELECT * FROM Role_ WHERE companyId = ? and name = ?");

			ps.setLong(1, companyId);
			ps.setString(2, name);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("roleId");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void processPermissions_5() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
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

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long roleId = rs.getLong("roleId");
				long groupId = GetterUtil.getLong(rs.getString("primKey"));

				processRoleUsers(companyId, roleId, groupId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		deleteLegacyPermissions_5();
	}

	protected void processPermissions_6() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
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

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long roleId = rs.getLong("roleId");
				long groupId = GetterUtil.getLong(rs.getString("primKey"));

				processRoleUsers(companyId, roleId, groupId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		// there is no need to delete permissions in this case
	}

	protected void processRoleUsers(long companyId, long roleId, long groupId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		long classNameId = 0;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement("SELECT * FROM Group_ WHERE groupId = ?");

			ps.setLong(1, groupId);

			rs = ps.executeQuery();

			if (rs.next()) {
				classNameId = rs.getLong("classNameId");
			}

			String className = PortalUtil.getClassName(classNameId);

			long communityContentReviewerRoleId = getRoleId(
				companyId, _ROLE_COMMUNITY_CONTENT_REVIEWER);
			long organizationContentReviewerRoleId = getRoleId(
				companyId, _ROLE_ORGANIZATION_CONTENT_REVIEWER);
			long portalContentReviewerRoleId = getRoleId(
				companyId, _ROLE_PORTAL_CONTENT_REVIEWER);

			ps = con.prepareStatement(
				"(SELECT u.* FROM User_ u, Users_Roles ur WHERE " +
					"u.userId = ur.userId AND ur.roleId = ?) UNION ALL " +
						"(SELECT u.* FROM User_ u, UserGroupRole ugr WHERE " +
							"u.userId = ugr.userId AND ugr.roleId = ?)");

			ps.setLong(1, roleId);
			ps.setLong(1, roleId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long userId = rs.getLong("userId");

				if (className.equals(Group.class.getName())) {
					addUserGroupRole(
						userId, groupId, communityContentReviewerRoleId);
				}
				else if (className.equals(Organization.class.getName())) {
					addUserGroupRole(
						userId, groupId, organizationContentReviewerRoleId);
				}
				else if (className.equals(Company.class.getName())) {
					addUserRole(userId, portalContentReviewerRoleId);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String _ROLE_COMMUNITY_CONTENT_REVIEWER =
		"Community Content Reviewer";
	private static final String _ROLE_ORGANIZATION_CONTENT_REVIEWER =
		"Organization Content Reviewer";
	private static final String _ROLE_PORTAL_CONTENT_REVIEWER =
		"Portal Content Reviewer";

	private static Log _log = LogFactoryUtil.getLog(UpgradePermission.class);

}