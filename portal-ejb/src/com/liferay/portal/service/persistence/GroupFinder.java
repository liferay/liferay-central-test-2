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

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * <a href="GroupFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class GroupFinder {

	public static String COUNT_BY_C_N_1 =
		GroupFinder.class.getName() + ".countByC_N_1";

	public static String FIND_BY_C_N_1 =
		GroupFinder.class.getName() + ".findByC_N_1";

	public static String FIND_BY_C_N_2 =
		GroupFinder.class.getName() + ".findByC_N_2";

	public static String JOIN_BY_GROUPS_ROLES =
		GroupFinder.class.getName() + ".joinByGroupsRoles";

	public static String JOIN_BY_LAYOUT_SET =
		GroupFinder.class.getName() + ".joinByLayoutSet";

	public static String JOIN_BY_ROLE_PERMISSIONS =
		GroupFinder.class.getName() + ".joinByRolePermissions";

	public static String JOIN_BY_USERS_GROUPS =
		GroupFinder.class.getName() + ".joinByUsersGroups";

	public static int countByC_N_1(String companyId, String name, Map params)
		throws SystemException {

		name = StringUtil.lowerCase(name);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_N_1);

			sql = StringUtil.replace(sql, "[$JOIN$]", _getJoin(params));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.INTEGER);

			QueryPos qPos = QueryPos.getInstance(q);

			_setJoin(qPos, params);
			qPos.add(companyId);
			qPos.add(StringPool.BLANK);
			qPos.add(StringPool.BLANK);
			qPos.add(name);
			qPos.add(name);

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

	public static List findByC_N_1(String companyId, String name, Map params)
		throws SystemException {

		name = StringUtil.lowerCase(name);

		List list = new ArrayList();

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_N_1);

			sql = StringUtil.replace(sql, "[$JOIN$]", _getJoin(params));

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Group_", GroupHBM.class);

			QueryPos qPos = QueryPos.getInstance(q);

			_setJoin(qPos, params);
			qPos.add(companyId);
			qPos.add(StringPool.BLANK);
			qPos.add(StringPool.BLANK);
			qPos.add(name);
			qPos.add(name);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				GroupHBM groupHBM = (GroupHBM)itr.next();

				Group group = GroupHBMUtil.model(groupHBM);

				list.add(group);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}

		return list;
	}

	public static List findByC_N_1(
			String companyId, String name, Map params, int begin, int end)
		throws SystemException {

		name = StringUtil.lowerCase(name);

		List list = new ArrayList();

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_N_1);

			sql = StringUtil.replace(sql, "[$JOIN$]", _getJoin(params));

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Group_", GroupHBM.class);

			QueryPos qPos = QueryPos.getInstance(q);

			_setJoin(qPos, params);
			qPos.add(companyId);
			qPos.add(StringPool.BLANK);
			qPos.add(StringPool.BLANK);
			qPos.add(name);
			qPos.add(name);

			Iterator itr = QueryUtil.iterate(
				q, HibernateUtil.getDialect(), begin, end);

			while (itr.hasNext()) {
				GroupHBM groupHBM = (GroupHBM)itr.next();

				Group group = GroupHBMUtil.model(groupHBM);

				list.add(group);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}

		return list;
	}

	public static Group findByC_N_2(String companyId, String name)
		throws NoSuchGroupException, SystemException {

		name = StringUtil.lowerCase(name);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_N_2);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Group_", GroupHBM.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				GroupHBM groupHBM = (GroupHBM)itr.next();

				return GroupHBMUtil.model(groupHBM);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}

		throw new NoSuchGroupException(
			"No Group exists with the key {companyId=" + companyId + ", name=" +
				name + "}");
	}

	private static String _getJoin(Map params) {
		if (params == null) {
			return StringPool.BLANK;
		}

		StringBuffer sb = new StringBuffer();

		Iterator itr = params.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			String key = (String)entry.getKey();

			if (key.equals("layoutSet") || key.equals("rolePermissions")) {
				sb.append(_getJoin(key));
			}
			else {
				String value = (String)entry.getValue();

				if (Validator.isNotNull(value)) {
					sb.append(_getJoin(key));
				}
			}
		}

		return sb.toString();
	}

	private static String _getJoin(String key) {
		StringBuffer sb = new StringBuffer();

		if (key.equals("groupsRoles")) {
			sb.append(CustomSQLUtil.get(JOIN_BY_GROUPS_ROLES));
		}
		else if (key.equals("layoutSet")) {
			sb.append(CustomSQLUtil.get(JOIN_BY_LAYOUT_SET));
		}
		else if (key.equals("rolePermissions")) {
			sb.append(CustomSQLUtil.get(JOIN_BY_ROLE_PERMISSIONS));
		}
		else if (key.equals("usersGroups")) {
			sb.append(CustomSQLUtil.get(JOIN_BY_USERS_GROUPS));
		}

		return sb.toString();
	}

	private static void _setJoin(QueryPos qPos, Map params) {
		if (params != null) {
			Iterator itr = params.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry entry = (Map.Entry)itr.next();

				String key = (String)entry.getKey();

				if (key.equals("layoutSet")) {
					Boolean value = (Boolean)entry.getValue();

					qPos.add(value);
					qPos.add(0);
				}
				else if (key.equals("rolePermissions")) {
					List values = (List)entry.getValue();

					for (int i = 0; i < values.size(); i++) {
						String value = (String)values.get(i);

						qPos.add(value);
					}
				}
				else {
					String value = (String)entry.getValue();

					if (Validator.isNotNull(value)) {
						qPos.add(value);
					}
				}
			}
		}
	}

}