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

package com.liferay.frontend.js.loader.modules.extender.internal.npm.builtin;

import com.liferay.frontend.js.loader.modules.extender.internal.npm.NPMRegistry;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;

import javax.servlet.Servlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=Serve Package Servlet",
		"osgi.http.whiteboard.servlet.pattern=/js/module/*",
		"service.ranking:Integer=" + (Integer.MAX_VALUE - 1000)
	},
	service = {BuiltInJSModuleServlet.class, Servlet.class}
)
public class BuiltInJSModuleServlet extends BaseBuiltInJSModuleServlet {

	@Override
	protected JSModule getJSModule(String moduleName) {
		return _npmRegistry.getJSModule(moduleName);
	}

	private static final long serialVersionUID = -8753225208295935344L;

	@Reference
	private NPMRegistry _npmRegistry;

}