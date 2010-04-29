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

package com.liferay.portlet.blogs.service.persistence;

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
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.OrganizationPersistence;
import com.liferay.portal.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagPersistence;
import com.liferay.portlet.blogs.NoSuchEntryException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.impl.BlogsEntryImpl;
import com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <a href="BlogsEntryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsEntryPersistence
 * @see       BlogsEntryUtil
 * @generated
 */
public class BlogsEntryPersistenceImpl extends BasePersistenceImpl<BlogsEntry>
	implements BlogsEntryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = BlogsEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_U = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_U = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_U = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_D = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_D",
			new String[] { Long.class.getName(), Date.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_D = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_D",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_D = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_D",
			new String[] { Long.class.getName(), Date.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_UT = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_UT",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_UT = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_UT",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_D = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_D",
			new String[] { Long.class.getName(), Date.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_D = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_D",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_D = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_D",
			new String[] { Long.class.getName(), Date.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_U_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_U_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_U_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_C_D_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_D_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_D_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_D_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_D_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_D_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_U_D = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U_D",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_U_D = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U_D",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_D = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_U_D",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_U_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_U_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_D_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_D_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_D_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_D_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_D_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_D_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_U_D_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U_D_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_U_D_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U_D_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_D_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_U_D_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(BlogsEntry blogsEntry) {
		EntityCacheUtil.putResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryImpl.class, blogsEntry.getPrimaryKey(), blogsEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { blogsEntry.getUuid(), new Long(
					blogsEntry.getGroupId()) }, blogsEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT,
			new Object[] {
				new Long(blogsEntry.getGroupId()),
				
			blogsEntry.getUrlTitle()
			}, blogsEntry);
	}

	public void cacheResult(List<BlogsEntry> blogsEntries) {
		for (BlogsEntry blogsEntry : blogsEntries) {
			if (EntityCacheUtil.getResult(
						BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
						BlogsEntryImpl.class, blogsEntry.getPrimaryKey(), this) == null) {
				cacheResult(blogsEntry);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(BlogsEntryImpl.class.getName());
		EntityCacheUtil.clearCache(BlogsEntryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public BlogsEntry create(long entryId) {
		BlogsEntry blogsEntry = new BlogsEntryImpl();

		blogsEntry.setNew(true);
		blogsEntry.setPrimaryKey(entryId);

		String uuid = PortalUUIDUtil.generate();

		blogsEntry.setUuid(uuid);

		return blogsEntry;
	}

	public BlogsEntry remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public BlogsEntry remove(long entryId)
		throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BlogsEntry blogsEntry = (BlogsEntry)session.get(BlogsEntryImpl.class,
					new Long(entryId));

			if (blogsEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + entryId);
				}

				throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					entryId);
			}

			return remove(blogsEntry);
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

	public BlogsEntry remove(BlogsEntry blogsEntry) throws SystemException {
		for (ModelListener<BlogsEntry> listener : listeners) {
			listener.onBeforeRemove(blogsEntry);
		}

		blogsEntry = removeImpl(blogsEntry);

		for (ModelListener<BlogsEntry> listener : listeners) {
			listener.onAfterRemove(blogsEntry);
		}

		return blogsEntry;
	}

	protected BlogsEntry removeImpl(BlogsEntry blogsEntry)
		throws SystemException {
		blogsEntry = toUnwrappedModel(blogsEntry);

		Session session = null;

		try {
			session = openSession();

			if (blogsEntry.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(BlogsEntryImpl.class,
						blogsEntry.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(blogsEntry);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		BlogsEntryModelImpl blogsEntryModelImpl = (BlogsEntryModelImpl)blogsEntry;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				blogsEntryModelImpl.getOriginalUuid(),
				new Long(blogsEntryModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_UT,
			new Object[] {
				new Long(blogsEntryModelImpl.getOriginalGroupId()),
				
			blogsEntryModelImpl.getOriginalUrlTitle()
			});

		EntityCacheUtil.removeResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryImpl.class, blogsEntry.getPrimaryKey());

		return blogsEntry;
	}

	public BlogsEntry updateImpl(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry, boolean merge)
		throws SystemException {
		blogsEntry = toUnwrappedModel(blogsEntry);

		boolean isNew = blogsEntry.isNew();

		BlogsEntryModelImpl blogsEntryModelImpl = (BlogsEntryModelImpl)blogsEntry;

		if (Validator.isNull(blogsEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			blogsEntry.setUuid(uuid);
		}

		long userId = GetterUtil.getLong(PrincipalThreadLocal.getName());

		if (userId > 0) {
			long companyId = blogsEntry.getCompanyId();

			long groupId = blogsEntry.getGroupId();

			long entryId = 0;

			if (!isNew) {
				entryId = blogsEntry.getPrimaryKey();
			}

			try {
				blogsEntry.setTitle(SanitizerUtil.sanitize(companyId, groupId,
						userId,
						com.liferay.portlet.blogs.model.BlogsEntry.class.getName(),
						entryId, ContentTypes.TEXT_PLAIN, Sanitizer.MODE_ALL,
						blogsEntry.getTitle(), null));

				blogsEntry.setContent(SanitizerUtil.sanitize(companyId,
						groupId, userId,
						com.liferay.portlet.blogs.model.BlogsEntry.class.getName(),
						entryId, ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
						blogsEntry.getContent(), null));
			}
			catch (SanitizerException se) {
				throw new SystemException(se);
			}
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, blogsEntry, merge);

			blogsEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryImpl.class, blogsEntry.getPrimaryKey(), blogsEntry);

		if (!isNew &&
				(!Validator.equals(blogsEntry.getUuid(),
					blogsEntryModelImpl.getOriginalUuid()) ||
				(blogsEntry.getGroupId() != blogsEntryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					blogsEntryModelImpl.getOriginalUuid(),
					new Long(blogsEntryModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(blogsEntry.getUuid(),
					blogsEntryModelImpl.getOriginalUuid()) ||
				(blogsEntry.getGroupId() != blogsEntryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					blogsEntry.getUuid(), new Long(blogsEntry.getGroupId())
				}, blogsEntry);
		}

		if (!isNew &&
				((blogsEntry.getGroupId() != blogsEntryModelImpl.getOriginalGroupId()) ||
				!Validator.equals(blogsEntry.getUrlTitle(),
					blogsEntryModelImpl.getOriginalUrlTitle()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_UT,
				new Object[] {
					new Long(blogsEntryModelImpl.getOriginalGroupId()),
					
				blogsEntryModelImpl.getOriginalUrlTitle()
				});
		}

		if (isNew ||
				((blogsEntry.getGroupId() != blogsEntryModelImpl.getOriginalGroupId()) ||
				!Validator.equals(blogsEntry.getUrlTitle(),
					blogsEntryModelImpl.getOriginalUrlTitle()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT,
				new Object[] {
					new Long(blogsEntry.getGroupId()),
					
				blogsEntry.getUrlTitle()
				}, blogsEntry);
		}

		return blogsEntry;
	}

	protected BlogsEntry toUnwrappedModel(BlogsEntry blogsEntry) {
		if (blogsEntry instanceof BlogsEntryImpl) {
			return blogsEntry;
		}

		BlogsEntryImpl blogsEntryImpl = new BlogsEntryImpl();

		blogsEntryImpl.setNew(blogsEntry.isNew());
		blogsEntryImpl.setPrimaryKey(blogsEntry.getPrimaryKey());

		blogsEntryImpl.setUuid(blogsEntry.getUuid());
		blogsEntryImpl.setEntryId(blogsEntry.getEntryId());
		blogsEntryImpl.setGroupId(blogsEntry.getGroupId());
		blogsEntryImpl.setCompanyId(blogsEntry.getCompanyId());
		blogsEntryImpl.setUserId(blogsEntry.getUserId());
		blogsEntryImpl.setUserName(blogsEntry.getUserName());
		blogsEntryImpl.setCreateDate(blogsEntry.getCreateDate());
		blogsEntryImpl.setModifiedDate(blogsEntry.getModifiedDate());
		blogsEntryImpl.setTitle(blogsEntry.getTitle());
		blogsEntryImpl.setUrlTitle(blogsEntry.getUrlTitle());
		blogsEntryImpl.setContent(blogsEntry.getContent());
		blogsEntryImpl.setDisplayDate(blogsEntry.getDisplayDate());
		blogsEntryImpl.setAllowPingbacks(blogsEntry.isAllowPingbacks());
		blogsEntryImpl.setAllowTrackbacks(blogsEntry.isAllowTrackbacks());
		blogsEntryImpl.setTrackbacks(blogsEntry.getTrackbacks());
		blogsEntryImpl.setStatus(blogsEntry.getStatus());
		blogsEntryImpl.setStatusByUserId(blogsEntry.getStatusByUserId());
		blogsEntryImpl.setStatusByUserName(blogsEntry.getStatusByUserName());
		blogsEntryImpl.setStatusDate(blogsEntry.getStatusDate());

		return blogsEntryImpl;
	}

	public BlogsEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public BlogsEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = fetchByPrimaryKey(entryId);

		if (blogsEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + entryId);
			}

			throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				entryId);
		}

		return blogsEntry;
	}

	public BlogsEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public BlogsEntry fetchByPrimaryKey(long entryId) throws SystemException {
		BlogsEntry blogsEntry = (BlogsEntry)EntityCacheUtil.getResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
				BlogsEntryImpl.class, entryId, this);

		if (blogsEntry == null) {
			Session session = null;

			try {
				session = openSession();

				blogsEntry = (BlogsEntry)session.get(BlogsEntryImpl.class,
						new Long(entryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (blogsEntry != null) {
					cacheResult(blogsEntry);
				}

				closeSession(session);
			}
		}

		return blogsEntry;
	}

	public List<BlogsEntry> findByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

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

				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);

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
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<BlogsEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
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

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

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
					query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByUuid(uuid);

		List<BlogsEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByUuid_PrevAndNext(long entryId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, blogsEntry, uuid,
					orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByUuid_PrevAndNext(session, blogsEntry, uuid,
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

	protected BlogsEntry getByUuid_PrevAndNext(Session session,
		BlogsEntry blogsEntry, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public BlogsEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = fetchByUUID_G(uuid, groupId);

		if (blogsEntry == null) {
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

			throw new NoSuchEntryException(msg.toString());
		}

		return blogsEntry;
	}

	public BlogsEntry fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public BlogsEntry fetchByUUID_G(String uuid, long groupId,
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

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

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

				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<BlogsEntry> list = q.list();

				result = list;

				BlogsEntry blogsEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					blogsEntry = list.get(0);

					cacheResult(blogsEntry);

					if ((blogsEntry.getUuid() == null) ||
							!blogsEntry.getUuid().equals(uuid) ||
							(blogsEntry.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, blogsEntry);
					}
				}

				return blogsEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<BlogsEntry>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (BlogsEntry)result;
			}
		}
	}

	public List<BlogsEntry> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);

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
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<BlogsEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
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

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByGroupId(groupId);

		List<BlogsEntry> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByGroupId_PrevAndNext(long entryId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, blogsEntry, groupId,
					orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByGroupId_PrevAndNext(session, blogsEntry, groupId,
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

	protected BlogsEntry getByGroupId_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<BlogsEntry> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);

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
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<BlogsEntry> findByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
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

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByCompanyId(companyId);

		List<BlogsEntry> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByCompanyId_PrevAndNext(long entryId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, blogsEntry,
					companyId, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByCompanyId_PrevAndNext(session, blogsEntry,
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

	protected BlogsEntry getByCompanyId_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<BlogsEntry> findByC_U(long companyId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), new Long(userId) };

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_U,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_U_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_U_USERID_2);

				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_U, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByC_U(long companyId, long userId, int start,
		int end) throws SystemException {
		return findByC_U(companyId, userId, start, end, null);
	}

	public List<BlogsEntry> findByC_U(long companyId, long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(userId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_U,
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

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_U_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_U_USERID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_U,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByC_U_First(long companyId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByC_U(companyId, userId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByC_U_Last(long companyId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByC_U(companyId, userId);

		List<BlogsEntry> list = findByC_U(companyId, userId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByC_U_PrevAndNext(long entryId, long companyId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByC_U_PrevAndNext(session, blogsEntry, companyId,
					userId, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByC_U_PrevAndNext(session, blogsEntry, companyId,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByC_U_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_U_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_U_USERID_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<BlogsEntry> findByC_D(long companyId, Date displayDate)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), displayDate };

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_D,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_D_COMPANYID_2);

				if (displayDate == null) {
					query.append(_FINDER_COLUMN_C_D_DISPLAYDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_C_D_DISPLAYDATE_2);
				}

				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_D, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByC_D(long companyId, Date displayDate,
		int start, int end) throws SystemException {
		return findByC_D(companyId, displayDate, start, end, null);
	}

	public List<BlogsEntry> findByC_D(long companyId, Date displayDate,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				displayDate,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_D,
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

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_D_COMPANYID_2);

				if (displayDate == null) {
					query.append(_FINDER_COLUMN_C_D_DISPLAYDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_C_D_DISPLAYDATE_2);
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_D,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByC_D_First(long companyId, Date displayDate,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByC_D(companyId, displayDate, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByC_D_Last(long companyId, Date displayDate,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByC_D(companyId, displayDate);

		List<BlogsEntry> list = findByC_D(companyId, displayDate, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByC_D_PrevAndNext(long entryId, long companyId,
		Date displayDate, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByC_D_PrevAndNext(session, blogsEntry, companyId,
					displayDate, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByC_D_PrevAndNext(session, blogsEntry, companyId,
					displayDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByC_D_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId, Date displayDate,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_D_COMPANYID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_C_D_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_C_D_DISPLAYDATE_2);
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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (displayDate != null) {
			qPos.add(CalendarUtil.getTimestamp(displayDate));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<BlogsEntry> findByC_S(long companyId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(status)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_S_STATUS_2);

				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);

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
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_S, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByC_S(long companyId, int status, int start,
		int end) throws SystemException {
		return findByC_S(companyId, status, start, end, null);
	}

	public List<BlogsEntry> findByC_S(long companyId, int status, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(status),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_S,
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

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByC_S_First(long companyId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByC_S(companyId, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByC_S_Last(long companyId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByC_S(companyId, status);

		List<BlogsEntry> list = findByC_S(companyId, status, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByC_S_PrevAndNext(long entryId, long companyId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByC_S_PrevAndNext(session, blogsEntry, companyId,
					status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByC_S_PrevAndNext(session, blogsEntry, companyId,
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

	protected BlogsEntry getByC_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public BlogsEntry findByG_UT(long groupId, String urlTitle)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = fetchByG_UT(groupId, urlTitle);

		if (blogsEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", urlTitle=");
			msg.append(urlTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return blogsEntry;
	}

	public BlogsEntry fetchByG_UT(long groupId, String urlTitle)
		throws SystemException {
		return fetchByG_UT(groupId, urlTitle, true);
	}

	public BlogsEntry fetchByG_UT(long groupId, String urlTitle,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), urlTitle };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_UT,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

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

				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (urlTitle != null) {
					qPos.add(urlTitle);
				}

				List<BlogsEntry> list = q.list();

				result = list;

				BlogsEntry blogsEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT,
						finderArgs, list);
				}
				else {
					blogsEntry = list.get(0);

					cacheResult(blogsEntry);

					if ((blogsEntry.getGroupId() != groupId) ||
							(blogsEntry.getUrlTitle() == null) ||
							!blogsEntry.getUrlTitle().equals(urlTitle)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT,
							finderArgs, blogsEntry);
					}
				}

				return blogsEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT,
						finderArgs, new ArrayList<BlogsEntry>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (BlogsEntry)result;
			}
		}
	}

	public List<BlogsEntry> findByG_D(long groupId, Date displayDate)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), displayDate };

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_D,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_D_GROUPID_2);

				if (displayDate == null) {
					query.append(_FINDER_COLUMN_G_D_DISPLAYDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_G_D_DISPLAYDATE_2);
				}

				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_D, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByG_D(long groupId, Date displayDate,
		int start, int end) throws SystemException {
		return findByG_D(groupId, displayDate, start, end, null);
	}

	public List<BlogsEntry> findByG_D(long groupId, Date displayDate,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				displayDate,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_D,
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

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_D_GROUPID_2);

				if (displayDate == null) {
					query.append(_FINDER_COLUMN_G_D_DISPLAYDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_G_D_DISPLAYDATE_2);
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_D,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByG_D_First(long groupId, Date displayDate,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByG_D(groupId, displayDate, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByG_D_Last(long groupId, Date displayDate,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByG_D(groupId, displayDate);

		List<BlogsEntry> list = findByG_D(groupId, displayDate, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByG_D_PrevAndNext(long entryId, long groupId,
		Date displayDate, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_D_PrevAndNext(session, blogsEntry, groupId,
					displayDate, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_D_PrevAndNext(session, blogsEntry, groupId,
					displayDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByG_D_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, Date displayDate,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_D_GROUPID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_D_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_G_D_DISPLAYDATE_2);
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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (displayDate != null) {
			qPos.add(CalendarUtil.getTimestamp(displayDate));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<BlogsEntry> findByG_S(long groupId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(status)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_S_GROUPID_2);

				query.append(_FINDER_COLUMN_G_S_STATUS_2);

				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_S, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByG_S(long groupId, int status, int start,
		int end) throws SystemException {
		return findByG_S(groupId, status, start, end, null);
	}

	public List<BlogsEntry> findByG_S(long groupId, int status, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(status),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_S,
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

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_S_GROUPID_2);

				query.append(_FINDER_COLUMN_G_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByG_S_First(long groupId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByG_S(groupId, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByG_S_Last(long groupId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByG_S(groupId, status);

		List<BlogsEntry> list = findByG_S(groupId, status, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByG_S_PrevAndNext(long entryId, long groupId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_S_PrevAndNext(session, blogsEntry, groupId,
					status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_S_PrevAndNext(session, blogsEntry, groupId,
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

	protected BlogsEntry getByG_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<BlogsEntry> findByC_U_S(long companyId, long userId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(userId), new Integer(status)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_U_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_U_S_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_U_S_USERID_2);

				query.append(_FINDER_COLUMN_C_U_S_STATUS_2);

				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_U_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByC_U_S(long companyId, long userId,
		int status, int start, int end) throws SystemException {
		return findByC_U_S(companyId, userId, status, start, end, null);
	}

	public List<BlogsEntry> findByC_U_S(long companyId, long userId,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(userId), new Integer(status),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_U_S,
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

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_U_S_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_U_S_USERID_2);

				query.append(_FINDER_COLUMN_C_U_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(status);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_U_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByC_U_S_First(long companyId, long userId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByC_U_S(companyId, userId, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByC_U_S_Last(long companyId, long userId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByC_U_S(companyId, userId, status);

		List<BlogsEntry> list = findByC_U_S(companyId, userId, status,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByC_U_S_PrevAndNext(long entryId, long companyId,
		long userId, int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByC_U_S_PrevAndNext(session, blogsEntry, companyId,
					userId, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByC_U_S_PrevAndNext(session, blogsEntry, companyId,
					userId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByC_U_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId, long userId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_U_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_U_S_USERID_2);

		query.append(_FINDER_COLUMN_C_U_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(userId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<BlogsEntry> findByC_D_S(long companyId, Date displayDate,
		int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				displayDate, new Integer(status)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_D_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_D_S_COMPANYID_2);

				if (displayDate == null) {
					query.append(_FINDER_COLUMN_C_D_S_DISPLAYDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_C_D_S_DISPLAYDATE_2);
				}

				query.append(_FINDER_COLUMN_C_D_S_STATUS_2);

				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_D_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByC_D_S(long companyId, Date displayDate,
		int status, int start, int end) throws SystemException {
		return findByC_D_S(companyId, displayDate, status, start, end, null);
	}

	public List<BlogsEntry> findByC_D_S(long companyId, Date displayDate,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				displayDate, new Integer(status),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_D_S,
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

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_D_S_COMPANYID_2);

				if (displayDate == null) {
					query.append(_FINDER_COLUMN_C_D_S_DISPLAYDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_C_D_S_DISPLAYDATE_2);
				}

				query.append(_FINDER_COLUMN_C_D_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(status);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_D_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByC_D_S_First(long companyId, Date displayDate,
		int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByC_D_S(companyId, displayDate, status, 0,
				1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByC_D_S_Last(long companyId, Date displayDate,
		int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByC_D_S(companyId, displayDate, status);

		List<BlogsEntry> list = findByC_D_S(companyId, displayDate, status,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByC_D_S_PrevAndNext(long entryId, long companyId,
		Date displayDate, int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByC_D_S_PrevAndNext(session, blogsEntry, companyId,
					displayDate, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByC_D_S_PrevAndNext(session, blogsEntry, companyId,
					displayDate, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByC_D_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId, Date displayDate, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_D_S_COMPANYID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_C_D_S_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_C_D_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_C_D_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (displayDate != null) {
			qPos.add(CalendarUtil.getTimestamp(displayDate));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<BlogsEntry> findByG_U_D(long groupId, long userId,
		Date displayDate) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				displayDate
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U_D,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_U_D_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_D_USERID_2);

				if (displayDate == null) {
					query.append(_FINDER_COLUMN_G_U_D_DISPLAYDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_G_U_D_DISPLAYDATE_2);
				}

				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U_D,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByG_U_D(long groupId, long userId,
		Date displayDate, int start, int end) throws SystemException {
		return findByG_U_D(groupId, userId, displayDate, start, end, null);
	}

	public List<BlogsEntry> findByG_U_D(long groupId, long userId,
		Date displayDate, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				displayDate,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_U_D,
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

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_U_D_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_D_USERID_2);

				if (displayDate == null) {
					query.append(_FINDER_COLUMN_G_U_D_DISPLAYDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_G_U_D_DISPLAYDATE_2);
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_U_D,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByG_U_D_First(long groupId, long userId,
		Date displayDate, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByG_U_D(groupId, userId, displayDate, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByG_U_D_Last(long groupId, long userId,
		Date displayDate, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByG_U_D(groupId, userId, displayDate);

		List<BlogsEntry> list = findByG_U_D(groupId, userId, displayDate,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByG_U_D_PrevAndNext(long entryId, long groupId,
		long userId, Date displayDate, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_U_D_PrevAndNext(session, blogsEntry, groupId,
					userId, displayDate, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_U_D_PrevAndNext(session, blogsEntry, groupId,
					userId, displayDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByG_U_D_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, long userId, Date displayDate,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_D_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_D_USERID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_D_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_G_U_D_DISPLAYDATE_2);
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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (displayDate != null) {
			qPos.add(CalendarUtil.getTimestamp(displayDate));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<BlogsEntry> findByG_U_S(long groupId, long userId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId), new Integer(status)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_S_USERID_2);

				query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByG_U_S(long groupId, long userId, int status,
		int start, int end) throws SystemException {
		return findByG_U_S(groupId, userId, status, start, end, null);
	}

	public List<BlogsEntry> findByG_U_S(long groupId, long userId, int status,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId), new Integer(status),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_U_S,
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

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_S_USERID_2);

				query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(status);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_U_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByG_U_S_First(long groupId, long userId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByG_U_S(groupId, userId, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByG_U_S_Last(long groupId, long userId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByG_U_S(groupId, userId, status);

		List<BlogsEntry> list = findByG_U_S(groupId, userId, status, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByG_U_S_PrevAndNext(long entryId, long groupId,
		long userId, int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_U_S_PrevAndNext(session, blogsEntry, groupId,
					userId, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_U_S_PrevAndNext(session, blogsEntry, groupId,
					userId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByG_U_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, long userId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_S_USERID_2);

		query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<BlogsEntry> findByG_D_S(long groupId, Date displayDate,
		int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				displayDate, new Integer(status)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_D_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_D_S_GROUPID_2);

				if (displayDate == null) {
					query.append(_FINDER_COLUMN_G_D_S_DISPLAYDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_G_D_S_DISPLAYDATE_2);
				}

				query.append(_FINDER_COLUMN_G_D_S_STATUS_2);

				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_D_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByG_D_S(long groupId, Date displayDate,
		int status, int start, int end) throws SystemException {
		return findByG_D_S(groupId, displayDate, status, start, end, null);
	}

	public List<BlogsEntry> findByG_D_S(long groupId, Date displayDate,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				displayDate, new Integer(status),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_D_S,
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

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_D_S_GROUPID_2);

				if (displayDate == null) {
					query.append(_FINDER_COLUMN_G_D_S_DISPLAYDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_G_D_S_DISPLAYDATE_2);
				}

				query.append(_FINDER_COLUMN_G_D_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(status);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_D_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByG_D_S_First(long groupId, Date displayDate,
		int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByG_D_S(groupId, displayDate, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByG_D_S_Last(long groupId, Date displayDate,
		int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByG_D_S(groupId, displayDate, status);

		List<BlogsEntry> list = findByG_D_S(groupId, displayDate, status,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByG_D_S_PrevAndNext(long entryId, long groupId,
		Date displayDate, int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_D_S_PrevAndNext(session, blogsEntry, groupId,
					displayDate, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_D_S_PrevAndNext(session, blogsEntry, groupId,
					displayDate, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByG_D_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, Date displayDate, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_D_S_GROUPID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_D_S_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_G_D_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_D_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (displayDate != null) {
			qPos.add(CalendarUtil.getTimestamp(displayDate));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<BlogsEntry> findByG_U_D_S(long groupId, long userId,
		Date displayDate, int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				displayDate, new Integer(status)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U_D_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_U_D_S_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_D_S_USERID_2);

				if (displayDate == null) {
					query.append(_FINDER_COLUMN_G_U_D_S_DISPLAYDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_G_U_D_S_DISPLAYDATE_2);
				}

				query.append(_FINDER_COLUMN_G_U_D_S_STATUS_2);

				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U_D_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<BlogsEntry> findByG_U_D_S(long groupId, long userId,
		Date displayDate, int status, int start, int end)
		throws SystemException {
		return findByG_U_D_S(groupId, userId, displayDate, status, start, end,
			null);
	}

	public List<BlogsEntry> findByG_U_D_S(long groupId, long userId,
		Date displayDate, int status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				displayDate, new Integer(status),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_U_D_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(6 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(6);
				}

				query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_U_D_S_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_D_S_USERID_2);

				if (displayDate == null) {
					query.append(_FINDER_COLUMN_G_U_D_S_DISPLAYDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_G_U_D_S_DISPLAYDATE_2);
				}

				query.append(_FINDER_COLUMN_G_U_D_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
				}

				qPos.add(status);

				list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_U_D_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public BlogsEntry findByG_U_D_S_First(long groupId, long userId,
		Date displayDate, int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		List<BlogsEntry> list = findByG_U_D_S(groupId, userId, displayDate,
				status, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry findByG_U_D_S_Last(long groupId, long userId,
		Date displayDate, int status, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		int count = countByG_U_D_S(groupId, userId, displayDate, status);

		List<BlogsEntry> list = findByG_U_D_S(groupId, userId, displayDate,
				status, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", displayDate=");
			msg.append(displayDate);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsEntry[] findByG_U_D_S_PrevAndNext(long entryId, long groupId,
		long userId, Date displayDate, int status,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_U_D_S_PrevAndNext(session, blogsEntry, groupId,
					userId, displayDate, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_U_D_S_PrevAndNext(session, blogsEntry, groupId,
					userId, displayDate, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByG_U_D_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, long userId, Date displayDate,
		int status, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_D_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_D_S_USERID_2);

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_D_S_DISPLAYDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_G_U_D_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_U_D_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (displayDate != null) {
			qPos.add(CalendarUtil.getTimestamp(displayDate));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<BlogsEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<BlogsEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<BlogsEntry> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_BLOGSENTRY);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_BLOGSENTRY.concat(BlogsEntryModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BlogsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (BlogsEntry blogsEntry : findByUuid(uuid)) {
			remove(blogsEntry);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByUUID_G(uuid, groupId);

		remove(blogsEntry);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (BlogsEntry blogsEntry : findByGroupId(groupId)) {
			remove(blogsEntry);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (BlogsEntry blogsEntry : findByCompanyId(companyId)) {
			remove(blogsEntry);
		}
	}

	public void removeByC_U(long companyId, long userId)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByC_U(companyId, userId)) {
			remove(blogsEntry);
		}
	}

	public void removeByC_D(long companyId, Date displayDate)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByC_D(companyId, displayDate)) {
			remove(blogsEntry);
		}
	}

	public void removeByC_S(long companyId, int status)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByC_S(companyId, status)) {
			remove(blogsEntry);
		}
	}

	public void removeByG_UT(long groupId, String urlTitle)
		throws NoSuchEntryException, SystemException {
		BlogsEntry blogsEntry = findByG_UT(groupId, urlTitle);

		remove(blogsEntry);
	}

	public void removeByG_D(long groupId, Date displayDate)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByG_D(groupId, displayDate)) {
			remove(blogsEntry);
		}
	}

	public void removeByG_S(long groupId, int status) throws SystemException {
		for (BlogsEntry blogsEntry : findByG_S(groupId, status)) {
			remove(blogsEntry);
		}
	}

	public void removeByC_U_S(long companyId, long userId, int status)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByC_U_S(companyId, userId, status)) {
			remove(blogsEntry);
		}
	}

	public void removeByC_D_S(long companyId, Date displayDate, int status)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByC_D_S(companyId, displayDate, status)) {
			remove(blogsEntry);
		}
	}

	public void removeByG_U_D(long groupId, long userId, Date displayDate)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByG_U_D(groupId, userId, displayDate)) {
			remove(blogsEntry);
		}
	}

	public void removeByG_U_S(long groupId, long userId, int status)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByG_U_S(groupId, userId, status)) {
			remove(blogsEntry);
		}
	}

	public void removeByG_D_S(long groupId, Date displayDate, int status)
		throws SystemException {
		for (BlogsEntry blogsEntry : findByG_D_S(groupId, displayDate, status)) {
			remove(blogsEntry);
		}
	}

	public void removeByG_U_D_S(long groupId, long userId, Date displayDate,
		int status) throws SystemException {
		for (BlogsEntry blogsEntry : findByG_U_D_S(groupId, userId,
				displayDate, status)) {
			remove(blogsEntry);
		}
	}

	public void removeAll() throws SystemException {
		for (BlogsEntry blogsEntry : findAll()) {
			remove(blogsEntry);
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

				query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

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

				query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

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

				query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

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

				query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

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

	public int countByC_U(long companyId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_U,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_U_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_U_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_D(long companyId, Date displayDate)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), displayDate };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_D,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_D_COMPANYID_2);

				if (displayDate == null) {
					query.append(_FINDER_COLUMN_C_D_DISPLAYDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_C_D_DISPLAYDATE_2);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_D, finderArgs,
					count);

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

				query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

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

				query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

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

	public int countByG_D(long groupId, Date displayDate)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), displayDate };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_D,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_D_GROUPID_2);

				if (displayDate == null) {
					query.append(_FINDER_COLUMN_G_D_DISPLAYDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_G_D_DISPLAYDATE_2);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_D, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_S(long groupId, int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_S_GROUPID_2);

				query.append(_FINDER_COLUMN_G_S_STATUS_2);

				String sql = query.toString();

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_U_S(long companyId, long userId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(userId), new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_U_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_U_S_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_U_S_USERID_2);

				query.append(_FINDER_COLUMN_C_U_S_STATUS_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_U_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_D_S(long companyId, Date displayDate, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				displayDate, new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_D_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_C_D_S_COMPANYID_2);

				if (displayDate == null) {
					query.append(_FINDER_COLUMN_C_D_S_DISPLAYDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_C_D_S_DISPLAYDATE_2);
				}

				query.append(_FINDER_COLUMN_C_D_S_STATUS_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_D_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_U_D(long groupId, long userId, Date displayDate)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				displayDate
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U_D,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_U_D_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_D_USERID_2);

				if (displayDate == null) {
					query.append(_FINDER_COLUMN_G_U_D_DISPLAYDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_G_U_D_DISPLAYDATE_2);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U_D,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_U_S(long groupId, long userId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId), new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_S_USERID_2);

				query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_D_S(long groupId, Date displayDate, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				displayDate, new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_D_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_D_S_GROUPID_2);

				if (displayDate == null) {
					query.append(_FINDER_COLUMN_G_D_S_DISPLAYDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_G_D_S_DISPLAYDATE_2);
				}

				query.append(_FINDER_COLUMN_G_D_S_STATUS_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_D_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_U_D_S(long groupId, long userId, Date displayDate,
		int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				displayDate, new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U_D_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_U_D_S_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_D_S_USERID_2);

				if (displayDate == null) {
					query.append(_FINDER_COLUMN_G_U_D_S_DISPLAYDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_G_U_D_S_DISPLAYDATE_2);
				}

				query.append(_FINDER_COLUMN_G_U_D_S_STATUS_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (displayDate != null) {
					qPos.add(CalendarUtil.getTimestamp(displayDate));
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U_D_S,
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

				Query q = session.createQuery(_SQL_COUNT_BLOGSENTRY);

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
						"value.object.listener.com.liferay.portlet.blogs.model.BlogsEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<BlogsEntry>> listenersList = new ArrayList<ModelListener<BlogsEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<BlogsEntry>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = BlogsEntryPersistence.class)
	protected BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(type = BlogsStatsUserPersistence.class)
	protected BlogsStatsUserPersistence blogsStatsUserPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
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
	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	private static final String _SQL_SELECT_BLOGSENTRY = "SELECT blogsEntry FROM BlogsEntry blogsEntry";
	private static final String _SQL_SELECT_BLOGSENTRY_WHERE = "SELECT blogsEntry FROM BlogsEntry blogsEntry WHERE ";
	private static final String _SQL_COUNT_BLOGSENTRY = "SELECT COUNT(blogsEntry) FROM BlogsEntry blogsEntry";
	private static final String _SQL_COUNT_BLOGSENTRY_WHERE = "SELECT COUNT(blogsEntry) FROM BlogsEntry blogsEntry WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "blogsEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "blogsEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(blogsEntry.uuid IS NULL OR blogsEntry.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "blogsEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "blogsEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(blogsEntry.uuid IS NULL OR blogsEntry.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "blogsEntry.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "blogsEntry.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "blogsEntry.companyId = ?";
	private static final String _FINDER_COLUMN_C_U_COMPANYID_2 = "blogsEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_USERID_2 = "blogsEntry.userId = ?";
	private static final String _FINDER_COLUMN_C_D_COMPANYID_2 = "blogsEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_D_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL";
	private static final String _FINDER_COLUMN_C_D_DISPLAYDATE_2 = "blogsEntry.displayDate < ?";
	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 = "blogsEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_S_STATUS_2 = "blogsEntry.status = ?";
	private static final String _FINDER_COLUMN_G_UT_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_UT_URLTITLE_1 = "blogsEntry.urlTitle IS NULL";
	private static final String _FINDER_COLUMN_G_UT_URLTITLE_2 = "blogsEntry.urlTitle = ?";
	private static final String _FINDER_COLUMN_G_UT_URLTITLE_3 = "(blogsEntry.urlTitle IS NULL OR blogsEntry.urlTitle = ?)";
	private static final String _FINDER_COLUMN_G_D_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_D_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL";
	private static final String _FINDER_COLUMN_G_D_DISPLAYDATE_2 = "blogsEntry.displayDate < ?";
	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STATUS_2 = "blogsEntry.status = ?";
	private static final String _FINDER_COLUMN_C_U_S_COMPANYID_2 = "blogsEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_S_USERID_2 = "blogsEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_S_STATUS_2 = "blogsEntry.status = ?";
	private static final String _FINDER_COLUMN_C_D_S_COMPANYID_2 = "blogsEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_D_S_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL AND ";
	private static final String _FINDER_COLUMN_C_D_S_DISPLAYDATE_2 = "blogsEntry.displayDate < ? AND ";
	private static final String _FINDER_COLUMN_C_D_S_STATUS_2 = "blogsEntry.status = ?";
	private static final String _FINDER_COLUMN_G_U_D_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_D_USERID_2 = "blogsEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_D_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL";
	private static final String _FINDER_COLUMN_G_U_D_DISPLAYDATE_2 = "blogsEntry.displayDate < ?";
	private static final String _FINDER_COLUMN_G_U_S_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_S_USERID_2 = "blogsEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_S_STATUS_2 = "blogsEntry.status = ?";
	private static final String _FINDER_COLUMN_G_D_S_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_D_S_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL AND ";
	private static final String _FINDER_COLUMN_G_D_S_DISPLAYDATE_2 = "blogsEntry.displayDate < ? AND ";
	private static final String _FINDER_COLUMN_G_D_S_STATUS_2 = "blogsEntry.status = ?";
	private static final String _FINDER_COLUMN_G_U_D_S_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_D_S_USERID_2 = "blogsEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_D_S_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL AND ";
	private static final String _FINDER_COLUMN_G_U_D_S_DISPLAYDATE_2 = "blogsEntry.displayDate < ? AND ";
	private static final String _FINDER_COLUMN_G_U_D_S_STATUS_2 = "blogsEntry.status = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "blogsEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No BlogsEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No BlogsEntry exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(BlogsEntryPersistenceImpl.class);
}