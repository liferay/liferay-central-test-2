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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Shuyang Zhou
 */
public class UpgradeMBThread extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		updateCompanyIdAndRootMessageUserId();
	}

	protected void updateCompanyIdAndRootMessageUserId() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select MBThread.threadId, MBMessage.companyId, "
					+ "MBMessage.userId from MBThread inner join MBMessage on "
					+ "MBThread.rootMessageId = MBMessage.messageId ");

			rs = ps.executeQuery();

			while (rs.next()) {
				long threadId = rs.getLong("MBThread.threadId");
				long companyId = rs.getLong("MBMessage.companyId");
				long userId = rs.getLong("MBMessage.userId");

				runSQL(
					"update MBThread set companyId = " + companyId +
						", rootMessageUserId = " + userId +
							" where threadId = " + threadId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}