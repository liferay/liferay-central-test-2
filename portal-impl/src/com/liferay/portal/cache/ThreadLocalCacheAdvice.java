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

import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCachable;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.util.MethodTargetClassKey;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.aop.BaseChainableAroundMethodAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInvocation;

/**
 * <a href="ThreadLocalCacheAdvice.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ThreadLocalCacheAdvice extends BaseChainableAroundMethodAdvice {

	public void afterReturning(
			MethodInvocation methodInvocation, Object result)
		throws Throwable {

		MethodTargetClassKey methodTargetClassKey = _buildMethodTargetClassKey(
			methodInvocation);

		ThreadLocalCachable threadLocalCachable =
			_findThreadLocalCachableAnnotation(methodTargetClassKey);

		if (threadLocalCachable == _nullThreadLocalCacheable) {
			return;
		}

		ThreadLocalCache<Object> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				threadLocalCachable.scope(), methodTargetClassKey.toString());

		String cacheKey = _buildCacheKey(methodInvocation.getArguments());

		if (result == null) {
			threadLocalCache.put(cacheKey, _nullResult);
		}
		else {
			threadLocalCache.put(cacheKey, result);
		}
	}

	public Object before(MethodInvocation methodInvocation) throws Throwable {
		MethodTargetClassKey methodTargetClassKey = _buildMethodTargetClassKey(
			methodInvocation);

		ThreadLocalCachable threadLocalCachable =
			_findThreadLocalCachableAnnotation(methodTargetClassKey);

		if (threadLocalCachable == _nullThreadLocalCacheable) {
			return null;
		}

		ThreadLocalCache<?> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				threadLocalCachable.scope(), methodTargetClassKey.toString());

		String cacheKey = _buildCacheKey(methodInvocation.getArguments());

		Object value = threadLocalCache.get(cacheKey);

		if (value == _nullResult) {
			return null;
		}

		return value;
	}

	private String _buildCacheKey(Object[] arguments) {
		StringBundler sb = new StringBundler(arguments.length * 2 - 1);

		for (int i = 0; i < arguments.length; i++) {
			sb.append(String.valueOf(arguments[i]));

			if ((i + 1) < arguments.length) {
				sb.append(StringPool.POUND);
			}
		}

		return sb.toString();
	}

	private MethodTargetClassKey _buildMethodTargetClassKey(
		MethodInvocation methodInvocation) {

		Method method = methodInvocation.getMethod();

		Class<?> targetClass = null;

		Object thisObject = methodInvocation.getThis();

		if (thisObject != null) {
			targetClass = thisObject.getClass();
		}

		return new MethodTargetClassKey(method, targetClass);
	}

	private ThreadLocalCachable _findThreadLocalCachableAnnotation(
		MethodTargetClassKey methodTargetClassKey){

		ThreadLocalCachable threadLocalCachable = _threadLocalCachables.get(
			methodTargetClassKey);

		if (threadLocalCachable != null) {
			return threadLocalCachable;
		}

		Method method = methodTargetClassKey.getMethod();
		Class<?> targetClass = methodTargetClassKey.getTargetClass();

		Method targetMethod = null;

		if (targetClass != null) {
			try {
				targetMethod = targetClass.getDeclaredMethod(
					method.getName(), method.getParameterTypes());
			}
			catch (Throwable t) {
			}
		}

		if (targetMethod != null) {
			threadLocalCachable = targetMethod.getAnnotation(
				ThreadLocalCachable.class);
		}

		if (threadLocalCachable == null) {
			threadLocalCachable = method.getAnnotation(
				ThreadLocalCachable.class);
		}

		if (threadLocalCachable == null) {
			threadLocalCachable = _nullThreadLocalCacheable;
		}

		_threadLocalCachables.put(methodTargetClassKey, threadLocalCachable);

		return threadLocalCachable;
	}

	private static Object _nullResult = new Object();

	private static ThreadLocalCachable _nullThreadLocalCacheable =
		new ThreadLocalCachable() {

			public Class<? extends Annotation> annotationType() {
				return ThreadLocalCachable.class;
			}

			public Lifecycle scope() {
				return null;
			}

		};

	private Map<MethodTargetClassKey, ThreadLocalCachable>
		_threadLocalCachables =
			new ConcurrentHashMap<MethodTargetClassKey, ThreadLocalCachable>();

}