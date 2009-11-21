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

package com.liferay.portal.dao.pool.c3p0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PropsUtil;

import com.mchange.v2.c3p0.ConnectionCustomizer;

import java.sql.Connection;

/**
 * <a href="PortalConnectionCustomizer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortalConnectionCustomizer implements ConnectionCustomizer {

	public void onAcquire(
			Connection connection, String parentDataSourceIdentityToken)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("JDBC property prefix " + parentDataSourceIdentityToken);
		}

		String transactionIsolation = PropsUtil.get(
			parentDataSourceIdentityToken + "transactionIsolation");

		if (_log.isDebugEnabled()) {
			_log.debug("Custom transaction isolation " + transactionIsolation);
		}

		if (transactionIsolation != null) {
			connection.setTransactionIsolation(
				GetterUtil.getInteger(transactionIsolation));
		}
	}

	public void onCheckIn(
		Connection connection, String parentDataSourceIdentityToken) {
	}

	public void onCheckOut(
		Connection connection, String parentDataSourceIdentityToken) {
	}

	public void onDestroy(
		Connection connection, String parentDataSourceIdentityToken) {
	}

	private static Log _log =
		LogFactoryUtil.getLog(PortalConnectionCustomizer.class);

}