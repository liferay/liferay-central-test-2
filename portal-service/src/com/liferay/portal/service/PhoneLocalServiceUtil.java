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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="PhoneLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link PhoneLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PhoneLocalService
 * @generated
 */
public class PhoneLocalServiceUtil {
	public static com.liferay.portal.model.Phone addPhone(
		com.liferay.portal.model.Phone phone)
		throws com.liferay.portal.SystemException {
		return getService().addPhone(phone);
	}

	public static com.liferay.portal.model.Phone createPhone(long phoneId) {
		return getService().createPhone(phoneId);
	}

	public static void deletePhone(long phoneId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deletePhone(phoneId);
	}

	public static void deletePhone(com.liferay.portal.model.Phone phone)
		throws com.liferay.portal.SystemException {
		getService().deletePhone(phone);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.Phone getPhone(long phoneId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getPhone(phoneId);
	}

	public static java.util.List<com.liferay.portal.model.Phone> getPhones(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getPhones(start, end);
	}

	public static int getPhonesCount()
		throws com.liferay.portal.SystemException {
		return getService().getPhonesCount();
	}

	public static com.liferay.portal.model.Phone updatePhone(
		com.liferay.portal.model.Phone phone)
		throws com.liferay.portal.SystemException {
		return getService().updatePhone(phone);
	}

	public static com.liferay.portal.model.Phone updatePhone(
		com.liferay.portal.model.Phone phone, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updatePhone(phone, merge);
	}

	public static com.liferay.portal.model.Phone addPhone(long userId,
		java.lang.String className, long classPK, java.lang.String number,
		java.lang.String extension, int typeId, boolean primary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addPhone(userId, className, classPK, number, extension,
			typeId, primary);
	}

	public static void deletePhones(long companyId, java.lang.String className,
		long classPK) throws com.liferay.portal.SystemException {
		getService().deletePhones(companyId, className, classPK);
	}

	public static java.util.List<com.liferay.portal.model.Phone> getPhones()
		throws com.liferay.portal.SystemException {
		return getService().getPhones();
	}

	public static java.util.List<com.liferay.portal.model.Phone> getPhones(
		long companyId, java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return getService().getPhones(companyId, className, classPK);
	}

	public static com.liferay.portal.model.Phone updatePhone(long phoneId,
		java.lang.String number, java.lang.String extension, int typeId,
		boolean primary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updatePhone(phoneId, number, extension, typeId, primary);
	}

	public static PhoneLocalService getService() {
		if (_service == null) {
			_service = (PhoneLocalService)PortalBeanLocatorUtil.locate(PhoneLocalService.class.getName());
		}

		return _service;
	}

	public void setService(PhoneLocalService service) {
		_service = service;
	}

	private static PhoneLocalService _service;
}