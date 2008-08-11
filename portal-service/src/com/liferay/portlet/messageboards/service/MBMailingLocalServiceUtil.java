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
 * <a href="MBMailingLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.messageboards.service.MBMailingLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.MBMailingLocalService
 *
 */
public class MBMailingLocalServiceUtil {
	public static com.liferay.portlet.messageboards.model.MBMailing addMBMailing(
		com.liferay.portlet.messageboards.model.MBMailing mbMailing)
		throws com.liferay.portal.SystemException {
		return _service.addMBMailing(mbMailing);
	}

	public static void deleteMBMailing(long mailingId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteMBMailing(mailingId);
	}

	public static void deleteMBMailing(
		com.liferay.portlet.messageboards.model.MBMailing mbMailing)
		throws com.liferay.portal.SystemException {
		_service.deleteMBMailing(mbMailing);
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

	public static com.liferay.portlet.messageboards.model.MBMailing getMBMailing(
		long mailingId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getMBMailing(mailingId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailing> getMBMailings(
		int start, int end) throws com.liferay.portal.SystemException {
		return _service.getMBMailings(start, end);
	}

	public static int getMBMailingsCount()
		throws com.liferay.portal.SystemException {
		return _service.getMBMailingsCount();
	}

	public static com.liferay.portlet.messageboards.model.MBMailing updateMBMailing(
		com.liferay.portlet.messageboards.model.MBMailing mbMailing)
		throws com.liferay.portal.SystemException {
		return _service.updateMBMailing(mbMailing);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing addMailing(
		long userId, long groupId, long categoryId,
		java.lang.String mailingListAddress, java.lang.String mailAddress,
		java.lang.String mailInProtocol, java.lang.String mailInServerName,
		boolean mailInUseSSL, int mailInServerPort,
		java.lang.String mailInUserName, java.lang.String mailInPassword,
		int mailInReadInterval, boolean mailOutConfigured,
		java.lang.String mailOutServerName, boolean mailOutUseSSL,
		int mailOutServerPort, java.lang.String mailOutUserName,
		java.lang.String mailOutPassword)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addMailing(userId, groupId, categoryId,
			mailingListAddress, mailAddress, mailInProtocol, mailInServerName,
			mailInUseSSL, mailInServerPort, mailInUserName, mailInPassword,
			mailInReadInterval, mailOutConfigured, mailOutServerName,
			mailOutUseSSL, mailOutServerPort, mailOutUserName, mailOutPassword);
	}

	public static void addMailingResources(
		com.liferay.portlet.messageboards.model.MBMailing mailing,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.addMailingResources(mailing, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void deleteMailing(long mailingId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteMailing(mailingId);
	}

	public static void deleteMailing(
		com.liferay.portlet.messageboards.model.MBMailing mailing)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteMailing(mailing);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing getMailingByCategory(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getMailingByCategory(categoryId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailing> getMailings()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getMailings();
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

	public static com.liferay.portlet.messageboards.model.MBMailing updateActive(
		long mailingId, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateActive(mailingId, active);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing updateMailing(
		long userId, long groupId, long categoryId,
		java.lang.String mailingListAddress, java.lang.String mailAddress,
		java.lang.String mailInProtocol, java.lang.String mailInServerName,
		boolean mailInUseSSL, int mailInServerPort,
		java.lang.String mailInUserName, java.lang.String mailInPassword,
		int mailInReadInterval, boolean mailOutConfigured,
		java.lang.String mailOutServerName, boolean mailOutUseSSL,
		int mailOutServerPort, java.lang.String mailOutUserName,
		java.lang.String mailOutPassword)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateMailing(userId, groupId, categoryId,
			mailingListAddress, mailAddress, mailInProtocol, mailInServerName,
			mailInUseSSL, mailInServerPort, mailInUserName, mailInPassword,
			mailInReadInterval, mailOutConfigured, mailOutServerName,
			mailOutUseSSL, mailOutServerPort, mailOutUserName, mailOutPassword);
	}

	public static com.liferay.portlet.messageboards.model.MBMailing updateMailOutConfigured(
		long mailingId, boolean mailOutConfigured)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateMailOutConfigured(mailingId, mailOutConfigured);
	}

	public static MBMailingLocalService getService() {
		return _service;
	}

	public void setService(MBMailingLocalService service) {
		_service = service;
	}

	private static MBMailingLocalService _service;
}