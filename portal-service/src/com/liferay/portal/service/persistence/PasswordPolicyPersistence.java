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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.PasswordPolicy;

/**
 * <a href="PasswordPolicyPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordPolicyPersistenceImpl
 * @see       PasswordPolicyUtil
 * @generated
 */
public interface PasswordPolicyPersistence extends BasePersistence<PasswordPolicy> {
	public void cacheResult(
		com.liferay.portal.model.PasswordPolicy passwordPolicy);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.PasswordPolicy> passwordPolicies);

	public com.liferay.portal.model.PasswordPolicy create(long passwordPolicyId);

	public com.liferay.portal.model.PasswordPolicy remove(long passwordPolicyId)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PasswordPolicy updateImpl(
		com.liferay.portal.model.PasswordPolicy passwordPolicy, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PasswordPolicy findByPrimaryKey(
		long passwordPolicyId)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PasswordPolicy fetchByPrimaryKey(
		long passwordPolicyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PasswordPolicy findByC_DP(long companyId,
		boolean defaultPolicy)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PasswordPolicy fetchByC_DP(long companyId,
		boolean defaultPolicy)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PasswordPolicy fetchByC_DP(long companyId,
		boolean defaultPolicy, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PasswordPolicy findByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PasswordPolicy fetchByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PasswordPolicy fetchByC_N(long companyId,
		java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PasswordPolicy> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PasswordPolicy> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PasswordPolicy> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_DP(long companyId, boolean defaultPolicy)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_DP(long companyId, boolean defaultPolicy)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}