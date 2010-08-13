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

import com.liferay.portal.dao.jdbc.spring.MappingSqlQueryImpl;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.shard.ShardUtil;

import java.util.List;

import javax.sql.DataSource;

/**
 * @author Alexander Chow
 */
public class ShardMappingSqlQueryImpl<T> extends MappingSqlQueryImpl<T> {

	public ShardMappingSqlQueryImpl(
		DataSource dataSource, String sql, int[] types,
		RowMapper<T> rowMapper) {

		super(dataSource, sql, types, rowMapper);
	}

	public List<T> execute(Object... params) {
		setDataSource(ShardUtil.getDataSource());

		return super.execute(params);
	}

}