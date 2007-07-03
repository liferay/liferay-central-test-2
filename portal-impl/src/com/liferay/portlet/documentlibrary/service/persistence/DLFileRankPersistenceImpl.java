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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.portlet.documentlibrary.NoSuchFileRankException;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.impl.DLFileRankImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="DLFileRankPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DLFileRankPersistenceImpl extends BasePersistence
	implements DLFileRankPersistence {
	public DLFileRank create(long fileRankId) {
		DLFileRank dlFileRank = new DLFileRankImpl();
		dlFileRank.setNew(true);
		dlFileRank.setPrimaryKey(fileRankId);

		return dlFileRank;
	}

	public DLFileRank remove(long fileRankId)
		throws NoSuchFileRankException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileRank dlFileRank = (DLFileRank)session.get(DLFileRankImpl.class,
					new Long(fileRankId));

			if (dlFileRank == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No DLFileRank exists with the primary key " +
						fileRankId);
				}

				throw new NoSuchFileRankException(
					"No DLFileRank exists with the primary key " + fileRankId);
			}

			return remove(dlFileRank);
		}
		catch (NoSuchFileRankException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileRank remove(DLFileRank dlFileRank) throws SystemException {
		FinderCache.clearCache(DLFileRank.class.getName());

		Session session = null;

		try {
			session = openSession();
			session.delete(dlFileRank);
			session.flush();

			return dlFileRank;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileRank update(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank)
		throws SystemException {
		return update(dlFileRank, false);
	}

	public DLFileRank update(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank,
		boolean saveOrUpdate) throws SystemException {
		FinderCache.clearCache(DLFileRank.class.getName());

		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(dlFileRank);
			}
			else {
				if (dlFileRank.isNew()) {
					session.save(dlFileRank);
				}
			}

			session.flush();
			dlFileRank.setNew(false);

			return dlFileRank;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileRank findByPrimaryKey(long fileRankId)
		throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = fetchByPrimaryKey(fileRankId);

		if (dlFileRank == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No DLFileRank exists with the primary key " +
					fileRankId);
			}

			throw new NoSuchFileRankException(
				"No DLFileRank exists with the primary key " + fileRankId);
		}

		return dlFileRank;
	}

	public DLFileRank fetchByPrimaryKey(long fileRankId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (DLFileRank)session.get(DLFileRankImpl.class,
				new Long(fileRankId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserId(long userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");
			query.append("userId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, userId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserId(long userId, int begin, int end)
		throws SystemException {
		return findByUserId(userId, begin, end, null);
	}

	public List findByUserId(long userId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");
			query.append("userId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, userId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileRank findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchFileRankException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No DLFileRank exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("userId=");
			msg.append(userId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return (DLFileRank)list.get(0);
		}
	}

	public DLFileRank findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchFileRankException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No DLFileRank exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("userId=");
			msg.append(userId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return (DLFileRank)list.get(0);
		}
	}

	public DLFileRank[] findByUserId_PrevAndNext(long fileRankId, long userId,
		OrderByComparator obc) throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = findByPrimaryKey(fileRankId);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");
			query.append("userId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileRank);
			DLFileRank[] array = new DLFileRankImpl[3];
			array[0] = (DLFileRank)objArray[0];
			array[1] = (DLFileRank)objArray[1];
			array[2] = (DLFileRank)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByF_N(long folderId, String name) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");
			query.append("folderId = ?");
			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, folderId);

			if (name != null) {
				q.setString(queryPos++, name);
			}

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByF_N(long folderId, String name, int begin, int end)
		throws SystemException {
		return findByF_N(folderId, name, begin, end, null);
	}

	public List findByF_N(long folderId, String name, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");
			query.append("folderId = ?");
			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, folderId);

			if (name != null) {
				q.setString(queryPos++, name);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileRank findByF_N_First(long folderId, String name,
		OrderByComparator obc) throws NoSuchFileRankException, SystemException {
		List list = findByF_N(folderId, name, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No DLFileRank exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("folderId=");
			msg.append(folderId);
			msg.append(", ");
			msg.append("name=");
			msg.append(name);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return (DLFileRank)list.get(0);
		}
	}

	public DLFileRank findByF_N_Last(long folderId, String name,
		OrderByComparator obc) throws NoSuchFileRankException, SystemException {
		int count = countByF_N(folderId, name);
		List list = findByF_N(folderId, name, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No DLFileRank exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("folderId=");
			msg.append(folderId);
			msg.append(", ");
			msg.append("name=");
			msg.append(name);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return (DLFileRank)list.get(0);
		}
	}

	public DLFileRank[] findByF_N_PrevAndNext(long fileRankId, long folderId,
		String name, OrderByComparator obc)
		throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = findByPrimaryKey(fileRankId);
		int count = countByF_N(folderId, name);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");
			query.append("folderId = ?");
			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, folderId);

			if (name != null) {
				q.setString(queryPos++, name);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileRank);
			DLFileRank[] array = new DLFileRankImpl[3];
			array[0] = (DLFileRank)objArray[0];
			array[1] = (DLFileRank)objArray[1];
			array[2] = (DLFileRank)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileRank findByC_U_F_N(long companyId, long userId, long folderId,
		String name) throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = fetchByC_U_F_N(companyId, userId, folderId, name);

		if (dlFileRank == null) {
			StringMaker msg = new StringMaker();
			msg.append("No DLFileRank exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("userId=");
			msg.append(userId);
			msg.append(", ");
			msg.append("folderId=");
			msg.append(folderId);
			msg.append(", ");
			msg.append("name=");
			msg.append(name);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileRankException(msg.toString());
		}

		return dlFileRank;
	}

	public DLFileRank fetchByC_U_F_N(long companyId, long userId,
		long folderId, String name) throws SystemException {
		String finderClassName = DLFileRank.class.getName();
		String finderMethodName = "fetchByC_U_F_N";
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(userId), new Long(folderId), name
			};
		Object result = FinderCache.getResult(finderClassName,
				finderMethodName, finderArgs);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();
				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");
				query.append("companyId = ?");
				query.append(" AND ");
				query.append("userId = ?");
				query.append(" AND ");
				query.append("folderId = ?");
				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");
				query.append("ORDER BY ");
				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());
				int queryPos = 0;
				q.setLong(queryPos++, companyId);
				q.setLong(queryPos++, userId);
				q.setLong(queryPos++, folderId);

				if (name != null) {
					q.setString(queryPos++, name);
				}

				List list = q.list();

				if (list.size() == 0) {
					return null;
				}

				DLFileRank dlFileRank = (DLFileRank)list.get(0);
				FinderCache.putResult(finderClassName, finderMethodName,
					finderArgs, dlFileRank);

				return dlFileRank;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (DLFileRank)result;
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
				"FROM com.liferay.portlet.documentlibrary.model.DLFileRank ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate DESC");
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

	public void removeByUserId(long userId) throws SystemException {
		Iterator itr = findByUserId(userId).iterator();

		while (itr.hasNext()) {
			DLFileRank dlFileRank = (DLFileRank)itr.next();
			remove(dlFileRank);
		}
	}

	public void removeByF_N(long folderId, String name)
		throws SystemException {
		Iterator itr = findByF_N(folderId, name).iterator();

		while (itr.hasNext()) {
			DLFileRank dlFileRank = (DLFileRank)itr.next();
			remove(dlFileRank);
		}
	}

	public void removeByC_U_F_N(long companyId, long userId, long folderId,
		String name) throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = findByC_U_F_N(companyId, userId, folderId, name);
		remove(dlFileRank);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((DLFileRank)itr.next());
		}
	}

	public int countByUserId(long userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");
			query.append("userId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, userId);

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

	public int countByF_N(long folderId, String name) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");
			query.append("folderId = ?");
			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, folderId);

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

	public int countByC_U_F_N(long companyId, long userId, long folderId,
		String name) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("userId = ?");
			query.append(" AND ");
			query.append("folderId = ?");
			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, userId);
			q.setLong(queryPos++, folderId);

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
			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileRank");

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

	private static Log _log = LogFactory.getLog(DLFileRankPersistenceImpl.class);
}