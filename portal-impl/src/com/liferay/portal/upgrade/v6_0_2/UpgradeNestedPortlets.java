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

package com.liferay.portal.upgrade.v6_0_2;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortletKeys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <a href="UpgradeNestedPortlets.java.html"><b><i>View Source</i></b></a>
 *
 * @author Wesley Gong
 * @author Bijan Vakili
 * @author Douglas Wong
 * @author Brian Wing Shun Chan
 */
public class UpgradeNestedPortlets extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_LAYOUT);

			rs = ps.executeQuery();

			while (rs.next()) {
				long plid = rs.getLong("plid");
				String typeSettings = rs.getString("typeSettings");

				Matcher matcher = _pattern.matcher(typeSettings);
				String newTypeSettings = typeSettings;

				while (matcher.find()) {
					String colVariable = matcher.group();

					int expectedNumOfUnderscores = StringUtil.count(
						PortletConstants.INSTANCE_SEPARATOR, "_") + 1;
					int numOfUnderscores = StringUtil.count(colVariable, "_");

					if (expectedNumOfUnderscores == numOfUnderscores) {
						String newColVariable = colVariable.replaceAll(
							_pattern.pattern(), "_$1_$2");
						newTypeSettings = newTypeSettings.replaceAll(
							colVariable, newColVariable);
					}
				}

				if (!newTypeSettings.equals(typeSettings)) {
					updateTypeSettings(plid, newTypeSettings);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateTypeSettings(long plid, String typeSettings)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update Layout set typeSettings = ? where plid = " + plid);

			ps.setString(1, typeSettings);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	private static final String _GET_LAYOUT =
		"select plid, typeSettings from Layout where typeSettings like " +
			"'%nested-column-ids=" + PortletKeys.NESTED_PORTLETS +
				PortletConstants.INSTANCE_SEPARATOR + "%'";

	private static Pattern _pattern = Pattern.compile(
		"(" + PortletKeys.NESTED_PORTLETS +
			PortletConstants.INSTANCE_SEPARATOR +
				"[^_,\\s=]+_)([^_,\\s=]+)");

}