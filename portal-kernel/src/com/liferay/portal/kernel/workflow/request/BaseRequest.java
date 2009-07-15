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

package com.liferay.portal.kernel.workflow.request;

import com.liferay.portal.kernel.workflow.WorkflowException;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Arrays;

/**
 * <a href="BaseRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public class BaseRequest implements Serializable {

	protected BaseRequest(Method method, Object... args) {
		_method = method;
		_args = args;
	}

	public Object execute(Object implObject)
		throws WorkflowException {
		try {
			return _method.invoke(implObject, _args);
		}
		catch (Exception ex) {
			throw new WorkflowException(ex);
		}
	}

	public boolean hasReturnValue() {
		return _method.getReturnType() != Void.TYPE;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BaseRequest other = (BaseRequest) obj;
		if (this._method != other._method
			&& (this._method == null || !this._method.equals(other._method))) {
			return false;
		}
		if (!Arrays.deepEquals(this._args, other._args)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + (this._method != null ? this._method.hashCode() : 0);
		hash = 97 * hash + Arrays.deepHashCode(this._args);
		return hash;
	}

	@Override
	public String toString() {
		return "RequestClass:" + getClass() +
			"[Method:" + _method + ", " +
			"Args:" + _args + "]";
	}

	private Object[] _args;
	private Method _method;

}