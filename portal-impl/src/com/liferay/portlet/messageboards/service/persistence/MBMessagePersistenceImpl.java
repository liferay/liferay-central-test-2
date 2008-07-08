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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.impl.MBMessageImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageModelImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="MBMessagePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBMessagePersistenceImpl extends BasePersistenceImpl
	implements MBMessagePersistence {
	public MBMessage create(long messageId) {
		MBMessage mbMessage = new MBMessageImpl();

		mbMessage.setNew(true);
		mbMessage.setPrimaryKey(messageId);

		String uuid = PortalUUIDUtil.generate();

		mbMessage.setUuid(uuid);

		return mbMessage;
	}

	public MBMessage remove(long messageId)
		throws NoSuchMessageException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBMessage mbMessage = (MBMessage)session.get(MBMessageImpl.class,
					new Long(messageId));

			if (mbMessage == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No MBMessage exists with the primary key " +
						messageId);
				}

				throw new NoSuchMessageException(
					"No MBMessage exists with the primary key " + messageId);
			}

			return remove(mbMessage);
		}
		catch (NoSuchMessageException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBMessage remove(MBMessage mbMessage) throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(mbMessage);
			}
		}

		mbMessage = removeImpl(mbMessage);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(mbMessage);
			}
		}

		return mbMessage;
	}

	protected MBMessage removeImpl(MBMessage mbMessage)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(mbMessage);

			session.flush();

			return mbMessage;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(MBMessage.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(MBMessage mbMessage, boolean merge)</code>.
	 */
	public MBMessage update(MBMessage mbMessage) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(MBMessage mbMessage) method. Use update(MBMessage mbMessage, boolean merge) instead.");
		}

		return update(mbMessage, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        mbMessage the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when mbMessage is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public MBMessage update(MBMessage mbMessage, boolean merge)
		throws SystemException {
		boolean isNew = mbMessage.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(mbMessage);
				}
				else {
					listener.onBeforeUpdate(mbMessage);
				}
			}
		}

		mbMessage = updateImpl(mbMessage, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(mbMessage);
				}
				else {
					listener.onAfterUpdate(mbMessage);
				}
			}
		}

		return mbMessage;
	}

	public MBMessage updateImpl(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage,
		boolean merge) throws SystemException {
		if (Validator.isNull(mbMessage.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mbMessage.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(mbMessage);
			}
			else {
				if (mbMessage.isNew()) {
					session.save(mbMessage);
				}
			}

			session.flush();

			mbMessage.setNew(false);

			return mbMessage;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(MBMessage.class.getName());
		}
	}

	public MBMessage findByPrimaryKey(long messageId)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = fetchByPrimaryKey(messageId);

		if (mbMessage == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No MBMessage exists with the primary key " +
					messageId);
			}

			throw new NoSuchMessageException(
				"No MBMessage exists with the primary key " + messageId);
		}

		return mbMessage;
	}

	public MBMessage fetchByPrimaryKey(long messageId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (MBMessage)session.get(MBMessageImpl.class,
				new Long(messageId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findByUuid(String uuid) throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
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
					"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC, ");
				query.append("messageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<MBMessage> list = q.list();

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
			return (List<MBMessage>)result;
		}
	}

	public List<MBMessage> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<MBMessage> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
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
					"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

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

					query.append("createDate ASC, ");
					query.append("messageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<MBMessage> list = (List<MBMessage>)QueryUtil.list(q,
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
			return (List<MBMessage>)result;
		}
	}

	public MBMessage findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByUuid(uuid, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		int count = countByUuid(uuid);

		List<MBMessage> list = findByUuid(uuid, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage[] findByUuid_PrevAndNext(long messageId, String uuid,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

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

				query.append("createDate ASC, ");
				query.append("messageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findByCompanyId(long companyId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
		String finderMethodName = "findByCompanyId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(companyId) };

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
					"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC, ");
				query.append("messageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<MBMessage> list = q.list();

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
			return (List<MBMessage>)result;
		}
	}

	public List<MBMessage> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<MBMessage> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
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
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC, ");
					query.append("messageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<MBMessage> list = (List<MBMessage>)QueryUtil.list(q,
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
			return (List<MBMessage>)result;
		}
	}

	public MBMessage findByCompanyId_First(long companyId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		int count = countByCompanyId(companyId);

		List<MBMessage> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage[] findByCompanyId_PrevAndNext(long messageId,
		long companyId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

			query.append("companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate ASC, ");
				query.append("messageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findByCategoryId(long categoryId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
		String finderMethodName = "findByCategoryId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(categoryId) };

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
					"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

				query.append("categoryId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC, ");
				query.append("messageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				List<MBMessage> list = q.list();

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
			return (List<MBMessage>)result;
		}
	}

	public List<MBMessage> findByCategoryId(long categoryId, int start, int end)
		throws SystemException {
		return findByCategoryId(categoryId, start, end, null);
	}

	public List<MBMessage> findByCategoryId(long categoryId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
		String finderMethodName = "findByCategoryId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(categoryId),
				
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
					"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

				query.append("categoryId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC, ");
					query.append("messageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				List<MBMessage> list = (List<MBMessage>)QueryUtil.list(q,
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
			return (List<MBMessage>)result;
		}
	}

	public MBMessage findByCategoryId_First(long categoryId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByCategoryId(categoryId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("categoryId=" + categoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage findByCategoryId_Last(long categoryId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		int count = countByCategoryId(categoryId);

		List<MBMessage> list = findByCategoryId(categoryId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("categoryId=" + categoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage[] findByCategoryId_PrevAndNext(long messageId,
		long categoryId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		int count = countByCategoryId(categoryId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

			query.append("categoryId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate ASC, ");
				query.append("messageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(categoryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findByThreadId(long threadId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
		String finderMethodName = "findByThreadId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(threadId) };

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
					"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

				query.append("threadId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC, ");
				query.append("messageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				List<MBMessage> list = q.list();

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
			return (List<MBMessage>)result;
		}
	}

	public List<MBMessage> findByThreadId(long threadId, int start, int end)
		throws SystemException {
		return findByThreadId(threadId, start, end, null);
	}

	public List<MBMessage> findByThreadId(long threadId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
		String finderMethodName = "findByThreadId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(threadId),
				
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
					"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

				query.append("threadId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC, ");
					query.append("messageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				List<MBMessage> list = (List<MBMessage>)QueryUtil.list(q,
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
			return (List<MBMessage>)result;
		}
	}

	public MBMessage findByThreadId_First(long threadId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByThreadId(threadId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("threadId=" + threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage findByThreadId_Last(long threadId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		int count = countByThreadId(threadId);

		List<MBMessage> list = findByThreadId(threadId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("threadId=" + threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage[] findByThreadId_PrevAndNext(long messageId,
		long threadId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		int count = countByThreadId(threadId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

			query.append("threadId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate ASC, ");
				query.append("messageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(threadId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findByC_T(long categoryId, long threadId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
		String finderMethodName = "findByC_T";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(categoryId), new Long(threadId)
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
					"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

				query.append("categoryId = ?");

				query.append(" AND ");

				query.append("threadId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC, ");
				query.append("messageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				qPos.add(threadId);

				List<MBMessage> list = q.list();

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
			return (List<MBMessage>)result;
		}
	}

	public List<MBMessage> findByC_T(long categoryId, long threadId, int start,
		int end) throws SystemException {
		return findByC_T(categoryId, threadId, start, end, null);
	}

	public List<MBMessage> findByC_T(long categoryId, long threadId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
		String finderMethodName = "findByC_T";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(categoryId), new Long(threadId),
				
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
					"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

				query.append("categoryId = ?");

				query.append(" AND ");

				query.append("threadId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC, ");
					query.append("messageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				qPos.add(threadId);

				List<MBMessage> list = (List<MBMessage>)QueryUtil.list(q,
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
			return (List<MBMessage>)result;
		}
	}

	public MBMessage findByC_T_First(long categoryId, long threadId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByC_T(categoryId, threadId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("categoryId=" + categoryId);

			msg.append(", ");
			msg.append("threadId=" + threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage findByC_T_Last(long categoryId, long threadId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		int count = countByC_T(categoryId, threadId);

		List<MBMessage> list = findByC_T(categoryId, threadId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("categoryId=" + categoryId);

			msg.append(", ");
			msg.append("threadId=" + threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage[] findByC_T_PrevAndNext(long messageId, long categoryId,
		long threadId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		int count = countByC_T(categoryId, threadId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

			query.append("categoryId = ?");

			query.append(" AND ");

			query.append("threadId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate ASC, ");
				query.append("messageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(categoryId);

			qPos.add(threadId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findByT_P(long threadId, long parentMessageId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
		String finderMethodName = "findByT_P";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(threadId), new Long(parentMessageId)
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
					"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

				query.append("threadId = ?");

				query.append(" AND ");

				query.append("parentMessageId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC, ");
				query.append("messageId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				qPos.add(parentMessageId);

				List<MBMessage> list = q.list();

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
			return (List<MBMessage>)result;
		}
	}

	public List<MBMessage> findByT_P(long threadId, long parentMessageId,
		int start, int end) throws SystemException {
		return findByT_P(threadId, parentMessageId, start, end, null);
	}

	public List<MBMessage> findByT_P(long threadId, long parentMessageId,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
		String finderMethodName = "findByT_P";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(threadId), new Long(parentMessageId),
				
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
					"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

				query.append("threadId = ?");

				query.append(" AND ");

				query.append("parentMessageId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC, ");
					query.append("messageId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				qPos.add(parentMessageId);

				List<MBMessage> list = (List<MBMessage>)QueryUtil.list(q,
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
			return (List<MBMessage>)result;
		}
	}

	public MBMessage findByT_P_First(long threadId, long parentMessageId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		List<MBMessage> list = findByT_P(threadId, parentMessageId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("threadId=" + threadId);

			msg.append(", ");
			msg.append("parentMessageId=" + parentMessageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage findByT_P_Last(long threadId, long parentMessageId,
		OrderByComparator obc) throws NoSuchMessageException, SystemException {
		int count = countByT_P(threadId, parentMessageId);

		List<MBMessage> list = findByT_P(threadId, parentMessageId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBMessage exists with the key {");

			msg.append("threadId=" + threadId);

			msg.append(", ");
			msg.append("parentMessageId=" + parentMessageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBMessage[] findByT_P_PrevAndNext(long messageId, long threadId,
		long parentMessageId, OrderByComparator obc)
		throws NoSuchMessageException, SystemException {
		MBMessage mbMessage = findByPrimaryKey(messageId);

		int count = countByT_P(threadId, parentMessageId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

			query.append("threadId = ?");

			query.append(" AND ");

			query.append("parentMessageId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate ASC, ");
				query.append("messageId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(threadId);

			qPos.add(parentMessageId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessage);

			MBMessage[] array = new MBMessageImpl[3];

			array[0] = (MBMessage)objArray[0];
			array[1] = (MBMessage)objArray[1];
			array[2] = (MBMessage)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findWithDynamicQuery(DynamicQuery dynamicQuery)
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

	public List<MBMessage> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			dynamicQuery.setLimit(start, end);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<MBMessage> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<MBMessage> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
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
					"FROM com.liferay.portlet.messageboards.model.MBMessage ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC, ");
					query.append("messageId ASC");
				}

				Query q = session.createQuery(query.toString());

				List<MBMessage> list = (List<MBMessage>)QueryUtil.list(q,
						getDialect(), start, end);

				if (obc == null) {
					Collections.sort(list);
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
			return (List<MBMessage>)result;
		}
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (MBMessage mbMessage : findByUuid(uuid)) {
			remove(mbMessage);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (MBMessage mbMessage : findByCompanyId(companyId)) {
			remove(mbMessage);
		}
	}

	public void removeByCategoryId(long categoryId) throws SystemException {
		for (MBMessage mbMessage : findByCategoryId(categoryId)) {
			remove(mbMessage);
		}
	}

	public void removeByThreadId(long threadId) throws SystemException {
		for (MBMessage mbMessage : findByThreadId(threadId)) {
			remove(mbMessage);
		}
	}

	public void removeByC_T(long categoryId, long threadId)
		throws SystemException {
		for (MBMessage mbMessage : findByC_T(categoryId, threadId)) {
			remove(mbMessage);
		}
	}

	public void removeByT_P(long threadId, long parentMessageId)
		throws SystemException {
		for (MBMessage mbMessage : findByT_P(threadId, parentMessageId)) {
			remove(mbMessage);
		}
	}

	public void removeAll() throws SystemException {
		for (MBMessage mbMessage : findAll()) {
			remove(mbMessage);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
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
					"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

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

	public int countByCompanyId(long companyId) throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
		String finderMethodName = "countByCompanyId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(companyId) };

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
					"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

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

	public int countByCategoryId(long categoryId) throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
		String finderMethodName = "countByCategoryId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(categoryId) };

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
					"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

				query.append("categoryId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

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

	public int countByThreadId(long threadId) throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
		String finderMethodName = "countByThreadId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(threadId) };

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
					"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

				query.append("threadId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

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

	public int countByC_T(long categoryId, long threadId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
		String finderMethodName = "countByC_T";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(categoryId), new Long(threadId)
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
					"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

				query.append("categoryId = ?");

				query.append(" AND ");

				query.append("threadId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				qPos.add(threadId);

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

	public int countByT_P(long threadId, long parentMessageId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
		String finderMethodName = "countByT_P";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(threadId), new Long(parentMessageId)
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
					"FROM com.liferay.portlet.messageboards.model.MBMessage WHERE ");

				query.append("threadId = ?");

				query.append(" AND ");

				query.append("parentMessageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				qPos.add(parentMessageId);

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
		boolean finderClassNameCacheEnabled = MBMessageModelImpl.CACHE_ENABLED;
		String finderClassName = MBMessage.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.messageboards.model.MBMessage");

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

	public void registerListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.add(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	public void unregisterListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.remove(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	protected void init() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.messageboards.model.MBMessage")));

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

	private static Log _log = LogFactory.getLog(MBMessagePersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}