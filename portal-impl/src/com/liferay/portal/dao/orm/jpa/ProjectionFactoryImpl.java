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

package com.liferay.portal.dao.orm.jpa;

import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactory;
import com.liferay.portal.kernel.dao.orm.ProjectionList;

/**
 * <a href="ProjectionFactoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 */
public class ProjectionFactoryImpl implements ProjectionFactory {

	public Projection alias(Projection projection, String alias) {
		throw new UnsupportedOperationException();
	}

	public Projection avg(String propertyName) {
		throw new UnsupportedOperationException();
	}

	public Projection count(String propertyName) {
		throw new UnsupportedOperationException();
	}

	public Projection countDistinct(String propertyName) {
		throw new UnsupportedOperationException();
	}

	public Projection distinct(Projection projection) {
		throw new UnsupportedOperationException();
	}

	public Projection groupProperty(String propertyName) {
		throw new UnsupportedOperationException();
	}

	public Projection max(String propertyName) {
		throw new UnsupportedOperationException();
	}

	public Projection min(String propertyName) {
		throw new UnsupportedOperationException();
	}

	public ProjectionList projectionList() {
		throw new UnsupportedOperationException();
	}

	public Projection property(String propertyName) {
		throw new UnsupportedOperationException();
	}

	public Projection rowCount() {
		throw new UnsupportedOperationException();
	}

	public Projection sum(String propertyName) {
		throw new UnsupportedOperationException();
	}

}