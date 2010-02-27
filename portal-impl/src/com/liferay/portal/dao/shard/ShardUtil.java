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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PropsValues;

import javax.sql.DataSource;

/**
 * <a href="ShardUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class ShardUtil {

	public static String COMPANY_SCOPE = "COMPANY_SCOPE";

	public static DataSource getDataSource() {
		return _shardAdvice.getDataSource();
	}

	public static ShardSelector getShardSelector() {
		return _shardSelector;
	}

	public static boolean isEnabled() {
		if (_shardAdvice != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public static String popCompanyService() {
		String value = null;

		if (_shardAdvice != null) {
			value = _shardAdvice.popCompanyService();
		}

		return value;
	}

	public static void pushCompanyService(long companyId) {
		if (_shardAdvice != null) {
			_shardAdvice.pushCompanyService(companyId);
		}
	}

	public static void pushCompanyService(String shardName) {
		if (_shardAdvice != null) {
			_shardAdvice.pushCompanyService(shardName);
		}
	}

	public void setShardAdvice(ShardAdvice shardAdvice) {
		_shardAdvice = shardAdvice;
	}

	private static Log _log = LogFactoryUtil.getLog(ShardUtil.class);

	private static ShardAdvice _shardAdvice;
	private static ShardSelector _shardSelector;

	static {
		try {
			_shardSelector = (ShardSelector)Class.forName(
				PropsValues.SHARD_SELECTOR).newInstance();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

}