/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="AccountUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AccountUtil {
	public static com.liferay.portal.model.Account create(
		java.lang.String accountId) {
		return getPersistence().create(accountId);
	}

	public static com.liferay.portal.model.Account remove(
		java.lang.String accountId)
		throws com.liferay.portal.NoSuchAccountException, 
			com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(accountId));
		}

		com.liferay.portal.model.Account account = getPersistence().remove(accountId);

		if (listener != null) {
			listener.onAfterRemove(account);
		}

		return account;
	}

	public static com.liferay.portal.model.Account remove(
		com.liferay.portal.model.Account account)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(account);
		}

		account = getPersistence().remove(account);

		if (listener != null) {
			listener.onAfterRemove(account);
		}

		return account;
	}

	public static com.liferay.portal.model.Account update(
		com.liferay.portal.model.Account account)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = account.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(account);
			}
			else {
				listener.onBeforeUpdate(account);
			}
		}

		account = getPersistence().update(account);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(account);
			}
			else {
				listener.onAfterUpdate(account);
			}
		}

		return account;
	}

	public static com.liferay.portal.model.Account update(
		com.liferay.portal.model.Account account, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = account.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(account);
			}
			else {
				listener.onBeforeUpdate(account);
			}
		}

		account = getPersistence().update(account, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(account);
			}
			else {
				listener.onAfterUpdate(account);
			}
		}

		return account;
	}

	public static com.liferay.portal.model.Account findByPrimaryKey(
		java.lang.String accountId)
		throws com.liferay.portal.NoSuchAccountException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(accountId);
	}

	public static com.liferay.portal.model.Account fetchByPrimaryKey(
		java.lang.String accountId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(accountId);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static AccountPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(AccountPersistence persistence) {
		_persistence = persistence;
	}

	private static AccountUtil _getUtil() {
		if (_util == null) {
			ApplicationContext ctx = SpringUtil.getContext();
			_util = (AccountUtil)ctx.getBean(_UTIL);
		}

		return _util;
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _UTIL = AccountUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.Account"));
	private static Log _log = LogFactory.getLog(AccountUtil.class);
	private static AccountUtil _util;
	private AccountPersistence _persistence;
}