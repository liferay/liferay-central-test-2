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

package com.liferay.portal.scheduler.quartz;

import com.liferay.portal.dao.db.DB2DB;
import com.liferay.portal.dao.db.HypersonicDB;
import com.liferay.portal.dao.db.PostgreSQLDB;
import com.liferay.portal.dao.db.SQLServerDB;
import com.liferay.portal.dao.db.SybaseDB;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.quartz.impl.jdbcjobstore.DB2v8Delegate;
import org.quartz.impl.jdbcjobstore.DriverDelegate;
import org.quartz.impl.jdbcjobstore.HSQLDBDelegate;
import org.quartz.impl.jdbcjobstore.JobStoreTX;
import org.quartz.impl.jdbcjobstore.MSSQLDelegate;
import org.quartz.impl.jdbcjobstore.NoSuchDelegateException;
import org.quartz.impl.jdbcjobstore.PostgreSQLDelegate;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalJobStore extends JobStoreTX {

	protected DriverDelegate getDelegate() throws NoSuchDelegateException {
		if (_driverDelegate != null) {
			return _driverDelegate;
		}

		try {
			Class<?> driverDelegateClass = StdJDBCDelegate.class;

			DB db = DBFactoryUtil.getDB();

			if (db instanceof DB2DB) {
				driverDelegateClass = DB2v8Delegate.class;
			}
			else if (db instanceof HypersonicDB) {
				driverDelegateClass = HSQLDBDelegate.class;
			}
			else if (db instanceof PostgreSQLDB) {
				driverDelegateClass = PostgreSQLDelegate.class;
			}
			else if (db instanceof SQLServerDB) {
				driverDelegateClass = MSSQLDelegate.class;
			}
			else if (db instanceof SybaseDB) {
				driverDelegateClass = MSSQLDelegate.class;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Instantiating " + driverDelegateClass);
			}

			setDriverDelegateClass(driverDelegateClass.getName());

			_driverDelegate = super.getDelegate();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Using driver delegate " +
						_driverDelegate.getClass().getName());
			}

			return _driverDelegate;
		}
		catch (NoSuchDelegateException nsde) {
			throw nsde;
		}
		catch (Exception e) {
			throw new NoSuchDelegateException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PortalJobStore.class);

	private DriverDelegate _driverDelegate;

}