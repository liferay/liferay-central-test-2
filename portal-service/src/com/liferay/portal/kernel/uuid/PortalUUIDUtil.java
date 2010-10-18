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

package com.liferay.portal.kernel.uuid;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalUUIDUtil {

	public static String fromSafeUuid(String safeUuid) {
		String uuid =
			StringUtil.replace(
				safeUuid,
				new String[] {StringPool.UNDERLINE + StringPool.UNDERLINE},
				new String[] {StringPool.DASH});

		return uuid;
	}


	public static String generate() {
		return getPortalUUID().generate();
	}

	public static PortalUUID getPortalUUID() {
		return _portalJNDI;
	}

	public void setPortalUUID(PortalUUID portalJNDI) {
		_portalJNDI = portalJNDI;
	}
	
	public static String toSafeUuid(String uuid) {
		String safeName =
			StringUtil.replace(
				uuid,
				new String[] {StringPool.DASH},
				new String[] {StringPool.UNDERLINE + StringPool.UNDERLINE});

		return safeName;
	}

	private static PortalUUID _portalJNDI;

}