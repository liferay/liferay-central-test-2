/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * This class provides static methods for the
 * <code>com.liferay.portal.service.AccountLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.AccountLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.AccountLocalService
 * @see com.liferay.portal.service.AccountLocalServiceFactory
 *
 */
public class AccountLocalServiceUtil {
	public static com.liferay.portal.model.Account addAccount(
		com.liferay.portal.model.Account account)
		throws com.liferay.portal.SystemException {
		AccountLocalService accountLocalService = AccountLocalServiceFactory.getService();

		return accountLocalService.addAccount(account);
	}

	public static void deleteAccount(long accountId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AccountLocalService accountLocalService = AccountLocalServiceFactory.getService();

		accountLocalService.deleteAccount(accountId);
	}

	public static void deleteAccount(com.liferay.portal.model.Account account)
		throws com.liferay.portal.SystemException {
		AccountLocalService accountLocalService = AccountLocalServiceFactory.getService();

		accountLocalService.deleteAccount(account);
	}

	public static java.util.List<com.liferay.portal.model.Account> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		AccountLocalService accountLocalService = AccountLocalServiceFactory.getService();

		return accountLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.Account> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		AccountLocalService accountLocalService = AccountLocalServiceFactory.getService();

		return accountLocalService.dynamicQuery(queryInitializer, start, end);
	}

	public static com.liferay.portal.model.Account getAccount(long accountId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AccountLocalService accountLocalService = AccountLocalServiceFactory.getService();

		return accountLocalService.getAccount(accountId);
	}

	public static com.liferay.portal.model.Account updateAccount(
		com.liferay.portal.model.Account account)
		throws com.liferay.portal.SystemException {
		AccountLocalService accountLocalService = AccountLocalServiceFactory.getService();

		return accountLocalService.updateAccount(account);
	}

	public static void init() {
		AccountLocalService accountLocalService = AccountLocalServiceFactory.getService();

		accountLocalService.init();
	}
}