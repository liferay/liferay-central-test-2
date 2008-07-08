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

	private org.hibernate.criterion.Property _property;

}