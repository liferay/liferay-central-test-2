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

package com.liferay.portal.upgrade.v4_4_0;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Location;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.util.dao.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradePermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradePermission extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			doUpgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected void deleteStaleRolePermissions(String roleName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_DELETE_STALE_PERMISSIONS_SQL);

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

	protected void doUpgrade() throws Exception {
		runSQL("delete from OrgGroupPermission");

		deleteStaleRolePermissions("Community Administrator");
		deleteStaleRolePermissions("Community Owner");
		deleteStaleRolePermissions("Organization Administrator");

		for (int i = 0; i < _PERMISSIONS.length; i++) {
			Object[] permission = _PERMISSIONS[i];

			String oldActionId = (String)permission[0];
			String newActionId = (String)permission[1];
			String resourceName = ((Class)permission[2]).getName();

			renameActionIds(oldActionId, newActionId, resourceName);
		}
	}

	protected void renameActionIds(
			String oldActionId, String newActionId, String resourceName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_RENAME_ACTION_IDS_SQL);

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

	private static Object[][] _PERMISSIONS = new Object[][] {
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

	private static final String _DELETE_STALE_PERMISSIONS_SQL =
		"select Roles_Permissions.roleId from Roles_Permissions inner join " +
			"Role_ on Role_.roleId = Roles_Permissions.roleId and " +
				"Role_.name = ?";

	private static final String _RENAME_ACTION_IDS_SQL =
		"select Permission_.permissionId from Permission_ inner join " +
			"Resource_ on Resource_.resourceId = Permission_.resourceId " +
				"inner join ResourceCode on ResourceCode.codeId = " +
					"Resource_.codeId where Permission_.actionId = ? and " +
						"ResourceCode.name = ?";

	private static Log _log = LogFactory.getLog(UpgradePermission.class);

}