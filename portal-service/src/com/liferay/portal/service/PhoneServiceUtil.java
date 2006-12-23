/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
 * <a href="PhoneServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PhoneServiceUtil {
	public static com.liferay.portal.model.Phone addPhone(
		java.lang.String className, java.lang.String classPK,
		java.lang.String number, java.lang.String extension,
		java.lang.String typeId, boolean primary)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PhoneService phoneService = PhoneServiceFactory.getService();

		return phoneService.addPhone(className, classPK, number, extension,
			typeId, primary);
	}

	public static void deletePhone(long phoneId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PhoneService phoneService = PhoneServiceFactory.getService();
		phoneService.deletePhone(phoneId);
	}

	public static com.liferay.portal.model.Phone getPhone(long phoneId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PhoneService phoneService = PhoneServiceFactory.getService();

		return phoneService.getPhone(phoneId);
	}

	public static java.util.List getPhones(java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PhoneService phoneService = PhoneServiceFactory.getService();

		return phoneService.getPhones(className, classPK);
	}

	public static com.liferay.portal.model.Phone updatePhone(long phoneId,
		java.lang.String number, java.lang.String extension,
		java.lang.String typeId, boolean primary)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PhoneService phoneService = PhoneServiceFactory.getService();

		return phoneService.updatePhone(phoneId, number, extension, typeId,
			primary);
	}
}