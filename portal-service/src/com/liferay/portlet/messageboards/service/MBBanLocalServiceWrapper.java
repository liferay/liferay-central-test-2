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
		throws com.liferay.portal.SystemException {
		return _mbBanLocalService.addMBBan(mbBan);
	}

	public com.liferay.portlet.messageboards.model.MBBan createMBBan(long banId) {
		return _mbBanLocalService.createMBBan(banId);
	}

	public void deleteMBBan(long banId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_mbBanLocalService.deleteMBBan(banId);
	}

	public void deleteMBBan(com.liferay.portlet.messageboards.model.MBBan mbBan)
		throws com.liferay.portal.SystemException {
		_mbBanLocalService.deleteMBBan(mbBan);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _mbBanLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _mbBanLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.messageboards.model.MBBan getMBBan(long banId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbBanLocalService.getMBBan(banId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> getMBBans(
		int start, int end) throws com.liferay.portal.SystemException {
		return _mbBanLocalService.getMBBans(start, end);
	}

	public int getMBBansCount() throws com.liferay.portal.SystemException {
		return _mbBanLocalService.getMBBansCount();
	}

	public com.liferay.portlet.messageboards.model.MBBan updateMBBan(
		com.liferay.portlet.messageboards.model.MBBan mbBan)
		throws com.liferay.portal.SystemException {
		return _mbBanLocalService.updateMBBan(mbBan);
	}

	public com.liferay.portlet.messageboards.model.MBBan updateMBBan(
		com.liferay.portlet.messageboards.model.MBBan mbBan, boolean merge)
		throws com.liferay.portal.SystemException {
		return _mbBanLocalService.updateMBBan(mbBan, merge);
	}

	public com.liferay.portlet.messageboards.model.MBBan addBan(long userId,
		long banUserId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbBanLocalService.addBan(userId, banUserId, serviceContext);
	}

	public void checkBan(long groupId, long banUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_mbBanLocalService.checkBan(groupId, banUserId);
	}

	public void deleteBan(long banUserId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.SystemException {
		_mbBanLocalService.deleteBan(banUserId, serviceContext);
	}

	public void deleteBansByBanUserId(long banUserId)
		throws com.liferay.portal.SystemException {
		_mbBanLocalService.deleteBansByBanUserId(banUserId);
	}

	public void deleteBansByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		_mbBanLocalService.deleteBansByGroupId(groupId);
	}

	public void expireBans() throws com.liferay.portal.SystemException {
		_mbBanLocalService.expireBans();
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> getBans(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _mbBanLocalService.getBans(groupId, start, end);
	}

	public int getBansCount(long groupId)
		throws com.liferay.portal.SystemException {
		return _mbBanLocalService.getBansCount(groupId);
	}

	public boolean hasBan(long groupId, long banUserId)
		throws com.liferay.portal.SystemException {
		return _mbBanLocalService.hasBan(groupId, banUserId);
	}

	public MBBanLocalService getWrappedMBBanLocalService() {
		return _mbBanLocalService;
	}

	private MBBanLocalService _mbBanLocalService;
}