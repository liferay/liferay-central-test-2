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

package com.liferay.portlet.imagegallery.service.persistence;

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

import com.liferay.portlet.imagegallery.NoSuchFolderException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.impl.IGFolderImpl;
import com.liferay.portlet.imagegallery.model.impl.IGFolderModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="IGFolderPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGFolderPersistence
 * @see       IGFolderUtil
 * @generated
 */
public class IGFolderPersistenceImpl extends BasePersistenceImpl<IGFolder>
	implements IGFolderPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = IGFolderImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_P = new FinderPath(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_P = new FinderPath(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_P_N = new FinderPath(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_P_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_N = new FinderPath(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_P_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(IGFolder igFolder) {
		EntityCacheUtil.putResult(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderImpl.class, igFolder.getPrimaryKey(), igFolder);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { igFolder.getUuid(), new Long(igFolder.getGroupId()) },
			igFolder);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
			new Object[] {
				new Long(igFolder.getGroupId()),
				new Long(igFolder.getParentFolderId()),
				
			igFolder.getName()
			}, igFolder);
	}

	public void cacheResult(List<IGFolder> igFolders) {
		for (IGFolder igFolder : igFolders) {
			if (EntityCacheUtil.getResult(
						IGFolderModelImpl.ENTITY_CACHE_ENABLED,
						IGFolderImpl.class, igFolder.getPrimaryKey(), this) == null) {
				cacheResult(igFolder);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(IGFolderImpl.class.getName());
		EntityCacheUtil.clearCache(IGFolderImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public IGFolder create(long folderId) {
		IGFolder igFolder = new IGFolderImpl();

		igFolder.setNew(true);
		igFolder.setPrimaryKey(folderId);

		String uuid = PortalUUIDUtil.generate();

		igFolder.setUuid(uuid);

		return igFolder;
	}

	public IGFolder remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public IGFolder remove(long folderId)
		throws NoSuchFolderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			IGFolder igFolder = (IGFolder)session.get(IGFolderImpl.class,
					new Long(folderId));

			if (igFolder == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No IGFolder exists with the primary key " +
						folderId);
				}

				throw new NoSuchFolderException(
					"No IGFolder exists with the primary key " + folderId);
			}

			return remove(igFolder);
		}
		catch (NoSuchFolderException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public IGFolder remove(IGFolder igFolder) throws SystemException {
		for (ModelListener<IGFolder> listener : listeners) {
			listener.onBeforeRemove(igFolder);
		}

		igFolder = removeImpl(igFolder);

		for (ModelListener<IGFolder> listener : listeners) {
			listener.onAfterRemove(igFolder);
		}

		return igFolder;
	}

	protected IGFolder removeImpl(IGFolder igFolder) throws SystemException {
		igFolder = toUnwrappedModel(igFolder);

		Session session = null;

		try {
			session = openSession();

			if (igFolder.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(IGFolderImpl.class,
						igFolder.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(igFolder);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		IGFolderModelImpl igFolderModelImpl = (IGFolderModelImpl)igFolder;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				igFolderModelImpl.getOriginalUuid(),
				new Long(igFolderModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_N,
			new Object[] {
				new Long(igFolderModelImpl.getOriginalGroupId()),
				new Long(igFolderModelImpl.getOriginalParentFolderId()),
				
			igFolderModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderImpl.class, igFolder.getPrimaryKey());

		return igFolder;
	}

	public IGFolder updateImpl(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder, boolean merge)
		throws SystemException {
		igFolder = toUnwrappedModel(igFolder);

		boolean isNew = igFolder.isNew();

		IGFolderModelImpl igFolderModelImpl = (IGFolderModelImpl)igFolder;

		if (Validator.isNull(igFolder.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			igFolder.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, igFolder, merge);

			igFolder.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
			IGFolderImpl.class, igFolder.getPrimaryKey(), igFolder);

		if (!isNew &&
				(!Validator.equals(igFolder.getUuid(),
					igFolderModelImpl.getOriginalUuid()) ||
				(igFolder.getGroupId() != igFolderModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					igFolderModelImpl.getOriginalUuid(),
					new Long(igFolderModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(igFolder.getUuid(),
					igFolderModelImpl.getOriginalUuid()) ||
				(igFolder.getGroupId() != igFolderModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] { igFolder.getUuid(), new Long(
						igFolder.getGroupId()) }, igFolder);
		}

		if (!isNew &&
				((igFolder.getGroupId() != igFolderModelImpl.getOriginalGroupId()) ||
				(igFolder.getParentFolderId() != igFolderModelImpl.getOriginalParentFolderId()) ||
				!Validator.equals(igFolder.getName(),
					igFolderModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_N,
				new Object[] {
					new Long(igFolderModelImpl.getOriginalGroupId()),
					new Long(igFolderModelImpl.getOriginalParentFolderId()),
					
				igFolderModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((igFolder.getGroupId() != igFolderModelImpl.getOriginalGroupId()) ||
				(igFolder.getParentFolderId() != igFolderModelImpl.getOriginalParentFolderId()) ||
				!Validator.equals(igFolder.getName(),
					igFolderModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
				new Object[] {
					new Long(igFolder.getGroupId()),
					new Long(igFolder.getParentFolderId()),
					
				igFolder.getName()
				}, igFolder);
		}

		return igFolder;
	}

	protected IGFolder toUnwrappedModel(IGFolder igFolder) {
		if (igFolder instanceof IGFolderImpl) {
			return igFolder;
		}

		IGFolderImpl igFolderImpl = new IGFolderImpl();

		igFolderImpl.setNew(igFolder.isNew());
		igFolderImpl.setPrimaryKey(igFolder.getPrimaryKey());

		igFolderImpl.setUuid(igFolder.getUuid());
		igFolderImpl.setFolderId(igFolder.getFolderId());
		igFolderImpl.setGroupId(igFolder.getGroupId());
		igFolderImpl.setCompanyId(igFolder.getCompanyId());
		igFolderImpl.setUserId(igFolder.getUserId());
		igFolderImpl.setCreateDate(igFolder.getCreateDate());
		igFolderImpl.setModifiedDate(igFolder.getModifiedDate());
		igFolderImpl.setParentFolderId(igFolder.getParentFolderId());
		igFolderImpl.setName(igFolder.getName());
		igFolderImpl.setDescription(igFolder.getDescription());

		return igFolderImpl;
	}

	public IGFolder findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public IGFolder findByPrimaryKey(long folderId)
		throws NoSuchFolderException, SystemException {
		IGFolder igFolder = fetchByPrimaryKey(folderId);

		if (igFolder == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No IGFolder exists with the primary key " +
					folderId);
			}

			throw new NoSuchFolderException(
				"No IGFolder exists with the primary key " + folderId);
		}

		return igFolder;
	}

	public IGFolder fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public IGFolder fetchByPrimaryKey(long folderId) throws SystemException {
		IGFolder igFolder = (IGFolder)EntityCacheUtil.getResult(IGFolderModelImpl.ENTITY_CACHE_ENABLED,
				IGFolderImpl.class, folderId, this);

		if (igFolder == null) {
			Session session = null;

			try {
				session = openSession();

				igFolder = (IGFolder)session.get(IGFolderImpl.class,
						new Long(folderId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (igFolder != null) {
					cacheResult(igFolder);
				}

				closeSession(session);
			}
		}

		return igFolder;
	}

	public List<IGFolder> findByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<IGFolder> list = (List<IGFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_IGFOLDER_WHERE);

				if (uuid == null) {
					query.append("igFolder.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(igFolder.uuid IS NULL OR ");
					}

					query.append("igFolder.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ORDER BY ");

				query.append("igFolder.folderId ASC, ");
				query.append("igFolder.name ASC");

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
					list = new ArrayList<IGFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<IGFolder> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<IGFolder> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<IGFolder> list = (List<IGFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_IGFOLDER_WHERE);

				if (uuid == null) {
					query.append("igFolder.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(igFolder.uuid IS NULL OR ");
					}

					query.append("igFolder.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("igFolder.");
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
					query.append(" ORDER BY ");

					query.append("igFolder.folderId ASC, ");
					query.append("igFolder.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<IGFolder>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public IGFolder findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		List<IGFolder> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No IGFolder exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGFolder findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		int count = countByUuid(uuid);

		List<IGFolder> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No IGFolder exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGFolder[] findByUuid_PrevAndNext(long folderId, String uuid,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		IGFolder igFolder = findByPrimaryKey(folderId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_IGFOLDER_WHERE);

			if (uuid == null) {
				query.append("igFolder.uuid IS NULL");
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append("(igFolder.uuid IS NULL OR ");
				}

				query.append("igFolder.uuid = ?");

				if (uuid.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("igFolder.");
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
				query.append(" ORDER BY ");

				query.append("igFolder.folderId ASC, ");
				query.append("igFolder.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, igFolder);

			IGFolder[] array = new IGFolderImpl[3];

			array[0] = (IGFolder)objArray[0];
			array[1] = (IGFolder)objArray[1];
			array[2] = (IGFolder)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public IGFolder findByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException, SystemException {
		IGFolder igFolder = fetchByUUID_G(uuid, groupId);

		if (igFolder == null) {
			StringBundler msg = new StringBundler();

			msg.append("No IGFolder exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFolderException(msg.toString());
		}

		return igFolder;
	}

	public IGFolder fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public IGFolder fetchByUUID_G(String uuid, long groupId,
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

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_IGFOLDER_WHERE);

				if (uuid == null) {
					query.append("igFolder.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(igFolder.uuid IS NULL OR ");
					}

					query.append("igFolder.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("igFolder.groupId = ?");

				query.append(" ORDER BY ");

				query.append("igFolder.folderId ASC, ");
				query.append("igFolder.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<IGFolder> list = q.list();

				result = list;

				IGFolder igFolder = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					igFolder = list.get(0);

					cacheResult(igFolder);

					if ((igFolder.getUuid() == null) ||
							!igFolder.getUuid().equals(uuid) ||
							(igFolder.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, igFolder);
					}
				}

				return igFolder;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<IGFolder>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (IGFolder)result;
			}
		}
	}

	public List<IGFolder> findByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<IGFolder> list = (List<IGFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_IGFOLDER_WHERE);

				query.append("igFolder.groupId = ?");

				query.append(" ORDER BY ");

				query.append("igFolder.folderId ASC, ");
				query.append("igFolder.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<IGFolder> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<IGFolder> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<IGFolder> list = (List<IGFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_IGFOLDER_WHERE);

				query.append("igFolder.groupId = ?");

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("igFolder.");
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
					query.append(" ORDER BY ");

					query.append("igFolder.folderId ASC, ");
					query.append("igFolder.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<IGFolder>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public IGFolder findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		List<IGFolder> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No IGFolder exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGFolder findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		int count = countByGroupId(groupId);

		List<IGFolder> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No IGFolder exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGFolder[] findByGroupId_PrevAndNext(long folderId, long groupId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		IGFolder igFolder = findByPrimaryKey(folderId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_IGFOLDER_WHERE);

			query.append("igFolder.groupId = ?");

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("igFolder.");
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
				query.append(" ORDER BY ");

				query.append("igFolder.folderId ASC, ");
				query.append("igFolder.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, igFolder);

			IGFolder[] array = new IGFolderImpl[3];

			array[0] = (IGFolder)objArray[0];
			array[1] = (IGFolder)objArray[1];
			array[2] = (IGFolder)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<IGFolder> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<IGFolder> list = (List<IGFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_IGFOLDER_WHERE);

				query.append("igFolder.companyId = ?");

				query.append(" ORDER BY ");

				query.append("igFolder.folderId ASC, ");
				query.append("igFolder.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<IGFolder> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<IGFolder> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<IGFolder> list = (List<IGFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_IGFOLDER_WHERE);

				query.append("igFolder.companyId = ?");

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("igFolder.");
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
					query.append(" ORDER BY ");

					query.append("igFolder.folderId ASC, ");
					query.append("igFolder.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<IGFolder>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public IGFolder findByCompanyId_First(long companyId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		List<IGFolder> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No IGFolder exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGFolder findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		int count = countByCompanyId(companyId);

		List<IGFolder> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No IGFolder exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGFolder[] findByCompanyId_PrevAndNext(long folderId,
		long companyId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		IGFolder igFolder = findByPrimaryKey(folderId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_IGFOLDER_WHERE);

			query.append("igFolder.companyId = ?");

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("igFolder.");
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
				query.append(" ORDER BY ");

				query.append("igFolder.folderId ASC, ");
				query.append("igFolder.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, igFolder);

			IGFolder[] array = new IGFolderImpl[3];

			array[0] = (IGFolder)objArray[0];
			array[1] = (IGFolder)objArray[1];
			array[2] = (IGFolder)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<IGFolder> findByG_P(long groupId, long parentFolderId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentFolderId)
			};

		List<IGFolder> list = (List<IGFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_IGFOLDER_WHERE);

				query.append("igFolder.groupId = ?");

				query.append(" AND ");

				query.append("igFolder.parentFolderId = ?");

				query.append(" ORDER BY ");

				query.append("igFolder.folderId ASC, ");
				query.append("igFolder.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_P, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<IGFolder> findByG_P(long groupId, long parentFolderId,
		int start, int end) throws SystemException {
		return findByG_P(groupId, parentFolderId, start, end, null);
	}

	public List<IGFolder> findByG_P(long groupId, long parentFolderId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentFolderId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<IGFolder> list = (List<IGFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_IGFOLDER_WHERE);

				query.append("igFolder.groupId = ?");

				query.append(" AND ");

				query.append("igFolder.parentFolderId = ?");

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("igFolder.");
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
					query.append(" ORDER BY ");

					query.append("igFolder.folderId ASC, ");
					query.append("igFolder.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				list = (List<IGFolder>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public IGFolder findByG_P_First(long groupId, long parentFolderId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		List<IGFolder> list = findByG_P(groupId, parentFolderId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No IGFolder exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("parentFolderId=" + parentFolderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGFolder findByG_P_Last(long groupId, long parentFolderId,
		OrderByComparator obc) throws NoSuchFolderException, SystemException {
		int count = countByG_P(groupId, parentFolderId);

		List<IGFolder> list = findByG_P(groupId, parentFolderId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No IGFolder exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("parentFolderId=" + parentFolderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public IGFolder[] findByG_P_PrevAndNext(long folderId, long groupId,
		long parentFolderId, OrderByComparator obc)
		throws NoSuchFolderException, SystemException {
		IGFolder igFolder = findByPrimaryKey(folderId);

		int count = countByG_P(groupId, parentFolderId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_IGFOLDER_WHERE);

			query.append("igFolder.groupId = ?");

			query.append(" AND ");

			query.append("igFolder.parentFolderId = ?");

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("igFolder.");
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
				query.append(" ORDER BY ");

				query.append("igFolder.folderId ASC, ");
				query.append("igFolder.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentFolderId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, igFolder);

			IGFolder[] array = new IGFolderImpl[3];

			array[0] = (IGFolder)objArray[0];
			array[1] = (IGFolder)objArray[1];
			array[2] = (IGFolder)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public IGFolder findByG_P_N(long groupId, long parentFolderId, String name)
		throws NoSuchFolderException, SystemException {
		IGFolder igFolder = fetchByG_P_N(groupId, parentFolderId, name);

		if (igFolder == null) {
			StringBundler msg = new StringBundler();

			msg.append("No IGFolder exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("parentFolderId=" + parentFolderId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFolderException(msg.toString());
		}

		return igFolder;
	}

	public IGFolder fetchByG_P_N(long groupId, long parentFolderId, String name)
		throws SystemException {
		return fetchByG_P_N(groupId, parentFolderId, name, true);
	}

	public IGFolder fetchByG_P_N(long groupId, long parentFolderId,
		String name, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentFolderId),
				
				name
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_P_N,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_IGFOLDER_WHERE);

				query.append("igFolder.groupId = ?");

				query.append(" AND ");

				query.append("igFolder.parentFolderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("igFolder.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(igFolder.name IS NULL OR ");
					}

					query.append("igFolder.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ORDER BY ");

				query.append("igFolder.folderId ASC, ");
				query.append("igFolder.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				if (name != null) {
					qPos.add(name);
				}

				List<IGFolder> list = q.list();

				result = list;

				IGFolder igFolder = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
						finderArgs, list);
				}
				else {
					igFolder = list.get(0);

					cacheResult(igFolder);

					if ((igFolder.getGroupId() != groupId) ||
							(igFolder.getParentFolderId() != parentFolderId) ||
							(igFolder.getName() == null) ||
							!igFolder.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
							finderArgs, igFolder);
					}
				}

				return igFolder;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
						finderArgs, new ArrayList<IGFolder>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (IGFolder)result;
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

	public List<IGFolder> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<IGFolder> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<IGFolder> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<IGFolder> list = (List<IGFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_IGFOLDER);

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("igFolder.");
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
					query.append(" ORDER BY ");

					query.append("igFolder.folderId ASC, ");
					query.append("igFolder.name ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<IGFolder>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<IGFolder>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<IGFolder>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (IGFolder igFolder : findByUuid(uuid)) {
			remove(igFolder);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException, SystemException {
		IGFolder igFolder = findByUUID_G(uuid, groupId);

		remove(igFolder);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (IGFolder igFolder : findByGroupId(groupId)) {
			remove(igFolder);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (IGFolder igFolder : findByCompanyId(companyId)) {
			remove(igFolder);
		}
	}

	public void removeByG_P(long groupId, long parentFolderId)
		throws SystemException {
		for (IGFolder igFolder : findByG_P(groupId, parentFolderId)) {
			remove(igFolder);
		}
	}

	public void removeByG_P_N(long groupId, long parentFolderId, String name)
		throws NoSuchFolderException, SystemException {
		IGFolder igFolder = findByG_P_N(groupId, parentFolderId, name);

		remove(igFolder);
	}

	public void removeAll() throws SystemException {
		for (IGFolder igFolder : findAll()) {
			remove(igFolder);
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

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_IGFOLDER_WHERE);

				if (uuid == null) {
					query.append("igFolder.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(igFolder.uuid IS NULL OR ");
					}

					query.append("igFolder.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

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

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_IGFOLDER_WHERE);

				if (uuid == null) {
					query.append("igFolder.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(igFolder.uuid IS NULL OR ");
					}

					query.append("igFolder.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("igFolder.groupId = ?");

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

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_IGFOLDER_WHERE);

				query.append("igFolder.groupId = ?");

				Query q = session.createQuery(query.toString());

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

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_IGFOLDER_WHERE);

				query.append("igFolder.companyId = ?");

				Query q = session.createQuery(query.toString());

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

	public int countByG_P(long groupId, long parentFolderId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentFolderId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_IGFOLDER_WHERE);

				query.append("igFolder.groupId = ?");

				query.append(" AND ");

				query.append("igFolder.parentFolderId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

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

	public int countByG_P_N(long groupId, long parentFolderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentFolderId),
				
				name
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_IGFOLDER_WHERE);

				query.append("igFolder.groupId = ?");

				query.append(" AND ");

				query.append("igFolder.parentFolderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("igFolder.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(igFolder.name IS NULL OR ");
					}

					query.append("igFolder.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				if (name != null) {
					qPos.add(name);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_N,
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

				Query q = session.createQuery(_SQL_COUNT_IGFOLDER);

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
						"value.object.listener.com.liferay.portlet.imagegallery.model.IGFolder")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<IGFolder>> listenersList = new ArrayList<ModelListener<IGFolder>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<IGFolder>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.imagegallery.service.persistence.IGFolderPersistence")
	protected com.liferay.portlet.imagegallery.service.persistence.IGFolderPersistence igFolderPersistence;
	@BeanReference(name = "com.liferay.portlet.imagegallery.service.persistence.IGImagePersistence")
	protected com.liferay.portlet.imagegallery.service.persistence.IGImagePersistence igImagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	private static final String _SQL_SELECT_IGFOLDER = "SELECT igFolder FROM IGFolder igFolder";
	private static final String _SQL_SELECT_IGFOLDER_WHERE = "SELECT igFolder FROM IGFolder igFolder WHERE ";
	private static final String _SQL_COUNT_IGFOLDER = "SELECT COUNT(igFolder) FROM IGFolder igFolder";
	private static final String _SQL_COUNT_IGFOLDER_WHERE = "SELECT COUNT(igFolder) FROM IGFolder igFolder WHERE ";
	private static Log _log = LogFactoryUtil.getLog(IGFolderPersistenceImpl.class);
}