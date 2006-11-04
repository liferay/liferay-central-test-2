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

import com.liferay.portal.NoSuchCompanyException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * <a href="CompanyPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class CompanyPersistence extends BasePersistence {
	public Company create(String companyId) {
		Company company = new Company();
		company.setNew(true);
		company.setPrimaryKey(companyId);

		return company;
	}

	public Company remove(String companyId)
		throws NoSuchCompanyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Company company = (Company)session.get(Company.class, companyId);

			if (company == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Company exists with the primary key " +
						companyId);
				}

				throw new NoSuchCompanyException(
					"No Company exists with the primary key " + companyId);
			}

			return remove(company);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Company remove(Company company) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(company);
			session.flush();

			return company;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Company update(
		com.liferay.portal.model.Company company) throws SystemException {
		return update(company, false);
	}

	public com.liferay.portal.model.Company update(
		com.liferay.portal.model.Company company, boolean saveOrUpdate)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(company);
			}
			else {
				if (company.isNew()) {
					session.save(company);
				}
			}

			session.flush();
			company.setNew(false);

			return company;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Company findByPrimaryKey(String companyId)
		throws NoSuchCompanyException, SystemException {
		Company company = fetchByPrimaryKey(companyId);

		if (company == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Company exists with the primary key " +
					companyId);
			}

			throw new NoSuchCompanyException(
				"No Company exists with the primary key " + companyId);
		}

		return company;
	}

	public Company fetchByPrimaryKey(String companyId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Company)session.get(Company.class, companyId);
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
			query.append("FROM com.liferay.portal.model.Company ");

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

	private static Log _log = LogFactory.getLog(CompanyPersistence.class);
}