/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v4_4_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradeLayout.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class UpgradeLayout extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select plid, typeSettings from Layout where type_ = " +
					"'link_to_layout'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long plid = rs.getLong("plid");
				String typeSettings = rs.getString("typeSettings");

				String newTypeSettings = upgradeTypeSettings(typeSettings);

				ps = con.prepareStatement(
					"update Layout set typeSettings = ? where plid = " +
						plid);

				ps.setString(1, newTypeSettings);

				ps.executeUpdate();

				ps.close();
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected Object[] getLayout(long plid) throws Exception {
		Object[] layout = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_LAYOUT);

			ps.setLong(1, plid);

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				boolean privateLayout = rs.getBoolean("privateLayout");
				long layoutId = rs.getLong("layoutId");

				layout = new Object[] {groupId, privateLayout, layoutId};
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return layout;
	}

	protected String upgradeTypeSettings(String typeSettings) throws Exception {
		UnicodeProperties props = new UnicodeProperties(true);

		props.load(typeSettings);

		long linkToPlid = GetterUtil.getLong(props.getProperty("linkToPlid"));

		if (linkToPlid > 0) {
			Object[] layout = getLayout(linkToPlid);

			if (layout != null) {
				long groupId = (Long)layout[0];
				boolean privateLayout = (Boolean)layout[1];
				long layoutId = (Long)layout[2];

				props.remove("linkToPlid");
				props.put("groupId", String.valueOf(groupId));
				props.put("privateLayout", String.valueOf(privateLayout));
				props.put("linkToLayoutId", String.valueOf(layoutId));
			}
		}

		return props.toString();
	}

	private static final String _GET_LAYOUT =
		"select groupId, privateLayout, layoutId from Layout where plid = ?";

}