/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.announcements.model.AnnouncementEntry;
import com.liferay.portlet.announcements.model.impl.AnnouncementEntryImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementFlagImpl;
import com.liferay.util.cal.CalendarUtil;
import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.sql.Timestamp;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * <a href="AnnouncementFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Thiago Moreira
 * @author Raymond Augé
 *
 */
public class AnnouncementEntryFinderImpl implements AnnouncementEntryFinder {

	public static String COUNT_BY_C_C_F =
		AnnouncementEntryFinder.class.getName() + ".countByC_C_F";

	public static String COUNT_BY_C_C_F_NO_JOIN =
		AnnouncementEntryFinder.class.getName() + ".countByC_C_F.noJoin";

	public static String FIND_BY_C_C_F =
		AnnouncementEntryFinder.class.getName() + ".findByC_C_F";

	public static String FIND_BY_C_C_F_NO_JOIN =
		AnnouncementEntryFinder.class.getName() + ".findByC_C_F.noJoin";

	public int countByC_C_F(
			long userId, long classNameId, long[] classPKs, int displayMonth,
			int displayDay, int displayYear, int expirationMonth,
			int expirationDay, int expirationYear, int flag, boolean alert)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_C_F);

			if (flag == AnnouncementFlagImpl.NOT_HIDDEN) {
				sql = CustomSQLUtil.get(COUNT_BY_C_C_F_NO_JOIN);
			}

			sql = StringUtil.replace(
				sql, "[$CLASS_PKS$]", getClassPKs(classNameId, classPKs));

			sql = CustomSQLUtil.replaceAndOperator(sql, true);

			if (_log.isDebugEnabled()) {
				_log.debug(sql);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setClassPKs(qPos, classNameId, classPKs);

			setDates(
				qPos, displayMonth, displayDay, displayYear, expirationMonth,
				expirationDay, expirationYear);

			qPos.add(alert);
			qPos.add(AnnouncementFlagImpl.HIDDEN);
			qPos.add(userId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		} catch (Exception e) {
			throw new SystemException(e);
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public int countByCNM_F(
			long userId, LinkedHashMap<Long,Long[]> entriesParams,
			int displayMonth, int displayDay, int displayYear,
			int expirationMonth, int expirationDay, int expirationYear,
			int flag, boolean alert)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_C_F);

			if (flag == AnnouncementFlagImpl.NOT_HIDDEN) {
				sql = CustomSQLUtil.get(COUNT_BY_C_C_F_NO_JOIN);
			}

			sql = StringUtil.replace(
				sql, "[$CLASS_PKS$]", getClassPKs(entriesParams));

			sql = CustomSQLUtil.replaceAndOperator(sql, true);

			if (_log.isDebugEnabled()) {
				_log.debug(sql);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setClassPKs(qPos, entriesParams);

			setDates(
				qPos, displayMonth, displayDay, displayYear, expirationMonth,
				expirationDay, expirationYear);

			qPos.add(alert);
			qPos.add(AnnouncementFlagImpl.HIDDEN);
			qPos.add(userId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		} catch (Exception e) {
			throw new SystemException(e);
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public List<AnnouncementEntry> findByC_C_F(
			long userId, long classNameId, long[] classPKs, int displayMonth,
			int displayDay, int displayYear, int expirationMonth,
			int expirationDay, int expirationYear, int flag, boolean alert,
			int begin, int end)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_C_F);

			if (flag == AnnouncementFlagImpl.NOT_HIDDEN) {
				sql = CustomSQLUtil.get(FIND_BY_C_C_F_NO_JOIN);
			}

			sql = StringUtil.replace(
				sql, "[$CLASS_PKS$]", getClassPKs(classNameId, classPKs));

			sql = CustomSQLUtil.replaceAndOperator(sql, true);

			if (_log.isDebugEnabled()) {
				_log.debug(sql);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("AnnouncementEntry", AnnouncementEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			setClassPKs(qPos, classNameId, classPKs);

			setDates(
				qPos, displayMonth, displayDay, displayYear, expirationMonth,
				expirationDay, expirationYear);

			qPos.add(alert);
			qPos.add(AnnouncementFlagImpl.HIDDEN);
			qPos.add(userId);

			return (List<AnnouncementEntry>)QueryUtil.list(
				q, HibernateUtil.getDialect(), begin, end);

		} catch (Exception e) {
			throw new SystemException(e);
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public List<AnnouncementEntry> findByCNM_F(
			long userId, LinkedHashMap<Long,Long[]> entriesParams,
			int displayMonth, int displayDay, int displayYear,
			int expirationMonth, int expirationDay, int expirationYear,
			int flag, boolean alert, int begin, int end)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_C_F);

			if (flag == AnnouncementFlagImpl.NOT_HIDDEN) {
				sql = CustomSQLUtil.get(FIND_BY_C_C_F_NO_JOIN);
			}

			sql = StringUtil.replace(
				sql, "[$CLASS_PKS$]", getClassPKs(entriesParams));

			sql = CustomSQLUtil.replaceAndOperator(sql, true);

			if (_log.isDebugEnabled()) {
				_log.debug(sql);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("AnnouncementEntry", AnnouncementEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			setClassPKs(qPos, entriesParams);

			setDates(
				qPos, displayMonth, displayDay, displayYear, expirationMonth,
				expirationDay, expirationYear);

			qPos.add(alert);
			qPos.add(AnnouncementFlagImpl.HIDDEN);
			qPos.add(userId);

			return (List<AnnouncementEntry>)QueryUtil.list(
				q, HibernateUtil.getDialect(), begin, end);

		} catch (Exception e) {
			throw new SystemException(e);
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	protected void setClassPKs(
			QueryPos qPos, long classNameId, long[] classPKs) {

		qPos.add(classNameId);

		for (int i = 0; i < classPKs.length; i++) {
			qPos.add(classPKs[i]);
		}
	}

	protected void setClassPKs(
			QueryPos qPos, Long classNameId, Long[] classPKs) {
		long classNameIdl = classNameId.longValue();

		long[] classPKsl = new long[classPKs.length];

		for (int i = 0; i < classPKs.length; i++) {
			classPKsl[i] = classPKs[i].longValue();
		}

		setClassPKs(qPos, classNameIdl, classPKsl);
	}

	protected void setClassPKs(
			QueryPos qPos, LinkedHashMap<Long,Long[]> entriesParams) {

		if (entriesParams == null) {
			return;
		}

		Iterator<Map.Entry<Long,Long[]>> itr =
			entriesParams.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<Long,Long[]> entry = itr.next();

			Long classNameId = entry.getKey();
			Long[] classPKs = entry.getValue();

			setClassPKs(qPos, classNameId, classPKs);
		}
	}

	protected void setDates(QueryPos qPos, int displayMonth, int displayDay,
			int displayYear, int expirationMonth, int expirationDay,
			int expirationYear) {

		Date displayDate = null;
		Date expirationDate = null;
		Date nowDate = new Date();

		try {
			displayDate = PortalUtil.getDate(
				displayMonth, displayDay, displayYear,
					new PortalException());
		}
		catch (PortalException pe) {
		}

		try {
			expirationDate = PortalUtil.getDate(
				expirationMonth, expirationDay, expirationYear,
					new PortalException());
		}
		catch (PortalException pe) {
		}

		if (displayDate == null) {
			displayDate = nowDate;
		}

		if (expirationDate == null) {
			expirationDate = nowDate;
		}

		Timestamp displayDateTS = CalendarUtil.getTimestamp(displayDate);
		Timestamp expirationDateTS = CalendarUtil.getTimestamp(expirationDate);

		qPos.add(displayDateTS);
		qPos.add(displayDateTS);
		qPos.add(expirationDateTS);
		qPos.add(expirationDateTS);
	}

	protected String getClassPKs(long classNameId, long[] classPKs) {
		StringMaker sm = new StringMaker();

		sm.append("(AnnouncementEntry.classNameId = ?) AND (");

		for (int i = 0; i < classPKs.length; i++) {
			sm.append("(AnnouncementEntry.classPK = ?)");
			if (i + 1 != classPKs.length) {
				sm.append(" OR ");
			}
			else {
				sm.append(")");
			}
		}

		return sm.toString();
	}

	protected String getClassPKs(Long classNameId, Long[] classPKs) {
		long classNameIdl = classNameId.longValue();

		long[] classPKsl = new long[classPKs.length];

		for (int i = 0; i < classPKs.length; i++) {
			classPKsl[i] = classPKs[i].longValue();
		}

		return getClassPKs(classNameIdl, classPKsl);
	}

	protected String getClassPKs(
			LinkedHashMap<Long,Long[]> entriesParams) {

		if (entriesParams == null) {
			return StringPool.BLANK;
		}

		StringMaker sm = new StringMaker();

		Iterator<Map.Entry<Long,Long[]>> itr =
			entriesParams.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<Long,Long[]> entry = itr.next();

			Long classNameId = entry.getKey();
			Long[] classPKs = entry.getValue();

			sm.append("(");
			sm.append(getClassPKs(classNameId, classPKs));
			sm.append(")");

			if (itr.hasNext()) {
				sm.append(" OR ");
			}
		}

		return sm.toString();
	}

	private static Log _log = LogFactory.getLog(
		AnnouncementEntryFinderImpl.class);

}