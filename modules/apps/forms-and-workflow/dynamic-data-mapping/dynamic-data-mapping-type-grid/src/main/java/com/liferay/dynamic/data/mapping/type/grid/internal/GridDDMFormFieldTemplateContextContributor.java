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

package com.liferay.dynamic.data.mapping.type.grid.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Queiroz
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=grid",
	service = {
		DDMFormFieldTemplateContextContributor.class,
		GridDDMFormFieldTemplateContextContributor.class
	}
)
public class GridDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> parameters = new HashMap<>();

		parameters.put(
			"columns",
			getOptions("columns", ddmFormField, ddmFormFieldRenderingContext));
		parameters.put(
			"rows",
			getOptions("rows", ddmFormField, ddmFormFieldRenderingContext));

		try {
			parameters.put(
				"value",
				jsonFactory.createJSONObject(
					ddmFormFieldRenderingContext.getValue()));
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}
		}

		return parameters;
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions(
		String optionType, DDMFormField ddmFormField) {

		return (DDMFormFieldOptions)ddmFormField.getProperty(optionType);
	}

	protected List<Object> getOptions(
		String key, DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		GridDDMFormFieldContextHelper gridDDMFormFieldContextHelper =
			new GridDDMFormFieldContextHelper(
				getDDMFormFieldOptions(key, ddmFormField),
				ddmFormFieldRenderingContext.getLocale());

		return gridDDMFormFieldContextHelper.getOptions();
	}

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		GridDDMFormFieldTemplateContextContributor.class);

}