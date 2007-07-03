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

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.util.StringUtil;
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
 * <a href="RoleFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RoleFinder {

	public static String COUNT_BY_C_N_D_T =
		RoleFinder.class.getName() + ".countByC_N_D_T";

	public static String COUNT_BY_COMMUNITY =
		RoleFinder.class.getName() + ".countByCommunity";

	public static String COUNT_BY_ORGANIZATION =
		RoleFinder.class.getName() + ".countByOrganization";

	public static String COUNT_BY_USER =
		RoleFinder.class.getName() + ".countByUser";

	public static String COUNT_BY_USER_GROUP =
		RoleFinder.class.getName() + ".countByUserGroup";

	public static String FIND_BY_USER_GROUP_ROLE =
		RoleFinder.class.getName() + ".findByUserGroupRole";

	public static String FIND_BY_C_N =
		RoleFinder.class.getName() + ".findByC_N";

	public static String FIND_BY_U_G =
		RoleFinder.class.getName() + ".findByU_G";

	public static String FIND_BY_C_N_D_T =
		RoleFinder.class.getName() + ".findByC_N_D_T";

	public static String FIND_BY_C_N_S_P =
		RoleFinder.class.getName() + ".findByC_N_S_P";

	public static int countByR_U(long roleId, long userId)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			StringMaker sm = new StringMaker();

			sm.append("(");
			sm.append(CustomSQLUtil.get(COUNT_BY_COMMUNITY));
			sm.append(") UNION (");
			sm.append(CustomSQLUtil.get(COUNT_BY_ORGANIZATION));
			sm.append(") UNION (");
			sm.append(CustomSQLUtil.get(COUNT_BY_USER));
			sm.append(") UNION (");
			sm.append(CustomSQLUtil.get(COUNT_BY_USER_GROUP));
			sm.append(")");

			SQLQuery q = session.createSQLQuery(sm.toString());

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < 4; i++) {
				qPos.add(roleId);
				qPos.add(userId);
			}

			int count = 0;

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				Long l = (Long)itr.next();

				if (l != null) {
					count += l.intValue();
				}
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

	public static int countByC_N_D_T(
			long companyId, String name, String description, Integer type)
		throws SystemException {

		name = StringUtil.lowerCase(name);
		description = StringUtil.lowerCase(description);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_N_D_T);

			if (type == null) {
				sql = StringUtil.replace(sql, "AND (Role_.type_ = ?)", "");
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);
			qPos.add(name);
			qPos.add(description);
			qPos.add(description);

			if (type != null) {
				qPos.add(type);
			}

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

	public static List findByUserGroupRole(long userId, long groupId)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_USER_GROUP_ROLE);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Role_", RoleImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);
			qPos.add(groupId);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static Role findByC_N(long companyId, String name)
		throws NoSuchRoleException, SystemException {

		name = StringUtil.lowerCase(name);

		String finderClassName = Role.class.getName();
		String finderMethodName = "findByC_N";
		Object finderArgs[] = new Object[] {new Long(companyId), name};

		Object result = FinderCache.getResult(
			finderClassName, finderMethodName, finderArgs);

		if (result == null) {
			Session session = null;

			try {
				session = HibernateUtil.openSession();

				String sql = CustomSQLUtil.get(FIND_BY_C_N);

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Role_", RoleImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);
				qPos.add(name);

				Iterator itr = q.list().iterator();

				if (itr.hasNext()) {
					Role role = (Role)itr.next();

					FinderCache.putResult(
						finderClassName, finderMethodName, finderArgs, role);

					return role;
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
		else {
			return (Role)result;
		}
	}

	public static List findByU_G(long userId, long groupId)
		throws SystemException {

		return findByU_G(userId, new long[] {groupId});
	}

	public static List findByU_G(long userId, long[] groupIds)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_U_G);

			sql = StringUtil.replace(
				sql, "[$GROUP_IDS$]", _getGroupIds(groupIds, "Groups_Roles"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Role_", RoleImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);
			_setGroupIds(qPos, groupIds);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static List findByU_G(long userId, List groups)
		throws SystemException {

		long[] groupIds = new long[groups.size()];

		for (int i = 0; i < groups.size(); i++) {
			Group group = (Group)groups.get(i);

			groupIds[i] = group.getGroupId();
		}

		return findByU_G(userId, groupIds);
	}

	public static List findByC_N_D_T(
			long companyId, String name, String description, Integer type,
			int begin, int end)
		throws SystemException {

		name = StringUtil.lowerCase(name);
		description = StringUtil.lowerCase(description);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_N_D_T);

			if (type == null) {
				sql = StringUtil.replace(sql, "AND (Role_.type_ = ?)", "");
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Role_", RoleImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);
			qPos.add(name);
			qPos.add(description);
			qPos.add(description);

			if (type != null) {
				qPos.add(type);
			}

			return QueryUtil.list(q, HibernateUtil.getDialect(), begin, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static Map findByC_N_S_P(
			long companyId, String name, int scope, String primKey)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_N_S_P);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("roleName", Hibernate.STRING);
			q.addScalar("actionId", Hibernate.STRING);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(name);
			qPos.add(scope);
			qPos.add(primKey);

			Map roleMap = new HashMap();

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				Object[] array = (Object[])itr.next();

				String roleName = (String)array[0];
				String actionId = (String)array[1];

				List roleList = (List)roleMap.get(roleName);

				if (roleList == null) {
					roleList = new ArrayList();
				}

				roleList.add(actionId);

				roleMap.put(roleName, roleList);
			}

			return roleMap;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	private static String _getGroupIds(long[] groupIds, String table) {
		StringMaker sm = new StringMaker();

		for (int i = 0; i < groupIds.length; i++) {
			sm.append(table);
			sm.append(".groupId = ?");

			if ((i + 1) < groupIds.length) {
				sm.append(" OR ");
			}
		}

		return sm.toString();
	}

	private static void _setGroupIds(QueryPos qPos, long[] groupIds) {
		for (int i = 0; i < groupIds.length; i++) {
			qPos.add(groupIds[i]);
		}
	}

}