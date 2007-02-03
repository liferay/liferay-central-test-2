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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.EmailAddressServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="EmailAddressServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EmailAddressServiceSoap {
	public static com.liferay.portal.model.EmailAddressSoap addEmailAddress(
		java.lang.String className, java.lang.String classPK,
		java.lang.String address, int typeId, boolean primary)
		throws RemoteException {
		try {
			com.liferay.portal.model.EmailAddress returnValue = EmailAddressServiceUtil.addEmailAddress(className,
					classPK, address, typeId, primary);

			return com.liferay.portal.model.EmailAddressSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteEmailAddress(long emailAddressId)
		throws RemoteException {
		try {
			EmailAddressServiceUtil.deleteEmailAddress(emailAddressId);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.EmailAddressSoap getEmailAddress(
		long emailAddressId) throws RemoteException {
		try {
			com.liferay.portal.model.EmailAddress returnValue = EmailAddressServiceUtil.getEmailAddress(emailAddressId);

			return com.liferay.portal.model.EmailAddressSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.EmailAddressSoap[] getEmailAddresses(
		java.lang.String className, java.lang.String classPK)
		throws RemoteException {
		try {
			java.util.List returnValue = EmailAddressServiceUtil.getEmailAddresses(className,
					classPK);

			return com.liferay.portal.model.EmailAddressSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.EmailAddressSoap updateEmailAddress(
		long emailAddressId, java.lang.String address, int typeId,
		boolean primary) throws RemoteException {
		try {
			com.liferay.portal.model.EmailAddress returnValue = EmailAddressServiceUtil.updateEmailAddress(emailAddressId,
					address, typeId, primary);

			return com.liferay.portal.model.EmailAddressSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(EmailAddressServiceSoap.class);
}