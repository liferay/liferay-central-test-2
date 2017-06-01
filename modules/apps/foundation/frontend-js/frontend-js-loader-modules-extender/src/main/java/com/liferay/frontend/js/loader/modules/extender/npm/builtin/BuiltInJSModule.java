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

package com.liferay.frontend.js.loader.modules.extender.npm.builtin;

import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.model.JSModuleAdapter;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Collection;

/**
 * @author Iván Zaera
 */
public abstract class BuiltInJSModule extends JSModuleAdapter {

	public BuiltInJSModule(
		JSPackage jsPackage, String name, Collection<String> dependencies) {

		super(
			jsPackage, name, _getURL(jsPackage, name),
			_getResolvedURL(jsPackage, name), _getResolvedId(jsPackage, name),
			dependencies);
	}

	private static String _getResolvedId(
		JSPackage jsPackage, String moduleName) {

		StringBundler sb = new StringBundler();

		sb.append(jsPackage.getName());
		sb.append(StringPool.AT);
		sb.append(jsPackage.getVersion());
		sb.append(StringPool.SLASH);
		sb.append(moduleName);

		return sb.toString();
	}

	private static String _getResolvedURL(
		JSPackage jsPackage, String moduleName) {

		StringBundler sb = new StringBundler();

		sb.append("/o/js/resolved-module/");
		sb.append(_getResolvedId(jsPackage, moduleName));

		return sb.toString();
	}

	private static String _getURL(JSPackage jsPackage, String moduleName) {
		StringBundler sb = new StringBundler();

		sb.append("/o/js/module/");
		sb.append(ModuleNameUtil.getModuleId(jsPackage, moduleName));

		return sb.toString();
	}

}