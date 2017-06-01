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

package com.liferay.frontend.js.loader.modules.extender.internal.npm.flat;

import com.liferay.frontend.js.loader.modules.extender.npm.JSBundle;
import com.liferay.frontend.js.loader.modules.extender.npm.model.JSPackageAdapter;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.net.URL;

/**
 * @author Iván Zaera
 */
public class FlatJSPackage extends JSPackageAdapter {

	public FlatJSPackage(
		FlatJSBundle flatJSBundle, String name, String version,
		String mainModuleName, boolean root) {

		super(flatJSBundle, name, version, mainModuleName);

		StringBundler basePath = new StringBundler();

		if (root) {
			basePath.append("META-INF/resources/");
		}
		else {
			basePath.append("META-INF/resources/node_modules/");
			basePath.append(getName());
			basePath.append(StringPool.AT);
			basePath.append(getVersion());
			basePath.append(StringPool.SLASH);
		}

		_basePath = basePath.toString();
	}

	@Override
	public FlatJSBundle getJSBundle() {
		return (FlatJSBundle)super.getJSBundle();
	}

	@Override
	public URL getResourceURL(String location) {
		JSBundle jsBundle = getJSBundle();

		return jsBundle.getResourceURL(_basePath + location);
	}

	@Override
	public String toString() {
		return getId();
	}

	private final String _basePath;

}