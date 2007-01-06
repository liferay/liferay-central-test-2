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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * <a href="JournalTemplateFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalTemplateFinder {

	public static String COUNT_BY_C_G_T_S_N_D =
		JournalTemplateFinder.class.getName() + ".countByC_G_T_S_N_D";

	public static String FIND_BY_C_G_T_S_N_D =
		JournalTemplateFinder.class.getName() + ".findByC_G_T_S_N_D";

	public static int countByC_G_T_S_N_D(
			String companyId, long groupId, String templateId,
			String structureId, String structureIdComparator, String name,
			String description, boolean andOperator)
		throws SystemException {

		templateId = StringUtil.upperCase(templateId);
		name = StringUtil.lowerCase(name);
		description = StringUtil.lowerCase(description);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_G_T_S_N_D);

			boolean customSqlVendorOracle = GetterUtil.getBoolean(
				PropsUtil.get(PropsUtil.CUSTOM_SQL_VENDOR_ORACLE));

			if (structureIdComparator.equals(StringPool.NOT_EQUAL)) {
				String replaceWith =
					"structureId != ? AND structureId IS NOT NULL";

				if (customSqlVendorOracle) {
					replaceWith = "structureId IS NOT NULL";
				}

				sql = StringUtil.replace(
					sql, "structureId = ? [$AND_OR_NULL_CHECK$]", replaceWith);
			}

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(groupId);
			qPos.add(templateId);
			qPos.add(templateId);

			if (structureIdComparator.equals(StringPool.NOT_EQUAL)) {
				if (customSqlVendorOracle) {
				}
				else {
					qPos.add(structureId);
				}
			}
			else {
				qPos.add(structureId);
			}

			if (structureIdComparator.equals(StringPool.EQUAL)) {
				qPos.add(structureId);
			}

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

	public static List findByC_G_T_S_N_D(
			String companyId, long groupId, String templateId,
			String structureId, String structureIdComparator, String name,
			String description, boolean andOperator, int begin, int end,
			OrderByComparator obc)
		throws SystemException {

		templateId = StringUtil.upperCase(templateId);
		name = StringUtil.lowerCase(name);
		description = StringUtil.lowerCase(description);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_G_T_S_N_D);

			boolean customSqlVendorOracle = GetterUtil.getBoolean(
				PropsUtil.get(PropsUtil.CUSTOM_SQL_VENDOR_ORACLE));

			if (structureIdComparator.equals(StringPool.NOT_EQUAL)) {
				String replaceWith =
					"structureId != ? AND structureId IS NOT NULL";

				if (customSqlVendorOracle) {
					replaceWith = "structureId IS NOT NULL";
				}

				sql = StringUtil.replace(
					sql, "structureId = ? [$AND_OR_NULL_CHECK$]", replaceWith);
			}

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addEntity("JournalTemplate", JournalTemplateImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(groupId);
			qPos.add(templateId);
			qPos.add(templateId);

			if (structureIdComparator.equals(StringPool.NOT_EQUAL)) {
				if (customSqlVendorOracle) {
				}
				else {
					qPos.add(structureId);
				}
			}
			else {
				qPos.add(structureId);
			}

			if (structureIdComparator.equals(StringPool.EQUAL)) {
				qPos.add(structureId);
			}

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

}
