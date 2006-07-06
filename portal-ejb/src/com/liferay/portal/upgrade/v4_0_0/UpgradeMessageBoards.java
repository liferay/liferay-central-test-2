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

package com.liferay.portal.upgrade.v4_0_0;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.Constants;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePK;
import com.liferay.portlet.messageboards.service.spring.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.spring.MBMessageLocalServiceUtil;
import com.liferay.util.dao.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeMessageBoards.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UpgradeMessageBoards extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeCategory();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradeCategory() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_UPGRADE_CATEGORY_1);

			ps.setString(1, Group.DEFAULT_PARENT_GROUP_ID);

			rs = ps.executeQuery();

			while (rs.next()) {
				String groupId = rs.getString("groupId");
				String userId = rs.getString("userId");

				String plid = Layout.PUBLIC + groupId + ".1";
				String parentCategoryId = MBCategory.DEFAULT_PARENT_CATEGORY_ID;
				String name = "Default Category";
				String description = name;
				boolean addCommunityPermissions = true;
				boolean addGuestPermissions = true;

				_log.debug("Adding category to group " + groupId);

				MBCategory category = MBCategoryLocalServiceUtil.addCategory(
					userId, plid, parentCategoryId, name, description,
					addCommunityPermissions, addGuestPermissions);

				_upgradeCategory(groupId, userId, category.getCategoryId());
			}
		}
		catch (NoSuchGroupException nsge) {
			_log.error(
				"Upgrade failed because data does not have a valid group id. " +
					"Manually assign a group id in the database.");
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private void _upgradeCategory(
			String groupId, String userId, String categoryId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_UPGRADE_CATEGORY_2);

			ps.setString(1, groupId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String topicId = rs.getString("topicId");
				String name = rs.getString("name");
				String description = rs.getString("description");

				String plid = Layout.PUBLIC + groupId + ".1";
				boolean addCommunityPermissions = true;
				boolean addGuestPermissions = true;

				_log.debug("Upgrading topic " + topicId);

				MBCategory category = MBCategoryLocalServiceUtil.addCategory(
					userId, plid, categoryId, name, description,
					addCommunityPermissions, addGuestPermissions);

				_upgradeMessage(categoryId, topicId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private void _upgradeMessage(String categoryId, String topicId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_UPGRADE_MESSAGE_1);

			ps.setString(1, topicId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String messageId = rs.getString("messageId");

				boolean addCommunityPermissions = true;
				boolean addGuestPermissions = true;

				_log.debug(
					"Upgrading message " + new MBMessagePK(topicId, messageId));

				MBMessageLocalServiceUtil.addMessageResources(
					categoryId, topicId, messageId, addCommunityPermissions,
					addGuestPermissions);
			}

			ps = con.prepareStatement(_UPGRADE_MESSAGE_2);

			ps.setString(1, categoryId);
			ps.setString(2, topicId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	public static final String _UPGRADE_CATEGORY_1 =
		"SELECT DISTINCT groupId, userId FROM MBTopic WHERE groupId != ?";

	public static final String _UPGRADE_CATEGORY_2 =
		"SELECT * FROM MBTopic WHERE groupId = ?";

	public static final String _UPGRADE_MESSAGE_1 =
		"SELECT * FROM MBMessage WHERE topicId = ?";

	public static final String _UPGRADE_MESSAGE_2 =
		"UPDATE MBMessage SET categoryId = ? WHERE topicId = ?";

	private static Log _log = LogFactory.getLog(UpgradeMessageBoards.class);

}