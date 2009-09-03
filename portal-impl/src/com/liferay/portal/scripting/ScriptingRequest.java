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

package com.liferay.portal.scripting;

import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;

import java.io.Serializable;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

/**
 * <a href="ScriptingRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ScriptingRequest implements Serializable {

	public ScriptingRequest(MethodInvocation methodInvocation) {
		Method method = methodInvocation.getMethod();

		_hasReturnValue = true;

		if (method.getReturnType().equals("void")) {
			_hasReturnValue = false;
		}

		Class<?>[] parameterTypes = method.getParameterTypes();

		Object[] arguments = methodInvocation.getArguments();

		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i] == null) {
				arguments[i] = new NullWrapper(parameterTypes[i].getName());
			}
		}

		_methodWrapper = new MethodWrapper(
			methodInvocation.getThis().getClass().getName(), method.getName(),
			arguments);
	}

	public Object execute(Object object) throws ScriptingException {
		try {
			return MethodInvoker.invoke(_methodWrapper, object);
		}
		catch (Exception e) {
			throw new ScriptingException(e);
		}
	}

	public boolean hasReturnValue(){
		return _hasReturnValue;
	}

	private boolean _hasReturnValue;
	private MethodWrapper _methodWrapper;

}