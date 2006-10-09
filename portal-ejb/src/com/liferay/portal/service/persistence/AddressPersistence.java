/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.NoSuchAddressException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Address;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="AddressPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AddressPersistence extends BasePersistence {
	public Address create(String addressId) {
		Address address = new Address();
		address.setNew(true);
		address.setPrimaryKey(addressId);

		return address;
	}

	public Address remove(String addressId)
		throws NoSuchAddressException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Address address = (Address)session.get(Address.class, addressId);

			if (address == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Address exists with the primary key " +
						addressId.toString());
				}

				throw new NoSuchAddressException(
					"No Address exists with the primary key " +
					addressId.toString());
			}

			return remove(address);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Address remove(Address address) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(address);
			session.flush();

			return address;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Address update(
		com.liferay.portal.model.Address address) throws SystemException {
		return update(address, false);
	}

	public com.liferay.portal.model.Address update(
		com.liferay.portal.model.Address address, boolean saveOrUpdate)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(address);
			}
			else {
				if (address.isNew()) {
					session.save(address);
				}
			}

			session.flush();
			address.setNew(false);

			return address;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Address findByPrimaryKey(String addressId)
		throws NoSuchAddressException, SystemException {
		Address address = fetchByPrimaryKey(addressId);

		if (address == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Address exists with the primary key " +
					addressId.toString());
			}

			throw new NoSuchAddressException(
				"No Address exists with the primary key " +
				addressId.toString());
		}

		return address;
	}

	public Address fetchByPrimaryKey(String addressId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Address)session.get(Address.class, addressId);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(String companyId, int begin, int end)
		throws SystemException {
		return findByCompanyId(companyId, begin, end, null);
	}

	public List findByCompanyId(String companyId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Address findByCompanyId_First(String companyId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Address exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchAddressException(msg);
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address findByCompanyId_Last(String companyId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Address exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchAddressException(msg);
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address[] findByCompanyId_PrevAndNext(String addressId,
		String companyId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		Address address = findByPrimaryKey(addressId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, address);
			Address[] array = new Address[3];
			array[0] = (Address)objArray[0];
			array[1] = (Address)objArray[1];
			array[2] = (Address)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserId(String userId, int begin, int end)
		throws SystemException {
		return findByUserId(userId, begin, end, null);
	}

	public List findByUserId(String userId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Address findByUserId_First(String userId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Address exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchAddressException(msg);
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address findByUserId_Last(String userId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Address exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchAddressException(msg);
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address[] findByUserId_PrevAndNext(String addressId, String userId,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
		Address address = findByPrimaryKey(addressId);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, address);
			Address[] array = new Address[3];
			array[0] = (Address)objArray[0];
			array[1] = (Address)objArray[1];
			array[2] = (Address)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C(String companyId, String className)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C(String companyId, String className, int begin, int end)
		throws SystemException {
		return findByC_C(companyId, className, begin, end, null);
	}

	public List findByC_C(String companyId, String className, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Address findByC_C_First(String companyId, String className,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
		List list = findByC_C(companyId, className, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Address exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "className=";
			msg += className;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchAddressException(msg);
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address findByC_C_Last(String companyId, String className,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
		int count = countByC_C(companyId, className);
		List list = findByC_C(companyId, className, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Address exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "className=";
			msg += className;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchAddressException(msg);
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address[] findByC_C_PrevAndNext(String addressId, String companyId,
		String className, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		Address address = findByPrimaryKey(addressId);
		int count = countByC_C(companyId, className);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, address);
			Address[] array = new Address[3];
			array[0] = (Address)objArray[0];
			array[1] = (Address)objArray[1];
			array[2] = (Address)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C_C(String companyId, String className, String classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK IS NULL");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
			}

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C_C(String companyId, String className, String classPK,
		int begin, int end) throws SystemException {
		return findByC_C_C(companyId, className, classPK, begin, end, null);
	}

	public List findByC_C_C(String companyId, String className, String classPK,
		int begin, int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK IS NULL");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Address findByC_C_C_First(String companyId, String className,
		String classPK, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		List list = findByC_C_C(companyId, className, classPK, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Address exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "className=";
			msg += className;
			msg += ", ";
			msg += "classPK=";
			msg += classPK;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchAddressException(msg);
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address findByC_C_C_Last(String companyId, String className,
		String classPK, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		int count = countByC_C_C(companyId, className, classPK);
		List list = findByC_C_C(companyId, className, classPK, count - 1,
				count, obc);

		if (list.size() == 0) {
			String msg = "No Address exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "className=";
			msg += className;
			msg += ", ";
			msg += "classPK=";
			msg += classPK;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchAddressException(msg);
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address[] findByC_C_C_PrevAndNext(String addressId,
		String companyId, String className, String classPK,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
		Address address = findByPrimaryKey(addressId);
		int count = countByC_C_C(companyId, className, classPK);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK IS NULL");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, address);
			Address[] array = new Address[3];
			array[0] = (Address)objArray[0];
			array[1] = (Address)objArray[1];
			array[2] = (Address)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C_C_M(String companyId, String className,
		String classPK, boolean mailing) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK IS NULL");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" AND ");
			query.append("mailing = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
			}

			q.setBoolean(queryPos++, mailing);

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C_C_M(String companyId, String className,
		String classPK, boolean mailing, int begin, int end)
		throws SystemException {
		return findByC_C_C_M(companyId, className, classPK, mailing, begin,
			end, null);
	}

	public List findByC_C_C_M(String companyId, String className,
		String classPK, boolean mailing, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK IS NULL");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" AND ");
			query.append("mailing = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
			}

			q.setBoolean(queryPos++, mailing);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Address findByC_C_C_M_First(String companyId, String className,
		String classPK, boolean mailing, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		List list = findByC_C_C_M(companyId, className, classPK, mailing, 0, 1,
				obc);

		if (list.size() == 0) {
			String msg = "No Address exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "className=";
			msg += className;
			msg += ", ";
			msg += "classPK=";
			msg += classPK;
			msg += ", ";
			msg += "mailing=";
			msg += mailing;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchAddressException(msg);
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address findByC_C_C_M_Last(String companyId, String className,
		String classPK, boolean mailing, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		int count = countByC_C_C_M(companyId, className, classPK, mailing);
		List list = findByC_C_C_M(companyId, className, classPK, mailing,
				count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Address exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "className=";
			msg += className;
			msg += ", ";
			msg += "classPK=";
			msg += classPK;
			msg += ", ";
			msg += "mailing=";
			msg += mailing;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchAddressException(msg);
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address[] findByC_C_C_M_PrevAndNext(String addressId,
		String companyId, String className, String classPK, boolean mailing,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
		Address address = findByPrimaryKey(addressId);
		int count = countByC_C_C_M(companyId, className, classPK, mailing);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK IS NULL");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" AND ");
			query.append("mailing = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
			}

			q.setBoolean(queryPos++, mailing);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, address);
			Address[] array = new Address[3];
			array[0] = (Address)objArray[0];
			array[1] = (Address)objArray[1];
			array[2] = (Address)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C_C_P(String companyId, String className,
		String classPK, boolean primary) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK IS NULL");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" AND ");
			query.append("primary_ = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
			}

			q.setBoolean(queryPos++, primary);

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C_C_P(String companyId, String className,
		String classPK, boolean primary, int begin, int end)
		throws SystemException {
		return findByC_C_C_P(companyId, className, classPK, primary, begin,
			end, null);
	}

	public List findByC_C_C_P(String companyId, String className,
		String classPK, boolean primary, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK IS NULL");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" AND ");
			query.append("primary_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
			}

			q.setBoolean(queryPos++, primary);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Address findByC_C_C_P_First(String companyId, String className,
		String classPK, boolean primary, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		List list = findByC_C_C_P(companyId, className, classPK, primary, 0, 1,
				obc);

		if (list.size() == 0) {
			String msg = "No Address exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "className=";
			msg += className;
			msg += ", ";
			msg += "classPK=";
			msg += classPK;
			msg += ", ";
			msg += "primary=";
			msg += primary;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchAddressException(msg);
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address findByC_C_C_P_Last(String companyId, String className,
		String classPK, boolean primary, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		int count = countByC_C_C_P(companyId, className, classPK, primary);
		List list = findByC_C_C_P(companyId, className, classPK, primary,
				count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Address exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "className=";
			msg += className;
			msg += ", ";
			msg += "classPK=";
			msg += classPK;
			msg += ", ";
			msg += "primary=";
			msg += primary;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchAddressException(msg);
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address[] findByC_C_C_P_PrevAndNext(String addressId,
		String companyId, String className, String classPK, boolean primary,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
		Address address = findByPrimaryKey(addressId);
		int count = countByC_C_C_P(companyId, className, classPK, primary);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK IS NULL");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" AND ");
			query.append("primary_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
			}

			q.setBoolean(queryPos++, primary);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, address);
			Address[] array = new Address[3];
			array[0] = (Address)objArray[0];
			array[1] = (Address)objArray[1];
			array[2] = (Address)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List findAll(int begin, int end) throws SystemException {
		return findAll(begin, end, null);
	}

	public List findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Address ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByCompanyId(String companyId) throws SystemException {
		Iterator itr = findByCompanyId(companyId).iterator();

		while (itr.hasNext()) {
			Address address = (Address)itr.next();
			remove(address);
		}
	}

	public void removeByUserId(String userId) throws SystemException {
		Iterator itr = findByUserId(userId).iterator();

		while (itr.hasNext()) {
			Address address = (Address)itr.next();
			remove(address);
		}
	}

	public void removeByC_C(String companyId, String className)
		throws SystemException {
		Iterator itr = findByC_C(companyId, className).iterator();

		while (itr.hasNext()) {
			Address address = (Address)itr.next();
			remove(address);
		}
	}

	public void removeByC_C_C(String companyId, String className, String classPK)
		throws SystemException {
		Iterator itr = findByC_C_C(companyId, className, classPK).iterator();

		while (itr.hasNext()) {
			Address address = (Address)itr.next();
			remove(address);
		}
	}

	public void removeByC_C_C_M(String companyId, String className,
		String classPK, boolean mailing) throws SystemException {
		Iterator itr = findByC_C_C_M(companyId, className, classPK, mailing)
						   .iterator();

		while (itr.hasNext()) {
			Address address = (Address)itr.next();
			remove(address);
		}
	}

	public void removeByC_C_C_P(String companyId, String className,
		String classPK, boolean primary) throws SystemException {
		Iterator itr = findByC_C_C_P(companyId, className, classPK, primary)
						   .iterator();

		while (itr.hasNext()) {
			Address address = (Address)itr.next();
			remove(address);
		}
	}

	public int countByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_C(String companyId, String className)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_C_C(String companyId, String className, String classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK IS NULL");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_C_C_M(String companyId, String className,
		String classPK, boolean mailing) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK IS NULL");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" AND ");
			query.append("mailing = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
			}

			q.setBoolean(queryPos++, mailing);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_C_C_P(String companyId, String className,
		String classPK, boolean primary) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Address WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK IS NULL");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" AND ");
			query.append("primary_ = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
			}

			q.setBoolean(queryPos++, primary);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(AddressPersistence.class);
}