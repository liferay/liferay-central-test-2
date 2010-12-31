/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.calendar.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.model.CalEventConstants;
import com.liferay.portlet.calendar.model.impl.CalEventImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Zsolt Balogh
 */
public class CalEventFinderImpl
	extends BasePersistenceImpl<CalEvent> implements CalEventFinder {

	public static String FIND_BY_FUTURE_REMINDERS =
		CalEventFinder.class.getName() + ".findByFutureReminders";

	public static String FIND_BY_NO_ASSETS =
		CalEventFinder.class.getName() + ".findByNoAssets";

	public static String FIND_BY_G_SD =
		CalEventFinder.class.getName() + ".findByG_SD";

	public List<CalEvent> findByFutureReminders() throws SystemException {
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.HOUR, -24);

		Timestamp calendar_TS = CalendarUtil.getTimestamp(calendar.getTime());

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_FUTURE_REMINDERS);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("CalEvent", CalEventImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(CalEventConstants.REMIND_BY_NONE);
			qPos.add(calendar_TS);
			qPos.add(calendar_TS);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<CalEvent> findByNoAssets() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_ASSETS);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("CalEvent", CalEventImpl.class);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<CalEvent> findByG_SD(
			long groupId, Date startDateGT, Date startDateLT,
			boolean timeZoneSensitive)
		throws SystemException {

		Timestamp startDateGT_TS = CalendarUtil.getTimestamp(startDateGT);
		Timestamp startDateLT_TS = CalendarUtil.getTimestamp(startDateLT);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_SD);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("CalEvent", CalEventImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(startDateGT_TS);
			qPos.add(startDateLT_TS);
			qPos.add(timeZoneSensitive);
			qPos.add(false);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}