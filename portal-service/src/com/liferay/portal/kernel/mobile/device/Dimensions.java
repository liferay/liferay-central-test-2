/*
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

package com.liferay.portal.kernel.mobile.device;

import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

/**
 * @author Michael C. Han
 */
public class Dimensions implements Serializable {

	public static final Dimensions UNKNOWN = new Dimensions(-1, -1);

	public Dimensions(float width, float height) {
		_width = width;
		_height = height;
	}

	public float getWidth() {
		return _width;
	}

	public float getHeight() {
		return _height;
	}

	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{width=");
		sb.append(getWidth());
		sb.append(", height=");
		sb.append(getHeight());
		sb.append("}");

		return sb.toString();
	}
	private float _width;
	private float _height;
}
