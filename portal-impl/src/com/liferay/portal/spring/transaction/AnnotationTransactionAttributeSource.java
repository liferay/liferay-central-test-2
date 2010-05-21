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

package com.liferay.portal.spring.transaction;

import com.liferay.portal.kernel.annotation.TransactionDefinition;
import com.liferay.portal.kernel.annotation.Transactional;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NoRollbackRuleAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

/**
 * <a href="AnnotationTransactionAttributeSource.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Shuyang Zhou
 */
public class AnnotationTransactionAttributeSource
	implements TransactionAttributeSource {

	public TransactionAttribute getTransactionAttribute(
		Method method, Class targetClass) {

		CacheKey key = new CacheKey(method, targetClass);
		TransactionAttribute value = _cache.get(key);
		if (value != null) {
			if (value == _NULL_TRANSACTION_ATTRIBUTE) {
				return null;
			}
			else {
				return value;
			}
		}
		else {
			Queue<Class<?>> candidateQueue = new LinkedList<Class<?>>();
			if (targetClass == null) {
				candidateQueue.offer(method.getDeclaringClass());
			}
			else {
				candidateQueue.offer(targetClass);
			}
			Transactional annotation =
				_findTransactionAnnotation(method, candidateQueue);
			TransactionAttribute transactionAttribute =
				_parseTransactionAnnotation(annotation);

			if (transactionAttribute == null) {
				_cache.put(key, _NULL_TRANSACTION_ATTRIBUTE);
			}
			else {
				_cache.put(key, transactionAttribute);
			}

			return transactionAttribute;
		}
	}

	private Transactional _findTransactionAnnotation(
		Method method, Queue<Class<?>> candidateQueue) {

		if (candidateQueue.isEmpty()) {
			return null;
		}

		Transactional annotation = null;
		Class<?> clazz = candidateQueue.poll();
		// Check method in current Class
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

		// Check current Class
		annotation = clazz.getAnnotation(Transactional.class);

		if (annotation != null) {
			return annotation;
		}

		// Check supers
		_queueSuperTypes(clazz, candidateQueue);
		return _findTransactionAnnotation(method, candidateQueue);

	}

	private TransactionAttribute _parseTransactionAnnotation(
		Transactional annotation) {

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

	private static class CacheKey {

		private final Method method;

		private final Class<?> targetClass;

		public CacheKey(Method method, Class<?> targetClass) {
			this.method = method;
			this.targetClass = targetClass;
		}

		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final CacheKey other = (CacheKey) obj;
			if (this.method != other.method &&
				(this.method == null || !this.method.equals(other.method))) {
				return false;
			}
			if (this.targetClass != other.targetClass &&
				(this.targetClass == null ||
					!this.targetClass.equals(other.targetClass))) {
				return false;
			}
			return true;
		}

		public int hashCode() {
			int hash = 5;
			hash = 59 * hash +
				(this.method != null ? this.method.hashCode() : 0);
			hash = 59 * hash +
				(this.targetClass != null ? this.targetClass.hashCode() : 0);
			return hash;
		}

	}

	private final static TransactionAttribute _NULL_TRANSACTION_ATTRIBUTE =
		new DefaultTransactionAttribute();

	private Map<CacheKey, TransactionAttribute> _cache =
		new ConcurrentHashMap<CacheKey, TransactionAttribute>();

}