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
 * <a href="AccountLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AccountLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AccountLocalService
 * @generated
 */
public class AccountLocalServiceWrapper implements AccountLocalService {
	public AccountLocalServiceWrapper(AccountLocalService accountLocalService) {
		_accountLocalService = accountLocalService;
	}

	public com.liferay.portal.model.Account addAccount(
		com.liferay.portal.model.Account account)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _accountLocalService.addAccount(account);
	}

	public com.liferay.portal.model.Account createAccount(long accountId) {
		return _accountLocalService.createAccount(accountId);
	}

	public void deleteAccount(long accountId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_accountLocalService.deleteAccount(accountId);
	}

	public void deleteAccount(com.liferay.portal.model.Account account)
		throws com.liferay.portal.kernel.exception.SystemException {
		_accountLocalService.deleteAccount(account);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _accountLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _accountLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.Account getAccount(long accountId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _accountLocalService.getAccount(accountId);
	}

	public java.util.List<com.liferay.portal.model.Account> getAccounts(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _accountLocalService.getAccounts(start, end);
	}

	public int getAccountsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _accountLocalService.getAccountsCount();
	}

	public com.liferay.portal.model.Account updateAccount(
		com.liferay.portal.model.Account account)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _accountLocalService.updateAccount(account);
	}

	public com.liferay.portal.model.Account updateAccount(
		com.liferay.portal.model.Account account, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _accountLocalService.updateAccount(account, merge);
	}

	public com.liferay.portal.model.Account getAccount(long companyId,
		long accountId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _accountLocalService.getAccount(companyId, accountId);
	}

	public AccountLocalService getWrappedAccountLocalService() {
		return _accountLocalService;
	}

	private AccountLocalService _accountLocalService;
}