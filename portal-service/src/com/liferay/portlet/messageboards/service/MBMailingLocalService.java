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
 * <a href="MBMailingLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.liferay.portlet.messageboards.service.impl.MBMailingLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.MBMailingLocalServiceUtil
 *
 */
public interface MBMailingLocalService {
	public com.liferay.portlet.messageboards.model.MBMailing addMBMailing(
		com.liferay.portlet.messageboards.model.MBMailing mbMailing)
		throws com.liferay.portal.SystemException;

	public void deleteMBMailing(long mailingId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteMBMailing(
		com.liferay.portlet.messageboards.model.MBMailing mbMailing)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMailing getMBMailing(
		long mailingId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMailing> getMBMailings(
		int start, int end) throws com.liferay.portal.SystemException;

	public int getMBMailingsCount() throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMailing updateMBMailing(
		com.liferay.portlet.messageboards.model.MBMailing mbMailing)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMailing addMailing(
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
			com.liferay.portal.SystemException;

	public void addMailingResources(
		com.liferay.portlet.messageboards.model.MBMailing mailing,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteMailing(long mailingId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteMailing(
		com.liferay.portlet.messageboards.model.MBMailing mailing)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMailing getMailingByCategory(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMailing> getMailings()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void startMailReader()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void stopMailReader()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMailing updateActive(
		long mailingId, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMailing updateMailing(
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
			com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMailing updateMailOutConfigured(
		long mailingId, boolean mailOutConfigured)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;
}