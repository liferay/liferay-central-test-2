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

package com.liferay.portal.upgrade.v4_1_0;

import com.liferay.counter.service.spring.CounterServiceUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.Constants;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePK;
import com.liferay.portlet.messageboards.service.spring.MBCategoryLocalServiceUtil;
import com.liferay.util.KeyValuePair;
import com.liferay.util.dao.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

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
			e.printStackTrace();
			throw new UpgradeException(e);
		}
	}

	private void _upgradeMessage(String categoryId, String topicId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			List kvpList = new ArrayList();

			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_UPGRADE_MESSAGE_1);

			ps.setString(1, topicId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String oldMessageId = rs.getString("messageId");

				String newMessageId = Long.toString(
					CounterServiceUtil.increment(MBMessage.class.getName()));

				kvpList.add(new KeyValuePair(oldMessageId, newMessageId));
			}

			for (int i = kvpList.size() - 1; i >= 0; i--) {
				KeyValuePair kvp = (KeyValuePair)kvpList.get(i);

				String oldMessageId = kvp.getKey();
				String newMessageId = kvp.getValue();

				_log.debug(
					"Upgrading message " +
						new MBMessagePK(topicId, oldMessageId));

				ps = con.prepareStatement(_UPGRADE_MESSAGE_5);

				ps.setString(1, newMessageId);
				ps.setString(2, topicId);
				ps.setString(3, oldMessageId);

				ps.executeUpdate();
			}

			for (int i = 0; i < kvpList.size(); i++) {
				KeyValuePair kvp = (KeyValuePair)kvpList.get(i);

				String oldMessageId = kvp.getKey();
				String newMessageId = kvp.getValue();

				_log.debug(
					"Upgrading message " +
						new MBMessagePK(topicId, oldMessageId));

				_upgradeMessage(
					categoryId, topicId, oldMessageId, newMessageId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private void _upgradeMessage(
			String categoryId, String topicId, String oldMessageId,
			String newMessageId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_UPGRADE_MESSAGE_2);

			ps.setString(1, categoryId);
			ps.setString(2, MBMessage.DEPRECATED_TOPIC_ID);
			ps.setString(3, newMessageId);
			ps.setString(4, topicId);
			ps.setString(5, oldMessageId);

			ps.executeUpdate();

			ps = con.prepareStatement(_UPGRADE_MESSAGE_3);

			ps.setString(1, MBMessage.DEPRECATED_TOPIC_ID);
			ps.setString(2, newMessageId);
			ps.setString(3, topicId);
			ps.setString(4, oldMessageId);

			ps.executeUpdate();

			ps = con.prepareStatement(_UPGRADE_MESSAGE_4);

			ps.setString(1, categoryId);
			ps.setString(2, MBMessage.DEPRECATED_TOPIC_ID);
			ps.setString(3, newMessageId);
			ps.setString(4, topicId);
			ps.setString(5, oldMessageId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	private void _upgradeCategory() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_UPGRADE_CATEGORY_1);

			rs = ps.executeQuery();

			while (rs.next()) {
				String topicId = rs.getString("topicId");
				String companyId = rs.getString("companyId");
				String userId = rs.getString("userId");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String parentCategoryId = rs.getString("categoryId");
				String name = rs.getString("name");
				String description = rs.getString("description");
				Timestamp lastPostDate = rs.getTimestamp("lastPostDate");

				_log.debug("Upgrading topic " + topicId);

				MBCategory category = null;

				if (topicId.equals(Company.SYSTEM)) {
					category = MBCategoryLocalServiceUtil.getSystemCategory();
				}
				else {
					MBCategory parentCategory =
						MBCategoryLocalServiceUtil.getCategory(
							parentCategoryId);

					String plid =
						Layout.PUBLIC + parentCategory.getGroupId() + ".1";
					boolean addCommunityPermissions = true;
					boolean addGuestPermissions = true;

					category = MBCategoryLocalServiceUtil.addCategory(
						userId, plid, parentCategoryId, name, description,
						addCommunityPermissions, addGuestPermissions);
				}

				_upgradeMessage(category.getCategoryId(), topicId);

				_upgradeCategory(
					category.getCategoryId(), createDate, modifiedDate,
					lastPostDate);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private void _upgradeCategory(
			String categoryId, Timestamp createDate, Timestamp modifiedDate,
			Timestamp lastPostDate)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_UPGRADE_CATEGORY_2);

			ps.setTimestamp(1, createDate);
			ps.setTimestamp(2, modifiedDate);
			ps.setTimestamp(3, lastPostDate);
			ps.setString(4, categoryId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	public static final String _UPGRADE_CATEGORY_1 = "SELECT * FROM MBTopic";

	public static final String _UPGRADE_CATEGORY_2 =
		"UPDATE MBCategory SET createDate = ?, modifiedDate = ?, " +
			"lastPostDate = ? WHERE categoryId = ?";

	public static final String _UPGRADE_MESSAGE_1 =
		"SELECT * FROM MBMessage WHERE topicId = ? ORDER by createDate ASC, " +
			"messageId ASC";

	public static final String _UPGRADE_MESSAGE_2 =
		"UPDATE MBMessage SET categoryId = ?, topicId = ?, messageId = ? " +
			"WHERE topicId = ? AND messageId = ?";

	public static final String _UPGRADE_MESSAGE_3 =
		"UPDATE MBMessageFlag SET topicId = ?, messageId = ? WHERE " +
			"topicId = ? AND messageId = ?";

	public static final String _UPGRADE_MESSAGE_4 =
		"UPDATE MBThread SET categoryId = ?, topicId = ?, rootMessageId = ? " +
			"WHERE topicId = ? AND rootMessageId = ?";

	public static final String _UPGRADE_MESSAGE_5 =
		"UPDATE MBMessage SET parentMessageId = ? WHERE topicId = ? AND " +
			"parentMessageId = ?";

	private static Log _log = LogFactory.getLog(UpgradeMessageBoards.class);

}