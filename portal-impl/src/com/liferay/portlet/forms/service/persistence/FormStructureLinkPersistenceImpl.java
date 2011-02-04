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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.forms.NoSuchFormStructureLinkException;
import com.liferay.portlet.forms.model.FormStructureLink;
import com.liferay.portlet.forms.model.impl.FormStructureLinkImpl;
import com.liferay.portlet.forms.model.impl.FormStructureLinkModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the form structure link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FormStructureLinkPersistence
 * @see FormStructureLinkUtil
 * @generated
 */
public class FormStructureLinkPersistenceImpl extends BasePersistenceImpl<FormStructureLink>
	implements FormStructureLinkPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link FormStructureLinkUtil} to access the form structure link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = FormStructureLinkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_FORMSTRUCTURELINKID = new FinderPath(FormStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByFormStructureLinkId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_FORMSTRUCTURELINKID = new FinderPath(FormStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByFormStructureLinkId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_FORMSTRUCTUREID = new FinderPath(FormStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByFormStructureId",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_FORMSTRUCTUREID = new FinderPath(FormStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByFormStructureId",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_F_C_C = new FinderPath(FormStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByF_C_C",
			new String[] {
				String.class.getName(), String.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_F_C_C = new FinderPath(FormStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByF_C_C",
			new String[] {
				String.class.getName(), String.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(FormStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FormStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the form structure link in the entity cache if it is enabled.
	 *
	 * @param formStructureLink the form structure link to cache
	 */
	public void cacheResult(FormStructureLink formStructureLink) {
		EntityCacheUtil.putResult(FormStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureLinkImpl.class, formStructureLink.getPrimaryKey(),
			formStructureLink);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FORMSTRUCTURELINKID,
			new Object[] { new Long(formStructureLink.getFormStructureLinkId()) },
			formStructureLink);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_C_C,
			new Object[] {
				formStructureLink.getFormStructureId(),
				
			formStructureLink.getClassName(),
				new Long(formStructureLink.getClassPK())
			}, formStructureLink);
	}

	/**
	 * Caches the form structure links in the entity cache if it is enabled.
	 *
	 * @param formStructureLinks the form structure links to cache
	 */
	public void cacheResult(List<FormStructureLink> formStructureLinks) {
		for (FormStructureLink formStructureLink : formStructureLinks) {
			if (EntityCacheUtil.getResult(
						FormStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
						FormStructureLinkImpl.class,
						formStructureLink.getPrimaryKey(), this) == null) {
				cacheResult(formStructureLink);
			}
		}
	}

	/**
	 * Clears the cache for all form structure links.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(FormStructureLinkImpl.class.getName());
		EntityCacheUtil.clearCache(FormStructureLinkImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the form structure link.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(FormStructureLink formStructureLink) {
		EntityCacheUtil.removeResult(FormStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureLinkImpl.class, formStructureLink.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_FORMSTRUCTURELINKID,
			new Object[] { new Long(formStructureLink.getFormStructureLinkId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_F_C_C,
			new Object[] {
				formStructureLink.getFormStructureId(),
				
			formStructureLink.getClassName(),
				new Long(formStructureLink.getClassPK())
			});
	}

	/**
	 * Creates a new form structure link with the primary key. Does not add the form structure link to the database.
	 *
	 * @param formStructureLinkId the primary key for the new form structure link
	 * @return the new form structure link
	 */
	public FormStructureLink create(long formStructureLinkId) {
		FormStructureLink formStructureLink = new FormStructureLinkImpl();

		formStructureLink.setNew(true);
		formStructureLink.setPrimaryKey(formStructureLinkId);

		return formStructureLink;
	}

	/**
	 * Removes the form structure link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the form structure link to remove
	 * @return the form structure link that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a form structure link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructureLink remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the form structure link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formStructureLinkId the primary key of the form structure link to remove
	 * @return the form structure link that was removed
	 * @throws com.liferay.portlet.forms.NoSuchFormStructureLinkException if a form structure link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructureLink remove(long formStructureLinkId)
		throws NoSuchFormStructureLinkException, SystemException {
		Session session = null;

		try {
			session = openSession();

			FormStructureLink formStructureLink = (FormStructureLink)session.get(FormStructureLinkImpl.class,
					new Long(formStructureLinkId));

			if (formStructureLink == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						formStructureLinkId);
				}

				throw new NoSuchFormStructureLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					formStructureLinkId);
			}

			return formStructureLinkPersistence.remove(formStructureLink);
		}
		catch (NoSuchFormStructureLinkException nsee) {
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
	 * Removes the form structure link from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formStructureLink the form structure link to remove
	 * @return the form structure link that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructureLink remove(FormStructureLink formStructureLink)
		throws SystemException {
		return super.remove(formStructureLink);
	}

	protected FormStructureLink removeImpl(FormStructureLink formStructureLink)
		throws SystemException {
		formStructureLink = toUnwrappedModel(formStructureLink);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, formStructureLink);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		FormStructureLinkModelImpl formStructureLinkModelImpl = (FormStructureLinkModelImpl)formStructureLink;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_FORMSTRUCTURELINKID,
			new Object[] {
				new Long(formStructureLinkModelImpl.getOriginalFormStructureLinkId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_F_C_C,
			new Object[] {
				formStructureLinkModelImpl.getOriginalFormStructureId(),
				
			formStructureLinkModelImpl.getOriginalClassName(),
				new Long(formStructureLinkModelImpl.getOriginalClassPK())
			});

		EntityCacheUtil.removeResult(FormStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureLinkImpl.class, formStructureLink.getPrimaryKey());

		return formStructureLink;
	}

	public FormStructureLink updateImpl(
		com.liferay.portlet.forms.model.FormStructureLink formStructureLink,
		boolean merge) throws SystemException {
		formStructureLink = toUnwrappedModel(formStructureLink);

		boolean isNew = formStructureLink.isNew();

		FormStructureLinkModelImpl formStructureLinkModelImpl = (FormStructureLinkModelImpl)formStructureLink;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, formStructureLink, merge);

			formStructureLink.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(FormStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormStructureLinkImpl.class, formStructureLink.getPrimaryKey(),
			formStructureLink);

		if (!isNew &&
				(formStructureLink.getFormStructureLinkId() != formStructureLinkModelImpl.getOriginalFormStructureLinkId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_FORMSTRUCTURELINKID,
				new Object[] {
					new Long(formStructureLinkModelImpl.getOriginalFormStructureLinkId())
				});
		}

		if (isNew ||
				(formStructureLink.getFormStructureLinkId() != formStructureLinkModelImpl.getOriginalFormStructureLinkId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FORMSTRUCTURELINKID,
				new Object[] {
					new Long(formStructureLink.getFormStructureLinkId())
				}, formStructureLink);
		}

		if (!isNew &&
				(!Validator.equals(formStructureLink.getFormStructureId(),
					formStructureLinkModelImpl.getOriginalFormStructureId()) ||
				!Validator.equals(formStructureLink.getClassName(),
					formStructureLinkModelImpl.getOriginalClassName()) ||
				(formStructureLink.getClassPK() != formStructureLinkModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_F_C_C,
				new Object[] {
					formStructureLinkModelImpl.getOriginalFormStructureId(),
					
				formStructureLinkModelImpl.getOriginalClassName(),
					new Long(formStructureLinkModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				(!Validator.equals(formStructureLink.getFormStructureId(),
					formStructureLinkModelImpl.getOriginalFormStructureId()) ||
				!Validator.equals(formStructureLink.getClassName(),
					formStructureLinkModelImpl.getOriginalClassName()) ||
				(formStructureLink.getClassPK() != formStructureLinkModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_C_C,
				new Object[] {
					formStructureLink.getFormStructureId(),
					
				formStructureLink.getClassName(),
					new Long(formStructureLink.getClassPK())
				}, formStructureLink);
		}

		return formStructureLink;
	}

	protected FormStructureLink toUnwrappedModel(
		FormStructureLink formStructureLink) {
		if (formStructureLink instanceof FormStructureLinkImpl) {
			return formStructureLink;
		}

		FormStructureLinkImpl formStructureLinkImpl = new FormStructureLinkImpl();

		formStructureLinkImpl.setNew(formStructureLink.isNew());
		formStructureLinkImpl.setPrimaryKey(formStructureLink.getPrimaryKey());

		formStructureLinkImpl.setFormStructureLinkId(formStructureLink.getFormStructureLinkId());
		formStructureLinkImpl.setFormStructureId(formStructureLink.getFormStructureId());
		formStructureLinkImpl.setClassName(formStructureLink.getClassName());
		formStructureLinkImpl.setClassPK(formStructureLink.getClassPK());

		return formStructureLinkImpl;
	}

	/**
	 * Finds the form structure link with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the form structure link to find
	 * @return the form structure link
	 * @throws com.liferay.portal.NoSuchModelException if a form structure link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructureLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the form structure link with the primary key or throws a {@link com.liferay.portlet.forms.NoSuchFormStructureLinkException} if it could not be found.
	 *
	 * @param formStructureLinkId the primary key of the form structure link to find
	 * @return the form structure link
	 * @throws com.liferay.portlet.forms.NoSuchFormStructureLinkException if a form structure link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructureLink findByPrimaryKey(long formStructureLinkId)
		throws NoSuchFormStructureLinkException, SystemException {
		FormStructureLink formStructureLink = fetchByPrimaryKey(formStructureLinkId);

		if (formStructureLink == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					formStructureLinkId);
			}

			throw new NoSuchFormStructureLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				formStructureLinkId);
		}

		return formStructureLink;
	}

	/**
	 * Finds the form structure link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the form structure link to find
	 * @return the form structure link, or <code>null</code> if a form structure link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructureLink fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the form structure link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param formStructureLinkId the primary key of the form structure link to find
	 * @return the form structure link, or <code>null</code> if a form structure link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructureLink fetchByPrimaryKey(long formStructureLinkId)
		throws SystemException {
		FormStructureLink formStructureLink = (FormStructureLink)EntityCacheUtil.getResult(FormStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
				FormStructureLinkImpl.class, formStructureLinkId, this);

		if (formStructureLink == null) {
			Session session = null;

			try {
				session = openSession();

				formStructureLink = (FormStructureLink)session.get(FormStructureLinkImpl.class,
						new Long(formStructureLinkId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (formStructureLink != null) {
					cacheResult(formStructureLink);
				}

				closeSession(session);
			}
		}

		return formStructureLink;
	}

	/**
	 * Finds the form structure link where formStructureLinkId = &#63; or throws a {@link com.liferay.portlet.forms.NoSuchFormStructureLinkException} if it could not be found.
	 *
	 * @param formStructureLinkId the form structure link ID to search with
	 * @return the matching form structure link
	 * @throws com.liferay.portlet.forms.NoSuchFormStructureLinkException if a matching form structure link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructureLink findByFormStructureLinkId(long formStructureLinkId)
		throws NoSuchFormStructureLinkException, SystemException {
		FormStructureLink formStructureLink = fetchByFormStructureLinkId(formStructureLinkId);

		if (formStructureLink == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("formStructureLinkId=");
			msg.append(formStructureLinkId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFormStructureLinkException(msg.toString());
		}

		return formStructureLink;
	}

	/**
	 * Finds the form structure link where formStructureLinkId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param formStructureLinkId the form structure link ID to search with
	 * @return the matching form structure link, or <code>null</code> if a matching form structure link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructureLink fetchByFormStructureLinkId(
		long formStructureLinkId) throws SystemException {
		return fetchByFormStructureLinkId(formStructureLinkId, true);
	}

	/**
	 * Finds the form structure link where formStructureLinkId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param formStructureLinkId the form structure link ID to search with
	 * @return the matching form structure link, or <code>null</code> if a matching form structure link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructureLink fetchByFormStructureLinkId(
		long formStructureLinkId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { formStructureLinkId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_FORMSTRUCTURELINKID,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_FORMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_FORMSTRUCTURELINKID_FORMSTRUCTURELINKID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(formStructureLinkId);

				List<FormStructureLink> list = q.list();

				result = list;

				FormStructureLink formStructureLink = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FORMSTRUCTURELINKID,
						finderArgs, list);
				}
				else {
					formStructureLink = list.get(0);

					cacheResult(formStructureLink);

					if ((formStructureLink.getFormStructureLinkId() != formStructureLinkId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FORMSTRUCTURELINKID,
							finderArgs, formStructureLink);
					}
				}

				return formStructureLink;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_FORMSTRUCTURELINKID,
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
				return (FormStructureLink)result;
			}
		}
	}

	/**
	 * Finds all the form structure links where formStructureId = &#63;.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @return the matching form structure links
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructureLink> findByFormStructureId(String formStructureId)
		throws SystemException {
		return findByFormStructureId(formStructureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the form structure links where formStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param start the lower bound of the range of form structure links to return
	 * @param end the upper bound of the range of form structure links to return (not inclusive)
	 * @return the range of matching form structure links
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructureLink> findByFormStructureId(
		String formStructureId, int start, int end) throws SystemException {
		return findByFormStructureId(formStructureId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the form structure links where formStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param start the lower bound of the range of form structure links to return
	 * @param end the upper bound of the range of form structure links to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching form structure links
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructureLink> findByFormStructureId(
		String formStructureId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				formStructureId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<FormStructureLink> list = (List<FormStructureLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_FORMSTRUCTUREID,
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

			query.append(_SQL_SELECT_FORMSTRUCTURELINK_WHERE);

			if (formStructureId == null) {
				query.append(_FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_1);
			}
			else {
				if (formStructureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_2);
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

				if (formStructureId != null) {
					qPos.add(formStructureId);
				}

				list = (List<FormStructureLink>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_FORMSTRUCTUREID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_FORMSTRUCTUREID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first form structure link in the ordered set where formStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching form structure link
	 * @throws com.liferay.portlet.forms.NoSuchFormStructureLinkException if a matching form structure link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructureLink findByFormStructureId_First(
		String formStructureId, OrderByComparator orderByComparator)
		throws NoSuchFormStructureLinkException, SystemException {
		List<FormStructureLink> list = findByFormStructureId(formStructureId,
				0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("formStructureId=");
			msg.append(formStructureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFormStructureLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last form structure link in the ordered set where formStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching form structure link
	 * @throws com.liferay.portlet.forms.NoSuchFormStructureLinkException if a matching form structure link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructureLink findByFormStructureId_Last(
		String formStructureId, OrderByComparator orderByComparator)
		throws NoSuchFormStructureLinkException, SystemException {
		int count = countByFormStructureId(formStructureId);

		List<FormStructureLink> list = findByFormStructureId(formStructureId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("formStructureId=");
			msg.append(formStructureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFormStructureLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the form structure links before and after the current form structure link in the ordered set where formStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param formStructureLinkId the primary key of the current form structure link
	 * @param formStructureId the form structure ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next form structure link
	 * @throws com.liferay.portlet.forms.NoSuchFormStructureLinkException if a form structure link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructureLink[] findByFormStructureId_PrevAndNext(
		long formStructureLinkId, String formStructureId,
		OrderByComparator orderByComparator)
		throws NoSuchFormStructureLinkException, SystemException {
		FormStructureLink formStructureLink = findByPrimaryKey(formStructureLinkId);

		Session session = null;

		try {
			session = openSession();

			FormStructureLink[] array = new FormStructureLinkImpl[3];

			array[0] = getByFormStructureId_PrevAndNext(session,
					formStructureLink, formStructureId, orderByComparator, true);

			array[1] = formStructureLink;

			array[2] = getByFormStructureId_PrevAndNext(session,
					formStructureLink, formStructureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected FormStructureLink getByFormStructureId_PrevAndNext(
		Session session, FormStructureLink formStructureLink,
		String formStructureId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_FORMSTRUCTURELINK_WHERE);

		if (formStructureId == null) {
			query.append(_FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_1);
		}
		else {
			if (formStructureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_2);
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

		if (formStructureId != null) {
			qPos.add(formStructureId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(formStructureLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FormStructureLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the form structure links where formStructureId = &#63;.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @return the matching form structure links that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructureLink> filterFindByFormStructureId(
		String formStructureId) throws SystemException {
		return filterFindByFormStructureId(formStructureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the form structure links where formStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param start the lower bound of the range of form structure links to return
	 * @param end the upper bound of the range of form structure links to return (not inclusive)
	 * @return the range of matching form structure links that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructureLink> filterFindByFormStructureId(
		String formStructureId, int start, int end) throws SystemException {
		return filterFindByFormStructureId(formStructureId, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the form structure links where formStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param start the lower bound of the range of form structure links to return
	 * @param end the upper bound of the range of form structure links to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching form structure links that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructureLink> filterFindByFormStructureId(
		String formStructureId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByFormStructureId(formStructureId, start, end,
				orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_FORMSTRUCTURELINK_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_FORMSTRUCTURELINK_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (formStructureId == null) {
			query.append(_FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_1);
		}
		else {
			if (formStructureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_FORMSTRUCTURELINK_NO_INLINE_DISTINCT_WHERE_2);
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
				FormStructureLink.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, FormStructureLinkImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, FormStructureLinkImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (formStructureId != null) {
				qPos.add(formStructureId);
			}

			return (List<FormStructureLink>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Finds the form structure link where formStructureId = &#63; and className = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.forms.NoSuchFormStructureLinkException} if it could not be found.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param className the class name to search with
	 * @param classPK the class p k to search with
	 * @return the matching form structure link
	 * @throws com.liferay.portlet.forms.NoSuchFormStructureLinkException if a matching form structure link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructureLink findByF_C_C(String formStructureId,
		String className, long classPK)
		throws NoSuchFormStructureLinkException, SystemException {
		FormStructureLink formStructureLink = fetchByF_C_C(formStructureId,
				className, classPK);

		if (formStructureLink == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("formStructureId=");
			msg.append(formStructureId);

			msg.append(", className=");
			msg.append(className);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFormStructureLinkException(msg.toString());
		}

		return formStructureLink;
	}

	/**
	 * Finds the form structure link where formStructureId = &#63; and className = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param className the class name to search with
	 * @param classPK the class p k to search with
	 * @return the matching form structure link, or <code>null</code> if a matching form structure link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructureLink fetchByF_C_C(String formStructureId,
		String className, long classPK) throws SystemException {
		return fetchByF_C_C(formStructureId, className, classPK, true);
	}

	/**
	 * Finds the form structure link where formStructureId = &#63; and className = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param className the class name to search with
	 * @param classPK the class p k to search with
	 * @return the matching form structure link, or <code>null</code> if a matching form structure link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormStructureLink fetchByF_C_C(String formStructureId,
		String className, long classPK, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { formStructureId, className, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_F_C_C,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_FORMSTRUCTURELINK_WHERE);

			if (formStructureId == null) {
				query.append(_FINDER_COLUMN_F_C_C_FORMSTRUCTUREID_1);
			}
			else {
				if (formStructureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_F_C_C_FORMSTRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_F_C_C_FORMSTRUCTUREID_2);
				}
			}

			if (className == null) {
				query.append(_FINDER_COLUMN_F_C_C_CLASSNAME_1);
			}
			else {
				if (className.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_F_C_C_CLASSNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_F_C_C_CLASSNAME_2);
				}
			}

			query.append(_FINDER_COLUMN_F_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (formStructureId != null) {
					qPos.add(formStructureId);
				}

				if (className != null) {
					qPos.add(className);
				}

				qPos.add(classPK);

				List<FormStructureLink> list = q.list();

				result = list;

				FormStructureLink formStructureLink = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_C_C,
						finderArgs, list);
				}
				else {
					formStructureLink = list.get(0);

					cacheResult(formStructureLink);

					if ((formStructureLink.getFormStructureId() == null) ||
							!formStructureLink.getFormStructureId()
												  .equals(formStructureId) ||
							(formStructureLink.getClassName() == null) ||
							!formStructureLink.getClassName().equals(className) ||
							(formStructureLink.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_C_C,
							finderArgs, formStructureLink);
					}
				}

				return formStructureLink;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_F_C_C,
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
				return (FormStructureLink)result;
			}
		}
	}

	/**
	 * Finds all the form structure links.
	 *
	 * @return the form structure links
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructureLink> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the form structure links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of form structure links to return
	 * @param end the upper bound of the range of form structure links to return (not inclusive)
	 * @return the range of form structure links
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructureLink> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the form structure links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of form structure links to return
	 * @param end the upper bound of the range of form structure links to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of form structure links
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormStructureLink> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<FormStructureLink> list = (List<FormStructureLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_FORMSTRUCTURELINK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_FORMSTRUCTURELINK;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<FormStructureLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<FormStructureLink>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes the form structure link where formStructureLinkId = &#63; from the database.
	 *
	 * @param formStructureLinkId the form structure link ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByFormStructureLinkId(long formStructureLinkId)
		throws NoSuchFormStructureLinkException, SystemException {
		FormStructureLink formStructureLink = findByFormStructureLinkId(formStructureLinkId);

		formStructureLinkPersistence.remove(formStructureLink);
	}

	/**
	 * Removes all the form structure links where formStructureId = &#63; from the database.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByFormStructureId(String formStructureId)
		throws SystemException {
		for (FormStructureLink formStructureLink : findByFormStructureId(
				formStructureId)) {
			formStructureLinkPersistence.remove(formStructureLink);
		}
	}

	/**
	 * Removes the form structure link where formStructureId = &#63; and className = &#63; and classPK = &#63; from the database.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param className the class name to search with
	 * @param classPK the class p k to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByF_C_C(String formStructureId, String className,
		long classPK) throws NoSuchFormStructureLinkException, SystemException {
		FormStructureLink formStructureLink = findByF_C_C(formStructureId,
				className, classPK);

		formStructureLinkPersistence.remove(formStructureLink);
	}

	/**
	 * Removes all the form structure links from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (FormStructureLink formStructureLink : findAll()) {
			formStructureLinkPersistence.remove(formStructureLink);
		}
	}

	/**
	 * Counts all the form structure links where formStructureLinkId = &#63;.
	 *
	 * @param formStructureLinkId the form structure link ID to search with
	 * @return the number of matching form structure links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByFormStructureLinkId(long formStructureLinkId)
		throws SystemException {
		Object[] finderArgs = new Object[] { formStructureLinkId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FORMSTRUCTURELINKID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_FORMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_FORMSTRUCTURELINKID_FORMSTRUCTURELINKID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(formStructureLinkId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_FORMSTRUCTURELINKID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the form structure links where formStructureId = &#63;.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @return the number of matching form structure links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByFormStructureId(String formStructureId)
		throws SystemException {
		Object[] finderArgs = new Object[] { formStructureId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FORMSTRUCTUREID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_FORMSTRUCTURELINK_WHERE);

			if (formStructureId == null) {
				query.append(_FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_1);
			}
			else {
				if (formStructureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_FORMSTRUCTUREID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Filters by the user's permissions and counts all the form structure links where formStructureId = &#63;.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @return the number of matching form structure links that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByFormStructureId(String formStructureId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByFormStructureId(formStructureId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_FORMSTRUCTURELINK_WHERE);

		if (formStructureId == null) {
			query.append(_FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_1);
		}
		else {
			if (formStructureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_2);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				FormStructureLink.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (formStructureId != null) {
				qPos.add(formStructureId);
			}

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
	 * Counts all the form structure links where formStructureId = &#63; and className = &#63; and classPK = &#63;.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param className the class name to search with
	 * @param classPK the class p k to search with
	 * @return the number of matching form structure links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByF_C_C(String formStructureId, String className,
		long classPK) throws SystemException {
		Object[] finderArgs = new Object[] { formStructureId, className, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_F_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_FORMSTRUCTURELINK_WHERE);

			if (formStructureId == null) {
				query.append(_FINDER_COLUMN_F_C_C_FORMSTRUCTUREID_1);
			}
			else {
				if (formStructureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_F_C_C_FORMSTRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_F_C_C_FORMSTRUCTUREID_2);
				}
			}

			if (className == null) {
				query.append(_FINDER_COLUMN_F_C_C_CLASSNAME_1);
			}
			else {
				if (className.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_F_C_C_CLASSNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_F_C_C_CLASSNAME_2);
				}
			}

			query.append(_FINDER_COLUMN_F_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (formStructureId != null) {
					qPos.add(formStructureId);
				}

				if (className != null) {
					qPos.add(className);
				}

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_F_C_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the form structure links.
	 *
	 * @return the number of form structure links
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

				Query q = session.createQuery(_SQL_COUNT_FORMSTRUCTURELINK);

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
	 * Initializes the form structure link persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.forms.model.FormStructureLink")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<FormStructureLink>> listenersList = new ArrayList<ModelListener<FormStructureLink>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<FormStructureLink>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(FormStructureLinkImpl.class.getName());
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
	private static final String _SQL_SELECT_FORMSTRUCTURELINK = "SELECT formStructureLink FROM FormStructureLink formStructureLink";
	private static final String _SQL_SELECT_FORMSTRUCTURELINK_WHERE = "SELECT formStructureLink FROM FormStructureLink formStructureLink WHERE ";
	private static final String _SQL_COUNT_FORMSTRUCTURELINK = "SELECT COUNT(formStructureLink) FROM FormStructureLink formStructureLink";
	private static final String _SQL_COUNT_FORMSTRUCTURELINK_WHERE = "SELECT COUNT(formStructureLink) FROM FormStructureLink formStructureLink WHERE ";
	private static final String _FINDER_COLUMN_FORMSTRUCTURELINKID_FORMSTRUCTURELINKID_2 =
		"formStructureLink.formStructureLinkId = ?";
	private static final String _FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_1 =
		"formStructureLink.formStructureId IS NULL";
	private static final String _FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_2 =
		"formStructureLink.formStructureId = ?";
	private static final String _FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_3 =
		"(formStructureLink.formStructureId IS NULL OR formStructureLink.formStructureId = ?)";
	private static final String _FINDER_COLUMN_F_C_C_FORMSTRUCTUREID_1 = "formStructureLink.formStructureId IS NULL AND ";
	private static final String _FINDER_COLUMN_F_C_C_FORMSTRUCTUREID_2 = "formStructureLink.formStructureId = ? AND ";
	private static final String _FINDER_COLUMN_F_C_C_FORMSTRUCTUREID_3 = "(formStructureLink.formStructureId IS NULL OR formStructureLink.formStructureId = ?) AND ";
	private static final String _FINDER_COLUMN_F_C_C_CLASSNAME_1 = "formStructureLink.className IS NULL AND ";
	private static final String _FINDER_COLUMN_F_C_C_CLASSNAME_2 = "formStructureLink.className = ? AND ";
	private static final String _FINDER_COLUMN_F_C_C_CLASSNAME_3 = "(formStructureLink.className IS NULL OR formStructureLink.className = ?) AND ";
	private static final String _FINDER_COLUMN_F_C_C_CLASSPK_2 = "formStructureLink.classPK = ?";
	private static final String _FILTER_SQL_SELECT_FORMSTRUCTURELINK_WHERE = "SELECT DISTINCT {formStructureLink.*} FROM FormStructureLink formStructureLink WHERE ";
	private static final String _FILTER_SQL_SELECT_FORMSTRUCTURELINK_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {FormStructureLink.*} FROM (SELECT DISTINCT formStructureLink.formStructureLinkId FROM FormStructureLink formStructureLink WHERE ";
	private static final String _FILTER_SQL_SELECT_FORMSTRUCTURELINK_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN FormStructureLink ON TEMP_TABLE.formStructureLinkId = FormStructureLink.formStructureLinkId";
	private static final String _FILTER_SQL_COUNT_FORMSTRUCTURELINK_WHERE = "SELECT COUNT(DISTINCT formStructureLink.formStructureLinkId) AS COUNT_VALUE FROM FormStructureLink formStructureLink WHERE ";
	private static final String _FILTER_COLUMN_PK = "formStructureLink.formStructureLinkId";
	private static final String _FILTER_COLUMN_USERID = null;
	private static final String _FILTER_ENTITY_ALIAS = "formStructureLink";
	private static final String _FILTER_ENTITY_TABLE = "FormStructureLink";
	private static final String _ORDER_BY_ENTITY_ALIAS = "formStructureLink.";
	private static final String _ORDER_BY_ENTITY_TABLE = "FormStructureLink.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No FormStructureLink exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No FormStructureLink exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(FormStructureLinkPersistenceImpl.class);
}