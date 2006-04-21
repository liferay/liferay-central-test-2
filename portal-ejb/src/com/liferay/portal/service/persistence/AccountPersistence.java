/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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
import com.liferay.portal.service.persistence.BasePersistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="AccountPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AccountPersistence extends BasePersistence {
	public com.liferay.portal.model.Account create(String accountId) {
		AccountHBM accountHBM = new AccountHBM();
		accountHBM.setNew(true);
		accountHBM.setPrimaryKey(accountId);

		return AccountHBMUtil.model(accountHBM);
	}

	public com.liferay.portal.model.Account remove(String accountId)
		throws NoSuchAccountException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AccountHBM accountHBM = (AccountHBM)session.get(AccountHBM.class,
					accountId);

			if (accountHBM == null) {
				_log.warn("No Account exists with the primary key " +
					accountId.toString());
				throw new NoSuchAccountException(
					"No Account exists with the primary key " +
					accountId.toString());
			}

			session.delete(accountHBM);
			session.flush();

			return AccountHBMUtil.model(accountHBM);
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
		Session session = null;

		try {
			if (account.isNew() || account.isModified()) {
				session = openSession();

				if (account.isNew()) {
					AccountHBM accountHBM = new AccountHBM();
					accountHBM.setAccountId(account.getAccountId());
					accountHBM.setCompanyId(account.getCompanyId());
					accountHBM.setUserId(account.getUserId());
					accountHBM.setUserName(account.getUserName());
					accountHBM.setCreateDate(account.getCreateDate());
					accountHBM.setModifiedDate(account.getModifiedDate());
					accountHBM.setParentAccountId(account.getParentAccountId());
					accountHBM.setName(account.getName());
					accountHBM.setLegalName(account.getLegalName());
					accountHBM.setLegalId(account.getLegalId());
					accountHBM.setLegalType(account.getLegalType());
					accountHBM.setSicCode(account.getSicCode());
					accountHBM.setTickerSymbol(account.getTickerSymbol());
					accountHBM.setIndustry(account.getIndustry());
					accountHBM.setType(account.getType());
					accountHBM.setSize(account.getSize());
					session.save(accountHBM);
					session.flush();
				}
				else {
					AccountHBM accountHBM = (AccountHBM)session.get(AccountHBM.class,
							account.getPrimaryKey());

					if (accountHBM != null) {
						accountHBM.setCompanyId(account.getCompanyId());
						accountHBM.setUserId(account.getUserId());
						accountHBM.setUserName(account.getUserName());
						accountHBM.setCreateDate(account.getCreateDate());
						accountHBM.setModifiedDate(account.getModifiedDate());
						accountHBM.setParentAccountId(account.getParentAccountId());
						accountHBM.setName(account.getName());
						accountHBM.setLegalName(account.getLegalName());
						accountHBM.setLegalId(account.getLegalId());
						accountHBM.setLegalType(account.getLegalType());
						accountHBM.setSicCode(account.getSicCode());
						accountHBM.setTickerSymbol(account.getTickerSymbol());
						accountHBM.setIndustry(account.getIndustry());
						accountHBM.setType(account.getType());
						accountHBM.setSize(account.getSize());
						session.flush();
					}
					else {
						accountHBM = new AccountHBM();
						accountHBM.setAccountId(account.getAccountId());
						accountHBM.setCompanyId(account.getCompanyId());
						accountHBM.setUserId(account.getUserId());
						accountHBM.setUserName(account.getUserName());
						accountHBM.setCreateDate(account.getCreateDate());
						accountHBM.setModifiedDate(account.getModifiedDate());
						accountHBM.setParentAccountId(account.getParentAccountId());
						accountHBM.setName(account.getName());
						accountHBM.setLegalName(account.getLegalName());
						accountHBM.setLegalId(account.getLegalId());
						accountHBM.setLegalType(account.getLegalType());
						accountHBM.setSicCode(account.getSicCode());
						accountHBM.setTickerSymbol(account.getTickerSymbol());
						accountHBM.setIndustry(account.getIndustry());
						accountHBM.setType(account.getType());
						accountHBM.setSize(account.getSize());
						session.save(accountHBM);
						session.flush();
					}
				}

				account.setNew(false);
				account.setModified(false);
			}

			return account;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Account findByPrimaryKey(String accountId)
		throws NoSuchAccountException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AccountHBM accountHBM = (AccountHBM)session.get(AccountHBM.class,
					accountId);

			if (accountHBM == null) {
				_log.warn("No Account exists with the primary key " +
					accountId.toString());
				throw new NoSuchAccountException(
					"No Account exists with the primary key " +
					accountId.toString());
			}

			return AccountHBMUtil.model(accountHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Account_ IN CLASS com.liferay.portal.service.persistence.AccountHBM ");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				AccountHBM accountHBM = (AccountHBM)itr.next();
				list.add(AccountHBMUtil.model(accountHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	private static Log _log = LogFactory.getLog(AccountPersistence.class);
}