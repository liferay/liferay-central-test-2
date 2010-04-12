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

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collection;

/**
 * <a href="PropertyImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PropertyImpl extends ProjectionImpl implements Property {

	public PropertyImpl(org.hibernate.criterion.Property property) {
		super(property);

		_property = property;
	}

	public Order asc() {
		return new OrderImpl(_property.asc());
	}

	public Projection avg() {
		String argument = "-AVG-" + _property.getPropertyName();

		return new ProjectionImpl(_property.avg(), argument);
	}

	public Criterion between(Object min, Object max) {
		String[] arguments = new String[] {
			_property.getPropertyName() + "-BETWEEN-" + String.valueOf(min) +
				StringPool.DASH + String.valueOf(max)
		};

		return new CriterionImpl(_property.between(min, max), arguments);
	}

	public Projection count() {
		String argument = "-COUNT-" + _property.getPropertyName();

		return new ProjectionImpl(_property.count(), argument);
	}

	public Order desc() {
		return new OrderImpl(_property.desc());
	}

	public Criterion eq(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		String[] arguments = new String[] {
			_property.getPropertyName() + "-EQ-"
		};

		arguments = ArrayUtil.append(
			arguments, dynamicQueryImpl.getArguments());

		return new CriterionImpl(
			_property.eq(dynamicQueryImpl.getDetachedCriteria()), arguments);
	}

	public Criterion eq(Object value) {
		String[] arguments = new String[] {
			_property.getPropertyName() + "-EQ-" + String.valueOf(value)
		};

		return new CriterionImpl(_property.eq(value), arguments);
	}

	public Criterion eqAll(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		String[] arguments = new String[] {
			_property.getPropertyName() + "-EQALL-"
		};

		arguments = ArrayUtil.append(
			arguments, dynamicQueryImpl.getArguments());

		return new CriterionImpl(
			_property.eqAll(dynamicQueryImpl.getDetachedCriteria()), arguments);
	}

	public Criterion eqProperty(Property other) {
		PropertyImpl otherPropertyImpl = (PropertyImpl)other;

		String otherPropertyName =
			otherPropertyImpl.getWrappedProperty().getPropertyName();

		String[] arguments = new String[] {
			_property.getPropertyName() + "-EQPROPERTY-" + otherPropertyName
		};

		return new CriterionImpl(
			_property.eqProperty(otherPropertyImpl.getWrappedProperty()),
			arguments);
	}

	public Criterion eqProperty(String other) {
		String[] arguments = new String[] {
			_property.getPropertyName() + "-EQPROPERTY-" + other
		};

		return new CriterionImpl(_property.eqProperty(other), arguments);
	}

	public Property getProperty(String propertyName) {
		return new PropertyImpl(_property.getProperty(propertyName));
	}

	public org.hibernate.criterion.Property getWrappedProperty() {
		return _property;
	}

	public Projection group() {
		String argument = "-GROUP-" + _property.getPropertyName();

		return new ProjectionImpl(_property.group(), argument);
	}

	public Criterion ge(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		String[] arguments = new String[] {
			_property.getPropertyName() + "-GE-"
		};

		arguments = ArrayUtil.append(
			arguments, dynamicQueryImpl.getArguments());

		return new CriterionImpl(
			_property.ge(dynamicQueryImpl.getDetachedCriteria()), arguments);
	}

	public Criterion ge(Object value) {
		String[] arguments = new String[] {
			_property.getPropertyName() + "-GE-" + String.valueOf(value)
		};

		return new CriterionImpl(_property.ge(value), arguments);
	}

	public Criterion geAll(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		String[] arguments = new String[] {
			_property.getPropertyName() + "-GEALL-"
		};

		arguments = ArrayUtil.append(
			arguments, dynamicQueryImpl.getArguments());

		return new CriterionImpl(
			_property.geAll(dynamicQueryImpl.getDetachedCriteria()), arguments);
	}

	public Criterion geProperty(Property other) {
		PropertyImpl otherPropertyImpl = (PropertyImpl)other;

		String otherPropertyName =
			otherPropertyImpl.getWrappedProperty().getPropertyName();

		String[] arguments = new String[] {
			_property.getPropertyName() + "-GEPROPERTY-" + otherPropertyName
		};

		return new CriterionImpl(
			_property.geProperty(otherPropertyImpl.getWrappedProperty()),
			arguments);
	}

	public Criterion geProperty(String other) {
		String[] arguments = {
			_property.getPropertyName() + "-GEPROPERTY-" + other
		};

		return new CriterionImpl(_property.geProperty(other), arguments);
	}

	public Criterion geSome(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		String[] arguments = new String[] {
			_property.getPropertyName() + "-GESOME-"
		};

		arguments = ArrayUtil.append(
			arguments, dynamicQueryImpl.getArguments());

		return new CriterionImpl(
			_property.geSome(dynamicQueryImpl.getDetachedCriteria()),
			arguments);
	}

	public Criterion gt(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		String[] arguments = new String[] {
			_property.getPropertyName() + "-GT-"
		};

		arguments = ArrayUtil.append(
			arguments, dynamicQueryImpl.getArguments());

		return new CriterionImpl(
			_property.gt(dynamicQueryImpl.getDetachedCriteria()), arguments);
	}

	public Criterion gt(Object value) {
		String[] arguments = new String[] {
			_property.getPropertyName() + "-GT-" + String.valueOf(value)
		};

		return new CriterionImpl(_property.gt(value), arguments);
	}

	public Criterion gtAll(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		String[] arguments = new String[] {
			_property.getPropertyName() + "-GTALL-"
		};

		arguments = ArrayUtil.append(
			arguments, dynamicQueryImpl.getArguments());

		return new CriterionImpl(
			_property.gtAll(dynamicQueryImpl.getDetachedCriteria()), arguments);
	}

	public Criterion gtProperty(Property other) {
		PropertyImpl otherPropertyImpl = (PropertyImpl)other;

		String otherPropertyName =
			otherPropertyImpl.getWrappedProperty().getPropertyName();

		String[] arguments = new String[] {
			_property.getPropertyName() + "-GTPROPERTY-" + otherPropertyName
		};

		return new CriterionImpl(
			_property.gtProperty(otherPropertyImpl.getWrappedProperty()),
			arguments);
	}

	public Criterion gtProperty(String other) {
		String[] arguments = new String[] {
			_property.getPropertyName() + "-GTPROPERTY-" + other
		};

		return new CriterionImpl(_property.gtProperty(other), arguments);
	}

	public Criterion gtSome(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		String[] arguments = new String[] {
			_property.getPropertyName() + "-GTSOME-"
		};

		arguments = ArrayUtil.append(
			arguments, dynamicQueryImpl.getArguments());

		return new CriterionImpl(
			_property.gtSome(dynamicQueryImpl.getDetachedCriteria()),
			arguments);
	}

	public Criterion in(Collection<Object> values) {
		String s = StringUtil.merge(values, StringPool.DASH);

		String[] arguments = new String[] {
			_property.getPropertyName() + "-IN-" + s
		};

		return new CriterionImpl(_property.in(values), arguments);
	}

	public Criterion in(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		String[] arguments = new String[] {
			_property.getPropertyName() + "-IN-"
		};

		arguments = ArrayUtil.append(
			arguments, dynamicQueryImpl.getArguments());

		return new CriterionImpl(
			_property.in(dynamicQueryImpl.getDetachedCriteria()), arguments);
	}

	public Criterion in(Object[] values) {
		String s = StringUtil.merge(values, StringPool.DASH);

		String[] arguments = new String[] {
			_property.getPropertyName() + "-IN-" + s
		};

		return new CriterionImpl(_property.in(values), arguments);
	}

	public Criterion isEmpty() {
		String[] arguments = new String[] {
			"-ISEMPTY-" + _property.getPropertyName()
		};

		return new CriterionImpl(_property.isEmpty(), arguments);
	}

	public Criterion isNotEmpty() {
		String[] arguments = new String[] {
			"-ISNOTEMPTY-" + _property.getPropertyName()
		};

		return new CriterionImpl(_property.isNotEmpty(), arguments);
	}

	public Criterion isNotNull() {
		String[] arguments = new String[] {
			"-ISNOTNULL-" + _property.getPropertyName()
		};

		return new CriterionImpl(_property.isNotNull(), arguments);
	}

	public Criterion isNull() {
		String[] arguments = new String[] {
			"-ISNULL-" + _property.getPropertyName()
		};

		return new CriterionImpl(_property.isNull(), arguments);
	}

	public Criterion le(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		String[] arguments = new String[] {
			_property.getPropertyName() + "-LE-"
		};

		arguments = ArrayUtil.append(
			arguments, dynamicQueryImpl.getArguments());

		return new CriterionImpl(
			_property.le(dynamicQueryImpl.getDetachedCriteria()), arguments);
	}

	public Criterion le(Object value) {
		String[] arguments = new String[] {
			_property.getPropertyName() + "-LE-" + String.valueOf(value)
		};

		return new CriterionImpl(_property.le(value), arguments);
	}

	public Criterion leAll(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		String[] arguments = new String[] {
			_property.getPropertyName() + "-LEALL-"
		};

		arguments = ArrayUtil.append(
			arguments, dynamicQueryImpl.getArguments());

		return new CriterionImpl(
			_property.leAll(dynamicQueryImpl.getDetachedCriteria()), arguments);
	}

	public Criterion leProperty(Property other) {
		PropertyImpl otherPropertyImpl = (PropertyImpl)other;

		String otherPropertyName =
			otherPropertyImpl.getWrappedProperty().getPropertyName();

		String[] arguments = new String[] {
			_property.getPropertyName() + "-LEPROPERTY-" + otherPropertyName
		};

		return new CriterionImpl(
			_property.leProperty(otherPropertyImpl.getWrappedProperty()),
			arguments);
	}

	public Criterion leProperty(String other) {
		String[] arguments = new String[] {
			_property.getPropertyName() + "-LEPROPERTY-" + other
		};

		return new CriterionImpl(_property.leProperty(other), arguments);
	}

	public Criterion leSome(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		String[] arguments = new String[] {
			_property.getPropertyName() + "-LESOME-"
		};

		arguments = ArrayUtil.append(
			arguments, dynamicQueryImpl.getArguments());

		return new CriterionImpl(
			_property.leSome(dynamicQueryImpl.getDetachedCriteria()),
			arguments);
	}

	public Criterion like(Object value) {
		String[] arguments = new String[] {
			_property.getPropertyName() + "-LIKE-" + String.valueOf(value)
		};

		return new CriterionImpl(_property.like(value), arguments);
	}

	public Criterion lt(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		String[] arguments = new String[] {
			_property.getPropertyName() + "-LT-"
		};

		arguments = ArrayUtil.append(
			arguments, dynamicQueryImpl.getArguments());

		return new CriterionImpl(
			_property.lt(dynamicQueryImpl.getDetachedCriteria()), arguments);
	}

	public Criterion lt(Object value) {
		String[] arguments = new String[] {
			_property.getPropertyName() + "-LT-" + String.valueOf(value)
		};

		return new CriterionImpl(_property.lt(value), arguments);
	}

	public Criterion ltAll(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		String[] arguments = new String[] {
			_property.getPropertyName() + "-LTALL-"
		};

		arguments = ArrayUtil.append(
			arguments, dynamicQueryImpl.getArguments());

		return new CriterionImpl(
			_property.ltAll(dynamicQueryImpl.getDetachedCriteria()), arguments);
	}

	public Criterion ltProperty(Property other) {
		PropertyImpl otherPropertyImpl = (PropertyImpl)other;

		String otherPropertyName =
			otherPropertyImpl.getWrappedProperty().getPropertyName();

		String[] arguments = new String[] {
			_property.getPropertyName() + "-LTPROPERTY-" + otherPropertyName
		};

		return new CriterionImpl(
			_property.ltProperty(otherPropertyImpl.getWrappedProperty()),
			arguments);
	}

	public Criterion ltProperty(String other) {
		String[] arguments = new String[] {
			_property.getPropertyName() + "-LTPROPERTY-" + other
		};

		return new CriterionImpl(_property.ltProperty(other), arguments);
	}

	public Criterion ltSome(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		String[] arguments = new String[] {
			_property.getPropertyName() + "-LTSOME-"
		};

		arguments = ArrayUtil.append(
			arguments, dynamicQueryImpl.getArguments());

		return new CriterionImpl(
			_property.ltSome(dynamicQueryImpl.getDetachedCriteria()),
			arguments);
	}

	public Projection max() {
		String argument = "-MAX-" + _property.getPropertyName();

		return new ProjectionImpl(_property.max(), argument);
	}

	public Projection min() {
		String argument = "-MIN-" + _property.getPropertyName();

		return new ProjectionImpl(_property.min(), argument);
	}

	public Criterion ne(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		String[] arguments = new String[] {
			_property.getPropertyName() + "-NE-"
		};

		arguments = ArrayUtil.append(
			arguments, dynamicQueryImpl.getArguments());

		return new CriterionImpl(
			_property.ne(dynamicQueryImpl.getDetachedCriteria()), arguments);
	}

	public Criterion ne(Object value) {
		String[] arguments = new String[] {
			_property.getPropertyName() + "-NE-" + String.valueOf(value)
		};

		return new CriterionImpl(_property.ne(value), arguments);
	}

	public Criterion neProperty(Property other) {
		PropertyImpl otherPropertyImpl = (PropertyImpl)other;

		String otherPropertyName =
			otherPropertyImpl.getWrappedProperty().getPropertyName();

		String[] arguments = new String[] {
			_property.getPropertyName() + "-NEPROPERTY-" + otherPropertyName
		};

		return new CriterionImpl(
			_property.neProperty(otherPropertyImpl.getWrappedProperty()),
			arguments);
	}

	public Criterion neProperty(String other) {
		String[] arguments = new String[] {
			_property.getPropertyName() + "-NEPROPERTY-" + other
		};

		return new CriterionImpl(_property.neProperty(other), arguments);
	}

	public Criterion notIn(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		String[] arguments = new String[] {
			_property.getPropertyName() + "-NOTIN-"
		};

		arguments = ArrayUtil.append(
			arguments, dynamicQueryImpl.getArguments());

		return new CriterionImpl(
			_property.notIn(dynamicQueryImpl.getDetachedCriteria()), arguments);
	}

	private org.hibernate.criterion.Property _property;

}