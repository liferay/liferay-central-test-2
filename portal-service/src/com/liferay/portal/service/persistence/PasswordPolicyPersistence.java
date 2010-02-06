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
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.PasswordPolicy updateImpl(
		com.liferay.portal.model.PasswordPolicy passwordPolicy, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.PasswordPolicy findByPrimaryKey(
		long passwordPolicyId)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.PasswordPolicy fetchByPrimaryKey(
		long passwordPolicyId) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.PasswordPolicy findByC_DP(long companyId,
		boolean defaultPolicy)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.PasswordPolicy fetchByC_DP(long companyId,
		boolean defaultPolicy) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.PasswordPolicy fetchByC_DP(long companyId,
		boolean defaultPolicy, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.PasswordPolicy findByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.PasswordPolicy fetchByC_N(long companyId,
		java.lang.String name) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.PasswordPolicy fetchByC_N(long companyId,
		java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PasswordPolicy> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PasswordPolicy> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.PasswordPolicy> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByC_DP(long companyId, boolean defaultPolicy)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.SystemException;

	public void removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.NoSuchPasswordPolicyException,
			com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByC_DP(long companyId, boolean defaultPolicy)
		throws com.liferay.portal.SystemException;

	public int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}