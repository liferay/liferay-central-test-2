/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

public class AccountLocalServiceUtil {
	public static com.liferay.portal.model.Account addAccount(
		com.liferay.portal.model.Account account)
		throws com.liferay.portal.SystemException {
		return getService().addAccount(account);
	}

	public static com.liferay.portal.model.Account createAccount(long accountId) {
		return getService().createAccount(accountId);
	}

	public static void deleteAccount(long accountId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteAccount(accountId);
	}

	public static void deleteAccount(com.liferay.portal.model.Account account)
		throws com.liferay.portal.SystemException {
		getService().deleteAccount(account);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.Account getAccount(long accountId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getAccount(accountId);
	}

	public static java.util.List<com.liferay.portal.model.Account> getAccounts(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getAccounts(start, end);
	}

	public static int getAccountsCount()
		throws com.liferay.portal.SystemException {
		return getService().getAccountsCount();
	}

	public static com.liferay.portal.model.Account updateAccount(
		com.liferay.portal.model.Account account)
		throws com.liferay.portal.SystemException {
		return getService().updateAccount(account);
	}

	public static com.liferay.portal.model.Account updateAccount(
		com.liferay.portal.model.Account account, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateAccount(account, merge);
	}

	public static com.liferay.portal.model.Account getAccount(long companyId,
		long accountId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getAccount(companyId, accountId);
	}

	public static AccountLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("AccountLocalService is not set");
		}

		return _service;
	}

	public void setService(AccountLocalService service) {
		_service = service;
	}

	private static AccountLocalService _service;
}