/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.pollsdisplay.question.votequestion;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class VoteQuestionTest extends BaseTestCase {
	public void testVoteQuestion() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Polls Display Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Polls Display Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//span[2]/span/span/input",
			RuntimeVariables.replace(""));
		selenium.clickAt("//input[@value='Vote']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Thank you for your vote."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isTextPresent("Thank you for your vote."));
		assertEquals(RuntimeVariables.replace("0%"),
			selenium.getText("//tr[2]/td[1]"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[2]/td[2]"));
		assertEquals(RuntimeVariables.replace("Test Choice A"),
			selenium.getText("//tr[2]/td[5]"));
		assertEquals(RuntimeVariables.replace("100%"),
			selenium.getText("//tr[3]/td[1]"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Test Choice B"),
			selenium.getText("//tr[3]/td[7]"));
		assertEquals(RuntimeVariables.replace("0%"),
			selenium.getText("//tr[4]/td[1]"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[4]/td[2]"));
		assertEquals(RuntimeVariables.replace("Test Choice C"),
			selenium.getText("//tr[4]/td[5]"));
		assertTrue(selenium.isTextPresent("Total Votes: 1"));
		assertFalse(selenium.isElementPresent("//input[@value='Vote']"));
		assertFalse(selenium.isElementPresent("//span[1]/span/span/input"));
		assertFalse(selenium.isElementPresent("//span[2]/span/span/input"));
		assertFalse(selenium.isElementPresent("//span[3]/span/span/input"));
	}
}