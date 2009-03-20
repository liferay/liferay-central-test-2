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

package com.liferay.portlet.polls.service.persistence;

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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.polls.NoSuchQuestionException;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.impl.PollsQuestionImpl;
import com.liferay.portlet.polls.model.impl.PollsQuestionModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="PollsQuestionPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PollsQuestionPersistenceImpl extends BasePersistenceImpl
	implements PollsQuestionPersistence {
	public PollsQuestion create(long questionId) {
		PollsQuestion pollsQuestion = new PollsQuestionImpl();

		pollsQuestion.setNew(true);
		pollsQuestion.setPrimaryKey(questionId);

		String uuid = PortalUUIDUtil.generate();

		pollsQuestion.setUuid(uuid);

		return pollsQuestion;
	}

	public PollsQuestion remove(long questionId)
		throws NoSuchQuestionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PollsQuestion pollsQuestion = (PollsQuestion)session.get(PollsQuestionImpl.class,
					new Long(questionId));

			if (pollsQuestion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No PollsQuestion exists with the primary key " +
						questionId);
				}

				throw new NoSuchQuestionException(
					"No PollsQuestion exists with the primary key " +
					questionId);
			}

			return remove(pollsQuestion);
		}
		catch (NoSuchQuestionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsQuestion remove(PollsQuestion pollsQuestion)
		throws SystemException {
		for (ModelListener<PollsQuestion> listener : listeners) {
			listener.onBeforeRemove(pollsQuestion);
		}

		pollsQuestion = removeImpl(pollsQuestion);

		for (ModelListener<PollsQuestion> listener : listeners) {
			listener.onAfterRemove(pollsQuestion);
		}

		return pollsQuestion;
	}

	protected PollsQuestion removeImpl(PollsQuestion pollsQuestion)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(PollsQuestionImpl.class,
						pollsQuestion.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(pollsQuestion);

			session.flush();

			return pollsQuestion;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(PollsQuestion.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(PollsQuestion pollsQuestion, boolean merge)</code>.
	 */
	public PollsQuestion update(PollsQuestion pollsQuestion)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(PollsQuestion pollsQuestion) method. Use update(PollsQuestion pollsQuestion, boolean merge) instead.");
		}

		return update(pollsQuestion, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        pollsQuestion the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when pollsQuestion is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public PollsQuestion update(PollsQuestion pollsQuestion, boolean merge)
		throws SystemException {
		boolean isNew = pollsQuestion.isNew();

		for (ModelListener<PollsQuestion> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(pollsQuestion);
			}
			else {
				listener.onBeforeUpdate(pollsQuestion);
			}
		}

		pollsQuestion = updateImpl(pollsQuestion, merge);

		for (ModelListener<PollsQuestion> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(pollsQuestion);
			}
			else {
				listener.onAfterUpdate(pollsQuestion);
			}
		}

		return pollsQuestion;
	}

	public PollsQuestion updateImpl(
		com.liferay.portlet.polls.model.PollsQuestion pollsQuestion,
		boolean merge) throws SystemException {
		if (Validator.isNull(pollsQuestion.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			pollsQuestion.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, pollsQuestion, merge);

			pollsQuestion.setNew(false);

			return pollsQuestion;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(PollsQuestion.class.getName());
		}
	}

	public PollsQuestion findByPrimaryKey(long questionId)
		throws NoSuchQuestionException, SystemException {
		PollsQuestion pollsQuestion = fetchByPrimaryKey(questionId);

		if (pollsQuestion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No PollsQuestion exists with the primary key " +
					questionId);
			}

			throw new NoSuchQuestionException(
				"No PollsQuestion exists with the primary key " + questionId);
		}

		return pollsQuestion;
	}

	public PollsQuestion fetchByPrimaryKey(long questionId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (PollsQuestion)session.get(PollsQuestionImpl.class,
				new Long(questionId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<PollsQuestion> findByUuid(String uuid)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PollsQuestionModelImpl.CACHE_ENABLED;
		String finderClassName = PollsQuestion.class.getName();
		String finderMethodName = "findByUuid";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { uuid };

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
					"FROM com.liferay.portlet.polls.model.PollsQuestion WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<PollsQuestion> list = q.list();

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
			return (List<PollsQuestion>)result;
		}
	}

	public List<PollsQuestion> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<PollsQuestion> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = PollsQuestionModelImpl.CACHE_ENABLED;
		String finderClassName = PollsQuestion.class.getName();
		String finderMethodName = "findByUuid";
		String[] finderParams = new String[] {
				String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				uuid,
				
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
					"FROM com.liferay.portlet.polls.model.PollsQuestion WHERE ");

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

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<PollsQuestion> list = (List<PollsQuestion>)QueryUtil.list(q,
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
			return (List<PollsQuestion>)result;
		}
	}

	public PollsQuestion findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchQuestionException, SystemException {
		List<PollsQuestion> list = findByUuid(uuid, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No PollsQuestion exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchQuestionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsQuestion findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchQuestionException, SystemException {
		int count = countByUuid(uuid);

		List<PollsQuestion> list = findByUuid(uuid, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No PollsQuestion exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchQuestionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsQuestion[] findByUuid_PrevAndNext(long questionId, String uuid,
		OrderByComparator obc) throws NoSuchQuestionException, SystemException {
		PollsQuestion pollsQuestion = findByPrimaryKey(questionId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.polls.model.PollsQuestion WHERE ");

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

			else {
				query.append("ORDER BY ");

				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					pollsQuestion);

			PollsQuestion[] array = new PollsQuestionImpl[3];

			array[0] = (PollsQuestion)objArray[0];
			array[1] = (PollsQuestion)objArray[1];
			array[2] = (PollsQuestion)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PollsQuestion findByUUID_G(String uuid, long groupId)
		throws NoSuchQuestionException, SystemException {
		PollsQuestion pollsQuestion = fetchByUUID_G(uuid, groupId);

		if (pollsQuestion == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No PollsQuestion exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchQuestionException(msg.toString());
		}

		return pollsQuestion;
	}

	public PollsQuestion fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PollsQuestionModelImpl.CACHE_ENABLED;
		String finderClassName = PollsQuestion.class.getName();
		String finderMethodName = "fetchByUUID_G";
		String[] finderParams = new String[] {
				String.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

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
					"FROM com.liferay.portlet.polls.model.PollsQuestion WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" AND ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<PollsQuestion> list = q.list();

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
			List<PollsQuestion> list = (List<PollsQuestion>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<PollsQuestion> findByGroupId(long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PollsQuestionModelImpl.CACHE_ENABLED;
		String finderClassName = PollsQuestion.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

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
					"FROM com.liferay.portlet.polls.model.PollsQuestion WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<PollsQuestion> list = q.list();

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
			return (List<PollsQuestion>)result;
		}
	}

	public List<PollsQuestion> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<PollsQuestion> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = PollsQuestionModelImpl.CACHE_ENABLED;
		String finderClassName = PollsQuestion.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
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
					"FROM com.liferay.portlet.polls.model.PollsQuestion WHERE ");

				query.append("groupId = ?");

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

				qPos.add(groupId);

				List<PollsQuestion> list = (List<PollsQuestion>)QueryUtil.list(q,
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
			return (List<PollsQuestion>)result;
		}
	}

	public PollsQuestion findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchQuestionException, SystemException {
		List<PollsQuestion> list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No PollsQuestion exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchQuestionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsQuestion findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchQuestionException, SystemException {
		int count = countByGroupId(groupId);

		List<PollsQuestion> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No PollsQuestion exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchQuestionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PollsQuestion[] findByGroupId_PrevAndNext(long questionId,
		long groupId, OrderByComparator obc)
		throws NoSuchQuestionException, SystemException {
		PollsQuestion pollsQuestion = findByPrimaryKey(questionId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.polls.model.PollsQuestion WHERE ");

			query.append("groupId = ?");

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

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					pollsQuestion);

			PollsQuestion[] array = new PollsQuestionImpl[3];

			array[0] = (PollsQuestion)objArray[0];
			array[1] = (PollsQuestion)objArray[1];
			array[2] = (PollsQuestion)objArray[2];

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

	public List<PollsQuestion> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<PollsQuestion> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<PollsQuestion> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PollsQuestionModelImpl.CACHE_ENABLED;
		String finderClassName = PollsQuestion.class.getName();
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
					"FROM com.liferay.portlet.polls.model.PollsQuestion ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				List<PollsQuestion> list = null;

				if (obc == null) {
					list = (List<PollsQuestion>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<PollsQuestion>)QueryUtil.list(q, getDialect(),
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
			return (List<PollsQuestion>)result;
		}
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (PollsQuestion pollsQuestion : findByUuid(uuid)) {
			remove(pollsQuestion);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchQuestionException, SystemException {
		PollsQuestion pollsQuestion = findByUUID_G(uuid, groupId);

		remove(pollsQuestion);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (PollsQuestion pollsQuestion : findByGroupId(groupId)) {
			remove(pollsQuestion);
		}
	}

	public void removeAll() throws SystemException {
		for (PollsQuestion pollsQuestion : findAll()) {
			remove(pollsQuestion);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		boolean finderClassNameCacheEnabled = PollsQuestionModelImpl.CACHE_ENABLED;
		String finderClassName = PollsQuestion.class.getName();
		String finderMethodName = "countByUuid";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { uuid };

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
					"FROM com.liferay.portlet.polls.model.PollsQuestion WHERE ");

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

	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PollsQuestionModelImpl.CACHE_ENABLED;
		String finderClassName = PollsQuestion.class.getName();
		String finderMethodName = "countByUUID_G";
		String[] finderParams = new String[] {
				String.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

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
					"FROM com.liferay.portlet.polls.model.PollsQuestion WHERE ");

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

	public int countByGroupId(long groupId) throws SystemException {
		boolean finderClassNameCacheEnabled = PollsQuestionModelImpl.CACHE_ENABLED;
		String finderClassName = PollsQuestion.class.getName();
		String finderMethodName = "countByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

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
					"FROM com.liferay.portlet.polls.model.PollsQuestion WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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
		boolean finderClassNameCacheEnabled = PollsQuestionModelImpl.CACHE_ENABLED;
		String finderClassName = PollsQuestion.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.polls.model.PollsQuestion");

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
						"value.object.listener.com.liferay.portlet.polls.model.PollsQuestion")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PollsQuestion>> listenersList = new ArrayList<ModelListener<PollsQuestion>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PollsQuestion>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.polls.service.persistence.PollsChoicePersistence.impl")
	protected com.liferay.portlet.polls.service.persistence.PollsChoicePersistence pollsChoicePersistence;
	@BeanReference(name = "com.liferay.portlet.polls.service.persistence.PollsQuestionPersistence.impl")
	protected com.liferay.portlet.polls.service.persistence.PollsQuestionPersistence pollsQuestionPersistence;
	@BeanReference(name = "com.liferay.portlet.polls.service.persistence.PollsVotePersistence.impl")
	protected com.liferay.portlet.polls.service.persistence.PollsVotePersistence pollsVotePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(PollsQuestionPersistenceImpl.class);
}