/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portlet.documentlibrary.NoSuchFileRankException;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.service.base.DLFileRankLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankUtil;

import java.util.Date;
import java.util.List;

/**
 * <a href="DLFileRankLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DLFileRankLocalServiceImpl extends DLFileRankLocalServiceBaseImpl {

	public void deleteFileRanks(long userId) throws SystemException {
		DLFileRankUtil.removeByUserId(userId);
	}

	public void deleteFileRanks(long folderId, String name)
		throws SystemException {

		DLFileRankUtil.removeByF_N(folderId, name);
	}

	public List getFileRanks(long groupId, long userId)
		throws SystemException {

		return DLFileRankFinder.findByG_U(groupId, userId);
	}

	public DLFileRank updateFileRank(
			long groupId, long companyId, long userId, long folderId,
			String name)
		throws PortalException, SystemException{

		try {
			DLFileRankUtil.removeByC_U_F_N(companyId, userId, folderId, name);
		}
		catch (NoSuchFileRankException nsfre) {
		}

		long fileRankId = CounterLocalServiceUtil.increment();

		DLFileRank fileRank = DLFileRankUtil.create(fileRankId);

		fileRank.setCompanyId(companyId);
		fileRank.setUserId(userId);
		fileRank.setCreateDate(new Date());
		fileRank.setFolderId(folderId);
		fileRank.setName(name);

		DLFileRankUtil.update(fileRank);

		if (DLFileRankFinder.countByG_U(groupId, userId) > 5) {
			List fileRanks = DLFileRankFinder.findByG_U(groupId, userId);

			DLFileRank lastFileRank = (DLFileRank)fileRanks.get(
				fileRanks.size() - 1);

			DLFileRankUtil.remove(lastFileRank.getPrimaryKey());
		}

		return fileRank;
	}

}