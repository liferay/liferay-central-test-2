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

package com.liferay.portal.tools;

import com.liferay.portal.PortalException;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Release;
import com.liferay.portal.model.ReleaseConstants;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.ReleaseLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;
import com.liferay.portal.util.InitUtil;

import org.apache.commons.lang.time.StopWatch;

/**
 * <a href="DBUpgrader.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class DBUpgrader {

	public static void main(String[] args) {
		try {
			StopWatch stopWatch = new StopWatch();

			stopWatch.start();

			InitUtil.initWithSpring();

			upgrade();
			verify();

			System.out.println(
				"\nSuccessfully completed upgrade process in " +
					(stopWatch.getTime() / Time.SECOND) + " seconds.");

			System.exit(0);
		}
		catch (Exception e) {
			e.printStackTrace();

			System.exit(1);
		}
	}

	public static void upgrade() throws Exception {

		// Disable database caching before upgrade

		CacheRegistry.setActive(false);

		// Upgrade

		int buildNumber = ReleaseLocalServiceUtil.getBuildNumberOrCreate();

		if (buildNumber < ReleaseInfo.RELEASE_4_2_1_BUILD_NUMBER) {
			String msg = "You must first upgrade to Liferay Portal 4.2.1";

			System.out.println(msg);

			throw new RuntimeException(msg);
		}

		StartupHelperUtil.upgradeProcess(buildNumber);

		// Class names

		ClassNameLocalServiceUtil.checkClassNames();

		// Resource actions

		ResourceActionsUtil.init();

		ResourceActionLocalServiceUtil.checkResourceActions();

		// Resource codes

		ResourceCodeLocalServiceUtil.checkResourceCodes();

		// Delete temporary images

		_deleteTempImages();

		// Clear the caches only if the upgrade process was run

		if (StartupHelperUtil.isUpgraded()) {
			MultiVMPoolUtil.clear();
		}
	}

	public static void verify() throws Exception {

		// Verify

		Release release = null;

		try {
			release = ReleaseLocalServiceUtil.getRelease(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME,
				ReleaseInfo.getBuildNumber());
		}
		catch (PortalException pe) {
			release = ReleaseLocalServiceUtil.addRelease(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME,
				ReleaseInfo.getBuildNumber());
		}

		StartupHelperUtil.verifyProcess(release.isVerified());

		// Update indexes

		if (StartupHelperUtil.isUpgraded()) {
			StartupHelperUtil.updateIndexes();
		}

		// Update release

		boolean verified = StartupHelperUtil.isVerified();

		if (release.isVerified()) {
			verified = true;
		}

		ReleaseLocalServiceUtil.updateRelease(
			release.getReleaseId(), ReleaseInfo.getBuildNumber(),
			ReleaseInfo.getBuildDate(), verified);

		// Enable database caching after verify

		CacheRegistry.setActive(true);
	}

	private static void _deleteTempImages() throws Exception {
		DB db = DBFactoryUtil.getDB();

		db.runSQL(_DELETE_TEMP_IMAGES_1);
		db.runSQL(_DELETE_TEMP_IMAGES_2);
	}

	private static final String _DELETE_TEMP_IMAGES_1 =
		"delete from Image where imageId IN (SELECT articleImageId FROM " +
			"JournalArticleImage where tempImage = TRUE)";

	private static final String _DELETE_TEMP_IMAGES_2 =
		"delete from JournalArticleImage where tempImage = TRUE";

}