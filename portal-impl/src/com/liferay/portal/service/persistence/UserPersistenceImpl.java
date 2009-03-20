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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.model.impl.UserModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="UserPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserPersistenceImpl extends BasePersistenceImpl
	implements UserPersistence {
	public User create(long userId) {
		User user = new UserImpl();

		user.setNew(true);
		user.setPrimaryKey(userId);

		String uuid = PortalUUIDUtil.generate();

		user.setUuid(uuid);

		return user;
	}

	public User remove(long userId) throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			User user = (User)session.get(UserImpl.class, new Long(userId));

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No User exists with the primary key " + userId);
				}

				throw new NoSuchUserException(
					"No User exists with the primary key " + userId);
			}

			return remove(user);
		}
		catch (NoSuchUserException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public User remove(User user) throws SystemException {
		for (ModelListener<User> listener : listeners) {
			listener.onBeforeRemove(user);
		}

		user = removeImpl(user);

		for (ModelListener<User> listener : listeners) {
			listener.onAfterRemove(user);
		}

		return user;
	}

	protected User removeImpl(User user) throws SystemException {
		try {
			clearGroups.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}

		try {
			clearOrganizations.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}

		try {
			clearPermissions.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}

		try {
			clearRoles.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}

		try {
			clearUserGroups.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}

		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(UserImpl.class,
						user.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(user);

			session.flush();

			return user;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(User.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(User user, boolean merge)</code>.
	 */
	public User update(User user) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(User user) method. Use update(User user, boolean merge) instead.");
		}

		return update(user, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        user the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when user is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public User update(User user, boolean merge) throws SystemException {
		boolean isNew = user.isNew();

		for (ModelListener<User> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(user);
			}
			else {
				listener.onBeforeUpdate(user);
			}
		}

		user = updateImpl(user, merge);

		for (ModelListener<User> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(user);
			}
			else {
				listener.onAfterUpdate(user);
			}
		}

		return user;
	}

	public User updateImpl(com.liferay.portal.model.User user, boolean merge)
		throws SystemException {
		FinderCacheUtil.clearCache("Users_Groups");
		FinderCacheUtil.clearCache("Users_Orgs");
		FinderCacheUtil.clearCache("Users_Permissions");
		FinderCacheUtil.clearCache("Users_Roles");
		FinderCacheUtil.clearCache("Users_UserGroups");

		if (Validator.isNull(user.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			user.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, user, merge);

			user.setNew(false);

			return user;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(User.class.getName());
		}
	}

	public User findByPrimaryKey(long userId)
		throws NoSuchUserException, SystemException {
		User user = fetchByPrimaryKey(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No User exists with the primary key " + userId);
			}

			throw new NoSuchUserException(
				"No User exists with the primary key " + userId);
		}

		return user;
	}

	public User fetchByPrimaryKey(long userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (User)session.get(UserImpl.class, new Long(userId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<User> findByUuid(String uuid) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
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

				query.append("FROM com.liferay.portal.model.User WHERE ");

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

				List<User> list = q.list();

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
			return (List<User>)result;
		}
	}

	public List<User> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<User> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
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

				query.append("FROM com.liferay.portal.model.User WHERE ");

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

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<User> list = (List<User>)QueryUtil.list(q, getDialect(),
						start, end);

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
			return (List<User>)result;
		}
	}

	public User findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		List<User> list = findByUuid(uuid, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		int count = countByUuid(uuid);

		List<User> list = findByUuid(uuid, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User[] findByUuid_PrevAndNext(long userId, String uuid,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.User WHERE ");

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

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, user);

			User[] array = new UserImpl[3];

			array[0] = (User)objArray[0];
			array[1] = (User)objArray[1];
			array[2] = (User)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<User> findByCompanyId(long companyId) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
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

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<User> list = q.list();

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
			return (List<User>)result;
		}
	}

	public List<User> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<User> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
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

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<User> list = (List<User>)QueryUtil.list(q, getDialect(),
						start, end);

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
			return (List<User>)result;
		}
	}

	public User findByCompanyId_First(long companyId, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		List<User> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		int count = countByCompanyId(companyId);

		List<User> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User[] findByCompanyId_PrevAndNext(long userId, long companyId,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.User WHERE ");

			query.append("companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, user);

			User[] array = new UserImpl[3];

			array[0] = (User)objArray[0];
			array[1] = (User)objArray[1];
			array[2] = (User)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public User findByContactId(long contactId)
		throws NoSuchUserException, SystemException {
		User user = fetchByContactId(contactId);

		if (user == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("contactId=" + contactId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByContactId(long contactId) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "fetchByContactId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(contactId) };

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

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("contactId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(contactId);

				List<User> list = q.list();

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
			List<User> list = (List<User>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<User> findByEmailAddress(String emailAddress)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "findByEmailAddress";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { emailAddress };

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

				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (emailAddress == null) {
					query.append("emailAddress IS NULL");
				}
				else {
					query.append("emailAddress = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (emailAddress != null) {
					qPos.add(emailAddress);
				}

				List<User> list = q.list();

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
			return (List<User>)result;
		}
	}

	public List<User> findByEmailAddress(String emailAddress, int start, int end)
		throws SystemException {
		return findByEmailAddress(emailAddress, start, end, null);
	}

	public List<User> findByEmailAddress(String emailAddress, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "findByEmailAddress";
		String[] finderParams = new String[] {
				String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				emailAddress,
				
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

				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (emailAddress == null) {
					query.append("emailAddress IS NULL");
				}
				else {
					query.append("emailAddress = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (emailAddress != null) {
					qPos.add(emailAddress);
				}

				List<User> list = (List<User>)QueryUtil.list(q, getDialect(),
						start, end);

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
			return (List<User>)result;
		}
	}

	public User findByEmailAddress_First(String emailAddress,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		List<User> list = findByEmailAddress(emailAddress, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("emailAddress=" + emailAddress);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User findByEmailAddress_Last(String emailAddress,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		int count = countByEmailAddress(emailAddress);

		List<User> list = findByEmailAddress(emailAddress, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("emailAddress=" + emailAddress);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User[] findByEmailAddress_PrevAndNext(long userId,
		String emailAddress, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		int count = countByEmailAddress(emailAddress);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (emailAddress == null) {
				query.append("emailAddress IS NULL");
			}
			else {
				query.append("emailAddress = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (emailAddress != null) {
				qPos.add(emailAddress);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, user);

			User[] array = new UserImpl[3];

			array[0] = (User)objArray[0];
			array[1] = (User)objArray[1];
			array[2] = (User)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public User findByOpenId(String openId)
		throws NoSuchUserException, SystemException {
		User user = fetchByOpenId(openId);

		if (user == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("openId=" + openId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByOpenId(String openId) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "fetchByOpenId";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { openId };

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

				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (openId == null) {
					query.append("openId IS NULL");
				}
				else {
					query.append("openId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (openId != null) {
					qPos.add(openId);
				}

				List<User> list = q.list();

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
			List<User> list = (List<User>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public User findByPortraitId(long portraitId)
		throws NoSuchUserException, SystemException {
		User user = fetchByPortraitId(portraitId);

		if (user == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("portraitId=" + portraitId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByPortraitId(long portraitId) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "fetchByPortraitId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(portraitId) };

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

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("portraitId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(portraitId);

				List<User> list = q.list();

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
			List<User> list = (List<User>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public User findByC_U(long companyId, long userId)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_U(companyId, userId);

		if (user == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByC_U(long companyId, long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "fetchByC_U";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(companyId), new Long(userId) };

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

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				List<User> list = q.list();

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
			List<User> list = (List<User>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public User findByC_DU(long companyId, boolean defaultUser)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_DU(companyId, defaultUser);

		if (user == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("defaultUser=" + defaultUser);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByC_DU(long companyId, boolean defaultUser)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "fetchByC_DU";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(defaultUser)
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

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("defaultUser = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultUser);

				List<User> list = q.list();

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
			List<User> list = (List<User>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public User findByC_SN(long companyId, String screenName)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_SN(companyId, screenName);

		if (user == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("screenName=" + screenName);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByC_SN(long companyId, String screenName)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "fetchByC_SN";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(companyId), screenName };

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

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (screenName == null) {
					query.append("screenName IS NULL");
				}
				else {
					query.append("screenName = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (screenName != null) {
					qPos.add(screenName);
				}

				List<User> list = q.list();

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
			List<User> list = (List<User>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public User findByC_EA(long companyId, String emailAddress)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_EA(companyId, emailAddress);

		if (user == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("emailAddress=" + emailAddress);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByC_EA(long companyId, String emailAddress)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "fetchByC_EA";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(companyId), emailAddress };

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

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (emailAddress == null) {
					query.append("emailAddress IS NULL");
				}
				else {
					query.append("emailAddress = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (emailAddress != null) {
					qPos.add(emailAddress);
				}

				List<User> list = q.list();

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
			List<User> list = (List<User>)result;

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

	public List<User> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<User> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<User> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
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

				query.append("FROM com.liferay.portal.model.User ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<User> list = null;

				if (obc == null) {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end);
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
			return (List<User>)result;
		}
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (User user : findByUuid(uuid)) {
			remove(user);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (User user : findByCompanyId(companyId)) {
			remove(user);
		}
	}

	public void removeByContactId(long contactId)
		throws NoSuchUserException, SystemException {
		User user = findByContactId(contactId);

		remove(user);
	}

	public void removeByEmailAddress(String emailAddress)
		throws SystemException {
		for (User user : findByEmailAddress(emailAddress)) {
			remove(user);
		}
	}

	public void removeByOpenId(String openId)
		throws NoSuchUserException, SystemException {
		User user = findByOpenId(openId);

		remove(user);
	}

	public void removeByPortraitId(long portraitId)
		throws NoSuchUserException, SystemException {
		User user = findByPortraitId(portraitId);

		remove(user);
	}

	public void removeByC_U(long companyId, long userId)
		throws NoSuchUserException, SystemException {
		User user = findByC_U(companyId, userId);

		remove(user);
	}

	public void removeByC_DU(long companyId, boolean defaultUser)
		throws NoSuchUserException, SystemException {
		User user = findByC_DU(companyId, defaultUser);

		remove(user);
	}

	public void removeByC_SN(long companyId, String screenName)
		throws NoSuchUserException, SystemException {
		User user = findByC_SN(companyId, screenName);

		remove(user);
	}

	public void removeByC_EA(long companyId, String emailAddress)
		throws NoSuchUserException, SystemException {
		User user = findByC_EA(companyId, emailAddress);

		remove(user);
	}

	public void removeAll() throws SystemException {
		for (User user : findAll()) {
			remove(user);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
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
				query.append("FROM com.liferay.portal.model.User WHERE ");

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
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
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
				query.append("FROM com.liferay.portal.model.User WHERE ");

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

	public int countByContactId(long contactId) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByContactId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(contactId) };

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
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("contactId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(contactId);

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

	public int countByEmailAddress(String emailAddress)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByEmailAddress";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { emailAddress };

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
				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (emailAddress == null) {
					query.append("emailAddress IS NULL");
				}
				else {
					query.append("emailAddress = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (emailAddress != null) {
					qPos.add(emailAddress);
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

	public int countByOpenId(String openId) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByOpenId";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { openId };

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
				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (openId == null) {
					query.append("openId IS NULL");
				}
				else {
					query.append("openId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (openId != null) {
					qPos.add(openId);
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

	public int countByPortraitId(long portraitId) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByPortraitId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(portraitId) };

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
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("portraitId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(portraitId);

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

	public int countByC_U(long companyId, long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByC_U";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(companyId), new Long(userId) };

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
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	public int countByC_DU(long companyId, boolean defaultUser)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByC_DU";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(defaultUser)
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
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("defaultUser = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultUser);

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

	public int countByC_SN(long companyId, String screenName)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByC_SN";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(companyId), screenName };

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
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (screenName == null) {
					query.append("screenName IS NULL");
				}
				else {
					query.append("screenName = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (screenName != null) {
					qPos.add(screenName);
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

	public int countByC_EA(long companyId, String emailAddress)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByC_EA";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(companyId), emailAddress };

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
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (emailAddress == null) {
					query.append("emailAddress IS NULL");
				}
				else {
					query.append("emailAddress = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (emailAddress != null) {
					qPos.add(emailAddress);
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
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portal.model.User");

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

	public List<com.liferay.portal.model.Group> getGroups(long pk)
		throws SystemException {
		return getGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end) throws SystemException {
		return getGroups(pk, start, end, null);
	}

	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_GROUPS;

		String finderClassName = "Users_Groups";

		String finderMethodName = "getGroups";
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
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETGROUPS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append("ORDER BY ");

					sb.append("Group_.name ASC");
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Group_",
					com.liferay.portal.model.impl.GroupImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				List<com.liferay.portal.model.Group> list = (List<com.liferay.portal.model.Group>)QueryUtil.list(q,
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
			return (List<com.liferay.portal.model.Group>)result;
		}
	}

	public int getGroupsSize(long pk) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_GROUPS;

		String finderClassName = "Users_Groups";

		String finderMethodName = "getGroupsSize";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(pk) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETGROUPSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

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

	public boolean containsGroup(long pk, long groupPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_GROUPS;

		String finderClassName = "Users_Groups";

		String finderMethodName = "containsGroups";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(pk), new Long(groupPK) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			try {
				Boolean value = Boolean.valueOf(containsGroup.contains(pk,
							groupPK));

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, value);

				return value.booleanValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
		}
		else {
			return ((Boolean)result).booleanValue();
		}
	}

	public boolean containsGroups(long pk) throws SystemException {
		if (getGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addGroup(long pk, long groupPK) throws SystemException {
		try {
			addGroup.add(pk, groupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void addGroup(long pk, com.liferay.portal.model.Group group)
		throws SystemException {
		try {
			addGroup.add(pk, group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void addGroups(long pk, long[] groupPKs) throws SystemException {
		try {
			for (long groupPK : groupPKs) {
				addGroup.add(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void addGroups(long pk, List<com.liferay.portal.model.Group> groups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Group group : groups) {
				addGroup.add(pk, group.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void clearGroups(long pk) throws SystemException {
		try {
			clearGroups.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void removeGroup(long pk, long groupPK) throws SystemException {
		try {
			removeGroup.remove(pk, groupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws SystemException {
		try {
			removeGroup.remove(pk, group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void removeGroups(long pk, long[] groupPKs)
		throws SystemException {
		try {
			for (long groupPK : groupPKs) {
				removeGroup.remove(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void removeGroups(long pk,
		List<com.liferay.portal.model.Group> groups) throws SystemException {
		try {
			for (com.liferay.portal.model.Group group : groups) {
				removeGroup.remove(pk, group.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void setGroups(long pk, long[] groupPKs) throws SystemException {
		try {
			clearGroups.clear(pk);

			for (long groupPK : groupPKs) {
				addGroup.add(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void setGroups(long pk, List<com.liferay.portal.model.Group> groups)
		throws SystemException {
		try {
			clearGroups.clear(pk);

			for (com.liferay.portal.model.Group group : groups) {
				addGroup.add(pk, group.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public List<com.liferay.portal.model.Organization> getOrganizations(long pk)
		throws SystemException {
		return getOrganizations(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end) throws SystemException {
		return getOrganizations(pk, start, end, null);
	}

	public List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_ORGS;

		String finderClassName = "Users_Orgs";

		String finderMethodName = "getOrganizations";
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
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETORGANIZATIONS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append("ORDER BY ");

					sb.append("Organization_.name ASC");
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Organization_",
					com.liferay.portal.model.impl.OrganizationImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				List<com.liferay.portal.model.Organization> list = (List<com.liferay.portal.model.Organization>)QueryUtil.list(q,
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
			return (List<com.liferay.portal.model.Organization>)result;
		}
	}

	public int getOrganizationsSize(long pk) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_ORGS;

		String finderClassName = "Users_Orgs";

		String finderMethodName = "getOrganizationsSize";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(pk) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETORGANIZATIONSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

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

	public boolean containsOrganization(long pk, long organizationPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_ORGS;

		String finderClassName = "Users_Orgs";

		String finderMethodName = "containsOrganizations";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(pk),
				
				new Long(organizationPK)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			try {
				Boolean value = Boolean.valueOf(containsOrganization.contains(
							pk, organizationPK));

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, value);

				return value.booleanValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
		}
		else {
			return ((Boolean)result).booleanValue();
		}
	}

	public boolean containsOrganizations(long pk) throws SystemException {
		if (getOrganizationsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addOrganization(long pk, long organizationPK)
		throws SystemException {
		try {
			addOrganization.add(pk, organizationPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void addOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws SystemException {
		try {
			addOrganization.add(pk, organization.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void addOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			for (long organizationPK : organizationPKs) {
				addOrganization.add(pk, organizationPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void addOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Organization organization : organizations) {
				addOrganization.add(pk, organization.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void clearOrganizations(long pk) throws SystemException {
		try {
			clearOrganizations.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void removeOrganization(long pk, long organizationPK)
		throws SystemException {
		try {
			removeOrganization.remove(pk, organizationPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void removeOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws SystemException {
		try {
			removeOrganization.remove(pk, organization.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void removeOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			for (long organizationPK : organizationPKs) {
				removeOrganization.remove(pk, organizationPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void removeOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Organization organization : organizations) {
				removeOrganization.remove(pk, organization.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void setOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			clearOrganizations.clear(pk);

			for (long organizationPK : organizationPKs) {
				addOrganization.add(pk, organizationPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public void setOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			clearOrganizations.clear(pk);

			for (com.liferay.portal.model.Organization organization : organizations) {
				addOrganization.add(pk, organization.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Orgs");
		}
	}

	public List<com.liferay.portal.model.Permission> getPermissions(long pk)
		throws SystemException {
		return getPermissions(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Permission> getPermissions(long pk,
		int start, int end) throws SystemException {
		return getPermissions(pk, start, end, null);
	}

	public List<com.liferay.portal.model.Permission> getPermissions(long pk,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_PERMISSIONS;

		String finderClassName = "Users_Permissions";

		String finderMethodName = "getPermissions";
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
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETPERMISSIONS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Permission_",
					com.liferay.portal.model.impl.PermissionImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				List<com.liferay.portal.model.Permission> list = (List<com.liferay.portal.model.Permission>)QueryUtil.list(q,
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
			return (List<com.liferay.portal.model.Permission>)result;
		}
	}

	public int getPermissionsSize(long pk) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_PERMISSIONS;

		String finderClassName = "Users_Permissions";

		String finderMethodName = "getPermissionsSize";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(pk) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETPERMISSIONSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

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

	public boolean containsPermission(long pk, long permissionPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_PERMISSIONS;

		String finderClassName = "Users_Permissions";

		String finderMethodName = "containsPermissions";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(pk), new Long(permissionPK) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			try {
				Boolean value = Boolean.valueOf(containsPermission.contains(
							pk, permissionPK));

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, value);

				return value.booleanValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
		}
		else {
			return ((Boolean)result).booleanValue();
		}
	}

	public boolean containsPermissions(long pk) throws SystemException {
		if (getPermissionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addPermission(long pk, long permissionPK)
		throws SystemException {
		try {
			addPermission.add(pk, permissionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void addPermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws SystemException {
		try {
			addPermission.add(pk, permission.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void addPermissions(long pk, long[] permissionPKs)
		throws SystemException {
		try {
			for (long permissionPK : permissionPKs) {
				addPermission.add(pk, permissionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void addPermissions(long pk,
		List<com.liferay.portal.model.Permission> permissions)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Permission permission : permissions) {
				addPermission.add(pk, permission.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void clearPermissions(long pk) throws SystemException {
		try {
			clearPermissions.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void removePermission(long pk, long permissionPK)
		throws SystemException {
		try {
			removePermission.remove(pk, permissionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void removePermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws SystemException {
		try {
			removePermission.remove(pk, permission.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void removePermissions(long pk, long[] permissionPKs)
		throws SystemException {
		try {
			for (long permissionPK : permissionPKs) {
				removePermission.remove(pk, permissionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void removePermissions(long pk,
		List<com.liferay.portal.model.Permission> permissions)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Permission permission : permissions) {
				removePermission.remove(pk, permission.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void setPermissions(long pk, long[] permissionPKs)
		throws SystemException {
		try {
			clearPermissions.clear(pk);

			for (long permissionPK : permissionPKs) {
				addPermission.add(pk, permissionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public void setPermissions(long pk,
		List<com.liferay.portal.model.Permission> permissions)
		throws SystemException {
		try {
			clearPermissions.clear(pk);

			for (com.liferay.portal.model.Permission permission : permissions) {
				addPermission.add(pk, permission.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Permissions");
		}
	}

	public List<com.liferay.portal.model.Role> getRoles(long pk)
		throws SystemException {
		return getRoles(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end) throws SystemException {
		return getRoles(pk, start, end, null);
	}

	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_ROLES;

		String finderClassName = "Users_Roles";

		String finderMethodName = "getRoles";
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
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETROLES);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append("ORDER BY ");

					sb.append("Role_.name ASC");
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Role_",
					com.liferay.portal.model.impl.RoleImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				List<com.liferay.portal.model.Role> list = (List<com.liferay.portal.model.Role>)QueryUtil.list(q,
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
			return (List<com.liferay.portal.model.Role>)result;
		}
	}

	public int getRolesSize(long pk) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_ROLES;

		String finderClassName = "Users_Roles";

		String finderMethodName = "getRolesSize";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(pk) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETROLESSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

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

	public boolean containsRole(long pk, long rolePK) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_ROLES;

		String finderClassName = "Users_Roles";

		String finderMethodName = "containsRoles";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(pk), new Long(rolePK) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			try {
				Boolean value = Boolean.valueOf(containsRole.contains(pk, rolePK));

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, value);

				return value.booleanValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
		}
		else {
			return ((Boolean)result).booleanValue();
		}
	}

	public boolean containsRoles(long pk) throws SystemException {
		if (getRolesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addRole(long pk, long rolePK) throws SystemException {
		try {
			addRole.add(pk, rolePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void addRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		try {
			addRole.add(pk, role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void addRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			for (long rolePK : rolePKs) {
				addRole.add(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void addRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Role role : roles) {
				addRole.add(pk, role.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void clearRoles(long pk) throws SystemException {
		try {
			clearRoles.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void removeRole(long pk, long rolePK) throws SystemException {
		try {
			removeRole.remove(pk, rolePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void removeRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		try {
			removeRole.remove(pk, role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void removeRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			for (long rolePK : rolePKs) {
				removeRole.remove(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void removeRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Role role : roles) {
				removeRole.remove(pk, role.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void setRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			clearRoles.clear(pk);

			for (long rolePK : rolePKs) {
				addRole.add(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public void setRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			clearRoles.clear(pk);

			for (com.liferay.portal.model.Role role : roles) {
				addRole.add(pk, role.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Roles");
		}
	}

	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk)
		throws SystemException {
		return getUserGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end) throws SystemException {
		return getUserGroups(pk, start, end, null);
	}

	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_USERGROUPS;

		String finderClassName = "Users_UserGroups";

		String finderMethodName = "getUserGroups";
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
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETUSERGROUPS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append("ORDER BY ");

					sb.append("UserGroup.name ASC");
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("UserGroup",
					com.liferay.portal.model.impl.UserGroupImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				List<com.liferay.portal.model.UserGroup> list = (List<com.liferay.portal.model.UserGroup>)QueryUtil.list(q,
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
			return (List<com.liferay.portal.model.UserGroup>)result;
		}
	}

	public int getUserGroupsSize(long pk) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_USERGROUPS;

		String finderClassName = "Users_UserGroups";

		String finderMethodName = "getUserGroupsSize";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(pk) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETUSERGROUPSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

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

	public boolean containsUserGroup(long pk, long userGroupPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_USERGROUPS;

		String finderClassName = "Users_UserGroups";

		String finderMethodName = "containsUserGroups";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(pk), new Long(userGroupPK) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			try {
				Boolean value = Boolean.valueOf(containsUserGroup.contains(pk,
							userGroupPK));

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, value);

				return value.booleanValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
		}
		else {
			return ((Boolean)result).booleanValue();
		}
	}

	public boolean containsUserGroups(long pk) throws SystemException {
		if (getUserGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addUserGroup(long pk, long userGroupPK)
		throws SystemException {
		try {
			addUserGroup.add(pk, userGroupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		try {
			addUserGroup.add(pk, userGroup.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void addUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			for (long userGroupPK : userGroupPKs) {
				addUserGroup.add(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void addUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				addUserGroup.add(pk, userGroup.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void clearUserGroups(long pk) throws SystemException {
		try {
			clearUserGroups.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void removeUserGroup(long pk, long userGroupPK)
		throws SystemException {
		try {
			removeUserGroup.remove(pk, userGroupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		try {
			removeUserGroup.remove(pk, userGroup.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void removeUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			for (long userGroupPK : userGroupPKs) {
				removeUserGroup.remove(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void removeUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				removeUserGroup.remove(pk, userGroup.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void setUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			clearUserGroups.clear(pk);

			for (long userGroupPK : userGroupPKs) {
				addUserGroup.add(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void setUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			clearUserGroups.clear(pk);

			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				addUserGroup.add(pk, userGroup.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_UserGroups");
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.User")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<User>> listenersList = new ArrayList<ModelListener<User>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<User>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsGroup = new ContainsGroup(this);

		addGroup = new AddGroup(this);
		clearGroups = new ClearGroups(this);
		removeGroup = new RemoveGroup(this);

		containsOrganization = new ContainsOrganization(this);

		addOrganization = new AddOrganization(this);
		clearOrganizations = new ClearOrganizations(this);
		removeOrganization = new RemoveOrganization(this);

		containsPermission = new ContainsPermission(this);

		addPermission = new AddPermission(this);
		clearPermissions = new ClearPermissions(this);
		removePermission = new RemovePermission(this);

		containsRole = new ContainsRole(this);

		addRole = new AddRole(this);
		clearRoles = new ClearRoles(this);
		removeRole = new RemoveRole(this);

		containsUserGroup = new ContainsUserGroup(this);

		addUserGroup = new AddUserGroup(this);
		clearUserGroups = new ClearUserGroups(this);
		removeUserGroup = new RemoveUserGroup(this);
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence.impl")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence.impl")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence.impl")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence.impl")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence.impl")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence.impl")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence.impl")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence.impl")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence.impl")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence.impl")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence.impl")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence.impl")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence.impl")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence.impl")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence.impl")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence.impl")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence.impl")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence.impl")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence.impl")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence.impl")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence.impl")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence.impl")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	@BeanReference(name = "com.liferay.portlet.announcements.service.persistence.AnnouncementsDeliveryPersistence.impl")
	protected com.liferay.portlet.announcements.service.persistence.AnnouncementsDeliveryPersistence announcementsDeliveryPersistence;
	@BeanReference(name = "com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence.impl")
	protected com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence blogsStatsUserPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence.impl")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence dlFileRankPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBBanPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBBanPersistence mbBanPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPersistence mbMessageFlagPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence shoppingCartPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence.impl")
	protected com.liferay.portlet.social.service.persistence.SocialActivityPersistence socialActivityPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialRequestPersistence.impl")
	protected com.liferay.portlet.social.service.persistence.SocialRequestPersistence socialRequestPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsAssetPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsAssetPersistence tagsAssetPersistence;
	protected ContainsGroup containsGroup;
	protected AddGroup addGroup;
	protected ClearGroups clearGroups;
	protected RemoveGroup removeGroup;
	protected ContainsOrganization containsOrganization;
	protected AddOrganization addOrganization;
	protected ClearOrganizations clearOrganizations;
	protected RemoveOrganization removeOrganization;
	protected ContainsPermission containsPermission;
	protected AddPermission addPermission;
	protected ClearPermissions clearPermissions;
	protected RemovePermission removePermission;
	protected ContainsRole containsRole;
	protected AddRole addRole;
	protected ClearRoles clearRoles;
	protected RemoveRole removeRole;
	protected ContainsUserGroup containsUserGroup;
	protected AddUserGroup addUserGroup;
	protected ClearUserGroups clearUserGroups;
	protected RemoveUserGroup removeUserGroup;

	protected class ContainsGroup {
		protected ContainsGroup(UserPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSGROUP,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long userId, long groupId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(groupId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddGroup {
		protected AddGroup(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Groups (userId, groupId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long userId, long groupId) throws SystemException {
			if (!_persistenceImpl.containsGroup.contains(userId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeAddAssociation(groupId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(groupId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterAddAssociation(groupId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearGroups {
		protected ClearGroups(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Groups WHERE userId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

			List<com.liferay.portal.model.Group> groups = null;

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				groups = getGroups(userId);

				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onBeforeRemoveAssociation(group.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onBeforeRemoveAssociation(group.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveGroup {
		protected RemoveGroup(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Groups WHERE userId = ? AND groupId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long userId, long groupId)
			throws SystemException {
			if (_persistenceImpl.containsGroup.contains(userId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeRemoveAssociation(groupId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(groupId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterRemoveAssociation(groupId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ContainsOrganization {
		protected ContainsOrganization(UserPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSORGANIZATION,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long userId, long organizationId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(organizationId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddOrganization {
		protected AddOrganization(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Orgs (userId, organizationId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long userId, long organizationId)
			throws SystemException {
			if (!_persistenceImpl.containsOrganization.contains(userId,
						organizationId)) {
				ModelListener<com.liferay.portal.model.Organization>[] organizationListeners =
					organizationPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onBeforeAddAssociation(organizationId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(organizationId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onAfterAddAssociation(organizationId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearOrganizations {
		protected ClearOrganizations(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Orgs WHERE userId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.Organization>[] organizationListeners =
				organizationPersistence.getListeners();

			List<com.liferay.portal.model.Organization> organizations = null;

			if ((listeners.length > 0) || (organizationListeners.length > 0)) {
				organizations = getOrganizations(userId);

				for (com.liferay.portal.model.Organization organization : organizations) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.Organization.class.getName(),
							organization.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
						listener.onBeforeRemoveAssociation(organization.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (organizationListeners.length > 0)) {
				for (com.liferay.portal.model.Organization organization : organizations) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.Organization.class.getName(),
							organization.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
						listener.onBeforeRemoveAssociation(organization.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveOrganization {
		protected RemoveOrganization(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Orgs WHERE userId = ? AND organizationId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long userId, long organizationId)
			throws SystemException {
			if (_persistenceImpl.containsOrganization.contains(userId,
						organizationId)) {
				ModelListener<com.liferay.portal.model.Organization>[] organizationListeners =
					organizationPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onBeforeRemoveAssociation(organizationId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(organizationId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onAfterRemoveAssociation(organizationId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ContainsPermission {
		protected ContainsPermission(UserPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSPERMISSION,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long userId, long permissionId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(permissionId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddPermission {
		protected AddPermission(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Permissions (userId, permissionId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long userId, long permissionId)
			throws SystemException {
			if (!_persistenceImpl.containsPermission.contains(userId,
						permissionId)) {
				ModelListener<com.liferay.portal.model.Permission>[] permissionListeners =
					permissionPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onBeforeAddAssociation(permissionId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(permissionId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onAfterAddAssociation(permissionId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearPermissions {
		protected ClearPermissions(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Permissions WHERE userId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.Permission>[] permissionListeners =
				permissionPersistence.getListeners();

			List<com.liferay.portal.model.Permission> permissions = null;

			if ((listeners.length > 0) || (permissionListeners.length > 0)) {
				permissions = getPermissions(userId);

				for (com.liferay.portal.model.Permission permission : permissions) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.Permission.class.getName(),
							permission.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
						listener.onBeforeRemoveAssociation(permission.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (permissionListeners.length > 0)) {
				for (com.liferay.portal.model.Permission permission : permissions) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.Permission.class.getName(),
							permission.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
						listener.onBeforeRemoveAssociation(permission.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemovePermission {
		protected RemovePermission(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Permissions WHERE userId = ? AND permissionId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long userId, long permissionId)
			throws SystemException {
			if (_persistenceImpl.containsPermission.contains(userId,
						permissionId)) {
				ModelListener<com.liferay.portal.model.Permission>[] permissionListeners =
					permissionPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onBeforeRemoveAssociation(permissionId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(permissionId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onAfterRemoveAssociation(permissionId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ContainsRole {
		protected ContainsRole(UserPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSROLE,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long userId, long roleId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(roleId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddRole {
		protected AddRole(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Roles (userId, roleId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long userId, long roleId) throws SystemException {
			if (!_persistenceImpl.containsRole.contains(userId, roleId)) {
				ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onBeforeAddAssociation(roleId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(roleId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onAfterAddAssociation(roleId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearRoles {
		protected ClearRoles(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Roles WHERE userId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

			List<com.liferay.portal.model.Role> roles = null;

			if ((listeners.length > 0) || (roleListeners.length > 0)) {
				roles = getRoles(userId);

				for (com.liferay.portal.model.Role role : roles) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.Role.class.getName(),
							role.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
						listener.onBeforeRemoveAssociation(role.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (roleListeners.length > 0)) {
				for (com.liferay.portal.model.Role role : roles) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.Role.class.getName(),
							role.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
						listener.onBeforeRemoveAssociation(role.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveRole {
		protected RemoveRole(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Roles WHERE userId = ? AND roleId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long userId, long roleId)
			throws SystemException {
			if (_persistenceImpl.containsRole.contains(userId, roleId)) {
				ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onBeforeRemoveAssociation(roleId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(roleId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onAfterRemoveAssociation(roleId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ContainsUserGroup {
		protected ContainsUserGroup(UserPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSUSERGROUP,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long userId, long userGroupId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(userGroupId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddUserGroup {
		protected AddUserGroup(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_UserGroups (userId, userGroupId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long userId, long userGroupId)
			throws SystemException {
			if (!_persistenceImpl.containsUserGroup.contains(userId, userGroupId)) {
				ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
					userGroupPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onBeforeAddAssociation(userGroupId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(userGroupId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onAfterAddAssociation(userGroupId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearUserGroups {
		protected ClearUserGroups(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_UserGroups WHERE userId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
				userGroupPersistence.getListeners();

			List<com.liferay.portal.model.UserGroup> userGroups = null;

			if ((listeners.length > 0) || (userGroupListeners.length > 0)) {
				userGroups = getUserGroups(userId);

				for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.UserGroup.class.getName(),
							userGroup.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
						listener.onBeforeRemoveAssociation(userGroup.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (userGroupListeners.length > 0)) {
				for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.UserGroup.class.getName(),
							userGroup.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
						listener.onBeforeRemoveAssociation(userGroup.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveUserGroup {
		protected RemoveUserGroup(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_UserGroups WHERE userId = ? AND userGroupId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long userId, long userGroupId)
			throws SystemException {
			if (_persistenceImpl.containsUserGroup.contains(userId, userGroupId)) {
				ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
					userGroupPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onBeforeRemoveAssociation(userGroupId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(userGroupId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onAfterRemoveAssociation(userGroupId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_GETGROUPS = "SELECT {Group_.*} FROM Group_ INNER JOIN Users_Groups ON (Users_Groups.groupId = Group_.groupId) WHERE (Users_Groups.userId = ?)";
	private static final String _SQL_GETGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE userId = ?";
	private static final String _SQL_CONTAINSGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE userId = ? AND groupId = ?";
	private static final String _SQL_GETORGANIZATIONS = "SELECT {Organization_.*} FROM Organization_ INNER JOIN Users_Orgs ON (Users_Orgs.organizationId = Organization_.organizationId) WHERE (Users_Orgs.userId = ?)";
	private static final String _SQL_GETORGANIZATIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Orgs WHERE userId = ?";
	private static final String _SQL_CONTAINSORGANIZATION = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Orgs WHERE userId = ? AND organizationId = ?";
	private static final String _SQL_GETPERMISSIONS = "SELECT {Permission_.*} FROM Permission_ INNER JOIN Users_Permissions ON (Users_Permissions.permissionId = Permission_.permissionId) WHERE (Users_Permissions.userId = ?)";
	private static final String _SQL_GETPERMISSIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Permissions WHERE userId = ?";
	private static final String _SQL_CONTAINSPERMISSION = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Permissions WHERE userId = ? AND permissionId = ?";
	private static final String _SQL_GETROLES = "SELECT {Role_.*} FROM Role_ INNER JOIN Users_Roles ON (Users_Roles.roleId = Role_.roleId) WHERE (Users_Roles.userId = ?)";
	private static final String _SQL_GETROLESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Roles WHERE userId = ?";
	private static final String _SQL_CONTAINSROLE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Roles WHERE userId = ? AND roleId = ?";
	private static final String _SQL_GETUSERGROUPS = "SELECT {UserGroup.*} FROM UserGroup INNER JOIN Users_UserGroups ON (Users_UserGroups.userGroupId = UserGroup.userGroupId) WHERE (Users_UserGroups.userId = ?)";
	private static final String _SQL_GETUSERGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_UserGroups WHERE userId = ?";
	private static final String _SQL_CONTAINSUSERGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_UserGroups WHERE userId = ? AND userGroupId = ?";
	private static Log _log = LogFactoryUtil.getLog(UserPersistenceImpl.class);
}