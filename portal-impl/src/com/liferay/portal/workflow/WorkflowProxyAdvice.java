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

import org.aopalliance.intercept.MethodInvocation;

import com.liferay.portal.kernel.messaging.proxy.ProxyRequest;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowRequest;
import com.liferay.portal.messaging.proxy.BaseProxyAdvice;

/**
 * <a href="WorkflowProxyAdvice.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * The advice being injected (weaved) into the generic proxies to generically
 * wrap the method being invoked into a {@link WorkflowRequest} to be sent
 * through the message bus.
 * </p>
 * 
 * @author Micha Kiener
 */
public class WorkflowProxyAdvice extends BaseProxyAdvice {

	/**
	 * Overwritten in order to wrap a possible {@link Exception} being thrown
	 * into a {@link WorkflowException}.
	 * 
	 * @see com.liferay.portal.messaging.proxy.BaseProxyAdvice#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		try {
			return super.invoke(methodInvocation);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	/**
	 * Overwritten in order to create a new {@link WorkflowRequest} wrapping the
	 * method being adviced into a workflow request.
	 * 
	 * @see com.liferay.portal.messaging.proxy.BaseProxyAdvice#createProxyRequest(org.aopalliance.intercept.MethodInvocation)
	 */
	@Override
	protected ProxyRequest createProxyRequest(MethodInvocation methodInvocation)
		throws Exception {

		return new WorkflowRequest(
			methodInvocation.getMethod(), methodInvocation.getArguments());
	}

}