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

package com.liferay.portal.tools.shard.builder.internal.algorithm;

import com.liferay.portal.tools.shard.builder.exporter.ShardExporter;
import com.liferay.portal.tools.shard.builder.exporter.ShardExporterFactory;
import com.liferay.portal.tools.shard.builder.internal.DBProvider;
import com.liferay.portal.tools.shard.builder.test.util.DBProviderTestUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Properties;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public abstract class BaseDBProviderTestCase {

	public int executeUpdate(DataSource dataSource, String sql)
		throws SQLException {

		try (Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement =
				connection.prepareStatement(sql)) {

			return preparedStatement.executeUpdate();
		}
	}

	@Before
	public void setUp() throws Exception {
		properties = DBProviderTestUtil.readProperties(
			getTestPropertiesFileName());

		ShardExporter shardExporter = ShardExporterFactory.getShardExporter(
			properties);

		dbProvider = (DBProvider)shardExporter;

		executeUpdate(dbProvider.getDataSource(), getCreateTableSQL());
	}

	@After
	public void tearDown() throws Exception {
		executeUpdate(dbProvider.getDataSource(), getDropTableSQL());
	}

	@Test
	public void testSerializeTableFieldDate() throws Exception {
		String serializeTableField = dbProvider.serializeTableField(
			new Date(0L));

		Assert.assertEquals("'1970-01-01 00:01:00'", serializeTableField);
	}

	@Test
	public void testSerializeTableFieldString() throws Exception {
		String serializeTableField = dbProvider.serializeTableField(
			new String("1"));

		Assert.assertEquals("'1'", serializeTableField);
	}

	@Test
	public void testSerializeTableFieldStringShouldEscapeQuotes()
		throws Exception {

		String serializeTableField = dbProvider.serializeTableField(
			new String("'1'"));

		Assert.assertEquals("'\\'1\\''", serializeTableField);
	}

	@Test
	public void testSerializeTableFieldTimestamp() throws Exception {
		String serializeTableField = dbProvider.serializeTableField(
			new Timestamp(0L));

		Assert.assertEquals("'1970-01-01 00:01:00'", serializeTableField);
	}

	protected String getCreateTableSQL() {
		return "create table foo (i INT, f FLOAT, s VARCHAR(75), d DATETIME)";
	}

	protected Object[] getDefaultArguments(
			int expectedInteger, Timestamp expectedTimestamp)
		throws Exception {

		float expectedFloat = 99.99f;
		String expectedString = "expectedString";

		return new Object[] {
			expectedInteger, expectedFloat, expectedString, expectedTimestamp
		};
	}

	protected String getDropTableSQL() {
		return "drop table foo";
	}

	protected abstract String getTestPropertiesFileName();

	protected static DBProvider dbProvider;
	protected static Properties properties;

}