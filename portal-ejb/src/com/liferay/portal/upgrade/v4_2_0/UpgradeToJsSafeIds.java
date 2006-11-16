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

package com.liferay.portal.upgrade.v4_2_0;

import com.liferay.portal.PortalException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.spring.LayoutLocalServiceUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.*;
import com.liferay.util.dao.DataAccess;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeToJsSafeIds.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Jorge Ferrer
 *
 */
public class UpgradeToJsSafeIds extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeTableColumns();
			_upgradeLayoutTypeSettings();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private String _getNewTypeSettings(String oldTypeSettings)
		throws Exception {

		Properties props = new Properties();

		PropertiesUtil.load(props, oldTypeSettings);

		for (int i = 0; i <= 10; i++) {
			String columnId = "column-" + i;

			String columnPortlets = props.getProperty(columnId);

			if (columnPortlets == null) {
				continue;
			}

			String[] ids = StringUtil.split(columnPortlets);

			for (int j = 0; j < ids.length; j++) {
				ids[j] = PortalUtil.getJsSafePortletName(ids[j]);
			}

			props.setProperty(columnId, StringUtil.merge(ids));
		}

		return PropertiesUtil.toString(props);
	}

	private void _upgradeTableColumns() throws Exception {

		// Portlets

		_updateColumnValue("Portlet", "portletId");
		_updateColumnValue("PortletPreferences", "portletId");
		_updateColumnValue("JournalContentSearch", "portletId");

		// Themes

		_updateColumnValue("Layout", "themeId");
		_updateColumnValue("LayoutSet", "themeId");
	}

	private void _upgradeLayoutTypeSettings() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_SELECT_ALL_LAYOUTS);

			rs = ps.executeQuery();

			while (rs.next()) {
				String layoutId = rs.getString("layoutId");
				String layoutType = rs.getString("type_");
				String ownerId = rs.getString("ownerId");
				String oldTypeSettings = rs.getString("typeSettings");

				if (!layoutType.equals(Layout.TYPE_PORTLET)) {
					continue;
				}

				try {
					String newTypeSettings =
						_getNewTypeSettings(oldTypeSettings);

					LayoutLocalServiceUtil.updateLayout(
						layoutId, ownerId, newTypeSettings);
				}
				catch (IOException ioe) {
					_log.error(
						"Unable to read settings for layout " + layoutId + " "
							+ ownerId,
						ioe);
				}
				catch (PortalException pe) {
					_log.error(
						"Unable to update layout " + layoutId + " " + ownerId,
						pe);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private void _updateColumnValue(String table, String column)
		throws Exception {

		Connection con = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps1 = con.prepareStatement(
				"SELECT " + column + " FROM " + table + " WHERE (" + column +
					" LIKE '%-%' OR " + column + " LIKE '% %') GROUP BY " +
						column);

			ps2 = con.prepareStatement(
				"UPDATE " + table + " SET " + column + " = ? WHERE " + column +
					" = ?");

			rs = ps1.executeQuery();

			while (rs.next()) {
				String columnValue = rs.getString(column);
				String safeColumnValue =
					PortalUtil.getJsSafePortletName(columnValue);

				if (!columnValue.equals(safeColumnValue)) {
					ps2.setString(1, safeColumnValue);
					ps2.setString(2, columnValue);

					if (_log.isDebugEnabled()) {
						_log.debug("Upgrading column value " + columnValue);
					}

					int rowsUpdated = ps2.executeUpdate();

					if (rowsUpdated != 1) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Column value " + columnValue + " not updated");
						}
					}
				}
			}
		}
		finally {
			try {
				ps2.close();
			}
			catch (Exception e) {
			}

			DataAccess.cleanUp(con, ps1, rs);
		}
	}

	private static Log _log = LogFactory.getLog(UpgradeToJsSafeIds.class);

	private static final String _SELECT_ALL_LAYOUTS = "SELECT * FROM Layout";

}