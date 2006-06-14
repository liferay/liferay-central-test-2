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

import com.liferay.portal.NoSuchGroupRelException;
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
 * <a href="GroupRelPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class GroupRelPersistence extends BasePersistence {
	public com.liferay.portal.model.GroupRel create(GroupRelPK groupRelPK) {
		GroupRelHBM groupRelHBM = new GroupRelHBM();
		groupRelHBM.setNew(true);
		groupRelHBM.setPrimaryKey(groupRelPK);

		return GroupRelHBMUtil.model(groupRelHBM);
	}

	public com.liferay.portal.model.GroupRel remove(GroupRelPK groupRelPK)
		throws NoSuchGroupRelException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupRelHBM groupRelHBM = (GroupRelHBM)session.get(GroupRelHBM.class,
					groupRelPK);

			if (groupRelHBM == null) {
				_log.warn("No GroupRel exists with the primary key " +
					groupRelPK.toString());
				throw new NoSuchGroupRelException(
					"No GroupRel exists with the primary key " +
					groupRelPK.toString());
			}

			session.delete(groupRelHBM);
			session.flush();

			return GroupRelHBMUtil.model(groupRelHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.GroupRel update(
		com.liferay.portal.model.GroupRel groupRel) throws SystemException {
		Session session = null;

		try {
			if (groupRel.isNew() || groupRel.isModified()) {
				session = openSession();

				if (groupRel.isNew()) {
					GroupRelHBM groupRelHBM = new GroupRelHBM();
					groupRelHBM.setGroupId(groupRel.getGroupId());
					groupRelHBM.setClassName(groupRel.getClassName());
					groupRelHBM.setClassPK(groupRel.getClassPK());
					session.save(groupRelHBM);
					session.flush();
				}
				else {
					GroupRelHBM groupRelHBM = (GroupRelHBM)session.get(GroupRelHBM.class,
							groupRel.getPrimaryKey());

					if (groupRelHBM != null) {
						session.flush();
					}
					else {
						groupRelHBM = new GroupRelHBM();
						groupRelHBM.setGroupId(groupRel.getGroupId());
						groupRelHBM.setClassName(groupRel.getClassName());
						groupRelHBM.setClassPK(groupRel.getClassPK());
						session.save(groupRelHBM);
						session.flush();
					}
				}

				groupRel.setNew(false);
				groupRel.setModified(false);
			}

			return groupRel;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.GroupRel findByPrimaryKey(
		GroupRelPK groupRelPK) throws NoSuchGroupRelException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupRelHBM groupRelHBM = (GroupRelHBM)session.get(GroupRelHBM.class,
					groupRelPK);

			if (groupRelHBM == null) {
				_log.warn("No GroupRel exists with the primary key " +
					groupRelPK.toString());
				throw new NoSuchGroupRelException(
					"No GroupRel exists with the primary key " +
					groupRelPK.toString());
			}

			return GroupRelHBMUtil.model(groupRelHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C(String className, String classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM GroupRel IN CLASS com.liferay.portal.service.persistence.GroupRelHBM WHERE ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				GroupRelHBM groupRelHBM = (GroupRelHBM)itr.next();
				list.add(GroupRelHBMUtil.model(groupRelHBM));
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

	public List findByC_C(String className, String classPK, int begin, int end)
		throws SystemException {
		return findByC_C(className, classPK, begin, end, null);
	}

	public List findByC_C(String className, String classPK, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM GroupRel IN CLASS com.liferay.portal.service.persistence.GroupRelHBM WHERE ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				GroupRelHBM groupRelHBM = (GroupRelHBM)itr.next();
				list.add(GroupRelHBMUtil.model(groupRelHBM));
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

	public com.liferay.portal.model.GroupRel findByC_C_First(String className,
		String classPK, OrderByComparator obc)
		throws NoSuchGroupRelException, SystemException {
		List list = findByC_C(className, classPK, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No GroupRel exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "className=";
			msg += className;
			msg += ", ";
			msg += "classPK=";
			msg += classPK;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchGroupRelException(msg);
		}
		else {
			return (com.liferay.portal.model.GroupRel)list.get(0);
		}
	}

	public com.liferay.portal.model.GroupRel findByC_C_Last(String className,
		String classPK, OrderByComparator obc)
		throws NoSuchGroupRelException, SystemException {
		int count = countByC_C(className, classPK);
		List list = findByC_C(className, classPK, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No GroupRel exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "className=";
			msg += className;
			msg += ", ";
			msg += "classPK=";
			msg += classPK;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchGroupRelException(msg);
		}
		else {
			return (com.liferay.portal.model.GroupRel)list.get(0);
		}
	}

	public com.liferay.portal.model.GroupRel[] findByC_C_PrevAndNext(
		GroupRelPK groupRelPK, String className, String classPK,
		OrderByComparator obc) throws NoSuchGroupRelException, SystemException {
		com.liferay.portal.model.GroupRel groupRel = findByPrimaryKey(groupRelPK);
		int count = countByC_C(className, classPK);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM GroupRel IN CLASS com.liferay.portal.service.persistence.GroupRelHBM WHERE ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					groupRel, GroupRelHBMUtil.getInstance());
			com.liferay.portal.model.GroupRel[] array = new com.liferay.portal.model.GroupRel[3];
			array[0] = (com.liferay.portal.model.GroupRel)objArray[0];
			array[1] = (com.liferay.portal.model.GroupRel)objArray[1];
			array[2] = (com.liferay.portal.model.GroupRel)objArray[2];

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
				"FROM GroupRel IN CLASS com.liferay.portal.service.persistence.GroupRelHBM ");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				GroupRelHBM groupRelHBM = (GroupRelHBM)itr.next();
				list.add(GroupRelHBMUtil.model(groupRelHBM));
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

	public void removeByC_C(String className, String classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM GroupRel IN CLASS com.liferay.portal.service.persistence.GroupRelHBM WHERE ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				GroupRelHBM groupRelHBM = (GroupRelHBM)itr.next();
				session.delete(groupRelHBM);
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

	public int countByC_C(String className, String classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM GroupRel IN CLASS com.liferay.portal.service.persistence.GroupRelHBM WHERE ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);

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

	private static Log _log = LogFactory.getLog(GroupRelPersistence.class);
}