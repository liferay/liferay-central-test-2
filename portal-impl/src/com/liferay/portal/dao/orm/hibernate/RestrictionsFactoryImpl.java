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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collection;
import java.util.Map;

/**
 * <a href="RestrictionsFactoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class RestrictionsFactoryImpl implements RestrictionsFactory {

	public Criterion allEq(Map<String, Criterion> propertyNameValues) {
		String[] arguments = new String[] {"-ALLEQ-"};

		for (Criterion criterion : propertyNameValues.values()) {
			CriterionImpl criterionImpl = (CriterionImpl)criterion;

			arguments = ArrayUtil.append(
				arguments, criterionImpl.getArguments());
		}

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.allEq(propertyNameValues),
			arguments);
	}

	public Criterion and(Criterion lhs, Criterion rhs) {
		CriterionImpl lhsImpl = (CriterionImpl)lhs;
		CriterionImpl rhsImpl = (CriterionImpl)rhs;

		String[] arguments = lhsImpl.getArguments();

		arguments = ArrayUtil.append(arguments, new String[] {"-AND-"});
		arguments = ArrayUtil.append(arguments, rhsImpl.getArguments());

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.and(
				lhsImpl.getWrappedCriterion(), rhsImpl.getWrappedCriterion()),
			arguments);
	}

	public Criterion between(String propertyName, Object lo, Object hi) {
		String[] arguments = new String[] {
			propertyName + "-BETWEEN-" + String.valueOf(lo) + StringPool.DASH +
				String.valueOf(hi)
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.between(propertyName, lo, hi),
			arguments);
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
		String[] arguments = new String[] {
			propertyName + "-EQ-" + String.valueOf(value)
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.eq(propertyName, value),
			arguments);
	}

	public Criterion eqProperty(String propertyName, String otherPropertyName) {
		String[] arguments = new String[] {
			propertyName + "-EQPROPERTY-" + otherPropertyName
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.eqProperty(
				propertyName, otherPropertyName),
			arguments);
	}

	public Criterion ge(String propertyName, Object value) {
		String[] arguments = new String[] {
			propertyName + "-GE-" + String.valueOf(value)
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.ge(propertyName, value),
			arguments);
	}

	public Criterion geProperty(String propertyName, String otherPropertyName) {
		String[] arguments = new String[] {
			propertyName + "-GEPROPERTY-" + otherPropertyName
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.geProperty(
				propertyName, otherPropertyName),
			arguments);
	}

	public Criterion gt(String propertyName, Object value) {
		String[] arguments = new String[] {
			propertyName + "-GT-" + String.valueOf(value)
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.gt(propertyName, value),
			arguments);
	}

	public Criterion gtProperty(String propertyName, String otherPropertyName) {
		String[] arguments = new String[] {
			propertyName + "-GTPROPERTY-" + otherPropertyName
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.gtProperty(
				propertyName, otherPropertyName),
			arguments);
	}

	public Criterion ilike(String propertyName, Object value) {
		String[] arguments = new String[] {
			propertyName + "-ILIKE-" + String.valueOf(value)
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.ilike(propertyName, value),
			arguments);
	}

	public Criterion in(String propertyName, Collection<Object> values) {
		String s = StringUtil.merge(values, StringPool.DASH);

		String[] arguments = new String[] {propertyName + "-IN-" + s};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.in(propertyName, values),
			arguments);
	}

	public Criterion in(String propertyName, Object[] values) {
		String s = StringUtil.merge(values, StringPool.PERIOD);

		String[] arguments = new String[] {propertyName + "-IN-" + s};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.in(propertyName, values),
			arguments);
	}

	public Criterion isEmpty(String propertyName) {
		String[] arguments = new String[] {"-ISEMPTY-" + propertyName};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.isEmpty(propertyName),
			arguments);
	}

	public Criterion isNotEmpty(String propertyName) {
		String[] arguments = new String[] {"-ISNOTEMPTY-" + propertyName};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.isNotEmpty(propertyName),
			arguments);
	}

	public Criterion isNotNull(String propertyName) {
		String[] arguments = new String[] {"-ISNOTNULL-" + propertyName};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.isNotNull(propertyName),
			arguments);
	}

	public Criterion isNull(String propertyName) {
		String[] arguments = new String[] {"-ISNULL-" + propertyName};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.isNull(propertyName),
			arguments);
	}

	public Criterion le(String propertyName, Object value) {
		String[] arguments = new String[] {
			propertyName + "-LE-" + String.valueOf(value)
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.le(propertyName, value),
			arguments);
	}

	public Criterion leProperty(String propertyName, String otherPropertyName) {
		String[] arguments = new String[] {
			propertyName + "-LEPROPERTY-" + otherPropertyName
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.leProperty(
				propertyName, otherPropertyName),
			arguments);
	}

	public Criterion like(String propertyName, Object value) {
		String[] arguments = new String[] {
			propertyName + "-LIKE-" + String.valueOf(value)
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.like(propertyName, value),
			arguments);
	}

	public Criterion lt(String propertyName, Object value) {
		String[] arguments = new String[] {
			propertyName + "-LT-" + String.valueOf(value)
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.lt(propertyName, value),
			arguments);
	}

	public Criterion ltProperty(String propertyName, String otherPropertyName) {
		String[] arguments = new String[] {
			propertyName + "-LTPROPERTY-" + otherPropertyName
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.ltProperty(
				propertyName, otherPropertyName),
			arguments);
	}

	public Criterion ne(String propertyName, Object value) {
		String[] arguments = new String[] {
			propertyName + "-NE-" + String.valueOf(value)
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.ne(propertyName, value),arguments);
	}

	public Criterion neProperty(String propertyName, String otherPropertyName) {
		String[] arguments = new String[] {
			propertyName + "-NEPROPERTY-" + otherPropertyName
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.neProperty(
				propertyName, otherPropertyName),
			arguments);
	}

	public Criterion not(Criterion expression) {
		CriterionImpl expressionImpl = (CriterionImpl)expression;

		String[] arguments = new String[] {"-NOT-"};

		arguments = ArrayUtil.append(arguments, expressionImpl.getArguments());

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.not(
				expressionImpl.getWrappedCriterion()),
			arguments);
	}

	public Criterion or(Criterion lhs, Criterion rhs) {
		CriterionImpl lhsImpl = (CriterionImpl)lhs;
		CriterionImpl rhsImpl = (CriterionImpl)rhs;

		String[] arguments = lhsImpl.getArguments();

		arguments = ArrayUtil.append(arguments, new String[] {"-OR-"});
		arguments = ArrayUtil.append(arguments, rhsImpl.getArguments());

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.or(
				lhsImpl.getWrappedCriterion(), rhsImpl.getWrappedCriterion()),
			arguments);
	}

	public Criterion sizeEq(String propertyName, int size) {
		String[] arguments = new String[] {
			propertyName + "-SIZEEQ-" + String.valueOf(size)
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeEq(propertyName, size),
			arguments);
	}

	public Criterion sizeGe(String propertyName, int size) {
		String[] arguments = new String[] {
			propertyName + "-SIZEGE-" + String.valueOf(size)
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeGe(propertyName, size),
			arguments);
	}

	public Criterion sizeGt(String propertyName, int size) {
		String[] arguments = new String[] {
			propertyName + "-SIZEGT-" + String.valueOf(size)
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeGe(propertyName, size),
			arguments);
	}

	public Criterion sizeLe(String propertyName, int size) {
		String[] arguments = new String[] {
			propertyName + "-SIZELE-" + String.valueOf(size)
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeLe(propertyName, size),
			arguments);
	}

	public Criterion sizeLt(String propertyName, int size) {
		String[] arguments = new String[] {
			propertyName + "-SIZELT-" + String.valueOf(size)
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeLt(propertyName, size),
			arguments);
	}

	public Criterion sizeNe(String propertyName, int size) {
		String[] arguments = new String[] {
			propertyName + "-SIZENE-" + String.valueOf(size)
		};

		return new CriterionImpl(
			org.hibernate.criterion.Restrictions.sizeNe(propertyName, size),
			arguments);
	}

}