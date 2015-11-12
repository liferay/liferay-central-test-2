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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFactory {

	public static DDMForm create(Class<?> clazz) {
		if (!clazz.isAnnotationPresent(_DDM_FORM_ANNOTATION)) {
			throw new IllegalArgumentException(
				"Unsupported class " + clazz.getName());
		}

		Map<String, DDMFormField> ddmFormFieldsMap = new HashMap<>();

		collectDDMFormFields(clazz, ddmFormFieldsMap);

		DDMForm ddmForm = new DDMForm();

		ddmForm.setDDMFormFields(
			new ArrayList<DDMFormField>(ddmFormFieldsMap.values()));

		return ddmForm;
	}

	protected static void collectDDMFormFields(
		Class<?> clazz, Map<String, DDMFormField> ddmFormFieldsMap) {

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			collectDDMFormFields(interfaceClass, ddmFormFieldsMap);
		}

		for (Method method : clazz.getDeclaredMethods()) {
			if (!method.isAnnotationPresent(_DDM_FORM_FIELD_ANNOTATION)) {
				continue;
			}

			DDMFormField ddmFormField = createDDMFormField(clazz, method);

			ddmFormFieldsMap.put(ddmFormField.getName(), ddmFormField);
		}
	}

	protected static DDMFormField createDDMFormField(
		Class<?> clazz, Method method) {

		DDMFormFactoryHelper ddmFormFactoryHelper = new DDMFormFactoryHelper(
			clazz, method);

		String name = ddmFormFactoryHelper.getDDMFormFieldName();
		String type = ddmFormFactoryHelper.getDDMFormFieldType();

		DDMFormField ddmFormField = new DDMFormField(name, type);

		Map<String, Object> properties = ddmFormFactoryHelper.getProperties();

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			if (ddmFormFactoryHelper.isLocalizableValue((String)value)) {
				value = ddmFormFactoryHelper.getPropertyValue(value);
			}

			ddmFormField.setProperty(key, value);
		}

		ddmFormField.setDataType(
			ddmFormFactoryHelper.getDDMFormFieldDataType());
		ddmFormField.setDDMFormFieldOptions(
			ddmFormFactoryHelper.getDDMFormFieldOptions());
		ddmFormField.setDDMFormFieldValidation(
			ddmFormFactoryHelper.getDDMFormFieldValidation());
		ddmFormField.setLabel(ddmFormFactoryHelper.getDDMFormFieldLabel());
		ddmFormField.setLocalizable(
			ddmFormFactoryHelper.isDDMFormFieldLocalizable(method));
		ddmFormField.setPredefinedValue(
			ddmFormFactoryHelper.getDDMFormFieldPredefinedValue());
		ddmFormField.setRequired(ddmFormFactoryHelper.isDDMFormFieldRequired());
		ddmFormField.setTip(ddmFormFactoryHelper.getDDMFormFieldTip());
		ddmFormField.setVisibilityExpression(
			ddmFormFactoryHelper.getDDMFormFieldVisibilityExpression());

		return ddmFormField;
	}

	private static final Class<? extends Annotation> _DDM_FORM_ANNOTATION =
		com.liferay.dynamic.data.mapping.annotations.DDMForm.class;

	private static final Class<? extends Annotation>
		_DDM_FORM_FIELD_ANNOTATION =
			com.liferay.dynamic.data.mapping.annotations.DDMFormField.class;

}