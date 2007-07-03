/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="CompanyPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CompanyPersistenceImpl extends BasePersistence
	implements CompanyPersistence {
	public Company create(long companyId) {
		Company company = new CompanyImpl();
		company.setNew(true);
		company.setPrimaryKey(companyId);

		return company;
	}

	public Company remove(long companyId)
		throws NoSuchCompanyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Company company = (Company)session.get(CompanyImpl.class,
					new Long(companyId));

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
		catch (NoSuchCompanyException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Company remove(Company company) throws SystemException {
		FinderCache.clearCache(Company.class.getName());

		Session session = null;

		try {
			session = openSession();
			session.delete(company);
			session.flush();

			return company;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Company update(com.liferay.portal.model.Company company)
		throws SystemException {
		return update(company, false);
	}

	public Company update(com.liferay.portal.model.Company company,
		boolean saveOrUpdate) throws SystemException {
		FinderCache.clearCache(Company.class.getName());

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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Company findByPrimaryKey(long companyId)
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

	public Company fetchByPrimaryKey(long companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Company)session.get(CompanyImpl.class, new Long(companyId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Company findByWebId(String webId)
		throws NoSuchCompanyException, SystemException {
		Company company = fetchByWebId(webId);

		if (company == null) {
			StringMaker msg = new StringMaker();
			msg.append("No Company exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("webId=");
			msg.append(webId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCompanyException(msg.toString());
		}

		return company;
	}

	public Company fetchByWebId(String webId) throws SystemException {
		String finderClassName = Company.class.getName();
		String finderMethodName = "fetchByWebId";
		Object[] finderArgs = new Object[] { webId };
		Object result = FinderCache.getResult(finderClassName,
				finderMethodName, finderArgs);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();
				query.append("FROM com.liferay.portal.model.Company WHERE ");

				if (webId == null) {
					query.append("webId IS NULL");
				}
				else {
					query.append("webId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());
				int queryPos = 0;

				if (webId != null) {
					q.setString(queryPos++, webId);
				}

				List list = q.list();

				if (list.size() == 0) {
					return null;
				}

				Company company = (Company)list.get(0);
				FinderCache.putResult(finderClassName, finderMethodName,
					finderArgs, company);

				return company;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (Company)result;
		}
	}

	public Company findByVirtualHost(String virtualHost)
		throws NoSuchCompanyException, SystemException {
		Company company = fetchByVirtualHost(virtualHost);

		if (company == null) {
			StringMaker msg = new StringMaker();
			msg.append("No Company exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("virtualHost=");
			msg.append(virtualHost);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCompanyException(msg.toString());
		}

		return company;
	}

	public Company fetchByVirtualHost(String virtualHost)
		throws SystemException {
		String finderClassName = Company.class.getName();
		String finderMethodName = "fetchByVirtualHost";
		Object[] finderArgs = new Object[] { virtualHost };
		Object result = FinderCache.getResult(finderClassName,
				finderMethodName, finderArgs);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();
				query.append("FROM com.liferay.portal.model.Company WHERE ");

				if (virtualHost == null) {
					query.append("virtualHost IS NULL");
				}
				else {
					query.append("virtualHost = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());
				int queryPos = 0;

				if (virtualHost != null) {
					q.setString(queryPos++, virtualHost);
				}

				List list = q.list();

				if (list.size() == 0) {
					return null;
				}

				Company company = (Company)list.get(0);
				FinderCache.putResult(finderClassName, finderMethodName,
					finderArgs, company);

				return company;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (Company)result;
		}
	}

	public Company findByMx(String mx)
		throws NoSuchCompanyException, SystemException {
		Company company = fetchByMx(mx);

		if (company == null) {
			StringMaker msg = new StringMaker();
			msg.append("No Company exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("mx=");
			msg.append(mx);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCompanyException(msg.toString());
		}

		return company;
	}

	public Company fetchByMx(String mx) throws SystemException {
		String finderClassName = Company.class.getName();
		String finderMethodName = "fetchByMx";
		Object[] finderArgs = new Object[] { mx };
		Object result = FinderCache.getResult(finderClassName,
				finderMethodName, finderArgs);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();
				query.append("FROM com.liferay.portal.model.Company WHERE ");

				if (mx == null) {
					query.append("mx IS NULL");
				}
				else {
					query.append("mx = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());
				int queryPos = 0;

				if (mx != null) {
					q.setString(queryPos++, mx);
				}

				List list = q.list();

				if (list.size() == 0) {
					return null;
				}

				Company company = (Company)list.get(0);
				FinderCache.putResult(finderClassName, finderMethodName,
					finderArgs, company);

				return company;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (Company)result;
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer,
		int begin, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);
			query.setLimit(begin, end);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Company ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByWebId(String webId)
		throws NoSuchCompanyException, SystemException {
		Company company = findByWebId(webId);
		remove(company);
	}

	public void removeByVirtualHost(String virtualHost)
		throws NoSuchCompanyException, SystemException {
		Company company = findByVirtualHost(virtualHost);
		remove(company);
	}

	public void removeByMx(String mx)
		throws NoSuchCompanyException, SystemException {
		Company company = findByMx(mx);
		remove(company);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((Company)itr.next());
		}
	}

	public int countByWebId(String webId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Company WHERE ");

			if (webId == null) {
				query.append("webId IS NULL");
			}
			else {
				query.append("webId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (webId != null) {
				q.setString(queryPos++, webId);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByVirtualHost(String virtualHost) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Company WHERE ");

			if (virtualHost == null) {
				query.append("virtualHost IS NULL");
			}
			else {
				query.append("virtualHost = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (virtualHost != null) {
				q.setString(queryPos++, virtualHost);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByMx(String mx) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Company WHERE ");

			if (mx == null) {
				query.append("mx IS NULL");
			}
			else {
				query.append("mx = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (mx != null) {
				q.setString(queryPos++, mx);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Company");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(CompanyPersistenceImpl.class);
}