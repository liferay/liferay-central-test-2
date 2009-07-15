/*
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.upgrade;

import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.model.Release;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.ReleaseLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;
import com.liferay.portal.util.InitUtil;

/**
 * <a href="StandaloneUpgrade.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class StandaloneDBUpgrader {

	public static void main(String[] args) {
		String updateProperties = "com/liferay/portal/tools/dependencies/portal-upgrade.properties";

		if (args.length == 1) {
			updateProperties = args[0];
		}

		if (System.getProperty("external-properties") == null) {
			System.setProperty("external-properties", updateProperties);
		}

		//System.setProperty(
		//	"com.mchange.v2.c3p0.management.ManagementCoordinator",
		//	"com.mchange.v2.c3p0.management.NullManagementCoordinator");
		
		System.out.println("Upgrading with properties: " + updateProperties);

		try {
			InitUtil.initWithSpring();

			CacheRegistry.setActive(false);

			// Upgrade

			int buildNumber = ReleaseLocalServiceUtil.getBuildNumberOrCreate();

			if (buildNumber < ReleaseInfo.RELEASE_4_2_1_BUILD_NUMBER) {
				String msg = "You must first upgrade to Liferay Portal 4.2.1";

				throw new RuntimeException(msg);
			}

			System.out.println("Upgrading database schemas...");
			StartupHelperUtil.upgradeProcess(buildNumber);

			// Class names

			ClassNameLocalServiceUtil.checkClassNames();

			// Resource actions

			ResourceActionsUtil.init();

			ResourceActionLocalServiceUtil.checkResourceActions();

			// Resource codes

			ResourceCodeLocalServiceUtil.checkResourceCodes();

			// Delete temporary images

			StartupHelperUtil.deleteTempImages();

			// Clear the caches only if the upgrade process was run

			if (StartupHelperUtil.isUpgraded()) {
				MultiVMPoolUtil.clear();
			}

			// Verify

			System.out.println("Verifying database...");
			Release release = ReleaseLocalServiceUtil.getRelease();

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

			System.out.println("Updating release information...");
			ReleaseLocalServiceUtil.updateRelease(verified);
			
			System.out.println(
				"Successfully completed upgrade process.  Exiting...");
			System.exit(0);
		}
		catch (Exception e) {
			System.out.println("Unable to complete upgrade process: ");
			e.printStackTrace();
			System.exit(1);
		}
	}
}
