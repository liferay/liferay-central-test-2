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

package com.liferay.portal.increment;
import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.concurrent.BatchablePipe;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.Increment;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.MethodTargetClassKey;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Zsolt Berentey
 * @author Shuyang Zhou
 */
public class BufferedIncrementAdvice
	extends AnnotationChainableMethodAdvice<BufferedIncrement> {

	public Object before(final MethodInvocation methodInvocation)
		throws Throwable {

		MethodTargetClassKey methodTargetClassKey = buildMethodTargetClassKey(
			methodInvocation);

		BufferedIncrement annotation = findAnnotation(methodTargetClassKey);

		if (annotation == _nullBufferedIncrement) {
			return null;
		}

		Object[] args = methodInvocation.getArguments();

		Object value = args[args.length - 1];

		CacheKeyGenerator cacheKeyGenerator =
			CacheKeyGeneratorUtil.getCacheKeyGenerator(_cacheKeyGeneratorName);
		cacheKeyGenerator.append(methodTargetClassKey.toString());
		for(int i = 0; i < args.length - 1; i++) {
			cacheKeyGenerator.append(String.valueOf(args[i]));
		}
		String batchKey = cacheKeyGenerator.finish();

		Increment increment = _createCounter(annotation.incrementClass(),
			value);

		BufferedIncreasableEntry entry = new BufferedIncreasableEntry(
			nextMethodInterceptor,
			methodInvocation,
			batchKey,
			increment);

		_batchablePipe.put(entry);

		MessageBusUtil.sendMessage(DestinationNames.BUFFERED_INCREMENT,
			_batchablePipe);

		return nullResult;
	}

	public BufferedIncrement getNullAnnotation() {
		return _nullBufferedIncrement;
	}

	private Increment<?> _createCounter(
			Class<? extends Increment> counterClass, Object value)
		throws SystemException {

		Class<?> valueClass = value.getClass();
		String key = counterClass.getName().concat(valueClass.getName());
		Constructor<? extends Increment> constructor =
			_incrementConstructorMap.get(key);
		try {
			if (constructor == null) {
				constructor = counterClass.getConstructor(valueClass);
				_incrementConstructorMap.put(key, constructor);
			}
			return constructor.newInstance(value);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

	}

	private static BatchablePipe<String, BufferedIncreasableEntry>
		_batchablePipe = new BatchablePipe<String, BufferedIncreasableEntry>();

	private static String _cacheKeyGeneratorName =
		BufferedIncrementAdvice.class.getName();

	private static Map<String, Constructor<? extends Increment>>
		_incrementConstructorMap =
			new ConcurrentHashMap<String, Constructor<? extends Increment>>();

	private static BufferedIncrement _nullBufferedIncrement =
		new BufferedIncrement() {

			public Class<? extends Annotation> annotationType() {
				return BufferedIncrement.class;
			}

			public Class<? extends Increment> incrementClass() {
				return null;
			}
		};

}