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
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="AddressPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AddressPersistence extends BasePersistence {
	public com.liferay.portal.model.Address create(String addressId) {
		AddressHBM addressHBM = new AddressHBM();
		addressHBM.setNew(true);
		addressHBM.setPrimaryKey(addressId);

		return AddressHBMUtil.model(addressHBM);
	}

	public com.liferay.portal.model.Address remove(String addressId)
		throws NoSuchAddressException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AddressHBM addressHBM = (AddressHBM)session.get(AddressHBM.class,
					addressId);

			if (addressHBM == null) {
				_log.warn("No Address exists with the primary key " +
					addressId.toString());
				throw new NoSuchAddressException(
					"No Address exists with the primary key " +
					addressId.toString());
			}

			session.delete(addressHBM);
			session.flush();

			return AddressHBMUtil.model(addressHBM);
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
		Session session = null;

		try {
			if (address.isNew() || address.isModified()) {
				session = openSession();

				if (address.isNew()) {
					AddressHBM addressHBM = new AddressHBM();
					addressHBM.setAddressId(address.getAddressId());
					addressHBM.setCompanyId(address.getCompanyId());
					addressHBM.setUserId(address.getUserId());
					addressHBM.setUserName(address.getUserName());
					addressHBM.setCreateDate(address.getCreateDate());
					addressHBM.setModifiedDate(address.getModifiedDate());
					addressHBM.setClassName(address.getClassName());
					addressHBM.setClassPK(address.getClassPK());
					addressHBM.setStreet1(address.getStreet1());
					addressHBM.setStreet2(address.getStreet2());
					addressHBM.setStreet3(address.getStreet3());
					addressHBM.setCity(address.getCity());
					addressHBM.setZip(address.getZip());
					addressHBM.setRegionId(address.getRegionId());
					addressHBM.setCountryId(address.getCountryId());
					addressHBM.setTypeId(address.getTypeId());
					addressHBM.setMailing(address.getMailing());
					addressHBM.setPrimary(address.getPrimary());
					session.save(addressHBM);
					session.flush();
				}
				else {
					AddressHBM addressHBM = (AddressHBM)session.get(AddressHBM.class,
							address.getPrimaryKey());

					if (addressHBM != null) {
						addressHBM.setCompanyId(address.getCompanyId());
						addressHBM.setUserId(address.getUserId());
						addressHBM.setUserName(address.getUserName());
						addressHBM.setCreateDate(address.getCreateDate());
						addressHBM.setModifiedDate(address.getModifiedDate());
						addressHBM.setClassName(address.getClassName());
						addressHBM.setClassPK(address.getClassPK());
						addressHBM.setStreet1(address.getStreet1());
						addressHBM.setStreet2(address.getStreet2());
						addressHBM.setStreet3(address.getStreet3());
						addressHBM.setCity(address.getCity());
						addressHBM.setZip(address.getZip());
						addressHBM.setRegionId(address.getRegionId());
						addressHBM.setCountryId(address.getCountryId());
						addressHBM.setTypeId(address.getTypeId());
						addressHBM.setMailing(address.getMailing());
						addressHBM.setPrimary(address.getPrimary());
						session.flush();
					}
					else {
						addressHBM = new AddressHBM();
						addressHBM.setAddressId(address.getAddressId());
						addressHBM.setCompanyId(address.getCompanyId());
						addressHBM.setUserId(address.getUserId());
						addressHBM.setUserName(address.getUserName());
						addressHBM.setCreateDate(address.getCreateDate());
						addressHBM.setModifiedDate(address.getModifiedDate());
						addressHBM.setClassName(address.getClassName());
						addressHBM.setClassPK(address.getClassPK());
						addressHBM.setStreet1(address.getStreet1());
						addressHBM.setStreet2(address.getStreet2());
						addressHBM.setStreet3(address.getStreet3());
						addressHBM.setCity(address.getCity());
						addressHBM.setZip(address.getZip());
						addressHBM.setRegionId(address.getRegionId());
						addressHBM.setCountryId(address.getCountryId());
						addressHBM.setTypeId(address.getTypeId());
						addressHBM.setMailing(address.getMailing());
						addressHBM.setPrimary(address.getPrimary());
						session.save(addressHBM);
						session.flush();
					}
				}

				address.setNew(false);
				address.setModified(false);
			}

			return address;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Address findByPrimaryKey(String addressId)
		throws NoSuchAddressException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AddressHBM addressHBM = (AddressHBM)session.get(AddressHBM.class,
					addressId);

			if (addressHBM == null) {
				_log.warn("No Address exists with the primary key " +
					addressId.toString());
				throw new NoSuchAddressException(
					"No Address exists with the primary key " +
					addressId.toString());
			}

			return AddressHBMUtil.model(addressHBM);
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
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				list.add(AddressHBMUtil.model(addressHBM));
			}

			return list;
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
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
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
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				list.add(AddressHBMUtil.model(addressHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Address findByCompanyId_First(
		String companyId, OrderByComparator obc)
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
			return (com.liferay.portal.model.Address)list.get(0);
		}
	}

	public com.liferay.portal.model.Address findByCompanyId_Last(
		String companyId, OrderByComparator obc)
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
			return (com.liferay.portal.model.Address)list.get(0);
		}
	}

	public com.liferay.portal.model.Address[] findByCompanyId_PrevAndNext(
		String addressId, String companyId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		com.liferay.portal.model.Address address = findByPrimaryKey(addressId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
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
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					address, AddressHBMUtil.getInstance());
			com.liferay.portal.model.Address[] array = new com.liferay.portal.model.Address[3];
			array[0] = (com.liferay.portal.model.Address)objArray[0];
			array[1] = (com.liferay.portal.model.Address)objArray[1];
			array[2] = (com.liferay.portal.model.Address)objArray[2];

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
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				list.add(AddressHBMUtil.model(addressHBM));
			}

			return list;
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
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
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
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				list.add(AddressHBMUtil.model(addressHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Address findByUserId_First(String userId,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
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
			return (com.liferay.portal.model.Address)list.get(0);
		}
	}

	public com.liferay.portal.model.Address findByUserId_Last(String userId,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
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
			return (com.liferay.portal.model.Address)list.get(0);
		}
	}

	public com.liferay.portal.model.Address[] findByUserId_PrevAndNext(
		String addressId, String userId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		com.liferay.portal.model.Address address = findByPrimaryKey(addressId);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
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
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					address, AddressHBMUtil.getInstance());
			com.liferay.portal.model.Address[] array = new com.liferay.portal.model.Address[3];
			array[0] = (com.liferay.portal.model.Address)objArray[0];
			array[1] = (com.liferay.portal.model.Address)objArray[1];
			array[2] = (com.liferay.portal.model.Address)objArray[2];

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
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				list.add(AddressHBMUtil.model(addressHBM));
			}

			return list;
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
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
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
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				list.add(AddressHBMUtil.model(addressHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Address findByC_C_First(String companyId,
		String className, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
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
			return (com.liferay.portal.model.Address)list.get(0);
		}
	}

	public com.liferay.portal.model.Address findByC_C_Last(String companyId,
		String className, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
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
			return (com.liferay.portal.model.Address)list.get(0);
		}
	}

	public com.liferay.portal.model.Address[] findByC_C_PrevAndNext(
		String addressId, String companyId, String className,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
		com.liferay.portal.model.Address address = findByPrimaryKey(addressId);
		int count = countByC_C(companyId, className);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
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
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					address, AddressHBMUtil.getInstance());
			com.liferay.portal.model.Address[] array = new com.liferay.portal.model.Address[3];
			array[0] = (com.liferay.portal.model.Address)objArray[0];
			array[1] = (com.liferay.portal.model.Address)objArray[1];
			array[2] = (com.liferay.portal.model.Address)objArray[2];

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
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK is null");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
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
			List list = new ArrayList();

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				list.add(AddressHBMUtil.model(addressHBM));
			}

			return list;
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
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK is null");
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

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				list.add(AddressHBMUtil.model(addressHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Address findByC_C_C_First(
		String companyId, String className, String classPK,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
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
			return (com.liferay.portal.model.Address)list.get(0);
		}
	}

	public com.liferay.portal.model.Address findByC_C_C_Last(String companyId,
		String className, String classPK, OrderByComparator obc)
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
			return (com.liferay.portal.model.Address)list.get(0);
		}
	}

	public com.liferay.portal.model.Address[] findByC_C_C_PrevAndNext(
		String addressId, String companyId, String className, String classPK,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
		com.liferay.portal.model.Address address = findByPrimaryKey(addressId);
		int count = countByC_C_C(companyId, className, classPK);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK is null");
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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					address, AddressHBMUtil.getInstance());
			com.liferay.portal.model.Address[] array = new com.liferay.portal.model.Address[3];
			array[0] = (com.liferay.portal.model.Address)objArray[0];
			array[1] = (com.liferay.portal.model.Address)objArray[1];
			array[2] = (com.liferay.portal.model.Address)objArray[2];

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
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK is null");
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
			List list = new ArrayList();

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				list.add(AddressHBMUtil.model(addressHBM));
			}

			return list;
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
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK is null");
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

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				list.add(AddressHBMUtil.model(addressHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Address findByC_C_C_M_First(
		String companyId, String className, String classPK, boolean mailing,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
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
			return (com.liferay.portal.model.Address)list.get(0);
		}
	}

	public com.liferay.portal.model.Address findByC_C_C_M_Last(
		String companyId, String className, String classPK, boolean mailing,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
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
			return (com.liferay.portal.model.Address)list.get(0);
		}
	}

	public com.liferay.portal.model.Address[] findByC_C_C_M_PrevAndNext(
		String addressId, String companyId, String className, String classPK,
		boolean mailing, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		com.liferay.portal.model.Address address = findByPrimaryKey(addressId);
		int count = countByC_C_C_M(companyId, className, classPK, mailing);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK is null");
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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					address, AddressHBMUtil.getInstance());
			com.liferay.portal.model.Address[] array = new com.liferay.portal.model.Address[3];
			array[0] = (com.liferay.portal.model.Address)objArray[0];
			array[1] = (com.liferay.portal.model.Address)objArray[1];
			array[2] = (com.liferay.portal.model.Address)objArray[2];

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
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK is null");
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
			List list = new ArrayList();

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				list.add(AddressHBMUtil.model(addressHBM));
			}

			return list;
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
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK is null");
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

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				list.add(AddressHBMUtil.model(addressHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Address findByC_C_C_P_First(
		String companyId, String className, String classPK, boolean primary,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
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
			return (com.liferay.portal.model.Address)list.get(0);
		}
	}

	public com.liferay.portal.model.Address findByC_C_C_P_Last(
		String companyId, String className, String classPK, boolean primary,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
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
			return (com.liferay.portal.model.Address)list.get(0);
		}
	}

	public com.liferay.portal.model.Address[] findByC_C_C_P_PrevAndNext(
		String addressId, String companyId, String className, String classPK,
		boolean primary, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		com.liferay.portal.model.Address address = findByPrimaryKey(addressId);
		int count = countByC_C_C_P(companyId, className, classPK, primary);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK is null");
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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					address, AddressHBMUtil.getInstance());
			com.liferay.portal.model.Address[] array = new com.liferay.portal.model.Address[3];
			array[0] = (com.liferay.portal.model.Address)objArray[0];
			array[1] = (com.liferay.portal.model.Address)objArray[1];
			array[2] = (com.liferay.portal.model.Address)objArray[2];

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
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				list.add(AddressHBMUtil.model(addressHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				session.delete(addressHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				session.delete(addressHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByC_C(String companyId, String className)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				session.delete(addressHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByC_C_C(String companyId, String className, String classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK is null");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
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

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				session.delete(addressHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByC_C_C_M(String companyId, String className,
		String classPK, boolean mailing) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK is null");
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

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				session.delete(addressHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByC_C_C_P(String companyId, String className,
		String classPK, boolean primary) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK is null");
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

			while (itr.hasNext()) {
				AddressHBM addressHBM = (AddressHBM)itr.next();
				session.delete(addressHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK is null");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK is null");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" AND ");
			query.append("mailing = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
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
			query.append(
				"FROM Address IN CLASS com.liferay.portal.service.persistence.AddressHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK is null");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" AND ");
			query.append("primary_ = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
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

	private static Log _log = LogFactory.getLog(AddressPersistence.class);
}