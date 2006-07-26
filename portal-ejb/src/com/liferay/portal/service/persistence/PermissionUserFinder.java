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

import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portlet.enterpriseadmin.search.UserSearchTerms;
import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * <a href="PermissionUserFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Charles May
 *
 */
public class PermissionUserFinder {

	public static String COUNT_BY_ADMIN_ROLE =
		PermissionUserFinder.class.getName() + ".countByAdminRole";

	public static String COUNT_BY_GROUP_PERMISSION =
		PermissionUserFinder.class.getName() + ".countByGroupPermission";

	public static String COUNT_BY_GROUP_ROLE =
		PermissionUserFinder.class.getName() + ".countByGroupRole";

	public static String COUNT_BY_ORG_GROUP_PERMISSION =
		PermissionUserFinder.class.getName() + ".countByOrgGroupPermission";

	public static String COUNT_BY_ORG_GROUP_PERMISSIONS =
		PermissionUserFinder.class.getName() + ".countByOrgGroupPermissions";

	public static String COUNT_BY_ORG_PERMISSION =
		PermissionUserFinder.class.getName() + ".countByOrgPermission";

	public static String COUNT_BY_ORG_ROLE =
		PermissionUserFinder.class.getName() + ".countByOrgRole";

	public static String COUNT_BY_USER_PERMISSION =
		PermissionUserFinder.class.getName() + ".countByUserPermission";

	public static String COUNT_BY_USER_ROLE =
		PermissionUserFinder.class.getName() + ".countByUserRole";

	public static String FIND_BY_ADMIN_ROLE =
		PermissionUserFinder.class.getName() + ".findByAdminRole";

	public static String FIND_BY_GROUP_PERMISSION =
		PermissionUserFinder.class.getName() + ".findByGroupPermission";

	public static String FIND_BY_GROUP_ROLE =
		PermissionUserFinder.class.getName() + ".findByGroupRole";

	public static String FIND_BY_ORG_GROUP_PERMISSION =
		PermissionUserFinder.class.getName() + ".findByOrgGroupPermission";

	public static String FIND_BY_ORG_PERMISSION =
		PermissionUserFinder.class.getName() + ".findByOrgPermission";

	public static String FIND_BY_ORG_ROLE =
		PermissionUserFinder.class.getName() + ".findByOrgRole";

	public static String FIND_BY_USER_PERMISSION =
		PermissionUserFinder.class.getName() + ".findByUserPermission";

	public static String FIND_BY_USER_ROLE =
		PermissionUserFinder.class.getName() + ".findByUserRole";

	public static int COUNT_USERS_TYPE_ADMIN = 1;

	public static int COUNT_USERS_TYPE_PERMISSION = 2;

	public static int COUNT_USERS_TYPE_ROLE = 3;

