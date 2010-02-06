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
		throws com.liferay.portal.SystemException {
		return _accountLocalService.addAccount(account);
	}

	public com.liferay.portal.model.Account createAccount(long accountId) {
		return _accountLocalService.createAccount(accountId);
	}

	public void deleteAccount(long accountId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_accountLocalService.deleteAccount(accountId);
	}

	public void deleteAccount(com.liferay.portal.model.Account account)
		throws com.liferay.portal.SystemException {
		_accountLocalService.deleteAccount(account);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _accountLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _accountLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.Account getAccount(long accountId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _accountLocalService.getAccount(accountId);
	}

	public java.util.List<com.liferay.portal.model.Account> getAccounts(
		int start, int end) throws com.liferay.portal.SystemException {
		return _accountLocalService.getAccounts(start, end);
	}

	public int getAccountsCount() throws com.liferay.portal.SystemException {
		return _accountLocalService.getAccountsCount();
	}

	public com.liferay.portal.model.Account updateAccount(
		com.liferay.portal.model.Account account)
		throws com.liferay.portal.SystemException {
		return _accountLocalService.updateAccount(account);
	}

	public com.liferay.portal.model.Account updateAccount(
		com.liferay.portal.model.Account account, boolean merge)
		throws com.liferay.portal.SystemException {
		return _accountLocalService.updateAccount(account, merge);
	}

	public com.liferay.portal.model.Account getAccount(long companyId,
		long accountId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _accountLocalService.getAccount(companyId, accountId);
	}

	public AccountLocalService getWrappedAccountLocalService() {
		return _accountLocalService;
	}

	private AccountLocalService _accountLocalService;
}