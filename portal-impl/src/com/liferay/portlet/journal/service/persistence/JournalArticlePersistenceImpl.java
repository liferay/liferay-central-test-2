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

package com.liferay.portlet.journal.service.persistence;

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
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.ImagePersistence;
import com.liferay.portal.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.SubscriptionPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagPersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.model.impl.JournalArticleModelImpl;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence;
import com.liferay.portlet.social.service.persistence.SocialEquityLogPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the journal article service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticlePersistence
 * @see JournalArticleUtil
 * @generated
 */
public class JournalArticlePersistenceImpl extends BasePersistenceImpl<JournalArticle>
	implements JournalArticlePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link JournalArticleUtil} to access the journal article persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = JournalArticleImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_RESOURCEPRIMKEY = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByResourcePrimKey",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByResourcePrimKey",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_SMALLIMAGEID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findBySmallImageId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_SMALLIMAGEID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countBySmallImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_R_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByR_ST",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_R_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByR_ST",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_A = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_A",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_S",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_T = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_UT = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_UT",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_UT = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_UT",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_ST",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_ST",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_ST",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_ST",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_A_V = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_A_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A_V = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_A_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_A_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_A_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_A_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_UT_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_UT_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_UT_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_UT_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the journal article in the entity cache if it is enabled.
	 *
	 * @param journalArticle the journal article to cache
	 */
	public void cacheResult(JournalArticle journalArticle) {
		EntityCacheUtil.putResult(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImpl.class, journalArticle.getPrimaryKey(),
			journalArticle);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalArticle.getUuid(), new Long(journalArticle.getGroupId())
			}, journalArticle);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V,
			new Object[] {
				new Long(journalArticle.getGroupId()),
				
			journalArticle.getArticleId(),
				new Double(journalArticle.getVersion())
			}, journalArticle);
	}

	/**
	 * Caches the journal articles in the entity cache if it is enabled.
	 *
	 * @param journalArticles the journal articles to cache
	 */
	public void cacheResult(List<JournalArticle> journalArticles) {
		for (JournalArticle journalArticle : journalArticles) {
			if (EntityCacheUtil.getResult(
						JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
						JournalArticleImpl.class,
						journalArticle.getPrimaryKey(), this) == null) {
				cacheResult(journalArticle);
			}
		}
	}

	/**
	 * Clears the cache for all journal articles.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(JournalArticleImpl.class.getName());
		EntityCacheUtil.clearCache(JournalArticleImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the journal article.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(JournalArticle journalArticle) {
		EntityCacheUtil.removeResult(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImpl.class, journalArticle.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalArticle.getUuid(), new Long(journalArticle.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_A_V,
			new Object[] {
				new Long(journalArticle.getGroupId()),
				
			journalArticle.getArticleId(),
				new Double(journalArticle.getVersion())
			});
	}

	/**
	 * Creates a new journal article with the primary key. Does not add the journal article to the database.
	 *
	 * @param id the primary key for the new journal article
	 * @return the new journal article
	 */
	public JournalArticle create(long id) {
		JournalArticle journalArticle = new JournalArticleImpl();

		journalArticle.setNew(true);
		journalArticle.setPrimaryKey(id);

		String uuid = PortalUUIDUtil.generate();

		journalArticle.setUuid(uuid);

		return journalArticle;
	}

	/**
	 * Removes the journal article with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the journal article to remove
	 * @return the journal article that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the journal article with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the journal article to remove
	 * @return the journal article that was removed
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle remove(long id)
		throws NoSuchArticleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalArticle journalArticle = (JournalArticle)session.get(JournalArticleImpl.class,
					new Long(id));

			if (journalArticle == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
				}

				throw new NoSuchArticleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					id);
			}

			return journalArticlePersistence.remove(journalArticle);
		}
		catch (NoSuchArticleException nsee) {
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
	 * Removes the journal article from the database. Also notifies the appropriate model listeners.
	 *
	 * @param the journal article to remove
	 * @return the journal article that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle remove(JournalArticle journalArticle)
		throws SystemException {
		return super.remove(journalArticle);
	}

	protected JournalArticle removeImpl(JournalArticle journalArticle)
		throws SystemException {
		journalArticle = toUnwrappedModel(journalArticle);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, journalArticle);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		JournalArticleModelImpl journalArticleModelImpl = (JournalArticleModelImpl)journalArticle;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalArticleModelImpl.getOriginalUuid(),
				new Long(journalArticleModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_A_V,
			new Object[] {
				new Long(journalArticleModelImpl.getOriginalGroupId()),
				
			journalArticleModelImpl.getOriginalArticleId(),
				new Double(journalArticleModelImpl.getOriginalVersion())
			});

		EntityCacheUtil.removeResult(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImpl.class, journalArticle.getPrimaryKey());

		return journalArticle;
	}

	public JournalArticle updateImpl(
		com.liferay.portlet.journal.model.JournalArticle journalArticle,
		boolean merge) throws SystemException {
		journalArticle = toUnwrappedModel(journalArticle);

		boolean isNew = journalArticle.isNew();

		JournalArticleModelImpl journalArticleModelImpl = (JournalArticleModelImpl)journalArticle;

		if (Validator.isNull(journalArticle.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalArticle.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, journalArticle, merge);

			journalArticle.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImpl.class, journalArticle.getPrimaryKey(),
			journalArticle);

		if (!isNew &&
				(!Validator.equals(journalArticle.getUuid(),
					journalArticleModelImpl.getOriginalUuid()) ||
				(journalArticle.getGroupId() != journalArticleModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalArticleModelImpl.getOriginalUuid(),
					new Long(journalArticleModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(journalArticle.getUuid(),
					journalArticleModelImpl.getOriginalUuid()) ||
				(journalArticle.getGroupId() != journalArticleModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalArticle.getUuid(),
					new Long(journalArticle.getGroupId())
				}, journalArticle);
		}

		if (!isNew &&
				((journalArticle.getGroupId() != journalArticleModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalArticle.getArticleId(),
					journalArticleModelImpl.getOriginalArticleId()) ||
				(journalArticle.getVersion() != journalArticleModelImpl.getOriginalVersion()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_A_V,
				new Object[] {
					new Long(journalArticleModelImpl.getOriginalGroupId()),
					
				journalArticleModelImpl.getOriginalArticleId(),
					new Double(journalArticleModelImpl.getOriginalVersion())
				});
		}

		if (isNew ||
				((journalArticle.getGroupId() != journalArticleModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalArticle.getArticleId(),
					journalArticleModelImpl.getOriginalArticleId()) ||
				(journalArticle.getVersion() != journalArticleModelImpl.getOriginalVersion()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V,
				new Object[] {
					new Long(journalArticle.getGroupId()),
					
				journalArticle.getArticleId(),
					new Double(journalArticle.getVersion())
				}, journalArticle);
		}

		return journalArticle;
	}

	protected JournalArticle toUnwrappedModel(JournalArticle journalArticle) {
		if (journalArticle instanceof JournalArticleImpl) {
			return journalArticle;
		}

		JournalArticleImpl journalArticleImpl = new JournalArticleImpl();

		journalArticleImpl.setNew(journalArticle.isNew());
		journalArticleImpl.setPrimaryKey(journalArticle.getPrimaryKey());

		journalArticleImpl.setUuid(journalArticle.getUuid());
		journalArticleImpl.setId(journalArticle.getId());
		journalArticleImpl.setResourcePrimKey(journalArticle.getResourcePrimKey());
		journalArticleImpl.setGroupId(journalArticle.getGroupId());
		journalArticleImpl.setCompanyId(journalArticle.getCompanyId());
		journalArticleImpl.setUserId(journalArticle.getUserId());
		journalArticleImpl.setUserName(journalArticle.getUserName());
		journalArticleImpl.setCreateDate(journalArticle.getCreateDate());
		journalArticleImpl.setModifiedDate(journalArticle.getModifiedDate());
		journalArticleImpl.setArticleId(journalArticle.getArticleId());
		journalArticleImpl.setVersion(journalArticle.getVersion());
		journalArticleImpl.setTitle(journalArticle.getTitle());
		journalArticleImpl.setUrlTitle(journalArticle.getUrlTitle());
		journalArticleImpl.setDescription(journalArticle.getDescription());
		journalArticleImpl.setContent(journalArticle.getContent());
		journalArticleImpl.setType(journalArticle.getType());
		journalArticleImpl.setStructureId(journalArticle.getStructureId());
		journalArticleImpl.setTemplateId(journalArticle.getTemplateId());
		journalArticleImpl.setDisplayDate(journalArticle.getDisplayDate());
		journalArticleImpl.setExpirationDate(journalArticle.getExpirationDate());
		journalArticleImpl.setReviewDate(journalArticle.getReviewDate());
		journalArticleImpl.setIndexable(journalArticle.isIndexable());
		journalArticleImpl.setSmallImage(journalArticle.isSmallImage());
		journalArticleImpl.setSmallImageId(journalArticle.getSmallImageId());
		journalArticleImpl.setSmallImageURL(journalArticle.getSmallImageURL());
		journalArticleImpl.setStatus(journalArticle.getStatus());
		journalArticleImpl.setStatusByUserId(journalArticle.getStatusByUserId());
		journalArticleImpl.setStatusByUserName(journalArticle.getStatusByUserName());
		journalArticleImpl.setStatusDate(journalArticle.getStatusDate());

		return journalArticleImpl;
	}

	/**
	 * Finds the journal article with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal article to find
	 * @return the journal article
	 * @throws com.liferay.portal.NoSuchModelException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the journal article with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchArticleException} if it could not be found.
	 *
	 * @param id the primary key of the journal article to find
	 * @return the journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByPrimaryKey(long id)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = fetchByPrimaryKey(id);

		if (journalArticle == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
			}

			throw new NoSuchArticleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				id);
		}

		return journalArticle;
	}

	/**
	 * Finds the journal article with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal article to find
	 * @return the journal article, or <code>null</code> if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the journal article with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the journal article to find
	 * @return the journal article, or <code>null</code> if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle fetchByPrimaryKey(long id) throws SystemException {
		JournalArticle journalArticle = (JournalArticle)EntityCacheUtil.getResult(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
				JournalArticleImpl.class, id, this);

		if (journalArticle == null) {
			Session session = null;

			try {
				session = openSession();

				journalArticle = (JournalArticle)session.get(JournalArticleImpl.class,
						new Long(id));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (journalArticle != null) {
					cacheResult(journalArticle);
				}

				closeSession(session);
			}
		}

		return journalArticle;
	}

	/**
	 * Finds all the journal articles where uuid = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @return the matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the journal articles where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Finds an ordered range of all the journal articles where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

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

			else {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
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

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
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
	 * Finds the first journal article in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last journal article in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		int count = countByUuid(uuid);

		List<JournalArticle> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the journal articles before and after the current journal article in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current journal article
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle[] findByUuid_PrevAndNext(long id, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByUuid_PrevAndNext(session, journalArticle, uuid,
					orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByUuid_PrevAndNext(session, journalArticle, uuid,
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

	protected JournalArticle getByUuid_PrevAndNext(Session session,
		JournalArticle journalArticle, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

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

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the journal article where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchArticleException} if it could not be found.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByUUID_G(String uuid, long groupId)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = fetchByUUID_G(uuid, groupId);

		if (journalArticle == null) {
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

			throw new NoSuchArticleException(msg.toString());
		}

		return journalArticle;
	}

	/**
	 * Finds the journal article where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Finds the journal article where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

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

			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);

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

				List<JournalArticle> list = q.list();

				result = list;

				JournalArticle journalArticle = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					journalArticle = list.get(0);

					cacheResult(journalArticle);

					if ((journalArticle.getUuid() == null) ||
							!journalArticle.getUuid().equals(uuid) ||
							(journalArticle.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, journalArticle);
					}
				}

				return journalArticle;
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
				return (JournalArticle)result;
			}
		}
	}

	/**
	 * Finds all the journal articles where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key to search with
	 * @return the matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByResourcePrimKey(long resourcePrimKey)
		throws SystemException {
		return findByResourcePrimKey(resourcePrimKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the journal articles where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByResourcePrimKey(long resourcePrimKey,
		int start, int end) throws SystemException {
		return findByResourcePrimKey(resourcePrimKey, start, end, null);
	}

	/**
	 * Finds an ordered range of all the journal articles where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByResourcePrimKey(long resourcePrimKey,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				resourcePrimKey,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_RESOURCEPRIMKEY,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_RESOURCEPRIMKEY,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_RESOURCEPRIMKEY,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first journal article in the ordered set where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByResourcePrimKey_First(long resourcePrimKey,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByResourcePrimKey(resourcePrimKey, 0,
				1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("resourcePrimKey=");
			msg.append(resourcePrimKey);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last journal article in the ordered set where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByResourcePrimKey_Last(long resourcePrimKey,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		int count = countByResourcePrimKey(resourcePrimKey);

		List<JournalArticle> list = findByResourcePrimKey(resourcePrimKey,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("resourcePrimKey=");
			msg.append(resourcePrimKey);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the journal articles before and after the current journal article in the ordered set where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current journal article
	 * @param resourcePrimKey the resource prim key to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle[] findByResourcePrimKey_PrevAndNext(long id,
		long resourcePrimKey, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByResourcePrimKey_PrevAndNext(session,
					journalArticle, resourcePrimKey, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByResourcePrimKey_PrevAndNext(session,
					journalArticle, resourcePrimKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByResourcePrimKey_PrevAndNext(Session session,
		JournalArticle journalArticle, long resourcePrimKey,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

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

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(resourcePrimKey);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the journal articles where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the journal articles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the journal articles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
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
	 * Finds the first journal article in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last journal article in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		int count = countByGroupId(groupId);

		List<JournalArticle> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the journal articles before and after the current journal article in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle[] findByGroupId_PrevAndNext(long id, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, journalArticle,
					groupId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByGroupId_PrevAndNext(session, journalArticle,
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

	protected JournalArticle getByGroupId_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

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

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the journal articles where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the journal articles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the journal articles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByGroupId(long groupId, int start,
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
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
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
	 * Finds all the journal articles where companyId = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @return the matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Finds a range of all the journal articles where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the journal articles where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_COMPANYID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first journal article in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last journal article in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		int count = countByCompanyId(companyId);

		List<JournalArticle> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the journal articles before and after the current journal article in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current journal article
	 * @param companyId the company ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle[] findByCompanyId_PrevAndNext(long id,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, journalArticle,
					companyId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByCompanyId_PrevAndNext(session, journalArticle,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByCompanyId_PrevAndNext(Session session,
		JournalArticle journalArticle, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the journal articles where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID to search with
	 * @return the matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findBySmallImageId(long smallImageId)
		throws SystemException {
		return findBySmallImageId(smallImageId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the journal articles where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param smallImageId the small image ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findBySmallImageId(long smallImageId,
		int start, int end) throws SystemException {
		return findBySmallImageId(smallImageId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the journal articles where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param smallImageId the small image ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findBySmallImageId(long smallImageId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				smallImageId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_SMALLIMAGEID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_SMALLIMAGEID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_SMALLIMAGEID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first journal article in the ordered set where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param smallImageId the small image ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findBySmallImageId_First(long smallImageId,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findBySmallImageId(smallImageId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("smallImageId=");
			msg.append(smallImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last journal article in the ordered set where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param smallImageId the small image ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findBySmallImageId_Last(long smallImageId,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		int count = countBySmallImageId(smallImageId);

		List<JournalArticle> list = findBySmallImageId(smallImageId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("smallImageId=");
			msg.append(smallImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the journal articles before and after the current journal article in the ordered set where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current journal article
	 * @param smallImageId the small image ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle[] findBySmallImageId_PrevAndNext(long id,
		long smallImageId, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getBySmallImageId_PrevAndNext(session, journalArticle,
					smallImageId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getBySmallImageId_PrevAndNext(session, journalArticle,
					smallImageId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getBySmallImageId_PrevAndNext(Session session,
		JournalArticle journalArticle, long smallImageId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

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

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(smallImageId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key to search with
	 * @param status the status to search with
	 * @return the matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByR_ST(long resourcePrimKey, int status)
		throws SystemException {
		return findByR_ST(resourcePrimKey, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByR_ST(long resourcePrimKey, int status,
		int start, int end) throws SystemException {
		return findByR_ST(resourcePrimKey, status, start, end, null);
	}

	/**
	 * Finds an ordered range of all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByR_ST(long resourcePrimKey, int status,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				resourcePrimKey, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_R_ST,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_R_ST_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_ST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(status);

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_R_ST,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_R_ST,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByR_ST_First(long resourcePrimKey, int status,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByR_ST(resourcePrimKey, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("resourcePrimKey=");
			msg.append(resourcePrimKey);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByR_ST_Last(long resourcePrimKey, int status,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		int count = countByR_ST(resourcePrimKey, status);

		List<JournalArticle> list = findByR_ST(resourcePrimKey, status,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("resourcePrimKey=");
			msg.append(resourcePrimKey);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the journal articles before and after the current journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current journal article
	 * @param resourcePrimKey the resource prim key to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle[] findByR_ST_PrevAndNext(long id,
		long resourcePrimKey, int status, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByR_ST_PrevAndNext(session, journalArticle,
					resourcePrimKey, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByR_ST_PrevAndNext(session, journalArticle,
					resourcePrimKey, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByR_ST_PrevAndNext(Session session,
		JournalArticle journalArticle, long resourcePrimKey, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_R_ST_RESOURCEPRIMKEY_2);

		query.append(_FINDER_COLUMN_R_ST_STATUS_2);

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

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(resourcePrimKey);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @return the matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_A(long groupId, String articleId)
		throws SystemException {
		return findByG_A(groupId, articleId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_A(long groupId, String articleId,
		int start, int end) throws SystemException {
		return findByG_A(groupId, articleId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_A(long groupId, String articleId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, articleId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_A,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_ARTICLEID_1);
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_A,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_A,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByG_A_First(long groupId, String articleId,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByG_A(groupId, articleId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", articleId=");
			msg.append(articleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByG_A_Last(long groupId, String articleId,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		int count = countByG_A(groupId, articleId);

		List<JournalArticle> list = findByG_A(groupId, articleId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", articleId=");
			msg.append(articleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the journal articles before and after the current journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle[] findByG_A_PrevAndNext(long id, long groupId,
		String articleId, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_A_PrevAndNext(session, journalArticle, groupId,
					articleId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_A_PrevAndNext(session, journalArticle, groupId,
					articleId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_A_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String articleId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_ARTICLEID_1);
		}
		else {
			if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
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

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (articleId != null) {
			qPos.add(articleId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @return the matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_A(long groupId, String articleId)
		throws SystemException {
		return filterFindByG_A(groupId, articleId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_A(long groupId, String articleId,
		int start, int end) throws SystemException {
		return filterFindByG_A(groupId, articleId, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_A(long groupId, String articleId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A(groupId, articleId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_ARTICLEID_1);
		}
		else {
			if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (articleId != null) {
				qPos.add(articleId);
			}

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
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
	 * Finds all the journal articles where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @return the matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_S(long groupId, String structureId)
		throws SystemException {
		return findByG_S(groupId, structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the journal articles where groupId = &#63; and structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_S(long groupId, String structureId,
		int start, int end) throws SystemException {
		return findByG_S(groupId, structureId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the journal articles where groupId = &#63; and structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_S(long groupId, String structureId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, structureId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_S,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			if (structureId == null) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
			}
			else {
				if (structureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
				}

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first journal article in the ordered set where groupId = &#63; and structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByG_S_First(long groupId, String structureId,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByG_S(groupId, structureId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last journal article in the ordered set where groupId = &#63; and structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByG_S_Last(long groupId, String structureId,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		int count = countByG_S(groupId, structureId);

		List<JournalArticle> list = findByG_S(groupId, structureId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the journal articles before and after the current journal article in the ordered set where groupId = &#63; and structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle[] findByG_S_PrevAndNext(long id, long groupId,
		String structureId, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_S_PrevAndNext(session, journalArticle, groupId,
					structureId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_S_PrevAndNext(session, journalArticle, groupId,
					structureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_S_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String structureId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		if (structureId == null) {
			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
		}
		else {
			if (structureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
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

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (structureId != null) {
			qPos.add(structureId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the journal articles where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @return the matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_S(long groupId, String structureId)
		throws SystemException {
		return filterFindByG_S(groupId, structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the journal articles where groupId = &#63; and structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_S(long groupId,
		String structureId, int start, int end) throws SystemException {
		return filterFindByG_S(groupId, structureId, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the journal articles where groupId = &#63; and structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_S(long groupId,
		String structureId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S(groupId, structureId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		if (structureId == null) {
			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
		}
		else {
			if (structureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (structureId != null) {
				qPos.add(structureId);
			}

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
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
	 * Finds all the journal articles where groupId = &#63; and templateId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param templateId the template ID to search with
	 * @return the matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_T(long groupId, String templateId)
		throws SystemException {
		return findByG_T(groupId, templateId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the journal articles where groupId = &#63; and templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param templateId the template ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_T(long groupId, String templateId,
		int start, int end) throws SystemException {
		return findByG_T(groupId, templateId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the journal articles where groupId = &#63; and templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param templateId the template ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_T(long groupId, String templateId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, templateId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_T,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			if (templateId == null) {
				query.append(_FINDER_COLUMN_G_T_TEMPLATEID_1);
			}
			else {
				if (templateId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_T_TEMPLATEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_T_TEMPLATEID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (templateId != null) {
					qPos.add(templateId);
				}

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_T,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_T,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first journal article in the ordered set where groupId = &#63; and templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param templateId the template ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByG_T_First(long groupId, String templateId,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByG_T(groupId, templateId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", templateId=");
			msg.append(templateId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last journal article in the ordered set where groupId = &#63; and templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param templateId the template ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByG_T_Last(long groupId, String templateId,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		int count = countByG_T(groupId, templateId);

		List<JournalArticle> list = findByG_T(groupId, templateId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", templateId=");
			msg.append(templateId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the journal articles before and after the current journal article in the ordered set where groupId = &#63; and templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID to search with
	 * @param templateId the template ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle[] findByG_T_PrevAndNext(long id, long groupId,
		String templateId, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_T_PrevAndNext(session, journalArticle, groupId,
					templateId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_T_PrevAndNext(session, journalArticle, groupId,
					templateId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_T_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String templateId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		if (templateId == null) {
			query.append(_FINDER_COLUMN_G_T_TEMPLATEID_1);
		}
		else {
			if (templateId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_T_TEMPLATEID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_T_TEMPLATEID_2);
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

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (templateId != null) {
			qPos.add(templateId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the journal articles where groupId = &#63; and templateId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param templateId the template ID to search with
	 * @return the matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_T(long groupId, String templateId)
		throws SystemException {
		return filterFindByG_T(groupId, templateId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the journal articles where groupId = &#63; and templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param templateId the template ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_T(long groupId,
		String templateId, int start, int end) throws SystemException {
		return filterFindByG_T(groupId, templateId, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the journal articles where groupId = &#63; and templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param templateId the template ID to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_T(long groupId,
		String templateId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T(groupId, templateId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		if (templateId == null) {
			query.append(_FINDER_COLUMN_G_T_TEMPLATEID_1);
		}
		else {
			if (templateId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_T_TEMPLATEID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_T_TEMPLATEID_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (templateId != null) {
				qPos.add(templateId);
			}

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
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
	 * Finds all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @return the matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_UT(long groupId, String urlTitle)
		throws SystemException {
		return findByG_UT(groupId, urlTitle, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_UT(long groupId, String urlTitle,
		int start, int end) throws SystemException {
		return findByG_UT(groupId, urlTitle, start, end, null);
	}

	/**
	 * Finds an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_UT(long groupId, String urlTitle,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, urlTitle,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_UT,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_UT_GROUPID_2);

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_1);
			}
			else {
				if (urlTitle.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (urlTitle != null) {
					qPos.add(urlTitle);
				}

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_UT,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_UT,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByG_UT_First(long groupId, String urlTitle,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByG_UT(groupId, urlTitle, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", urlTitle=");
			msg.append(urlTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByG_UT_Last(long groupId, String urlTitle,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		int count = countByG_UT(groupId, urlTitle);

		List<JournalArticle> list = findByG_UT(groupId, urlTitle, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", urlTitle=");
			msg.append(urlTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the journal articles before and after the current journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle[] findByG_UT_PrevAndNext(long id, long groupId,
		String urlTitle, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_UT_PrevAndNext(session, journalArticle, groupId,
					urlTitle, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_UT_PrevAndNext(session, journalArticle, groupId,
					urlTitle, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_UT_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String urlTitle,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_UT_GROUPID_2);

		if (urlTitle == null) {
			query.append(_FINDER_COLUMN_G_UT_URLTITLE_1);
		}
		else {
			if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
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

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (urlTitle != null) {
			qPos.add(urlTitle);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @return the matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_UT(long groupId, String urlTitle)
		throws SystemException {
		return filterFindByG_UT(groupId, urlTitle, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_UT(long groupId, String urlTitle,
		int start, int end) throws SystemException {
		return filterFindByG_UT(groupId, urlTitle, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_UT(long groupId, String urlTitle,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_UT(groupId, urlTitle, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_UT_GROUPID_2);

		if (urlTitle == null) {
			query.append(_FINDER_COLUMN_G_UT_URLTITLE_1);
		}
		else {
			if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (urlTitle != null) {
				qPos.add(urlTitle);
			}

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
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
	 * Finds all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @return the matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_ST(long groupId, int status)
		throws SystemException {
		return findByG_ST(groupId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_ST(long groupId, int status, int start,
		int end) throws SystemException {
		return findByG_ST(groupId, status, start, end, null);
	}

	/**
	 * Finds an ordered range of all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_ST(long groupId, int status, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_ST,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_ST_GROUPID_2);

			query.append(_FINDER_COLUMN_G_ST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_ST,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_ST,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first journal article in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByG_ST_First(long groupId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByG_ST(groupId, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last journal article in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByG_ST_Last(long groupId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		int count = countByG_ST(groupId, status);

		List<JournalArticle> list = findByG_ST(groupId, status, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the journal articles before and after the current journal article in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle[] findByG_ST_PrevAndNext(long id, long groupId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_ST_PrevAndNext(session, journalArticle, groupId,
					status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_ST_PrevAndNext(session, journalArticle, groupId,
					status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_ST_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_ST_GROUPID_2);

		query.append(_FINDER_COLUMN_G_ST_STATUS_2);

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

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @return the matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_ST(long groupId, int status)
		throws SystemException {
		return filterFindByG_ST(groupId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_ST(long groupId, int status,
		int start, int end) throws SystemException {
		return filterFindByG_ST(groupId, status, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_ST(long groupId, int status,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_ST(groupId, status, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_ST_GROUPID_2);

		query.append(_FINDER_COLUMN_G_ST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
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
	 * Finds all the journal articles where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @param status the status to search with
	 * @return the matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByC_ST(long companyId, int status)
		throws SystemException {
		return findByC_ST(companyId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the journal articles where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByC_ST(long companyId, int status,
		int start, int end) throws SystemException {
		return findByC_ST(companyId, status, start, end, null);
	}

	/**
	 * Finds an ordered range of all the journal articles where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByC_ST(long companyId, int status,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_ST,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_C_ST_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_ST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_C_ST,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_ST,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first journal article in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByC_ST_First(long companyId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByC_ST(companyId, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last journal article in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByC_ST_Last(long companyId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		int count = countByC_ST(companyId, status);

		List<JournalArticle> list = findByC_ST(companyId, status, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the journal articles before and after the current journal article in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current journal article
	 * @param companyId the company ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle[] findByC_ST_PrevAndNext(long id, long companyId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByC_ST_PrevAndNext(session, journalArticle,
					companyId, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByC_ST_PrevAndNext(session, journalArticle,
					companyId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByC_ST_PrevAndNext(Session session,
		JournalArticle journalArticle, long companyId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_C_ST_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_ST_STATUS_2);

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

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchArticleException} if it could not be found.
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param version the version to search with
	 * @return the matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByG_A_V(long groupId, String articleId,
		double version) throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = fetchByG_A_V(groupId, articleId, version);

		if (journalArticle == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", articleId=");
			msg.append(articleId);

			msg.append(", version=");
			msg.append(version);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchArticleException(msg.toString());
		}

		return journalArticle;
	}

	/**
	 * Finds the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param version the version to search with
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle fetchByG_A_V(long groupId, String articleId,
		double version) throws SystemException {
		return fetchByG_A_V(groupId, articleId, version, true);
	}

	/**
	 * Finds the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param version the version to search with
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle fetchByG_A_V(long groupId, String articleId,
		double version, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, articleId, version };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_A_V,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_A_V_GROUPID_2);

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_1);
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_2);
				}
			}

			query.append(_FINDER_COLUMN_G_A_V_VERSION_2);

			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				qPos.add(version);

				List<JournalArticle> list = q.list();

				result = list;

				JournalArticle journalArticle = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V,
						finderArgs, list);
				}
				else {
					journalArticle = list.get(0);

					cacheResult(journalArticle);

					if ((journalArticle.getGroupId() != groupId) ||
							(journalArticle.getArticleId() == null) ||
							!journalArticle.getArticleId().equals(articleId) ||
							(journalArticle.getVersion() != version)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V,
							finderArgs, journalArticle);
					}
				}

				return journalArticle;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_A_V,
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
				return (JournalArticle)result;
			}
		}
	}

	/**
	 * Finds all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param status the status to search with
	 * @return the matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_A_ST(long groupId, String articleId,
		int status) throws SystemException {
		return findByG_A_ST(groupId, articleId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_A_ST(long groupId, String articleId,
		int status, int start, int end) throws SystemException {
		return findByG_A_ST(groupId, articleId, status, start, end, null);
	}

	/**
	 * Finds an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_A_ST(long groupId, String articleId,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, articleId, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_A_ST,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_1);
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
				}
			}

			query.append(_FINDER_COLUMN_G_A_ST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				qPos.add(status);

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_A_ST,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_A_ST,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByG_A_ST_First(long groupId, String articleId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByG_A_ST(groupId, articleId, status, 0,
				1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", articleId=");
			msg.append(articleId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByG_A_ST_Last(long groupId, String articleId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		int count = countByG_A_ST(groupId, articleId, status);

		List<JournalArticle> list = findByG_A_ST(groupId, articleId, status,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", articleId=");
			msg.append(articleId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the journal articles before and after the current journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle[] findByG_A_ST_PrevAndNext(long id, long groupId,
		String articleId, int status, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_A_ST_PrevAndNext(session, journalArticle,
					groupId, articleId, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_A_ST_PrevAndNext(session, journalArticle,
					groupId, articleId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_A_ST_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String articleId,
		int status, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_1);
		}
		else {
			if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
			}
		}

		query.append(_FINDER_COLUMN_G_A_ST_STATUS_2);

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

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (articleId != null) {
			qPos.add(articleId);
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param status the status to search with
	 * @return the matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_A_ST(long groupId,
		String articleId, int status) throws SystemException {
		return filterFindByG_A_ST(groupId, articleId, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_A_ST(long groupId,
		String articleId, int status, int start, int end)
		throws SystemException {
		return filterFindByG_A_ST(groupId, articleId, status, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_A_ST(long groupId,
		String articleId, int status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_ST(groupId, articleId, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_1);
		}
		else {
			if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
			}
		}

		query.append(_FINDER_COLUMN_G_A_ST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (articleId != null) {
				qPos.add(articleId);
			}

			qPos.add(status);

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
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
	 * Finds all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param status the status to search with
	 * @return the matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_UT_ST(long groupId, String urlTitle,
		int status) throws SystemException {
		return findByG_UT_ST(groupId, urlTitle, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_UT_ST(long groupId, String urlTitle,
		int status, int start, int end) throws SystemException {
		return findByG_UT_ST(groupId, urlTitle, status, start, end, null);
	}

	/**
	 * Finds an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findByG_UT_ST(long groupId, String urlTitle,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, urlTitle, status,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_UT_ST,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_UT_ST_GROUPID_2);

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_1);
			}
			else {
				if (urlTitle.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_2);
				}
			}

			query.append(_FINDER_COLUMN_G_UT_ST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (urlTitle != null) {
					qPos.add(urlTitle);
				}

				qPos.add(status);

				list = (List<JournalArticle>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_UT_ST,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_UT_ST,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByG_UT_ST_First(long groupId, String urlTitle,
		int status, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByG_UT_ST(groupId, urlTitle, status, 0,
				1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", urlTitle=");
			msg.append(urlTitle);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle findByG_UT_ST_Last(long groupId, String urlTitle,
		int status, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		int count = countByG_UT_ST(groupId, urlTitle, status);

		List<JournalArticle> list = findByG_UT_ST(groupId, urlTitle, status,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", urlTitle=");
			msg.append(urlTitle);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the journal articles before and after the current journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param status the status to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticle[] findByG_UT_ST_PrevAndNext(long id, long groupId,
		String urlTitle, int status, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_UT_ST_PrevAndNext(session, journalArticle,
					groupId, urlTitle, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_UT_ST_PrevAndNext(session, journalArticle,
					groupId, urlTitle, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_UT_ST_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String urlTitle,
		int status, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_UT_ST_GROUPID_2);

		if (urlTitle == null) {
			query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_1);
		}
		else {
			if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_2);
			}
		}

		query.append(_FINDER_COLUMN_G_UT_ST_STATUS_2);

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

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (urlTitle != null) {
			qPos.add(urlTitle);
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Filters by the user's permissions and finds all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param status the status to search with
	 * @return the matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_UT_ST(long groupId,
		String urlTitle, int status) throws SystemException {
		return filterFindByG_UT_ST(groupId, urlTitle, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Filters by the user's permissions and finds a range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_UT_ST(long groupId,
		String urlTitle, int status, int start, int end)
		throws SystemException {
		return filterFindByG_UT_ST(groupId, urlTitle, status, start, end, null);
	}

	/**
	 * Filters by the user's permissions and finds an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param status the status to search with
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> filterFindByG_UT_ST(long groupId,
		String urlTitle, int status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_UT_ST(groupId, urlTitle, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_UT_ST_GROUPID_2);

		if (urlTitle == null) {
			query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_1);
		}
		else {
			if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_2);
			}
		}

		query.append(_FINDER_COLUMN_G_UT_ST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (urlTitle != null) {
				qPos.add(urlTitle);
			}

			qPos.add(status);

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
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
	 * Finds all the journal articles.
	 *
	 * @return the journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the journal articles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @return the range of journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the journal articles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal articles to return
	 * @param end the upper bound of the range of journal articles to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticle> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_JOURNALARTICLE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_JOURNALARTICLE.concat(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
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
	 * Removes all the journal articles where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (JournalArticle journalArticle : findByUuid(uuid)) {
			journalArticlePersistence.remove(journalArticle);
		}
	}

	/**
	 * Removes the journal article where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByUUID_G(uuid, groupId);

		journalArticlePersistence.remove(journalArticle);
	}

	/**
	 * Removes all the journal articles where resourcePrimKey = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByResourcePrimKey(long resourcePrimKey)
		throws SystemException {
		for (JournalArticle journalArticle : findByResourcePrimKey(
				resourcePrimKey)) {
			journalArticlePersistence.remove(journalArticle);
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (JournalArticle journalArticle : findByGroupId(groupId)) {
			journalArticlePersistence.remove(journalArticle);
		}
	}

	/**
	 * Removes all the journal articles where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (JournalArticle journalArticle : findByCompanyId(companyId)) {
			journalArticlePersistence.remove(journalArticle);
		}
	}

	/**
	 * Removes all the journal articles where smallImageId = &#63; from the database.
	 *
	 * @param smallImageId the small image ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeBySmallImageId(long smallImageId)
		throws SystemException {
		for (JournalArticle journalArticle : findBySmallImageId(smallImageId)) {
			journalArticlePersistence.remove(journalArticle);
		}
	}

	/**
	 * Removes all the journal articles where resourcePrimKey = &#63; and status = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key to search with
	 * @param status the status to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByR_ST(long resourcePrimKey, int status)
		throws SystemException {
		for (JournalArticle journalArticle : findByR_ST(resourcePrimKey, status)) {
			journalArticlePersistence.remove(journalArticle);
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and articleId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_A(long groupId, String articleId)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_A(groupId, articleId)) {
			journalArticlePersistence.remove(journalArticle);
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and structureId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_S(long groupId, String structureId)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_S(groupId, structureId)) {
			journalArticlePersistence.remove(journalArticle);
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and templateId = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param templateId the template ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_T(long groupId, String templateId)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_T(groupId, templateId)) {
			journalArticlePersistence.remove(journalArticle);
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_UT(long groupId, String urlTitle)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_UT(groupId, urlTitle)) {
			journalArticlePersistence.remove(journalArticle);
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_ST(long groupId, int status)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_ST(groupId, status)) {
			journalArticlePersistence.remove(journalArticle);
		}
	}

	/**
	 * Removes all the journal articles where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID to search with
	 * @param status the status to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_ST(long companyId, int status)
		throws SystemException {
		for (JournalArticle journalArticle : findByC_ST(companyId, status)) {
			journalArticlePersistence.remove(journalArticle);
		}
	}

	/**
	 * Removes the journal article where groupId = &#63; and articleId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param version the version to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_A_V(long groupId, String articleId, double version)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByG_A_V(groupId, articleId, version);

		journalArticlePersistence.remove(journalArticle);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param status the status to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_A_ST(long groupId, String articleId, int status)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_A_ST(groupId, articleId,
				status)) {
			journalArticlePersistence.remove(journalArticle);
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param status the status to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_UT_ST(long groupId, String urlTitle, int status)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_UT_ST(groupId, urlTitle,
				status)) {
			journalArticlePersistence.remove(journalArticle);
		}
	}

	/**
	 * Removes all the journal articles from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (JournalArticle journalArticle : findAll()) {
			journalArticlePersistence.remove(journalArticle);
		}
	}

	/**
	 * Counts all the journal articles where uuid = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @return the number of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

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
	 * Counts all the journal articles where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @param groupId the group ID to search with
	 * @return the number of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

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
	 * Counts all the journal articles where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key to search with
	 * @return the number of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByResourcePrimKey(long resourcePrimKey)
		throws SystemException {
		Object[] finderArgs = new Object[] { resourcePrimKey };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the journal articles where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the number of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

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
	 * Filters by the user's permissions and counts all the journal articles where groupId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @return the number of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(), _FILTER_COLUMN_PK,
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
	 * Counts all the journal articles where companyId = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @return the number of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the journal articles where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID to search with
	 * @return the number of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public int countBySmallImageId(long smallImageId) throws SystemException {
		Object[] finderArgs = new Object[] { smallImageId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key to search with
	 * @param status the status to search with
	 * @return the number of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByR_ST(long resourcePrimKey, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { resourcePrimKey, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_R_ST,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_R_ST_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_ST_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_R_ST,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @return the number of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_A(long groupId, String articleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, articleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_A,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_ARTICLEID_1);
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_A, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Filters by the user's permissions and counts all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @return the number of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_A(long groupId, String articleId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A(groupId, articleId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_ARTICLEID_1);
		}
		else {
			if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (articleId != null) {
				qPos.add(articleId);
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
	 * Counts all the journal articles where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @return the number of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_S(long groupId, String structureId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, structureId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			if (structureId == null) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
			}
			else {
				if (structureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Filters by the user's permissions and counts all the journal articles where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param structureId the structure ID to search with
	 * @return the number of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_S(long groupId, String structureId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_S(groupId, structureId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		if (structureId == null) {
			query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
		}
		else {
			if (structureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (structureId != null) {
				qPos.add(structureId);
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
	 * Counts all the journal articles where groupId = &#63; and templateId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param templateId the template ID to search with
	 * @return the number of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_T(long groupId, String templateId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, templateId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			if (templateId == null) {
				query.append(_FINDER_COLUMN_G_T_TEMPLATEID_1);
			}
			else {
				if (templateId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_T_TEMPLATEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_T_TEMPLATEID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (templateId != null) {
					qPos.add(templateId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_T, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Filters by the user's permissions and counts all the journal articles where groupId = &#63; and templateId = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param templateId the template ID to search with
	 * @return the number of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_T(long groupId, String templateId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_T(groupId, templateId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		if (templateId == null) {
			query.append(_FINDER_COLUMN_G_T_TEMPLATEID_1);
		}
		else {
			if (templateId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_T_TEMPLATEID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_T_TEMPLATEID_2);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (templateId != null) {
				qPos.add(templateId);
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
	 * Counts all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @return the number of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_UT(long groupId, String urlTitle)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, urlTitle };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_UT,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_UT_GROUPID_2);

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_1);
			}
			else {
				if (urlTitle.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (urlTitle != null) {
					qPos.add(urlTitle);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_UT,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Filters by the user's permissions and counts all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @return the number of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_UT(long groupId, String urlTitle)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_UT(groupId, urlTitle);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_UT_GROUPID_2);

		if (urlTitle == null) {
			query.append(_FINDER_COLUMN_G_UT_URLTITLE_1);
		}
		else {
			if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (urlTitle != null) {
				qPos.add(urlTitle);
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
	 * Counts all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @return the number of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_ST(long groupId, int status) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_ST,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_ST_GROUPID_2);

			query.append(_FINDER_COLUMN_G_ST_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_ST,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Filters by the user's permissions and counts all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param status the status to search with
	 * @return the number of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_ST(long groupId, int status)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_ST(groupId, status);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_ST_GROUPID_2);

		query.append(_FINDER_COLUMN_G_ST_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

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
	 * Counts all the journal articles where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @param status the status to search with
	 * @return the number of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_ST(long companyId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_ST,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_C_ST_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_ST_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_ST,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the journal articles where groupId = &#63; and articleId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param version the version to search with
	 * @return the number of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_A_V(long groupId, String articleId, double version)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, articleId, version };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_A_V,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_A_V_GROUPID_2);

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_1);
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_2);
				}
			}

			query.append(_FINDER_COLUMN_G_A_V_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				qPos.add(version);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_A_V,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param status the status to search with
	 * @return the number of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_A_ST(long groupId, String articleId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, articleId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_A_ST,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_1);
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
				}
			}

			query.append(_FINDER_COLUMN_G_A_ST_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_A_ST,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Filters by the user's permissions and counts all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param articleId the article ID to search with
	 * @param status the status to search with
	 * @return the number of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_A_ST(long groupId, String articleId, int status)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A_ST(groupId, articleId, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_1);
		}
		else {
			if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
			}
		}

		query.append(_FINDER_COLUMN_G_A_ST_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (articleId != null) {
				qPos.add(articleId);
			}

			qPos.add(status);

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
	 * Counts all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param status the status to search with
	 * @return the number of matching journal articles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_UT_ST(long groupId, String urlTitle, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, urlTitle, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_UT_ST,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_UT_ST_GROUPID_2);

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_1);
			}
			else {
				if (urlTitle.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_2);
				}
			}

			query.append(_FINDER_COLUMN_G_UT_ST_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (urlTitle != null) {
					qPos.add(urlTitle);
				}

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_UT_ST,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Filters by the user's permissions and counts all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID to search with
	 * @param urlTitle the url title to search with
	 * @param status the status to search with
	 * @return the number of matching journal articles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_UT_ST(long groupId, String urlTitle, int status)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_UT_ST(groupId, urlTitle, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_UT_ST_GROUPID_2);

		if (urlTitle == null) {
			query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_1);
		}
		else {
			if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_2);
			}
		}

		query.append(_FINDER_COLUMN_G_UT_ST_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(), _FILTER_COLUMN_PK,
				_FILTER_COLUMN_USERID, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (urlTitle != null) {
				qPos.add(urlTitle);
			}

			qPos.add(status);

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
	 * Counts all the journal articles.
	 *
	 * @return the number of journal articles
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

				Query q = session.createQuery(_SQL_COUNT_JOURNALARTICLE);

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
	 * Initializes the journal article persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.journal.model.JournalArticle")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalArticle>> listenersList = new ArrayList<ModelListener<JournalArticle>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalArticle>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(JournalArticleImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = JournalArticlePersistence.class)
	protected JournalArticlePersistence journalArticlePersistence;
	@BeanReference(type = JournalArticleImagePersistence.class)
	protected JournalArticleImagePersistence journalArticleImagePersistence;
	@BeanReference(type = JournalArticleResourcePersistence.class)
	protected JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(type = JournalContentSearchPersistence.class)
	protected JournalContentSearchPersistence journalContentSearchPersistence;
	@BeanReference(type = JournalFeedPersistence.class)
	protected JournalFeedPersistence journalFeedPersistence;
	@BeanReference(type = JournalStructurePersistence.class)
	protected JournalStructurePersistence journalStructurePersistence;
	@BeanReference(type = JournalTemplatePersistence.class)
	protected JournalTemplatePersistence journalTemplatePersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	@BeanReference(type = AssetCategoryPersistence.class)
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = RatingsStatsPersistence.class)
	protected RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(type = SocialEquityLogPersistence.class)
	protected SocialEquityLogPersistence socialEquityLogPersistence;
	private static final String _SQL_SELECT_JOURNALARTICLE = "SELECT journalArticle FROM JournalArticle journalArticle";
	private static final String _SQL_SELECT_JOURNALARTICLE_WHERE = "SELECT journalArticle FROM JournalArticle journalArticle WHERE ";
	private static final String _SQL_COUNT_JOURNALARTICLE = "SELECT COUNT(journalArticle) FROM JournalArticle journalArticle";
	private static final String _SQL_COUNT_JOURNALARTICLE_WHERE = "SELECT COUNT(journalArticle) FROM JournalArticle journalArticle WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "journalArticle.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "journalArticle.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(journalArticle.uuid IS NULL OR journalArticle.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "journalArticle.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "journalArticle.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(journalArticle.uuid IS NULL OR journalArticle.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "journalArticle.groupId = ?";
	private static final String _FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2 =
		"journalArticle.resourcePrimKey = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "journalArticle.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "journalArticle.companyId = ?";
	private static final String _FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2 = "journalArticle.smallImageId = ?";
	private static final String _FINDER_COLUMN_R_ST_RESOURCEPRIMKEY_2 = "journalArticle.resourcePrimKey = ? AND ";
	private static final String _FINDER_COLUMN_R_ST_STATUS_2 = "journalArticle.status = ?";
	private static final String _FINDER_COLUMN_G_A_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ARTICLEID_1 = "journalArticle.articleId IS NULL";
	private static final String _FINDER_COLUMN_G_A_ARTICLEID_2 = "journalArticle.articleId = ?";
	private static final String _FINDER_COLUMN_G_A_ARTICLEID_3 = "(journalArticle.articleId IS NULL OR journalArticle.articleId = ?)";
	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_1 = "journalArticle.structureId IS NULL";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_2 = "journalArticle.structureId = ?";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_3 = "(journalArticle.structureId IS NULL OR journalArticle.structureId = ?)";
	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_TEMPLATEID_1 = "journalArticle.templateId IS NULL";
	private static final String _FINDER_COLUMN_G_T_TEMPLATEID_2 = "journalArticle.templateId = ?";
	private static final String _FINDER_COLUMN_G_T_TEMPLATEID_3 = "(journalArticle.templateId IS NULL OR journalArticle.templateId = ?)";
	private static final String _FINDER_COLUMN_G_UT_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_UT_URLTITLE_1 = "journalArticle.urlTitle IS NULL";
	private static final String _FINDER_COLUMN_G_UT_URLTITLE_2 = "journalArticle.urlTitle = ?";
	private static final String _FINDER_COLUMN_G_UT_URLTITLE_3 = "(journalArticle.urlTitle IS NULL OR journalArticle.urlTitle = ?)";
	private static final String _FINDER_COLUMN_G_ST_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_ST_STATUS_2 = "journalArticle.status = ?";
	private static final String _FINDER_COLUMN_C_ST_COMPANYID_2 = "journalArticle.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_ST_STATUS_2 = "journalArticle.status = ?";
	private static final String _FINDER_COLUMN_G_A_V_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_V_ARTICLEID_1 = "journalArticle.articleId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_A_V_ARTICLEID_2 = "journalArticle.articleId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_V_ARTICLEID_3 = "(journalArticle.articleId IS NULL OR journalArticle.articleId = ?) AND ";
	private static final String _FINDER_COLUMN_G_A_V_VERSION_2 = "journalArticle.version = ?";
	private static final String _FINDER_COLUMN_G_A_ST_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ST_ARTICLEID_1 = "journalArticle.articleId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_A_ST_ARTICLEID_2 = "journalArticle.articleId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ST_ARTICLEID_3 = "(journalArticle.articleId IS NULL OR journalArticle.articleId = ?) AND ";
	private static final String _FINDER_COLUMN_G_A_ST_STATUS_2 = "journalArticle.status = ?";
	private static final String _FINDER_COLUMN_G_UT_ST_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_UT_ST_URLTITLE_1 = "journalArticle.urlTitle IS NULL AND ";
	private static final String _FINDER_COLUMN_G_UT_ST_URLTITLE_2 = "journalArticle.urlTitle = ? AND ";
	private static final String _FINDER_COLUMN_G_UT_ST_URLTITLE_3 = "(journalArticle.urlTitle IS NULL OR journalArticle.urlTitle = ?) AND ";
	private static final String _FINDER_COLUMN_G_UT_ST_STATUS_2 = "journalArticle.status = ?";
	private static final String _FILTER_SQL_SELECT_JOURNALARTICLE_WHERE = "SELECT DISTINCT {journalArticle.*} FROM JournalArticle journalArticle WHERE ";
	private static final String _FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {JournalArticle.*} FROM (SELECT DISTINCT journalArticle.id FROM JournalArticle journalArticle WHERE ";
	private static final String _FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN JournalArticle ON TEMP_TABLE.id = JournalArticle.id";
	private static final String _FILTER_SQL_COUNT_JOURNALARTICLE_WHERE = "SELECT COUNT(DISTINCT journalArticle.id) AS COUNT_VALUE FROM JournalArticle journalArticle WHERE ";
	private static final String _FILTER_COLUMN_PK = "journalArticle.id";
	private static final String _FILTER_COLUMN_USERID = "journalArticle.userId";
	private static final String _FILTER_ENTITY_ALIAS = "journalArticle";
	private static final String _FILTER_ENTITY_TABLE = "JournalArticle";
	private static final String _ORDER_BY_ENTITY_ALIAS = "journalArticle.";
	private static final String _ORDER_BY_ENTITY_TABLE = "JournalArticle.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No JournalArticle exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No JournalArticle exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(JournalArticlePersistenceImpl.class);
}