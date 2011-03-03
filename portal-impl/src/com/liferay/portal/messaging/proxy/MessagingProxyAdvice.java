/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.messaging.proxy;

import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.messaging.proxy.ProxyRequest;
import com.liferay.portal.kernel.messaging.proxy.ProxyResponse;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationSynchronousMessageSender;
import com.liferay.util.aspectj.AspectJUtil;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class MessagingProxyAdvice {

	public Object invoke(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		ProxyRequest proxyRequest = createProxyRequest(proceedingJoinPoint);

		BaseProxyBean baseProxyBean =
			(BaseProxyBean)proceedingJoinPoint.getTarget();

		if (proxyRequest.isSynchronous() ||
			BatchModelThreadLocal.isBatchModel()) {
			return doInvokeSynchronous(proxyRequest, baseProxyBean);
		}
		else {
			doInvokeAsynchronous(proxyRequest, baseProxyBean);

			return null;
		}
	}

	protected ProxyRequest createProxyRequest(
			ProceedingJoinPoint proceedingJoinPoint)
		throws Exception {

		return new ProxyRequest(
			AspectJUtil.getMethod(proceedingJoinPoint),
			proceedingJoinPoint.getArgs());
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

		if (proxyResponse == null) {
			return null;
		}
		else if (proxyResponse.hasError()) {
			throw proxyResponse.getException();
		}
		else {
			return proxyResponse.getResult();
		}
	}

}