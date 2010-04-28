/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRankLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRankLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRankLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRankLocalService.dynamicQueryCount(dynamicQuery);
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
		java.lang.String name,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRankLocalService.addFileRank(groupId, companyId, userId,
			folderId, name, serviceContext);
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
		java.lang.String name,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRankLocalService.updateFileRank(groupId, companyId,
			userId, folderId, name, serviceContext);
	}

	public DLFileRankLocalService getWrappedDLFileRankLocalService() {
		return _dlFileRankLocalService;
	}

	private DLFileRankLocalService _dlFileRankLocalService;
}