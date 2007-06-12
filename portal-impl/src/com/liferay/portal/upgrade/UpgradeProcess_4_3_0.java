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

import com.liferay.portal.upgrade.v4_3_0.UpgradeCompany;
import com.liferay.portal.upgrade.v4_3_0.UpgradeUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeProcess_4_3_0.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class UpgradeProcess_4_3_0 extends UpgradeProcess {

	public int getThreshold() {

		// Version 4.2.2 has build number 3502

		return 3502;
	}

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		// Upgrade major tables

		upgrade(new UpgradeCompany());
		//upgrade(new UpgradeResource());
		upgrade(new UpgradeUser());
		//upgrade(new UpgradeUserGroup());
		//upgrade(new UpgradeGroup());

		// Upgrade all other tables

		//upgrade(new UpgradeAddress());
		//upgrade(new UpgradeBlogs());
		//upgrade(new UpgradeBookmarks());
		//upgrade(new UpgradeCalendar());
		//upgrade(new UpgradeContact());
		//upgrade(new UpgradeEmailAddress());
		//upgrade(new UpgradeOrgLabor());
		//upgrade(new UpgradePasswordTracker());
		//upgrade(new UpgradePhone());
		//upgrade(new UpgradePolls());
		//upgrade(new UpgradeSubscription());
		//upgrade(new UpgradeWebsite());
		//upgrade(new UpgradeWiki());
	}

	private static Log _log = LogFactory.getLog(UpgradeProcess_4_3_0.class);

}