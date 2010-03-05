/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchPortletItemException;
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.impl.PortletItemImpl;
import com.liferay.portal.model.impl.PortletItemModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="PortletItemPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletItemPersistence
 * @see       PortletItemUtil
 * @generated
 */
public class PortletItemPersistenceImpl extends BasePersistenceImpl<PortletItem>
	implements PortletItemPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = PortletItemImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_G_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_P_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_P_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_P_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_P_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_P_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N_P_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_N_P_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N_P_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_N_P_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(PortletItem portletItem) {
		EntityCacheUtil.putResult(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemImpl.class, portletItem.getPrimaryKey(), portletItem);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N_P_C,
			new Object[] {
				new Long(portletItem.getGroupId()),
				
			portletItem.getName(),
				
			portletItem.getPortletId(), new Long(portletItem.getClassNameId())
			}, portletItem);
	}

	public void cacheResult(List<PortletItem> portletItems) {
		for (PortletItem portletItem : portletItems) {
			if (EntityCacheUtil.getResult(
						PortletItemModelImpl.ENTITY_CACHE_ENABLED,
						PortletItemImpl.class, portletItem.getPrimaryKey(), this) == null) {
				cacheResult(portletItem);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(PortletItemImpl.class.getName());
		EntityCacheUtil.clearCache(PortletItemImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public PortletItem create(long portletItemId) {
		PortletItem portletItem = new PortletItemImpl();

		portletItem.setNew(true);
		portletItem.setPrimaryKey(portletItemId);

		return portletItem;
	}

	public PortletItem remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public PortletItem remove(long portletItemId)
		throws NoSuchPortletItemException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PortletItem portletItem = (PortletItem)session.get(PortletItemImpl.class,
					new Long(portletItemId));

			if (portletItem == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + portletItemId);
				}

				throw new NoSuchPortletItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					portletItemId);
			}

			return remove(portletItem);
		}
		catch (NoSuchPortletItemException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletItem remove(PortletItem portletItem)
		throws SystemException {
		for (ModelListener<PortletItem> listener : listeners) {
			listener.onBeforeRemove(portletItem);
		}

		portletItem = removeImpl(portletItem);

		for (ModelListener<PortletItem> listener : listeners) {
			listener.onAfterRemove(portletItem);
		}

		return portletItem;
	}

	protected PortletItem removeImpl(PortletItem portletItem)
		throws SystemException {
		portletItem = toUnwrappedModel(portletItem);

		Session session = null;

		try {
			session = openSession();

			if (portletItem.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(PortletItemImpl.class,
						portletItem.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(portletItem);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		PortletItemModelImpl portletItemModelImpl = (PortletItemModelImpl)portletItem;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N_P_C,
			new Object[] {
				new Long(portletItemModelImpl.getOriginalGroupId()),
				
			portletItemModelImpl.getOriginalName(),
				
			portletItemModelImpl.getOriginalPortletId(),
				new Long(portletItemModelImpl.getOriginalClassNameId())
			});

		EntityCacheUtil.removeResult(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemImpl.class, portletItem.getPrimaryKey());

		return portletItem;
	}

	public PortletItem updateImpl(
		com.liferay.portal.model.PortletItem portletItem, boolean merge)
		throws SystemException {
		portletItem = toUnwrappedModel(portletItem);

		boolean isNew = portletItem.isNew();

		PortletItemModelImpl portletItemModelImpl = (PortletItemModelImpl)portletItem;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, portletItem, merge);

			portletItem.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemImpl.class, portletItem.getPrimaryKey(), portletItem);

		if (!isNew &&
				((portletItem.getGroupId() != portletItemModelImpl.getOriginalGroupId()) ||
				!Validator.equals(portletItem.getName(),
					portletItemModelImpl.getOriginalName()) ||
				!Validator.equals(portletItem.getPortletId(),
					portletItemModelImpl.getOriginalPortletId()) ||
				(portletItem.getClassNameId() != portletItemModelImpl.getOriginalClassNameId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N_P_C,
				new Object[] {
					new Long(portletItemModelImpl.getOriginalGroupId()),
					
				portletItemModelImpl.getOriginalName(),
					
				portletItemModelImpl.getOriginalPortletId(),
					new Long(portletItemModelImpl.getOriginalClassNameId())
				});
		}

		if (isNew ||
				((portletItem.getGroupId() != portletItemModelImpl.getOriginalGroupId()) ||
				!Validator.equals(portletItem.getName(),
					portletItemModelImpl.getOriginalName()) ||
				!Validator.equals(portletItem.getPortletId(),
					portletItemModelImpl.getOriginalPortletId()) ||
				(portletItem.getClassNameId() != portletItemModelImpl.getOriginalClassNameId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N_P_C,
				new Object[] {
					new Long(portletItem.getGroupId()),
					
				portletItem.getName(),
					
				portletItem.getPortletId(),
					new Long(portletItem.getClassNameId())
				}, portletItem);
		}

		return portletItem;
	}

	protected PortletItem toUnwrappedModel(PortletItem portletItem) {
		if (portletItem instanceof PortletItemImpl) {
			return portletItem;
		}

		PortletItemImpl portletItemImpl = new PortletItemImpl();

		portletItemImpl.setNew(portletItem.isNew());
		portletItemImpl.setPrimaryKey(portletItem.getPrimaryKey());

		portletItemImpl.setPortletItemId(portletItem.getPortletItemId());
		portletItemImpl.setGroupId(portletItem.getGroupId());
		portletItemImpl.setCompanyId(portletItem.getCompanyId());
		portletItemImpl.setUserId(portletItem.getUserId());
		portletItemImpl.setUserName(portletItem.getUserName());
		portletItemImpl.setCreateDate(portletItem.getCreateDate());
		portletItemImpl.setModifiedDate(portletItem.getModifiedDate());
		portletItemImpl.setName(portletItem.getName());
		portletItemImpl.setPortletId(portletItem.getPortletId());
		portletItemImpl.setClassNameId(portletItem.getClassNameId());

		return portletItemImpl;
	}

	public PortletItem findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public PortletItem findByPrimaryKey(long portletItemId)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = fetchByPrimaryKey(portletItemId);

		if (portletItem == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + portletItemId);
			}

			throw new NoSuchPortletItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				portletItemId);
		}

		return portletItem;
	}

	public PortletItem fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public PortletItem fetchByPrimaryKey(long portletItemId)
		throws SystemException {
		PortletItem portletItem = (PortletItem)EntityCacheUtil.getResult(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
				PortletItemImpl.class, portletItemId, this);

		if (portletItem == null) {
			Session session = null;

			try {
				session = openSession();

				portletItem = (PortletItem)session.get(PortletItemImpl.class,
						new Long(portletItemId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (portletItem != null) {
					cacheResult(portletItem);
				}

				closeSession(session);
			}
		}

		return portletItem;
	}

	public List<PortletItem> findByG_C(long groupId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(classNameId)
			};

		List<PortletItem> list = (List<PortletItem>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_PORTLETITEM_WHERE);

				query.append(_FINDER_COLUMN_G_C_GROUPID_2);

				query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PortletItem>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_C, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<PortletItem> findByG_C(long groupId, long classNameId,
		int start, int end) throws SystemException {
		return findByG_C(groupId, classNameId, start, end, null);
	}

	public List<PortletItem> findByG_C(long groupId, long classNameId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(classNameId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<PortletItem> list = (List<PortletItem>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(4 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_PORTLETITEM_WHERE);

				query.append(_FINDER_COLUMN_G_C_GROUPID_2);

				query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				list = (List<PortletItem>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PortletItem>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public PortletItem findByG_C_First(long groupId, long classNameId,
		OrderByComparator obc)
		throws NoSuchPortletItemException, SystemException {
		List<PortletItem> list = findByG_C(groupId, classNameId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPortletItemException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletItem findByG_C_Last(long groupId, long classNameId,
		OrderByComparator obc)
		throws NoSuchPortletItemException, SystemException {
		int count = countByG_C(groupId, classNameId);

		List<PortletItem> list = findByG_C(groupId, classNameId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPortletItemException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletItem[] findByG_C_PrevAndNext(long portletItemId,
		long groupId, long classNameId, OrderByComparator obc)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = findByPrimaryKey(portletItemId);

		int count = countByG_C(groupId, classNameId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(4 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_PORTLETITEM_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					portletItem);

			PortletItem[] array = new PortletItemImpl[3];

			array[0] = (PortletItem)objArray[0];
			array[1] = (PortletItem)objArray[1];
			array[2] = (PortletItem)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<PortletItem> findByG_P_C(long groupId, String portletId,
		long classNameId) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				portletId, new Long(classNameId)
			};

		List<PortletItem> list = (List<PortletItem>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_P_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_PORTLETITEM_WHERE);

				query.append(_FINDER_COLUMN_G_P_C_GROUPID_2);

				if (portletId == null) {
					query.append(_FINDER_COLUMN_G_P_C_PORTLETID_1);
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_P_C_PORTLETID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_P_C_PORTLETID_2);
					}
				}

				query.append(_FINDER_COLUMN_G_P_C_CLASSNAMEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				qPos.add(classNameId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PortletItem>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_P_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<PortletItem> findByG_P_C(long groupId, String portletId,
		long classNameId, int start, int end) throws SystemException {
		return findByG_P_C(groupId, portletId, classNameId, start, end, null);
	}

	public List<PortletItem> findByG_P_C(long groupId, String portletId,
		long classNameId, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				portletId, new Long(classNameId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<PortletItem> list = (List<PortletItem>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_P_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(5 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_PORTLETITEM_WHERE);

				query.append(_FINDER_COLUMN_G_P_C_GROUPID_2);

				if (portletId == null) {
					query.append(_FINDER_COLUMN_G_P_C_PORTLETID_1);
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_P_C_PORTLETID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_P_C_PORTLETID_2);
					}
				}

				query.append(_FINDER_COLUMN_G_P_C_CLASSNAMEID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				qPos.add(classNameId);

				list = (List<PortletItem>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PortletItem>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_P_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public PortletItem findByG_P_C_First(long groupId, String portletId,
		long classNameId, OrderByComparator obc)
		throws NoSuchPortletItemException, SystemException {
		List<PortletItem> list = findByG_P_C(groupId, portletId, classNameId,
				0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", portletId=");
			msg.append(portletId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPortletItemException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletItem findByG_P_C_Last(long groupId, String portletId,
		long classNameId, OrderByComparator obc)
		throws NoSuchPortletItemException, SystemException {
		int count = countByG_P_C(groupId, portletId, classNameId);

		List<PortletItem> list = findByG_P_C(groupId, portletId, classNameId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", portletId=");
			msg.append(portletId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPortletItemException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletItem[] findByG_P_C_PrevAndNext(long portletItemId,
		long groupId, String portletId, long classNameId, OrderByComparator obc)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = findByPrimaryKey(portletItemId);

		int count = countByG_P_C(groupId, portletId, classNameId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(5 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_PORTLETITEM_WHERE);

			query.append(_FINDER_COLUMN_G_P_C_GROUPID_2);

			if (portletId == null) {
				query.append(_FINDER_COLUMN_G_P_C_PORTLETID_1);
			}
			else {
				if (portletId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_C_PORTLETID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_C_PORTLETID_2);
				}
			}

			query.append(_FINDER_COLUMN_G_P_C_CLASSNAMEID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (portletId != null) {
				qPos.add(portletId);
			}

			qPos.add(classNameId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					portletItem);

			PortletItem[] array = new PortletItemImpl[3];

			array[0] = (PortletItem)objArray[0];
			array[1] = (PortletItem)objArray[1];
			array[2] = (PortletItem)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletItem findByG_N_P_C(long groupId, String name,
		String portletId, long classNameId)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = fetchByG_N_P_C(groupId, name, portletId,
				classNameId);

		if (portletItem == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", portletId=");
			msg.append(portletId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPortletItemException(msg.toString());
		}

		return portletItem;
	}

	public PortletItem fetchByG_N_P_C(long groupId, String name,
		String portletId, long classNameId) throws SystemException {
		return fetchByG_N_P_C(groupId, name, portletId, classNameId, true);
	}

	public PortletItem fetchByG_N_P_C(long groupId, String name,
		String portletId, long classNameId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				name,
				
				portletId, new Long(classNameId)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_N_P_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_PORTLETITEM_WHERE);

				query.append(_FINDER_COLUMN_G_N_P_C_GROUPID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_G_N_P_C_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_N_P_C_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_N_P_C_NAME_2);
					}
				}

				if (portletId == null) {
					query.append(_FINDER_COLUMN_G_N_P_C_PORTLETID_1);
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_N_P_C_PORTLETID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_N_P_C_PORTLETID_2);
					}
				}

				query.append(_FINDER_COLUMN_G_N_P_C_CLASSNAMEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				if (portletId != null) {
					qPos.add(portletId);
				}

				qPos.add(classNameId);

				List<PortletItem> list = q.list();

				result = list;

				PortletItem portletItem = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N_P_C,
						finderArgs, list);
				}
				else {
					portletItem = list.get(0);

					cacheResult(portletItem);

					if ((portletItem.getGroupId() != groupId) ||
							(portletItem.getName() == null) ||
							!portletItem.getName().equals(name) ||
							(portletItem.getPortletId() == null) ||
							!portletItem.getPortletId().equals(portletId) ||
							(portletItem.getClassNameId() != classNameId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N_P_C,
							finderArgs, portletItem);
					}
				}

				return portletItem;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N_P_C,
						finderArgs, new ArrayList<PortletItem>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (PortletItem)result;
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

	public List<PortletItem> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<PortletItem> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<PortletItem> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<PortletItem> list = (List<PortletItem>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (obc != null) {
					query = new StringBundler(2 +
							(obc.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_PORTLETITEM);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				sql = _SQL_SELECT_PORTLETITEM;

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<PortletItem>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<PortletItem>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PortletItem>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByG_C(long groupId, long classNameId)
		throws SystemException {
		for (PortletItem portletItem : findByG_C(groupId, classNameId)) {
			remove(portletItem);
		}
	}

	public void removeByG_P_C(long groupId, String portletId, long classNameId)
		throws SystemException {
		for (PortletItem portletItem : findByG_P_C(groupId, portletId,
				classNameId)) {
			remove(portletItem);
		}
	}

	public void removeByG_N_P_C(long groupId, String name, String portletId,
		long classNameId) throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = findByG_N_P_C(groupId, name, portletId,
				classNameId);

		remove(portletItem);
	}

	public void removeAll() throws SystemException {
		for (PortletItem portletItem : findAll()) {
			remove(portletItem);
		}
	}

	public int countByG_C(long groupId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(classNameId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_PORTLETITEM_WHERE);

				query.append(_FINDER_COLUMN_G_C_GROUPID_2);

				query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_P_C(long groupId, String portletId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				portletId, new Long(classNameId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_PORTLETITEM_WHERE);

				query.append(_FINDER_COLUMN_G_P_C_GROUPID_2);

				if (portletId == null) {
					query.append(_FINDER_COLUMN_G_P_C_PORTLETID_1);
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_P_C_PORTLETID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_P_C_PORTLETID_2);
					}
				}

				query.append(_FINDER_COLUMN_G_P_C_CLASSNAMEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_N_P_C(long groupId, String name, String portletId,
		long classNameId) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				name,
				
				portletId, new Long(classNameId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_N_P_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_COUNT_PORTLETITEM_WHERE);

				query.append(_FINDER_COLUMN_G_N_P_C_GROUPID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_G_N_P_C_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_N_P_C_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_N_P_C_NAME_2);
					}
				}

				if (portletId == null) {
					query.append(_FINDER_COLUMN_G_N_P_C_PORTLETID_1);
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_N_P_C_PORTLETID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_N_P_C_PORTLETID_2);
					}
				}

				query.append(_FINDER_COLUMN_G_N_P_C_CLASSNAMEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				if (portletId != null) {
					qPos.add(portletId);
				}

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_N_P_C,
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

				Query q = session.createQuery(_SQL_COUNT_PORTLETITEM);

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
						"value.object.listener.com.liferay.portal.model.PortletItem")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PortletItem>> listenersList = new ArrayList<ModelListener<PortletItem>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PortletItem>)Class.forName(
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
	@BeanReference(name = "com.liferay.portal.service.persistence.TeamPersistence")
	protected com.liferay.portal.service.persistence.TeamPersistence teamPersistence;
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
	private static final String _SQL_SELECT_PORTLETITEM = "SELECT portletItem FROM PortletItem portletItem";
	private static final String _SQL_SELECT_PORTLETITEM_WHERE = "SELECT portletItem FROM PortletItem portletItem WHERE ";
	private static final String _SQL_COUNT_PORTLETITEM = "SELECT COUNT(portletItem) FROM PortletItem portletItem";
	private static final String _SQL_COUNT_PORTLETITEM_WHERE = "SELECT COUNT(portletItem) FROM PortletItem portletItem WHERE ";
	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "portletItem.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_CLASSNAMEID_2 = "portletItem.classNameId = ?";
	private static final String _FINDER_COLUMN_G_P_C_GROUPID_2 = "portletItem.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_C_PORTLETID_1 = "portletItem.portletId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_P_C_PORTLETID_2 = "portletItem.portletId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_C_PORTLETID_3 = "(portletItem.portletId IS NULL OR portletItem.portletId = ?) AND ";
	private static final String _FINDER_COLUMN_G_P_C_CLASSNAMEID_2 = "portletItem.classNameId = ?";
	private static final String _FINDER_COLUMN_G_N_P_C_GROUPID_2 = "portletItem.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_NAME_1 = "portletItem.name IS NULL AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_NAME_2 = "portletItem.lower(name) = ? AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_NAME_3 = "(portletItem.name IS NULL OR portletItem.lower(name) = ?) AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_PORTLETID_1 = "portletItem.portletId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_PORTLETID_2 = "portletItem.portletId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_PORTLETID_3 = "(portletItem.portletId IS NULL OR portletItem.portletId = ?) AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_CLASSNAMEID_2 = "portletItem.classNameId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "portletItem.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PortletItem exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PortletItem exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(PortletItemPersistenceImpl.class);
}