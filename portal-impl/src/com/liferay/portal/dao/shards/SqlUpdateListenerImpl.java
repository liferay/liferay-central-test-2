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

package com.liferay.portal.dao.shards;

import com.liferay.portal.dao.jdbc.spring.SqlUpdateListener;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.sql.DataSource;

/**
 * <a href="SqlUpdateListenerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class SqlUpdateListenerImpl implements SqlUpdateListener {

	public void onAfterUpdate(SqlUpdate sqlUpdate) {
		if (_log.isDebugEnabled()) {
			_log.debug("onAfterUpdate()");
		}
	}

	public void onBeforeUpdate(SqlUpdate sqlUpdate) {
		if (_log.isDebugEnabled()) {
			_log.debug("onBeforeUpdate()");
		}

		// Ensure the sharded datasource is chosen before the update is called.
		// Since SqlUpdate.update() is always a nested call in a
		// *PersistenceImpl, the shard ID has already been properly set prior to
		// this call and does not need to be manipulated by this listener.

		DataSource dataSource = _shardedAdvice.getDataSource();

		sqlUpdate.setDataSource(dataSource);
	}

	public void setShardedAdvice(ShardedAdvice shardedAdvice) {
		_shardedAdvice = shardedAdvice;
	}

	private ShardedAdvice _shardedAdvice;

	private static Log _log =
		LogFactoryUtil.getLog(SqlUpdateListenerImpl.class);

}