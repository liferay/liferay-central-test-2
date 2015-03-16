/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
import com.liferay.portal.upgrade.v7_0_0.UpgradeAddress;
import com.liferay.portal.upgrade.v7_0_0.UpgradeAdminPortlets;
import com.liferay.portal.upgrade.v7_0_0.UpgradeAsset;
import com.liferay.portal.upgrade.v7_0_0.UpgradeBackgroundTask;
import com.liferay.portal.upgrade.v7_0_0.UpgradeCalEvent;
import com.liferay.portal.upgrade.v7_0_0.UpgradeContact;
import com.liferay.portal.upgrade.v7_0_0.UpgradeDLPreferences;
import com.liferay.portal.upgrade.v7_0_0.UpgradeDocumentLibrary;
import com.liferay.portal.upgrade.v7_0_0.UpgradeDynamicDataLists;
import com.liferay.portal.upgrade.v7_0_0.UpgradeDynamicDataMapping;
import com.liferay.portal.upgrade.v7_0_0.UpgradeEmailAddress;
import com.liferay.portal.upgrade.v7_0_0.UpgradeEmailNotificationPreferences;
import com.liferay.portal.upgrade.v7_0_0.UpgradeExpando;
import com.liferay.portal.upgrade.v7_0_0.UpgradeGroup;
import com.liferay.portal.upgrade.v7_0_0.UpgradeJournal;
import com.liferay.portal.upgrade.v7_0_0.UpgradeJournalArticleType;
import com.liferay.portal.upgrade.v7_0_0.UpgradeJournalDisplayPreferences;
import com.liferay.portal.upgrade.v7_0_0.UpgradeListType;
import com.liferay.portal.upgrade.v7_0_0.UpgradeLock;
import com.liferay.portal.upgrade.v7_0_0.UpgradeMembershipRequest;
import com.liferay.portal.upgrade.v7_0_0.UpgradeMessageBoards;
import com.liferay.portal.upgrade.v7_0_0.UpgradeOrgLabor;
import com.liferay.portal.upgrade.v7_0_0.UpgradeOrganization;
import com.liferay.portal.upgrade.v7_0_0.UpgradePhone;
import com.liferay.portal.upgrade.v7_0_0.UpgradePortletDisplayTemplatePreferences;
import com.liferay.portal.upgrade.v7_0_0.UpgradePortletSettings;
import com.liferay.portal.upgrade.v7_0_0.UpgradeRatings;
import com.liferay.portal.upgrade.v7_0_0.UpgradeRepositoryEntry;
import com.liferay.portal.upgrade.v7_0_0.UpgradeSchema;
import com.liferay.portal.upgrade.v7_0_0.UpgradeShopping;
import com.liferay.portal.upgrade.v7_0_0.UpgradeShoppingPreferences;
import com.liferay.portal.upgrade.v7_0_0.UpgradeSubscription;
import com.liferay.portal.upgrade.v7_0_0.UpgradeWebsite;

/**
 * @author Julio Camarero
 */
public class UpgradeProcess_7_0_0 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_7_0_0_BUILD_NUMBER;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(UpgradeSchema.class);
		upgrade(UpgradeAddress.class);
		upgrade(UpgradeAdminPortlets.class);
		upgrade(UpgradeAsset.class);
		upgrade(UpgradeBackgroundTask.class);
		upgrade(UpgradeCalEvent.class);
		upgrade(UpgradeContact.class);
		upgrade(UpgradeDLPreferences.class);
		upgrade(UpgradeDocumentLibrary.class);
		upgrade(UpgradeDynamicDataLists.class);
		upgrade(UpgradeDynamicDataMapping.class);
		upgrade(UpgradeEmailAddress.class);
		upgrade(UpgradeEmailNotificationPreferences.class);
		upgrade(UpgradeExpando.class);
		upgrade(UpgradeGroup.class);
		upgrade(UpgradeJournal.class);
		upgrade(UpgradeJournalDisplayPreferences.class);
		upgrade(UpgradeJournalArticleType.class);
		upgrade(UpgradeListType.class);
		upgrade(UpgradeLock.class);
		upgrade(UpgradeMembershipRequest.class);
		upgrade(UpgradeMessageBoards.class);
		upgrade(UpgradeOrganization.class);
		upgrade(UpgradeOrgLabor.class);
		upgrade(UpgradePhone.class);
		upgrade(UpgradePortletDisplayTemplatePreferences.class);
		upgrade(UpgradeRatings.class);
		upgrade(UpgradeRepositoryEntry.class);
		upgrade(UpgradeShopping.class);
		upgrade(UpgradeShoppingPreferences.class);
		upgrade(UpgradeSubscription.class);
		upgrade(UpgradeWebsite.class);

		// This must be the last upgrade process. Otherwise, upgrades based on
		// BaseUpgradePortletPreferences will fail because the portlet
		// preferences will be in the new settings format.

		upgrade(UpgradePortletSettings.class);
	}

}