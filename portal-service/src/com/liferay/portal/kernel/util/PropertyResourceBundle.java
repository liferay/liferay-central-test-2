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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class PropertyResourceBundle extends ResourceBundle {

	@SuppressWarnings("rawtypes")
	public PropertyResourceBundle(String string, String charsetName)
		throws IOException {

		_map = new HashMap(PropertiesUtil.load(string, charsetName));
	}

	@SuppressWarnings("rawtypes")
	public PropertyResourceBundle(InputStream inputStream, String charsetName)
		throws IOException {

		_map = new HashMap(PropertiesUtil.load(inputStream, charsetName));
	}

	public Object handleGetObject(String key) {
		if (key == null) {
			throw new NullPointerException();
		}

		return _map.get(key);
	}

	public Enumeration<String> getKeys() {
		final Set<String> keys = _map.keySet();

		final Enumeration<String> parentKeys = parent.getKeys();

		final Iterator<String> itr = keys.iterator();

		return new Enumeration<String>() {
			String next = null;

			public boolean hasMoreElements() {
				if (next == null) {
					if (itr.hasNext()) {
						next = itr.next();
					}
					else if (parentKeys != null) {
						while ((next == null) && parentKeys.hasMoreElements()) {
							next = parentKeys.nextElement();

							if (keys.contains(next)) {
								next = null;
							}
						}
					}
				}

				if (next != null) {
					return true;
				}
				else {
					return false;
				}
			}

			public String nextElement() {
				if (hasMoreElements()) {
					String result = next;

					next = null;

					return result;
				}
				else {
					throw new NoSuchElementException();
				}
			}
		};
	}

	private Map<String, String> _map;

}