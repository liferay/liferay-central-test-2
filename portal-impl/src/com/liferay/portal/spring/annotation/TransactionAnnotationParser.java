/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.annotation.Transactional;

import java.io.Serializable;

import java.lang.reflect.AnnotatedElement;

import java.util.ArrayList;

import org.springframework.transaction.interceptor.NoRollbackRuleAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * <a href="TransactionAnnotationParser.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 *
 */
public class TransactionAnnotationParser implements
	org.springframework.transaction.annotation.TransactionAnnotationParser,
	Serializable {

	public TransactionAttribute parseTransactionAnnotation(
			AnnotatedElement annotatedElement) {

		Transactional annotation =
			annotatedElement.getAnnotation(Transactional.class);

		if (annotation != null) {
			RuleBasedTransactionAttribute rbta =
				new RuleBasedTransactionAttribute();

			rbta.setPropagationBehavior(annotation.propagation().value());
			rbta.setIsolationLevel(annotation.isolation().value());
			rbta.setTimeout(annotation.timeout());
			rbta.setReadOnly(annotation.readOnly());

			ArrayList<RollbackRuleAttribute> rollBackRules =
				new ArrayList<RollbackRuleAttribute>();

			RollbackRuleAttribute rollbackRule = null;
			Class[] rbf = annotation.rollbackFor();

			for (int i = 0; i < rbf.length; ++i) {
				rollbackRule = new RollbackRuleAttribute(rbf[i]);
				rollBackRules.add(rollbackRule);
			}

			String[] rbfc = annotation.rollbackForClassName();

			for (int i = 0; i < rbfc.length; ++i) {
				rollbackRule = new RollbackRuleAttribute(rbfc[i]);
				rollBackRules.add(rollbackRule);
			}

			NoRollbackRuleAttribute noRollbackRule = null;
			Class[] nrbf = annotation.noRollbackFor();

			for (int i = 0; i < nrbf.length; ++i) {
				noRollbackRule = new NoRollbackRuleAttribute(nrbf[i]);
				rollBackRules.add(noRollbackRule);
			}

			String[] nrbfc = annotation.noRollbackForClassName();

			for (int i = 0; i < nrbfc.length; ++i) {
				noRollbackRule = new NoRollbackRuleAttribute(nrbfc[i]);
				rollBackRules.add(noRollbackRule);
			}

			rbta.getRollbackRules().addAll(rollBackRules);

			return rbta;
		} else {
			return null;
		}
	}

}