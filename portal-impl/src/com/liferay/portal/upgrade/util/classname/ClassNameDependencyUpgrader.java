/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.util.classname;

import com.liferay.portal.upgrade.util.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Pastor
 */
public class ClassNameDependencyUpgrader {

	public ClassNameDependencyUpgrader(
		String oldValue, String newValue,
		List<ClassNameDependency> classNameDependencies) {

		_oldValue = oldValue;
		_newValue = newValue;

		if (classNameDependencies == null) {
			_classNameDependencies = new ArrayList<>();
		}
		else {
			_classNameDependencies = classNameDependencies;
		}
	}

	public void upgrade() {
		Table table = new Table("ClassName_");

		table.updateColumnValue("value", _oldValue, _newValue);

		for (ClassNameDependency classNameDependency : _classNameDependencies) {
			classNameDependency.update(_oldValue, _newValue);
		}
	}

	private final List<ClassNameDependency> _classNameDependencies;
	private final String _newValue;
	private final String _oldValue;

}