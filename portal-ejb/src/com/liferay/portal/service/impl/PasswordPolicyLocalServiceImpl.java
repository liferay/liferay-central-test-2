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

package com.liferay.portal.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.User;
import com.liferay.portal.service.base.PasswordPolicyLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.PasswordPolicyFinder;
import com.liferay.portal.service.persistence.PasswordPolicyUtil;
import com.liferay.portal.service.persistence.UserUtil;

import java.util.Date;
import java.util.List;

/**
 * <a href="PasswordPolicyLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Scott Lee
 *
 */
public class PasswordPolicyLocalServiceImpl
	extends PasswordPolicyLocalServiceBaseImpl {

	public PasswordPolicy addPolicy(
			long userId, String name, String description, boolean changeable,
			boolean changeRequired, int minAge, String storageScheme,
			boolean checkSyntax, boolean allowDictionaryWords, int minLength,
			boolean history, int historyCount, boolean expireable, int maxAge,
			int warningTime, int graceLimit, boolean lockout, int maxFailure,
			boolean requireUnlock, int lockoutDuration, int resetFailureCount)
		throws PortalException, SystemException {

		// Password policy

		User user = UserUtil.findByPrimaryKey(userId);
		Date now = new Date();

		validate(user.getCompanyId(), name);

		long passwordPolicyId = CounterLocalServiceUtil.increment(
			PasswordPolicy.class.getName());

		PasswordPolicy passwordPolicy = PasswordPolicyUtil.create(
			passwordPolicyId);

		passwordPolicy.setUserId(userId);
		passwordPolicy.setCompanyId(user.getCompanyId());
		passwordPolicy.setUserName(user.getFullName());
		passwordPolicy.setCreateDate(now);
		passwordPolicy.setModifiedDate(now);
		passwordPolicy.setName(name);
		passwordPolicy.setDescription(description);
		passwordPolicy.setChangeable(changeable);
		passwordPolicy.setChangeRequired(changeRequired);
		passwordPolicy.setMinAge(minAge);
		passwordPolicy.setStorageScheme(storageScheme);
		passwordPolicy.setCheckSyntax(checkSyntax);
		passwordPolicy.setAllowDictionaryWords(allowDictionaryWords);
		passwordPolicy.setMinLength(minLength);
		passwordPolicy.setHistory(history);
		passwordPolicy.setHistoryCount(historyCount);
		passwordPolicy.setExpireable(expireable);
		passwordPolicy.setMaxAge(maxAge);
		passwordPolicy.setWarningTime(warningTime);
		passwordPolicy.setGraceLimit(graceLimit);
		passwordPolicy.setLockout(lockout);
		passwordPolicy.setMaxFailure(maxFailure);
		passwordPolicy.setRequireUnlock(requireUnlock);
		passwordPolicy.setLockoutDuration(lockoutDuration);
		passwordPolicy.setResetFailureCount(resetFailureCount);

		PasswordPolicyUtil.update(passwordPolicy);

		// Resources

		return passwordPolicy;
	}

	public void deletePolicy(long passwordPolicyId)
		throws PortalException, SystemException {

		// Resources

		// Password policy

		PasswordPolicyUtil.remove(passwordPolicyId);
	}

	public PasswordPolicy getPolicy(long passwordPolicyId)
		throws PortalException, SystemException {

		return PasswordPolicyUtil.findByPrimaryKey(passwordPolicyId);
	}

	public List search(long companyId, String name, int begin, int end)
		throws SystemException {

		return PasswordPolicyFinder.findByC_N(companyId, name, begin, end);
	}

	public int searchCount(long companyId, String name)
		throws SystemException {

		return PasswordPolicyFinder.countByC_N(companyId, name);
	}

	public PasswordPolicy updatePolicy(
			long passwordPolicyId, String name, String description,
			boolean changeable, boolean changeRequired, int minAge,
			String storageScheme, boolean checkSyntax,
			boolean allowDictionaryWords, int minLength, boolean history,
			int historyCount, boolean expireable, int maxAge, int warningTime,
			int graceLimit, boolean lockout, int maxFailure,
			boolean requireUnlock, int lockoutDuration, int resetFailureCount)
		throws PortalException, SystemException {

		Date now = new Date();

		PasswordPolicy passwordPolicy = PasswordPolicyUtil.findByPrimaryKey(
			passwordPolicyId);

		validate(passwordPolicy.getCompanyId(), name);

		passwordPolicy.setModifiedDate(now);
		passwordPolicy.setName(name);
		passwordPolicy.setDescription(description);
		passwordPolicy.setChangeable(changeable);
		passwordPolicy.setChangeRequired(changeRequired);
		passwordPolicy.setMinAge(minAge);
		passwordPolicy.setStorageScheme(storageScheme);
		passwordPolicy.setCheckSyntax(checkSyntax);
		passwordPolicy.setAllowDictionaryWords(allowDictionaryWords);
		passwordPolicy.setMinLength(minLength);
		passwordPolicy.setHistory(history);
		passwordPolicy.setHistoryCount(historyCount);
		passwordPolicy.setExpireable(expireable);
		passwordPolicy.setMaxAge(maxAge);
		passwordPolicy.setWarningTime(warningTime);
		passwordPolicy.setGraceLimit(graceLimit);
		passwordPolicy.setLockout(lockout);
		passwordPolicy.setMaxFailure(maxFailure);
		passwordPolicy.setRequireUnlock(requireUnlock);
		passwordPolicy.setLockoutDuration(lockoutDuration);
		passwordPolicy.setResetFailureCount(resetFailureCount);

		PasswordPolicyUtil.update(passwordPolicy);

		return passwordPolicy;
	}

	protected void validate(long companyId, String name)
		throws PortalException, SystemException {
	}

}