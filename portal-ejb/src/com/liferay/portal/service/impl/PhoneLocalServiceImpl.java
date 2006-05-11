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

import com.liferay.counter.service.spring.CounterServiceUtil;
import com.liferay.portal.PhoneNumberException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.PhoneUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.ListTypeServiceUtil;
import com.liferay.portal.service.spring.PhoneLocalService;
import com.liferay.util.Validator;
import com.liferay.util.format.PhoneNumberUtil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="PhoneLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PhoneLocalServiceImpl implements PhoneLocalService {

	public Phone addPhone(
			String userId, String className, String classPK, String number,
			String extension, String typeId, boolean primary)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);
		Date now = new Date();

		number = PhoneNumberUtil.strip(number);
		extension = PhoneNumberUtil.strip(extension);

		validate(
			null, user.getCompanyId(), className, classPK, number, typeId,
			primary);

		String phoneId = Long.toString(CounterServiceUtil.increment(
			Phone.class.getName()));

		Phone phone = PhoneUtil.create(phoneId);

		phone.setCompanyId(user.getCompanyId());
		phone.setUserId(user.getUserId());
		phone.setUserName(user.getFullName());
		phone.setCreateDate(now);
		phone.setModifiedDate(now);
		phone.setClassName(className);
		phone.setClassPK(classPK);
		phone.setNumber(number);
		phone.setExtension(extension);
		phone.setTypeId(typeId);
		phone.setPrimary(primary);

		PhoneUtil.update(phone);

		return phone;
	}

	public void deletePhone(String phoneId)
		throws PortalException, SystemException {

		PhoneUtil.remove(phoneId);
	}

	public void deletePhones(String companyId, String className, String classPK)
		throws SystemException {

		PhoneUtil.removeByC_C_C(companyId, className, classPK);
	}

	public Phone getPhone(String phoneId)
		throws PortalException, SystemException {

		return PhoneUtil.findByPrimaryKey(phoneId);
	}

	public List getPhones(String companyId, String className, String classPK)
		throws SystemException {

		return PhoneUtil.findByC_C_C(companyId, className, classPK);
	}

	public Phone updatePhone(
			String phoneId, String number, String extension, String typeId,
			boolean primary)
		throws PortalException, SystemException {

		number = PhoneNumberUtil.strip(number);
		extension = PhoneNumberUtil.strip(extension);

		validate(phoneId, null, null, null, number, typeId, primary);

		Phone phone = PhoneUtil.findByPrimaryKey(phoneId);

		phone.setModifiedDate(new Date());
		phone.setNumber(number);
		phone.setExtension(extension);
		phone.setTypeId(typeId);
		phone.setPrimary(primary);

		PhoneUtil.update(phone);

		return phone;
	}

	protected void validate(
			String phoneId, String companyId, String className, String classPK,
			String number, String typeId, boolean primary)
		throws PortalException, SystemException {

		if (Validator.isNull(number)) {
			throw new PhoneNumberException();
		}

		if (phoneId != null) {
			Phone phone = PhoneUtil.findByPrimaryKey(phoneId);

			companyId = phone.getCompanyId();
			className = phone.getClassName();
			classPK = phone.getClassPK();
		}

		ListTypeServiceUtil.validate(typeId, className + ListType.PHONE);

		validate(phoneId, companyId, className, classPK, primary);
	}

	protected void validate(
			String phoneId, String companyId, String className,
			String classPK, boolean primary)
		throws PortalException, SystemException {

		// Check to make sure there isn't another phone with the same company
		// id, class name, and class pk that also has primary set to true

		if (primary) {
			Iterator itr = PhoneUtil.findByC_C_C_P(
				companyId, className, classPK, primary).iterator();

			while (itr.hasNext()) {
				Phone phone = (Phone)itr.next();

				if ((phoneId == null) ||
					(!phone.getPhoneId().equals(phoneId))) {

					phone.setPrimary(false);

					PhoneUtil.update(phone);
				}
			}
		}
	}

}