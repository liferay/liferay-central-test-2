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

import com.liferay.portal.kernel.messaging.proxy.ProxyRequest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * <a href="WorkflowRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Micha Kiener
 */
public class WorkflowRequest extends ProxyRequest {

	public WorkflowRequest(Method method, Object[] arguments) throws Exception {
		super(method, arguments);

		_userCredential = inspectForCallingUserCredential();
	}

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

	public boolean hasReturnValue() {
		if (getMethod().getReturnType() != Void.TYPE) {
			return true;
		}
		else {
			return false;
		}
	}

	protected UserCredential inspectForCallingUserCredential()
		throws WorkflowException {

		Annotation[][] annotationsArray = getMethod().getParameterAnnotations();

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
						(Long)getArguments()[i]);
				}
			}
		}

		return null;
	}

	private UserCredential _userCredential;

}