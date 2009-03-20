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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchFileRankException;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.impl.DLFileRankImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileRankModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="DLFileRankPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DLFileRankPersistenceImpl extends BasePersistenceImpl
	implements DLFileRankPersistence {
	public DLFileRank create(long fileRankId) {
		DLFileRank dlFileRank = new DLFileRankImpl();

		dlFileRank.setNew(true);
		dlFileRank.setPrimaryKey(fileRankId);

		return dlFileRank;
	}

	public DLFileRank remove(long fileRankId)
		throws NoSuchFileRankException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileRank dlFileRank = (DLFileRank)session.get(DLFileRankImpl.class,
					new Long(fileRankId));

			if (dlFileRank == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No DLFileRank exists with the primary key " +
						fileRankId);
				}

				throw new NoSuchFileRankException(
					"No DLFileRank exists with the primary key " + fileRankId);
			}

			return remove(dlFileRank);
		}
		catch (NoSuchFileRankException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileRank remove(DLFileRank dlFileRank) throws SystemException {
		for (ModelListener<DLFileRank> listener : listeners) {
			listener.onBeforeRemove(dlFileRank);
		}

		dlFileRank = removeImpl(dlFileRank);

		for (ModelListener<DLFileRank> listener : listeners) {
			listener.onAfterRemove(dlFileRank);
		}

		return dlFileRank;
	}

	protected DLFileRank removeImpl(DLFileRank dlFileRank)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(DLFileRankImpl.class,
						dlFileRank.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(dlFileRank);

			session.flush();

			return dlFileRank;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(DLFileRank.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(DLFileRank dlFileRank, boolean merge)</code>.
	 */
	public DLFileRank update(DLFileRank dlFileRank) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(DLFileRank dlFileRank) method. Use update(DLFileRank dlFileRank, boolean merge) instead.");
		}

		return update(dlFileRank, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        dlFileRank the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when dlFileRank is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public DLFileRank update(DLFileRank dlFileRank, boolean merge)
		throws SystemException {
		boolean isNew = dlFileRank.isNew();

		for (ModelListener<DLFileRank> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(dlFileRank);
			}
			else {
				listener.onBeforeUpdate(dlFileRank);
			}
		}

		dlFileRank = updateImpl(dlFileRank, merge);

		for (ModelListener<DLFileRank> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(dlFileRank);
			}
			else {
				listener.onAfterUpdate(dlFileRank);
			}
		}

		return dlFileRank;
	}

	public DLFileRank updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, dlFileRank, merge);

			dlFileRank.setNew(false);

			return dlFileRank;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(DLFileRank.class.getName());
		}
	}

	public DLFileRank findByPrimaryKey(long fileRankId)
		throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = fetchByPrimaryKey(fileRankId);

		if (dlFileRank == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No DLFileRank exists with the primary key " +
					fileRankId);
			}

			throw new NoSuchFileRankException(
				"No DLFileRank exists with the primary key " + fileRankId);
		}

		return dlFileRank;
	}

	public DLFileRank fetchByPrimaryKey(long fileRankId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (DLFileRank)session.get(DLFileRankImpl.class,
				new Long(fileRankId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFileRank> findByUserId(long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = DLFileRankModelImpl.CACHE_ENABLED;
		String finderClassName = DLFileRank.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<DLFileRank> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<DLFileRank>)result;
		}
	}

	public List<DLFileRank> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<DLFileRank> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = DLFileRankModelImpl.CACHE_ENABLED;
		String finderClassName = DLFileRank.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<DLFileRank> list = (List<DLFileRank>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<DLFileRank>)result;
		}
	}

	public DLFileRank findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchFileRankException, SystemException {
		List<DLFileRank> list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileRank exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileRank findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchFileRankException, SystemException {
		int count = countByUserId(userId);

		List<DLFileRank> list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileRank exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileRank[] findByUserId_PrevAndNext(long fileRankId, long userId,
		OrderByComparator obc) throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = findByPrimaryKey(fileRankId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileRank);

			DLFileRank[] array = new DLFileRankImpl[3];

			array[0] = (DLFileRank)objArray[0];
			array[1] = (DLFileRank)objArray[1];
			array[2] = (DLFileRank)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFileRank> findByF_N(long folderId, String name)
		throws SystemException {
		boolean finderClassNameCacheEnabled = DLFileRankModelImpl.CACHE_ENABLED;
		String finderClassName = DLFileRank.class.getName();
		String finderMethodName = "findByF_N";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(folderId), name };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");

				query.append("folderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				List<DLFileRank> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<DLFileRank>)result;
		}
	}

	public List<DLFileRank> findByF_N(long folderId, String name, int start,
		int end) throws SystemException {
		return findByF_N(folderId, name, start, end, null);
	}

	public List<DLFileRank> findByF_N(long folderId, String name, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = DLFileRankModelImpl.CACHE_ENABLED;
		String finderClassName = DLFileRank.class.getName();
		String finderMethodName = "findByF_N";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(folderId),
				
				name,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");

				query.append("folderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				List<DLFileRank> list = (List<DLFileRank>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<DLFileRank>)result;
		}
	}

	public DLFileRank findByF_N_First(long folderId, String name,
		OrderByComparator obc) throws NoSuchFileRankException, SystemException {
		List<DLFileRank> list = findByF_N(folderId, name, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileRank exists with the key {");

			msg.append("folderId=" + folderId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileRank findByF_N_Last(long folderId, String name,
		OrderByComparator obc) throws NoSuchFileRankException, SystemException {
		int count = countByF_N(folderId, name);

		List<DLFileRank> list = findByF_N(folderId, name, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileRank exists with the key {");

			msg.append("folderId=" + folderId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileRank[] findByF_N_PrevAndNext(long fileRankId, long folderId,
		String name, OrderByComparator obc)
		throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = findByPrimaryKey(fileRankId);

		int count = countByF_N(folderId, name);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");

			query.append("folderId = ?");

			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(folderId);

			if (name != null) {
				qPos.add(name);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileRank);

			DLFileRank[] array = new DLFileRankImpl[3];

			array[0] = (DLFileRank)objArray[0];
			array[1] = (DLFileRank)objArray[1];
			array[2] = (DLFileRank)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileRank findByC_U_F_N(long companyId, long userId, long folderId,
		String name) throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = fetchByC_U_F_N(companyId, userId, folderId, name);

		if (dlFileRank == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No DLFileRank exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("folderId=" + folderId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileRankException(msg.toString());
		}

		return dlFileRank;
	}

	public DLFileRank fetchByC_U_F_N(long companyId, long userId,
		long folderId, String name) throws SystemException {
		boolean finderClassNameCacheEnabled = DLFileRankModelImpl.CACHE_ENABLED;
		String finderClassName = DLFileRank.class.getName();
		String finderMethodName = "fetchByC_U_F_N";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(userId), new Long(folderId),
				
				name
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("folderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				List<DLFileRank> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<DLFileRank> list = (List<DLFileRank>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
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

	public List<DLFileRank> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<DLFileRank> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<DLFileRank> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = DLFileRankModelImpl.CACHE_ENABLED;
		String finderClassName = DLFileRank.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileRank ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				List<DLFileRank> list = null;

				if (obc == null) {
					list = (List<DLFileRank>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLFileRank>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<DLFileRank>)result;
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (DLFileRank dlFileRank : findByUserId(userId)) {
			remove(dlFileRank);
		}
	}

	public void removeByF_N(long folderId, String name)
		throws SystemException {
		for (DLFileRank dlFileRank : findByF_N(folderId, name)) {
			remove(dlFileRank);
		}
	}

	public void removeByC_U_F_N(long companyId, long userId, long folderId,
		String name) throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = findByC_U_F_N(companyId, userId, folderId, name);

		remove(dlFileRank);
	}

	public void removeAll() throws SystemException {
		for (DLFileRank dlFileRank : findAll()) {
			remove(dlFileRank);
		}
	}

	public int countByUserId(long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = DLFileRankModelImpl.CACHE_ENABLED;
		String finderClassName = DLFileRank.class.getName();
		String finderMethodName = "countByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByF_N(long folderId, String name) throws SystemException {
		boolean finderClassNameCacheEnabled = DLFileRankModelImpl.CACHE_ENABLED;
		String finderClassName = DLFileRank.class.getName();
		String finderMethodName = "countByF_N";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(folderId), name };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");

				query.append("folderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByC_U_F_N(long companyId, long userId, long folderId,
		String name) throws SystemException {
		boolean finderClassNameCacheEnabled = DLFileRankModelImpl.CACHE_ENABLED;
		String finderClassName = DLFileRank.class.getName();
		String finderMethodName = "countByC_U_F_N";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(userId), new Long(folderId),
				
				name
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.documentlibrary.model.DLFileRank WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("folderId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = DLFileRankModelImpl.CACHE_ENABLED;
		String finderClassName = DLFileRank.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.documentlibrary.model.DLFileRank");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFileRank")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLFileRank>> listenersList = new ArrayList<ModelListener<DLFileRank>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLFileRank>)Class.forName(
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
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	private static Log _log = LogFactoryUtil.getLog(DLFileRankPersistenceImpl.class);
}