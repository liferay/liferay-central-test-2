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

import java.util.Arrays;

import org.aopalliance.intercept.MethodInvocation;

/**
 * <a href="ScriptingRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ScriptingRequest implements Serializable {

	public ScriptingRequest(MethodInvocation invocation) {
		Method method=invocation.getMethod();
		_hasReturnValue=!void.class.equals(method.getReturnType());
		Class<?>[] argTypes = invocation.getMethod().getParameterTypes();
		Object[] args = invocation.getArguments();
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) {
				args[i] = new NullWrapper(argTypes[i].getName());
			}
		}
		_methodWrapper = new MethodWrapper(
			invocation.getThis().getClass().getName(), method.getName(), args);
	}

	public Object execute(Object implObject)
		throws ScriptingException {
		try {
			Object result = MethodInvoker.invoke(_methodWrapper,implObject);
			return result;
		}
		catch (Exception ex) {
			throw new ScriptingException(ex);
		}
	}

	public boolean hasReturnValue(){
		return _hasReturnValue;
	}

	@Override
	public String toString() {
		return "ScriptingRequest[" +
			"class:"+_methodWrapper.getClassName()+
			", method:"+_methodWrapper.getMethodName()+
			", args:"+Arrays.toString(_methodWrapper.getArgs())+
			", hasReturnValue:"+_hasReturnValue+
			"]";
	}

	private boolean _hasReturnValue;
	private MethodWrapper _methodWrapper;

}