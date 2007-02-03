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
 * <a href="EmailAddressLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EmailAddressLocalServiceUtil {
	public static com.liferay.portal.model.EmailAddress addEmailAddress(
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK, java.lang.String address, int typeId,
		boolean primary)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		EmailAddressLocalService emailAddressLocalService = EmailAddressLocalServiceFactory.getService();

		return emailAddressLocalService.addEmailAddress(userId, className,
			classPK, address, typeId, primary);
	}

	public static void deleteEmailAddress(long emailAddressId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		EmailAddressLocalService emailAddressLocalService = EmailAddressLocalServiceFactory.getService();
		emailAddressLocalService.deleteEmailAddress(emailAddressId);
	}

	public static void deleteEmailAddresses(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		EmailAddressLocalService emailAddressLocalService = EmailAddressLocalServiceFactory.getService();
		emailAddressLocalService.deleteEmailAddresses(companyId, className,
			classPK);
	}

	public static com.liferay.portal.model.EmailAddress getEmailAddress(
		long emailAddressId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		EmailAddressLocalService emailAddressLocalService = EmailAddressLocalServiceFactory.getService();

		return emailAddressLocalService.getEmailAddress(emailAddressId);
	}

	public static java.util.List getEmailAddresses()
		throws com.liferay.portal.SystemException {
		EmailAddressLocalService emailAddressLocalService = EmailAddressLocalServiceFactory.getService();

		return emailAddressLocalService.getEmailAddresses();
	}

	public static java.util.List getEmailAddresses(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		EmailAddressLocalService emailAddressLocalService = EmailAddressLocalServiceFactory.getService();

		return emailAddressLocalService.getEmailAddresses(companyId, className,
			classPK);
	}

	public static com.liferay.portal.model.EmailAddress updateEmailAddress(
		long emailAddressId, java.lang.String address, int typeId,
		boolean primary)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		EmailAddressLocalService emailAddressLocalService = EmailAddressLocalServiceFactory.getService();

		return emailAddressLocalService.updateEmailAddress(emailAddressId,
			address, typeId, primary);
	}
}