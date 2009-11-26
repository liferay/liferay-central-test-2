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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.journal.NoSuchFeedException;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.impl.JournalFeedImpl;
import com.liferay.portlet.journal.model.impl.JournalFeedModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="JournalFeedPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalFeedPersistence
 * @see       JournalFeedUtil
 * @generated
 */
public class JournalFeedPersistenceImpl extends BasePersistenceImpl<JournalFeed>
	implements JournalFeedPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = JournalFeedImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_F",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_F",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(JournalFeed journalFeed) {
		EntityCacheUtil.putResult(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedImpl.class, journalFeed.getPrimaryKey(), journalFeed);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalFeed.getUuid(), new Long(journalFeed.getGroupId())
			}, journalFeed);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
			new Object[] {
				new Long(journalFeed.getGroupId()),
				
			journalFeed.getFeedId()
			}, journalFeed);
	}

	public void cacheResult(List<JournalFeed> journalFeeds) {
		for (JournalFeed journalFeed : journalFeeds) {
			if (EntityCacheUtil.getResult(
						JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
						JournalFeedImpl.class, journalFeed.getPrimaryKey(), this) == null) {
				cacheResult(journalFeed);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(JournalFeedImpl.class.getName());
		EntityCacheUtil.clearCache(JournalFeedImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public JournalFeed create(long id) {
		JournalFeed journalFeed = new JournalFeedImpl();

		journalFeed.setNew(true);
		journalFeed.setPrimaryKey(id);

		String uuid = PortalUUIDUtil.generate();

		journalFeed.setUuid(uuid);

		return journalFeed;
	}

	public JournalFeed remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public JournalFeed remove(long id)
		throws NoSuchFeedException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalFeed journalFeed = (JournalFeed)session.get(JournalFeedImpl.class,
					new Long(id));

			if (journalFeed == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No JournalFeed exists with the primary key " +
						id);
				}

				throw new NoSuchFeedException(
					"No JournalFeed exists with the primary key " + id);
			}

			return remove(journalFeed);
		}
		catch (NoSuchFeedException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalFeed remove(JournalFeed journalFeed)
		throws SystemException {
		for (ModelListener<JournalFeed> listener : listeners) {
			listener.onBeforeRemove(journalFeed);
		}

		journalFeed = removeImpl(journalFeed);

		for (ModelListener<JournalFeed> listener : listeners) {
			listener.onAfterRemove(journalFeed);
		}

		return journalFeed;
	}

	protected JournalFeed removeImpl(JournalFeed journalFeed)
		throws SystemException {
		journalFeed = toUnwrappedModel(journalFeed);

		Session session = null;

		try {
			session = openSession();

			if (journalFeed.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(JournalFeedImpl.class,
						journalFeed.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(journalFeed);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		JournalFeedModelImpl journalFeedModelImpl = (JournalFeedModelImpl)journalFeed;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalFeedModelImpl.getOriginalUuid(),
				new Long(journalFeedModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F,
			new Object[] {
				new Long(journalFeedModelImpl.getOriginalGroupId()),
				
			journalFeedModelImpl.getOriginalFeedId()
			});

		EntityCacheUtil.removeResult(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedImpl.class, journalFeed.getPrimaryKey());

		return journalFeed;
	}

	public JournalFeed updateImpl(
		com.liferay.portlet.journal.model.JournalFeed journalFeed, boolean merge)
		throws SystemException {
		journalFeed = toUnwrappedModel(journalFeed);

		boolean isNew = journalFeed.isNew();

		JournalFeedModelImpl journalFeedModelImpl = (JournalFeedModelImpl)journalFeed;

		if (Validator.isNull(journalFeed.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalFeed.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, journalFeed, merge);

			journalFeed.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedImpl.class, journalFeed.getPrimaryKey(), journalFeed);

		if (!isNew &&
				(!Validator.equals(journalFeed.getUuid(),
					journalFeedModelImpl.getOriginalUuid()) ||
				(journalFeed.getGroupId() != journalFeedModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalFeedModelImpl.getOriginalUuid(),
					new Long(journalFeedModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(journalFeed.getUuid(),
					journalFeedModelImpl.getOriginalUuid()) ||
				(journalFeed.getGroupId() != journalFeedModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalFeed.getUuid(), new Long(journalFeed.getGroupId())
				}, journalFeed);
		}

		if (!isNew &&
				((journalFeed.getGroupId() != journalFeedModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalFeed.getFeedId(),
					journalFeedModelImpl.getOriginalFeedId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F,
				new Object[] {
					new Long(journalFeedModelImpl.getOriginalGroupId()),
					
				journalFeedModelImpl.getOriginalFeedId()
				});
		}

		if (isNew ||
				((journalFeed.getGroupId() != journalFeedModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalFeed.getFeedId(),
					journalFeedModelImpl.getOriginalFeedId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
				new Object[] {
					new Long(journalFeed.getGroupId()),
					
				journalFeed.getFeedId()
				}, journalFeed);
		}

		return journalFeed;
	}

	protected JournalFeed toUnwrappedModel(JournalFeed journalFeed) {
		if (journalFeed instanceof JournalFeedImpl) {
			return journalFeed;
		}

		JournalFeedImpl journalFeedImpl = new JournalFeedImpl();

		journalFeedImpl.setNew(journalFeed.isNew());
		journalFeedImpl.setPrimaryKey(journalFeed.getPrimaryKey());

		journalFeedImpl.setUuid(journalFeed.getUuid());
		journalFeedImpl.setId(journalFeed.getId());
		journalFeedImpl.setGroupId(journalFeed.getGroupId());
		journalFeedImpl.setCompanyId(journalFeed.getCompanyId());
		journalFeedImpl.setUserId(journalFeed.getUserId());
		journalFeedImpl.setUserName(journalFeed.getUserName());
		journalFeedImpl.setCreateDate(journalFeed.getCreateDate());
		journalFeedImpl.setModifiedDate(journalFeed.getModifiedDate());
		journalFeedImpl.setFeedId(journalFeed.getFeedId());
		journalFeedImpl.setName(journalFeed.getName());
		journalFeedImpl.setDescription(journalFeed.getDescription());
		journalFeedImpl.setType(journalFeed.getType());
		journalFeedImpl.setStructureId(journalFeed.getStructureId());
		journalFeedImpl.setTemplateId(journalFeed.getTemplateId());
		journalFeedImpl.setRendererTemplateId(journalFeed.getRendererTemplateId());
		journalFeedImpl.setDelta(journalFeed.getDelta());
		journalFeedImpl.setOrderByCol(journalFeed.getOrderByCol());
		journalFeedImpl.setOrderByType(journalFeed.getOrderByType());
		journalFeedImpl.setTargetLayoutFriendlyUrl(journalFeed.getTargetLayoutFriendlyUrl());
		journalFeedImpl.setTargetPortletId(journalFeed.getTargetPortletId());
		journalFeedImpl.setContentField(journalFeed.getContentField());
		journalFeedImpl.setFeedType(journalFeed.getFeedType());
		journalFeedImpl.setFeedVersion(journalFeed.getFeedVersion());

		return journalFeedImpl;
	}

	public JournalFeed findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JournalFeed findByPrimaryKey(long id)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = fetchByPrimaryKey(id);

		if (journalFeed == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No JournalFeed exists with the primary key " + id);
			}

			throw new NoSuchFeedException(
				"No JournalFeed exists with the primary key " + id);
		}

		return journalFeed;
	}

	public JournalFeed fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JournalFeed fetchByPrimaryKey(long id) throws SystemException {
		JournalFeed journalFeed = (JournalFeed)EntityCacheUtil.getResult(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
				JournalFeedImpl.class, id, this);

		if (journalFeed == null) {
			Session session = null;

			try {
				session = openSession();

				journalFeed = (JournalFeed)session.get(JournalFeedImpl.class,
						new Long(id));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (journalFeed != null) {
					cacheResult(journalFeed);
				}

				closeSession(session);
			}
		}

		return journalFeed;
	}

	public List<JournalFeed> findByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<JournalFeed> list = (List<JournalFeed>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalFeed FROM JournalFeed journalFeed WHERE ");

				if (uuid == null) {
					query.append("journalFeed.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(journalFeed.uuid IS NULL OR ");
					}

					query.append("journalFeed.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("journalFeed.feedId ASC");

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
					list = new ArrayList<JournalFeed>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalFeed> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<JournalFeed> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalFeed> list = (List<JournalFeed>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalFeed FROM JournalFeed journalFeed WHERE ");

				if (uuid == null) {
					query.append("journalFeed.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(journalFeed.uuid IS NULL OR ");
					}

					query.append("journalFeed.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalFeed.");
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

					query.append("journalFeed.feedId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<JournalFeed>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalFeed>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalFeed findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchFeedException, SystemException {
		List<JournalFeed> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalFeed exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFeedException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalFeed findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchFeedException, SystemException {
		int count = countByUuid(uuid);

		List<JournalFeed> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalFeed exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFeedException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalFeed[] findByUuid_PrevAndNext(long id, String uuid,
		OrderByComparator obc) throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = findByPrimaryKey(id);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT journalFeed FROM JournalFeed journalFeed WHERE ");

			if (uuid == null) {
				query.append("journalFeed.uuid IS NULL");
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append("(journalFeed.uuid IS NULL OR ");
				}

				query.append("journalFeed.uuid = ?");

				if (uuid.equals(StringPool.BLANK)) {
					query.append(")");
				}
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalFeed.");
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

				query.append("journalFeed.feedId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalFeed);

			JournalFeed[] array = new JournalFeedImpl[3];

			array[0] = (JournalFeed)objArray[0];
			array[1] = (JournalFeed)objArray[1];
			array[2] = (JournalFeed)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalFeed findByUUID_G(String uuid, long groupId)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = fetchByUUID_G(uuid, groupId);

		if (journalFeed == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalFeed exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFeedException(msg.toString());
		}

		return journalFeed;
	}

	public JournalFeed fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public JournalFeed fetchByUUID_G(String uuid, long groupId,
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
					"SELECT journalFeed FROM JournalFeed journalFeed WHERE ");

				if (uuid == null) {
					query.append("journalFeed.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(journalFeed.uuid IS NULL OR ");
					}

					query.append("journalFeed.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("journalFeed.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("journalFeed.feedId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<JournalFeed> list = q.list();

				result = list;

				JournalFeed journalFeed = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					journalFeed = list.get(0);

					cacheResult(journalFeed);

					if ((journalFeed.getUuid() == null) ||
							!journalFeed.getUuid().equals(uuid) ||
							(journalFeed.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, journalFeed);
					}
				}

				return journalFeed;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<JournalFeed>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (JournalFeed)result;
			}
		}
	}

	public List<JournalFeed> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<JournalFeed> list = (List<JournalFeed>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalFeed FROM JournalFeed journalFeed WHERE ");

				query.append("journalFeed.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("journalFeed.feedId ASC");

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
					list = new ArrayList<JournalFeed>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalFeed> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<JournalFeed> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalFeed> list = (List<JournalFeed>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalFeed FROM JournalFeed journalFeed WHERE ");

				query.append("journalFeed.groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalFeed.");
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

					query.append("journalFeed.feedId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<JournalFeed>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalFeed>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalFeed findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchFeedException, SystemException {
		List<JournalFeed> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalFeed exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFeedException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalFeed findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchFeedException, SystemException {
		int count = countByGroupId(groupId);

		List<JournalFeed> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalFeed exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFeedException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalFeed[] findByGroupId_PrevAndNext(long id, long groupId,
		OrderByComparator obc) throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = findByPrimaryKey(id);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT journalFeed FROM JournalFeed journalFeed WHERE ");

			query.append("journalFeed.groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalFeed.");
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

				query.append("journalFeed.feedId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalFeed);

			JournalFeed[] array = new JournalFeedImpl[3];

			array[0] = (JournalFeed)objArray[0];
			array[1] = (JournalFeed)objArray[1];
			array[2] = (JournalFeed)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalFeed findByG_F(long groupId, String feedId)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = fetchByG_F(groupId, feedId);

		if (journalFeed == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalFeed exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("feedId=" + feedId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFeedException(msg.toString());
		}

		return journalFeed;
	}

	public JournalFeed fetchByG_F(long groupId, String feedId)
		throws SystemException {
		return fetchByG_F(groupId, feedId, true);
	}

	public JournalFeed fetchByG_F(long groupId, String feedId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), feedId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_F,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalFeed FROM JournalFeed journalFeed WHERE ");

				query.append("journalFeed.groupId = ?");

				query.append(" AND ");

				if (feedId == null) {
					query.append("journalFeed.feedId IS NULL");
				}
				else {
					if (feedId.equals(StringPool.BLANK)) {
						query.append("(journalFeed.feedId IS NULL OR ");
					}

					query.append("journalFeed.feedId = ?");

					if (feedId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("journalFeed.feedId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (feedId != null) {
					qPos.add(feedId);
				}

				List<JournalFeed> list = q.list();

				result = list;

				JournalFeed journalFeed = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
						finderArgs, list);
				}
				else {
					journalFeed = list.get(0);

					cacheResult(journalFeed);

					if ((journalFeed.getGroupId() != groupId) ||
							(journalFeed.getFeedId() == null) ||
							!journalFeed.getFeedId().equals(feedId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
							finderArgs, journalFeed);
					}
				}

				return journalFeed;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
						finderArgs, new ArrayList<JournalFeed>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (JournalFeed)result;
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

	public List<JournalFeed> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalFeed> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<JournalFeed> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalFeed> list = (List<JournalFeed>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT journalFeed FROM JournalFeed journalFeed ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalFeed.");
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

					query.append("journalFeed.feedId ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<JournalFeed>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<JournalFeed>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalFeed>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (JournalFeed journalFeed : findByUuid(uuid)) {
			remove(journalFeed);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = findByUUID_G(uuid, groupId);

		remove(journalFeed);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (JournalFeed journalFeed : findByGroupId(groupId)) {
			remove(journalFeed);
		}
	}

	public void removeByG_F(long groupId, String feedId)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = findByG_F(groupId, feedId);

		remove(journalFeed);
	}

	public void removeAll() throws SystemException {
		for (JournalFeed journalFeed : findAll()) {
			remove(journalFeed);
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

				query.append("SELECT COUNT(journalFeed) ");
				query.append("FROM JournalFeed journalFeed WHERE ");

				if (uuid == null) {
					query.append("journalFeed.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(journalFeed.uuid IS NULL OR ");
					}

					query.append("journalFeed.uuid = ?");

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

				query.append("SELECT COUNT(journalFeed) ");
				query.append("FROM JournalFeed journalFeed WHERE ");

				if (uuid == null) {
					query.append("journalFeed.uuid IS NULL");
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append("(journalFeed.uuid IS NULL OR ");
					}

					query.append("journalFeed.uuid = ?");

					if (uuid.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" AND ");

				query.append("journalFeed.groupId = ?");

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalFeed) ");
				query.append("FROM JournalFeed journalFeed WHERE ");

				query.append("journalFeed.groupId = ?");

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

	public int countByG_F(long groupId, String feedId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), feedId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalFeed) ");
				query.append("FROM JournalFeed journalFeed WHERE ");

				query.append("journalFeed.groupId = ?");

				query.append(" AND ");

				if (feedId == null) {
					query.append("journalFeed.feedId IS NULL");
				}
				else {
					if (feedId.equals(StringPool.BLANK)) {
						query.append("(journalFeed.feedId IS NULL OR ");
					}

					query.append("journalFeed.feedId = ?");

					if (feedId.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (feedId != null) {
					qPos.add(feedId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F, finderArgs,
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
						"SELECT COUNT(journalFeed) FROM JournalFeed journalFeed");

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
						"value.object.listener.com.liferay.portlet.journal.model.JournalFeed")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalFeed>> listenersList = new ArrayList<ModelListener<JournalFeed>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalFeed>)Class.forName(
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
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	private static Log _log = LogFactoryUtil.getLog(JournalFeedPersistenceImpl.class);
}