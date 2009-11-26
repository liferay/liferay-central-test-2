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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.messageboards.NoSuchBanException;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.impl.MBBanImpl;
import com.liferay.portlet.messageboards.model.impl.MBBanModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="MBBanPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBBanPersistence
 * @see       MBBanUtil
 * @generated
 */
public class MBBanPersistenceImpl extends BasePersistenceImpl<MBBan>
	implements MBBanPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = MBBanImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_BANUSERID = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByBanUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_BANUSERID = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByBanUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_BANUSERID = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByBanUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_B = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_B",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_B = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_B",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(MBBan mbBan) {
		EntityCacheUtil.putResult(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanImpl.class, mbBan.getPrimaryKey(), mbBan);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_B,
			new Object[] {
				new Long(mbBan.getGroupId()), new Long(mbBan.getBanUserId())
			}, mbBan);
	}

	public void cacheResult(List<MBBan> mbBans) {
		for (MBBan mbBan : mbBans) {
			if (EntityCacheUtil.getResult(MBBanModelImpl.ENTITY_CACHE_ENABLED,
						MBBanImpl.class, mbBan.getPrimaryKey(), this) == null) {
				cacheResult(mbBan);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(MBBanImpl.class.getName());
		EntityCacheUtil.clearCache(MBBanImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public MBBan create(long banId) {
		MBBan mbBan = new MBBanImpl();

		mbBan.setNew(true);
		mbBan.setPrimaryKey(banId);

		return mbBan;
	}

	public MBBan remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public MBBan remove(long banId) throws NoSuchBanException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBBan mbBan = (MBBan)session.get(MBBanImpl.class, new Long(banId));

			if (mbBan == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No MBBan exists with the primary key " + banId);
				}

				throw new NoSuchBanException(
					"No MBBan exists with the primary key " + banId);
			}

			return remove(mbBan);
		}
		catch (NoSuchBanException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBBan remove(MBBan mbBan) throws SystemException {
		for (ModelListener<MBBan> listener : listeners) {
			listener.onBeforeRemove(mbBan);
		}

		mbBan = removeImpl(mbBan);

		for (ModelListener<MBBan> listener : listeners) {
			listener.onAfterRemove(mbBan);
		}

		return mbBan;
	}

	protected MBBan removeImpl(MBBan mbBan) throws SystemException {
		mbBan = toUnwrappedModel(mbBan);

		Session session = null;

		try {
			session = openSession();

			if (mbBan.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(MBBanImpl.class,
						mbBan.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(mbBan);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		MBBanModelImpl mbBanModelImpl = (MBBanModelImpl)mbBan;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_B,
			new Object[] {
				new Long(mbBanModelImpl.getOriginalGroupId()),
				new Long(mbBanModelImpl.getOriginalBanUserId())
			});

		EntityCacheUtil.removeResult(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanImpl.class, mbBan.getPrimaryKey());

		return mbBan;
	}

	public MBBan updateImpl(
		com.liferay.portlet.messageboards.model.MBBan mbBan, boolean merge)
		throws SystemException {
		mbBan = toUnwrappedModel(mbBan);

		boolean isNew = mbBan.isNew();

		MBBanModelImpl mbBanModelImpl = (MBBanModelImpl)mbBan;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, mbBan, merge);

			mbBan.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanImpl.class, mbBan.getPrimaryKey(), mbBan);

		if (!isNew &&
				((mbBan.getGroupId() != mbBanModelImpl.getOriginalGroupId()) ||
				(mbBan.getBanUserId() != mbBanModelImpl.getOriginalBanUserId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_B,
				new Object[] {
					new Long(mbBanModelImpl.getOriginalGroupId()),
					new Long(mbBanModelImpl.getOriginalBanUserId())
				});
		}

		if (isNew ||
				((mbBan.getGroupId() != mbBanModelImpl.getOriginalGroupId()) ||
				(mbBan.getBanUserId() != mbBanModelImpl.getOriginalBanUserId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_B,
				new Object[] {
					new Long(mbBan.getGroupId()), new Long(mbBan.getBanUserId())
				}, mbBan);
		}

		return mbBan;
	}

	protected MBBan toUnwrappedModel(MBBan mbBan) {
		if (mbBan instanceof MBBanImpl) {
			return mbBan;
		}

		MBBanImpl mbBanImpl = new MBBanImpl();

		mbBanImpl.setNew(mbBan.isNew());
		mbBanImpl.setPrimaryKey(mbBan.getPrimaryKey());

		mbBanImpl.setBanId(mbBan.getBanId());
		mbBanImpl.setGroupId(mbBan.getGroupId());
		mbBanImpl.setCompanyId(mbBan.getCompanyId());
		mbBanImpl.setUserId(mbBan.getUserId());
		mbBanImpl.setUserName(mbBan.getUserName());
		mbBanImpl.setCreateDate(mbBan.getCreateDate());
		mbBanImpl.setModifiedDate(mbBan.getModifiedDate());
		mbBanImpl.setBanUserId(mbBan.getBanUserId());

		return mbBanImpl;
	}

	public MBBan findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public MBBan findByPrimaryKey(long banId)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = fetchByPrimaryKey(banId);

		if (mbBan == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No MBBan exists with the primary key " + banId);
			}

			throw new NoSuchBanException(
				"No MBBan exists with the primary key " + banId);
		}

		return mbBan;
	}

	public MBBan fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public MBBan fetchByPrimaryKey(long banId) throws SystemException {
		MBBan mbBan = (MBBan)EntityCacheUtil.getResult(MBBanModelImpl.ENTITY_CACHE_ENABLED,
				MBBanImpl.class, banId, this);

		if (mbBan == null) {
			Session session = null;

			try {
				session = openSession();

				mbBan = (MBBan)session.get(MBBanImpl.class, new Long(banId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (mbBan != null) {
					cacheResult(mbBan);
				}

				closeSession(session);
			}
		}

		return mbBan;
	}

	public List<MBBan> findByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<MBBan> list = (List<MBBan>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_MBBAN_WHERE);

				query.append("mbBan.groupId = ?");

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
					list = new ArrayList<MBBan>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBBan> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<MBBan> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBBan> list = (List<MBBan>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_MBBAN_WHERE);

				query.append("mbBan.groupId = ?");

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbBan.");
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

				list = (List<MBBan>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBBan>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBBan findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchBanException, SystemException {
		List<MBBan> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No MBBan exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchBanException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBBan findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchBanException, SystemException {
		int count = countByGroupId(groupId);

		List<MBBan> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No MBBan exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchBanException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBBan[] findByGroupId_PrevAndNext(long banId, long groupId,
		OrderByComparator obc) throws NoSuchBanException, SystemException {
		MBBan mbBan = findByPrimaryKey(banId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_MBBAN_WHERE);

			query.append("mbBan.groupId = ?");

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbBan.");
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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, mbBan);

			MBBan[] array = new MBBanImpl[3];

			array[0] = (MBBan)objArray[0];
			array[1] = (MBBan)objArray[1];
			array[2] = (MBBan)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBBan> findByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		List<MBBan> list = (List<MBBan>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_MBBAN_WHERE);

				query.append("mbBan.userId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBBan>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBBan> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<MBBan> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBBan> list = (List<MBBan>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_MBBAN_WHERE);

				query.append("mbBan.userId = ?");

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbBan.");
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

				qPos.add(userId);

				list = (List<MBBan>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBBan>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBBan findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchBanException, SystemException {
		List<MBBan> list = findByUserId(userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No MBBan exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchBanException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBBan findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchBanException, SystemException {
		int count = countByUserId(userId);

		List<MBBan> list = findByUserId(userId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No MBBan exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchBanException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBBan[] findByUserId_PrevAndNext(long banId, long userId,
		OrderByComparator obc) throws NoSuchBanException, SystemException {
		MBBan mbBan = findByPrimaryKey(banId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_MBBAN_WHERE);

			query.append("mbBan.userId = ?");

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbBan.");
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

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, mbBan);

			MBBan[] array = new MBBanImpl[3];

			array[0] = (MBBan)objArray[0];
			array[1] = (MBBan)objArray[1];
			array[2] = (MBBan)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBBan> findByBanUserId(long banUserId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(banUserId) };

		List<MBBan> list = (List<MBBan>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_BANUSERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_MBBAN_WHERE);

				query.append("mbBan.banUserId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(banUserId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBBan>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_BANUSERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBBan> findByBanUserId(long banUserId, int start, int end)
		throws SystemException {
		return findByBanUserId(banUserId, start, end, null);
	}

	public List<MBBan> findByBanUserId(long banUserId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(banUserId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBBan> list = (List<MBBan>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_BANUSERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_MBBAN_WHERE);

				query.append("mbBan.banUserId = ?");

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbBan.");
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

				qPos.add(banUserId);

				list = (List<MBBan>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBBan>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_BANUSERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBBan findByBanUserId_First(long banUserId, OrderByComparator obc)
		throws NoSuchBanException, SystemException {
		List<MBBan> list = findByBanUserId(banUserId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No MBBan exists with the key {");

			msg.append("banUserId=" + banUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchBanException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBBan findByBanUserId_Last(long banUserId, OrderByComparator obc)
		throws NoSuchBanException, SystemException {
		int count = countByBanUserId(banUserId);

		List<MBBan> list = findByBanUserId(banUserId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No MBBan exists with the key {");

			msg.append("banUserId=" + banUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchBanException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBBan[] findByBanUserId_PrevAndNext(long banId, long banUserId,
		OrderByComparator obc) throws NoSuchBanException, SystemException {
		MBBan mbBan = findByPrimaryKey(banId);

		int count = countByBanUserId(banUserId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_MBBAN_WHERE);

			query.append("mbBan.banUserId = ?");

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbBan.");
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

			qPos.add(banUserId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, mbBan);

			MBBan[] array = new MBBanImpl[3];

			array[0] = (MBBan)objArray[0];
			array[1] = (MBBan)objArray[1];
			array[2] = (MBBan)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBBan findByG_B(long groupId, long banUserId)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = fetchByG_B(groupId, banUserId);

		if (mbBan == null) {
			StringBundler msg = new StringBundler();

			msg.append("No MBBan exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("banUserId=" + banUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchBanException(msg.toString());
		}

		return mbBan;
	}

	public MBBan fetchByG_B(long groupId, long banUserId)
		throws SystemException {
		return fetchByG_B(groupId, banUserId, true);
	}

	public MBBan fetchByG_B(long groupId, long banUserId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(banUserId)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_B,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_MBBAN_WHERE);

				query.append("mbBan.groupId = ?");

				query.append(" AND ");

				query.append("mbBan.banUserId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(banUserId);

				List<MBBan> list = q.list();

				result = list;

				MBBan mbBan = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_B,
						finderArgs, list);
				}
				else {
					mbBan = list.get(0);

					cacheResult(mbBan);

					if ((mbBan.getGroupId() != groupId) ||
							(mbBan.getBanUserId() != banUserId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_B,
							finderArgs, mbBan);
					}
				}

				return mbBan;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_B,
						finderArgs, new ArrayList<MBBan>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (MBBan)result;
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

	public List<MBBan> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<MBBan> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<MBBan> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBBan> list = (List<MBBan>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_MBBAN);

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbBan.");
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
					list = (List<MBBan>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MBBan>)QueryUtil.list(q, getDialect(), start,
							end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBBan>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (MBBan mbBan : findByGroupId(groupId)) {
			remove(mbBan);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (MBBan mbBan : findByUserId(userId)) {
			remove(mbBan);
		}
	}

	public void removeByBanUserId(long banUserId) throws SystemException {
		for (MBBan mbBan : findByBanUserId(banUserId)) {
			remove(mbBan);
		}
	}

	public void removeByG_B(long groupId, long banUserId)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = findByG_B(groupId, banUserId);

		remove(mbBan);
	}

	public void removeAll() throws SystemException {
		for (MBBan mbBan : findAll()) {
			remove(mbBan);
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

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_MBBAN_WHERE);

				query.append("mbBan.groupId = ?");

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

	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_MBBAN_WHERE);

				query.append("mbBan.userId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByBanUserId(long banUserId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(banUserId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_BANUSERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_MBBAN_WHERE);

				query.append("mbBan.banUserId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(banUserId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_BANUSERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_B(long groupId, long banUserId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(banUserId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_B,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_MBBAN_WHERE);

				query.append("mbBan.groupId = ?");

				query.append(" AND ");

				query.append("mbBan.banUserId = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(banUserId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_B, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_MBBAN);

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
						"value.object.listener.com.liferay.portlet.messageboards.model.MBBan")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MBBan>> listenersList = new ArrayList<ModelListener<MBBan>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MBBan>)Class.forName(
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
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static final String _SQL_SELECT_MBBAN = "SELECT mbBan FROM MBBan mbBan";
	private static final String _SQL_SELECT_MBBAN_WHERE = "SELECT mbBan FROM MBBan mbBan WHERE ";
	private static final String _SQL_COUNT_MBBAN = "SELECT COUNT(mbBan) FROM MBBan mbBan";
	private static final String _SQL_COUNT_MBBAN_WHERE = "SELECT COUNT(mbBan) FROM MBBan mbBan WHERE ";
	private static Log _log = LogFactoryUtil.getLog(MBBanPersistenceImpl.class);
}