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

import com.liferay.portlet.announcements.NoSuchAnnouncementEntryException;
import com.liferay.portlet.announcements.model.AnnouncementEntry;
import com.liferay.portlet.announcements.model.impl.AnnouncementEntryImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementEntryModelImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="AnnouncementEntryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementEntryPersistenceImpl extends BasePersistence
	implements AnnouncementEntryPersistence {
	public AnnouncementEntry create(long entryId) {
		AnnouncementEntry announcementEntry = new AnnouncementEntryImpl();

		announcementEntry.setNew(true);
		announcementEntry.setPrimaryKey(entryId);

		String uuid = PortalUUIDUtil.generate();

		announcementEntry.setUuid(uuid);

		return announcementEntry;
	}

	public AnnouncementEntry remove(long entryId)
		throws NoSuchAnnouncementEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AnnouncementEntry announcementEntry = (AnnouncementEntry)session.get(AnnouncementEntryImpl.class,
					new Long(entryId));

			if (announcementEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No AnnouncementEntry exists with the primary key " +
						entryId);
				}

				throw new NoSuchAnnouncementEntryException(
					"No AnnouncementEntry exists with the primary key " +
					entryId);
			}

			return remove(announcementEntry);
		}
		catch (NoSuchAnnouncementEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AnnouncementEntry remove(AnnouncementEntry announcementEntry)
		throws SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(announcementEntry);
		}

		announcementEntry = removeImpl(announcementEntry);

		if (listener != null) {
			listener.onAfterRemove(announcementEntry);
		}

		return announcementEntry;
	}

	protected AnnouncementEntry removeImpl(AnnouncementEntry announcementEntry)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(announcementEntry);

			session.flush();

			return announcementEntry;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(AnnouncementEntry.class.getName());
		}
	}

	public AnnouncementEntry update(AnnouncementEntry announcementEntry)
		throws SystemException {
		return update(announcementEntry, false);
	}

	public AnnouncementEntry update(AnnouncementEntry announcementEntry,
		boolean merge) throws SystemException {
		ModelListener listener = _getListener();

		boolean isNew = announcementEntry.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(announcementEntry);
			}
			else {
				listener.onBeforeUpdate(announcementEntry);
			}
		}

		announcementEntry = updateImpl(announcementEntry, merge);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(announcementEntry);
			}
			else {
				listener.onAfterUpdate(announcementEntry);
			}
		}

		return announcementEntry;
	}

	public AnnouncementEntry updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementEntry announcementEntry,
		boolean merge) throws SystemException {
		if (Validator.isNull(announcementEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			announcementEntry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(announcementEntry);
			}
			else {
				if (announcementEntry.isNew()) {
					session.save(announcementEntry);
				}
			}

			session.flush();

			announcementEntry.setNew(false);

			return announcementEntry;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(AnnouncementEntry.class.getName());
		}
	}

	public AnnouncementEntry findByPrimaryKey(long entryId)
		throws NoSuchAnnouncementEntryException, SystemException {
		AnnouncementEntry announcementEntry = fetchByPrimaryKey(entryId);

		if (announcementEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No AnnouncementEntry exists with the primary key " +
					entryId);
			}

			throw new NoSuchAnnouncementEntryException(
				"No AnnouncementEntry exists with the primary key " + entryId);
		}

		return announcementEntry;
	}

	public AnnouncementEntry fetchByPrimaryKey(long entryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (AnnouncementEntry)session.get(AnnouncementEntryImpl.class,
				new Long(entryId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AnnouncementEntry> findByUuid(String uuid)
		throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementEntryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementEntry.class.getName();
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
					"FROM com.liferay.portlet.announcements.model.AnnouncementEntry WHERE ");

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

				List<AnnouncementEntry> list = q.list();

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
			return (List<AnnouncementEntry>)result;
		}
	}

	public List<AnnouncementEntry> findByUuid(String uuid, int begin, int end)
		throws SystemException {
		return findByUuid(uuid, begin, end, null);
	}

	public List<AnnouncementEntry> findByUuid(String uuid, int begin, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementEntryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementEntry.class.getName();
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
					"FROM com.liferay.portlet.announcements.model.AnnouncementEntry WHERE ");

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

				List<AnnouncementEntry> list = (List<AnnouncementEntry>)QueryUtil.list(q,
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
			return (List<AnnouncementEntry>)result;
		}
	}

	public AnnouncementEntry findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchAnnouncementEntryException, SystemException {
		List<AnnouncementEntry> list = findByUuid(uuid, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No AnnouncementEntry exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAnnouncementEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementEntry findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchAnnouncementEntryException, SystemException {
		int count = countByUuid(uuid);

		List<AnnouncementEntry> list = findByUuid(uuid, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No AnnouncementEntry exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAnnouncementEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementEntry[] findByUuid_PrevAndNext(long entryId,
		String uuid, OrderByComparator obc)
		throws NoSuchAnnouncementEntryException, SystemException {
		AnnouncementEntry announcementEntry = findByPrimaryKey(entryId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.announcements.model.AnnouncementEntry WHERE ");

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
					announcementEntry);

			AnnouncementEntry[] array = new AnnouncementEntryImpl[3];

			array[0] = (AnnouncementEntry)objArray[0];
			array[1] = (AnnouncementEntry)objArray[1];
			array[2] = (AnnouncementEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AnnouncementEntry> findByC_C(long classNameId, long classPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementEntryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementEntry.class.getName();
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
					"FROM com.liferay.portlet.announcements.model.AnnouncementEntry WHERE ");

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

				List<AnnouncementEntry> list = q.list();

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
			return (List<AnnouncementEntry>)result;
		}
	}

	public List<AnnouncementEntry> findByC_C(long classNameId, long classPK,
		int begin, int end) throws SystemException {
		return findByC_C(classNameId, classPK, begin, end, null);
	}

	public List<AnnouncementEntry> findByC_C(long classNameId, long classPK,
		int begin, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementEntryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementEntry.class.getName();
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
					"FROM com.liferay.portlet.announcements.model.AnnouncementEntry WHERE ");

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

				List<AnnouncementEntry> list = (List<AnnouncementEntry>)QueryUtil.list(q,
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
			return (List<AnnouncementEntry>)result;
		}
	}

	public AnnouncementEntry findByC_C_First(long classNameId, long classPK,
		OrderByComparator obc)
		throws NoSuchAnnouncementEntryException, SystemException {
		List<AnnouncementEntry> list = findByC_C(classNameId, classPK, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No AnnouncementEntry exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAnnouncementEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementEntry findByC_C_Last(long classNameId, long classPK,
		OrderByComparator obc)
		throws NoSuchAnnouncementEntryException, SystemException {
		int count = countByC_C(classNameId, classPK);

		List<AnnouncementEntry> list = findByC_C(classNameId, classPK,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No AnnouncementEntry exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAnnouncementEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementEntry[] findByC_C_PrevAndNext(long entryId,
		long classNameId, long classPK, OrderByComparator obc)
		throws NoSuchAnnouncementEntryException, SystemException {
		AnnouncementEntry announcementEntry = findByPrimaryKey(entryId);

		int count = countByC_C(classNameId, classPK);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.announcements.model.AnnouncementEntry WHERE ");

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
					announcementEntry);

			AnnouncementEntry[] array = new AnnouncementEntryImpl[3];

			array[0] = (AnnouncementEntry)objArray[0];
			array[1] = (AnnouncementEntry)objArray[1];
			array[2] = (AnnouncementEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AnnouncementEntry> findByC_C_A(long classNameId, long classPK,
		boolean alert) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementEntryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementEntry.class.getName();
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
					"FROM com.liferay.portlet.announcements.model.AnnouncementEntry WHERE ");

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

				List<AnnouncementEntry> list = q.list();

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
			return (List<AnnouncementEntry>)result;
		}
	}

	public List<AnnouncementEntry> findByC_C_A(long classNameId, long classPK,
		boolean alert, int begin, int end) throws SystemException {
		return findByC_C_A(classNameId, classPK, alert, begin, end, null);
	}

	public List<AnnouncementEntry> findByC_C_A(long classNameId, long classPK,
		boolean alert, int begin, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementEntryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementEntry.class.getName();
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
					"FROM com.liferay.portlet.announcements.model.AnnouncementEntry WHERE ");

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

				List<AnnouncementEntry> list = (List<AnnouncementEntry>)QueryUtil.list(q,
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
			return (List<AnnouncementEntry>)result;
		}
	}

	public AnnouncementEntry findByC_C_A_First(long classNameId, long classPK,
		boolean alert, OrderByComparator obc)
		throws NoSuchAnnouncementEntryException, SystemException {
		List<AnnouncementEntry> list = findByC_C_A(classNameId, classPK, alert,
				0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No AnnouncementEntry exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(", ");
			msg.append("alert=" + alert);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAnnouncementEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementEntry findByC_C_A_Last(long classNameId, long classPK,
		boolean alert, OrderByComparator obc)
		throws NoSuchAnnouncementEntryException, SystemException {
		int count = countByC_C_A(classNameId, classPK, alert);

		List<AnnouncementEntry> list = findByC_C_A(classNameId, classPK, alert,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No AnnouncementEntry exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(", ");
			msg.append("alert=" + alert);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAnnouncementEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementEntry[] findByC_C_A_PrevAndNext(long entryId,
		long classNameId, long classPK, boolean alert, OrderByComparator obc)
		throws NoSuchAnnouncementEntryException, SystemException {
		AnnouncementEntry announcementEntry = findByPrimaryKey(entryId);

		int count = countByC_C_A(classNameId, classPK, alert);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.announcements.model.AnnouncementEntry WHERE ");

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
					announcementEntry);

			AnnouncementEntry[] array = new AnnouncementEntryImpl[3];

			array[0] = (AnnouncementEntry)objArray[0];
			array[1] = (AnnouncementEntry)objArray[1];
			array[2] = (AnnouncementEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AnnouncementEntry> findByUserId(long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementEntryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementEntry.class.getName();
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
					"FROM com.liferay.portlet.announcements.model.AnnouncementEntry WHERE ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("priority ASC, ");
				query.append("modifiedDate ASC");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, userId);

				List<AnnouncementEntry> list = q.list();

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
			return (List<AnnouncementEntry>)result;
		}
	}

	public List<AnnouncementEntry> findByUserId(long userId, int begin, int end)
		throws SystemException {
		return findByUserId(userId, begin, end, null);
	}

	public List<AnnouncementEntry> findByUserId(long userId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementEntryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementEntry.class.getName();
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
					"FROM com.liferay.portlet.announcements.model.AnnouncementEntry WHERE ");

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

				List<AnnouncementEntry> list = (List<AnnouncementEntry>)QueryUtil.list(q,
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
			return (List<AnnouncementEntry>)result;
		}
	}

	public AnnouncementEntry findByUserId_First(long userId,
		OrderByComparator obc)
		throws NoSuchAnnouncementEntryException, SystemException {
		List<AnnouncementEntry> list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No AnnouncementEntry exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAnnouncementEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementEntry findByUserId_Last(long userId,
		OrderByComparator obc)
		throws NoSuchAnnouncementEntryException, SystemException {
		int count = countByUserId(userId);

		List<AnnouncementEntry> list = findByUserId(userId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No AnnouncementEntry exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAnnouncementEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementEntry[] findByUserId_PrevAndNext(long entryId,
		long userId, OrderByComparator obc)
		throws NoSuchAnnouncementEntryException, SystemException {
		AnnouncementEntry announcementEntry = findByPrimaryKey(entryId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.announcements.model.AnnouncementEntry WHERE ");

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
					announcementEntry);

			AnnouncementEntry[] array = new AnnouncementEntryImpl[3];

			array[0] = (AnnouncementEntry)objArray[0];
			array[1] = (AnnouncementEntry)objArray[1];
			array[2] = (AnnouncementEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AnnouncementEntry> findWithDynamicQuery(
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

	public List<AnnouncementEntry> findWithDynamicQuery(
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

	public List<AnnouncementEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AnnouncementEntry> findAll(int begin, int end)
		throws SystemException {
		return findAll(begin, end, null);
	}

	public List<AnnouncementEntry> findAll(int begin, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementEntryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementEntry.class.getName();
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
					"FROM com.liferay.portlet.announcements.model.AnnouncementEntry ");

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

				List<AnnouncementEntry> list = (List<AnnouncementEntry>)QueryUtil.list(q,
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
			return (List<AnnouncementEntry>)result;
		}
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (AnnouncementEntry announcementEntry : findByUuid(uuid)) {
			remove(announcementEntry);
		}
	}

	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (AnnouncementEntry announcementEntry : findByC_C(classNameId,
				classPK)) {
			remove(announcementEntry);
		}
	}

	public void removeByC_C_A(long classNameId, long classPK, boolean alert)
		throws SystemException {
		for (AnnouncementEntry announcementEntry : findByC_C_A(classNameId,
				classPK, alert)) {
			remove(announcementEntry);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (AnnouncementEntry announcementEntry : findByUserId(userId)) {
			remove(announcementEntry);
		}
	}

	public void removeAll() throws SystemException {
		for (AnnouncementEntry announcementEntry : findAll()) {
			remove(announcementEntry);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		boolean finderClassNameCacheEnabled = AnnouncementEntryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementEntry.class.getName();
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
					"FROM com.liferay.portlet.announcements.model.AnnouncementEntry WHERE ");

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
		boolean finderClassNameCacheEnabled = AnnouncementEntryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementEntry.class.getName();
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
					"FROM com.liferay.portlet.announcements.model.AnnouncementEntry WHERE ");

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
		boolean finderClassNameCacheEnabled = AnnouncementEntryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementEntry.class.getName();
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
					"FROM com.liferay.portlet.announcements.model.AnnouncementEntry WHERE ");

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
		boolean finderClassNameCacheEnabled = AnnouncementEntryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementEntry.class.getName();
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
					"FROM com.liferay.portlet.announcements.model.AnnouncementEntry WHERE ");

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
		boolean finderClassNameCacheEnabled = AnnouncementEntryModelImpl.CACHE_ENABLED;
		String finderClassName = AnnouncementEntry.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.announcements.model.AnnouncementEntry");

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
				"value.object.listener.com.liferay.portlet.announcements.model.AnnouncementEntry"));
	private static Log _log = LogFactory.getLog(AnnouncementEntryPersistenceImpl.class);
}