/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.socialofficehome.upcomingtasks.task.viewtaskstaskassignedtomemultipleut;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddTasksTask3AssignedToMeTest extends BaseTestCase {
	public void testAddTasksTask3AssignedToMe() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/user/joebloggs/so/dashboard/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//nav/ul/li[contains(.,'Tasks')]/a/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//nav/ul/li[contains(.,'Tasks')]/a/span",
					RuntimeVariables.replace("Tasks"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Tasks"),
					selenium.getText("//span[@class='portlet-title-default']"));
				assertEquals(RuntimeVariables.replace("Add Task"),
					selenium.getText("link=Add Task"));
				selenium.clickAt("link=Add Task",
					RuntimeVariables.replace("Add Task"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@id='_1_WAR_tasksportlet_title']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@id='_1_WAR_tasksportlet_title']",
					RuntimeVariables.replace("Task3 Description"));
				selenium.select("//select[@name='_1_WAR_tasksportlet_assigneeUserId']",
					RuntimeVariables.replace("label=Joe Bloggs"));
				selenium.select("//select[@name='_1_WAR_tasksportlet_priority']",
					RuntimeVariables.replace("label=Normal"));

				boolean neverDueChecked = selenium.isChecked(
						"_1_WAR_tasksportlet_neverDueCheckbox");

				if (neverDueChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_1_WAR_tasksportlet_neverDueCheckbox']",
					RuntimeVariables.replace("Never Due"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_1_WAR_tasksportlet_neverDueCheckbox']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Task3 Description")
												.equals(selenium.getText(
										"//h1[@class='header-title']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Task3 Description"),
					selenium.getText("//h1[@class='header-title']"));
				assertEquals(RuntimeVariables.replace("Assigned to Joe Bloggs"),
					selenium.getText("//div[@class='task-data assignee']"));

			case 100:
				label = -1;
			}
		}
	}
}