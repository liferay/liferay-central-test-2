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
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import java.util.PropertyResourceBundle;

/**
 * @author Michael Young
 */
public class Utf8PropertyResourceBundle extends PropertyResourceBundle {

	public Utf8PropertyResourceBundle(InputStream stream) throws IOException {
		super(stream);
	}

	public Utf8PropertyResourceBundle(Reader reader) throws IOException {
		super(reader);
	}

	public Object handleGetObject(String key) {
		String value = (String)super.handleGetObject(key);

		if (value == null) {
			return null;
		}

		try {
			return new String(value.getBytes("ISO-8859-1"), "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			return null;
		}
	}

}