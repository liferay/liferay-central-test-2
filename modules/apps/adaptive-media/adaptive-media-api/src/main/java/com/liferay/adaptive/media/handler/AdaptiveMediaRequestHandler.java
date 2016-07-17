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

package com.liferay.adaptive.media.handler;

import com.liferay.adaptive.media.processor.AdaptiveMedia;

import java.io.IOException;

import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public interface AdaptiveMediaRequestHandler<T> {

	public Optional<AdaptiveMedia<T>> handleRequest(HttpServletRequest request)
		throws IOException, ServletException;

}