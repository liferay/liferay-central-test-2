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
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.impl.EmailAddressImpl;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="EmailAddressPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EmailAddressPersistenceImpl extends BasePersistence
	implements EmailAddressPersistence {
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
		catch (NoSuchEmailAddressException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(long companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
			query.append("companyId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, companyId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(long companyId, int begin, int end)
		throws SystemException {
		return findByCompanyId(companyId, begin, end, null);
	}

	public List findByCompanyId(long companyId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
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
			int queryPos = 0;
			q.setLong(queryPos++, companyId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public EmailAddress findByCompanyId_First(long companyId,
		OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No EmailAddress exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchEmailAddressException(msg.toString());
		}
		else {
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress findByCompanyId_Last(long companyId,
		OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No EmailAddress exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchEmailAddressException(msg.toString());
		}
		else {
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress[] findByCompanyId_PrevAndNext(long emailAddressId,
		long companyId, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		EmailAddress emailAddress = findByPrimaryKey(emailAddressId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
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
			int queryPos = 0;
			q.setLong(queryPos++, companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					emailAddress);
			EmailAddress[] array = new EmailAddressImpl[3];
			array[0] = (EmailAddress)objArray[0];
			array[1] = (EmailAddress)objArray[1];
			array[2] = (EmailAddress)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserId(long userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
			query.append("userId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, userId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserId(long userId, int begin, int end)
		throws SystemException {
		return findByUserId(userId, begin, end, null);
	}

	public List findByUserId(long userId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
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
			int queryPos = 0;
			q.setLong(queryPos++, userId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public EmailAddress findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No EmailAddress exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("userId=");
			msg.append(userId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchEmailAddressException(msg.toString());
		}
		else {
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No EmailAddress exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("userId=");
			msg.append(userId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchEmailAddressException(msg.toString());
		}
		else {
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress[] findByUserId_PrevAndNext(long emailAddressId,
		long userId, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		EmailAddress emailAddress = findByPrimaryKey(emailAddressId);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
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
			int queryPos = 0;
			q.setLong(queryPos++, userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					emailAddress);
			EmailAddress[] array = new EmailAddressImpl[3];
			array[0] = (EmailAddress)objArray[0];
			array[1] = (EmailAddress)objArray[1];
			array[2] = (EmailAddress)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C(long companyId, long classNameId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("classNameId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C(long companyId, long classNameId, int begin, int end)
		throws SystemException {
		return findByC_C(companyId, classNameId, begin, end, null);
	}

	public List findByC_C(long companyId, long classNameId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
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
			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public EmailAddress findByC_C_First(long companyId, long classNameId,
		OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		List list = findByC_C(companyId, classNameId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No EmailAddress exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("classNameId=");
			msg.append(classNameId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchEmailAddressException(msg.toString());
		}
		else {
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress findByC_C_Last(long companyId, long classNameId,
		OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		int count = countByC_C(companyId, classNameId);
		List list = findByC_C(companyId, classNameId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No EmailAddress exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("classNameId=");
			msg.append(classNameId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchEmailAddressException(msg.toString());
		}
		else {
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress[] findByC_C_PrevAndNext(long emailAddressId,
		long companyId, long classNameId, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		EmailAddress emailAddress = findByPrimaryKey(emailAddressId);
		int count = countByC_C(companyId, classNameId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
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
			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					emailAddress);
			EmailAddress[] array = new EmailAddressImpl[3];
			array[0] = (EmailAddress)objArray[0];
			array[1] = (EmailAddress)objArray[1];
			array[2] = (EmailAddress)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("classNameId = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C_C(long companyId, long classNameId, long classPK,
		int begin, int end) throws SystemException {
		return findByC_C_C(companyId, classNameId, classPK, begin, end, null);
	}

	public List findByC_C_C(long companyId, long classNameId, long classPK,
		int begin, int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
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
			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public EmailAddress findByC_C_C_First(long companyId, long classNameId,
		long classPK, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		List list = findByC_C_C(companyId, classNameId, classPK, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No EmailAddress exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("classNameId=");
			msg.append(classNameId);
			msg.append(", ");
			msg.append("classPK=");
			msg.append(classPK);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchEmailAddressException(msg.toString());
		}
		else {
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress findByC_C_C_Last(long companyId, long classNameId,
		long classPK, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		int count = countByC_C_C(companyId, classNameId, classPK);
		List list = findByC_C_C(companyId, classNameId, classPK, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No EmailAddress exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("classNameId=");
			msg.append(classNameId);
			msg.append(", ");
			msg.append("classPK=");
			msg.append(classPK);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchEmailAddressException(msg.toString());
		}
		else {
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress[] findByC_C_C_PrevAndNext(long emailAddressId,
		long companyId, long classNameId, long classPK, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		EmailAddress emailAddress = findByPrimaryKey(emailAddressId);
		int count = countByC_C_C(companyId, classNameId, classPK);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
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
			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					emailAddress);
			EmailAddress[] array = new EmailAddressImpl[3];
			array[0] = (EmailAddress)objArray[0];
			array[1] = (EmailAddress)objArray[1];
			array[2] = (EmailAddress)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
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
			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);
			q.setBoolean(queryPos++, primary);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary, int begin, int end) throws SystemException {
		return findByC_C_C_P(companyId, classNameId, classPK, primary, begin,
			end, null);
	}

	public List findByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary, int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
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
			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);
			q.setBoolean(queryPos++, primary);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public EmailAddress findByC_C_C_P_First(long companyId, long classNameId,
		long classPK, boolean primary, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		List list = findByC_C_C_P(companyId, classNameId, classPK, primary, 0,
				1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No EmailAddress exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("classNameId=");
			msg.append(classNameId);
			msg.append(", ");
			msg.append("classPK=");
			msg.append(classPK);
			msg.append(", ");
			msg.append("primary=");
			msg.append(primary);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchEmailAddressException(msg.toString());
		}
		else {
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress findByC_C_C_P_Last(long companyId, long classNameId,
		long classPK, boolean primary, OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		int count = countByC_C_C_P(companyId, classNameId, classPK, primary);
		List list = findByC_C_C_P(companyId, classNameId, classPK, primary,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No EmailAddress exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("classNameId=");
			msg.append(classNameId);
			msg.append(", ");
			msg.append("classPK=");
			msg.append(classPK);
			msg.append(", ");
			msg.append("primary=");
			msg.append(primary);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchEmailAddressException(msg.toString());
		}
		else {
			return (EmailAddress)list.get(0);
		}
	}

	public EmailAddress[] findByC_C_C_P_PrevAndNext(long emailAddressId,
		long companyId, long classNameId, long classPK, boolean primary,
		OrderByComparator obc)
		throws NoSuchEmailAddressException, SystemException {
		EmailAddress emailAddress = findByPrimaryKey(emailAddressId);
		int count = countByC_C_C_P(companyId, classNameId, classPK, primary);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
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
			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);
			q.setBoolean(queryPos++, primary);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					emailAddress);
			EmailAddress[] array = new EmailAddressImpl[3];
			array[0] = (EmailAddress)objArray[0];
			array[1] = (EmailAddress)objArray[1];
			array[2] = (EmailAddress)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.EmailAddress ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		Iterator itr = findByCompanyId(companyId).iterator();

		while (itr.hasNext()) {
			EmailAddress emailAddress = (EmailAddress)itr.next();
			remove(emailAddress);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		Iterator itr = findByUserId(userId).iterator();

		while (itr.hasNext()) {
			EmailAddress emailAddress = (EmailAddress)itr.next();
			remove(emailAddress);
		}
	}

	public void removeByC_C(long companyId, long classNameId)
		throws SystemException {
		Iterator itr = findByC_C(companyId, classNameId).iterator();

		while (itr.hasNext()) {
			EmailAddress emailAddress = (EmailAddress)itr.next();
			remove(emailAddress);
		}
	}

	public void removeByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		Iterator itr = findByC_C_C(companyId, classNameId, classPK).iterator();

		while (itr.hasNext()) {
			EmailAddress emailAddress = (EmailAddress)itr.next();
			remove(emailAddress);
		}
	}

	public void removeByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary) throws SystemException {
		Iterator itr = findByC_C_C_P(companyId, classNameId, classPK, primary)
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

	public int countByCompanyId(long companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, companyId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByUserId(long userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
			query.append("userId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, userId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_C(long companyId, long classNameId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("classNameId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("classNameId = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.EmailAddress WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("classNameId = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" AND ");
			query.append("primary_ = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.EmailAddress");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(EmailAddressPersistenceImpl.class);
}