/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v4_1_0;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.Constants;
import com.liferay.util.dao.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeGroup.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Charles May
 *
 */
public class UpgradeGroup extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeGroup();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradeGroup() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_UPGRADE_GROUP);

			rs = ps.executeQuery();

			while (rs.next()) {
				String roleId = rs.getString("roleId");
				String companyId = rs.getString("companyId");
				String groupId = rs.getString("classPK");

				List users = UserLocalServiceUtil.getRoleUsers(roleId);

				for (int i = 0; i < users.size(); i++) {
					User user = (User)users.get(i);

					Resource resource = ResourceLocalServiceUtil.addResource(
						companyId, Group.class.getName(),
						ResourceImpl.TYPE_CLASS, ResourceImpl.SCOPE_INDIVIDUAL,
						groupId);

					PermissionLocalServiceUtil.addUserPermissions(
						user.getUserId(),
						new String[] {ActionKeys.ADMINISTRATE},
						resource.getResourceId());
				}

				RoleLocalServiceUtil.deleteRole(roleId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String _UPGRADE_GROUP =
		"SELECT * FROM Role_ where name LIKE 'GROUP_%_ADMINISTRATOR'";

	private static Log _log = LogFactory.getLog(UpgradeGroup.class);

}