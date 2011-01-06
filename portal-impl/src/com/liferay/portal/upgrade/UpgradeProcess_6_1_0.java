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
import com.liferay.portal.upgrade.v6_1_0.UpgradeAdminPortlets;
import com.liferay.portal.upgrade.v6_1_0.UpgradeDocumentLibrary;
import com.liferay.portal.upgrade.v6_1_0.UpgradeMBThread;
import com.liferay.portal.upgrade.v6_1_0.UpgradeNavigation;
import com.liferay.portal.upgrade.v6_1_0.UpgradePortletPreferences;
import com.liferay.portal.upgrade.v6_1_0.UpgradeSchema;
import com.liferay.portal.upgrade.v6_1_0.UpgradeSubscription;
import com.liferay.portal.upgrade.v6_1_0.UpgradeVirtualHost;

/**
 * @author Jorge Ferrer
 * @author Juan Fernández
 */
public class UpgradeProcess_6_1_0 extends UpgradeProcess {

	public int getThreshold() {
		return ReleaseInfo.RELEASE_6_1_0_BUILD_NUMBER;
	}

	protected void doUpgrade() throws Exception {
		upgrade(UpgradeSchema.class);
		upgrade(UpgradeAdminPortlets.class);
		upgrade(UpgradeDocumentLibrary.class);
		upgrade(UpgradeMBThread.class);
		upgrade(UpgradeNavigation.class);
		upgrade(UpgradePortletPreferences.class);
		upgrade(UpgradeSubscription.class);
		upgrade(UpgradeVirtualHost.class);
	}

}