/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.dao.orm.jpa;

import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactory;

import java.util.Collection;
import java.util.Map;

/**
 * <a href="RestrictionsFactoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 */
public class RestrictionsFactoryImpl implements RestrictionsFactory {

	public Criterion allEq(Map<String, Criterion> propertyNameValues) {
		throw new UnsupportedOperationException();
	}

	public Criterion and(Criterion lhs, Criterion rhs) {
		throw new UnsupportedOperationException();
	}

	public Criterion between(String propertyName, Object lo, Object hi) {
		throw new UnsupportedOperationException();
	}

	public Conjunction conjunction() {
		throw new UnsupportedOperationException();
	}

	public Disjunction disjunction() {
		throw new UnsupportedOperationException();
	}

	public Criterion eq(String propertyName, Object value) {
		throw new UnsupportedOperationException();
	}

	public Criterion eqProperty(String propertyName, String otherPropertyName) {
		throw new UnsupportedOperationException();
	}

	public Criterion ge(String propertyName, Object value) {
		throw new UnsupportedOperationException();
	}

	public Criterion geProperty(String propertyName, String otherPropertyName) {
		throw new UnsupportedOperationException();
	}

	public Criterion gt(String propertyName, Object value) {
		throw new UnsupportedOperationException();
	}

	public Criterion gtProperty(String propertyName, String otherPropertyName) {
		throw new UnsupportedOperationException();
	}

	public Criterion ilike(String propertyName, Object value) {
		throw new UnsupportedOperationException();
	}

	public Criterion in(String propertyName, Collection<Object> values) {
		throw new UnsupportedOperationException();
	}

	public Criterion in(String propertyName, Object[] values) {
		throw new UnsupportedOperationException();
	}

	public Criterion isEmpty(String propertyName) {
		throw new UnsupportedOperationException();
	}

	public Criterion isNotEmpty(String propertyName) {
		throw new UnsupportedOperationException();
	}

	public Criterion isNotNull(String propertyName) {
		throw new UnsupportedOperationException();
	}

	public Criterion isNull(String propertyName) {
		throw new UnsupportedOperationException();
	}

	public Criterion le(String propertyName, Object value) {
		throw new UnsupportedOperationException();
	}

	public Criterion leProperty(String propertyName, String otherPropertyName) {
		throw new UnsupportedOperationException();
	}

	public Criterion like(String propertyName, Object value) {
		throw new UnsupportedOperationException();
	}

	public Criterion lt(String propertyName, Object value) {
		throw new UnsupportedOperationException();
	}

	public Criterion ltProperty(String propertyName, String otherPropertyName) {
		throw new UnsupportedOperationException();
	}

	public Criterion ne(String propertyName, Object value) {
		throw new UnsupportedOperationException();
	}

	public Criterion neProperty(String propertyName, String otherPropertyName) {
		throw new UnsupportedOperationException();
	}

	public Criterion not(Criterion expression) {
		throw new UnsupportedOperationException();
	}

	public Criterion or(Criterion lhs, Criterion rhs) {
		throw new UnsupportedOperationException();
	}

	public Criterion sizeEq(String propertyName, int size) {
		throw new UnsupportedOperationException();
	}

	public Criterion sizeGe(String propertyName, int size) {
		throw new UnsupportedOperationException();
	}

	public Criterion sizeGt(String propertyName, int size) {
		throw new UnsupportedOperationException();
	}

	public Criterion sizeLe(String propertyName, int size) {
		throw new UnsupportedOperationException();
	}

	public Criterion sizeLt(String propertyName, int size) {
		throw new UnsupportedOperationException();
	}

	public Criterion sizeNe(String propertyName, int size) {
		throw new UnsupportedOperationException();
	}

}