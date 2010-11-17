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
import com.liferay.portal.kernel.util.MethodTargetClassKey;
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
 * @author Shuyang Zhou
 */
public class AnnotationTransactionAttributeSource
	implements TransactionAttributeSource {

	@SuppressWarnings("rawtypes")
	public TransactionAttribute getTransactionAttribute(
		Method method, Class targetClass) {

		MethodTargetClassKey methodTargetClassKey = new MethodTargetClassKey(
			method, targetClass);

		TransactionAttribute transactionAttribute = _transactionAttributes.get(
			methodTargetClassKey);

		if (transactionAttribute != null) {
			if (transactionAttribute == _nullTransactionAttribute) {
				return null;
			}
			else {
				return transactionAttribute;
			}
		}

		Queue<Class<?>> candidateQueue = new LinkedList<Class<?>>();

		if (targetClass == null) {
			candidateQueue.offer(method.getDeclaringClass());
		}
		else {
			candidateQueue.offer(targetClass);
		}

		Transactional transactional = _findTransactionAnnotation(
			method, candidateQueue);

		transactionAttribute = _parseTransactionAnnotation(transactional);

		if (transactionAttribute == null) {
			_transactionAttributes.put(
				methodTargetClassKey, _nullTransactionAttribute);
		}
		else {
			_transactionAttributes.put(
				methodTargetClassKey, transactionAttribute);
		}

		return transactionAttribute;
	}

	private Transactional _findTransactionAnnotation(
		Method method, Queue<Class<?>> candidateQueue) {

		if (candidateQueue.isEmpty()) {
			return null;
		}

		Transactional transactional = null;

		Class<?> clazz = candidateQueue.poll();

		try {
			Method specificMethod = clazz.getDeclaredMethod(
				method.getName(), method.getParameterTypes());

			transactional = specificMethod.getAnnotation(Transactional.class);

			if (transactional != null) {
				return transactional;
			}
		}
		catch (Exception e) {
		}

		transactional = clazz.getAnnotation(Transactional.class);

		if (transactional != null) {
			return transactional;
		}

		_queueSuperTypes(clazz, candidateQueue);

		return _findTransactionAnnotation(method, candidateQueue);
	}

	private TransactionAttribute _parseTransactionAnnotation(
		Transactional transactional) {

		if ((transactional == null) || !transactional.enabled()) {
			return null;
		}

		RuleBasedTransactionAttribute ruleBasedTransactionAttribute =
			new RuleBasedTransactionAttribute();

		int isolationLevel = transactional.isolation().value();

		if (isolationLevel == TransactionDefinition.ISOLATION_COUNTER) {
			ruleBasedTransactionAttribute.setIsolationLevel(
				PropsValues.TRANSACTION_ISOLATION_COUNTER);
		}
		else if (isolationLevel == TransactionDefinition.ISOLATION_PORTAL) {
			ruleBasedTransactionAttribute.setIsolationLevel(
				PropsValues.TRANSACTION_ISOLATION_PORTAL);
		}
		else {
			ruleBasedTransactionAttribute.setIsolationLevel(isolationLevel);
		}

		ruleBasedTransactionAttribute.setPropagationBehavior(
			transactional.propagation().value());
		ruleBasedTransactionAttribute.setReadOnly(transactional.readOnly());
		ruleBasedTransactionAttribute.setTimeout(transactional.timeout());

		List<RollbackRuleAttribute> rollBackAttributes =
			new ArrayList<RollbackRuleAttribute>();

		Class<?>[] rollbackFor = transactional.rollbackFor();

		for (int i = 0; i < rollbackFor.length; i++) {
			RollbackRuleAttribute rollbackRuleAttribute =
				new RollbackRuleAttribute(rollbackFor[i]);

			rollBackAttributes.add(rollbackRuleAttribute);
		}

		String[] rollbackForClassName = transactional.rollbackForClassName();

		for (int i = 0; i < rollbackForClassName.length; i++) {
			RollbackRuleAttribute rollbackRuleAttribute =
				new RollbackRuleAttribute(rollbackForClassName[i]);

			rollBackAttributes.add(rollbackRuleAttribute);
		}

		Class<?>[] noRollbackFor = transactional.noRollbackFor();

		for (int i = 0; i < noRollbackFor.length; ++i) {
			NoRollbackRuleAttribute noRollbackRuleAttribute =
				new NoRollbackRuleAttribute(noRollbackFor[i]);

			rollBackAttributes.add(noRollbackRuleAttribute);
		}

		String[] noRollbackForClassName =
			transactional.noRollbackForClassName();

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

	private static TransactionAttribute _nullTransactionAttribute =
		new DefaultTransactionAttribute();
	private Map<MethodTargetClassKey, TransactionAttribute>
		_transactionAttributes =
			new ConcurrentHashMap<MethodTargetClassKey, TransactionAttribute>();

}