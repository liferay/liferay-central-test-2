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
 * <a href="PhoneLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PhoneLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PhoneLocalService
 * @generated
 */
public class PhoneLocalServiceWrapper implements PhoneLocalService {
	public PhoneLocalServiceWrapper(PhoneLocalService phoneLocalService) {
		_phoneLocalService = phoneLocalService;
	}

	public com.liferay.portal.model.Phone addPhone(
		com.liferay.portal.model.Phone phone)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _phoneLocalService.addPhone(phone);
	}

	public com.liferay.portal.model.Phone createPhone(long phoneId) {
		return _phoneLocalService.createPhone(phoneId);
	}

	public void deletePhone(long phoneId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_phoneLocalService.deletePhone(phoneId);
	}

	public void deletePhone(com.liferay.portal.model.Phone phone)
		throws com.liferay.portal.kernel.exception.SystemException {
		_phoneLocalService.deletePhone(phone);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _phoneLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _phoneLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _phoneLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _phoneLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.Phone getPhone(long phoneId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _phoneLocalService.getPhone(phoneId);
	}

	public java.util.List<com.liferay.portal.model.Phone> getPhones(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _phoneLocalService.getPhones(start, end);
	}

	public int getPhonesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _phoneLocalService.getPhonesCount();
	}

	public com.liferay.portal.model.Phone updatePhone(
		com.liferay.portal.model.Phone phone)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _phoneLocalService.updatePhone(phone);
	}

	public com.liferay.portal.model.Phone updatePhone(
		com.liferay.portal.model.Phone phone, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _phoneLocalService.updatePhone(phone, merge);
	}

	public com.liferay.portal.model.Phone addPhone(long userId,
		java.lang.String className, long classPK, java.lang.String number,
		java.lang.String extension, int typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _phoneLocalService.addPhone(userId, className, classPK, number,
			extension, typeId, primary);
	}

	public void deletePhones(long companyId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		_phoneLocalService.deletePhones(companyId, className, classPK);
	}

	public java.util.List<com.liferay.portal.model.Phone> getPhones()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _phoneLocalService.getPhones();
	}

	public java.util.List<com.liferay.portal.model.Phone> getPhones(
		long companyId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _phoneLocalService.getPhones(companyId, className, classPK);
	}

	public com.liferay.portal.model.Phone updatePhone(long phoneId,
		java.lang.String number, java.lang.String extension, int typeId,
		boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _phoneLocalService.updatePhone(phoneId, number, extension,
			typeId, primary);
	}

	public PhoneLocalService getWrappedPhoneLocalService() {
		return _phoneLocalService;
	}

	private PhoneLocalService _phoneLocalService;
}