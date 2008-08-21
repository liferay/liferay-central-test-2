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

package com.liferay.portal.spring.hibernate;

import com.liferay.portal.dao.orm.hibernate.DB2Dialect;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.sql.DBUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.DB2400Dialect;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.DialectFactory;
import org.hibernate.dialect.SybaseDialect;

/**
 * <a href="PortalHibernateConfiguration.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortalHibernateConfiguration
	extends TransactionAwareConfiguration {

	protected String determineDialect() {
		Dialect dialect = null;

		Connection con = null;

		try {
			con = DataAccess.getConnection();

			DatabaseMetaData metaData = con.getMetaData();

			String dbName = metaData.getDatabaseProductName();
			int dbMajorVersion = metaData.getDatabaseMajorVersion();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Determining dialect for " + dbName + " " + dbMajorVersion);
			}

			if (dbName.startsWith("HSQL")) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Liferay is configured to use Hypersonic as its " +
							"database. Do NOT use Hypersonic in production. " +
								"Hypersonic is an embedded database useful " +
									"for development and demo'ing purposes.");
				}
			}

			if (dbName.equals("ASE") && (dbMajorVersion == 15)) {
				dialect = new SybaseDialect();
			}
			else if (dbName.startsWith("DB2") && (dbMajorVersion == 9)) {
				dialect = new DB2Dialect();
			}
			else {
				dialect = DialectFactory.determineDialect(
					dbName, dbMajorVersion);
			}

			DBUtil.setInstance(dialect);

			if (_log.isInfoEnabled()) {
				_log.info("Using dialect " + dialect.getClass().getName());
			}
		}
		catch (Exception e) {
			String msg = GetterUtil.getString(e.getMessage());

			if (msg.indexOf("explicitly set for database: DB2") != -1) {
				dialect = new DB2400Dialect();

				if (_log.isWarnEnabled()) {
					_log.warn(
						"DB2400Dialect was dynamically chosen as the " +
							"Hibernate dialect for DB2. This can be " +
								"overriden in portal.properties");
				}
			}
			else {
				_log.error(e, e);
			}
		}
		finally {
			DataAccess.cleanUp(con);
		}

		if (dialect == null) {
			throw new RuntimeException("No dialect found");
		}

		return dialect.getClass().getName();
	}

	protected ClassLoader getConfigurationClassLoader() {
		return getClass().getClassLoader();
	}

	protected String[] getConfigurationResources() {
		return PropsUtil.getArray(PropsKeys.HIBERNATE_CONFIGS);
	}

	protected Configuration newConfiguration() {
		Configuration configuration = new Configuration();

		try {
			ClassLoader classLoader = getConfigurationClassLoader();

			String[] resources = getConfigurationResources();

			for (String resource : resources) {
				try {
					InputStream is = classLoader.getResourceAsStream(resource);

					if (is != null) {
						configuration = configuration.addInputStream(is);

						is.close();
					}
				}
				catch (Exception e1) {
					if (_log.isWarnEnabled()) {
						_log.warn(e1);
					}
				}
			}

			if (Validator.isNull(PropsValues.HIBERNATE_DIALECT)) {
				String dialect = determineDialect();

				configuration.setProperty("hibernate.dialect", dialect);
			}

			configuration.setProperties(PropsUtil.getProperties());
		}
		catch (Exception e2) {
			_log.error(e2, e2);
		}

		return configuration;
	}

	protected void postProcessConfiguration(Configuration configuration) {

		// Make sure that the Hibernate settings from PropsUtil are set. See the
		// buildSessionFactory implementation in the LocalSessionFactoryBean
		// class to understand how Spring automates a lot of configuration for
		// Hibernate.

		String connectionReleaseMode = PropsUtil.get(
			Environment.RELEASE_CONNECTIONS);

		if (Validator.isNotNull(connectionReleaseMode)) {
			configuration.setProperty(
				Environment.RELEASE_CONNECTIONS, connectionReleaseMode);
		}
	}

	private static Log _log =
		LogFactory.getLog(PortalHibernateConfiguration.class);

}