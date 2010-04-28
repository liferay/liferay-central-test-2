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

package com.liferay.portal.upgrade.v6_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortletKeys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradeNested.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bijan Vakili
 * @author Douglas Wong
 * @author Wesley Gong
 */
public class UpgradeNested extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		updateNestedPortletColumnId();
	}

	protected void updateNestedPortletColumnId() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(UpgradeNested._GET_LAYOUT);

			rs = ps.executeQuery();

			while (rs.next()) {
				String typeSettings = rs.getString("typeSettings");
				long plid = rs.getLong("plid");

				String newTypeSettings = typeSettings.replaceAll(
					_REPLACE_PATTERN, "_$1_$2");

				if (!newTypeSettings.equals(typeSettings)) {
					runSQL(
						"update Layout set typeSettings = '" + newTypeSettings +
							"' where plid = " + plid);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String _GET_LAYOUT =
		"select plid, typeSettings from Layout where typeSettings like " +
			"'%nested-column-ids="+ PortletKeys.NESTED_PORTLETS +
				PortletConstants.INSTANCE_SEPARATOR + "%'";

	private static final String _REPLACE_PATTERN = "(" +
		PortletKeys.NESTED_PORTLETS + PortletConstants.INSTANCE_SEPARATOR +
			".+?_)([^,]*)";

}