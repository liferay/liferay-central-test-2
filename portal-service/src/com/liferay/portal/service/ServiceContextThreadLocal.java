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

package com.liferay.portal.service;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * @author Michael C. Han
 */
public class ServiceContextThreadLocal {

	public static ServiceContext getServiceContext() {
		try {
			return _serviceContextThreadLocal.get().peek();
		}
		catch (EmptyStackException ese) {
			return null;
		}
	}

	public static ServiceContext popServiceContext() {
		try {
			return _serviceContextThreadLocal.get().pop();
		}
		catch (EmptyStackException ese) {
			return null;
		}
	}

	public static void pushServiceContext(ServiceContext serviceContext) {
		_serviceContextThreadLocal.get().push(serviceContext);
	}

	private static ThreadLocal<Stack<ServiceContext>> _serviceContextThreadLocal =
		new AutoResetThreadLocal<Stack<ServiceContext>>(
			ServiceContextThreadLocal.class + "._serviceContextThreadLocal",
			new Stack<ServiceContext>());

}