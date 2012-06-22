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

package com.liferay.portalweb.portal;

import com.liferay.portal.util.InitUtil;
import com.liferay.portalweb.portal.util.LiferaySeleneseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;
import com.liferay.portalweb.portal.util.TestPropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class BaseTestCase extends LiferaySeleneseTestCase {

	public BaseTestCase() {
		InitUtil.initWithSpring();
	}

	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();
	}

	@Override
	public void tearDown() throws Exception {
	}

	protected void loadRequiredJavaScriptModules() {
		Class<?> clazz = getClass();

		String className = clazz.getName();

		if (className.contains(".sampledata523.")) {
			return;
		}

		if (className.contains(".sampledata525.")) {
			return;
		}

		if (className.contains(".sampledata527.")) {
			return;
		}

		if (className.contains(".sampledata528.")) {
			return;
		}

		if (className.contains(".sampledata529.")) {
			return;
		}

		String location = selenium.getLocation();

		if (location.contains("/blogs/rss")) {
			return;
		}

		if (location.contains("/documents/")) {
			return;
		}

		if (location.contains("/journal/rss/")) {
			return;
		}

		if (location.contains(TestPropsValues.PORTAL_URL) ||
			location.contains("www.able.com") ||
			location.contains("www.baker.com") ||
			location.contains("www.charlie.com") ||
			location.contains("www.dog.com") ||
			location.contains("www.easy.com") ||
			location.contains("www.fox.com")) {

			selenium.getEval("window.Liferay.fire(\'initDockbar\');");
		}

	}

}