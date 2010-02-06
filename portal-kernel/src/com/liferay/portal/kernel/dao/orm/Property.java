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

package com.liferay.portal.kernel.dao.orm;

import java.util.Collection;

/**
 * <a href="Property.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface Property extends Projection {

	public Order asc();

	public Projection avg();

	public Criterion between(Object min, Object max);

	public Projection count();

	public Order desc();

	public Criterion eq(DynamicQuery subselect);

	public Criterion eq(Object value);

	public Criterion eqAll(DynamicQuery subselect);

	public Criterion eqProperty(Property other);

	public Criterion eqProperty(String other);

	public Criterion ge(DynamicQuery subselect);

	public Criterion ge(Object value);

	public Criterion geAll(DynamicQuery subselect);

	public Criterion geProperty(Property other);

	public Criterion geProperty(String other);

	public Criterion geSome(DynamicQuery subselect);

	public Property getProperty(String propertyName);

	public Projection group();

	public Criterion gt(DynamicQuery subselect);

	public Criterion gt(Object value);

	public Criterion gtAll(DynamicQuery subselect);

	public Criterion gtProperty(Property other);

	public Criterion gtProperty(String other);

	public Criterion gtSome(DynamicQuery subselect);

	public Criterion in(Collection<Object> values);

	public Criterion in(DynamicQuery subselect);

	public Criterion in(Object[] values);

	public Criterion isEmpty();

	public Criterion isNotEmpty();

	public Criterion isNotNull();

	public Criterion isNull();

	public Criterion le(DynamicQuery subselect);

	public Criterion le(Object value);

	public Criterion leAll(DynamicQuery subselect);

	public Criterion leProperty(Property other);

	public Criterion leProperty(String other);

	public Criterion leSome(DynamicQuery subselect);

	public Criterion like(Object value);

	public Criterion lt(DynamicQuery subselect);

	public Criterion lt(Object value);

	public Criterion ltAll(DynamicQuery subselect);

	public Criterion ltProperty(Property other);

	public Criterion ltProperty(String other);

	public Criterion ltSome(DynamicQuery subselect);

	public Projection max();

	public Projection min();

	public Criterion ne(DynamicQuery subselect);

	public Criterion ne(Object value);

	public Criterion neProperty(Property other);

	public Criterion neProperty(String other);

	public Criterion notIn(DynamicQuery subselect);

}