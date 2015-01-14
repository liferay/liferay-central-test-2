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

package com.liferay.portal.servlet.jsp.compiler.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import org.phidias.compile.ResourceResolver;

/**
 * @author Raymond Augé
 */
public class JspResolverFactory implements BundleActivator {

	public static ResourceResolver getResourceResolver() {
		if (_jspResourceCache == null) {
			return null;
		}

		return new JspResourceResolver(_jspResourceCache);
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_jspResourceCache = new JspResourceCache();

		bundleContext.addBundleListener(_jspResourceCache);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		bundleContext.removeBundleListener(_jspResourceCache);

		_jspResourceCache = null;
	}

	private static JspResourceCache _jspResourceCache;

}