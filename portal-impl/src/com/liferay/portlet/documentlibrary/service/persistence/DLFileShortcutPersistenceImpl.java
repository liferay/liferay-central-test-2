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

package com.liferay.portlet.documentlibrary.service.persistence;

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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="DLFileShortcutPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileShortcutPersistence
 * @see       DLFileShortcutUtil
 * @generated
 */
public class DLFileShortcutPersistenceImpl extends BasePersistenceImpl<DLFileShortcut>
	implements DLFileShortcutPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileShortcutImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_F = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_F = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_F_S = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_F_S = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_S = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_TF_TN = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_TF_TN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_TF_TN = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_TF_TN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_TF_TN = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_TF_TN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_TF_TN_S = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_TF_TN_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_TF_TN_S = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_TF_TN_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_TF_TN_S = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_TF_TN_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(DLFileShortcut dlFileShortcut) {
		EntityCacheUtil.putResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutImpl.class, dlFileShortcut.getPrimaryKey(),
			dlFileShortcut);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				dlFileShortcut.getUuid(), new Long(dlFileShortcut.getGroupId())
			}, dlFileShortcut);
	}

	public void cacheResult(List<DLFileShortcut> dlFileShortcuts) {
		for (DLFileShortcut dlFileShortcut : dlFileShortcuts) {
			if (EntityCacheUtil.getResult(
						DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
						DLFileShortcutImpl.class,
						dlFileShortcut.getPrimaryKey(), this) == null) {
				cacheResult(dlFileShortcut);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(DLFileShortcutImpl.class.getName());
		EntityCacheUtil.clearCache(DLFileShortcutImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public DLFileShortcut create(long fileShortcutId) {
		DLFileShortcut dlFileShortcut = new DLFileShortcutImpl();

		dlFileShortcut.setNew(true);
		dlFileShortcut.setPrimaryKey(fileShortcutId);

		String uuid = PortalUUIDUtil.generate();

		dlFileShortcut.setUuid(uuid);

		return dlFileShortcut;
	}

	public DLFileShortcut remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public DLFileShortcut remove(long fileShortcutId)
		throws NoSuchFileShortcutException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileShortcut dlFileShortcut = (DLFileShortcut)session.get(DLFileShortcutImpl.class,
					new Long(fileShortcutId));

			if (dlFileShortcut == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						fileShortcutId);
				}

				throw new NoSuchFileShortcutException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					fileShortcutId);
			}

			return remove(dlFileShortcut);
		}
		catch (NoSuchFileShortcutException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileShortcut remove(DLFileShortcut dlFileShortcut)
		throws SystemException {
		for (ModelListener<DLFileShortcut> listener : listeners) {
			listener.onBeforeRemove(dlFileShortcut);
		}

		dlFileShortcut = removeImpl(dlFileShortcut);

		for (ModelListener<DLFileShortcut> listener : listeners) {
			listener.onAfterRemove(dlFileShortcut);
		}

		return dlFileShortcut;
	}

	protected DLFileShortcut removeImpl(DLFileShortcut dlFileShortcut)
		throws SystemException {
		dlFileShortcut = toUnwrappedModel(dlFileShortcut);

		Session session = null;

		try {
			session = openSession();

			if (dlFileShortcut.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(DLFileShortcutImpl.class,
						dlFileShortcut.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(dlFileShortcut);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		DLFileShortcutModelImpl dlFileShortcutModelImpl = (DLFileShortcutModelImpl)dlFileShortcut;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				dlFileShortcutModelImpl.getOriginalUuid(),
				new Long(dlFileShortcutModelImpl.getOriginalGroupId())
			});

		EntityCacheUtil.removeResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutImpl.class, dlFileShortcut.getPrimaryKey());

		return dlFileShortcut;
	}

	public DLFileShortcut updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut,
		boolean merge) throws SystemException {
		dlFileShortcut = toUnwrappedModel(dlFileShortcut);

		boolean isNew = dlFileShortcut.isNew();

		DLFileShortcutModelImpl dlFileShortcutModelImpl = (DLFileShortcutModelImpl)dlFileShortcut;

		if (Validator.isNull(dlFileShortcut.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFileShortcut.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, dlFileShortcut, merge);

			dlFileShortcut.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutImpl.class, dlFileShortcut.getPrimaryKey(),
			dlFileShortcut);

		if (!isNew &&
				(!Validator.equals(dlFileShortcut.getUuid(),
					dlFileShortcutModelImpl.getOriginalUuid()) ||
				(dlFileShortcut.getGroupId() != dlFileShortcutModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					dlFileShortcutModelImpl.getOriginalUuid(),
					new Long(dlFileShortcutModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(dlFileShortcut.getUuid(),
					dlFileShortcutModelImpl.getOriginalUuid()) ||
				(dlFileShortcut.getGroupId() != dlFileShortcutModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					dlFileShortcut.getUuid(),
					new Long(dlFileShortcut.getGroupId())
				}, dlFileShortcut);
		}

		return dlFileShortcut;
	}

	protected DLFileShortcut toUnwrappedModel(DLFileShortcut dlFileShortcut) {
		if (dlFileShortcut instanceof DLFileShortcutImpl) {
			return dlFileShortcut;
		}

		DLFileShortcutImpl dlFileShortcutImpl = new DLFileShortcutImpl();

		dlFileShortcutImpl.setNew(dlFileShortcut.isNew());
		dlFileShortcutImpl.setPrimaryKey(dlFileShortcut.getPrimaryKey());

		dlFileShortcutImpl.setUuid(dlFileShortcut.getUuid());
		dlFileShortcutImpl.setFileShortcutId(dlFileShortcut.getFileShortcutId());
		dlFileShortcutImpl.setGroupId(dlFileShortcut.getGroupId());
		dlFileShortcutImpl.setCompanyId(dlFileShortcut.getCompanyId());
		dlFileShortcutImpl.setUserId(dlFileShortcut.getUserId());
		dlFileShortcutImpl.setUserName(dlFileShortcut.getUserName());
		dlFileShortcutImpl.setCreateDate(dlFileShortcut.getCreateDate());
		dlFileShortcutImpl.setModifiedDate(dlFileShortcut.getModifiedDate());
		dlFileShortcutImpl.setFolderId(dlFileShortcut.getFolderId());
		dlFileShortcutImpl.setToFolderId(dlFileShortcut.getToFolderId());
		dlFileShortcutImpl.setToName(dlFileShortcut.getToName());
		dlFileShortcutImpl.setStatus(dlFileShortcut.getStatus());
		dlFileShortcutImpl.setStatusByUserId(dlFileShortcut.getStatusByUserId());
		dlFileShortcutImpl.setStatusByUserName(dlFileShortcut.getStatusByUserName());
		dlFileShortcutImpl.setStatusDate(dlFileShortcut.getStatusDate());

		return dlFileShortcutImpl;
	}

	public DLFileShortcut findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public DLFileShortcut findByPrimaryKey(long fileShortcutId)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = fetchByPrimaryKey(fileShortcutId);

		if (dlFileShortcut == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + fileShortcutId);
			}

			throw new NoSuchFileShortcutException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				fileShortcutId);
		}

		return dlFileShortcut;
	}

	public DLFileShortcut fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public DLFileShortcut fetchByPrimaryKey(long fileShortcutId)
		throws SystemException {
		DLFileShortcut dlFileShortcut = (DLFileShortcut)EntityCacheUtil.getResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
				DLFileShortcutImpl.class, fileShortcutId, this);

		if (dlFileShortcut == null) {
			Session session = null;

			try {
				session = openSession();

				dlFileShortcut = (DLFileShortcut)session.get(DLFileShortcutImpl.class,
						new Long(fileShortcutId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (dlFileShortcut != null) {
					cacheResult(dlFileShortcut);
				}

				closeSession(session);
			}
		}

		return dlFileShortcut;
	}

	public List<DLFileShortcut> findByUuid(String uuid)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

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

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileShortcut>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileShortcut> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<DLFileShortcut> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
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
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

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

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<DLFileShortcut>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileShortcut>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileShortcut findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		List<DLFileShortcut> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileShortcutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileShortcut findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		int count = countByUuid(uuid);

		List<DLFileShortcut> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileShortcutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileShortcut[] findByUuid_PrevAndNext(long fileShortcutId,
		String uuid, OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

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

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileShortcut);

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = (DLFileShortcut)objArray[0];
			array[1] = (DLFileShortcut)objArray[1];
			array[2] = (DLFileShortcut)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileShortcut findByUUID_G(String uuid, long groupId)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = fetchByUUID_G(uuid, groupId);

		if (dlFileShortcut == null) {
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

			throw new NoSuchFileShortcutException(msg.toString());
		}

		return dlFileShortcut;
	}

	public DLFileShortcut fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public DLFileShortcut fetchByUUID_G(String uuid, long groupId,
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

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

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

				List<DLFileShortcut> list = q.list();

				result = list;

				DLFileShortcut dlFileShortcut = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					dlFileShortcut = list.get(0);

					cacheResult(dlFileShortcut);

					if ((dlFileShortcut.getUuid() == null) ||
							!dlFileShortcut.getUuid().equals(uuid) ||
							(dlFileShortcut.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, dlFileShortcut);
					}
				}

				return dlFileShortcut;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<DLFileShortcut>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (DLFileShortcut)result;
			}
		}
	}

	public List<DLFileShortcut> findByG_F(long groupId, long folderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(folderId) };

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_F,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

				query.append(_FINDER_COLUMN_G_F_GROUPID_2);

				query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileShortcut>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_F, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileShortcut> findByG_F(long groupId, long folderId,
		int start, int end) throws SystemException {
		return findByG_F(groupId, folderId, start, end, null);
	}

	public List<DLFileShortcut> findByG_F(long groupId, long folderId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_F,
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
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

				query.append(_FINDER_COLUMN_G_F_GROUPID_2);

				query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				list = (List<DLFileShortcut>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileShortcut>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_F,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileShortcut findByG_F_First(long groupId, long folderId,
		OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		List<DLFileShortcut> list = findByG_F(groupId, folderId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileShortcutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileShortcut findByG_F_Last(long groupId, long folderId,
		OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		int count = countByG_F(groupId, folderId);

		List<DLFileShortcut> list = findByG_F(groupId, folderId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileShortcutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileShortcut[] findByG_F_PrevAndNext(long fileShortcutId,
		long groupId, long folderId, OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		int count = countByG_F(groupId, folderId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(4 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileShortcut);

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = (DLFileShortcut)objArray[0];
			array[1] = (DLFileShortcut)objArray[1];
			array[2] = (DLFileShortcut)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFileShortcut> findByG_F_S(long groupId, long folderId,
		int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId), new Integer(status)
			};

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_F_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

				query.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

				query.append(_FINDER_COLUMN_G_F_S_FOLDERID_2);

				query.append(_FINDER_COLUMN_G_F_S_STATUS_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileShortcut>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_F_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileShortcut> findByG_F_S(long groupId, long folderId,
		int status, int start, int end) throws SystemException {
		return findByG_F_S(groupId, folderId, status, start, end, null);
	}

	public List<DLFileShortcut> findByG_F_S(long groupId, long folderId,
		int status, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId), new Integer(status),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_F_S,
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
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

				query.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

				query.append(_FINDER_COLUMN_G_F_S_FOLDERID_2);

				query.append(_FINDER_COLUMN_G_F_S_STATUS_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(status);

				list = (List<DLFileShortcut>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileShortcut>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_F_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileShortcut findByG_F_S_First(long groupId, long folderId,
		int status, OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		List<DLFileShortcut> list = findByG_F_S(groupId, folderId, status, 0,
				1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileShortcutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileShortcut findByG_F_S_Last(long groupId, long folderId,
		int status, OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		int count = countByG_F_S(groupId, folderId, status);

		List<DLFileShortcut> list = findByG_F_S(groupId, folderId, status,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileShortcutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileShortcut[] findByG_F_S_PrevAndNext(long fileShortcutId,
		long groupId, long folderId, int status, OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		int count = countByG_F_S(groupId, folderId, status);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(5 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_S_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_S_STATUS_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			qPos.add(status);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileShortcut);

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = (DLFileShortcut)objArray[0];
			array[1] = (DLFileShortcut)objArray[1];
			array[2] = (DLFileShortcut)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFileShortcut> findByG_TF_TN(long groupId, long toFolderId,
		String toName) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(toFolderId),
				
				toName
			};

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_TF_TN,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

				query.append(_FINDER_COLUMN_G_TF_TN_GROUPID_2);

				query.append(_FINDER_COLUMN_G_TF_TN_TOFOLDERID_2);

				if (toName == null) {
					query.append(_FINDER_COLUMN_G_TF_TN_TONAME_1);
				}
				else {
					if (toName.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_TF_TN_TONAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_TF_TN_TONAME_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(toFolderId);

				if (toName != null) {
					qPos.add(toName);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileShortcut>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_TF_TN,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileShortcut> findByG_TF_TN(long groupId, long toFolderId,
		String toName, int start, int end) throws SystemException {
		return findByG_TF_TN(groupId, toFolderId, toName, start, end, null);
	}

	public List<DLFileShortcut> findByG_TF_TN(long groupId, long toFolderId,
		String toName, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(toFolderId),
				
				toName,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_TF_TN,
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
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

				query.append(_FINDER_COLUMN_G_TF_TN_GROUPID_2);

				query.append(_FINDER_COLUMN_G_TF_TN_TOFOLDERID_2);

				if (toName == null) {
					query.append(_FINDER_COLUMN_G_TF_TN_TONAME_1);
				}
				else {
					if (toName.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_TF_TN_TONAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_TF_TN_TONAME_2);
					}
				}

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(toFolderId);

				if (toName != null) {
					qPos.add(toName);
				}

				list = (List<DLFileShortcut>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileShortcut>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_TF_TN,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileShortcut findByG_TF_TN_First(long groupId, long toFolderId,
		String toName, OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		List<DLFileShortcut> list = findByG_TF_TN(groupId, toFolderId, toName,
				0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", toFolderId=");
			msg.append(toFolderId);

			msg.append(", toName=");
			msg.append(toName);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileShortcutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileShortcut findByG_TF_TN_Last(long groupId, long toFolderId,
		String toName, OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		int count = countByG_TF_TN(groupId, toFolderId, toName);

		List<DLFileShortcut> list = findByG_TF_TN(groupId, toFolderId, toName,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", toFolderId=");
			msg.append(toFolderId);

			msg.append(", toName=");
			msg.append(toName);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileShortcutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileShortcut[] findByG_TF_TN_PrevAndNext(long fileShortcutId,
		long groupId, long toFolderId, String toName, OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		int count = countByG_TF_TN(groupId, toFolderId, toName);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(5 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_G_TF_TN_GROUPID_2);

			query.append(_FINDER_COLUMN_G_TF_TN_TOFOLDERID_2);

			if (toName == null) {
				query.append(_FINDER_COLUMN_G_TF_TN_TONAME_1);
			}
			else {
				if (toName.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_TF_TN_TONAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_TF_TN_TONAME_2);
				}
			}

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(toFolderId);

			if (toName != null) {
				qPos.add(toName);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileShortcut);

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = (DLFileShortcut)objArray[0];
			array[1] = (DLFileShortcut)objArray[1];
			array[2] = (DLFileShortcut)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFileShortcut> findByG_TF_TN_S(long groupId, long toFolderId,
		String toName, int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(toFolderId),
				
				toName, new Integer(status)
			};

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_TF_TN_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

				query.append(_FINDER_COLUMN_G_TF_TN_S_GROUPID_2);

				query.append(_FINDER_COLUMN_G_TF_TN_S_TOFOLDERID_2);

				if (toName == null) {
					query.append(_FINDER_COLUMN_G_TF_TN_S_TONAME_1);
				}
				else {
					if (toName.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_TF_TN_S_TONAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_TF_TN_S_TONAME_2);
					}
				}

				query.append(_FINDER_COLUMN_G_TF_TN_S_STATUS_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(toFolderId);

				if (toName != null) {
					qPos.add(toName);
				}

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileShortcut>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_TF_TN_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileShortcut> findByG_TF_TN_S(long groupId, long toFolderId,
		String toName, int status, int start, int end)
		throws SystemException {
		return findByG_TF_TN_S(groupId, toFolderId, toName, status, start, end,
			null);
	}

	public List<DLFileShortcut> findByG_TF_TN_S(long groupId, long toFolderId,
		String toName, int status, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(toFolderId),
				
				toName, new Integer(status),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_TF_TN_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(6 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(5);
				}

				query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

				query.append(_FINDER_COLUMN_G_TF_TN_S_GROUPID_2);

				query.append(_FINDER_COLUMN_G_TF_TN_S_TOFOLDERID_2);

				if (toName == null) {
					query.append(_FINDER_COLUMN_G_TF_TN_S_TONAME_1);
				}
				else {
					if (toName.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_TF_TN_S_TONAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_TF_TN_S_TONAME_2);
					}
				}

				query.append(_FINDER_COLUMN_G_TF_TN_S_STATUS_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(toFolderId);

				if (toName != null) {
					qPos.add(toName);
				}

				qPos.add(status);

				list = (List<DLFileShortcut>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileShortcut>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_TF_TN_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileShortcut findByG_TF_TN_S_First(long groupId, long toFolderId,
		String toName, int status, OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		List<DLFileShortcut> list = findByG_TF_TN_S(groupId, toFolderId,
				toName, status, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", toFolderId=");
			msg.append(toFolderId);

			msg.append(", toName=");
			msg.append(toName);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileShortcutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileShortcut findByG_TF_TN_S_Last(long groupId, long toFolderId,
		String toName, int status, OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		int count = countByG_TF_TN_S(groupId, toFolderId, toName, status);

		List<DLFileShortcut> list = findByG_TF_TN_S(groupId, toFolderId,
				toName, status, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", toFolderId=");
			msg.append(toFolderId);

			msg.append(", toName=");
			msg.append(toName);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileShortcutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileShortcut[] findByG_TF_TN_S_PrevAndNext(long fileShortcutId,
		long groupId, long toFolderId, String toName, int status,
		OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		int count = countByG_TF_TN_S(groupId, toFolderId, toName, status);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(6 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_G_TF_TN_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_TF_TN_S_TOFOLDERID_2);

			if (toName == null) {
				query.append(_FINDER_COLUMN_G_TF_TN_S_TONAME_1);
			}
			else {
				if (toName.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_TF_TN_S_TONAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_TF_TN_S_TONAME_2);
				}
			}

			query.append(_FINDER_COLUMN_G_TF_TN_S_STATUS_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(toFolderId);

			if (toName != null) {
				qPos.add(toName);
			}

			qPos.add(status);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileShortcut);

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = (DLFileShortcut)objArray[0];
			array[1] = (DLFileShortcut)objArray[1];
			array[2] = (DLFileShortcut)objArray[2];

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

	public List<DLFileShortcut> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<DLFileShortcut> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<DLFileShortcut> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_DLFILESHORTCUT);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				sql = _SQL_SELECT_DLFILESHORTCUT;

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileShortcut>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (DLFileShortcut dlFileShortcut : findByUuid(uuid)) {
			remove(dlFileShortcut);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = findByUUID_G(uuid, groupId);

		remove(dlFileShortcut);
	}

	public void removeByG_F(long groupId, long folderId)
		throws SystemException {
		for (DLFileShortcut dlFileShortcut : findByG_F(groupId, folderId)) {
			remove(dlFileShortcut);
		}
	}

	public void removeByG_F_S(long groupId, long folderId, int status)
		throws SystemException {
		for (DLFileShortcut dlFileShortcut : findByG_F_S(groupId, folderId,
				status)) {
			remove(dlFileShortcut);
		}
	}

	public void removeByG_TF_TN(long groupId, long toFolderId, String toName)
		throws SystemException {
		for (DLFileShortcut dlFileShortcut : findByG_TF_TN(groupId, toFolderId,
				toName)) {
			remove(dlFileShortcut);
		}
	}

	public void removeByG_TF_TN_S(long groupId, long toFolderId, String toName,
		int status) throws SystemException {
		for (DLFileShortcut dlFileShortcut : findByG_TF_TN_S(groupId,
				toFolderId, toName, status)) {
			remove(dlFileShortcut);
		}
	}

	public void removeAll() throws SystemException {
		for (DLFileShortcut dlFileShortcut : findAll()) {
			remove(dlFileShortcut);
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

				query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

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

				query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

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

	public int countByG_F(long groupId, long folderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(folderId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

				query.append(_FINDER_COLUMN_G_F_GROUPID_2);

				query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

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

	public int countByG_F_S(long groupId, long folderId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId), new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

				query.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

				query.append(_FINDER_COLUMN_G_F_S_FOLDERID_2);

				query.append(_FINDER_COLUMN_G_F_S_STATUS_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_TF_TN(long groupId, long toFolderId, String toName)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(toFolderId),
				
				toName
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_TF_TN,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

				query.append(_FINDER_COLUMN_G_TF_TN_GROUPID_2);

				query.append(_FINDER_COLUMN_G_TF_TN_TOFOLDERID_2);

				if (toName == null) {
					query.append(_FINDER_COLUMN_G_TF_TN_TONAME_1);
				}
				else {
					if (toName.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_TF_TN_TONAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_TF_TN_TONAME_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(toFolderId);

				if (toName != null) {
					qPos.add(toName);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_TF_TN,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_TF_TN_S(long groupId, long toFolderId, String toName,
		int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(toFolderId),
				
				toName, new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_TF_TN_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

				query.append(_FINDER_COLUMN_G_TF_TN_S_GROUPID_2);

				query.append(_FINDER_COLUMN_G_TF_TN_S_TOFOLDERID_2);

				if (toName == null) {
					query.append(_FINDER_COLUMN_G_TF_TN_S_TONAME_1);
				}
				else {
					if (toName.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_TF_TN_S_TONAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_TF_TN_S_TONAME_2);
					}
				}

				query.append(_FINDER_COLUMN_G_TF_TN_S_STATUS_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(toFolderId);

				if (toName != null) {
					qPos.add(toName);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_TF_TN_S,
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

				Query q = session.createQuery(_SQL_COUNT_DLFILESHORTCUT);

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
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFileShortcut")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLFileShortcut>> listenersList = new ArrayList<ModelListener<DLFileShortcut>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLFileShortcut>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence dlFileRankPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutPersistence")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutPersistence dlFileShortcutPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPersistence")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPersistence dlFileVersionPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence dlFolderPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetEntryPersistence assetEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetTagPersistence assetTagPersistence;
	private static final String _SQL_SELECT_DLFILESHORTCUT = "SELECT dlFileShortcut FROM DLFileShortcut dlFileShortcut";
	private static final String _SQL_SELECT_DLFILESHORTCUT_WHERE = "SELECT dlFileShortcut FROM DLFileShortcut dlFileShortcut WHERE ";
	private static final String _SQL_COUNT_DLFILESHORTCUT = "SELECT COUNT(dlFileShortcut) FROM DLFileShortcut dlFileShortcut";
	private static final String _SQL_COUNT_DLFILESHORTCUT_WHERE = "SELECT COUNT(dlFileShortcut) FROM DLFileShortcut dlFileShortcut WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "dlFileShortcutuuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "dlFileShortcut.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(dlFileShortcutuuid IS NULL OR dlFileShortcut.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "dlFileShortcutuuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "dlFileShortcut.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(dlFileShortcutuuid IS NULL OR dlFileShortcut.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "dlFileShortcut.groupId = ?";
	private static final String _FINDER_COLUMN_G_F_GROUPID_2 = "dlFileShortcut.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_FOLDERID_2 = "dlFileShortcut.folderId = ?";
	private static final String _FINDER_COLUMN_G_F_S_GROUPID_2 = "dlFileShortcut.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_S_FOLDERID_2 = "dlFileShortcut.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_S_STATUS_2 = "dlFileShortcut.status = ?";
	private static final String _FINDER_COLUMN_G_TF_TN_GROUPID_2 = "dlFileShortcut.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_TF_TN_TOFOLDERID_2 = "dlFileShortcut.toFolderId = ? AND ";
	private static final String _FINDER_COLUMN_G_TF_TN_TONAME_1 = "dlFileShortcuttoName IS NULL";
	private static final String _FINDER_COLUMN_G_TF_TN_TONAME_2 = "dlFileShortcut.toName = ?";
	private static final String _FINDER_COLUMN_G_TF_TN_TONAME_3 = "(dlFileShortcuttoName IS NULL OR dlFileShortcut.toName = ?)";
	private static final String _FINDER_COLUMN_G_TF_TN_S_GROUPID_2 = "dlFileShortcut.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_TF_TN_S_TOFOLDERID_2 = "dlFileShortcut.toFolderId = ? AND ";
	private static final String _FINDER_COLUMN_G_TF_TN_S_TONAME_1 = "dlFileShortcuttoName IS NULL AND ";
	private static final String _FINDER_COLUMN_G_TF_TN_S_TONAME_2 = "dlFileShortcut.toName = ? AND ";
	private static final String _FINDER_COLUMN_G_TF_TN_S_TONAME_3 = "(dlFileShortcuttoName IS NULL OR dlFileShortcut.toName = ?) AND ";
	private static final String _FINDER_COLUMN_G_TF_TN_S_STATUS_2 = "dlFileShortcut.status = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileShortcut.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFileShortcut exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFileShortcut exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(DLFileShortcutPersistenceImpl.class);
}