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

import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactory;
import com.liferay.portal.kernel.dao.orm.ProjectionList;

import org.hibernate.criterion.Projections;

/**
 * <a href="ProjectionFactoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ProjectionFactoryImpl implements ProjectionFactory {

	public Projection alias(Projection projection, String alias) {
		ProjectionImpl projectionImpl = (ProjectionImpl)projection;

		return new ProjectionImpl(
			Projections.alias(projectionImpl.getWrappedProjection(), alias));
	}

	public Projection avg(String propertyName) {
		return new ProjectionImpl(Projections.avg(propertyName));
	}

	public Projection count(String propertyName) {
		return new ProjectionImpl(Projections.count(propertyName));
	}

	public Projection countDistinct(String propertyName) {
		return new ProjectionImpl(Projections.countDistinct(propertyName));
	}

	public Projection distinct(Projection projection) {
		ProjectionImpl projectionImpl = (ProjectionImpl)projection;

		return new ProjectionImpl(
			Projections.distinct(projectionImpl.getWrappedProjection()));
	}

	public Projection groupProperty(String propertyName) {
		return new ProjectionImpl(Projections.groupProperty(propertyName));
	}

	public Projection max(String propertyName) {
		return new ProjectionImpl(Projections.max(propertyName));
	}

	public Projection min(String propertyName) {
		return new ProjectionImpl(Projections.min(propertyName));
	}

	public ProjectionList projectionList() {
		return new ProjectionListImpl(Projections.projectionList());
	}

	public Projection property(String propertyName) {
		return new ProjectionImpl(Projections.property(propertyName));
	}

	public Projection rowCount() {
		return new ProjectionImpl(Projections.rowCount());
	}

	public Projection sum(String propertyName) {
		return new ProjectionImpl(Projections.sum(propertyName));
	}

}