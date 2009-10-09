/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;

/**
 * <a href="ProxyRequest.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * The proxy request is used to wrap a method and its arguments to be sent over
 * the message bus. Its execution method ({@link #execute(Object)}) uses the
 * wrapped method and arguments and returns back the return value.
 * </p>
 * 
 * @author Micha Kiener
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class ProxyRequest implements Serializable {

	/**
	 * Constructor for a proxy request taking the method and an array of
	 * arguments needed to execute the method.
	 * 
	 * @param method the reflected method to be wrapped by this proxy
	 * @param arguments the arguments needed to execute the method on the target
	 *            object, can be <code>null</code>, if the method does not need
	 *            any arguments
	 * @throws Exception is thrown, if inspecting the method or initializing the
	 *             proxy failed
	 */
	public ProxyRequest(Method method, Object[] arguments) throws Exception {
		Class<?>[] argumentTypes = method.getParameterTypes();

		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i] == null) {
				arguments[i] = new NullWrapper(argumentTypes[i].getName());
			}
		}

		_methodWrapper = new MethodWrapper(
			method.getDeclaringClass().getName(), method.getName(), arguments);

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

	/**
	 * Executes the method wrapped by this proxy on the given target object and
	 * returns the value being returned by the method.
	 * 
	 * @param object the target object to execute the method on
	 * @return the value returned by executing the method on the target object
	 * @throws Exception is thrown if either the execution failed or if the
	 *             method being invoked threw an exception
	 */
	public Object execute(Object object) throws Exception {
		try {
			return MethodInvoker.invoke(_methodWrapper, object);
		}
		catch (InvocationTargetException e) {
			throw new Exception(e.getTargetException());
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