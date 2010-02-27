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

import com.liferay.portal.model.Account;

/**
 * <a href="AccountPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AccountPersistenceImpl
 * @see       AccountUtil
 * @generated
 */
public interface AccountPersistence extends BasePersistence<Account> {
	public void cacheResult(com.liferay.portal.model.Account account);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Account> accounts);

	public com.liferay.portal.model.Account create(long accountId);

	public com.liferay.portal.model.Account remove(long accountId)
		throws com.liferay.portal.NoSuchAccountException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Account updateImpl(
		com.liferay.portal.model.Account account, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Account findByPrimaryKey(long accountId)
		throws com.liferay.portal.NoSuchAccountException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Account fetchByPrimaryKey(long accountId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Account> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Account> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Account> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}