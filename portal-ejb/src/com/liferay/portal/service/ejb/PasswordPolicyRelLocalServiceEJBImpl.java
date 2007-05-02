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

package com.liferay.portal.service.ejb;

import com.liferay.portal.service.PasswordPolicyRelLocalService;
import com.liferay.portal.service.PasswordPolicyRelLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="PasswordPolicyRelLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is the EJB implementation of the service that is used when Liferay
 * is run inside a full J2EE container.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.PasswordPolicyRelLocalService
 * @see com.liferay.portal.service.PasswordPolicyRelLocalServiceUtil
 * @see com.liferay.portal.service.ejb.PasswordPolicyRelLocalServiceEJB
 * @see com.liferay.portal.service.ejb.PasswordPolicyRelLocalServiceHome
 * @see com.liferay.portal.service.impl.PasswordPolicyRelLocalServiceImpl
 *
 */
public class PasswordPolicyRelLocalServiceEJBImpl
	implements PasswordPolicyRelLocalService, SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return PasswordPolicyRelLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return PasswordPolicyRelLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portal.model.PasswordPolicyRel addPasswordPolicyRel(
		long passwordPolicyId, java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PasswordPolicyRelLocalServiceFactory.getTxImpl()
												   .addPasswordPolicyRel(passwordPolicyId,
			className, classPK);
	}

	public void addPasswordPolicyRels(long passwordPolicyId,
		java.lang.String className, long[] classPKs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PasswordPolicyRelLocalServiceFactory.getTxImpl().addPasswordPolicyRels(passwordPolicyId,
			className, classPKs);
	}

	public void deletePasswordPolicyRel(java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PasswordPolicyRelLocalServiceFactory.getTxImpl()
											.deletePasswordPolicyRel(className,
			classPK);
	}

	public void deletePasswordPolicyRel(long passwordPolicyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PasswordPolicyRelLocalServiceFactory.getTxImpl()
											.deletePasswordPolicyRel(passwordPolicyId,
			className, classPK);
	}

	public void deletePasswordPolicyRels(long passwordPolicyId,
		java.lang.String className, long[] classPKs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PasswordPolicyRelLocalServiceFactory.getTxImpl()
											.deletePasswordPolicyRels(passwordPolicyId,
			className, classPKs);
	}

	public com.liferay.portal.model.PasswordPolicyRel getPasswordPolicyRel(
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PasswordPolicyRelLocalServiceFactory.getTxImpl()
												   .getPasswordPolicyRel(className,
			classPK);
	}

	public com.liferay.portal.model.PasswordPolicyRel getPasswordPolicyRel(
		long passwordPolicyId, java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PasswordPolicyRelLocalServiceFactory.getTxImpl()
												   .getPasswordPolicyRel(passwordPolicyId,
			className, classPK);
	}

	public boolean hasPasswordPolicyRel(long passwordPolicyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PasswordPolicyRelLocalServiceFactory.getTxImpl()
												   .hasPasswordPolicyRel(passwordPolicyId,
			className, classPK);
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