/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author Raymond Augé
 */
public class ClassLoaderUtil {

	public static ClassLoader getClassLoader(final Class<?> clazz) {
		return AccessController.doPrivileged(
			new PrivilegedAction<ClassLoader>() {

				public ClassLoader run() {
					return clazz.getClassLoader();
				}

			}
		);
	}

	public static ClassLoader getContextClassLoader() {
		return AccessController.doPrivileged(
			new PrivilegedAction<ClassLoader>() {

				public ClassLoader run() {
					Thread thread = Thread.currentThread();

					return thread.getContextClassLoader();
				}

			}
		);
	}

	public static ClassLoader getPortalClassLoader() {
		return AccessController.doPrivileged(
			new PrivilegedAction<ClassLoader>() {

				public ClassLoader run() {
					return PortalClassLoaderUtil.getClassLoader();
				}

			}
		);
	}

	public static void setContextClassLoader(final ClassLoader classLoader) {
		AccessController.doPrivileged(
			new PrivilegedAction<Void>() {

				public Void run() {
					Thread thread = Thread.currentThread();

					thread.setContextClassLoader(classLoader);

					return null;
				}

			}
		);
	}

}