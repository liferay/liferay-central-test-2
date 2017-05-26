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

package com.liferay.adaptive.media;

/**
 * Set of functions that can be used for converting data into
 * {@link AdaptiveMediaAttribute} values.
 *
 * All the functions defined here should throw an {@link
 * AdaptiveMediaRuntimeException.AdaptiveMediaAttributeFormatException}
 * if it cannot convert the String.
 *
 * @author Alejandro Hernández
 *
 * @review
 */
public class AdaptiveMediaAttributeConverterUtil {

	public static Integer parseInt(String value)
		throws
			AdaptiveMediaRuntimeException.
				AdaptiveMediaAttributeFormatException {

		try {
			return Integer.parseInt(value);
		}
		catch (NumberFormatException nfe) {
			throw new AdaptiveMediaRuntimeException.
				AdaptiveMediaAttributeFormatException(nfe);
		}
	}

}