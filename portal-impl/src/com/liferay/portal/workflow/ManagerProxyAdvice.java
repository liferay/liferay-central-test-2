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

package com.liferay.portal.workflow;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.workflow.WorkflowException;

/**
 * <a href="ManagerProxyAdvice.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * The advice being weaved into all methods of the proxy managers to use the
 * event bus transporting the method and arguments to be invoked on the final
 * target implementation.
 * </p>
 * 
 * @author Micha Kiener
 * 
 */
public class ManagerProxyAdvice implements MethodInterceptor {

	/**
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation invocation)
		throws Throwable {
		try {
			// create a request out of the invocation information to be
			// serialized through the event bus
			WorkflowRequest request = new WorkflowRequest(invocation);
			
			// send the message over the event bus, the listener will then
			// invoke it on the target and return the result
			WorkflowResultContainer response =
				(WorkflowResultContainer) ((BaseWorkflowProxy) invocation.getThis()).getMessageSender().send(
					request);
			if (response.hasError()) {
				throw response.getException();
			}
			else {
				return response.getResult();
			}
		}
		catch (MessageBusException ex) {
			throw new WorkflowException(
				"Unable to invoke workflow method.", ex);
		}
	}
}
