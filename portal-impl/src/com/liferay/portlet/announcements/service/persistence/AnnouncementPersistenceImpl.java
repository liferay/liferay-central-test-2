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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.announcements.NoSuchAnnouncementException;
import com.liferay.portlet.announcements.model.Announcement;
import com.liferay.portlet.announcements.model.impl.AnnouncementImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementModelImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="AnnouncementPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementPersistenceImpl extends BasePersistence
	implements AnnouncementPersistence {
	public Announcement create(long announcementId) {
		Announcement announcement = new AnnouncementImpl();

		announcement.setNew(true);
		announcement.setPrimaryKey(announcementId);

		String uuid = PortalUUIDUtil.generate();

		announcement.setUuid(uuid);

		return announcement;
	}

	public Announcement remove(long announcementId)
		throws NoSuchAnnouncementException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Announcement announcement = (Announcement)session.get(AnnouncementImpl.class,
					new Long(announcementId));

			if (announcement == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Announcement exists with the primary key " +
						announcementId);
				}

				throw new NoSuchAnnouncementException(
					"No Announcement exists with the primary key " +
					announcementId);
			}

			return remove(announcement);
		}
		catch (NoSuchAnnouncementException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Announcement remove(Announcement announcement)
		throws SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(announcement);
		}

		announcement = removeImpl(announcement);

		if (listener != null) {
			listener.onAfterRemove(announcement);
		}

		return announcement;
	}

	protected Announcement removeImpl(Announcement announcement)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(announcement);

			session.flush();

			return announcement;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(Announcement.class.getName());
		}
	}

	public Announcement update(Announcement announcement)
		throws SystemException {
		return update(announcement, false);
	}

	public Announcement update(Announcement announcement, boolean merge)
		throws SystemException {
		ModelListener listener = _getListener();

		boolean isNew = announcement.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(announcement);
			}
			else {
				listener.onBeforeUpdate(announcement);
			}
		}

		announcement = updateImpl(announcement, merge);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(announcement);
			}
			else {
				listener.onAfterUpdate(announcement);
			}
		}

		return announcement;
	}

	public Announcement updateImpl(
		com.liferay.portlet.announcements.model.Announcement announcement,
		boolean merge) throws SystemException {
		if (Validator.isNull(announcement.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			announcement.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(announcement);
			}
			else {
				if (announcement.isNew()) {
					session.save(announcement);
				}
			}

			session.flush();

			announcement.setNew(false);

			return announcement;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(Announcement.class.getName());
		}
	}

	public Announcement findByPrimaryKey(long announcementId)
		throws NoSuchAnnouncementException, SystemException {
		Announcement announcement = fetchByPrimaryKey(announcementId);

		if (announcement == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Announcement exists with the primary key " +
					announcementId);
			}

			throw new NoSuchAnnouncementException(
				"No Announcement exists with the primary key " +
				announcementId);
		}

		return announcement;
	}

	public Announcement fetchByPrimaryKey(long announcementId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Announcement)session.get(AnnouncementImpl.class,
				new Long(announcementId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Announcement> findByUuid(String uuid) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementModelImpl.CACHE_ENABLED;
		String finderClassName = Announcement.class.getName();
		String finderMethodName = "findByUuid";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { uuid };

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
					"FROM com.liferay.portlet.announcements.model.Announcement WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("priority ASC, ");
				query.append("modifiedDate ASC");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				if (uuid != null) {
					q.setString(queryPos++, uuid);
				}

				List<Announcement> list = q.list();

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
			return (List<Announcement>)result;
		}
	}

	public List<Announcement> findByUuid(String uuid, int begin, int end)
		throws SystemException {
		return findByUuid(uuid, begin, end, null);
	}

	public List<Announcement> findByUuid(String uuid, int begin, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementModelImpl.CACHE_ENABLED;
		String finderClassName = Announcement.class.getName();
		String finderMethodName = "findByUuid";
		String[] finderParams = new String[] {
				String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				uuid,
				
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
					"FROM com.liferay.portlet.announcements.model.Announcement WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("priority ASC, ");
					query.append("modifiedDate ASC");
				}

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				if (uuid != null) {
					q.setString(queryPos++, uuid);
				}

				List<Announcement> list = (List<Announcement>)QueryUtil.list(q,
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
			return (List<Announcement>)result;
		}
	}

	public Announcement findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchAnnouncementException, SystemException {
		List<Announcement> list = findByUuid(uuid, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No Announcement exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAnnouncementException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Announcement findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchAnnouncementException, SystemException {
		int count = countByUuid(uuid);

		List<Announcement> list = findByUuid(uuid, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No Announcement exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAnnouncementException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Announcement[] findByUuid_PrevAndNext(long announcementId,
		String uuid, OrderByComparator obc)
		throws NoSuchAnnouncementException, SystemException {
		Announcement announcement = findByPrimaryKey(announcementId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.announcements.model.Announcement WHERE ");

			if (uuid == null) {
				query.append("uuid_ IS NULL");
			}
			else {
				query.append("uuid_ = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("priority ASC, ");
				query.append("modifiedDate ASC");
			}

			Query q = session.createQuery(query.toString());

			int queryPos = 0;

			if (uuid != null) {
				q.setString(queryPos++, uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					announcement);

			Announcement[] array = new AnnouncementImpl[3];

			array[0] = (Announcement)objArray[0];
			array[1] = (Announcement)objArray[1];
			array[2] = (Announcement)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Announcement> findByC_C(long classNameId, long classPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementModelImpl.CACHE_ENABLED;
		String finderClassName = Announcement.class.getName();
		String finderMethodName = "findByC_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
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
					"FROM com.liferay.portlet.announcements.model.Announcement WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("priority ASC, ");
				query.append("modifiedDate ASC");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classNameId);

				q.setLong(queryPos++, classPK);

				List<Announcement> list = q.list();

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
			return (List<Announcement>)result;
		}
	}

	public List<Announcement> findByC_C(long classNameId, long classPK,
		int begin, int end) throws SystemException {
		return findByC_C(classNameId, classPK, begin, end, null);
	}

	public List<Announcement> findByC_C(long classNameId, long classPK,
		int begin, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementModelImpl.CACHE_ENABLED;
		String finderClassName = Announcement.class.getName();
		String finderMethodName = "findByC_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK),
				
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
					"FROM com.liferay.portlet.announcements.model.Announcement WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("priority ASC, ");
					query.append("modifiedDate ASC");
				}

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classNameId);

				q.setLong(queryPos++, classPK);

				List<Announcement> list = (List<Announcement>)QueryUtil.list(q,
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
			return (List<Announcement>)result;
		}
	}

	public Announcement findByC_C_First(long classNameId, long classPK,
		OrderByComparator obc)
		throws NoSuchAnnouncementException, SystemException {
		List<Announcement> list = findByC_C(classNameId, classPK, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No Announcement exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAnnouncementException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Announcement findByC_C_Last(long classNameId, long classPK,
		OrderByComparator obc)
		throws NoSuchAnnouncementException, SystemException {
		int count = countByC_C(classNameId, classPK);

		List<Announcement> list = findByC_C(classNameId, classPK, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No Announcement exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAnnouncementException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Announcement[] findByC_C_PrevAndNext(long announcementId,
		long classNameId, long classPK, OrderByComparator obc)
		throws NoSuchAnnouncementException, SystemException {
		Announcement announcement = findByPrimaryKey(announcementId);

		int count = countByC_C(classNameId, classPK);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.announcements.model.Announcement WHERE ");

			query.append("classNameId = ?");

			query.append(" AND ");

			query.append("classPK = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("priority ASC, ");
				query.append("modifiedDate ASC");
			}

			Query q = session.createQuery(query.toString());

			int queryPos = 0;

			q.setLong(queryPos++, classNameId);

			q.setLong(queryPos++, classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					announcement);

			Announcement[] array = new AnnouncementImpl[3];

			array[0] = (Announcement)objArray[0];
			array[1] = (Announcement)objArray[1];
			array[2] = (Announcement)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Announcement> findByC_C_A(long classNameId, long classPK,
		boolean alert) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementModelImpl.CACHE_ENABLED;
		String finderClassName = Announcement.class.getName();
		String finderMethodName = "findByC_C_A";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK), Boolean.valueOf(alert)
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
					"FROM com.liferay.portlet.announcements.model.Announcement WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("alert = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("priority ASC, ");
				query.append("modifiedDate ASC");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classNameId);

				q.setLong(queryPos++, classPK);

				q.setBoolean(queryPos++, alert);

				List<Announcement> list = q.list();

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
			return (List<Announcement>)result;
		}
	}

	public List<Announcement> findByC_C_A(long classNameId, long classPK,
		boolean alert, int begin, int end) throws SystemException {
		return findByC_C_A(classNameId, classPK, alert, begin, end, null);
	}

	public List<Announcement> findByC_C_A(long classNameId, long classPK,
		boolean alert, int begin, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementModelImpl.CACHE_ENABLED;
		String finderClassName = Announcement.class.getName();
		String finderMethodName = "findByC_C_A";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK), Boolean.valueOf(alert),
				
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
					"FROM com.liferay.portlet.announcements.model.Announcement WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("alert = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("priority ASC, ");
					query.append("modifiedDate ASC");
				}

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classNameId);

				q.setLong(queryPos++, classPK);

				q.setBoolean(queryPos++, alert);

				List<Announcement> list = (List<Announcement>)QueryUtil.list(q,
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
			return (List<Announcement>)result;
		}
	}

	public Announcement findByC_C_A_First(long classNameId, long classPK,
		boolean alert, OrderByComparator obc)
		throws NoSuchAnnouncementException, SystemException {
		List<Announcement> list = findByC_C_A(classNameId, classPK, alert, 0,
				1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No Announcement exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(", ");
			msg.append("alert=" + alert);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAnnouncementException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Announcement findByC_C_A_Last(long classNameId, long classPK,
		boolean alert, OrderByComparator obc)
		throws NoSuchAnnouncementException, SystemException {
		int count = countByC_C_A(classNameId, classPK, alert);

		List<Announcement> list = findByC_C_A(classNameId, classPK, alert,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No Announcement exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(", ");
			msg.append("alert=" + alert);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAnnouncementException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Announcement[] findByC_C_A_PrevAndNext(long announcementId,
		long classNameId, long classPK, boolean alert, OrderByComparator obc)
		throws NoSuchAnnouncementException, SystemException {
		Announcement announcement = findByPrimaryKey(announcementId);

		int count = countByC_C_A(classNameId, classPK, alert);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.announcements.model.Announcement WHERE ");

			query.append("classNameId = ?");

			query.append(" AND ");

			query.append("classPK = ?");

			query.append(" AND ");

			query.append("alert = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("priority ASC, ");
				query.append("modifiedDate ASC");
			}

			Query q = session.createQuery(query.toString());

			int queryPos = 0;

			q.setLong(queryPos++, classNameId);

			q.setLong(queryPos++, classPK);

			q.setBoolean(queryPos++, alert);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					announcement);

			Announcement[] array = new AnnouncementImpl[3];

			array[0] = (Announcement)objArray[0];
			array[1] = (Announcement)objArray[1];
			array[2] = (Announcement)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Announcement> findByUserId(long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementModelImpl.CACHE_ENABLED;
		String finderClassName = Announcement.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

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
					"FROM com.liferay.portlet.announcements.model.Announcement WHERE ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("priority ASC, ");
				query.append("modifiedDate ASC");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, userId);

				List<Announcement> list = q.list();

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
			return (List<Announcement>)result;
		}
	}

	public List<Announcement> findByUserId(long userId, int begin, int end)
		throws SystemException {
		return findByUserId(userId, begin, end, null);
	}

	public List<Announcement> findByUserId(long userId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementModelImpl.CACHE_ENABLED;
		String finderClassName = Announcement.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
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
					"FROM com.liferay.portlet.announcements.model.Announcement WHERE ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("priority ASC, ");
					query.append("modifiedDate ASC");
				}

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, userId);

				List<Announcement> list = (List<Announcement>)QueryUtil.list(q,
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
			return (List<Announcement>)result;
		}
	}

	public Announcement findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchAnnouncementException, SystemException {
		List<Announcement> list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No Announcement exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAnnouncementException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Announcement findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchAnnouncementException, SystemException {
		int count = countByUserId(userId);

		List<Announcement> list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No Announcement exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAnnouncementException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Announcement[] findByUserId_PrevAndNext(long announcementId,
		long userId, OrderByComparator obc)
		throws NoSuchAnnouncementException, SystemException {
		Announcement announcement = findByPrimaryKey(announcementId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.announcements.model.Announcement WHERE ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("priority ASC, ");
				query.append("modifiedDate ASC");
			}

			Query q = session.createQuery(query.toString());

			int queryPos = 0;

			q.setLong(queryPos++, userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					announcement);

			Announcement[] array = new AnnouncementImpl[3];

			array[0] = (Announcement)objArray[0];
			array[1] = (Announcement)objArray[1];
			array[2] = (Announcement)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Announcement> findWithDynamicQuery(
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

	public List<Announcement> findWithDynamicQuery(
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

	public List<Announcement> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Announcement> findAll(int begin, int end)
		throws SystemException {
		return findAll(begin, end, null);
	}

	public List<Announcement> findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementModelImpl.CACHE_ENABLED;
		String finderClassName = Announcement.class.getName();
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
					"FROM com.liferay.portlet.announcements.model.Announcement ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("priority ASC, ");
					query.append("modifiedDate ASC");
				}

				Query q = session.createQuery(query.toString());

				List<Announcement> list = (List<Announcement>)QueryUtil.list(q,
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
			return (List<Announcement>)result;
		}
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (Announcement announcement : findByUuid(uuid)) {
			remove(announcement);
		}
	}

	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (Announcement announcement : findByC_C(classNameId, classPK)) {
			remove(announcement);
		}
	}

	public void removeByC_C_A(long classNameId, long classPK, boolean alert)
		throws SystemException {
		for (Announcement announcement : findByC_C_A(classNameId, classPK, alert)) {
			remove(announcement);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (Announcement announcement : findByUserId(userId)) {
			remove(announcement);
		}
	}

	public void removeAll() throws SystemException {
		for (Announcement announcement : findAll()) {
			remove(announcement);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementModelImpl.CACHE_ENABLED;
		String finderClassName = Announcement.class.getName();
		String finderMethodName = "countByUuid";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { uuid };

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
					"FROM com.liferay.portlet.announcements.model.Announcement WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				if (uuid != null) {
					q.setString(queryPos++, uuid);
				}

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

	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementModelImpl.CACHE_ENABLED;
		String finderClassName = Announcement.class.getName();
		String finderMethodName = "countByC_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
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
					"FROM com.liferay.portlet.announcements.model.Announcement WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classNameId);

				q.setLong(queryPos++, classPK);

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

	public int countByC_C_A(long classNameId, long classPK, boolean alert)
		throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementModelImpl.CACHE_ENABLED;
		String finderClassName = Announcement.class.getName();
		String finderMethodName = "countByC_C_A";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK), Boolean.valueOf(alert)
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
					"FROM com.liferay.portlet.announcements.model.Announcement WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("alert = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classNameId);

				q.setLong(queryPos++, classPK);

				q.setBoolean(queryPos++, alert);

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

	public int countByUserId(long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementModelImpl.CACHE_ENABLED;
		String finderClassName = Announcement.class.getName();
		String finderMethodName = "countByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

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
					"FROM com.liferay.portlet.announcements.model.Announcement WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, userId);

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
		boolean finderClassNameCacheEnabled = AnnouncementModelImpl.CACHE_ENABLED;
		String finderClassName = Announcement.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.announcements.model.Announcement");

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
				"value.object.listener.com.liferay.portlet.announcements.model.Announcement"));
	private static Log _log = LogFactory.getLog(AnnouncementPersistenceImpl.class);
}