/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * <a href="GroupFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
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

	public static String JOIN_BY_ACTIVE =
		GroupFinder.class.getName() + ".joinByActive";

	public static String JOIN_BY_CREATOR_USER_ID =
		GroupFinder.class.getName() + ".joinByCreatorUserId";

	public static String JOIN_BY_GROUPS_ORGS =
		GroupFinder.class.getName() + ".joinByGroupsOrgs";

	public static String JOIN_BY_GROUPS_ROLES =
		GroupFinder.class.getName() + ".joinByGroupsRoles";

	public static String JOIN_BY_GROUPS_USER_GROUPS =
		GroupFinder.class.getName() + ".joinByGroupsUserGroups";

	public static String JOIN_BY_LAYOUT_SET =
		GroupFinder.class.getName() + ".joinByLayoutSet";

	public static String JOIN_BY_PAGE_COUNT =
		GroupFinder.class.getName() + ".joinByPageCount";

	public static String JOIN_BY_ROLE_PERMISSIONS =
		GroupFinder.class.getName() + ".joinByRolePermissions";

	public static String JOIN_BY_TYPE =
		GroupFinder.class.getName() + ".joinByType";

	public static String JOIN_BY_USERS_GROUPS =
		GroupFinder.class.getName() + ".joinByUsersGroups";

	public static int countByG_U(long groupId, long userId)
		throws SystemException {

		Long userIdObj = new Long(userId);

		LinkedHashMap params1 = new LinkedHashMap();

		params1.put("usersGroups", userIdObj);

		LinkedHashMap params2 = new LinkedHashMap();

		params2.put("groupsOrgs", userIdObj);

		LinkedHashMap params3 = new LinkedHashMap();

		params3.put("groupsUserGroups", userIdObj);

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
			long companyId, String name, String description,
			LinkedHashMap params)
		throws SystemException {

		name = StringUtil.lowerCase(name);
		description = StringUtil.lowerCase(description);

		if (params == null) {
			params = new LinkedHashMap();
		}

		Long userId = (Long)params.get("usersGroups");

		LinkedHashMap params1 = params;

		LinkedHashMap params2 = new LinkedHashMap();

		params2.putAll(params1);

		if (userId != null) {
			params2.remove("usersGroups");
			params2.put("groupsOrgs", userId);
		}

		LinkedHashMap params3 = new LinkedHashMap();

		params3.putAll(params1);

		if (userId != null) {
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

	public static Group findByC_N(long companyId, String name)
		throws NoSuchGroupException, SystemException {

		name = StringUtil.lowerCase(name);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_N);

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(true);

			q.addEntity("Group_", GroupImpl.class);

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

		StringMaker sm = new StringMaker();

		sm.append("No Group exists with the key {companyId=");
		sm.append(companyId);
		sm.append(", name=");
		sm.append(name);
		sm.append("}");

		throw new NoSuchGroupException(sm.toString());
	}

	public static List findByC_N_D(
			long companyId, String name, String description,
			LinkedHashMap params, int begin, int end)
		throws SystemException {

		name = StringUtil.lowerCase(name);
		description = StringUtil.lowerCase(description);

		if (params == null) {
			params = new LinkedHashMap();
		}

		Long userId = (Long)params.get("usersGroups");

		LinkedHashMap params1 = params;

		LinkedHashMap params2 = new LinkedHashMap();

		params2.putAll(params1);

		if (userId != null) {
			params2.remove("usersGroups");
			params2.put("groupsOrgs", userId);
		}

		LinkedHashMap params3 = new LinkedHashMap();

		params3.putAll(params1);

		if (userId != null) {
			params3.remove("usersGroups");
			params3.put("groupsUserGroups", userId);
		}

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			StringMaker sm = new StringMaker();

			sm.append("(");

			sm.append(CustomSQLUtil.get(FIND_BY_C_N_D));

			String sql = sm.toString();

			sql = StringUtil.replace(sql, "[$JOIN$]", _getJoin(params1));
			sql = StringUtil.replace(sql, "[$WHERE$]", _getWhere(params1));

			sm = new StringMaker();

			sm.append(sql);

			sm.append(")");

			if (Validator.isNotNull(userId)) {
				sm.append(" UNION (");

				sm.append(CustomSQLUtil.get(FIND_BY_C_N_D));

				sql = sm.toString();

				sql = StringUtil.replace(sql, "[$JOIN$]", _getJoin(params2));
				sql = StringUtil.replace(sql, "[$WHERE$]", _getWhere(params2));

				sm = new StringMaker();

				sm.append(sql);

				sm.append(") UNION (");

				sm.append(CustomSQLUtil.get(FIND_BY_C_N_D));

				sql = sm.toString();

				sql = StringUtil.replace(sql, "[$JOIN$]", _getJoin(params3));
				sql = StringUtil.replace(sql, "[$WHERE$]", _getWhere(params3));

				sm = new StringMaker();

				sm.append(sql);

				sm.append(")");
			}

			sm.append(" ORDER BY groupName ASC");

			sql = sm.toString();

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

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
				long groupId = GetterUtil.getLong((String)itr.next());

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
			Session session, long groupId, LinkedHashMap params)
		throws SystemException {

		String sql = CustomSQLUtil.get(COUNT_BY_GROUP_ID);

		sql = StringUtil.replace(sql, "[$JOIN$]", _getJoin(params));
		sql = StringUtil.replace(sql, "[$WHERE$]", _getWhere(params));

		SQLQuery q = session.createSQLQuery(sql);

		q.setCacheable(false);

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
			Session session, long companyId, String name, String description,
			LinkedHashMap params)
		throws SystemException {

		String sql = CustomSQLUtil.get(COUNT_BY_C_N_D);

		sql = StringUtil.replace(sql, "[$JOIN$]", _getJoin(params));
		sql = StringUtil.replace(sql, "[$WHERE$]", _getWhere(params));

		SQLQuery q = session.createSQLQuery(sql);

		q.setCacheable(false);

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

	private static String _getJoin(LinkedHashMap params) {
		if (params == null) {
			return StringPool.BLANK;
		}

		StringMaker sm = new StringMaker();

		Iterator itr = params.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			String key = (String)entry.getKey();
			Object value = entry.getValue();

			if (Validator.isNotNull(value)) {
				sm.append(_getJoin(key));
			}
		}

		return sm.toString();
	}

	private static String _getJoin(String key) {
		String join = StringPool.BLANK;

		if (key.equals("groupsOrgs")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_ORGS);
		}
		else if (key.equals("groupsRoles")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_ROLES);
		}
		else if (key.equals("groupsUserGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_USER_GROUPS);
		}
		else if (key.equals("layoutSet")) {
			join = CustomSQLUtil.get(JOIN_BY_LAYOUT_SET);
		}
		else if (key.equals("pageCount")) {
			join = CustomSQLUtil.get(JOIN_BY_PAGE_COUNT);
		}
		else if (key.equals("rolePermissions")) {
			join = CustomSQLUtil.get(JOIN_BY_ROLE_PERMISSIONS);
		}
		else if (key.equals("usersGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_GROUPS);
		}

		if (Validator.isNotNull(join)) {
			int pos = join.indexOf("WHERE");

			if (pos != -1) {
				join = join.substring(0, pos);
			}
		}

		return join;
	}

	private static String _getWhere(LinkedHashMap params) {
		if (params == null) {
			return StringPool.BLANK;
		}

		StringMaker sm = new StringMaker();

		Iterator itr = params.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			String key = (String)entry.getKey();
			Object value = entry.getValue();

			if (Validator.isNotNull(value)) {
				sm.append(_getWhere(key));
			}
		}

		return sm.toString();
	}

	private static String _getWhere(String key) {
		String join = StringPool.BLANK;

		if (key.equals("active")) {
			join = CustomSQLUtil.get(JOIN_BY_ACTIVE);
		}
		else if (key.equals("creatorUserId")) {
			join = CustomSQLUtil.get(JOIN_BY_CREATOR_USER_ID);
		}
		else if (key.equals("groupsOrgs")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_ORGS);
		}
		else if (key.equals("groupsRoles")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_ROLES);
		}
		else if (key.equals("groupsUserGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_USER_GROUPS);
		}
		else if (key.equals("layoutSet")) {
			join = CustomSQLUtil.get(JOIN_BY_LAYOUT_SET);
		}
		else if (key.equals("pageCount")) {
			join = CustomSQLUtil.get(JOIN_BY_PAGE_COUNT);
		}
		else if (key.equals("rolePermissions")) {
			join = CustomSQLUtil.get(JOIN_BY_ROLE_PERMISSIONS);
		}
		else if (key.equals("type")) {
			join = CustomSQLUtil.get(JOIN_BY_TYPE);
		}
		else if (key.equals("usersGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_GROUPS);
		}

		if (Validator.isNotNull(join)) {
			int pos = join.indexOf("WHERE");

			if (pos != -1) {
				StringMaker sm = new StringMaker();

				sm.append(join.substring(pos + 5, join.length()));
				sm.append(" AND ");

				join = sm.toString();
			}
		}

		return join;
	}

	private static void _setJoin(QueryPos qPos, LinkedHashMap params) {
		if (params != null) {
			Iterator itr = params.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry entry = (Map.Entry)itr.next();

				String key = (String)entry.getKey();

				if (key.equals("active") || key.equals("layoutSet")) {
					Boolean value = (Boolean)entry.getValue();

					qPos.add(value);
				}
				else if (key.equals("pageCount")) {
				}
				else if (key.equals("rolePermissions")) {
					List values = (List)entry.getValue();

					for (int i = 0; i < values.size(); i++) {
						Object value = values.get(i);

						if (value instanceof Integer) {
							Integer valueInteger = (Integer)value;

							qPos.add(valueInteger);
						}
						else if (value instanceof Long) {
							Long valueLong = (Long)value;

							qPos.add(valueLong);
						}
						else if (value instanceof String) {
							String valueString = (String)value;

							qPos.add(valueString);
						}
					}
				}
				else {
					Object value = entry.getValue();

					if (value instanceof Long) {
						Long valueLong = (Long)value;

						if (Validator.isNotNull(valueLong)) {
							qPos.add(valueLong);
						}
					}
					else if (value instanceof String) {
						String valueString = (String)value;

						if (Validator.isNotNull(valueString)) {
							qPos.add(valueString);
						}
					}
				}
			}
		}
	}

}