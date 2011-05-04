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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.model.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Hugo Huijser
 */
public class UpgradeGroup extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select groupId, classNameId from group_");

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long classNameId = rs.getLong("classNameId");

				boolean site = isSite(groupId, classNameId);

				runSQL(
					"update group_ set site = " + site + " where groupId = " +
						groupId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected boolean isSite(long groupId, long classNameId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select value from classname_ where classNameId = ?");

			ps.setLong(1, classNameId);

			rs = ps.executeQuery();

			if (rs.next() && 
					rs.getString("value").equals(Group.class.getName())) {

				return true;
			}
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from layout where groupId = ?");

			ps.setLong(1, groupId);

			rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}

		return false;
	}

}