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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.v4_3_1.UpgradeOrganization;
import com.liferay.portal.upgrade.v4_3_1.UpgradeSchema;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeProcess_4_3_1 extends UpgradeProcess {

	public int getThreshold() {
		return ReleaseInfo.RELEASE_4_3_1_BUILD_NUMBER;
	}

	protected void doUpgrade() throws Exception {
		upgrade(UpgradeSchema.class);
		upgrade(UpgradeOrganization.class);
	}

}