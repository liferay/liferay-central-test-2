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

import com.liferay.portal.service.PasswordPolicyService;
import com.liferay.portal.service.PasswordPolicyServiceFactory;
import com.liferay.portal.service.impl.PrincipalSessionBean;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="PasswordPolicyServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portal.service.PasswordPolicyService
 * @see com.liferay.portal.service.PasswordPolicyServiceUtil
 * @see com.liferay.portal.service.ejb.PasswordPolicyServiceEJB
 * @see com.liferay.portal.service.ejb.PasswordPolicyServiceHome
 * @see com.liferay.portal.service.impl.PasswordPolicyServiceImpl
 *
 */
public class PasswordPolicyServiceEJBImpl implements PasswordPolicyService,
	SessionBean {
	public com.liferay.portal.model.PasswordPolicy addPolicy(
		java.lang.String name, java.lang.String description,
		boolean changeable, boolean changeRequired, int minAge,
		java.lang.String storageScheme, boolean checkSyntax,
		boolean allowDictionaryWords, int minLength, boolean history,
		int historyCount, boolean expireable, int maxAge, int warningTime,
		int graceLimit, boolean lockout, int maxFailure, boolean requireUnlock,
		int lockoutDuration, int resetFailureCount)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return PasswordPolicyServiceFactory.getTxImpl().addPolicy(name,
			description, changeable, changeRequired, minAge, storageScheme,
			checkSyntax, allowDictionaryWords, minLength, history,
			historyCount, expireable, maxAge, warningTime, graceLimit, lockout,
			maxFailure, requireUnlock, lockoutDuration, resetFailureCount);
	}

	public void deletePolicy(long passwordPolicyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		PasswordPolicyServiceFactory.getTxImpl().deletePolicy(passwordPolicyId);
	}

	public com.liferay.portal.model.PasswordPolicy updatePolicy(
		long passwordPolicyId, java.lang.String name,
		java.lang.String description, boolean changeable,
		boolean changeRequired, int minAge, java.lang.String storageScheme,
		boolean checkSyntax, boolean allowDictionaryWords, int minLength,
		boolean history, int historyCount, boolean expireable, int maxAge,
		int warningTime, int graceLimit, boolean lockout, int maxFailure,
		boolean requireUnlock, int lockoutDuration, int resetFailureCount)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return PasswordPolicyServiceFactory.getTxImpl().updatePolicy(passwordPolicyId,
			name, description, changeable, changeRequired, minAge,
			storageScheme, checkSyntax, allowDictionaryWords, minLength,
			history, historyCount, expireable, maxAge, warningTime, graceLimit,
			lockout, maxFailure, requireUnlock, lockoutDuration,
			resetFailureCount);
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