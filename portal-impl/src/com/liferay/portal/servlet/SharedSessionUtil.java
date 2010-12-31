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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletVersionDetector;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Shuyang Zhou
 */
public class SharedSessionUtil {

	public static Map<String, Object> getSharedSessionAttributes(
		HttpServletRequest request) {

		HttpSession session = request.getSession();

		if (ServletVersionDetector.is2_5()) {
			Map<String, Object> attributes = new HashMap<String, Object>();

			Enumeration<String> enu = session.getAttributeNames();

			while (enu.hasMoreElements()) {
				String name = enu.nextElement();

				Object value = session.getAttribute(name);

				if (value == null) {
					continue;
				}

				if (ArrayUtil.contains(
						PropsValues.SHARED_SESSION_ATTRIBUTES_EXCLUDES, name)) {

					continue;
				}

				for (String sharedName :
						PropsValues.SHARED_SESSION_ATTRIBUTES) {

					if (!name.startsWith(sharedName)) {
						continue;
					}

					if (_log.isDebugEnabled()) {
						_log.debug("Sharing " + name);
					}

					attributes.put(name, value);

					break;
				}
			}

			return attributes;
		}
		else {
			SharedSessionAttributeCache sharedSessionAttributeCache =
				SharedSessionAttributeCache.getInstance(session);

			Map<String, Object> values =
				sharedSessionAttributeCache.getValues();

			if (_log.isDebugEnabled()) {
				_log.debug("Shared session attributes " + values);
			}

			return values;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SharedSessionUtil.class);

}