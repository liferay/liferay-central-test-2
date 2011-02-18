/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.context;

import com.liferay.portal.kernel.util.InitialThreadLocal;

/**
 * @author Shuyang Zhou
 */
public class PortalContextLoaderLifecycleThreadLocal {

	public static boolean isContextDestroying() {
		return _contextDestroying.get();
	}

	public static boolean isContextInitializing() {
		return _contextInitializing.get();
	}

	public static void setContextDestroying(boolean contextDestroying) {
		_contextDestroying.set(contextDestroying);
	}

	public static void setContextInitializing(boolean contextInitializing) {
		_contextInitializing.set(contextInitializing);
	}

	private static ThreadLocal<Boolean> _contextDestroying =
		new InitialThreadLocal<Boolean>(
			PortalContextLoaderLifecycleThreadLocal.class +
				"._contextDestroying", Boolean.FALSE);
	private static ThreadLocal<Boolean> _contextInitializing =
		new InitialThreadLocal<Boolean>(
			PortalContextLoaderLifecycleThreadLocal.class +
				"._contextInitializing", Boolean.FALSE);

}