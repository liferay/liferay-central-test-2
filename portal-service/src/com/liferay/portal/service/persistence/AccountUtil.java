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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.model.Account;

import java.util.List;

/**
 * <a href="AccountUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AccountPersistence
 * @see       AccountPersistenceImpl
 * @generated
 */
public class AccountUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static Account remove(Account account) throws SystemException {
		return getPersistence().remove(account);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Account update(Account account, boolean merge)
		throws SystemException {
		return getPersistence().update(account, merge);
	}

	public static void cacheResult(com.liferay.portal.model.Account account) {
		getPersistence().cacheResult(account);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Account> accounts) {
		getPersistence().cacheResult(accounts);
	}

	public static com.liferay.portal.model.Account create(long accountId) {
		return getPersistence().create(accountId);
	}

	public static com.liferay.portal.model.Account remove(long accountId)
		throws com.liferay.portal.NoSuchAccountException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(accountId);
	}

	public static com.liferay.portal.model.Account updateImpl(
		com.liferay.portal.model.Account account, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(account, merge);
	}

	public static com.liferay.portal.model.Account findByPrimaryKey(
		long accountId)
		throws com.liferay.portal.NoSuchAccountException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(accountId);
	}

	public static com.liferay.portal.model.Account fetchByPrimaryKey(
		long accountId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(accountId);
	}

	public static java.util.List<com.liferay.portal.model.Account> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Account> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Account> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static AccountPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AccountPersistence)PortalBeanLocatorUtil.locate(AccountPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(AccountPersistence persistence) {
		_persistence = persistence;
	}

	private static AccountPersistence _persistence;
}