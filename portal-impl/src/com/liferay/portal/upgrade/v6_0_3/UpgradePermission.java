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
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;

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

}