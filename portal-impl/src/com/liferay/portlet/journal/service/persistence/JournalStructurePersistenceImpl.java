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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.impl.JournalStructureImpl;
import com.liferay.portlet.journal.model.impl.JournalStructureModelImpl;

import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="JournalStructurePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalStructurePersistenceImpl extends BasePersistence
	implements JournalStructurePersistence {
	public JournalStructure create(long id) {
		JournalStructure journalStructure = new JournalStructureImpl();

		journalStructure.setNew(true);
		journalStructure.setPrimaryKey(id);

		String uuid = PortalUUIDUtil.generate();

		journalStructure.setUuid(uuid);

		return journalStructure;
	}

	public JournalStructure remove(long id)
		throws NoSuchStructureException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalStructure journalStructure = (JournalStructure)session.get(JournalStructureImpl.class,
					new Long(id));

			if (journalStructure == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No JournalStructure exists with the primary key " +
						id);
				}

				throw new NoSuchStructureException(
					"No JournalStructure exists with the primary key " + id);
			}

			return remove(journalStructure);
		}
		catch (NoSuchStructureException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalStructure remove(JournalStructure journalStructure)
		throws SystemException {
		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(journalStructure);
			}
		}

		journalStructure = removeImpl(journalStructure);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(journalStructure);
			}
		}

		return journalStructure;
	}

	protected JournalStructure removeImpl(JournalStructure journalStructure)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(journalStructure);

			session.flush();

			return journalStructure;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(JournalStructure.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(JournalStructure journalStructure, boolean merge)</code>.
	 */
	public JournalStructure update(JournalStructure journalStructure)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(JournalStructure journalStructure) method. Use update(JournalStructure journalStructure, boolean merge) instead.");
		}

		return update(journalStructure, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        journalStructure the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when journalStructure is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public JournalStructure update(JournalStructure journalStructure,
		boolean merge) throws SystemException {
		boolean isNew = journalStructure.isNew();

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(journalStructure);
				}
				else {
					listener.onBeforeUpdate(journalStructure);
				}
			}
		}

		journalStructure = updateImpl(journalStructure, merge);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(journalStructure);
				}
				else {
					listener.onAfterUpdate(journalStructure);
				}
			}
		}

		return journalStructure;
	}

	public JournalStructure updateImpl(
		com.liferay.portlet.journal.model.JournalStructure journalStructure,
		boolean merge) throws SystemException {
		if (Validator.isNull(journalStructure.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalStructure.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(journalStructure);
			}
			else {
				if (journalStructure.isNew()) {
					session.save(journalStructure);
				}
			}

			session.flush();

			journalStructure.setNew(false);

			return journalStructure;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(JournalStructure.class.getName());
		}
	}

	public JournalStructure findByPrimaryKey(long id)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByPrimaryKey(id);

		if (journalStructure == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No JournalStructure exists with the primary key " +
					id);
			}

			throw new NoSuchStructureException(
				"No JournalStructure exists with the primary key " + id);
		}

		return journalStructure;
	}

	public JournalStructure fetchByPrimaryKey(long id)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (JournalStructure)session.get(JournalStructureImpl.class,
				new Long(id));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalStructure> findByUuid(String uuid)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalStructureModelImpl.CACHE_ENABLED;
		String finderClassName = JournalStructure.class.getName();
		String finderMethodName = "findByUuid";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { uuid };

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
					"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("structureId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<JournalStructure> list = q.list();

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
			return (List<JournalStructure>)result;
		}
	}

	public List<JournalStructure> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<JournalStructure> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalStructureModelImpl.CACHE_ENABLED;
		String finderClassName = JournalStructure.class.getName();
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
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

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

					query.append("structureId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<JournalStructure> list = (List<JournalStructure>)QueryUtil.list(q,
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
			return (List<JournalStructure>)result;
		}
	}

	public JournalStructure findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchStructureException, SystemException {
		List<JournalStructure> list = findByUuid(uuid, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No JournalStructure exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalStructure findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchStructureException, SystemException {
		int count = countByUuid(uuid);

		List<JournalStructure> list = findByUuid(uuid, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No JournalStructure exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalStructure[] findByUuid_PrevAndNext(long id, String uuid,
		OrderByComparator obc) throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByPrimaryKey(id);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

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

				query.append("structureId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalStructure);

			JournalStructure[] array = new JournalStructureImpl[3];

			array[0] = (JournalStructure)objArray[0];
			array[1] = (JournalStructure)objArray[1];
			array[2] = (JournalStructure)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalStructure findByUUID_G(String uuid, long groupId)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByUUID_G(uuid, groupId);

		if (journalStructure == null) {
			StringMaker msg = new StringMaker();

			msg.append("No JournalStructure exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStructureException(msg.toString());
		}

		return journalStructure;
	}

	public JournalStructure fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalStructureModelImpl.CACHE_ENABLED;
		String finderClassName = JournalStructure.class.getName();
		String finderMethodName = "fetchByUUID_G";
		String[] finderParams = new String[] {
				String.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

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
					"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

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

				query.append("structureId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<JournalStructure> list = q.list();

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
			List<JournalStructure> list = (List<JournalStructure>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<JournalStructure> findByGroupId(long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalStructureModelImpl.CACHE_ENABLED;
		String finderClassName = JournalStructure.class.getName();
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
					"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("structureId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<JournalStructure> list = q.list();

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
			return (List<JournalStructure>)result;
		}
	}

	public List<JournalStructure> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<JournalStructure> findByGroupId(long groupId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalStructureModelImpl.CACHE_ENABLED;
		String finderClassName = JournalStructure.class.getName();
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
					"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("structureId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<JournalStructure> list = (List<JournalStructure>)QueryUtil.list(q,
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
			return (List<JournalStructure>)result;
		}
	}

	public JournalStructure findByGroupId_First(long groupId,
		OrderByComparator obc) throws NoSuchStructureException, SystemException {
		List<JournalStructure> list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No JournalStructure exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalStructure findByGroupId_Last(long groupId,
		OrderByComparator obc) throws NoSuchStructureException, SystemException {
		int count = countByGroupId(groupId);

		List<JournalStructure> list = findByGroupId(groupId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No JournalStructure exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalStructure[] findByGroupId_PrevAndNext(long id, long groupId,
		OrderByComparator obc) throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByPrimaryKey(id);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("structureId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalStructure);

			JournalStructure[] array = new JournalStructureImpl[3];

			array[0] = (JournalStructure)objArray[0];
			array[1] = (JournalStructure)objArray[1];
			array[2] = (JournalStructure)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalStructure> findByStructureId(String structureId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalStructureModelImpl.CACHE_ENABLED;
		String finderClassName = JournalStructure.class.getName();
		String finderMethodName = "findByStructureId";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { structureId };

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
					"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

				if (structureId == null) {
					query.append("structureId IS NULL");
				}
				else {
					query.append("structureId = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("structureId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (structureId != null) {
					qPos.add(structureId);
				}

				List<JournalStructure> list = q.list();

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
			return (List<JournalStructure>)result;
		}
	}

	public List<JournalStructure> findByStructureId(String structureId,
		int start, int end) throws SystemException {
		return findByStructureId(structureId, start, end, null);
	}

	public List<JournalStructure> findByStructureId(String structureId,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalStructureModelImpl.CACHE_ENABLED;
		String finderClassName = JournalStructure.class.getName();
		String finderMethodName = "findByStructureId";
		String[] finderParams = new String[] {
				String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				structureId,
				
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
					"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

				if (structureId == null) {
					query.append("structureId IS NULL");
				}
				else {
					query.append("structureId = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("structureId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (structureId != null) {
					qPos.add(structureId);
				}

				List<JournalStructure> list = (List<JournalStructure>)QueryUtil.list(q,
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
			return (List<JournalStructure>)result;
		}
	}

	public JournalStructure findByStructureId_First(String structureId,
		OrderByComparator obc) throws NoSuchStructureException, SystemException {
		List<JournalStructure> list = findByStructureId(structureId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No JournalStructure exists with the key {");

			msg.append("structureId=" + structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalStructure findByStructureId_Last(String structureId,
		OrderByComparator obc) throws NoSuchStructureException, SystemException {
		int count = countByStructureId(structureId);

		List<JournalStructure> list = findByStructureId(structureId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No JournalStructure exists with the key {");

			msg.append("structureId=" + structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalStructure[] findByStructureId_PrevAndNext(long id,
		String structureId, OrderByComparator obc)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByPrimaryKey(id);

		int count = countByStructureId(structureId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

			if (structureId == null) {
				query.append("structureId IS NULL");
			}
			else {
				query.append("structureId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("structureId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (structureId != null) {
				qPos.add(structureId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalStructure);

			JournalStructure[] array = new JournalStructureImpl[3];

			array[0] = (JournalStructure)objArray[0];
			array[1] = (JournalStructure)objArray[1];
			array[2] = (JournalStructure)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalStructure findByG_S(long groupId, String structureId)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByG_S(groupId, structureId);

		if (journalStructure == null) {
			StringMaker msg = new StringMaker();

			msg.append("No JournalStructure exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("structureId=" + structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStructureException(msg.toString());
		}

		return journalStructure;
	}

	public JournalStructure fetchByG_S(long groupId, String structureId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalStructureModelImpl.CACHE_ENABLED;
		String finderClassName = JournalStructure.class.getName();
		String finderMethodName = "fetchByG_S";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(groupId), structureId };

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
					"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (structureId == null) {
					query.append("structureId IS NULL");
				}
				else {
					query.append("structureId = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("structureId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
				}

				List<JournalStructure> list = q.list();

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
			List<JournalStructure> list = (List<JournalStructure>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<JournalStructure> findWithDynamicQuery(
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

	public List<JournalStructure> findWithDynamicQuery(
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

	public List<JournalStructure> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalStructure> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<JournalStructure> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalStructureModelImpl.CACHE_ENABLED;
		String finderClassName = JournalStructure.class.getName();
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
					"FROM com.liferay.portlet.journal.model.JournalStructure ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("structureId ASC");
				}

				Query q = session.createQuery(query.toString());

				List<JournalStructure> list = (List<JournalStructure>)QueryUtil.list(q,
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
			return (List<JournalStructure>)result;
		}
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (JournalStructure journalStructure : findByUuid(uuid)) {
			remove(journalStructure);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByUUID_G(uuid, groupId);

		remove(journalStructure);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (JournalStructure journalStructure : findByGroupId(groupId)) {
			remove(journalStructure);
		}
	}

	public void removeByStructureId(String structureId)
		throws SystemException {
		for (JournalStructure journalStructure : findByStructureId(structureId)) {
			remove(journalStructure);
		}
	}

	public void removeByG_S(long groupId, String structureId)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByG_S(groupId, structureId);

		remove(journalStructure);
	}

	public void removeAll() throws SystemException {
		for (JournalStructure journalStructure : findAll()) {
			remove(journalStructure);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalStructureModelImpl.CACHE_ENABLED;
		String finderClassName = JournalStructure.class.getName();
		String finderMethodName = "countByUuid";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { uuid };

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
					"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

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

	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalStructureModelImpl.CACHE_ENABLED;
		String finderClassName = JournalStructure.class.getName();
		String finderMethodName = "countByUUID_G";
		String[] finderParams = new String[] {
				String.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

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
					"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

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

	public int countByGroupId(long groupId) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalStructureModelImpl.CACHE_ENABLED;
		String finderClassName = JournalStructure.class.getName();
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
					"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

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

	public int countByStructureId(String structureId) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalStructureModelImpl.CACHE_ENABLED;
		String finderClassName = JournalStructure.class.getName();
		String finderMethodName = "countByStructureId";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { structureId };

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
					"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

				if (structureId == null) {
					query.append("structureId IS NULL");
				}
				else {
					query.append("structureId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (structureId != null) {
					qPos.add(structureId);
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

	public int countByG_S(long groupId, String structureId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalStructureModelImpl.CACHE_ENABLED;
		String finderClassName = JournalStructure.class.getName();
		String finderMethodName = "countByG_S";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(groupId), structureId };

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
					"FROM com.liferay.portlet.journal.model.JournalStructure WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (structureId == null) {
					query.append("structureId IS NULL");
				}
				else {
					query.append("structureId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
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
		boolean finderClassNameCacheEnabled = JournalStructureModelImpl.CACHE_ENABLED;
		String finderClassName = JournalStructure.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.journal.model.JournalStructure");

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
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					PropsUtil.get(
						"value.object.listener.com.liferay.portlet.journal.model.JournalStructure")));

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
	}

	private static Log _log = LogFactory.getLog(JournalStructurePersistenceImpl.class);
	private ModelListener[] _listeners;
}