/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v5_3_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.PortletKeys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradePermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Amos Fong
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

	protected void doUpgrade() throws Exception {
		deleteGuestViewPermission(PortletKeys.JOURNAL);
		deleteGuestViewPermission(PortletKeys.POLLS);
		deleteGuestViewPermission(PortletKeys.COMMUNITIES);
	}

	protected void deleteGuestViewPermission(String className)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			if (isSupportsUpdateWithInnerJoin()) {
				ps = con.prepareStatement(_DELETE_ROLES_PERMISSIONS_SQL);

				ps.setString(1, className);

				ps.executeUpdate();
			}
			else {
				ps = con.prepareStatement(_GET_ROLES_PERMISSIONS_SQL);

				ps.setString(1, className);

				rs = ps.executeQuery();

				while (rs.next()) {
					long roleId = rs.getLong("roleId");
					long permissionId = rs.getLong("permissionId");

					runSQL(
						"delete from Roles_Permissions where roleId = " +
							roleId + " and permissionId = " + permissionId);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	private static final String _DELETE_ROLES_PERMISSIONS_SQL =
		"delete Roles_Permissions from Roles_Permissions " +
		"inner join Permission_ on Permission_.permissionId = " +
			"Roles_Permissions.permissionId " +
		"inner join Resource_ on Resource_.resourceId = " +
			"Permission_.resourceId " +
		"inner join ResourceCode on ResourceCode.codeId = Resource_.codeId " +
		"inner join Role_ on Role_.roleId = Roles_Permissions.roleId " +
		"where Role_.name = 'Guest' and Permission_.actionId = 'VIEW' and " +
			"ResourceCode.name = ?";

	private static final String _GET_ROLES_PERMISSIONS_SQL =
		"select Roles_Permissions.permissionId, Roles_Permissions.roleId " +
			"from Roles_Permissions " +
		"inner join Permission_ on Permission_.permissionId = " +
			"Roles_Permissions.permissionId " +
		"inner join Resource_ on Resource_.resourceId = " +
			"Permission_.resourceId " +
		"inner join ResourceCode on ResourceCode.codeId = Resource_.codeId " +
		"inner join Role_ on Role_.roleId = Roles_Permissions.roleId " +
		"where Role_.name = 'Guest' and Permission_.actionId = 'VIEW' and " +
			"ResourceCode.name = ?";

	private static Log _log = LogFactoryUtil.getLog(UpgradePermission.class);

}