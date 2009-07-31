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

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.liferay.portal.kernel.workflow.UserCredential;
import com.liferay.portal.kernel.workflow.WorkflowException;

/**
 * <a href="BaseRequest.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * The base request acts as the basic implementation of a workflow API request
 * transported through the message bus. It contains the {@link Method} to be
 * invoked on its target as well as the arguments needed for the invocation.
 * </p>
 *
 * <p>
 * Additionally, most methods provide a {@link UserCredential} representing the
 * calling user, some of its attributes as well as its role set to avoid the
 * underlying adapter or workflow engine to call back the portal to get those
 * information. Every method most likely being transitional (not read only)
 * should provide its calling user.
 * </p>
 *
 * <p>
 * The calling user (if it was passed on) will be made available through {@link
 * WorkflowCallingUser#getCallingUserCredential()} before the method is invoked
 * and is immediately removed, if the method was finished.
 * </p>
 *
 * @author Shuyang Zhou
 * @author Micha Kiener
 */
public class BaseRequest implements Serializable {

	/**
	 * Creates a new base request object by providing a method and its arguments
	 * needed as well as an optional credential of the calling user.
	 * 
	 * @param method the method to be invoked through this request
	 * @param callingUserCredential the optional credential of the calling user,
	 *            if any, <code>null</code>, if not available
	 * @param args the arguments to be used for the method being invoked in the
	 *            same order as the signature of the method
	 */
	protected BaseRequest(
		Method method, UserCredential callingUserCredential, Object... args) {
		_method = method;
		_callingUserCredential = callingUserCredential;
		_args = args;
	}

	/**
	 * Invokes the method being submitted by the request through reflection and
	 * returns its value. Before the method is being invoked, the calling user
	 * credential is attached, if available, and after the invocation removed
	 * again through a thread local.
	 * 
	 * @param implObject the object to invoke the method on
	 * @return the return value as being provided by the method invocation
	 * @throws WorkflowException is thrown, if any exception occurred within the
	 *             invocation
	 */
	public Object execute(Object implObject)
		throws WorkflowException {
		try {
			// attach the calling user credential, if available
			WorkflowCallingUser.setCallingUserCredential(
				_callingUserCredential);
			return _method.invoke(implObject, _args);
		}
		catch (Exception ex) {
			throw new WorkflowException(ex);
		}
		finally {
			// always make sure to remove the attached user credential as this
			// thread might be reused
			WorkflowCallingUser.setCallingUserCredential(null);
		}
	}

	/**
	 * @return the credential of the calling user, if any provided by the
	 *		   request, <code>null</code> otherwise
	 */
	public UserCredential getCallingUserCredential() {
		return _callingUserCredential;
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
	private UserCredential _callingUserCredential;
	private Method _method;

}