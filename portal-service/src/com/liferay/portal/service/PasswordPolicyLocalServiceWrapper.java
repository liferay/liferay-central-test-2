/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyLocalService.addPasswordPolicy(passwordPolicy);
	}

	public com.liferay.portal.model.PasswordPolicy createPasswordPolicy(
		long passwordPolicyId) {
		return _passwordPolicyLocalService.createPasswordPolicy(passwordPolicyId);
	}

	public void deletePasswordPolicy(long passwordPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_passwordPolicyLocalService.deletePasswordPolicy(passwordPolicyId);
	}

	public void deletePasswordPolicy(
		com.liferay.portal.model.PasswordPolicy passwordPolicy)
		throws com.liferay.portal.kernel.exception.SystemException {
		_passwordPolicyLocalService.deletePasswordPolicy(passwordPolicy);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.PasswordPolicy getPasswordPolicy(
		long passwordPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyLocalService.getPasswordPolicy(passwordPolicyId);
	}

	public java.util.List<com.liferay.portal.model.PasswordPolicy> getPasswordPolicies(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyLocalService.getPasswordPolicies(start, end);
	}

	public int getPasswordPoliciesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyLocalService.getPasswordPoliciesCount();
	}

	public com.liferay.portal.model.PasswordPolicy updatePasswordPolicy(
		com.liferay.portal.model.PasswordPolicy passwordPolicy)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyLocalService.updatePasswordPolicy(passwordPolicy);
	}

	public com.liferay.portal.model.PasswordPolicy updatePasswordPolicy(
		com.liferay.portal.model.PasswordPolicy passwordPolicy, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyLocalService.updatePasswordPolicy(passwordPolicy,
			merge);
	}

	public com.liferay.portal.model.PasswordPolicy addPasswordPolicy(
		long userId, boolean defaultPolicy, java.lang.String name,
		java.lang.String description, boolean changeable,
		boolean changeRequired, long minAge, boolean checkSyntax,
		boolean allowDictionaryWords, int minAlphaNumeric, int minLength,
		int minLowerCase, int minNumbers, int minSymbols, int minUpperCase,
		boolean history, int historyCount, boolean expireable, long maxAge,
		long warningTime, int graceLimit, boolean lockout, int maxFailure,
		long lockoutDuration, long resetFailureCount, long resetTicketMaxAge)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyLocalService.addPasswordPolicy(userId,
			defaultPolicy, name, description, changeable, changeRequired,
			minAge, checkSyntax, allowDictionaryWords, minAlphaNumeric,
			minLength, minLowerCase, minNumbers, minSymbols, minUpperCase,
			history, historyCount, expireable, maxAge, warningTime, graceLimit,
			lockout, maxFailure, lockoutDuration, resetFailureCount,
			resetTicketMaxAge);
	}

	public void checkDefaultPasswordPolicy(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_passwordPolicyLocalService.checkDefaultPasswordPolicy(companyId);
	}

	public com.liferay.portal.model.PasswordPolicy getDefaultPasswordPolicy(
		long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyLocalService.getDefaultPasswordPolicy(companyId);
	}

	public com.liferay.portal.model.PasswordPolicy getPasswordPolicy(
		long companyId, long organizationId, long locationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyLocalService.getPasswordPolicy(companyId,
			organizationId, locationId);
	}

	public com.liferay.portal.model.PasswordPolicy getPasswordPolicy(
		long companyId, long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyLocalService.getPasswordPolicy(companyId,
			organizationIds);
	}

	public com.liferay.portal.model.PasswordPolicy getPasswordPolicyByUserId(
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyLocalService.getPasswordPolicyByUserId(userId);
	}

	public java.util.List<com.liferay.portal.model.PasswordPolicy> search(
		long companyId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyLocalService.search(companyId, name, start, end,
			obc);
	}

	public int searchCount(long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyLocalService.searchCount(companyId, name);
	}

	public com.liferay.portal.model.PasswordPolicy updatePasswordPolicy(
		long passwordPolicyId, java.lang.String name,
		java.lang.String description, boolean changeable,
		boolean changeRequired, long minAge, boolean checkSyntax,
		boolean allowDictionaryWords, int minAlphaNumeric, int minLength,
		int minLowerCase, int minNumbers, int minSymbols, int minUpperCase,
		boolean history, int historyCount, boolean expireable, long maxAge,
		long warningTime, int graceLimit, boolean lockout, int maxFailure,
		long lockoutDuration, long resetFailureCount, long resetTicketMaxAge)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicyLocalService.updatePasswordPolicy(passwordPolicyId,
			name, description, changeable, changeRequired, minAge, checkSyntax,
			allowDictionaryWords, minAlphaNumeric, minLength, minLowerCase,
			minNumbers, minSymbols, minUpperCase, history, historyCount,
			expireable, maxAge, warningTime, graceLimit, lockout, maxFailure,
			lockoutDuration, resetFailureCount, resetTicketMaxAge);
	}

	public PasswordPolicyLocalService getWrappedPasswordPolicyLocalService() {
		return _passwordPolicyLocalService;
	}

	private PasswordPolicyLocalService _passwordPolicyLocalService;
}