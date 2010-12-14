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

package com.liferay.portal.dao.jdbc;

import com.liferay.portal.kernel.dao.jdbc.DataSourceFactory;
import com.liferay.portal.kernel.jndi.JNDIUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.PwdGenerator;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.util.Enumeration;
import java.util.Properties;

import javax.naming.InitialContext;

import javax.sql.DataSource;

import jodd.bean.BeanUtil;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import uk.org.primrose.pool.datasource.GenericDataSourceFactory;

/**
 * @author Brian Wing Shun Chan
 */
public class DataSourceFactoryImpl implements DataSourceFactory {

	public void destroyDataSource(DataSource dataSource) throws Exception {
		if (dataSource instanceof ComboPooledDataSource) {
			ComboPooledDataSource comboPooledDataSource =
				(ComboPooledDataSource)dataSource;

			comboPooledDataSource.close();
		}
	}

	public DataSource initDataSource(Properties properties) throws Exception {
		Properties defaultProperties = PropsUtil.getProperties(
			"jdbc.default.", true);

		PropertiesUtil.merge(defaultProperties, properties);

		properties = defaultProperties;

		String jndiName = properties.getProperty("jndi.name");

		if (Validator.isNotNull(jndiName)) {
			try {
				return (DataSource)JNDIUtil.lookup(
					new InitialContext(), jndiName);
			}
			catch (Exception e) {
				_log.error("Unable to lookup " + jndiName, e);
			}
		}

		DataSource dataSource = null;

		String liferayPoolProvider =
			PropsValues.JDBC_DEFAULT_LIFERAY_POOL_PROVIDER;

		if (liferayPoolProvider.equalsIgnoreCase("c3p0") ||
			liferayPoolProvider.equalsIgnoreCase("c3po")) {

			if (_log.isDebugEnabled()) {
				_log.debug("Initializing C3P0 data source");
			}

			dataSource = initDataSourceC3PO(properties);
		}
		else if (liferayPoolProvider.equalsIgnoreCase("dbcp")) {
			if (_log.isDebugEnabled()) {
				_log.debug("Initializing DBCP data source");
			}

			dataSource = initDataSourceDBCP(properties);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Initializing Primrose data source");
			}

			dataSource = initDataSourcePrimrose(properties);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Created data source " + dataSource.getClass().getName());

			SortedProperties sortedProperties = new SortedProperties(
				properties);

			sortedProperties.list(System.out);
		}

		return dataSource;
	}

	public DataSource initDataSource(
			String driverClassName, String url, String userName,
			String password)
		throws Exception {

		Properties properties = new Properties();

		properties.setProperty("driverClassName", driverClassName);
		properties.setProperty("url", url);
		properties.setProperty("username", userName);
		properties.setProperty("password", password);

		return initDataSource(properties);
	}

	protected DataSource initDataSourceC3PO(Properties properties)
		throws Exception {

		ComboPooledDataSource comboPooledDataSource =
			new ComboPooledDataSource();

		String identityToken = PwdGenerator.getPassword(PwdGenerator.KEY2, 8);

		comboPooledDataSource.setIdentityToken(identityToken);

		Enumeration<String> enu =
			(Enumeration<String>)properties.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();

			String value = properties.getProperty(key);

			// Map org.apache.commons.dbcp.BasicDataSource to C3PO

			if (key.equalsIgnoreCase("driverClassName")) {
				key = "driverClass";
			}
			else if (key.equalsIgnoreCase("url")) {
				key = "jdbcUrl";
			}
			else if (key.equalsIgnoreCase("username")) {
				key = "user";
			}

			// Ignore Liferay properties

			if (key.equalsIgnoreCase("jndi.name") ||
				key.equalsIgnoreCase("liferay.pool.provider")) {

				continue;
			}

			// Ignore DBCP properties

			if (key.equalsIgnoreCase("defaultTransactionIsolation") ||
				key.equalsIgnoreCase("maxActive") ||
				key.equalsIgnoreCase("minIdle") ||
				key.equalsIgnoreCase("removeAbandonedTimeout")) {

				continue;
			}

			// Ignore Primrose properties

			if (key.equalsIgnoreCase("base") ||
				key.equalsIgnoreCase("connectionTransactionIsolation") ||
				key.equalsIgnoreCase("idleTime") ||
				key.equalsIgnoreCase("numberOfConnectionsToInitializeWith")) {

				continue;
			}

			try {
				BeanUtil.setProperty(comboPooledDataSource, key, value);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Property " + key + " is not a valid C3PO property");
				}
			}
		}

		return comboPooledDataSource;
	}

	protected DataSource initDataSourceDBCP(Properties properties)
		throws Exception {

		return BasicDataSourceFactory.createDataSource(properties);
	}

	protected DataSource initDataSourcePrimrose(Properties properties)
		throws Exception {

		String poolName = PwdGenerator.getPassword(PwdGenerator.KEY2, 8);

		properties.setProperty("poolName", poolName);

		Enumeration<String> enu =
			(Enumeration<String>)properties.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();

			String value = properties.getProperty(key);

			// Map org.apache.commons.dbcp.BasicDataSource to Primrose

			if (key.equalsIgnoreCase("driverClassName")) {
				key = "driverClass";
			}
			else if (key.equalsIgnoreCase("url")) {
				key = "driverURL";
			}
			else if (key.equalsIgnoreCase("username")) {
				key = "user";
			}

			properties.setProperty(key, value);
		}

		GenericDataSourceFactory genericDataSourceFactory =
			new GenericDataSourceFactory();

		return genericDataSourceFactory.loadPool(poolName, properties);
	}

	private static Log _log = LogFactoryUtil.getLog(
		DataSourceFactoryImpl.class);

}