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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.service.base.PortalServiceBaseImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

/**
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