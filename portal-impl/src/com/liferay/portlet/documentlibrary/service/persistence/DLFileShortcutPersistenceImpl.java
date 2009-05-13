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

import com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="DLFileShortcutPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DLFileShortcutPersistenceImpl extends BasePersistenceImpl
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
	public static final FinderPath FINDER_PATH_FIND_BY_FOLDERID = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByFolderId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_FOLDERID = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByFolderId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_FOLDERID = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByFolderId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_TF_TN = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByTF_TN",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_TF_TN = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByTF_TN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_TF_TN = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByTF_TN",
			new String[] { Long.class.getName(), String.class.getName() });
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

	public DLFileShortcut remove(long fileShortcutId)
		throws NoSuchFileShortcutException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileShortcut dlFileShortcut = (DLFileShortcut)session.get(DLFileShortcutImpl.class,
					new Long(fileShortcutId));

			if (dlFileShortcut == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No DLFileShortcut exists with the primary key " +
						fileShortcutId);
				}

				throw new NoSuchFileShortcutException(
					"No DLFileShortcut exists with the primary key " +
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

	/**
	 * @deprecated Use <code>update(DLFileShortcut dlFileShortcut, boolean merge)</code>.
	 */
	public DLFileShortcut update(DLFileShortcut dlFileShortcut)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(DLFileShortcut dlFileShortcut) method. Use update(DLFileShortcut dlFileShortcut, boolean merge) instead.");
		}

		return update(dlFileShortcut, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        dlFileShortcut the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when dlFileShortcut is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public DLFileShortcut update(DLFileShortcut dlFileShortcut, boolean merge)
		throws SystemException {
		boolean isNew = dlFileShortcut.isNew();

		for (ModelListener<DLFileShortcut> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(dlFileShortcut);
			}
			else {
				listener.onBeforeUpdate(dlFileShortcut);
			}
		}

		dlFileShortcut = updateImpl(dlFileShortcut, merge);

		for (ModelListener<DLFileShortcut> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(dlFileShortcut);
			}
			else {
				listener.onAfterUpdate(dlFileShortcut);
			}
		}

		return dlFileShortcut;
	}

	public DLFileShortcut updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut,
		boolean merge) throws SystemException {
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
				(!dlFileShortcut.getUuid()
									.equals(dlFileShortcutModelImpl.getOriginalUuid()) ||
				(dlFileShortcut.getGroupId() != dlFileShortcutModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					dlFileShortcutModelImpl.getOriginalUuid(),
					new Long(dlFileShortcutModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!dlFileShortcut.getUuid()
									.equals(dlFileShortcutModelImpl.getOriginalUuid()) ||
				(dlFileShortcut.getGroupId() != dlFileShortcutModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					dlFileShortcut.getUuid(),
					new Long(dlFileShortcut.getGroupId())
				}, dlFileShortcut);
		}

		return dlFileShortcut;
	}

	public DLFileShortcut findByPrimaryKey(long fileShortcutId)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = fetchByPrimaryKey(fileShortcutId);

		if (dlFileShortcut == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No DLFileShortcut exists with the primary key " +
					fileShortcutId);
			}

			throw new NoSuchFileShortcutException(
				"No DLFileShortcut exists with the primary key " +
				fileShortcutId);
		}

		return dlFileShortcut;
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

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

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

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

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
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileShortcut exists with the key {");

			msg.append("uuid=" + uuid);

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
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileShortcut exists with the key {");

			msg.append("uuid=" + uuid);

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

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

			if (uuid == null) {
				query.append("uuid_ IS NULL");
			}
			else {
				query.append("uuid_ = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

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
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileShortcut exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

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

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" AND ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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
			if (result instanceof List) {
				return null;
			}
			else {
				return (DLFileShortcut)result;
			}
		}
	}

	public List<DLFileShortcut> findByFolderId(long folderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(folderId) };

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_FOLDERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

				query.append("folderId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_FOLDERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileShortcut> findByFolderId(long folderId, int start, int end)
		throws SystemException {
		return findByFolderId(folderId, start, end, null);
	}

	public List<DLFileShortcut> findByFolderId(long folderId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(folderId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_FOLDERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

				query.append("folderId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_FOLDERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileShortcut findByFolderId_First(long folderId,
		OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		List<DLFileShortcut> list = findByFolderId(folderId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileShortcut exists with the key {");

			msg.append("folderId=" + folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileShortcutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileShortcut findByFolderId_Last(long folderId,
		OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		int count = countByFolderId(folderId);

		List<DLFileShortcut> list = findByFolderId(folderId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileShortcut exists with the key {");

			msg.append("folderId=" + folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileShortcutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileShortcut[] findByFolderId_PrevAndNext(long fileShortcutId,
		long folderId, OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		int count = countByFolderId(folderId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

			query.append("folderId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

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

	public List<DLFileShortcut> findByTF_TN(long toFolderId, String toName)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(toFolderId), toName };

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_TF_TN,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

				query.append("toFolderId = ?");

				query.append(" AND ");

				if (toName == null) {
					query.append("toName IS NULL");
				}
				else {
					query.append("toName = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_TF_TN,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileShortcut> findByTF_TN(long toFolderId, String toName,
		int start, int end) throws SystemException {
		return findByTF_TN(toFolderId, toName, start, end, null);
	}

	public List<DLFileShortcut> findByTF_TN(long toFolderId, String toName,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(toFolderId),
				
				toName,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_TF_TN,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

				query.append("toFolderId = ?");

				query.append(" AND ");

				if (toName == null) {
					query.append("toName IS NULL");
				}
				else {
					query.append("toName = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_TF_TN,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileShortcut findByTF_TN_First(long toFolderId, String toName,
		OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		List<DLFileShortcut> list = findByTF_TN(toFolderId, toName, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileShortcut exists with the key {");

			msg.append("toFolderId=" + toFolderId);

			msg.append(", ");
			msg.append("toName=" + toName);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileShortcutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileShortcut findByTF_TN_Last(long toFolderId, String toName,
		OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		int count = countByTF_TN(toFolderId, toName);

		List<DLFileShortcut> list = findByTF_TN(toFolderId, toName, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileShortcut exists with the key {");

			msg.append("toFolderId=" + toFolderId);

			msg.append(", ");
			msg.append("toName=" + toName);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileShortcutException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileShortcut[] findByTF_TN_PrevAndNext(long fileShortcutId,
		long toFolderId, String toName, OrderByComparator obc)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		int count = countByTF_TN(toFolderId, toName);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

			query.append("toFolderId = ?");

			query.append(" AND ");

			if (toName == null) {
				query.append("toName IS NULL");
			}
			else {
				query.append("toName = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

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

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

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

	public void removeByFolderId(long folderId) throws SystemException {
		for (DLFileShortcut dlFileShortcut : findByFolderId(folderId)) {
			remove(dlFileShortcut);
		}
	}

	public void removeByTF_TN(long toFolderId, String toName)
		throws SystemException {
		for (DLFileShortcut dlFileShortcut : findByTF_TN(toFolderId, toName)) {
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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
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

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" AND ");

				query.append("groupId = ?");

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

	public int countByFolderId(long folderId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(folderId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FOLDERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

				query.append("folderId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_FOLDERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByTF_TN(long toFolderId, String toName)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(toFolderId), toName };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TF_TN,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut WHERE ");

				query.append("toFolderId = ?");

				query.append(" AND ");

				if (toName == null) {
					query.append("toName IS NULL");
				}
				else {
					query.append("toName = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TF_TN,
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
						"SELECT COUNT(*) FROM com.liferay.portlet.documentlibrary.model.DLFileShortcut");

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

	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence.impl")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence.impl")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence dlFileRankPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutPersistence.impl")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutPersistence dlFileShortcutPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPersistence.impl")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPersistence dlFileVersionPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence.impl")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence dlFolderPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(DLFileShortcutPersistenceImpl.class);
}