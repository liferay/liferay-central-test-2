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
 * <a href="AnnotationChainableMethodAdvice.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public abstract class AnnotationChainableMethodAdvice<T extends Annotation>
	extends ChainableMethodAdvice {

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
		T annotation = _annotations.get(methodTargetClassKey);

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

		_annotations.put(methodTargetClassKey, annotation);

		return annotation;
	}

	protected Map<MethodTargetClassKey, T> _annotations =
		new ConcurrentHashMap<MethodTargetClassKey, T>();

}