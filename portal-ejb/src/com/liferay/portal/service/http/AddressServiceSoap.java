/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.service.http;

import com.liferay.portal.service.spring.AddressServiceUtil;
import com.liferay.portal.shared.util.StackTraceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;

/**
 * <a href="AddressServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AddressServiceSoap {
	public static com.liferay.portal.model.AddressModel addAddress(
		java.lang.String className, java.lang.String classPK,
		java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		java.lang.String regionId, java.lang.String countryId,
		java.lang.String typeId, boolean mailing, boolean primary)
		throws RemoteException {
		try {
			com.liferay.portal.model.Address returnValue = AddressServiceUtil.addAddress(className,
					classPK, street1, street2, street3, city, zip, regionId,
					countryId, typeId, mailing, primary);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void deleteAddress(java.lang.String addressId)
		throws RemoteException {
		try {
			AddressServiceUtil.deleteAddress(addressId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portal.model.AddressModel getAddress(
		java.lang.String addressId) throws RemoteException {
		try {
			com.liferay.portal.model.Address returnValue = AddressServiceUtil.getAddress(addressId);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portal.model.AddressModel[] getAddresses(
		java.lang.String className, java.lang.String classPK)
		throws RemoteException {
		try {
			java.util.List returnValue = AddressServiceUtil.getAddresses(className,
					classPK);

			return (com.liferay.portal.model.Address[])returnValue.toArray(new com.liferay.portal.model.Address[0]);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portal.model.AddressModel updateAddress(
		java.lang.String addressId, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, java.lang.String regionId,
		java.lang.String countryId, java.lang.String typeId, boolean mailing,
		boolean primary) throws RemoteException {
		try {
			com.liferay.portal.model.Address returnValue = AddressServiceUtil.updateAddress(addressId,
					street1, street2, street3, city, zip, regionId, countryId,
					typeId, mailing, primary);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	private static Log _log = LogFactory.getLog(AddressServiceSoap.class);
}