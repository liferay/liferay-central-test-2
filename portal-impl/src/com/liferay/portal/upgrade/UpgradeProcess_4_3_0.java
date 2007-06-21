/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade;

import com.liferay.portal.upgrade.v4_3_0.UpgradeAddress;
import com.liferay.portal.upgrade.v4_3_0.UpgradeBlogs;
import com.liferay.portal.upgrade.v4_3_0.UpgradeBookmarks;
import com.liferay.portal.upgrade.v4_3_0.UpgradeCalendar;
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
import com.liferay.portal.upgrade.v4_3_0.UpgradeLucene;
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
import com.liferay.portal.upgrade.v4_3_0.UpgradeShopping;
import com.liferay.portal.upgrade.v4_3_0.UpgradeSubscription;
import com.liferay.portal.upgrade.v4_3_0.UpgradeUser;
import com.liferay.portal.upgrade.v4_3_0.UpgradeUserGroup;
import com.liferay.portal.upgrade.v4_3_0.UpgradeUserIdMapper;
import com.liferay.portal.upgrade.v4_3_0.UpgradeWebsite;
import com.liferay.portal.upgrade.v4_3_0.UpgradeWiki;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeProcess_4_3_0.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeProcess_4_3_0 extends UpgradeProcess {

	public int getThreshold() {

		// Version 4.2.2 has build number 3502

		return 3502;
	}

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		upgrade(new UpgradeLucene());
		upgrade(new UpgradeImage());
		upgrade(new UpgradeCompany());
		upgrade(new UpgradeUser());
		upgrade(new UpgradeContact());
		upgrade(new UpgradeUserGroup());
		upgrade(new UpgradeOrganization());
		upgrade(new UpgradeGroup());
		upgrade(new UpgradeRole());
		upgrade(new UpgradeAddress());
		upgrade(new UpgradeBlogs());
		upgrade(new UpgradeBookmarks());
		upgrade(new UpgradeCalendar());
		upgrade(new UpgradeCountry());
		upgrade(new UpgradeDocumentLibrary());
		upgrade(new UpgradeEmailAddress());
		upgrade(new UpgradeImageGallery());
		upgrade(new UpgradeJournal());
		upgrade(new UpgradeListType());
		upgrade(new UpgradeMessageBoards());
		upgrade(new UpgradePermission());
		upgrade(new UpgradePhone());
		upgrade(new UpgradePolls());
		upgrade(new UpgradePortlet());
		upgrade(new UpgradeRegion());
		upgrade(new UpgradeRelease());
		upgrade(new UpgradeShopping());
		upgrade(new UpgradeSubscription());
		upgrade(new UpgradeUserIdMapper());
		upgrade(new UpgradeWebsite());
		upgrade(new UpgradeWiki());
		upgrade(new UpgradePortletPreferences());
		upgrade(new UpgradeRatings());
		upgrade(new UpgradeCounter());
		upgrade(new UpgradeResource());
		upgrade(new UpgradeMappingTables());
	}

	private static Log _log = LogFactory.getLog(UpgradeProcess_4_3_0.class);

}