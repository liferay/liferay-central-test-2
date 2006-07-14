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

import com.liferay.portal.NoSuchListTypeException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.ListType;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="ListTypePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ListTypePersistence extends BasePersistence {
	public ListType create(String listTypeId) {
		ListType listType = new ListType();
		listType.setNew(true);
		listType.setPrimaryKey(listTypeId);

		return listType;
	}

	public ListType remove(String listTypeId)
		throws NoSuchListTypeException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ListType listType = (ListType)session.get(ListType.class, listTypeId);

			if (listType == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ListType exists with the primary key " +
						listTypeId.toString());
				}

				throw new NoSuchListTypeException(
					"No ListType exists with the primary key " +
					listTypeId.toString());
			}

			session.delete(listType);
			session.flush();

			return listType;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.ListType update(
		com.liferay.portal.model.ListType listType) throws SystemException {
		Session session = null;

		try {
			if (listType.isNew() || listType.isModified()) {
				session = openSession();

				if (listType.isNew()) {
					ListType listTypeModel = new ListType();
					listTypeModel.setListTypeId(listType.getListTypeId());
					listTypeModel.setName(listType.getName());
					listTypeModel.setType(listType.getType());
					session.save(listTypeModel);
					session.flush();
				}
				else {
					ListType listTypeModel = (ListType)session.get(ListType.class,
							listType.getPrimaryKey());

					if (listTypeModel != null) {
						listTypeModel.setName(listType.getName());
						listTypeModel.setType(listType.getType());
						session.flush();
					}
					else {
						listTypeModel = new ListType();
						listTypeModel.setListTypeId(listType.getListTypeId());
						listTypeModel.setName(listType.getName());
						listTypeModel.setType(listType.getType());
						session.save(listTypeModel);
						session.flush();
					}
				}

				listType.setNew(false);
				listType.setModified(false);
			}

			return listType;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public ListType findByPrimaryKey(String listTypeId)
		throws NoSuchListTypeException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ListType listType = (ListType)session.get(ListType.class, listTypeId);

			if (listType == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ListType exists with the primary key " +
						listTypeId.toString());
					throw new NoSuchListTypeException(
						"No ListType exists with the primary key " +
						listTypeId.toString());
				}
			}

			return listType;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public ListType fetchByPrimaryKey(String listTypeId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ListType)session.get(ListType.class, listTypeId);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByType(String type) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.ListType WHERE ");

			if (type == null) {
				query.append("type_ IS NULL");
			}
			else {
				query.append("type_ = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (type != null) {
				q.setString(queryPos++, type);
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

	public List findByType(String type, int begin, int end)
		throws SystemException {
		return findByType(type, begin, end, null);
	}

	public List findByType(String type, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.ListType WHERE ");

			if (type == null) {
				query.append("type_ IS NULL");
			}
			else {
				query.append("type_ = ?");
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

			if (type != null) {
				q.setString(queryPos++, type);
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

	public ListType findByType_First(String type, OrderByComparator obc)
		throws NoSuchListTypeException, SystemException {
		List list = findByType(type, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No ListType exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "type=";
			msg += type;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchListTypeException(msg);
		}
		else {
			return (ListType)list.get(0);
		}
	}

	public ListType findByType_Last(String type, OrderByComparator obc)
		throws NoSuchListTypeException, SystemException {
		int count = countByType(type);
		List list = findByType(type, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No ListType exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "type=";
			msg += type;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchListTypeException(msg);
		}
		else {
			return (ListType)list.get(0);
		}
	}

	public ListType[] findByType_PrevAndNext(String listTypeId, String type,
		OrderByComparator obc) throws NoSuchListTypeException, SystemException {
		ListType listType = findByPrimaryKey(listTypeId);
		int count = countByType(type);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.ListType WHERE ");

			if (type == null) {
				query.append("type_ IS NULL");
			}
			else {
				query.append("type_ = ?");
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

			if (type != null) {
				q.setString(queryPos++, type);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, listType);
			ListType[] array = new ListType[3];
			array[0] = (ListType)objArray[0];
			array[1] = (ListType)objArray[1];
			array[2] = (ListType)objArray[2];

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
			query.append("FROM com.liferay.portal.model.ListType ");
			query.append("ORDER BY ");
			query.append("name ASC");

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

	public void removeByType(String type) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.ListType WHERE ");

			if (type == null) {
				query.append("type_ IS NULL");
			}
			else {
				query.append("type_ = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (type != null) {
				q.setString(queryPos++, type);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				ListType listType = (ListType)itr.next();
				session.delete(listType);
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

	public int countByType(String type) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.ListType WHERE ");

			if (type == null) {
				query.append("type_ IS NULL");
			}
			else {
				query.append("type_ = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (type != null) {
				q.setString(queryPos++, type);
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

	private static Log _log = LogFactory.getLog(ListTypePersistence.class);
}