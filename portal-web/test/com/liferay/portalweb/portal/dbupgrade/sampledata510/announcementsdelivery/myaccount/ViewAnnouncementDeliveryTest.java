/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.dbupgrade.sampledata510.announcementsdelivery.myaccount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="ViewAnnouncementDeliveryTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class ViewAnnouncementDeliveryTest extends BaseTestCase {
	public void testViewAnnouncementDelivery() throws Exception {
		selenium.open("/web/guest/home");
		selenium.clickAt("link=My Account", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Alerts and Announcements",
			RuntimeVariables.replace(""));
		assertTrue(selenium.isChecked("//tr[2]/td[2]/input[2]"));
		assertTrue(selenium.isChecked("//td[3]/input[2]"));
		assertTrue(selenium.isChecked("//tr[3]/td[2]/input[2]"));
		assertTrue(selenium.isChecked(
				"//div[2]/table/tbody/tr[4]/td[2]/input[2]"));
		assertTrue(selenium.isChecked("//tr[3]/td[3]/input[2]"));
		assertTrue(selenium.isChecked("//tr[4]/td[3]/input[2]"));
		assertTrue(selenium.isChecked("//td[4]/input[2]"));
		assertTrue(selenium.isChecked("//tr[3]/td[4]/input[2]"));
		assertTrue(selenium.isChecked("//tr[4]/td[4]/input[2]"));
	}
}