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

package com.liferay.portlet.journal.service.persistence;

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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="JournalArticlePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticlePersistence
 * @see       JournalArticleUtil
 * @generated
 */
public class JournalArticlePersistenceImpl extends BasePersistenceImpl<JournalArticle>
	implements JournalArticlePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = JournalArticleImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
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
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
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
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
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
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_SMALLIMAGEID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
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
	public static final FinderPath FINDER_PATH_FIND_BY_R_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByR_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_R_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByR_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_R_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByR_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_A = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_A",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_A = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
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
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
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
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_T = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
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
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_UT = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
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
	public static final FinderPath FINDER_PATH_FIND_BY_C_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_S",
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
	public static final FinderPath FINDER_PATH_FIND_BY_G_A_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_A_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_A_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_A_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_A_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_UT_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_UT_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_UT_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_UT_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_UT_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_UT_S",
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

	public void clearCache() {
		CacheRegistry.clear(JournalArticleImpl.class.getName());
		EntityCacheUtil.clearCache(JournalArticleImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

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

	public JournalArticle create(long id) {
		JournalArticle journalArticle = new JournalArticleImpl();

		journalArticle.setNew(true);
		journalArticle.setPrimaryKey(id);

		String uuid = PortalUUIDUtil.generate();

		journalArticle.setUuid(uuid);

		return journalArticle;
	}

	public JournalArticle remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

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

			return remove(journalArticle);
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

	public JournalArticle remove(JournalArticle journalArticle)
		throws SystemException {
		for (ModelListener<JournalArticle> listener : listeners) {
			listener.onBeforeRemove(journalArticle);
		}

		journalArticle = removeImpl(journalArticle);

		for (ModelListener<JournalArticle> listener : listeners) {
			listener.onAfterRemove(journalArticle);
		}

		return journalArticle;
	}

	protected JournalArticle removeImpl(JournalArticle journalArticle)
		throws SystemException {
		journalArticle = toUnwrappedModel(journalArticle);

		Session session = null;

		try {
			session = openSession();

			if (journalArticle.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(JournalArticleImpl.class,
						journalArticle.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(journalArticle);

			session.flush();
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

	public JournalArticle findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

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

	public JournalArticle fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

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

	public List<JournalArticle> findByUuid(String uuid)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

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

				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<JournalArticle> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
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
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

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

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

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

	public JournalArticle fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public JournalArticle fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

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
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<JournalArticle>());
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

	public List<JournalArticle> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<JournalArticle> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
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
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

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

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

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

	public List<JournalArticle> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<JournalArticle> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
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
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

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

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

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

	public List<JournalArticle> findBySmallImageId(long smallImageId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(smallImageId) };

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_SMALLIMAGEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_SMALLIMAGEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findBySmallImageId(long smallImageId,
		int start, int end) throws SystemException {
		return findBySmallImageId(smallImageId, start, end, null);
	}

	public List<JournalArticle> findBySmallImageId(long smallImageId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(smallImageId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_SMALLIMAGEID,
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
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_SMALLIMAGEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

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

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

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

	public List<JournalArticle> findByR_S(long resourcePrimKey, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(resourcePrimKey), new Integer(status)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_R_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				query.append(_FINDER_COLUMN_R_S_RESOURCEPRIMKEY_2);

				query.append(_FINDER_COLUMN_R_S_STATUS_2);

				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_R_S, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByR_S(long resourcePrimKey, int status,
		int start, int end) throws SystemException {
		return findByR_S(resourcePrimKey, status, start, end, null);
	}

	public List<JournalArticle> findByR_S(long resourcePrimKey, int status,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(resourcePrimKey), new Integer(status),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_R_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(4 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				query.append(_FINDER_COLUMN_R_S_RESOURCEPRIMKEY_2);

				query.append(_FINDER_COLUMN_R_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

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
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_R_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticle findByR_S_First(long resourcePrimKey, int status,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByR_S(resourcePrimKey, status, 0, 1,
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

	public JournalArticle findByR_S_Last(long resourcePrimKey, int status,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		int count = countByR_S(resourcePrimKey, status);

		List<JournalArticle> list = findByR_S(resourcePrimKey, status,
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

	public JournalArticle[] findByR_S_PrevAndNext(long id,
		long resourcePrimKey, int status, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByR_S_PrevAndNext(session, journalArticle,
					resourcePrimKey, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByR_S_PrevAndNext(session, journalArticle,
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

	protected JournalArticle getByR_S_PrevAndNext(Session session,
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

		query.append(_FINDER_COLUMN_R_S_RESOURCEPRIMKEY_2);

		query.append(_FINDER_COLUMN_R_S_STATUS_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

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

	public List<JournalArticle> findByG_A(long groupId, String articleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), articleId };

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

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

				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_A, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByG_A(long groupId, String articleId,
		int start, int end) throws SystemException {
		return findByG_A(groupId, articleId, start, end, null);
	}

	public List<JournalArticle> findByG_A(long groupId, String articleId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

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
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_A,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

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

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

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

	public List<JournalArticle> findByG_S(long groupId, String structureId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), structureId };

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

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

				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_S, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByG_S(long groupId, String structureId,
		int start, int end) throws SystemException {
		return findByG_S(groupId, structureId, start, end, null);
	}

	public List<JournalArticle> findByG_S(long groupId, String structureId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				structureId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

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
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

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

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

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

	public List<JournalArticle> findByG_T(long groupId, String templateId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), templateId };

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

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

				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (templateId != null) {
					qPos.add(templateId);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_T, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByG_T(long groupId, String templateId,
		int start, int end) throws SystemException {
		return findByG_T(groupId, templateId, start, end, null);
	}

	public List<JournalArticle> findByG_T(long groupId, String templateId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				templateId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

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
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

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

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

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

	public List<JournalArticle> findByG_UT(long groupId, String urlTitle)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), urlTitle };

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_UT,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

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

				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (urlTitle != null) {
					qPos.add(urlTitle);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_UT, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByG_UT(long groupId, String urlTitle,
		int start, int end) throws SystemException {
		return findByG_UT(groupId, urlTitle, start, end, null);
	}

	public List<JournalArticle> findByG_UT(long groupId, String urlTitle,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				urlTitle,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_UT,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

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
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_UT,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

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

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

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

	public List<JournalArticle> findByC_S(long companyId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(status)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_S_STATUS_2);

				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_S, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByC_S(long companyId, int status,
		int start, int end) throws SystemException {
		return findByC_S(companyId, status, start, end, null);
	}

	public List<JournalArticle> findByC_S(long companyId, int status,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(status),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(4 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

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
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticle findByC_S_First(long companyId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByC_S(companyId, status, 0, 1,
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

	public JournalArticle findByC_S_Last(long companyId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		int count = countByC_S(companyId, status);

		List<JournalArticle> list = findByC_S(companyId, status, count - 1,
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

	public JournalArticle[] findByC_S_PrevAndNext(long id, long companyId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByC_S_PrevAndNext(session, journalArticle, companyId,
					status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByC_S_PrevAndNext(session, journalArticle, companyId,
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

	protected JournalArticle getByC_S_PrevAndNext(Session session,
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

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_S_STATUS_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

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

	public JournalArticle fetchByG_A_V(long groupId, String articleId,
		double version) throws SystemException {
		return fetchByG_A_V(groupId, articleId, version, true);
	}

	public JournalArticle fetchByG_A_V(long groupId, String articleId,
		double version, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId, new Double(version)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_A_V,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

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
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V,
						finderArgs, new ArrayList<JournalArticle>());
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

	public List<JournalArticle> findByG_A_S(long groupId, String articleId,
		int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId, new Integer(status)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_A_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				query.append(_FINDER_COLUMN_G_A_S_GROUPID_2);

				if (articleId == null) {
					query.append(_FINDER_COLUMN_G_A_S_ARTICLEID_1);
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_A_S_ARTICLEID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_A_S_ARTICLEID_2);
					}
				}

				query.append(_FINDER_COLUMN_G_A_S_STATUS_2);

				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_A_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByG_A_S(long groupId, String articleId,
		int status, int start, int end) throws SystemException {
		return findByG_A_S(groupId, articleId, status, start, end, null);
	}

	public List<JournalArticle> findByG_A_S(long groupId, String articleId,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId, new Integer(status),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_A_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(5 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(5);
				}

				query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				query.append(_FINDER_COLUMN_G_A_S_GROUPID_2);

				if (articleId == null) {
					query.append(_FINDER_COLUMN_G_A_S_ARTICLEID_1);
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_A_S_ARTICLEID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_A_S_ARTICLEID_2);
					}
				}

				query.append(_FINDER_COLUMN_G_A_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

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
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_A_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticle findByG_A_S_First(long groupId, String articleId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByG_A_S(groupId, articleId, status, 0,
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

	public JournalArticle findByG_A_S_Last(long groupId, String articleId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		int count = countByG_A_S(groupId, articleId, status);

		List<JournalArticle> list = findByG_A_S(groupId, articleId, status,
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

	public JournalArticle[] findByG_A_S_PrevAndNext(long id, long groupId,
		String articleId, int status, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_A_S_PrevAndNext(session, journalArticle, groupId,
					articleId, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_A_S_PrevAndNext(session, journalArticle, groupId,
					articleId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_A_S_PrevAndNext(Session session,
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

		query.append(_FINDER_COLUMN_G_A_S_GROUPID_2);

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_S_ARTICLEID_1);
		}
		else {
			if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_S_ARTICLEID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_A_S_ARTICLEID_2);
			}
		}

		query.append(_FINDER_COLUMN_G_A_S_STATUS_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

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

	public List<JournalArticle> findByG_UT_S(long groupId, String urlTitle,
		int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				urlTitle, new Integer(status)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_UT_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				query.append(_FINDER_COLUMN_G_UT_S_GROUPID_2);

				if (urlTitle == null) {
					query.append(_FINDER_COLUMN_G_UT_S_URLTITLE_1);
				}
				else {
					if (urlTitle.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_UT_S_URLTITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_UT_S_URLTITLE_2);
					}
				}

				query.append(_FINDER_COLUMN_G_UT_S_STATUS_2);

				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (urlTitle != null) {
					qPos.add(urlTitle);
				}

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_UT_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticle> findByG_UT_S(long groupId, String urlTitle,
		int status, int start, int end) throws SystemException {
		return findByG_UT_S(groupId, urlTitle, status, start, end, null);
	}

	public List<JournalArticle> findByG_UT_S(long groupId, String urlTitle,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				urlTitle, new Integer(status),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_UT_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(5 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(5);
				}

				query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

				query.append(_FINDER_COLUMN_G_UT_S_GROUPID_2);

				if (urlTitle == null) {
					query.append(_FINDER_COLUMN_G_UT_S_URLTITLE_1);
				}
				else {
					if (urlTitle.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_UT_S_URLTITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_UT_S_URLTITLE_2);
					}
				}

				query.append(_FINDER_COLUMN_G_UT_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

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
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_UT_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticle findByG_UT_S_First(long groupId, String urlTitle,
		int status, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		List<JournalArticle> list = findByG_UT_S(groupId, urlTitle, status, 0,
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

	public JournalArticle findByG_UT_S_Last(long groupId, String urlTitle,
		int status, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		int count = countByG_UT_S(groupId, urlTitle, status);

		List<JournalArticle> list = findByG_UT_S(groupId, urlTitle, status,
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

	public JournalArticle[] findByG_UT_S_PrevAndNext(long id, long groupId,
		String urlTitle, int status, OrderByComparator orderByComparator)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_UT_S_PrevAndNext(session, journalArticle,
					groupId, urlTitle, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_UT_S_PrevAndNext(session, journalArticle,
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

	protected JournalArticle getByG_UT_S_PrevAndNext(Session session,
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

		query.append(_FINDER_COLUMN_G_UT_S_GROUPID_2);

		if (urlTitle == null) {
			query.append(_FINDER_COLUMN_G_UT_S_URLTITLE_1);
		}
		else {
			if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_UT_S_URLTITLE_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_UT_S_URLTITLE_2);
			}
		}

		query.append(_FINDER_COLUMN_G_UT_S_STATUS_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

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

	public List<JournalArticle> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalArticle> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<JournalArticle> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_JOURNALARTICLE);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_JOURNALARTICLE.concat(JournalArticleModelImpl.ORDER_BY_JPQL);
				}

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
					list = new ArrayList<JournalArticle>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (JournalArticle journalArticle : findByUuid(uuid)) {
			remove(journalArticle);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByUUID_G(uuid, groupId);

		remove(journalArticle);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (JournalArticle journalArticle : findByGroupId(groupId)) {
			remove(journalArticle);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (JournalArticle journalArticle : findByCompanyId(companyId)) {
			remove(journalArticle);
		}
	}

	public void removeBySmallImageId(long smallImageId)
		throws SystemException {
		for (JournalArticle journalArticle : findBySmallImageId(smallImageId)) {
			remove(journalArticle);
		}
	}

	public void removeByR_S(long resourcePrimKey, int status)
		throws SystemException {
		for (JournalArticle journalArticle : findByR_S(resourcePrimKey, status)) {
			remove(journalArticle);
		}
	}

	public void removeByG_A(long groupId, String articleId)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_A(groupId, articleId)) {
			remove(journalArticle);
		}
	}

	public void removeByG_S(long groupId, String structureId)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_S(groupId, structureId)) {
			remove(journalArticle);
		}
	}

	public void removeByG_T(long groupId, String templateId)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_T(groupId, templateId)) {
			remove(journalArticle);
		}
	}

	public void removeByG_UT(long groupId, String urlTitle)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_UT(groupId, urlTitle)) {
			remove(journalArticle);
		}
	}

	public void removeByC_S(long companyId, int status)
		throws SystemException {
		for (JournalArticle journalArticle : findByC_S(companyId, status)) {
			remove(journalArticle);
		}
	}

	public void removeByG_A_V(long groupId, String articleId, double version)
		throws NoSuchArticleException, SystemException {
		JournalArticle journalArticle = findByG_A_V(groupId, articleId, version);

		remove(journalArticle);
	}

	public void removeByG_A_S(long groupId, String articleId, int status)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_A_S(groupId, articleId,
				status)) {
			remove(journalArticle);
		}
	}

	public void removeByG_UT_S(long groupId, String urlTitle, int status)
		throws SystemException {
		for (JournalArticle journalArticle : findByG_UT_S(groupId, urlTitle,
				status)) {
			remove(journalArticle);
		}
	}

	public void removeAll() throws SystemException {
		for (JournalArticle journalArticle : findAll()) {
			remove(journalArticle);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

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

	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

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

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				String sql = query.toString();

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

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				String sql = query.toString();

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

	public int countBySmallImageId(long smallImageId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(smallImageId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

				String sql = query.toString();

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

	public int countByR_S(long resourcePrimKey, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(resourcePrimKey), new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_R_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				query.append(_FINDER_COLUMN_R_S_RESOURCEPRIMKEY_2);

				query.append(_FINDER_COLUMN_R_S_STATUS_2);

				String sql = query.toString();

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_R_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_A(long groupId, String articleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), articleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_A,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

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

	public int countByG_S(long groupId, String structureId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), structureId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

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

	public int countByG_T(long groupId, String templateId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), templateId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

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

	public int countByG_UT(long groupId, String urlTitle)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), urlTitle };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_UT,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

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

	public int countByC_S(long companyId, int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_S_STATUS_2);

				String sql = query.toString();

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_A_V(long groupId, String articleId, double version)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId, new Double(version)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_A_V,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

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

	public int countByG_A_S(long groupId, String articleId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId, new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_A_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				query.append(_FINDER_COLUMN_G_A_S_GROUPID_2);

				if (articleId == null) {
					query.append(_FINDER_COLUMN_G_A_S_ARTICLEID_1);
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_A_S_ARTICLEID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_A_S_ARTICLEID_2);
					}
				}

				query.append(_FINDER_COLUMN_G_A_S_STATUS_2);

				String sql = query.toString();

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_A_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_UT_S(long groupId, String urlTitle, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				urlTitle, new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_UT_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

				query.append(_FINDER_COLUMN_G_UT_S_GROUPID_2);

				if (urlTitle == null) {
					query.append(_FINDER_COLUMN_G_UT_S_URLTITLE_1);
				}
				else {
					if (urlTitle.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_UT_S_URLTITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_UT_S_URLTITLE_2);
					}
				}

				query.append(_FINDER_COLUMN_G_UT_S_STATUS_2);

				String sql = query.toString();

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_UT_S,
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

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.journal.model.JournalArticle")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalArticle>> listenersList = new ArrayList<ModelListener<JournalArticle>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalArticle>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
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
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "journalArticle.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "journalArticle.companyId = ?";
	private static final String _FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2 = "journalArticle.smallImageId = ?";
	private static final String _FINDER_COLUMN_R_S_RESOURCEPRIMKEY_2 = "journalArticle.resourcePrimKey = ? AND ";
	private static final String _FINDER_COLUMN_R_S_STATUS_2 = "journalArticle.status = ?";
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
	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 = "journalArticle.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_S_STATUS_2 = "journalArticle.status = ?";
	private static final String _FINDER_COLUMN_G_A_V_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_V_ARTICLEID_1 = "journalArticle.articleId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_A_V_ARTICLEID_2 = "journalArticle.articleId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_V_ARTICLEID_3 = "(journalArticle.articleId IS NULL OR journalArticle.articleId = ?) AND ";
	private static final String _FINDER_COLUMN_G_A_V_VERSION_2 = "journalArticle.version = ?";
	private static final String _FINDER_COLUMN_G_A_S_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_S_ARTICLEID_1 = "journalArticle.articleId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_A_S_ARTICLEID_2 = "journalArticle.articleId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_S_ARTICLEID_3 = "(journalArticle.articleId IS NULL OR journalArticle.articleId = ?) AND ";
	private static final String _FINDER_COLUMN_G_A_S_STATUS_2 = "journalArticle.status = ?";
	private static final String _FINDER_COLUMN_G_UT_S_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_UT_S_URLTITLE_1 = "journalArticle.urlTitle IS NULL AND ";
	private static final String _FINDER_COLUMN_G_UT_S_URLTITLE_2 = "journalArticle.urlTitle = ? AND ";
	private static final String _FINDER_COLUMN_G_UT_S_URLTITLE_3 = "(journalArticle.urlTitle IS NULL OR journalArticle.urlTitle = ?) AND ";
	private static final String _FINDER_COLUMN_G_UT_S_STATUS_2 = "journalArticle.status = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "journalArticle.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No JournalArticle exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No JournalArticle exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(JournalArticlePersistenceImpl.class);
}