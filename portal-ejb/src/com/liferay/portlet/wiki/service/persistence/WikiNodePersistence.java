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

package com.liferay.portlet.wiki.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.wiki.NoSuchNodeException;

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
 * <a href="WikiNodePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WikiNodePersistence extends BasePersistence {
	public com.liferay.portlet.wiki.model.WikiNode create(String nodeId) {
		WikiNodeHBM wikiNodeHBM = new WikiNodeHBM();
		wikiNodeHBM.setNew(true);
		wikiNodeHBM.setPrimaryKey(nodeId);

		return WikiNodeHBMUtil.model(wikiNodeHBM);
	}

	public com.liferay.portlet.wiki.model.WikiNode remove(String nodeId)
		throws NoSuchNodeException, SystemException {
		Session session = null;

		try {
			session = openSession();

			WikiNodeHBM wikiNodeHBM = (WikiNodeHBM)session.get(WikiNodeHBM.class,
					nodeId);

			if (wikiNodeHBM == null) {
				_log.warn("No WikiNode exists with the primary key " +
					nodeId.toString());
				throw new NoSuchNodeException(
					"No WikiNode exists with the primary key " +
					nodeId.toString());
			}

			session.delete(wikiNodeHBM);
			session.flush();

			return WikiNodeHBMUtil.model(wikiNodeHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.wiki.model.WikiNode update(
		com.liferay.portlet.wiki.model.WikiNode wikiNode)
		throws SystemException {
		Session session = null;

		try {
			if (wikiNode.isNew() || wikiNode.isModified()) {
				session = openSession();

				if (wikiNode.isNew()) {
					WikiNodeHBM wikiNodeHBM = new WikiNodeHBM();
					wikiNodeHBM.setNodeId(wikiNode.getNodeId());
					wikiNodeHBM.setGroupId(wikiNode.getGroupId());
					wikiNodeHBM.setCompanyId(wikiNode.getCompanyId());
					wikiNodeHBM.setUserId(wikiNode.getUserId());
					wikiNodeHBM.setUserName(wikiNode.getUserName());
					wikiNodeHBM.setCreateDate(wikiNode.getCreateDate());
					wikiNodeHBM.setModifiedDate(wikiNode.getModifiedDate());
					wikiNodeHBM.setName(wikiNode.getName());
					wikiNodeHBM.setDescription(wikiNode.getDescription());
					wikiNodeHBM.setLastPostDate(wikiNode.getLastPostDate());
					session.save(wikiNodeHBM);
					session.flush();
				}
				else {
					WikiNodeHBM wikiNodeHBM = (WikiNodeHBM)session.get(WikiNodeHBM.class,
							wikiNode.getPrimaryKey());

					if (wikiNodeHBM != null) {
						wikiNodeHBM.setGroupId(wikiNode.getGroupId());
						wikiNodeHBM.setCompanyId(wikiNode.getCompanyId());
						wikiNodeHBM.setUserId(wikiNode.getUserId());
						wikiNodeHBM.setUserName(wikiNode.getUserName());
						wikiNodeHBM.setCreateDate(wikiNode.getCreateDate());
						wikiNodeHBM.setModifiedDate(wikiNode.getModifiedDate());
						wikiNodeHBM.setName(wikiNode.getName());
						wikiNodeHBM.setDescription(wikiNode.getDescription());
						wikiNodeHBM.setLastPostDate(wikiNode.getLastPostDate());
						session.flush();
					}
					else {
						wikiNodeHBM = new WikiNodeHBM();
						wikiNodeHBM.setNodeId(wikiNode.getNodeId());
						wikiNodeHBM.setGroupId(wikiNode.getGroupId());
						wikiNodeHBM.setCompanyId(wikiNode.getCompanyId());
						wikiNodeHBM.setUserId(wikiNode.getUserId());
						wikiNodeHBM.setUserName(wikiNode.getUserName());
						wikiNodeHBM.setCreateDate(wikiNode.getCreateDate());
						wikiNodeHBM.setModifiedDate(wikiNode.getModifiedDate());
						wikiNodeHBM.setName(wikiNode.getName());
						wikiNodeHBM.setDescription(wikiNode.getDescription());
						wikiNodeHBM.setLastPostDate(wikiNode.getLastPostDate());
						session.save(wikiNodeHBM);
						session.flush();
					}
				}

				wikiNode.setNew(false);
				wikiNode.setModified(false);
			}

			return wikiNode;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.wiki.model.WikiNode findByPrimaryKey(
		String nodeId) throws NoSuchNodeException, SystemException {
		Session session = null;

		try {
			session = openSession();

			WikiNodeHBM wikiNodeHBM = (WikiNodeHBM)session.get(WikiNodeHBM.class,
					nodeId);

			if (wikiNodeHBM == null) {
				_log.warn("No WikiNode exists with the primary key " +
					nodeId.toString());
				throw new NoSuchNodeException(
					"No WikiNode exists with the primary key " +
					nodeId.toString());
			}

			return WikiNodeHBMUtil.model(wikiNodeHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM WikiNode IN CLASS com.liferay.portlet.wiki.service.persistence.WikiNodeHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				WikiNodeHBM wikiNodeHBM = (WikiNodeHBM)itr.next();
				list.add(WikiNodeHBMUtil.model(wikiNodeHBM));
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

	public List findByGroupId(String groupId, int begin, int end)
		throws SystemException {
		return findByGroupId(groupId, begin, end, null);
	}

	public List findByGroupId(String groupId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM WikiNode IN CLASS com.liferay.portlet.wiki.service.persistence.WikiNodeHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				WikiNodeHBM wikiNodeHBM = (WikiNodeHBM)itr.next();
				list.add(WikiNodeHBMUtil.model(wikiNodeHBM));
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

	public com.liferay.portlet.wiki.model.WikiNode findByGroupId_First(
		String groupId, OrderByComparator obc)
		throws NoSuchNodeException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No WikiNode exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchNodeException(msg);
		}
		else {
			return (com.liferay.portlet.wiki.model.WikiNode)list.get(0);
		}
	}

	public com.liferay.portlet.wiki.model.WikiNode findByGroupId_Last(
		String groupId, OrderByComparator obc)
		throws NoSuchNodeException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No WikiNode exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchNodeException(msg);
		}
		else {
			return (com.liferay.portlet.wiki.model.WikiNode)list.get(0);
		}
	}

	public com.liferay.portlet.wiki.model.WikiNode[] findByGroupId_PrevAndNext(
		String nodeId, String groupId, OrderByComparator obc)
		throws NoSuchNodeException, SystemException {
		com.liferay.portlet.wiki.model.WikiNode wikiNode = findByPrimaryKey(nodeId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM WikiNode IN CLASS com.liferay.portlet.wiki.service.persistence.WikiNodeHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					wikiNode, WikiNodeHBMUtil.getInstance());
			com.liferay.portlet.wiki.model.WikiNode[] array = new com.liferay.portlet.wiki.model.WikiNode[3];
			array[0] = (com.liferay.portlet.wiki.model.WikiNode)objArray[0];
			array[1] = (com.liferay.portlet.wiki.model.WikiNode)objArray[1];
			array[2] = (com.liferay.portlet.wiki.model.WikiNode)objArray[2];

			return array;
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
				"FROM WikiNode IN CLASS com.liferay.portlet.wiki.service.persistence.WikiNodeHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				WikiNodeHBM wikiNodeHBM = (WikiNodeHBM)itr.next();
				list.add(WikiNodeHBMUtil.model(wikiNodeHBM));
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
				"FROM WikiNode IN CLASS com.liferay.portlet.wiki.service.persistence.WikiNodeHBM WHERE ");

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
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				WikiNodeHBM wikiNodeHBM = (WikiNodeHBM)itr.next();
				list.add(WikiNodeHBMUtil.model(wikiNodeHBM));
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

	public com.liferay.portlet.wiki.model.WikiNode findByCompanyId_First(
		String companyId, OrderByComparator obc)
		throws NoSuchNodeException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No WikiNode exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchNodeException(msg);
		}
		else {
			return (com.liferay.portlet.wiki.model.WikiNode)list.get(0);
		}
	}

	public com.liferay.portlet.wiki.model.WikiNode findByCompanyId_Last(
		String companyId, OrderByComparator obc)
		throws NoSuchNodeException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No WikiNode exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchNodeException(msg);
		}
		else {
			return (com.liferay.portlet.wiki.model.WikiNode)list.get(0);
		}
	}

	public com.liferay.portlet.wiki.model.WikiNode[] findByCompanyId_PrevAndNext(
		String nodeId, String companyId, OrderByComparator obc)
		throws NoSuchNodeException, SystemException {
		com.liferay.portlet.wiki.model.WikiNode wikiNode = findByPrimaryKey(nodeId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM WikiNode IN CLASS com.liferay.portlet.wiki.service.persistence.WikiNodeHBM WHERE ");

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
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					wikiNode, WikiNodeHBMUtil.getInstance());
			com.liferay.portlet.wiki.model.WikiNode[] array = new com.liferay.portlet.wiki.model.WikiNode[3];
			array[0] = (com.liferay.portlet.wiki.model.WikiNode)objArray[0];
			array[1] = (com.liferay.portlet.wiki.model.WikiNode)objArray[1];
			array[2] = (com.liferay.portlet.wiki.model.WikiNode)objArray[2];

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
				"FROM WikiNode IN CLASS com.liferay.portlet.wiki.service.persistence.WikiNodeHBM ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				WikiNodeHBM wikiNodeHBM = (WikiNodeHBM)itr.next();
				list.add(WikiNodeHBMUtil.model(wikiNodeHBM));
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

	public void removeByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM WikiNode IN CLASS com.liferay.portlet.wiki.service.persistence.WikiNodeHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				WikiNodeHBM wikiNodeHBM = (WikiNodeHBM)itr.next();
				session.delete(wikiNodeHBM);
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

	public void removeByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM WikiNode IN CLASS com.liferay.portlet.wiki.service.persistence.WikiNodeHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				WikiNodeHBM wikiNodeHBM = (WikiNodeHBM)itr.next();
				session.delete(wikiNodeHBM);
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

	public int countByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM WikiNode IN CLASS com.liferay.portlet.wiki.service.persistence.WikiNodeHBM WHERE ");

			if (groupId == null) {
				query.append("groupId is null");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
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

	public int countByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM WikiNode IN CLASS com.liferay.portlet.wiki.service.persistence.WikiNodeHBM WHERE ");

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

	private static Log _log = LogFactory.getLog(WikiNodePersistence.class);
}