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

import com.liferay.portal.service.EmailAddressServiceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <a href="EmailAddressServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EmailAddressServiceJSON {
	public static JSONObject addEmailAddress(java.lang.String className,
		java.lang.String classPK, java.lang.String address, int typeId,
		boolean primary)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.EmailAddress returnValue = EmailAddressServiceUtil.addEmailAddress(className,
				classPK, address, typeId, primary);

		return EmailAddressJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteEmailAddress(long emailAddressId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		EmailAddressServiceUtil.deleteEmailAddress(emailAddressId);
	}

	public static JSONObject getEmailAddress(long emailAddressId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.EmailAddress returnValue = EmailAddressServiceUtil.getEmailAddress(emailAddressId);

		return EmailAddressJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONArray getEmailAddresses(java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		java.util.List returnValue = EmailAddressServiceUtil.getEmailAddresses(className,
				classPK);

		return EmailAddressJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONObject updateEmailAddress(long emailAddressId,
		java.lang.String address, int typeId, boolean primary)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.EmailAddress returnValue = EmailAddressServiceUtil.updateEmailAddress(emailAddressId,
				address, typeId, primary);

		return EmailAddressJSONSerializer.toJSONObject(returnValue);
	}
}