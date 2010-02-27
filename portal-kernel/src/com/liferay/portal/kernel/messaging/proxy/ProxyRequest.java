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

package com.liferay.portal.kernel.messaging.proxy;

import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;

import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <a href="ProxyRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Micha Kiener
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class ProxyRequest implements Serializable {

	public ProxyRequest(Method method, Object[] arguments) throws Exception {
		Class<?>[] argumentTypes = method.getParameterTypes();

		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i] == null) {
				arguments[i] = new NullWrapper(argumentTypes[i].getName());
			}
		}

		_methodWrapper = new MethodWrapper(method, arguments);

		_hasReturnValue = false;

		if (method.getReturnType() != Void.TYPE) {
			_hasReturnValue = true;
		}

		MessagingProxy messagingProxy = method.getAnnotation(
			MessagingProxy.class);

		if (messagingProxy == null) {
			messagingProxy = method.getDeclaringClass().getAnnotation(
				MessagingProxy.class);
		}

		if ((messagingProxy != null) &&
			(messagingProxy.mode().equals(ProxyMode.SYNC))) {

			_synchronous = true;
		}
	}

	public Object execute(Object object) throws Exception {
		try {
			return MethodInvoker.invoke(_methodWrapper, object);
		}
		catch (InvocationTargetException ite) {
			Throwable t = ite.getCause();

			if (t instanceof Exception) {
				throw (Exception)t;
			}
			else {
				throw new Exception(t);
			}
		}
	}

	public MethodWrapper getMethodWrapper() {
		return _methodWrapper;
	}

	public boolean hasReturnValue() {
		return _hasReturnValue;
	}

	public boolean isSynchronous() {
		return _synchronous;
	}

	private boolean _hasReturnValue;
	private MethodWrapper _methodWrapper;
	private boolean _synchronous;

}