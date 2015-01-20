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

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.dynamicdatamapping.model.Value;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marcellus Tavares
 * @author Pablo Carvalho
 */
public class DDMFormFieldValue implements Serializable {

	public void addNestedDDMFormFieldValue(
		DDMFormFieldValue nestedDDMFormFieldValue) {

		nestedDDMFormFieldValue.setDDMFormValues(_ddmFormValues);

		_nestedDDMFormFieldValues.add(nestedDDMFormFieldValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMFormFieldValue)) {
			return false;
		}

		DDMFormFieldValue ddmFormFieldValue = (DDMFormFieldValue)obj;

		if (Validator.equals(_instanceId, ddmFormFieldValue._instanceId) &&
			Validator.equals(_name, ddmFormFieldValue._name) &&
			Validator.equals(_value, ddmFormFieldValue._value) &&
			Validator.equals(
				_nestedDDMFormFieldValues,
				ddmFormFieldValue._nestedDDMFormFieldValues)) {

			return true;
		}

		return false;
	}

	public DDMFormValues getDDMFormValues() {
		return _ddmFormValues;
	}

	public String getInstanceId() {
		return _instanceId;
	}

	public String getName() {
		return _name;
	}

	public List<DDMFormFieldValue> getNestedDDMFormFieldValues() {
		return _nestedDDMFormFieldValues;
	}

	public Map<String, List<DDMFormFieldValue>>
		getNestedDDMFormFieldValuesMap() {

		Map<String, List<DDMFormFieldValue>> nestedDDMFormFieldValuesMap =
			new HashMap<>();

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				_nestedDDMFormFieldValues) {

			List<DDMFormFieldValue> nestedDDMFormFieldValues =
				nestedDDMFormFieldValuesMap.get(
					nestedDDMFormFieldValue.getName());

			if (nestedDDMFormFieldValues == null) {
				nestedDDMFormFieldValues = new ArrayList<>();

				nestedDDMFormFieldValuesMap.put(
					nestedDDMFormFieldValue.getName(),
					nestedDDMFormFieldValues);
			}

			nestedDDMFormFieldValues.add(nestedDDMFormFieldValue);
		}

		return nestedDDMFormFieldValuesMap;
	}

	public Value getValue() {
		return _value;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _instanceId);

		hash = HashUtil.hash(hash, _name);
		hash = HashUtil.hash(hash, _nestedDDMFormFieldValues);

		return HashUtil.hash(hash, _value);
	}

	public void setDDMFormValues(DDMFormValues ddmFormValues) {
		for (DDMFormFieldValue nestedDDMFormFieldValue :
				_nestedDDMFormFieldValues) {

			nestedDDMFormFieldValue.setDDMFormValues(ddmFormValues);
		}

		_ddmFormValues = ddmFormValues;
	}

	public void setInstanceId(String instanceId) {
		_instanceId = instanceId;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setNestedDDMFormFields(
		List<DDMFormFieldValue> nestedDDMFormFieldValues) {

		_nestedDDMFormFieldValues = nestedDDMFormFieldValues;
	}

	public void setValue(Value value) {
		_value = value;
	}

	private DDMFormValues _ddmFormValues;
	private String _instanceId;
	private String _name;
	private List<DDMFormFieldValue> _nestedDDMFormFieldValues =
		new ArrayList<>();
	private Value _value;

}