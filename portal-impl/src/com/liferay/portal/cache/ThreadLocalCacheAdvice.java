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

import com.liferay.portal.kernel.annotation.ThreadLocalCachable;
import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.aop.ChainabelAroundMethodAdviceAdapter;

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
public class ThreadLocalCacheAdvice extends ChainabelAroundMethodAdviceAdapter {

	public Object before(MethodInvocation methodInvocation) throws Throwable {

		String cacheName = _buildCacheName(methodInvocation);

		ThreadLocalCachable threadLocalCachable =
			_findThreadLocalCachableAnnotation(methodInvocation, cacheName);
		if (threadLocalCachable == _NULL_THREADLOCALCACHE) {
			return null;
		}

		ThreadLocalCache<?> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				threadLocalCachable.scope(), cacheName);
		String cacheKey = _buildCacheKey(methodInvocation.getArguments());
		Object cacheValue = threadLocalCache.get(cacheKey);
		if (cacheValue == _NULL_RESULT) {
			return null;
		}
		return cacheValue;
	}

	public void afterReturning(
		MethodInvocation methodInvocation, Object result) throws Throwable {
		String cacheName = _buildCacheName(methodInvocation);

		ThreadLocalCachable threadLocalCachable =
			_findThreadLocalCachableAnnotation(methodInvocation, cacheName);
		if (threadLocalCachable == _NULL_THREADLOCALCACHE) {
			return;
		}

		ThreadLocalCache<Object> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				threadLocalCachable.scope(), cacheName);
		String cacheKey = _buildCacheKey(methodInvocation.getArguments());
		if (result == null) {
			threadLocalCache.put(cacheKey, _NULL_RESULT);
		}
		else {
			threadLocalCache.put(cacheKey, result);
		}
	}

	private String _buildCacheName(MethodInvocation methodInvocation) {
		Method method = methodInvocation.getMethod();

		return method.getDeclaringClass().getName().concat(StringPool.PERIOD).
			concat(method.getName());
	}

	private String _buildCacheKey(Object[] args) {
		int length = args.length;

		StringBundler sb = new StringBundler(length * 2 - 1);

		for(int i = 0; i < length - 1; i++) {
			sb.append(String.valueOf(args[i]));
			sb.append(StringPool.POUND);
		}
		if (length > 1) {
			sb.append(String.valueOf(args[length - 1]));
		}

		return sb.toString();
	}

	private ThreadLocalCachable _findThreadLocalCachableAnnotation(
		MethodInvocation methodInvocation, String key){

		Method method = methodInvocation.getMethod();
		Object thisObject = methodInvocation.getThis();

		Class<?> targetClass = null;
		if (thisObject != null) {
			targetClass = thisObject.getClass();
		}

		ThreadLocalCachable threadLocalCachable = _annotationCache.get(key);
		if (threadLocalCachable != null) {
			return threadLocalCachable;
		}

		Method specificMethod = null;
		if (targetClass != null) {
			try{
				specificMethod = targetClass.getDeclaredMethod(method.getName(),
					method.getParameterTypes());
			}
			catch(Throwable t) {
			}
		}
		if (specificMethod != null) {
			threadLocalCachable =
				specificMethod.getAnnotation(ThreadLocalCachable.class);
		}

		if (threadLocalCachable == null) {
			threadLocalCachable =
				method.getAnnotation(ThreadLocalCachable.class);
		}

		if (threadLocalCachable == null) {
			threadLocalCachable = _NULL_THREADLOCALCACHE;
		}

		_annotationCache.put(key, threadLocalCachable);

		return threadLocalCachable;
	}

	private static final Object _NULL_RESULT = new Object();

	private static final ThreadLocalCachable _NULL_THREADLOCALCACHE =
		new ThreadLocalCachable() {

		public Lifecycle scope() {
			return null;
		}

		public Class<? extends Annotation> annotationType() {
			return ThreadLocalCachable.class;
		}
	};

	private Map<String, ThreadLocalCachable> _annotationCache =
		new ConcurrentHashMap<String, ThreadLocalCachable>();

}