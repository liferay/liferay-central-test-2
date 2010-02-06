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

package com.liferay.portal.kernel.util;

import java.io.Serializable;

import java.lang.reflect.Method;

/**
 * <a href="MethodWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MethodWrapper implements Serializable {

	public MethodWrapper(String className, String methodName) {
		this(className, methodName, new Object[0]);
	}

	public MethodWrapper(String className, String methodName, Object argument) {
		this(className, methodName, new Object[] {argument});
	}

	public MethodWrapper(
		String className, String methodName, Object[] arguments) {

		_className = className;
		_methodName = methodName;
		_arguments = arguments;
	}

	public MethodWrapper(Method method, Object[] arguments) {
		this(method.getDeclaringClass().getName(), method.getName(), arguments);

		_argumentClassNames = new String[arguments.length];

		Class<?>[] parameterTypes = method.getParameterTypes();

		for (int i = 0; i < parameterTypes.length; i++) {
			_argumentClassNames[i] = parameterTypes[i].getName();
		}
	}

	public String getClassName() {
		return _className;
	}

	public String getMethodName() {
		return _methodName;
	}

	/**
	 * @deprecated Use <code>getArguments</code>.
	 */
	public Object[] getArgs() {
		return getArguments();
	}

	public String[] getArgumentClassNames() {
		return _argumentClassNames;
	}

	public Object[] getArguments() {
		Object[] arguments = new Object[_arguments.length];

		System.arraycopy(_arguments, 0, arguments, 0, _arguments.length);

		return arguments;
	}

	private String _className;
	private String _methodName;
	private String[] _argumentClassNames;
	private Object[] _arguments;

}