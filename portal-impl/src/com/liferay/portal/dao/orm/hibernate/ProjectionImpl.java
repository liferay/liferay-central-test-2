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
import com.liferay.portal.kernel.util.StringPool;

/**
 * <a href="ProjectionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ProjectionImpl implements Projection {

	public ProjectionImpl(org.hibernate.criterion.Projection projection) {
		_argument = StringPool.BLANK;
		_projection = projection;
	}

	public ProjectionImpl(
		org.hibernate.criterion.Projection projection, String argument) {

		_argument = argument;
		_projection = projection;
	}

	public String getArgument() {
		return _argument;
	}

	public org.hibernate.criterion.Projection getWrappedProjection() {
		return _projection;
	}

	private String _argument;
	private org.hibernate.criterion.Projection _projection;

}