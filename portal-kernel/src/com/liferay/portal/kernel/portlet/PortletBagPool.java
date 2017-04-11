/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.security.pacl.PACLConstants;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletBagPool {

	public static PortletBag get(String portletId) {
		PortalRuntimePermission.checkPortletBagPool(portletId);

		return _portletBagPool.get(portletId);
	}

	public static void put(String portletId, PortletBag portletBag) {
		PortalRuntimePermission.checkPortletBagPool(portletId);

		_portletBagPool.put(portletId, portletBag);
	}

	public static PortletBag remove(String portletId) {
		PortalRuntimePermission.checkPortletBagPool(portletId);

		return _portletBagPool.remove(portletId);
	}

	public static void reset() {
		PortalRuntimePermission.checkPortletBagPool(
			PACLConstants.
				PORTAL_RUNTIME_PERMISSION_PORTLET_BAG_POOL_ALL_PORTLETS);

		_portletBagPool.clear();
	}

	private PortletBagPool() {
	}

	private static final Map<String, PortletBag> _portletBagPool =
		new ConcurrentHashMap<>();

}