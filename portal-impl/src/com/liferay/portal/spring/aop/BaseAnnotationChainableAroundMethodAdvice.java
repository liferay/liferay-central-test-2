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

package com.liferay.portal.spring.aop;

import com.liferay.portal.kernel.util.MethodTargetClassKey;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInvocation;

/**
 * <a href="BaseAnnotationChainableAroundMethodAdvice.java.html"><b><i>
 * View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public abstract class BaseAnnotationChainableAroundMethodAdvice
	<T extends Annotation> extends ChainableAroundMethodAdvice {

	public void afterReturning(MethodInvocation methodInvocation, Object result)
		throws Throwable {
	}

	public void afterThrowing(
			MethodInvocation methodInvocation, Throwable throwable)
		throws Throwable {
	}

	public Object before(MethodInvocation methodInvocation) throws Throwable {
		return null;
	}

	public abstract Class<T> getAnnotationClass();

	public abstract T getNullAnnotation();

	protected MethodTargetClassKey buildMethodTargetClassKey(
		MethodInvocation methodInvocation) {

		Method method = methodInvocation.getMethod();

		Class<?> targetClass = null;

		Object thisObject = methodInvocation.getThis();

		if (thisObject != null) {
			targetClass = thisObject.getClass();
		}

		return new MethodTargetClassKey(method, targetClass);
	}

	protected T findAnnotation(MethodTargetClassKey methodTargetClassKey){

		T annotation = annotationCache.get(methodTargetClassKey);

		if (annotation != null) {
			return annotation;
		}

		Method method = methodTargetClassKey.getMethod();

		Method targetMethod = methodTargetClassKey.getTargetMethod();

		Class<T> annotationClass = getAnnotationClass();

		if (targetMethod != null) {
			annotation = targetMethod.getAnnotation(annotationClass);
		}

		if (annotation == null) {
			annotation = method.getAnnotation(annotationClass);
		}

		if (annotation == null) {
			annotation = getNullAnnotation();
		}

		annotationCache.put(methodTargetClassKey, annotation);

		return annotation;
	}

	protected Map<MethodTargetClassKey, T>
		annotationCache =
			new ConcurrentHashMap<MethodTargetClassKey, T>();

}