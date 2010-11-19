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

package com.liferay.portal.kernel.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Shuyang Zhou
 */
public class AnnotationLocator {

	public static <T extends Annotation> T locate(
		Class<?> targetClass, Class<T> annotationClass) {
		Queue<Class<?>> candidateQueue = new LinkedList<Class<?>>();

		candidateQueue.offer(targetClass);

		return _deepSearchTypes(candidateQueue, annotationClass);
	}

	public static <T extends Annotation> T locate(
		Method method, Class<?> targetClass, Class<T> annotationClass) {
		Queue<Class<?>> candidateQueue = new LinkedList<Class<?>>();

		if (targetClass == null) {
			candidateQueue.offer(method.getDeclaringClass());
		}
		else {
			candidateQueue.offer(targetClass);
		}

		return _deepSearchMethods(method, candidateQueue, annotationClass);
	}

	private static <T extends Annotation> T _deepSearchMethods(
		Method method, Queue<Class<?>> candidateQueue,
		Class<T> annotationClass) {

		if (candidateQueue.isEmpty()) {
			return null;
		}

		T annotation = null;

		Class<?> clazz = candidateQueue.poll();

		try {
			Method specificMethod = clazz.getDeclaredMethod(
				method.getName(), method.getParameterTypes());

			annotation = specificMethod.getAnnotation(annotationClass);

			if (annotation != null) {
				return annotation;
			}
		}
		catch (Exception e) {
		}

		annotation = clazz.getAnnotation(annotationClass);

		if (annotation == null) {
			_queueSuperTypes(clazz, candidateQueue);

			return _deepSearchMethods(method, candidateQueue, annotationClass);
		}
		else {
			return annotation;
		}
	}

	private static <T extends Annotation> T _deepSearchTypes(
		Queue<Class<?>> candidateQueue, Class<T> annotationClass) {
		if (candidateQueue.isEmpty()) {
			return null;
		}

		Class<?> clazz = candidateQueue.poll();

		T annotation = clazz.getAnnotation(annotationClass);

		if (annotation == null) {
			_queueSuperTypes(clazz, candidateQueue);

			return _deepSearchTypes(candidateQueue, annotationClass);
		}
		else {
			return annotation;
		}
	}

	private static void _queueSuperTypes(
		Class<?> clazz, Queue<Class<?>> candidateQueue) {

		Class<?> supperClass = clazz.getSuperclass();

		if ((supperClass != null) && (supperClass != Object.class)) {
			candidateQueue.offer(supperClass);
		}

		Class<?>[] interfaces = clazz.getInterfaces();

		for (Class<?> inter : interfaces) {
			candidateQueue.offer(inter);
		}
	}

}