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

package com.liferay.portal.tools.servicebuilder;

import java.util.List;

/**
 * <a href="EntityFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class EntityFinder {

	public EntityFinder(
		String name, String returnType, boolean unique, String where,
		boolean dbIndex, List<EntityColumn> columns) {

		_name = name;
		_returnType = returnType;
		_unique = unique;
		_where = where;
		_dbIndex = dbIndex;
		_columns = columns;
	}

	public List<EntityColumn> getColumns() {
		return _columns;
	}

	public String getName() {
		return _name;
	}

	public String getReturnType() {
		return _returnType;
	}

	public String getWhere() {
		return _where;
	}

	public boolean isCollection() {
		if ((_returnType != null) && _returnType.equals("Collection")) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isDBIndex() {
		return _dbIndex;
	}

	public boolean isUnique() {
		return _unique;
	}

	private List<EntityColumn> _columns;
	private boolean _dbIndex;
	private String _name;
	private String _returnType;
	private boolean _unique;
	private String _where;

}