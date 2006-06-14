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

import com.liferay.portlet.messageboards.NoSuchTopicException;

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
 * <a href="MBTopicPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBTopicPersistence extends BasePersistence {
	public com.liferay.portlet.messageboards.model.MBTopic create(
		String topicId) {
		MBTopicHBM mbTopicHBM = new MBTopicHBM();
		mbTopicHBM.setNew(true);
		mbTopicHBM.setPrimaryKey(topicId);

		return MBTopicHBMUtil.model(mbTopicHBM);
	}

	public com.liferay.portlet.messageboards.model.MBTopic remove(
		String topicId) throws NoSuchTopicException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBTopicHBM mbTopicHBM = (MBTopicHBM)session.get(MBTopicHBM.class,
					topicId);

			if (mbTopicHBM == null) {
				_log.warn("No MBTopic exists with the primary key " +
					topicId.toString());
				throw new NoSuchTopicException(
					"No MBTopic exists with the primary key " +
					topicId.toString());
			}

			session.delete(mbTopicHBM);
			session.flush();

			return MBTopicHBMUtil.model(mbTopicHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.messageboards.model.MBTopic update(
		com.liferay.portlet.messageboards.model.MBTopic mbTopic)
		throws SystemException {
		Session session = null;

		try {
			if (mbTopic.isNew() || mbTopic.isModified()) {
				session = openSession();

				if (mbTopic.isNew()) {
					MBTopicHBM mbTopicHBM = new MBTopicHBM();
					mbTopicHBM.setTopicId(mbTopic.getTopicId());
					mbTopicHBM.setCompanyId(mbTopic.getCompanyId());
					mbTopicHBM.setUserId(mbTopic.getUserId());
					mbTopicHBM.setUserName(mbTopic.getUserName());
					mbTopicHBM.setCreateDate(mbTopic.getCreateDate());
					mbTopicHBM.setModifiedDate(mbTopic.getModifiedDate());
					mbTopicHBM.setCategoryId(mbTopic.getCategoryId());
					mbTopicHBM.setName(mbTopic.getName());
					mbTopicHBM.setDescription(mbTopic.getDescription());
					mbTopicHBM.setLastPostDate(mbTopic.getLastPostDate());
					session.save(mbTopicHBM);
					session.flush();
				}
				else {
					MBTopicHBM mbTopicHBM = (MBTopicHBM)session.get(MBTopicHBM.class,
							mbTopic.getPrimaryKey());

					if (mbTopicHBM != null) {
						mbTopicHBM.setCompanyId(mbTopic.getCompanyId());
						mbTopicHBM.setUserId(mbTopic.getUserId());
						mbTopicHBM.setUserName(mbTopic.getUserName());
						mbTopicHBM.setCreateDate(mbTopic.getCreateDate());
						mbTopicHBM.setModifiedDate(mbTopic.getModifiedDate());
						mbTopicHBM.setCategoryId(mbTopic.getCategoryId());
						mbTopicHBM.setName(mbTopic.getName());
						mbTopicHBM.setDescription(mbTopic.getDescription());
						mbTopicHBM.setLastPostDate(mbTopic.getLastPostDate());
						session.flush();
					}
					else {
						mbTopicHBM = new MBTopicHBM();
						mbTopicHBM.setTopicId(mbTopic.getTopicId());
						mbTopicHBM.setCompanyId(mbTopic.getCompanyId());
						mbTopicHBM.setUserId(mbTopic.getUserId());
						mbTopicHBM.setUserName(mbTopic.getUserName());
						mbTopicHBM.setCreateDate(mbTopic.getCreateDate());
						mbTopicHBM.setModifiedDate(mbTopic.getModifiedDate());
						mbTopicHBM.setCategoryId(mbTopic.getCategoryId());
						mbTopicHBM.setName(mbTopic.getName());
						mbTopicHBM.setDescription(mbTopic.getDescription());
						mbTopicHBM.setLastPostDate(mbTopic.getLastPostDate());
						session.save(mbTopicHBM);
						session.flush();
					}
				}

				mbTopic.setNew(false);
				mbTopic.setModified(false);
			}

			return mbTopic;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.messageboards.model.MBTopic findByPrimaryKey(
		String topicId) throws NoSuchTopicException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBTopicHBM mbTopicHBM = (MBTopicHBM)session.get(MBTopicHBM.class,
					topicId);

			if (mbTopicHBM == null) {
				_log.warn("No MBTopic exists with the primary key " +
					topicId.toString());
				throw new NoSuchTopicException(
					"No MBTopic exists with the primary key " +
					topicId.toString());
			}

			return MBTopicHBMUtil.model(mbTopicHBM);
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
				"FROM MBTopic IN CLASS com.liferay.portlet.messageboards.service.persistence.MBTopicHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				MBTopicHBM mbTopicHBM = (MBTopicHBM)itr.next();
				list.add(MBTopicHBMUtil.model(mbTopicHBM));
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
				"FROM MBTopic IN CLASS com.liferay.portlet.messageboards.service.persistence.MBTopicHBM WHERE ");
			query.append("companyId = ?");
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
			q.setString(queryPos++, companyId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				MBTopicHBM mbTopicHBM = (MBTopicHBM)itr.next();
				list.add(MBTopicHBMUtil.model(mbTopicHBM));
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

	public com.liferay.portlet.messageboards.model.MBTopic findByCompanyId_First(
		String companyId, OrderByComparator obc)
		throws NoSuchTopicException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MBTopic exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchTopicException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBTopic)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBTopic findByCompanyId_Last(
		String companyId, OrderByComparator obc)
		throws NoSuchTopicException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MBTopic exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchTopicException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBTopic)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBTopic[] findByCompanyId_PrevAndNext(
		String topicId, String companyId, OrderByComparator obc)
		throws NoSuchTopicException, SystemException {
		com.liferay.portlet.messageboards.model.MBTopic mbTopic = findByPrimaryKey(topicId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBTopic IN CLASS com.liferay.portlet.messageboards.service.persistence.MBTopicHBM WHERE ");
			query.append("companyId = ?");
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
			q.setString(queryPos++, companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbTopic, MBTopicHBMUtil.getInstance());
			com.liferay.portlet.messageboards.model.MBTopic[] array = new com.liferay.portlet.messageboards.model.MBTopic[3];
			array[0] = (com.liferay.portlet.messageboards.model.MBTopic)objArray[0];
			array[1] = (com.liferay.portlet.messageboards.model.MBTopic)objArray[1];
			array[2] = (com.liferay.portlet.messageboards.model.MBTopic)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCategoryId(String categoryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBTopic IN CLASS com.liferay.portlet.messageboards.service.persistence.MBTopicHBM WHERE ");
			query.append("categoryId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, categoryId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				MBTopicHBM mbTopicHBM = (MBTopicHBM)itr.next();
				list.add(MBTopicHBMUtil.model(mbTopicHBM));
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

	public List findByCategoryId(String categoryId, int begin, int end)
		throws SystemException {
		return findByCategoryId(categoryId, begin, end, null);
	}

	public List findByCategoryId(String categoryId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBTopic IN CLASS com.liferay.portlet.messageboards.service.persistence.MBTopicHBM WHERE ");
			query.append("categoryId = ?");
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
			q.setString(queryPos++, categoryId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				MBTopicHBM mbTopicHBM = (MBTopicHBM)itr.next();
				list.add(MBTopicHBMUtil.model(mbTopicHBM));
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

	public com.liferay.portlet.messageboards.model.MBTopic findByCategoryId_First(
		String categoryId, OrderByComparator obc)
		throws NoSuchTopicException, SystemException {
		List list = findByCategoryId(categoryId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No MBTopic exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "categoryId=";
			msg += categoryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchTopicException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBTopic)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBTopic findByCategoryId_Last(
		String categoryId, OrderByComparator obc)
		throws NoSuchTopicException, SystemException {
		int count = countByCategoryId(categoryId);
		List list = findByCategoryId(categoryId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No MBTopic exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "categoryId=";
			msg += categoryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchTopicException(msg);
		}
		else {
			return (com.liferay.portlet.messageboards.model.MBTopic)list.get(0);
		}
	}

	public com.liferay.portlet.messageboards.model.MBTopic[] findByCategoryId_PrevAndNext(
		String topicId, String categoryId, OrderByComparator obc)
		throws NoSuchTopicException, SystemException {
		com.liferay.portlet.messageboards.model.MBTopic mbTopic = findByPrimaryKey(topicId);
		int count = countByCategoryId(categoryId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBTopic IN CLASS com.liferay.portlet.messageboards.service.persistence.MBTopicHBM WHERE ");
			query.append("categoryId = ?");
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
			q.setString(queryPos++, categoryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbTopic, MBTopicHBMUtil.getInstance());
			com.liferay.portlet.messageboards.model.MBTopic[] array = new com.liferay.portlet.messageboards.model.MBTopic[3];
			array[0] = (com.liferay.portlet.messageboards.model.MBTopic)objArray[0];
			array[1] = (com.liferay.portlet.messageboards.model.MBTopic)objArray[1];
			array[2] = (com.liferay.portlet.messageboards.model.MBTopic)objArray[2];

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
				"FROM MBTopic IN CLASS com.liferay.portlet.messageboards.service.persistence.MBTopicHBM ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				MBTopicHBM mbTopicHBM = (MBTopicHBM)itr.next();
				list.add(MBTopicHBMUtil.model(mbTopicHBM));
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
				"FROM MBTopic IN CLASS com.liferay.portlet.messageboards.service.persistence.MBTopicHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				MBTopicHBM mbTopicHBM = (MBTopicHBM)itr.next();
				session.delete(mbTopicHBM);
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

	public void removeByCategoryId(String categoryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM MBTopic IN CLASS com.liferay.portlet.messageboards.service.persistence.MBTopicHBM WHERE ");
			query.append("categoryId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate DESC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, categoryId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				MBTopicHBM mbTopicHBM = (MBTopicHBM)itr.next();
				session.delete(mbTopicHBM);
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
				"FROM MBTopic IN CLASS com.liferay.portlet.messageboards.service.persistence.MBTopicHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

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

	public int countByCategoryId(String categoryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM MBTopic IN CLASS com.liferay.portlet.messageboards.service.persistence.MBTopicHBM WHERE ");
			query.append("categoryId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, categoryId);

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

	private static Log _log = LogFactory.getLog(MBTopicPersistence.class);
}