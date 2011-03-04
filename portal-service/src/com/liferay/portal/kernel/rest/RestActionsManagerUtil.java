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

package com.liferay.portal.kernel.rest;

import java.lang.reflect.Method;
public class RestActionsManagerUtil {

	public static RestActionsManager getRestActionsManager() {
		return _restRestActionsManager;
	}

	public static Object lookup(String[] pathChunks, String method) {

		return getRestActionsManager().lookup(pathChunks, method);
	}

	public static Object[] prepareParameters(
		String[] pathChunks, Object config) {

		return getRestActionsManager().prepareParameters(pathChunks, config);
	}

	public static void registerRestAction(
		Class actionClass, Method actionMethod, String path, String method) {

		getRestActionsManager().registerRestAction(
			actionClass, actionMethod, path, method);
	}

	public void setRestActionsManager(RestActionsManager restActionsManager) {

		_restRestActionsManager = restActionsManager;
	}

	private static RestActionsManager _restRestActionsManager;

}