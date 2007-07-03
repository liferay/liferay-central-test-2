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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.portlet.softwarecatalog.NoSuchLicenseException;
import com.liferay.portlet.softwarecatalog.model.SCLicense;
import com.liferay.portlet.softwarecatalog.model.impl.SCLicenseImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="SCLicensePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCLicensePersistenceImpl extends BasePersistence
	implements SCLicensePersistence {
	public SCLicense create(long licenseId) {
		SCLicense scLicense = new SCLicenseImpl();
		scLicense.setNew(true);
		scLicense.setPrimaryKey(licenseId);

		return scLicense;
	}

	public SCLicense remove(long licenseId)
		throws NoSuchLicenseException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCLicense scLicense = (SCLicense)session.get(SCLicenseImpl.class,
					new Long(licenseId));

			if (scLicense == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No SCLicense exists with the primary key " +
						licenseId);
				}

				throw new NoSuchLicenseException(
					"No SCLicense exists with the primary key " + licenseId);
			}

			return remove(scLicense);
		}
		catch (NoSuchLicenseException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCLicense remove(SCLicense scLicense) throws SystemException {
		FinderCache.clearCache(SCLicense.class.getName());

		Session session = null;

		try {
			session = openSession();
			session.delete(scLicense);
			session.flush();

			return scLicense;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCLicense update(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws SystemException {
		return update(scLicense, false);
	}

	public SCLicense update(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense,
		boolean saveOrUpdate) throws SystemException {
		FinderCache.clearCache(SCLicense.class.getName());

		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(scLicense);
			}
			else {
				if (scLicense.isNew()) {
					session.save(scLicense);
				}
			}

			session.flush();
			scLicense.setNew(false);

			return scLicense;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCLicense findByPrimaryKey(long licenseId)
		throws NoSuchLicenseException, SystemException {
		SCLicense scLicense = fetchByPrimaryKey(licenseId);

		if (scLicense == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SCLicense exists with the primary key " +
					licenseId);
			}

			throw new NoSuchLicenseException(
				"No SCLicense exists with the primary key " + licenseId);
		}

		return scLicense;
	}

	public SCLicense fetchByPrimaryKey(long licenseId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (SCLicense)session.get(SCLicenseImpl.class,
				new Long(licenseId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByActive(boolean active) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCLicense WHERE ");
			query.append("active_ = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setBoolean(queryPos++, active);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCLicense WHERE ");
			query.append("active_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setBoolean(queryPos++, active);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCLicense findByActive_First(boolean active, OrderByComparator obc)
		throws NoSuchLicenseException, SystemException {
		List list = findByActive(active, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No SCLicense exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("active=");
			msg.append(active);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchLicenseException(msg.toString());
		}
		else {
			return (SCLicense)list.get(0);
		}
	}

	public SCLicense findByActive_Last(boolean active, OrderByComparator obc)
		throws NoSuchLicenseException, SystemException {
		int count = countByActive(active);
		List list = findByActive(active, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No SCLicense exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("active=");
			msg.append(active);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchLicenseException(msg.toString());
		}
		else {
			return (SCLicense)list.get(0);
		}
	}

	public SCLicense[] findByActive_PrevAndNext(long licenseId, boolean active,
		OrderByComparator obc) throws NoSuchLicenseException, SystemException {
		SCLicense scLicense = findByPrimaryKey(licenseId);
		int count = countByActive(active);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCLicense WHERE ");
			query.append("active_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setBoolean(queryPos++, active);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scLicense);
			SCLicense[] array = new SCLicenseImpl[3];
			array[0] = (SCLicense)objArray[0];
			array[1] = (SCLicense)objArray[1];
			array[2] = (SCLicense)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCLicense WHERE ");
			query.append("active_ = ?");
			query.append(" AND ");
			query.append("recommended = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setBoolean(queryPos++, active);
			q.setBoolean(queryPos++, recommended);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCLicense WHERE ");
			query.append("active_ = ?");
			query.append(" AND ");
			query.append("recommended = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setBoolean(queryPos++, active);
			q.setBoolean(queryPos++, recommended);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCLicense findByA_R_First(boolean active, boolean recommended,
		OrderByComparator obc) throws NoSuchLicenseException, SystemException {
		List list = findByA_R(active, recommended, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No SCLicense exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("active=");
			msg.append(active);
			msg.append(", ");
			msg.append("recommended=");
			msg.append(recommended);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchLicenseException(msg.toString());
		}
		else {
			return (SCLicense)list.get(0);
		}
	}

	public SCLicense findByA_R_Last(boolean active, boolean recommended,
		OrderByComparator obc) throws NoSuchLicenseException, SystemException {
		int count = countByA_R(active, recommended);
		List list = findByA_R(active, recommended, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No SCLicense exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("active=");
			msg.append(active);
			msg.append(", ");
			msg.append("recommended=");
			msg.append(recommended);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchLicenseException(msg.toString());
		}
		else {
			return (SCLicense)list.get(0);
		}
	}

	public SCLicense[] findByA_R_PrevAndNext(long licenseId, boolean active,
		boolean recommended, OrderByComparator obc)
		throws NoSuchLicenseException, SystemException {
		SCLicense scLicense = findByPrimaryKey(licenseId);
		int count = countByA_R(active, recommended);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCLicense WHERE ");
			query.append("active_ = ?");
			query.append(" AND ");
			query.append("recommended = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setBoolean(queryPos++, active);
			q.setBoolean(queryPos++, recommended);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scLicense);
			SCLicense[] array = new SCLicenseImpl[3];
			array[0] = (SCLicense)objArray[0];
			array[1] = (SCLicense)objArray[1];
			array[2] = (SCLicense)objArray[2];

			return array;
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
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCLicense ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
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

	public void removeByActive(boolean active) throws SystemException {
		Iterator itr = findByActive(active).iterator();

		while (itr.hasNext()) {
			SCLicense scLicense = (SCLicense)itr.next();
			remove(scLicense);
		}
	}

	public void removeByA_R(boolean active, boolean recommended)
		throws SystemException {
		Iterator itr = findByA_R(active, recommended).iterator();

		while (itr.hasNext()) {
			SCLicense scLicense = (SCLicense)itr.next();
			remove(scLicense);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((SCLicense)itr.next());
		}
	}

	public int countByActive(boolean active) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCLicense WHERE ");
			query.append("active_ = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCLicense WHERE ");
			query.append("active_ = ?");
			query.append(" AND ");
			query.append("recommended = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
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
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCLicense");

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

	private static Log _log = LogFactory.getLog(SCLicensePersistenceImpl.class);
}