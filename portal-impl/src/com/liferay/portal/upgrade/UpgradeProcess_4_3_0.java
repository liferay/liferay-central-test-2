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
import com.liferay.portal.upgrade.v4_3_0.UpgradeAddress;
import com.liferay.portal.upgrade.v4_3_0.UpgradeBlogs;
import com.liferay.portal.upgrade.v4_3_0.UpgradeBookmarks;
import com.liferay.portal.upgrade.v4_3_0.UpgradeCalendar;
import com.liferay.portal.upgrade.v4_3_0.UpgradeClassName;
import com.liferay.portal.upgrade.v4_3_0.UpgradeCompany;
import com.liferay.portal.upgrade.v4_3_0.UpgradeContact;
import com.liferay.portal.upgrade.v4_3_0.UpgradeCounter;
import com.liferay.portal.upgrade.v4_3_0.UpgradeCountry;
import com.liferay.portal.upgrade.v4_3_0.UpgradeDocumentLibrary;
import com.liferay.portal.upgrade.v4_3_0.UpgradeEmailAddress;
import com.liferay.portal.upgrade.v4_3_0.UpgradeGroup;
import com.liferay.portal.upgrade.v4_3_0.UpgradeImage;
import com.liferay.portal.upgrade.v4_3_0.UpgradeImageGallery;
import com.liferay.portal.upgrade.v4_3_0.UpgradeJournal;
import com.liferay.portal.upgrade.v4_3_0.UpgradeListType;
import com.liferay.portal.upgrade.v4_3_0.UpgradeMappingTables;
import com.liferay.portal.upgrade.v4_3_0.UpgradeMessageBoards;
import com.liferay.portal.upgrade.v4_3_0.UpgradeOrganization;
import com.liferay.portal.upgrade.v4_3_0.UpgradePermission;
import com.liferay.portal.upgrade.v4_3_0.UpgradePhone;
import com.liferay.portal.upgrade.v4_3_0.UpgradePolls;
import com.liferay.portal.upgrade.v4_3_0.UpgradePortlet;
import com.liferay.portal.upgrade.v4_3_0.UpgradePortletPreferences;
import com.liferay.portal.upgrade.v4_3_0.UpgradeRatings;
import com.liferay.portal.upgrade.v4_3_0.UpgradeRegion;
import com.liferay.portal.upgrade.v4_3_0.UpgradeRelease;
import com.liferay.portal.upgrade.v4_3_0.UpgradeResource;
import com.liferay.portal.upgrade.v4_3_0.UpgradeRole;
import com.liferay.portal.upgrade.v4_3_0.UpgradeSchema;
import com.liferay.portal.upgrade.v4_3_0.UpgradeShopping;
import com.liferay.portal.upgrade.v4_3_0.UpgradeSubscription;
import com.liferay.portal.upgrade.v4_3_0.UpgradeUser;
import com.liferay.portal.upgrade.v4_3_0.UpgradeUserGroup;
import com.liferay.portal.upgrade.v4_3_0.UpgradeUserIdMapper;
import com.liferay.portal.upgrade.v4_3_0.UpgradeWebsite;
import com.liferay.portal.upgrade.v4_3_0.UpgradeWiki;

/**
 * @author     Alexander Chow
 * @author     Brian Wing Shun Chan
 * @deprecated
 */
public class UpgradeProcess_4_3_0 extends UpgradeProcess {

	public int getThreshold() {
		return ReleaseInfo.RELEASE_4_3_0_BUILD_NUMBER;
	}

	protected void doUpgrade() throws Exception {
		upgrade(UpgradeSchema.class);
		upgrade(UpgradeClassName.class);
		upgrade(UpgradeImage.class);
		upgrade(UpgradeCompany.class);
		upgrade(UpgradeUser.class);
		upgrade(UpgradeContact.class);
		upgrade(UpgradeUserGroup.class);
		upgrade(UpgradeOrganization.class);
		upgrade(UpgradeGroup.class);
		upgrade(UpgradeRole.class);
		upgrade(UpgradeAddress.class);
		upgrade(UpgradeBlogs.class);
		upgrade(UpgradeBookmarks.class);
		upgrade(UpgradeCalendar.class);
		upgrade(UpgradeCountry.class);
		upgrade(UpgradeDocumentLibrary.class);
		upgrade(UpgradeEmailAddress.class);
		upgrade(UpgradeImageGallery.class);
		upgrade(UpgradeJournal.class);
		upgrade(UpgradeListType.class);
		upgrade(UpgradeMessageBoards.class);
		upgrade(UpgradePermission.class);
		upgrade(UpgradePhone.class);
		upgrade(UpgradePolls.class);
		upgrade(UpgradePortlet.class);
		upgrade(UpgradeRegion.class);
		upgrade(UpgradeRelease.class);
		upgrade(UpgradeShopping.class);
		upgrade(UpgradeSubscription.class);
		upgrade(UpgradeUserIdMapper.class);
		upgrade(UpgradeWebsite.class);
		upgrade(UpgradeWiki.class);
		upgrade(UpgradePortletPreferences.class);
		upgrade(UpgradeRatings.class);
		upgrade(UpgradeCounter.class);
		upgrade(UpgradeResource.class);
		upgrade(UpgradeMappingTables.class);
	}

}