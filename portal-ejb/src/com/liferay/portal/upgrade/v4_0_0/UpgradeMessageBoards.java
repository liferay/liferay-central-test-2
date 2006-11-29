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

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.PortalException;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePK;
import com.liferay.util.FileUtil;
import com.liferay.util.dao.DataAccess;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

	private byte[] _dlGetFile(
			String companyId, String repositoryId, String fileName)
		throws PortalException {

		String coRepoId = companyId + StringPool.PERIOD + repositoryId;

		File file = new File(
			PropsUtil.get(PropsUtil.DL_ROOT_DIR) + coRepoId + fileName);

		byte[] bytes = null;

		try {
			bytes = FileUtil.getBytes(file);

			if (bytes == null || bytes.length == 0) {
				throw new IOException();
			}
		}
		catch (IOException ioe) {
			throw new NoSuchFileException(fileName);
		}

		return bytes;
	}

	private String[] _dlGetFileNames(
			String companyId, String repositoryId, String dirName)
		throws PortalException {

		String coRepoId = companyId + StringPool.PERIOD + repositoryId;

		File directory = new File(
			PropsUtil.get(PropsUtil.DL_ROOT_DIR) + coRepoId + dirName);

		if (!directory.exists()) {
			throw new NoSuchDirectoryException(dirName);
		}

		List fileNames = new ArrayList();

		File[] array = directory.listFiles();

		for (int i = 0; i < array.length; i++) {
			if (array[i].isFile()) {
				fileNames.add(dirName + "/" + array[i].getName());
			}
		}

		return (String[])fileNames.toArray(new String[0]);
	}

	private void _upgradeCategory() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_UPGRADE_CATEGORY_1);

			ps.setString(1, GroupImpl.DEFAULT_PARENT_GROUP_ID);

			rs = ps.executeQuery();

			while (rs.next()) {
				String groupId = rs.getString("groupId");
				String userId = rs.getString("userId");

				String plid = LayoutImpl.PUBLIC + groupId + ".1";
				String parentCategoryId =
					MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID;
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

				String plid = LayoutImpl.PUBLIC + groupId + ".1";
				boolean addCommunityPermissions = true;
				boolean addGuestPermissions = true;

				_log.debug("Upgrading topic " + topicId);

				MBCategoryLocalServiceUtil.addCategory(
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
				String companyId = rs.getString("companyId");
				boolean attachments = rs.getBoolean("attachments");

				boolean addCommunityPermissions = true;
				boolean addGuestPermissions = true;

				_log.debug(
					"Upgrading message " + new MBMessagePK(topicId, messageId));

				MBMessageLocalServiceUtil.addMessageResources(
					categoryId, topicId, messageId, addCommunityPermissions,
					addGuestPermissions);

				if (attachments) {
					_log.debug(
						"Message " + new MBMessagePK(topicId, messageId) +
							" has attachments");

					_upgradeMessageAttachments(companyId, topicId, messageId);
				}
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

	private void _upgradeMessageAttachments(
			String companyId, String topicId, String messageId)
		throws Exception {

		String portletId = CompanyImpl.SYSTEM;
		String groupId = GroupImpl.DEFAULT_PARENT_GROUP_ID;
		String repositoryId = CompanyImpl.SYSTEM;
		String dirName = "/messageboards/" + topicId + "/" + messageId;

		String[] fileNames = null;

		try {
			fileNames = _dlGetFileNames(companyId, repositoryId, dirName);
		}
		catch (NoSuchDirectoryException nsde) {
		}

		if (fileNames == null) {
			return;
		}

		dirName = dirName.substring(1, dirName.length());

		try {
			DLServiceUtil.deleteDirectory(
				companyId, portletId, repositoryId, dirName);
		}
		catch (NoSuchDirectoryException nsde) {
		}

		DLServiceUtil.addDirectory(companyId, repositoryId, dirName);

		for (int i = 0; i < fileNames.length; i++) {
			String fileName = FileUtil.getShortFileName(fileNames[i]);
			byte[] byteArray = _dlGetFile(
				companyId, repositoryId, fileNames[i]);

			_log.debug(
				"Migrating message board attachment " + fileNames[i]);

			try {
				DLServiceUtil.addFile(
					companyId, portletId, groupId, repositoryId,
					dirName + "/" + fileName, byteArray);
			}
			catch (DuplicateFileException dfe) {
			}
		}
	}

	private static final String _UPGRADE_CATEGORY_1 =
		"SELECT DISTINCT groupId, userId FROM MBTopic WHERE groupId != ?";

	private static final String _UPGRADE_CATEGORY_2 =
		"SELECT * FROM MBTopic WHERE groupId = ?";

	private static final String _UPGRADE_MESSAGE_1 =
		"SELECT * FROM MBMessage WHERE topicId = ?";

	private static final String _UPGRADE_MESSAGE_2 =
		"UPDATE MBMessage SET categoryId = ? WHERE topicId = ?";

	private static Log _log = LogFactory.getLog(UpgradeMessageBoards.class);

}