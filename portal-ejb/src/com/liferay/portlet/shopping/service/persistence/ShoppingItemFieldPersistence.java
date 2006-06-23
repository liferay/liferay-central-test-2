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

import com.liferay.portlet.shopping.NoSuchItemFieldException;

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
 * <a href="ShoppingItemFieldPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingItemFieldPersistence extends BasePersistence {
	public com.liferay.portlet.shopping.model.ShoppingItemField create(
		String itemFieldId) {
		ShoppingItemFieldHBM shoppingItemFieldHBM = new ShoppingItemFieldHBM();
		shoppingItemFieldHBM.setNew(true);
		shoppingItemFieldHBM.setPrimaryKey(itemFieldId);

		return ShoppingItemFieldHBMUtil.model(shoppingItemFieldHBM);
	}

	public com.liferay.portlet.shopping.model.ShoppingItemField remove(
		String itemFieldId) throws NoSuchItemFieldException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemFieldHBM shoppingItemFieldHBM = (ShoppingItemFieldHBM)session.get(ShoppingItemFieldHBM.class,
					itemFieldId);

			if (shoppingItemFieldHBM == null) {
				_log.warn("No ShoppingItemField exists with the primary key " +
					itemFieldId.toString());
				throw new NoSuchItemFieldException(
					"No ShoppingItemField exists with the primary key " +
					itemFieldId.toString());
			}

			session.delete(shoppingItemFieldHBM);
			session.flush();

			return ShoppingItemFieldHBMUtil.model(shoppingItemFieldHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingItemField update(
		com.liferay.portlet.shopping.model.ShoppingItemField shoppingItemField)
		throws SystemException {
		Session session = null;

		try {
			if (shoppingItemField.isNew() || shoppingItemField.isModified()) {
				session = openSession();

				if (shoppingItemField.isNew()) {
					ShoppingItemFieldHBM shoppingItemFieldHBM = new ShoppingItemFieldHBM();
					shoppingItemFieldHBM.setItemFieldId(shoppingItemField.getItemFieldId());
					shoppingItemFieldHBM.setItemId(shoppingItemField.getItemId());
					shoppingItemFieldHBM.setName(shoppingItemField.getName());
					shoppingItemFieldHBM.setValues(shoppingItemField.getValues());
					shoppingItemFieldHBM.setDescription(shoppingItemField.getDescription());
					session.save(shoppingItemFieldHBM);
					session.flush();
				}
				else {
					ShoppingItemFieldHBM shoppingItemFieldHBM = (ShoppingItemFieldHBM)session.get(ShoppingItemFieldHBM.class,
							shoppingItemField.getPrimaryKey());

					if (shoppingItemFieldHBM != null) {
						shoppingItemFieldHBM.setItemId(shoppingItemField.getItemId());
						shoppingItemFieldHBM.setName(shoppingItemField.getName());
						shoppingItemFieldHBM.setValues(shoppingItemField.getValues());
						shoppingItemFieldHBM.setDescription(shoppingItemField.getDescription());
						session.flush();
					}
					else {
						shoppingItemFieldHBM = new ShoppingItemFieldHBM();
						shoppingItemFieldHBM.setItemFieldId(shoppingItemField.getItemFieldId());
						shoppingItemFieldHBM.setItemId(shoppingItemField.getItemId());
						shoppingItemFieldHBM.setName(shoppingItemField.getName());
						shoppingItemFieldHBM.setValues(shoppingItemField.getValues());
						shoppingItemFieldHBM.setDescription(shoppingItemField.getDescription());
						session.save(shoppingItemFieldHBM);
						session.flush();
					}
				}

				shoppingItemField.setNew(false);
				shoppingItemField.setModified(false);
			}

			return shoppingItemField;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingItemField findByPrimaryKey(
		String itemFieldId) throws NoSuchItemFieldException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemFieldHBM shoppingItemFieldHBM = (ShoppingItemFieldHBM)session.get(ShoppingItemFieldHBM.class,
					itemFieldId);

			if (shoppingItemFieldHBM == null) {
				_log.warn("No ShoppingItemField exists with the primary key " +
					itemFieldId.toString());
				throw new NoSuchItemFieldException(
					"No ShoppingItemField exists with the primary key " +
					itemFieldId.toString());
			}

			return ShoppingItemFieldHBMUtil.model(shoppingItemFieldHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByItemId(String itemId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingItemField IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldHBM WHERE ");

			if (itemId == null) {
				query.append("itemId is null");
			}
			else {
				query.append("itemId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("itemId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (itemId != null) {
				q.setString(queryPos++, itemId);
			}

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ShoppingItemFieldHBM shoppingItemFieldHBM = (ShoppingItemFieldHBM)itr.next();
				list.add(ShoppingItemFieldHBMUtil.model(shoppingItemFieldHBM));
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

	public List findByItemId(String itemId, int begin, int end)
		throws SystemException {
		return findByItemId(itemId, begin, end, null);
	}

	public List findByItemId(String itemId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingItemField IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldHBM WHERE ");

			if (itemId == null) {
				query.append("itemId is null");
			}
			else {
				query.append("itemId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("itemId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (itemId != null) {
				q.setString(queryPos++, itemId);
			}

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				ShoppingItemFieldHBM shoppingItemFieldHBM = (ShoppingItemFieldHBM)itr.next();
				list.add(ShoppingItemFieldHBMUtil.model(shoppingItemFieldHBM));
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

	public com.liferay.portlet.shopping.model.ShoppingItemField findByItemId_First(
		String itemId, OrderByComparator obc)
		throws NoSuchItemFieldException, SystemException {
		List list = findByItemId(itemId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingItemField exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "itemId=";
			msg += itemId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchItemFieldException(msg);
		}
		else {
			return (com.liferay.portlet.shopping.model.ShoppingItemField)list.get(0);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingItemField findByItemId_Last(
		String itemId, OrderByComparator obc)
		throws NoSuchItemFieldException, SystemException {
		int count = countByItemId(itemId);
		List list = findByItemId(itemId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No ShoppingItemField exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "itemId=";
			msg += itemId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchItemFieldException(msg);
		}
		else {
			return (com.liferay.portlet.shopping.model.ShoppingItemField)list.get(0);
		}
	}

	public com.liferay.portlet.shopping.model.ShoppingItemField[] findByItemId_PrevAndNext(
		String itemFieldId, String itemId, OrderByComparator obc)
		throws NoSuchItemFieldException, SystemException {
		com.liferay.portlet.shopping.model.ShoppingItemField shoppingItemField = findByPrimaryKey(itemFieldId);
		int count = countByItemId(itemId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingItemField IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldHBM WHERE ");

			if (itemId == null) {
				query.append("itemId is null");
			}
			else {
				query.append("itemId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("itemId ASC").append(", ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (itemId != null) {
				q.setString(queryPos++, itemId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingItemField, ShoppingItemFieldHBMUtil.getInstance());
			com.liferay.portlet.shopping.model.ShoppingItemField[] array = new com.liferay.portlet.shopping.model.ShoppingItemField[3];
			array[0] = (com.liferay.portlet.shopping.model.ShoppingItemField)objArray[0];
			array[1] = (com.liferay.portlet.shopping.model.ShoppingItemField)objArray[1];
			array[2] = (com.liferay.portlet.shopping.model.ShoppingItemField)objArray[2];

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
				"FROM ShoppingItemField IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldHBM ");
			query.append("ORDER BY ");
			query.append("itemId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ShoppingItemFieldHBM shoppingItemFieldHBM = (ShoppingItemFieldHBM)itr.next();
				list.add(ShoppingItemFieldHBMUtil.model(shoppingItemFieldHBM));
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

	public void removeByItemId(String itemId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ShoppingItemField IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldHBM WHERE ");

			if (itemId == null) {
				query.append("itemId is null");
			}
			else {
				query.append("itemId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("itemId ASC").append(", ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (itemId != null) {
				q.setString(queryPos++, itemId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				ShoppingItemFieldHBM shoppingItemFieldHBM = (ShoppingItemFieldHBM)itr.next();
				session.delete(shoppingItemFieldHBM);
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

	public int countByItemId(String itemId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM ShoppingItemField IN CLASS com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldHBM WHERE ");

			if (itemId == null) {
				query.append("itemId is null");
			}
			else {
				query.append("itemId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (itemId != null) {
				q.setString(queryPos++, itemId);
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

	private static Log _log = LogFactory.getLog(ShoppingItemFieldPersistence.class);
}