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

package com.liferay.portlet.messageboards.service.persistence;

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

import com.liferay.portlet.messageboards.NoSuchCategoryException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="MBCategoryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBCategoryPersistence
 * @see       MBCategoryUtil
 * @generated
 */
public class MBCategoryPersistenceImpl extends BasePersistenceImpl<MBCategory>
	implements MBCategoryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = MBCategoryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_P = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_P = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(MBCategory mbCategory) {
		EntityCacheUtil.putResult(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryImpl.class, mbCategory.getPrimaryKey(), mbCategory);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { mbCategory.getUuid(), new Long(
					mbCategory.getGroupId()) }, mbCategory);
	}

	public void cacheResult(List<MBCategory> mbCategories) {
		for (MBCategory mbCategory : mbCategories) {
			if (EntityCacheUtil.getResult(
						MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
						MBCategoryImpl.class, mbCategory.getPrimaryKey(), this) == null) {
				cacheResult(mbCategory);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(MBCategoryImpl.class.getName());
		EntityCacheUtil.clearCache(MBCategoryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public MBCategory create(long categoryId) {
		MBCategory mbCategory = new MBCategoryImpl();

		mbCategory.setNew(true);
		mbCategory.setPrimaryKey(categoryId);

		String uuid = PortalUUIDUtil.generate();

		mbCategory.setUuid(uuid);

		return mbCategory;
	}

	public MBCategory remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public MBCategory remove(long categoryId)
		throws NoSuchCategoryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBCategory mbCategory = (MBCategory)session.get(MBCategoryImpl.class,
					new Long(categoryId));

			if (mbCategory == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No MBCategory exists with the primary key " +
						categoryId);
				}

				throw new NoSuchCategoryException(
					"No MBCategory exists with the primary key " + categoryId);
			}

			return remove(mbCategory);
		}
		catch (NoSuchCategoryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBCategory remove(MBCategory mbCategory) throws SystemException {
		for (ModelListener<MBCategory> listener : listeners) {
			listener.onBeforeRemove(mbCategory);
		}

		mbCategory = removeImpl(mbCategory);

		for (ModelListener<MBCategory> listener : listeners) {
			listener.onAfterRemove(mbCategory);
		}

		return mbCategory;
	}

	protected MBCategory removeImpl(MBCategory mbCategory)
		throws SystemException {
		mbCategory = toUnwrappedModel(mbCategory);

		Session session = null;

		try {
			session = openSession();

			if (mbCategory.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(MBCategoryImpl.class,
						mbCategory.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(mbCategory);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		MBCategoryModelImpl mbCategoryModelImpl = (MBCategoryModelImpl)mbCategory;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				mbCategoryModelImpl.getOriginalUuid(),
				new Long(mbCategoryModelImpl.getOriginalGroupId())
			});

		EntityCacheUtil.removeResult(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryImpl.class, mbCategory.getPrimaryKey());

		return mbCategory;
	}

	public MBCategory updateImpl(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory,
		boolean merge) throws SystemException {
		mbCategory = toUnwrappedModel(mbCategory);

		boolean isNew = mbCategory.isNew();

		MBCategoryModelImpl mbCategoryModelImpl = (MBCategoryModelImpl)mbCategory;

		if (Validator.isNull(mbCategory.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mbCategory.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, mbCategory, merge);

			mbCategory.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryImpl.class, mbCategory.getPrimaryKey(), mbCategory);

		if (!isNew &&
				(!Validator.equals(mbCategory.getUuid(),
					mbCategoryModelImpl.getOriginalUuid()) ||
				(mbCategory.getGroupId() != mbCategoryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					mbCategoryModelImpl.getOriginalUuid(),
					new Long(mbCategoryModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(mbCategory.getUuid(),
					mbCategoryModelImpl.getOriginalUuid()) ||
				(mbCategory.getGroupId() != mbCategoryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					mbCategory.getUuid(), new Long(mbCategory.getGroupId())
				}, mbCategory);
		}

		return mbCategory;
	}

	protected MBCategory toUnwrappedModel(MBCategory mbCategory) {
		if (mbCategory instanceof MBCategoryImpl) {
			return mbCategory;
		}

		MBCategoryImpl mbCategoryImpl = new MBCategoryImpl();

		mbCategoryImpl.setNew(mbCategory.isNew());
		mbCategoryImpl.setPrimaryKey(mbCategory.getPrimaryKey());

		mbCategoryImpl.setUuid(mbCategory.getUuid());
		mbCategoryImpl.setCategoryId(mbCategory.getCategoryId());
		mbCategoryImpl.setGroupId(mbCategory.getGroupId());
		mbCategoryImpl.setCompanyId(mbCategory.getCompanyId());
		mbCategoryImpl.setUserId(mbCategory.getUserId());
		mbCategoryImpl.setUserName(mbCategory.getUserName());
		mbCategoryImpl.setCreateDate(mbCategory.getCreateDate());
		mbCategoryImpl.setModifiedDate(mbCategory.getModifiedDate());
		mbCategoryImpl.setParentCategoryId(mbCategory.getParentCategoryId());
		mbCategoryImpl.setName(mbCategory.getName());
		mbCategoryImpl.setDescription(mbCategory.getDescription());
		mbCategoryImpl.setThreadCount(mbCategory.getThreadCount());
		mbCategoryImpl.setMessageCount(mbCategory.getMessageCount());
		mbCategoryImpl.setLastPostDate(mbCategory.getLastPostDate());

		return mbCategoryImpl;
	}

	public MBCategory findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public MBCategory findByPrimaryKey(long categoryId)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = fetchByPrimaryKey(categoryId);

		if (mbCategory == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No MBCategory exists with the primary key " +
					categoryId);
			}

			throw new NoSuchCategoryException(
				"No MBCategory exists with the primary key " + categoryId);
		}

		return mbCategory;
	}

	public MBCategory fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public MBCategory fetchByPrimaryKey(long categoryId)
		throws SystemException {
		MBCategory mbCategory = (MBCategory)EntityCacheUtil.getResult(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
				MBCategoryImpl.class, categoryId, this);

		if (mbCategory == null) {
			Session session = null;

			try {
				session = openSession();

				mbCategory = (MBCategory)session.get(MBCategoryImpl.class,
						new Long(categoryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (mbCategory != null) {
					cacheResult(mbCategory);
				}

				closeSession(session);
			}
		}

		return mbCategory;
	}

	public List<MBCategory> findByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<MBCategory> list = (List<MBCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(9);
				query.append(
					"SELECT mbCategory FROM MBCategory mbCategory WHERE ");

				if (uuid == null) {
					query.append("mbCategory.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(mbCategory.uuid IS NULL OR ");
					}

					query.append("mbCategory.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbCategory.parentCategoryId ASC, ");
				query.append("mbCategory.name ASC");

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
					list = new ArrayList<MBCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBCategory> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<MBCategory> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBCategory> list = (List<MBCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 6;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				if (9 > arrayCapacity) {
					arrayCapacity = 9;
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT mbCategory FROM MBCategory mbCategory WHERE ");

				if (uuid == null) {
					query.append("mbCategory.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(mbCategory.uuid IS NULL OR ");
					}

					query.append("mbCategory.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbCategory.");
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

					query.append("mbCategory.parentCategoryId ASC, ");
					query.append("mbCategory.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<MBCategory>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBCategory findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		List<MBCategory> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No MBCategory exists with the key {");
			msg.append("uuid=" + uuid);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBCategory findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		int count = countByUuid(uuid);

		List<MBCategory> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No MBCategory exists with the key {");
			msg.append("uuid=" + uuid);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBCategory[] findByUuid_PrevAndNext(long categoryId, String uuid,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = findByPrimaryKey(categoryId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 6;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			if (9 > arrayCapacity) {
				arrayCapacity = 9;
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append("SELECT mbCategory FROM MBCategory mbCategory WHERE ");

			if (uuid == null) {
				query.append("mbCategory.uuid IS NULL");
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append("(mbCategory.uuid IS NULL OR ");
				}

				query.append("mbCategory.uuid = ?");

				if (uuid.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbCategory.");
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

				query.append("mbCategory.parentCategoryId ASC, ");
				query.append("mbCategory.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbCategory);

			MBCategory[] array = new MBCategoryImpl[3];

			array[0] = (MBCategory)objArray[0];
			array[1] = (MBCategory)objArray[1];
			array[2] = (MBCategory)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBCategory findByUUID_G(String uuid, long groupId)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = fetchByUUID_G(uuid, groupId);

		if (mbCategory == null) {
			StringBundler msg = new StringBundler(5);
			msg.append("No MBCategory exists with the key {");
			msg.append("uuid=" + uuid);
			msg.append(", ");
			msg.append("groupId=" + groupId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCategoryException(msg.toString());
		}

		return mbCategory;
	}

	public MBCategory fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public MBCategory fetchByUUID_G(String uuid, long groupId,
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

				StringBundler query = new StringBundler(11);
				query.append(
					"SELECT mbCategory FROM MBCategory mbCategory WHERE ");

				if (uuid == null) {
					query.append("mbCategory.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(mbCategory.uuid IS NULL OR ");
					}

					query.append("mbCategory.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("mbCategory.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbCategory.parentCategoryId ASC, ");
				query.append("mbCategory.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<MBCategory> list = q.list();

				result = list;

				MBCategory mbCategory = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					mbCategory = list.get(0);

					cacheResult(mbCategory);

					if ((mbCategory.getUuid() == null) ||
							!mbCategory.getUuid().equals(uuid) ||
							(mbCategory.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, mbCategory);
					}
				}

				return mbCategory;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<MBCategory>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (MBCategory)result;
			}
		}
	}

	public List<MBCategory> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<MBCategory> list = (List<MBCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);
				query.append(
					"SELECT mbCategory FROM MBCategory mbCategory WHERE ");

				query.append("mbCategory.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbCategory.parentCategoryId ASC, ");
				query.append("mbCategory.name ASC");

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
					list = new ArrayList<MBCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBCategory> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<MBCategory> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBCategory> list = (List<MBCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 3;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				if (6 > arrayCapacity) {
					arrayCapacity = 6;
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT mbCategory FROM MBCategory mbCategory WHERE ");

				query.append("mbCategory.groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbCategory.");
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

					query.append("mbCategory.parentCategoryId ASC, ");
					query.append("mbCategory.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<MBCategory>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBCategory findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		List<MBCategory> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No MBCategory exists with the key {");
			msg.append("groupId=" + groupId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBCategory findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		int count = countByGroupId(groupId);

		List<MBCategory> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No MBCategory exists with the key {");
			msg.append("groupId=" + groupId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBCategory[] findByGroupId_PrevAndNext(long categoryId,
		long groupId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = findByPrimaryKey(categoryId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 3;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			if (6 > arrayCapacity) {
				arrayCapacity = 6;
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append("SELECT mbCategory FROM MBCategory mbCategory WHERE ");

			query.append("mbCategory.groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbCategory.");
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

				query.append("mbCategory.parentCategoryId ASC, ");
				query.append("mbCategory.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbCategory);

			MBCategory[] array = new MBCategoryImpl[3];

			array[0] = (MBCategory)objArray[0];
			array[1] = (MBCategory)objArray[1];
			array[2] = (MBCategory)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBCategory> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<MBCategory> list = (List<MBCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);
				query.append(
					"SELECT mbCategory FROM MBCategory mbCategory WHERE ");

				query.append("mbCategory.companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbCategory.parentCategoryId ASC, ");
				query.append("mbCategory.name ASC");

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
					list = new ArrayList<MBCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBCategory> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<MBCategory> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBCategory> list = (List<MBCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 3;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				if (6 > arrayCapacity) {
					arrayCapacity = 6;
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT mbCategory FROM MBCategory mbCategory WHERE ");

				query.append("mbCategory.companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbCategory.");
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

					query.append("mbCategory.parentCategoryId ASC, ");
					query.append("mbCategory.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<MBCategory>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBCategory findByCompanyId_First(long companyId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		List<MBCategory> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No MBCategory exists with the key {");
			msg.append("companyId=" + companyId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBCategory findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		int count = countByCompanyId(companyId);

		List<MBCategory> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No MBCategory exists with the key {");
			msg.append("companyId=" + companyId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBCategory[] findByCompanyId_PrevAndNext(long categoryId,
		long companyId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = findByPrimaryKey(categoryId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 3;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			if (6 > arrayCapacity) {
				arrayCapacity = 6;
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append("SELECT mbCategory FROM MBCategory mbCategory WHERE ");

			query.append("mbCategory.companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbCategory.");
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

				query.append("mbCategory.parentCategoryId ASC, ");
				query.append("mbCategory.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbCategory);

			MBCategory[] array = new MBCategoryImpl[3];

			array[0] = (MBCategory)objArray[0];
			array[1] = (MBCategory)objArray[1];
			array[2] = (MBCategory)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBCategory> findByG_P(long groupId, long parentCategoryId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentCategoryId)
			};

		List<MBCategory> list = (List<MBCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(8);
				query.append(
					"SELECT mbCategory FROM MBCategory mbCategory WHERE ");

				query.append("mbCategory.groupId = ?");

				query.append(" AND ");

				query.append("mbCategory.parentCategoryId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbCategory.parentCategoryId ASC, ");
				query.append("mbCategory.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentCategoryId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_P, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBCategory> findByG_P(long groupId, long parentCategoryId,
		int start, int end) throws SystemException {
		return findByG_P(groupId, parentCategoryId, start, end, null);
	}

	public List<MBCategory> findByG_P(long groupId, long parentCategoryId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentCategoryId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBCategory> list = (List<MBCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 5;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				if (8 > arrayCapacity) {
					arrayCapacity = 8;
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT mbCategory FROM MBCategory mbCategory WHERE ");

				query.append("mbCategory.groupId = ?");

				query.append(" AND ");

				query.append("mbCategory.parentCategoryId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbCategory.");
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

					query.append("mbCategory.parentCategoryId ASC, ");
					query.append("mbCategory.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentCategoryId);

				list = (List<MBCategory>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBCategory findByG_P_First(long groupId, long parentCategoryId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		List<MBCategory> list = findByG_P(groupId, parentCategoryId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(5);
			msg.append("No MBCategory exists with the key {");
			msg.append("groupId=" + groupId);
			msg.append(", ");
			msg.append("parentCategoryId=" + parentCategoryId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBCategory findByG_P_Last(long groupId, long parentCategoryId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		int count = countByG_P(groupId, parentCategoryId);

		List<MBCategory> list = findByG_P(groupId, parentCategoryId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(5);
			msg.append("No MBCategory exists with the key {");
			msg.append("groupId=" + groupId);
			msg.append(", ");
			msg.append("parentCategoryId=" + parentCategoryId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBCategory[] findByG_P_PrevAndNext(long categoryId, long groupId,
		long parentCategoryId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = findByPrimaryKey(categoryId);

		int count = countByG_P(groupId, parentCategoryId);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 5;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			if (8 > arrayCapacity) {
				arrayCapacity = 8;
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append("SELECT mbCategory FROM MBCategory mbCategory WHERE ");

			query.append("mbCategory.groupId = ?");

			query.append(" AND ");

			query.append("mbCategory.parentCategoryId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbCategory.");
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

				query.append("mbCategory.parentCategoryId ASC, ");
				query.append("mbCategory.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentCategoryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbCategory);

			MBCategory[] array = new MBCategoryImpl[3];

			array[0] = (MBCategory)objArray[0];
			array[1] = (MBCategory)objArray[1];
			array[2] = (MBCategory)objArray[2];

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

	public List<MBCategory> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<MBCategory> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<MBCategory> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBCategory> list = (List<MBCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 1;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				if (4 > arrayCapacity) {
					arrayCapacity = 4;
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append("SELECT mbCategory FROM MBCategory mbCategory ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbCategory.");
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

					query.append("mbCategory.parentCategoryId ASC, ");
					query.append("mbCategory.name ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<MBCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MBCategory>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (MBCategory mbCategory : findByUuid(uuid)) {
			remove(mbCategory);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = findByUUID_G(uuid, groupId);

		remove(mbCategory);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (MBCategory mbCategory : findByGroupId(groupId)) {
			remove(mbCategory);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (MBCategory mbCategory : findByCompanyId(companyId)) {
			remove(mbCategory);
		}
	}

	public void removeByG_P(long groupId, long parentCategoryId)
		throws SystemException {
		for (MBCategory mbCategory : findByG_P(groupId, parentCategoryId)) {
			remove(mbCategory);
		}
	}

	public void removeAll() throws SystemException {
		for (MBCategory mbCategory : findAll()) {
			remove(mbCategory);
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

				StringBundler query = new StringBundler(7);
				query.append("SELECT COUNT(mbCategory) ");
				query.append("FROM MBCategory mbCategory WHERE ");

				if (uuid == null) {
					query.append("mbCategory.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(mbCategory.uuid IS NULL OR ");
					}

					query.append("mbCategory.uuid = ?");

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

				StringBundler query = new StringBundler(9);
				query.append("SELECT COUNT(mbCategory) ");
				query.append("FROM MBCategory mbCategory WHERE ");

				if (uuid == null) {
					query.append("mbCategory.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(mbCategory.uuid IS NULL OR ");
					}

					query.append("mbCategory.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("mbCategory.groupId = ?");

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

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);
				query.append("SELECT COUNT(mbCategory) ");
				query.append("FROM MBCategory mbCategory WHERE ");

				query.append("mbCategory.groupId = ?");

				query.append(" ");

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

				StringBundler query = new StringBundler(4);
				query.append("SELECT COUNT(mbCategory) ");
				query.append("FROM MBCategory mbCategory WHERE ");

				query.append("mbCategory.companyId = ?");

				query.append(" ");

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

	public int countByG_P(long groupId, long parentCategoryId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentCategoryId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);
				query.append("SELECT COUNT(mbCategory) ");
				query.append("FROM MBCategory mbCategory WHERE ");

				query.append("mbCategory.groupId = ?");

				query.append(" AND ");

				query.append("mbCategory.parentCategoryId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentCategoryId);

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

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(mbCategory) FROM MBCategory mbCategory");

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
						"value.object.listener.com.liferay.portlet.messageboards.model.MBCategory")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MBCategory>> listenersList = new ArrayList<ModelListener<MBCategory>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MBCategory>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBBanPersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBBanPersistence mbBanPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence mbCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence mbDiscussionPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMailingListPersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBMailingListPersistence mbMailingListPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence mbMessagePersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPersistence mbMessageFlagPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBThreadPersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBThreadPersistence mbThreadPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetTagPersistence assetTagPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	private static Log _log = LogFactoryUtil.getLog(MBCategoryPersistenceImpl.class);
}