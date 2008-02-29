/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tasks.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.tasks.NoSuchProposalException;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.model.impl.TasksProposalImpl;
import com.liferay.portlet.tasks.model.impl.TasksProposalModelImpl;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="TasksProposalPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TasksProposalPersistenceImpl extends BasePersistence
	implements TasksProposalPersistence {
	public TasksProposal create(long proposalId) {
		TasksProposal tasksProposal = new TasksProposalImpl();

		tasksProposal.setNew(true);
		tasksProposal.setPrimaryKey(proposalId);

		return tasksProposal;
	}

	public TasksProposal remove(long proposalId)
		throws NoSuchProposalException, SystemException {
		Session session = null;

		try {
			session = openSession();

			TasksProposal tasksProposal = (TasksProposal)session.get(TasksProposalImpl.class,
					new Long(proposalId));

			if (tasksProposal == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No TasksProposal exists with the primary key " +
						proposalId);
				}

				throw new NoSuchProposalException(
					"No TasksProposal exists with the primary key " +
					proposalId);
			}

			return remove(tasksProposal);
		}
		catch (NoSuchProposalException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public TasksProposal remove(TasksProposal tasksProposal)
		throws SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(tasksProposal);
		}

		tasksProposal = removeImpl(tasksProposal);

		if (listener != null) {
			listener.onAfterRemove(tasksProposal);
		}

		return tasksProposal;
	}

	protected TasksProposal removeImpl(TasksProposal tasksProposal)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(tasksProposal);

			session.flush();

			return tasksProposal;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(TasksProposal.class.getName());
		}
	}

	public TasksProposal update(TasksProposal tasksProposal)
		throws SystemException {
		return update(tasksProposal, false);
	}

	public TasksProposal update(TasksProposal tasksProposal, boolean merge)
		throws SystemException {
		ModelListener listener = _getListener();

		boolean isNew = tasksProposal.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(tasksProposal);
			}
			else {
				listener.onBeforeUpdate(tasksProposal);
			}
		}

		tasksProposal = updateImpl(tasksProposal, merge);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(tasksProposal);
			}
			else {
				listener.onAfterUpdate(tasksProposal);
			}
		}

		return tasksProposal;
	}

	public TasksProposal updateImpl(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(tasksProposal);
			}
			else {
				if (tasksProposal.isNew()) {
					session.save(tasksProposal);
				}
			}

			session.flush();

			tasksProposal.setNew(false);

			return tasksProposal;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(TasksProposal.class.getName());
		}
	}

	public TasksProposal findByPrimaryKey(long proposalId)
		throws NoSuchProposalException, SystemException {
		TasksProposal tasksProposal = fetchByPrimaryKey(proposalId);

		if (tasksProposal == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No TasksProposal exists with the primary key " +
					proposalId);
			}

			throw new NoSuchProposalException(
				"No TasksProposal exists with the primary key " + proposalId);
		}

		return tasksProposal;
	}

	public TasksProposal fetchByPrimaryKey(long proposalId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (TasksProposal)session.get(TasksProposalImpl.class,
				new Long(proposalId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksProposal> findByCompanyId(long companyId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TasksProposalModelImpl.CACHE_ENABLED;
		String finderClassName = TasksProposal.class.getName();
		String finderMethodName = "findByCompanyId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksProposal WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dueDate ASC, ");
				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, companyId);

				List<TasksProposal> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TasksProposal>)result;
		}
	}

	public List<TasksProposal> findByCompanyId(long companyId, int begin,
		int end) throws SystemException {
		return findByCompanyId(companyId, begin, end, null);
	}

	public List<TasksProposal> findByCompanyId(long companyId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = TasksProposalModelImpl.CACHE_ENABLED;
		String finderClassName = TasksProposal.class.getName();
		String finderMethodName = "findByCompanyId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksProposal WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("dueDate ASC, ");
					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, companyId);

				List<TasksProposal> list = (List<TasksProposal>)QueryUtil.list(q,
						getDialect(), begin, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TasksProposal>)result;
		}
	}

	public TasksProposal findByCompanyId_First(long companyId,
		OrderByComparator obc) throws NoSuchProposalException, SystemException {
		List<TasksProposal> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No TasksProposal exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProposalException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksProposal findByCompanyId_Last(long companyId,
		OrderByComparator obc) throws NoSuchProposalException, SystemException {
		int count = countByCompanyId(companyId);

		List<TasksProposal> list = findByCompanyId(companyId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No TasksProposal exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProposalException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksProposal[] findByCompanyId_PrevAndNext(long proposalId,
		long companyId, OrderByComparator obc)
		throws NoSuchProposalException, SystemException {
		TasksProposal tasksProposal = findByPrimaryKey(proposalId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.tasks.model.TasksProposal WHERE ");

			query.append("companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("dueDate ASC, ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			int queryPos = 0;

			q.setLong(queryPos++, companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tasksProposal);

			TasksProposal[] array = new TasksProposalImpl[3];

			array[0] = (TasksProposal)objArray[0];
			array[1] = (TasksProposal)objArray[1];
			array[2] = (TasksProposal)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public TasksProposal findByC_C(long classNameId, long classPK)
		throws NoSuchProposalException, SystemException {
		TasksProposal tasksProposal = fetchByC_C(classNameId, classPK);

		if (tasksProposal == null) {
			StringMaker msg = new StringMaker();

			msg.append("No TasksProposal exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProposalException(msg.toString());
		}

		return tasksProposal;
	}

	public TasksProposal fetchByC_C(long classNameId, long classPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TasksProposalModelImpl.CACHE_ENABLED;
		String finderClassName = TasksProposal.class.getName();
		String finderMethodName = "fetchByC_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksProposal WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dueDate ASC, ");
				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classNameId);

				q.setLong(queryPos++, classPK);

				List<TasksProposal> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
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
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<TasksProposal> list = (List<TasksProposal>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<TasksProposal> findByC_G(long companyId, long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TasksProposalModelImpl.CACHE_ENABLED;
		String finderClassName = TasksProposal.class.getName();
		String finderMethodName = "findByC_G";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(groupId)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksProposal WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dueDate ASC, ");
				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, companyId);

				q.setLong(queryPos++, groupId);

				List<TasksProposal> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TasksProposal>)result;
		}
	}

	public List<TasksProposal> findByC_G(long companyId, long groupId,
		int begin, int end) throws SystemException {
		return findByC_G(companyId, groupId, begin, end, null);
	}

	public List<TasksProposal> findByC_G(long companyId, long groupId,
		int begin, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = TasksProposalModelImpl.CACHE_ENABLED;
		String finderClassName = TasksProposal.class.getName();
		String finderMethodName = "findByC_G";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(groupId),
				
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksProposal WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("dueDate ASC, ");
					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, companyId);

				q.setLong(queryPos++, groupId);

				List<TasksProposal> list = (List<TasksProposal>)QueryUtil.list(q,
						getDialect(), begin, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TasksProposal>)result;
		}
	}

	public TasksProposal findByC_G_First(long companyId, long groupId,
		OrderByComparator obc) throws NoSuchProposalException, SystemException {
		List<TasksProposal> list = findByC_G(companyId, groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No TasksProposal exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProposalException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksProposal findByC_G_Last(long companyId, long groupId,
		OrderByComparator obc) throws NoSuchProposalException, SystemException {
		int count = countByC_G(companyId, groupId);

		List<TasksProposal> list = findByC_G(companyId, groupId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No TasksProposal exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProposalException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksProposal[] findByC_G_PrevAndNext(long proposalId,
		long companyId, long groupId, OrderByComparator obc)
		throws NoSuchProposalException, SystemException {
		TasksProposal tasksProposal = findByPrimaryKey(proposalId);

		int count = countByC_G(companyId, groupId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.tasks.model.TasksProposal WHERE ");

			query.append("companyId = ?");

			query.append(" AND ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("dueDate ASC, ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			int queryPos = 0;

			q.setLong(queryPos++, companyId);

			q.setLong(queryPos++, groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tasksProposal);

			TasksProposal[] array = new TasksProposalImpl[3];

			array[0] = (TasksProposal)objArray[0];
			array[1] = (TasksProposal)objArray[1];
			array[2] = (TasksProposal)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksProposal> findByC_G_U(long companyId, long groupId,
		long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = TasksProposalModelImpl.CACHE_ENABLED;
		String finderClassName = TasksProposal.class.getName();
		String finderMethodName = "findByC_G_U";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(groupId), new Long(userId)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksProposal WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("dueDate ASC, ");
				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, companyId);

				q.setLong(queryPos++, groupId);

				q.setLong(queryPos++, userId);

				List<TasksProposal> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TasksProposal>)result;
		}
	}

	public List<TasksProposal> findByC_G_U(long companyId, long groupId,
		long userId, int begin, int end) throws SystemException {
		return findByC_G_U(companyId, groupId, userId, begin, end, null);
	}

	public List<TasksProposal> findByC_G_U(long companyId, long groupId,
		long userId, int begin, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TasksProposalModelImpl.CACHE_ENABLED;
		String finderClassName = TasksProposal.class.getName();
		String finderMethodName = "findByC_G_U";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(groupId), new Long(userId),
				
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksProposal WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("dueDate ASC, ");
					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, companyId);

				q.setLong(queryPos++, groupId);

				q.setLong(queryPos++, userId);

				List<TasksProposal> list = (List<TasksProposal>)QueryUtil.list(q,
						getDialect(), begin, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TasksProposal>)result;
		}
	}

	public TasksProposal findByC_G_U_First(long companyId, long groupId,
		long userId, OrderByComparator obc)
		throws NoSuchProposalException, SystemException {
		List<TasksProposal> list = findByC_G_U(companyId, groupId, userId, 0,
				1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No TasksProposal exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProposalException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksProposal findByC_G_U_Last(long companyId, long groupId,
		long userId, OrderByComparator obc)
		throws NoSuchProposalException, SystemException {
		int count = countByC_G_U(companyId, groupId, userId);

		List<TasksProposal> list = findByC_G_U(companyId, groupId, userId,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No TasksProposal exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProposalException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksProposal[] findByC_G_U_PrevAndNext(long proposalId,
		long companyId, long groupId, long userId, OrderByComparator obc)
		throws NoSuchProposalException, SystemException {
		TasksProposal tasksProposal = findByPrimaryKey(proposalId);

		int count = countByC_G_U(companyId, groupId, userId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.tasks.model.TasksProposal WHERE ");

			query.append("companyId = ?");

			query.append(" AND ");

			query.append("groupId = ?");

			query.append(" AND ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("dueDate ASC, ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			int queryPos = 0;

			q.setLong(queryPos++, companyId);

			q.setLong(queryPos++, groupId);

			q.setLong(queryPos++, userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tasksProposal);

			TasksProposal[] array = new TasksProposalImpl[3];

			array[0] = (TasksProposal)objArray[0];
			array[1] = (TasksProposal)objArray[1];
			array[2] = (TasksProposal)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksProposal> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksProposal> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer, int begin, int end)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			query.setLimit(begin, end);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksProposal> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<TasksProposal> findAll(int begin, int end)
		throws SystemException {
		return findAll(begin, end, null);
	}

	public List<TasksProposal> findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TasksProposalModelImpl.CACHE_ENABLED;
		String finderClassName = TasksProposal.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksProposal ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("dueDate ASC, ");
					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				List<TasksProposal> list = (List<TasksProposal>)QueryUtil.list(q,
						getDialect(), begin, end);

				if (obc == null) {
					Collections.sort(list);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TasksProposal>)result;
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (TasksProposal tasksProposal : findByCompanyId(companyId)) {
			remove(tasksProposal);
		}
	}

	public void removeByC_C(long classNameId, long classPK)
		throws NoSuchProposalException, SystemException {
		TasksProposal tasksProposal = findByC_C(classNameId, classPK);

		remove(tasksProposal);
	}

	public void removeByC_G(long companyId, long groupId)
		throws SystemException {
		for (TasksProposal tasksProposal : findByC_G(companyId, groupId)) {
			remove(tasksProposal);
		}
	}

	public void removeByC_G_U(long companyId, long groupId, long userId)
		throws SystemException {
		for (TasksProposal tasksProposal : findByC_G_U(companyId, groupId,
				userId)) {
			remove(tasksProposal);
		}
	}

	public void removeAll() throws SystemException {
		for (TasksProposal tasksProposal : findAll()) {
			remove(tasksProposal);
		}
	}

	public int countByCompanyId(long companyId) throws SystemException {
		boolean finderClassNameCacheEnabled = TasksProposalModelImpl.CACHE_ENABLED;
		String finderClassName = TasksProposal.class.getName();
		String finderMethodName = "countByCompanyId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksProposal WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, companyId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TasksProposalModelImpl.CACHE_ENABLED;
		String finderClassName = TasksProposal.class.getName();
		String finderMethodName = "countByC_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksProposal WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, classNameId);

				q.setLong(queryPos++, classPK);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByC_G(long companyId, long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TasksProposalModelImpl.CACHE_ENABLED;
		String finderClassName = TasksProposal.class.getName();
		String finderMethodName = "countByC_G";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(groupId)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksProposal WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, companyId);

				q.setLong(queryPos++, groupId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByC_G_U(long companyId, long groupId, long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TasksProposalModelImpl.CACHE_ENABLED;
		String finderClassName = TasksProposal.class.getName();
		String finderMethodName = "countByC_G_U";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(groupId), new Long(userId)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksProposal WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, companyId);

				q.setLong(queryPos++, groupId);

				q.setLong(queryPos++, userId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
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
		boolean finderClassNameCacheEnabled = TasksProposalModelImpl.CACHE_ENABLED;
		String finderClassName = TasksProposal.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.tasks.model.TasksProposal");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	protected void initDao() {
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.tasks.model.TasksProposal"));
	private static Log _log = LogFactory.getLog(TasksProposalPersistenceImpl.class);
}