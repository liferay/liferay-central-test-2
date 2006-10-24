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

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * <a href="RoleFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class RoleFinder {

	public static String COUNT_BY_C_N_D =
		RoleFinder.class.getName() + ".countByC_N_D";

	public static String FIND_BY_C_N =
		RoleFinder.class.getName() + ".findByC_N";

	public static String FIND_BY_U_G =
		RoleFinder.class.getName() + ".findByU_G";

	public static String FIND_BY_C_N_D =
		RoleFinder.class.getName() + ".findByC_N_D";

	public static int countByC_N_D(
			String companyId, String name, String description)
		throws SystemException {

		name = StringUtil.lowerCase(name);
		description = StringUtil.lowerCase(description);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_N_D);

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);
			qPos.add(name);
			qPos.add(description);
			qPos.add(description);

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

	public static Role findByC_N(String companyId, String name)
		throws NoSuchRoleException, SystemException {

		name = StringUtil.lowerCase(name);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_N);

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(true);

			q.addEntity("Role_", Role.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				return (Role)itr.next();
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}

		throw new NoSuchRoleException(
			"No Role exists with the key {companyId=" + companyId + ", name=" +
				name + "}");
	}

	public static List findByU_G(String userId, List groups)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_U_G);

			sql = StringUtil.replace(
				sql, "[$GROUP_IDS$]", _getGroupIds(groups, "Groups_Roles"));

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addEntity("Role_", Role.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);
			_setGroupIds(qPos, groups);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static List findByC_N_D(
			String companyId, String name, String description, int begin,
			int end)
		throws SystemException {

		name = StringUtil.lowerCase(name);
		description = StringUtil.lowerCase(description);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_N_D);

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addEntity("Role_", Role.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);
			qPos.add(name);
			qPos.add(description);
			qPos.add(description);

			return QueryUtil.list(q, HibernateUtil.getDialect(), begin, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	private static String _getGroupIds(List groups, String table) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < groups.size(); i++) {
			sb.append(table);
			sb.append(".groupId = ?");

			if ((i + 1) < groups.size()) {
				sb.append(" OR ");
			}
		}

		return sb.toString();
	}

	private static void _setGroupIds(QueryPos qPos, List groups) {
		for (int i = 0; i < groups.size(); i++) {
			Group group = (Group)groups.get(i);

			qPos.add(group.getGroupId());
		}
	}

}