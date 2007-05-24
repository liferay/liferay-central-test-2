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

import com.liferay.portal.NoSuchPasswordPolicyException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.impl.PasswordPolicyImpl;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="PasswordPolicyPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PasswordPolicyPersistenceImpl extends BasePersistence
	implements PasswordPolicyPersistence {
	public PasswordPolicy create(long passwordPolicyId) {
		PasswordPolicy passwordPolicy = new PasswordPolicyImpl();
		passwordPolicy.setNew(true);
		passwordPolicy.setPrimaryKey(passwordPolicyId);

		return passwordPolicy;
	}

	public PasswordPolicy remove(long passwordPolicyId)
		throws NoSuchPasswordPolicyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PasswordPolicy passwordPolicy = (PasswordPolicy)session.get(PasswordPolicyImpl.class,
					new Long(passwordPolicyId));

			if (passwordPolicy == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No PasswordPolicy exists with the primary key " +
						passwordPolicyId);
				}

				throw new NoSuchPasswordPolicyException(
					"No PasswordPolicy exists with the primary key " +
					passwordPolicyId);
			}

			return remove(passwordPolicy);
		}
		catch (NoSuchPasswordPolicyException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PasswordPolicy remove(PasswordPolicy passwordPolicy)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(passwordPolicy);
			session.flush();

			return passwordPolicy;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.PasswordPolicy update(
		com.liferay.portal.model.PasswordPolicy passwordPolicy)
		throws SystemException {
		return update(passwordPolicy, false);
	}

	public com.liferay.portal.model.PasswordPolicy update(
		com.liferay.portal.model.PasswordPolicy passwordPolicy,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(passwordPolicy);
			}
			else {
				if (passwordPolicy.isNew()) {
					session.save(passwordPolicy);
				}
			}

			session.flush();
			passwordPolicy.setNew(false);

			return passwordPolicy;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PasswordPolicy findByPrimaryKey(long passwordPolicyId)
		throws NoSuchPasswordPolicyException, SystemException {
		PasswordPolicy passwordPolicy = fetchByPrimaryKey(passwordPolicyId);

		if (passwordPolicy == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No PasswordPolicy exists with the primary key " +
					passwordPolicyId);
			}

			throw new NoSuchPasswordPolicyException(
				"No PasswordPolicy exists with the primary key " +
				passwordPolicyId);
		}

		return passwordPolicy;
	}

	public PasswordPolicy fetchByPrimaryKey(long passwordPolicyId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (PasswordPolicy)session.get(PasswordPolicyImpl.class,
				new Long(passwordPolicyId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PasswordPolicy findByC_DP(long companyId, boolean defaultPolicy)
		throws NoSuchPasswordPolicyException, SystemException {
		PasswordPolicy passwordPolicy = fetchByC_DP(companyId, defaultPolicy);

		if (passwordPolicy == null) {
			StringMaker msg = new StringMaker();
			msg.append("No PasswordPolicy exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("defaultPolicy=");
			msg.append(defaultPolicy);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPasswordPolicyException(msg.toString());
		}

		return passwordPolicy;
	}

	public PasswordPolicy fetchByC_DP(long companyId, boolean defaultPolicy)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.PasswordPolicy WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("defaultPolicy = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setBoolean(queryPos++, defaultPolicy);

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			PasswordPolicy passwordPolicy = (PasswordPolicy)list.get(0);

			return passwordPolicy;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PasswordPolicy findByC_N(long companyId, String name)
		throws NoSuchPasswordPolicyException, SystemException {
		PasswordPolicy passwordPolicy = fetchByC_N(companyId, name);

		if (passwordPolicy == null) {
			StringMaker msg = new StringMaker();
			msg.append("No PasswordPolicy exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("name=");
			msg.append(name);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPasswordPolicyException(msg.toString());
		}

		return passwordPolicy;
	}

	public PasswordPolicy fetchByC_N(long companyId, String name)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.PasswordPolicy WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);

			if (name != null) {
				q.setString(queryPos++, name);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			PasswordPolicy passwordPolicy = (PasswordPolicy)list.get(0);

			return passwordPolicy;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
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
			query.append("FROM com.liferay.portal.model.PasswordPolicy ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByC_DP(long companyId, boolean defaultPolicy)
		throws NoSuchPasswordPolicyException, SystemException {
		PasswordPolicy passwordPolicy = findByC_DP(companyId, defaultPolicy);
		remove(passwordPolicy);
	}

	public void removeByC_N(long companyId, String name)
		throws NoSuchPasswordPolicyException, SystemException {
		PasswordPolicy passwordPolicy = findByC_N(companyId, name);
		remove(passwordPolicy);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((PasswordPolicy)itr.next());
		}
	}

	public int countByC_DP(long companyId, boolean defaultPolicy)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.PasswordPolicy WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("defaultPolicy = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setBoolean(queryPos++, defaultPolicy);

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

	public int countByC_N(long companyId, String name)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.PasswordPolicy WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);

			if (name != null) {
				q.setString(queryPos++, name);
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
			query.append("FROM com.liferay.portal.model.PasswordPolicy");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

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

	private static Log _log = LogFactory.getLog(PasswordPolicyPersistenceImpl.class);
}