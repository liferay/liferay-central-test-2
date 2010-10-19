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

import com.liferay.portal.model.ResourcePermission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class ResourcePermissionBagThreadLocal {

	public static Map<Long, ResourcePermission> getResourcePermissions() {
		return _resourcePermissionBag.get();
	}

	public static void remove() {
		_resourcePermissionBag.remove();
	}

	public static void setResourcePermissions(
		List<ResourcePermission> resourcePermissions) {
		Map<Long, ResourcePermission> resourcePermissionMap =
			new HashMap<Long, ResourcePermission>();
		for (ResourcePermission resourcePermission : resourcePermissions) {
			resourcePermissionMap.put(
				resourcePermission.getRoleId(), resourcePermission);
		}
	}

	private static ThreadLocal<Map<Long, ResourcePermission>>
		_resourcePermissionBag =
			new InitialThreadLocal<Map<Long, ResourcePermission>>(
				ResourcePermissionBagThreadLocal.class +
					"._resourcePermissionBag", null);

}