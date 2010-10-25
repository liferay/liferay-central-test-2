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

package com.liferay.portal.upgrade.v6_0_5;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Julio Camarero
 * @author Brett Swain
 */
public class UpgradeLayout extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		long groupId = 0;
		long liveGroupId = 0;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select groupId, liveGroupId from Group_ " +
					"where liveGroupId != 0");

			rs = ps.executeQuery();

			while (rs.next()) {
				groupId = rs.getLong("groupId");
				liveGroupId = rs.getLong("liveGroupId");

				upgradeStagedLayoutUUID(groupId, liveGroupId);
			}
		}
		catch (Exception e) {
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

	}

	protected void upgradeStagedLayoutUUID(long groupId, long liveGroupId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
			  "select plid, privateLayout, layoutId, friendlyURL from Layout " +
				  "where groupId = ?");

			ps.setLong(1, groupId);

			rs = ps.executeQuery();

			while (rs.next()) {
				boolean privateLayout = rs.getBoolean("privateLayout");
				long layoutId = rs.getLong("layoutId");
				long plid = rs.getLong("plid");
				String friendlyURL = rs.getString("friendlyURL");

				updateUUID(
					liveGroupId, plid, layoutId, privateLayout, friendlyURL);
			}
		}
		catch (Exception e) {
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateUUID(
		long liveGroupId, long plid, long layoutId, boolean privateLayout,
		String friendlyURL)

		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select uuid_ from Layout where groupId = ? " +
					"AND friendlyURL = ?");

			ps.setLong(1, liveGroupId);
			ps.setString(2, friendlyURL);

			rs = ps.executeQuery();

			if (!rs.next()) {
				ps = con.prepareStatement(
				  "select uuid_ from Layout where groupId = ? " +
					  "AND layoutId = ? AND privateLayout = ?");

				ps.setLong(1, liveGroupId);
				ps.setLong(2, layoutId);
				ps.setBoolean(3, privateLayout);

				rs = ps.executeQuery();

				rs.next();
			}

			String uuid = rs.getString("uuid_");

			runSQL(
				"update Layout set uuid_ = '" + uuid + "' where" +
					" plid = '" + plid + "';");
		}
		catch (Exception e) {
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}