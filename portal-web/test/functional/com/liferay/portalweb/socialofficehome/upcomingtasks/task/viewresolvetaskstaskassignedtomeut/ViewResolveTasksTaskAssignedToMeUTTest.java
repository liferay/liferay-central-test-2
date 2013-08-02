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

package com.liferay.portalweb.socialofficehome.upcomingtasks.task.viewresolvetaskstaskassignedtomeut;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewResolveTasksTaskAssignedToMeUTTest extends BaseTestCase {
	public void testViewResolveTasksTaskAssignedToMeUT()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/user/joebloggs/so/dashboard/");
				selenium.waitForText("//h1/span[contains(.,'Upcoming Tasks')]",
					"Upcoming Tasks");
				assertEquals(RuntimeVariables.replace("Upcoming Tasks"),
					selenium.getText("//h1/span[contains(.,'Upcoming Tasks')]"));
				assertTrue(selenium.isElementNotPresent(
						"//li[@class='tasks-title normal']/a"));
				assertFalse(selenium.isTextPresent("Task Description"));
				assertEquals(RuntimeVariables.replace("View All Tasks"),
					selenium.getText("//div[@class='view-all-tasks']/a"));
				selenium.clickAt("//div[@class='view-all-tasks']/a",
					RuntimeVariables.replace("View All Tasks"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-info']");
				assertEquals(RuntimeVariables.replace("No tasks were found."),
					selenium.getText("//div[@class='portlet-msg-info']"));

				boolean showCompleted1Checked = selenium.isChecked(
						"//td[1]/input");

				if (showCompleted1Checked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//td[1]/input",
					RuntimeVariables.replace("Check Show Completed Tasks"));

			case 2:
				selenium.waitForVisible("link=Task Description");
				assertEquals(RuntimeVariables.replace("Task Description"),
					selenium.getText("link=Task Description"));

			case 100:
				label = -1;
			}
		}
	}
}