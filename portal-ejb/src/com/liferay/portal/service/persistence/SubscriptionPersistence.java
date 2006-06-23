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

import com.liferay.portal.NoSuchSubscriptionException;
import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="SubscriptionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SubscriptionPersistence extends BasePersistence {
	public com.liferay.portal.model.Subscription create(String subscriptionId) {
		SubscriptionHBM subscriptionHBM = new SubscriptionHBM();
		subscriptionHBM.setNew(true);
		subscriptionHBM.setPrimaryKey(subscriptionId);

		return SubscriptionHBMUtil.model(subscriptionHBM);
	}

	public com.liferay.portal.model.Subscription remove(String subscriptionId)
		throws NoSuchSubscriptionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SubscriptionHBM subscriptionHBM = (SubscriptionHBM)session.get(SubscriptionHBM.class,
					subscriptionId);

			if (subscriptionHBM == null) {
				_log.warn("No Subscription exists with the primary key " +
					subscriptionId.toString());
				throw new NoSuchSubscriptionException(
					"No Subscription exists with the primary key " +
					subscriptionId.toString());
			}

			session.delete(subscriptionHBM);
			session.flush();

			return SubscriptionHBMUtil.model(subscriptionHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Subscription update(
		com.liferay.portal.model.Subscription subscription)
		throws SystemException {
		Session session = null;

		try {
			if (subscription.isNew() || subscription.isModified()) {
				session = openSession();

				if (subscription.isNew()) {
					SubscriptionHBM subscriptionHBM = new SubscriptionHBM();
					subscriptionHBM.setSubscriptionId(subscription.getSubscriptionId());
					subscriptionHBM.setCompanyId(subscription.getCompanyId());
					subscriptionHBM.setUserId(subscription.getUserId());
					subscriptionHBM.setUserName(subscription.getUserName());
					subscriptionHBM.setCreateDate(subscription.getCreateDate());
					subscriptionHBM.setModifiedDate(subscription.getModifiedDate());
					subscriptionHBM.setClassName(subscription.getClassName());
					subscriptionHBM.setClassPK(subscription.getClassPK());
					subscriptionHBM.setFrequency(subscription.getFrequency());
					session.save(subscriptionHBM);
					session.flush();
				}
				else {
					SubscriptionHBM subscriptionHBM = (SubscriptionHBM)session.get(SubscriptionHBM.class,
							subscription.getPrimaryKey());

					if (subscriptionHBM != null) {
						subscriptionHBM.setCompanyId(subscription.getCompanyId());
						subscriptionHBM.setUserId(subscription.getUserId());
						subscriptionHBM.setUserName(subscription.getUserName());
						subscriptionHBM.setCreateDate(subscription.getCreateDate());
						subscriptionHBM.setModifiedDate(subscription.getModifiedDate());
						subscriptionHBM.setClassName(subscription.getClassName());
						subscriptionHBM.setClassPK(subscription.getClassPK());
						subscriptionHBM.setFrequency(subscription.getFrequency());
						session.flush();
					}
					else {
						subscriptionHBM = new SubscriptionHBM();
						subscriptionHBM.setSubscriptionId(subscription.getSubscriptionId());
						subscriptionHBM.setCompanyId(subscription.getCompanyId());
						subscriptionHBM.setUserId(subscription.getUserId());
						subscriptionHBM.setUserName(subscription.getUserName());
						subscriptionHBM.setCreateDate(subscription.getCreateDate());
						subscriptionHBM.setModifiedDate(subscription.getModifiedDate());
						subscriptionHBM.setClassName(subscription.getClassName());
						subscriptionHBM.setClassPK(subscription.getClassPK());
						subscriptionHBM.setFrequency(subscription.getFrequency());
						session.save(subscriptionHBM);
						session.flush();
					}
				}

				subscription.setNew(false);
				subscription.setModified(false);
			}

			return subscription;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Subscription findByPrimaryKey(
		String subscriptionId)
		throws NoSuchSubscriptionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SubscriptionHBM subscriptionHBM = (SubscriptionHBM)session.get(SubscriptionHBM.class,
					subscriptionId);

			if (subscriptionHBM == null) {
				_log.warn("No Subscription exists with the primary key " +
					subscriptionId.toString());
				throw new NoSuchSubscriptionException(
					"No Subscription exists with the primary key " +
					subscriptionId.toString());
			}

			return SubscriptionHBMUtil.model(subscriptionHBM);
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
				"FROM Subscription IN CLASS com.liferay.portal.service.persistence.SubscriptionHBM WHERE ");

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
			List list = new ArrayList();

			while (itr.hasNext()) {
				SubscriptionHBM subscriptionHBM = (SubscriptionHBM)itr.next();
				list.add(SubscriptionHBMUtil.model(subscriptionHBM));
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
				"FROM Subscription IN CLASS com.liferay.portal.service.persistence.SubscriptionHBM WHERE ");

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

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				SubscriptionHBM subscriptionHBM = (SubscriptionHBM)itr.next();
				list.add(SubscriptionHBMUtil.model(subscriptionHBM));
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

	public com.liferay.portal.model.Subscription findByUserId_First(
		String userId, OrderByComparator obc)
		throws NoSuchSubscriptionException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Subscription exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchSubscriptionException(msg);
		}
		else {
			return (com.liferay.portal.model.Subscription)list.get(0);
		}
	}

	public com.liferay.portal.model.Subscription findByUserId_Last(
		String userId, OrderByComparator obc)
		throws NoSuchSubscriptionException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Subscription exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchSubscriptionException(msg);
		}
		else {
			return (com.liferay.portal.model.Subscription)list.get(0);
		}
	}

	public com.liferay.portal.model.Subscription[] findByUserId_PrevAndNext(
		String subscriptionId, String userId, OrderByComparator obc)
		throws NoSuchSubscriptionException, SystemException {
		com.liferay.portal.model.Subscription subscription = findByPrimaryKey(subscriptionId);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Subscription IN CLASS com.liferay.portal.service.persistence.SubscriptionHBM WHERE ");

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

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					subscription, SubscriptionHBMUtil.getInstance());
			com.liferay.portal.model.Subscription[] array = new com.liferay.portal.model.Subscription[3];
			array[0] = (com.liferay.portal.model.Subscription)objArray[0];
			array[1] = (com.liferay.portal.model.Subscription)objArray[1];
			array[2] = (com.liferay.portal.model.Subscription)objArray[2];

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
				"FROM Subscription IN CLASS com.liferay.portal.service.persistence.SubscriptionHBM WHERE ");

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
			List list = new ArrayList();

			while (itr.hasNext()) {
				SubscriptionHBM subscriptionHBM = (SubscriptionHBM)itr.next();
				list.add(SubscriptionHBMUtil.model(subscriptionHBM));
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
				"FROM Subscription IN CLASS com.liferay.portal.service.persistence.SubscriptionHBM WHERE ");

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
				SubscriptionHBM subscriptionHBM = (SubscriptionHBM)itr.next();
				list.add(SubscriptionHBMUtil.model(subscriptionHBM));
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

	public com.liferay.portal.model.Subscription findByC_C_C_First(
		String companyId, String className, String classPK,
		OrderByComparator obc)
		throws NoSuchSubscriptionException, SystemException {
		List list = findByC_C_C(companyId, className, classPK, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Subscription exists with the key ";
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
			throw new NoSuchSubscriptionException(msg);
		}
		else {
			return (com.liferay.portal.model.Subscription)list.get(0);
		}
	}

	public com.liferay.portal.model.Subscription findByC_C_C_Last(
		String companyId, String className, String classPK,
		OrderByComparator obc)
		throws NoSuchSubscriptionException, SystemException {
		int count = countByC_C_C(companyId, className, classPK);
		List list = findByC_C_C(companyId, className, classPK, count - 1,
				count, obc);

		if (list.size() == 0) {
			String msg = "No Subscription exists with the key ";
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
			throw new NoSuchSubscriptionException(msg);
		}
		else {
			return (com.liferay.portal.model.Subscription)list.get(0);
		}
	}

	public com.liferay.portal.model.Subscription[] findByC_C_C_PrevAndNext(
		String subscriptionId, String companyId, String className,
		String classPK, OrderByComparator obc)
		throws NoSuchSubscriptionException, SystemException {
		com.liferay.portal.model.Subscription subscription = findByPrimaryKey(subscriptionId);
		int count = countByC_C_C(companyId, className, classPK);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Subscription IN CLASS com.liferay.portal.service.persistence.SubscriptionHBM WHERE ");

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
					subscription, SubscriptionHBMUtil.getInstance());
			com.liferay.portal.model.Subscription[] array = new com.liferay.portal.model.Subscription[3];
			array[0] = (com.liferay.portal.model.Subscription)objArray[0];
			array[1] = (com.liferay.portal.model.Subscription)objArray[1];
			array[2] = (com.liferay.portal.model.Subscription)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Subscription findByC_U_C_C(
		String companyId, String userId, String className, String classPK)
		throws NoSuchSubscriptionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Subscription IN CLASS com.liferay.portal.service.persistence.SubscriptionHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
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

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
			}

			Iterator itr = q.list().iterator();

			if (!itr.hasNext()) {
				String msg = "No Subscription exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "companyId=";
				msg += companyId;
				msg += ", ";
				msg += "userId=";
				msg += userId;
				msg += ", ";
				msg += "className=";
				msg += className;
				msg += ", ";
				msg += "classPK=";
				msg += classPK;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchSubscriptionException(msg);
			}

			SubscriptionHBM subscriptionHBM = (SubscriptionHBM)itr.next();

			return SubscriptionHBMUtil.model(subscriptionHBM);
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
				"FROM Subscription IN CLASS com.liferay.portal.service.persistence.SubscriptionHBM ");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				SubscriptionHBM subscriptionHBM = (SubscriptionHBM)itr.next();
				list.add(SubscriptionHBMUtil.model(subscriptionHBM));
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

	public void removeByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Subscription IN CLASS com.liferay.portal.service.persistence.SubscriptionHBM WHERE ");

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

			while (itr.hasNext()) {
				SubscriptionHBM subscriptionHBM = (SubscriptionHBM)itr.next();
				session.delete(subscriptionHBM);
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
				"FROM Subscription IN CLASS com.liferay.portal.service.persistence.SubscriptionHBM WHERE ");

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

			while (itr.hasNext()) {
				SubscriptionHBM subscriptionHBM = (SubscriptionHBM)itr.next();
				session.delete(subscriptionHBM);
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

	public void removeByC_U_C_C(String companyId, String userId,
		String className, String classPK)
		throws NoSuchSubscriptionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Subscription IN CLASS com.liferay.portal.service.persistence.SubscriptionHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
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

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				SubscriptionHBM subscriptionHBM = (SubscriptionHBM)itr.next();
				session.delete(subscriptionHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			if (he instanceof ObjectNotFoundException) {
				String msg = "No Subscription exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "companyId=";
				msg += companyId;
				msg += ", ";
				msg += "userId=";
				msg += userId;
				msg += ", ";
				msg += "className=";
				msg += className;
				msg += ", ";
				msg += "classPK=";
				msg += classPK;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchSubscriptionException(msg);
			}
			else {
				throw new SystemException(he);
			}
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
				"FROM Subscription IN CLASS com.liferay.portal.service.persistence.SubscriptionHBM WHERE ");

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

	public int countByC_C_C(String companyId, String className, String classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Subscription IN CLASS com.liferay.portal.service.persistence.SubscriptionHBM WHERE ");

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

	public int countByC_U_C_C(String companyId, String userId,
		String className, String classPK) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Subscription IN CLASS com.liferay.portal.service.persistence.SubscriptionHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
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

			if (userId != null) {
				q.setString(queryPos++, userId);
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

	private static Log _log = LogFactory.getLog(SubscriptionPersistence.class);
}