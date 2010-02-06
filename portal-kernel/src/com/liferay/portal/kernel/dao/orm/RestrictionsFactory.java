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
import java.util.Map;

/**
 * <a href="RestrictionsFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public interface RestrictionsFactory {

	public Criterion allEq(Map<String, Criterion> propertyNameValues);

	public Criterion and(Criterion lhs, Criterion rhs);

	public Criterion between(String propertyName, Object lo, Object hi);

	public Conjunction conjunction();

	public Disjunction disjunction();

	public Criterion eq(String propertyName, Object value);

	public Criterion eqProperty(String propertyName, String otherPropertyName);

	public Criterion ge(String propertyName, Object value);

	public Criterion geProperty(String propertyName, String otherPropertyName);

	public Criterion gt(String propertyName, Object value);

	public Criterion gtProperty(String propertyName, String otherPropertyName);

	public Criterion ilike(String propertyName, Object value);

	public Criterion in(String propertyName, Collection<Object> values);

	public Criterion in(String propertyName, Object[] values);

	public Criterion isEmpty(String propertyName);

	public Criterion isNotEmpty(String propertyName);

	public Criterion isNotNull(String propertyName);

	public Criterion isNull(String propertyName);

	public Criterion le(String propertyName, Object value);

	public Criterion leProperty(String propertyName, String otherPropertyName);

	public Criterion like(String propertyName, Object value);

	public Criterion lt(String propertyName, Object value);

	public Criterion ltProperty(String propertyName, String otherPropertyName);

	public Criterion ne(String propertyName, Object value);

	public Criterion neProperty(String propertyName, String otherPropertyName);

	public Criterion not(Criterion expression);

	public Criterion or(Criterion lhs, Criterion rhs);

	public Criterion sizeEq(String propertyName, int size);

	public Criterion sizeGe(String propertyName, int size);

	public Criterion sizeGt(String propertyName, int size);

	public Criterion sizeLe(String propertyName, int size);

	public Criterion sizeLt(String propertyName, int size);

	public Criterion sizeNe(String propertyName, int size);

}