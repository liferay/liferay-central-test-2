/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.announcements.NoSuchAnnouncementFlagException;
import com.liferay.portlet.announcements.model.AnnouncementFlag;
import com.liferay.portlet.announcements.model.impl.AnnouncementFlagImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementFlagModelImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="AnnouncementFlagPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementFlagPersistenceImpl extends BasePersistence
	implements AnnouncementFlagPersistence {
	public AnnouncementFlag create(long announcementFlagId) {
		AnnouncementFlag announcementFlag = new AnnouncementFlagImpl();

		announcementFlag.setNew(true);
		announcementFlag.setPrimaryKey(announcementFlagId);

		return announcementFlag;
	}

	public AnnouncementFlag remove(long announcementFlagId)
		throws NoSuchAnnouncementFlagException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AnnouncementFlag announcementFlag = (AnnouncementFlag)session.get(AnnouncementFlagImpl.class,
					new Long(announcementFlagId));

			if (announcementFlag == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No AnnouncementFlag exists with the primary key " +
						announcementFlagId);
				}

				throw new NoSuchAnnouncementFlagException(
					"No AnnouncementFlag exists with the primary key " +
					announcementFlagId);
			}

			return remove(announcementFlag);
		}
		catch (NoSuchAnnouncementFlagException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AnnouncementFlag remove(AnnouncementFlag announcementFlag)
		throws SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(announcementFlag);
		}

		announcementFlag = removeImpl(announcementFlag);

		if (listener != null) {
			listener.onAfterRemove(announcementFlag);
		}

		return announcementFlag;
	}

	protected AnnouncementFlag removeImpl(AnnouncementFlag announcementFlag)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(announcementFlag);

			session.flush();

			return announcementFlag;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(AnnouncementFlag.class.getName());
		}
	}

	public AnnouncementFlag update(AnnouncementFlag announcementFlag)
		throws SystemException {
		return update(announcementFlag, false);
	}

	public AnnouncementFlag update(AnnouncementFlag announcementFlag,
		boolean merge) throws SystemException {
		ModelListener listener = _getListener();

		boolean isNew = announcementFlag.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(announcementFlag);
			}
			else {
				listener.onBeforeUpdate(announcementFlag);
			}
		}

		announcementFlag = updateImpl(announcementFlag, merge);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(announcementFlag);
			}
			else {
				listener.onAfterUpdate(announcementFlag);
			}
		}

		return announcementFlag;
	}

	public AnnouncementFlag updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementFlag announcementFlag,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(announcementFlag);
			}
			else {
				if (announcementFlag.isNew()) {
					session.save(announcementFlag);
				}
			}

			session.flush();

			announcementFlag.setNew(false);

			return announcementFlag;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(AnnouncementFlag.class.getName());
		}
	}

	public AnnouncementFlag findByPrimaryKey(long announcementFlagId)
		throws NoSuchAnnouncementFlagException, SystemException {
		AnnouncementFlag announcementFlag = fetchByPrimaryKey(announcementFlagId);

		if (announcementFlag == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No AnnouncementFlag exists with the primary key " +
					announcementFlagId);
			}

			throw new NoSuchAnnouncementFlagException(
				"No AnnouncementFlag exists with the primary key " +
				announcementFlagId);
		}

		return announcementFlag;
	}

	public AnnouncementFlag fetchByPrimaryKey(long announcementFlagId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (AnnouncementFlag)session.get(AnnouncementFlagImpl.class,
				new Long(announcementFlagId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AnnouncementFlag> findByAnnouncementId(long announcementId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementFlagModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementFlag.class.getName();
		String finderMethodName = "findByAnnouncementId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(announcementId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementFlag WHERE ");

				query.append("announcementId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("userId ASC, ");
				query.append("flagDate ASC");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, announcementId);

				List<AnnouncementFlag> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

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
			return (List<AnnouncementFlag>)result;
		}
	}

	public List<AnnouncementFlag> findByAnnouncementId(long announcementId,
		int begin, int end) throws SystemException {
		return findByAnnouncementId(announcementId, begin, end, null);
	}

	public List<AnnouncementFlag> findByAnnouncementId(long announcementId,
		int begin, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementFlagModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementFlag.class.getName();
		String finderMethodName = "findByAnnouncementId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(announcementId),
				
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementFlag WHERE ");

				query.append("announcementId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("userId ASC, ");
					query.append("flagDate ASC");
				}

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, announcementId);

				List<AnnouncementFlag> list = (List<AnnouncementFlag>)QueryUtil.list(q,
						getDialect(), begin, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

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
			return (List<AnnouncementFlag>)result;
		}
	}

	public AnnouncementFlag findByAnnouncementId_First(long announcementId,
		OrderByComparator obc)
		throws NoSuchAnnouncementFlagException, SystemException {
		List<AnnouncementFlag> list = findByAnnouncementId(announcementId, 0,
				1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No AnnouncementFlag exists with the key {");

			msg.append("announcementId=" + announcementId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAnnouncementFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementFlag findByAnnouncementId_Last(long announcementId,
		OrderByComparator obc)
		throws NoSuchAnnouncementFlagException, SystemException {
		int count = countByAnnouncementId(announcementId);

		List<AnnouncementFlag> list = findByAnnouncementId(announcementId,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No AnnouncementFlag exists with the key {");

			msg.append("announcementId=" + announcementId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAnnouncementFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementFlag[] findByAnnouncementId_PrevAndNext(
		long announcementFlagId, long announcementId, OrderByComparator obc)
		throws NoSuchAnnouncementFlagException, SystemException {
		AnnouncementFlag announcementFlag = findByPrimaryKey(announcementFlagId);

		int count = countByAnnouncementId(announcementId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.announcements.model.AnnouncementFlag WHERE ");

			query.append("announcementId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("userId ASC, ");
				query.append("flagDate ASC");
			}

			Query q = session.createQuery(query.toString());

			int queryPos = 0;

			q.setLong(queryPos++, announcementId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					announcementFlag);

			AnnouncementFlag[] array = new AnnouncementFlagImpl[3];

			array[0] = (AnnouncementFlag)objArray[0];
			array[1] = (AnnouncementFlag)objArray[1];
			array[2] = (AnnouncementFlag)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AnnouncementFlag findByU_A_F(long userId, long announcementId,
		int flag) throws NoSuchAnnouncementFlagException, SystemException {
		AnnouncementFlag announcementFlag = fetchByU_A_F(userId,
				announcementId, flag);

		if (announcementFlag == null) {
			StringMaker msg = new StringMaker();

			msg.append("No AnnouncementFlag exists with the key {");

			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("announcementId=" + announcementId);

			msg.append(", ");
			msg.append("flag=" + flag);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchAnnouncementFlagException(msg.toString());
		}

		return announcementFlag;
	}

	public AnnouncementFlag fetchByU_A_F(long userId, long announcementId,
		int flag) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementFlagModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementFlag.class.getName();
		String finderMethodName = "fetchByU_A_F";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(announcementId), new Integer(flag)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementFlag WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("announcementId = ?");

				query.append(" AND ");

				query.append("flag = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("userId ASC, ");
				query.append("flagDate ASC");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, userId);

				q.setLong(queryPos++, announcementId);

				q.setInteger(queryPos++, flag);

				List<AnnouncementFlag> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
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
			List<AnnouncementFlag> list = (List<AnnouncementFlag>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<AnnouncementFlag> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer) throws SystemException {
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

	public List<AnnouncementFlag> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer, int begin, int end)
		throws SystemException {
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

	public List<AnnouncementFlag> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AnnouncementFlag> findAll(int begin, int end)
		throws SystemException {
		return findAll(begin, end, null);
	}

	public List<AnnouncementFlag> findAll(int begin, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementFlagModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementFlag.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementFlag ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("userId ASC, ");
					query.append("flagDate ASC");
				}

				Query q = session.createQuery(query.toString());

				List<AnnouncementFlag> list = (List<AnnouncementFlag>)QueryUtil.list(q,
						getDialect(), begin, end);

				if (obc == null) {
					Collections.sort(list);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

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
			return (List<AnnouncementFlag>)result;
		}
	}

	public void removeByAnnouncementId(long announcementId)
		throws SystemException {
		for (AnnouncementFlag announcementFlag : findByAnnouncementId(
				announcementId)) {
			remove(announcementFlag);
		}
	}

	public void removeByU_A_F(long userId, long announcementId, int flag)
		throws NoSuchAnnouncementFlagException, SystemException {
		AnnouncementFlag announcementFlag = findByU_A_F(userId, announcementId,
				flag);

		remove(announcementFlag);
	}

	public void removeAll() throws SystemException {
		for (AnnouncementFlag announcementFlag : findAll()) {
			remove(announcementFlag);
		}
	}

	public int countByAnnouncementId(long announcementId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementFlagModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementFlag.class.getName();
		String finderMethodName = "countByAnnouncementId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(announcementId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementFlag WHERE ");

				query.append("announcementId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, announcementId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

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

	public int countByU_A_F(long userId, long announcementId, int flag)
		throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementFlagModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementFlag.class.getName();
		String finderMethodName = "countByU_A_F";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(announcementId), new Integer(flag)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.announcements.model.AnnouncementFlag WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("announcementId = ?");

				query.append(" AND ");

				query.append("flag = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, userId);

				q.setLong(queryPos++, announcementId);

				q.setInteger(queryPos++, flag);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

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
		boolean finderClassNameCacheEnabled = AnnouncementFlagModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementFlag.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.announcements.model.AnnouncementFlag");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

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

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.announcements.model.AnnouncementFlag"));
	private static Log _log = LogFactory.getLog(AnnouncementFlagPersistenceImpl.class);
}