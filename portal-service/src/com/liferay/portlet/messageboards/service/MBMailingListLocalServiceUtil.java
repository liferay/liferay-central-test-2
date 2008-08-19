/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * This class provides static methods for the
 * <code>com.liferay.portlet.messageboards.service.MBMailingListLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.MBMailingListLocalService
 *
 */
public class MBMailingListLocalServiceUtil {
	public static com.liferay.portlet.messageboards.model.MBMailingList addMBMailingList(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList)
		throws com.liferay.portal.SystemException {
		return _service.addMBMailingList(mbMailingList);
	}

	public static void deleteMBMailingList(long mailingListId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteMBMailingList(mailingListId);
	}

	public static void deleteMBMailingList(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList)
		throws com.liferay.portal.SystemException {
		_service.deleteMBMailingList(mbMailingList);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _service.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _service.dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList getMBMailingList(
		long mailingListId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getMBMailingList(mailingListId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> getMBMailingLists(
		int start, int end) throws com.liferay.portal.SystemException {
		return _service.getMBMailingLists(start, end);
	}

	public static int getMBMailingListsCount()
		throws com.liferay.portal.SystemException {
		return _service.getMBMailingListsCount();
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList updateMBMailingList(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList)
		throws com.liferay.portal.SystemException {
		return _service.updateMBMailingList(mbMailingList);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList addMailing(
		long userId, long groupId, long categoryId,
		java.lang.String emailAddress, java.lang.String inProtocol,
		java.lang.String inServerName, boolean inUseSSL, int inServerPort,
		java.lang.String inUserName, java.lang.String inPassword,
		int inReadInterval, boolean outCustom,
		java.lang.String outEmailAddress, java.lang.String outServerName,
		boolean outUseSSL, int outServerPort, java.lang.String outUserName,
		java.lang.String outPassword)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addMailing(userId, groupId, categoryId, emailAddress,
			inProtocol, inServerName, inUseSSL, inServerPort, inUserName,
			inPassword, inReadInterval, outCustom, outEmailAddress,
			outServerName, outUseSSL, outServerPort, outUserName, outPassword);
	}

	public static void addMailingListResources(
		com.liferay.portlet.messageboards.model.MBMailingList mailingList,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.addMailingListResources(mailingList, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void deleteMailingList(long mailingListId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteMailingList(mailingListId);
	}

	public static void deleteMailingList(
		com.liferay.portlet.messageboards.model.MBMailingList mailingList)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteMailingList(mailingList);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList getMailingListByCategory(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getMailingListByCategory(categoryId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> getMailingLists()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getMailingLists();
	}

	public static void startMailReader()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.startMailReader();
	}

	public static void stopMailReader()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.stopMailReader();
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList updateActive(
		long mailingListId, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateActive(mailingListId, active);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList updateMailingList(
		long userId, long groupId, long categoryId,
		java.lang.String emailAddress, java.lang.String inProtocol,
		java.lang.String inServerName, boolean inUseSSL, int inServerPort,
		java.lang.String inUserName, java.lang.String inPassword,
		int inReadInterval, java.lang.String outEmailAddress,
		boolean outCustom, java.lang.String outServerName, boolean outUseSSL,
		int outServerPort, java.lang.String outUserName,
		java.lang.String outPassword)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateMailingList(userId, groupId, categoryId,
			emailAddress, inProtocol, inServerName, inUseSSL, inServerPort,
			inUserName, inPassword, inReadInterval, outEmailAddress, outCustom,
			outServerName, outUseSSL, outServerPort, outUserName, outPassword);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList updateOutCustom(
		long mailingListId, boolean outCustom)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateOutCustom(mailingListId, outCustom);
	}

	public static MBMailingListLocalService getService() {
		return _service;
	}

	public void setService(MBMailingListLocalService service) {
		_service = service;
	}

	private static MBMailingListLocalService _service;
}