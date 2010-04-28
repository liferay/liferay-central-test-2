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
		throws com.liferay.portal.kernel.exception.SystemException {
		return _emailAddressLocalService.addEmailAddress(emailAddress);
	}

	public com.liferay.portal.model.EmailAddress createEmailAddress(
		long emailAddressId) {
		return _emailAddressLocalService.createEmailAddress(emailAddressId);
	}

	public void deleteEmailAddress(long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_emailAddressLocalService.deleteEmailAddress(emailAddressId);
	}

	public void deleteEmailAddress(
		com.liferay.portal.model.EmailAddress emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException {
		_emailAddressLocalService.deleteEmailAddress(emailAddress);
	}

	public java.util.List<com.liferay.portal.model.EmailAddress> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _emailAddressLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<com.liferay.portal.model.EmailAddress> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _emailAddressLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public java.util.List<com.liferay.portal.model.EmailAddress> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _emailAddressLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _emailAddressLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.EmailAddress getEmailAddress(
		long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _emailAddressLocalService.getEmailAddress(emailAddressId);
	}

	public java.util.List<com.liferay.portal.model.EmailAddress> getEmailAddresses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _emailAddressLocalService.getEmailAddresses(start, end);
	}

	public int getEmailAddressesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _emailAddressLocalService.getEmailAddressesCount();
	}

	public com.liferay.portal.model.EmailAddress updateEmailAddress(
		com.liferay.portal.model.EmailAddress emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _emailAddressLocalService.updateEmailAddress(emailAddress);
	}

	public com.liferay.portal.model.EmailAddress updateEmailAddress(
		com.liferay.portal.model.EmailAddress emailAddress, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _emailAddressLocalService.updateEmailAddress(emailAddress, merge);
	}

	public com.liferay.portal.model.EmailAddress addEmailAddress(long userId,
		java.lang.String className, long classPK, java.lang.String address,
		int typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _emailAddressLocalService.addEmailAddress(userId, className,
			classPK, address, typeId, primary);
	}

	public void deleteEmailAddresses(long companyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		_emailAddressLocalService.deleteEmailAddresses(companyId, className,
			classPK);
	}

	public java.util.List<com.liferay.portal.model.EmailAddress> getEmailAddresses()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _emailAddressLocalService.getEmailAddresses();
	}

	public java.util.List<com.liferay.portal.model.EmailAddress> getEmailAddresses(
		long companyId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _emailAddressLocalService.getEmailAddresses(companyId,
			className, classPK);
	}

	public com.liferay.portal.model.EmailAddress updateEmailAddress(
		long emailAddressId, java.lang.String address, int typeId,
		boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _emailAddressLocalService.updateEmailAddress(emailAddressId,
			address, typeId, primary);
	}

	public EmailAddressLocalService getWrappedEmailAddressLocalService() {
		return _emailAddressLocalService;
	}

	private EmailAddressLocalService _emailAddressLocalService;
}