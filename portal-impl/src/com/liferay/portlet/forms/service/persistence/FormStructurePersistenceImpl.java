/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.forms.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.forms.NoSuchFormStructureException;
import com.liferay.portlet.forms.model.FormStructure;
import com.liferay.portlet.forms.model.impl.FormStructureImpl;
import com.liferay.portlet.forms.model.impl.FormStructureModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the form structure service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FormStructurePersistence
 * @see FormStructureUtil
 * @generated
 */
public class FormStructurePersistenceImpl extends BasePersistenceImpl<FormStructure>
	implements FormStructurePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link FormStructureUtil} to access the form structure persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = FormStructureImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(FormStructureModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(FormStructureModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(FormStructureModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(FormStructureModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(FormStructureModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(FormStructureModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F = new FinderPath(FormStructureModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_F",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(FormStructureModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_F",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(FormStructureModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FormStructureModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the form structure in the entity cache if it is enabled.
	 *
	 * @param formStructure the form structure to cache
	 */
	public void cacheResult(FormStructure formStructure) {
		EntityCacheUtil.putResult(FormStructureModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureImpl.class, formStructure.getPrimaryKey(),
			formStructure);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				formStructure.getUuid(), new Long(formStructure.getGroupId())
			}, formStructure);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
			new Object[] {
				new Long(formStructure.getGroupId()),
				
			formStructure.getFormStructureId()
			}, formStructure);
	}

	/**
	 * Caches the form structures in the entity cache if it is enabled.
	 *
	 * @param formStructures the form structures to cache
	 */
	public void cacheResult(List<FormStructure> formStructures) {
		for (FormStructure formStructure : formStructures) {
			if (EntityCacheUtil.getResult(
						FormStructureModelImpl.ENTITY_CACHE_ENABLED,
						FormStructureImpl.class, formStructure.getPrimaryKey(),
						this) == null) {
				cacheResult(formStructure);
			}
		}
	}

	/**
	 * Clears the cache for all form structures.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(FormStructureImpl.class.getName());
		EntityCacheUtil.clearCache(FormStructureImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the form structure.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(FormStructure formStructure) {
		EntityCacheUtil.removeResult(FormStructureModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureImpl.class, formStructure.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				formStructure.getUuid(), new Long(formStructure.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F,
			new Object[] {
				new Long(formStructure.getGroupId()),
				
			formStructure.getFormStructureId()
			});
	}

	/**
	 * Creates a new form structure with the primary key. Does not add the form structure to the database.
	 *
	 * @param id the primary key for the new form structure
	 * @return the new form structure
	 */
	public FormStructure create(long id) {
		FormStructure formStructure = new FormStructureImpl();

		formStructure.setNew(true);
		formStructure.setPrimaryKey(id);

		String uuid = PortalUUIDUtil.generate();

		formStructure.setUuid(uuid);

		return formStructure;
	}

	/**
	 * Removes the form structure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the form structure to remove
	 * @return the form structure that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a form structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the form structure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the form structure to remove
	 * @return the form structure that was removed
	 * @throws com.liferay.portlet.forms.NoSuchFormStructureException if a form structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure remove(long id)
		throws NoSuchFormStructureException, SystemException {
		Session session = null;

		try {
			session = openSession();

			FormStructure formStructure = (FormStructure)session.get(FormStructureImpl.class,
					new Long(id));

			if (formStructure == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
				}

				throw new NoSuchFormStructureException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					id);
			}

			return formStructurePersistence.remove(formStructure);
		}
		catch (NoSuchFormStructureException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes the form structure from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formStructure the form structure to remove
	 * @return the form structure that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure remove(FormStructure formStructure)
		throws SystemException {
		return super.remove(formStructure);
	}

	protected FormStructure removeImpl(FormStructure formStructure)
		throws SystemException {
		formStructure = toUnwrappedModel(formStructure);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, formStructure);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		FormStructureModelImpl formStructureModelImpl = (FormStructureModelImpl)formStructure;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				formStructureModelImpl.getOriginalUuid(),
				new Long(formStructureModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F,
			new Object[] {
				new Long(formStructureModelImpl.getOriginalGroupId()),
				
			formStructureModelImpl.getOriginalFormStructureId()
			});

		EntityCacheUtil.removeResult(FormStructureModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureImpl.class, formStructure.getPrimaryKey());

		return formStructure;
	}

	public FormStructure updateImpl(
		com.liferay.portlet.forms.model.FormStructure formStructure,
		boolean merge) throws SystemException {
		formStructure = toUnwrappedModel(formStructure);

		boolean isNew = formStructure.isNew();

		FormStructureModelImpl formStructureModelImpl = (FormStructureModelImpl)formStructure;

		if (Validator.isNull(formStructure.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			formStructure.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, formStructure, merge);

			formStructure.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(FormStructureModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureImpl.class, formStructure.getPrimaryKey(),
			formStructure);

		if (!isNew &&
				(!Validator.equals(formStructure.getUuid(),
					formStructureModelImpl.getOriginalUuid()) ||
				(formStructure.getGroupId() != formStructureModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					formStructureModelImpl.getOriginalUuid(),
					new Long(formStructureModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(formStructure.getUuid(),
					formStructureModelImpl.getOriginalUuid()) ||
				(formStructure.getGroupId() != formStructureModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					formStructure.getUuid(),
					new Long(formStructure.getGroupId())
				}, formStructure);
		}

		if (!isNew &&
				((formStructure.getGroupId() != formStructureModelImpl.getOriginalGroupId()) ||
				!Validator.equals(formStructure.getFormStructureId(),
					formStructureModelImpl.getOriginalFormStructureId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F,
				new Object[] {
					new Long(formStructureModelImpl.getOriginalGroupId()),
					
				formStructureModelImpl.getOriginalFormStructureId()
				});
		}

		if (isNew ||
				((formStructure.getGroupId() != formStructureModelImpl.getOriginalGroupId()) ||
				!Validator.equals(formStructure.getFormStructureId(),
					formStructureModelImpl.getOriginalFormStructureId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
				new Object[] {
					new Long(formStructure.getGroupId()),
					
				formStructure.getFormStructureId()
				}, formStructure);
		}

		return formStructure;
	}

	protected FormStructure toUnwrappedModel(FormStructure formStructure) {
		if (formStructure instanceof FormStructureImpl) {
			return formStructure;
		}

		FormStructureImpl formStructureImpl = new FormStructureImpl();

		formStructureImpl.setNew(formStructure.isNew());
		formStructureImpl.setPrimaryKey(formStructure.getPrimaryKey());

		formStructureImpl.setUuid(formStructure.getUuid());
		formStructureImpl.setId(formStructure.getId());
		formStructureImpl.setGroupId(formStructure.getGroupId());
		formStructureImpl.setCompanyId(formStructure.getCompanyId());
		formStructureImpl.setUserId(formStructure.getUserId());
		formStructureImpl.setUserName(formStructure.getUserName());
		formStructureImpl.setCreateDate(formStructure.getCreateDate());
		formStructureImpl.setModifiedDate(formStructure.getModifiedDate());
		formStructureImpl.setFormStructureId(formStructure.getFormStructureId());
		formStructureImpl.setName(formStructure.getName());
		formStructureImpl.setDescription(formStructure.getDescription());
		formStructureImpl.setXsd(formStructure.getXsd());

		return formStructureImpl;
	}

	/**
	 * Finds the form structure with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the form structure to find
	 * @return the form structure
	 * @throws com.liferay.portal.NoSuchModelException if a form structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the form structure with the primary key or throws a {@link com.liferay.portlet.forms.NoSuchFormStructureException} if it could not be found.
	 *
	 * @param id the primary key of the form structure to find
	 * @return the form structure
	 * @throws com.liferay.portlet.forms.NoSuchFormStructureException if a form structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure findByPrimaryKey(long id)
		throws NoSuchFormStructureException, SystemException {
		FormStructure formStructure = fetchByPrimaryKey(id);

		if (formStructure == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
			}

			throw new NoSuchFormStructureException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				id);
		}

		return formStructure;
	}

	/**
	 * Finds the form structure with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the form structure to find
	 * @return the form structure, or <code>null</code> if a form structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the form structure with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the form structure to find
	 * @return the form structure, or <code>null</code> if a form structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure fetchByPrimaryKey(long id) throws SystemException {
		FormStructure formStructure = (FormStructure)EntityCacheUtil.getResult(FormStructureModelImpl.ENTITY_CACHE_ENABLED,
				FormStructureImpl.class, id, this);

		if (formStructure == null) {
			Session session = null;

			try {
				session = openSession();

				formStructure = (FormStructure)session.get(FormStructureImpl.class,
						new Long(id));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (formStructure != null) {
					cacheResult(formStructure);
				}

				closeSession(session);
			}
		}

		return formStructure;
	}

	/**
	 * Finds all the form structures where uuid = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @return the matching form structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructure> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the form structures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param start the lower bound of the range of form structures to return
	 * @param end the upper bound of the range of form structures to return (not inclusive)
	 * @return the range of matching form structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructure> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Finds an ordered range of all the form structures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param start the lower bound of the range of form structures to return
	 * @param end the upper bound of the range of form structures to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching form structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructure> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<FormStructure> list = (List<FormStructure>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_FORMSTRUCTURE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<FormStructure>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_UUID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first form structure in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching form structure
	 * @throws com.liferay.portlet.forms.NoSuchFormStructureException if a matching form structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFormStructureException, SystemException {
		List<FormStructure> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFormStructureException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last form structure in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching form structure
	 * @throws com.liferay.portlet.forms.NoSuchFormStructureException if a matching form structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFormStructureException, SystemException {
		int count = countByUuid(uuid);

		List<FormStructure> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFormStructureException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the form structures before and after the current form structure in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current form structure
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next form structure
	 * @throws com.liferay.portlet.forms.NoSuchFormStructureException if a form structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure[] findByUuid_PrevAndNext(long id, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFormStructureException, SystemException {
		FormStructure formStructure = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			FormStructure[] array = new FormStructureImpl[3];

			array[0] = getByUuid_PrevAndNext(session, formStructure, uuid,
					orderByComparator, true);

			array[1] = formStructure;

			array[2] = getByUuid_PrevAndNext(session, formStructure, uuid,
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

	protected FormStructure getByUuid_PrevAndNext(Session session,
		FormStructure formStructure, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_FORMSTRUCTURE_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
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
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(formStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FormStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the form structure where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.forms.NoSuchFormStructureException} if it could not be found.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the matching form structure
	 * @throws com.liferay.portlet.forms.NoSuchFormStructureException if a matching form structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure findByUUID_G(String uuid, long groupId)
		throws NoSuchFormStructureException, SystemException {
		FormStructure formStructure = fetchByUUID_G(uuid, groupId);

		if (formStructure == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFormStructureException(msg.toString());
		}

		return formStructure;
	}

	/**
	 * Finds the form structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the matching form structure, or <code>null</code> if a matching form structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Finds the form structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the matching form structure, or <code>null</code> if a matching form structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_FORMSTRUCTURE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<FormStructure> list = q.list();

				result = list;

				FormStructure formStructure = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					formStructure = list.get(0);

					cacheResult(formStructure);

					if ((formStructure.getUuid() == null) ||
							!formStructure.getUuid().equals(uuid) ||
							(formStructure.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, formStructure);
					}
				}

				return formStructure;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (FormStructure)result;
			}
		}
	}

	/**
	 * Finds all the form structures where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the matching form structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructure> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the form structures where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of form structures to return
	 * @param end the upper bound of the range of form structures to return (not inclusive)
	 * @return the range of matching form structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructure> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the form structures where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of form structures to return
	 * @param end the upper bound of the range of form structures to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching form structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructure> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<FormStructure> list = (List<FormStructure>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_FORMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<FormStructure>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_GROUPID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first form structure in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching form structure
	 * @throws com.liferay.portlet.forms.NoSuchFormStructureException if a matching form structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFormStructureException, SystemException {
		List<FormStructure> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFormStructureException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last form structure in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching form structure
	 * @throws com.liferay.portlet.forms.NoSuchFormStructureException if a matching form structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFormStructureException, SystemException {
		int count = countByGroupId(groupId);

		List<FormStructure> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFormStructureException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the form structures before and after the current form structure in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current form structure
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next form structure
	 * @throws com.liferay.portlet.forms.NoSuchFormStructureException if a form structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure[] findByGroupId_PrevAndNext(long id, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFormStructureException, SystemException {
		FormStructure formStructure = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			FormStructure[] array = new FormStructureImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, formStructure,
					groupId, orderByComparator, true);

			array[1] = formStructure;

			array[2] = getByGroupId_PrevAndNext(session, formStructure,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected FormStructure getByGroupId_PrevAndNext(Session session,
		FormStructure formStructure, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_FORMSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(formStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FormStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the form structures where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the matching form structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructure> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the form structures where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of form structures to return
	 * @param end the upper bound of the range of form structures to return (not inclusive)
	 * @return the range of matching form structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructure> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the form structures where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of form structures to return
	 * @param end the upper bound of the range of form structures to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching form structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructure> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(2);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_FORMSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_FORMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_FORMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				FormStructure.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, FormStructureImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, FormStructureImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<FormStructure>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Finds the form structure where groupId = &#63; and formStructureId = &#63; or throws a {@link com.liferay.portlet.forms.NoSuchFormStructureException} if it could not be found.
	 *
	 * @param groupId the group ID to search with
	 * @param formStructureId the form structure ID to search with
	 * @return the matching form structure
	 * @throws com.liferay.portlet.forms.NoSuchFormStructureException if a matching form structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure findByG_F(long groupId, String formStructureId)
		throws NoSuchFormStructureException, SystemException {
		FormStructure formStructure = fetchByG_F(groupId, formStructureId);

		if (formStructure == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", formStructureId=");
			msg.append(formStructureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFormStructureException(msg.toString());
		}

		return formStructure;
	}

	/**
	 * Finds the form structure where groupId = &#63; and formStructureId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID to search with
	 * @param formStructureId the form structure ID to search with
	 * @return the matching form structure, or <code>null</code> if a matching form structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure fetchByG_F(long groupId, String formStructureId)
		throws SystemException {
		return fetchByG_F(groupId, formStructureId, true);
	}

	/**
	 * Finds the form structure where groupId = &#63; and formStructureId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID to search with
	 * @param formStructureId the form structure ID to search with
	 * @return the matching form structure, or <code>null</code> if a matching form structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructure fetchByG_F(long groupId, String formStructureId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, formStructureId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_F,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_FORMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			if (formStructureId == null) {
				query.append(_FINDER_COLUMN_G_F_FORMSTRUCTUREID_1);
			}
			else {
				if (formStructureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_FORMSTRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_FORMSTRUCTUREID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (formStructureId != null) {
					qPos.add(formStructureId);
				}

				List<FormStructure> list = q.list();

				result = list;

				FormStructure formStructure = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
						finderArgs, list);
				}
				else {
					formStructure = list.get(0);

					cacheResult(formStructure);

					if ((formStructure.getGroupId() != groupId) ||
							(formStructure.getFormStructureId() == null) ||
							!formStructure.getFormStructureId()
											  .equals(formStructureId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
							finderArgs, formStructure);
					}
				}

				return formStructure;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (FormStructure)result;
			}
		}
	}

	/**
	 * Finds all the form structures.
	 *
	 * @return the form structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructure> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the form structures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of form structures to return
	 * @param end the upper bound of the range of form structures to return (not inclusive)
	 * @return the range of form structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructure> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the form structures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of form structures to return
	 * @param end the upper bound of the range of form structures to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of form structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructure> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<FormStructure> list = (List<FormStructure>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_FORMSTRUCTURE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_FORMSTRUCTURE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<FormStructure>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<FormStructure>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs,
						list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the form structures where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (FormStructure formStructure : findByUuid(uuid)) {
			formStructurePersistence.remove(formStructure);
		}
	}

	/**
	 * Removes the form structure where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchFormStructureException, SystemException {
		FormStructure formStructure = findByUUID_G(uuid, groupId);

		formStructurePersistence.remove(formStructure);
	}

	/**
	 * Removes all the form structures where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (FormStructure formStructure : findByGroupId(groupId)) {
			formStructurePersistence.remove(formStructure);
		}
	}

	/**
	 * Removes the form structure where groupId = &#63; and formStructureId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param formStructureId the form structure ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_F(long groupId, String formStructureId)
		throws NoSuchFormStructureException, SystemException {
		FormStructure formStructure = findByG_F(groupId, formStructureId);

		formStructurePersistence.remove(formStructure);
	}

	/**
	 * Removes all the form structures from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (FormStructure formStructure : findAll()) {
			formStructurePersistence.remove(formStructure);
		}
	}

	/**
	 * Counts all the form structures where uuid = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @return the number of matching form structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_FORMSTRUCTURE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the form structures where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the number of matching form structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FORMSTRUCTURE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the form structures where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the number of matching form structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_FORMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Filters by the user's permissions and counts all the form structures where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the number of matching form structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_FORMSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				FormStructure.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Counts all the form structures where groupId = &#63; and formStructureId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param formStructureId the form structure ID to search with
	 * @return the number of matching form structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F(long groupId, String formStructureId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, formStructureId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FORMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			if (formStructureId == null) {
				query.append(_FINDER_COLUMN_G_F_FORMSTRUCTUREID_1);
			}
			else {
				if (formStructureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_FORMSTRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_FORMSTRUCTUREID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (formStructureId != null) {
					qPos.add(formStructureId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the form structures.
	 *
	 * @return the number of form structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_FORMSTRUCTURE);

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

	/**
	 * Initializes the form structure persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.forms.model.FormStructure")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<FormStructure>> listenersList = new ArrayList<ModelListener<FormStructure>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<FormStructure>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(FormStructureImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = FormStructurePersistence.class)
	protected FormStructurePersistence formStructurePersistence;
	@BeanReference(type = FormStructureLinkPersistence.class)
	protected FormStructureLinkPersistence formStructureLinkPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	private static final String _SQL_SELECT_FORMSTRUCTURE = "SELECT formStructure FROM FormStructure formStructure";
	private static final String _SQL_SELECT_FORMSTRUCTURE_WHERE = "SELECT formStructure FROM FormStructure formStructure WHERE ";
	private static final String _SQL_COUNT_FORMSTRUCTURE = "SELECT COUNT(formStructure) FROM FormStructure formStructure";
	private static final String _SQL_COUNT_FORMSTRUCTURE_WHERE = "SELECT COUNT(formStructure) FROM FormStructure formStructure WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "formStructure.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "formStructure.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(formStructure.uuid IS NULL OR formStructure.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "formStructure.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "formStructure.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(formStructure.uuid IS NULL OR formStructure.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "formStructure.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "formStructure.groupId = ?";
	private static final String _FINDER_COLUMN_G_F_GROUPID_2 = "formStructure.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_FORMSTRUCTUREID_1 = "formStructure.formStructureId IS NULL";
	private static final String _FINDER_COLUMN_G_F_FORMSTRUCTUREID_2 = "formStructure.formStructureId = ?";
	private static final String _FINDER_COLUMN_G_F_FORMSTRUCTUREID_3 = "(formStructure.formStructureId IS NULL OR formStructure.formStructureId = ?)";
	private static final String _FILTER_SQL_SELECT_FORMSTRUCTURE_WHERE = "SELECT DISTINCT {formStructure.*} FROM FormStructure formStructure WHERE ";
	private static final String _FILTER_SQL_SELECT_FORMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {FormStructure.*} FROM (SELECT DISTINCT formStructure.id FROM FormStructure formStructure WHERE ";
	private static final String _FILTER_SQL_SELECT_FORMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN FormStructure ON TEMP_TABLE.id = FormStructure.id";
	private static final String _FILTER_SQL_COUNT_FORMSTRUCTURE_WHERE = "SELECT COUNT(DISTINCT formStructure.id) AS COUNT_VALUE FROM FormStructure formStructure WHERE ";
	private static final String _FILTER_COLUMN_PK = "formStructure.id";
	private static final String _FILTER_COLUMN_USERID = "formStructure.userId";
	private static final String _FILTER_ENTITY_ALIAS = "formStructure";
	private static final String _FILTER_ENTITY_TABLE = "FormStructure";
	private static final String _ORDER_BY_ENTITY_ALIAS = "formStructure.";
	private static final String _ORDER_BY_ENTITY_TABLE = "FormStructure.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No FormStructure exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No FormStructure exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(FormStructurePersistenceImpl.class);
}