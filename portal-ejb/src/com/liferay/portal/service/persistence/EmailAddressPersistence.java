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

import com.liferay.portal.NoSuchEmailAddressException;
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
 * <a href="EmailAddressPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class EmailAddressPersistence extends BasePersistence {
	public com.liferay.portal.model.EmailAddress create(String emailAddressId) {
		EmailAddressHBM emailAddressHBM = new EmailAddressHBM();
		emailAddressHBM.setNew(true);
		emailAddressHBM.setPrimaryKey(emailAddressId);

		return EmailAddressHBMUtil.model(emailAddressHBM);
	}

	public com.liferay.portal.model.EmailAddress remove(String emailAddressId)
		throws NoSuchEmailAddressException, SystemException {
		Session session = null;

		try {
			session = openSession();

			EmailAddressHBM emailAddressHBM = (EmailAddressHBM)session.get(EmailAddressHBM.class,
					emailAddressId);

			if (emailAddressHBM == null) {
				_log.warn("No EmailAddress exists with the primary key " +
					emailAddressId.toString());
				throw new NoSuchEmailAddressException(
					"No EmailAddress exists with the primary key " +
					emailAddressId.toString());
			}

			session.delete(emailAddressHBM);
			session.flush();

			return EmailAddressHBMUtil.model(emailAddressHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.EmailAddress update(
		com.liferay.portal.model.EmailAddress emailAddress)
		throws SystemException {
		Session session = null;

		try {
			if (emailAddress.isNew() || emailAddress.isModified()) {
				session = openSession();

				if (emailAddress.isNew()) {
					EmailAddressHBM emailAddressHBM = new EmailAddressHBM();
					emailAddressHBM.setEmailAddressId(emailAddress.getEmailAddressId());
					emailAddressHBM.setCompanyId(emailAddress.getCompanyId());
					emailAddressHBM.setUserId(emailAddress.getUserId());
					emailAddressHBM.setUserName(emailAddress.getUserName());
					emailAddressHBM.setCreateDate(emailAddress.getCreateDate());
					emailAddressHBM.setModifiedDate(emailAddress.getModifiedDate());
					emailAddressHBM.setClassName(emailAddress.getClassName());
					emailAddressHBM.setClassPK(emailAddress.getClassPK());
					emailAddressHBM.setAddress(emailAddress.getAddress());
					emailAddressHBM.setTypeId(emailAddress.getTypeId());
					emailAddressHBM.setPrimary(emailAddress.getPrimary());
					session.save(emailAddressHBM);
					session.flush();
				}
				else {
					EmailAddressHBM emailAddressHBM = (EmailAddressHBM)session.get(EmailAddressHBM.class,
							emailAddress.getPrimaryKey());

					if (emailAddressHBM != null) {
						emailAddressHBM.setCompanyId(emailAddress.getCompanyId());
						emailAddressHBM.setUserId(emailAddress.getUserId());
						emailAddressHBM.setUserName(emailAddress.getUserName());
						emailAddressHBM.setCreateDate(emailAddress.getCreateDate());
						emailAddressHBM.setModifiedDate(emailAddress.getModifiedDate());
						emailAddressHBM.setClassName(emailAddress.getClassName());
						emailAddressHBM.setClassPK(emailAddress.getClassPK());
						emailAddressHBM.setAddress(emailAddress.getAddress());
						emailAddressHBM.setTypeId(emailAddress.getTypeId());
						emailAddressHBM.setPrimary(emailAddress.getPrimary());
						session.flush();
					}
					else {
						emailAddressHBM = new EmailAddressHBM();
						emailAddressHBM.setEmailAddressId(emailAddress.getEmailAddressId());
						emailAddressHBM.setCompanyId(emailAddress.getCompanyId());
						emailAddressHBM.setUserId(emailAddress.getUserId());
						emailAddressHBM.setUserName(emailAddress.getUserName());
						emailAddressHBM.setCreateDate(emailAddress.getCreateDate());
						emailAddressHBM.setModifiedDate(emailAddress.getModifiedDate());
						emailAddressHBM.setClassName(emailAddress.getClassName());
						emailAddressHBM.setClassPK(emailAddress.getClassPK());
						emailAddressHBM.setAddress(emailAddress.getAddress());
						emailAddressHBM.setTypeId(emailAddress.getTypeId());
						emailAddressHBM.setPrimary(emailAddress.getPrimary());
						session.save(emailAddressHBM);
						session.flush();
					}
				}

				emailAddress.setNew(false);
				emailAddress.setModified(false);
			}

			return emailAddress;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.EmailAddress findByPrimaryKey(
		String emailAddressId)
		throws NoSuchEmailAddressException, SystemException {
		Session session = null;

		try {
			session = openSession();

			EmailAddressHBM emailAddressHBM = (EmailAddressHBM)session.get(EmailAddressHBM.class,
					emailAddressId);

			if (emailAddressHBM == null) {
				_log.warn("No EmailAddress exists with the primary key " +
					emailAddressId.toString());
				throw new NoSuchEmailAddressException(
					"No EmailAddress exists with the primary key " +
					emailAddressId.toString());
			}

			return EmailAddressHBMUtil.model(emailAddressHBM);
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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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
				EmailAddressHBM emailAddressHBM = (EmailAddressHBM)itr.next();
				list.add(EmailAddressHBMUtil.model(emailAddressHBM));
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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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
				EmailAddressHBM emailAddressHBM = (EmailAddressHBM)itr.next();
				list.add(EmailAddressHBMUtil.model(emailAddressHBM));
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

	public com.liferay.portal.model.EmailAddress findByCompanyId_First(
		String companyId, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No EmailAddress exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEmailAddressException(msg);
		}
		else {
			return (com.liferay.portal.model.EmailAddress)list.get(0);
		}
	}

	public com.liferay.portal.model.EmailAddress findByCompanyId_Last(
		String companyId, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No EmailAddress exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEmailAddressException(msg);
		}
		else {
			return (com.liferay.portal.model.EmailAddress)list.get(0);
		}
	}

	public com.liferay.portal.model.EmailAddress[] findByCompanyId_PrevAndNext(
		String emailAddressId, String companyId, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		com.liferay.portal.model.EmailAddress emailAddress = findByPrimaryKey(emailAddressId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					emailAddress, EmailAddressHBMUtil.getInstance());
			com.liferay.portal.model.EmailAddress[] array = new com.liferay.portal.model.EmailAddress[3];
			array[0] = (com.liferay.portal.model.EmailAddress)objArray[0];
			array[1] = (com.liferay.portal.model.EmailAddress)objArray[1];
			array[2] = (com.liferay.portal.model.EmailAddress)objArray[2];

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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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
				EmailAddressHBM emailAddressHBM = (EmailAddressHBM)itr.next();
				list.add(EmailAddressHBMUtil.model(emailAddressHBM));
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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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
				EmailAddressHBM emailAddressHBM = (EmailAddressHBM)itr.next();
				list.add(EmailAddressHBMUtil.model(emailAddressHBM));
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

	public com.liferay.portal.model.EmailAddress findByUserId_First(
		String userId, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No EmailAddress exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEmailAddressException(msg);
		}
		else {
			return (com.liferay.portal.model.EmailAddress)list.get(0);
		}
	}

	public com.liferay.portal.model.EmailAddress findByUserId_Last(
		String userId, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No EmailAddress exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEmailAddressException(msg);
		}
		else {
			return (com.liferay.portal.model.EmailAddress)list.get(0);
		}
	}

	public com.liferay.portal.model.EmailAddress[] findByUserId_PrevAndNext(
		String emailAddressId, String userId, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		com.liferay.portal.model.EmailAddress emailAddress = findByPrimaryKey(emailAddressId);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					emailAddress, EmailAddressHBMUtil.getInstance());
			com.liferay.portal.model.EmailAddress[] array = new com.liferay.portal.model.EmailAddress[3];
			array[0] = (com.liferay.portal.model.EmailAddress)objArray[0];
			array[1] = (com.liferay.portal.model.EmailAddress)objArray[1];
			array[2] = (com.liferay.portal.model.EmailAddress)objArray[2];

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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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
				EmailAddressHBM emailAddressHBM = (EmailAddressHBM)itr.next();
				list.add(EmailAddressHBMUtil.model(emailAddressHBM));
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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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
				EmailAddressHBM emailAddressHBM = (EmailAddressHBM)itr.next();
				list.add(EmailAddressHBMUtil.model(emailAddressHBM));
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

	public com.liferay.portal.model.EmailAddress findByC_C_First(
		String companyId, String className, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		List list = findByC_C(companyId, className, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No EmailAddress exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "className=";
			msg += className;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEmailAddressException(msg);
		}
		else {
			return (com.liferay.portal.model.EmailAddress)list.get(0);
		}
	}

	public com.liferay.portal.model.EmailAddress findByC_C_Last(
		String companyId, String className, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		int count = countByC_C(companyId, className);
		List list = findByC_C(companyId, className, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No EmailAddress exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "className=";
			msg += className;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchEmailAddressException(msg);
		}
		else {
			return (com.liferay.portal.model.EmailAddress)list.get(0);
		}
	}

	public com.liferay.portal.model.EmailAddress[] findByC_C_PrevAndNext(
		String emailAddressId, String companyId, String className,
		OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		com.liferay.portal.model.EmailAddress emailAddress = findByPrimaryKey(emailAddressId);
		int count = countByC_C(companyId, className);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					emailAddress, EmailAddressHBMUtil.getInstance());
			com.liferay.portal.model.EmailAddress[] array = new com.liferay.portal.model.EmailAddress[3];
			array[0] = (com.liferay.portal.model.EmailAddress)objArray[0];
			array[1] = (com.liferay.portal.model.EmailAddress)objArray[1];
			array[2] = (com.liferay.portal.model.EmailAddress)objArray[2];

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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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
				EmailAddressHBM emailAddressHBM = (EmailAddressHBM)itr.next();
				list.add(EmailAddressHBMUtil.model(emailAddressHBM));
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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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
				EmailAddressHBM emailAddressHBM = (EmailAddressHBM)itr.next();
				list.add(EmailAddressHBMUtil.model(emailAddressHBM));
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

	public com.liferay.portal.model.EmailAddress findByC_C_C_First(
		String companyId, String className, String classPK,
		OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		List list = findByC_C_C(companyId, className, classPK, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No EmailAddress exists with the key ";
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
			throw new NoSuchEmailAddressException(msg);
		}
		else {
			return (com.liferay.portal.model.EmailAddress)list.get(0);
		}
	}

	public com.liferay.portal.model.EmailAddress findByC_C_C_Last(
		String companyId, String className, String classPK,
		OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		int count = countByC_C_C(companyId, className, classPK);
		List list = findByC_C_C(companyId, className, classPK, count - 1,
				count, obc);

		if (list.size() == 0) {
			String msg = "No EmailAddress exists with the key ";
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
			throw new NoSuchEmailAddressException(msg);
		}
		else {
			return (com.liferay.portal.model.EmailAddress)list.get(0);
		}
	}

	public com.liferay.portal.model.EmailAddress[] findByC_C_C_PrevAndNext(
		String emailAddressId, String companyId, String className,
		String classPK, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		com.liferay.portal.model.EmailAddress emailAddress = findByPrimaryKey(emailAddressId);
		int count = countByC_C_C(companyId, className, classPK);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					emailAddress, EmailAddressHBMUtil.getInstance());
			com.liferay.portal.model.EmailAddress[] array = new com.liferay.portal.model.EmailAddress[3];
			array[0] = (com.liferay.portal.model.EmailAddress)objArray[0];
			array[1] = (com.liferay.portal.model.EmailAddress)objArray[1];
			array[2] = (com.liferay.portal.model.EmailAddress)objArray[2];

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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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
				EmailAddressHBM emailAddressHBM = (EmailAddressHBM)itr.next();
				list.add(EmailAddressHBMUtil.model(emailAddressHBM));
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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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
				EmailAddressHBM emailAddressHBM = (EmailAddressHBM)itr.next();
				list.add(EmailAddressHBMUtil.model(emailAddressHBM));
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

	public com.liferay.portal.model.EmailAddress findByC_C_C_P_First(
		String companyId, String className, String classPK, boolean primary,
		OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		List list = findByC_C_C_P(companyId, className, classPK, primary, 0, 1,
				obc);

		if (list.size() == 0) {
			String msg = "No EmailAddress exists with the key ";
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
			throw new NoSuchEmailAddressException(msg);
		}
		else {
			return (com.liferay.portal.model.EmailAddress)list.get(0);
		}
	}

	public com.liferay.portal.model.EmailAddress findByC_C_C_P_Last(
		String companyId, String className, String classPK, boolean primary,
		OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		int count = countByC_C_C_P(companyId, className, classPK, primary);
		List list = findByC_C_C_P(companyId, className, classPK, primary,
				count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No EmailAddress exists with the key ";
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
			throw new NoSuchEmailAddressException(msg);
		}
		else {
			return (com.liferay.portal.model.EmailAddress)list.get(0);
		}
	}

	public com.liferay.portal.model.EmailAddress[] findByC_C_C_P_PrevAndNext(
		String emailAddressId, String companyId, String className,
		String classPK, boolean primary, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		com.liferay.portal.model.EmailAddress emailAddress = findByPrimaryKey(emailAddressId);
		int count = countByC_C_C_P(companyId, className, classPK, primary);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					emailAddress, EmailAddressHBMUtil.getInstance());
			com.liferay.portal.model.EmailAddress[] array = new com.liferay.portal.model.EmailAddress[3];
			array[0] = (com.liferay.portal.model.EmailAddress)objArray[0];
			array[1] = (com.liferay.portal.model.EmailAddress)objArray[1];
			array[2] = (com.liferay.portal.model.EmailAddress)objArray[2];

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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				EmailAddressHBM emailAddressHBM = (EmailAddressHBM)itr.next();
				list.add(EmailAddressHBMUtil.model(emailAddressHBM));
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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				EmailAddressHBM emailAddressHBM = (EmailAddressHBM)itr.next();
				session.delete(emailAddressHBM);
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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				EmailAddressHBM emailAddressHBM = (EmailAddressHBM)itr.next();
				session.delete(emailAddressHBM);
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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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
				EmailAddressHBM emailAddressHBM = (EmailAddressHBM)itr.next();
				session.delete(emailAddressHBM);
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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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
				EmailAddressHBM emailAddressHBM = (EmailAddressHBM)itr.next();
				session.delete(emailAddressHBM);
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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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
				EmailAddressHBM emailAddressHBM = (EmailAddressHBM)itr.next();
				session.delete(emailAddressHBM);
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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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
				"FROM EmailAddress IN CLASS com.liferay.portal.service.persistence.EmailAddressHBM WHERE ");
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

	private static Log _log = LogFactory.getLog(EmailAddressPersistence.class);
}