/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model.impl;

import com.liferay.portal.kernel.util.Validator;

/**
 * @author Roberto Díaz
 */
public class AssetQueryRule {

	public AssetQueryRule(
		String name, String[] values, boolean contains, boolean andOperator) {

		_name = name;
		_values = values;
		_contains = contains;
		_andOperator = andOperator;
	}

	public boolean equals(AssetQueryRule assetQueryRule) {
		if (Validator.equals(_name, assetQueryRule._name) &&
			Validator.equals(_contains, assetQueryRule._contains) &&
			Validator.equals(_andOperator, assetQueryRule._andOperator)) {

			return true;
		}

		return false;
	}

	public String getName() {
		return _name;
	}

	public String[] getValues() {
		return _values;
	}

	public boolean isAndOperator() {
		return _andOperator;
	}

	public boolean isContains() {
		return _contains;
	}

	public void setAndOperator(boolean andOperator) {
		_andOperator = andOperator;
	}
	
	public void setContains(boolean contains) {
		_contains = contains;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public void setValues(String[] values) {
		_values = values;
	}
	
	private boolean _andOperator;
	private boolean _contains;
	private String _name;
	private String[] _values;

}