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

package com.liferay.portal.kernel.deploy.auto;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="AutoDeployUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class AutoDeployUtil {

	public static AutoDeployDir getDir(String name) {
		return _instance._getDir(name);
	}

	public static void registerDir(AutoDeployDir dir) {
		_instance._registerDir(dir);
	}

	public static void unregisterDir(String name) {
		_instance._unregisterDir(name);
	}

	private AutoDeployUtil() {
		_dirs = new HashMap<String, AutoDeployDir>();
	}

	private AutoDeployDir _getDir(String name) {
		return _dirs.get(name);
	}

	private void _registerDir(AutoDeployDir dir) {
		_dirs.put(dir.getName(), dir);

		dir.start();
	}

	private void _unregisterDir(String name) {
		AutoDeployDir dir = _dirs.remove(name);

		if (dir != null) {
			dir.stop();
		}
	}

	private static AutoDeployUtil _instance = new AutoDeployUtil();

	private Map<String, AutoDeployDir> _dirs;

}