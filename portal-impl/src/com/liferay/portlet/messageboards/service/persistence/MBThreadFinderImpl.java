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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBThreadImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="MBThreadFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MBThreadFinderImpl
	extends BasePersistenceImpl<MBThread> implements MBThreadFinder {

	public static String COUNT_BY_S_G_U_S =
		MBThreadFinder.class.getName() + ".countByS_G_U_S";

	public static String FIND_BY_S_G_U_S =
		MBThreadFinder.class.getName() + ".findByS_G_U_S";

	public int countByS_G_U_S(long groupId, long userId, int status)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_S_G_U_S);

			if (status != StatusConstants.ANY) {
				sql = StringUtil.replace(
					sql, "[$STATUS$]", "AND (MBThread.status = ?)");
			}
			else {
				sql = StringUtil.replace(sql, "[$STATUS$]", StringPool.BLANK);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(PortalUtil.getClassNameId(MBThread.class.getName()));
			qPos.add(groupId);

			if (status != StatusConstants.ANY) {
				qPos.add(status);
			}

			qPos.add(userId);

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

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
			closeSession(session);
		}
	}

	public List<MBThread> findByS_G_U_S(
			long groupId, long userId, int status, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_S_G_U_S);

			if (status != StatusConstants.ANY) {
				sql = StringUtil.replace(
					sql, "[$STATUS$]", "AND (MBThread.status = ?)");
			}
			else {
				sql = StringUtil.replace(sql, "[$STATUS$]", StringPool.BLANK);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(PortalUtil.getClassNameId(MBThread.class.getName()));
			qPos.add(groupId);

			if (status != StatusConstants.ANY) {
				qPos.add(status);
			}

			qPos.add(userId);

			return (List<MBThread>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}