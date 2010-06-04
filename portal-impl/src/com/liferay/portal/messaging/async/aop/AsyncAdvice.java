/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.messaging.async.aop;

import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.async.annotation.Async;
import com.liferay.portal.kernel.util.MethodTargetClassKey;
import com.liferay.portal.spring.aop.BaseAnnotationChainableAroundMethodAdvice;

import java.lang.annotation.Annotation;

import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;

/**
 * <a href="AsyncAdvice.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class AsyncAdvice
	extends BaseAnnotationChainableAroundMethodAdvice<Async> {

	public Object before(final MethodInvocation methodInvocation)
		throws Throwable {
		MethodTargetClassKey methodTargetClassKey = buildMethodTargetClassKey(
			methodInvocation);
		Async async = findAnnotation(methodTargetClassKey);

		if (async == _nullAsync) {
			return null;
		}

		String destination =
			_destinations.get(methodTargetClassKey.getTargetClass());
		if (destination == null) {
			destination = _defaultDestination;
		}

		MessageBusUtil.sendMessage(destination, new Runnable(){

			public void run() {
				try {
					nextMethodInterceptor.invoke(methodInvocation);
				} catch (Throwable ex) {
					throw new RuntimeException(ex);
				}
			}

			public String toString() {
				return methodInvocation.toString();
			}
		});

		return discardResult;

	}

	public Class<Async> getAnnotationClass() {
		return Async.class;
	}

	public String getDefaultDestination() {
		return _defaultDestination;
	}

	public Async getNullAnnotation() {
		return _nullAsync;
	}

	public void setDefaultDestination(String defaultDestination) {
		_defaultDestination = defaultDestination;
	}

	public void setDestinations(Map<Class<?>, String> destinations) {
		_destinations = destinations;
	}

	private static Async _nullAsync =
		new Async() {

			public Class<? extends Annotation> annotationType() {
				return Async.class;
			}

		};

	private String _defaultDestination;
	private Map<Class<?>, String> _destinations;

}