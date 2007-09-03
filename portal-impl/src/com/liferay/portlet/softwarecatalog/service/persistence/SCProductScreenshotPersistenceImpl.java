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

import com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;
import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductScreenshotImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="SCProductScreenshotPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCProductScreenshotPersistenceImpl extends BasePersistence
	implements SCProductScreenshotPersistence {
	public SCProductScreenshot create(long productScreenshotId) {
		SCProductScreenshot scProductScreenshot = new SCProductScreenshotImpl();
		scProductScreenshot.setNew(true);
		scProductScreenshot.setPrimaryKey(productScreenshotId);

		return scProductScreenshot;
	}

	public SCProductScreenshot remove(long productScreenshotId)
		throws NoSuchProductScreenshotException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCProductScreenshot scProductScreenshot = (SCProductScreenshot)session.get(SCProductScreenshotImpl.class,
					new Long(productScreenshotId));

			if (scProductScreenshot == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No SCProductScreenshot exists with the primary key " +
						productScreenshotId);
				}

				throw new NoSuchProductScreenshotException(
					"No SCProductScreenshot exists with the primary key " +
					productScreenshotId);
			}

			return remove(scProductScreenshot);
		}
		catch (NoSuchProductScreenshotException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCProductScreenshot remove(SCProductScreenshot scProductScreenshot)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(scProductScreenshot);
			session.flush();

			return scProductScreenshot;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
			FinderCache.clearCache(SCProductScreenshot.class.getName());
		}
	}

	public SCProductScreenshot update(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot)
		throws SystemException {
		return update(scProductScreenshot, false);
	}

	public SCProductScreenshot update(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot,
		boolean saveOrUpdate) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(scProductScreenshot);
			}
			else {
				if (scProductScreenshot.isNew()) {
					session.save(scProductScreenshot);
				}
			}

			session.flush();
			scProductScreenshot.setNew(false);

			return scProductScreenshot;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
			FinderCache.clearCache(SCProductScreenshot.class.getName());
		}
	}

	public SCProductScreenshot findByPrimaryKey(long productScreenshotId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByPrimaryKey(productScreenshotId);

		if (scProductScreenshot == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SCProductScreenshot exists with the primary key " +
					productScreenshotId);
			}

			throw new NoSuchProductScreenshotException(
				"No SCProductScreenshot exists with the primary key " +
				productScreenshotId);
		}

		return scProductScreenshot;
	}

	public SCProductScreenshot fetchByPrimaryKey(long productScreenshotId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (SCProductScreenshot)session.get(SCProductScreenshotImpl.class,
				new Long(productScreenshotId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByProductEntryId(long productEntryId)
		throws SystemException {
		String finderClassName = SCProductScreenshot.class.getName();
		String finderMethodName = "findByProductEntryId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(productEntryId) };
		Object result = FinderCache.getResult(finderClassName,
				finderMethodName, finderParams, finderArgs, getSessionFactory());

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");
				query.append("productEntryId = ?");
				query.append(" ");
				query.append("ORDER BY ");
				query.append("productEntryId ASC").append(", ");
				query.append("priority ASC");

				Query q = session.createQuery(query.toString());
				int queryPos = 0;
				q.setLong(queryPos++, productEntryId);

				List list = q.list();
				FinderCache.putResult(finderClassName, finderMethodName,
					finderParams, finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List)result;
		}
	}

	public List findByProductEntryId(long productEntryId, int begin, int end)
		throws SystemException {
		return findByProductEntryId(productEntryId, begin, end, null);
	}

	public List findByProductEntryId(long productEntryId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		String finderClassName = SCProductScreenshot.class.getName();
		String finderMethodName = "findByProductEntryId";
		String[] finderParams = new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(productEntryId), String.valueOf(begin),
				String.valueOf(end), String.valueOf(obc)
			};
		Object result = FinderCache.getResult(finderClassName,
				finderMethodName, finderParams, finderArgs, getSessionFactory());

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");
				query.append("productEntryId = ?");
				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}
				else {
					query.append("ORDER BY ");
					query.append("productEntryId ASC").append(", ");
					query.append("priority ASC");
				}

				Query q = session.createQuery(query.toString());
				int queryPos = 0;
				q.setLong(queryPos++, productEntryId);

				List list = QueryUtil.list(q, getDialect(), begin, end);
				FinderCache.putResult(finderClassName, finderMethodName,
					finderParams, finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List)result;
		}
	}

	public SCProductScreenshot findByProductEntryId_First(long productEntryId,
		OrderByComparator obc)
		throws NoSuchProductScreenshotException, SystemException {
		List list = findByProductEntryId(productEntryId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No SCProductScreenshot exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("productEntryId=");
			msg.append(productEntryId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchProductScreenshotException(msg.toString());
		}
		else {
			return (SCProductScreenshot)list.get(0);
		}
	}

	public SCProductScreenshot findByProductEntryId_Last(long productEntryId,
		OrderByComparator obc)
		throws NoSuchProductScreenshotException, SystemException {
		int count = countByProductEntryId(productEntryId);
		List list = findByProductEntryId(productEntryId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No SCProductScreenshot exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("productEntryId=");
			msg.append(productEntryId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchProductScreenshotException(msg.toString());
		}
		else {
			return (SCProductScreenshot)list.get(0);
		}
	}

	public SCProductScreenshot[] findByProductEntryId_PrevAndNext(
		long productScreenshotId, long productEntryId, OrderByComparator obc)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = findByPrimaryKey(productScreenshotId);
		int count = countByProductEntryId(productEntryId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");
			query.append("productEntryId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("productEntryId ASC").append(", ");
				query.append("priority ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, productEntryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scProductScreenshot);
			SCProductScreenshot[] array = new SCProductScreenshotImpl[3];
			array[0] = (SCProductScreenshot)objArray[0];
			array[1] = (SCProductScreenshot)objArray[1];
			array[2] = (SCProductScreenshot)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCProductScreenshot findByP_P(long productEntryId, int priority)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByP_P(productEntryId,
				priority);

		if (scProductScreenshot == null) {
			StringMaker msg = new StringMaker();
			msg.append("No SCProductScreenshot exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("productEntryId=");
			msg.append(productEntryId);
			msg.append(", ");
			msg.append("priority=");
			msg.append(priority);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductScreenshotException(msg.toString());
		}

		return scProductScreenshot;
	}

	public SCProductScreenshot fetchByP_P(long productEntryId, int priority)
		throws SystemException {
		String finderClassName = SCProductScreenshot.class.getName();
		String finderMethodName = "fetchByP_P";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(productEntryId), new Integer(priority)
			};
		Object result = FinderCache.getResult(finderClassName,
				finderMethodName, finderParams, finderArgs, getSessionFactory());

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");
				query.append("productEntryId = ?");
				query.append(" AND ");
				query.append("priority = ?");
				query.append(" ");
				query.append("ORDER BY ");
				query.append("productEntryId ASC").append(", ");
				query.append("priority ASC");

				Query q = session.createQuery(query.toString());
				int queryPos = 0;
				q.setLong(queryPos++, productEntryId);
				q.setInteger(queryPos++, priority);

				List list = q.list();
				FinderCache.putResult(finderClassName, finderMethodName,
					finderParams, finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return (SCProductScreenshot)list.get(0);
				}
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List list = (List)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return (SCProductScreenshot)list.get(0);
			}
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
		String finderClassName = SCProductScreenshot.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
			};
		Object result = FinderCache.getResult(finderClassName,
				finderMethodName, finderParams, finderArgs, getSessionFactory());

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}
				else {
					query.append("ORDER BY ");
					query.append("productEntryId ASC").append(", ");
					query.append("priority ASC");
				}

				Query q = session.createQuery(query.toString());
				List list = QueryUtil.list(q, getDialect(), begin, end);

				if (obc == null) {
					Collections.sort(list);
				}

				FinderCache.putResult(finderClassName, finderMethodName,
					finderParams, finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List)result;
		}
	}

	public void removeByProductEntryId(long productEntryId)
		throws SystemException {
		Iterator itr = findByProductEntryId(productEntryId).iterator();

		while (itr.hasNext()) {
			SCProductScreenshot scProductScreenshot = (SCProductScreenshot)itr.next();
			remove(scProductScreenshot);
		}
	}

	public void removeByP_P(long productEntryId, int priority)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = findByP_P(productEntryId,
				priority);
		remove(scProductScreenshot);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((SCProductScreenshot)itr.next());
		}
	}

	public int countByProductEntryId(long productEntryId)
		throws SystemException {
		String finderClassName = SCProductScreenshot.class.getName();
		String finderMethodName = "countByProductEntryId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(productEntryId) };
		Object result = FinderCache.getResult(finderClassName,
				finderMethodName, finderParams, finderArgs, getSessionFactory());

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();
				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");
				query.append("productEntryId = ?");
				query.append(" ");

				Query q = session.createQuery(query.toString());
				int queryPos = 0;
				q.setLong(queryPos++, productEntryId);

				Long count = null;
				Iterator itr = q.list().iterator();

				if (itr.hasNext()) {
					count = (Long)itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassName, finderMethodName,
					finderParams, finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByP_P(long productEntryId, int priority)
		throws SystemException {
		String finderClassName = SCProductScreenshot.class.getName();
		String finderMethodName = "countByP_P";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(productEntryId), new Integer(priority)
			};
		Object result = FinderCache.getResult(finderClassName,
				finderMethodName, finderParams, finderArgs, getSessionFactory());

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();
				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");
				query.append("productEntryId = ?");
				query.append(" AND ");
				query.append("priority = ?");
				query.append(" ");

				Query q = session.createQuery(query.toString());
				int queryPos = 0;
				q.setLong(queryPos++, productEntryId);
				q.setInteger(queryPos++, priority);

				Long count = null;
				Iterator itr = q.list().iterator();

				if (itr.hasNext()) {
					count = (Long)itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassName, finderMethodName,
					finderParams, finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countAll() throws SystemException {
		String finderClassName = SCProductScreenshot.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };
		Object result = FinderCache.getResult(finderClassName,
				finderMethodName, finderParams, finderArgs, getSessionFactory());

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();
				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot");

				Query q = session.createQuery(query.toString());
				Long count = null;
				Iterator itr = q.list().iterator();

				if (itr.hasNext()) {
					count = (Long)itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassName, finderMethodName,
					finderParams, finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(SCProductScreenshotPersistenceImpl.class);
}