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

package com.liferay.portlet.softwarerepository.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.softwarerepository.NoSuchLicenseException;
import com.liferay.portlet.softwarerepository.model.SRLicense;
import com.liferay.portlet.softwarerepository.model.impl.SRLicenseImpl;

import com.liferay.util.dao.DynamicQuery;
import com.liferay.util.dao.DynamicQueryInitializer;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="SRLicensePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SRLicensePersistence extends BasePersistence {
	public SRLicense create(long licenseId) {
		SRLicense srLicense = new SRLicenseImpl();
		srLicense.setNew(true);
		srLicense.setPrimaryKey(licenseId);

		return srLicense;
	}

	public SRLicense remove(long licenseId)
		throws NoSuchLicenseException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SRLicense srLicense = (SRLicense)session.get(SRLicenseImpl.class,
					new Long(licenseId));

			if (srLicense == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No SRLicense exists with the primary key " +
						licenseId);
				}

				throw new NoSuchLicenseException(
					"No SRLicense exists with the primary key " + licenseId);
			}

			return remove(srLicense);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public SRLicense remove(SRLicense srLicense) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(srLicense);
			session.flush();

			return srLicense;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.softwarerepository.model.SRLicense update(
		com.liferay.portlet.softwarerepository.model.SRLicense srLicense)
		throws SystemException {
		return update(srLicense, false);
	}

	public com.liferay.portlet.softwarerepository.model.SRLicense update(
		com.liferay.portlet.softwarerepository.model.SRLicense srLicense,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(srLicense);
			}
			else {
				if (srLicense.isNew()) {
					session.save(srLicense);
				}
			}

			session.flush();
			srLicense.setNew(false);

			return srLicense;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public SRLicense findByPrimaryKey(long licenseId)
		throws NoSuchLicenseException, SystemException {
		SRLicense srLicense = fetchByPrimaryKey(licenseId);

		if (srLicense == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SRLicense exists with the primary key " +
					licenseId);
			}

			throw new NoSuchLicenseException(
				"No SRLicense exists with the primary key " + licenseId);
		}

		return srLicense;
	}

	public SRLicense fetchByPrimaryKey(long licenseId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (SRLicense)session.get(SRLicenseImpl.class,
				new Long(licenseId));
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByActive(boolean active) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRLicense WHERE ");
			query.append("active_ = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setBoolean(queryPos++, active);

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByActive(boolean active, int begin, int end)
		throws SystemException {
		return findByActive(active, begin, end, null);
	}

	public List findByActive(boolean active, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRLicense WHERE ");
			query.append("active_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setBoolean(queryPos++, active);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public SRLicense findByActive_First(boolean active, OrderByComparator obc)
		throws NoSuchLicenseException, SystemException {
		List list = findByActive(active, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No SRLicense exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "active=";
			msg += active;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchLicenseException(msg);
		}
		else {
			return (SRLicense)list.get(0);
		}
	}

	public SRLicense findByActive_Last(boolean active, OrderByComparator obc)
		throws NoSuchLicenseException, SystemException {
		int count = countByActive(active);
		List list = findByActive(active, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No SRLicense exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "active=";
			msg += active;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchLicenseException(msg);
		}
		else {
			return (SRLicense)list.get(0);
		}
	}

	public SRLicense[] findByActive_PrevAndNext(long licenseId, boolean active,
		OrderByComparator obc) throws NoSuchLicenseException, SystemException {
		SRLicense srLicense = findByPrimaryKey(licenseId);
		int count = countByActive(active);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRLicense WHERE ");
			query.append("active_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setBoolean(queryPos++, active);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					srLicense);
			SRLicense[] array = new SRLicenseImpl[3];
			array[0] = (SRLicense)objArray[0];
			array[1] = (SRLicense)objArray[1];
			array[2] = (SRLicense)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByA_R(boolean active, boolean recommended)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRLicense WHERE ");
			query.append("active_ = ?");
			query.append(" AND ");
			query.append("recommended = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setBoolean(queryPos++, active);
			q.setBoolean(queryPos++, recommended);

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByA_R(boolean active, boolean recommended, int begin,
		int end) throws SystemException {
		return findByA_R(active, recommended, begin, end, null);
	}

	public List findByA_R(boolean active, boolean recommended, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRLicense WHERE ");
			query.append("active_ = ?");
			query.append(" AND ");
			query.append("recommended = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setBoolean(queryPos++, active);
			q.setBoolean(queryPos++, recommended);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public SRLicense findByA_R_First(boolean active, boolean recommended,
		OrderByComparator obc) throws NoSuchLicenseException, SystemException {
		List list = findByA_R(active, recommended, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No SRLicense exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "active=";
			msg += active;
			msg += ", ";
			msg += "recommended=";
			msg += recommended;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchLicenseException(msg);
		}
		else {
			return (SRLicense)list.get(0);
		}
	}

	public SRLicense findByA_R_Last(boolean active, boolean recommended,
		OrderByComparator obc) throws NoSuchLicenseException, SystemException {
		int count = countByA_R(active, recommended);
		List list = findByA_R(active, recommended, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No SRLicense exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "active=";
			msg += active;
			msg += ", ";
			msg += "recommended=";
			msg += recommended;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchLicenseException(msg);
		}
		else {
			return (SRLicense)list.get(0);
		}
	}

	public SRLicense[] findByA_R_PrevAndNext(long licenseId, boolean active,
		boolean recommended, OrderByComparator obc)
		throws NoSuchLicenseException, SystemException {
		SRLicense srLicense = findByPrimaryKey(licenseId);
		int count = countByA_R(active, recommended);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRLicense WHERE ");
			query.append("active_ = ?");
			query.append(" AND ");
			query.append("recommended = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setBoolean(queryPos++, active);
			q.setBoolean(queryPos++, recommended);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					srLicense);
			SRLicense[] array = new SRLicenseImpl[3];
			array[0] = (SRLicense)objArray[0];
			array[1] = (SRLicense)objArray[1];
			array[2] = (SRLicense)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
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
		catch (HibernateException he) {
			throw new SystemException(he);
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
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRLicense ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
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

	public void removeByActive(boolean active) throws SystemException {
		Iterator itr = findByActive(active).iterator();

		while (itr.hasNext()) {
			SRLicense srLicense = (SRLicense)itr.next();
			remove(srLicense);
		}
	}

	public void removeByA_R(boolean active, boolean recommended)
		throws SystemException {
		Iterator itr = findByA_R(active, recommended).iterator();

		while (itr.hasNext()) {
			SRLicense srLicense = (SRLicense)itr.next();
			remove(srLicense);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((SRLicense)itr.next());
		}
	}

	public int countByActive(boolean active) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRLicense WHERE ");
			query.append("active_ = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setBoolean(queryPos++, active);

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

	public int countByA_R(boolean active, boolean recommended)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRLicense WHERE ");
			query.append("active_ = ?");
			query.append(" AND ");
			query.append("recommended = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setBoolean(queryPos++, active);
			q.setBoolean(queryPos++, recommended);

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

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.softwarerepository.model.SRLicense");

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
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(SRLicensePersistence.class);
}