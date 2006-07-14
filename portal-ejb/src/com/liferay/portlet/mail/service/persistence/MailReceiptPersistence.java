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

package com.liferay.portlet.mail.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.mail.NoSuchReceiptException;
import com.liferay.portlet.mail.model.MailReceipt;

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
 * <a href="MailReceiptPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MailReceiptPersistence extends BasePersistence {
	public MailReceipt create(String receiptId) {
		MailReceipt mailReceipt = new MailReceipt();
		mailReceipt.setNew(true);
		mailReceipt.setPrimaryKey(receiptId);

		return mailReceipt;
	}

	public MailReceipt remove(String receiptId)
		throws NoSuchReceiptException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MailReceipt mailReceipt = (MailReceipt)session.get(MailReceipt.class,
					receiptId);

			if (mailReceipt == null) {
				_log.warn("No MailReceipt exists with the primary key " +
					receiptId.toString());
				throw new NoSuchReceiptException(
					"No MailReceipt exists with the primary key " +
					receiptId.toString());
			}

			session.delete(mailReceipt);
			session.flush();

			return mailReceipt;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.mail.model.MailReceipt update(
		com.liferay.portlet.mail.model.MailReceipt mailReceipt)
		throws SystemException {
		Session session = null;

		try {
			if (mailReceipt.isNew() || mailReceipt.isModified()) {
				session = openSession();

				if (mailReceipt.isNew()) {
					MailReceipt mailReceiptModel = new MailReceipt();
					mailReceiptModel.setReceiptId(mailReceipt.getReceiptId());
					mailReceiptModel.setCompanyId(mailReceipt.getCompanyId());
					mailReceiptModel.setUserId(mailReceipt.getUserId());
					mailReceiptModel.setCreateDate(mailReceipt.getCreateDate());
					mailReceiptModel.setModifiedDate(mailReceipt.getModifiedDate());
					mailReceiptModel.setRecipientName(mailReceipt.getRecipientName());
					mailReceiptModel.setRecipientAddress(mailReceipt.getRecipientAddress());
					mailReceiptModel.setSubject(mailReceipt.getSubject());
					mailReceiptModel.setSentDate(mailReceipt.getSentDate());
					mailReceiptModel.setReadCount(mailReceipt.getReadCount());
					mailReceiptModel.setFirstReadDate(mailReceipt.getFirstReadDate());
					mailReceiptModel.setLastReadDate(mailReceipt.getLastReadDate());
					session.save(mailReceiptModel);
					session.flush();
				}
				else {
					MailReceipt mailReceiptModel = (MailReceipt)session.get(MailReceipt.class,
							mailReceipt.getPrimaryKey());

					if (mailReceiptModel != null) {
						mailReceiptModel.setCompanyId(mailReceipt.getCompanyId());
						mailReceiptModel.setUserId(mailReceipt.getUserId());
						mailReceiptModel.setCreateDate(mailReceipt.getCreateDate());
						mailReceiptModel.setModifiedDate(mailReceipt.getModifiedDate());
						mailReceiptModel.setRecipientName(mailReceipt.getRecipientName());
						mailReceiptModel.setRecipientAddress(mailReceipt.getRecipientAddress());
						mailReceiptModel.setSubject(mailReceipt.getSubject());
						mailReceiptModel.setSentDate(mailReceipt.getSentDate());
						mailReceiptModel.setReadCount(mailReceipt.getReadCount());
						mailReceiptModel.setFirstReadDate(mailReceipt.getFirstReadDate());
						mailReceiptModel.setLastReadDate(mailReceipt.getLastReadDate());
						session.flush();
					}
					else {
						mailReceiptModel = new MailReceipt();
						mailReceiptModel.setReceiptId(mailReceipt.getReceiptId());
						mailReceiptModel.setCompanyId(mailReceipt.getCompanyId());
						mailReceiptModel.setUserId(mailReceipt.getUserId());
						mailReceiptModel.setCreateDate(mailReceipt.getCreateDate());
						mailReceiptModel.setModifiedDate(mailReceipt.getModifiedDate());
						mailReceiptModel.setRecipientName(mailReceipt.getRecipientName());
						mailReceiptModel.setRecipientAddress(mailReceipt.getRecipientAddress());
						mailReceiptModel.setSubject(mailReceipt.getSubject());
						mailReceiptModel.setSentDate(mailReceipt.getSentDate());
						mailReceiptModel.setReadCount(mailReceipt.getReadCount());
						mailReceiptModel.setFirstReadDate(mailReceipt.getFirstReadDate());
						mailReceiptModel.setLastReadDate(mailReceipt.getLastReadDate());
						session.save(mailReceiptModel);
						session.flush();
					}
				}

				mailReceipt.setNew(false);
				mailReceipt.setModified(false);
			}

			return mailReceipt;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public MailReceipt findByPrimaryKey(String receiptId)
		throws NoSuchReceiptException, SystemException {
		return findByPrimaryKey(receiptId, true);
	}

	public MailReceipt findByPrimaryKey(String receiptId,
		boolean throwNoSuchObjectException)
		throws NoSuchReceiptException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MailReceipt mailReceipt = (MailReceipt)session.get(MailReceipt.class,
					receiptId);

			if (mailReceipt == null) {
				_log.warn("No MailReceipt exists with the primary key " +
					receiptId.toString());

				if (throwNoSuchObjectException) {
					throw new NoSuchReceiptException(
						"No MailReceipt exists with the primary key " +
						receiptId.toString());
				}
			}

			return mailReceipt;
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
				"FROM com.liferay.portlet.mail.model.MailReceipt WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
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
			query.append(
				"FROM com.liferay.portlet.mail.model.MailReceipt WHERE ");

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
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
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

	public MailReceipt findByCompanyId_First(String companyId,
		OrderByComparator obc) throws NoSuchReceiptException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MailReceipt exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchReceiptException(msg);
		}
		else {
			return (MailReceipt)list.get(0);
		}
	}

	public MailReceipt findByCompanyId_Last(String companyId,
		OrderByComparator obc) throws NoSuchReceiptException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MailReceipt exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchReceiptException(msg);
		}
		else {
			return (MailReceipt)list.get(0);
		}
	}

	public MailReceipt[] findByCompanyId_PrevAndNext(String receiptId,
		String companyId, OrderByComparator obc)
		throws NoSuchReceiptException, SystemException {
		MailReceipt mailReceipt = findByPrimaryKey(receiptId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.mail.model.MailReceipt WHERE ");

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
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mailReceipt);
			MailReceipt[] array = new MailReceipt[3];
			array[0] = (MailReceipt)objArray[0];
			array[1] = (MailReceipt)objArray[1];
			array[2] = (MailReceipt)objArray[2];

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
				"FROM com.liferay.portlet.mail.model.MailReceipt WHERE ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
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
			query.append(
				"FROM com.liferay.portlet.mail.model.MailReceipt WHERE ");

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
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
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

	public MailReceipt findByUserId_First(String userId, OrderByComparator obc)
		throws NoSuchReceiptException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MailReceipt exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchReceiptException(msg);
		}
		else {
			return (MailReceipt)list.get(0);
		}
	}

	public MailReceipt findByUserId_Last(String userId, OrderByComparator obc)
		throws NoSuchReceiptException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MailReceipt exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchReceiptException(msg);
		}
		else {
			return (MailReceipt)list.get(0);
		}
	}

	public MailReceipt[] findByUserId_PrevAndNext(String receiptId,
		String userId, OrderByComparator obc)
		throws NoSuchReceiptException, SystemException {
		MailReceipt mailReceipt = findByPrimaryKey(receiptId);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.mail.model.MailReceipt WHERE ");

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
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mailReceipt);
			MailReceipt[] array = new MailReceipt[3];
			array[0] = (MailReceipt)objArray[0];
			array[1] = (MailReceipt)objArray[1];
			array[2] = (MailReceipt)objArray[2];

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
			query.append("FROM com.liferay.portlet.mail.model.MailReceipt ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());

			return q.list();
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
				"FROM com.liferay.portlet.mail.model.MailReceipt WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				MailReceipt mailReceipt = (MailReceipt)itr.next();
				session.delete(mailReceipt);
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
				"FROM com.liferay.portlet.mail.model.MailReceipt WHERE ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				MailReceipt mailReceipt = (MailReceipt)itr.next();
				session.delete(mailReceipt);
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
				"FROM com.liferay.portlet.mail.model.MailReceipt WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
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
				"FROM com.liferay.portlet.mail.model.MailReceipt WHERE ");

			if (userId == null) {
				query.append("userId IS NULL");
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

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(MailReceiptPersistence.class);
}