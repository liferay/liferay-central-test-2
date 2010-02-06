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

package com.liferay.portal.dao.shard;

import com.liferay.portal.util.PropsValues;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.aop.TargetSource;

/**
 * <a href="ShardDataSourceTargetSource.java.html"><b><i>View Source</i></b></a>
 *
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