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

package com.liferay.portlet.announcements.service.persistence;

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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.announcements.NoSuchDeliveryException;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="AnnouncementsDeliveryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsDeliveryPersistence
 * @see       AnnouncementsDeliveryUtil
 * @generated
 */
public class AnnouncementsDeliveryPersistenceImpl extends BasePersistenceImpl<AnnouncementsDelivery>
	implements AnnouncementsDeliveryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = AnnouncementsDeliveryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U_T = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_U_T = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(AnnouncementsDelivery announcementsDelivery) {
		EntityCacheUtil.putResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			announcementsDelivery.getPrimaryKey(), announcementsDelivery);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
			new Object[] {
				new Long(announcementsDelivery.getUserId()),
				
			announcementsDelivery.getType()
			}, announcementsDelivery);
	}

	public void cacheResult(List<AnnouncementsDelivery> announcementsDeliveries) {
		for (AnnouncementsDelivery announcementsDelivery : announcementsDeliveries) {
			if (EntityCacheUtil.getResult(
						AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
						AnnouncementsDeliveryImpl.class,
						announcementsDelivery.getPrimaryKey(), this) == null) {
				cacheResult(announcementsDelivery);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(AnnouncementsDeliveryImpl.class.getName());
		EntityCacheUtil.clearCache(AnnouncementsDeliveryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public AnnouncementsDelivery create(long deliveryId) {
		AnnouncementsDelivery announcementsDelivery = new AnnouncementsDeliveryImpl();

		announcementsDelivery.setNew(true);
		announcementsDelivery.setPrimaryKey(deliveryId);

		return announcementsDelivery;
	}

	public AnnouncementsDelivery remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public AnnouncementsDelivery remove(long deliveryId)
		throws NoSuchDeliveryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AnnouncementsDelivery announcementsDelivery = (AnnouncementsDelivery)session.get(AnnouncementsDeliveryImpl.class,
					new Long(deliveryId));

			if (announcementsDelivery == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No AnnouncementsDelivery exists with the primary key " +
						deliveryId);
				}

				throw new NoSuchDeliveryException(
					"No AnnouncementsDelivery exists with the primary key " +
					deliveryId);
			}

			return remove(announcementsDelivery);
		}
		catch (NoSuchDeliveryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AnnouncementsDelivery remove(
		AnnouncementsDelivery announcementsDelivery) throws SystemException {
		for (ModelListener<AnnouncementsDelivery> listener : listeners) {
			listener.onBeforeRemove(announcementsDelivery);
		}

		announcementsDelivery = removeImpl(announcementsDelivery);

		for (ModelListener<AnnouncementsDelivery> listener : listeners) {
			listener.onAfterRemove(announcementsDelivery);
		}

		return announcementsDelivery;
	}

	protected AnnouncementsDelivery removeImpl(
		AnnouncementsDelivery announcementsDelivery) throws SystemException {
		announcementsDelivery = toUnwrappedModel(announcementsDelivery);

		Session session = null;

		try {
			session = openSession();

			if (announcementsDelivery.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(AnnouncementsDeliveryImpl.class,
						announcementsDelivery.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(announcementsDelivery);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		AnnouncementsDeliveryModelImpl announcementsDeliveryModelImpl = (AnnouncementsDeliveryModelImpl)announcementsDelivery;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_T,
			new Object[] {
				new Long(announcementsDeliveryModelImpl.getOriginalUserId()),
				
			announcementsDeliveryModelImpl.getOriginalType()
			});

		EntityCacheUtil.removeResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			announcementsDelivery.getPrimaryKey());

		return announcementsDelivery;
	}

	public AnnouncementsDelivery updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery,
		boolean merge) throws SystemException {
		announcementsDelivery = toUnwrappedModel(announcementsDelivery);

		boolean isNew = announcementsDelivery.isNew();

		AnnouncementsDeliveryModelImpl announcementsDeliveryModelImpl = (AnnouncementsDeliveryModelImpl)announcementsDelivery;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, announcementsDelivery, merge);

			announcementsDelivery.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			announcementsDelivery.getPrimaryKey(), announcementsDelivery);

		if (!isNew &&
				((announcementsDelivery.getUserId() != announcementsDeliveryModelImpl.getOriginalUserId()) ||
				!Validator.equals(announcementsDelivery.getType(),
					announcementsDeliveryModelImpl.getOriginalType()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_T,
				new Object[] {
					new Long(announcementsDeliveryModelImpl.getOriginalUserId()),
					
				announcementsDeliveryModelImpl.getOriginalType()
				});
		}

		if (isNew ||
				((announcementsDelivery.getUserId() != announcementsDeliveryModelImpl.getOriginalUserId()) ||
				!Validator.equals(announcementsDelivery.getType(),
					announcementsDeliveryModelImpl.getOriginalType()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
				new Object[] {
					new Long(announcementsDelivery.getUserId()),
					
				announcementsDelivery.getType()
				}, announcementsDelivery);
		}

		return announcementsDelivery;
	}

	protected AnnouncementsDelivery toUnwrappedModel(
		AnnouncementsDelivery announcementsDelivery) {
		if (announcementsDelivery instanceof AnnouncementsDeliveryImpl) {
			return announcementsDelivery;
		}

		AnnouncementsDeliveryImpl announcementsDeliveryImpl = new AnnouncementsDeliveryImpl();

		announcementsDeliveryImpl.setNew(announcementsDelivery.isNew());
		announcementsDeliveryImpl.setPrimaryKey(announcementsDelivery.getPrimaryKey());

		announcementsDeliveryImpl.setDeliveryId(announcementsDelivery.getDeliveryId());
		announcementsDeliveryImpl.setCompanyId(announcementsDelivery.getCompanyId());
		announcementsDeliveryImpl.setUserId(announcementsDelivery.getUserId());
		announcementsDeliveryImpl.setType(announcementsDelivery.getType());
		announcementsDeliveryImpl.setEmail(announcementsDelivery.isEmail());
		announcementsDeliveryImpl.setSms(announcementsDelivery.isSms());
		announcementsDeliveryImpl.setWebsite(announcementsDelivery.isWebsite());

		return announcementsDeliveryImpl;
	}

	public AnnouncementsDelivery findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AnnouncementsDelivery findByPrimaryKey(long deliveryId)
		throws NoSuchDeliveryException, SystemException {
		AnnouncementsDelivery announcementsDelivery = fetchByPrimaryKey(deliveryId);

		if (announcementsDelivery == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No AnnouncementsDelivery exists with the primary key " +
					deliveryId);
			}

			throw new NoSuchDeliveryException(
				"No AnnouncementsDelivery exists with the primary key " +
				deliveryId);
		}

		return announcementsDelivery;
	}

	public AnnouncementsDelivery fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AnnouncementsDelivery fetchByPrimaryKey(long deliveryId)
		throws SystemException {
		AnnouncementsDelivery announcementsDelivery = (AnnouncementsDelivery)EntityCacheUtil.getResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
				AnnouncementsDeliveryImpl.class, deliveryId, this);

		if (announcementsDelivery == null) {
			Session session = null;

			try {
				session = openSession();

				announcementsDelivery = (AnnouncementsDelivery)session.get(AnnouncementsDeliveryImpl.class,
						new Long(deliveryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (announcementsDelivery != null) {
					cacheResult(announcementsDelivery);
				}

				closeSession(session);
			}
		}

		return announcementsDelivery;
	}

	public List<AnnouncementsDelivery> findByUserId(long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		List<AnnouncementsDelivery> list = (List<AnnouncementsDelivery>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT announcementsDelivery FROM AnnouncementsDelivery announcementsDelivery WHERE ");

				query.append("announcementsDelivery.userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsDelivery>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AnnouncementsDelivery> findByUserId(long userId, int start,
		int end) throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<AnnouncementsDelivery> findByUserId(long userId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AnnouncementsDelivery> list = (List<AnnouncementsDelivery>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT announcementsDelivery FROM AnnouncementsDelivery announcementsDelivery WHERE ");

				query.append("announcementsDelivery.userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("announcementsDelivery.");
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

				qPos.add(userId);

				list = (List<AnnouncementsDelivery>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsDelivery>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AnnouncementsDelivery findByUserId_First(long userId,
		OrderByComparator obc) throws NoSuchDeliveryException, SystemException {
		List<AnnouncementsDelivery> list = findByUserId(userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AnnouncementsDelivery exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchDeliveryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsDelivery findByUserId_Last(long userId,
		OrderByComparator obc) throws NoSuchDeliveryException, SystemException {
		int count = countByUserId(userId);

		List<AnnouncementsDelivery> list = findByUserId(userId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AnnouncementsDelivery exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchDeliveryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsDelivery[] findByUserId_PrevAndNext(long deliveryId,
		long userId, OrderByComparator obc)
		throws NoSuchDeliveryException, SystemException {
		AnnouncementsDelivery announcementsDelivery = findByPrimaryKey(deliveryId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT announcementsDelivery FROM AnnouncementsDelivery announcementsDelivery WHERE ");

			query.append("announcementsDelivery.userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("announcementsDelivery.");
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

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					announcementsDelivery);

			AnnouncementsDelivery[] array = new AnnouncementsDeliveryImpl[3];

			array[0] = (AnnouncementsDelivery)objArray[0];
			array[1] = (AnnouncementsDelivery)objArray[1];
			array[2] = (AnnouncementsDelivery)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AnnouncementsDelivery findByU_T(long userId, String type)
		throws NoSuchDeliveryException, SystemException {
		AnnouncementsDelivery announcementsDelivery = fetchByU_T(userId, type);

		if (announcementsDelivery == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No AnnouncementsDelivery exists with the key {");

			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchDeliveryException(msg.toString());
		}

		return announcementsDelivery;
	}

	public AnnouncementsDelivery fetchByU_T(long userId, String type)
		throws SystemException {
		return fetchByU_T(userId, type, true);
	}

	public AnnouncementsDelivery fetchByU_T(long userId, String type,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId), type };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_T,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT announcementsDelivery FROM AnnouncementsDelivery announcementsDelivery WHERE ");

				query.append("announcementsDelivery.userId = ?");

				query.append(" AND ");

				if (type == null) {
					query.append("announcementsDelivery.type IS NULL");
				}
				else {
					if (type.equals(StringPool.BLANK)) {
						query.append("(announcementsDelivery.type IS NULL OR ");
					}

					query.append("announcementsDelivery.type = ?");

					if (type.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (type != null) {
					qPos.add(type);
				}

				List<AnnouncementsDelivery> list = q.list();

				result = list;

				AnnouncementsDelivery announcementsDelivery = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
						finderArgs, list);
				}
				else {
					announcementsDelivery = list.get(0);

					cacheResult(announcementsDelivery);

					if ((announcementsDelivery.getUserId() != userId) ||
							(announcementsDelivery.getType() == null) ||
							!announcementsDelivery.getType().equals(type)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
							finderArgs, announcementsDelivery);
					}
				}

				return announcementsDelivery;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
						finderArgs, new ArrayList<AnnouncementsDelivery>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (AnnouncementsDelivery)result;
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

	public List<AnnouncementsDelivery> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AnnouncementsDelivery> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<AnnouncementsDelivery> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<AnnouncementsDelivery> list = (List<AnnouncementsDelivery>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT announcementsDelivery FROM AnnouncementsDelivery announcementsDelivery ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("announcementsDelivery.");
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
					list = (List<AnnouncementsDelivery>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AnnouncementsDelivery>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsDelivery>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUserId(long userId) throws SystemException {
		for (AnnouncementsDelivery announcementsDelivery : findByUserId(userId)) {
			remove(announcementsDelivery);
		}
	}

	public void removeByU_T(long userId, String type)
		throws NoSuchDeliveryException, SystemException {
		AnnouncementsDelivery announcementsDelivery = findByU_T(userId, type);

		remove(announcementsDelivery);
	}

	public void removeAll() throws SystemException {
		for (AnnouncementsDelivery announcementsDelivery : findAll()) {
			remove(announcementsDelivery);
		}
	}

	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(announcementsDelivery) ");
				query.append(
					"FROM AnnouncementsDelivery announcementsDelivery WHERE ");

				query.append("announcementsDelivery.userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_T(long userId, String type) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId), type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(announcementsDelivery) ");
				query.append(
					"FROM AnnouncementsDelivery announcementsDelivery WHERE ");

				query.append("announcementsDelivery.userId = ?");

				query.append(" AND ");

				if (type == null) {
					query.append("announcementsDelivery.type IS NULL");
				}
				else {
					if (type.equals(StringPool.BLANK)) {
						query.append("(announcementsDelivery.type IS NULL OR ");
					}

					query.append("announcementsDelivery.type = ?");

					if (type.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (type != null) {
					qPos.add(type);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_T, finderArgs,
					count);

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
						"SELECT COUNT(announcementsDelivery) FROM AnnouncementsDelivery announcementsDelivery");

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
						"value.object.listener.com.liferay.portlet.announcements.model.AnnouncementsDelivery")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AnnouncementsDelivery>> listenersList = new ArrayList<ModelListener<AnnouncementsDelivery>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AnnouncementsDelivery>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.announcements.service.persistence.AnnouncementsDeliveryPersistence")
	protected com.liferay.portlet.announcements.service.persistence.AnnouncementsDeliveryPersistence announcementsDeliveryPersistence;
	@BeanReference(name = "com.liferay.portlet.announcements.service.persistence.AnnouncementsEntryPersistence")
	protected com.liferay.portlet.announcements.service.persistence.AnnouncementsEntryPersistence announcementsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.announcements.service.persistence.AnnouncementsFlagPersistence")
	protected com.liferay.portlet.announcements.service.persistence.AnnouncementsFlagPersistence announcementsFlagPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(AnnouncementsDeliveryPersistenceImpl.class);
}