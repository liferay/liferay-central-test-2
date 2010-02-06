/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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