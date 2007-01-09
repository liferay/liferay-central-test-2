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

package com.liferay.portal.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * <a href="UserFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Jon Steer
 *
 */
public class UserFinder {

	public static String COUNT_BY_C_FN_MN_LN_EA_A =
		UserFinder.class.getName() + ".countByC_FN_MN_LN_EA_A";

	public static String FIND_BY_C_FN_MN_LN_EA_A =
		UserFinder.class.getName() + ".findByC_FN_MN_LN_EA_A";

	public static String JOIN_BY_USERS_GROUPS =
		UserFinder.class.getName() + ".joinByUsersGroups";

	public static String JOIN_BY_USERS_ORGS =
		UserFinder.class.getName() + ".joinByUsersOrgs";

	public static String JOIN_BY_PERMISSION =
		UserFinder.class.getName() + ".joinByPermission";

	public static String JOIN_BY_USERS_ROLES =
		UserFinder.class.getName() + ".joinByUsersRoles";

	public static String JOIN_BY_USERS_USER_GROUPS =
		UserFinder.class.getName() + ".joinByUsersUserGroups";

	public static int countByC_FN_MN_LN_EA_A(
			String companyId, String firstName, String middleName,
			String lastName, String emailAddress, boolean active,
			LinkedHashMap params, boolean andOperator)
		throws SystemException {

		firstName = StringUtil.lowerCase(firstName);
		middleName = StringUtil.lowerCase(middleName);
		lastName = StringUtil.lowerCase(lastName);
		emailAddress = StringUtil.lowerCase(emailAddress);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_FN_MN_LN_EA_A);

			sql = StringUtil.replace(sql, "[$JOIN$]", _getJoin(params));
			sql = StringUtil.replace(sql, "[$WHERE$]", _getWhere(params));
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			_setJoin(qPos, params);
			qPos.add(companyId);
			qPos.add(firstName);
			qPos.add(firstName);
			qPos.add(middleName);
			qPos.add(middleName);
			qPos.add(lastName);
			qPos.add(lastName);
			qPos.add(emailAddress);
			qPos.add(emailAddress);
			qPos.add(active);

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

	public static List findByC_FN_MN_LN_EA_A(
			String companyId, String firstName, String middleName,
			String lastName, String emailAddress, boolean active,
			LinkedHashMap params, boolean andOperator, int begin, int end,
			OrderByComparator obc)
		throws SystemException {

		firstName = StringUtil.lowerCase(firstName);
		middleName = StringUtil.lowerCase(middleName);
		lastName = StringUtil.lowerCase(lastName);
		emailAddress = StringUtil.lowerCase(emailAddress);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_FN_MN_LN_EA_A);

			sql = StringUtil.replace(sql, "[$JOIN$]", _getJoin(params));
			sql = StringUtil.replace(sql, "[$WHERE$]", _getWhere(params));
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addEntity("User_", UserImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			_setJoin(qPos, params);
			qPos.add(companyId);
			qPos.add(firstName);
			qPos.add(firstName);
			qPos.add(middleName);
			qPos.add(middleName);
			qPos.add(lastName);
			qPos.add(lastName);
			qPos.add(emailAddress);
			qPos.add(emailAddress);
			qPos.add(active);

			return QueryUtil.list(q, HibernateUtil.getDialect(), begin, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	private static String _getJoin(LinkedHashMap params) {
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
		String join = StringPool.BLANK;

		if (key.equals("permission")) {
			join = CustomSQLUtil.get(JOIN_BY_PERMISSION);
		}
		else if (key.equals("usersGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_GROUPS);
		}
		else if (key.equals("usersOrgs")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_ORGS);
		}
		else if (key.equals("usersRoles")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_ROLES);
		}
		else if (key.equals("usersUserGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_USER_GROUPS);
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

		StringBuffer sb = new StringBuffer();

		Iterator itr = params.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			String key = (String)entry.getKey();
			Object value = entry.getValue();

			if (value != null) {
				sb.append(_getWhere(key));
			}
		}

		return sb.toString();
	}

	private static String _getWhere(String key) {
		String join = StringPool.BLANK;

		if (key.equals("permission")) {
			join = CustomSQLUtil.get(JOIN_BY_PERMISSION);
		}
		else if (key.equals("usersGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_GROUPS);
		}
		else if (key.equals("usersOrgs")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_ORGS);
		}
		else if (key.equals("usersRoles")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_ROLES);
		}
		else if (key.equals("usersUserGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_USERS_USER_GROUPS);
		}

		if (Validator.isNotNull(join)) {
			int pos = join.indexOf("WHERE");

			if (pos != -1) {
				join = join.substring(pos + 5, join.length()) + " AND ";
			}
		}

		return join;
	}

	private static void _setJoin(QueryPos qPos, LinkedHashMap params) {
		if (params != null) {
			Iterator itr = params.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry entry = (Map.Entry)itr.next();

				String value = (String)entry.getValue();

				if (Validator.isNotNull(value)) {
					qPos.add(value);
				}
			}
		}
	}

}