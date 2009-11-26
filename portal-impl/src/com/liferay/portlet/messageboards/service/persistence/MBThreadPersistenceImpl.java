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
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.messageboards.NoSuchThreadException;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBThreadImpl;
import com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <a href="MBThreadPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBThreadPersistence
 * @see       MBThreadUtil
 * @generated
 */
public class MBThreadPersistenceImpl extends BasePersistenceImpl<MBThread>
	implements MBThreadPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = MBThreadImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_C = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_C = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_S = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_S = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_C_L = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_C_L = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_L = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_G_C_S = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_C_S = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_S = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(MBThread mbThread) {
		EntityCacheUtil.putResult(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadImpl.class, mbThread.getPrimaryKey(), mbThread);
	}

	public void cacheResult(List<MBThread> mbThreads) {
		for (MBThread mbThread : mbThreads) {
			if (EntityCacheUtil.getResult(
						MBThreadModelImpl.ENTITY_CACHE_ENABLED,
						MBThreadImpl.class, mbThread.getPrimaryKey(), this) == null) {
				cacheResult(mbThread);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(MBThreadImpl.class.getName());
		EntityCacheUtil.clearCache(MBThreadImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public MBThread create(long threadId) {
		MBThread mbThread = new MBThreadImpl();

		mbThread.setNew(true);
		mbThread.setPrimaryKey(threadId);

		return mbThread;
	}

	public MBThread remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public MBThread remove(long threadId)
		throws NoSuchThreadException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBThread mbThread = (MBThread)session.get(MBThreadImpl.class,
					new Long(threadId));

			if (mbThread == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No MBThread exists with the primary key " +
						threadId);
				}

				throw new NoSuchThreadException(
					"No MBThread exists with the primary key " + threadId);
			}

			return remove(mbThread);
		}
		catch (NoSuchThreadException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBThread remove(MBThread mbThread) throws SystemException {
		for (ModelListener<MBThread> listener : listeners) {
			listener.onBeforeRemove(mbThread);
		}

		mbThread = removeImpl(mbThread);

		for (ModelListener<MBThread> listener : listeners) {
			listener.onAfterRemove(mbThread);
		}

		return mbThread;
	}

	protected MBThread removeImpl(MBThread mbThread) throws SystemException {
		mbThread = toUnwrappedModel(mbThread);

		Session session = null;

		try {
			session = openSession();

			if (mbThread.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(MBThreadImpl.class,
						mbThread.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(mbThread);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadImpl.class, mbThread.getPrimaryKey());

		return mbThread;
	}

	public MBThread updateImpl(
		com.liferay.portlet.messageboards.model.MBThread mbThread, boolean merge)
		throws SystemException {
		mbThread = toUnwrappedModel(mbThread);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, mbThread, merge);

			mbThread.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadImpl.class, mbThread.getPrimaryKey(), mbThread);

		return mbThread;
	}

	protected MBThread toUnwrappedModel(MBThread mbThread) {
		if (mbThread instanceof MBThreadImpl) {
			return mbThread;
		}

		MBThreadImpl mbThreadImpl = new MBThreadImpl();

		mbThreadImpl.setNew(mbThread.isNew());
		mbThreadImpl.setPrimaryKey(mbThread.getPrimaryKey());

		mbThreadImpl.setThreadId(mbThread.getThreadId());
		mbThreadImpl.setGroupId(mbThread.getGroupId());
		mbThreadImpl.setCategoryId(mbThread.getCategoryId());
		mbThreadImpl.setRootMessageId(mbThread.getRootMessageId());
		mbThreadImpl.setMessageCount(mbThread.getMessageCount());
		mbThreadImpl.setViewCount(mbThread.getViewCount());
		mbThreadImpl.setLastPostByUserId(mbThread.getLastPostByUserId());
		mbThreadImpl.setLastPostDate(mbThread.getLastPostDate());
		mbThreadImpl.setPriority(mbThread.getPriority());
		mbThreadImpl.setStatus(mbThread.getStatus());
		mbThreadImpl.setStatusByUserId(mbThread.getStatusByUserId());
		mbThreadImpl.setStatusByUserName(mbThread.getStatusByUserName());
		mbThreadImpl.setStatusDate(mbThread.getStatusDate());

		return mbThreadImpl;
	}

	public MBThread findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public MBThread findByPrimaryKey(long threadId)
		throws NoSuchThreadException, SystemException {
		MBThread mbThread = fetchByPrimaryKey(threadId);

		if (mbThread == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No MBThread exists with the primary key " +
					threadId);
			}

			throw new NoSuchThreadException(
				"No MBThread exists with the primary key " + threadId);
		}

		return mbThread;
	}

	public MBThread fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public MBThread fetchByPrimaryKey(long threadId) throws SystemException {
		MBThread mbThread = (MBThread)EntityCacheUtil.getResult(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
				MBThreadImpl.class, threadId, this);

		if (mbThread == null) {
			Session session = null;

			try {
				session = openSession();

				mbThread = (MBThread)session.get(MBThreadImpl.class,
						new Long(threadId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (mbThread != null) {
					cacheResult(mbThread);
				}

				closeSession(session);
			}
		}

		return mbThread;
	}

	public List<MBThread> findByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<MBThread> list = (List<MBThread>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbThread FROM MBThread mbThread WHERE ");

				query.append("mbThread.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbThread.priority DESC, ");
				query.append("mbThread.lastPostDate DESC");

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
					list = new ArrayList<MBThread>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBThread> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<MBThread> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBThread> list = (List<MBThread>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbThread FROM MBThread mbThread WHERE ");

				query.append("mbThread.groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbThread.");
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

					query.append("mbThread.priority DESC, ");
					query.append("mbThread.lastPostDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<MBThread>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBThread>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBThread findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchThreadException, SystemException {
		List<MBThread> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBThread exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchThreadException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBThread findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchThreadException, SystemException {
		int count = countByGroupId(groupId);

		List<MBThread> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBThread exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchThreadException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBThread[] findByGroupId_PrevAndNext(long threadId, long groupId,
		OrderByComparator obc) throws NoSuchThreadException, SystemException {
		MBThread mbThread = findByPrimaryKey(threadId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT mbThread FROM MBThread mbThread WHERE ");

			query.append("mbThread.groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbThread.");
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

				query.append("mbThread.priority DESC, ");
				query.append("mbThread.lastPostDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, mbThread);

			MBThread[] array = new MBThreadImpl[3];

			array[0] = (MBThread)objArray[0];
			array[1] = (MBThread)objArray[1];
			array[2] = (MBThread)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBThread> findByG_C(long groupId, long categoryId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(categoryId)
			};

		List<MBThread> list = (List<MBThread>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbThread FROM MBThread mbThread WHERE ");

				query.append("mbThread.groupId = ?");

				query.append(" AND ");

				query.append("mbThread.categoryId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbThread.priority DESC, ");
				query.append("mbThread.lastPostDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBThread>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_C, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBThread> findByG_C(long groupId, long categoryId, int start,
		int end) throws SystemException {
		return findByG_C(groupId, categoryId, start, end, null);
	}

	public List<MBThread> findByG_C(long groupId, long categoryId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(categoryId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBThread> list = (List<MBThread>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbThread FROM MBThread mbThread WHERE ");

				query.append("mbThread.groupId = ?");

				query.append(" AND ");

				query.append("mbThread.categoryId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbThread.");
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

					query.append("mbThread.priority DESC, ");
					query.append("mbThread.lastPostDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				list = (List<MBThread>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBThread>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBThread findByG_C_First(long groupId, long categoryId,
		OrderByComparator obc) throws NoSuchThreadException, SystemException {
		List<MBThread> list = findByG_C(groupId, categoryId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBThread exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("categoryId=" + categoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchThreadException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBThread findByG_C_Last(long groupId, long categoryId,
		OrderByComparator obc) throws NoSuchThreadException, SystemException {
		int count = countByG_C(groupId, categoryId);

		List<MBThread> list = findByG_C(groupId, categoryId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBThread exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("categoryId=" + categoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchThreadException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBThread[] findByG_C_PrevAndNext(long threadId, long groupId,
		long categoryId, OrderByComparator obc)
		throws NoSuchThreadException, SystemException {
		MBThread mbThread = findByPrimaryKey(threadId);

		int count = countByG_C(groupId, categoryId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT mbThread FROM MBThread mbThread WHERE ");

			query.append("mbThread.groupId = ?");

			query.append(" AND ");

			query.append("mbThread.categoryId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbThread.");
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

				query.append("mbThread.priority DESC, ");
				query.append("mbThread.lastPostDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, mbThread);

			MBThread[] array = new MBThreadImpl[3];

			array[0] = (MBThread)objArray[0];
			array[1] = (MBThread)objArray[1];
			array[2] = (MBThread)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBThread> findByG_S(long groupId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(status)
			};

		List<MBThread> list = (List<MBThread>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbThread FROM MBThread mbThread WHERE ");

				query.append("mbThread.groupId = ?");

				query.append(" AND ");

				query.append("mbThread.status = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbThread.priority DESC, ");
				query.append("mbThread.lastPostDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBThread>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_S, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBThread> findByG_S(long groupId, int status, int start, int end)
		throws SystemException {
		return findByG_S(groupId, status, start, end, null);
	}

	public List<MBThread> findByG_S(long groupId, int status, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(status),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBThread> list = (List<MBThread>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbThread FROM MBThread mbThread WHERE ");

				query.append("mbThread.groupId = ?");

				query.append(" AND ");

				query.append("mbThread.status = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbThread.");
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

					query.append("mbThread.priority DESC, ");
					query.append("mbThread.lastPostDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				list = (List<MBThread>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBThread>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBThread findByG_S_First(long groupId, int status,
		OrderByComparator obc) throws NoSuchThreadException, SystemException {
		List<MBThread> list = findByG_S(groupId, status, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBThread exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("status=" + status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchThreadException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBThread findByG_S_Last(long groupId, int status,
		OrderByComparator obc) throws NoSuchThreadException, SystemException {
		int count = countByG_S(groupId, status);

		List<MBThread> list = findByG_S(groupId, status, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBThread exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("status=" + status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchThreadException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBThread[] findByG_S_PrevAndNext(long threadId, long groupId,
		int status, OrderByComparator obc)
		throws NoSuchThreadException, SystemException {
		MBThread mbThread = findByPrimaryKey(threadId);

		int count = countByG_S(groupId, status);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT mbThread FROM MBThread mbThread WHERE ");

			query.append("mbThread.groupId = ?");

			query.append(" AND ");

			query.append("mbThread.status = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbThread.");
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

				query.append("mbThread.priority DESC, ");
				query.append("mbThread.lastPostDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, mbThread);

			MBThread[] array = new MBThreadImpl[3];

			array[0] = (MBThread)objArray[0];
			array[1] = (MBThread)objArray[1];
			array[2] = (MBThread)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBThread> findByG_C_L(long groupId, long categoryId,
		Date lastPostDate) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(categoryId),
				
				lastPostDate
			};

		List<MBThread> list = (List<MBThread>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_C_L,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbThread FROM MBThread mbThread WHERE ");

				query.append("mbThread.groupId = ?");

				query.append(" AND ");

				query.append("mbThread.categoryId = ?");

				query.append(" AND ");

				if (lastPostDate == null) {
					query.append("mbThread.lastPostDate IS NULL");
				}
				else {
					query.append("mbThread.lastPostDate = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbThread.priority DESC, ");
				query.append("mbThread.lastPostDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				if (lastPostDate != null) {
					qPos.add(CalendarUtil.getTimestamp(lastPostDate));
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBThread>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_C_L,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBThread> findByG_C_L(long groupId, long categoryId,
		Date lastPostDate, int start, int end) throws SystemException {
		return findByG_C_L(groupId, categoryId, lastPostDate, start, end, null);
	}

	public List<MBThread> findByG_C_L(long groupId, long categoryId,
		Date lastPostDate, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(categoryId),
				
				lastPostDate,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBThread> list = (List<MBThread>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_C_L,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbThread FROM MBThread mbThread WHERE ");

				query.append("mbThread.groupId = ?");

				query.append(" AND ");

				query.append("mbThread.categoryId = ?");

				query.append(" AND ");

				if (lastPostDate == null) {
					query.append("mbThread.lastPostDate IS NULL");
				}
				else {
					query.append("mbThread.lastPostDate = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbThread.");
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

					query.append("mbThread.priority DESC, ");
					query.append("mbThread.lastPostDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				if (lastPostDate != null) {
					qPos.add(CalendarUtil.getTimestamp(lastPostDate));
				}

				list = (List<MBThread>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBThread>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_C_L,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBThread findByG_C_L_First(long groupId, long categoryId,
		Date lastPostDate, OrderByComparator obc)
		throws NoSuchThreadException, SystemException {
		List<MBThread> list = findByG_C_L(groupId, categoryId, lastPostDate, 0,
				1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBThread exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("categoryId=" + categoryId);

			msg.append(", ");
			msg.append("lastPostDate=" + lastPostDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchThreadException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBThread findByG_C_L_Last(long groupId, long categoryId,
		Date lastPostDate, OrderByComparator obc)
		throws NoSuchThreadException, SystemException {
		int count = countByG_C_L(groupId, categoryId, lastPostDate);

		List<MBThread> list = findByG_C_L(groupId, categoryId, lastPostDate,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBThread exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("categoryId=" + categoryId);

			msg.append(", ");
			msg.append("lastPostDate=" + lastPostDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchThreadException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBThread[] findByG_C_L_PrevAndNext(long threadId, long groupId,
		long categoryId, Date lastPostDate, OrderByComparator obc)
		throws NoSuchThreadException, SystemException {
		MBThread mbThread = findByPrimaryKey(threadId);

		int count = countByG_C_L(groupId, categoryId, lastPostDate);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT mbThread FROM MBThread mbThread WHERE ");

			query.append("mbThread.groupId = ?");

			query.append(" AND ");

			query.append("mbThread.categoryId = ?");

			query.append(" AND ");

			if (lastPostDate == null) {
				query.append("mbThread.lastPostDate IS NULL");
			}
			else {
				query.append("mbThread.lastPostDate = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbThread.");
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

				query.append("mbThread.priority DESC, ");
				query.append("mbThread.lastPostDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			if (lastPostDate != null) {
				qPos.add(CalendarUtil.getTimestamp(lastPostDate));
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, mbThread);

			MBThread[] array = new MBThreadImpl[3];

			array[0] = (MBThread)objArray[0];
			array[1] = (MBThread)objArray[1];
			array[2] = (MBThread)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBThread> findByG_C_S(long groupId, long categoryId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(categoryId), new Integer(status)
			};

		List<MBThread> list = (List<MBThread>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_C_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbThread FROM MBThread mbThread WHERE ");

				query.append("mbThread.groupId = ?");

				query.append(" AND ");

				query.append("mbThread.categoryId = ?");

				query.append(" AND ");

				query.append("mbThread.status = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("mbThread.priority DESC, ");
				query.append("mbThread.lastPostDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				qPos.add(status);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBThread>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_C_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<MBThread> findByG_C_S(long groupId, long categoryId,
		int status, int start, int end) throws SystemException {
		return findByG_C_S(groupId, categoryId, status, start, end, null);
	}

	public List<MBThread> findByG_C_S(long groupId, long categoryId,
		int status, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(categoryId), new Integer(status),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBThread> list = (List<MBThread>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_C_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbThread FROM MBThread mbThread WHERE ");

				query.append("mbThread.groupId = ?");

				query.append(" AND ");

				query.append("mbThread.categoryId = ?");

				query.append(" AND ");

				query.append("mbThread.status = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbThread.");
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

					query.append("mbThread.priority DESC, ");
					query.append("mbThread.lastPostDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				qPos.add(status);

				list = (List<MBThread>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBThread>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_C_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public MBThread findByG_C_S_First(long groupId, long categoryId,
		int status, OrderByComparator obc)
		throws NoSuchThreadException, SystemException {
		List<MBThread> list = findByG_C_S(groupId, categoryId, status, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBThread exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("categoryId=" + categoryId);

			msg.append(", ");
			msg.append("status=" + status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchThreadException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBThread findByG_C_S_Last(long groupId, long categoryId, int status,
		OrderByComparator obc) throws NoSuchThreadException, SystemException {
		int count = countByG_C_S(groupId, categoryId, status);

		List<MBThread> list = findByG_C_S(groupId, categoryId, status,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBThread exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("categoryId=" + categoryId);

			msg.append(", ");
			msg.append("status=" + status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchThreadException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBThread[] findByG_C_S_PrevAndNext(long threadId, long groupId,
		long categoryId, int status, OrderByComparator obc)
		throws NoSuchThreadException, SystemException {
		MBThread mbThread = findByPrimaryKey(threadId);

		int count = countByG_C_S(groupId, categoryId, status);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT mbThread FROM MBThread mbThread WHERE ");

			query.append("mbThread.groupId = ?");

			query.append(" AND ");

			query.append("mbThread.categoryId = ?");

			query.append(" AND ");

			query.append("mbThread.status = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("mbThread.");
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

				query.append("mbThread.priority DESC, ");
				query.append("mbThread.lastPostDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			qPos.add(status);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, mbThread);

			MBThread[] array = new MBThreadImpl[3];

			array[0] = (MBThread)objArray[0];
			array[1] = (MBThread)objArray[1];
			array[2] = (MBThread)objArray[2];

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

	public List<MBThread> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<MBThread> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<MBThread> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<MBThread> list = (List<MBThread>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT mbThread FROM MBThread mbThread ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("mbThread.");
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

					query.append("mbThread.priority DESC, ");
					query.append("mbThread.lastPostDate DESC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<MBThread>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (MBThread mbThread : findByGroupId(groupId)) {
			remove(mbThread);
		}
	}

	public void removeByG_C(long groupId, long categoryId)
		throws SystemException {
		for (MBThread mbThread : findByG_C(groupId, categoryId)) {
			remove(mbThread);
		}
	}

	public void removeByG_S(long groupId, int status) throws SystemException {
		for (MBThread mbThread : findByG_S(groupId, status)) {
			remove(mbThread);
		}
	}

	public void removeByG_C_L(long groupId, long categoryId, Date lastPostDate)
		throws SystemException {
		for (MBThread mbThread : findByG_C_L(groupId, categoryId, lastPostDate)) {
			remove(mbThread);
		}
	}

	public void removeByG_C_S(long groupId, long categoryId, int status)
		throws SystemException {
		for (MBThread mbThread : findByG_C_S(groupId, categoryId, status)) {
			remove(mbThread);
		}
	}

	public void removeAll() throws SystemException {
		for (MBThread mbThread : findAll()) {
			remove(mbThread);
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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(mbThread) ");
				query.append("FROM MBThread mbThread WHERE ");

				query.append("mbThread.groupId = ?");

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

	public int countByG_C(long groupId, long categoryId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(categoryId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(mbThread) ");
				query.append("FROM MBThread mbThread WHERE ");

				query.append("mbThread.groupId = ?");

				query.append(" AND ");

				query.append("mbThread.categoryId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_S(long groupId, int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(mbThread) ");
				query.append("FROM MBThread mbThread WHERE ");

				query.append("mbThread.groupId = ?");

				query.append(" AND ");

				query.append("mbThread.status = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_C_L(long groupId, long categoryId, Date lastPostDate)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(categoryId),
				
				lastPostDate
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_L,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(mbThread) ");
				query.append("FROM MBThread mbThread WHERE ");

				query.append("mbThread.groupId = ?");

				query.append(" AND ");

				query.append("mbThread.categoryId = ?");

				query.append(" AND ");

				if (lastPostDate == null) {
					query.append("mbThread.lastPostDate IS NULL");
				}
				else {
					query.append("mbThread.lastPostDate = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				if (lastPostDate != null) {
					qPos.add(CalendarUtil.getTimestamp(lastPostDate));
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_L,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_C_S(long groupId, long categoryId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(categoryId), new Integer(status)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(mbThread) ");
				query.append("FROM MBThread mbThread WHERE ");

				query.append("mbThread.groupId = ?");

				query.append(" AND ");

				query.append("mbThread.categoryId = ?");

				query.append(" AND ");

				query.append("mbThread.status = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_S,
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
						"SELECT COUNT(mbThread) FROM MBThread mbThread");

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
						"value.object.listener.com.liferay.portlet.messageboards.model.MBThread")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MBThread>> listenersList = new ArrayList<ModelListener<MBThread>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MBThread>)Class.forName(
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
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetEntryPersistence assetEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence")
	protected com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence")
	protected com.liferay.portlet.social.service.persistence.SocialActivityPersistence socialActivityPersistence;
	private static Log _log = LogFactoryUtil.getLog(MBThreadPersistenceImpl.class);
}