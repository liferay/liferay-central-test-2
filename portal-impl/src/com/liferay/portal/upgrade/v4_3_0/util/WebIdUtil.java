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

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class WebIdUtil {

	public static String[] getWebIds() throws Exception {
		if (_webIds != null) {
			return _webIds;
		}

		List<String> webIds = new ArrayList<String>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_WEB_IDS);

			rs = ps.executeQuery();

			while (rs.next()) {
				String companyId = rs.getString("companyId");

				webIds.add(companyId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		_webIds = webIds.toArray(new String[webIds.size()]);

		return _webIds;
	}

	private static final String _GET_WEB_IDS = "select companyId from Company";

	private static String[] _webIds;

}