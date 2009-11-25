/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.ratings.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.ratings.NoSuchEntryException;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.impl.RatingsEntryImpl;
import com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="RatingsEntryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RatingsEntryPersistence
 * @see       RatingsEntryUtil
 * @generated
 */
public class RatingsEntryPersistenceImpl extends BasePersistenceImpl<RatingsEntry>
	implements RatingsEntryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = RatingsEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_C_C = new FinderPath(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_C = new FinderPath(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U_C_C = new FinderPath(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_C_C = new FinderPath(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(RatingsEntry ratingsEntry) {
		EntityCacheUtil.putResult(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryImpl.class, ratingsEntry.getPrimaryKey(), ratingsEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C,
			new Object[] {
				new Long(ratingsEntry.getUserId()),
				new Long(ratingsEntry.getClassNameId()),
				new Long(ratingsEntry.getClassPK())
			}, ratingsEntry);
	}

	public void cacheResult(List<RatingsEntry> ratingsEntries) {
		for (RatingsEntry ratingsEntry : ratingsEntries) {
			if (EntityCacheUtil.getResult(
						RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
						RatingsEntryImpl.class, ratingsEntry.getPrimaryKey(),
						this) == null) {
				cacheResult(ratingsEntry);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(RatingsEntryImpl.class.getName());
		EntityCacheUtil.clearCache(RatingsEntryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public RatingsEntry create(long entryId) {
		RatingsEntry ratingsEntry = new RatingsEntryImpl();

		ratingsEntry.setNew(true);
		ratingsEntry.setPrimaryKey(entryId);

		return ratingsEntry;
	}

	public RatingsEntry remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public RatingsEntry remove(long entryId)
		throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RatingsEntry ratingsEntry = (RatingsEntry)session.get(RatingsEntryImpl.class,
					new Long(entryId));

			if (ratingsEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No RatingsEntry exists with the primary key " +
						entryId);
				}

				throw new NoSuchEntryException(
					"No RatingsEntry exists with the primary key " + entryId);
			}

			return remove(ratingsEntry);
		}
		catch (NoSuchEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public RatingsEntry remove(RatingsEntry ratingsEntry)
		throws SystemException {
		for (ModelListener<RatingsEntry> listener : listeners) {
			listener.onBeforeRemove(ratingsEntry);
		}

		ratingsEntry = removeImpl(ratingsEntry);

		for (ModelListener<RatingsEntry> listener : listeners) {
			listener.onAfterRemove(ratingsEntry);
		}

		return ratingsEntry;
	}

	protected RatingsEntry removeImpl(RatingsEntry ratingsEntry)
		throws SystemException {
		ratingsEntry = toUnwrappedModel(ratingsEntry);

		Session session = null;

		try {
			session = openSession();

			if (ratingsEntry.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(RatingsEntryImpl.class,
						ratingsEntry.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(ratingsEntry);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		RatingsEntryModelImpl ratingsEntryModelImpl = (RatingsEntryModelImpl)ratingsEntry;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_C_C,
			new Object[] {
				new Long(ratingsEntryModelImpl.getOriginalUserId()),
				new Long(ratingsEntryModelImpl.getOriginalClassNameId()),
				new Long(ratingsEntryModelImpl.getOriginalClassPK())
			});

		EntityCacheUtil.removeResult(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryImpl.class, ratingsEntry.getPrimaryKey());

		return ratingsEntry;
	}

	public RatingsEntry updateImpl(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry,
		boolean merge) throws SystemException {
		ratingsEntry = toUnwrappedModel(ratingsEntry);

		boolean isNew = ratingsEntry.isNew();

		RatingsEntryModelImpl ratingsEntryModelImpl = (RatingsEntryModelImpl)ratingsEntry;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, ratingsEntry, merge);

			ratingsEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryImpl.class, ratingsEntry.getPrimaryKey(), ratingsEntry);

		if (!isNew &&
				((ratingsEntry.getUserId() != ratingsEntryModelImpl.getOriginalUserId()) ||
				(ratingsEntry.getClassNameId() != ratingsEntryModelImpl.getOriginalClassNameId()) ||
				(ratingsEntry.getClassPK() != ratingsEntryModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_C_C,
				new Object[] {
					new Long(ratingsEntryModelImpl.getOriginalUserId()),
					new Long(ratingsEntryModelImpl.getOriginalClassNameId()),
					new Long(ratingsEntryModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				((ratingsEntry.getUserId() != ratingsEntryModelImpl.getOriginalUserId()) ||
				(ratingsEntry.getClassNameId() != ratingsEntryModelImpl.getOriginalClassNameId()) ||
				(ratingsEntry.getClassPK() != ratingsEntryModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C,
				new Object[] {
					new Long(ratingsEntry.getUserId()),
					new Long(ratingsEntry.getClassNameId()),
					new Long(ratingsEntry.getClassPK())
				}, ratingsEntry);
		}

		return ratingsEntry;
	}

	protected RatingsEntry toUnwrappedModel(RatingsEntry ratingsEntry) {
		if (ratingsEntry instanceof RatingsEntryImpl) {
			return ratingsEntry;
		}

		RatingsEntryImpl ratingsEntryImpl = new RatingsEntryImpl();

		ratingsEntryImpl.setNew(ratingsEntry.isNew());
		ratingsEntryImpl.setPrimaryKey(ratingsEntry.getPrimaryKey());

		ratingsEntryImpl.setEntryId(ratingsEntry.getEntryId());
		ratingsEntryImpl.setCompanyId(ratingsEntry.getCompanyId());
		ratingsEntryImpl.setUserId(ratingsEntry.getUserId());
		ratingsEntryImpl.setUserName(ratingsEntry.getUserName());
		ratingsEntryImpl.setCreateDate(ratingsEntry.getCreateDate());
		ratingsEntryImpl.setModifiedDate(ratingsEntry.getModifiedDate());
		ratingsEntryImpl.setClassNameId(ratingsEntry.getClassNameId());
		ratingsEntryImpl.setClassPK(ratingsEntry.getClassPK());
		ratingsEntryImpl.setScore(ratingsEntry.getScore());

		return ratingsEntryImpl;
	}

	public RatingsEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public RatingsEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException, SystemException {
		RatingsEntry ratingsEntry = fetchByPrimaryKey(entryId);

		if (ratingsEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No RatingsEntry exists with the primary key " +
					entryId);
			}

			throw new NoSuchEntryException(
				"No RatingsEntry exists with the primary key " + entryId);
		}

		return ratingsEntry;
	}

	public RatingsEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public RatingsEntry fetchByPrimaryKey(long entryId)
		throws SystemException {
		RatingsEntry ratingsEntry = (RatingsEntry)EntityCacheUtil.getResult(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
				RatingsEntryImpl.class, entryId, this);

		if (ratingsEntry == null) {
			Session session = null;

			try {
				session = openSession();

				ratingsEntry = (RatingsEntry)session.get(RatingsEntryImpl.class,
						new Long(entryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (ratingsEntry != null) {
					cacheResult(ratingsEntry);
				}

				closeSession(session);
			}
		}

		return ratingsEntry;
	}

	public List<RatingsEntry> findByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		List<RatingsEntry> list = (List<RatingsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);
				query.append(
					"SELECT ratingsEntry FROM RatingsEntry ratingsEntry WHERE ");

				query.append("ratingsEntry.classNameId = ?");

				query.append(" AND ");

				query.append("ratingsEntry.classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<RatingsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<RatingsEntry> findByC_C(long classNameId, long classPK,
		int start, int end) throws SystemException {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	public List<RatingsEntry> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<RatingsEntry> list = (List<RatingsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 5;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT ratingsEntry FROM RatingsEntry ratingsEntry WHERE ");

				query.append("ratingsEntry.classNameId = ?");

				query.append(" AND ");

				query.append("ratingsEntry.classPK = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("ratingsEntry.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<RatingsEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<RatingsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public RatingsEntry findByC_C_First(long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		List<RatingsEntry> list = findByC_C(classNameId, classPK, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(5);
			msg.append("No RatingsEntry exists with the key {");
			msg.append("classNameId=" + classNameId);
			msg.append(", ");
			msg.append("classPK=" + classPK);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public RatingsEntry findByC_C_Last(long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		int count = countByC_C(classNameId, classPK);

		List<RatingsEntry> list = findByC_C(classNameId, classPK, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(5);
			msg.append("No RatingsEntry exists with the key {");
			msg.append("classNameId=" + classNameId);
			msg.append(", ");
			msg.append("classPK=" + classPK);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public RatingsEntry[] findByC_C_PrevAndNext(long entryId, long classNameId,
		long classPK, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		RatingsEntry ratingsEntry = findByPrimaryKey(entryId);

		int count = countByC_C(classNameId, classPK);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 5;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append(
				"SELECT ratingsEntry FROM RatingsEntry ratingsEntry WHERE ");

			query.append("ratingsEntry.classNameId = ?");

			query.append(" AND ");

			query.append("ratingsEntry.classPK = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("ratingsEntry.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					ratingsEntry);

			RatingsEntry[] array = new RatingsEntryImpl[3];

			array[0] = (RatingsEntry)objArray[0];
			array[1] = (RatingsEntry)objArray[1];
			array[2] = (RatingsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public RatingsEntry findByU_C_C(long userId, long classNameId, long classPK)
		throws NoSuchEntryException, SystemException {
		RatingsEntry ratingsEntry = fetchByU_C_C(userId, classNameId, classPK);

		if (ratingsEntry == null) {
			StringBundler msg = new StringBundler(7);
			msg.append("No RatingsEntry exists with the key {");
			msg.append("userId=" + userId);
			msg.append(", ");
			msg.append("classNameId=" + classNameId);
			msg.append(", ");
			msg.append("classPK=" + classPK);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return ratingsEntry;
	}

	public RatingsEntry fetchByU_C_C(long userId, long classNameId, long classPK)
		throws SystemException {
		return fetchByU_C_C(userId, classNameId, classPK, true);
	}

	public RatingsEntry fetchByU_C_C(long userId, long classNameId,
		long classPK, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(classNameId), new Long(classPK)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_C_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(7);
				query.append(
					"SELECT ratingsEntry FROM RatingsEntry ratingsEntry WHERE ");

				query.append("ratingsEntry.userId = ?");

				query.append(" AND ");

				query.append("ratingsEntry.classNameId = ?");

				query.append(" AND ");

				query.append("ratingsEntry.classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<RatingsEntry> list = q.list();

				result = list;

				RatingsEntry ratingsEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C,
						finderArgs, list);
				}
				else {
					ratingsEntry = list.get(0);

					cacheResult(ratingsEntry);

					if ((ratingsEntry.getUserId() != userId) ||
							(ratingsEntry.getClassNameId() != classNameId) ||
							(ratingsEntry.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C,
							finderArgs, ratingsEntry);
					}
				}

				return ratingsEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C,
						finderArgs, new ArrayList<RatingsEntry>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (RatingsEntry)result;
			}
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<RatingsEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<RatingsEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<RatingsEntry> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<RatingsEntry> list = (List<RatingsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 1;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT ratingsEntry FROM RatingsEntry ratingsEntry ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("ratingsEntry.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<RatingsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<RatingsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<RatingsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (RatingsEntry ratingsEntry : findByC_C(classNameId, classPK)) {
			remove(ratingsEntry);
		}
	}

	public void removeByU_C_C(long userId, long classNameId, long classPK)
		throws NoSuchEntryException, SystemException {
		RatingsEntry ratingsEntry = findByU_C_C(userId, classNameId, classPK);

		remove(ratingsEntry);
	}

	public void removeAll() throws SystemException {
		for (RatingsEntry ratingsEntry : findAll()) {
			remove(ratingsEntry);
		}
	}

	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);
				query.append("SELECT COUNT(ratingsEntry) ");
				query.append("FROM RatingsEntry ratingsEntry WHERE ");

				query.append("ratingsEntry.classNameId = ?");

				query.append(" AND ");

				query.append("ratingsEntry.classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_C_C(long userId, long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(classNameId), new Long(classPK)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(8);
				query.append("SELECT COUNT(ratingsEntry) ");
				query.append("FROM RatingsEntry ratingsEntry WHERE ");

				query.append("ratingsEntry.userId = ?");

				query.append(" AND ");

				query.append("ratingsEntry.classNameId = ?");

				query.append(" AND ");

				query.append("ratingsEntry.classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_C_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(ratingsEntry) FROM RatingsEntry ratingsEntry");

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.ratings.model.RatingsEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<RatingsEntry>> listenersList = new ArrayList<ModelListener<RatingsEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<RatingsEntry>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsEntryPersistence")
	protected com.liferay.portlet.ratings.service.persistence.RatingsEntryPersistence ratingsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence")
	protected com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence")
	protected com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence")
	protected com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence blogsStatsUserPersistence;
	private static Log _log = LogFactoryUtil.getLog(RatingsEntryPersistenceImpl.class);
}