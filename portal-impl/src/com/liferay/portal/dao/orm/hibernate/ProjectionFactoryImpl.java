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

		String argument = projectionImpl.getArgument() + "-ALIAS-" + alias;

		return new ProjectionImpl(
			Projections.alias(projectionImpl.getWrappedProjection(), alias),
			argument);
	}

	public Projection avg(String propertyName) {
		String argument = "-AVG-" + propertyName;

		return new ProjectionImpl(Projections.avg(propertyName), argument);
	}

	public Projection count(String propertyName) {
		String argument = "-COUNT-" + propertyName;

		return new ProjectionImpl(Projections.count(propertyName), argument);
	}

	public Projection countDistinct(String propertyName) {
		String argument = "-COUNTDISTINCT-" + propertyName;

		return new ProjectionImpl(
			Projections.countDistinct(propertyName), argument);
	}

	public Projection distinct(Projection projection) {
		ProjectionImpl projectionImpl = (ProjectionImpl)projection;

		String argument = "-DISTINCT-" + projectionImpl.getArgument();

		return new ProjectionImpl(
			Projections.distinct(
				projectionImpl.getWrappedProjection()), argument);
	}

	public Projection groupProperty(String propertyName) {
		String argument = "-GROUPPROPERTY-" + propertyName;

		return new ProjectionImpl(
			Projections.groupProperty(propertyName), argument);
	}

	public Projection max(String propertyName) {
		String argument = "-MAX-" + propertyName;

		return new ProjectionImpl(Projections.max(propertyName), argument);
	}

	public Projection min(String propertyName) {
		String argument = "-MIN-" + propertyName;

		return new ProjectionImpl(Projections.min(propertyName), argument);
	}

	public ProjectionList projectionList() {
		return new ProjectionListImpl(Projections.projectionList());
	}

	public Projection property(String propertyName) {
		String argument = "-PROPERTY-" + propertyName;

		return new ProjectionImpl(Projections.property(propertyName), argument);
	}

	public Projection rowCount() {
		String argument = "-ROWCOUNT-";

		return new ProjectionImpl(Projections.rowCount(), argument);
	}

	public Projection sum(String propertyName) {
		String argument = "-SUM-" + propertyName;

		return new ProjectionImpl(Projections.sum(propertyName), argument);
	}

}