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

import com.liferay.portal.kernel.annotation.TransactionDefinition;
import com.liferay.portal.kernel.annotation.Transactional;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.transaction.annotation.TransactionAnnotationParser;
import org.springframework.transaction.interceptor.NoRollbackRuleAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
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

		//Transactional annotation = annotatedElement.getAnnotation(
		//	Transactional.class);

		Transactional annotation = null;

		Queue<Class<?>> candidateQueue = new LinkedList<Class<?>>();

		if (annotatedElement instanceof Method) {
			Method method = (Method)annotatedElement;

			candidateQueue.offer(method.getDeclaringClass());

			annotation = _deepSearchMethods(method, candidateQueue);
		}
		else {
			candidateQueue.offer((Class<?>)annotatedElement);

			annotation = _deepSearchTypes(candidateQueue);
		}

		if (annotation == null) {
			return null;
		}

		RuleBasedTransactionAttribute ruleBasedTransactionAttribute =
			new RuleBasedTransactionAttribute();

		int isolationLevel = annotation.isolation().value();

		if (isolationLevel == TransactionDefinition.ISOLATION_PORTAL) {
			ruleBasedTransactionAttribute.setIsolationLevel(
				PropsValues.TRANSACTION_ISOLATION_PORTAL);
		}
		else {
			ruleBasedTransactionAttribute.setIsolationLevel(isolationLevel);
		}

		ruleBasedTransactionAttribute.setPropagationBehavior(
			annotation.propagation().value());
		ruleBasedTransactionAttribute.setReadOnly(annotation.readOnly());
		ruleBasedTransactionAttribute.setTimeout(annotation.timeout());

		List<RollbackRuleAttribute> rollBackAttributes =
			new ArrayList<RollbackRuleAttribute>();

		Class<?>[] rollbackFor = annotation.rollbackFor();

		for (int i = 0; i < rollbackFor.length; i++) {
			RollbackRuleAttribute rollbackRuleAttribute =
				new RollbackRuleAttribute(rollbackFor[i]);

			rollBackAttributes.add(rollbackRuleAttribute);
		}

		String[] rollbackForClassName = annotation.rollbackForClassName();

		for (int i = 0; i < rollbackForClassName.length; i++) {
			RollbackRuleAttribute rollbackRuleAttribute =
				new RollbackRuleAttribute(rollbackForClassName[i]);

			rollBackAttributes.add(rollbackRuleAttribute);
		}

		Class<?>[] noRollbackFor = annotation.noRollbackFor();

		for (int i = 0; i < noRollbackFor.length; ++i) {
			NoRollbackRuleAttribute noRollbackRuleAttribute =
				new NoRollbackRuleAttribute(noRollbackFor[i]);

			rollBackAttributes.add(noRollbackRuleAttribute);
		}

		String[] noRollbackForClassName = annotation.noRollbackForClassName();

		for (int i = 0; i < noRollbackForClassName.length; ++i) {
			NoRollbackRuleAttribute noRollbackRuleAttribute =
				new NoRollbackRuleAttribute(noRollbackForClassName[i]);

			rollBackAttributes.add(noRollbackRuleAttribute);
		}

		ruleBasedTransactionAttribute.getRollbackRules().addAll(
			rollBackAttributes);

		return ruleBasedTransactionAttribute;
	}

	private Transactional _deepSearchMethods(
		Method method, Queue<Class<?>> candidateQueue) {

		Transactional annotation = method.getAnnotation(Transactional.class);

		if (annotation != null) {
			return annotation;
		}

		if (candidateQueue.isEmpty()) {
			return null;
		}

		Class<?> clazz = candidateQueue.poll();

		if (method.getDeclaringClass() != clazz) {
			try {
				Method specificMethod = clazz.getDeclaredMethod(
					method.getName(), method.getParameterTypes());

				annotation = specificMethod.getAnnotation(Transactional.class);

				if (annotation != null) {
					return annotation;
				}
			}
			catch (Exception e) {
			}
		}

		annotation = clazz.getAnnotation(Transactional.class);

		if (annotation == null) {
			_queueSuperTypes(clazz, candidateQueue);

			return _deepSearchMethods(method, candidateQueue);
		}
		else {
			return annotation;
		}
	}

	private Transactional _deepSearchTypes(Queue<Class<?>> candidateQueue) {
		if (candidateQueue.isEmpty()) {
			return null;
		}

		Class<?> clazz = candidateQueue.poll();

		Transactional annotation = clazz.getAnnotation(Transactional.class);

		if (annotation == null) {
			_queueSuperTypes(clazz, candidateQueue);

			return _deepSearchTypes(candidateQueue);
		}
		else {
			return annotation;
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