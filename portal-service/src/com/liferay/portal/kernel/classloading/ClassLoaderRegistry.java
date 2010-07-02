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

package com.liferay.portal.kernel.classloading;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="ClassLoaderRegistry.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ClassLoaderRegistry {

	public static synchronized ClassLoader lookupClassLoader(String id) {
		return _idToClassLoader.get(id);
	}

	public static synchronized String lookupId(ClassLoader classLoader) {
		return _classLoaderToId.get(classLoader);
	}

	public static synchronized void register(
		ClassLoader classLoader, String id) {
		_classLoaderToId.put(classLoader, id);
		_idToClassLoader.put(id, classLoader);
	}

	public static synchronized void unregister(String id) {
		ClassLoader oldClassLoader = _idToClassLoader.remove(id);
		_classLoaderToId.remove(oldClassLoader);

	}

	private static Map<ClassLoader, String> _classLoaderToId =
		new HashMap<ClassLoader, String>();
	private static Map<String, ClassLoader> _idToClassLoader =
		new HashMap<String, ClassLoader>();

}