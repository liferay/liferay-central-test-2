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

package com.liferay.portal.messaging.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.messaging.proxy.ProxyRequest;
import com.liferay.portal.kernel.messaging.proxy.ProxyResponse;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationSynchronousMessageSender;

/**
 * <a href="BaseProxyAdvice.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * A base proxy implementation to be used as an advice weaved by Spring AOP as a
 * method injection whereas the method and its arguments are wrapped into a
 * request to be sent over the message bus. If there is a special way of
 * wrapping it, just overwrite the {@link #createProxyRequest(MethodInvocation)}
 * method and return your own instance of the request.
 * </p>
 * 
 * @author Micha Kiener
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class BaseProxyAdvice implements MethodInterceptor {

	/**
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		return doInvoke(methodInvocation);
	}

	/**
	 * @param methodInvocation
	 * @return
	 * @throws Exception
	 */
	protected ProxyRequest createProxyRequest(MethodInvocation methodInvocation)
		throws Exception {

		return new ProxyRequest(
			methodInvocation.getMethod(), methodInvocation.getArguments());
	}

	/**
	 * Invoked by {@link #invoke(MethodInvocation)} in order to intercept the
	 * method being weaved in. It will create the proxy request and invoke it
	 * either synchronous or asynchronous, depending on its implementation.
	 * 
	 * @param methodInvocation the invocation information being intercepted
	 * @return the return value, if the method was invoked synchronous or
	 *         <code>null</code> for asynchronous invocation
	 * @throws Exception is just rethrown from original invocation
	 */
	protected Object doInvoke(MethodInvocation methodInvocation)
		throws Exception {

		ProxyRequest proxyRequest = createProxyRequest(methodInvocation);
		BaseProxyBean baseProxyBean = (BaseProxyBean)methodInvocation.getThis();

		if (proxyRequest.isSynchronous()) {
			return doInvokeSynchronous(proxyRequest, baseProxyBean);
		}
		else {
			doInvokeAsynchronous(proxyRequest, baseProxyBean);
			return null;
		}
	}

	/**
	 * If the proxy should be invoked asynchronous, this method is called to
	 * send it over the message bus asynchronously.
	 * 
	 * @param proxyRequest the request to be sent containing the wrapped method
	 *            and arguments
	 * @param baseProxyBean the target object where this advice was weaved into
	 */
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

	/**
	 * If the proxy should be invoked synchronously, this method is called to
	 * send it over the message bus and to wait for the answer to be return back
	 * to the invoker as the result.
	 * 
	 * @param proxyRequest the request to be sent containing the wrapped method
	 *            and arguments
	 * @param baseProxyBean the target object where this advice was weaved into
	 * @return the result object being returned by the synchronous message
	 *         sender
	 * @throws Exception is either thrown if there was an error while sending
	 *             the request over the message bus or it is rethrown, if the
	 *             invocation originally threw an exception
	 */
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