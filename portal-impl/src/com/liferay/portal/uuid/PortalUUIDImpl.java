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

package com.liferay.portal.uuid;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.util.UUID;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalUUIDImpl implements PortalUUID {

	public String fromSafeUuid(String safeUuid) {
		return StringUtil.replace(
			safeUuid, StringPool.DOUBLE_UNDERSCORE, StringPool.DASH);
	}

	public String generate() {
		return UUID.randomUUID().toString();
	}

	public String toSafeUuid(String uuid) {
		return StringUtil.replace(
			uuid, StringPool.DASH, StringPool.DOUBLE_UNDERSCORE);
	}

}