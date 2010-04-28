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

package com.liferay.portlet.messageboards.service;


/**
 * <a href="MBBanLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link MBBanLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBBanLocalService
 * @generated
 */
public class MBBanLocalServiceWrapper implements MBBanLocalService {
	public MBBanLocalServiceWrapper(MBBanLocalService mbBanLocalService) {
		_mbBanLocalService = mbBanLocalService;
	}

	public com.liferay.portlet.messageboards.model.MBBan addMBBan(
		com.liferay.portlet.messageboards.model.MBBan mbBan)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.addMBBan(mbBan);
	}

	public com.liferay.portlet.messageboards.model.MBBan createMBBan(long banId) {
		return _mbBanLocalService.createMBBan(banId);
	}

	public void deleteMBBan(long banId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbBanLocalService.deleteMBBan(banId);
	}

	public void deleteMBBan(com.liferay.portlet.messageboards.model.MBBan mbBan)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbBanLocalService.deleteMBBan(mbBan);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.messageboards.model.MBBan getMBBan(long banId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.getMBBan(banId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> getMBBans(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.getMBBans(start, end);
	}

	public int getMBBansCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.getMBBansCount();
	}

	public com.liferay.portlet.messageboards.model.MBBan updateMBBan(
		com.liferay.portlet.messageboards.model.MBBan mbBan)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.updateMBBan(mbBan);
	}

	public com.liferay.portlet.messageboards.model.MBBan updateMBBan(
		com.liferay.portlet.messageboards.model.MBBan mbBan, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.updateMBBan(mbBan, merge);
	}

	public com.liferay.portlet.messageboards.model.MBBan addBan(long userId,
		long banUserId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.addBan(userId, banUserId, serviceContext);
	}

	public void checkBan(long groupId, long banUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbBanLocalService.checkBan(groupId, banUserId);
	}

	public void deleteBan(long banUserId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbBanLocalService.deleteBan(banUserId, serviceContext);
	}

	public void deleteBansByBanUserId(long banUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbBanLocalService.deleteBansByBanUserId(banUserId);
	}

	public void deleteBansByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbBanLocalService.deleteBansByGroupId(groupId);
	}

	public void expireBans()
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbBanLocalService.expireBans();
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> getBans(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.getBans(groupId, start, end);
	}

	public int getBansCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.getBansCount(groupId);
	}

	public boolean hasBan(long groupId, long banUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.hasBan(groupId, banUserId);
	}

	public MBBanLocalService getWrappedMBBanLocalService() {
		return _mbBanLocalService;
	}

	private MBBanLocalService _mbBanLocalService;
}