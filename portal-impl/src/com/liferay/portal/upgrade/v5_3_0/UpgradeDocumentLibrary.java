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

package com.liferay.portal.upgrade.v5_3_0;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Date;

/**
 * <a href="UpgradeDocumentLibrary.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		updateFileVersions();
	}

	protected void updateFileVersions() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from DLFileEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				long userId = rs.getLong("userId");
				long companyId = rs.getLong("companyId");
				long groupId = rs.getLong("groupId");
				long folderId = rs.getLong("folderId");
				String name = rs.getString("name");
				int size = rs.getInt("size_");
				double version = rs.getDouble("version");

				try {
					addFileVersion(
						userId, companyId, groupId, folderId, name, size,
						version);
				}
				catch (Exception e) {
					_log.warn(
						"Version " + version + " for " + name + " was not " +
							"created: " + e.getMessage());
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void addFileVersion(
			long userId, long companyId, long groupId, long folderId,
			String fileName, int fileSize, double version)
		throws PortalException, SystemException {

		User user = UserLocalServiceUtil.getUser(userId);
		Date now = new Date();

		long fileVersionId = CounterLocalServiceUtil.increment();

		DLFileVersion fileVersion = new DLFileVersionImpl();

		fileVersion.setNew(true);
		fileVersion.setPrimaryKey(fileVersionId);

		fileVersion.setGroupId(groupId);
		fileVersion.setCompanyId(companyId);
		fileVersion.setUserId(user.getUserId());
		fileVersion.setUserName(user.getFullName());
		fileVersion.setCreateDate(now);
		fileVersion.setFolderId(folderId);
		fileVersion.setName(fileName);
		fileVersion.setVersion(version);
		fileVersion.setSize(fileSize);
		fileVersion.setStatus(StatusConstants.APPROVED);
		fileVersion.setStatusByUserId(user.getUserId());
		fileVersion.setStatusByUserName(user.getFullName());
		fileVersion.setStatusDate(now);

		DLFileVersionLocalServiceUtil.addDLFileVersion(fileVersion);
	}

	private static Log _log = LogFactoryUtil.getLog(
		UpgradeDocumentLibrary.class);

}