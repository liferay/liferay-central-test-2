/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.spring.context;

import com.liferay.portal.kernel.servlet.PortalClassLoaderServletContextListener;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import javax.servlet.ServletContextListener;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletContextLoaderListener
	extends PortalClassLoaderServletContextListener {

	protected ServletContextListener getInstance() throws Exception {
		Class<?> classObj = Class.forName(
			_CLASS_NAME, true, PortalClassLoaderUtil.getClassLoader());

		return (ServletContextListener)classObj.newInstance();
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.spring.context.PortletContextLoaderListener";

}