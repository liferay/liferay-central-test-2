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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.messageboards.NoSuchMessageFlagException;

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
 * <a href="MBMessageFlagPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBMessageFlagPersistence extends BasePersistence {
	public com.liferay.portlet.messageboards.model.MBMessageFlag create(
		MBMessageFlagPK mbMessageFlagPK) {
		MBMessageFlagHBM mbMessageFlagHBM = new MBMessageFlagHBM();
		mbMessageFlagHBM.setNew(true);
		mbMessageFlagHBM.setPrimaryKey(mbMessageFlagPK);

		return MBMessageFlagHBMUtil.model(mbMessageFlagHBM);
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag remove(
		MBMessageFlagPK mbMessageFlagPK)
		throws NoSuchMessageFlagException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBMessageFlagHBM mbMessageFlagHBM = (MBMessageFlagHBM)session.get(MBMessageFlagHBM.class,
					mbMessageFlagPK);

			if (mbMessageFlagHBM == null) {
				_log.warn("No MBMessageFlag exists with the primary key " +
					mbMessageFlagPK.toString());
				throw new NoSuchMessageFlagException(
					"No MBMessageFlag exists with the primary key " +
					mbMessageFlagPK.toString());
			}

			session.delete(mbMessageFlagHBM);
			session.flush();

			return MBMessageFlagHBMUtil.model(mbMessageFlagHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag update(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws SystemException {
		Session session = null;

		try {
			if (mbMessageFlag.isNew() || mbMessageFlag.isModified()) {
				session = openSession();

				if (mbMessageFlag.isNew()) {
					MBMessageFlagHBM mbMessageFlagHBM = new MBMessageFlagHBM();
					mbMessageFlagHBM.setTopicId(mbMessageFlag.getTopicId());
					mbMessageFlagHBM.setMessageId(mbMessageFlag.getMessageId());
					mbMessageFlagHBM.setUserId(mbMessageFlag.getUserId());
					mbMessageFlagHBM.setFlag(mbMessageFlag.getFlag());
					session.save(mbMessageFlagHBM);
					session.flush();
				}
				else {
					MBMessageFlagHBM mbMessageFlagHBM = (MBMessageFlagHBM)session.get(MBMessageFlagHBM.class,
							mbMessageFlag.getPrimaryKey());

					if (mbMessageFlagHBM != null) {
						mbMessageFlagHBM.setFlag(mbMessageFlag.getFlag());
						session.flush();
					}
					else {
						mbMessageFlagHBM = new MBMessageFlagHBM();
						mbMessageFlagHBM.setTopicId(mbMessageFlag.getTopicId());
						mbMessageFlagHBM.setMessageId(mbMessageFlag.getMessageId());
						mbMessageFlagHBM.setUserId(mbMessageFlag.getUserId());
						mbMessageFlagHBM.setFlag(mbMessageFlag.getFlag());
						session.save(mbMessageFlagHBM);
						session.flush();
					}
				}

				mbMessageFlag.setNew(false);
				mbMessageFlag.setModified(false);
			}

			return mbMessageFlag;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByPrimaryKey(
		MBMessageFlagPK mbMessageFlagPK)
		throws NoSuchMessageFlagException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBMessageFlagHBM mbMessageFlagHBM = (MBMessageFlagHBM)session.get(MBMessageFlagHBM.class,
					mbMessageFlagPK);

			if (mbMessageFlagHBM == null) {
				_log.warn("No MBMessageFlag exists with the primary key " +
					mbMessageFlagPK.toString());
				throw new NoSuchMessageFlagException(
					"No MBMessageFlag exists with the primary key " +
					mbMessageFlagPK.toString());
			}

			return MBMessageFlagHBMUtil.model(mbMessageFlagHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByTopicId(String topicId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (topicId != null) {
				q.setString(queryPos++, topicId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				MBMessageFlagHBM mbMessageFlagHBM = (MBMessageFlagHBM)itr.next();
				list.add(MBMessageFlagHBMUtil.model(mbMessageFlagHBM));
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

	public List findByTopicId(String topicId, int begin, int end)
		throws SystemException {
		return findByTopicId(topicId, begin, end, null);
	}

	public List findByTopicId(String topicId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (topicId != null) {
				q.setString(queryPos++, topicId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				MBMessageFlagHBM mbMessageFlagHBM = (MBMessageFlagHBM)itr.next();
				list.add(MBMessageFlagHBMUtil.model(mbMessageFlagHBM));
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

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByTopicId_First(
		String topicId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		List list = findByTopicId(topicId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MBMessageFlag exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "topicId=";
			msg += topicId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchMessageFlagException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBMessageFlag)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByTopicId_Last(
		String topicId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		int count = countByTopicId(topicId);
		List list = findByTopicId(topicId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MBMessageFlag exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "topicId=";
			msg += topicId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchMessageFlagException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBMessageFlag)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag[] findByTopicId_PrevAndNext(
		MBMessageFlagPK mbMessageFlagPK, String topicId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag = findByPrimaryKey(mbMessageFlagPK);
		int count = countByTopicId(topicId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (topicId != null) {
				q.setString(queryPos++, topicId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessageFlag, MBMessageFlagHBMUtil.getInstance());
			com.liferay.portlet.messageboards.model.MBMessageFlag[] array = new com.liferay.portlet.messageboards.model.MBMessageFlag[3];
			array[0] = (com.liferay.portlet.messageboards.model.MBMessageFlag)objArray[0];
			array[1] = (com.liferay.portlet.messageboards.model.MBMessageFlag)objArray[1];
			array[2] = (com.liferay.portlet.messageboards.model.MBMessageFlag)objArray[2];

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
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

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
				MBMessageFlagHBM mbMessageFlagHBM = (MBMessageFlagHBM)itr.next();
				list.add(MBMessageFlagHBMUtil.model(mbMessageFlagHBM));
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
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

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
				MBMessageFlagHBM mbMessageFlagHBM = (MBMessageFlagHBM)itr.next();
				list.add(MBMessageFlagHBMUtil.model(mbMessageFlagHBM));
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

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByUserId_First(
		String userId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MBMessageFlag exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchMessageFlagException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBMessageFlag)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByUserId_Last(
		String userId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MBMessageFlag exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchMessageFlagException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBMessageFlag)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag[] findByUserId_PrevAndNext(
		MBMessageFlagPK mbMessageFlagPK, String userId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag = findByPrimaryKey(mbMessageFlagPK);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

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
					mbMessageFlag, MBMessageFlagHBMUtil.getInstance());
			com.liferay.portlet.messageboards.model.MBMessageFlag[] array = new com.liferay.portlet.messageboards.model.MBMessageFlag[3];
			array[0] = (com.liferay.portlet.messageboards.model.MBMessageFlag)objArray[0];
			array[1] = (com.liferay.portlet.messageboards.model.MBMessageFlag)objArray[1];
			array[2] = (com.liferay.portlet.messageboards.model.MBMessageFlag)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByT_M(String topicId, String messageId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" AND ");

			if (messageId == null) {
				query.append("messageId is null");
			}
			else {
				query.append("messageId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (topicId != null) {
				q.setString(queryPos++, topicId);
			}

			if (messageId != null) {
				q.setString(queryPos++, messageId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				MBMessageFlagHBM mbMessageFlagHBM = (MBMessageFlagHBM)itr.next();
				list.add(MBMessageFlagHBMUtil.model(mbMessageFlagHBM));
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

	public List findByT_M(String topicId, String messageId, int begin, int end)
		throws SystemException {
		return findByT_M(topicId, messageId, begin, end, null);
	}

	public List findByT_M(String topicId, String messageId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" AND ");

			if (messageId == null) {
				query.append("messageId is null");
			}
			else {
				query.append("messageId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (topicId != null) {
				q.setString(queryPos++, topicId);
			}

			if (messageId != null) {
				q.setString(queryPos++, messageId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				MBMessageFlagHBM mbMessageFlagHBM = (MBMessageFlagHBM)itr.next();
				list.add(MBMessageFlagHBMUtil.model(mbMessageFlagHBM));
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

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByT_M_First(
		String topicId, String messageId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		List list = findByT_M(topicId, messageId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MBMessageFlag exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "topicId=";
			msg += topicId;
			msg += ", ";
			msg += "messageId=";
			msg += messageId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchMessageFlagException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBMessageFlag)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByT_M_Last(
		String topicId, String messageId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		int count = countByT_M(topicId, messageId);
		List list = findByT_M(topicId, messageId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MBMessageFlag exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "topicId=";
			msg += topicId;
			msg += ", ";
			msg += "messageId=";
			msg += messageId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchMessageFlagException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBMessageFlag)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag[] findByT_M_PrevAndNext(
		MBMessageFlagPK mbMessageFlagPK, String topicId, String messageId,
		OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag = findByPrimaryKey(mbMessageFlagPK);
		int count = countByT_M(topicId, messageId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" AND ");

			if (messageId == null) {
				query.append("messageId is null");
			}
			else {
				query.append("messageId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (topicId != null) {
				q.setString(queryPos++, topicId);
			}

			if (messageId != null) {
				q.setString(queryPos++, messageId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessageFlag, MBMessageFlagHBMUtil.getInstance());
			com.liferay.portlet.messageboards.model.MBMessageFlag[] array = new com.liferay.portlet.messageboards.model.MBMessageFlag[3];
			array[0] = (com.liferay.portlet.messageboards.model.MBMessageFlag)objArray[0];
			array[1] = (com.liferay.portlet.messageboards.model.MBMessageFlag)objArray[1];
			array[2] = (com.liferay.portlet.messageboards.model.MBMessageFlag)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByT_U(String topicId, String userId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (topicId != null) {
				q.setString(queryPos++, topicId);
			}

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				MBMessageFlagHBM mbMessageFlagHBM = (MBMessageFlagHBM)itr.next();
				list.add(MBMessageFlagHBMUtil.model(mbMessageFlagHBM));
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

	public List findByT_U(String topicId, String userId, int begin, int end)
		throws SystemException {
		return findByT_U(topicId, userId, begin, end, null);
	}

	public List findByT_U(String topicId, String userId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" AND ");

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

			if (topicId != null) {
				q.setString(queryPos++, topicId);
			}

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				MBMessageFlagHBM mbMessageFlagHBM = (MBMessageFlagHBM)itr.next();
				list.add(MBMessageFlagHBMUtil.model(mbMessageFlagHBM));
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

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByT_U_First(
		String topicId, String userId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		List list = findByT_U(topicId, userId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MBMessageFlag exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "topicId=";
			msg += topicId;
			msg += ", ";
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchMessageFlagException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBMessageFlag)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByT_U_Last(
		String topicId, String userId, OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		int count = countByT_U(topicId, userId);
		List list = findByT_U(topicId, userId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MBMessageFlag exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "topicId=";
			msg += topicId;
			msg += ", ";
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchMessageFlagException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBMessageFlag)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag[] findByT_U_PrevAndNext(
		MBMessageFlagPK mbMessageFlagPK, String topicId, String userId,
		OrderByComparator obc)
		throws NoSuchMessageFlagException, SystemException {
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag = findByPrimaryKey(mbMessageFlagPK);
		int count = countByT_U(topicId, userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" AND ");

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

			if (topicId != null) {
				q.setString(queryPos++, topicId);
			}

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbMessageFlag, MBMessageFlagHBMUtil.getInstance());
			com.liferay.portlet.messageboards.model.MBMessageFlag[] array = new com.liferay.portlet.messageboards.model.MBMessageFlag[3];
			array[0] = (com.liferay.portlet.messageboards.model.MBMessageFlag)objArray[0];
			array[1] = (com.liferay.portlet.messageboards.model.MBMessageFlag)objArray[1];
			array[2] = (com.liferay.portlet.messageboards.model.MBMessageFlag)objArray[2];

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
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM ");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				MBMessageFlagHBM mbMessageFlagHBM = (MBMessageFlagHBM)itr.next();
				list.add(MBMessageFlagHBMUtil.model(mbMessageFlagHBM));
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

	public void removeByTopicId(String topicId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (topicId != null) {
				q.setString(queryPos++, topicId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				MBMessageFlagHBM mbMessageFlagHBM = (MBMessageFlagHBM)itr.next();
				session.delete(mbMessageFlagHBM);
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
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

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
				MBMessageFlagHBM mbMessageFlagHBM = (MBMessageFlagHBM)itr.next();
				session.delete(mbMessageFlagHBM);
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

	public void removeByT_M(String topicId, String messageId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" AND ");

			if (messageId == null) {
				query.append("messageId is null");
			}
			else {
				query.append("messageId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (topicId != null) {
				q.setString(queryPos++, topicId);
			}

			if (messageId != null) {
				q.setString(queryPos++, messageId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				MBMessageFlagHBM mbMessageFlagHBM = (MBMessageFlagHBM)itr.next();
				session.delete(mbMessageFlagHBM);
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

	public void removeByT_U(String topicId, String userId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (topicId != null) {
				q.setString(queryPos++, topicId);
			}

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				MBMessageFlagHBM mbMessageFlagHBM = (MBMessageFlagHBM)itr.next();
				session.delete(mbMessageFlagHBM);
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

	public int countByTopicId(String topicId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (topicId != null) {
				q.setString(queryPos++, topicId);
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
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

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

	public int countByT_M(String topicId, String messageId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" AND ");

			if (messageId == null) {
				query.append("messageId is null");
			}
			else {
				query.append("messageId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (topicId != null) {
				q.setString(queryPos++, topicId);
			}

			if (messageId != null) {
				q.setString(queryPos++, messageId);
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

	public int countByT_U(String topicId, String userId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM MBMessageFlag IN CLASS com.liferay.portlet.messageboards.service.persistence.MBMessageFlagHBM WHERE ");

			if (topicId == null) {
				query.append("topicId is null");
			}
			else {
				query.append("topicId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId is null");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (topicId != null) {
				q.setString(queryPos++, topicId);
			}

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

	private static Log _log = LogFactory.getLog(MBMessageFlagPersistence.class);
}