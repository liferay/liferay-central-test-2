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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.PasswordPolicy;

import java.util.List;

/**
 * <a href="PasswordPolicyUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordPolicyPersistence
 * @see       PasswordPolicyPersistenceImpl
 * @generated
 */
public class PasswordPolicyUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(PasswordPolicy)
	 */
	public static void clearCache(PasswordPolicy passwordPolicy) {
		getPersistence().clearCache(passwordPolicy);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<PasswordPolicy> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PasswordPolicy> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static PasswordPolicy remove(PasswordPolicy passwordPolicy)
		throws SystemException {
		return getPersistence().remove(passwordPolicy);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static PasswordPolicy update(PasswordPolicy passwordPolicy,
		boolean merge) throws SystemException {
		return getPersistence().update(passwordPolicy, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.PasswordPolicy passwordPolicy) {
		getPersistence().cacheResult(passwordPolicy);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.PasswordPolicy> passwordPolicies) {
		getPersistence().cacheResult(passwordPolicies);
	}

	public static com.liferay.portal.model.PasswordPolicy create(
		long passwordPolicyId) {
		return getPersistence().create(passwordPolicyId);
	}

	public static com.liferay.portal.model.PasswordPolicy remove(
		long passwordPolicyId)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(passwordPolicyId);
	}

	public static com.liferay.portal.model.PasswordPolicy updateImpl(
		com.liferay.portal.model.PasswordPolicy passwordPolicy, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(passwordPolicy, merge);
	}

	public static com.liferay.portal.model.PasswordPolicy findByPrimaryKey(
		long passwordPolicyId)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(passwordPolicyId);
	}

	public static com.liferay.portal.model.PasswordPolicy fetchByPrimaryKey(
		long passwordPolicyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(passwordPolicyId);
	}

	public static com.liferay.portal.model.PasswordPolicy findByC_DP(
		long companyId, boolean defaultPolicy)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_DP(companyId, defaultPolicy);
	}

	public static com.liferay.portal.model.PasswordPolicy fetchByC_DP(
		long companyId, boolean defaultPolicy)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_DP(companyId, defaultPolicy);
	}

	public static com.liferay.portal.model.PasswordPolicy fetchByC_DP(
		long companyId, boolean defaultPolicy, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_DP(companyId, defaultPolicy, retrieveFromCache);
	}

	public static com.liferay.portal.model.PasswordPolicy findByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_N(companyId, name);
	}

	public static com.liferay.portal.model.PasswordPolicy fetchByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_N(companyId, name);
	}

	public static com.liferay.portal.model.PasswordPolicy fetchByC_N(
		long companyId, java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_N(companyId, name, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.PasswordPolicy> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.PasswordPolicy> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.PasswordPolicy> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByC_DP(long companyId, boolean defaultPolicy)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_DP(companyId, defaultPolicy);
	}

	public static void removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_N(companyId, name);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByC_DP(long companyId, boolean defaultPolicy)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_DP(companyId, defaultPolicy);
	}

	public static int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_N(companyId, name);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static PasswordPolicyPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PasswordPolicyPersistence)PortalBeanLocatorUtil.locate(PasswordPolicyPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(PasswordPolicyPersistence persistence) {
		_persistence = persistence;
	}

	private static PasswordPolicyPersistence _persistence;
}