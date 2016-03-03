/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.StringPool;

import java.io.File;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class BaseSourceProcessorTest {

	@Before
	public void setUp() {
		setUpBaseSourceProcessor();
	}

	@Test
	public void testGetModuleLangPath() {
		testGetModuleLangPath(
			"./modules/apps/business-productivity/dynamic-data-mapping" +
				"/dynamic-data-mapping-web",
			"./modules/apps/business-productivity/dynamic-data-mapping" +
				"/dynamic-data-mapping-lang/src/main/resources/content");
		testGetModuleLangPath(
			"./modules/apps/web-experience-management/staging/staging-bar-web",
			"./modules/apps/web-experience-management/staging/staging-lang/" +
				"src/main/resources/content");
		testGetModuleLangPath(
			"./modules/apps/business-productivity/portal-workflow" +
				"/portal-workflow-definition-web",
			"./modules/apps/business-productivity/portal-workflow" +
				"/portal-workflow-lang/src/main/resources/content");
	}

	protected void setUpBaseSourceProcessor() {
		_baseSourceProcessor = new BaseSourceProcessor() {

			@Override
			public String[] getIncludes() {
				return null;
			}

			@Override
			protected String doFormat(
					File file, String fileName, String absolutePath,
					String content)
				throws Exception {

				return null;
			}

			@Override
			protected List<String> doGetFileNames() throws Exception {
				return null;
			}

		};
	}

	protected void testGetModuleLangPath(
		String moduleDirName, String... expectedModuleLangDirNames) {

		Assert.assertEquals(
			Arrays.asList(expectedModuleLangDirNames),
			_baseSourceProcessor.getModuleLangDirNames(
				moduleDirName, StringPool.BLANK));
	}

	private BaseSourceProcessor _baseSourceProcessor;

}