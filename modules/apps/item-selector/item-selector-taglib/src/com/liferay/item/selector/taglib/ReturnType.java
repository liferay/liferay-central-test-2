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

package com.liferay.item.selector.taglib;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.util.Set;

/**
 * @author Roberto Díaz
 */
public enum ReturnType {

	BASE_64(Base64.class), FILE_ENTRY(FileEntry.class), URL(java.net.URL.class);

	public static ReturnType parse(Class<?> value) {
		if (BASE_64.getValue().equals(value)) {
			return BASE_64;
		}

		if (FILE_ENTRY.getValue().equals(value)) {
			return FILE_ENTRY;
		}

		if (URL.getValue().equals(value)) {
			return URL;
		}

		throw new IllegalArgumentException("Invalid value " + value.getName());
	}

	public static ReturnType parseFirst(Set<Class<?>> values) {
		for (Class<?> value : values) {
			try {
				return parse(value);
			}
			catch (IllegalArgumentException iae) {
			}
		}

		throw new IllegalArgumentException("Invalid values " + values);
	}

	public ObjectValuePair<String, String> getReturnTypeAndValue(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		Class<?> clazz = this.getValue();

		if (this == FILE_ENTRY) {
			return new ObjectValuePair<>(
				clazz.getName(), String.valueOf(fileEntry.getFileEntryId()));
		}
		else if (this == URL) {
			return new ObjectValuePair<>(
				clazz.getName(),
				DLUtil.getImagePreviewURL(fileEntry, themeDisplay));
		}
		else {
			return new ObjectValuePair<>(clazz.getName(), StringPool.BLANK);
		}
	}

	public Class<?> getValue() {
		return _value;
	}

	private ReturnType(Class<?> value) {
		_value = value;
	}

	private final Class<?> _value;

}