/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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