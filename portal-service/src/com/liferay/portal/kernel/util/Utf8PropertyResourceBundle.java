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

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.util.PropertyResourceBundle;

/**
 * @author Michael Young
 */
public class Utf8PropertyResourceBundle extends PropertyResourceBundle {

	public Utf8PropertyResourceBundle(InputStream inputStream)
		throws IOException {

		super(inputStream);
	}

	public Object handleGetObject(String key) {
		String value = (String)super.handleGetObject(key);

		if (value == null) {
			return null;
		}

		try {
			return new String(
				value.getBytes(StringPool.ISO_8859_1), StringPool.UTF8);
		}
		catch (UnsupportedEncodingException e) {
			return null;
		}
	}

}