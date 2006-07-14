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
import java.util.HashMap;
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

	public static String COUNT_BY_GROUP_ID =
		GroupFinder.class.getName() + ".countByGroupId";

	public static String COUNT_BY_C_N_D =
		GroupFinder.class.getName() + ".countByC_N_D";

	public static String FIND_BY_C_N =
		GroupFinder.class.getName() + ".findByC_N";

	public static String FIND_BY_C_N_D =
		GroupFinder.class.getName() + ".findByC_N_D";

	public static String JOIN_BY_GROUPS_ORGS =
		GroupFinder.class.getName() + ".joinByGroupsOrgs";

	public static String JOIN_BY_GROUPS_ROLES =
		GroupFinder.class.getName() + ".joinByGroupsRoles";

	public static String JOIN_BY_GROUPS_USER_GROUPS =
		GroupFinder.class.getName() + ".joinByGroupsUserGroups";

	public static String JOIN_BY_LAYOUT_SET =
		GroupFinder.class.getName() + ".joinByLayoutSet";

	public static String JOIN_BY_ROLE_PERMISSIONS =
		GroupFinder.class.getName() + ".joinByRolePermissions";

	public static String JOIN_BY_USERS_GROUPS =
		GroupFinder.class.getName() + ".joinByUsersGroups";

	public static int countByG_U(String groupId, String userId)
		throws SystemException {

		Map params1 = new HashMap();

		params1.put("usersGroups", userId);

		Map params2 = new HashMap();

		params2.put("groupsOrgs", userId);

		Map params3 = new HashMap();

		params3.put("groupsUserGroups", userId);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			int count = _countByGroupId(session, groupId, params1);
			count += _countByGroupId(session, groupId, params2);
			count += _countByGroupId(session, groupId, params3);

			return count;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static int countByC_N_D(
			String companyId, String name, String description, Map params)
		throws SystemException {

		name = StringUtil.lowerCase(name);
		description = StringUtil.lowerCase(description);

		if (params == null) {
			params = new HashMap();
		}

		String userId = (String)params.get("usersGroups");

		Map params1 = params;

		Map params2 = new HashMap();

		params2.putAll(params1);

		if (Validator.isNotNull(userId)) {
			params2.remove("usersGroups");
			params2.put("groupsOrgs", userId);
		}

		Map params3 = new HashMap();

		params3.putAll(params1);

		if (Validator.isNotNull(userId)) {
			params3.remove("usersGroups");
			params3.put("groupsUserGroups", userId);
		}

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			int count = _countByC_N_D(
				session, companyId, name, description, params1);

			if (Validator.isNotNull(userId)) {
				count += _countByC_N_D(
					session, companyId, name, description, params2);

				count += _countByC_N_D(
					session, companyId, name, description, params3);
			}

			return count;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static Group findByC_N(String companyId, String name)
		throws NoSuchGroupException, SystemException {

		name = StringUtil.lowerCase(name);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_N);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Group_", Group.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				return (Group)itr.next();
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

	public static List findByC_N_D(
			String companyId, String name, String description, Map params,
			int begin, int end)
		throws SystemException {

		name = StringUtil.lowerCase(name);
		description = StringUtil.lowerCase(description);

		if (params == null) {
			params = new HashMap();
		}

		String userId = (String)params.get("usersGroups");

		Map params1 = params;

		Map params2 = new HashMap();

		params2.putAll(params1);

		if (Validator.isNotNull(userId)) {
			params2.remove("usersGroups");
			params2.put("groupsOrgs", userId);
		}

		Map params3 = new HashMap();

		params3.putAll(params1);

		if (Validator.isNotNull(userId)) {
			params3.remove("usersGroups");
			params3.put("groupsUserGroups", userId);
		}

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = null;

			sql = "(";
			sql += CustomSQLUtil.get(FIND_BY_C_N_D);
			sql = StringUtil.replace(sql, "[$JOIN$]", _getJoin(params1));
			sql += ")";

			if (Validator.isNotNull(userId)) {
				sql += " UNION ";

				sql += "(";
				sql += CustomSQLUtil.get(FIND_BY_C_N_D);
				sql = StringUtil.replace(sql, "[$JOIN$]", _getJoin(params2));
				sql += ")";

				sql += " UNION ";

				sql += "(";
				sql += CustomSQLUtil.get(FIND_BY_C_N_D);
				sql = StringUtil.replace(sql, "[$JOIN$]", _getJoin(params3));
				sql += ")";
			}

			sql += " ORDER BY groupName ASC";

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("groupId", Hibernate.STRING);

			QueryPos qPos = QueryPos.getInstance(q);

			_setJoin(qPos, params1);
			qPos.add(companyId);
			qPos.add(name);
			qPos.add(name);
			qPos.add(description);
			qPos.add(description);

			if (Validator.isNotNull(userId)) {
				_setJoin(qPos, params2);
				qPos.add(companyId);
				qPos.add(name);
				qPos.add(name);
				qPos.add(description);
				qPos.add(description);

				_setJoin(qPos, params3);
				qPos.add(companyId);
				qPos.add(name);
				qPos.add(name);
				qPos.add(description);
				qPos.add(description);
			}

			List list = new ArrayList();

			Iterator itr = QueryUtil.iterate(
				q, HibernateUtil.getDialect(), begin, end);

			while (itr.hasNext()) {
				String groupId = (String)itr.next();

				Group group = GroupUtil.findByPrimaryKey(groupId);

				list.add(group);
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

	private static int _countByGroupId(
			Session session, String groupId, Map params)
		throws SystemException {

		String sql = CustomSQLUtil.get(COUNT_BY_GROUP_ID);

		sql = StringUtil.replace(sql, "[$JOIN$]", _getJoin(params));

		SQLQuery q = session.createSQLQuery(sql);

		q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

		QueryPos qPos = QueryPos.getInstance(q);

		_setJoin(qPos, params);
		qPos.add(groupId);

		Iterator itr = q.list().iterator();

		if (itr.hasNext()) {
			Long count = (Long)itr.next();

			if (count != null) {
				return count.intValue();
			}
		}

		return 0;
	}

	private static int _countByC_N_D(
			Session session, String companyId, String name, String description,
			Map params)
		throws SystemException {

		String sql = CustomSQLUtil.get(COUNT_BY_C_N_D);

		sql = StringUtil.replace(sql, "[$JOIN$]", _getJoin(params));

		SQLQuery q = session.createSQLQuery(sql);

		q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

		QueryPos qPos = QueryPos.getInstance(q);

		_setJoin(qPos, params);
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

	private static String _getJoin(Map params) {
		if (params == null) {
			return StringPool.BLANK;
		}

		StringBuffer sb = new StringBuffer();

		Iterator itr = params.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			String key = (String)entry.getKey();
			Object value = entry.getValue();

			if (value != null) {
				sb.append(_getJoin(key));
			}
		}

		return sb.toString();
	}

	private static String _getJoin(String key) {
		StringBuffer sb = new StringBuffer();

		if (key.equals("groupsOrgs")) {
			sb.append(CustomSQLUtil.get(JOIN_BY_GROUPS_ORGS));
		}
		else if (key.equals("groupsRoles")) {
			sb.append(CustomSQLUtil.get(JOIN_BY_GROUPS_ROLES));
		}
		else if (key.equals("groupsUserGroups")) {
			sb.append(CustomSQLUtil.get(JOIN_BY_GROUPS_USER_GROUPS));
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