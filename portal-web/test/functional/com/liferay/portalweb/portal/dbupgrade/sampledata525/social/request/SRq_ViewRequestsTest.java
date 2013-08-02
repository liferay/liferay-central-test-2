/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.dbupgrade.sampledata525.social.request;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SRq_ViewRequestsTest extends BaseTestCase {
	public void testSRq_ViewRequests() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/socialrequestsn1/home/");
		selenium.clickAt("link=Requests Test Page",
			RuntimeVariables.replace("Requests Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"socialrequestfn2 socialrequestmn2 socialrequestln2"),
			selenium.getText("//div/a[contains(.,'socialrequestfn2')]"));
		assertEquals(RuntimeVariables.replace("Confirm"),
			selenium.getText("//a[.='Confirm']"));
		assertEquals(RuntimeVariables.replace("Ignore"),
			selenium.getText("//a[.='Ignore']"));
	}
}