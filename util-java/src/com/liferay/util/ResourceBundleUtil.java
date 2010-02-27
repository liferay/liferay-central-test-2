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

package com.liferay.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.text.MessageFormat;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <a href="ResourceBundleUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This class provides a convenience routine named getString() that gets the
 * value of a ResourceBundle key, but also provides the ability to perform
 * search & replace on tokens within the value of the key.
 * </p>
 *
 * @author Neil Griffin
 */
public class ResourceBundleUtil {

	public static String getString(
		ResourceBundle bundle, Locale locale, String key, Object[] args) {

		String value = null;

		if (bundle == null) {
			if (_log.isErrorEnabled()) {
				_log.error("Resource bundle is null");
			}
		}
		else {

			// Get the value associated with the specified key, and substitute
			// any arguuments like {0}, {1}, {2}, etc. with the specified
			// argument values.

			value = bundle.getString(key);

			if (value == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No value found for key " + key);
				}
			}
			else {
				if ((args != null) && (args.length > 0)) {
					MessageFormat format = new MessageFormat(value, locale);

					value = format.format(args);
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