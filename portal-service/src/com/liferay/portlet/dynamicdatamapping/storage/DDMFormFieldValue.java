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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marcellus Tavares
 * @author Pablo Carvalho
 */
public class DDMFormFieldValue {

	public void addValue(LocalizedValue value) {
		_values.add(value);
	}

	public String getName() {
		return _name;
	}

	public List<DDMFormFieldValue> getNestedDDMFormFieldValues() {
		return _nestedDDMFormFieldValues;
	}

	public Map<String, DDMFormFieldValue> getNestedDDMFormFieldValuesMap() {
		Map<String, DDMFormFieldValue> nestedDDMFormFieldValuesMap =
			new HashMap<String, DDMFormFieldValue>();

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				_nestedDDMFormFieldValues) {

			nestedDDMFormFieldValuesMap.put(
				nestedDDMFormFieldValue.getName(), nestedDDMFormFieldValue);
		}

		return nestedDDMFormFieldValuesMap;
	}

	public Value getValue(int index) {
		return _values.get(index);
	}

	public List<Value> getValues() {
		return _values;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setNestedDDMFormFields(
		List<DDMFormFieldValue> nestedDDMFormFieldValues) {

		_nestedDDMFormFieldValues = nestedDDMFormFieldValues;
	}

	public void setValues(List<Value> values) {
		_values = values;
	}

	private String _name;
	private List<DDMFormFieldValue> _nestedDDMFormFieldValues =
		new ArrayList<DDMFormFieldValue>();
	private List<Value> _values = new ArrayList<Value>();

}