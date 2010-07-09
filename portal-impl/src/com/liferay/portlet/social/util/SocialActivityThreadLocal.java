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

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.util.InitialThreadLocal;

/**
 * @author Amos Fong
 */
public class SocialActivityThreadLocal {

	public static boolean isEnabled() {
		return _enabled.get().booleanValue();
	}

	public static void setEnabled(boolean enabled) {
		_enabled.set(enabled);
	}

	private static ThreadLocal<Boolean> _enabled =
		new InitialThreadLocal<Boolean>(
			SocialActivityThreadLocal.class + "._enabled", true);

}