/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.social.model.SocialEquityLog;
import com.liferay.portlet.social.model.impl.SocialEquityLogImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * @author Zsolt Berentey
 */
public class SocialEquityLogFinderImpl extends
	BasePersistenceImpl<SocialEquityLog> implements SocialEquityLogFinder {

	public static final String _FIND_CONTRIBUTION_EQUITY_LOGS_BY_USER =
		SocialEquityLogFinder.class.getName() +
			".findContributionEquityLogsByUser";

	public static final String _FIND_CONTRIBUTION_EQUITY_LOGS_BY_ACTION_DATE =
		SocialEquityLogFinder.class.getName() +
			".findContributionEquityLogsByActionDate";

	@SuppressWarnings("unchecked")
	public List<SocialEquityLog> findContributionEquityLogsByActionDate(
			long groupId, long userId, int actionDate, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(
				_FIND_CONTRIBUTION_EQUITY_LOGS_BY_ACTION_DATE);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("SocialEquityLog", SocialEquityLogImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);
			qPos.add(actionDate);

			return (List<SocialEquityLog>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}

	}

	@SuppressWarnings("unchecked")
	public List<SocialEquityLog> findContributionEquityLogsByUser(
			long groupId, long userId, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(
				_FIND_CONTRIBUTION_EQUITY_LOGS_BY_USER);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("SocialEquityLog", SocialEquityLogImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);

			return (List<SocialEquityLog>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}

	}

}