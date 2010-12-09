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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alexander Chow
 */
public class UpgradeVirtualHost extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		updateCompany();
		updateLayoutSet();
	}

	protected void addVirtualHost(
			long companyId, long layoutSetId, String virtualHostName)
		throws Exception {

		long virtualHostId = increment();

		runSQL(
			"insert into VirtualHost (virtualHostId, companyId, layoutSetId, " +
				"virtualHostName) values (" + virtualHostId + ", " + companyId +
					", " + layoutSetId + ", " + virtualHostName + ")");
	}

	protected void updateCompany() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select companyId, virtualHost from " +
					"Company where virtualHost != ''");

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				String virtualHostName = rs.getString("virtualHost");

				addVirtualHost(companyId, 0, virtualHostName);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		runSQL("alter table Company drop column virtualHost");
	}

	protected void updateLayoutSet() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select layoutSetId, companyId, virtualHost from " +
					"LayoutSet where virtualHost != ''");

			rs = ps.executeQuery();

			while (rs.next()) {
				long layoutSetId = rs.getLong("layoutSetId");
				long companyId = rs.getLong("companyId");
				String virtualHostName = rs.getString("virtualHost");

				addVirtualHost(companyId, layoutSetId, virtualHostName);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		runSQL("alter table LayoutSet drop column virtualHost");
	}

}