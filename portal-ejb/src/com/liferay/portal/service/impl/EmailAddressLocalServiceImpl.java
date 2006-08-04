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

import com.liferay.counter.service.spring.CounterLocalServiceUtil;
import com.liferay.portal.EmailAddressException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.EmailAddressUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.EmailAddressLocalService;
import com.liferay.portal.service.spring.ListTypeServiceUtil;
import com.liferay.util.Validator;

import java.rmi.RemoteException;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="EmailAddressLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class EmailAddressLocalServiceImpl implements EmailAddressLocalService {

	public EmailAddress addEmailAddress(
			String userId, String className, String classPK,
			String address, String typeId, boolean primary)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);
		Date now = new Date();

		validate(
			null, user.getCompanyId(), className, classPK, address, typeId,
			primary);

		String emailAddressId = Long.toString(CounterLocalServiceUtil.increment(
			EmailAddress.class.getName()));

		EmailAddress emailAddress = EmailAddressUtil.create(emailAddressId);

		emailAddress.setCompanyId(user.getCompanyId());
		emailAddress.setUserId(user.getUserId());
		emailAddress.setUserName(user.getFullName());
		emailAddress.setCreateDate(now);
		emailAddress.setModifiedDate(now);
		emailAddress.setClassName(className);
		emailAddress.setClassPK(classPK);
		emailAddress.setAddress(address);
		emailAddress.setTypeId(typeId);
		emailAddress.setPrimary(primary);

		EmailAddressUtil.update(emailAddress);

		return emailAddress;
	}

	public void deleteEmailAddress(String emailAddressId)
		throws PortalException, SystemException {

		EmailAddressUtil.remove(emailAddressId);
	}

	public void deleteEmailAddresses(
			String companyId, String className, String classPK)
		throws SystemException {

		EmailAddressUtil.removeByC_C_C(companyId, className, classPK);
	}

	public EmailAddress getEmailAddress(String emailAddressId)
		throws PortalException, SystemException {

		return EmailAddressUtil.findByPrimaryKey(emailAddressId);
	}

	public List getEmailAddresses(
			String companyId, String className, String classPK)
		throws SystemException {

		return EmailAddressUtil.findByC_C_C(companyId, className, classPK);
	}

	public EmailAddress updateEmailAddress(
			String emailAddressId, String address, String typeId,
			boolean primary)
		throws PortalException, SystemException {

		validate(emailAddressId, null, null, null, address, typeId, primary);

		EmailAddress emailAddress =
			EmailAddressUtil.findByPrimaryKey(emailAddressId);

		emailAddress.setModifiedDate(new Date());
		emailAddress.setAddress(address);
		emailAddress.setTypeId(typeId);
		emailAddress.setPrimary(primary);

		EmailAddressUtil.update(emailAddress);

		return emailAddress;
	}

	protected void validate(
			String emailAddressId, String companyId, String className,
			String classPK, String address, String typeId, boolean primary)
		throws PortalException, SystemException {

		if (!Validator.isEmailAddress(address)) {
			throw new EmailAddressException();
		}

		if (emailAddressId != null) {
			EmailAddress emailAddress =
				EmailAddressUtil.findByPrimaryKey(emailAddressId);

			companyId = emailAddress.getCompanyId();
			className = emailAddress.getClassName();
			classPK = emailAddress.getClassPK();
		}

		try {
			ListTypeServiceUtil.validate(
				typeId, className + ListType.EMAIL_ADDRESS);
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}

		validate(emailAddressId, companyId, className, classPK, primary);
	}

	protected void validate(
			String emailAddressId, String companyId, String className,
			String classPK, boolean primary)
		throws PortalException, SystemException {

		// Check to make sure there isn't another emailAddress with the same
		// company id, class name, and class pk that also has primary set to
		// true

		if (primary) {
			Iterator itr = EmailAddressUtil.findByC_C_C_P(
				companyId, className, classPK, primary).iterator();

			while (itr.hasNext()) {
				EmailAddress emailAddress = (EmailAddress)itr.next();

				if ((emailAddressId == null) ||
					(!emailAddress.getEmailAddressId().equals(
						emailAddressId))) {

					emailAddress.setPrimary(false);

					EmailAddressUtil.update(emailAddress);
				}
			}
		}
	}

}