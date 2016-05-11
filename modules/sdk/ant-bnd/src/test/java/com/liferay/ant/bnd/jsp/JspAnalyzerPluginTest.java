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

package com.liferay.ant.bnd.jsp;

import static org.junit.Assert.assertEquals;

import aQute.bnd.osgi.Builder;
import aQute.bnd.osgi.Constants;

import aQute.lib.io.IO;

import com.liferay.ant.bnd.jsp.JspAnalyzerPlugin;

import java.io.InputStream;

import java.net.URL;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Gregory Amerson
 */
public class JspAnalyzerPluginTest {

	@Test
	public void testGetTaglibURIsWithComments() throws Exception {
		JspAnalyzerPlugin jspAnalyzerPlugin = new JspAnalyzerPlugin();

		URL url = getResource("dependencies/imports_with_comments.jsp");

		InputStream inputStream = url.openStream();

		String content = IO.collect(inputStream);

		Set<String> taglibURIs = jspAnalyzerPlugin.getTaglibURIs(content);

		Assert.assertNotNull(taglibURIs);

		int size = taglibURIs.size();

		Assert.assertEquals(3, size);
	}

	@Test
	public void testGetTaglibURIsWithoutComments() throws Exception {
		JspAnalyzerPlugin jspAnalyzerPlugin = new JspAnalyzerPlugin();

		URL url = getResource("dependencies/imports_without_comments.jsp");

		InputStream inputStream = url.openStream();

		String content = IO.collect(inputStream);

		Set<String> taglibURIs = jspAnalyzerPlugin.getTaglibURIs(content);

		Assert.assertNotNull(taglibURIs);

		int size = taglibURIs.size();

		Assert.assertEquals(8, size);
	}

	@Test
	public void testRemoveDuplicateTaglibRequirements() throws Exception {
		JspAnalyzerPlugin jspAnalyzerPlugin = new JspAnalyzerPlugin();

		URL url = getResource("dependencies/imports_without_comments.jsp");

		InputStream inputStream = url.openStream();

		String content = IO.collect(inputStream);

		Builder b = new Builder();

		b.build();

		jspAnalyzerPlugin.addTaglibRequirements(b, content);

		String requireCapabilityBefore = b.getProperty(
			Constants.REQUIRE_CAPABILITY);

		jspAnalyzerPlugin.addTaglibRequirements(b, content);

		String requireCapabilityAfter = b.getProperty(
			Constants.REQUIRE_CAPABILITY);

		assertEquals(requireCapabilityBefore, requireCapabilityAfter);
	}

	protected URL getResource(String path) {
		Class<?> clazz = getClass();

		return clazz.getResource(path);
	}

}