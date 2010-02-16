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

package com.liferay.portlet.documentlibrary.service;


/**
 * <a href="DLFileRankLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link DLFileRankLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileRankLocalService
 * @generated
 */
public class DLFileRankLocalServiceWrapper implements DLFileRankLocalService {
	public DLFileRankLocalServiceWrapper(
		DLFileRankLocalService dlFileRankLocalService) {
		_dlFileRankLocalService = dlFileRankLocalService;
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank addDLFileRank(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRankLocalService.addDLFileRank(dlFileRank);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank createDLFileRank(
		long fileRankId) {
		return _dlFileRankLocalService.createDLFileRank(fileRankId);
	}

	public void deleteDLFileRank(long fileRankId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileRankLocalService.deleteDLFileRank(fileRankId);
	}

	public void deleteDLFileRank(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileRankLocalService.deleteDLFileRank(dlFileRank);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRankLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRankLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank getDLFileRank(
		long fileRankId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRankLocalService.getDLFileRank(fileRankId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> getDLFileRanks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRankLocalService.getDLFileRanks(start, end);
	}

	public int getDLFileRanksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRankLocalService.getDLFileRanksCount();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank updateDLFileRank(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRankLocalService.updateDLFileRank(dlFileRank);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank updateDLFileRank(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRankLocalService.updateDLFileRank(dlFileRank, merge);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank addFileRank(
		long groupId, long companyId, long userId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRankLocalService.addFileRank(groupId, companyId, userId,
			folderId, name);
	}

	public void deleteFileRanks(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileRankLocalService.deleteFileRanks(userId);
	}

	public void deleteFileRanks(long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileRankLocalService.deleteFileRanks(folderId, name);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> getFileRanks(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRankLocalService.getFileRanks(groupId, userId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> getFileRanks(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRankLocalService.getFileRanks(groupId, userId, start, end);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank updateFileRank(
		long groupId, long companyId, long userId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRankLocalService.updateFileRank(groupId, companyId,
			userId, folderId, name);
	}

	public DLFileRankLocalService getWrappedDLFileRankLocalService() {
		return _dlFileRankLocalService;
	}

	private DLFileRankLocalService _dlFileRankLocalService;
}