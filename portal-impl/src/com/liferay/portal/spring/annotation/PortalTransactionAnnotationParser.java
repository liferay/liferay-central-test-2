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

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.TransactionAnnotationParser;
import org.springframework.transaction.interceptor.NoRollbackRuleAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * <a href="PortalTransactionAnnotationParser.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Michael Young
 */
public class PortalTransactionAnnotationParser
	implements TransactionAnnotationParser, Serializable {

	public TransactionAttribute parseTransactionAnnotation(
		AnnotatedElement annotatedElement) {

		Transactional annotation = annotatedElement.getAnnotation(
			Transactional.class);

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

}