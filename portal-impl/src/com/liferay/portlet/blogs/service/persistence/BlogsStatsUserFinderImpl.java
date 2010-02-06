/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.blogs.model.BlogsStatsUser;
import com.liferay.portlet.blogs.model.impl.BlogsStatsUserImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="BlogsStatsUserFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BlogsStatsUserFinderImpl
	extends BasePersistenceImpl<BlogsStatsUser>
	implements BlogsStatsUserFinder {

	public static String COUNT_BY_ORGANIZATION_IDS =
		BlogsStatsUserFinder.class.getName() + ".countByOrganizationIds";

	public static String FIND_BY_ORGANIZATION_IDS =
		BlogsStatsUserFinder.class.getName() + ".findByOrganizationIds";

	public int countByOrganizationId(long organizationId)
		throws SystemException {

		List<Long> organizationIds = new ArrayList<Long>();

		organizationIds.add(organizationId);

		return countByOrganizationIds(organizationIds);
	}

	public int countByOrganizationIds(List<Long> organizationIds)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_ORGANIZATION_IDS);

			sql = StringUtil.replace(
				sql, "[$ORGANIZATION_ID$]",
				getOrganizationIds(organizationIds));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < organizationIds.size(); i++) {
				Long organizationId = organizationIds.get(i);

				qPos.add(organizationId);
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

	public List<BlogsStatsUser> findByOrganizationId(
			long organizationId, int start, int end, OrderByComparator obc)
		throws SystemException {

		List<Long> organizationIds = new ArrayList<Long>();

		organizationIds.add(organizationId);

		return findByOrganizationIds(organizationIds, start, end, obc);
	}

	public List<BlogsStatsUser> findByOrganizationIds(
			List<Long> organizationIds, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_ORGANIZATION_IDS);

			sql = StringUtil.replace(
				sql, "[$ORGANIZATION_ID$]",
				getOrganizationIds(organizationIds));
			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("BlogsStatsUser", BlogsStatsUserImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < organizationIds.size(); i++) {
				Long organizationId = organizationIds.get(i);

				qPos.add(organizationId);
			}

			return (List<BlogsStatsUser>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getOrganizationIds(List<Long> organizationIds) {
		if (organizationIds.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(organizationIds.size() * 2 - 1);

		for (int i = 0; i < organizationIds.size(); i++) {
			sb.append("Users_Orgs.organizationId = ? ");

			if ((i + 1) != organizationIds.size()) {
				sb.append("OR ");
			}
		}

		return sb.toString();
	}

}