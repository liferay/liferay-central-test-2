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

package com.liferay.portal.service.ejb;

import com.liferay.portal.service.spring.PhoneLocalService;
import com.liferay.portal.spring.util.SpringUtil;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="PhoneLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PhoneLocalServiceEJBImpl implements PhoneLocalService, SessionBean {
	public static final String CLASS_NAME = PhoneLocalService.class.getName() +
		".transaction";

	public static PhoneLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (PhoneLocalService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portal.model.Phone addPhone(java.lang.String userId,
		java.lang.String className, java.lang.String classPK,
		java.lang.String number, java.lang.String extension,
		java.lang.String typeId, boolean primary)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addPhone(userId, className, classPK, number,
			extension, typeId, primary);
	}

	public void deletePhone(java.lang.String phoneId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deletePhone(phoneId);
	}

	public void deletePhones(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		getService().deletePhones(companyId, className, classPK);
	}

	public com.liferay.portal.model.Phone getPhone(java.lang.String phoneId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getPhone(phoneId);
	}

	public java.util.List getPhones(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getService().getPhones(companyId, className, classPK);
	}

	public com.liferay.portal.model.Phone updatePhone(
		java.lang.String phoneId, java.lang.String number,
		java.lang.String extension, java.lang.String typeId, boolean primary)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updatePhone(phoneId, number, extension, typeId,
			primary);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}