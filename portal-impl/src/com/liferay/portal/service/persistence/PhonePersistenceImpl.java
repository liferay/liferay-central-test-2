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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchPhoneException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.impl.PhoneImpl;
import com.liferay.portal.model.impl.PhoneModelImpl;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;

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
 * <a href="PhonePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PhonePersistenceImpl extends BasePersistence
	implements PhonePersistence {
	public Phone create(long phoneId) {
		Phone phone = new PhoneImpl();

		phone.setNew(true);
		phone.setPrimaryKey(phoneId);

		return phone;
	}

	public Phone remove(long phoneId)
		throws NoSuchPhoneException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Phone phone = (Phone)session.get(PhoneImpl.class, new Long(phoneId));

			if (phone == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Phone exists with the primary key " +
						phoneId);
				}

				throw new NoSuchPhoneException(
					"No Phone exists with the primary key " + phoneId);
			}

			return remove(phone);
		}
		catch (NoSuchPhoneException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Phone remove(Phone phone) throws SystemException {
		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(phone);
			}
		}

		phone = removeImpl(phone);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(phone);
			}
		}

		return phone;
	}

	protected Phone removeImpl(Phone phone) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(phone);

			session.flush();

			return phone;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(Phone.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(Phone phone, boolean merge)</code>.
	 */
	public Phone update(Phone phone) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(Phone phone) method. Use update(Phone phone, boolean merge) instead.");
		}

		return update(phone, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        phone the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when phone is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public Phone update(Phone phone, boolean merge) throws SystemException {
		boolean isNew = phone.isNew();

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(phone);
				}
				else {
					listener.onBeforeUpdate(phone);
				}
			}
		}

		phone = updateImpl(phone, merge);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(phone);
				}
				else {
					listener.onAfterUpdate(phone);
				}
			}
		}

		return phone;
	}

	public Phone updateImpl(com.liferay.portal.model.Phone phone, boolean merge)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(phone);
			}
			else {
				if (phone.isNew()) {
					session.save(phone);
				}
			}

			session.flush();

			phone.setNew(false);

			return phone;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(Phone.class.getName());
		}
	}

	public Phone findByPrimaryKey(long phoneId)
		throws NoSuchPhoneException, SystemException {
		Phone phone = fetchByPrimaryKey(phoneId);

		if (phone == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Phone exists with the primary key " + phoneId);
			}

			throw new NoSuchPhoneException(
				"No Phone exists with the primary key " + phoneId);
		}

		return phone;
	}

	public Phone fetchByPrimaryKey(long phoneId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Phone)session.get(PhoneImpl.class, new Long(phoneId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Phone> findByCompanyId(long companyId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PhoneModelImpl.CACHE_ENABLED;
		String finderClassName = Phone.class.getName();
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

				query.append("FROM com.liferay.portal.model.Phone WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<Phone> list = q.list();

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
			return (List<Phone>)result;
		}
	}

	public List<Phone> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<Phone> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = PhoneModelImpl.CACHE_ENABLED;
		String finderClassName = Phone.class.getName();
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

				query.append("FROM com.liferay.portal.model.Phone WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<Phone> list = (List<Phone>)QueryUtil.list(q, getDialect(),
						start, end);

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
			return (List<Phone>)result;
		}
	}

	public Phone findByCompanyId_First(long companyId, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		List<Phone> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No Phone exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPhoneException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Phone findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		int count = countByCompanyId(companyId);

		List<Phone> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No Phone exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPhoneException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Phone[] findByCompanyId_PrevAndNext(long phoneId, long companyId,
		OrderByComparator obc) throws NoSuchPhoneException, SystemException {
		Phone phone = findByPrimaryKey(phoneId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append("FROM com.liferay.portal.model.Phone WHERE ");

			query.append("companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, phone);

			Phone[] array = new PhoneImpl[3];

			array[0] = (Phone)objArray[0];
			array[1] = (Phone)objArray[1];
			array[2] = (Phone)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Phone> findByUserId(long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = PhoneModelImpl.CACHE_ENABLED;
		String finderClassName = Phone.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

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

				query.append("FROM com.liferay.portal.model.Phone WHERE ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<Phone> list = q.list();

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
			return (List<Phone>)result;
		}
	}

	public List<Phone> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<Phone> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = PhoneModelImpl.CACHE_ENABLED;
		String finderClassName = Phone.class.getName();
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
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("FROM com.liferay.portal.model.Phone WHERE ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<Phone> list = (List<Phone>)QueryUtil.list(q, getDialect(),
						start, end);

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
			return (List<Phone>)result;
		}
	}

	public Phone findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		List<Phone> list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No Phone exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPhoneException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Phone findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		int count = countByUserId(userId);

		List<Phone> list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No Phone exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPhoneException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Phone[] findByUserId_PrevAndNext(long phoneId, long userId,
		OrderByComparator obc) throws NoSuchPhoneException, SystemException {
		Phone phone = findByPrimaryKey(phoneId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append("FROM com.liferay.portal.model.Phone WHERE ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, phone);

			Phone[] array = new PhoneImpl[3];

			array[0] = (Phone)objArray[0];
			array[1] = (Phone)objArray[1];
			array[2] = (Phone)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Phone> findByC_C(long companyId, long classNameId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PhoneModelImpl.CACHE_ENABLED;
		String finderClassName = Phone.class.getName();
		String finderMethodName = "findByC_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId)
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

				query.append("FROM com.liferay.portal.model.Phone WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				List<Phone> list = q.list();

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
			return (List<Phone>)result;
		}
	}

	public List<Phone> findByC_C(long companyId, long classNameId, int start,
		int end) throws SystemException {
		return findByC_C(companyId, classNameId, start, end, null);
	}

	public List<Phone> findByC_C(long companyId, long classNameId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = PhoneModelImpl.CACHE_ENABLED;
		String finderClassName = Phone.class.getName();
		String finderMethodName = "findByC_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId),
				
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

				query.append("FROM com.liferay.portal.model.Phone WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				List<Phone> list = (List<Phone>)QueryUtil.list(q, getDialect(),
						start, end);

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
			return (List<Phone>)result;
		}
	}

	public Phone findByC_C_First(long companyId, long classNameId,
		OrderByComparator obc) throws NoSuchPhoneException, SystemException {
		List<Phone> list = findByC_C(companyId, classNameId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No Phone exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPhoneException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Phone findByC_C_Last(long companyId, long classNameId,
		OrderByComparator obc) throws NoSuchPhoneException, SystemException {
		int count = countByC_C(companyId, classNameId);

		List<Phone> list = findByC_C(companyId, classNameId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No Phone exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPhoneException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Phone[] findByC_C_PrevAndNext(long phoneId, long companyId,
		long classNameId, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		Phone phone = findByPrimaryKey(phoneId);

		int count = countByC_C(companyId, classNameId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append("FROM com.liferay.portal.model.Phone WHERE ");

			query.append("companyId = ?");

			query.append(" AND ");

			query.append("classNameId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(classNameId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, phone);

			Phone[] array = new PhoneImpl[3];

			array[0] = (Phone)objArray[0];
			array[1] = (Phone)objArray[1];
			array[2] = (Phone)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Phone> findByC_C_C(long companyId, long classNameId,
		long classPK) throws SystemException {
		boolean finderClassNameCacheEnabled = PhoneModelImpl.CACHE_ENABLED;
		String finderClassName = Phone.class.getName();
		String finderMethodName = "findByC_C_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK)
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

				query.append("FROM com.liferay.portal.model.Phone WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<Phone> list = q.list();

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
			return (List<Phone>)result;
		}
	}

	public List<Phone> findByC_C_C(long companyId, long classNameId,
		long classPK, int start, int end) throws SystemException {
		return findByC_C_C(companyId, classNameId, classPK, start, end, null);
	}

	public List<Phone> findByC_C_C(long companyId, long classNameId,
		long classPK, int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PhoneModelImpl.CACHE_ENABLED;
		String finderClassName = Phone.class.getName();
		String finderMethodName = "findByC_C_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK),
				
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

				query.append("FROM com.liferay.portal.model.Phone WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<Phone> list = (List<Phone>)QueryUtil.list(q, getDialect(),
						start, end);

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
			return (List<Phone>)result;
		}
	}

	public Phone findByC_C_C_First(long companyId, long classNameId,
		long classPK, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		List<Phone> list = findByC_C_C(companyId, classNameId, classPK, 0, 1,
				obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No Phone exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPhoneException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Phone findByC_C_C_Last(long companyId, long classNameId,
		long classPK, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		int count = countByC_C_C(companyId, classNameId, classPK);

		List<Phone> list = findByC_C_C(companyId, classNameId, classPK,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No Phone exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPhoneException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Phone[] findByC_C_C_PrevAndNext(long phoneId, long companyId,
		long classNameId, long classPK, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		Phone phone = findByPrimaryKey(phoneId);

		int count = countByC_C_C(companyId, classNameId, classPK);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append("FROM com.liferay.portal.model.Phone WHERE ");

			query.append("companyId = ?");

			query.append(" AND ");

			query.append("classNameId = ?");

			query.append(" AND ");

			query.append("classPK = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(classNameId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, phone);

			Phone[] array = new PhoneImpl[3];

			array[0] = (Phone)objArray[0];
			array[1] = (Phone)objArray[1];
			array[2] = (Phone)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Phone> findByC_C_C_P(long companyId, long classNameId,
		long classPK, boolean primary) throws SystemException {
		boolean finderClassNameCacheEnabled = PhoneModelImpl.CACHE_ENABLED;
		String finderClassName = Phone.class.getName();
		String finderMethodName = "findByC_C_C_P";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK),
				Boolean.valueOf(primary)
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

				query.append("FROM com.liferay.portal.model.Phone WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("primary_ = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(primary);

				List<Phone> list = q.list();

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
			return (List<Phone>)result;
		}
	}

	public List<Phone> findByC_C_C_P(long companyId, long classNameId,
		long classPK, boolean primary, int start, int end)
		throws SystemException {
		return findByC_C_C_P(companyId, classNameId, classPK, primary, start,
			end, null);
	}

	public List<Phone> findByC_C_C_P(long companyId, long classNameId,
		long classPK, boolean primary, int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PhoneModelImpl.CACHE_ENABLED;
		String finderClassName = Phone.class.getName();
		String finderMethodName = "findByC_C_C_P";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK),
				Boolean.valueOf(primary),
				
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

				query.append("FROM com.liferay.portal.model.Phone WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("primary_ = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(primary);

				List<Phone> list = (List<Phone>)QueryUtil.list(q, getDialect(),
						start, end);

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
			return (List<Phone>)result;
		}
	}

	public Phone findByC_C_C_P_First(long companyId, long classNameId,
		long classPK, boolean primary, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		List<Phone> list = findByC_C_C_P(companyId, classNameId, classPK,
				primary, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No Phone exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(", ");
			msg.append("primary=" + primary);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPhoneException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Phone findByC_C_C_P_Last(long companyId, long classNameId,
		long classPK, boolean primary, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		int count = countByC_C_C_P(companyId, classNameId, classPK, primary);

		List<Phone> list = findByC_C_C_P(companyId, classNameId, classPK,
				primary, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No Phone exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(", ");
			msg.append("primary=" + primary);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPhoneException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Phone[] findByC_C_C_P_PrevAndNext(long phoneId, long companyId,
		long classNameId, long classPK, boolean primary, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		Phone phone = findByPrimaryKey(phoneId);

		int count = countByC_C_C_P(companyId, classNameId, classPK, primary);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append("FROM com.liferay.portal.model.Phone WHERE ");

			query.append("companyId = ?");

			query.append(" AND ");

			query.append("classNameId = ?");

			query.append(" AND ");

			query.append("classPK = ?");

			query.append(" AND ");

			query.append("primary_ = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(classNameId);

			qPos.add(classPK);

			qPos.add(primary);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, phone);

			Phone[] array = new PhoneImpl[3];

			array[0] = (Phone)objArray[0];
			array[1] = (Phone)objArray[1];
			array[2] = (Phone)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Phone> findWithDynamicQuery(
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

	public List<Phone> findWithDynamicQuery(
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

	public List<Phone> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Phone> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Phone> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PhoneModelImpl.CACHE_ENABLED;
		String finderClassName = Phone.class.getName();
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

				query.append("FROM com.liferay.portal.model.Phone ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				List<Phone> list = (List<Phone>)QueryUtil.list(q, getDialect(),
						start, end);

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
			return (List<Phone>)result;
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (Phone phone : findByCompanyId(companyId)) {
			remove(phone);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (Phone phone : findByUserId(userId)) {
			remove(phone);
		}
	}

	public void removeByC_C(long companyId, long classNameId)
		throws SystemException {
		for (Phone phone : findByC_C(companyId, classNameId)) {
			remove(phone);
		}
	}

	public void removeByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		for (Phone phone : findByC_C_C(companyId, classNameId, classPK)) {
			remove(phone);
		}
	}

	public void removeByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary) throws SystemException {
		for (Phone phone : findByC_C_C_P(companyId, classNameId, classPK,
				primary)) {
			remove(phone);
		}
	}

	public void removeAll() throws SystemException {
		for (Phone phone : findAll()) {
			remove(phone);
		}
	}

	public int countByCompanyId(long companyId) throws SystemException {
		boolean finderClassNameCacheEnabled = PhoneModelImpl.CACHE_ENABLED;
		String finderClassName = Phone.class.getName();
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
				query.append("FROM com.liferay.portal.model.Phone WHERE ");

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

	public int countByUserId(long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = PhoneModelImpl.CACHE_ENABLED;
		String finderClassName = Phone.class.getName();
		String finderMethodName = "countByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

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
				query.append("FROM com.liferay.portal.model.Phone WHERE ");

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

	public int countByC_C(long companyId, long classNameId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PhoneModelImpl.CACHE_ENABLED;
		String finderClassName = Phone.class.getName();
		String finderMethodName = "countByC_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId)
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
				query.append("FROM com.liferay.portal.model.Phone WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

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

	public int countByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PhoneModelImpl.CACHE_ENABLED;
		String finderClassName = Phone.class.getName();
		String finderMethodName = "countByC_C_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK)
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
				query.append("FROM com.liferay.portal.model.Phone WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	public int countByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary) throws SystemException {
		boolean finderClassNameCacheEnabled = PhoneModelImpl.CACHE_ENABLED;
		String finderClassName = Phone.class.getName();
		String finderMethodName = "countByC_C_C_P";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK),
				Boolean.valueOf(primary)
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
				query.append("FROM com.liferay.portal.model.Phone WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("primary_ = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(primary);

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
		boolean finderClassNameCacheEnabled = PhoneModelImpl.CACHE_ENABLED;
		String finderClassName = Phone.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portal.model.Phone");

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
						"value.object.listener.com.liferay.portal.model.Phone")));

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

	private static Log _log = LogFactory.getLog(PhonePersistenceImpl.class);
	private ModelListener[] _listeners;
}