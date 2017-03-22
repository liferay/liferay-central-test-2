/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.adaptive.media.document.library.thumbnails.internal.test.util;

import com.liferay.portal.util.PropsUtil;

/**
 * @author Adolfo Pérez
 */
public class PropsUtilReplacer implements AutoCloseable {

	public PropsUtilReplacer(String key, String value) {
		_key = key;
		_oldValue = PropsUtil.get(key);

		PropsUtil.set(key, value);
	}

	@Override
	public void close() throws Exception {
		PropsUtil.set(_key, _oldValue);
	}

	private final String _key;
	private final String _oldValue;

}