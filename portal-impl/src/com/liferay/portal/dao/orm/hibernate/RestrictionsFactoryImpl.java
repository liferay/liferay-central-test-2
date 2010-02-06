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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactory;

import java.util.Collection;
import java.util.Map;

/**
 * <a href="RestrictionsFactoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class RestrictionsFactoryImpl implements RestrictionsFactory {

	public Criterion allEq(Map<String, Criterion> propertyNameValues) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.allEq(propertyNameValues));
	}

	public Criterion and(Criterion lhs, Criterion rhs) {
		CriterionImpl lhsImpl = (CriterionImpl)lhs;
		CriterionImpl rhsImpl = (CriterionImpl)rhs;

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.and(
				lhsImpl.getWrappedCriterion(), rhsImpl.getWrappedCriterion()));
	}

	public Criterion between(String propertyName, Object lo, Object hi) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.between(propertyName, lo, hi));
	}

	public Conjunction conjunction() {
		return new ConjunctionImpl(
			org.hibernate.criterion.Restrictions.conjunction());
	}

	public Disjunction disjunction() {
		return new DisjunctionImpl(
			org.hibernate.criterion.Restrictions.disjunction());
	}

	public Criterion eq(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.eq(propertyName, value));
	}

	public Criterion eqProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.eqProperty(
				propertyName, otherPropertyName));
	}

	public Criterion ge(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.ge(propertyName, value));
	}

	public Criterion geProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.geProperty(
				propertyName, otherPropertyName));
	}

	public Criterion gt(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.gt(propertyName, value));
	}

	public Criterion gtProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.gtProperty(
				propertyName, otherPropertyName));
	}

	public Criterion ilike(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.ilike(propertyName, value));
	}

	public Criterion in(String propertyName, Collection<Object> values) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.in(propertyName, values));
	}

	public Criterion in(String propertyName, Object[] values) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.in(propertyName, values));
	}

	public Criterion isEmpty(String propertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.isEmpty(propertyName));
	}

	public Criterion isNotEmpty(String propertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.isNotEmpty(propertyName));
	}

	public Criterion isNotNull(String propertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.isNotNull(propertyName));
	}

	public Criterion isNull(String propertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.isNull(propertyName));
	}

	public Criterion le(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.le(propertyName, value));
	}

	public Criterion leProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.leProperty(
				propertyName, otherPropertyName));
	}

	public Criterion like(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.like(propertyName, value));
	}

	public Criterion lt(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.lt(propertyName, value));
	}

	public Criterion ltProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.ltProperty(
				propertyName, otherPropertyName));
	}

	public Criterion ne(String propertyName, Object value) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.ne(propertyName, value));
	}

	public Criterion neProperty(String propertyName, String otherPropertyName) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.neProperty(
				propertyName, otherPropertyName));
	}

	public Criterion not(Criterion expression) {
		CriterionImpl expressionImpl = (CriterionImpl)expression;

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.not(
				expressionImpl.getWrappedCriterion()));
	}

	public Criterion or(Criterion lhs, Criterion rhs) {
		CriterionImpl lhsImpl = (CriterionImpl)lhs;
		CriterionImpl rhsImpl = (CriterionImpl)rhs;

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.or(
				lhsImpl.getWrappedCriterion(), rhsImpl.getWrappedCriterion()));
	}

	public Criterion sizeEq(String propertyName, int size) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeEq(propertyName, size));
	}

	public Criterion sizeGe(String propertyName, int size) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeGe(propertyName, size));
	}

	public Criterion sizeGt(String propertyName, int size) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeGe(propertyName, size));
	}

	public Criterion sizeLe(String propertyName, int size) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeLe(propertyName, size));
	}

	public Criterion sizeLt(String propertyName, int size) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeLt(propertyName, size));
	}

	public Criterion sizeNe(String propertyName, int size) {
		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeNe(propertyName, size));
	}

}