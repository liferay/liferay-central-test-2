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

package com.liferay.portal.upgrade.v4_0_0;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.LayoutTypePortletImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.persistence.LayoutPK;
import com.liferay.portal.service.persistence.PortletPreferencesPK;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.util.GetterUtil;
import com.liferay.util.PropertiesUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.dao.DataAccess;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

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

	private String[] _getLookAndFeelByGroupId(long groupId) throws Exception {
		String[] lookAndFeel = new String[2];

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_GET_LOOK_AND_FEEL_BY_GROUP_ID);

			ps.setLong(1, groupId);

			rs = ps.executeQuery();

			while (rs.next()) {
				lookAndFeel[0] = rs.getString("themeId");
				lookAndFeel[1] = rs.getString("colorSchemeId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return lookAndFeel;
	}

	private String[] _getLookAndFeelByUserId(String userId) throws Exception {
		String[] lookAndFeel = new String[2];

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_GET_LOOK_AND_FEEL_BY_USER_ID);

			ps.setString(1, userId);

			rs = ps.executeQuery();

			while (rs.next()) {
				lookAndFeel[0] = rs.getString("themeId");
				lookAndFeel[1] = rs.getString("colorSchemeId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return lookAndFeel;
	}

	private String _getNewLayoutId(String oldLayoutId) {
		int pos = oldLayoutId.indexOf(".");

		if (pos != -1) {
			return oldLayoutId.substring(pos + 1, oldLayoutId.length());
		}
		else {
			return oldLayoutId;
		}
	}

	private String _getNewLayoutName(String oldName) {
		return "<?xml version=\"1.0\"?><root><name>" + oldName +
			"</name></root>";
	}

	private String _getNewLayoutTypeSettings(String oldTypeSettings)
		throws IOException {

		Properties props = new Properties();

		PropertiesUtil.load(props, oldTypeSettings);

		// Replace "column-order" with appropriate "layout-template-id"

		String[] columnOrder =
			StringUtil.split((String)props.remove("column-order"));

		String layoutTemplateId = "1_column";

		if (columnOrder.length == 3) {
			layoutTemplateId = "3_columns";
		}
		else if (columnOrder.length == 2) {
			if (columnOrder[0].equals("n1")) {
				layoutTemplateId = "2_columns_ii";
			}
			else {
				layoutTemplateId = "2_columns_iii";
			}
		}

		props.put(LayoutTypePortletImpl.LAYOUT_TEMPLATE_ID, layoutTemplateId);

		// Replace "narrow-x" with appropriate "column-x"

		String narrow1 = GetterUtil.getString(
			(String)props.remove("narrow-1"));

		String narrow2 = GetterUtil.getString(
			(String)props.remove("narrow-2"));

		String wide1 = GetterUtil.getString(
			(String)props.remove("wide-1"));

		for (int i = 0; i < columnOrder.length; i++) {
			String value = StringPool.BLANK;

			if (columnOrder[i].equals("n1")) {
				value = narrow1;
			}
			else if (columnOrder[i].equals("n2")) {
				value = narrow2;
			}
			else if (columnOrder[i].equals("w1")) {
				value = wide1;
			}

			String[] ids = StringUtil.split(value);

			for (int j = 0; j < ids.length; j++) {
				ids[j] = _getNewPortletId(ids[j]);
			}

			props.put("column-" + (i + 1), StringUtil.merge(ids));
		}

		return PropertiesUtil.toString(props);
	}

	private String _getNewPortletId(String oldPortletId) {
		for (int i = 0; i < _PORTLET_IDS.length; i++) {
			if (oldPortletId.startsWith(_PORTLET_IDS[i][0])) {
				oldPortletId = StringUtil.replace(
					oldPortletId, _PORTLET_IDS[i][0], _PORTLET_IDS[i][1]);

				break;
			}
		}

		return oldPortletId;
	}

	private String _getNewPortletPreferences(String xml) {
		for (int i = 0; i < _PORTLET_PREFERENCES.length; i++) {
			xml = StringUtil.replace(
				xml, _PORTLET_PREFERENCES[i][0], _PORTLET_PREFERENCES[i][1]);
		}

		return xml;
	}

	private void _upgradeLayout() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Set groupIds = new HashSet();

			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_UPGRADE_LAYOUT_1);

			rs = ps.executeQuery();

			while (rs.next()) {
				String oldLayoutId = rs.getString("layoutId");
				String oldOwnerId = rs.getString("ownerId");
				String companyId = rs.getString("companyId");
				String oldParentLayoutId = rs.getString("parentLayoutId");
				String oldName = rs.getString("name");
				String oldTypeSettings = rs.getString("typeSettings");

				_log.info(
					"Upgrading layout " +
						new LayoutPK(oldLayoutId, oldOwnerId));

				String newLayoutId = _getNewLayoutId(oldLayoutId);

				String newOwnerId = null;

				int pos = oldLayoutId.indexOf(".");

				if (pos != -1) {
					Long groupId =
						new Long(oldLayoutId.substring(0, pos));

					if (!groupIds.contains(groupId)) {
						String[] lookAndFeel =
							_getLookAndFeelByGroupId(groupId.longValue());

						LayoutSetLocalServiceUtil.addLayoutSet(
							LayoutImpl.PRIVATE + groupId, companyId);

						LayoutSetLocalServiceUtil.updateLookAndFeel(
							LayoutImpl.PRIVATE + groupId, lookAndFeel[0],
							lookAndFeel[1]);

						LayoutSetLocalServiceUtil.addLayoutSet(
							LayoutImpl.PUBLIC + groupId, companyId);

						LayoutSetLocalServiceUtil.updateLookAndFeel(
							LayoutImpl.PUBLIC + groupId, lookAndFeel[0],
							lookAndFeel[1]);
					}

					groupIds.add(groupId);

					Group group =
						GroupLocalServiceUtil.getGroup(groupId.longValue());

					if (group.getName().equals("Guest")) {
						newOwnerId = LayoutImpl.PUBLIC + groupId;
					}
					else if (group.getName().equals("General User")) {
						continue;
					}
					else {
						newOwnerId = LayoutImpl.PRIVATE + groupId;
					}
				}
				else {
					String userId = oldOwnerId;

					Group group = null;

					try {
						group = GroupLocalServiceUtil.getUserGroup(
							companyId, userId);
					}
					catch (NoSuchGroupException nsge) {
						group = GroupLocalServiceUtil.addGroup(
							userId, User.class.getName(), userId, null, null,
							null, null);

						long groupId = group.getGroupId();

						String[] lookAndFeel = _getLookAndFeelByUserId(userId);

						LayoutSetLocalServiceUtil.updateLookAndFeel(
							LayoutImpl.PRIVATE + groupId, lookAndFeel[0],
							lookAndFeel[1]);

						LayoutSetLocalServiceUtil.updateLookAndFeel(
							LayoutImpl.PUBLIC + groupId, lookAndFeel[0],
							lookAndFeel[1]);
					}

					newOwnerId = LayoutImpl.PRIVATE + group.getGroupId();
				}

				String newParentLayoutId = _getNewLayoutId(oldParentLayoutId);
				String newName = _getNewLayoutName(oldName);
				String newTypeSettings =
					_getNewLayoutTypeSettings(oldTypeSettings);

				ps = con.prepareStatement(_UPGRADE_LAYOUT_2);

				ps.setString(1, newLayoutId);
				ps.setString(2, newOwnerId);
				ps.setString(3, newParentLayoutId);
				ps.setString(4, newName);
				ps.setString(5, newTypeSettings);
				ps.setString(6, oldLayoutId);
				ps.setString(7, oldOwnerId);

				ps.executeUpdate();

				_upgradePortletPreferences(oldLayoutId, oldOwnerId, newOwnerId);

				LayoutSetLocalServiceUtil.updatePageCount(newOwnerId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private void _upgradePortletPreferences(
			String oldLayoutId, String oldOwnerId, String newOwnerId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_UPGRADE_PORTLET_PREFERENCES_1);

			ps.setString(1, oldLayoutId);
			ps.setString(2, oldOwnerId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String oldPortletId = rs.getString("portletId");
				String oldPreferences = rs.getString("preferences");

				_log.info(
					"Upgrading portlet preferences " +
						new PortletPreferencesPK(
							oldPortletId, oldLayoutId, oldOwnerId));

				String newPortletId = _getNewPortletId(oldPortletId);
				String newLayoutId = _getNewLayoutId(oldLayoutId);
				String newPreferences =
					_getNewPortletPreferences(oldPreferences);

				ps = con.prepareStatement(_UPGRADE_PORTLET_PREFERENCES_2);

				ps.setString(1, newPortletId);
				ps.setString(2, newLayoutId);
				ps.setString(3, newOwnerId);
				ps.setString(4, newPreferences);
				ps.setString(5, oldPortletId);
				ps.setString(6, oldLayoutId);
				ps.setString(7, oldOwnerId);

				ps.executeUpdate();
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String _GET_LOOK_AND_FEEL_BY_GROUP_ID =
		"SELECT themeId, colorSchemeId FROM Group_ WHERE groupId = ?";

	private static final String _GET_LOOK_AND_FEEL_BY_USER_ID =
		"SELECT themeId, colorSchemeId FROM User_ WHERE userId = ?";

	public static final String[][] _PORTLET_IDS = new String[][] {
		{"57_", "56_"},	// Journal article content
		{"55_", "54_"},	// Wiki display
		{"60_", "59_"}	// Polls display
	};

	public static final String[][] _PORTLET_PREFERENCES = new String[][] {
		{"portlet-title", "portlet-setup-title"},
		{"show-portlet-borders", "portlet-setup-show-borders"}
	};

	private static final String _UPGRADE_LAYOUT_1 = "SELECT * FROM Layout";

	private static final String _UPGRADE_LAYOUT_2 =
		"UPDATE Layout SET layoutId = ?, ownerId = ?, parentLayoutId = ?, " +
			"name = ?, typeSettings = ? WHERE layoutId = ? AND ownerId = ?";

	private static final String _UPGRADE_PORTLET_PREFERENCES_1 =
		"SELECT * FROM PortletPreferences WHERE layoutId = ? AND ownerId = ?";

	private static final String _UPGRADE_PORTLET_PREFERENCES_2 =
		"UPDATE PortletPreferences SET portletId = ?, layoutId = ? , " +
			"ownerId = ?, preferences = ? WHERE portletId = ? AND " +
				"layoutId = ? AND ownerId = ?";

	private static Log _log = LogFactory.getLog(UpgradeLayout.class);

}