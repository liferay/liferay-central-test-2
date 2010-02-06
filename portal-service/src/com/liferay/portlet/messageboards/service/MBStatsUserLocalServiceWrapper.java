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
 * <a href="MBStatsUserLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link MBStatsUserLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBStatsUserLocalService
 * @generated
 */
public class MBStatsUserLocalServiceWrapper implements MBStatsUserLocalService {
	public MBStatsUserLocalServiceWrapper(
		MBStatsUserLocalService mbStatsUserLocalService) {
		_mbStatsUserLocalService = mbStatsUserLocalService;
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser addMBStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser)
		throws com.liferay.portal.SystemException {
		return _mbStatsUserLocalService.addMBStatsUser(mbStatsUser);
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser createMBStatsUser(
		long statsUserId) {
		return _mbStatsUserLocalService.createMBStatsUser(statsUserId);
	}

	public void deleteMBStatsUser(long statsUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_mbStatsUserLocalService.deleteMBStatsUser(statsUserId);
	}

	public void deleteMBStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser)
		throws com.liferay.portal.SystemException {
		_mbStatsUserLocalService.deleteMBStatsUser(mbStatsUser);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _mbStatsUserLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _mbStatsUserLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser getMBStatsUser(
		long statsUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbStatsUserLocalService.getMBStatsUser(statsUserId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> getMBStatsUsers(
		int start, int end) throws com.liferay.portal.SystemException {
		return _mbStatsUserLocalService.getMBStatsUsers(start, end);
	}

	public int getMBStatsUsersCount() throws com.liferay.portal.SystemException {
		return _mbStatsUserLocalService.getMBStatsUsersCount();
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser updateMBStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser)
		throws com.liferay.portal.SystemException {
		return _mbStatsUserLocalService.updateMBStatsUser(mbStatsUser);
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser updateMBStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser,
		boolean merge) throws com.liferay.portal.SystemException {
		return _mbStatsUserLocalService.updateMBStatsUser(mbStatsUser, merge);
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser addStatsUser(
		long groupId, long userId) throws com.liferay.portal.SystemException {
		return _mbStatsUserLocalService.addStatsUser(groupId, userId);
	}

	public void deleteStatsUsersByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		_mbStatsUserLocalService.deleteStatsUsersByGroupId(groupId);
	}

	public void deleteStatsUsersByUserId(long userId)
		throws com.liferay.portal.SystemException {
		_mbStatsUserLocalService.deleteStatsUsersByUserId(userId);
	}

	public int getMessageCountByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return _mbStatsUserLocalService.getMessageCountByUserId(userId);
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser getStatsUser(
		long groupId, long userId) throws com.liferay.portal.SystemException {
		return _mbStatsUserLocalService.getStatsUser(groupId, userId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> getStatsUsersByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _mbStatsUserLocalService.getStatsUsersByGroupId(groupId, start,
			end);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> getStatsUsersByUserId(
		long userId) throws com.liferay.portal.SystemException {
		return _mbStatsUserLocalService.getStatsUsersByUserId(userId);
	}

	public int getStatsUsersByGroupIdCount(long groupId)
		throws com.liferay.portal.SystemException {
		return _mbStatsUserLocalService.getStatsUsersByGroupIdCount(groupId);
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser updateStatsUser(
		long groupId, long userId) throws com.liferay.portal.SystemException {
		return _mbStatsUserLocalService.updateStatsUser(groupId, userId);
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser updateStatsUser(
		long groupId, long userId, java.util.Date lastPostDate)
		throws com.liferay.portal.SystemException {
		return _mbStatsUserLocalService.updateStatsUser(groupId, userId,
			lastPostDate);
	}

	public MBStatsUserLocalService getWrappedMBStatsUserLocalService() {
		return _mbStatsUserLocalService;
	}

	private MBStatsUserLocalService _mbStatsUserLocalService;
}