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

package com.liferay.portal.dao.shard;

import com.liferay.portal.util.PropsValues;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.aop.TargetSource;

/**
 * @author Michael Young
 */
public class ShardDataSourceTargetSource implements TargetSource {

	public DataSource getDataSource() {
		return _dataSource.get();
	}

	public Object getTarget() throws Exception {
		return getDataSource();
	}

	public Class<DataSource> getTargetClass() {
		return DataSource.class;
	}

	public boolean isStatic() {
		return false;
	}

	public void releaseTarget(Object target) throws Exception {
	}

	public void setDataSource(String shardName) {
		_dataSource.set(_dataSources.get(shardName));
	}

	public void setDataSources(Map<String, DataSource> dataSources) {
		_dataSources = dataSources;
	}

	private static ThreadLocal<DataSource> _dataSource =
		new ThreadLocal<DataSource>() {

		protected DataSource initialValue() {
			return _dataSources.get(PropsValues.SHARD_DEFAULT_NAME);
		}

	};

	private static Map<String, DataSource> _dataSources;

}