/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;


/**
 * <a href="PasswordPolicyLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PasswordPolicyLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordPolicyLocalService
 * @generated
 */
public class PasswordPolicyLocalServiceWrapper
	implements PasswordPolicyLocalService {
	public PasswordPolicyLocalServiceWrapper(
		PasswordPolicyLocalService passwordPolicyLocalService) {
		_passwordPolicyLocalService = passwordPolicyLocalService;
	}

	public com.liferay.portal.model.PasswordPolicy addPasswordPolicy(
		com.liferay.portal.model.PasswordPolicy passwordPolicy)
		throws com.liferay.portal.SystemException {
		return _passwordPolicyLocalService.addPasswordPolicy(passwordPolicy);
	}

	public com.liferay.portal.model.PasswordPolicy createPasswordPolicy(
		long passwordPolicyId) {
		return _passwordPolicyLocalService.createPasswordPolicy(passwordPolicyId);
	}

	public void deletePasswordPolicy(long passwordPolicyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_passwordPolicyLocalService.deletePasswordPolicy(passwordPolicyId);
	}

	public void deletePasswordPolicy(
		com.liferay.portal.model.PasswordPolicy passwordPolicy)
		throws com.liferay.portal.SystemException {
		_passwordPolicyLocalService.deletePasswordPolicy(passwordPolicy);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _passwordPolicyLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _passwordPolicyLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.PasswordPolicy getPasswordPolicy(
		long passwordPolicyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _passwordPolicyLocalService.getPasswordPolicy(passwordPolicyId);
	}

	public java.util.List<com.liferay.portal.model.PasswordPolicy> getPasswordPolicies(
		int start, int end) throws com.liferay.portal.SystemException {
		return _passwordPolicyLocalService.getPasswordPolicies(start, end);
	}

	public int getPasswordPoliciesCount()
		throws com.liferay.portal.SystemException {
		return _passwordPolicyLocalService.getPasswordPoliciesCount();
	}

	public com.liferay.portal.model.PasswordPolicy updatePasswordPolicy(
		com.liferay.portal.model.PasswordPolicy passwordPolicy)
		throws com.liferay.portal.SystemException {
		return _passwordPolicyLocalService.updatePasswordPolicy(passwordPolicy);
	}

	public com.liferay.portal.model.PasswordPolicy updatePasswordPolicy(
		com.liferay.portal.model.PasswordPolicy passwordPolicy, boolean merge)
		throws com.liferay.portal.SystemException {
		return _passwordPolicyLocalService.updatePasswordPolicy(passwordPolicy,
			merge);
	}

	public com.liferay.portal.model.PasswordPolicy addPasswordPolicy(
		long userId, boolean defaultPolicy, java.lang.String name,
		java.lang.String description, boolean changeable,
		boolean changeRequired, long minAge, boolean checkSyntax,
		boolean allowDictionaryWords, int minLength, boolean history,
		int historyCount, boolean expireable, long maxAge, long warningTime,
		int graceLimit, boolean lockout, int maxFailure, long lockoutDuration,
		long resetFailureCount)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _passwordPolicyLocalService.addPasswordPolicy(userId,
			defaultPolicy, name, description, changeable, changeRequired,
			minAge, checkSyntax, allowDictionaryWords, minLength, history,
			historyCount, expireable, maxAge, warningTime, graceLimit, lockout,
			maxFailure, lockoutDuration, resetFailureCount);
	}

	public void checkDefaultPasswordPolicy(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_passwordPolicyLocalService.checkDefaultPasswordPolicy(companyId);
	}

	public com.liferay.portal.model.PasswordPolicy getDefaultPasswordPolicy(
		long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _passwordPolicyLocalService.getDefaultPasswordPolicy(companyId);
	}

	public com.liferay.portal.model.PasswordPolicy getPasswordPolicy(
		long companyId, long organizationId, long locationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _passwordPolicyLocalService.getPasswordPolicy(companyId,
			organizationId, locationId);
	}

	public com.liferay.portal.model.PasswordPolicy getPasswordPolicy(
		long companyId, long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _passwordPolicyLocalService.getPasswordPolicy(companyId,
			organizationIds);
	}

	public com.liferay.portal.model.PasswordPolicy getPasswordPolicyByUserId(
		long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _passwordPolicyLocalService.getPasswordPolicyByUserId(userId);
	}

	public java.util.List<com.liferay.portal.model.PasswordPolicy> search(
		long companyId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _passwordPolicyLocalService.search(companyId, name, start, end,
			obc);
	}

	public int searchCount(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return _passwordPolicyLocalService.searchCount(companyId, name);
	}

	public com.liferay.portal.model.PasswordPolicy updatePasswordPolicy(
		long passwordPolicyId, java.lang.String name,
		java.lang.String description, boolean changeable,
		boolean changeRequired, long minAge, boolean checkSyntax,
		boolean allowDictionaryWords, int minLength, boolean history,
		int historyCount, boolean expireable, long maxAge, long warningTime,
		int graceLimit, boolean lockout, int maxFailure, long lockoutDuration,
		long resetFailureCount)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _passwordPolicyLocalService.updatePasswordPolicy(passwordPolicyId,
			name, description, changeable, changeRequired, minAge, checkSyntax,
			allowDictionaryWords, minLength, history, historyCount, expireable,
			maxAge, warningTime, graceLimit, lockout, maxFailure,
			lockoutDuration, resetFailureCount);
	}

	public PasswordPolicyLocalService getWrappedPasswordPolicyLocalService() {
		return _passwordPolicyLocalService;
	}

	private PasswordPolicyLocalService _passwordPolicyLocalService;
}