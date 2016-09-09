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

package com.liferay.adaptive.media.image.internal.util;

/**
 * @author Sergio González
 */
public class ImageInfo {

	public ImageInfo(String mimeType, long size) {
		_mimeType = mimeType;
		_size = size;
	}

	public String getMimeType() {
		return _mimeType;
	}

	public long getSize() {
		return _size;
	}

	private final String _mimeType;
	private final long _size;

}