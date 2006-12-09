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

package com.liferay.portal.upgrade.v4_1_0;

import com.liferay.counter.service.CounterServiceUtil;
import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageImpl;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePK;
import com.liferay.util.FileUtil;
import com.liferay.util.SystemProperties;
import com.liferay.util.Validator;
import com.liferay.util.dao.DataAccess;

import java.io.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			_log.error(StackTraceUtil.getStackTrace(e));

			throw new UpgradeException(e);
		}
	}

	private List _getFiles(
			String companyId, String topicId, String oldMessageId,
			boolean attachments)
		throws Exception {

		List files = new ArrayList();

		if (!attachments) {
			return files;
		}

		String portletId = CompanyImpl.SYSTEM;
		String repositoryId = CompanyImpl.SYSTEM;
		String dirName = "messageboards/" + topicId + "/" + oldMessageId;

		String[] fileNames = null;

		try {
			fileNames = DLServiceUtil.getFileNames(
				companyId, repositoryId, dirName);
		}
		catch (NoSuchDirectoryException nsde) {
			return files;
		}

		String tmpDir = SystemProperties.get(SystemProperties.TMP_DIR);

		for (int i = 0; i < fileNames.length; i++) {
			String fileName = fileNames[i];

			byte[] byteArray = DLServiceUtil.getFile(
				companyId, repositoryId, fileName);

			FileUtil.write(tmpDir + "/" + fileName, byteArray);

			files.add(fileName);
		}

		try {
			DLServiceUtil.deleteDirectory(
				companyId, portletId, repositoryId, dirName);
		}
		catch (NoSuchDirectoryException nsde) {
		}

		return files;
	}

	private void _upgradeCategory(
			String categoryId, Timestamp createDate, Timestamp modifiedDate,
			Timestamp lastPostDate)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = HibernateUtil.getConnection();

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

	private void _upgradeFiles(
			String companyId, String threadId, String newMessageId, List files)
		throws Exception {

		if (files.size() == 0) {
			return;
		}

		String portletId = CompanyImpl.SYSTEM;
		String groupId = GroupImpl.DEFAULT_PARENT_GROUP_ID;
		String repositoryId = CompanyImpl.SYSTEM;
		String dirName = "messageboards/" + threadId + "/" + newMessageId;

		try {
			DLServiceUtil.deleteDirectory(
				companyId, portletId, repositoryId, dirName);
		}
		catch (NoSuchDirectoryException nsde) {
		}

		DLServiceUtil.addDirectory(companyId, repositoryId, dirName);

		String tmpDir = SystemProperties.get(SystemProperties.TMP_DIR);

		for (int i = 0; i < files.size(); i++) {
			String fileName = (String)files.get(i);

			File file = new File(tmpDir + "/" + fileName);
			byte[] byteArray = FileUtil.getBytes(file);

			int pos = fileName.indexOf("/");

			pos = fileName.indexOf("/", pos + 1);
			pos = fileName.indexOf("/", pos + 1);

			fileName = fileName.substring(pos + 1, fileName.length());

			try {
				DLServiceUtil.addFile(
					companyId, portletId, groupId, repositoryId,
					dirName + "/" + fileName, byteArray);
			}
			catch (DuplicateFileException dfe) {
			}

			file.delete();
		}
	}

	private void _upgradeMessage(String categoryId, String topicId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			List messages = new ArrayList();

			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_UPGRADE_MESSAGE_1);

			ps.setString(1, topicId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String oldMessageId = rs.getString("messageId");
				String companyId = rs.getString("companyId");
				String threadId = rs.getString("threadId");
				boolean attachments = rs.getBoolean("attachments");

				String newMessageId = Long.toString(
					CounterServiceUtil.increment(MBMessage.class.getName()));

				List files = _getFiles(
					companyId, topicId, oldMessageId, attachments);

				messages.add(
					new Object[] {
						companyId, threadId, oldMessageId, newMessageId, files
					});
			}

			for (int i = messages.size() - 1; i >= 0; i--) {
				Object[] array = (Object[])messages.get(i);

				String oldMessageId = (String)array[2];
				String newMessageId = (String)array[3];

				_log.info(
					"Upgrading message " +
						new MBMessagePK(topicId, oldMessageId));

				ps = con.prepareStatement(_UPGRADE_MESSAGE_2);

				ps.setString(1, newMessageId);
				ps.setString(2, topicId);
				ps.setString(3, oldMessageId);

				ps.executeUpdate();
			}

			for (int i = 0; i < messages.size(); i++) {
				Object[] array = (Object[])messages.get(i);

				String companyId = (String)array[0];
				String threadId = (String)array[1];
				String oldMessageId = (String)array[2];
				String newMessageId = (String)array[3];
				List files = (List)array[4];

				_log.info(
					"Upgrading message " +
						new MBMessagePK(topicId, oldMessageId));

				_upgradeMessage(
					categoryId, topicId, oldMessageId, newMessageId);

				_upgradeFiles(companyId, threadId, newMessageId, files);
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
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_UPGRADE_MESSAGE_3);

			ps.setString(1, categoryId);
			ps.setString(2, MBMessageImpl.DEPRECATED_TOPIC_ID);
			ps.setString(3, newMessageId);
			ps.setString(4, topicId);
			ps.setString(5, oldMessageId);

			ps.executeUpdate();

			ps = con.prepareStatement(_UPGRADE_MESSAGE_4);

			ps.setString(1, MBMessageImpl.DEPRECATED_TOPIC_ID);
			ps.setString(2, newMessageId);
			ps.setString(3, topicId);
			ps.setString(4, oldMessageId);

			ps.executeUpdate();

			ps = con.prepareStatement(_UPGRADE_MESSAGE_5);

			ps.setString(1, categoryId);
			ps.setString(2, MBMessageImpl.DEPRECATED_TOPIC_ID);
			ps.setString(3, newMessageId);
			ps.setString(4, topicId);
			ps.setString(5, oldMessageId);

			ps.executeUpdate();

			ps = con.prepareStatement(_UPGRADE_MESSAGE_6);

			ps.setString(1, "ADD_MESSAGE");
			ps.setString(2, "ADD_TOPIC");

			ps.executeUpdate();

			ps = con.prepareStatement(_UPGRADE_MESSAGE_7);

			ps.setString(
				1,
				"{topicId=" + MBMessageImpl.DEPRECATED_TOPIC_ID +
					", messageId=" + newMessageId + "}");
			ps.setString(2, MBMessage.class.getName());
			ps.setString(
				3, "{topicId=" + topicId + ", messageId=" + oldMessageId + "}");

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
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_UPGRADE_CATEGORY_1);

			rs = ps.executeQuery();

			while (rs.next()) {
				String topicId = rs.getString("topicId");
				String userId = rs.getString("userId");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String parentCategoryId = rs.getString("categoryId");
				String name = rs.getString("name");
				String description = rs.getString("description");
				Timestamp lastPostDate = rs.getTimestamp("lastPostDate");

				_log.info("Upgrading topic " + topicId);

				MBCategory category = null;

				if (topicId.equals(CompanyImpl.SYSTEM)) {
					category = MBCategoryLocalServiceUtil.getSystemCategory();
				}
				else {
					String groupId = null;

					try {
						groupId = rs.getString("groupId");
					}
					catch (SQLException sqle) {
					}

					if (Validator.isNull(parentCategoryId)) {
						parentCategoryId =
							MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID;
					}
					else {
						MBCategory parentCategory =
							MBCategoryLocalServiceUtil.getCategory(
								parentCategoryId);

						groupId = parentCategory.getGroupId();
					}

					if (Validator.isNotNull(groupId) &&
						!groupId.equals(GroupImpl.DEFAULT_PARENT_GROUP_ID)) {

						String plid = LayoutImpl.PUBLIC + groupId + ".1";
						boolean addCommunityPermissions = true;
						boolean addGuestPermissions = true;

						category = MBCategoryLocalServiceUtil.addCategory(
							userId, plid, parentCategoryId, name, description,
							addCommunityPermissions, addGuestPermissions);
					}
				}

				if (category != null) {
					_upgradeMessage(category.getCategoryId(), topicId);

					_upgradeCategory(
						category.getCategoryId(), createDate, modifiedDate,
						lastPostDate);
				}
			}

			ResourceLocalServiceUtil.deleteResources(
				"com.liferay.portlet.messageboards.model.MBTopic");
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

	private static final String _UPGRADE_CATEGORY_1 = "SELECT * FROM MBTopic";

	private static final String _UPGRADE_CATEGORY_2 =
		"UPDATE MBCategory SET createDate = ?, modifiedDate = ?, " +
			"lastPostDate = ? WHERE categoryId = ?";

	private static final String _UPGRADE_MESSAGE_1 =
		"SELECT * FROM MBMessage WHERE topicId = ? ORDER by createDate ASC, " +
			"messageId ASC";

	private static final String _UPGRADE_MESSAGE_2 =
		"UPDATE MBMessage SET parentMessageId = ? WHERE topicId = ? AND " +
			"parentMessageId = ?";

	private static final String _UPGRADE_MESSAGE_3 =
		"UPDATE MBMessage SET categoryId = ?, topicId = ?, messageId = ? " +
			"WHERE topicId = ? AND messageId = ?";

	private static final String _UPGRADE_MESSAGE_4 =
		"UPDATE MBMessageFlag SET topicId = ?, messageId = ? WHERE " +
			"topicId = ? AND messageId = ?";

	private static final String _UPGRADE_MESSAGE_5 =
		"UPDATE MBThread SET categoryId = ?, topicId = ?, rootMessageId = ? " +
			"WHERE topicId = ? AND rootMessageId = ?";

	private static final String _UPGRADE_MESSAGE_6 =
		"UPDATE Permission_ SET actionId = ? WHERE actionId = ?";

	private static final String _UPGRADE_MESSAGE_7 =
		"UPDATE Resource_ SET primKey = ? WHERE name = ? AND primKey = ?";

	private static Log _log = LogFactory.getLog(UpgradeMessageBoards.class);

}