/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v3_5_0;

import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.service.persistence.LayoutPK;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.ClusterPool;
import com.liferay.portal.util.Constants;
import com.liferay.util.GetterUtil;
import com.liferay.util.PropertiesUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.dao.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeLayout.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UpgradeLayout extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeLayout();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private String _getCompanyIdByGroupId(String groupId) throws Exception {
		String companyId = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_GET_COMPANY_ID_BY_GROUP_ID);

			ps.setString(1, groupId);

			rs = ps.executeQuery();

			while (rs.next()) {
				companyId = rs.getString("companyId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return companyId;
	}

	private String _getCompanyIdByUserId(String userId) throws Exception {
		String companyId = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_GET_COMPANY_ID_BY_USER_ID);

			ps.setString(1, userId);

			rs = ps.executeQuery();

			while (rs.next()) {
				companyId = rs.getString("companyId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return companyId;
	}

	private void _upgradeLayout() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_UPGRADE_LAYOUT_1);

			rs = ps.executeQuery();

			while (rs.next()) {
				String layoutId = rs.getString("layoutId");
				String userId = rs.getString("userId");
				String parentLayoutId = rs.getString("parentLayoutId");
				String columnOrder = rs.getString("columnOrder");
				String narrow1 = rs.getString("narrow1");
				String narrow2 = rs.getString("narrow2");
				String wide = rs.getString("wide");
				String stateMax = rs.getString("stateMax");
				String stateMin = rs.getString("stateMin");
				String modeEdit = rs.getString("modeEdit");
				String modeHelp = rs.getString("modeHelp");
				String type = Layout.TYPE_PORTLET;
				int priority = 0;

				_log.debug(
					"Upgrading layout " + new LayoutPK(layoutId, userId));

				String companyId = null;

				int pos = layoutId.indexOf(".");

				if (pos != -1) {
					String groupId = layoutId.substring(0, pos);

					companyId = _getCompanyIdByGroupId(groupId);
				}
				else {
					companyId = _getCompanyIdByUserId(userId);
				}

				parentLayoutId = GetterUtil.getString(
					parentLayoutId, Layout.DEFAULT_PARENT_LAYOUT_ID);

				Properties props = new Properties();

				props.setProperty(
					"column-order",
					StringUtil.replace(
						GetterUtil.getString(columnOrder), "w", "w1"));
				props.setProperty("narrow-1", GetterUtil.getString(narrow1));
				props.setProperty("narrow-2", GetterUtil.getString(narrow2));
				props.setProperty("wide-1", GetterUtil.getString(wide));
				props.setProperty(
					LayoutTypePortlet.STATE_MAX,
					GetterUtil.getString(stateMax));
				props.setProperty(
					LayoutTypePortlet.STATE_MIN,
					GetterUtil.getString(stateMin));
				props.setProperty(
					LayoutTypePortlet.MODE_EDIT,
					GetterUtil.getString(modeEdit));
				props.setProperty(
					LayoutTypePortlet.MODE_HELP,
					GetterUtil.getString(modeHelp));

				String typeSettings = PropertiesUtil.toString(props);

				ps = con.prepareStatement(_UPGRADE_LAYOUT_2);

				ps.setString(1, companyId);
				ps.setString(2, parentLayoutId);
				ps.setString(3, type);
				ps.setString(4, typeSettings);
				ps.setInt(5, priority);
				ps.setString(6, layoutId);
				ps.setString(7, userId);

				ps.executeUpdate();
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		ClusterPool.clear();
	}

	private static final String _GET_COMPANY_ID_BY_GROUP_ID =
		"SELECT companyId FROM Group_ WHERE groupId = ?";

	private static final String _GET_COMPANY_ID_BY_USER_ID =
		"SELECT companyId FROM User_ WHERE userId = ?";

	private static final String _UPGRADE_LAYOUT_1 = "SELECT * FROM Layout";

	private static final String _UPGRADE_LAYOUT_2 =
		"UPDATE Layout SET companyId = ?, parentLayoutId = ?, type_ = ?, " +
			"typeSettings = ?, priority = ? WHERE layoutId = ? AND userId = ?";

	private static Log _log = LogFactory.getLog(UpgradeLayout.class);

}