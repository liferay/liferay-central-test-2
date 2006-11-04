/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.sql.Timestamp;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * <a href="WikiPageFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WikiPageFinder {

	public static String COUNT_BY_CREATEDATE =
		WikiPageFinder.class.getName() + ".countByCreateDate";

	public static String FIND_BY_CREATEDATE =
		WikiPageFinder.class.getName() + ".findByCreateDate";

	public static int countByCreateDate(
			String nodeId, Date createDate, boolean before)
		throws SystemException {

		return countByCreateDate(
			nodeId, new Timestamp(createDate.getTime()), before);
	}

	public static int countByCreateDate(
			String nodeId, Timestamp createDate, boolean before)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String createDateComparator = StringPool.GREATER_THAN;

			if (before) {
				createDateComparator = StringPool.LESS_THAN;
			}

			String sql = CustomSQLUtil.get(COUNT_BY_CREATEDATE);

			sql = StringUtil.replace(
				sql, "[$CREATE_DATE_COMPARATOR$]", createDateComparator);

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(nodeId);
			qPos.add(createDate);
			qPos.add(true);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

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

	public static List findByCreateDate(
			String nodeId, Date createDate, boolean before, int begin, int end)
		throws SystemException {

		return findByCreateDate(
			nodeId, new Timestamp(createDate.getTime()), before, begin, end);
	}

	public static List findByCreateDate(
			String nodeId, Timestamp createDate, boolean before, int begin,
			int end)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String createDateComparator = StringPool.GREATER_THAN;

			if (before) {
				createDateComparator = StringPool.LESS_THAN;
			}

			String sql = CustomSQLUtil.get(FIND_BY_CREATEDATE);

			sql = StringUtil.replace(
				sql, "[$CREATE_DATE_COMPARATOR$]", createDateComparator);

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addEntity("WikiPage", WikiPage.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(nodeId);
			qPos.add(createDate);
			qPos.add(true);

			return QueryUtil.list(q, HibernateUtil.getDialect(), begin, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

}