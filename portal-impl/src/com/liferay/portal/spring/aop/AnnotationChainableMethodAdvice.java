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
		Annotation[] annotationArray = annotations.get(methodTargetClassKey);

		if (annotationArray != null) {
			return getAnnotation(annotationArray);
		}

		Method method = methodTargetClassKey.getMethod();

		Method targetMethod = methodTargetClassKey.getTargetMethod();

		if (targetMethod != null) {
			annotationArray = targetMethod.getAnnotations();
		}

		if (annotationArray == null || annotationArray.length == 0) {
			annotationArray = method.getAnnotations();
		}

		if (annotationArray == null || annotationArray.length == 0) {
			annotationArray = emptyAnnotations;
		}

		annotations.put(methodTargetClassKey, annotationArray);

		return getAnnotation(annotationArray);
	}

	protected T getAnnotation(Annotation[] annotationArray) {
		for(Annotation annotation : annotationArray) {
			if (annotation.annotationType() == annotationType) {
				return (T) annotation;
			}
		}
		return nullAnnotation;
	}

	protected static Map<MethodTargetClassKey, Annotation[]> annotations =
		new ConcurrentHashMap<MethodTargetClassKey, Annotation[]>();

	protected static Annotation[] emptyAnnotations = new Annotation[0];

	protected T nullAnnotation = getNullAnnotation();
	protected Class<? extends Annotation> annotationType =
		nullAnnotation.annotationType();

}