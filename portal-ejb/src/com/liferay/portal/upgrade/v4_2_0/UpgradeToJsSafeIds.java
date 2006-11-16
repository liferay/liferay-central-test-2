/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.service.spring.LayoutLocalServiceUtil;
import com.liferay.portal.SystemException;
import com.liferay.portal.PortalException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.util.dao.DataAccess;
import com.liferay.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.io.IOException;

/**
 * <a href="UpgradeToJsSafeIds.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Jorge Ferrer
 *
 */
public class UpgradeToJsSafeIds
	extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading portlet, theme and layout ids to safe values");

		try {
			_upgradeTableColumns();
			_upgradeLayoutTypeSettings();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradeTableColumns() throws SQLException, NamingException {
		// Portlets
		__updateColumnValue("Portlet", "portletId");
		__updateColumnValue("PortletPreferences", "portletId");
		__updateColumnValue("JournalContentSearch", "portletId");

		// Themes
		__updateColumnValue("Layout", "themeId");
		__updateColumnValue("LayoutSet", "themeId");
	}

	private void _upgradeLayoutTypeSettings()
		throws SystemException, SQLException, NamingException {
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
						_getUpdatedSettings(oldTypeSettings);
					LayoutLocalServiceUtil.updateLayout(
						layoutId, ownerId, newTypeSettings);
				}
				catch (PortalException e) {
					_log.warn("Error when updating layout " + layoutId +
						":" + ownerId, e);
				}
				catch (IOException e) {
					_log.warn("Unable to read settings for layout " +
						layoutId + ":" + ownerId, e);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}


	private String _getUpdatedSettings(String oldTypeSettings)
		throws IOException {
		Properties props = new Properties();

		PropertiesUtil.load(props, oldTypeSettings);

		// Update layout template id
		
		String layoutTemplateId = props.
			getProperty(LayoutTypePortlet.LAYOUT_TEMPLATE_ID);
		String newLayoutTemplateId = PortalUtil.
			getJsSafePortletName(layoutTemplateId);
		props.setProperty(
			LayoutTypePortlet.LAYOUT_TEMPLATE_ID, newLayoutTemplateId);

		// Iterate columns guessing their names

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

	private void __updateColumnValue(String table, String column)
		throws SQLException, NamingException {
		Connection con = null;
		PreparedStatement selectPs = null;
		PreparedStatement updatePs = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			selectPs = con.prepareStatement(
				"select " + column + " from " + table + " where (" + column +
				" like '%-%' " + "or " + column + " like '% %') " +
				"group by " + column);
			updatePs = con.prepareStatement(
				"update " + table + " set " + column + "=? where " +
				column + "=?");

			rs = selectPs.executeQuery();

			while (rs.next()) {
				String columnValue = rs.getString(column);
				String safeColumnValue = PortalUtil.
					getJsSafePortletName(columnValue);
				if (!columnValue.equals(safeColumnValue)) {
					updatePs.setString(1, safeColumnValue);
					updatePs.setString(2, columnValue);
					_log.debug("Upgrading column value " + columnValue);
					int rowsUpdated = updatePs.executeUpdate();
					if (rowsUpdated != 1) {
						_log.warn("Column value " + columnValue + " not updated");
					}
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, selectPs, rs);
		}

	}


	private static Log _log = LogFactory.getLog(UpgradeToJsSafeIds.class);

	private static final String _SELECT_ALL_LAYOUTS = "SELECT * FROM Layout";

}
