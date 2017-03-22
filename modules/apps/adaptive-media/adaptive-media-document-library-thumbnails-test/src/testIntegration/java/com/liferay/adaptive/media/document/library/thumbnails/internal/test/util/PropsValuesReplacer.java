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

import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Field;

/**
 * @author Adolfo Pérez
 */
public class PropsValuesReplacer implements AutoCloseable {

	public PropsValuesReplacer(String name, Object value) throws Exception {
		_field = ReflectionUtil.getDeclaredField(PropsValues.class, name);

		_oldValue = _field.get(null);

		_field.set(null, value);
	}

	@Override
	public void close() throws Exception {
		_field.set(null, _oldValue);

		ReflectionUtil.unfinalField(_field);
	}

	private final Field _field;
	private final Object _oldValue;

}