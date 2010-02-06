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
 * <a href="MBMailingListLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link MBMailingListLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMailingListLocalService
 * @generated
 */
public class MBMailingListLocalServiceWrapper
	implements MBMailingListLocalService {
	public MBMailingListLocalServiceWrapper(
		MBMailingListLocalService mbMailingListLocalService) {
		_mbMailingListLocalService = mbMailingListLocalService;
	}

	public com.liferay.portlet.messageboards.model.MBMailingList addMBMailingList(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList)
		throws com.liferay.portal.SystemException {
		return _mbMailingListLocalService.addMBMailingList(mbMailingList);
	}

	public com.liferay.portlet.messageboards.model.MBMailingList createMBMailingList(
		long mailingListId) {
		return _mbMailingListLocalService.createMBMailingList(mailingListId);
	}

	public void deleteMBMailingList(long mailingListId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_mbMailingListLocalService.deleteMBMailingList(mailingListId);
	}

	public void deleteMBMailingList(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList)
		throws com.liferay.portal.SystemException {
		_mbMailingListLocalService.deleteMBMailingList(mbMailingList);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _mbMailingListLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _mbMailingListLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.messageboards.model.MBMailingList getMBMailingList(
		long mailingListId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMailingListLocalService.getMBMailingList(mailingListId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> getMBMailingLists(
		int start, int end) throws com.liferay.portal.SystemException {
		return _mbMailingListLocalService.getMBMailingLists(start, end);
	}

	public int getMBMailingListsCount()
		throws com.liferay.portal.SystemException {
		return _mbMailingListLocalService.getMBMailingListsCount();
	}

	public com.liferay.portlet.messageboards.model.MBMailingList updateMBMailingList(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList)
		throws com.liferay.portal.SystemException {
		return _mbMailingListLocalService.updateMBMailingList(mbMailingList);
	}

	public com.liferay.portlet.messageboards.model.MBMailingList updateMBMailingList(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList,
		boolean merge) throws com.liferay.portal.SystemException {
		return _mbMailingListLocalService.updateMBMailingList(mbMailingList,
			merge);
	}

	public com.liferay.portlet.messageboards.model.MBMailingList addMailingList(
		java.lang.String uuid, long userId, long groupId, long categoryId,
		java.lang.String emailAddress, java.lang.String inProtocol,
		java.lang.String inServerName, int inServerPort, boolean inUseSSL,
		java.lang.String inUserName, java.lang.String inPassword,
		int inReadInterval, java.lang.String outEmailAddress,
		boolean outCustom, java.lang.String outServerName, int outServerPort,
		boolean outUseSSL, java.lang.String outUserName,
		java.lang.String outPassword, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMailingListLocalService.addMailingList(uuid, userId, groupId,
			categoryId, emailAddress, inProtocol, inServerName, inServerPort,
			inUseSSL, inUserName, inPassword, inReadInterval, outEmailAddress,
			outCustom, outServerName, outServerPort, outUseSSL, outUserName,
			outPassword, active);
	}

	public void deleteCategoryMailingList(long groupId, long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_mbMailingListLocalService.deleteCategoryMailingList(groupId, categoryId);
	}

	public void deleteMailingList(long mailingListId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_mbMailingListLocalService.deleteMailingList(mailingListId);
	}

	public void deleteMailingList(
		com.liferay.portlet.messageboards.model.MBMailingList mailingList)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_mbMailingListLocalService.deleteMailingList(mailingList);
	}

	public com.liferay.portlet.messageboards.model.MBMailingList getCategoryMailingList(
		long groupId, long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMailingListLocalService.getCategoryMailingList(groupId,
			categoryId);
	}

	public com.liferay.portlet.messageboards.model.MBMailingList updateMailingList(
		long mailingListId, java.lang.String emailAddress,
		java.lang.String inProtocol, java.lang.String inServerName,
		int inServerPort, boolean inUseSSL, java.lang.String inUserName,
		java.lang.String inPassword, int inReadInterval,
		java.lang.String outEmailAddress, boolean outCustom,
		java.lang.String outServerName, int outServerPort, boolean outUseSSL,
		java.lang.String outUserName, java.lang.String outPassword,
		boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMailingListLocalService.updateMailingList(mailingListId,
			emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
			inUserName, inPassword, inReadInterval, outEmailAddress, outCustom,
			outServerName, outServerPort, outUseSSL, outUserName, outPassword,
			active);
	}

	public MBMailingListLocalService getWrappedMBMailingListLocalService() {
		return _mbMailingListLocalService;
	}

	private MBMailingListLocalService _mbMailingListLocalService;
}