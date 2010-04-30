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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.servlet.PortletServlet;

import javax.servlet.ServletContext;

/**
 * <a href="PortletClassInvoker.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class PortletClassInvoker {

	public static Object invoke(
			String portletId, String className, String methodName)
		throws Exception {

		return invoke(portletId, className, methodName, new Object[] {});
	}

	public static Object invoke(
			String portletId, String className, String methodName, Object arg)
		throws Exception {

		return invoke(portletId, className, methodName, new Object[] {arg});
	}

	public static Object invoke(
			String portletId, String className, String methodName, Object arg1,
			Object arg2)
		throws Exception {

		return invoke(
			portletId, className, methodName, new Object[] {arg1, arg2});
	}

	public static Object invoke(
			String portletId, String className, String methodName, Object arg1,
			Object arg2, Object arg3)
		throws Exception {

		return invoke(
			portletId, className, methodName, new Object[] {arg1, arg2, arg3});
	}

	public static Object invoke(
			String portletId, String className, String methodName, Object arg1,
			Object arg2, Object arg3, Object arg4)
		throws Exception {

		return invoke(
			portletId, className, methodName,
			new Object[] {arg1, arg2, arg3, arg4});
	}

	public static Object invoke(
			String portletId, String className, String methodName,
			Object[] args)
		throws Exception {

		return invoke(portletId, className, methodName, args, true);
	}

	public static Object invoke(
		String portletId,String className, String methodName,
		boolean newInstance)
		throws Exception {

		return invoke(
			portletId, className, methodName, new Object[] {}, newInstance);
	}

	public static Object invoke(
			String portletId, String className, String methodName, Object arg,
			boolean newInstance)
		throws Exception {

		return invoke(
			portletId, className, methodName, new Object[] {arg}, newInstance);
	}

	public static Object invoke(
			String portletId, String className, String methodName, Object arg1,
			Object arg2, boolean newInstance)
		throws Exception {

		return invoke(
			portletId, className, methodName, new Object[] {arg1, arg2},
			newInstance);
	}

	public static Object invoke(
			String portletId, String className, String methodName, Object arg1,
			Object arg2, Object arg3, boolean newInstance)
		throws Exception {

		return invoke(
			portletId, className, methodName, new Object[] {arg1, arg2, arg3},
			newInstance);
	}

	public static Object invoke(
			String portletId, String className, String methodName, Object arg1,
			Object arg2, Object arg3, Object arg4, boolean newInstance)
		throws Exception {

		return invoke(
			portletId, className, methodName,
			new Object[] {arg1, arg2, arg3, arg4}, newInstance);
	}

	public static Object invoke(
			String portletId, String className, String methodName,
			Object[] args, boolean newInstance)
		throws Exception {

		portletId = _getRootPortletId(portletId);

		ClassLoader portletClassLoader = PortalClassLoaderUtil.getClassLoader();

		PortletBag portletBag = PortletBagPool.get(portletId);

		if (portletBag != null) {
			ServletContext servletContext = portletBag.getServletContext();

			portletClassLoader = (ClassLoader)servletContext.getAttribute(
				PortletServlet.PORTLET_CLASS_LOADER);
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(portletClassLoader);

			MethodWrapper methodWrapper = new MethodWrapper(
				className, methodName, args);

			return MethodInvoker.invoke(methodWrapper, newInstance);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	/**
	 * Copied from <code>com.liferay.portal.model.PortletConstants</code>.
	 */
	private static String _getRootPortletId(String portletId) {
		int pos = portletId.indexOf(_INSTANCE_SEPARATOR);

		if (pos == -1) {
			return portletId;
		}
		else {
			return portletId.substring(0, pos);
		}
	}

	private static final String _INSTANCE_SEPARATOR = "_INSTANCE_";

}