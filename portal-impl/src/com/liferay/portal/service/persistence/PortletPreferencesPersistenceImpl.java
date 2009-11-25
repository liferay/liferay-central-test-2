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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchPortletPreferencesException;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.impl.PortletPreferencesImpl;
import com.liferay.portal.model.impl.PortletPreferencesModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="PortletPreferencesPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletPreferencesPersistence
 * @see       PortletPreferencesUtil
 * @generated
 */
public class PortletPreferencesPersistenceImpl extends BasePersistenceImpl<PortletPreferences>
	implements PortletPreferencesPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = PortletPreferencesImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_PLID = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByPlid",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_PLID = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByPlid",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_PLID = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByPlid",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_P_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByP_P",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_P_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByP_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_P_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByP_P",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_O_O_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByO_O_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_O_O_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByO_O_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_O_O_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByO_O_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_O_O_P_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByO_O_P_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Long.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_O_O_P_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByO_O_P_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Long.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(PortletPreferences portletPreferences) {
		EntityCacheUtil.putResult(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesImpl.class, portletPreferences.getPrimaryKey(),
			portletPreferences);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O_P_P,
			new Object[] {
				new Long(portletPreferences.getOwnerId()),
				new Integer(portletPreferences.getOwnerType()),
				new Long(portletPreferences.getPlid()),
				
			portletPreferences.getPortletId()
			}, portletPreferences);
	}

	public void cacheResult(List<PortletPreferences> portletPreferenceses) {
		for (PortletPreferences portletPreferences : portletPreferenceses) {
			if (EntityCacheUtil.getResult(
						PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
						PortletPreferencesImpl.class,
						portletPreferences.getPrimaryKey(), this) == null) {
				cacheResult(portletPreferences);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(PortletPreferencesImpl.class.getName());
		EntityCacheUtil.clearCache(PortletPreferencesImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public PortletPreferences create(long portletPreferencesId) {
		PortletPreferences portletPreferences = new PortletPreferencesImpl();

		portletPreferences.setNew(true);
		portletPreferences.setPrimaryKey(portletPreferencesId);

		return portletPreferences;
	}

	public PortletPreferences remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public PortletPreferences remove(long portletPreferencesId)
		throws NoSuchPortletPreferencesException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PortletPreferences portletPreferences = (PortletPreferences)session.get(PortletPreferencesImpl.class,
					new Long(portletPreferencesId));

			if (portletPreferences == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No PortletPreferences exists with the primary key " +
						portletPreferencesId);
				}

				throw new NoSuchPortletPreferencesException(
					"No PortletPreferences exists with the primary key " +
					portletPreferencesId);
			}

			return remove(portletPreferences);
		}
		catch (NoSuchPortletPreferencesException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletPreferences remove(PortletPreferences portletPreferences)
		throws SystemException {
		for (ModelListener<PortletPreferences> listener : listeners) {
			listener.onBeforeRemove(portletPreferences);
		}

		portletPreferences = removeImpl(portletPreferences);

		for (ModelListener<PortletPreferences> listener : listeners) {
			listener.onAfterRemove(portletPreferences);
		}

		return portletPreferences;
	}

	protected PortletPreferences removeImpl(
		PortletPreferences portletPreferences) throws SystemException {
		portletPreferences = toUnwrappedModel(portletPreferences);

		Session session = null;

		try {
			session = openSession();

			if (portletPreferences.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(PortletPreferencesImpl.class,
						portletPreferences.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(portletPreferences);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		PortletPreferencesModelImpl portletPreferencesModelImpl = (PortletPreferencesModelImpl)portletPreferences;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_O_O_P_P,
			new Object[] {
				new Long(portletPreferencesModelImpl.getOriginalOwnerId()),
				new Integer(portletPreferencesModelImpl.getOriginalOwnerType()),
				new Long(portletPreferencesModelImpl.getOriginalPlid()),
				
			portletPreferencesModelImpl.getOriginalPortletId()
			});

		EntityCacheUtil.removeResult(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesImpl.class, portletPreferences.getPrimaryKey());

		return portletPreferences;
	}

	public PortletPreferences updateImpl(
		com.liferay.portal.model.PortletPreferences portletPreferences,
		boolean merge) throws SystemException {
		portletPreferences = toUnwrappedModel(portletPreferences);

		boolean isNew = portletPreferences.isNew();

		PortletPreferencesModelImpl portletPreferencesModelImpl = (PortletPreferencesModelImpl)portletPreferences;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, portletPreferences, merge);

			portletPreferences.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesImpl.class, portletPreferences.getPrimaryKey(),
			portletPreferences);

		if (!isNew &&
				((portletPreferences.getOwnerId() != portletPreferencesModelImpl.getOriginalOwnerId()) ||
				(portletPreferences.getOwnerType() != portletPreferencesModelImpl.getOriginalOwnerType()) ||
				(portletPreferences.getPlid() != portletPreferencesModelImpl.getOriginalPlid()) ||
				!Validator.equals(portletPreferences.getPortletId(),
					portletPreferencesModelImpl.getOriginalPortletId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_O_O_P_P,
				new Object[] {
					new Long(portletPreferencesModelImpl.getOriginalOwnerId()),
					new Integer(portletPreferencesModelImpl.getOriginalOwnerType()),
					new Long(portletPreferencesModelImpl.getOriginalPlid()),
					
				portletPreferencesModelImpl.getOriginalPortletId()
				});
		}

		if (isNew ||
				((portletPreferences.getOwnerId() != portletPreferencesModelImpl.getOriginalOwnerId()) ||
				(portletPreferences.getOwnerType() != portletPreferencesModelImpl.getOriginalOwnerType()) ||
				(portletPreferences.getPlid() != portletPreferencesModelImpl.getOriginalPlid()) ||
				!Validator.equals(portletPreferences.getPortletId(),
					portletPreferencesModelImpl.getOriginalPortletId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O_P_P,
				new Object[] {
					new Long(portletPreferences.getOwnerId()),
					new Integer(portletPreferences.getOwnerType()),
					new Long(portletPreferences.getPlid()),
					
				portletPreferences.getPortletId()
				}, portletPreferences);
		}

		return portletPreferences;
	}

	protected PortletPreferences toUnwrappedModel(
		PortletPreferences portletPreferences) {
		if (portletPreferences instanceof PortletPreferencesImpl) {
			return portletPreferences;
		}

		PortletPreferencesImpl portletPreferencesImpl = new PortletPreferencesImpl();

		portletPreferencesImpl.setNew(portletPreferences.isNew());
		portletPreferencesImpl.setPrimaryKey(portletPreferences.getPrimaryKey());

		portletPreferencesImpl.setPortletPreferencesId(portletPreferences.getPortletPreferencesId());
		portletPreferencesImpl.setOwnerId(portletPreferences.getOwnerId());
		portletPreferencesImpl.setOwnerType(portletPreferences.getOwnerType());
		portletPreferencesImpl.setPlid(portletPreferences.getPlid());
		portletPreferencesImpl.setPortletId(portletPreferences.getPortletId());
		portletPreferencesImpl.setPreferences(portletPreferences.getPreferences());

		return portletPreferencesImpl;
	}

	public PortletPreferences findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public PortletPreferences findByPrimaryKey(long portletPreferencesId)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = fetchByPrimaryKey(portletPreferencesId);

		if (portletPreferences == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No PortletPreferences exists with the primary key " +
					portletPreferencesId);
			}

			throw new NoSuchPortletPreferencesException(
				"No PortletPreferences exists with the primary key " +
				portletPreferencesId);
		}

		return portletPreferences;
	}

	public PortletPreferences fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public PortletPreferences fetchByPrimaryKey(long portletPreferencesId)
		throws SystemException {
		PortletPreferences portletPreferences = (PortletPreferences)EntityCacheUtil.getResult(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
				PortletPreferencesImpl.class, portletPreferencesId, this);

		if (portletPreferences == null) {
			Session session = null;

			try {
				session = openSession();

				portletPreferences = (PortletPreferences)session.get(PortletPreferencesImpl.class,
						new Long(portletPreferencesId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (portletPreferences != null) {
					cacheResult(portletPreferences);
				}

				closeSession(session);
			}
		}

		return portletPreferences;
	}

	public List<PortletPreferences> findByPlid(long plid)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(plid) };

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_PLID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);
				query.append(
					"SELECT portletPreferences FROM PortletPreferences portletPreferences WHERE ");

				query.append("portletPreferences.plid = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PortletPreferences>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_PLID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<PortletPreferences> findByPlid(long plid, int start, int end)
		throws SystemException {
		return findByPlid(plid, start, end, null);
	}

	public List<PortletPreferences> findByPlid(long plid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(plid),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_PLID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 3;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT portletPreferences FROM PortletPreferences portletPreferences WHERE ");

				query.append("portletPreferences.plid = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("portletPreferences.");
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

				qPos.add(plid);

				list = (List<PortletPreferences>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PortletPreferences>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_PLID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public PortletPreferences findByPlid_First(long plid, OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		List<PortletPreferences> list = findByPlid(plid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No PortletPreferences exists with the key {");
			msg.append("plid=" + plid);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPortletPreferencesException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletPreferences findByPlid_Last(long plid, OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		int count = countByPlid(plid);

		List<PortletPreferences> list = findByPlid(plid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No PortletPreferences exists with the key {");
			msg.append("plid=" + plid);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPortletPreferencesException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletPreferences[] findByPlid_PrevAndNext(
		long portletPreferencesId, long plid, OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesId);

		int count = countByPlid(plid);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 3;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append(
				"SELECT portletPreferences FROM PortletPreferences portletPreferences WHERE ");

			query.append("portletPreferences.plid = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("portletPreferences.");
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

			qPos.add(plid);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					portletPreferences);

			PortletPreferences[] array = new PortletPreferencesImpl[3];

			array[0] = (PortletPreferences)objArray[0];
			array[1] = (PortletPreferences)objArray[1];
			array[2] = (PortletPreferences)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<PortletPreferences> findByP_P(long plid, String portletId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(plid), portletId };

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_P_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(8);
				query.append(
					"SELECT portletPreferences FROM PortletPreferences portletPreferences WHERE ");

				query.append("portletPreferences.plid = ?");

				query.append(" AND ");

				if (portletId == null) {
					query.append("portletPreferences.portletId IS NULL");
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(
							"(portletPreferences.portletId IS NULL OR ");
					}

					query.append("portletPreferences.portletId = ?");

					if (portletId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				if (portletId != null) {
					qPos.add(portletId);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PortletPreferences>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_P_P, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<PortletPreferences> findByP_P(long plid, String portletId,
		int start, int end) throws SystemException {
		return findByP_P(plid, portletId, start, end, null);
	}

	public List<PortletPreferences> findByP_P(long plid, String portletId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(plid),
				
				portletId,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_P_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 8;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT portletPreferences FROM PortletPreferences portletPreferences WHERE ");

				query.append("portletPreferences.plid = ?");

				query.append(" AND ");

				if (portletId == null) {
					query.append("portletPreferences.portletId IS NULL");
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(
							"(portletPreferences.portletId IS NULL OR ");
					}

					query.append("portletPreferences.portletId = ?");

					if (portletId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("portletPreferences.");
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

				qPos.add(plid);

				if (portletId != null) {
					qPos.add(portletId);
				}

				list = (List<PortletPreferences>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PortletPreferences>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_P_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public PortletPreferences findByP_P_First(long plid, String portletId,
		OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		List<PortletPreferences> list = findByP_P(plid, portletId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(5);
			msg.append("No PortletPreferences exists with the key {");
			msg.append("plid=" + plid);
			msg.append(", ");
			msg.append("portletId=" + portletId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPortletPreferencesException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletPreferences findByP_P_Last(long plid, String portletId,
		OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		int count = countByP_P(plid, portletId);

		List<PortletPreferences> list = findByP_P(plid, portletId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(5);
			msg.append("No PortletPreferences exists with the key {");
			msg.append("plid=" + plid);
			msg.append(", ");
			msg.append("portletId=" + portletId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPortletPreferencesException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletPreferences[] findByP_P_PrevAndNext(
		long portletPreferencesId, long plid, String portletId,
		OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesId);

		int count = countByP_P(plid, portletId);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 8;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append(
				"SELECT portletPreferences FROM PortletPreferences portletPreferences WHERE ");

			query.append("portletPreferences.plid = ?");

			query.append(" AND ");

			if (portletId == null) {
				query.append("portletPreferences.portletId IS NULL");
			}
			else {
				if (portletId.equals(StringPool.BLANK)) {
					query.append("(portletPreferences.portletId IS NULL OR ");
				}

				query.append("portletPreferences.portletId = ?");

				if (portletId.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("portletPreferences.");
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

			qPos.add(plid);

			if (portletId != null) {
				qPos.add(portletId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					portletPreferences);

			PortletPreferences[] array = new PortletPreferencesImpl[3];

			array[0] = (PortletPreferences)objArray[0];
			array[1] = (PortletPreferences)objArray[1];
			array[2] = (PortletPreferences)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<PortletPreferences> findByO_O_P(long ownerId, int ownerType,
		long plid) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(ownerId), new Integer(ownerType), new Long(plid)
			};

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_O_O_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(7);
				query.append(
					"SELECT portletPreferences FROM PortletPreferences portletPreferences WHERE ");

				query.append("portletPreferences.ownerId = ?");

				query.append(" AND ");

				query.append("portletPreferences.ownerType = ?");

				query.append(" AND ");

				query.append("portletPreferences.plid = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerId);

				qPos.add(ownerType);

				qPos.add(plid);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PortletPreferences>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_O_O_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<PortletPreferences> findByO_O_P(long ownerId, int ownerType,
		long plid, int start, int end) throws SystemException {
		return findByO_O_P(ownerId, ownerType, plid, start, end, null);
	}

	public List<PortletPreferences> findByO_O_P(long ownerId, int ownerType,
		long plid, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(ownerId), new Integer(ownerType), new Long(plid),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_O_O_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 7;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT portletPreferences FROM PortletPreferences portletPreferences WHERE ");

				query.append("portletPreferences.ownerId = ?");

				query.append(" AND ");

				query.append("portletPreferences.ownerType = ?");

				query.append(" AND ");

				query.append("portletPreferences.plid = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("portletPreferences.");
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

				qPos.add(ownerId);

				qPos.add(ownerType);

				qPos.add(plid);

				list = (List<PortletPreferences>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PortletPreferences>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_O_O_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public PortletPreferences findByO_O_P_First(long ownerId, int ownerType,
		long plid, OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		List<PortletPreferences> list = findByO_O_P(ownerId, ownerType, plid,
				0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(7);
			msg.append("No PortletPreferences exists with the key {");
			msg.append("ownerId=" + ownerId);
			msg.append(", ");
			msg.append("ownerType=" + ownerType);
			msg.append(", ");
			msg.append("plid=" + plid);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPortletPreferencesException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletPreferences findByO_O_P_Last(long ownerId, int ownerType,
		long plid, OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		int count = countByO_O_P(ownerId, ownerType, plid);

		List<PortletPreferences> list = findByO_O_P(ownerId, ownerType, plid,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(7);
			msg.append("No PortletPreferences exists with the key {");
			msg.append("ownerId=" + ownerId);
			msg.append(", ");
			msg.append("ownerType=" + ownerType);
			msg.append(", ");
			msg.append("plid=" + plid);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchPortletPreferencesException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletPreferences[] findByO_O_P_PrevAndNext(
		long portletPreferencesId, long ownerId, int ownerType, long plid,
		OrderByComparator obc)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesId);

		int count = countByO_O_P(ownerId, ownerType, plid);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 7;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append(
				"SELECT portletPreferences FROM PortletPreferences portletPreferences WHERE ");

			query.append("portletPreferences.ownerId = ?");

			query.append(" AND ");

			query.append("portletPreferences.ownerType = ?");

			query.append(" AND ");

			query.append("portletPreferences.plid = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("portletPreferences.");
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

			qPos.add(ownerId);

			qPos.add(ownerType);

			qPos.add(plid);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					portletPreferences);

			PortletPreferences[] array = new PortletPreferencesImpl[3];

			array[0] = (PortletPreferences)objArray[0];
			array[1] = (PortletPreferences)objArray[1];
			array[2] = (PortletPreferences)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletPreferences findByO_O_P_P(long ownerId, int ownerType,
		long plid, String portletId)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = fetchByO_O_P_P(ownerId,
				ownerType, plid, portletId);

		if (portletPreferences == null) {
			StringBundler msg = new StringBundler(9);
			msg.append("No PortletPreferences exists with the key {");
			msg.append("ownerId=" + ownerId);
			msg.append(", ");
			msg.append("ownerType=" + ownerType);
			msg.append(", ");
			msg.append("plid=" + plid);
			msg.append(", ");
			msg.append("portletId=" + portletId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPortletPreferencesException(msg.toString());
		}

		return portletPreferences;
	}

	public PortletPreferences fetchByO_O_P_P(long ownerId, int ownerType,
		long plid, String portletId) throws SystemException {
		return fetchByO_O_P_P(ownerId, ownerType, plid, portletId, true);
	}

	public PortletPreferences fetchByO_O_P_P(long ownerId, int ownerType,
		long plid, String portletId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(ownerId), new Integer(ownerType), new Long(plid),
				
				portletId
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_O_O_P_P,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(12);
				query.append(
					"SELECT portletPreferences FROM PortletPreferences portletPreferences WHERE ");

				query.append("portletPreferences.ownerId = ?");

				query.append(" AND ");

				query.append("portletPreferences.ownerType = ?");

				query.append(" AND ");

				query.append("portletPreferences.plid = ?");

				query.append(" AND ");

				if (portletId == null) {
					query.append("portletPreferences.portletId IS NULL");
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(
							"(portletPreferences.portletId IS NULL OR ");
					}

					query.append("portletPreferences.portletId = ?");

					if (portletId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerId);

				qPos.add(ownerType);

				qPos.add(plid);

				if (portletId != null) {
					qPos.add(portletId);
				}

				List<PortletPreferences> list = q.list();

				result = list;

				PortletPreferences portletPreferences = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O_P_P,
						finderArgs, list);
				}
				else {
					portletPreferences = list.get(0);

					cacheResult(portletPreferences);

					if ((portletPreferences.getOwnerId() != ownerId) ||
							(portletPreferences.getOwnerType() != ownerType) ||
							(portletPreferences.getPlid() != plid) ||
							(portletPreferences.getPortletId() == null) ||
							!portletPreferences.getPortletId().equals(portletId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O_P_P,
							finderArgs, portletPreferences);
					}
				}

				return portletPreferences;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O_P_P,
						finderArgs, new ArrayList<PortletPreferences>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (PortletPreferences)result;
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

	public List<PortletPreferences> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<PortletPreferences> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<PortletPreferences> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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
					"SELECT portletPreferences FROM PortletPreferences portletPreferences ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("portletPreferences.");
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
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PortletPreferences>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByPlid(long plid) throws SystemException {
		for (PortletPreferences portletPreferences : findByPlid(plid)) {
			remove(portletPreferences);
		}
	}

	public void removeByP_P(long plid, String portletId)
		throws SystemException {
		for (PortletPreferences portletPreferences : findByP_P(plid, portletId)) {
			remove(portletPreferences);
		}
	}

	public void removeByO_O_P(long ownerId, int ownerType, long plid)
		throws SystemException {
		for (PortletPreferences portletPreferences : findByO_O_P(ownerId,
				ownerType, plid)) {
			remove(portletPreferences);
		}
	}

	public void removeByO_O_P_P(long ownerId, int ownerType, long plid,
		String portletId)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = findByO_O_P_P(ownerId,
				ownerType, plid, portletId);

		remove(portletPreferences);
	}

	public void removeAll() throws SystemException {
		for (PortletPreferences portletPreferences : findAll()) {
			remove(portletPreferences);
		}
	}

	public int countByPlid(long plid) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(plid) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PLID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);
				query.append("SELECT COUNT(portletPreferences) ");
				query.append(
					"FROM PortletPreferences portletPreferences WHERE ");

				query.append("portletPreferences.plid = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PLID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByP_P(long plid, String portletId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(plid), portletId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(9);
				query.append("SELECT COUNT(portletPreferences) ");
				query.append(
					"FROM PortletPreferences portletPreferences WHERE ");

				query.append("portletPreferences.plid = ?");

				query.append(" AND ");

				if (portletId == null) {
					query.append("portletPreferences.portletId IS NULL");
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(
							"(portletPreferences.portletId IS NULL OR ");
					}

					query.append("portletPreferences.portletId = ?");

					if (portletId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				if (portletId != null) {
					qPos.add(portletId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByO_O_P(long ownerId, int ownerType, long plid)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(ownerId), new Integer(ownerType), new Long(plid)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_O_O_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(8);
				query.append("SELECT COUNT(portletPreferences) ");
				query.append(
					"FROM PortletPreferences portletPreferences WHERE ");

				query.append("portletPreferences.ownerId = ?");

				query.append(" AND ");

				query.append("portletPreferences.ownerType = ?");

				query.append(" AND ");

				query.append("portletPreferences.plid = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerId);

				qPos.add(ownerType);

				qPos.add(plid);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_O_O_P,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByO_O_P_P(long ownerId, int ownerType, long plid,
		String portletId) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(ownerId), new Integer(ownerType), new Long(plid),
				
				portletId
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_O_O_P_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(13);
				query.append("SELECT COUNT(portletPreferences) ");
				query.append(
					"FROM PortletPreferences portletPreferences WHERE ");

				query.append("portletPreferences.ownerId = ?");

				query.append(" AND ");

				query.append("portletPreferences.ownerType = ?");

				query.append(" AND ");

				query.append("portletPreferences.plid = ?");

				query.append(" AND ");

				if (portletId == null) {
					query.append("portletPreferences.portletId IS NULL");
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(
							"(portletPreferences.portletId IS NULL OR ");
					}

					query.append("portletPreferences.portletId = ?");

					if (portletId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerId);

				qPos.add(ownerType);

				qPos.add(plid);

				if (portletId != null) {
					qPos.add(portletId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_O_O_P_P,
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
						"SELECT COUNT(portletPreferences) FROM PortletPreferences portletPreferences");

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
						"value.object.listener.com.liferay.portal.model.PortletPreferences")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PortletPreferences>> listenersList = new ArrayList<ModelListener<PortletPreferences>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PortletPreferences>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.BrowserTrackerPersistence")
	protected com.liferay.portal.service.persistence.BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPrototypePersistence")
	protected com.liferay.portal.service.persistence.LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPrototypePersistence")
	protected com.liferay.portal.service.persistence.LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LockPersistence")
	protected com.liferay.portal.service.persistence.LockPersistence lockPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupGroupRolePersistence")
	protected com.liferay.portal.service.persistence.UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static Log _log = LogFactoryUtil.getLog(PortletPreferencesPersistenceImpl.class);
}