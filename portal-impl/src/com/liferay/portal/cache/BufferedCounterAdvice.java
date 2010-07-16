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

package com.liferay.portal.cache;

import com.liferay.portal.kernel.cache.BufferedCounter;
import com.liferay.portal.kernel.concurrent.BatchablePipe;
import com.liferay.portal.kernel.counter.Counter;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.SerialDestination;
import com.liferay.portal.kernel.util.MethodTargetClassKey;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;

import edu.emory.mathcs.backport.java.util.Arrays;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Zsolt Berentey
 */
public class BufferedCounterAdvice
	extends AnnotationChainableMethodAdvice<BufferedCounter> {

	public Object before(final MethodInvocation methodInvocation)
		throws Throwable {

		MethodTargetClassKey methodTargetClassKey = buildMethodTargetClassKey(
			methodInvocation);

		BufferedCounter annotation = findAnnotation(methodTargetClassKey);

		if (annotation == _nullBufferedCounter) {
			return null;
		}

		Object[] args = methodInvocation.getArguments();

		Object value = args[args.length - 1];

		Object key = StringUtil.merge(
			Arrays.copyOf(args, args.length - 1), StringPool.POUND);

		Counter<?> counter = _createCounter(annotation.counterClass(), value);

		IncreasableCounterEntry entry = new IncreasableCounterEntry(
			nextMethodInterceptor,
			methodInvocation,
			key,
			counter);

		_pipe.put(entry);

		_invokeUpdate();

		return nullResult;
	}

	public Class<BufferedCounter> getAnnotationClass() {
		return BufferedCounter.class;
	}

	public BufferedCounter getNullAnnotation() {
		return _nullBufferedCounter;
	}

	private static class IncreaseCounterMessageListener
		implements MessageListener {

		@SuppressWarnings("unchecked")
		public void receive(Message message) {

			BatchablePipe<String, Counter<?>> pipe =
				(BatchablePipe<String, Counter<?>>)message.getPayload();

			IncreasableCounterEntry entry =
				(IncreasableCounterEntry)pipe.take();

			if (entry == null) {
				return;
			}

			MethodInvocation methodInvocation = entry.getMethodInvocation();

			_deserializeMethodArguments(methodInvocation, entry);

			try {
				entry.getNextInterceptor().invoke(methodInvocation);
			}
			catch (Throwable t) {
				_log.error("Cannot write buffered counter " +
					"value to the database", t);
			}
		}

		private Object[] _deserializeMethodArguments(
			MethodInvocation methodInvocation, IncreasableCounterEntry entry) {

			Object[] args = methodInvocation.getArguments();

			Class<?>[] parameterTypes =
				methodInvocation.getMethod().getParameterTypes();

			String[] params = StringUtil.split(
				entry.getId().toString(), StringPool.POUND);

			int index = 0;

			for (String param : params) {
				if ("null".equals(param)) {
					args[index++] = null;
					continue;
				}

				String paramType = parameterTypes[index].getSimpleName();

				if (paramType.equalsIgnoreCase("integer")) {
					args[index++] = Integer.valueOf(param);
				}
				else if (paramType.equalsIgnoreCase("long")) {
					args[index++] = Long.valueOf(param);
				}
				else if (paramType.equalsIgnoreCase("double")) {
					args[index++] = Double.valueOf(param);
				}
				else if (paramType.equalsIgnoreCase("string")) {
					args[index++] = param;
				}
			}

			args[index] = entry.getValue().getValue();

			return args;
		}
	}

	private Counter<?> _createCounter(String className, Object value)
		throws SystemException {

		Counter<?> counter;

		try {
			Class<?> clazz = Class.forName(className);

			Constructor<?>[] constructors = clazz.getDeclaredConstructors();

			for (Constructor<?> c : constructors) {
				if (c.getParameterTypes().length == 1) {
					counter = (Counter<?>)c.newInstance(value);
					return counter;
				}
			}
		}
		catch (Exception cnfe) {
			throw new SystemException(cnfe);
		}

		throw new SystemException(
			className + " must declare a constructor with exactly one " +
			"argument to be used as a counter!");

	}

	private void _invokeUpdate() {
		if (!MessageBusUtil.hasMessageListener(
			_INCREASE_COUNTER_MESSAGE_DESTINATION)) {

			SerialDestination destination = new SerialDestination();

			destination.setName(_INCREASE_COUNTER_MESSAGE_DESTINATION);
			destination.setWorkersCoreSize(1);
			destination.setWorkersMaxSize(1);

			MessageBusUtil.addDestination(destination);

			destination.open();

			MessageBusUtil.registerMessageListener(
				_INCREASE_COUNTER_MESSAGE_DESTINATION,
				new IncreaseCounterMessageListener());
		}

		Message message = new Message();

		message.setPayload(_pipe);

		MessageBusUtil.sendMessage(
			_INCREASE_COUNTER_MESSAGE_DESTINATION, message);
	}

	private static String _INCREASE_COUNTER_MESSAGE_DESTINATION =
		"liferay/increase_counter_message_destination";

	private static BufferedCounter _nullBufferedCounter =
		new BufferedCounter() {

			public Class<? extends Annotation> annotationType() {
				return BufferedCounter.class;
			}

			public String counterClass() {
				return "com.liferay.portal.kernel.counter.NumberCounter";
			}
		};

	private static BatchablePipe<String, Counter<?>> _pipe =
		new BatchablePipe<String, Counter<?>>();

	private static Log _log = LogFactoryUtil.getLog(
		BufferedCounterAdvice.class);

}