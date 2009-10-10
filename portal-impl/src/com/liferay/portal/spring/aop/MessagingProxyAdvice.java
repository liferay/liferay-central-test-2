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

package com.liferay.portal.spring.aop;

import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.messaging.proxy.ProxyRequest;
import com.liferay.portal.kernel.messaging.proxy.ProxyResponse;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationSynchronousMessageSender;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * <a href="MessagingProxyAdvice.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class MessagingProxyAdvice {

	public Object invoke(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {
		ProxyRequest proxyRequest = createProxyRequest(proceedingJoinPoint);
		BaseProxyBean baseProxyBean =
			(BaseProxyBean)proceedingJoinPoint.getTarget();

		if (proxyRequest.isSynchronous()) {
			return doInvokeSynchronous(proxyRequest, baseProxyBean);
		}
		else {
			doInvokeAsynchronous(proxyRequest, baseProxyBean);

			return null;
		}
	}

	protected ProxyRequest createProxyRequest(
		ProceedingJoinPoint proceedingJoinPoint) throws Exception {

		MethodSignature methodSignature =
			(MethodSignature)proceedingJoinPoint.getSignature();
		return new ProxyRequest(
			methodSignature.getMethod(), proceedingJoinPoint.getArgs());
	}

	protected void doInvokeAsynchronous(
		ProxyRequest proxyRequest, BaseProxyBean baseProxyBean) {

		SingleDestinationMessageSender messageSender =
			baseProxyBean.getSingleDestinationMessageSender();

		if (messageSender == null) {
			throw new IllegalStateException(
				"Asynchronous message sender was not configured properly for " +
					baseProxyBean.getClass().getName());
		}

		messageSender.send(proxyRequest);
	}

	protected Object doInvokeSynchronous(
			ProxyRequest proxyRequest, BaseProxyBean baseProxyBean)
		throws Exception {

		SingleDestinationSynchronousMessageSender messageSender =
			baseProxyBean.getSingleDestinationSynchronousMessageSender();

		if (messageSender == null) {
			throw new IllegalStateException(
				"Synchronous message sender was not configured properly for " +
					baseProxyBean.getClass().getName());
		}

		ProxyResponse proxyResponse = (ProxyResponse)messageSender.send(
			proxyRequest);

		if (proxyResponse.hasError()) {
			throw proxyResponse.getException();
		}
		else {
			return proxyResponse.getResult();
		}
	}

}