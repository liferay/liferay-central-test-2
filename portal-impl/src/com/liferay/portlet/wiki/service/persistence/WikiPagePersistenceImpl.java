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

package com.liferay.portlet.wiki.service.persistence;

import com.liferay.portal.NoSuchModelException;
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

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
			"findByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
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
			"findByNodeId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_NODEID = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
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
			"findByFormat", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_FORMAT = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
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
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_N_T = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
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
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_N_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
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
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_N_P = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
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
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_N_R = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
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
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_N_T_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
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
	public static final FinderPath FINDER_PATH_FIND_BY_N_H_P = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByN_H_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_N_H_P = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
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
					_log.warn("No WikiPage exists with the primary key " +
						pageId);
				}

				throw new NoSuchPageException(
					"No WikiPage exists with the primary key " + pageId);
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
				_log.warn("No WikiPage exists with the primary key " + pageId);
			}

			throw new NoSuchPageException(
				"No WikiPage exists with the primary key " + pageId);
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
		Object[] finderArgs = new Object[] { uuid };

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				if (uuid == null) {
					query.append("wikiPage.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(wikiPage.uuid IS NULL OR ");
					}

					query.append("wikiPage.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");

				Query q = session.createQuery(query.toString());

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

	public List<WikiPage> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<WikiPage> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				if (uuid == null) {
					query.append("wikiPage.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(wikiPage.uuid IS NULL OR ");
					}

					query.append("wikiPage.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("wikiPage.");
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

				else {
					query.append("ORDER BY ");

					query.append("wikiPage.nodeId ASC, ");
					query.append("wikiPage.title ASC, ");
					query.append("wikiPage.version ASC");
				}

				Query q = session.createQuery(query.toString());

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

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		int count = countByUuid(uuid);

		List<WikiPage> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByUuid_PrevAndNext(long pageId, String uuid,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

			if (uuid == null) {
				query.append("wikiPage.uuid IS NULL");
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append("(wikiPage.uuid IS NULL OR ");
				}

				query.append("wikiPage.uuid = ?");

				if (uuid.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("wikiPage.");
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

			else {
				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, wikiPage);

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = (WikiPage)objArray[0];
			array[1] = (WikiPage)objArray[1];
			array[2] = (WikiPage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiPage findByUUID_G(String uuid, long groupId)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = fetchByUUID_G(uuid, groupId);

		if (wikiPage == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				if (uuid == null) {
					query.append("wikiPage.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(wikiPage.uuid IS NULL OR ");
					}

					query.append("wikiPage.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("wikiPage.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");

				Query q = session.createQuery(query.toString());

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
		Object[] finderArgs = new Object[] { new Long(nodeId) };

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_NODEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				list = q.list();
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

	public List<WikiPage> findByNodeId(long nodeId, int start, int end)
		throws SystemException {
		return findByNodeId(nodeId, start, end, null);
	}

	public List<WikiPage> findByNodeId(long nodeId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_NODEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("wikiPage.");
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

				else {
					query.append("ORDER BY ");

					query.append("wikiPage.nodeId ASC, ");
					query.append("wikiPage.title ASC, ");
					query.append("wikiPage.version ASC");
				}

				Query q = session.createQuery(query.toString());

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

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_NODEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByNodeId_First(long nodeId, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByNodeId(nodeId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("nodeId=" + nodeId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByNodeId_Last(long nodeId, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		int count = countByNodeId(nodeId);

		List<WikiPage> list = findByNodeId(nodeId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("nodeId=" + nodeId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByNodeId_PrevAndNext(long pageId, long nodeId,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		int count = countByNodeId(nodeId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

			query.append("wikiPage.nodeId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("wikiPage.");
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

			else {
				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(nodeId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, wikiPage);

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = (WikiPage)objArray[0];
			array[1] = (WikiPage)objArray[1];
			array[2] = (WikiPage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<WikiPage> findByFormat(String format) throws SystemException {
		Object[] finderArgs = new Object[] { format };

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_FORMAT,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				if (format == null) {
					query.append("wikiPage.format IS NULL");
				}
				else {
					if (format.equals(StringPool.BLANK)) {
						query.append("(wikiPage.format IS NULL OR ");
					}

					query.append("wikiPage.format = ?");

					if (format.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (format != null) {
					qPos.add(format);
				}

				list = q.list();
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

	public List<WikiPage> findByFormat(String format, int start, int end)
		throws SystemException {
		return findByFormat(format, start, end, null);
	}

	public List<WikiPage> findByFormat(String format, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				format,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_FORMAT,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				if (format == null) {
					query.append("wikiPage.format IS NULL");
				}
				else {
					if (format.equals(StringPool.BLANK)) {
						query.append("(wikiPage.format IS NULL OR ");
					}

					query.append("wikiPage.format = ?");

					if (format.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("wikiPage.");
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

				else {
					query.append("ORDER BY ");

					query.append("wikiPage.nodeId ASC, ");
					query.append("wikiPage.title ASC, ");
					query.append("wikiPage.version ASC");
				}

				Query q = session.createQuery(query.toString());

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

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_FORMAT,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByFormat_First(String format, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByFormat(format, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("format=" + format);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByFormat_Last(String format, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		int count = countByFormat(format);

		List<WikiPage> list = findByFormat(format, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("format=" + format);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByFormat_PrevAndNext(long pageId, String format,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		int count = countByFormat(format);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

			if (format == null) {
				query.append("wikiPage.format IS NULL");
			}
			else {
				if (format.equals(StringPool.BLANK)) {
					query.append("(wikiPage.format IS NULL OR ");
				}

				query.append("wikiPage.format = ?");

				if (format.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("wikiPage.");
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

			else {
				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (format != null) {
				qPos.add(format);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, wikiPage);

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = (WikiPage)objArray[0];
			array[1] = (WikiPage)objArray[1];
			array[2] = (WikiPage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<WikiPage> findByN_T(long nodeId, String title)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(nodeId), title };

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_N_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				if (title == null) {
					query.append("wikiPage.title IS NULL");
				}
				else {
					if (title.equals(StringPool.BLANK)) {
						query.append("(wikiPage.title IS NULL OR ");
					}

					query.append("wikiPage.title = ?");

					if (title.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (title != null) {
					qPos.add(title);
				}

				list = q.list();
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

	public List<WikiPage> findByN_T(long nodeId, String title, int start,
		int end) throws SystemException {
		return findByN_T(nodeId, title, start, end, null);
	}

	public List<WikiPage> findByN_T(long nodeId, String title, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId),
				
				title,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_N_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				if (title == null) {
					query.append("wikiPage.title IS NULL");
				}
				else {
					if (title.equals(StringPool.BLANK)) {
						query.append("(wikiPage.title IS NULL OR ");
					}

					query.append("wikiPage.title = ?");

					if (title.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("wikiPage.");
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

				else {
					query.append("ORDER BY ");

					query.append("wikiPage.nodeId ASC, ");
					query.append("wikiPage.title ASC, ");
					query.append("wikiPage.version ASC");
				}

				Query q = session.createQuery(query.toString());

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

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_N_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByN_T_First(long nodeId, String title,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByN_T(nodeId, title, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("nodeId=" + nodeId);

			msg.append(", ");
			msg.append("title=" + title);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByN_T_Last(long nodeId, String title,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		int count = countByN_T(nodeId, title);

		List<WikiPage> list = findByN_T(nodeId, title, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("nodeId=" + nodeId);

			msg.append(", ");
			msg.append("title=" + title);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByN_T_PrevAndNext(long pageId, long nodeId,
		String title, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		int count = countByN_T(nodeId, title);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

			query.append("wikiPage.nodeId = ?");

			query.append(" AND ");

			if (title == null) {
				query.append("wikiPage.title IS NULL");
			}
			else {
				if (title.equals(StringPool.BLANK)) {
					query.append("(wikiPage.title IS NULL OR ");
				}

				query.append("wikiPage.title = ?");

				if (title.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("wikiPage.");
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

			else {
				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(nodeId);

			if (title != null) {
				qPos.add(title);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, wikiPage);

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = (WikiPage)objArray[0];
			array[1] = (WikiPage)objArray[1];
			array[2] = (WikiPage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<WikiPage> findByN_H(long nodeId, boolean head)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId), Boolean.valueOf(head)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_N_H,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				query.append("wikiPage.head = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				list = q.list();
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

	public List<WikiPage> findByN_H(long nodeId, boolean head, int start,
		int end) throws SystemException {
		return findByN_H(nodeId, head, start, end, null);
	}

	public List<WikiPage> findByN_H(long nodeId, boolean head, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId), Boolean.valueOf(head),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_N_H,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				query.append("wikiPage.head = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("wikiPage.");
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

				else {
					query.append("ORDER BY ");

					query.append("wikiPage.nodeId ASC, ");
					query.append("wikiPage.title ASC, ");
					query.append("wikiPage.version ASC");
				}

				Query q = session.createQuery(query.toString());

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

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_N_H,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByN_H_First(long nodeId, boolean head,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByN_H(nodeId, head, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("nodeId=" + nodeId);

			msg.append(", ");
			msg.append("head=" + head);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByN_H_Last(long nodeId, boolean head,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		int count = countByN_H(nodeId, head);

		List<WikiPage> list = findByN_H(nodeId, head, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("nodeId=" + nodeId);

			msg.append(", ");
			msg.append("head=" + head);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByN_H_PrevAndNext(long pageId, long nodeId,
		boolean head, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		int count = countByN_H(nodeId, head);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

			query.append("wikiPage.nodeId = ?");

			query.append(" AND ");

			query.append("wikiPage.head = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("wikiPage.");
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

			else {
				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(nodeId);

			qPos.add(head);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, wikiPage);

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = (WikiPage)objArray[0];
			array[1] = (WikiPage)objArray[1];
			array[2] = (WikiPage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<WikiPage> findByN_P(long nodeId, String parentTitle)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(nodeId), parentTitle };

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_N_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				if (parentTitle == null) {
					query.append("wikiPage.parentTitle IS NULL");
				}
				else {
					if (parentTitle.equals(StringPool.BLANK)) {
						query.append("(wikiPage.parentTitle IS NULL OR ");
					}

					query.append("wikiPage.parentTitle = ?");

					if (parentTitle.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (parentTitle != null) {
					qPos.add(parentTitle);
				}

				list = q.list();
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

	public List<WikiPage> findByN_P(long nodeId, String parentTitle, int start,
		int end) throws SystemException {
		return findByN_P(nodeId, parentTitle, start, end, null);
	}

	public List<WikiPage> findByN_P(long nodeId, String parentTitle, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId),
				
				parentTitle,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_N_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				if (parentTitle == null) {
					query.append("wikiPage.parentTitle IS NULL");
				}
				else {
					if (parentTitle.equals(StringPool.BLANK)) {
						query.append("(wikiPage.parentTitle IS NULL OR ");
					}

					query.append("wikiPage.parentTitle = ?");

					if (parentTitle.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("wikiPage.");
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

				else {
					query.append("ORDER BY ");

					query.append("wikiPage.nodeId ASC, ");
					query.append("wikiPage.title ASC, ");
					query.append("wikiPage.version ASC");
				}

				Query q = session.createQuery(query.toString());

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

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_N_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByN_P_First(long nodeId, String parentTitle,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByN_P(nodeId, parentTitle, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("nodeId=" + nodeId);

			msg.append(", ");
			msg.append("parentTitle=" + parentTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByN_P_Last(long nodeId, String parentTitle,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		int count = countByN_P(nodeId, parentTitle);

		List<WikiPage> list = findByN_P(nodeId, parentTitle, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("nodeId=" + nodeId);

			msg.append(", ");
			msg.append("parentTitle=" + parentTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByN_P_PrevAndNext(long pageId, long nodeId,
		String parentTitle, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		int count = countByN_P(nodeId, parentTitle);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

			query.append("wikiPage.nodeId = ?");

			query.append(" AND ");

			if (parentTitle == null) {
				query.append("wikiPage.parentTitle IS NULL");
			}
			else {
				if (parentTitle.equals(StringPool.BLANK)) {
					query.append("(wikiPage.parentTitle IS NULL OR ");
				}

				query.append("wikiPage.parentTitle = ?");

				if (parentTitle.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("wikiPage.");
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

			else {
				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(nodeId);

			if (parentTitle != null) {
				qPos.add(parentTitle);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, wikiPage);

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = (WikiPage)objArray[0];
			array[1] = (WikiPage)objArray[1];
			array[2] = (WikiPage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<WikiPage> findByN_R(long nodeId, String redirectTitle)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(nodeId), redirectTitle };

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_N_R,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				if (redirectTitle == null) {
					query.append("wikiPage.redirectTitle IS NULL");
				}
				else {
					if (redirectTitle.equals(StringPool.BLANK)) {
						query.append("(wikiPage.redirectTitle IS NULL OR ");
					}

					query.append("wikiPage.redirectTitle = ?");

					if (redirectTitle.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (redirectTitle != null) {
					qPos.add(redirectTitle);
				}

				list = q.list();
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

	public List<WikiPage> findByN_R(long nodeId, String redirectTitle,
		int start, int end) throws SystemException {
		return findByN_R(nodeId, redirectTitle, start, end, null);
	}

	public List<WikiPage> findByN_R(long nodeId, String redirectTitle,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId),
				
				redirectTitle,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_N_R,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				if (redirectTitle == null) {
					query.append("wikiPage.redirectTitle IS NULL");
				}
				else {
					if (redirectTitle.equals(StringPool.BLANK)) {
						query.append("(wikiPage.redirectTitle IS NULL OR ");
					}

					query.append("wikiPage.redirectTitle = ?");

					if (redirectTitle.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("wikiPage.");
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

				else {
					query.append("ORDER BY ");

					query.append("wikiPage.nodeId ASC, ");
					query.append("wikiPage.title ASC, ");
					query.append("wikiPage.version ASC");
				}

				Query q = session.createQuery(query.toString());

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

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_N_R,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByN_R_First(long nodeId, String redirectTitle,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByN_R(nodeId, redirectTitle, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("nodeId=" + nodeId);

			msg.append(", ");
			msg.append("redirectTitle=" + redirectTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByN_R_Last(long nodeId, String redirectTitle,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		int count = countByN_R(nodeId, redirectTitle);

		List<WikiPage> list = findByN_R(nodeId, redirectTitle, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("nodeId=" + nodeId);

			msg.append(", ");
			msg.append("redirectTitle=" + redirectTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByN_R_PrevAndNext(long pageId, long nodeId,
		String redirectTitle, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		int count = countByN_R(nodeId, redirectTitle);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

			query.append("wikiPage.nodeId = ?");

			query.append(" AND ");

			if (redirectTitle == null) {
				query.append("wikiPage.redirectTitle IS NULL");
			}
			else {
				if (redirectTitle.equals(StringPool.BLANK)) {
					query.append("(wikiPage.redirectTitle IS NULL OR ");
				}

				query.append("wikiPage.redirectTitle = ?");

				if (redirectTitle.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("wikiPage.");
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

			else {
				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(nodeId);

			if (redirectTitle != null) {
				qPos.add(redirectTitle);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, wikiPage);

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = (WikiPage)objArray[0];
			array[1] = (WikiPage)objArray[1];
			array[2] = (WikiPage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiPage findByN_T_V(long nodeId, String title, double version)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = fetchByN_T_V(nodeId, title, version);

		if (wikiPage == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("nodeId=" + nodeId);

			msg.append(", ");
			msg.append("title=" + title);

			msg.append(", ");
			msg.append("version=" + version);

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				if (title == null) {
					query.append("wikiPage.title IS NULL");
				}
				else {
					if (title.equals(StringPool.BLANK)) {
						query.append("(wikiPage.title IS NULL OR ");
					}

					query.append("wikiPage.title = ?");

					if (title.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("wikiPage.version = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");

				Query q = session.createQuery(query.toString());

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
		Object[] finderArgs = new Object[] {
				new Long(nodeId),
				
				title, Boolean.valueOf(head)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_N_T_H,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				if (title == null) {
					query.append("wikiPage.title IS NULL");
				}
				else {
					if (title.equals(StringPool.BLANK)) {
						query.append("(wikiPage.title IS NULL OR ");
					}

					query.append("wikiPage.title = ?");

					if (title.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("wikiPage.head = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (title != null) {
					qPos.add(title);
				}

				qPos.add(head);

				list = q.list();
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

	public List<WikiPage> findByN_T_H(long nodeId, String title, boolean head,
		int start, int end) throws SystemException {
		return findByN_T_H(nodeId, title, head, start, end, null);
	}

	public List<WikiPage> findByN_T_H(long nodeId, String title, boolean head,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId),
				
				title, Boolean.valueOf(head),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_N_T_H,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				if (title == null) {
					query.append("wikiPage.title IS NULL");
				}
				else {
					if (title.equals(StringPool.BLANK)) {
						query.append("(wikiPage.title IS NULL OR ");
					}

					query.append("wikiPage.title = ?");

					if (title.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("wikiPage.head = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("wikiPage.");
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

				else {
					query.append("ORDER BY ");

					query.append("wikiPage.nodeId ASC, ");
					query.append("wikiPage.title ASC, ");
					query.append("wikiPage.version ASC");
				}

				Query q = session.createQuery(query.toString());

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

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_N_T_H,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByN_T_H_First(long nodeId, String title, boolean head,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByN_T_H(nodeId, title, head, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("nodeId=" + nodeId);

			msg.append(", ");
			msg.append("title=" + title);

			msg.append(", ");
			msg.append("head=" + head);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByN_T_H_Last(long nodeId, String title, boolean head,
		OrderByComparator obc) throws NoSuchPageException, SystemException {
		int count = countByN_T_H(nodeId, title, head);

		List<WikiPage> list = findByN_T_H(nodeId, title, head, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("nodeId=" + nodeId);

			msg.append(", ");
			msg.append("title=" + title);

			msg.append(", ");
			msg.append("head=" + head);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByN_T_H_PrevAndNext(long pageId, long nodeId,
		String title, boolean head, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		int count = countByN_T_H(nodeId, title, head);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

			query.append("wikiPage.nodeId = ?");

			query.append(" AND ");

			if (title == null) {
				query.append("wikiPage.title IS NULL");
			}
			else {
				if (title.equals(StringPool.BLANK)) {
					query.append("(wikiPage.title IS NULL OR ");
				}

				query.append("wikiPage.title = ?");

				if (title.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" AND ");

			query.append("wikiPage.head = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("wikiPage.");
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

			else {
				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(nodeId);

			if (title != null) {
				qPos.add(title);
			}

			qPos.add(head);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, wikiPage);

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = (WikiPage)objArray[0];
			array[1] = (WikiPage)objArray[1];
			array[2] = (WikiPage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<WikiPage> findByN_H_P(long nodeId, boolean head,
		String parentTitle) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId), Boolean.valueOf(head),
				
				parentTitle
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_N_H_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				query.append("wikiPage.head = ?");

				query.append(" AND ");

				if (parentTitle == null) {
					query.append("wikiPage.parentTitle IS NULL");
				}
				else {
					if (parentTitle.equals(StringPool.BLANK)) {
						query.append("(wikiPage.parentTitle IS NULL OR ");
					}

					query.append("wikiPage.parentTitle = ?");

					if (parentTitle.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				if (parentTitle != null) {
					qPos.add(parentTitle);
				}

				list = q.list();
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

	public List<WikiPage> findByN_H_P(long nodeId, boolean head,
		String parentTitle, int start, int end) throws SystemException {
		return findByN_H_P(nodeId, head, parentTitle, start, end, null);
	}

	public List<WikiPage> findByN_H_P(long nodeId, boolean head,
		String parentTitle, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(nodeId), Boolean.valueOf(head),
				
				parentTitle,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_N_H_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				query.append("wikiPage.head = ?");

				query.append(" AND ");

				if (parentTitle == null) {
					query.append("wikiPage.parentTitle IS NULL");
				}
				else {
					if (parentTitle.equals(StringPool.BLANK)) {
						query.append("(wikiPage.parentTitle IS NULL OR ");
					}

					query.append("wikiPage.parentTitle = ?");

					if (parentTitle.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("wikiPage.");
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

				else {
					query.append("ORDER BY ");

					query.append("wikiPage.nodeId ASC, ");
					query.append("wikiPage.title ASC, ");
					query.append("wikiPage.version ASC");
				}

				Query q = session.createQuery(query.toString());

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

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_N_H_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiPage findByN_H_P_First(long nodeId, boolean head,
		String parentTitle, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		List<WikiPage> list = findByN_H_P(nodeId, head, parentTitle, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("nodeId=" + nodeId);

			msg.append(", ");
			msg.append("head=" + head);

			msg.append(", ");
			msg.append("parentTitle=" + parentTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage findByN_H_P_Last(long nodeId, boolean head,
		String parentTitle, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		int count = countByN_H_P(nodeId, head, parentTitle);

		List<WikiPage> list = findByN_H_P(nodeId, head, parentTitle, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiPage exists with the key {");

			msg.append("nodeId=" + nodeId);

			msg.append(", ");
			msg.append("head=" + head);

			msg.append(", ");
			msg.append("parentTitle=" + parentTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiPage[] findByN_H_P_PrevAndNext(long pageId, long nodeId,
		boolean head, String parentTitle, OrderByComparator obc)
		throws NoSuchPageException, SystemException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		int count = countByN_H_P(nodeId, head, parentTitle);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT wikiPage FROM WikiPage wikiPage WHERE ");

			query.append("wikiPage.nodeId = ?");

			query.append(" AND ");

			query.append("wikiPage.head = ?");

			query.append(" AND ");

			if (parentTitle == null) {
				query.append("wikiPage.parentTitle IS NULL");
			}
			else {
				if (parentTitle.equals(StringPool.BLANK)) {
					query.append("(wikiPage.parentTitle IS NULL OR ");
				}

				query.append("wikiPage.parentTitle = ?");

				if (parentTitle.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("wikiPage.");
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

			else {
				query.append("ORDER BY ");

				query.append("wikiPage.nodeId ASC, ");
				query.append("wikiPage.title ASC, ");
				query.append("wikiPage.version ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(nodeId);

			qPos.add(head);

			if (parentTitle != null) {
				qPos.add(parentTitle);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, wikiPage);

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = (WikiPage)objArray[0];
			array[1] = (WikiPage)objArray[1];
			array[2] = (WikiPage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
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

	public List<WikiPage> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<WikiPage> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<WikiPage> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WikiPage> list = (List<WikiPage>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiPage FROM WikiPage wikiPage ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("wikiPage.");
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

				else {
					query.append("ORDER BY ");

					query.append("wikiPage.nodeId ASC, ");
					query.append("wikiPage.title ASC, ");
					query.append("wikiPage.version ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(wikiPage) ");
				query.append("FROM WikiPage wikiPage WHERE ");

				if (uuid == null) {
					query.append("wikiPage.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(wikiPage.uuid IS NULL OR ");
					}

					query.append("wikiPage.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(wikiPage) ");
				query.append("FROM WikiPage wikiPage WHERE ");

				if (uuid == null) {
					query.append("wikiPage.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(wikiPage.uuid IS NULL OR ");
					}

					query.append("wikiPage.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("wikiPage.groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(wikiPage) ");
				query.append("FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(wikiPage) ");
				query.append("FROM WikiPage wikiPage WHERE ");

				if (format == null) {
					query.append("wikiPage.format IS NULL");
				}
				else {
					if (format.equals(StringPool.BLANK)) {
						query.append("(wikiPage.format IS NULL OR ");
					}

					query.append("wikiPage.format = ?");

					if (format.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(wikiPage) ");
				query.append("FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				if (title == null) {
					query.append("wikiPage.title IS NULL");
				}
				else {
					if (title.equals(StringPool.BLANK)) {
						query.append("(wikiPage.title IS NULL OR ");
					}

					query.append("wikiPage.title = ?");

					if (title.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(wikiPage) ");
				query.append("FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				query.append("wikiPage.head = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(wikiPage) ");
				query.append("FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				if (parentTitle == null) {
					query.append("wikiPage.parentTitle IS NULL");
				}
				else {
					if (parentTitle.equals(StringPool.BLANK)) {
						query.append("(wikiPage.parentTitle IS NULL OR ");
					}

					query.append("wikiPage.parentTitle = ?");

					if (parentTitle.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(wikiPage) ");
				query.append("FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				if (redirectTitle == null) {
					query.append("wikiPage.redirectTitle IS NULL");
				}
				else {
					if (redirectTitle.equals(StringPool.BLANK)) {
						query.append("(wikiPage.redirectTitle IS NULL OR ");
					}

					query.append("wikiPage.redirectTitle = ?");

					if (redirectTitle.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(wikiPage) ");
				query.append("FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				if (title == null) {
					query.append("wikiPage.title IS NULL");
				}
				else {
					if (title.equals(StringPool.BLANK)) {
						query.append("(wikiPage.title IS NULL OR ");
					}

					query.append("wikiPage.title = ?");

					if (title.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("wikiPage.version = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(wikiPage) ");
				query.append("FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				if (title == null) {
					query.append("wikiPage.title IS NULL");
				}
				else {
					if (title.equals(StringPool.BLANK)) {
						query.append("(wikiPage.title IS NULL OR ");
					}

					query.append("wikiPage.title = ?");

					if (title.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("wikiPage.head = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(wikiPage) ");
				query.append("FROM WikiPage wikiPage WHERE ");

				query.append("wikiPage.nodeId = ?");

				query.append(" AND ");

				query.append("wikiPage.head = ?");

				query.append(" AND ");

				if (parentTitle == null) {
					query.append("wikiPage.parentTitle IS NULL");
				}
				else {
					if (parentTitle.equals(StringPool.BLANK)) {
						query.append("(wikiPage.parentTitle IS NULL OR ");
					}

					query.append("wikiPage.parentTitle = ?");

					if (parentTitle.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				Query q = session.createQuery(
						"SELECT COUNT(wikiPage) FROM WikiPage wikiPage");

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

	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiNodePersistence")
	protected com.liferay.portlet.wiki.service.persistence.WikiNodePersistence wikiNodePersistence;
	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiPagePersistence")
	protected com.liferay.portlet.wiki.service.persistence.WikiPagePersistence wikiPagePersistence;
	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiPageResourcePersistence")
	protected com.liferay.portlet.wiki.service.persistence.WikiPageResourcePersistence wikiPageResourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetEntryPersistence assetEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetTagPersistence assetTagPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence mbMessagePersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence")
	protected com.liferay.portlet.social.service.persistence.SocialActivityPersistence socialActivityPersistence;
	private static Log _log = LogFactoryUtil.getLog(WikiPagePersistenceImpl.class);
}