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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryImpl;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl;

import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import org.springframework.dao.DataAccessException;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="SCProductEntryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCProductEntryPersistenceImpl extends BasePersistence
	implements SCProductEntryPersistence {
	public SCProductEntry create(long productEntryId) {
		SCProductEntry scProductEntry = new SCProductEntryImpl();

		scProductEntry.setNew(true);
		scProductEntry.setPrimaryKey(productEntryId);

		return scProductEntry;
	}

	public SCProductEntry remove(long productEntryId)
		throws NoSuchProductEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCProductEntry scProductEntry = (SCProductEntry)session.get(SCProductEntryImpl.class,
					new Long(productEntryId));

			if (scProductEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No SCProductEntry exists with the primary key " +
						productEntryId);
				}

				throw new NoSuchProductEntryException(
					"No SCProductEntry exists with the primary key " +
					productEntryId);
			}

			return remove(scProductEntry);
		}
		catch (NoSuchProductEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCProductEntry remove(SCProductEntry scProductEntry)
		throws SystemException {
		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(scProductEntry);
			}
		}

		scProductEntry = removeImpl(scProductEntry);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(scProductEntry);
			}
		}

		return scProductEntry;
	}

	protected SCProductEntry removeImpl(SCProductEntry scProductEntry)
		throws SystemException {
		try {
			clearSCLicenses.clear(scProductEntry.getPrimaryKey());
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			FinderCache.clearCache("SCLicenses_SCProductEntries");
		}

		Session session = null;

		try {
			session = openSession();

			session.delete(scProductEntry);

			session.flush();

			return scProductEntry;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(SCProductEntry.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(SCProductEntry scProductEntry, boolean merge)</code>.
	 */
	public SCProductEntry update(SCProductEntry scProductEntry)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(SCProductEntry scProductEntry) method. Use update(SCProductEntry scProductEntry, boolean merge) instead.");
		}

		return update(scProductEntry, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        scProductEntry the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when scProductEntry is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public SCProductEntry update(SCProductEntry scProductEntry, boolean merge)
		throws SystemException {
		boolean isNew = scProductEntry.isNew();

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(scProductEntry);
				}
				else {
					listener.onBeforeUpdate(scProductEntry);
				}
			}
		}

		scProductEntry = updateImpl(scProductEntry, merge);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(scProductEntry);
				}
				else {
					listener.onAfterUpdate(scProductEntry);
				}
			}
		}

		return scProductEntry;
	}

	public SCProductEntry updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry,
		boolean merge) throws SystemException {
		FinderCache.clearCache("SCLicenses_SCProductEntries");

		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(scProductEntry);
			}
			else {
				if (scProductEntry.isNew()) {
					session.save(scProductEntry);
				}
			}

			session.flush();

			scProductEntry.setNew(false);

			return scProductEntry;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(SCProductEntry.class.getName());
		}
	}

	public SCProductEntry findByPrimaryKey(long productEntryId)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = fetchByPrimaryKey(productEntryId);

		if (scProductEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SCProductEntry exists with the primary key " +
					productEntryId);
			}

			throw new NoSuchProductEntryException(
				"No SCProductEntry exists with the primary key " +
				productEntryId);
		}

		return scProductEntry;
	}

	public SCProductEntry fetchByPrimaryKey(long productEntryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (SCProductEntry)session.get(SCProductEntryImpl.class,
				new Long(productEntryId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCProductEntry> findByGroupId(long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductEntryModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductEntry.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

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
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductEntry WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("modifiedDate DESC, ");
				query.append("name DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<SCProductEntry> list = q.list();

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
			return (List<SCProductEntry>)result;
		}
	}

	public List<SCProductEntry> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<SCProductEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductEntryModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductEntry.class.getName();
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
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductEntry WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("modifiedDate DESC, ");
					query.append("name DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<SCProductEntry> list = (List<SCProductEntry>)QueryUtil.list(q,
						getDialect(), start, end);

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
			return (List<SCProductEntry>)result;
		}
	}

	public SCProductEntry findByGroupId_First(long groupId,
		OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		List<SCProductEntry> list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No SCProductEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductEntry findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		int count = countByGroupId(groupId);

		List<SCProductEntry> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No SCProductEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductEntry[] findByGroupId_PrevAndNext(long productEntryId,
		long groupId, OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = findByPrimaryKey(productEntryId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCProductEntry WHERE ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("modifiedDate DESC, ");
				query.append("name DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scProductEntry);

			SCProductEntry[] array = new SCProductEntryImpl[3];

			array[0] = (SCProductEntry)objArray[0];
			array[1] = (SCProductEntry)objArray[1];
			array[2] = (SCProductEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCProductEntry> findByCompanyId(long companyId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductEntryModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductEntry.class.getName();
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
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductEntry WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("modifiedDate DESC, ");
				query.append("name DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<SCProductEntry> list = q.list();

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
			return (List<SCProductEntry>)result;
		}
	}

	public List<SCProductEntry> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<SCProductEntry> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductEntryModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductEntry.class.getName();
		String finderMethodName = "findByCompanyId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
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
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductEntry WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("modifiedDate DESC, ");
					query.append("name DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<SCProductEntry> list = (List<SCProductEntry>)QueryUtil.list(q,
						getDialect(), start, end);

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
			return (List<SCProductEntry>)result;
		}
	}

	public SCProductEntry findByCompanyId_First(long companyId,
		OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		List<SCProductEntry> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No SCProductEntry exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductEntry findByCompanyId_Last(long companyId,
		OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		int count = countByCompanyId(companyId);

		List<SCProductEntry> list = findByCompanyId(companyId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No SCProductEntry exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductEntry[] findByCompanyId_PrevAndNext(long productEntryId,
		long companyId, OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = findByPrimaryKey(productEntryId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCProductEntry WHERE ");

			query.append("companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("modifiedDate DESC, ");
				query.append("name DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scProductEntry);

			SCProductEntry[] array = new SCProductEntryImpl[3];

			array[0] = (SCProductEntry)objArray[0];
			array[1] = (SCProductEntry)objArray[1];
			array[2] = (SCProductEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCProductEntry> findByG_U(long groupId, long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductEntryModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductEntry.class.getName();
		String finderMethodName = "findByG_U";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

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
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductEntry WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("modifiedDate DESC, ");
				query.append("name DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				List<SCProductEntry> list = q.list();

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
			return (List<SCProductEntry>)result;
		}
	}

	public List<SCProductEntry> findByG_U(long groupId, long userId, int start,
		int end) throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	public List<SCProductEntry> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductEntryModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductEntry.class.getName();
		String finderMethodName = "findByG_U";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
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
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductEntry WHERE ");

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

					query.append("modifiedDate DESC, ");
					query.append("name DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				List<SCProductEntry> list = (List<SCProductEntry>)QueryUtil.list(q,
						getDialect(), start, end);

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
			return (List<SCProductEntry>)result;
		}
	}

	public SCProductEntry findByG_U_First(long groupId, long userId,
		OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		List<SCProductEntry> list = findByG_U(groupId, userId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No SCProductEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductEntry findByG_U_Last(long groupId, long userId,
		OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		int count = countByG_U(groupId, userId);

		List<SCProductEntry> list = findByG_U(groupId, userId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No SCProductEntry exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductEntry[] findByG_U_PrevAndNext(long productEntryId,
		long groupId, long userId, OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = findByPrimaryKey(productEntryId);

		int count = countByG_U(groupId, userId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCProductEntry WHERE ");

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

				query.append("modifiedDate DESC, ");
				query.append("name DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scProductEntry);

			SCProductEntry[] array = new SCProductEntryImpl[3];

			array[0] = (SCProductEntry)objArray[0];
			array[1] = (SCProductEntry)objArray[1];
			array[2] = (SCProductEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCProductEntry findByRG_RA(String repoGroupId, String repoArtifactId)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = fetchByRG_RA(repoGroupId, repoArtifactId);

		if (scProductEntry == null) {
			StringMaker msg = new StringMaker();

			msg.append("No SCProductEntry exists with the key {");

			msg.append("repoGroupId=" + repoGroupId);

			msg.append(", ");
			msg.append("repoArtifactId=" + repoArtifactId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductEntryException(msg.toString());
		}

		return scProductEntry;
	}

	public SCProductEntry fetchByRG_RA(String repoGroupId, String repoArtifactId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductEntryModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductEntry.class.getName();
		String finderMethodName = "fetchByRG_RA";
		String[] finderParams = new String[] {
				String.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { repoGroupId, repoArtifactId };

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
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductEntry WHERE ");

				if (repoGroupId == null) {
					query.append("repoGroupId IS NULL");
				}
				else {
					query.append("lower(repoGroupId) = ?");
				}

				query.append(" AND ");

				if (repoArtifactId == null) {
					query.append("repoArtifactId IS NULL");
				}
				else {
					query.append("lower(repoArtifactId) = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("modifiedDate DESC, ");
				query.append("name DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (repoGroupId != null) {
					qPos.add(repoGroupId);
				}

				if (repoArtifactId != null) {
					qPos.add(repoArtifactId);
				}

				List<SCProductEntry> list = q.list();

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
			List<SCProductEntry> list = (List<SCProductEntry>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<SCProductEntry> findWithDynamicQuery(
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

	public List<SCProductEntry> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer, int start, int end)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			query.setLimit(start, end);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCProductEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SCProductEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SCProductEntry> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductEntryModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductEntry.class.getName();
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
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductEntry ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("modifiedDate DESC, ");
					query.append("name DESC");
				}

				Query q = session.createQuery(query.toString());

				List<SCProductEntry> list = (List<SCProductEntry>)QueryUtil.list(q,
						getDialect(), start, end);

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
			return (List<SCProductEntry>)result;
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (SCProductEntry scProductEntry : findByGroupId(groupId)) {
			remove(scProductEntry);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (SCProductEntry scProductEntry : findByCompanyId(companyId)) {
			remove(scProductEntry);
		}
	}

	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (SCProductEntry scProductEntry : findByG_U(groupId, userId)) {
			remove(scProductEntry);
		}
	}

	public void removeByRG_RA(String repoGroupId, String repoArtifactId)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = findByRG_RA(repoGroupId, repoArtifactId);

		remove(scProductEntry);
	}

	public void removeAll() throws SystemException {
		for (SCProductEntry scProductEntry : findAll()) {
			remove(scProductEntry);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductEntryModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductEntry.class.getName();
		String finderMethodName = "countByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

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
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductEntry WHERE ");

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

	public int countByCompanyId(long companyId) throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductEntryModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductEntry.class.getName();
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
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductEntry WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	public int countByG_U(long groupId, long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductEntryModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductEntry.class.getName();
		String finderMethodName = "countByG_U";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

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
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductEntry WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

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

	public int countByRG_RA(String repoGroupId, String repoArtifactId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductEntryModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductEntry.class.getName();
		String finderMethodName = "countByRG_RA";
		String[] finderParams = new String[] {
				String.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { repoGroupId, repoArtifactId };

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
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductEntry WHERE ");

				if (repoGroupId == null) {
					query.append("repoGroupId IS NULL");
				}
				else {
					query.append("lower(repoGroupId) = ?");
				}

				query.append(" AND ");

				if (repoArtifactId == null) {
					query.append("repoArtifactId IS NULL");
				}
				else {
					query.append("lower(repoArtifactId) = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (repoGroupId != null) {
					qPos.add(repoGroupId);
				}

				if (repoArtifactId != null) {
					qPos.add(repoArtifactId);
				}

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
		boolean finderClassNameCacheEnabled = SCProductEntryModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductEntry.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.softwarecatalog.model.SCProductEntry");

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

	public List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		long pk) throws NoSuchProductEntryException, SystemException {
		return getSCLicenses(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		long pk, int start, int end)
		throws NoSuchProductEntryException, SystemException {
		return getSCLicenses(pk, start, end, null);
	}

	public List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		long pk, int start, int end, OrderByComparator obc)
		throws NoSuchProductEntryException, SystemException {
		boolean finderClassNameCacheEnabled = SCProductEntryModelImpl.CACHE_ENABLED_SCLICENSES_SCPRODUCTENTRIES;

		String finderClassName = "SCLicenses_SCProductEntries";

		String finderMethodName = "getSCLicenses";
		String[] finderParams = new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = HibernateUtil.openSession();

				StringMaker sm = new StringMaker();

				sm.append(_SQL_GETSCLICENSES);

				if (obc != null) {
					sm.append("ORDER BY ");
					sm.append(obc.getOrderBy());
				}

				else {
					sm.append("ORDER BY ");

					sm.append("SCLicense.name ASC");
				}

				String sql = sm.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("SCLicense",
					com.liferay.portlet.softwarecatalog.model.impl.SCLicenseImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				List<com.liferay.portlet.softwarecatalog.model.SCLicense> list = (List<com.liferay.portlet.softwarecatalog.model.SCLicense>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<com.liferay.portlet.softwarecatalog.model.SCLicense>)result;
		}
	}

	public int getSCLicensesSize(long pk) throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductEntryModelImpl.CACHE_ENABLED_SCLICENSES_SCPRODUCTENTRIES;

		String finderClassName = "SCLicenses_SCProductEntries";

		String finderMethodName = "getSCLicensesSize";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(pk) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETSCLICENSESSIZE);

				q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

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

	public boolean containsSCLicense(long pk, long scLicensePK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductEntryModelImpl.CACHE_ENABLED_SCLICENSES_SCPRODUCTENTRIES;

		String finderClassName = "SCLicenses_SCProductEntries";

		String finderMethodName = "containsSCLicenses";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(pk), new Long(scLicensePK) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			try {
				Boolean value = Boolean.valueOf(containsSCLicense.contains(pk,
							scLicensePK));

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, value);

				return value.booleanValue();
			}
			catch (DataAccessException dae) {
				throw new SystemException(dae);
			}
		}
		else {
			return ((Boolean)result).booleanValue();
		}
	}

	public boolean containsSCLicenses(long pk) throws SystemException {
		if (getSCLicensesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addSCLicense(long pk, long scLicensePK)
		throws NoSuchProductEntryException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException,
			SystemException {
		try {
			addSCLicense.add(pk, scLicensePK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void addSCLicense(long pk,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws NoSuchProductEntryException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException,
			SystemException {
		try {
			addSCLicense.add(pk, scLicense.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void addSCLicenses(long pk, long[] scLicensePKs)
		throws NoSuchProductEntryException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException,
			SystemException {
		try {
			for (long scLicensePK : scLicensePKs) {
				addSCLicense.add(pk, scLicensePK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void addSCLicenses(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses)
		throws NoSuchProductEntryException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException,
			SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCLicense scLicense : scLicenses) {
				addSCLicense.add(pk, scLicense.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void clearSCLicenses(long pk)
		throws NoSuchProductEntryException, SystemException {
		try {
			clearSCLicenses.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void removeSCLicense(long pk, long scLicensePK)
		throws NoSuchProductEntryException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException,
			SystemException {
		try {
			removeSCLicense.remove(pk, scLicensePK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void removeSCLicense(long pk,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws NoSuchProductEntryException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException,
			SystemException {
		try {
			removeSCLicense.remove(pk, scLicense.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void removeSCLicenses(long pk, long[] scLicensePKs)
		throws NoSuchProductEntryException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException,
			SystemException {
		try {
			for (long scLicensePK : scLicensePKs) {
				removeSCLicense.remove(pk, scLicensePK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void removeSCLicenses(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses)
		throws NoSuchProductEntryException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException,
			SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCLicense scLicense : scLicenses) {
				removeSCLicense.remove(pk, scLicense.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void setSCLicenses(long pk, long[] scLicensePKs)
		throws NoSuchProductEntryException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException,
			SystemException {
		try {
			clearSCLicenses.clear(pk);

			for (long scLicensePK : scLicensePKs) {
				addSCLicense.add(pk, scLicensePK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("SCLicenses_SCProductEntries");
		}
	}

	public void setSCLicenses(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses)
		throws NoSuchProductEntryException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException,
			SystemException {
		try {
			clearSCLicenses.clear(pk);

			for (com.liferay.portlet.softwarecatalog.model.SCLicense scLicense : scLicenses) {
				addSCLicense.add(pk, scLicense.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("SCLicenses_SCProductEntries");
		}
	}

	protected void initDao() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					PropsUtil.get(
						"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCProductEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener> listeners = new ArrayList<ModelListener>();

				for (String listenerClassName : listenerClassNames) {
					listeners.add((ModelListener)Class.forName(
							listenerClassName).newInstance());
				}

				_listeners = listeners.toArray(new ModelListener[listeners.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsSCLicense = new ContainsSCLicense(this);

		addSCLicense = new AddSCLicense(this);
		clearSCLicenses = new ClearSCLicenses(this);
		removeSCLicense = new RemoveSCLicense(this);
	}

	protected ContainsSCLicense containsSCLicense;
	protected AddSCLicense addSCLicense;
	protected ClearSCLicenses clearSCLicenses;
	protected RemoveSCLicense removeSCLicense;

	protected class ContainsSCLicense extends MappingSqlQuery {
		protected ContainsSCLicense(
			SCProductEntryPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(), _SQL_CONTAINSSCLICENSE);

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(long productEntryId, long licenseId) {
			List<Integer> results = execute(new Object[] {
						new Long(productEntryId), new Long(licenseId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}
	}

	protected class AddSCLicense extends SqlUpdate {
		protected AddSCLicense(SCProductEntryPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"INSERT INTO SCLicenses_SCProductEntries (productEntryId, licenseId) VALUES (?, ?)");

			_persistenceImpl = persistenceImpl;

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void add(long productEntryId, long licenseId) {
			if (!_persistenceImpl.containsSCLicense.contains(productEntryId,
						licenseId)) {
				update(new Object[] {
						new Long(productEntryId), new Long(licenseId)
					});
			}
		}

		private SCProductEntryPersistenceImpl _persistenceImpl;
	}

	protected class ClearSCLicenses extends SqlUpdate {
		protected ClearSCLicenses(SCProductEntryPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"DELETE FROM SCLicenses_SCProductEntries WHERE productEntryId = ?");

			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void clear(long productEntryId) {
			update(new Object[] { new Long(productEntryId) });
		}
	}

	protected class RemoveSCLicense extends SqlUpdate {
		protected RemoveSCLicense(SCProductEntryPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"DELETE FROM SCLicenses_SCProductEntries WHERE productEntryId = ? AND licenseId = ?");

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void remove(long productEntryId, long licenseId) {
			update(new Object[] { new Long(productEntryId), new Long(licenseId) });
		}
	}

	private static final String _SQL_GETSCLICENSES = "SELECT {SCLicense.*} FROM SCLicense INNER JOIN SCLicenses_SCProductEntries ON (SCLicenses_SCProductEntries.licenseId = SCLicense.licenseId) WHERE (SCLicenses_SCProductEntries.productEntryId = ?)";
	private static final String _SQL_GETSCLICENSESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM SCLicenses_SCProductEntries WHERE productEntryId = ?";
	private static final String _SQL_CONTAINSSCLICENSE = "SELECT COUNT(*) AS COUNT_VALUE FROM SCLicenses_SCProductEntries WHERE productEntryId = ? AND licenseId = ?";
	private static Log _log = LogFactory.getLog(SCProductEntryPersistenceImpl.class);
	private ModelListener[] _listeners;
}