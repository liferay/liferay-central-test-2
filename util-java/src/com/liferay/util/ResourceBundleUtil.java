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

package com.liferay.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.text.MessageFormat;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Neil Griffin
 */
public class ResourceBundleUtil {

	public static String getString(
		ResourceBundle resourceBundle, Locale locale, String key,
		Object[] arguments) {

		String value = null;

		if (resourceBundle == null) {
			if (_log.isErrorEnabled()) {
				_log.error("Resource bundle is null");
			}
		}
		else {

			// Get the value associated with the specified key, and substitute
			// any arguuments like {0}, {1}, {2}, etc. with the specified
			// argument values.

			value = resourceBundle.getString(key);

			if (value == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No value found for key " + key);
				}
			}
			else {
				if ((arguments != null) && (arguments.length > 0)) {
					MessageFormat messageFormat = new MessageFormat(
						value, locale);

					value = messageFormat.format(arguments);
				}
			}
		}

		if (value == null) {
			value = key;
		}

		return value;
	}

	private static Log _log = LogFactoryUtil.getLog(ResourceBundleUtil.class);

}