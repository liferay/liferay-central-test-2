/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
 * <a href="PortalJobStore.java.html"><b><i>View Source</i></b></a>
 *
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