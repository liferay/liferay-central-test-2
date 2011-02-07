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
import com.liferay.portlet.forms.NoSuchStructureEntryLinkException;
import com.liferay.portlet.forms.model.FormsStructureEntryLink;
import com.liferay.portlet.forms.model.impl.FormsStructureEntryLinkImpl;
import com.liferay.portlet.forms.model.impl.FormsStructureEntryLinkModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the forms structure entry link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FormsStructureEntryLinkPersistence
 * @see FormsStructureEntryLinkUtil
 * @generated
 */
public class FormsStructureEntryLinkPersistenceImpl extends BasePersistenceImpl<FormsStructureEntryLink>
	implements FormsStructureEntryLinkPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link FormsStructureEntryLinkUtil} to access the forms structure entry link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = FormsStructureEntryLinkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_FORMSTRUCTURELINKID = new FinderPath(FormsStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByFormStructureLinkId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_FORMSTRUCTURELINKID = new FinderPath(FormsStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByFormStructureLinkId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_FORMSTRUCTUREID = new FinderPath(FormsStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByFormStructureId",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_FORMSTRUCTUREID = new FinderPath(FormsStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByFormStructureId",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_F_C_C = new FinderPath(FormsStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByF_C_C",
			new String[] {
				String.class.getName(), String.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_F_C_C = new FinderPath(FormsStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByF_C_C",
			new String[] {
				String.class.getName(), String.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(FormsStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FormsStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the forms structure entry link in the entity cache if it is enabled.
	 *
	 * @param formsStructureEntryLink the forms structure entry link to cache
	 */
	public void cacheResult(FormsStructureEntryLink formsStructureEntryLink) {
		EntityCacheUtil.putResult(FormsStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryLinkImpl.class,
			formsStructureEntryLink.getPrimaryKey(), formsStructureEntryLink);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FORMSTRUCTURELINKID,
			new Object[] {
				new Long(formsStructureEntryLink.getFormStructureLinkId())
			}, formsStructureEntryLink);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_C_C,
			new Object[] {
				formsStructureEntryLink.getFormStructureId(),
				
			formsStructureEntryLink.getClassName(),
				new Long(formsStructureEntryLink.getClassPK())
			}, formsStructureEntryLink);
	}

	/**
	 * Caches the forms structure entry links in the entity cache if it is enabled.
	 *
	 * @param formsStructureEntryLinks the forms structure entry links to cache
	 */
	public void cacheResult(
		List<FormsStructureEntryLink> formsStructureEntryLinks) {
		for (FormsStructureEntryLink formsStructureEntryLink : formsStructureEntryLinks) {
			if (EntityCacheUtil.getResult(
						FormsStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
						FormsStructureEntryLinkImpl.class,
						formsStructureEntryLink.getPrimaryKey(), this) == null) {
				cacheResult(formsStructureEntryLink);
			}
		}
	}

	/**
	 * Clears the cache for all forms structure entry links.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(FormsStructureEntryLinkImpl.class.getName());
		EntityCacheUtil.clearCache(FormsStructureEntryLinkImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the forms structure entry link.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(FormsStructureEntryLink formsStructureEntryLink) {
		EntityCacheUtil.removeResult(FormsStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryLinkImpl.class,
			formsStructureEntryLink.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_FORMSTRUCTURELINKID,
			new Object[] {
				new Long(formsStructureEntryLink.getFormStructureLinkId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_F_C_C,
			new Object[] {
				formsStructureEntryLink.getFormStructureId(),
				
			formsStructureEntryLink.getClassName(),
				new Long(formsStructureEntryLink.getClassPK())
			});
	}

	/**
	 * Creates a new forms structure entry link with the primary key. Does not add the forms structure entry link to the database.
	 *
	 * @param formStructureLinkId the primary key for the new forms structure entry link
	 * @return the new forms structure entry link
	 */
	public FormsStructureEntryLink create(long formStructureLinkId) {
		FormsStructureEntryLink formsStructureEntryLink = new FormsStructureEntryLinkImpl();

		formsStructureEntryLink.setNew(true);
		formsStructureEntryLink.setPrimaryKey(formStructureLinkId);

		return formsStructureEntryLink;
	}

	/**
	 * Removes the forms structure entry link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the forms structure entry link to remove
	 * @return the forms structure entry link that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a forms structure entry link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntryLink remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the forms structure entry link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formStructureLinkId the primary key of the forms structure entry link to remove
	 * @return the forms structure entry link that was removed
	 * @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a forms structure entry link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntryLink remove(long formStructureLinkId)
		throws NoSuchStructureEntryLinkException, SystemException {
		Session session = null;

		try {
			session = openSession();

			FormsStructureEntryLink formsStructureEntryLink = (FormsStructureEntryLink)session.get(FormsStructureEntryLinkImpl.class,
					new Long(formStructureLinkId));

			if (formsStructureEntryLink == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						formStructureLinkId);
				}

				throw new NoSuchStructureEntryLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					formStructureLinkId);
			}

			return formsStructureEntryLinkPersistence.remove(formsStructureEntryLink);
		}
		catch (NoSuchStructureEntryLinkException nsee) {
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
	 * Removes the forms structure entry link from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formsStructureEntryLink the forms structure entry link to remove
	 * @return the forms structure entry link that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntryLink remove(
		FormsStructureEntryLink formsStructureEntryLink)
		throws SystemException {
		return super.remove(formsStructureEntryLink);
	}

	protected FormsStructureEntryLink removeImpl(
		FormsStructureEntryLink formsStructureEntryLink)
		throws SystemException {
		formsStructureEntryLink = toUnwrappedModel(formsStructureEntryLink);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, formsStructureEntryLink);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		FormsStructureEntryLinkModelImpl formsStructureEntryLinkModelImpl = (FormsStructureEntryLinkModelImpl)formsStructureEntryLink;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_FORMSTRUCTURELINKID,
			new Object[] {
				new Long(formsStructureEntryLinkModelImpl.getOriginalFormStructureLinkId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_F_C_C,
			new Object[] {
				formsStructureEntryLinkModelImpl.getOriginalFormStructureId(),
				
			formsStructureEntryLinkModelImpl.getOriginalClassName(),
				new Long(formsStructureEntryLinkModelImpl.getOriginalClassPK())
			});

		EntityCacheUtil.removeResult(FormsStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryLinkImpl.class,
			formsStructureEntryLink.getPrimaryKey());

		return formsStructureEntryLink;
	}

	public FormsStructureEntryLink updateImpl(
		com.liferay.portlet.forms.model.FormsStructureEntryLink formsStructureEntryLink,
		boolean merge) throws SystemException {
		formsStructureEntryLink = toUnwrappedModel(formsStructureEntryLink);

		boolean isNew = formsStructureEntryLink.isNew();

		FormsStructureEntryLinkModelImpl formsStructureEntryLinkModelImpl = (FormsStructureEntryLinkModelImpl)formsStructureEntryLink;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, formsStructureEntryLink, merge);

			formsStructureEntryLink.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(FormsStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
			FormsStructureEntryLinkImpl.class,
			formsStructureEntryLink.getPrimaryKey(), formsStructureEntryLink);

		if (!isNew &&
				(formsStructureEntryLink.getFormStructureLinkId() != formsStructureEntryLinkModelImpl.getOriginalFormStructureLinkId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_FORMSTRUCTURELINKID,
				new Object[] {
					new Long(formsStructureEntryLinkModelImpl.getOriginalFormStructureLinkId())
				});
		}

		if (isNew ||
				(formsStructureEntryLink.getFormStructureLinkId() != formsStructureEntryLinkModelImpl.getOriginalFormStructureLinkId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FORMSTRUCTURELINKID,
				new Object[] {
					new Long(formsStructureEntryLink.getFormStructureLinkId())
				}, formsStructureEntryLink);
		}

		if (!isNew &&
				(!Validator.equals(
					formsStructureEntryLink.getFormStructureId(),
					formsStructureEntryLinkModelImpl.getOriginalFormStructureId()) ||
				!Validator.equals(formsStructureEntryLink.getClassName(),
					formsStructureEntryLinkModelImpl.getOriginalClassName()) ||
				(formsStructureEntryLink.getClassPK() != formsStructureEntryLinkModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_F_C_C,
				new Object[] {
					formsStructureEntryLinkModelImpl.getOriginalFormStructureId(),
					
				formsStructureEntryLinkModelImpl.getOriginalClassName(),
					new Long(formsStructureEntryLinkModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				(!Validator.equals(
					formsStructureEntryLink.getFormStructureId(),
					formsStructureEntryLinkModelImpl.getOriginalFormStructureId()) ||
				!Validator.equals(formsStructureEntryLink.getClassName(),
					formsStructureEntryLinkModelImpl.getOriginalClassName()) ||
				(formsStructureEntryLink.getClassPK() != formsStructureEntryLinkModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_C_C,
				new Object[] {
					formsStructureEntryLink.getFormStructureId(),
					
				formsStructureEntryLink.getClassName(),
					new Long(formsStructureEntryLink.getClassPK())
				}, formsStructureEntryLink);
		}

		return formsStructureEntryLink;
	}

	protected FormsStructureEntryLink toUnwrappedModel(
		FormsStructureEntryLink formsStructureEntryLink) {
		if (formsStructureEntryLink instanceof FormsStructureEntryLinkImpl) {
			return formsStructureEntryLink;
		}

		FormsStructureEntryLinkImpl formsStructureEntryLinkImpl = new FormsStructureEntryLinkImpl();

		formsStructureEntryLinkImpl.setNew(formsStructureEntryLink.isNew());
		formsStructureEntryLinkImpl.setPrimaryKey(formsStructureEntryLink.getPrimaryKey());

		formsStructureEntryLinkImpl.setFormStructureLinkId(formsStructureEntryLink.getFormStructureLinkId());
		formsStructureEntryLinkImpl.setFormStructureId(formsStructureEntryLink.getFormStructureId());
		formsStructureEntryLinkImpl.setClassName(formsStructureEntryLink.getClassName());
		formsStructureEntryLinkImpl.setClassPK(formsStructureEntryLink.getClassPK());

		return formsStructureEntryLinkImpl;
	}

	/**
	 * Finds the forms structure entry link with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the forms structure entry link to find
	 * @return the forms structure entry link
	 * @throws com.liferay.portal.NoSuchModelException if a forms structure entry link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntryLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the forms structure entry link with the primary key or throws a {@link com.liferay.portlet.forms.NoSuchStructureEntryLinkException} if it could not be found.
	 *
	 * @param formStructureLinkId the primary key of the forms structure entry link to find
	 * @return the forms structure entry link
	 * @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a forms structure entry link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntryLink findByPrimaryKey(long formStructureLinkId)
		throws NoSuchStructureEntryLinkException, SystemException {
		FormsStructureEntryLink formsStructureEntryLink = fetchByPrimaryKey(formStructureLinkId);

		if (formsStructureEntryLink == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					formStructureLinkId);
			}

			throw new NoSuchStructureEntryLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				formStructureLinkId);
		}

		return formsStructureEntryLink;
	}

	/**
	 * Finds the forms structure entry link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the forms structure entry link to find
	 * @return the forms structure entry link, or <code>null</code> if a forms structure entry link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntryLink fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the forms structure entry link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param formStructureLinkId the primary key of the forms structure entry link to find
	 * @return the forms structure entry link, or <code>null</code> if a forms structure entry link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntryLink fetchByPrimaryKey(long formStructureLinkId)
		throws SystemException {
		FormsStructureEntryLink formsStructureEntryLink = (FormsStructureEntryLink)EntityCacheUtil.getResult(FormsStructureEntryLinkModelImpl.ENTITY_CACHE_ENABLED,
				FormsStructureEntryLinkImpl.class, formStructureLinkId, this);

		if (formsStructureEntryLink == null) {
			Session session = null;

			try {
				session = openSession();

				formsStructureEntryLink = (FormsStructureEntryLink)session.get(FormsStructureEntryLinkImpl.class,
						new Long(formStructureLinkId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (formsStructureEntryLink != null) {
					cacheResult(formsStructureEntryLink);
				}

				closeSession(session);
			}
		}

		return formsStructureEntryLink;
	}

	/**
	 * Finds the forms structure entry link where formStructureLinkId = &#63; or throws a {@link com.liferay.portlet.forms.NoSuchStructureEntryLinkException} if it could not be found.
	 *
	 * @param formStructureLinkId the form structure link ID to search with
	 * @return the matching forms structure entry link
	 * @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a matching forms structure entry link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntryLink findByFormStructureLinkId(
		long formStructureLinkId)
		throws NoSuchStructureEntryLinkException, SystemException {
		FormsStructureEntryLink formsStructureEntryLink = fetchByFormStructureLinkId(formStructureLinkId);

		if (formsStructureEntryLink == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("formStructureLinkId=");
			msg.append(formStructureLinkId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStructureEntryLinkException(msg.toString());
		}

		return formsStructureEntryLink;
	}

	/**
	 * Finds the forms structure entry link where formStructureLinkId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param formStructureLinkId the form structure link ID to search with
	 * @return the matching forms structure entry link, or <code>null</code> if a matching forms structure entry link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntryLink fetchByFormStructureLinkId(
		long formStructureLinkId) throws SystemException {
		return fetchByFormStructureLinkId(formStructureLinkId, true);
	}

	/**
	 * Finds the forms structure entry link where formStructureLinkId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param formStructureLinkId the form structure link ID to search with
	 * @return the matching forms structure entry link, or <code>null</code> if a matching forms structure entry link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntryLink fetchByFormStructureLinkId(
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

			query.append(_SQL_SELECT_FORMSSTRUCTUREENTRYLINK_WHERE);

			query.append(_FINDER_COLUMN_FORMSTRUCTURELINKID_FORMSTRUCTURELINKID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(formStructureLinkId);

				List<FormsStructureEntryLink> list = q.list();

				result = list;

				FormsStructureEntryLink formsStructureEntryLink = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FORMSTRUCTURELINKID,
						finderArgs, list);
				}
				else {
					formsStructureEntryLink = list.get(0);

					cacheResult(formsStructureEntryLink);

					if ((formsStructureEntryLink.getFormStructureLinkId() != formStructureLinkId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FORMSTRUCTURELINKID,
							finderArgs, formsStructureEntryLink);
					}
				}

				return formsStructureEntryLink;
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
				return (FormsStructureEntryLink)result;
			}
		}
	}

	/**
	 * Finds all the forms structure entry links where formStructureId = &#63;.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @return the matching forms structure entry links
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormsStructureEntryLink> findByFormStructureId(
		String formStructureId) throws SystemException {
		return findByFormStructureId(formStructureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the forms structure entry links where formStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param start the lower bound of the range of forms structure entry links to return
	 * @param end the upper bound of the range of forms structure entry links to return (not inclusive)
	 * @return the range of matching forms structure entry links
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormsStructureEntryLink> findByFormStructureId(
		String formStructureId, int start, int end) throws SystemException {
		return findByFormStructureId(formStructureId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the forms structure entry links where formStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param start the lower bound of the range of forms structure entry links to return
	 * @param end the upper bound of the range of forms structure entry links to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching forms structure entry links
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormsStructureEntryLink> findByFormStructureId(
		String formStructureId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				formStructureId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<FormsStructureEntryLink> list = (List<FormsStructureEntryLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_FORMSTRUCTUREID,
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

			query.append(_SQL_SELECT_FORMSSTRUCTUREENTRYLINK_WHERE);

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

				list = (List<FormsStructureEntryLink>)QueryUtil.list(q,
						getDialect(), start, end);
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
	 * Finds the first forms structure entry link in the ordered set where formStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching forms structure entry link
	 * @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a matching forms structure entry link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntryLink findByFormStructureId_First(
		String formStructureId, OrderByComparator orderByComparator)
		throws NoSuchStructureEntryLinkException, SystemException {
		List<FormsStructureEntryLink> list = findByFormStructureId(formStructureId,
				0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("formStructureId=");
			msg.append(formStructureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureEntryLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last forms structure entry link in the ordered set where formStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching forms structure entry link
	 * @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a matching forms structure entry link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntryLink findByFormStructureId_Last(
		String formStructureId, OrderByComparator orderByComparator)
		throws NoSuchStructureEntryLinkException, SystemException {
		int count = countByFormStructureId(formStructureId);

		List<FormsStructureEntryLink> list = findByFormStructureId(formStructureId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("formStructureId=");
			msg.append(formStructureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureEntryLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the forms structure entry links before and after the current forms structure entry link in the ordered set where formStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param formStructureLinkId the primary key of the current forms structure entry link
	 * @param formStructureId the form structure ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next forms structure entry link
	 * @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a forms structure entry link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntryLink[] findByFormStructureId_PrevAndNext(
		long formStructureLinkId, String formStructureId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureEntryLinkException, SystemException {
		FormsStructureEntryLink formsStructureEntryLink = findByPrimaryKey(formStructureLinkId);

		Session session = null;

		try {
			session = openSession();

			FormsStructureEntryLink[] array = new FormsStructureEntryLinkImpl[3];

			array[0] = getByFormStructureId_PrevAndNext(session,
					formsStructureEntryLink, formStructureId,
					orderByComparator, true);

			array[1] = formsStructureEntryLink;

			array[2] = getByFormStructureId_PrevAndNext(session,
					formsStructureEntryLink, formStructureId,
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

	protected FormsStructureEntryLink getByFormStructureId_PrevAndNext(
		Session session, FormsStructureEntryLink formsStructureEntryLink,
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

		query.append(_SQL_SELECT_FORMSSTRUCTUREENTRYLINK_WHERE);

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
			Object[] values = orderByComparator.getOrderByValues(formsStructureEntryLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FormsStructureEntryLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the forms structure entry links where formStructureId = &#63;.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @return the matching forms structure entry links that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormsStructureEntryLink> filterFindByFormStructureId(
		String formStructureId) throws SystemException {
		return filterFindByFormStructureId(formStructureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the forms structure entry links where formStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param start the lower bound of the range of forms structure entry links to return
	 * @param end the upper bound of the range of forms structure entry links to return (not inclusive)
	 * @return the range of matching forms structure entry links that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormsStructureEntryLink> filterFindByFormStructureId(
		String formStructureId, int start, int end) throws SystemException {
		return filterFindByFormStructureId(formStructureId, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the forms structure entry links where formStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param start the lower bound of the range of forms structure entry links to return
	 * @param end the upper bound of the range of forms structure entry links to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching forms structure entry links that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormsStructureEntryLink> filterFindByFormStructureId(
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
			query.append(_FILTER_SQL_SELECT_FORMSSTRUCTUREENTRYLINK_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_FORMSSTRUCTUREENTRYLINK_NO_INLINE_DISTINCT_WHERE_1);
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
			query.append(_FILTER_SQL_SELECT_FORMSSTRUCTUREENTRYLINK_NO_INLINE_DISTINCT_WHERE_2);
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
				FormsStructureEntryLink.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					FormsStructureEntryLinkImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					FormsStructureEntryLinkImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (formStructureId != null) {
				qPos.add(formStructureId);
			}

			return (List<FormsStructureEntryLink>)QueryUtil.list(q,
				getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Filters the forms structure entry links before and after the current forms structure entry link in the ordered set where formStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param formStructureLinkId the primary key of the current forms structure entry link
	 * @param formStructureId the form structure ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next forms structure entry link
	 * @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a forms structure entry link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntryLink[] filterFindByFormStructureId_PrevAndNext(
		long formStructureLinkId, String formStructureId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureEntryLinkException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByFormStructureId_PrevAndNext(formStructureLinkId,
				formStructureId, orderByComparator);
		}

		FormsStructureEntryLink formsStructureEntryLink = findByPrimaryKey(formStructureLinkId);

		Session session = null;

		try {
			session = openSession();

			FormsStructureEntryLink[] array = new FormsStructureEntryLinkImpl[3];

			array[0] = filterGetByFormStructureId_PrevAndNext(session,
					formsStructureEntryLink, formStructureId,
					orderByComparator, true);

			array[1] = formsStructureEntryLink;

			array[2] = filterGetByFormStructureId_PrevAndNext(session,
					formsStructureEntryLink, formStructureId,
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

	protected FormsStructureEntryLink filterGetByFormStructureId_PrevAndNext(
		Session session, FormsStructureEntryLink formsStructureEntryLink,
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

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_FORMSSTRUCTUREENTRYLINK_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_FORMSSTRUCTUREENTRYLINK_NO_INLINE_DISTINCT_WHERE_1);
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
			query.append(_FILTER_SQL_SELECT_FORMSSTRUCTUREENTRYLINK_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				FormsStructureEntryLink.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, FormsStructureEntryLinkImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, FormsStructureEntryLinkImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (formStructureId != null) {
			qPos.add(formStructureId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(formsStructureEntryLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FormsStructureEntryLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the forms structure entry link where formStructureId = &#63; and className = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.forms.NoSuchStructureEntryLinkException} if it could not be found.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param className the class name to search with
	 * @param classPK the class p k to search with
	 * @return the matching forms structure entry link
	 * @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a matching forms structure entry link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntryLink findByF_C_C(String formStructureId,
		String className, long classPK)
		throws NoSuchStructureEntryLinkException, SystemException {
		FormsStructureEntryLink formsStructureEntryLink = fetchByF_C_C(formStructureId,
				className, classPK);

		if (formsStructureEntryLink == null) {
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

			throw new NoSuchStructureEntryLinkException(msg.toString());
		}

		return formsStructureEntryLink;
	}

	/**
	 * Finds the forms structure entry link where formStructureId = &#63; and className = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param className the class name to search with
	 * @param classPK the class p k to search with
	 * @return the matching forms structure entry link, or <code>null</code> if a matching forms structure entry link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntryLink fetchByF_C_C(String formStructureId,
		String className, long classPK) throws SystemException {
		return fetchByF_C_C(formStructureId, className, classPK, true);
	}

	/**
	 * Finds the forms structure entry link where formStructureId = &#63; and className = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param className the class name to search with
	 * @param classPK the class p k to search with
	 * @return the matching forms structure entry link, or <code>null</code> if a matching forms structure entry link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public FormsStructureEntryLink fetchByF_C_C(String formStructureId,
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

			query.append(_SQL_SELECT_FORMSSTRUCTUREENTRYLINK_WHERE);

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

				List<FormsStructureEntryLink> list = q.list();

				result = list;

				FormsStructureEntryLink formsStructureEntryLink = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_C_C,
						finderArgs, list);
				}
				else {
					formsStructureEntryLink = list.get(0);

					cacheResult(formsStructureEntryLink);

					if ((formsStructureEntryLink.getFormStructureId() == null) ||
							!formsStructureEntryLink.getFormStructureId()
														.equals(formStructureId) ||
							(formsStructureEntryLink.getClassName() == null) ||
							!formsStructureEntryLink.getClassName()
														.equals(className) ||
							(formsStructureEntryLink.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_C_C,
							finderArgs, formsStructureEntryLink);
					}
				}

				return formsStructureEntryLink;
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
				return (FormsStructureEntryLink)result;
			}
		}
	}

	/**
	 * Finds all the forms structure entry links.
	 *
	 * @return the forms structure entry links
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormsStructureEntryLink> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the forms structure entry links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of forms structure entry links to return
	 * @param end the upper bound of the range of forms structure entry links to return (not inclusive)
	 * @return the range of forms structure entry links
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormsStructureEntryLink> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the forms structure entry links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of forms structure entry links to return
	 * @param end the upper bound of the range of forms structure entry links to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of forms structure entry links
	 * @throws SystemException if a system exception occurred
	 */
	public List<FormsStructureEntryLink> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<FormsStructureEntryLink> list = (List<FormsStructureEntryLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_FORMSSTRUCTUREENTRYLINK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_FORMSSTRUCTUREENTRYLINK;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<FormsStructureEntryLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<FormsStructureEntryLink>)QueryUtil.list(q,
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
	 * Removes the forms structure entry link where formStructureLinkId = &#63; from the database.
	 *
	 * @param formStructureLinkId the form structure link ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByFormStructureLinkId(long formStructureLinkId)
		throws NoSuchStructureEntryLinkException, SystemException {
		FormsStructureEntryLink formsStructureEntryLink = findByFormStructureLinkId(formStructureLinkId);

		formsStructureEntryLinkPersistence.remove(formsStructureEntryLink);
	}

	/**
	 * Removes all the forms structure entry links where formStructureId = &#63; from the database.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByFormStructureId(String formStructureId)
		throws SystemException {
		for (FormsStructureEntryLink formsStructureEntryLink : findByFormStructureId(
				formStructureId)) {
			formsStructureEntryLinkPersistence.remove(formsStructureEntryLink);
		}
	}

	/**
	 * Removes the forms structure entry link where formStructureId = &#63; and className = &#63; and classPK = &#63; from the database.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param className the class name to search with
	 * @param classPK the class p k to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByF_C_C(String formStructureId, String className,
		long classPK) throws NoSuchStructureEntryLinkException, SystemException {
		FormsStructureEntryLink formsStructureEntryLink = findByF_C_C(formStructureId,
				className, classPK);

		formsStructureEntryLinkPersistence.remove(formsStructureEntryLink);
	}

	/**
	 * Removes all the forms structure entry links from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (FormsStructureEntryLink formsStructureEntryLink : findAll()) {
			formsStructureEntryLinkPersistence.remove(formsStructureEntryLink);
		}
	}

	/**
	 * Counts all the forms structure entry links where formStructureLinkId = &#63;.
	 *
	 * @param formStructureLinkId the form structure link ID to search with
	 * @return the number of matching forms structure entry links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByFormStructureLinkId(long formStructureLinkId)
		throws SystemException {
		Object[] finderArgs = new Object[] { formStructureLinkId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FORMSTRUCTURELINKID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_FORMSSTRUCTUREENTRYLINK_WHERE);

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
	 * Counts all the forms structure entry links where formStructureId = &#63;.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @return the number of matching forms structure entry links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByFormStructureId(String formStructureId)
		throws SystemException {
		Object[] finderArgs = new Object[] { formStructureId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FORMSTRUCTUREID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_FORMSSTRUCTUREENTRYLINK_WHERE);

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
	 * Filters by the user's permissions and counts all the forms structure entry links where formStructureId = &#63;.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @return the number of matching forms structure entry links that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByFormStructureId(String formStructureId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByFormStructureId(formStructureId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_FORMSSTRUCTUREENTRYLINK_WHERE);

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
				FormsStructureEntryLink.class.getName(), _FILTER_COLUMN_PK,
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
	 * Counts all the forms structure entry links where formStructureId = &#63; and className = &#63; and classPK = &#63;.
	 *
	 * @param formStructureId the form structure ID to search with
	 * @param className the class name to search with
	 * @param classPK the class p k to search with
	 * @return the number of matching forms structure entry links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByF_C_C(String formStructureId, String className,
		long classPK) throws SystemException {
		Object[] finderArgs = new Object[] { formStructureId, className, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_F_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_FORMSSTRUCTUREENTRYLINK_WHERE);

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
	 * Counts all the forms structure entry links.
	 *
	 * @return the number of forms structure entry links
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

				Query q = session.createQuery(_SQL_COUNT_FORMSSTRUCTUREENTRYLINK);

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
	 * Initializes the forms structure entry link persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.forms.model.FormsStructureEntryLink")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<FormsStructureEntryLink>> listenersList = new ArrayList<ModelListener<FormsStructureEntryLink>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<FormsStructureEntryLink>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(FormsStructureEntryLinkImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = FormsStructureEntryPersistence.class)
	protected FormsStructureEntryPersistence formsStructureEntryPersistence;
	@BeanReference(type = FormsStructureEntryLinkPersistence.class)
	protected FormsStructureEntryLinkPersistence formsStructureEntryLinkPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	private static final String _SQL_SELECT_FORMSSTRUCTUREENTRYLINK = "SELECT formsStructureEntryLink FROM FormsStructureEntryLink formsStructureEntryLink";
	private static final String _SQL_SELECT_FORMSSTRUCTUREENTRYLINK_WHERE = "SELECT formsStructureEntryLink FROM FormsStructureEntryLink formsStructureEntryLink WHERE ";
	private static final String _SQL_COUNT_FORMSSTRUCTUREENTRYLINK = "SELECT COUNT(formsStructureEntryLink) FROM FormsStructureEntryLink formsStructureEntryLink";
	private static final String _SQL_COUNT_FORMSSTRUCTUREENTRYLINK_WHERE = "SELECT COUNT(formsStructureEntryLink) FROM FormsStructureEntryLink formsStructureEntryLink WHERE ";
	private static final String _FINDER_COLUMN_FORMSTRUCTURELINKID_FORMSTRUCTURELINKID_2 =
		"formsStructureEntryLink.formStructureLinkId = ?";
	private static final String _FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_1 =
		"formsStructureEntryLink.formStructureId IS NULL";
	private static final String _FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_2 =
		"formsStructureEntryLink.formStructureId = ?";
	private static final String _FINDER_COLUMN_FORMSTRUCTUREID_FORMSTRUCTUREID_3 =
		"(formsStructureEntryLink.formStructureId IS NULL OR formsStructureEntryLink.formStructureId = ?)";
	private static final String _FINDER_COLUMN_F_C_C_FORMSTRUCTUREID_1 = "formsStructureEntryLink.formStructureId IS NULL AND ";
	private static final String _FINDER_COLUMN_F_C_C_FORMSTRUCTUREID_2 = "formsStructureEntryLink.formStructureId = ? AND ";
	private static final String _FINDER_COLUMN_F_C_C_FORMSTRUCTUREID_3 = "(formsStructureEntryLink.formStructureId IS NULL OR formsStructureEntryLink.formStructureId = ?) AND ";
	private static final String _FINDER_COLUMN_F_C_C_CLASSNAME_1 = "formsStructureEntryLink.className IS NULL AND ";
	private static final String _FINDER_COLUMN_F_C_C_CLASSNAME_2 = "formsStructureEntryLink.className = ? AND ";
	private static final String _FINDER_COLUMN_F_C_C_CLASSNAME_3 = "(formsStructureEntryLink.className IS NULL OR formsStructureEntryLink.className = ?) AND ";
	private static final String _FINDER_COLUMN_F_C_C_CLASSPK_2 = "formsStructureEntryLink.classPK = ?";
	private static final String _FILTER_SQL_SELECT_FORMSSTRUCTUREENTRYLINK_WHERE =
		"SELECT DISTINCT {formsStructureEntryLink.*} FROM FormsStructureEntryLink formsStructureEntryLink WHERE ";
	private static final String _FILTER_SQL_SELECT_FORMSSTRUCTUREENTRYLINK_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {FormsStructureEntryLink.*} FROM (SELECT DISTINCT formsStructureEntryLink.formStructureLinkId FROM FormsStructureEntryLink formsStructureEntryLink WHERE ";
	private static final String _FILTER_SQL_SELECT_FORMSSTRUCTUREENTRYLINK_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN FormsStructureEntryLink ON TEMP_TABLE.formStructureLinkId = FormsStructureEntryLink.formStructureLinkId";
	private static final String _FILTER_SQL_COUNT_FORMSSTRUCTUREENTRYLINK_WHERE = "SELECT COUNT(DISTINCT formsStructureEntryLink.formStructureLinkId) AS COUNT_VALUE FROM FormsStructureEntryLink formsStructureEntryLink WHERE ";
	private static final String _FILTER_COLUMN_PK = "formsStructureEntryLink.formStructureLinkId";
	private static final String _FILTER_COLUMN_USERID = null;
	private static final String _FILTER_ENTITY_ALIAS = "formsStructureEntryLink";
	private static final String _FILTER_ENTITY_TABLE = "FormsStructureEntryLink";
	private static final String _ORDER_BY_ENTITY_ALIAS = "formsStructureEntryLink.";
	private static final String _ORDER_BY_ENTITY_TABLE = "FormsStructureEntryLink.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No FormsStructureEntryLink exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No FormsStructureEntryLink exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(FormsStructureEntryLinkPersistenceImpl.class);
}