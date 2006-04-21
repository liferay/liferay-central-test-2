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

import com.liferay.portal.NoSuchPhoneException;
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
 * <a href="PhonePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PhonePersistence extends BasePersistence {
	public com.liferay.portal.model.Phone create(String phoneId) {
		PhoneHBM phoneHBM = new PhoneHBM();
		phoneHBM.setNew(true);
		phoneHBM.setPrimaryKey(phoneId);

		return PhoneHBMUtil.model(phoneHBM);
	}

	public com.liferay.portal.model.Phone remove(String phoneId)
		throws NoSuchPhoneException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PhoneHBM phoneHBM = (PhoneHBM)session.get(PhoneHBM.class, phoneId);

			if (phoneHBM == null) {
				_log.warn("No Phone exists with the primary key " +
					phoneId.toString());
				throw new NoSuchPhoneException(
					"No Phone exists with the primary key " +
					phoneId.toString());
			}

			session.delete(phoneHBM);
			session.flush();

			return PhoneHBMUtil.model(phoneHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Phone update(
		com.liferay.portal.model.Phone phone) throws SystemException {
		Session session = null;

		try {
			if (phone.isNew() || phone.isModified()) {
				session = openSession();

				if (phone.isNew()) {
					PhoneHBM phoneHBM = new PhoneHBM();
					phoneHBM.setPhoneId(phone.getPhoneId());
					phoneHBM.setCompanyId(phone.getCompanyId());
					phoneHBM.setUserId(phone.getUserId());
					phoneHBM.setUserName(phone.getUserName());
					phoneHBM.setCreateDate(phone.getCreateDate());
					phoneHBM.setModifiedDate(phone.getModifiedDate());
					phoneHBM.setClassName(phone.getClassName());
					phoneHBM.setClassPK(phone.getClassPK());
					phoneHBM.setNumber(phone.getNumber());
					phoneHBM.setExtension(phone.getExtension());
					phoneHBM.setTypeId(phone.getTypeId());
					phoneHBM.setPrimary(phone.getPrimary());
					session.save(phoneHBM);
					session.flush();
				}
				else {
					PhoneHBM phoneHBM = (PhoneHBM)session.get(PhoneHBM.class,
							phone.getPrimaryKey());

					if (phoneHBM != null) {
						phoneHBM.setCompanyId(phone.getCompanyId());
						phoneHBM.setUserId(phone.getUserId());
						phoneHBM.setUserName(phone.getUserName());
						phoneHBM.setCreateDate(phone.getCreateDate());
						phoneHBM.setModifiedDate(phone.getModifiedDate());
						phoneHBM.setClassName(phone.getClassName());
						phoneHBM.setClassPK(phone.getClassPK());
						phoneHBM.setNumber(phone.getNumber());
						phoneHBM.setExtension(phone.getExtension());
						phoneHBM.setTypeId(phone.getTypeId());
						phoneHBM.setPrimary(phone.getPrimary());
						session.flush();
					}
					else {
						phoneHBM = new PhoneHBM();
						phoneHBM.setPhoneId(phone.getPhoneId());
						phoneHBM.setCompanyId(phone.getCompanyId());
						phoneHBM.setUserId(phone.getUserId());
						phoneHBM.setUserName(phone.getUserName());
						phoneHBM.setCreateDate(phone.getCreateDate());
						phoneHBM.setModifiedDate(phone.getModifiedDate());
						phoneHBM.setClassName(phone.getClassName());
						phoneHBM.setClassPK(phone.getClassPK());
						phoneHBM.setNumber(phone.getNumber());
						phoneHBM.setExtension(phone.getExtension());
						phoneHBM.setTypeId(phone.getTypeId());
						phoneHBM.setPrimary(phone.getPrimary());
						session.save(phoneHBM);
						session.flush();
					}
				}

				phone.setNew(false);
				phone.setModified(false);
			}

			return phone;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Phone findByPrimaryKey(String phoneId)
		throws NoSuchPhoneException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PhoneHBM phoneHBM = (PhoneHBM)session.get(PhoneHBM.class, phoneId);

			if (phoneHBM == null) {
				_log.warn("No Phone exists with the primary key " +
					phoneId.toString());
				throw new NoSuchPhoneException(
					"No Phone exists with the primary key " +
					phoneId.toString());
			}

			return PhoneHBMUtil.model(phoneHBM);
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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				PhoneHBM phoneHBM = (PhoneHBM)itr.next();
				list.add(PhoneHBMUtil.model(phoneHBM));
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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
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
			q.setString(queryPos++, companyId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				PhoneHBM phoneHBM = (PhoneHBM)itr.next();
				list.add(PhoneHBMUtil.model(phoneHBM));
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

	public com.liferay.portal.model.Phone findByCompanyId_First(
		String companyId, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Phone exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPhoneException(msg);
		}
		else {
			return (com.liferay.portal.model.Phone)list.get(0);
		}
	}

	public com.liferay.portal.model.Phone findByCompanyId_Last(
		String companyId, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Phone exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPhoneException(msg);
		}
		else {
			return (com.liferay.portal.model.Phone)list.get(0);
		}
	}

	public com.liferay.portal.model.Phone[] findByCompanyId_PrevAndNext(
		String phoneId, String companyId, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		com.liferay.portal.model.Phone phone = findByPrimaryKey(phoneId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
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
			q.setString(queryPos++, companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, phone,
					PhoneHBMUtil.getInstance());
			com.liferay.portal.model.Phone[] array = new com.liferay.portal.model.Phone[3];
			array[0] = (com.liferay.portal.model.Phone)objArray[0];
			array[1] = (com.liferay.portal.model.Phone)objArray[1];
			array[2] = (com.liferay.portal.model.Phone)objArray[2];

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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				PhoneHBM phoneHBM = (PhoneHBM)itr.next();
				list.add(PhoneHBMUtil.model(phoneHBM));
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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("userId = ?");
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
			q.setString(queryPos++, userId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				PhoneHBM phoneHBM = (PhoneHBM)itr.next();
				list.add(PhoneHBMUtil.model(phoneHBM));
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

	public com.liferay.portal.model.Phone findByUserId_First(String userId,
		OrderByComparator obc) throws NoSuchPhoneException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Phone exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPhoneException(msg);
		}
		else {
			return (com.liferay.portal.model.Phone)list.get(0);
		}
	}

	public com.liferay.portal.model.Phone findByUserId_Last(String userId,
		OrderByComparator obc) throws NoSuchPhoneException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Phone exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPhoneException(msg);
		}
		else {
			return (com.liferay.portal.model.Phone)list.get(0);
		}
	}

	public com.liferay.portal.model.Phone[] findByUserId_PrevAndNext(
		String phoneId, String userId, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		com.liferay.portal.model.Phone phone = findByPrimaryKey(phoneId);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("userId = ?");
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
			q.setString(queryPos++, userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, phone,
					PhoneHBMUtil.getInstance());
			com.liferay.portal.model.Phone[] array = new com.liferay.portal.model.Phone[3];
			array[0] = (com.liferay.portal.model.Phone)objArray[0];
			array[1] = (com.liferay.portal.model.Phone)objArray[1];
			array[2] = (com.liferay.portal.model.Phone)objArray[2];

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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				PhoneHBM phoneHBM = (PhoneHBM)itr.next();
				list.add(PhoneHBMUtil.model(phoneHBM));
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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
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
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				PhoneHBM phoneHBM = (PhoneHBM)itr.next();
				list.add(PhoneHBMUtil.model(phoneHBM));
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

	public com.liferay.portal.model.Phone findByC_C_First(String companyId,
		String className, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		List list = findByC_C(companyId, className, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Phone exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "className=";
			msg += className;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPhoneException(msg);
		}
		else {
			return (com.liferay.portal.model.Phone)list.get(0);
		}
	}

	public com.liferay.portal.model.Phone findByC_C_Last(String companyId,
		String className, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		int count = countByC_C(companyId, className);
		List list = findByC_C(companyId, className, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Phone exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "className=";
			msg += className;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPhoneException(msg);
		}
		else {
			return (com.liferay.portal.model.Phone)list.get(0);
		}
	}

	public com.liferay.portal.model.Phone[] findByC_C_PrevAndNext(
		String phoneId, String companyId, String className,
		OrderByComparator obc) throws NoSuchPhoneException, SystemException {
		com.liferay.portal.model.Phone phone = findByPrimaryKey(phoneId);
		int count = countByC_C(companyId, className);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
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
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, phone,
					PhoneHBMUtil.getInstance());
			com.liferay.portal.model.Phone[] array = new com.liferay.portal.model.Phone[3];
			array[0] = (com.liferay.portal.model.Phone)objArray[0];
			array[1] = (com.liferay.portal.model.Phone)objArray[1];
			array[2] = (com.liferay.portal.model.Phone)objArray[2];

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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				PhoneHBM phoneHBM = (PhoneHBM)itr.next();
				list.add(PhoneHBMUtil.model(phoneHBM));
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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
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
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				PhoneHBM phoneHBM = (PhoneHBM)itr.next();
				list.add(PhoneHBMUtil.model(phoneHBM));
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

	public com.liferay.portal.model.Phone findByC_C_C_First(String companyId,
		String className, String classPK, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		List list = findByC_C_C(companyId, className, classPK, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Phone exists with the key ";
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
			throw new NoSuchPhoneException(msg);
		}
		else {
			return (com.liferay.portal.model.Phone)list.get(0);
		}
	}

	public com.liferay.portal.model.Phone findByC_C_C_Last(String companyId,
		String className, String classPK, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		int count = countByC_C_C(companyId, className, classPK);
		List list = findByC_C_C(companyId, className, classPK, count - 1,
				count, obc);

		if (list.size() == 0) {
			String msg = "No Phone exists with the key ";
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
			throw new NoSuchPhoneException(msg);
		}
		else {
			return (com.liferay.portal.model.Phone)list.get(0);
		}
	}

	public com.liferay.portal.model.Phone[] findByC_C_C_PrevAndNext(
		String phoneId, String companyId, String className, String classPK,
		OrderByComparator obc) throws NoSuchPhoneException, SystemException {
		com.liferay.portal.model.Phone phone = findByPrimaryKey(phoneId);
		int count = countByC_C_C(companyId, className, classPK);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
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
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, phone,
					PhoneHBMUtil.getInstance());
			com.liferay.portal.model.Phone[] array = new com.liferay.portal.model.Phone[3];
			array[0] = (com.liferay.portal.model.Phone)objArray[0];
			array[1] = (com.liferay.portal.model.Phone)objArray[1];
			array[2] = (com.liferay.portal.model.Phone)objArray[2];

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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" AND ");
			query.append("primary_ = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);
			q.setBoolean(queryPos++, primary);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				PhoneHBM phoneHBM = (PhoneHBM)itr.next();
				list.add(PhoneHBMUtil.model(phoneHBM));
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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
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
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);
			q.setBoolean(queryPos++, primary);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				PhoneHBM phoneHBM = (PhoneHBM)itr.next();
				list.add(PhoneHBMUtil.model(phoneHBM));
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

	public com.liferay.portal.model.Phone findByC_C_C_P_First(
		String companyId, String className, String classPK, boolean primary,
		OrderByComparator obc) throws NoSuchPhoneException, SystemException {
		List list = findByC_C_C_P(companyId, className, classPK, primary, 0, 1,
				obc);

		if (list.size() == 0) {
			String msg = "No Phone exists with the key ";
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
			throw new NoSuchPhoneException(msg);
		}
		else {
			return (com.liferay.portal.model.Phone)list.get(0);
		}
	}

	public com.liferay.portal.model.Phone findByC_C_C_P_Last(String companyId,
		String className, String classPK, boolean primary, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		int count = countByC_C_C_P(companyId, className, classPK, primary);
		List list = findByC_C_C_P(companyId, className, classPK, primary,
				count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Phone exists with the key ";
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
			throw new NoSuchPhoneException(msg);
		}
		else {
			return (com.liferay.portal.model.Phone)list.get(0);
		}
	}

	public com.liferay.portal.model.Phone[] findByC_C_C_P_PrevAndNext(
		String phoneId, String companyId, String className, String classPK,
		boolean primary, OrderByComparator obc)
		throws NoSuchPhoneException, SystemException {
		com.liferay.portal.model.Phone phone = findByPrimaryKey(phoneId);
		int count = countByC_C_C_P(companyId, className, classPK, primary);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
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
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);
			q.setBoolean(queryPos++, primary);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, phone,
					PhoneHBMUtil.getInstance());
			com.liferay.portal.model.Phone[] array = new com.liferay.portal.model.Phone[3];
			array[0] = (com.liferay.portal.model.Phone)objArray[0];
			array[1] = (com.liferay.portal.model.Phone)objArray[1];
			array[2] = (com.liferay.portal.model.Phone)objArray[2];

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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				PhoneHBM phoneHBM = (PhoneHBM)itr.next();
				list.add(PhoneHBMUtil.model(phoneHBM));
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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				PhoneHBM phoneHBM = (PhoneHBM)itr.next();
				session.delete(phoneHBM);
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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				PhoneHBM phoneHBM = (PhoneHBM)itr.next();
				session.delete(phoneHBM);
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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				PhoneHBM phoneHBM = (PhoneHBM)itr.next();
				session.delete(phoneHBM);
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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				PhoneHBM phoneHBM = (PhoneHBM)itr.next();
				session.delete(phoneHBM);
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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" AND ");
			query.append("primary_ = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);
			q.setBoolean(queryPos++, primary);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				PhoneHBM phoneHBM = (PhoneHBM)itr.next();
				session.delete(phoneHBM);
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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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
				"FROM Phone IN CLASS com.liferay.portal.service.persistence.PhoneHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" AND ");
			query.append("primary_ = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);
			q.setBoolean(queryPos++, primary);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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

	private static Log _log = LogFactory.getLog(PhonePersistence.class);
}