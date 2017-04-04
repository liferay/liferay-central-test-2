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

package com.liferay.portal.module.framework;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ServiceLoaderCondition;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.util.PropsValues;

import java.io.UnsupportedEncodingException;

import java.net.URL;
import java.net.URLEncoder;

/**
 * @author Raymond Augé
 */
public class RuntimeServiceLoaderCondition implements ServiceLoaderCondition {

	@Override
	public boolean isLoad(URL url) {
		String path = url.getPath();

		path = StringUtil.replace(path, CharPool.BACK_SLASH, CharPool.SLASH);

		if (!path.startsWith("file:/") && path.startsWith("file:")) {
			path = "file:/" + path.substring(5, path.length());
		}

		String moduleFrameworkBaseDirName =
			PropsValues.MODULE_FRAMEWORK_BASE_DIR.replace(
				StringPool.DOUBLE_SLASH, StringPool.SLASH);

		moduleFrameworkBaseDirName = URLCodec.encodeURL(
			moduleFrameworkBaseDirName, StringPool.UTF8, true);

		return path.contains(moduleFrameworkBaseDirName);
	}

}