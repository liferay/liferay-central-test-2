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

package com.liferay.portal.kernel.workflow;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.liferay.portal.kernel.messaging.proxy.ProxyRequest;

/**
 * <a href="WorkflowRequest.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * This proxy extends the generic {@link ProxyRequest} by adding an optional
 * {@link UserCredential} to the request being attached to the current thread
 * before invoking the method and finally being removed after the invocation.
 * The initialization of the proxy will inspect the method signature for having
 * the {@link CallingUserId} annotation on an argument to treat it as the
 * calling user id to request a user credential for it and pass it along with
 * the request to be attached to the thread before invoking the method.
 * </p>
 * 
 * @author Micha Kiener
 */
public class WorkflowRequest extends ProxyRequest {

	public WorkflowRequest(Method method, Object[] arguments) throws Exception {
		super(method, arguments);

		_method = method;
		_userCredential = inspectForCallingUserCredential();
	}

	/**
	 * Overwritten to attach the user credential passed along, if any available.
	 * 
	 * @see com.liferay.portal.kernel.messaging.proxy.ProxyRequest#execute(java.lang.Object)
	 */
	@Override
	public Object execute(Object object) throws WorkflowException {
		try {
			UserCredentialThreadLocal.setUserCredential(_userCredential);
			return super.execute(object);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
		finally {
			UserCredentialThreadLocal.setUserCredential(null);
		}
	}

	public UserCredential getUserCredential() {
		return _userCredential;
	}

	/**
	 * Inspects the parameter list of the given method for having the
	 * {@link CallingUserId} annotation on it to mark it as the id of the
	 * calling user which might be used to create a user credential for it which
	 * is passed along with this proxy to be attached to the current thread
	 * before invoking the method on the target object.
	 * 
	 * @return the user credential according to the calling user id, if
	 *         available
	 * @throws WorkflowException is thrown, if initialization of the credential
	 *             failed
	 */
	protected UserCredential inspectForCallingUserCredential()
		throws WorkflowException {

		Annotation[][] annotationsArray = _method.getParameterAnnotations();

		if ((annotationsArray == null) || (annotationsArray.length == 0)) {
			return null;
		}

		for (int i = 0; i < annotationsArray.length; i++) {
			Annotation[] annotations = annotationsArray[i];

			if ((annotations == null) || (annotations.length == 0)) {
				continue;
			}

			for (Annotation annotation : annotations) {
				if (CallingUserId.class.isAssignableFrom(
						annotation.annotationType())) {

					return UserCredentialFactoryUtil.createCredential(
						(Long)getMethodWrapper().getArguments()[i]);
				}
			}
		}

		return null;
	}

	private Method _method;
	private UserCredential _userCredential;

}