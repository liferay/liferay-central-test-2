/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portal.service.PhoneLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.PhoneLocalServiceFactory</code> is responsible
 * for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.PhoneLocalService
 * @see com.liferay.portal.service.PhoneLocalServiceFactory
 *
 */
public class PhoneLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		PhoneLocalService phoneLocalService = PhoneLocalServiceFactory.getService();

		return phoneLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		PhoneLocalService phoneLocalService = PhoneLocalServiceFactory.getService();

		return phoneLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portal.model.Phone addPhone(long userId,
		java.lang.String className, java.lang.String classPK,
		java.lang.String number, java.lang.String extension, int typeId,
		boolean primary)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PhoneLocalService phoneLocalService = PhoneLocalServiceFactory.getService();

		return phoneLocalService.addPhone(userId, className, classPK, number,
			extension, typeId, primary);
	}

	public static void deletePhone(long phoneId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PhoneLocalService phoneLocalService = PhoneLocalServiceFactory.getService();
		phoneLocalService.deletePhone(phoneId);
	}

	public static void deletePhones(long companyId, java.lang.String className,
		java.lang.String classPK) throws com.liferay.portal.SystemException {
		PhoneLocalService phoneLocalService = PhoneLocalServiceFactory.getService();
		phoneLocalService.deletePhones(companyId, className, classPK);
	}

	public static com.liferay.portal.model.Phone getPhone(long phoneId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PhoneLocalService phoneLocalService = PhoneLocalServiceFactory.getService();

		return phoneLocalService.getPhone(phoneId);
	}

	public static java.util.List getPhones()
		throws com.liferay.portal.SystemException {
		PhoneLocalService phoneLocalService = PhoneLocalServiceFactory.getService();

		return phoneLocalService.getPhones();
	}

	public static java.util.List getPhones(long companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		PhoneLocalService phoneLocalService = PhoneLocalServiceFactory.getService();

		return phoneLocalService.getPhones(companyId, className, classPK);
	}

	public static com.liferay.portal.model.Phone updatePhone(long phoneId,
		java.lang.String number, java.lang.String extension, int typeId,
		boolean primary)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PhoneLocalService phoneLocalService = PhoneLocalServiceFactory.getService();

		return phoneLocalService.updatePhone(phoneId, number, extension,
			typeId, primary);
	}
}