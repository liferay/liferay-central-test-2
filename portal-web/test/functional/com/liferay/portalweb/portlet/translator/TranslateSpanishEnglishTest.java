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

package com.liferay.portalweb.portlet.translator;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TranslateSpanishEnglishTest extends BaseTestCase {
	public void testTranslateSpanishEnglish() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Translator Test Page");
		selenium.clickAt("link=Translator Test Page",
			RuntimeVariables.replace("Translator Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//textarea[@id='_26_text']",
			RuntimeVariables.replace(
				"Mi nombre es traductor de Liferay, fluido adentro sobre 6 millones de formas de comunicaci\u00f3n."));
		selenium.select("//select[@id='_26_id']",
			RuntimeVariables.replace("Spanish to English"));
		selenium.clickAt("//input[@value='Translate']",
			RuntimeVariables.replace("Translate"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"My name is translator of Liferay, fluid inside on 6 million communication forms."));
	}
}