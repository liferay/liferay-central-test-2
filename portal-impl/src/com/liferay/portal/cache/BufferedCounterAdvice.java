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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cache.BufferedCounter;
import com.liferay.portal.kernel.concurrent.BatchablePipe;
import com.liferay.portal.kernel.counter.Counter;
import com.liferay.portal.kernel.counter.IncreasableCounterEntry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.SerialDestination;
import com.liferay.portal.kernel.util.MethodTargetClassKey;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

/**
 * <a href="BufferedCounterAdvice.java.html"><b><i>View Source</i></b></a>
 *
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

		Method method = methodTargetClassKey.getMethod();

		Object[] args = methodInvocation.getArguments();

		if (args == null || args.length != 2) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"BufferedCounter annotation on method " + method.getName() +
						" with invalid number of parameters (requires 2).");
			}

			annotations.put(methodTargetClassKey, _nullBufferedCounter);

			return null;
		}

		if (!(args[1] instanceof Counter<?>)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"BufferedCounter annotation on method " + method.getName() +
						" without a second parameter of type Counter.");
			}

			annotations.put(methodTargetClassKey, _nullBufferedCounter);

			return null;
		}

		IncreasableCounterEntry entry = new IncreasableCounterEntry(
			_getService(methodInvocation),
			_getUpdateMethod(methodInvocation, annotation),
			args[0],
			(Counter<?>)args[1]);

		_pipe.put(entry);

		_invokeUpdate();

		return null;
	}

	@Override
	public Class<BufferedCounter> getAnnotationClass() {
		return BufferedCounter.class;
	}

	@Override
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

			String key = entry.getKey();

			if (key.endsWith("none")) {
				return;
			}

			String serviceName = entry.getService();

			Object service = PortalBeanLocatorUtil.locate(serviceName);

			Object [] args = new Object[] {
				entry.getId(),
				entry.getValue()
			};

			try {
				Method realUpdateMethod = service.getClass().getMethod(
					entry.getUpdateMethod().getName(),
					entry.getUpdateMethod().getParameterTypes());

				realUpdateMethod.invoke(service, args);
			} catch (Exception e) {
				_log.error("Cannot write buffered counter " +
					"value to the database", e);
			}
		}

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

	private String _getService(MethodInvocation methodInvocation) {

		String serviceName = methodInvocation.getThis().getClass().getName();

		serviceName = StringUtil.replace(serviceName, ".impl.",".");
		serviceName = StringUtil.replaceLast(serviceName, "Impl", "");

		return serviceName;
	}

	private Method _getUpdateMethod(
			MethodInvocation methodInvocation, BufferedCounter annotation)
		throws NoSuchMethodException {

		String methodName = methodInvocation.getMethod().getName();

		String updateMethodName = annotation.flushMethod();

		if ("".equals(updateMethodName)) {
			if (methodName.startsWith("increment")) {
				updateMethodName = "update" + methodName.substring(9);
			}
		}

		Class<?> targetClass = methodInvocation.getThis().getClass();

		return targetClass.getDeclaredMethod(
			updateMethodName, methodInvocation.getMethod().getParameterTypes());
	}

	private static String _INCREASE_COUNTER_MESSAGE_DESTINATION =
		"liferay/increase_counter_message_destination";

	private static BufferedCounter _nullBufferedCounter =
		new BufferedCounter() {

			public Class<? extends Annotation> annotationType() {
				return BufferedCounter.class;
			}

			public String flushMethod() {
				return "";
			}
		};

	private static BatchablePipe<String, Counter<?>> _pipe =
		new BatchablePipe<String, Counter<?>>();

	private static Log _log = LogFactoryUtil.getLog(
		BufferedCounterAdvice.class);

}