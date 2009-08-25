/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.spring.jpa;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import javax.sql.DataSource;

import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;

/**
 * <a href="LocalContainerEntityManagerFactoryBean.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 */
public class LocalContainerEntityManagerFactoryBean extends
	 org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean {

	public LocalContainerEntityManagerFactoryBean() {
		try {
			String weaverClassName = PropsValues.JPA_LOADTIME_WEAVER;

			if (Validator.isNotNull(weaverClassName)) {

				Class weaverClass = Class.forName(weaverClassName);

				LoadTimeWeaver weaver =
					(LoadTimeWeaver)weaverClass.newInstance();

				setLoadTimeWeaver(weaver);
			}
		}
		catch(Exception ex) {
			_log.error(ex);

			throw new RuntimeException(ex);
		}
	 }

	@Override
	public void setDataSource(DataSource dataSource) {
		Database dbType = DatabaseDetector.determineDatabase(dataSource);

		String provider = PropsValues.JPA_PROVIDER;

		AbstractJpaVendorAdapter adapter = null;

		try {
			Class providerClass = _getProviderClass(provider);

			if (_log.isInfoEnabled()) {
				_log.info("Using provider class " + providerClass.getName());
			}

			adapter = (AbstractJpaVendorAdapter)providerClass.newInstance();
		}
		catch(Exception ex) {
			_log.error(ex);

			return;
		}

		String dbName = PropsValues.JPA_PROVIDER_DATABASE;

		if ("EclipseLink".equalsIgnoreCase(provider) ||
			"TopLink".equalsIgnoreCase(provider)) {

			if (dbName == null) {
				dbName = _getDatabasePlatform(provider, dbType);
			}

			if (_log.isInfoEnabled()) {
				_log.info("Using database platform " + dbName);
			}

			adapter.setDatabasePlatform(dbName);
		}
		else {
			if (dbName == null) {
				adapter.setDatabase(dbType);

				if (_log.isInfoEnabled()) {
					_log.info("Using database name " + dbType.toString());
				}
			}
			else {
				adapter.setDatabase(Database.valueOf(dbName));

				if (_log.isInfoEnabled()) {
					_log.info("Using database name " + dbName);
				}

			}
		}

		setJpaVendorAdapter(adapter);

		super.setDataSource(dataSource);
	}

	private String _getDatabasePlatform(String provider, Database dbType) {
		String dbPlatform = null;

		String pkg = null;

		boolean eclipselink = false;

		if ("EclipseLink".equalsIgnoreCase(provider)) {
			pkg = "org.eclipse.persistence.platform.database.";

			eclipselink = true;
		}
		else {
			pkg = "oracle.toplink.essentials.platform.database.";
		}

		if (dbType.equals(Database.DB2)) {
			dbPlatform = pkg + "DB2Platform";
		}
		else if (dbType.equals(Database.DERBY)) {
			dbPlatform = pkg + "DerbyPlatform";
		}
		else if (dbType.equals(Database.HSQL)) {
			dbPlatform = pkg + "HSQLPlatform";
		}
		else if (dbType.equals(Database.INFORMIX)) {
			dbPlatform = pkg + "InformixPlatform";
		}
		else if (dbType.equals(Database.MYSQL)) {
			if (eclipselink) {
				dbPlatform = pkg + "MySQLPlatform";
			}
			else {
				dbPlatform = pkg + "MySQL4Platform";
			}
		}
		else if (dbType.equals(Database.ORACLE)) {
			if (eclipselink) {
				dbPlatform = pkg + "OraclePlatform";
			}
			else {
				dbPlatform = pkg + "oracle.OraclePlatform";
			}
		}
		else if (dbType.equals(Database.POSTGRESQL)) {
			dbPlatform = pkg + "PostgreSQLPlatform";
		}
		else if (dbType.equals(Database.SQL_SERVER)) {
			dbPlatform = pkg + "SQLServerPlatform";
		}
		else if (dbType.equals(Database.SYBASE)) {
			dbPlatform = pkg + "SybasePlatform";
		}
		else {
			_log.error(
				"Unable to detect database platform for  \"" +
				dbType.toString() +
				"\". Override using \"jpa.provider.database\" property.");
		}

		return dbPlatform;
	}

	private Class _getProviderClass(String provider) throws Exception {
		Class clazz = null;

		if ("EclipseLink".equalsIgnoreCase(provider)) {
			clazz = Class.forName(
				"org.springframework.orm.jpa.vendor." +
				"EclipseLinkJpaVendorAdapter");
		}
		else if ("TopLink".equalsIgnoreCase(provider)) {
			clazz = Class.forName(
				"org.springframework.orm.jpa.vendor.TopLinkJpaVendorAdapter");
		}
		else if ("Hibernate".equalsIgnoreCase(provider)) {
			clazz = Class.forName(
				"org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter");
		}
		else if ("OpenJPA".equalsIgnoreCase(provider)) {
			clazz = Class.forName(
				"org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter");
		}

		return clazz;
	}

	private static Log _log = LogFactoryUtil.getLog(
		LocalContainerEntityManagerFactoryBean.class);

}