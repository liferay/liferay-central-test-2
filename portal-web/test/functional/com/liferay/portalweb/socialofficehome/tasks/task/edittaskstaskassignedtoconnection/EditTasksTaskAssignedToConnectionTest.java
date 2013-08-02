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

package com.liferay.portalweb.socialofficehome.tasks.task.edittaskstaskassignedtoconnection;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditTasksTaskAssignedToConnectionTest extends BaseTestCase {
	public void testEditTasksTaskAssignedToConnection()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/user/joebloggs/so/dashboard/");
				selenium.clickAt("//nav/ul/li[contains(.,'Tasks')]/a/span",
					RuntimeVariables.replace("Tasks"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Tasks"),
					selenium.getText(
						"xPath=(//span[@class='portlet-title-default'])[contains(.,'Tasks')]"));
				assertEquals(RuntimeVariables.replace("I Have Created"),
					selenium.getText("link=I Have Created"));
				selenium.clickAt("link=I Have Created",
					RuntimeVariables.replace("I Have Created"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Task Description"),
					selenium.getText("link=Task Description"));
				selenium.clickAt("link=Task Description",
					RuntimeVariables.replace("Task Description"));
				selenium.waitForText("//h1[@class='header-title']",
					"Task Description");
				assertEquals(RuntimeVariables.replace("Task Description"),
					selenium.getText("//h1[@class='header-title']"));
				assertEquals(RuntimeVariables.replace(
						"Assigned to Social01 Office01 User01"),
					selenium.getText("//div[@class='task-data assignee']"));
				assertEquals(RuntimeVariables.replace("Open"),
					selenium.getText("//div[@class='task-data status']"));
				assertEquals(RuntimeVariables.replace("Normal"),
					selenium.getText("//div[@class='task-data normal']"));
				selenium.clickAt("//input[@value='Edit']",
					RuntimeVariables.replace("Edit"));
				Thread.sleep(1000);
				selenium.waitForVisible(
					"//input[@id='_1_WAR_tasksportlet_title']");
				selenium.type("//input[@id='_1_WAR_tasksportlet_title']",
					RuntimeVariables.replace("Task Description Edit"));
				assertEquals(RuntimeVariables.replace("Add Due Date"),
					selenium.getText("//a[@id='toggleDueDate']"));
				selenium.clickAt("//a[@id='toggleDueDate']",
					RuntimeVariables.replace("Add Due Date"));
				selenium.waitForText("//a[@id='toggleDueDate']",
					"Remove Due Date");

				boolean newDueDateMonthSelectPresent = selenium.isElementPresent(
						"//select[@id='_1_WAR_tasksportlet_duedatemonth']");

				if (!newDueDateMonthSelectPresent) {
					label = 2;

					continue;
				}

				selenium.select("//select[@id='_1_WAR_tasksportlet_duedatemonth']",
					RuntimeVariables.replace("label=April"));
				selenium.select("//select[@id='_1_WAR_tasksportlet_duedateday']",
					RuntimeVariables.replace("label=17"));
				selenium.select("//select[@id='_1_WAR_tasksportlet_duedateyear']",
					RuntimeVariables.replace("label=2017"));

			case 2:

				boolean oldDueDateMonthSelectPresent = selenium.isElementPresent(
						"//select[@id='_1_WAR_tasksportlet_dueDateMonth']");

				if (!oldDueDateMonthSelectPresent) {
					label = 3;

					continue;
				}

				selenium.select("//select[@id='_1_WAR_tasksportlet_dueDateMonth']",
					RuntimeVariables.replace("label=April"));
				selenium.select("//select[@id='_1_WAR_tasksportlet_dueDateDay']",
					RuntimeVariables.replace("label=17"));
				selenium.select("//select[@id='_1_WAR_tasksportlet_dueDateYear']",
					RuntimeVariables.replace("label=2017"));

			case 3:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForText("//h1[@class='header-title']",
					"Task Description Edit");
				assertEquals(RuntimeVariables.replace("Task Description Edit"),
					selenium.getText("//h1[@class='header-title']"));
				assertEquals(RuntimeVariables.replace(
						"Assigned to Social01 Office01 User01"),
					selenium.getText("//div[@class='task-data assignee']"));
				assertTrue(selenium.isPartialText(
						"//div[@class='task-data due-date']", "4/17/17"));

			case 100:
				label = -1;
			}
		}
	}
}