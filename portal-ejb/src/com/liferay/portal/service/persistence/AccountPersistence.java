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

import com.liferay.portal.NoSuchAccountException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.impl.AccountImpl;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * <a href="AccountPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AccountPersistence extends BasePersistence {
	public Account create(String accountId) {
		Account account = new AccountImpl();
		account.setNew(true);
		account.setPrimaryKey(accountId);

		return account;
	}

	public Account remove(String accountId)
		throws NoSuchAccountException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Account account = (Account)session.get(AccountImpl.class, accountId);

			if (account == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Account exists with the primary key " +
						accountId);
				}

				throw new NoSuchAccountException(
					"No Account exists with the primary key " + accountId);
			}

			return remove(account);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Account remove(Account account) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(account);
			session.flush();

			return account;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Account update(
		com.liferay.portal.model.Account account) throws SystemException {
		return update(account, false);
	}

	public com.liferay.portal.model.Account update(
		com.liferay.portal.model.Account account, boolean saveOrUpdate)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(account);
			}
			else {
				if (account.isNew()) {
					session.save(account);
				}
			}

			session.flush();
			account.setNew(false);

			return account;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Account findByPrimaryKey(String accountId)
		throws NoSuchAccountException, SystemException {
		Account account = fetchByPrimaryKey(accountId);

		if (account == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Account exists with the primary key " +
					accountId);
			}

			throw new NoSuchAccountException(
				"No Account exists with the primary key " + accountId);
		}

		return account;
	}

	public Account fetchByPrimaryKey(String accountId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Account)session.get(AccountImpl.class, accountId);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List findAll(int begin, int end) throws SystemException {
		return findAll(begin, end, null);
	}

	public List findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Account ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(AccountPersistence.class);
}