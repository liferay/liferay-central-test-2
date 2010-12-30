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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.util.PortalInstances;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;

/**
 * @author Juan Fernández
 */
public class UpgradeDiscussionSubscriptions extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		if (companyIds.length == 0) {
			return;
		}

		for (int i = 0; i < companyIds.length; i++) {
			long companyId = companyIds[i];

			subscribeToComments(companyId);
		}
	}

	protected void addSubscription(
			long companyId, long userId, String userName, Date createDate,
			Date modifiedDate, long classNameId, long classPK)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		long subscriptionId = increment();
		String frequecy = "instant";

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into Subscription(subscriptionId, companyId, userId, " +
					"userName, createDate, modifiedDate, classNameId, " +
					"classPK, frequency) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

			ps.setLong(1, subscriptionId);
			ps.setLong(2, companyId);
			ps.setLong(3, userId);
			ps.setString(4, userName);
			ps.setDate(5, createDate);
			ps.setDate(6, modifiedDate);
			ps.setLong(7, classNameId);
			ps.setLong(8, classPK);
			ps.setString(9, frequecy);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void subscribeToComments(long companyId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select userId, userName, createDate, modifiedDate, " +
				"classNameId, classPK from MBMessage where companyId = '" +
				companyId +"' and classNameId != 0 and parentMessageId != 0  " +
				"group by companyId, userId, classNameId, classPK");

			rs = ps.executeQuery();

			while (rs.next()) {
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Date createDate = rs.getDate("createDate");
				Date modifiedDate = rs.getDate("modifiedDate");
				long classNameId = rs.getLong("classNameId");
				long classPK = rs.getLong("classPK");

				addSubscription(
					companyId, userId, userName, createDate, modifiedDate,
					classNameId, classPK);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}