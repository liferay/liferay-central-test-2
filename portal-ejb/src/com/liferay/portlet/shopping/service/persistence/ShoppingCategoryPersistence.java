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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.shopping.NoSuchCategoryException;
import com.liferay.portlet.shopping.model.ShoppingCategory;

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
 * <a href="ShoppingCategoryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCategoryPersistence extends BasePersistence {
	public ShoppingCategory create(String categoryId) {
		ShoppingCategory shoppingCategory = new ShoppingCategory();
		shoppingCategory.setNew(true);
		shoppingCategory.setPrimaryKey(categoryId);

		return shoppingCategory;
	}

	public ShoppingCategory remove(String categoryId)
		throws NoSuchCategoryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingCategory shoppingCategory = (ShoppingCategory)session.get(ShoppingCategory.class,
					categoryId);

			if (shoppingCategory == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No ShoppingCategory exists with the primary key " +
						categoryId.toString());
				}

				throw new NoSuchCategoryException(
					"No ShoppingCategory exists with the primary key " +
					categoryId.toString());
			}

			session.delete(shoppingCategory);
			session.flush();

			return shoppingCategory;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory update(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory)
		throws SystemException {
		Session session = null;

		try {
			if (shoppingCategory.isNew() || shoppingCategory.isModified()) {
				session = openSession();

				if (shoppingCategory.isNew()) {
					ShoppingCategory shoppingCategoryModel = new ShoppingCategory();
					shoppingCategoryModel.setCategoryId(shoppingCategory.getCategoryId());
					shoppingCategoryModel.setGroupId(shoppingCategory.getGroupId());
					shoppingCategoryModel.setCompanyId(shoppingCategory.getCompanyId());
					shoppingCategoryModel.setUserId(shoppingCategory.getUserId());
					shoppingCategoryModel.setUserName(shoppingCategory.getUserName());
					shoppingCategoryModel.setCreateDate(shoppingCategory.getCreateDate());
					shoppingCategoryModel.setModifiedDate(shoppingCategory.getModifiedDate());
					shoppingCategoryModel.setParentCategoryId(shoppingCategory.getParentCategoryId());
					shoppingCategoryModel.setName(shoppingCategory.getName());
					shoppingCategoryModel.setDescription(shoppingCategory.getDescription());
					session.save(shoppingCategoryModel);
					session.flush();
				}
				else {
					ShoppingCategory shoppingCategoryModel = (ShoppingCategory)session.get(ShoppingCategory.class,
							shoppingCategory.getPrimaryKey());

					if (shoppingCategoryModel != null) {
						shoppingCategoryModel.setGroupId(shoppingCategory.getGroupId());
						shoppingCategoryModel.setCompanyId(shoppingCategory.getCompanyId());
						shoppingCategoryModel.setUserId(shoppingCategory.getUserId());
						shoppingCategoryModel.setUserName(shoppingCategory.getUserName());
						shoppingCategoryModel.setCreateDate(shoppingCategory.getCreateDate());
						shoppingCategoryModel.setModifiedDate(shoppingCategory.getModifiedDate());
						shoppingCategoryModel.setParentCategoryId(shoppingCategory.getParentCategoryId());
						shoppingCategoryModel.setName(shoppingCategory.getName());
						shoppingCategoryModel.setDescription(shoppingCategory.getDescription());
						session.flush();
					}
					else {
						shoppingCategoryModel = new ShoppingCategory();
						shoppingCategoryModel.setCategoryId(shoppingCategory.getCategoryId());
						shoppingCategoryModel.setGroupId(shoppingCategory.getGroupId());
						shoppingCategoryModel.setCompanyId(shoppingCategory.getCompanyId());
						shoppingCategoryModel.setUserId(shoppingCategory.getUserId());
						shoppingCategoryModel.setUserName(shoppingCategory.getUserName());
						shoppingCategoryModel.setCreateDate(shoppingCategory.getCreateDate());
						shoppingCategoryModel.setModifiedDate(shoppingCategory.getModifiedDate());
						shoppingCategoryModel.setParentCategoryId(shoppingCategory.getParentCategoryId());
						shoppingCategoryModel.setName(shoppingCategory.getName());
						shoppingCategoryModel.setDescription(shoppingCategory.getDescription());
						session.save(shoppingCategoryModel);
						session.flush();
					}
				}

				shoppingCategory.setNew(false);
				shoppingCategory.setModified(false);
			}

			return shoppingCategory;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingCategory findByPrimaryKey(String categoryId)
		throws NoSuchCategoryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingCategory shoppingCategory = (ShoppingCategory)session.get(ShoppingCategory.class,
					categoryId);

			if (shoppingCategory == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No ShoppingCategory exists with the primary key " +
						categoryId.toString());
					throw new NoSuchCategoryException(
						"No ShoppingCategory exists with the primary key " +
						categoryId.toString());
				}
			}

			return shoppingCategory;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingCategory fetchByPrimaryKey(String categoryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ShoppingCategory)session.get(ShoppingCategory.class,
				categoryId);
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
				"FROM com.liferay.portlet.shopping.model.ShoppingCategory WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentCategoryId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
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
				"FROM com.liferay.portlet.shopping.model.ShoppingCategory WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
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
				query.append("parentCategoryId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
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

	public ShoppingCategory findByGroupId_First(String groupId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		List list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingCategory exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCategoryException(msg);
		}
		else {
			return (ShoppingCategory)list.get(0);
		}
	}

	public ShoppingCategory findByGroupId_Last(String groupId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		int count = countByGroupId(groupId);
		List list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingCategory exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCategoryException(msg);
		}
		else {
			return (ShoppingCategory)list.get(0);
		}
	}

	public ShoppingCategory[] findByGroupId_PrevAndNext(String categoryId,
		String groupId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		ShoppingCategory shoppingCategory = findByPrimaryKey(categoryId);
		int count = countByGroupId(groupId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCategory WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
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
				query.append("parentCategoryId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingCategory);
			ShoppingCategory[] array = new ShoppingCategory[3];
			array[0] = (ShoppingCategory)objArray[0];
			array[1] = (ShoppingCategory)objArray[1];
			array[2] = (ShoppingCategory)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByG_P(String groupId, String parentCategoryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCategory WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentCategoryId == null) {
				query.append("parentCategoryId IS NULL");
			}
			else {
				query.append("parentCategoryId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentCategoryId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentCategoryId != null) {
				q.setString(queryPos++, parentCategoryId);
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

	public List findByG_P(String groupId, String parentCategoryId, int begin,
		int end) throws SystemException {
		return findByG_P(groupId, parentCategoryId, begin, end, null);
	}

	public List findByG_P(String groupId, String parentCategoryId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCategory WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentCategoryId == null) {
				query.append("parentCategoryId IS NULL");
			}
			else {
				query.append("parentCategoryId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentCategoryId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentCategoryId != null) {
				q.setString(queryPos++, parentCategoryId);
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

	public ShoppingCategory findByG_P_First(String groupId,
		String parentCategoryId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		List list = findByG_P(groupId, parentCategoryId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingCategory exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "parentCategoryId=";
			msg += parentCategoryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCategoryException(msg);
		}
		else {
			return (ShoppingCategory)list.get(0);
		}
	}

	public ShoppingCategory findByG_P_Last(String groupId,
		String parentCategoryId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		int count = countByG_P(groupId, parentCategoryId);
		List list = findByG_P(groupId, parentCategoryId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingCategory exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "groupId=";
			msg += groupId;
			msg += ", ";
			msg += "parentCategoryId=";
			msg += parentCategoryId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchCategoryException(msg);
		}
		else {
			return (ShoppingCategory)list.get(0);
		}
	}

	public ShoppingCategory[] findByG_P_PrevAndNext(String categoryId,
		String groupId, String parentCategoryId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		ShoppingCategory shoppingCategory = findByPrimaryKey(categoryId);
		int count = countByG_P(groupId, parentCategoryId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCategory WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentCategoryId == null) {
				query.append("parentCategoryId IS NULL");
			}
			else {
				query.append("parentCategoryId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("parentCategoryId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentCategoryId != null) {
				q.setString(queryPos++, parentCategoryId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingCategory);
			ShoppingCategory[] array = new ShoppingCategory[3];
			array[0] = (ShoppingCategory)objArray[0];
			array[1] = (ShoppingCategory)objArray[1];
			array[2] = (ShoppingCategory)objArray[2];

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
				"FROM com.liferay.portlet.shopping.model.ShoppingCategory ");
			query.append("ORDER BY ");
			query.append("parentCategoryId ASC").append(", ");
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

	public void removeByGroupId(String groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCategory WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentCategoryId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				ShoppingCategory shoppingCategory = (ShoppingCategory)itr.next();
				session.delete(shoppingCategory);
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

	public void removeByG_P(String groupId, String parentCategoryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCategory WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentCategoryId == null) {
				query.append("parentCategoryId IS NULL");
			}
			else {
				query.append("parentCategoryId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("parentCategoryId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentCategoryId != null) {
				q.setString(queryPos++, parentCategoryId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				ShoppingCategory shoppingCategory = (ShoppingCategory)itr.next();
				session.delete(shoppingCategory);
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
				"FROM com.liferay.portlet.shopping.model.ShoppingCategory WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
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

	public int countByG_P(String groupId, String parentCategoryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCategory WHERE ");

			if (groupId == null) {
				query.append("groupId IS NULL");
			}
			else {
				query.append("groupId = ?");
			}

			query.append(" AND ");

			if (parentCategoryId == null) {
				query.append("parentCategoryId IS NULL");
			}
			else {
				query.append("parentCategoryId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (groupId != null) {
				q.setString(queryPos++, groupId);
			}

			if (parentCategoryId != null) {
				q.setString(queryPos++, parentCategoryId);
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

	private static Log _log = LogFactory.getLog(ShoppingCategoryPersistence.class);
}