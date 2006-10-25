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
import com.liferay.portal.model.Organization;
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
 * <a href="OrganizationFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OrganizationFinder {

	public static String COUNT_BY_C_PO_N_S_C_Z_R_C =
		OrganizationFinder.class.getName() + ".countByC_PO_N_S_C_Z_R_C";

	public static String FIND_BY_C_PO_N_S_C_Z_R_C =
		OrganizationFinder.class.getName() + ".findByC_PO_N_S_C_Z_R_C";

	public static String JOIN_BY_GROUPS_PERMISSIONS =
		OrganizationFinder.class.getName() + ".joinByGroupsPermissions";

	public static String JOIN_BY_ORG_GROUP_PERMISSION =
		OrganizationFinder.class.getName() + ".joinByOrgGroupPermission";

	public static String JOIN_BY_ORGANIZATIONS_GROUPS =
		OrganizationFinder.class.getName() + ".joinByOrganizationsGroups";

	public static String JOIN_BY_ORGANIZATIONS_ROLES =
		OrganizationFinder.class.getName() + ".joinByOrganizationsRoles";

	public static int countByC_PO_N_S_C_Z_R_C(
			String companyId, String parentOrganizationId,
			String parentOrganizationComparator, String name, String street,
			String city, String zip, String regionId, String countryId,
			Map params, boolean andOperator)
		throws SystemException {

		name = StringUtil.lowerCase(name);
		street = StringUtil.lowerCase(street);
		city = StringUtil.lowerCase(city);
		zip = StringUtil.lowerCase(zip);

		if (params != null) {
			String resourceId = (String)params.get("permissionsResourceId");
			String groupId = (String)params.get("permissionsGroupId");

			if (Validator.isNotNull(groupId) &&
				Validator.isNotNull(resourceId)) {

				return _countByPermissions(
					companyId, parentOrganizationId,
					parentOrganizationComparator, name, street, city, zip,
					regionId, countryId, resourceId, groupId, andOperator);
			}
		}

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_PO_N_S_C_Z_R_C);

			sql = StringUtil.replace(sql, "[$JOIN$]", _getJoin(params));
			sql = StringUtil.replace(sql, "[$WHERE$]", _getWhere(params));
			sql = StringUtil.replace(
				sql, "[$PARENT_ORGANIZATION_ID_COMPARATOR$]",
				parentOrganizationComparator);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			_setJoin(qPos, params);
			qPos.add(companyId);
			qPos.add(parentOrganizationId);
			qPos.add(name);
			qPos.add(name);
			qPos.add(street);
			qPos.add(street);
			qPos.add(street);
			qPos.add(street);
			qPos.add(street);
			qPos.add(street);
			qPos.add(city);
			qPos.add(city);
			qPos.add(zip);
			qPos.add(zip);
			qPos.add(regionId);
			qPos.add(regionId);
			qPos.add(regionId);
			qPos.add(regionId);
			qPos.add(countryId);
			qPos.add(countryId);
			qPos.add(countryId);
			qPos.add(countryId);

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

	public static List findByC_PO_N_S_C_Z_R_C(
			String companyId, String parentOrganizationId,
			String parentOrganizationComparator, String name, String street,
			String city, String zip, String regionId, String countryId,
			Map params, boolean andOperator, int begin, int end)
		throws SystemException {

		name = StringUtil.lowerCase(name);
		street = StringUtil.lowerCase(street);
		city = StringUtil.lowerCase(city);
		zip = StringUtil.lowerCase(zip);

		if (params != null) {
			String resourceId = (String)params.get("permissionsResourceId");
			String groupId = (String)params.get("permissionsGroupId");

			if (Validator.isNotNull(groupId) &&
				Validator.isNotNull(resourceId)) {

				return _findByPermissions(
					companyId, parentOrganizationId,
					parentOrganizationComparator, name, street, city, zip,
					regionId, countryId, resourceId, groupId, andOperator,
					begin, end);
			}
		}

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_PO_N_S_C_Z_R_C);

			sql = StringUtil.replace(sql, "[$JOIN$]", _getJoin(params));
			sql = StringUtil.replace(sql, "[$WHERE$]", _getWhere(params));
			sql = StringUtil.replace(
				sql, "[$PARENT_ORGANIZATION_ID_COMPARATOR$]",
				parentOrganizationComparator);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			sql += " ORDER BY orgName ASC";

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addScalar("orgId", Hibernate.STRING);

			QueryPos qPos = QueryPos.getInstance(q);

			_setJoin(qPos, params);
			qPos.add(companyId);
			qPos.add(parentOrganizationId);
			qPos.add(name);
			qPos.add(name);
			qPos.add(street);
			qPos.add(street);
			qPos.add(street);
			qPos.add(street);
			qPos.add(street);
			qPos.add(street);
			qPos.add(city);
			qPos.add(city);
			qPos.add(zip);
			qPos.add(zip);
			qPos.add(regionId);
			qPos.add(regionId);
			qPos.add(regionId);
			qPos.add(regionId);
			qPos.add(countryId);
			qPos.add(countryId);
			qPos.add(countryId);
			qPos.add(countryId);

			List list = new ArrayList();

			Iterator itr = QueryUtil.iterate(
				q, HibernateUtil.getDialect(), begin, end);

			while (itr.hasNext()) {
				String organizationId = (String)itr.next();

				Organization organization = OrganizationUtil.findByPrimaryKey(
					organizationId);

				list.add(organization);
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

	private static int _countByPermissions(
			String companyId, String parentOrganizationId,
			String parentOrganizationComparator, String name, String street,
			String city, String zip, String regionId, String countryId,
			String resourceId, String groupId, boolean andOperator)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = null;

			sql = "(";
			sql += CustomSQLUtil.get(COUNT_BY_C_PO_N_S_C_Z_R_C);
			sql = StringUtil.replace(
				sql, "[$JOIN$]", _getJoin("groupsPermissions"));
			sql = StringUtil.replace(
				sql, "[$WHERE$]", _getWhere("groupsPermissions"));
			sql += ")";

			sql += " UNION ";

			sql += "(";
			sql += CustomSQLUtil.get(COUNT_BY_C_PO_N_S_C_Z_R_C);
			sql = StringUtil.replace(
				sql, "[$JOIN$]", _getJoin("orgGroupPermission"));
			sql = StringUtil.replace(
				sql, "[$WHERE$]", _getWhere("orgGroupPermission"));
			sql = StringUtil.replace(
				sql, "[$PARENT_ORGANIZATION_ID_COMPARATOR$]",
				parentOrganizationComparator);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			sql += ")";

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < 2; i++) {
				qPos.add(resourceId);

				if (i == 1) {
					qPos.add(groupId);
				}

				qPos.add(companyId);
				qPos.add(parentOrganizationId);
				qPos.add(name);
				qPos.add(name);
				qPos.add(street);
				qPos.add(street);
				qPos.add(street);
				qPos.add(street);
				qPos.add(street);
				qPos.add(street);
				qPos.add(city);
				qPos.add(city);
				qPos.add(zip);
				qPos.add(zip);
				qPos.add(regionId);
				qPos.add(regionId);
				qPos.add(regionId);
				qPos.add(regionId);
				qPos.add(countryId);
				qPos.add(countryId);
				qPos.add(countryId);
				qPos.add(countryId);
			}

			int count = 0;

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				Long i = (Long)itr.next();

				if (i != null) {
					count += i.intValue();
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

	private static List _findByPermissions(
			String companyId, String parentOrganizationId,
			String parentOrganizationComparator, String name, String street,
			String city, String zip, String regionId, String countryId,
			String resourceId, String groupId, boolean andOperator, int begin,
			int end)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = null;

			sql = "(";
			sql += CustomSQLUtil.get(FIND_BY_C_PO_N_S_C_Z_R_C);
			sql = StringUtil.replace(
				sql, "[$JOIN$]", _getJoin("groupsPermissions"));
			sql = StringUtil.replace(
				sql, "[$WHERE$]", _getWhere("groupsPermissions"));
			sql += ")";

			sql += " UNION ";

			sql += "(";
			sql += CustomSQLUtil.get(FIND_BY_C_PO_N_S_C_Z_R_C);
			sql = StringUtil.replace(
				sql, "[$JOIN$]", _getJoin("orgGroupPermission"));
			sql = StringUtil.replace(
				sql, "[$WHERE$]", _getWhere("orgGroupPermission"));
			sql = StringUtil.replace(
				sql, "[$PARENT_ORGANIZATION_ID_COMPARATOR$]",
				parentOrganizationComparator);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			sql += ")";

			sql += " ORDER BY orgName ASC";

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addScalar("orgId", Hibernate.STRING);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < 2; i++) {
				qPos.add(resourceId);

				if (i == 1) {
					qPos.add(groupId);
				}

				qPos.add(companyId);
				qPos.add(parentOrganizationId);
				qPos.add(name);
				qPos.add(name);
				qPos.add(street);
				qPos.add(street);
				qPos.add(street);
				qPos.add(street);
				qPos.add(street);
				qPos.add(street);
				qPos.add(city);
				qPos.add(city);
				qPos.add(zip);
				qPos.add(zip);
				qPos.add(regionId);
				qPos.add(regionId);
				qPos.add(regionId);
				qPos.add(regionId);
				qPos.add(countryId);
				qPos.add(countryId);
				qPos.add(countryId);
				qPos.add(countryId);
			}

			List list = new ArrayList();

			Iterator itr = QueryUtil.iterate(
				q, HibernateUtil.getDialect(), begin, end);

			while (itr.hasNext()) {
				String organizationId = (String)itr.next();

				Organization organization = OrganizationUtil.findByPrimaryKey(
					organizationId);

				list.add(organization);
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
		String join = StringPool.BLANK;

		if (key.equals("groupsPermissions")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_PERMISSIONS);
		}
		else if (key.equals("organizationsGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_ORGANIZATIONS_GROUPS);
		}
		else if (key.equals("organizationsRoles")) {
			join = CustomSQLUtil.get(JOIN_BY_ORGANIZATIONS_ROLES);
		}
		else if (key.equals("orgGroupPermission")) {
			join = CustomSQLUtil.get(JOIN_BY_ORG_GROUP_PERMISSION);
		}

		if (Validator.isNotNull(join)) {
			int pos = join.indexOf("WHERE");

			if (pos != -1) {
				join = join.substring(0, pos);
			}
		}

		return join;
	}

	private static String _getWhere(Map params) {
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

		if (key.equals("groupsPermissions")) {
			join = CustomSQLUtil.get(JOIN_BY_GROUPS_PERMISSIONS);
		}
		else if (key.equals("organizationsGroups")) {
			join = CustomSQLUtil.get(JOIN_BY_ORGANIZATIONS_GROUPS);
		}
		else if (key.equals("organizationsRoles")) {
			join = CustomSQLUtil.get(JOIN_BY_ORGANIZATIONS_ROLES);
		}
		else if (key.equals("orgGroupPermission")) {
			join = CustomSQLUtil.get(JOIN_BY_ORG_GROUP_PERMISSION);
		}

		if (Validator.isNotNull(join)) {
			int pos = join.indexOf("WHERE");

			if (pos != -1) {
				join = join.substring(pos + 5, join.length()) + " AND ";
			}
		}

		return join;
	}

	private static void _setJoin(QueryPos qPos, Map params) {
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