/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.PasswordPolicyServiceUtil;

import java.rmi.RemoteException;

public class PasswordPolicyServiceSoap {
	public static com.liferay.portal.model.PasswordPolicySoap addPasswordPolicy(
		java.lang.String name, java.lang.String description,
		boolean changeable, boolean changeRequired, long minAge,
		boolean checkSyntax, boolean allowDictionaryWords, int minLength,
		boolean history, int historyCount, boolean expireable, long maxAge,
		long warningTime, int graceLimit, boolean lockout, int maxFailure,
		long lockoutDuration, long resetFailureCount) throws RemoteException {
		try {
			com.liferay.portal.model.PasswordPolicy returnValue = PasswordPolicyServiceUtil.addPasswordPolicy(name,
					description, changeable, changeRequired, minAge,
					checkSyntax, allowDictionaryWords, minLength, history,
					historyCount, expireable, maxAge, warningTime, graceLimit,
					lockout, maxFailure, lockoutDuration, resetFailureCount);

			return com.liferay.portal.model.PasswordPolicySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deletePasswordPolicy(long passwordPolicyId)
		throws RemoteException {
		try {
			PasswordPolicyServiceUtil.deletePasswordPolicy(passwordPolicyId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.PasswordPolicySoap updatePasswordPolicy(
		long passwordPolicyId, java.lang.String name,
		java.lang.String description, boolean changeable,
		boolean changeRequired, long minAge, boolean checkSyntax,
		boolean allowDictionaryWords, int minLength, boolean history,
		int historyCount, boolean expireable, long maxAge, long warningTime,
		int graceLimit, boolean lockout, int maxFailure, long lockoutDuration,
		long resetFailureCount) throws RemoteException {
		try {
			com.liferay.portal.model.PasswordPolicy returnValue = PasswordPolicyServiceUtil.updatePasswordPolicy(passwordPolicyId,
					name, description, changeable, changeRequired, minAge,
					checkSyntax, allowDictionaryWords, minLength, history,
					historyCount, expireable, maxAge, warningTime, graceLimit,
					lockout, maxFailure, lockoutDuration, resetFailureCount);

			return com.liferay.portal.model.PasswordPolicySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PasswordPolicyServiceSoap.class);
}