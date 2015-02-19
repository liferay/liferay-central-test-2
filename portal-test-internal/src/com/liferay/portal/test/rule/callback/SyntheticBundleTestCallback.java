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

package com.liferay.portal.test.rule.callback;

import aQute.bnd.osgi.Builder;
import aQute.bnd.osgi.Jar;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.rule.callback.BaseTestCallback;
import com.liferay.portal.module.framework.ModuleFrameworkUtilAdapter;

import java.io.File;
import java.io.InputStream;

import java.net.URL;

import java.util.Properties;

import org.junit.runner.Description;

/**
 * @author Raymond Augé
 */
public class SyntheticBundleTestCallback extends BaseTestCallback<Long, Long> {

	public SyntheticBundleTestCallback(String bundlePackageName) {
		_bundlePackageName = bundlePackageName;
	}

	@Override
	public void afterClass(Class<?> clazz, Long bundleId) throws Throwable {
		if (bundleId == null) {
			return;
		}

		ModuleFrameworkUtilAdapter.stopBundle(bundleId);

		ModuleFrameworkUtilAdapter.uninstallBundle(bundleId);
	}

	@Override
	public Long beforeClass(Class<?> clazz) throws Throwable {
		InputStream inputStream = createBundle(clazz);

		Long bundleId = ModuleFrameworkUtilAdapter.addBundle(
			clazz.getName(), inputStream);

		ModuleFrameworkUtilAdapter.startBundle(bundleId);

		return bundleId;
	}

	@Override
	public void doAfterClass(Description description, Long bundleId)
		throws Throwable {

		afterClass(description.getTestClass(), bundleId);
	}

	@Override
	public Long doBeforeClass(Description description) throws Throwable {
		return beforeClass(description.getTestClass());
	}

	protected InputStream createBundle(Class<?> clazz) throws Exception {
		Builder builder = new Builder();

		builder.setBundleSymbolicName(clazz.getName());

		try {
			URL url = clazz.getResource("");

			String protocol = url.getProtocol();

			if (!protocol.equals("file")) {
				throw new IllegalStateException(
					"Test classes are not on the file system");
			}

			String basePath = url.getPath();

			Package pkg = clazz.getPackage();

			String packageName = pkg.getName();

			int index = basePath.indexOf(packageName.replace('.', '/') + '/');

			basePath = basePath.substring(0, index);

			File baseDir = new File(basePath);

			builder.setBase(baseDir);
			builder.setClasspath(new File[] {baseDir});
			builder.setProperty(
				"bundle.package", packageName + "." + _bundlePackageName);

			Properties properties = builder.getProperties();

			InputStream inputStream = clazz.getResourceAsStream(
				_bundlePackageName.replace('.', '/') + "/bnd.bnd");

			properties.load(inputStream);

			Jar jar = builder.build();

			UnsyncByteArrayOutputStream outputStream =
				new UnsyncByteArrayOutputStream();

			jar.write(outputStream);

			return new UnsyncByteArrayInputStream(outputStream.toByteArray());
		}
		finally {
			builder.close();
		}
	}

	private final String _bundlePackageName;

}