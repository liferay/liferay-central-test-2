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

package com.liferay.portal.service;


/**
 * <a href="EmailAddressLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link EmailAddressLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       EmailAddressLocalService
 * @generated
 */
public class EmailAddressLocalServiceWrapper implements EmailAddressLocalService {
	public EmailAddressLocalServiceWrapper(
		EmailAddressLocalService emailAddressLocalService) {
		_emailAddressLocalService = emailAddressLocalService;
	}

	public com.liferay.portal.model.EmailAddress addEmailAddress(
		com.liferay.portal.model.EmailAddress emailAddress)
		throws com.liferay.portal.SystemException {
		return _emailAddressLocalService.addEmailAddress(emailAddress);
	}

	public com.liferay.portal.model.EmailAddress createEmailAddress(
		long emailAddressId) {
		return _emailAddressLocalService.createEmailAddress(emailAddressId);
	}

	public void deleteEmailAddress(long emailAddressId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_emailAddressLocalService.deleteEmailAddress(emailAddressId);
	}

	public void deleteEmailAddress(
		com.liferay.portal.model.EmailAddress emailAddress)
		throws com.liferay.portal.SystemException {
		_emailAddressLocalService.deleteEmailAddress(emailAddress);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _emailAddressLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _emailAddressLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.EmailAddress getEmailAddress(
		long emailAddressId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _emailAddressLocalService.getEmailAddress(emailAddressId);
	}

	public java.util.List<com.liferay.portal.model.EmailAddress> getEmailAddresses(
		int start, int end) throws com.liferay.portal.SystemException {
		return _emailAddressLocalService.getEmailAddresses(start, end);
	}

	public int getEmailAddressesCount()
		throws com.liferay.portal.SystemException {
		return _emailAddressLocalService.getEmailAddressesCount();
	}

	public com.liferay.portal.model.EmailAddress updateEmailAddress(
		com.liferay.portal.model.EmailAddress emailAddress)
		throws com.liferay.portal.SystemException {
		return _emailAddressLocalService.updateEmailAddress(emailAddress);
	}

	public com.liferay.portal.model.EmailAddress updateEmailAddress(
		com.liferay.portal.model.EmailAddress emailAddress, boolean merge)
		throws com.liferay.portal.SystemException {
		return _emailAddressLocalService.updateEmailAddress(emailAddress, merge);
	}

	public com.liferay.portal.model.EmailAddress addEmailAddress(long userId,
		java.lang.String className, long classPK, java.lang.String address,
		int typeId, boolean primary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _emailAddressLocalService.addEmailAddress(userId, className,
			classPK, address, typeId, primary);
	}

	public void deleteEmailAddresses(long companyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		_emailAddressLocalService.deleteEmailAddresses(companyId, className,
			classPK);
	}

	public java.util.List<com.liferay.portal.model.EmailAddress> getEmailAddresses()
		throws com.liferay.portal.SystemException {
		return _emailAddressLocalService.getEmailAddresses();
	}

	public java.util.List<com.liferay.portal.model.EmailAddress> getEmailAddresses(
		long companyId, java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return _emailAddressLocalService.getEmailAddresses(companyId,
			className, classPK);
	}

	public com.liferay.portal.model.EmailAddress updateEmailAddress(
		long emailAddressId, java.lang.String address, int typeId,
		boolean primary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _emailAddressLocalService.updateEmailAddress(emailAddressId,
			address, typeId, primary);
	}

	public EmailAddressLocalService getWrappedEmailAddressLocalService() {
		return _emailAddressLocalService;
	}

	private EmailAddressLocalService _emailAddressLocalService;
}