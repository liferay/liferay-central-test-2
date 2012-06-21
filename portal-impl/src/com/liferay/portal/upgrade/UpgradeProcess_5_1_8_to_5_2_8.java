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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.v5_1_7_to_5_2_7.UpgradeDuplicates;
import com.liferay.portal.upgrade.v5_1_7_to_5_2_7.UpgradeSocial;
import com.liferay.portal.upgrade.v5_1_8_to_5_2_8.UpgradeSchema;
import com.liferay.portal.upgrade.v5_2_0.UpgradeExpando;
import com.liferay.portal.upgrade.v5_2_0.UpgradeJournal;
import com.liferay.portal.upgrade.v5_2_0.UpgradeOrganization;
import com.liferay.portal.upgrade.v5_2_0.UpgradePortletId;
import com.liferay.portal.upgrade.v5_2_0.UpgradePortletPermissions;
import com.liferay.portal.upgrade.v5_2_0.UpgradeTags;
import com.liferay.portal.upgrade.v5_2_2.UpgradeWebForm;
import com.liferay.portal.upgrade.v5_2_6.UpgradeGroup;
import com.liferay.portal.upgrade.v5_2_8.UpgradeDocumentLibrary;

/**
 * @author Douglas Wong
 */
public class UpgradeProcess_5_1_8_to_5_2_8 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_5_2_8_BUILD_NUMBER;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(UpgradeSchema.class);

		// 5.1.2 to 5.2.0

		upgrade(UpgradeExpando.class);
		upgrade(UpgradeJournal.class);
		upgrade(UpgradeOrganization.class);
		upgrade(UpgradePortletId.class);
		upgrade(UpgradePortletPermissions.class);
		upgrade(UpgradeTags.class);

		// 5.2.1 to 5.2.2

		upgrade(UpgradeWebForm.class);

		// 5.2.2 to 5.2.3

		upgrade(UpgradeDuplicates.class);

		// 5.2.4 to 5.2.5

		upgrade(UpgradeSocial.class);

		// 5.2.5 to 5.2.6

		upgrade(UpgradeGroup.class);

		// 5.2.7 to 5.2.8

		upgrade(UpgradeDocumentLibrary.class);
	}

}