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