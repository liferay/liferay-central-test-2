/*
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

package com.liferay.portal.reflect;

import com.liferay.portal.kernel.messaging.sender.SingleDestinationSynchronousMessageSender;
import com.liferay.portal.kernel.proxy.BaseServiceProxy;
import com.liferay.portal.kernel.proxy.ServiceRequest;
import com.liferay.portal.kernel.proxy.ServiceResponseContainer;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * <a href="ServiceProxyAdvice.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class ServiceProxyAdvice implements MethodInterceptor {

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		return doInvoke(methodInvocation);
	}

	protected ServiceRequest createServiceRequest(
			MethodInvocation methodInvocation)
	throws Exception {

		return new ServiceRequest(
				methodInvocation.getMethod(), methodInvocation.getArguments());
	}

	protected Object doInvoke(MethodInvocation methodInvocation)
			throws Exception {

		ServiceRequest serviceRequest = createServiceRequest(methodInvocation);

		BaseServiceProxy baseServiceProxy =
				(BaseServiceProxy) methodInvocation.getThis();

		SingleDestinationSynchronousMessageSender messageSender =
				baseServiceProxy
						.getSingleDestinationSynchronousMessageSender();

		ServiceResponseContainer serviceResponseContainer =
				(ServiceResponseContainer) messageSender.send(serviceRequest);

		if (serviceResponseContainer.hasError()) {
			throw serviceResponseContainer.getException();
		}
		else {
			return serviceResponseContainer.getResult();
		}
	}

}
