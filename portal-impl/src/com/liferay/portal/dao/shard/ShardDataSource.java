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

package com.liferay.portal.dao.shard;

import com.liferay.portal.kernel.dao.shard.ShardUtil;

import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * @author Alexander Chow
 */
public class ShardDataSource implements DataSource {

	public static DataSource getInstance() {
		return _instance;
	}

	public PrintWriter getLogWriter() throws SQLException {
		return getDataSource().getLogWriter();
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		getDataSource().setLogWriter(out);
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		getDataSource().setLoginTimeout(seconds);
	}

	public int getLoginTimeout() throws SQLException {
		return getDataSource().getLoginTimeout();
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return getDataSource().unwrap(iface);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return getDataSource().isWrapperFor(iface);
	}

	public Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}

	public Connection getConnection(String username, String password)
		throws SQLException {

		return getDataSource().getConnection(username, password);
	}

	protected DataSource getDataSource() {
		return ShardUtil.getDataSource();
	}

	private static ShardDataSource _instance = new ShardDataSource();

}