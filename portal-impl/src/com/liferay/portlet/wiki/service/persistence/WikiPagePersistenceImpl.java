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

package com.liferay.portlet.wiki.service.persistence;

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
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.impl.WikiPageImpl;
import com.liferay.portlet.wiki.model.impl.WikiPageModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="WikiPagePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiPagePersistence
 * @see       WikiPageUtil
 * @generated
 */
public class WikiPagePersistenceImpl extends BasePersistenceImpl<WikiPage>
	implements WikiPagePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = WikiPageImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_NODEID = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByNodeId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_NODEID = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByNodeId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_FORMAT = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByFormat",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_FORMAT = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByFormat", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_N_T = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByN_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_N_T = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByN_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_N_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByN_H",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_N_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByN_H",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_N_P = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByN_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_N_P = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByN_P",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_N_R = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByN_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_N_R = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByN_R",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_N_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByN_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_N_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByN_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_U_N_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByU_N_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_N_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByU_N_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_N_T_V = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByN_T_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_N_T_V = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByN_T_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_N_T_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByN_T_H",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_N_T_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByN_T_H",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_N_T_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByN_T_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_N_T_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByN_T_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_N_H_P = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByN_H_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_N_H_P = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByN_H_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(WikiPage wikiPage) {
		EntityCacheUtil.putResult(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageImpl.class, wikiPage.getPrimaryKey(), wikiPage);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { wikiPage.getUuid(), new Long(wikiPage.getGroupId()) },
			wikiPage);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_T_V,
			new Object[] {
				new Long(wikiPage.getNodeId()),
				
			wikiPage.getTitle(), new Double(wikiPage.getVersion())
			}, wikiPage);
	}

	public void cacheResult(List<WikiPage> wikiPages) {
		for (WikiPage wikiPage : wikiPages) {
			if (EntityCacheUtil.getResult(
						WikiPageModelImpl.ENTITY_CACHE_ENABLED,
						WikiPageImpl.class, wikiPage.getPrimaryKey(), this) == null) {
				cacheResult(wikiPage);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(WikiPageImpl.class.getName());
		EntityCacheUtil.clearCache(WikiPageImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(WikiPage wikiPage) {
		EntityCacheUtil.removeResult(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageImpl.class, wikiPage.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { wikiPage.getUuid(), new Long(wikiPage.getGroupId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_N_T_V,
			new Object[] {
				new Long(wikiPage.getNodeId()),
				
			wikiPage.getTitle(), new Double(wikiPage.getVersion())
			});
	}

	public WikiPage create(long pageId) {
		WikiPage wikiPage = new WikiPageImpl();

		wikiPage.setNew(true);
		wikiPage.setPrimaryKey(pageId);

		String uuid = PortalUUIDUtil.generate();

		wikiPage.setUuid(uuid);

		return wikiPage;
	}

	public WikiPage remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public WikiPage remove(long pageId)
		throws NoSuchPageException, SystemException {
		Session session = null;

		try {
			session = openSession();

			WikiPage wikiPage = (WikiPage)session.get(WikiPageImpl.class,
					new Long(pageId));

			if (wikiPage == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + pageId);
				}

				throw new NoSuchPageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					pageId);
			}

			return remove(wikiPage);
		}
		catch (NoSuchPageException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiPage remove(WikiPage wikiPage) throws SystemException {
		for (ModelListener<WikiPage> listener : listeners) {
			listener.onBeforeRemove(wikiPage);
		}

		wikiPage = removeImpl(wikiPage);

		for (ModelListener<WikiPage> listener : listeners) {
			listener.onAfterRemove(wikiPage);
		}

		return wikiPage;
	}

	protected WikiPage removeImpl(WikiPage wikiPage) throws SystemException {
		wikiPage = toUnwrappedModel(wikiPage);

		Session session = null;

		try {
			session = openSession();

			if (wikiPage.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(WikiPageImpl.class,
						wikiPage.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(wikiPage);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		WikiPageModelImpl wikiPageModelImpl = (WikiPageModelImpl)wikiPage;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				wikiPageModelImpl.getOriginalUuid(),
				new Long(wikiPageModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_N_T_V,
			new Object[] {
				new Long(wikiPageModelImpl.getOriginalNodeId()),
				
			wikiPageModelImpl.getOriginalTitle(),
				new Double(wikiPageModelImpl.getOriginalVersion())
			});

		EntityCacheUtil.removeResult(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageImpl.class, wikiPage.getPrimaryKey());

		return wikiPage;
	}

	public WikiPage updateImpl(
		com.liferay.portlet.wiki.model.WikiPage wikiPage, boolean merge)
		throws SystemException {
		wikiPage = toUnwrappedModel(wikiPage);

		boolean isNew = wikiPage.isNew();

		WikiPageModelImpl wikiPageModelImpl = (WikiPageModelImpl)wikiPage;

		if (Validator.isNull(wikiPage.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			wikiPage.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, wikiPage, merge);

			wikiPage.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageImpl.class, wikiPage.getPrimaryKey(), wikiPage);

		if (!isNew &&
				(!Validator.equals(wikiPage.getUuid(),
					wikiPageModelImpl.getOriginalUuid()) ||
				(wikiPage.getGroupId() != wikiPageModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					wikiPageModelImpl.getOriginalUuid(),
					new Long(wikiPageModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(wikiPage.getUuid(),
					wikiPageModelImpl.getOriginalUuid()) ||
				(wikiPage.getGroupId() != wikiPageModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] { wikiPage.getUuid(), new Long(
						wikiPage.getGroupId()) }, wikiPage);
		}

		if (!isNew &&
				((wikiPage.getNodeId() != wikiPageModelImpl.getOriginalNodeId()) ||
				!Validator.equals(wikiPage.getTitle(),
					wikiPageModelImpl.getOriginalTitle()) ||
				(wikiPage.getVersion() != wikiPageModelImpl.getOriginalVersion()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_N_T_V,
				new Object[] {
					new Long(wikiPageModelImpl.getOriginalNodeId()),
					
				wikiPageModelImpl.getOriginalTitle(),
					new Double(wikiPageModelImpl.getOriginalVersion())
				});
		}

		if (isNew ||
				((wikiPage.getNodeId() != wikiPageModelImpl.getOriginalNodeId()) ||
				!Validator.equals(wikiPage.getTitle(),
					wikiPageModelImpl.getOriginalTitle()) ||
				(wikiPage.getVersion() != wikiPageModelImpl.getOriginalVersion()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_T_V,
				new Object[] {
					new Long(wikiPage.getNodeId()),
					
				wikiPage.getTitle(), new Double(wikiPage.getVersion())
				}, wikiPage);
		}

		return wikiPage;
	}

	protected WikiPage toUnwrappedModel(WikiPage wikiPage) {
		if (wikiPage instanceof WikiPageImpl) {
			return wikiPage;
		}

		WikiPageImpl wikiPageImpl = new WikiPageImpl();

		wikiPageImpl.setNew(wikiPage.isNew());
		wikiPageImpl.setPrimaryKey(wikiPage.getPrimaryKey());

		wikiPageImpl.setUuid(wikiPage.getUuid());
		wikiPageImpl.setPageId(wikiPage.getPageId());
		wikiPageImpl.setResourcePrimKey(wikiPage.getResourcePrimKey());
		wikiPageImpl.setGroupId(wikiPage.getGroupId());
		wikiPageImpl.setCompanyId(wikiPage.getCompanyId());
		wikiPageImpl.setUserId(wikiPage.getUserId());
		wikiPageImpl.setUserName(wikiPage.getUserName());
		wikiPageImpl.setCreateDate(wikiPage.getCreateDate());
		wikiPageImpl.setModifiedDate(wikiPage.getModifiedDate());
		wikiPageImpl.setNodeId(wikiPage.getNodeId());
		wikiPageImpl.setTitle(wikiPage.getTitle());
		wikiPageImpl.setVersion(wikiPage.getVersion());
		wikiPageImpl.setMinorEdit(wikiPage.isMinorEdit());
		wikiPageImpl.setContent(wikiPage.getContent());
		wikiPageImpl.setSummary(wikiPage.getSummary());
		wikiPageImpl.setFormat(wikiPage.getFormat());
		wikiPageImpl.setHead(wikiPage.isHead());
		wikiPageImpl.setParentTitle(wikiPage.getParentTitle());
		wikiPageImpl.setRedirectTitle(wikiPage.getRedirectTitle());
		wikiPageImpl.setStatus(wikiPage.getStatus());
		wikiPageImpl.setStatusByUserId(wikiPage.getStatusByUserId());
		wikiPageImpl.setStatusByUserName(wikiPage.getStatusByUserName());
		wikiPageImpl.setStatusDate(wikiPage.getStatusDate());

		return wikiPageImpl;
	}

	public WikiPage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public WikiPage findByPrimaryKey(long pageId)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = fetchByPrimaryKey(pageId);

		if (wikiPage == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + pageId);
			}

			throw new NoSuchPageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				pageId);
		}

		return wikiPage;
	}

	public WikiPage fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public WikiPage fetchByPrimaryKey(long pageId) throws SystemException {
		WikiPage wikiPage = (WikiPage)EntityCacheUtil.getResult(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
				WikiPageImpl.class, pageId, this);

		if (wikiPage == null) {
			Session session = null;

			try {
				session = openSession();

				wikiPage = (WikiPage)session.get(WikiPageImpl.class,
						new Long(pageId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (wikiPage != null) {
					cacheResult(wikiPage);
				}

				closeSession(session);
			}
		}

		return wikiPage;
	}

	public List<WikiPage> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<WikiPage> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<WikiPage> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
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

				query.append(_SQL_SELECT_WIKIPAGE_WHERE);

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
					query.append(WikiPageModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<WikiPage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiPage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		int count = countByUuid(uuid);

		List<WikiPage> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByUuid_PrevAndNext(long pageId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByUuid_PrevAndNext(session, wikiPage, uuid,
					orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByUuid_PrevAndNext(session, wikiPage, uuid,
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

	protected WikiPage getByUuid_PrevAndNext(Session session,
		WikiPage wikiPage, String uuid, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public WikiPage findByUUID_G(String uuid, long groupId)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = fetchByUUID_G(uuid, groupId);

		if (wikiPage == null) {
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

			throw new NoSuchPageException(msg.toString());
		}

		return wikiPage;
	}

	public WikiPage fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public WikiPage fetchByUUID_G(String uuid, long groupId,
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

				query.append(_SQL_SELECT_WIKIPAGE_WHERE);

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

				query.append(WikiPageModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<WikiPage> list = q.list();

				result = list;

				WikiPage wikiPage = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					wikiPage = list.get(0);

					cacheResult(wikiPage);

					if ((wikiPage.getUuid() == null) ||
							!wikiPage.getUuid().equals(uuid) ||
							(wikiPage.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, wikiPage);
					}
				}

				return wikiPage;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<WikiPage>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (WikiPage)result;
			}
		}
	}

	public List<WikiPage> findByNodeId(long nodeId) throws SystemException {
		return findByNodeId(nodeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<WikiPage> findByNodeId(long nodeId, int start, int end)
		throws SystemException {
		return findByNodeId(nodeId, start, end, null);
	}

	public List<WikiPage> findByNodeId(long nodeId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_NODEID,
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

				query.append(_SQL_SELECT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_NODEID_NODEID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(WikiPageModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				list = (List<WikiPage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiPage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_NODEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByNodeId_First(long nodeId,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByNodeId(nodeId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByNodeId_Last(long nodeId,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		int count = countByNodeId(nodeId);

		List<WikiPage> list = findByNodeId(nodeId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByNodeId_PrevAndNext(long pageId, long nodeId,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByNodeId_PrevAndNext(session, wikiPage, nodeId,
					orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByNodeId_PrevAndNext(session, wikiPage, nodeId,
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

	protected WikiPage getByNodeId_PrevAndNext(Session session,
		WikiPage wikiPage, long nodeId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_NODEID_NODEID_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<WikiPage> findByFormat(String format) throws SystemException {
		return findByFormat(format, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<WikiPage> findByFormat(String format, int start, int end)
		throws SystemException {
		return findByFormat(format, start, end, null);
	}

	public List<WikiPage> findByFormat(String format, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				format,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_FORMAT,
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

				query.append(_SQL_SELECT_WIKIPAGE_WHERE);

				if (format == null) {
					query.append(_FINDER_COLUMN_FORMAT_FORMAT_1);
				}
				else {
					if (format.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_FORMAT_FORMAT_3);
					}
					else {
						query.append(_FINDER_COLUMN_FORMAT_FORMAT_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(WikiPageModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (format != null) {
					qPos.add(format);
				}

				list = (List<WikiPage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiPage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_FORMAT,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByFormat_First(String format,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByFormat(format, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("format=");
			msg.append(format);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByFormat_Last(String format,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		int count = countByFormat(format);

		List<WikiPage> list = findByFormat(format, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("format=");
			msg.append(format);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByFormat_PrevAndNext(long pageId, String format,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByFormat_PrevAndNext(session, wikiPage, format,
					orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByFormat_PrevAndNext(session, wikiPage, format,
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

	protected WikiPage getByFormat_PrevAndNext(Session session,
		WikiPage wikiPage, String format, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		if (format == null) {
			query.append(_FINDER_COLUMN_FORMAT_FORMAT_1);
		}
		else {
			if (format.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_FORMAT_FORMAT_3);
			}
			else {
				query.append(_FINDER_COLUMN_FORMAT_FORMAT_2);
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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		if (format != null) {
			qPos.add(format);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<WikiPage> findByN_T(long nodeId, String title)
		throws SystemException {
		return findByN_T(nodeId, title, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	public List<WikiPage> findByN_T(long nodeId, String title, int start,
		int end) throws SystemException {
		return findByN_T(nodeId, title, start, end, null);
	}

	public List<WikiPage> findByN_T(long nodeId, String title, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId),
				
				title,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_N_T,
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

				query.append(_SQL_SELECT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_N_T_NODEID_2);

				if (title == null) {
					query.append(_FINDER_COLUMN_N_T_TITLE_1);
				}
				else {
					if (title.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_N_T_TITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_N_T_TITLE_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(WikiPageModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (title != null) {
					qPos.add(title);
				}

				list = (List<WikiPage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiPage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_N_T, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByN_T_First(long nodeId, String title,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByN_T(nodeId, title, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", title=");
			msg.append(title);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByN_T_Last(long nodeId, String title,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		int count = countByN_T(nodeId, title);

		List<WikiPage> list = findByN_T(nodeId, title, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", title=");
			msg.append(title);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByN_T_PrevAndNext(long pageId, long nodeId,
		String title, OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_T_PrevAndNext(session, wikiPage, nodeId, title,
					orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_T_PrevAndNext(session, wikiPage, nodeId, title,
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

	protected WikiPage getByN_T_PrevAndNext(Session session, WikiPage wikiPage,
		long nodeId, String title, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_T_NODEID_2);

		if (title == null) {
			query.append(_FINDER_COLUMN_N_T_TITLE_1);
		}
		else {
			if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_T_TITLE_3);
			}
			else {
				query.append(_FINDER_COLUMN_N_T_TITLE_2);
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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		if (title != null) {
			qPos.add(title);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<WikiPage> findByN_H(long nodeId, boolean head)
		throws SystemException {
		return findByN_H(nodeId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	public List<WikiPage> findByN_H(long nodeId, boolean head, int start,
		int end) throws SystemException {
		return findByN_H(nodeId, head, start, end, null);
	}

	public List<WikiPage> findByN_H(long nodeId, boolean head, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId), Boolean.valueOf(head),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_N_H,
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

				query.append(_SQL_SELECT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_N_H_NODEID_2);

				query.append(_FINDER_COLUMN_N_H_HEAD_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(WikiPageModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				list = (List<WikiPage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiPage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_N_H, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByN_H_First(long nodeId, boolean head,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByN_H(nodeId, head, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", head=");
			msg.append(head);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByN_H_Last(long nodeId, boolean head,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		int count = countByN_H(nodeId, head);

		List<WikiPage> list = findByN_H(nodeId, head, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", head=");
			msg.append(head);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByN_H_PrevAndNext(long pageId, long nodeId,
		boolean head, OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_H_PrevAndNext(session, wikiPage, nodeId, head,
					orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_H_PrevAndNext(session, wikiPage, nodeId, head,
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

	protected WikiPage getByN_H_PrevAndNext(Session session, WikiPage wikiPage,
		long nodeId, boolean head, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_H_NODEID_2);

		query.append(_FINDER_COLUMN_N_H_HEAD_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		qPos.add(head);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<WikiPage> findByN_P(long nodeId, String parentTitle)
		throws SystemException {
		return findByN_P(nodeId, parentTitle, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<WikiPage> findByN_P(long nodeId, String parentTitle, int start,
		int end) throws SystemException {
		return findByN_P(nodeId, parentTitle, start, end, null);
	}

	public List<WikiPage> findByN_P(long nodeId, String parentTitle, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId),
				
				parentTitle,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_N_P,
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

				query.append(_SQL_SELECT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_N_P_NODEID_2);

				if (parentTitle == null) {
					query.append(_FINDER_COLUMN_N_P_PARENTTITLE_1);
				}
				else {
					if (parentTitle.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_N_P_PARENTTITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_N_P_PARENTTITLE_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(WikiPageModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (parentTitle != null) {
					qPos.add(parentTitle);
				}

				list = (List<WikiPage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiPage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_N_P, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByN_P_First(long nodeId, String parentTitle,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByN_P(nodeId, parentTitle, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", parentTitle=");
			msg.append(parentTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByN_P_Last(long nodeId, String parentTitle,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		int count = countByN_P(nodeId, parentTitle);

		List<WikiPage> list = findByN_P(nodeId, parentTitle, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", parentTitle=");
			msg.append(parentTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByN_P_PrevAndNext(long pageId, long nodeId,
		String parentTitle, OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_P_PrevAndNext(session, wikiPage, nodeId,
					parentTitle, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_P_PrevAndNext(session, wikiPage, nodeId,
					parentTitle, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByN_P_PrevAndNext(Session session, WikiPage wikiPage,
		long nodeId, String parentTitle, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_P_NODEID_2);

		if (parentTitle == null) {
			query.append(_FINDER_COLUMN_N_P_PARENTTITLE_1);
		}
		else {
			if (parentTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_P_PARENTTITLE_3);
			}
			else {
				query.append(_FINDER_COLUMN_N_P_PARENTTITLE_2);
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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		if (parentTitle != null) {
			qPos.add(parentTitle);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<WikiPage> findByN_R(long nodeId, String redirectTitle)
		throws SystemException {
		return findByN_R(nodeId, redirectTitle, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<WikiPage> findByN_R(long nodeId, String redirectTitle,
		int start, int end) throws SystemException {
		return findByN_R(nodeId, redirectTitle, start, end, null);
	}

	public List<WikiPage> findByN_R(long nodeId, String redirectTitle,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId),
				
				redirectTitle,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_N_R,
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

				query.append(_SQL_SELECT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_N_R_NODEID_2);

				if (redirectTitle == null) {
					query.append(_FINDER_COLUMN_N_R_REDIRECTTITLE_1);
				}
				else {
					if (redirectTitle.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_N_R_REDIRECTTITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_N_R_REDIRECTTITLE_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(WikiPageModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (redirectTitle != null) {
					qPos.add(redirectTitle);
				}

				list = (List<WikiPage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiPage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_N_R, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByN_R_First(long nodeId, String redirectTitle,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByN_R(nodeId, redirectTitle, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", redirectTitle=");
			msg.append(redirectTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByN_R_Last(long nodeId, String redirectTitle,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		int count = countByN_R(nodeId, redirectTitle);

		List<WikiPage> list = findByN_R(nodeId, redirectTitle, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", redirectTitle=");
			msg.append(redirectTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByN_R_PrevAndNext(long pageId, long nodeId,
		String redirectTitle, OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_R_PrevAndNext(session, wikiPage, nodeId,
					redirectTitle, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_R_PrevAndNext(session, wikiPage, nodeId,
					redirectTitle, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByN_R_PrevAndNext(Session session, WikiPage wikiPage,
		long nodeId, String redirectTitle, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_R_NODEID_2);

		if (redirectTitle == null) {
			query.append(_FINDER_COLUMN_N_R_REDIRECTTITLE_1);
		}
		else {
			if (redirectTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_R_REDIRECTTITLE_3);
			}
			else {
				query.append(_FINDER_COLUMN_N_R_REDIRECTTITLE_2);
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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		if (redirectTitle != null) {
			qPos.add(redirectTitle);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<WikiPage> findByN_S(long nodeId, int status)
		throws SystemException {
		return findByN_S(nodeId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	public List<WikiPage> findByN_S(long nodeId, int status, int start, int end)
		throws SystemException {
		return findByN_S(nodeId, status, start, end, null);
	}

	public List<WikiPage> findByN_S(long nodeId, int status, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId), new Integer(status),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_N_S,
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

				query.append(_SQL_SELECT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_N_S_NODEID_2);

				query.append(_FINDER_COLUMN_N_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(WikiPageModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(status);

				list = (List<WikiPage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiPage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_N_S, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByN_S_First(long nodeId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByN_S(nodeId, status, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByN_S_Last(long nodeId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		int count = countByN_S(nodeId, status);

		List<WikiPage> list = findByN_S(nodeId, status, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByN_S_PrevAndNext(long pageId, long nodeId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_S_PrevAndNext(session, wikiPage, nodeId, status,
					orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_S_PrevAndNext(session, wikiPage, nodeId, status,
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

	protected WikiPage getByN_S_PrevAndNext(Session session, WikiPage wikiPage,
		long nodeId, int status, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_S_NODEID_2);

		query.append(_FINDER_COLUMN_N_S_STATUS_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<WikiPage> findByU_N_S(long userId, long nodeId, int status)
		throws SystemException {
		return findByU_N_S(userId, nodeId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<WikiPage> findByU_N_S(long userId, long nodeId, int status,
		int start, int end) throws SystemException {
		return findByU_N_S(userId, nodeId, status, start, end, null);
	}

	public List<WikiPage> findByU_N_S(long userId, long nodeId, int status,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(nodeId), new Integer(status),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U_N_S,
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

				query.append(_SQL_SELECT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_U_N_S_USERID_2);

				query.append(_FINDER_COLUMN_U_N_S_NODEID_2);

				query.append(_FINDER_COLUMN_U_N_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(WikiPageModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(nodeId);

				qPos.add(status);

				list = (List<WikiPage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiPage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U_N_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByU_N_S_First(long userId, long nodeId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByU_N_S(userId, nodeId, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", nodeId=");
			msg.append(nodeId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByU_N_S_Last(long userId, long nodeId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		int count = countByU_N_S(userId, nodeId, status);

		List<WikiPage> list = findByU_N_S(userId, nodeId, status, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", nodeId=");
			msg.append(nodeId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByU_N_S_PrevAndNext(long pageId, long userId,
		long nodeId, int status, OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByU_N_S_PrevAndNext(session, wikiPage, userId,
					nodeId, status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByU_N_S_PrevAndNext(session, wikiPage, userId,
					nodeId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByU_N_S_PrevAndNext(Session session,
		WikiPage wikiPage, long userId, long nodeId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_U_N_S_USERID_2);

		query.append(_FINDER_COLUMN_U_N_S_NODEID_2);

		query.append(_FINDER_COLUMN_U_N_S_STATUS_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(nodeId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public WikiPage findByN_T_V(long nodeId, String title, double version)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = fetchByN_T_V(nodeId, title, version);

		if (wikiPage == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", title=");
			msg.append(title);

			msg.append(", version=");
			msg.append(version);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPageException(msg.toString());
		}

		return wikiPage;
	}

	public WikiPage fetchByN_T_V(long nodeId, String title, double version)
		throws SystemException {
		return fetchByN_T_V(nodeId, title, version, true);
	}

	public WikiPage fetchByN_T_V(long nodeId, String title, double version,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId),
				
				title, new Double(version)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_N_T_V,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_N_T_V_NODEID_2);

				if (title == null) {
					query.append(_FINDER_COLUMN_N_T_V_TITLE_1);
				}
				else {
					if (title.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_N_T_V_TITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_N_T_V_TITLE_2);
					}
				}

				query.append(_FINDER_COLUMN_N_T_V_VERSION_2);

				query.append(WikiPageModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (title != null) {
					qPos.add(title);
				}

				qPos.add(version);

				List<WikiPage> list = q.list();

				result = list;

				WikiPage wikiPage = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_T_V,
						finderArgs, list);
				}
				else {
					wikiPage = list.get(0);

					cacheResult(wikiPage);

					if ((wikiPage.getNodeId() != nodeId) ||
							(wikiPage.getTitle() == null) ||
							!wikiPage.getTitle().equals(title) ||
							(wikiPage.getVersion() != version)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_T_V,
							finderArgs, wikiPage);
					}
				}

				return wikiPage;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_T_V,
						finderArgs, new ArrayList<WikiPage>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (WikiPage)result;
			}
		}
	}

	public List<WikiPage> findByN_T_H(long nodeId, String title, boolean head)
		throws SystemException {
		return findByN_T_H(nodeId, title, head, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<WikiPage> findByN_T_H(long nodeId, String title, boolean head,
		int start, int end) throws SystemException {
		return findByN_T_H(nodeId, title, head, start, end, null);
	}

	public List<WikiPage> findByN_T_H(long nodeId, String title, boolean head,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId),
				
				title, Boolean.valueOf(head),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_N_T_H,
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

				query.append(_SQL_SELECT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_N_T_H_NODEID_2);

				if (title == null) {
					query.append(_FINDER_COLUMN_N_T_H_TITLE_1);
				}
				else {
					if (title.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_N_T_H_TITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_N_T_H_TITLE_2);
					}
				}

				query.append(_FINDER_COLUMN_N_T_H_HEAD_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(WikiPageModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (title != null) {
					qPos.add(title);
				}

				qPos.add(head);

				list = (List<WikiPage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiPage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_N_T_H,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByN_T_H_First(long nodeId, String title, boolean head,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByN_T_H(nodeId, title, head, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", title=");
			msg.append(title);

			msg.append(", head=");
			msg.append(head);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByN_T_H_Last(long nodeId, String title, boolean head,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		int count = countByN_T_H(nodeId, title, head);

		List<WikiPage> list = findByN_T_H(nodeId, title, head, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", title=");
			msg.append(title);

			msg.append(", head=");
			msg.append(head);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByN_T_H_PrevAndNext(long pageId, long nodeId,
		String title, boolean head, OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_T_H_PrevAndNext(session, wikiPage, nodeId, title,
					head, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_T_H_PrevAndNext(session, wikiPage, nodeId, title,
					head, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByN_T_H_PrevAndNext(Session session,
		WikiPage wikiPage, long nodeId, String title, boolean head,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_T_H_NODEID_2);

		if (title == null) {
			query.append(_FINDER_COLUMN_N_T_H_TITLE_1);
		}
		else {
			if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_T_H_TITLE_3);
			}
			else {
				query.append(_FINDER_COLUMN_N_T_H_TITLE_2);
			}
		}

		query.append(_FINDER_COLUMN_N_T_H_HEAD_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		if (title != null) {
			qPos.add(title);
		}

		qPos.add(head);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<WikiPage> findByN_T_S(long nodeId, String title, int status)
		throws SystemException {
		return findByN_T_S(nodeId, title, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<WikiPage> findByN_T_S(long nodeId, String title, int status,
		int start, int end) throws SystemException {
		return findByN_T_S(nodeId, title, status, start, end, null);
	}

	public List<WikiPage> findByN_T_S(long nodeId, String title, int status,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId),
				
				title, new Integer(status),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_N_T_S,
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

				query.append(_SQL_SELECT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_N_T_S_NODEID_2);

				if (title == null) {
					query.append(_FINDER_COLUMN_N_T_S_TITLE_1);
				}
				else {
					if (title.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_N_T_S_TITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_N_T_S_TITLE_2);
					}
				}

				query.append(_FINDER_COLUMN_N_T_S_STATUS_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(WikiPageModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (title != null) {
					qPos.add(title);
				}

				qPos.add(status);

				list = (List<WikiPage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiPage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_N_T_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByN_T_S_First(long nodeId, String title, int status,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByN_T_S(nodeId, title, status, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", title=");
			msg.append(title);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByN_T_S_Last(long nodeId, String title, int status,
		OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		int count = countByN_T_S(nodeId, title, status);

		List<WikiPage> list = findByN_T_S(nodeId, title, status, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", title=");
			msg.append(title);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByN_T_S_PrevAndNext(long pageId, long nodeId,
		String title, int status, OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_T_S_PrevAndNext(session, wikiPage, nodeId, title,
					status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_T_S_PrevAndNext(session, wikiPage, nodeId, title,
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

	protected WikiPage getByN_T_S_PrevAndNext(Session session,
		WikiPage wikiPage, long nodeId, String title, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_T_S_NODEID_2);

		if (title == null) {
			query.append(_FINDER_COLUMN_N_T_S_TITLE_1);
		}
		else {
			if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_T_S_TITLE_3);
			}
			else {
				query.append(_FINDER_COLUMN_N_T_S_TITLE_2);
			}
		}

		query.append(_FINDER_COLUMN_N_T_S_STATUS_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		if (title != null) {
			qPos.add(title);
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<WikiPage> findByN_H_P(long nodeId, boolean head,
		String parentTitle) throws SystemException {
		return findByN_H_P(nodeId, head, parentTitle, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<WikiPage> findByN_H_P(long nodeId, boolean head,
		String parentTitle, int start, int end) throws SystemException {
		return findByN_H_P(nodeId, head, parentTitle, start, end, null);
	}

	public List<WikiPage> findByN_H_P(long nodeId, boolean head,
		String parentTitle, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId), Boolean.valueOf(head),
				
				parentTitle,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_N_H_P,
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

				query.append(_SQL_SELECT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_N_H_P_NODEID_2);

				query.append(_FINDER_COLUMN_N_H_P_HEAD_2);

				if (parentTitle == null) {
					query.append(_FINDER_COLUMN_N_H_P_PARENTTITLE_1);
				}
				else {
					if (parentTitle.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_N_H_P_PARENTTITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_N_H_P_PARENTTITLE_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(WikiPageModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				if (parentTitle != null) {
					qPos.add(parentTitle);
				}

				list = (List<WikiPage>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiPage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_N_H_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByN_H_P_First(long nodeId, boolean head,
		String parentTitle, OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByN_H_P(nodeId, head, parentTitle, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", head=");
			msg.append(head);

			msg.append(", parentTitle=");
			msg.append(parentTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByN_H_P_Last(long nodeId, boolean head,
		String parentTitle, OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		int count = countByN_H_P(nodeId, head, parentTitle);

		List<WikiPage> list = findByN_H_P(nodeId, head, parentTitle, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", head=");
			msg.append(head);

			msg.append(", parentTitle=");
			msg.append(parentTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByN_H_P_PrevAndNext(long pageId, long nodeId,
		boolean head, String parentTitle, OrderByComparator orderByComparator)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_H_P_PrevAndNext(session, wikiPage, nodeId, head,
					parentTitle, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_H_P_PrevAndNext(session, wikiPage, nodeId, head,
					parentTitle, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByN_H_P_PrevAndNext(Session session,
		WikiPage wikiPage, long nodeId, boolean head, String parentTitle,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_H_P_NODEID_2);

		query.append(_FINDER_COLUMN_N_H_P_HEAD_2);

		if (parentTitle == null) {
			query.append(_FINDER_COLUMN_N_H_P_PARENTTITLE_1);
		}
		else {
			if (parentTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_H_P_PARENTTITLE_3);
			}
			else {
				query.append(_FINDER_COLUMN_N_H_P_PARENTTITLE_2);
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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		qPos.add(head);

		if (parentTitle != null) {
			qPos.add(parentTitle);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<WikiPage> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<WikiPage> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<WikiPage> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_WIKIPAGE);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_WIKIPAGE.concat(WikiPageModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiPage>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (WikiPage wikiPage : findByUuid(uuid)) {
			remove(wikiPage);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByUUID_G(uuid, groupId);

		remove(wikiPage);
	}

	public void removeByNodeId(long nodeId) throws SystemException {
		for (WikiPage wikiPage : findByNodeId(nodeId)) {
			remove(wikiPage);
		}
	}

	public void removeByFormat(String format) throws SystemException {
		for (WikiPage wikiPage : findByFormat(format)) {
			remove(wikiPage);
		}
	}

	public void removeByN_T(long nodeId, String title)
		throws SystemException {
		for (WikiPage wikiPage : findByN_T(nodeId, title)) {
			remove(wikiPage);
		}
	}

	public void removeByN_H(long nodeId, boolean head)
		throws SystemException {
		for (WikiPage wikiPage : findByN_H(nodeId, head)) {
			remove(wikiPage);
		}
	}

	public void removeByN_P(long nodeId, String parentTitle)
		throws SystemException {
		for (WikiPage wikiPage : findByN_P(nodeId, parentTitle)) {
			remove(wikiPage);
		}
	}

	public void removeByN_R(long nodeId, String redirectTitle)
		throws SystemException {
		for (WikiPage wikiPage : findByN_R(nodeId, redirectTitle)) {
			remove(wikiPage);
		}
	}

	public void removeByN_S(long nodeId, int status) throws SystemException {
		for (WikiPage wikiPage : findByN_S(nodeId, status)) {
			remove(wikiPage);
		}
	}

	public void removeByU_N_S(long userId, long nodeId, int status)
		throws SystemException {
		for (WikiPage wikiPage : findByU_N_S(userId, nodeId, status)) {
			remove(wikiPage);
		}
	}

	public void removeByN_T_V(long nodeId, String title, double version)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByN_T_V(nodeId, title, version);

		remove(wikiPage);
	}

	public void removeByN_T_H(long nodeId, String title, boolean head)
		throws SystemException {
		for (WikiPage wikiPage : findByN_T_H(nodeId, title, head)) {
			remove(wikiPage);
		}
	}

	public void removeByN_T_S(long nodeId, String title, int status)
		throws SystemException {
		for (WikiPage wikiPage : findByN_T_S(nodeId, title, status)) {
			remove(wikiPage);
		}
	}

	public void removeByN_H_P(long nodeId, boolean head, String parentTitle)
		throws SystemException {
		for (WikiPage wikiPage : findByN_H_P(nodeId, head, parentTitle)) {
			remove(wikiPage);
		}
	}

	public void removeAll() throws SystemException {
		for (WikiPage wikiPage : findAll()) {
			remove(wikiPage);
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

				query.append(_SQL_COUNT_WIKIPAGE_WHERE);

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

				query.append(_SQL_COUNT_WIKIPAGE_WHERE);

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

	public int countByNodeId(long nodeId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(nodeId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_NODEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_NODEID_NODEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_NODEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByFormat(String format) throws SystemException {
		Object[] finderArgs = new Object[] { format };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FORMAT,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_WIKIPAGE_WHERE);

				if (format == null) {
					query.append(_FINDER_COLUMN_FORMAT_FORMAT_1);
				}
				else {
					if (format.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_FORMAT_FORMAT_3);
					}
					else {
						query.append(_FINDER_COLUMN_FORMAT_FORMAT_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (format != null) {
					qPos.add(format);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_FORMAT,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByN_T(long nodeId, String title) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(nodeId), title };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_N_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_N_T_NODEID_2);

				if (title == null) {
					query.append(_FINDER_COLUMN_N_T_TITLE_1);
				}
				else {
					if (title.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_N_T_TITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_N_T_TITLE_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (title != null) {
					qPos.add(title);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_N_T, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByN_H(long nodeId, boolean head) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId), Boolean.valueOf(head)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_N_H,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_N_H_NODEID_2);

				query.append(_FINDER_COLUMN_N_H_HEAD_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_N_H, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByN_P(long nodeId, String parentTitle)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(nodeId), parentTitle };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_N_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_N_P_NODEID_2);

				if (parentTitle == null) {
					query.append(_FINDER_COLUMN_N_P_PARENTTITLE_1);
				}
				else {
					if (parentTitle.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_N_P_PARENTTITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_N_P_PARENTTITLE_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (parentTitle != null) {
					qPos.add(parentTitle);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_N_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByN_R(long nodeId, String redirectTitle)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(nodeId), redirectTitle };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_N_R,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_N_R_NODEID_2);

				if (redirectTitle == null) {
					query.append(_FINDER_COLUMN_N_R_REDIRECTTITLE_1);
				}
				else {
					if (redirectTitle.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_N_R_REDIRECTTITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_N_R_REDIRECTTITLE_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (redirectTitle != null) {
					qPos.add(redirectTitle);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_N_R, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByN_S(long nodeId, int status) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(nodeId), new Integer(status) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_N_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_N_S_NODEID_2);

				query.append(_FINDER_COLUMN_N_S_STATUS_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_N_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_N_S(long userId, long nodeId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(nodeId), new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_N_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_U_N_S_USERID_2);

				query.append(_FINDER_COLUMN_U_N_S_NODEID_2);

				query.append(_FINDER_COLUMN_U_N_S_STATUS_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(nodeId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_N_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByN_T_V(long nodeId, String title, double version)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId),
				
				title, new Double(version)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_N_T_V,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_N_T_V_NODEID_2);

				if (title == null) {
					query.append(_FINDER_COLUMN_N_T_V_TITLE_1);
				}
				else {
					if (title.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_N_T_V_TITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_N_T_V_TITLE_2);
					}
				}

				query.append(_FINDER_COLUMN_N_T_V_VERSION_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (title != null) {
					qPos.add(title);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_N_T_V,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByN_T_H(long nodeId, String title, boolean head)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId),
				
				title, Boolean.valueOf(head)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_N_T_H,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_N_T_H_NODEID_2);

				if (title == null) {
					query.append(_FINDER_COLUMN_N_T_H_TITLE_1);
				}
				else {
					if (title.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_N_T_H_TITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_N_T_H_TITLE_2);
					}
				}

				query.append(_FINDER_COLUMN_N_T_H_HEAD_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (title != null) {
					qPos.add(title);
				}

				qPos.add(head);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_N_T_H,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByN_T_S(long nodeId, String title, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId),
				
				title, new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_N_T_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_N_T_S_NODEID_2);

				if (title == null) {
					query.append(_FINDER_COLUMN_N_T_S_TITLE_1);
				}
				else {
					if (title.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_N_T_S_TITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_N_T_S_TITLE_2);
					}
				}

				query.append(_FINDER_COLUMN_N_T_S_STATUS_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (title != null) {
					qPos.add(title);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_N_T_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByN_H_P(long nodeId, boolean head, String parentTitle)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId), Boolean.valueOf(head),
				
				parentTitle
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_N_H_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_WIKIPAGE_WHERE);

				query.append(_FINDER_COLUMN_N_H_P_NODEID_2);

				query.append(_FINDER_COLUMN_N_H_P_HEAD_2);

				if (parentTitle == null) {
					query.append(_FINDER_COLUMN_N_H_P_PARENTTITLE_1);
				}
				else {
					if (parentTitle.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_N_H_P_PARENTTITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_N_H_P_PARENTTITLE_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				if (parentTitle != null) {
					qPos.add(parentTitle);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_N_H_P,
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

				Query q = session.createQuery(_SQL_COUNT_WIKIPAGE);

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
						"value.object.listener.com.liferay.portlet.wiki.model.WikiPage")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<WikiPage>> listenersList = new ArrayList<ModelListener<WikiPage>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<WikiPage>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = WikiNodePersistence.class)
	protected WikiNodePersistence wikiNodePersistence;
	@BeanReference(type = WikiPagePersistence.class)
	protected WikiPagePersistence wikiPagePersistence;
	@BeanReference(type = WikiPageResourcePersistence.class)
	protected WikiPageResourcePersistence wikiPageResourcePersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
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
	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	private static final String _SQL_SELECT_WIKIPAGE = "SELECT wikiPage FROM WikiPage wikiPage";
	private static final String _SQL_SELECT_WIKIPAGE_WHERE = "SELECT wikiPage FROM WikiPage wikiPage WHERE ";
	private static final String _SQL_COUNT_WIKIPAGE = "SELECT COUNT(wikiPage) FROM WikiPage wikiPage";
	private static final String _SQL_COUNT_WIKIPAGE_WHERE = "SELECT COUNT(wikiPage) FROM WikiPage wikiPage WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "wikiPage.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "wikiPage.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(wikiPage.uuid IS NULL OR wikiPage.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "wikiPage.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "wikiPage.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(wikiPage.uuid IS NULL OR wikiPage.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "wikiPage.groupId = ?";
	private static final String _FINDER_COLUMN_NODEID_NODEID_2 = "wikiPage.nodeId = ?";
	private static final String _FINDER_COLUMN_FORMAT_FORMAT_1 = "wikiPage.format IS NULL";
	private static final String _FINDER_COLUMN_FORMAT_FORMAT_2 = "wikiPage.format = ?";
	private static final String _FINDER_COLUMN_FORMAT_FORMAT_3 = "(wikiPage.format IS NULL OR wikiPage.format = ?)";
	private static final String _FINDER_COLUMN_N_T_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_T_TITLE_1 = "wikiPage.title IS NULL";
	private static final String _FINDER_COLUMN_N_T_TITLE_2 = "wikiPage.title = ?";
	private static final String _FINDER_COLUMN_N_T_TITLE_3 = "(wikiPage.title IS NULL OR wikiPage.title = ?)";
	private static final String _FINDER_COLUMN_N_H_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_H_HEAD_2 = "wikiPage.head = ?";
	private static final String _FINDER_COLUMN_N_P_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_P_PARENTTITLE_1 = "wikiPage.parentTitle IS NULL";
	private static final String _FINDER_COLUMN_N_P_PARENTTITLE_2 = "wikiPage.parentTitle = ?";
	private static final String _FINDER_COLUMN_N_P_PARENTTITLE_3 = "(wikiPage.parentTitle IS NULL OR wikiPage.parentTitle = ?)";
	private static final String _FINDER_COLUMN_N_R_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_R_REDIRECTTITLE_1 = "wikiPage.redirectTitle IS NULL";
	private static final String _FINDER_COLUMN_N_R_REDIRECTTITLE_2 = "wikiPage.redirectTitle = ?";
	private static final String _FINDER_COLUMN_N_R_REDIRECTTITLE_3 = "(wikiPage.redirectTitle IS NULL OR wikiPage.redirectTitle = ?)";
	private static final String _FINDER_COLUMN_N_S_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_S_STATUS_2 = "wikiPage.status = ?";
	private static final String _FINDER_COLUMN_U_N_S_USERID_2 = "wikiPage.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_N_S_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_U_N_S_STATUS_2 = "wikiPage.status = ?";
	private static final String _FINDER_COLUMN_N_T_V_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_T_V_TITLE_1 = "wikiPage.title IS NULL AND ";
	private static final String _FINDER_COLUMN_N_T_V_TITLE_2 = "wikiPage.title = ? AND ";
	private static final String _FINDER_COLUMN_N_T_V_TITLE_3 = "(wikiPage.title IS NULL OR wikiPage.title = ?) AND ";
	private static final String _FINDER_COLUMN_N_T_V_VERSION_2 = "wikiPage.version = ?";
	private static final String _FINDER_COLUMN_N_T_H_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_T_H_TITLE_1 = "wikiPage.title IS NULL AND ";
	private static final String _FINDER_COLUMN_N_T_H_TITLE_2 = "wikiPage.title = ? AND ";
	private static final String _FINDER_COLUMN_N_T_H_TITLE_3 = "(wikiPage.title IS NULL OR wikiPage.title = ?) AND ";
	private static final String _FINDER_COLUMN_N_T_H_HEAD_2 = "wikiPage.head = ?";
	private static final String _FINDER_COLUMN_N_T_S_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_T_S_TITLE_1 = "wikiPage.title IS NULL AND ";
	private static final String _FINDER_COLUMN_N_T_S_TITLE_2 = "wikiPage.title = ? AND ";
	private static final String _FINDER_COLUMN_N_T_S_TITLE_3 = "(wikiPage.title IS NULL OR wikiPage.title = ?) AND ";
	private static final String _FINDER_COLUMN_N_T_S_STATUS_2 = "wikiPage.status = ?";
	private static final String _FINDER_COLUMN_N_H_P_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_H_P_HEAD_2 = "wikiPage.head = ? AND ";
	private static final String _FINDER_COLUMN_N_H_P_PARENTTITLE_1 = "wikiPage.parentTitle IS NULL";
	private static final String _FINDER_COLUMN_N_H_P_PARENTTITLE_2 = "wikiPage.parentTitle = ?";
	private static final String _FINDER_COLUMN_N_H_P_PARENTTITLE_3 = "(wikiPage.parentTitle IS NULL OR wikiPage.parentTitle = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "wikiPage.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No WikiPage exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No WikiPage exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(WikiPagePersistenceImpl.class);
}