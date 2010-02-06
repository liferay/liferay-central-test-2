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

/**
 * <a href="ProjectionFactoryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ProjectionFactoryUtil {

	public static Projection alias(Projection projection, String alias) {
		return getProjectionFactory().alias(projection, alias);
	}

	public static Projection avg(String propertyName) {
		return getProjectionFactory().avg(propertyName);
	}

	public static Projection count(String propertyName) {
		return getProjectionFactory().count(propertyName);
	}

	public static Projection countDistinct(String propertyName) {
		return getProjectionFactory().countDistinct(propertyName);
	}

	public static Projection distinct(Projection projection) {
		return getProjectionFactory().distinct(projection);
	}

	public static ProjectionFactory getProjectionFactory() {
		return _projectionFactory;
	}

	public static Projection groupProperty(String propertyName) {
		return getProjectionFactory().groupProperty(propertyName);
	}

	public static Projection max(String propertyName) {
		return getProjectionFactory().max(propertyName);
	}

	public static Projection min(String propertyName) {
		return getProjectionFactory().min(propertyName);
	}

	public static ProjectionList projectionList() {
		return getProjectionFactory().projectionList();
	}

	public static Projection property(String propertyName) {
		return getProjectionFactory().property(propertyName);
	}

	public static Projection rowCount() {
		return getProjectionFactory().rowCount();
	}

	public static Projection sum(String propertyName) {
		return getProjectionFactory().sum(propertyName);
	}

	public void setProjectionFactory(ProjectionFactory projectionFactory) {
		_projectionFactory = projectionFactory;
	}

	private static ProjectionFactory _projectionFactory;

}