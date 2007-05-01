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

import com.liferay.portal.service.PasswordPolicyLocalService;
import com.liferay.portal.service.PasswordPolicyLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="PasswordPolicyLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portal.service.PasswordPolicyLocalService
 * @see com.liferay.portal.service.PasswordPolicyLocalServiceUtil
 * @see com.liferay.portal.service.ejb.PasswordPolicyLocalServiceEJB
 * @see com.liferay.portal.service.ejb.PasswordPolicyLocalServiceHome
 * @see com.liferay.portal.service.impl.PasswordPolicyLocalServiceImpl
 *
 */
public class PasswordPolicyLocalServiceEJBImpl
	implements PasswordPolicyLocalService, SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return PasswordPolicyLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return PasswordPolicyLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portal.model.PasswordPolicy addPolicy(long userId,
		java.lang.String name, java.lang.String description,
		java.lang.String storageScheme, boolean changeable,
		boolean changeRequired, long minAge, boolean checkSyntax,
		boolean allowDictionaryWords, int minLength, boolean history,
		int historyCount, boolean expireable, long maxAge, long warningTime,
		int graceLimit, boolean lockout, int maxFailure, long lockoutDuration,
		long resetFailureCount)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PasswordPolicyLocalServiceFactory.getTxImpl().addPolicy(userId,
			name, description, storageScheme, changeable, changeRequired,
			minAge, checkSyntax, allowDictionaryWords, minLength, history,
			historyCount, expireable, maxAge, warningTime, graceLimit, lockout,
			maxFailure, lockoutDuration, resetFailureCount);
	}

	public void deletePolicy(long passwordPolicyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PasswordPolicyLocalServiceFactory.getTxImpl().deletePolicy(passwordPolicyId);
	}

	public com.liferay.portal.model.PasswordPolicy getPolicy(
		long passwordPolicyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PasswordPolicyLocalServiceFactory.getTxImpl().getPolicy(passwordPolicyId);
	}

	public java.util.List search(long companyId, java.lang.String name,
		int begin, int end) throws com.liferay.portal.SystemException {
		return PasswordPolicyLocalServiceFactory.getTxImpl().search(companyId,
			name, begin, end);
	}

	public int searchCount(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return PasswordPolicyLocalServiceFactory.getTxImpl().searchCount(companyId,
			name);
	}

	public com.liferay.portal.model.PasswordPolicy updatePolicy(
		long passwordPolicyId, java.lang.String name,
		java.lang.String description, java.lang.String storageScheme,
		boolean changeable, boolean changeRequired, long minAge,
		boolean checkSyntax, boolean allowDictionaryWords, int minLength,
		boolean history, int historyCount, boolean expireable, long maxAge,
		long warningTime, int graceLimit, boolean lockout, int maxFailure,
		long lockoutDuration, long resetFailureCount)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PasswordPolicyLocalServiceFactory.getTxImpl().updatePolicy(passwordPolicyId,
			name, description, storageScheme, changeable, changeRequired,
			minAge, checkSyntax, allowDictionaryWords, minLength, history,
			historyCount, expireable, maxAge, warningTime, graceLimit, lockout,
			maxFailure, lockoutDuration, resetFailureCount);
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