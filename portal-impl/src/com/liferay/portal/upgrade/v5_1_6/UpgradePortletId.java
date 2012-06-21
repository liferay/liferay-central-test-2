/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v5_1_6;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradePortletId
	extends com.liferay.portal.upgrade.v5_2_0.UpgradePortletId {

	protected Log getLog() {
		return _log;
	}

	@Override
	protected String[][] getPortletIdsArray() {
		return new String[][] {
			new String[] {
				"7", "1_WAR_biblegatewayportlet"
			},
			new String[] {
				"21", "1_WAR_randombibleverseportlet"
			},
			new String[] {
				"46", "1_WAR_gospelforasiaportlet"
			}
		};
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradePortletId.class);

}