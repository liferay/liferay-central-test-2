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

package com.liferay.portal.service.impl;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.service.base.PortalServiceBaseImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

/**
 * <a href="PortalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortalServiceImpl extends PortalServiceBaseImpl {

	public String getAutoDeployDirectory() throws SystemException {
		return PrefsPropsUtil.getString(
			PropsKeys.AUTO_DEPLOY_DEPLOY_DIR,
			PropsValues.AUTO_DEPLOY_DEPLOY_DIR);
	}

	public int getBuildNumber() {
		return ReleaseInfo.getBuildNumber();
	}

	public void test() {
		long userId = 0;

		try {
			userId = getUserId();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		if (_log.isInfoEnabled()) {
			_log.info("User id " + userId);
		}
	}

	public void testCounterRollback() throws SystemException {
		int counterIncrement = PropsValues.COUNTER_INCREMENT;

		for (int i = 0; i < counterIncrement * 2; i++) {
			counterLocalService.increment();
		}

		throw new SystemException();
	}

	private static Log _log = LogFactoryUtil.getLog(PortalServiceImpl.class);

}