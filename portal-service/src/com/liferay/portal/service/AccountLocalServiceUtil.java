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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="AccountLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link AccountLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AccountLocalService
 * @generated
 */
public class AccountLocalServiceUtil {
	public static com.liferay.portal.model.Account addAccount(
		com.liferay.portal.model.Account account)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addAccount(account);
	}

	public static com.liferay.portal.model.Account createAccount(long accountId) {
		return getService().createAccount(accountId);
	}

	public static void deleteAccount(long accountId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAccount(accountId);
	}

	public static void deleteAccount(com.liferay.portal.model.Account account)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAccount(account);
	}

	public static java.util.List<com.liferay.portal.model.Account> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portal.model.Account> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Account> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portal.model.Account getAccount(long accountId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAccount(accountId);
	}

	public static java.util.List<com.liferay.portal.model.Account> getAccounts(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAccounts(start, end);
	}

	public static int getAccountsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAccountsCount();
	}

	public static com.liferay.portal.model.Account updateAccount(
		com.liferay.portal.model.Account account)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAccount(account);
	}

	public static com.liferay.portal.model.Account updateAccount(
		com.liferay.portal.model.Account account, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAccount(account, merge);
	}

	public static com.liferay.portal.model.Account getAccount(long companyId,
		long accountId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAccount(companyId, accountId);
	}

	public static AccountLocalService getService() {
		if (_service == null) {
			_service = (AccountLocalService)PortalBeanLocatorUtil.locate(AccountLocalService.class.getName());
		}

		return _service;
	}

	public void setService(AccountLocalService service) {
		_service = service;
	}

	private static AccountLocalService _service;
}