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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.struts.action.Action;

/**
 * @author Mika Koivisto
 */
public class StrutsActionWrapperRegistry {

	public static void registerMapping(
			String path, StrutsPortletAction strutsAction) {

		Action action = new StrutsWrapperPortletAction(strutsAction);

		_actionMappings.put(path, action);
	}

	public static void registerMapping(String path, StrutsAction strutsAction) {
		Action action = new StrutsWrapperAction(strutsAction);

		_actionMappings.put(path, action);
	}

	public static void unregisterMapping(String path) {
		_actionMappings.remove(path);
	}

	public static Action getAction(String path) {
		return _actionMappings.get(path);
	}

	private static Map<String, Action> _actionMappings =
		new ConcurrentHashMap<String, Action>();
}
