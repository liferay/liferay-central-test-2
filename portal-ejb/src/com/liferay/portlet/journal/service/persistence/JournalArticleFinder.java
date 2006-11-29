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
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.util.StringUtil;
import com.liferay.util.cal.CalendarUtil;
import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.sql.Timestamp;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * <a href="JournalArticleFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalArticleFinder {

	public static String COUNT_BY_C_A_V_G_T_D_C_T_S_T_D_A_E_R =
		JournalArticleFinder.class.getName() +
			".countByC_A_V_G_T_D_C_T_S_T_D_A_E_R";

	public static String FIND_BY_C_A_V_G_T_D_C_T_S_T_D_A_E_R =
		JournalArticleFinder.class.getName() +
			".findByC_A_V_G_T_D_C_T_S_T_D_A_E_R";

	public static int countByC_A_V_G_T_D_C_T_S_T_D_A_E_R(
			String companyId, String articleId, Double version, String groupId,
			String title, String description, String content, String type,
			String structureId, String templateId, Date displayDateGT,
			Date displayDateLT, Boolean approved, Boolean expired,
			Date reviewDate, boolean andOperator)
		throws SystemException {

		articleId = StringUtil.upperCase(articleId);
		title = StringUtil.lowerCase(title);
		content = StringUtil.lowerCase(content);
		Timestamp displayDateGT_TS = CalendarUtil.getTimestamp(displayDateGT);
		Timestamp displayDateLT_TS = CalendarUtil.getTimestamp(displayDateLT);
		Timestamp reviewDate_TS = CalendarUtil.getTimestamp(reviewDate);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(
				COUNT_BY_C_A_V_G_T_D_C_T_S_T_D_A_E_R);

			if (version == null) {
				sql = StringUtil.replace(
					sql, "(version = ?) [$AND_OR_CONNECTOR$]", "");
			}

			if (approved == null) {
				sql = StringUtil.replace(sql, "(approved = ?) AND", "");
			}

			if (expired == null) {
				sql = StringUtil.replace(sql, "(expired = ?) AND", "");
			}

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(articleId);
			qPos.add(articleId);

			if (version != null) {
				qPos.add(version);
			}

			qPos.add(groupId);
			qPos.add(groupId);
			qPos.add(title);
			qPos.add(title);
			qPos.add(description);
			qPos.add(description);
			qPos.add(content);
			qPos.add(content);
			qPos.add(type);
			qPos.add(type);
			qPos.add(structureId);
			qPos.add(structureId);
			qPos.add(templateId);
			qPos.add(templateId);
			qPos.add(displayDateGT_TS);
			qPos.add(displayDateGT_TS);
			qPos.add(displayDateLT_TS);
			qPos.add(displayDateLT_TS);

			if (approved != null) {
				qPos.add(approved);
			}

			if (expired != null) {
				qPos.add(expired);
			}

			qPos.add(reviewDate_TS);
			qPos.add(reviewDate_TS);

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

	public static List findByC_A_V_G_T_D_C_T_S_T_D_A_E_R(
			String companyId, String articleId, Double version, String groupId,
			String title, String description, String content, String type,
			String structureId, String templateId, Date displayDateGT,
			Date displayDateLT, Boolean approved, Boolean expired,
			Date reviewDate, boolean andOperator, int begin, int end,
			OrderByComparator obc)
		throws SystemException {

		articleId = StringUtil.upperCase(articleId);
		title = StringUtil.lowerCase(title);
		content = StringUtil.lowerCase(content);
		Timestamp displayDateGT_TS = CalendarUtil.getTimestamp(displayDateGT);
		Timestamp displayDateLT_TS = CalendarUtil.getTimestamp(displayDateLT);
		Timestamp reviewDate_TS = CalendarUtil.getTimestamp(reviewDate);

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_A_V_G_T_D_C_T_S_T_D_A_E_R);

			if (version == null) {
				sql = StringUtil.replace(
					sql, "(version = ?) [$AND_OR_CONNECTOR$]", "");
			}

			if (approved == null) {
				sql = StringUtil.replace(sql, "(approved = ?) AND", "");
			}

			if (expired == null) {
				sql = StringUtil.replace(sql, "(expired = ?) AND", "");
			}

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addEntity("JournalArticle", JournalArticleImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(articleId);
			qPos.add(articleId);

			if (version != null) {
				qPos.add(version);
			}

			qPos.add(groupId);
			qPos.add(groupId);
			qPos.add(title);
			qPos.add(title);
			qPos.add(description);
			qPos.add(description);
			qPos.add(content);
			qPos.add(content);
			qPos.add(type);
			qPos.add(type);
			qPos.add(structureId);
			qPos.add(structureId);
			qPos.add(templateId);
			qPos.add(templateId);
			qPos.add(displayDateGT_TS);
			qPos.add(displayDateGT_TS);
			qPos.add(displayDateLT_TS);
			qPos.add(displayDateLT_TS);

			if (approved != null) {
				qPos.add(approved);
			}

			if (expired != null) {
				qPos.add(expired);
			}

			qPos.add(reviewDate_TS);
			qPos.add(reviewDate_TS);

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