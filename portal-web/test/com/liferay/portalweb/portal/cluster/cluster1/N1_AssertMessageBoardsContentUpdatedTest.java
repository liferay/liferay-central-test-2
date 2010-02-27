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

package com.liferay.portalweb.portal.cluster.cluster1;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="N1_AssertMessageBoardsContentUpdatedTest.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class N1_AssertMessageBoardsContentUpdatedTest extends BaseTestCase {
	public void testN1_AssertMessageBoardsContentUpdated()
		throws Exception {
		selenium.open("/");
		selenium.type("_58_login", RuntimeVariables.replace("test@liferay.com"));
		selenium.type("_58_password", RuntimeVariables.replace("test"));
		selenium.click(RuntimeVariables.replace("//input[@value='Sign In']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//div[4]/ul/li[5]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Node: [$CLUSTER_NODE_1$]"),
			selenium.getText("//div[@id='wrapper']/div[6]"));
		assertTrue(selenium.isElementPresent("link=Test Category 2"));
	}
}