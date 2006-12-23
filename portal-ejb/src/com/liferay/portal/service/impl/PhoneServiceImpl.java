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

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Phone;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.PhoneLocalServiceUtil;
import com.liferay.portal.service.PhoneService;
import com.liferay.portal.service.permission.CommonPermission;
import com.liferay.portal.service.persistence.PhoneUtil;

import java.util.List;

/**
 * <a href="PhoneServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PhoneServiceImpl extends PrincipalBean implements PhoneService {

	public Phone addPhone(
			String className, String classPK, String number, String extension,
			int typeId, boolean primary)
		throws PortalException, SystemException {

		CommonPermission.checkPermission(
			getPermissionChecker(), className, classPK, ActionKeys.UPDATE);

		return PhoneLocalServiceUtil.addPhone(
			getUserId(), className, classPK, number, extension, typeId,
			primary);
	}

	public void deletePhone(long phoneId)
		throws PortalException, SystemException {

		Phone phone = PhoneUtil.findByPrimaryKey(phoneId);

		CommonPermission.checkPermission(
			getPermissionChecker(), phone.getClassName(), phone.getClassPK(),
			ActionKeys.UPDATE);

		PhoneLocalServiceUtil.deletePhone(phoneId);
	}

	public Phone getPhone(long phoneId)
		throws PortalException, SystemException {

		Phone phone = PhoneUtil.findByPrimaryKey(phoneId);

		CommonPermission.checkPermission(
			getPermissionChecker(), phone.getClassName(), phone.getClassPK(),
			ActionKeys.VIEW);

		return phone;
	}

	public List getPhones(String className, String classPK)
		throws PortalException, SystemException {

		CommonPermission.checkPermission(
			getPermissionChecker(), className, classPK, ActionKeys.VIEW);

		return PhoneLocalServiceUtil.getPhones(
			getUser().getCompanyId(), className, classPK);
	}

	public Phone updatePhone(
			long phoneId, String number, String extension, int typeId,
			boolean primary)
		throws PortalException, SystemException {

		Phone phone = PhoneUtil.findByPrimaryKey(phoneId);

		CommonPermission.checkPermission(
			getPermissionChecker(), phone.getClassName(), phone.getClassPK(),
			ActionKeys.UPDATE);

		return PhoneLocalServiceUtil.updatePhone(
			phoneId, number, extension, typeId, primary);
	}

}