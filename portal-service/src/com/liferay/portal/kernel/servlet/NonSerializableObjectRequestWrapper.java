/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Igor Spasic
 */
public class NonSerializableObjectRequestWrapper extends
	HttpServletRequestWrapper {

	public NonSerializableObjectRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public Object getAttribute(String name) {
		Object object = super.getAttribute(name);

		object = NonSerializableObjectHandler.getValue(object);

		return object;
	}

	@Override
	public void setAttribute(String name, Object object) {
		object = new NonSerializableObjectHandler(object);

		super.setAttribute(name, object);
	}

}