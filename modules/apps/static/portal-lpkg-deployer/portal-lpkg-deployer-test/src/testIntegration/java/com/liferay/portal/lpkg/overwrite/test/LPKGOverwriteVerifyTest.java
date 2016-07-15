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

package com.liferay.portal.lpkg.overwrite.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.Version;

/**
 * @author Matthew Tambara
 */
@RunWith(Arquillian.class)
public class LPKGOverwriteVerifyTest {

	@Test
	public void testCleanStartUp() throws Exception {
		if (Boolean.getBoolean("lpkg.clean.startup")) {
			_testOverWrittenLPKGs();
		}
	}

	@Test
	public void testSecondStartup() throws Exception {
		if (!Boolean.getBoolean("lpkg.clean.startup")) {
			_testOverWrittenLPKGs();
		}
	}

	private void _testOverWrittenLPKGs() throws Exception {
		Bundle testBundle = FrameworkUtil.getBundle(
			LPKGOverwriteVerifyTest.class);

		BundleContext bundleContext = testBundle.getBundleContext();

		Properties properties = new Properties();

		Path path = Paths.get(PropsValues.LIFERAY_HOME, "/overwrites");

		Assert.assertTrue(Files.exists(path));

		properties.load(Files.newBufferedReader(path));

		Map<String, String> jars = new HashMap<>();

		List<String> statics = new ArrayList<>();

		List<String> wars = new ArrayList<>();

		for (Entry<Object, Object> entry : properties.entrySet()) {
			String symbolicName = (String)entry.getKey();

			if (symbolicName.startsWith("static.")) {
				statics.add(symbolicName.substring(7));
			}
			else if (symbolicName.startsWith("war.")) {
				wars.add(symbolicName.substring(4));
			}
			else {
				jars.put((String)entry.getKey(), (String)entry.getValue());
			}
		}

		for (Bundle bundle : bundleContext.getBundles()) {
			String symbolicName = bundle.getSymbolicName();

			String version = jars.remove(symbolicName);

			if (Validator.isNotNull(version)) {
				Assert.assertEquals(
					"JAR not sucessfully overwritten: " + symbolicName,
					new Version(version), bundle.getVersion());
			}
			else if (statics.remove(symbolicName)) {
				String location = bundle.getLocation();

				Assert.assertTrue(
					"Static JAR not sucessfully overwritten: " + symbolicName,
					location.contains("Static-Jar::"));
			}
			else if (wars.remove(symbolicName)) {
				String location = bundle.getLocation();

				Assert.assertTrue(
					"WAR not sucessfully overwritten: " + symbolicName,
					!location.contains("lpkg://"));
			}
		}

		List<Entry> leftoverEntries = new ArrayList<>();

		leftoverEntries.addAll(jars.entrySet());

		for (Entry entry : leftoverEntries) {
			if (entry.getValue() == null) {
				leftoverEntries.remove(entry);
			}
		}

		Collections.sort(
			leftoverEntries,
			new Comparator<Entry>() {

				@Override
				public int compare(Entry entry1, Entry entry2) {
					String entrySymbolicname = (String)entry1.getKey();

					return entrySymbolicname.compareTo((String)entry2.getKey());
				}

			});

		Assert.assertTrue(
			"JARs not overwritten: " + jars.entrySet(), jars.isEmpty());
		Assert.assertTrue(
			"Static JARs not overwritten: " + statics, statics.isEmpty());
		Assert.assertTrue("WARs not overwritten: " + wars, wars.isEmpty());
	}

}