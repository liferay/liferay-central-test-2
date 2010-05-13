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

package com.liferay.portal.model.impl;

import com.liferay.portal.model.Lock;

/**
 * <a href="LockImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LockImpl extends LockModelImpl implements Lock {

	public LockImpl() {
	}

	public long getExpirationTime() {
		if (getExpirationDate().getTime() == 0) {
			return Long.MAX_VALUE;
		}

		return getExpirationDate().getTime();
	}

	public boolean isExpired() {
		if (System.currentTimeMillis() > getExpirationTime()) {
			return true;
		}
		else {
			return false;
		}
	}

}