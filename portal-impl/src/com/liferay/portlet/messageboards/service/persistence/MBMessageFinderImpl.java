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
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.impl.MBMessageImpl;
import com.liferay.util.dao.hibernate.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="MBMessageFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBMessageFinderImpl
	extends BasePersistenceImpl implements MBMessageFinder {

	public static String COUNT_BY_CATEGORY_IDS =
		MBMessageFinder.class.getName() + ".countByCategoryIds";

	public static String COUNT_BY_GROUP_ID =
		MBMessageFinder.class.getName() + ".countByGroupId";

	public static String COUNT_BY_G_U =
		MBMessageFinder.class.getName() + ".countByG_U";

	public static String FIND_BY_GROUP_ID =
		MBMessageFinder.class.getName() + ".findByGroupId";

	public static String FIND_BY_NO_ASSETS =
		MBMessageFinder.class.getName() + ".findByNoAssets";

	public static String FIND_BY_UUID_G =
		MBMessageFinder.class.getName() + ".findByUuid_G";

	public static String FIND_BY_G_U =
		MBMessageFinder.class.getName() + ".findByG_U";

	public static String FIND_BY_C_C =
		MBMessageFinder.class.getName() + ".findByC_C";

	public int countByCategoryIds(List<Long> categoryIds)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_CATEGORY_IDS);

			sql = StringUtil.replace(
				sql, "[$CATEGORY_ID$]", getCategoryIds(categoryIds));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < categoryIds.size(); i++) {
				Long categoryId = categoryIds.get(i);

				qPos.add(categoryId);
			}

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

	public List<MBMessage> findByGroupId(long groupId, int start, int end)
		throws SystemException {

		return findByGroupId(groupId, start, end, null);
	}

	public List<MBMessage> findByGroupId(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_GROUP_ID);

			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("MBMessage", MBMessageImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<MBMessage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findByNoAssets() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_ASSETS);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("MBMessage", MBMessageImpl.class);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBMessage findByUuid_G(String uuid, long groupId)
		throws NoSuchMessageException, SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_UUID_G);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("MBMessage", MBMessageImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(uuid);
			qPos.add(groupId);

			List<MBMessage> list = q.list();

			if (list.size() == 0) {
				StringBuilder sb = new StringBuilder();

				sb.append("No MBMessage exists with the key {uuid=");
				sb.append(uuid);
				sb.append(", groupId=");
				sb.append(groupId);
				sb.append("}");

				throw new NoSuchMessageException(sb.toString());
			}
			else {
				return list.get(0);
			}
		}
		catch (NoSuchMessageException nsme) {
			throw nsme;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findByG_U(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return findByG_U(groupId, userId, start, end, null);
	}

	public List<MBMessage> findByG_U(
			long groupId, long userId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_U);

			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("MBMessage", MBMessageImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);

			return (List<MBMessage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBMessage> findByC_C(long classNameId, long classPK)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_C);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("MBMessage", MBMessageImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);
			qPos.add(classPK);

			return q.list();
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