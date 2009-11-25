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

package com.liferay.portlet.journal.service.persistence;

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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.journal.NoSuchContentSearchException;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.model.impl.JournalContentSearchImpl;
import com.liferay.portlet.journal.model.impl.JournalContentSearchModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="JournalContentSearchPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalContentSearchPersistence
 * @see       JournalContentSearchUtil
 * @generated
 */
public class JournalContentSearchPersistenceImpl extends BasePersistenceImpl<JournalContentSearch>
	implements JournalContentSearchPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = JournalContentSearchImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_ARTICLEID = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByArticleId",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_ARTICLEID = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByArticleId",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ARTICLEID = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByArticleId",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_P = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_P",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_P = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_P",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_A = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_A",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_A = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_A",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_P_L = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_P_L = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_L = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_P_A = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_P_A = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_A = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_P_L_P = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_P_L_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_P_L_P = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_P_L_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_L_P = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_P_L_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_P_L_P_A = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_P_L_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_L_P_A = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_P_L_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(JournalContentSearch journalContentSearch) {
		EntityCacheUtil.putResult(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			journalContentSearch.getPrimaryKey(), journalContentSearch);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L_P_A,
			new Object[] {
				new Long(journalContentSearch.getGroupId()),
				Boolean.valueOf(journalContentSearch.getPrivateLayout()),
				new Long(journalContentSearch.getLayoutId()),
				
			journalContentSearch.getPortletId(),
				
			journalContentSearch.getArticleId()
			}, journalContentSearch);
	}

	public void cacheResult(List<JournalContentSearch> journalContentSearchs) {
		for (JournalContentSearch journalContentSearch : journalContentSearchs) {
			if (EntityCacheUtil.getResult(
						JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
						JournalContentSearchImpl.class,
						journalContentSearch.getPrimaryKey(), this) == null) {
				cacheResult(journalContentSearch);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(JournalContentSearchImpl.class.getName());
		EntityCacheUtil.clearCache(JournalContentSearchImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public JournalContentSearch create(long contentSearchId) {
		JournalContentSearch journalContentSearch = new JournalContentSearchImpl();

		journalContentSearch.setNew(true);
		journalContentSearch.setPrimaryKey(contentSearchId);

		return journalContentSearch;
	}

	public JournalContentSearch remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public JournalContentSearch remove(long contentSearchId)
		throws NoSuchContentSearchException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalContentSearch journalContentSearch = (JournalContentSearch)session.get(JournalContentSearchImpl.class,
					new Long(contentSearchId));

			if (journalContentSearch == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No JournalContentSearch exists with the primary key " +
						contentSearchId);
				}

				throw new NoSuchContentSearchException(
					"No JournalContentSearch exists with the primary key " +
					contentSearchId);
			}

			return remove(journalContentSearch);
		}
		catch (NoSuchContentSearchException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalContentSearch remove(
		JournalContentSearch journalContentSearch) throws SystemException {
		for (ModelListener<JournalContentSearch> listener : listeners) {
			listener.onBeforeRemove(journalContentSearch);
		}

		journalContentSearch = removeImpl(journalContentSearch);

		for (ModelListener<JournalContentSearch> listener : listeners) {
			listener.onAfterRemove(journalContentSearch);
		}

		return journalContentSearch;
	}

	protected JournalContentSearch removeImpl(
		JournalContentSearch journalContentSearch) throws SystemException {
		journalContentSearch = toUnwrappedModel(journalContentSearch);

		Session session = null;

		try {
			session = openSession();

			if (journalContentSearch.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(JournalContentSearchImpl.class,
						journalContentSearch.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(journalContentSearch);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		JournalContentSearchModelImpl journalContentSearchModelImpl = (JournalContentSearchModelImpl)journalContentSearch;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_L_P_A,
			new Object[] {
				new Long(journalContentSearchModelImpl.getOriginalGroupId()),
				Boolean.valueOf(
					journalContentSearchModelImpl.getOriginalPrivateLayout()),
				new Long(journalContentSearchModelImpl.getOriginalLayoutId()),
				
			journalContentSearchModelImpl.getOriginalPortletId(),
				
			journalContentSearchModelImpl.getOriginalArticleId()
			});

		EntityCacheUtil.removeResult(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchImpl.class, journalContentSearch.getPrimaryKey());

		return journalContentSearch;
	}

	public JournalContentSearch updateImpl(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch,
		boolean merge) throws SystemException {
		journalContentSearch = toUnwrappedModel(journalContentSearch);

		boolean isNew = journalContentSearch.isNew();

		JournalContentSearchModelImpl journalContentSearchModelImpl = (JournalContentSearchModelImpl)journalContentSearch;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, journalContentSearch, merge);

			journalContentSearch.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			journalContentSearch.getPrimaryKey(), journalContentSearch);

		if (!isNew &&
				((journalContentSearch.getGroupId() != journalContentSearchModelImpl.getOriginalGroupId()) ||
				(journalContentSearch.getPrivateLayout() != journalContentSearchModelImpl.getOriginalPrivateLayout()) ||
				(journalContentSearch.getLayoutId() != journalContentSearchModelImpl.getOriginalLayoutId()) ||
				!Validator.equals(journalContentSearch.getPortletId(),
					journalContentSearchModelImpl.getOriginalPortletId()) ||
				!Validator.equals(journalContentSearch.getArticleId(),
					journalContentSearchModelImpl.getOriginalArticleId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_L_P_A,
				new Object[] {
					new Long(journalContentSearchModelImpl.getOriginalGroupId()),
					Boolean.valueOf(
						journalContentSearchModelImpl.getOriginalPrivateLayout()),
					new Long(journalContentSearchModelImpl.getOriginalLayoutId()),
					
				journalContentSearchModelImpl.getOriginalPortletId(),
					
				journalContentSearchModelImpl.getOriginalArticleId()
				});
		}

		if (isNew ||
				((journalContentSearch.getGroupId() != journalContentSearchModelImpl.getOriginalGroupId()) ||
				(journalContentSearch.getPrivateLayout() != journalContentSearchModelImpl.getOriginalPrivateLayout()) ||
				(journalContentSearch.getLayoutId() != journalContentSearchModelImpl.getOriginalLayoutId()) ||
				!Validator.equals(journalContentSearch.getPortletId(),
					journalContentSearchModelImpl.getOriginalPortletId()) ||
				!Validator.equals(journalContentSearch.getArticleId(),
					journalContentSearchModelImpl.getOriginalArticleId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L_P_A,
				new Object[] {
					new Long(journalContentSearch.getGroupId()),
					Boolean.valueOf(journalContentSearch.getPrivateLayout()),
					new Long(journalContentSearch.getLayoutId()),
					
				journalContentSearch.getPortletId(),
					
				journalContentSearch.getArticleId()
				}, journalContentSearch);
		}

		return journalContentSearch;
	}

	protected JournalContentSearch toUnwrappedModel(
		JournalContentSearch journalContentSearch) {
		if (journalContentSearch instanceof JournalContentSearchImpl) {
			return journalContentSearch;
		}

		JournalContentSearchImpl journalContentSearchImpl = new JournalContentSearchImpl();

		journalContentSearchImpl.setNew(journalContentSearch.isNew());
		journalContentSearchImpl.setPrimaryKey(journalContentSearch.getPrimaryKey());

		journalContentSearchImpl.setContentSearchId(journalContentSearch.getContentSearchId());
		journalContentSearchImpl.setGroupId(journalContentSearch.getGroupId());
		journalContentSearchImpl.setCompanyId(journalContentSearch.getCompanyId());
		journalContentSearchImpl.setPrivateLayout(journalContentSearch.isPrivateLayout());
		journalContentSearchImpl.setLayoutId(journalContentSearch.getLayoutId());
		journalContentSearchImpl.setPortletId(journalContentSearch.getPortletId());
		journalContentSearchImpl.setArticleId(journalContentSearch.getArticleId());

		return journalContentSearchImpl;
	}

	public JournalContentSearch findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JournalContentSearch findByPrimaryKey(long contentSearchId)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByPrimaryKey(contentSearchId);

		if (journalContentSearch == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No JournalContentSearch exists with the primary key " +
					contentSearchId);
			}

			throw new NoSuchContentSearchException(
				"No JournalContentSearch exists with the primary key " +
				contentSearchId);
		}

		return journalContentSearch;
	}

	public JournalContentSearch fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JournalContentSearch fetchByPrimaryKey(long contentSearchId)
		throws SystemException {
		JournalContentSearch journalContentSearch = (JournalContentSearch)EntityCacheUtil.getResult(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
				JournalContentSearchImpl.class, contentSearchId, this);

		if (journalContentSearch == null) {
			Session session = null;

			try {
				session = openSession();

				journalContentSearch = (JournalContentSearch)session.get(JournalContentSearchImpl.class,
						new Long(contentSearchId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (journalContentSearch != null) {
					cacheResult(journalContentSearch);
				}

				closeSession(session);
			}
		}

		return journalContentSearch;
	}

	public List<JournalContentSearch> findByArticleId(String articleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { articleId };

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ARTICLEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);
				query.append(
					"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

				if (articleId == null) {
					query.append("journalContentSearch.articleId IS NULL");
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(
							"(journalContentSearch.articleId IS NULL OR ");
					}

					query.append("journalContentSearch.articleId = ?");

					if (articleId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

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
					list = new ArrayList<JournalContentSearch>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ARTICLEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalContentSearch> findByArticleId(String articleId,
		int start, int end) throws SystemException {
		return findByArticleId(articleId, start, end, null);
	}

	public List<JournalContentSearch> findByArticleId(String articleId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				articleId,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ARTICLEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 6;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

				if (articleId == null) {
					query.append("journalContentSearch.articleId IS NULL");
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(
							"(journalContentSearch.articleId IS NULL OR ");
					}

					query.append("journalContentSearch.articleId = ?");

					if (articleId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalContentSearch.");
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

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (articleId != null) {
					qPos.add(articleId);
				}

				list = (List<JournalContentSearch>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalContentSearch>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_ARTICLEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalContentSearch findByArticleId_First(String articleId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List<JournalContentSearch> list = findByArticleId(articleId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No JournalContentSearch exists with the key {");
			msg.append("articleId=" + articleId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch findByArticleId_Last(String articleId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByArticleId(articleId);

		List<JournalContentSearch> list = findByArticleId(articleId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No JournalContentSearch exists with the key {");
			msg.append("articleId=" + articleId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch[] findByArticleId_PrevAndNext(
		long contentSearchId, String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);

		int count = countByArticleId(articleId);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 6;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append(
				"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

			if (articleId == null) {
				query.append("journalContentSearch.articleId IS NULL");
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append("(journalContentSearch.articleId IS NULL OR ");
				}

				query.append("journalContentSearch.articleId = ?");

				if (articleId.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalContentSearch.");
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

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (articleId != null) {
				qPos.add(articleId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch);

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = (JournalContentSearch)objArray[0];
			array[1] = (JournalContentSearch)objArray[1];
			array[2] = (JournalContentSearch)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalContentSearch> findByG_P(long groupId,
		boolean privateLayout) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout)
			};

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);
				query.append(
					"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

				query.append("journalContentSearch.groupId = ?");

				query.append(" AND ");

				query.append("journalContentSearch.privateLayout = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalContentSearch>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_P, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalContentSearch> findByG_P(long groupId,
		boolean privateLayout, int start, int end) throws SystemException {
		return findByG_P(groupId, privateLayout, start, end, null);
	}

	public List<JournalContentSearch> findByG_P(long groupId,
		boolean privateLayout, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 5;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

				query.append("journalContentSearch.groupId = ?");

				query.append(" AND ");

				query.append("journalContentSearch.privateLayout = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalContentSearch.");
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

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				list = (List<JournalContentSearch>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalContentSearch>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalContentSearch findByG_P_First(long groupId,
		boolean privateLayout, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List<JournalContentSearch> list = findByG_P(groupId, privateLayout, 0,
				1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(5);
			msg.append("No JournalContentSearch exists with the key {");
			msg.append("groupId=" + groupId);
			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch findByG_P_Last(long groupId,
		boolean privateLayout, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByG_P(groupId, privateLayout);

		List<JournalContentSearch> list = findByG_P(groupId, privateLayout,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(5);
			msg.append("No JournalContentSearch exists with the key {");
			msg.append("groupId=" + groupId);
			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch[] findByG_P_PrevAndNext(long contentSearchId,
		long groupId, boolean privateLayout, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);

		int count = countByG_P(groupId, privateLayout);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 5;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append(
				"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

			query.append("journalContentSearch.groupId = ?");

			query.append(" AND ");

			query.append("journalContentSearch.privateLayout = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalContentSearch.");
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

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch);

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = (JournalContentSearch)objArray[0];
			array[1] = (JournalContentSearch)objArray[1];
			array[2] = (JournalContentSearch)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalContentSearch> findByG_A(long groupId, String articleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), articleId };

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(8);
				query.append(
					"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

				query.append("journalContentSearch.groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalContentSearch.articleId IS NULL");
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(
							"(journalContentSearch.articleId IS NULL OR ");
					}

					query.append("journalContentSearch.articleId = ?");

					if (articleId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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
					list = new ArrayList<JournalContentSearch>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_A, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalContentSearch> findByG_A(long groupId, String articleId,
		int start, int end) throws SystemException {
		return findByG_A(groupId, articleId, start, end, null);
	}

	public List<JournalContentSearch> findByG_A(long groupId, String articleId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 8;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

				query.append("journalContentSearch.groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalContentSearch.articleId IS NULL");
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(
							"(journalContentSearch.articleId IS NULL OR ");
					}

					query.append("journalContentSearch.articleId = ?");

					if (articleId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalContentSearch.");
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

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				list = (List<JournalContentSearch>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalContentSearch>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_A,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalContentSearch findByG_A_First(long groupId, String articleId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List<JournalContentSearch> list = findByG_A(groupId, articleId, 0, 1,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(5);
			msg.append("No JournalContentSearch exists with the key {");
			msg.append("groupId=" + groupId);
			msg.append(", ");
			msg.append("articleId=" + articleId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch findByG_A_Last(long groupId, String articleId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByG_A(groupId, articleId);

		List<JournalContentSearch> list = findByG_A(groupId, articleId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(5);
			msg.append("No JournalContentSearch exists with the key {");
			msg.append("groupId=" + groupId);
			msg.append(", ");
			msg.append("articleId=" + articleId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch[] findByG_A_PrevAndNext(long contentSearchId,
		long groupId, String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);

		int count = countByG_A(groupId, articleId);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 8;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append(
				"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

			query.append("journalContentSearch.groupId = ?");

			query.append(" AND ");

			if (articleId == null) {
				query.append("journalContentSearch.articleId IS NULL");
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append("(journalContentSearch.articleId IS NULL OR ");
				}

				query.append("journalContentSearch.articleId = ?");

				if (articleId.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalContentSearch.");
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

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (articleId != null) {
				qPos.add(articleId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch);

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = (JournalContentSearch)objArray[0];
			array[1] = (JournalContentSearch)objArray[1];
			array[2] = (JournalContentSearch)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalContentSearch> findByG_P_L(long groupId,
		boolean privateLayout, long layoutId) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(layoutId)
			};

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_P_L,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(7);
				query.append(
					"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

				query.append("journalContentSearch.groupId = ?");

				query.append(" AND ");

				query.append("journalContentSearch.privateLayout = ?");

				query.append(" AND ");

				query.append("journalContentSearch.layoutId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalContentSearch>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_P_L,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalContentSearch> findByG_P_L(long groupId,
		boolean privateLayout, long layoutId, int start, int end)
		throws SystemException {
		return findByG_P_L(groupId, privateLayout, layoutId, start, end, null);
	}

	public List<JournalContentSearch> findByG_P_L(long groupId,
		boolean privateLayout, long layoutId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(layoutId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_P_L,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 7;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

				query.append("journalContentSearch.groupId = ?");

				query.append(" AND ");

				query.append("journalContentSearch.privateLayout = ?");

				query.append(" AND ");

				query.append("journalContentSearch.layoutId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalContentSearch.");
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

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				list = (List<JournalContentSearch>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalContentSearch>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_P_L,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalContentSearch findByG_P_L_First(long groupId,
		boolean privateLayout, long layoutId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List<JournalContentSearch> list = findByG_P_L(groupId, privateLayout,
				layoutId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(7);
			msg.append("No JournalContentSearch exists with the key {");
			msg.append("groupId=" + groupId);
			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);
			msg.append(", ");
			msg.append("layoutId=" + layoutId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch findByG_P_L_Last(long groupId,
		boolean privateLayout, long layoutId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByG_P_L(groupId, privateLayout, layoutId);

		List<JournalContentSearch> list = findByG_P_L(groupId, privateLayout,
				layoutId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(7);
			msg.append("No JournalContentSearch exists with the key {");
			msg.append("groupId=" + groupId);
			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);
			msg.append(", ");
			msg.append("layoutId=" + layoutId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch[] findByG_P_L_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		long layoutId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);

		int count = countByG_P_L(groupId, privateLayout, layoutId);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 7;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append(
				"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

			query.append("journalContentSearch.groupId = ?");

			query.append(" AND ");

			query.append("journalContentSearch.privateLayout = ?");

			query.append(" AND ");

			query.append("journalContentSearch.layoutId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalContentSearch.");
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

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			qPos.add(layoutId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch);

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = (JournalContentSearch)objArray[0];
			array[1] = (JournalContentSearch)objArray[1];
			array[2] = (JournalContentSearch)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalContentSearch> findByG_P_A(long groupId,
		boolean privateLayout, String articleId) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				
				articleId
			};

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_P_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(10);
				query.append(
					"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

				query.append("journalContentSearch.groupId = ?");

				query.append(" AND ");

				query.append("journalContentSearch.privateLayout = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalContentSearch.articleId IS NULL");
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(
							"(journalContentSearch.articleId IS NULL OR ");
					}

					query.append("journalContentSearch.articleId = ?");

					if (articleId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

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
					list = new ArrayList<JournalContentSearch>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_P_A,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalContentSearch> findByG_P_A(long groupId,
		boolean privateLayout, String articleId, int start, int end)
		throws SystemException {
		return findByG_P_A(groupId, privateLayout, articleId, start, end, null);
	}

	public List<JournalContentSearch> findByG_P_A(long groupId,
		boolean privateLayout, String articleId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				
				articleId,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_P_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 10;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

				query.append("journalContentSearch.groupId = ?");

				query.append(" AND ");

				query.append("journalContentSearch.privateLayout = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalContentSearch.articleId IS NULL");
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(
							"(journalContentSearch.articleId IS NULL OR ");
					}

					query.append("journalContentSearch.articleId = ?");

					if (articleId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalContentSearch.");
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

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (articleId != null) {
					qPos.add(articleId);
				}

				list = (List<JournalContentSearch>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalContentSearch>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_P_A,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalContentSearch findByG_P_A_First(long groupId,
		boolean privateLayout, String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List<JournalContentSearch> list = findByG_P_A(groupId, privateLayout,
				articleId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(7);
			msg.append("No JournalContentSearch exists with the key {");
			msg.append("groupId=" + groupId);
			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);
			msg.append(", ");
			msg.append("articleId=" + articleId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch findByG_P_A_Last(long groupId,
		boolean privateLayout, String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByG_P_A(groupId, privateLayout, articleId);

		List<JournalContentSearch> list = findByG_P_A(groupId, privateLayout,
				articleId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(7);
			msg.append("No JournalContentSearch exists with the key {");
			msg.append("groupId=" + groupId);
			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);
			msg.append(", ");
			msg.append("articleId=" + articleId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch[] findByG_P_A_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);

		int count = countByG_P_A(groupId, privateLayout, articleId);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 10;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append(
				"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

			query.append("journalContentSearch.groupId = ?");

			query.append(" AND ");

			query.append("journalContentSearch.privateLayout = ?");

			query.append(" AND ");

			if (articleId == null) {
				query.append("journalContentSearch.articleId IS NULL");
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append("(journalContentSearch.articleId IS NULL OR ");
				}

				query.append("journalContentSearch.articleId = ?");

				if (articleId.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalContentSearch.");
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

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			if (articleId != null) {
				qPos.add(articleId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch);

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = (JournalContentSearch)objArray[0];
			array[1] = (JournalContentSearch)objArray[1];
			array[2] = (JournalContentSearch)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalContentSearch> findByG_P_L_P(long groupId,
		boolean privateLayout, long layoutId, String portletId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(layoutId),
				
				portletId
			};

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_P_L_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(12);
				query.append(
					"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

				query.append("journalContentSearch.groupId = ?");

				query.append(" AND ");

				query.append("journalContentSearch.privateLayout = ?");

				query.append(" AND ");

				query.append("journalContentSearch.layoutId = ?");

				query.append(" AND ");

				if (portletId == null) {
					query.append("journalContentSearch.portletId IS NULL");
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(
							"(journalContentSearch.portletId IS NULL OR ");
					}

					query.append("journalContentSearch.portletId = ?");

					if (portletId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalContentSearch>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_P_L_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalContentSearch> findByG_P_L_P(long groupId,
		boolean privateLayout, long layoutId, String portletId, int start,
		int end) throws SystemException {
		return findByG_P_L_P(groupId, privateLayout, layoutId, portletId,
			start, end, null);
	}

	public List<JournalContentSearch> findByG_P_L_P(long groupId,
		boolean privateLayout, long layoutId, String portletId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(layoutId),
				
				portletId,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_P_L_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 12;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

				query.append("journalContentSearch.groupId = ?");

				query.append(" AND ");

				query.append("journalContentSearch.privateLayout = ?");

				query.append(" AND ");

				query.append("journalContentSearch.layoutId = ?");

				query.append(" AND ");

				if (portletId == null) {
					query.append("journalContentSearch.portletId IS NULL");
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(
							"(journalContentSearch.portletId IS NULL OR ");
					}

					query.append("journalContentSearch.portletId = ?");

					if (portletId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalContentSearch.");
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

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				list = (List<JournalContentSearch>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalContentSearch>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_P_L_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalContentSearch findByG_P_L_P_First(long groupId,
		boolean privateLayout, long layoutId, String portletId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List<JournalContentSearch> list = findByG_P_L_P(groupId, privateLayout,
				layoutId, portletId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(9);
			msg.append("No JournalContentSearch exists with the key {");
			msg.append("groupId=" + groupId);
			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);
			msg.append(", ");
			msg.append("layoutId=" + layoutId);
			msg.append(", ");
			msg.append("portletId=" + portletId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch findByG_P_L_P_Last(long groupId,
		boolean privateLayout, long layoutId, String portletId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByG_P_L_P(groupId, privateLayout, layoutId, portletId);

		List<JournalContentSearch> list = findByG_P_L_P(groupId, privateLayout,
				layoutId, portletId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(9);
			msg.append("No JournalContentSearch exists with the key {");
			msg.append("groupId=" + groupId);
			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);
			msg.append(", ");
			msg.append("layoutId=" + layoutId);
			msg.append(", ");
			msg.append("portletId=" + portletId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch[] findByG_P_L_P_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		long layoutId, String portletId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);

		int count = countByG_P_L_P(groupId, privateLayout, layoutId, portletId);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 12;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append(
				"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

			query.append("journalContentSearch.groupId = ?");

			query.append(" AND ");

			query.append("journalContentSearch.privateLayout = ?");

			query.append(" AND ");

			query.append("journalContentSearch.layoutId = ?");

			query.append(" AND ");

			if (portletId == null) {
				query.append("journalContentSearch.portletId IS NULL");
			}
			else {
				if (portletId.equals(StringPool.BLANK)) {
					query.append("(journalContentSearch.portletId IS NULL OR ");
				}

				query.append("journalContentSearch.portletId = ?");

				if (portletId.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalContentSearch.");
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

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			qPos.add(layoutId);

			if (portletId != null) {
				qPos.add(portletId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch);

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = (JournalContentSearch)objArray[0];
			array[1] = (JournalContentSearch)objArray[1];
			array[2] = (JournalContentSearch)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalContentSearch findByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, String portletId, String articleId)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByG_P_L_P_A(groupId,
				privateLayout, layoutId, portletId, articleId);

		if (journalContentSearch == null) {
			StringBundler msg = new StringBundler(11);
			msg.append("No JournalContentSearch exists with the key {");
			msg.append("groupId=" + groupId);
			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);
			msg.append(", ");
			msg.append("layoutId=" + layoutId);
			msg.append(", ");
			msg.append("portletId=" + portletId);
			msg.append(", ");
			msg.append("articleId=" + articleId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchContentSearchException(msg.toString());
		}

		return journalContentSearch;
	}

	public JournalContentSearch fetchByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, String portletId, String articleId)
		throws SystemException {
		return fetchByG_P_L_P_A(groupId, privateLayout, layoutId, portletId,
			articleId, true);
	}

	public JournalContentSearch fetchByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, String portletId,
		String articleId, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(layoutId),
				
				portletId,
				
				articleId
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_P_L_P_A,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(17);
				query.append(
					"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ");

				query.append("journalContentSearch.groupId = ?");

				query.append(" AND ");

				query.append("journalContentSearch.privateLayout = ?");

				query.append(" AND ");

				query.append("journalContentSearch.layoutId = ?");

				query.append(" AND ");

				if (portletId == null) {
					query.append("journalContentSearch.portletId IS NULL");
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(
							"(journalContentSearch.portletId IS NULL OR ");
					}

					query.append("journalContentSearch.portletId = ?");

					if (portletId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalContentSearch.articleId IS NULL");
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(
							"(journalContentSearch.articleId IS NULL OR ");
					}

					query.append("journalContentSearch.articleId = ?");

					if (articleId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				if (articleId != null) {
					qPos.add(articleId);
				}

				List<JournalContentSearch> list = q.list();

				result = list;

				JournalContentSearch journalContentSearch = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L_P_A,
						finderArgs, list);
				}
				else {
					journalContentSearch = list.get(0);

					cacheResult(journalContentSearch);

					if ((journalContentSearch.getGroupId() != groupId) ||
							(journalContentSearch.getPrivateLayout() != privateLayout) ||
							(journalContentSearch.getLayoutId() != layoutId) ||
							(journalContentSearch.getPortletId() == null) ||
							!journalContentSearch.getPortletId()
													 .equals(portletId) ||
							(journalContentSearch.getArticleId() == null) ||
							!journalContentSearch.getArticleId()
													 .equals(articleId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L_P_A,
							finderArgs, journalContentSearch);
					}
				}

				return journalContentSearch;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L_P_A,
						finderArgs, new ArrayList<JournalContentSearch>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (JournalContentSearch)result;
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

	public List<JournalContentSearch> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalContentSearch> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<JournalContentSearch> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 1;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalContentSearch.");
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

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<JournalContentSearch>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<JournalContentSearch>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalContentSearch>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByArticleId(String articleId) throws SystemException {
		for (JournalContentSearch journalContentSearch : findByArticleId(
				articleId)) {
			remove(journalContentSearch);
		}
	}

	public void removeByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		for (JournalContentSearch journalContentSearch : findByG_P(groupId,
				privateLayout)) {
			remove(journalContentSearch);
		}
	}

	public void removeByG_A(long groupId, String articleId)
		throws SystemException {
		for (JournalContentSearch journalContentSearch : findByG_A(groupId,
				articleId)) {
			remove(journalContentSearch);
		}
	}

	public void removeByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws SystemException {
		for (JournalContentSearch journalContentSearch : findByG_P_L(groupId,
				privateLayout, layoutId)) {
			remove(journalContentSearch);
		}
	}

	public void removeByG_P_A(long groupId, boolean privateLayout,
		String articleId) throws SystemException {
		for (JournalContentSearch journalContentSearch : findByG_P_A(groupId,
				privateLayout, articleId)) {
			remove(journalContentSearch);
		}
	}

	public void removeByG_P_L_P(long groupId, boolean privateLayout,
		long layoutId, String portletId) throws SystemException {
		for (JournalContentSearch journalContentSearch : findByG_P_L_P(
				groupId, privateLayout, layoutId, portletId)) {
			remove(journalContentSearch);
		}
	}

	public void removeByG_P_L_P_A(long groupId, boolean privateLayout,
		long layoutId, String portletId, String articleId)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByG_P_L_P_A(groupId,
				privateLayout, layoutId, portletId, articleId);

		remove(journalContentSearch);
	}

	public void removeAll() throws SystemException {
		for (JournalContentSearch journalContentSearch : findAll()) {
			remove(journalContentSearch);
		}
	}

	public int countByArticleId(String articleId) throws SystemException {
		Object[] finderArgs = new Object[] { articleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ARTICLEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(7);
				query.append("SELECT COUNT(journalContentSearch) ");
				query.append(
					"FROM JournalContentSearch journalContentSearch WHERE ");

				if (articleId == null) {
					query.append("journalContentSearch.articleId IS NULL");
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(
							"(journalContentSearch.articleId IS NULL OR ");
					}

					query.append("journalContentSearch.articleId = ?");

					if (articleId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ARTICLEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);
				query.append("SELECT COUNT(journalContentSearch) ");
				query.append(
					"FROM JournalContentSearch journalContentSearch WHERE ");

				query.append("journalContentSearch.groupId = ?");

				query.append(" AND ");

				query.append("journalContentSearch.privateLayout = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P, finderArgs,
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

				StringBundler query = new StringBundler(9);
				query.append("SELECT COUNT(journalContentSearch) ");
				query.append(
					"FROM JournalContentSearch journalContentSearch WHERE ");

				query.append("journalContentSearch.groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalContentSearch.articleId IS NULL");
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(
							"(journalContentSearch.articleId IS NULL OR ");
					}

					query.append("journalContentSearch.articleId = ?");

					if (articleId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

	public int countByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(layoutId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_L,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(8);
				query.append("SELECT COUNT(journalContentSearch) ");
				query.append(
					"FROM JournalContentSearch journalContentSearch WHERE ");

				query.append("journalContentSearch.groupId = ?");

				query.append(" AND ");

				query.append("journalContentSearch.privateLayout = ?");

				query.append(" AND ");

				query.append("journalContentSearch.layoutId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_L,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_P_A(long groupId, boolean privateLayout,
		String articleId) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				
				articleId
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_A,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(11);
				query.append("SELECT COUNT(journalContentSearch) ");
				query.append(
					"FROM JournalContentSearch journalContentSearch WHERE ");

				query.append("journalContentSearch.groupId = ?");

				query.append(" AND ");

				query.append("journalContentSearch.privateLayout = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalContentSearch.articleId IS NULL");
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(
							"(journalContentSearch.articleId IS NULL OR ");
					}

					query.append("journalContentSearch.articleId = ?");

					if (articleId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_A,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_P_L_P(long groupId, boolean privateLayout,
		long layoutId, String portletId) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(layoutId),
				
				portletId
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_L_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(13);
				query.append("SELECT COUNT(journalContentSearch) ");
				query.append(
					"FROM JournalContentSearch journalContentSearch WHERE ");

				query.append("journalContentSearch.groupId = ?");

				query.append(" AND ");

				query.append("journalContentSearch.privateLayout = ?");

				query.append(" AND ");

				query.append("journalContentSearch.layoutId = ?");

				query.append(" AND ");

				if (portletId == null) {
					query.append("journalContentSearch.portletId IS NULL");
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(
							"(journalContentSearch.portletId IS NULL OR ");
					}

					query.append("journalContentSearch.portletId = ?");

					if (portletId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				if (portletId != null) {
					qPos.add(portletId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_L_P,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_P_L_P_A(long groupId, boolean privateLayout,
		long layoutId, String portletId, String articleId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(layoutId),
				
				portletId,
				
				articleId
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_L_P_A,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(18);
				query.append("SELECT COUNT(journalContentSearch) ");
				query.append(
					"FROM JournalContentSearch journalContentSearch WHERE ");

				query.append("journalContentSearch.groupId = ?");

				query.append(" AND ");

				query.append("journalContentSearch.privateLayout = ?");

				query.append(" AND ");

				query.append("journalContentSearch.layoutId = ?");

				query.append(" AND ");

				if (portletId == null) {
					query.append("journalContentSearch.portletId IS NULL");
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(
							"(journalContentSearch.portletId IS NULL OR ");
					}

					query.append("journalContentSearch.portletId = ?");

					if (portletId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalContentSearch.articleId IS NULL");
				}
				else {
					if (articleId.equals(StringPool.BLANK)) {
						query.append(
							"(journalContentSearch.articleId IS NULL OR ");
					}

					query.append("journalContentSearch.articleId = ?");

					if (articleId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				if (portletId != null) {
					qPos.add(portletId);
				}

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_L_P_A,
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
						"SELECT COUNT(journalContentSearch) FROM JournalContentSearch journalContentSearch");

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
						"value.object.listener.com.liferay.portlet.journal.model.JournalContentSearch")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalContentSearch>> listenersList = new ArrayList<ModelListener<JournalContentSearch>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalContentSearch>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticlePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalArticlePersistence journalArticlePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence journalArticleImagePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence journalContentSearchPersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalFeedPersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalFeedPersistence journalFeedPersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalStructurePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalStructurePersistence journalStructurePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence journalTemplatePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(JournalContentSearchPersistenceImpl.class);
}