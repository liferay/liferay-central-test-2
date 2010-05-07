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

import com.liferay.portal.NoSuchListTypeException;
import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
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
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.ListTypeImpl;
import com.liferay.portal.model.impl.ListTypeModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ListTypePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ListTypePersistence
 * @see       ListTypeUtil
 * @generated
 */
public class ListTypePersistenceImpl extends BasePersistenceImpl<ListType>
	implements ListTypePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ListTypeImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_TYPE = new FinderPath(ListTypeModelImpl.ENTITY_CACHE_ENABLED,
			ListTypeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByType", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_TYPE = new FinderPath(ListTypeModelImpl.ENTITY_CACHE_ENABLED,
			ListTypeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByType",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_TYPE = new FinderPath(ListTypeModelImpl.ENTITY_CACHE_ENABLED,
			ListTypeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByType", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ListTypeModelImpl.ENTITY_CACHE_ENABLED,
			ListTypeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ListTypeModelImpl.ENTITY_CACHE_ENABLED,
			ListTypeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(ListType listType) {
		EntityCacheUtil.putResult(ListTypeModelImpl.ENTITY_CACHE_ENABLED,
			ListTypeImpl.class, listType.getPrimaryKey(), listType);
	}

	public void cacheResult(List<ListType> listTypes) {
		for (ListType listType : listTypes) {
			if (EntityCacheUtil.getResult(
						ListTypeModelImpl.ENTITY_CACHE_ENABLED,
						ListTypeImpl.class, listType.getPrimaryKey(), this) == null) {
				cacheResult(listType);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ListTypeImpl.class.getName());
		EntityCacheUtil.clearCache(ListTypeImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(ListType listType) {
		EntityCacheUtil.removeResult(ListTypeModelImpl.ENTITY_CACHE_ENABLED,
			ListTypeImpl.class, listType.getPrimaryKey());
	}

	public ListType create(int listTypeId) {
		ListType listType = new ListTypeImpl();

		listType.setNew(true);
		listType.setPrimaryKey(listTypeId);

		return listType;
	}

	public ListType remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Integer)primaryKey).intValue());
	}

	public ListType remove(int listTypeId)
		throws NoSuchListTypeException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ListType listType = (ListType)session.get(ListTypeImpl.class,
					new Integer(listTypeId));

			if (listType == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + listTypeId);
				}

				throw new NoSuchListTypeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					listTypeId);
			}

			return remove(listType);
		}
		catch (NoSuchListTypeException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ListType remove(ListType listType) throws SystemException {
		for (ModelListener<ListType> listener : listeners) {
			listener.onBeforeRemove(listType);
		}

		listType = removeImpl(listType);

		for (ModelListener<ListType> listener : listeners) {
			listener.onAfterRemove(listType);
		}

		return listType;
	}

	protected ListType removeImpl(ListType listType) throws SystemException {
		listType = toUnwrappedModel(listType);

		Session session = null;

		try {
			session = openSession();

			if (listType.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ListTypeImpl.class,
						listType.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(listType);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(ListTypeModelImpl.ENTITY_CACHE_ENABLED,
			ListTypeImpl.class, listType.getPrimaryKey());

		return listType;
	}

	public ListType updateImpl(com.liferay.portal.model.ListType listType,
		boolean merge) throws SystemException {
		listType = toUnwrappedModel(listType);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, listType, merge);

			listType.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ListTypeModelImpl.ENTITY_CACHE_ENABLED,
			ListTypeImpl.class, listType.getPrimaryKey(), listType);

		return listType;
	}

	protected ListType toUnwrappedModel(ListType listType) {
		if (listType instanceof ListTypeImpl) {
			return listType;
		}

		ListTypeImpl listTypeImpl = new ListTypeImpl();

		listTypeImpl.setNew(listType.isNew());
		listTypeImpl.setPrimaryKey(listType.getPrimaryKey());

		listTypeImpl.setListTypeId(listType.getListTypeId());
		listTypeImpl.setName(listType.getName());
		listTypeImpl.setType(listType.getType());

		return listTypeImpl;
	}

	public ListType findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Integer)primaryKey).intValue());
	}

	public ListType findByPrimaryKey(int listTypeId)
		throws NoSuchListTypeException, SystemException {
		ListType listType = fetchByPrimaryKey(listTypeId);

		if (listType == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + listTypeId);
			}

			throw new NoSuchListTypeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				listTypeId);
		}

		return listType;
	}

	public ListType fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Integer)primaryKey).intValue());
	}

	public ListType fetchByPrimaryKey(int listTypeId) throws SystemException {
		ListType listType = (ListType)EntityCacheUtil.getResult(ListTypeModelImpl.ENTITY_CACHE_ENABLED,
				ListTypeImpl.class, listTypeId, this);

		if (listType == null) {
			Session session = null;

			try {
				session = openSession();

				listType = (ListType)session.get(ListTypeImpl.class,
						new Integer(listTypeId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (listType != null) {
					cacheResult(listType);
				}

				closeSession(session);
			}
		}

		return listType;
	}

	public List<ListType> findByType(String type) throws SystemException {
		Object[] finderArgs = new Object[] { type };

		List<ListType> list = (List<ListType>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_TYPE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_LISTTYPE_WHERE);

				if (type == null) {
					query.append(_FINDER_COLUMN_TYPE_TYPE_1);
				}
				else {
					if (type.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_TYPE_TYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_TYPE_TYPE_2);
					}
				}

				query.append(ListTypeModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (type != null) {
					qPos.add(type);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ListType>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_TYPE, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ListType> findByType(String type, int start, int end)
		throws SystemException {
		return findByType(type, start, end, null);
	}

	public List<ListType> findByType(String type, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				type,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ListType> list = (List<ListType>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_TYPE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_LISTTYPE_WHERE);

				if (type == null) {
					query.append(_FINDER_COLUMN_TYPE_TYPE_1);
				}
				else {
					if (type.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_TYPE_TYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_TYPE_TYPE_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(ListTypeModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (type != null) {
					qPos.add(type);
				}

				list = (List<ListType>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ListType>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_TYPE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ListType findByType_First(String type,
		OrderByComparator orderByComparator)
		throws NoSuchListTypeException, SystemException {
		List<ListType> list = findByType(type, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchListTypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ListType findByType_Last(String type,
		OrderByComparator orderByComparator)
		throws NoSuchListTypeException, SystemException {
		int count = countByType(type);

		List<ListType> list = findByType(type, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchListTypeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ListType[] findByType_PrevAndNext(int listTypeId, String type,
		OrderByComparator orderByComparator)
		throws NoSuchListTypeException, SystemException {
		ListType listType = findByPrimaryKey(listTypeId);

		Session session = null;

		try {
			session = openSession();

			ListType[] array = new ListTypeImpl[3];

			array[0] = getByType_PrevAndNext(session, listType, type,
					orderByComparator, true);

			array[1] = listType;

			array[2] = getByType_PrevAndNext(session, listType, type,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ListType getByType_PrevAndNext(Session session,
		ListType listType, String type, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LISTTYPE_WHERE);

		if (type == null) {
			query.append(_FINDER_COLUMN_TYPE_TYPE_1);
		}
		else {
			if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_TYPE_TYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_TYPE_TYPE_2);
			}
		}

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(ListTypeModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		if (type != null) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(listType);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ListType> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<ListType> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ListType> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<ListType> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ListType> list = (List<ListType>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (orderByComparator != null) {
					query = new StringBundler(2 +
							(orderByComparator.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_LISTTYPE);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_LISTTYPE.concat(ListTypeModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ListType>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ListType>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ListType>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByType(String type) throws SystemException {
		for (ListType listType : findByType(type)) {
			remove(listType);
		}
	}

	public void removeAll() throws SystemException {
		for (ListType listType : findAll()) {
			remove(listType);
		}
	}

	public int countByType(String type) throws SystemException {
		Object[] finderArgs = new Object[] { type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TYPE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_LISTTYPE_WHERE);

				if (type == null) {
					query.append(_FINDER_COLUMN_TYPE_TYPE_1);
				}
				else {
					if (type.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_TYPE_TYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_TYPE_TYPE_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TYPE,
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

				Query q = session.createQuery(_SQL_COUNT_LISTTYPE);

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
						"value.object.listener.com.liferay.portal.model.ListType")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ListType>> listenersList = new ArrayList<ModelListener<ListType>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ListType>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupPermissionPersistence.class)
	protected OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PermissionPersistence.class)
	protected PermissionPersistence permissionPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceCodePersistence.class)
	protected ResourceCodePersistence resourceCodePersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_LISTTYPE = "SELECT listType FROM ListType listType";
	private static final String _SQL_SELECT_LISTTYPE_WHERE = "SELECT listType FROM ListType listType WHERE ";
	private static final String _SQL_COUNT_LISTTYPE = "SELECT COUNT(listType) FROM ListType listType";
	private static final String _SQL_COUNT_LISTTYPE_WHERE = "SELECT COUNT(listType) FROM ListType listType WHERE ";
	private static final String _FINDER_COLUMN_TYPE_TYPE_1 = "listType.type IS NULL";
	private static final String _FINDER_COLUMN_TYPE_TYPE_2 = "listType.type = ?";
	private static final String _FINDER_COLUMN_TYPE_TYPE_3 = "(listType.type IS NULL OR listType.type = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "listType.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ListType exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ListType exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(ListTypePersistenceImpl.class);
}