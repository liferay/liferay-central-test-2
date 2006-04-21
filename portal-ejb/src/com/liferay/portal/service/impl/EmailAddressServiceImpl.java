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

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.service.persistence.EmailAddressUtil;
import com.liferay.portal.service.spring.EmailAddressLocalServiceUtil;
import com.liferay.portal.service.spring.EmailAddressService;

import java.util.List;

/**
 * <a href="EmailAddressServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class EmailAddressServiceImpl
	extends PrincipalBean implements EmailAddressService {

	// Business methods

	public EmailAddress addEmailAddress(
			String className, String classPK, String address, String typeId,
			boolean primary)
		throws PortalException, SystemException {

		return EmailAddressLocalServiceUtil.addEmailAddress(
			getUserId(), className, classPK, address, typeId, primary);
	}

	public void deleteEmailAddress(String emailAddressId)
		throws PortalException, SystemException {

		EmailAddressLocalServiceUtil.deleteEmailAddress(emailAddressId);
	}

	public EmailAddress getEmailAddress(String emailAddressId)
		throws PortalException, SystemException {

		return EmailAddressUtil.findByPrimaryKey(emailAddressId);
	}

	public List getEmailAddresses(String className, String classPK)
		throws PortalException, SystemException {

		return EmailAddressLocalServiceUtil.getEmailAddresses(
			getUser().getCompanyId(), className, classPK);
	}

	public EmailAddress updateEmailAddress(
			String emailAddressId, String address, String typeId,
			boolean primary)
		throws PortalException, SystemException {

		return EmailAddressLocalServiceUtil.updateEmailAddress(
			emailAddressId, address, typeId, primary);
	}

}