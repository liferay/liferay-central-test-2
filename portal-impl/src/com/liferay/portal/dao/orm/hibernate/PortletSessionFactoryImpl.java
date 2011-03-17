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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;

import javax.sql.DataSource;

import org.hibernate.engine.SessionFactoryImplementor;

/**
 * @author Shuyang Zhou
 */
public class PortletSessionFactoryImpl extends SessionFactoryImpl {

	public Session openSession() throws ORMException {
		SessionFactoryImplementor sessionFactoryImplementor =
			getSessionFactoryImplementor();

		org.hibernate.Session session = null;

		if (PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED) {
			Connection connection = CurrentConnectionUtil.getConnection(
				_dataSource);

			if (connection == null) {
				session = sessionFactoryImplementor.getCurrentSession();
			}
			else {
				session = sessionFactoryImplementor.openSession(connection);
			}
		}
		else {
			session = sessionFactoryImplementor.openSession();
		}

		if (_log.isDebugEnabled()) {
			org.hibernate.impl.SessionImpl sessionImpl =
				(org.hibernate.impl.SessionImpl)session;

			_log.debug(
				"Session is using connection release mode " +
					sessionImpl.getConnectionReleaseMode());
		}

		return wrapSession(session);
	}

	public void setDataSource(DataSource dataSource) {
		_dataSource = dataSource;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletSessionFactoryImpl.class);

	private DataSource _dataSource;

}