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

/**
 * @author Shuyang Zhou
 */
public class PropertyResourceBundle extends ResourceBundle {

	public PropertyResourceBundle(String s, String charsetName)
		throws IOException {
		lookup = new HashMap(PropertiesUtil.load(s, charsetName));
	}

	public PropertyResourceBundle(InputStream stream, String charsetName)
		throws IOException {
		lookup = new HashMap(PropertiesUtil.load(stream, charsetName));
	}

	public Object handleGetObject(String key) {
		if (key == null) {
			throw new NullPointerException();
		}
		return lookup.get(key);
	}

	public Enumeration<String> getKeys() {
		final Iterator<String> iterator = lookup.keySet().iterator();
		final Enumeration<String> parentKeys = parent.getKeys();

		return new Enumeration<String>() {

			String next = null;

			public boolean hasMoreElements() {
				if (next == null) {
					if (iterator.hasNext()) {
						next = iterator.next();
					}
					else if (parentKeys != null) {
						while (next == null && parentKeys.hasMoreElements()) {
							next = parentKeys.nextElement();
							if (lookup.keySet().contains(next)) {
								next = null;
							}
						}
					}
				}
				return next != null;
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

	private Map lookup;

}