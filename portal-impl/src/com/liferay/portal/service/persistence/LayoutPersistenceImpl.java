/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchLayoutException;
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
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.LayoutModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="LayoutPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutPersistence
 * @see       LayoutUtil
 * @generated
 */
public class LayoutPersistenceImpl extends BasePersistenceImpl<Layout>
	implements LayoutPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_DLFOLDERID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByDLFolderId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_DLFOLDERID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByDLFolderId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_ICONIMAGEID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByIconImageId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_ICONIMAGEID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByIconImageId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_P",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_P",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_P_L = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_L = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_P_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_P_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_P_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_P_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_P_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_P_F = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_P_F",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_F = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_P_F",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_P_T = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_P_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_P_T = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_P_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_T = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_P_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Layout layout) {
		EntityCacheUtil.putResult(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutImpl.class, layout.getPrimaryKey(), layout);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DLFOLDERID,
			new Object[] { new Long(layout.getDlFolderId()) }, layout);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ICONIMAGEID,
			new Object[] { new Long(layout.getIconImageId()) }, layout);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L,
			new Object[] {
				new Long(layout.getGroupId()),
				Boolean.valueOf(layout.getPrivateLayout()),
				new Long(layout.getLayoutId())
			}, layout);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_F,
			new Object[] {
				new Long(layout.getGroupId()),
				Boolean.valueOf(layout.getPrivateLayout()),
				
			layout.getFriendlyURL()
			}, layout);
	}

	public void cacheResult(List<Layout> layouts) {
		for (Layout layout : layouts) {
			if (EntityCacheUtil.getResult(
						LayoutModelImpl.ENTITY_CACHE_ENABLED, LayoutImpl.class,
						layout.getPrimaryKey(), this) == null) {
				cacheResult(layout);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(LayoutImpl.class.getName());
		EntityCacheUtil.clearCache(LayoutImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public Layout create(long plid) {
		Layout layout = new LayoutImpl();

		layout.setNew(true);
		layout.setPrimaryKey(plid);

		return layout;
	}

	public Layout remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public Layout remove(long plid)
		throws NoSuchLayoutException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Layout layout = (Layout)session.get(LayoutImpl.class, new Long(plid));

			if (layout == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + plid);
				}

				throw new NoSuchLayoutException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					plid);
			}

			return remove(layout);
		}
		catch (NoSuchLayoutException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Layout remove(Layout layout) throws SystemException {
		for (ModelListener<Layout> listener : listeners) {
			listener.onBeforeRemove(layout);
		}

		layout = removeImpl(layout);

		for (ModelListener<Layout> listener : listeners) {
			listener.onAfterRemove(layout);
		}

		return layout;
	}

	protected Layout removeImpl(Layout layout) throws SystemException {
		layout = toUnwrappedModel(layout);

		Session session = null;

		try {
			session = openSession();

			if (layout.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(LayoutImpl.class,
						layout.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(layout);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		LayoutModelImpl layoutModelImpl = (LayoutModelImpl)layout;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_DLFOLDERID,
			new Object[] { new Long(layoutModelImpl.getOriginalDlFolderId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ICONIMAGEID,
			new Object[] { new Long(layoutModelImpl.getOriginalIconImageId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_L,
			new Object[] {
				new Long(layoutModelImpl.getOriginalGroupId()),
				Boolean.valueOf(layoutModelImpl.getOriginalPrivateLayout()),
				new Long(layoutModelImpl.getOriginalLayoutId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_F,
			new Object[] {
				new Long(layoutModelImpl.getOriginalGroupId()),
				Boolean.valueOf(layoutModelImpl.getOriginalPrivateLayout()),
				
			layoutModelImpl.getOriginalFriendlyURL()
			});

		EntityCacheUtil.removeResult(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutImpl.class, layout.getPrimaryKey());

		return layout;
	}

	public Layout updateImpl(com.liferay.portal.model.Layout layout,
		boolean merge) throws SystemException {
		layout = toUnwrappedModel(layout);

		boolean isNew = layout.isNew();

		LayoutModelImpl layoutModelImpl = (LayoutModelImpl)layout;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, layout, merge);

			layout.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutImpl.class, layout.getPrimaryKey(), layout);

		if (!isNew &&
				(layout.getDlFolderId() != layoutModelImpl.getOriginalDlFolderId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_DLFOLDERID,
				new Object[] { new Long(layoutModelImpl.getOriginalDlFolderId()) });
		}

		if (isNew ||
				(layout.getDlFolderId() != layoutModelImpl.getOriginalDlFolderId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DLFOLDERID,
				new Object[] { new Long(layout.getDlFolderId()) }, layout);
		}

		if (!isNew &&
				(layout.getIconImageId() != layoutModelImpl.getOriginalIconImageId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ICONIMAGEID,
				new Object[] { new Long(layoutModelImpl.getOriginalIconImageId()) });
		}

		if (isNew ||
				(layout.getIconImageId() != layoutModelImpl.getOriginalIconImageId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ICONIMAGEID,
				new Object[] { new Long(layout.getIconImageId()) }, layout);
		}

		if (!isNew &&
				((layout.getGroupId() != layoutModelImpl.getOriginalGroupId()) ||
				(layout.getPrivateLayout() != layoutModelImpl.getOriginalPrivateLayout()) ||
				(layout.getLayoutId() != layoutModelImpl.getOriginalLayoutId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_L,
				new Object[] {
					new Long(layoutModelImpl.getOriginalGroupId()),
					Boolean.valueOf(layoutModelImpl.getOriginalPrivateLayout()),
					new Long(layoutModelImpl.getOriginalLayoutId())
				});
		}

		if (isNew ||
				((layout.getGroupId() != layoutModelImpl.getOriginalGroupId()) ||
				(layout.getPrivateLayout() != layoutModelImpl.getOriginalPrivateLayout()) ||
				(layout.getLayoutId() != layoutModelImpl.getOriginalLayoutId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L,
				new Object[] {
					new Long(layout.getGroupId()),
					Boolean.valueOf(layout.getPrivateLayout()),
					new Long(layout.getLayoutId())
				}, layout);
		}

		if (!isNew &&
				((layout.getGroupId() != layoutModelImpl.getOriginalGroupId()) ||
				(layout.getPrivateLayout() != layoutModelImpl.getOriginalPrivateLayout()) ||
				!Validator.equals(layout.getFriendlyURL(),
					layoutModelImpl.getOriginalFriendlyURL()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_F,
				new Object[] {
					new Long(layoutModelImpl.getOriginalGroupId()),
					Boolean.valueOf(layoutModelImpl.getOriginalPrivateLayout()),
					
				layoutModelImpl.getOriginalFriendlyURL()
				});
		}

		if (isNew ||
				((layout.getGroupId() != layoutModelImpl.getOriginalGroupId()) ||
				(layout.getPrivateLayout() != layoutModelImpl.getOriginalPrivateLayout()) ||
				!Validator.equals(layout.getFriendlyURL(),
					layoutModelImpl.getOriginalFriendlyURL()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_F,
				new Object[] {
					new Long(layout.getGroupId()),
					Boolean.valueOf(layout.getPrivateLayout()),
					
				layout.getFriendlyURL()
				}, layout);
		}

		return layout;
	}

	protected Layout toUnwrappedModel(Layout layout) {
		if (layout instanceof LayoutImpl) {
			return layout;
		}

		LayoutImpl layoutImpl = new LayoutImpl();

		layoutImpl.setNew(layout.isNew());
		layoutImpl.setPrimaryKey(layout.getPrimaryKey());

		layoutImpl.setPlid(layout.getPlid());
		layoutImpl.setGroupId(layout.getGroupId());
		layoutImpl.setCompanyId(layout.getCompanyId());
		layoutImpl.setPrivateLayout(layout.isPrivateLayout());
		layoutImpl.setLayoutId(layout.getLayoutId());
		layoutImpl.setParentLayoutId(layout.getParentLayoutId());
		layoutImpl.setName(layout.getName());
		layoutImpl.setTitle(layout.getTitle());
		layoutImpl.setDescription(layout.getDescription());
		layoutImpl.setType(layout.getType());
		layoutImpl.setTypeSettings(layout.getTypeSettings());
		layoutImpl.setHidden(layout.isHidden());
		layoutImpl.setFriendlyURL(layout.getFriendlyURL());
		layoutImpl.setIconImage(layout.isIconImage());
		layoutImpl.setIconImageId(layout.getIconImageId());
		layoutImpl.setThemeId(layout.getThemeId());
		layoutImpl.setColorSchemeId(layout.getColorSchemeId());
		layoutImpl.setWapThemeId(layout.getWapThemeId());
		layoutImpl.setWapColorSchemeId(layout.getWapColorSchemeId());
		layoutImpl.setCss(layout.getCss());
		layoutImpl.setPriority(layout.getPriority());
		layoutImpl.setLayoutPrototypeId(layout.getLayoutPrototypeId());
		layoutImpl.setDlFolderId(layout.getDlFolderId());

		return layoutImpl;
	}

	public Layout findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Layout findByPrimaryKey(long plid)
		throws NoSuchLayoutException, SystemException {
		Layout layout = fetchByPrimaryKey(plid);

		if (layout == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + plid);
			}

			throw new NoSuchLayoutException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				plid);
		}

		return layout;
	}

	public Layout fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Layout fetchByPrimaryKey(long plid) throws SystemException {
		Layout layout = (Layout)EntityCacheUtil.getResult(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutImpl.class, plid, this);

		if (layout == null) {
			Session session = null;

			try {
				session = openSession();

				layout = (Layout)session.get(LayoutImpl.class, new Long(plid));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (layout != null) {
					cacheResult(layout);
				}

				closeSession(session);
			}
		}

		return layout;
	}

	public List<Layout> findByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				query.append(LayoutModelImpl.ORDER_BY_JPQL);

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
					list = new ArrayList<Layout>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Layout> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<Layout> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(3 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(LayoutModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<Layout>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Layout>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Layout findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		List<Layout> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Layout findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		int count = countByGroupId(groupId);

		List<Layout> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Layout[] findByGroupId_PrevAndNext(long plid, long groupId,
		OrderByComparator obc) throws NoSuchLayoutException, SystemException {
		Layout layout = findByPrimaryKey(plid);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, layout);

			Layout[] array = new LayoutImpl[3];

			array[0] = (Layout)objArray[0];
			array[1] = (Layout)objArray[1];
			array[2] = (Layout)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Layout> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				query.append(LayoutModelImpl.ORDER_BY_JPQL);

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
					list = new ArrayList<Layout>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Layout> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<Layout> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(3 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(LayoutModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<Layout>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Layout>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Layout findByCompanyId_First(long companyId, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		List<Layout> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Layout findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		int count = countByCompanyId(companyId);

		List<Layout> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Layout[] findByCompanyId_PrevAndNext(long plid, long companyId,
		OrderByComparator obc) throws NoSuchLayoutException, SystemException {
		Layout layout = findByPrimaryKey(plid);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, layout);

			Layout[] array = new LayoutImpl[3];

			array[0] = (Layout)objArray[0];
			array[1] = (Layout)objArray[1];
			array[2] = (Layout)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Layout findByDLFolderId(long dlFolderId)
		throws NoSuchLayoutException, SystemException {
		Layout layout = fetchByDLFolderId(dlFolderId);

		if (layout == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("dlFolderId=");
			msg.append(dlFolderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutException(msg.toString());
		}

		return layout;
	}

	public Layout fetchByDLFolderId(long dlFolderId) throws SystemException {
		return fetchByDLFolderId(dlFolderId, true);
	}

	public Layout fetchByDLFolderId(long dlFolderId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(dlFolderId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_DLFOLDERID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_DLFOLDERID_DLFOLDERID_2);

				query.append(LayoutModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(dlFolderId);

				List<Layout> list = q.list();

				result = list;

				Layout layout = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DLFOLDERID,
						finderArgs, list);
				}
				else {
					layout = list.get(0);

					cacheResult(layout);

					if ((layout.getDlFolderId() != dlFolderId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DLFOLDERID,
							finderArgs, layout);
					}
				}

				return layout;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DLFOLDERID,
						finderArgs, new ArrayList<Layout>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Layout)result;
			}
		}
	}

	public Layout findByIconImageId(long iconImageId)
		throws NoSuchLayoutException, SystemException {
		Layout layout = fetchByIconImageId(iconImageId);

		if (layout == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("iconImageId=");
			msg.append(iconImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutException(msg.toString());
		}

		return layout;
	}

	public Layout fetchByIconImageId(long iconImageId)
		throws SystemException {
		return fetchByIconImageId(iconImageId, true);
	}

	public Layout fetchByIconImageId(long iconImageId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(iconImageId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_ICONIMAGEID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_ICONIMAGEID_ICONIMAGEID_2);

				query.append(LayoutModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(iconImageId);

				List<Layout> list = q.list();

				result = list;

				Layout layout = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ICONIMAGEID,
						finderArgs, list);
				}
				else {
					layout = list.get(0);

					cacheResult(layout);

					if ((layout.getIconImageId() != iconImageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ICONIMAGEID,
							finderArgs, layout);
					}
				}

				return layout;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ICONIMAGEID,
						finderArgs, new ArrayList<Layout>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Layout)result;
			}
		}
	}

	public List<Layout> findByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout)
			};

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_G_P_GROUPID_2);

				query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

				query.append(LayoutModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

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
					list = new ArrayList<Layout>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_P, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Layout> findByG_P(long groupId, boolean privateLayout,
		int start, int end) throws SystemException {
		return findByG_P(groupId, privateLayout, start, end, null);
	}

	public List<Layout> findByG_P(long groupId, boolean privateLayout,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(4 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_G_P_GROUPID_2);

				query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(LayoutModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				list = (List<Layout>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Layout>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Layout findByG_P_First(long groupId, boolean privateLayout,
		OrderByComparator obc) throws NoSuchLayoutException, SystemException {
		List<Layout> list = findByG_P(groupId, privateLayout, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Layout findByG_P_Last(long groupId, boolean privateLayout,
		OrderByComparator obc) throws NoSuchLayoutException, SystemException {
		int count = countByG_P(groupId, privateLayout);

		List<Layout> list = findByG_P(groupId, privateLayout, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Layout[] findByG_P_PrevAndNext(long plid, long groupId,
		boolean privateLayout, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		Layout layout = findByPrimaryKey(plid);

		int count = countByG_P(groupId, privateLayout);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(4 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, layout);

			Layout[] array = new LayoutImpl[3];

			array[0] = (Layout)objArray[0];
			array[1] = (Layout)objArray[1];
			array[2] = (Layout)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Layout findByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws NoSuchLayoutException, SystemException {
		Layout layout = fetchByG_P_L(groupId, privateLayout, layoutId);

		if (layout == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(", layoutId=");
			msg.append(layoutId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutException(msg.toString());
		}

		return layout;
	}

	public Layout fetchByG_P_L(long groupId, boolean privateLayout,
		long layoutId) throws SystemException {
		return fetchByG_P_L(groupId, privateLayout, layoutId, true);
	}

	public Layout fetchByG_P_L(long groupId, boolean privateLayout,
		long layoutId, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(layoutId)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_P_L,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_G_P_L_GROUPID_2);

				query.append(_FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2);

				query.append(_FINDER_COLUMN_G_P_L_LAYOUTID_2);

				query.append(LayoutModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				List<Layout> list = q.list();

				result = list;

				Layout layout = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L,
						finderArgs, list);
				}
				else {
					layout = list.get(0);

					cacheResult(layout);

					if ((layout.getGroupId() != groupId) ||
							(layout.getPrivateLayout() != privateLayout) ||
							(layout.getLayoutId() != layoutId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L,
							finderArgs, layout);
					}
				}

				return layout;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L,
						finderArgs, new ArrayList<Layout>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Layout)result;
			}
		}
	}

	public List<Layout> findByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(parentLayoutId)
			};

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_P_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_G_P_P_GROUPID_2);

				query.append(_FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2);

				query.append(_FINDER_COLUMN_G_P_P_PARENTLAYOUTID_2);

				query.append(LayoutModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(parentLayoutId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Layout>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_P_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Layout> findByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId, int start, int end) throws SystemException {
		return findByG_P_P(groupId, privateLayout, parentLayoutId, start, end,
			null);
	}

	public List<Layout> findByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(parentLayoutId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_P_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(5 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(5);
				}

				query.append(_SQL_SELECT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_G_P_P_GROUPID_2);

				query.append(_FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2);

				query.append(_FINDER_COLUMN_G_P_P_PARENTLAYOUTID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(LayoutModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(parentLayoutId);

				list = (List<Layout>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Layout>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_P_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Layout findByG_P_P_First(long groupId, boolean privateLayout,
		long parentLayoutId, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		List<Layout> list = findByG_P_P(groupId, privateLayout, parentLayoutId,
				0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(", parentLayoutId=");
			msg.append(parentLayoutId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Layout findByG_P_P_Last(long groupId, boolean privateLayout,
		long parentLayoutId, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		int count = countByG_P_P(groupId, privateLayout, parentLayoutId);

		List<Layout> list = findByG_P_P(groupId, privateLayout, parentLayoutId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(", parentLayoutId=");
			msg.append(parentLayoutId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Layout[] findByG_P_P_PrevAndNext(long plid, long groupId,
		boolean privateLayout, long parentLayoutId, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		Layout layout = findByPrimaryKey(plid);

		int count = countByG_P_P(groupId, privateLayout, parentLayoutId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(5 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_P_PARENTLAYOUTID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			qPos.add(parentLayoutId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, layout);

			Layout[] array = new LayoutImpl[3];

			array[0] = (Layout)objArray[0];
			array[1] = (Layout)objArray[1];
			array[2] = (Layout)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Layout findByG_P_F(long groupId, boolean privateLayout,
		String friendlyURL) throws NoSuchLayoutException, SystemException {
		Layout layout = fetchByG_P_F(groupId, privateLayout, friendlyURL);

		if (layout == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(", friendlyURL=");
			msg.append(friendlyURL);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutException(msg.toString());
		}

		return layout;
	}

	public Layout fetchByG_P_F(long groupId, boolean privateLayout,
		String friendlyURL) throws SystemException {
		return fetchByG_P_F(groupId, privateLayout, friendlyURL, true);
	}

	public Layout fetchByG_P_F(long groupId, boolean privateLayout,
		String friendlyURL, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				
				friendlyURL
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_P_F,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_G_P_F_GROUPID_2);

				query.append(_FINDER_COLUMN_G_P_F_PRIVATELAYOUT_2);

				if (friendlyURL == null) {
					query.append(_FINDER_COLUMN_G_P_F_FRIENDLYURL_1);
				}
				else {
					if (friendlyURL.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_P_F_FRIENDLYURL_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_P_F_FRIENDLYURL_2);
					}
				}

				query.append(LayoutModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (friendlyURL != null) {
					qPos.add(friendlyURL);
				}

				List<Layout> list = q.list();

				result = list;

				Layout layout = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_F,
						finderArgs, list);
				}
				else {
					layout = list.get(0);

					cacheResult(layout);

					if ((layout.getGroupId() != groupId) ||
							(layout.getPrivateLayout() != privateLayout) ||
							(layout.getFriendlyURL() == null) ||
							!layout.getFriendlyURL().equals(friendlyURL)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_F,
							finderArgs, layout);
					}
				}

				return layout;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_F,
						finderArgs, new ArrayList<Layout>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Layout)result;
			}
		}
	}

	public List<Layout> findByG_P_T(long groupId, boolean privateLayout,
		String type) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				
				type
			};

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_P_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

				query.append(_FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2);

				if (type == null) {
					query.append(_FINDER_COLUMN_G_P_T_TYPE_1);
				}
				else {
					if (type.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_P_T_TYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_P_T_TYPE_2);
					}
				}

				query.append(LayoutModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

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
					list = new ArrayList<Layout>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_P_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Layout> findByG_P_T(long groupId, boolean privateLayout,
		String type, int start, int end) throws SystemException {
		return findByG_P_T(groupId, privateLayout, type, start, end, null);
	}

	public List<Layout> findByG_P_T(long groupId, boolean privateLayout,
		String type, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				
				type,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_P_T,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(5 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(5);
				}

				query.append(_SQL_SELECT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

				query.append(_FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2);

				if (type == null) {
					query.append(_FINDER_COLUMN_G_P_T_TYPE_1);
				}
				else {
					if (type.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_P_T_TYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_P_T_TYPE_2);
					}
				}

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(LayoutModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (type != null) {
					qPos.add(type);
				}

				list = (List<Layout>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Layout>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_P_T,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Layout findByG_P_T_First(long groupId, boolean privateLayout,
		String type, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		List<Layout> list = findByG_P_T(groupId, privateLayout, type, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Layout findByG_P_T_Last(long groupId, boolean privateLayout,
		String type, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		int count = countByG_P_T(groupId, privateLayout, type);

		List<Layout> list = findByG_P_T(groupId, privateLayout, type,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Layout[] findByG_P_T_PrevAndNext(long plid, long groupId,
		boolean privateLayout, String type, OrderByComparator obc)
		throws NoSuchLayoutException, SystemException {
		Layout layout = findByPrimaryKey(plid);

		int count = countByG_P_T(groupId, privateLayout, type);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(5 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2);

			if (type == null) {
				query.append(_FINDER_COLUMN_G_P_T_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_T_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_T_TYPE_2);
				}
			}

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			if (type != null) {
				qPos.add(type);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, layout);

			Layout[] array = new LayoutImpl[3];

			array[0] = (Layout)objArray[0];
			array[1] = (Layout)objArray[1];
			array[2] = (Layout)objArray[2];

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

	public List<Layout> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Layout> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Layout> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (obc != null) {
					query = new StringBundler(2 +
							(obc.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_LAYOUT);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_LAYOUT.concat(LayoutModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Layout>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (Layout layout : findByGroupId(groupId)) {
			remove(layout);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (Layout layout : findByCompanyId(companyId)) {
			remove(layout);
		}
	}

	public void removeByDLFolderId(long dlFolderId)
		throws NoSuchLayoutException, SystemException {
		Layout layout = findByDLFolderId(dlFolderId);

		remove(layout);
	}

	public void removeByIconImageId(long iconImageId)
		throws NoSuchLayoutException, SystemException {
		Layout layout = findByIconImageId(iconImageId);

		remove(layout);
	}

	public void removeByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		for (Layout layout : findByG_P(groupId, privateLayout)) {
			remove(layout);
		}
	}

	public void removeByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws NoSuchLayoutException, SystemException {
		Layout layout = findByG_P_L(groupId, privateLayout, layoutId);

		remove(layout);
	}

	public void removeByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) throws SystemException {
		for (Layout layout : findByG_P_P(groupId, privateLayout, parentLayoutId)) {
			remove(layout);
		}
	}

	public void removeByG_P_F(long groupId, boolean privateLayout,
		String friendlyURL) throws NoSuchLayoutException, SystemException {
		Layout layout = findByG_P_F(groupId, privateLayout, friendlyURL);

		remove(layout);
	}

	public void removeByG_P_T(long groupId, boolean privateLayout, String type)
		throws SystemException {
		for (Layout layout : findByG_P_T(groupId, privateLayout, type)) {
			remove(layout);
		}
	}

	public void removeAll() throws SystemException {
		for (Layout layout : findAll()) {
			remove(layout);
		}
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

				query.append(_SQL_COUNT_LAYOUT_WHERE);

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

				query.append(_SQL_COUNT_LAYOUT_WHERE);

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

	public int countByDLFolderId(long dlFolderId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(dlFolderId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_DLFOLDERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_DLFOLDERID_DLFOLDERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(dlFolderId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_DLFOLDERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByIconImageId(long iconImageId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(iconImageId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ICONIMAGEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_ICONIMAGEID_ICONIMAGEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(iconImageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ICONIMAGEID,
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

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_G_P_GROUPID_2);

				query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

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

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_G_P_L_GROUPID_2);

				query.append(_FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2);

				query.append(_FINDER_COLUMN_G_P_L_LAYOUTID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

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

	public int countByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(parentLayoutId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_G_P_P_GROUPID_2);

				query.append(_FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2);

				query.append(_FINDER_COLUMN_G_P_P_PARENTLAYOUTID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(parentLayoutId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_P,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_P_F(long groupId, boolean privateLayout,
		String friendlyURL) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				
				friendlyURL
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_F,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_G_P_F_GROUPID_2);

				query.append(_FINDER_COLUMN_G_P_F_PRIVATELAYOUT_2);

				if (friendlyURL == null) {
					query.append(_FINDER_COLUMN_G_P_F_FRIENDLYURL_1);
				}
				else {
					if (friendlyURL.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_P_F_FRIENDLYURL_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_P_F_FRIENDLYURL_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (friendlyURL != null) {
					qPos.add(friendlyURL);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_F,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_P_T(long groupId, boolean privateLayout, String type)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				
				type
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_LAYOUT_WHERE);

				query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

				query.append(_FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2);

				if (type == null) {
					query.append(_FINDER_COLUMN_G_P_T_TYPE_1);
				}
				else {
					if (type.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_P_T_TYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_P_T_TYPE_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_T,
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

				Query q = session.createQuery(_SQL_COUNT_LAYOUT);

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
						"value.object.listener.com.liferay.portal.model.Layout")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Layout>> listenersList = new ArrayList<ModelListener<Layout>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Layout>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.BrowserTrackerPersistence")
	protected com.liferay.portal.service.persistence.BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPrototypePersistence")
	protected com.liferay.portal.service.persistence.LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPrototypePersistence")
	protected com.liferay.portal.service.persistence.LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LockPersistence")
	protected com.liferay.portal.service.persistence.LockPersistence lockPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupGroupRolePersistence")
	protected com.liferay.portal.service.persistence.UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence dlFolderPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence journalContentSearchPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence mbMessagePersistence;
	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence")
	protected com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(name = "com.liferay.portlet.tasks.service.persistence.TasksProposalPersistence")
	protected com.liferay.portlet.tasks.service.persistence.TasksProposalPersistence tasksProposalPersistence;
	private static final String _SQL_SELECT_LAYOUT = "SELECT layout FROM Layout layout";
	private static final String _SQL_SELECT_LAYOUT_WHERE = "SELECT layout FROM Layout layout WHERE ";
	private static final String _SQL_COUNT_LAYOUT = "SELECT COUNT(layout) FROM Layout layout";
	private static final String _SQL_COUNT_LAYOUT_WHERE = "SELECT COUNT(layout) FROM Layout layout WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "layout.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "layout.companyId = ?";
	private static final String _FINDER_COLUMN_DLFOLDERID_DLFOLDERID_2 = "layout.dlFolderId = ?";
	private static final String _FINDER_COLUMN_ICONIMAGEID_ICONIMAGEID_2 = "layout.iconImageId = ?";
	private static final String _FINDER_COLUMN_G_P_GROUPID_2 = "layout.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_PRIVATELAYOUT_2 = "layout.privateLayout = ?";
	private static final String _FINDER_COLUMN_G_P_L_GROUPID_2 = "layout.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2 = "layout.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_G_P_L_LAYOUTID_2 = "layout.layoutId = ?";
	private static final String _FINDER_COLUMN_G_P_P_GROUPID_2 = "layout.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2 = "layout.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_G_P_P_PARENTLAYOUTID_2 = "layout.parentLayoutId = ?";
	private static final String _FINDER_COLUMN_G_P_F_GROUPID_2 = "layout.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_F_PRIVATELAYOUT_2 = "layout.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_G_P_F_FRIENDLYURL_1 = "layout.friendlyURL IS NULL";
	private static final String _FINDER_COLUMN_G_P_F_FRIENDLYURL_2 = "layout.friendlyURL = ?";
	private static final String _FINDER_COLUMN_G_P_F_FRIENDLYURL_3 = "(layout.friendlyURL IS NULL OR layout.friendlyURL = ?)";
	private static final String _FINDER_COLUMN_G_P_T_GROUPID_2 = "layout.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2 = "layout.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_G_P_T_TYPE_1 = "layout.type IS NULL";
	private static final String _FINDER_COLUMN_G_P_T_TYPE_2 = "layout.type = ?";
	private static final String _FINDER_COLUMN_G_P_T_TYPE_3 = "(layout.type IS NULL OR layout.type = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layout.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Layout exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Layout exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(LayoutPersistenceImpl.class);
}