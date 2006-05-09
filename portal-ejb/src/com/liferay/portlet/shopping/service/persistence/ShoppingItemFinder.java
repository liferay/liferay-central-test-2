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
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.util.Randomizer;
import com.liferay.util.StringUtil;
import com.liferay.util.dao.hibernate.QueryPos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

/**
 * <a href="ShoppingItemFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingItemFinder {

	public static String COUNT_BY_CATEGORY_IDS =
		ShoppingItemFinder.class.getName() + ".countByCategoryIds";

	public static int countByCategoryIds(List categoryIds)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_CATEGORY_IDS);

			sql = StringUtil.replace(
				sql, "[$CATEGORY_ID$]", _getCategoryIds(categoryIds));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.INTEGER);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < categoryIds.size(); i++) {
				String categoryId = (String)categoryIds.get(i);

				qPos.add(categoryId);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static int countByFeatured(String groupId, String[] categoryIds)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			StringBuffer query = new StringBuffer();

			query.append("SELECT COUNT(*) FROM ");
			query.append(ShoppingItemHBM.class.getName());
			query.append(" AS shoppingItem ");
			query.append("WHERE ");
			query.append("(");

			for (int i = 0; i < categoryIds.length; i++) {
				query.append("shoppingItem.categoryId = ? ");

				if (i + 1 < categoryIds.length) {
					query.append("OR ");
				}
			}

			query.append(") AND ");
			query.append("shoppingItem.groupId = ? AND ");
			query.append("shoppingItem.featured = ? AND ");
			query.append("shoppingItem.smallImage = ?");

			Query q = session.createQuery(query.toString());

			for (int i = 0; i < categoryIds.length; i++) {
				q.setString(i, categoryIds[i]);
			}

			q.setString(categoryIds.length, groupId);
			q.setBoolean(categoryIds.length + 1, true);
			q.setBoolean(categoryIds.length + 2, true);

			Iterator itr = q.iterate();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static int countByKeywords(
			String groupId, String[] categoryIds, String keywords)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			StringBuffer query = new StringBuffer();

			query.append("SELECT COUNT(*) FROM ");
			query.append(ShoppingItemHBM.class.getName());
			query.append(" AS shoppingItem ");
			query.append("WHERE ");

			if ((categoryIds != null) && (categoryIds.length > 0)) {
				query.append("(");

				for (int i = 0; i < categoryIds.length; i++) {
					query.append("shoppingItem.categoryId = ? ");

					if (i + 1 < categoryIds.length) {
						query.append("OR ");
					}
				}

				query.append(") AND ");
			}

			query.append("shoppingItem.groupId = ? AND (");
			query.append("shoppingItem.name LIKE ? OR ");
			query.append("shoppingItem.description LIKE ? OR ");
			query.append("shoppingItem.properties LIKE ?)");

			keywords = '%' + keywords + '%';

			Query q = session.createQuery(query.toString());

			for (int i = 0; i < categoryIds.length; i++) {
				q.setString(i, categoryIds[i]);
			}

			q.setString(categoryIds.length, groupId);
			q.setString(categoryIds.length + 1, keywords);
			q.setString(categoryIds.length + 2, keywords);
			q.setString(categoryIds.length + 3, keywords);

			Iterator itr = q.iterate();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static int countBySale(String groupId, String[] categoryIds)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			StringBuffer query = new StringBuffer();

			query.append("SELECT COUNT(*) FROM ");
			query.append(ShoppingItemHBM.class.getName());
			query.append(" AS shoppingItem ");
			query.append("WHERE ");
			query.append("(");

			for (int i = 0; i < categoryIds.length; i++) {
				query.append("shoppingItem.categoryId = ? ");

				if (i + 1 < categoryIds.length) {
					query.append("OR ");
				}
			}

			query.append(") AND ");
			query.append("shoppingItem.groupId = ? AND ");
			query.append("shoppingItem.sale = ? AND ");
			query.append("shoppingItem.smallImage = ?");

			Query q = session.createQuery(query.toString());

			for (int i = 0; i < categoryIds.length; i++) {
				q.setString(i, categoryIds[i]);
			}

			q.setString(categoryIds.length, groupId);
			q.setBoolean(categoryIds.length + 1, true);
			q.setBoolean(categoryIds.length + 2, true);

			Iterator itr = q.iterate();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static List findByFeatured(
			String groupId, String[] categoryIds, int numOfItems)
		throws SystemException {

		List list = new ArrayList(numOfItems);

		int countByFeatured = countByFeatured(groupId, categoryIds);

		if (countByFeatured == 0) {
			return list;
		}

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			StringBuffer query = new StringBuffer();

			query.append("SELECT shoppingItem FROM ");
			query.append(ShoppingItemHBM.class.getName());
			query.append(" AS shoppingItem ");
			query.append("WHERE ");
			query.append("(");

			for (int i = 0; i < categoryIds.length; i++) {
				query.append("shoppingItem.categoryId = ? ");

				if (i + 1 < categoryIds.length) {
					query.append("OR ");
				}
			}

			query.append(") AND ");
			query.append("shoppingItem.groupId = ? AND ");
			query.append("shoppingItem.featured = ? AND ");
			query.append("shoppingItem.smallImage = ?");

			Query q = session.createQuery(query.toString());

			for (int i = 0; i < categoryIds.length; i++) {
				q.setString(i, categoryIds[i]);
			}

			q.setString(categoryIds.length, groupId);
			q.setBoolean(categoryIds.length + 1, true);
			q.setBoolean(categoryIds.length + 2, true);

			ScrollableResults sr = q.scroll();

			if (sr.first()) {
				Randomizer randomizer = new Randomizer();

				int[] scrollIds =
					randomizer.nextInt(countByFeatured, numOfItems);

				for (int i = 0; i < scrollIds.length; i++) {
					if (sr.scroll(scrollIds[i])) {
						ShoppingItemHBM shoppingItemHBM =
							(ShoppingItemHBM)sr.get(0);

						list.add(ShoppingItemHBMUtil.model(shoppingItemHBM));

						sr.first();
					}
				}
			}

			return list;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static List findByKeywords(
			String groupId, String[] categoryIds, String keywords, int begin,
			int end)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			StringBuffer query = new StringBuffer();

			query.append("SELECT shoppingItem FROM ");
			query.append(ShoppingItemHBM.class.getName());
			query.append(" AS shoppingItem ");
			query.append("WHERE ");

			if ((categoryIds != null) && (categoryIds.length > 0)) {
				query.append("(");

				for (int i = 0; i < categoryIds.length; i++) {
					query.append("shoppingItem.categoryId = ? ");

					if (i + 1 < categoryIds.length) {
						query.append("OR ");
					}
				}

				query.append(") AND ");
			}

			query.append("shoppingItem.groupId = ? AND (");
			query.append("shoppingItem.name LIKE ? OR ");
			query.append("shoppingItem.description LIKE ? OR ");
			query.append("shoppingItem.properties LIKE ?)");

			keywords = '%' + keywords + '%';

			Query q = session.createQuery(query.toString());

			for (int i = 0; i < categoryIds.length; i++) {
				q.setString(i, categoryIds[i]);
			}

			q.setString(categoryIds.length, groupId);
			q.setString(categoryIds.length + 1, keywords);
			q.setString(categoryIds.length + 2, keywords);
			q.setString(categoryIds.length + 3, keywords);

			ScrollableResults sr = q.scroll();

			List list = new ArrayList();

			if (sr.first() && sr.scroll(begin)) {
				for (int i = begin; i < end; i++) {
					ShoppingItemHBM shoppingItemHBM =
						(ShoppingItemHBM)sr.get(0);

					list.add(ShoppingItemHBMUtil.model(shoppingItemHBM));

					if (!sr.next()) {
						break;
					}
				}
			}

			return list;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static List findBySale(
			String groupId, String[] categoryIds, int numOfItems)
		throws SystemException {

		List list = new ArrayList(numOfItems);

		int countBySale = countBySale(groupId, categoryIds);

		if (countBySale == 0) {
			return list;
		}

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			StringBuffer query = new StringBuffer();

			query.append("SELECT shoppingItem FROM ");
			query.append(ShoppingItemHBM.class.getName());
			query.append(" AS shoppingItem ");
			query.append("WHERE ");
			query.append("(");

			for (int i = 0; i < categoryIds.length; i++) {
				query.append("shoppingItem.categoryId = ? ");

				if (i + 1 < categoryIds.length) {
					query.append("OR ");
				}
			}

			query.append(") AND ");
			query.append("shoppingItem.groupId = ? AND ");
			query.append("shoppingItem.sale = ? AND ");
			query.append("shoppingItem.smallImage = ?");

			Query q = session.createQuery(query.toString());

			for (int i = 0; i < categoryIds.length; i++) {
				q.setString(i, categoryIds[i]);
			}

			q.setString(categoryIds.length, groupId);
			q.setBoolean(categoryIds.length + 1, true);
			q.setBoolean(categoryIds.length + 2, true);

			ScrollableResults sr = q.scroll();

			if (sr.first()) {
				Randomizer randomizer = new Randomizer();

				int[] scrollIds = randomizer.nextInt(countBySale, numOfItems);

				for (int i = 0; i < scrollIds.length; i++) {
					if (sr.scroll(scrollIds[i])) {
						ShoppingItemHBM shoppingItemHBM =
							(ShoppingItemHBM)sr.get(0);

						list.add(ShoppingItemHBMUtil.model(shoppingItemHBM));

						sr.first();
					}
				}
			}

			return list;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	private static String _getCategoryIds(List categoryIds) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < categoryIds.size(); i++) {
			sb.append("categoryId = ? ");

			if ((i + 1) != categoryIds.size()) {
				sb.append("OR ");
			}
		}

		return sb.toString();
	}

}