	public static int countByOrgGroupPermissions(
			String companyId, String name, String primKey,
			String actionId)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_ORG_GROUP_PERMISSIONS);

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);
			qPos.add(primKey);
			qPos.add(actionId);

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

	public static int countByPermissionAndRole(
			String companyId, String groupId, String name,
			String primKey, String actionId, UserSearchTerms searchTerms)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			int count = countUsers(
				session, CustomSQLUtil.get(COUNT_BY_ADMIN_ROLE), companyId,
				groupId, name, primKey, actionId, searchTerms,
				COUNT_USERS_TYPE_ADMIN);

			count += countUsers(
				session, CustomSQLUtil.get(COUNT_BY_USER_PERMISSION), companyId,
				groupId, name, primKey, actionId, searchTerms,
				COUNT_USERS_TYPE_PERMISSION);

			count += countUsers(
				session, CustomSQLUtil.get(COUNT_BY_GROUP_PERMISSION),
				companyId, groupId, name, primKey, actionId, searchTerms,
				COUNT_USERS_TYPE_PERMISSION);

			count += countUsers(
				session, CustomSQLUtil.get(COUNT_BY_ORG_PERMISSION), companyId,
				groupId, name, primKey, actionId, searchTerms,
				COUNT_USERS_TYPE_PERMISSION);

			count += countUsers(
				session, CustomSQLUtil.get(COUNT_BY_USER_ROLE), companyId,
				groupId, name, primKey, actionId, searchTerms,
				COUNT_USERS_TYPE_ROLE);

			count += countUsers(
				session, CustomSQLUtil.get(COUNT_BY_GROUP_ROLE), companyId,
				groupId, name, primKey, actionId, searchTerms,
				COUNT_USERS_TYPE_ROLE);

			count += countUsers(
				session, CustomSQLUtil.get(COUNT_BY_ORG_ROLE), companyId,
				groupId, name, primKey, actionId, searchTerms,
				COUNT_USERS_TYPE_ROLE);

			return count;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static int countByUserAndOrgGroupPermission(
			String companyId, String name, String primKey,
			String actionId, UserSearchTerms searchTerms)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			int count = countUsers(
				session, CustomSQLUtil.get(COUNT_BY_ADMIN_ROLE), companyId,
				null, name, primKey, actionId, searchTerms,
				COUNT_USERS_TYPE_ADMIN);

			count += countUsers(
				session, CustomSQLUtil.get(COUNT_BY_USER_PERMISSION), companyId,
				null, name, primKey, actionId, searchTerms,
				COUNT_USERS_TYPE_PERMISSION);

			count += countUsers(
				session, CustomSQLUtil.get(COUNT_BY_ORG_GROUP_PERMISSION),
				companyId, null, name, primKey, actionId, searchTerms,
				COUNT_USERS_TYPE_PERMISSION);

			return count;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static int countUsers(
			Session session, String sql, String companyId, String groupId,
			String name, String primKey, String actionId,
			UserSearchTerms searchTerms, int countUsersType)
		throws SystemException {

		sql = CustomSQLUtil.replaceAndOperator(
			sql, searchTerms.isAndOperator());

		SQLQuery q = session.createSQLQuery(sql);

		q.setCacheable(false);

		q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (countUsersType != COUNT_USERS_TYPE_ADMIN) {
			qPos.add(name);

			if (countUsersType == COUNT_USERS_TYPE_PERMISSION) {
				qPos.add(primKey);
			}
			else if (countUsersType == COUNT_USERS_TYPE_ROLE){
				qPos.add(companyId);
				qPos.add(groupId);
			}

			qPos.add(actionId);
		}

		qPos.add(searchTerms.getFirstName());
		qPos.add(searchTerms.getFirstName());
		qPos.add(searchTerms.getMiddleName());
		qPos.add(searchTerms.getMiddleName());
		qPos.add(searchTerms.getLastName());
		qPos.add(searchTerms.getLastName());
		qPos.add(searchTerms.getEmailAddress());
		qPos.add(searchTerms.getEmailAddress());

		Iterator itr = q.list().iterator();

		if (itr.hasNext()) {
			Long count = (Long)itr.next();

			if (count != null) {
				return count.intValue();
			}
		}

		return 0;
	}

	public static List findByPermissionAndRole(
			String companyId, String groupId, String name,
			String primKey, String actionId, UserSearchTerms searchTerms,
			int begin, int end)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = "";

			sql += "(" + CustomSQLUtil.get(FIND_BY_ADMIN_ROLE) + ") ";
			sql += "UNION ";
			sql += "(" + CustomSQLUtil.get(FIND_BY_USER_PERMISSION) + ") ";
			sql += "UNION ";
			sql += "(" + CustomSQLUtil.get(FIND_BY_GROUP_PERMISSION) + ") ";
			sql += "UNION ";
			sql += "(" + CustomSQLUtil.get(FIND_BY_ORG_PERMISSION) + ") ";
			sql += "UNION ";
			sql += "(" + CustomSQLUtil.get(FIND_BY_USER_ROLE) + ") ";
			sql += "UNION ";
			sql += "(" + CustomSQLUtil.get(FIND_BY_GROUP_ROLE) + ") ";
			sql += "UNION ";
			sql += "(" + CustomSQLUtil.get(FIND_BY_ORG_ROLE) + ") ";
			sql += "ORDER BY lastName ASC, firstName ASC, middleName ASC ";

			sql = CustomSQLUtil.replaceAndOperator(
				sql, searchTerms.isAndOperator());

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addScalar("userId", Hibernate.STRING);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < 7; i++) {
				qPos.add(companyId);

				if (i > 0) {
					qPos.add(name);

					if (i < 4) {
						qPos.add(primKey);
					}
					else {
						qPos.add(companyId);
						qPos.add(groupId);
					}

					qPos.add(actionId);
				}

				qPos.add(searchTerms.getFirstName());
				qPos.add(searchTerms.getFirstName());
				qPos.add(searchTerms.getMiddleName());
				qPos.add(searchTerms.getMiddleName());
				qPos.add(searchTerms.getLastName());
				qPos.add(searchTerms.getLastName());
				qPos.add(searchTerms.getEmailAddress());
				qPos.add(searchTerms.getEmailAddress());
			}

			List list = new ArrayList();

			Iterator itr = QueryUtil.iterate(
				q, HibernateUtil.getDialect(), begin, end);

			while (itr.hasNext()) {
				String userId = (String)itr.next();

				User user = UserUtil.findByPrimaryKey(userId);

				list.add(user);
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

	public static List findByUserAndOrgGroupPermission(
			String companyId, String name, String primKey,
			String actionId, UserSearchTerms searchTerms,
			int begin, int end)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = "";

			sql += "(" + CustomSQLUtil.get(FIND_BY_ADMIN_ROLE) + ") ";
			sql += "UNION ";
			sql += "(" + CustomSQLUtil.get(FIND_BY_USER_PERMISSION) + ") ";
			sql += "UNION ";
			sql += "(" + CustomSQLUtil.get(FIND_BY_ORG_GROUP_PERMISSION) + ") ";
			sql += "ORDER BY lastName ASC, firstName ASC, middleName ASC ";

			sql = CustomSQLUtil.replaceAndOperator(
				sql, searchTerms.isAndOperator());

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addScalar("userId", Hibernate.STRING);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < 3; i++) {
				qPos.add(companyId);

				if (i > 0) {
					qPos.add(name);
					qPos.add(primKey);
					qPos.add(actionId);
				}

				qPos.add(searchTerms.getFirstName());
				qPos.add(searchTerms.getFirstName());
				qPos.add(searchTerms.getMiddleName());
				qPos.add(searchTerms.getMiddleName());
				qPos.add(searchTerms.getLastName());
				qPos.add(searchTerms.getLastName());
				qPos.add(searchTerms.getEmailAddress());
				qPos.add(searchTerms.getEmailAddress());
			}

			List list = new ArrayList();

			Iterator itr = QueryUtil.iterate(
				q, HibernateUtil.getDialect(), begin, end);

			while (itr.hasNext()) {
				String userId = (String)itr.next();

				User user = UserUtil.findByPrimaryKey(userId);

				list.add(user);
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

}