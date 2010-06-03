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

package com.liferay.portal.kernel.deploy.sandbox;

/**
 * <a href="SandboxUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Igor Spasic
 */
// todo add support for multiple dirs
public class SandboxUtil {

	public static void registerDir(SandboxDir sandboxDir) {
		_instance._registerDir(sandboxDir);
	}

	public static void unregisterDir() {
		_instance._unregisterDir();
	}

	private void _registerDir(SandboxDir sandboxDir) {
		_sandboxDir = sandboxDir;
		_sandboxDir.start();
	}

	private void _unregisterDir() {
		_sandboxDir.stopThread();
		_sandboxDir = null;
	}

	private static SandboxUtil _instance = new SandboxUtil();
	private SandboxDir _sandboxDir;

}