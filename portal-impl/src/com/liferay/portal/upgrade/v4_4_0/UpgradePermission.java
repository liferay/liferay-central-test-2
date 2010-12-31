/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v4_4_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Location;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.UserGroup;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.shopping.model.ShoppingCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated
 */
public class UpgradePermission extends UpgradeProcess {

	protected void deletePermissionByActionIdAndResourceName(
			String actionId, String resourceName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_PERMISSION_IDS_1);

			ps.setString(1, actionId);
			ps.setString(2, resourceName);

			rs = ps.executeQuery();

			while (rs.next()) {
				long permissionId = rs.getLong("permissionId");

				deletePermissionByPermissionId(permissionId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void deletePermissionByPermissionId(long permissionId)
		throws Exception {

		runSQL(
			"delete from Permission_ where permissionId = " + permissionId);
		runSQL(
			"delete from Groups_Permissions where permissionId = " +
				permissionId);
		runSQL(
			"delete from Roles_Permissions where permissionId = " +
				permissionId);
		runSQL(
			"delete from Users_Permissions where permissionId = " +
				permissionId);
	}

	protected void deletePermissionByResourceId(long resourceId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select permissionId from Permission_ where resourceId = ?");

			ps.setLong(1, resourceId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long permissionId = rs.getLong("permissionId");

				deletePermissionByPermissionId(permissionId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void deleteResource(long codeId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select resourceId from Resource_ where codeId = ?");

			ps.setLong(1, codeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long resourceId = rs.getLong("resourceId");

				deletePermissionByResourceId(resourceId);

				runSQL(
					"delete from Resource_ where resourceId = " + resourceId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void deleteResourceCode(String resourceName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select codeId from ResourceCode where name = ?");

			ps.setString(1, resourceName);

			rs = ps.executeQuery();

			while (rs.next()) {
				long codeId = rs.getLong("codeId");

				deleteResource(codeId);

				runSQL(
					"delete from ResourceCode where name = '" + resourceName +
						"'");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void deleteRolesPermissions(String roleName) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_ROLE_IDS);

			ps.setString(1, roleName);

			rs = ps.executeQuery();

			while (rs.next()) {
				long roleId = rs.getLong("roleId");

				runSQL(
					"delete from Roles_Permissions where roleId = " + roleId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void deleteUsersPermissions(int scope) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_PERMISSION_IDS_2);

			ps.setLong(1, scope);

			rs = ps.executeQuery();

			while (rs.next()) {
				long permissionId = rs.getLong("permissionId");

				runSQL(
					"delete from Users_Permissions where permissionId = " +
						permissionId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void doUpgrade() throws Exception {
		runSQL("delete from OrgGroupPermission");

		for (int i = 0; i < _DELETE_PERMISSIONS.length; i++) {
			Object[] permission = _DELETE_PERMISSIONS[i];

			String actionId = (String)permission[0];
			String resourceName = ((Class<?>)permission[1]).getName();

			deletePermissionByActionIdAndResourceName(actionId, resourceName);
		}

		for (int i = 0; i < _UPDATE_PERMISSIONS.length; i++) {
			Object[] permission = _UPDATE_PERMISSIONS[i];

			String oldActionId = (String)permission[0];
			String newActionId = (String)permission[1];
			String resourceName = ((Class<?>)permission[2]).getName();

			updatePermission(oldActionId, newActionId, resourceName);
		}

		deleteResourceCode("com.liferay.portlet.blogs.model.BlogsCategory");

		deleteRolesPermissions("Community Administrator");
		deleteRolesPermissions("Community Owner");
		deleteRolesPermissions("Organization Administrator");

		deleteUsersPermissions(ResourceConstants.SCOPE_GROUP);
	}

	protected void updatePermission(
			String oldActionId, String newActionId, String resourceName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_PERMISSION_IDS_1);

			ps.setString(1, oldActionId);
			ps.setString(2, resourceName);

			rs = ps.executeQuery();

			while (rs.next()) {
				long permissionId = rs.getLong("permissionId");

				runSQL(
					"update Permission_ set actionId = '" + newActionId +
						"' where permissionId = " + permissionId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static Object[][] _DELETE_PERMISSIONS = new Object[][] {
		new Object[] {
			"ADMINISTRATE", Group.class
		},
		new Object[] {
			"ADD_USER", Location.class
		},
		new Object[] {
			"ADD_USER", Organization.class
		},
		new Object[] {
			"DELETE_USER", Location.class
		},
		new Object[] {
			"DELETE_USER", Organization.class
		},
		new Object[] {
			"PERMISSIONS_USER", Location.class
		},
		new Object[] {
			"PERMISSIONS_USER", Organization.class
		},
		new Object[] {
			"UPDATE_USER", Location.class
		},
		new Object[] {
			"UPDATE_USER", Organization.class
		},
		new Object[] {
			"VIEW_USER", Location.class
		},
		new Object[] {
			"VIEW_USER", Organization.class
		}
	};

	private static final String _GET_PERMISSION_IDS_1 =
		"select Permission_.permissionId from Permission_ inner join " +
			"Resource_ on Resource_.resourceId = Permission_.resourceId " +
				"inner join ResourceCode on ResourceCode.codeId = " +
					"Resource_.codeId where Permission_.actionId = ? and " +
						"ResourceCode.name = ?";

	private static final String _GET_PERMISSION_IDS_2 =
		"select Users_Permissions.permissionId from Users_Permissions inner " +
			"join Permission_ on Permission_.permissionId = " +
				"Users_Permissions.permissionId inner join Resource_ on " +
					"Resource_.resourceId = Permission_.resourceId inner " +
						"join ResourceCode on ResourceCode.codeId = " +
							"Resource_.codeId where ResourceCode.scope = ?";

	private static final String _GET_ROLE_IDS =
		"select Roles_Permissions.roleId from Roles_Permissions inner join " +
			"Role_ on Role_.roleId = Roles_Permissions.roleId where " +
				"Role_.name = ?";

	private static Object[][] _UPDATE_PERMISSIONS = new Object[][] {
		new Object[] {
			"ADD_CATEGORY", "ADD_SUBCATEGORY", MBCategory.class
		},
		new Object[] {
			"ADD_CATEGORY", "ADD_SUBCATEGORY", ShoppingCategory.class
		},
		new Object[] {
			"ADD_FOLDER", "ADD_SUBFOLDER", DLFolder.class
		},
		new Object[] {
			"ADD_FOLDER", "ADD_SUBFOLDER", IGFolder.class
		},
		new Object[] {
			"ADD_FOLDER", "ADD_SUBFOLDER", BookmarksFolder.class
		},
		new Object[] {
			"ADD_LOCATION", "MANAGE_SUBORGANIZATIONS", Organization.class
		},
		new Object[] {
			"ADD_PERMISSIONS", "DEFINE_PERMISSIONS", Role.class
		},
		new Object[] {
			"ADD_USER", "MANAGE_USERS", Location.class
		},
		new Object[] {
			"ADD_USER", "MANAGE_USERS", Organization.class
		},
		new Object[] {
			"ASSIGN_USERS", "ASSIGN_MEMBERS", Group.class
		},
		new Object[] {
			"ASSIGN_USERS", "ASSIGN_MEMBERS", Role.class
		},
		new Object[] {
			"ASSIGN_USERS", "ASSIGN_MEMBERS", UserGroup.class
		}
	};

}