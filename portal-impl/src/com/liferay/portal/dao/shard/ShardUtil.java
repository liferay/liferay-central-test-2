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

package com.liferay.portal.dao.shard;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PropsValues;

/**
 * <a href="ShardUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class ShardUtil {

	public static ShardSelector getShardSelector() {
		return _shardSelector;
	}

	public static String popCompanyService() {
		String retVal = null;

		if (_getShardedAdvice() != null) {
			retVal = _getShardedAdvice().popCompanyService();
		}

		return retVal;
	}

	public static void pushCompanyService(String shardId) {
		if (_getShardedAdvice() != null) {
			_getShardedAdvice().pushCompanyService(shardId);
		}
	}

	public static void pushCompanyService(long companyId) {
		if (_getShardedAdvice() != null) {
			_getShardedAdvice().pushCompanyService(companyId);
		}
	}

	private static ShardedAdvice _getShardedAdvice() {
		return _shardedAdvice;
	}

	public void setShardedAdvice(ShardedAdvice shardedAdvice) {
		_shardedAdvice = shardedAdvice;
	}

	private static Log _log = LogFactoryUtil.getLog(ShardUtil.class);

	private static ShardedAdvice _shardedAdvice;

	private static ShardSelector _shardSelector;

	static {
		try {
			_shardSelector = (ShardSelector)
				Class.forName(PropsValues.SHARD_SELECTOR).newInstance();
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

}