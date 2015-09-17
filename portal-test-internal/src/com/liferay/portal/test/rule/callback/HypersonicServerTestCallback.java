/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.test.rule.callback;

import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.test.rule.callback.BaseTestCallback;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.hsqldb.jdbcDriver;
import org.hsqldb.server.Server;
import org.hsqldb.server.ServerConstants;

import org.junit.runner.Description;

/**
 * @author William Newbury
 * @author Shuyang Zhou
 */
public class HypersonicServerTestCallback
	extends BaseTestCallback<Server, Object> {

	public HypersonicServerTestCallback(String databaseName) {
		_databaseName = databaseName;
	}

	@Override
	public void doAfterClass(Description description, Server server)
		throws SQLException {

		try (Connection connection = DriverManager.getConnection(
				"jdbc:hsqldb:hsql://localhost/".concat(databaseName), "sa", "");
			Statement statement = connection.createStatement()) {

			statement.execute("SHUTDOWN COMPACT");
		}

		server.stop();
	}

	@Override
	public Server doBeforeClass(Description description) throws Exception {
		Class.forName(jdbcDriver.class.getName());

		final CountDownLatch startCountDownLatch = new CountDownLatch(1);

		Server server = new Server() {

			@Override
			public int stop() {
				try (PrintWriter logPrintWriter = getLogWriter();
					PrintWriter errPrintWriter = getErrWriter()) {

					int state = super.stop();

					if (!_shutdownCountDownLatch.await(1, TimeUnit.MINUTES)) {
						throw new IllegalStateException(
							"Unable to shutdown HSQLDB " + _databaseName);
					}

					return state;
				}
				catch (InterruptedException ie) {
					return ReflectionUtil.throwException(ie);
				}
			}

			@Override
			protected synchronized void setState(int state) {
				super.setState(state);

				if (state == ServerConstants.SERVER_STATE_ONLINE) {
					startCountDownLatch.countDown();
				}
				else if (state == ServerConstants.SERVER_STATE_SHUTDOWN) {
					_shutdownCountDownLatch.countDown();
				}
			}

			private final CountDownLatch _shutdownCountDownLatch =
				new CountDownLatch(1);

		};

		File hsqlHome = new File(_HSQL_HOME);

		hsqlHome.mkdirs();

		server.setErrWriter(
			new UnsyncPrintWriter(
				new File(hsqlHome, _databaseName.concat(".err.log"))));
		server.setLogWriter(
			new UnsyncPrintWriter(
				new File(hsqlHome, _databaseName.concat(".std.log"))));

		server.setDatabaseName(0, _databaseName);
		server.setDatabasePath(0, _HSQL_HOME.concat(_databaseName));

		server.start();

		if (!startCountDownLatch.await(1, TimeUnit.MINUTES)) {
			throw new IllegalStateException(
				"Unable to start up HSQLDB " + _databaseName);
		}

		return server;
	}

	private static final String _HSQL_HOME = PropsValues.LIFERAY_HOME.concat(
		"/data/hsql/");

	private final String _databaseName;

}