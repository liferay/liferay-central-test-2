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

import com.liferay.counter.service.spring.CounterServiceUtil;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPK;
import com.liferay.portlet.documentlibrary.service.spring.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.spring.DLFolderLocalServiceUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.dao.DataAccess;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeDocumentLibrary.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeFolder();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private String _fixName(String name) {
		name = StringUtil.replace(name, "[", "");
		name = StringUtil.replace(name, "]", "");
		name = StringUtil.replace(name, "'", "");

		return name;
	}

	private String _getFileEntryName(String fileName) {
		int pos = fileName.lastIndexOf(StringPool.SLASH);

		return fileName.substring(pos + 1, fileName.length());
	}

	private String _getSubfolderId(String folderId, String fileName)
		throws PortalException, SystemException {

		if (StringUtil.count(fileName, StringPool.SLASH) == 1) {
			return folderId;
		}

		DLFolder parentFolder = DLFolderLocalServiceUtil.getFolder(folderId);

		String[] folderNames = StringUtil.split(fileName, StringPool.SLASH);

		for (int i = 1; i < (folderNames.length - 1); i++) {
			try {
				parentFolder = DLFolderLocalServiceUtil.getFolder(
					parentFolder.getFolderId(), folderNames[i]);
			}
			catch (NoSuchFolderException nsfe) {
				String userId = parentFolder.getUserId();
				String plid = Layout.PUBLIC + parentFolder.getGroupId() + ".1";
				String parentFolderId = parentFolder.getFolderId();
				String name = _fixName(folderNames[i]);
				String description = name;
				boolean addCommunityPermissions = true;
				boolean addGuestPermissions = true;

				parentFolder = DLFolderLocalServiceUtil.addFolder(
					userId, plid, parentFolderId, name, description,
					addCommunityPermissions, addGuestPermissions);
			}
		}

		return parentFolder.getFolderId();
	}

	private void _dlDeleteAll(
		String companyId, String portletId, String repositoryId) {

		String coRepoId = companyId + StringPool.PERIOD + repositoryId;

		FileUtil.deltree(PropsUtil.get(PropsUtil.DL_ROOT_DIR) + coRepoId);

		FileUtil.deltree(
			PropsUtil.get(PropsUtil.DL_VERSION_ROOT_DIR) + coRepoId);
	}

	private boolean _dlExists(
		String companyId, String repositoryId, String fileName) {

		String coRepoId = companyId + StringPool.PERIOD + repositoryId;

		File file = new File(
			PropsUtil.get(PropsUtil.DL_ROOT_DIR) + coRepoId + fileName);

		return file.exists();
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

	private void _upgradeFileEntry(String repositoryId, String folderId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_UPGRADE_FILE_ENTRY);

			ps.setString(1, repositoryId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String companyId = rs.getString("companyId");
				String fileName = rs.getString("fileName");
				String userId = rs.getString("userId");

				if (_dlExists(companyId, repositoryId, fileName)) {
					byte[] byteArray = _dlGetFile(
						companyId, repositoryId, fileName);

					String subfolderId = _getSubfolderId(folderId, fileName);
					String name = _fixName(_getFileEntryName(fileName));
					String title = name;
					String description = name;
					boolean addCommunityPermissions = true;
					boolean addGuestPermissions = true;

					_log.debug(
						"Migrating file profile " +
							new DLFileEntryPK(subfolderId, name));

					DLFileEntryLocalServiceUtil.addFileEntry(
						userId, subfolderId, name, title, description,
						byteArray, addCommunityPermissions,
						addGuestPermissions);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private void _upgradeFolder() throws Exception {
		CounterServiceUtil.rename(
			"com.liferay.portlet.documentlibrary.model.DLRepository",
			DLFolder.class.getName());

		long folderIdCount = CounterServiceUtil.increment(
			DLFolder.class.getName());

		CounterServiceUtil.increment(
			DLFolder.class.getName(), (int)folderIdCount * 2);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_UPGRADE_FOLDER_1);

			ps.setString(1, Group.DEFAULT_PARENT_GROUP_ID);

			rs = ps.executeQuery();

			while (rs.next()) {
				String repositoryId = rs.getString("repositoryId");
				String groupId = rs.getString("groupId");
				String companyId = rs.getString("companyId");
				String userId = rs.getString("userId");
				String name = _fixName(rs.getString("name"));

				String plid = Layout.PUBLIC + groupId + ".1";
				String parentFolderId = DLFolder.DEFAULT_PARENT_FOLDER_ID;
				String description = name;
				boolean addCommunityPermissions = true;
				boolean addGuestPermissions = true;

				_log.debug("Migrating repository " + repositoryId);

				DLFolder folder = DLFolderLocalServiceUtil.addFolder(
					userId, plid, parentFolderId, name, description,
					addCommunityPermissions, addGuestPermissions);

				_upgradeFileEntry(repositoryId, folder.getFolderId());

				_dlDeleteAll(
					companyId, PortletKeys.DOCUMENT_LIBRARY, repositoryId);

				ps = con.prepareStatement(_UPGRADE_FOLDER_2);

				ps.setString(1, repositoryId);

				ps.executeUpdate();
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String _UPGRADE_FILE_ENTRY =
		"SELECT * FROM DLFileProfile where repositoryId = ?";

	private static final String _UPGRADE_FOLDER_1 =
		"SELECT * FROM DLRepository where groupId != ?";
		//"SELECT * FROM DLRepository where repositoryId = '24'";
		//"SELECT * FROM DLRepository where repositoryId = '122'";

	private static final String _UPGRADE_FOLDER_2 =
		"DELETE FROM DLFileProfile where repositoryId = ?";

	private static Log _log = LogFactory.getLog(UpgradeDocumentLibrary.class);

}