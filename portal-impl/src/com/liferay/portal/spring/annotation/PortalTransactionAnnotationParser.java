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

package com.liferay.portal.spring.annotation;

import com.liferay.portal.kernel.annotation.Transactional;
import com.liferay.portal.spring.transaction.TransactionAttributeBuilder;

import java.io.Serializable;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.transaction.annotation.TransactionAnnotationParser;
import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * @author	   Michael Young
 * @author	   Shuyang Zhou
 * @deprecated
 */
public class PortalTransactionAnnotationParser
	implements TransactionAnnotationParser, Serializable {

	public TransactionAttribute parseTransactionAnnotation(
		AnnotatedElement annotatedElement) {

		Transactional transactional = null;

		Queue<Class<?>> candidateQueue = new LinkedList<Class<?>>();

		if (annotatedElement instanceof Method) {
			Method method = (Method)annotatedElement;

			candidateQueue.offer(method.getDeclaringClass());

			transactional = _deepSearchMethods(method, candidateQueue);
		}
		else {
			candidateQueue.offer((Class<?>)annotatedElement);

			transactional = _deepSearchTypes(candidateQueue);
		}

		return TransactionAttributeBuilder.build(transactional);
	}

	private Transactional _deepSearchMethods(
		Method method, Queue<Class<?>> candidateQueue) {

		Transactional transactional = method.getAnnotation(Transactional.class);

		if (transactional != null) {
			return transactional;
		}

		if (candidateQueue.isEmpty()) {
			return null;
		}

		Class<?> clazz = candidateQueue.poll();

		if (method.getDeclaringClass() != clazz) {
			try {
				Method specificMethod = clazz.getDeclaredMethod(
					method.getName(), method.getParameterTypes());

				transactional = specificMethod.getAnnotation(
					Transactional.class);

				if (transactional != null) {
					return transactional;
				}
			}
			catch (Exception e) {
			}
		}

		transactional = clazz.getAnnotation(Transactional.class);

		if (transactional == null) {
			_queueSuperTypes(clazz, candidateQueue);

			return _deepSearchMethods(method, candidateQueue);
		}
		else {
			return transactional;
		}
	}

	private Transactional _deepSearchTypes(Queue<Class<?>> candidateQueue) {
		if (candidateQueue.isEmpty()) {
			return null;
		}

		Class<?> clazz = candidateQueue.poll();

		Transactional transactional = clazz.getAnnotation(Transactional.class);

		if (transactional == null) {
			_queueSuperTypes(clazz, candidateQueue);

			return _deepSearchTypes(candidateQueue);
		}
		else {
			return transactional;
		}
	}

	private void _queueSuperTypes(
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