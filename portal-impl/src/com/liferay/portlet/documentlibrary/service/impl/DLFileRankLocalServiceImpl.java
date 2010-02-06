/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.service.base.DLFileRankLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.util.comparator.FileRankCreateDateComparator;

import java.util.Date;
import java.util.List;

/**
 * <a href="DLFileRankLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DLFileRankLocalServiceImpl extends DLFileRankLocalServiceBaseImpl {

	public DLFileRank addFileRank(
			long groupId, long companyId, long userId, long folderId,
			String name)
		throws SystemException {

		long fileRankId = counterLocalService.increment();

		DLFileRank fileRank = dlFileRankPersistence.create(fileRankId);

		fileRank.setGroupId(groupId);
		fileRank.setCompanyId(companyId);
		fileRank.setUserId(userId);
		fileRank.setCreateDate(new Date());
		fileRank.setFolderId(folderId);
		fileRank.setName(name);

		try {
			dlFileRankPersistence.update(fileRank, false);
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Add failed, fetch {companyId=" + companyId + ", userId=" +
						userId + ", folderId=" + folderId + ", name=" + name +
							"}");
			}

			fileRank = dlFileRankPersistence.fetchByC_U_F_N(
				companyId, userId, folderId, name, false);

			if (fileRank == null) {
				throw se;
			}
		}

		return fileRank;
	}

	public void deleteFileRanks(long userId) throws SystemException {
		dlFileRankPersistence.removeByUserId(userId);
	}

	public void deleteFileRanks(long folderId, String name)
		throws SystemException {

		dlFileRankPersistence.removeByF_N(folderId, name);
	}

	public List<DLFileRank> getFileRanks(long groupId, long userId)
		throws SystemException {

		return getFileRanks(
			groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<DLFileRank> getFileRanks(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return dlFileRankPersistence.findByG_U(
			groupId, userId, start, end, new FileRankCreateDateComparator());
	}

	public DLFileRank updateFileRank(
			long groupId, long companyId, long userId, long folderId,
			String name)
		throws SystemException {

		if (!PropsValues.DL_FILE_RANK_ENABLED) {
			return null;
		}

		DLFileRank fileRank = dlFileRankPersistence.fetchByC_U_F_N(
			companyId, userId, folderId, name);

		if (fileRank != null) {
			fileRank.setCreateDate(new Date());

			dlFileRankPersistence.update(fileRank, false);
		}
		else {
			fileRank = addFileRank(
				groupId, companyId, userId, folderId, name);
		}

		if (dlFileRankPersistence.countByG_U(groupId, userId) > 5) {
			List<DLFileRank> fileRanks = getFileRanks(groupId, userId);

			DLFileRank lastFileRank = fileRanks.get(fileRanks.size() - 1);

			long lastFileRankId = lastFileRank.getFileRankId();

			try {
				dlFileRankPersistence.remove(lastFileRank);
			}
			catch (Exception e) {
				_log.warn(
					"Failed to remove file rank " + lastFileRankId +
						" because another thread already removed it");
			}
		}

		return fileRank;
	}

	private static Log _log =
		LogFactoryUtil.getLog(DLFileRankLocalServiceImpl.class);

}