/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.hibernate.QueryPos;
import com.liferay.portal.kernel.dao.hibernate.QueryUtil;
import com.liferay.portal.kernel.dao.hibernate.SQLQuery;
import com.liferay.portal.kernel.dao.hibernate.Session;
import com.liferay.portal.kernel.dao.hibernate.Type;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBThreadImpl;
import com.liferay.util.dao.hibernate.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="MBThreadFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBThreadFinderImpl
	extends BasePersistenceImpl implements MBThreadFinder {

	public static String COUNT_BY_CATEGORY_IDS =
		MBThreadFinder.class.getName() + ".countByCategoryIds";

	public static String COUNT_BY_GROUP_ID =
		MBThreadFinder.class.getName() + ".countByGroupId";

	public static String COUNT_BY_G_U =
		MBThreadFinder.class.getName() + ".countByG_U";

	public static String COUNT_BY_S_G_U =
		MBThreadFinder.class.getName() + ".countByS_G_U";

	public static String FIND_BY_GROUP_ID =
		MBThreadFinder.class.getName() + ".findByGroupId";

	public static String FIND_BY_G_U =
		MBThreadFinder.class.getName() + ".findByG_U";

	public static String FIND_BY_S_G_U =
		MBThreadFinder.class.getName() + ".findByS_G_U";

	public int countByCategoryIds(List<Long> categoryIds)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_CATEGORY_IDS);

			sql = StringUtil.replace(
				sql, "[$CATEGORY_ID$]", getCategoryIds(categoryIds));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.INTEGER);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < categoryIds.size(); i++) {
				Long categoryId = categoryIds.get(i);

				qPos.add(categoryId);
			}

			Iterator<Integer> itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = itr.next();

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
			closeSession(session);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_GROUP_ID);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

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
			closeSession(session);
		}
	}

	public int countByG_U(long groupId, long userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_U);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

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
			closeSession(session);
		}
	}

	public int countByS_G_U(long groupId, long userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_S_G_U);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(PortalUtil.getClassNameId(MBThread.class.getName()));
			qPos.add(groupId);
			qPos.add(userId);

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

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
			closeSession(session);
		}
	}

	public List<MBThread> findByGroupId(long groupId, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_GROUP_ID);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<MBThread>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBThread> findByG_U(
			long groupId, long userId, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_U);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);

			return (List<MBThread>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBThread> findByS_G_U(
			long groupId, long userId, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_S_G_U);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(PortalUtil.getClassNameId(MBThread.class.getName()));
			qPos.add(groupId);
			qPos.add(userId);

			return (List<MBThread>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getCategoryIds(List<Long> categoryIds) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < categoryIds.size(); i++) {
			sb.append("categoryId = ? ");

			if ((i + 1) != categoryIds.size()) {
				sb.append("OR ");
			}
		}

		return sb.toString();
	}

}