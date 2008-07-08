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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;

import java.util.Collection;

/**
 * <a href="PropertyImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PropertyImpl implements Property {

	public PropertyImpl(org.hibernate.criterion.Property property) {
		_property = property;
	}

	public Criterion eq(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.eq(dynamicQueryImpl.getDetachedCriteria()));
	}

	public Criterion eq(Object value) {
		return new CriterionImpl(_property.eq(value));
	}

	public Criterion eqAll(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.eqAll(dynamicQueryImpl.getDetachedCriteria()));
	}

	public Criterion eqProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return new CriterionImpl(
			_property.eqProperty(propertyImpl.getProperty()));
	}

	public Criterion eqProperty(String other) {
		return new CriterionImpl(_property.eqProperty(other));
	}

	public org.hibernate.criterion.Property getProperty() {
		return _property;
	}

	public Criterion ge(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.ge(dynamicQueryImpl.getDetachedCriteria()));
	}

	public Criterion ge(Object value) {
		return new CriterionImpl(_property.ge(value));
	}

	public Criterion geAll(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.geAll(dynamicQueryImpl.getDetachedCriteria()));
	}

	public Criterion geProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return new CriterionImpl(
			_property.geProperty(propertyImpl.getProperty()));
	}

	public Criterion geProperty(String other) {
		return new CriterionImpl(_property.geProperty(other));
	}

	public Criterion geSome(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.geSome(dynamicQueryImpl.getDetachedCriteria()));
	}

	public Criterion gt(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.gt(dynamicQueryImpl.getDetachedCriteria()));
	}

	public Criterion gt(Object value) {
		return new CriterionImpl(_property.gt(value));
	}

	public Criterion gtAll(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.gtAll(dynamicQueryImpl.getDetachedCriteria()));
	}

	public Criterion gtProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return new CriterionImpl(
			_property.gtProperty(propertyImpl.getProperty()));
	}

	public Criterion gtProperty(String other) {
		return new CriterionImpl(_property.gtProperty(other));
	}

	public Criterion gtSome(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.gtSome(dynamicQueryImpl.getDetachedCriteria()));
	}

	public Criterion in(Collection values) {
		return new CriterionImpl(_property.in(values));
	}

	public Criterion in(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.in(dynamicQueryImpl.getDetachedCriteria()));
	}

	public Criterion in(Object[] values) {
		return new CriterionImpl(_property.in(values));
	}

	public Criterion le(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.le(dynamicQueryImpl.getDetachedCriteria()));
	}

	public Criterion le(Object value) {
		return new CriterionImpl(_property.le(value));
	}

	public Criterion leAll(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.leAll(dynamicQueryImpl.getDetachedCriteria()));
	}

	public Criterion leProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return new CriterionImpl(
			_property.leProperty(propertyImpl.getProperty()));
	}

	public Criterion leProperty(String other) {
		return new CriterionImpl(_property.leProperty(other));
	}

	public Criterion leSome(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.leSome(dynamicQueryImpl.getDetachedCriteria()));
	}

	public Criterion like(Object value) {
		return new CriterionImpl(_property.like(value));
	}

	public Criterion lt(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.lt(dynamicQueryImpl.getDetachedCriteria()));
	}

	public Criterion lt(Object value) {
		return new CriterionImpl(_property.lt(value));
	}

	public Criterion ltAll(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.ltAll(dynamicQueryImpl.getDetachedCriteria()));
	}

	public Criterion ltProperty(Property other) {
		PropertyImpl propertyImpl = (PropertyImpl)other;

		return new CriterionImpl(
			_property.ltProperty(propertyImpl.getProperty()));
	}

	public Criterion ltProperty(String other) {
		return new CriterionImpl(_property.ltProperty(other));
	}

	public Criterion ltSome(DynamicQuery subselect) {
		DynamicQueryImpl dynamicQueryImpl = (DynamicQueryImpl)subselect;

		return new CriterionImpl(
			_property.ltSome(dynamicQueryImpl.getDetachedCriteria()));
	}

	private org.hibernate.criterion.Property _property;

}