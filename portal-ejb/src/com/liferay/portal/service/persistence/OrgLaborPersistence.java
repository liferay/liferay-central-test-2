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

import com.liferay.portal.NoSuchOrgLaborException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="OrgLaborPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OrgLaborPersistence extends BasePersistence {
	public OrgLabor create(String orgLaborId) {
		OrgLabor orgLabor = new OrgLabor();
		orgLabor.setNew(true);
		orgLabor.setPrimaryKey(orgLaborId);

		return orgLabor;
	}

	public OrgLabor remove(String orgLaborId)
		throws NoSuchOrgLaborException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrgLabor orgLabor = (OrgLabor)session.get(OrgLabor.class, orgLaborId);

			if (orgLabor == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No OrgLabor exists with the primary key " +
						orgLaborId);
				}

				throw new NoSuchOrgLaborException(
					"No OrgLabor exists with the primary key " + orgLaborId);
			}

			return remove(orgLabor);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public OrgLabor remove(OrgLabor orgLabor) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(orgLabor);
			session.flush();

			return orgLabor;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.OrgLabor update(
		com.liferay.portal.model.OrgLabor orgLabor) throws SystemException {
		return update(orgLabor, false);
	}

	public com.liferay.portal.model.OrgLabor update(
		com.liferay.portal.model.OrgLabor orgLabor, boolean saveOrUpdate)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(orgLabor);
			}
			else {
				if (orgLabor.isNew()) {
					session.save(orgLabor);
				}
			}

			session.flush();
			orgLabor.setNew(false);

			return orgLabor;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public OrgLabor findByPrimaryKey(String orgLaborId)
		throws NoSuchOrgLaborException, SystemException {
		OrgLabor orgLabor = fetchByPrimaryKey(orgLaborId);

		if (orgLabor == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No OrgLabor exists with the primary key " +
					orgLaborId);
			}

			throw new NoSuchOrgLaborException(
				"No OrgLabor exists with the primary key " + orgLaborId);
		}

		return orgLabor;
	}

	public OrgLabor fetchByPrimaryKey(String orgLaborId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (OrgLabor)session.get(OrgLabor.class, orgLaborId);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByOrganizationId(String organizationId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.OrgLabor WHERE ");

			if (organizationId == null) {
				query.append("organizationId IS NULL");
			}
			else {
				query.append("organizationId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("organizationId ASC").append(", ");
			query.append("typeId ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (organizationId != null) {
				q.setString(queryPos++, organizationId);
			}

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByOrganizationId(String organizationId, int begin, int end)
		throws SystemException {
		return findByOrganizationId(organizationId, begin, end, null);
	}

	public List findByOrganizationId(String organizationId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.OrgLabor WHERE ");

			if (organizationId == null) {
				query.append("organizationId IS NULL");
			}
			else {
				query.append("organizationId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("organizationId ASC").append(", ");
				query.append("typeId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (organizationId != null) {
				q.setString(queryPos++, organizationId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public OrgLabor findByOrganizationId_First(String organizationId,
		OrderByComparator obc) throws NoSuchOrgLaborException, SystemException {
		List list = findByOrganizationId(organizationId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No OrgLabor exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "organizationId=";
			msg += organizationId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchOrgLaborException(msg);
		}
		else {
			return (OrgLabor)list.get(0);
		}
	}

	public OrgLabor findByOrganizationId_Last(String organizationId,
		OrderByComparator obc) throws NoSuchOrgLaborException, SystemException {
		int count = countByOrganizationId(organizationId);
		List list = findByOrganizationId(organizationId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No OrgLabor exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "organizationId=";
			msg += organizationId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchOrgLaborException(msg);
		}
		else {
			return (OrgLabor)list.get(0);
		}
	}

	public OrgLabor[] findByOrganizationId_PrevAndNext(String orgLaborId,
		String organizationId, OrderByComparator obc)
		throws NoSuchOrgLaborException, SystemException {
		OrgLabor orgLabor = findByPrimaryKey(orgLaborId);
		int count = countByOrganizationId(organizationId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.OrgLabor WHERE ");

			if (organizationId == null) {
				query.append("organizationId IS NULL");
			}
			else {
				query.append("organizationId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("organizationId ASC").append(", ");
				query.append("typeId ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (organizationId != null) {
				q.setString(queryPos++, organizationId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, orgLabor);
			OrgLabor[] array = new OrgLabor[3];
			array[0] = (OrgLabor)objArray[0];
			array[1] = (OrgLabor)objArray[1];
			array[2] = (OrgLabor)objArray[2];

			return array;
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
			query.append("FROM com.liferay.portal.model.OrgLabor ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("organizationId ASC").append(", ");
				query.append("typeId ASC");
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

	public void removeByOrganizationId(String organizationId)
		throws SystemException {
		Iterator itr = findByOrganizationId(organizationId).iterator();

		while (itr.hasNext()) {
			OrgLabor orgLabor = (OrgLabor)itr.next();
			remove(orgLabor);
		}
	}

	public int countByOrganizationId(String organizationId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.OrgLabor WHERE ");

			if (organizationId == null) {
				query.append("organizationId IS NULL");
			}
			else {
				query.append("organizationId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (organizationId != null) {
				q.setString(queryPos++, organizationId);
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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(OrgLaborPersistence.class);
}