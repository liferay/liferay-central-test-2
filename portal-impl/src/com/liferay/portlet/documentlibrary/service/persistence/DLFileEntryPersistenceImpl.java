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

import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="DLFileEntryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileEntryPersistence
 * @see       DLFileEntryUtil
 * @generated
 */
public class DLFileEntryPersistenceImpl extends BasePersistenceImpl<DLFileEntry>
	implements DLFileEntryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_U = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_U = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F_N = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_N = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F_T = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_F_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_T = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_F_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(DLFileEntry dlFileEntry) {
		EntityCacheUtil.putResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryImpl.class, dlFileEntry.getPrimaryKey(), dlFileEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				dlFileEntry.getUuid(), new Long(dlFileEntry.getGroupId())
			}, dlFileEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
			new Object[] {
				new Long(dlFileEntry.getGroupId()),
				new Long(dlFileEntry.getFolderId()),
				
			dlFileEntry.getName()
			}, dlFileEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_T,
			new Object[] {
				new Long(dlFileEntry.getGroupId()),
				new Long(dlFileEntry.getFolderId()),
				
			dlFileEntry.getTitle()
			}, dlFileEntry);
	}

	public void cacheResult(List<DLFileEntry> dlFileEntries) {
		for (DLFileEntry dlFileEntry : dlFileEntries) {
			if (EntityCacheUtil.getResult(
						DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
						DLFileEntryImpl.class, dlFileEntry.getPrimaryKey(), this) == null) {
				cacheResult(dlFileEntry);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(DLFileEntryImpl.class.getName());
		EntityCacheUtil.clearCache(DLFileEntryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public DLFileEntry create(long fileEntryId) {
		DLFileEntry dlFileEntry = new DLFileEntryImpl();

		dlFileEntry.setNew(true);
		dlFileEntry.setPrimaryKey(fileEntryId);

		String uuid = PortalUUIDUtil.generate();

		dlFileEntry.setUuid(uuid);

		return dlFileEntry;
	}

	public DLFileEntry remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public DLFileEntry remove(long fileEntryId)
		throws NoSuchFileEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileEntry dlFileEntry = (DLFileEntry)session.get(DLFileEntryImpl.class,
					new Long(fileEntryId));

			if (dlFileEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + fileEntryId);
				}

				throw new NoSuchFileEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					fileEntryId);
			}

			return remove(dlFileEntry);
		}
		catch (NoSuchFileEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileEntry remove(DLFileEntry dlFileEntry)
		throws SystemException {
		for (ModelListener<DLFileEntry> listener : listeners) {
			listener.onBeforeRemove(dlFileEntry);
		}

		dlFileEntry = removeImpl(dlFileEntry);

		for (ModelListener<DLFileEntry> listener : listeners) {
			listener.onAfterRemove(dlFileEntry);
		}

		return dlFileEntry;
	}

	protected DLFileEntry removeImpl(DLFileEntry dlFileEntry)
		throws SystemException {
		dlFileEntry = toUnwrappedModel(dlFileEntry);

		Session session = null;

		try {
			session = openSession();

			if (dlFileEntry.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(DLFileEntryImpl.class,
						dlFileEntry.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(dlFileEntry);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		DLFileEntryModelImpl dlFileEntryModelImpl = (DLFileEntryModelImpl)dlFileEntry;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				dlFileEntryModelImpl.getOriginalUuid(),
				new Long(dlFileEntryModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_N,
			new Object[] {
				new Long(dlFileEntryModelImpl.getOriginalGroupId()),
				new Long(dlFileEntryModelImpl.getOriginalFolderId()),
				
			dlFileEntryModelImpl.getOriginalName()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_T,
			new Object[] {
				new Long(dlFileEntryModelImpl.getOriginalGroupId()),
				new Long(dlFileEntryModelImpl.getOriginalFolderId()),
				
			dlFileEntryModelImpl.getOriginalTitle()
			});

		EntityCacheUtil.removeResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryImpl.class, dlFileEntry.getPrimaryKey());

		return dlFileEntry;
	}

	public DLFileEntry updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry,
		boolean merge) throws SystemException {
		dlFileEntry = toUnwrappedModel(dlFileEntry);

		boolean isNew = dlFileEntry.isNew();

		DLFileEntryModelImpl dlFileEntryModelImpl = (DLFileEntryModelImpl)dlFileEntry;

		if (Validator.isNull(dlFileEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFileEntry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, dlFileEntry, merge);

			dlFileEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryImpl.class, dlFileEntry.getPrimaryKey(), dlFileEntry);

		if (!isNew &&
				(!Validator.equals(dlFileEntry.getUuid(),
					dlFileEntryModelImpl.getOriginalUuid()) ||
				(dlFileEntry.getGroupId() != dlFileEntryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					dlFileEntryModelImpl.getOriginalUuid(),
					new Long(dlFileEntryModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(dlFileEntry.getUuid(),
					dlFileEntryModelImpl.getOriginalUuid()) ||
				(dlFileEntry.getGroupId() != dlFileEntryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					dlFileEntry.getUuid(), new Long(dlFileEntry.getGroupId())
				}, dlFileEntry);
		}

		if (!isNew &&
				((dlFileEntry.getGroupId() != dlFileEntryModelImpl.getOriginalGroupId()) ||
				(dlFileEntry.getFolderId() != dlFileEntryModelImpl.getOriginalFolderId()) ||
				!Validator.equals(dlFileEntry.getName(),
					dlFileEntryModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_N,
				new Object[] {
					new Long(dlFileEntryModelImpl.getOriginalGroupId()),
					new Long(dlFileEntryModelImpl.getOriginalFolderId()),
					
				dlFileEntryModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((dlFileEntry.getGroupId() != dlFileEntryModelImpl.getOriginalGroupId()) ||
				(dlFileEntry.getFolderId() != dlFileEntryModelImpl.getOriginalFolderId()) ||
				!Validator.equals(dlFileEntry.getName(),
					dlFileEntryModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
				new Object[] {
					new Long(dlFileEntry.getGroupId()),
					new Long(dlFileEntry.getFolderId()),
					
				dlFileEntry.getName()
				}, dlFileEntry);
		}

		if (!isNew &&
				((dlFileEntry.getGroupId() != dlFileEntryModelImpl.getOriginalGroupId()) ||
				(dlFileEntry.getFolderId() != dlFileEntryModelImpl.getOriginalFolderId()) ||
				!Validator.equals(dlFileEntry.getTitle(),
					dlFileEntryModelImpl.getOriginalTitle()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F_T,
				new Object[] {
					new Long(dlFileEntryModelImpl.getOriginalGroupId()),
					new Long(dlFileEntryModelImpl.getOriginalFolderId()),
					
				dlFileEntryModelImpl.getOriginalTitle()
				});
		}

		if (isNew ||
				((dlFileEntry.getGroupId() != dlFileEntryModelImpl.getOriginalGroupId()) ||
				(dlFileEntry.getFolderId() != dlFileEntryModelImpl.getOriginalFolderId()) ||
				!Validator.equals(dlFileEntry.getTitle(),
					dlFileEntryModelImpl.getOriginalTitle()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_T,
				new Object[] {
					new Long(dlFileEntry.getGroupId()),
					new Long(dlFileEntry.getFolderId()),
					
				dlFileEntry.getTitle()
				}, dlFileEntry);
		}

		return dlFileEntry;
	}

	protected DLFileEntry toUnwrappedModel(DLFileEntry dlFileEntry) {
		if (dlFileEntry instanceof DLFileEntryImpl) {
			return dlFileEntry;
		}

		DLFileEntryImpl dlFileEntryImpl = new DLFileEntryImpl();

		dlFileEntryImpl.setNew(dlFileEntry.isNew());
		dlFileEntryImpl.setPrimaryKey(dlFileEntry.getPrimaryKey());

		dlFileEntryImpl.setUuid(dlFileEntry.getUuid());
		dlFileEntryImpl.setFileEntryId(dlFileEntry.getFileEntryId());
		dlFileEntryImpl.setGroupId(dlFileEntry.getGroupId());
		dlFileEntryImpl.setCompanyId(dlFileEntry.getCompanyId());
		dlFileEntryImpl.setUserId(dlFileEntry.getUserId());
		dlFileEntryImpl.setUserName(dlFileEntry.getUserName());
		dlFileEntryImpl.setVersionUserId(dlFileEntry.getVersionUserId());
		dlFileEntryImpl.setVersionUserName(dlFileEntry.getVersionUserName());
		dlFileEntryImpl.setCreateDate(dlFileEntry.getCreateDate());
		dlFileEntryImpl.setModifiedDate(dlFileEntry.getModifiedDate());
		dlFileEntryImpl.setFolderId(dlFileEntry.getFolderId());
		dlFileEntryImpl.setName(dlFileEntry.getName());
		dlFileEntryImpl.setTitle(dlFileEntry.getTitle());
		dlFileEntryImpl.setDescription(dlFileEntry.getDescription());
		dlFileEntryImpl.setVersion(dlFileEntry.getVersion());
		dlFileEntryImpl.setPendingVersion(dlFileEntry.getPendingVersion());
		dlFileEntryImpl.setSize(dlFileEntry.getSize());
		dlFileEntryImpl.setReadCount(dlFileEntry.getReadCount());
		dlFileEntryImpl.setExtraSettings(dlFileEntry.getExtraSettings());

		return dlFileEntryImpl;
	}

	public DLFileEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public DLFileEntry findByPrimaryKey(long fileEntryId)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByPrimaryKey(fileEntryId);

		if (dlFileEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + fileEntryId);
			}

			throw new NoSuchFileEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				fileEntryId);
		}

		return dlFileEntry;
	}

	public DLFileEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public DLFileEntry fetchByPrimaryKey(long fileEntryId)
		throws SystemException {
		DLFileEntry dlFileEntry = (DLFileEntry)EntityCacheUtil.getResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
				DLFileEntryImpl.class, fileEntryId, this);

		if (dlFileEntry == null) {
			Session session = null;

			try {
				session = openSession();

				dlFileEntry = (DLFileEntry)session.get(DLFileEntryImpl.class,
						new Long(fileEntryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (dlFileEntry != null) {
					cacheResult(dlFileEntry);
				}

				closeSession(session);
			}
		}

		return dlFileEntry;
	}

	public List<DLFileEntry> findByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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

				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);

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
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileEntry> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<DLFileEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
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

				query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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

				else {
					query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileEntry findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		List<DLFileEntry> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		int count = countByUuid(uuid);

		List<DLFileEntry> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry[] findByUuid_PrevAndNext(long fileEntryId, String uuid,
		OrderByComparator obc) throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

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
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileEntry);

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = (DLFileEntry)objArray[0];
			array[1] = (DLFileEntry)objArray[1];
			array[2] = (DLFileEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByUUID_G(uuid, groupId);

		if (dlFileEntry == null) {
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

			throw new NoSuchFileEntryException(msg.toString());
		}

		return dlFileEntry;
	}

	public DLFileEntry fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public DLFileEntry fetchByUUID_G(String uuid, long groupId,
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

				query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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

				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<DLFileEntry> list = q.list();

				result = list;

				DLFileEntry dlFileEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					dlFileEntry = list.get(0);

					cacheResult(dlFileEntry);

					if ((dlFileEntry.getUuid() == null) ||
							!dlFileEntry.getUuid().equals(uuid) ||
							(dlFileEntry.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, dlFileEntry);
					}
				}

				return dlFileEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<DLFileEntry>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (DLFileEntry)result;
			}
		}
	}

	public List<DLFileEntry> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);

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
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileEntry> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<DLFileEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
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

				query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileEntry findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		List<DLFileEntry> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		int count = countByGroupId(groupId);

		List<DLFileEntry> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry[] findByGroupId_PrevAndNext(long fileEntryId,
		long groupId, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileEntry);

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = (DLFileEntry)objArray[0];
			array[1] = (DLFileEntry)objArray[1];
			array[2] = (DLFileEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFileEntry> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);

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
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileEntry> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<DLFileEntry> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
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

				query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileEntry findByCompanyId_First(long companyId,
		OrderByComparator obc) throws NoSuchFileEntryException, SystemException {
		List<DLFileEntry> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry findByCompanyId_Last(long companyId,
		OrderByComparator obc) throws NoSuchFileEntryException, SystemException {
		int count = countByCompanyId(companyId);

		List<DLFileEntry> list = findByCompanyId(companyId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry[] findByCompanyId_PrevAndNext(long fileEntryId,
		long companyId, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileEntry);

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = (DLFileEntry)objArray[0];
			array[1] = (DLFileEntry)objArray[1];
			array[2] = (DLFileEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFileEntry> findByG_U(long groupId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_U_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_USERID_2);

				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileEntry> findByG_U(long groupId, long userId, int start,
		int end) throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	public List<DLFileEntry> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_U,
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

				query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_U_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_USERID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_U,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileEntry findByG_U_First(long groupId, long userId,
		OrderByComparator obc) throws NoSuchFileEntryException, SystemException {
		List<DLFileEntry> list = findByG_U(groupId, userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry findByG_U_Last(long groupId, long userId,
		OrderByComparator obc) throws NoSuchFileEntryException, SystemException {
		int count = countByG_U(groupId, userId);

		List<DLFileEntry> list = findByG_U(groupId, userId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry[] findByG_U_PrevAndNext(long fileEntryId, long groupId,
		long userId, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		int count = countByG_U(groupId, userId);

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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileEntry);

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = (DLFileEntry)objArray[0];
			array[1] = (DLFileEntry)objArray[1];
			array[2] = (DLFileEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFileEntry> findByG_F(long groupId, long folderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(folderId) };

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_F,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_F_GROUPID_2);

				query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);

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
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_F, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileEntry> findByG_F(long groupId, long folderId, int start,
		int end) throws SystemException {
		return findByG_F(groupId, folderId, start, end, null);
	}

	public List<DLFileEntry> findByG_F(long groupId, long folderId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_F,
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

				query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_F_GROUPID_2);

				query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_F,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileEntry findByG_F_First(long groupId, long folderId,
		OrderByComparator obc) throws NoSuchFileEntryException, SystemException {
		List<DLFileEntry> list = findByG_F(groupId, folderId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry findByG_F_Last(long groupId, long folderId,
		OrderByComparator obc) throws NoSuchFileEntryException, SystemException {
		int count = countByG_F(groupId, folderId);

		List<DLFileEntry> list = findByG_F(groupId, folderId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileEntry[] findByG_F_PrevAndNext(long fileEntryId, long groupId,
		long folderId, OrderByComparator obc)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

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
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileEntry);

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = (DLFileEntry)objArray[0];
			array[1] = (DLFileEntry)objArray[1];
			array[2] = (DLFileEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileEntry findByG_F_N(long groupId, long folderId, String name)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByG_F_N(groupId, folderId, name);

		if (dlFileEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileEntryException(msg.toString());
		}

		return dlFileEntry;
	}

	public DLFileEntry fetchByG_F_N(long groupId, long folderId, String name)
		throws SystemException {
		return fetchByG_F_N(groupId, folderId, name, true);
	}

	public DLFileEntry fetchByG_F_N(long groupId, long folderId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId),
				
				name
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_F_N,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_F_N_GROUPID_2);

				query.append(_FINDER_COLUMN_G_F_N_FOLDERID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_G_F_N_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_F_N_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_F_N_NAME_2);
					}
				}

				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				List<DLFileEntry> list = q.list();

				result = list;

				DLFileEntry dlFileEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
						finderArgs, list);
				}
				else {
					dlFileEntry = list.get(0);

					cacheResult(dlFileEntry);

					if ((dlFileEntry.getGroupId() != groupId) ||
							(dlFileEntry.getFolderId() != folderId) ||
							(dlFileEntry.getName() == null) ||
							!dlFileEntry.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
							finderArgs, dlFileEntry);
					}
				}

				return dlFileEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_N,
						finderArgs, new ArrayList<DLFileEntry>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (DLFileEntry)result;
			}
		}
	}

	public DLFileEntry findByG_F_T(long groupId, long folderId, String title)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = fetchByG_F_T(groupId, folderId, title);

		if (dlFileEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", title=");
			msg.append(title);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileEntryException(msg.toString());
		}

		return dlFileEntry;
	}

	public DLFileEntry fetchByG_F_T(long groupId, long folderId, String title)
		throws SystemException {
		return fetchByG_F_T(groupId, folderId, title, true);
	}

	public DLFileEntry fetchByG_F_T(long groupId, long folderId, String title,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId),
				
				title
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_F_T,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_F_T_GROUPID_2);

				query.append(_FINDER_COLUMN_G_F_T_FOLDERID_2);

				if (title == null) {
					query.append(_FINDER_COLUMN_G_F_T_TITLE_1);
				}
				else {
					if (title.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_F_T_TITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_F_T_TITLE_2);
					}
				}

				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (title != null) {
					qPos.add(title);
				}

				List<DLFileEntry> list = q.list();

				result = list;

				DLFileEntry dlFileEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_T,
						finderArgs, list);
				}
				else {
					dlFileEntry = list.get(0);

					cacheResult(dlFileEntry);

					if ((dlFileEntry.getGroupId() != groupId) ||
							(dlFileEntry.getFolderId() != folderId) ||
							(dlFileEntry.getTitle() == null) ||
							!dlFileEntry.getTitle().equals(title)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_T,
							finderArgs, dlFileEntry);
					}
				}

				return dlFileEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F_T,
						finderArgs, new ArrayList<DLFileEntry>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (DLFileEntry)result;
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

	public List<DLFileEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<DLFileEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<DLFileEntry> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileEntry> list = (List<DLFileEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_DLFILEENTRY);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_DLFILEENTRY.concat(DLFileEntryModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (DLFileEntry dlFileEntry : findByUuid(uuid)) {
			remove(dlFileEntry);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByUUID_G(uuid, groupId);

		remove(dlFileEntry);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (DLFileEntry dlFileEntry : findByGroupId(groupId)) {
			remove(dlFileEntry);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (DLFileEntry dlFileEntry : findByCompanyId(companyId)) {
			remove(dlFileEntry);
		}
	}

	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (DLFileEntry dlFileEntry : findByG_U(groupId, userId)) {
			remove(dlFileEntry);
		}
	}

	public void removeByG_F(long groupId, long folderId)
		throws SystemException {
		for (DLFileEntry dlFileEntry : findByG_F(groupId, folderId)) {
			remove(dlFileEntry);
		}
	}

	public void removeByG_F_N(long groupId, long folderId, String name)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByG_F_N(groupId, folderId, name);

		remove(dlFileEntry);
	}

	public void removeByG_F_T(long groupId, long folderId, String title)
		throws NoSuchFileEntryException, SystemException {
		DLFileEntry dlFileEntry = findByG_F_T(groupId, folderId, title);

		remove(dlFileEntry);
	}

	public void removeAll() throws SystemException {
		for (DLFileEntry dlFileEntry : findAll()) {
			remove(dlFileEntry);
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

				query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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

				query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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

				query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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

				query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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

	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_U_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U, finderArgs,
					count);

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

				query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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

	public int countByG_F_N(long groupId, long folderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId),
				
				name
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_F_N_GROUPID_2);

				query.append(_FINDER_COLUMN_G_F_N_FOLDERID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_G_F_N_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_F_N_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_F_N_NAME_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F_N,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_F_T(long groupId, long folderId, String title)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(folderId),
				
				title
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

				query.append(_FINDER_COLUMN_G_F_T_GROUPID_2);

				query.append(_FINDER_COLUMN_G_F_T_FOLDERID_2);

				if (title == null) {
					query.append(_FINDER_COLUMN_G_F_T_TITLE_1);
				}
				else {
					if (title.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_F_T_TITLE_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_F_T_TITLE_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F_T,
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

				Query q = session.createQuery(_SQL_COUNT_DLFILEENTRY);

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
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFileEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLFileEntry>> listenersList = new ArrayList<ModelListener<DLFileEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLFileEntry>)Class.forName(
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
	@BeanReference(name = "com.liferay.portal.service.persistence.LockPersistence")
	protected com.liferay.portal.service.persistence.LockPersistence lockPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetEntryPersistence assetEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetTagPersistence assetTagPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence mbDiscussionPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence mbMessagePersistence;
	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsEntryPersistence")
	protected com.liferay.portlet.ratings.service.persistence.RatingsEntryPersistence ratingsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence")
	protected com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence")
	protected com.liferay.portlet.social.service.persistence.SocialActivityPersistence socialActivityPersistence;
	private static final String _SQL_SELECT_DLFILEENTRY = "SELECT dlFileEntry FROM DLFileEntry dlFileEntry";
	private static final String _SQL_SELECT_DLFILEENTRY_WHERE = "SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ";
	private static final String _SQL_COUNT_DLFILEENTRY = "SELECT COUNT(dlFileEntry) FROM DLFileEntry dlFileEntry";
	private static final String _SQL_COUNT_DLFILEENTRY_WHERE = "SELECT COUNT(dlFileEntry) FROM DLFileEntry dlFileEntry WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "dlFileEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "dlFileEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(dlFileEntry.uuid IS NULL OR dlFileEntry.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "dlFileEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "dlFileEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(dlFileEntry.uuid IS NULL OR dlFileEntry.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "dlFileEntry.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "dlFileEntry.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "dlFileEntry.companyId = ?";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "dlFileEntry.userId = ?";
	private static final String _FINDER_COLUMN_G_F_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_FOLDERID_2 = "dlFileEntry.folderId = ?";
	private static final String _FINDER_COLUMN_G_F_N_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_N_FOLDERID_2 = "dlFileEntry.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_N_NAME_1 = "dlFileEntry.name IS NULL";
	private static final String _FINDER_COLUMN_G_F_N_NAME_2 = "dlFileEntry.name = ?";
	private static final String _FINDER_COLUMN_G_F_N_NAME_3 = "(dlFileEntry.name IS NULL OR dlFileEntry.name = ?)";
	private static final String _FINDER_COLUMN_G_F_T_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_T_FOLDERID_2 = "dlFileEntry.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_T_TITLE_1 = "dlFileEntry.title IS NULL";
	private static final String _FINDER_COLUMN_G_F_T_TITLE_2 = "dlFileEntry.title = ?";
	private static final String _FINDER_COLUMN_G_F_T_TITLE_3 = "(dlFileEntry.title IS NULL OR dlFileEntry.title = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFileEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFileEntry exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(DLFileEntryPersistenceImpl.class);
}