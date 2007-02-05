/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.impl.EmailAddressImpl;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.util.dao.DynamicQuery;
import com.liferay.util.dao.DynamicQueryInitializer;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="EmailAddressPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EmailAddressPersistence extends BasePersistence {
	public EmailAddress create(long emailAddressId) {
		EmailAddress emailAddress = new EmailAddressImpl();
		emailAddress.setNew(true);
		emailAddress.setPrimaryKey(emailAddressId);

		return emailAddress;
	}

	public EmailAddress remove(long emailAddressId)
		throws NoSuchEmailAddressException, SystemException {
		Session session = null;

		try {
			session = openSession();

			EmailAddress emailAddress = (EmailAddress)session.get(EmailAddressImpl.class,
					new Long(emailAddressId));

			if (emailAddress == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No EmailAddress exists with the primary key " +
						emailAddressId);
				}

				throw new NoSuchEmailAddressException(
					"No EmailAddress exists with the primary key " +
					emailAddressId);
			}

			return remove(emailAddress);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public EmailAddress remove(EmailAddress emailAddress)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(emailAddress);
			session.flush();

			return emailAddress;
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
		return update(emailAddress, false);
	}

	public com.liferay.portal.model.EmailAddress update(
		com.liferay.portal.model.EmailAddress emailAddress, boolean saveOrUpdate)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(emailAddress);
			}
			else {
				if (emailAddress.isNew()) {
					session.save(emailAddress);
				}
			}

			session.flush();
			emailAddress.setNew(false);

			return emailAddress;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public EmailAddress findByPrimaryKey(long emailAddressId)
		throws NoSuchEmailAddressException, SystemException {
		EmailAddress emailAddress = fetchByPrimaryKey(emailAddressId);

		if (emailAddress == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No EmailAddress exists with the primary key " +
					emailAddressId);
			}

			throw new NoSuchEmailAddressException(
				"No EmailAddress exists with the primary key " +
				emailAddressId);
		}

		return emailAddress;
	}

	public EmailAddress fetchByPrimaryKey(long emailAddressId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (EmailAddress)session.get(EmailAddressImpl.class,
				new Long(emailAddressId));
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
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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

	public EmailAddress findByCompanyId_First(String companyId,
		OrderByComparator obc)
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
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress findByCompanyId_Last(String companyId,
		OrderByComparator obc)
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
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress[] findByCompanyId_PrevAndNext(long emailAddressId,
		String companyId, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		EmailAddress emailAddress = findByPrimaryKey(emailAddressId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					emailAddress);
			EmailAddress[] array = new EmailAddressImpl[3];
			array[0] = (EmailAddress)objArray[0];
			array[1] = (EmailAddress)objArray[1];
			array[2] = (EmailAddress)objArray[2];

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
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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

	public EmailAddress findByUserId_First(String userId, OrderByComparator obc)
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
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress findByUserId_Last(String userId, OrderByComparator obc)
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
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress[] findByUserId_PrevAndNext(long emailAddressId,
		String userId, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		EmailAddress emailAddress = findByPrimaryKey(emailAddressId);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					emailAddress);
			EmailAddress[] array = new EmailAddressImpl[3];
			array[0] = (EmailAddress)objArray[0];
			array[1] = (EmailAddress)objArray[1];
			array[2] = (EmailAddress)objArray[2];

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
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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

	public EmailAddress findByC_C_First(String companyId, String className,
		OrderByComparator obc)
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
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress findByC_C_Last(String companyId, String className,
		OrderByComparator obc)
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
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress[] findByC_C_PrevAndNext(long emailAddressId,
		String companyId, String className, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		EmailAddress emailAddress = findByPrimaryKey(emailAddressId);
		int count = countByC_C(companyId, className);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					emailAddress);
			EmailAddress[] array = new EmailAddressImpl[3];
			array[0] = (EmailAddress)objArray[0];
			array[1] = (EmailAddress)objArray[1];
			array[2] = (EmailAddress)objArray[2];

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
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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

	public EmailAddress findByC_C_C_First(String companyId, String className,
		String classPK, OrderByComparator obc)
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
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress findByC_C_C_Last(String companyId, String className,
		String classPK, OrderByComparator obc)
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
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress[] findByC_C_C_PrevAndNext(long emailAddressId,
		String companyId, String className, String classPK,
		OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		EmailAddress emailAddress = findByPrimaryKey(emailAddressId);
		int count = countByC_C_C(companyId, className, classPK);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					emailAddress);
			EmailAddress[] array = new EmailAddressImpl[3];
			array[0] = (EmailAddress)objArray[0];
			array[1] = (EmailAddress)objArray[1];
			array[2] = (EmailAddress)objArray[2];

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
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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

	public EmailAddress findByC_C_C_P_First(String companyId, String className,
		String classPK, boolean primary, OrderByComparator obc)
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
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress findByC_C_C_P_Last(String companyId, String className,
		String classPK, boolean primary, OrderByComparator obc)
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
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress[] findByC_C_C_P_PrevAndNext(long emailAddressId,
		String companyId, String className, String classPK, boolean primary,
		OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		EmailAddress emailAddress = findByPrimaryKey(emailAddressId);
		int count = countByC_C_C_P(companyId, className, classPK, primary);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					emailAddress);
			EmailAddress[] array = new EmailAddressImpl[3];
			array[0] = (EmailAddress)objArray[0];
			array[1] = (EmailAddress)objArray[1];
			array[2] = (EmailAddress)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer,
		int begin, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);
			query.setLimit(begin, end);

			return query.list();
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
			query.append("FROM com.liferay.portal.model.EmailAddress ");

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
			EmailAddress emailAddress = (EmailAddress)itr.next();
			remove(emailAddress);
		}
	}

	public void removeByUserId(String userId) throws SystemException {
		Iterator itr = findByUserId(userId).iterator();

		while (itr.hasNext()) {
			EmailAddress emailAddress = (EmailAddress)itr.next();
			remove(emailAddress);
		}
	}

	public void removeByC_C(String companyId, String className)
		throws SystemException {
		Iterator itr = findByC_C(companyId, className).iterator();

		while (itr.hasNext()) {
			EmailAddress emailAddress = (EmailAddress)itr.next();
			remove(emailAddress);
		}
	}

	public void removeByC_C_C(String companyId, String className, String classPK)
		throws SystemException {
		Iterator itr = findByC_C_C(companyId, className, classPK).iterator();

		while (itr.hasNext()) {
			EmailAddress emailAddress = (EmailAddress)itr.next();
			remove(emailAddress);
		}
	}

	public void removeByC_C_C_P(String companyId, String className,
		String classPK, boolean primary) throws SystemException {
		Iterator itr = findByC_C_C_P(companyId, className, classPK, primary)
						   .iterator();

		while (itr.hasNext()) {
			EmailAddress emailAddress = (EmailAddress)itr.next();
			remove(emailAddress);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((EmailAddress)itr.next());
		}
	}

	public int countByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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

	public int countByC_C_C_P(String companyId, String className,
		String classPK, boolean primary) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");

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

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.EmailAddress");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

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

	private static Log _log = LogFactory.getLog(EmailAddressPersistence.class);
}