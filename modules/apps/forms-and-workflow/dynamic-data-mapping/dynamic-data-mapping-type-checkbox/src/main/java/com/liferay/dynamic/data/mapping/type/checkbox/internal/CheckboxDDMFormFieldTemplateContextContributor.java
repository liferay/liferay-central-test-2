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

package com.liferay.dynamic.data.mapping.type.checkbox.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=checkbox",
	service = {
		CheckboxDDMFormFieldTemplateContextContributor.class,
		DDMFormFieldTemplateContextContributor.class
	}
)
public class CheckboxDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> parameters = new HashMap<>();

		boolean showAsSwitcher = GetterUtil.getBoolean(
			ddmFormField.getProperty("showAsSwitcher"));

		parameters.put("showAsSwitcher", showAsSwitcher);

		LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

		String status = getStatus(
			ddmFormFieldRenderingContext.getValue(),
			predefinedValue.getString(
				ddmFormFieldRenderingContext.getLocale()));

		parameters.put("status", status);

		return parameters;
	}

	protected String getStatus(String value, String predefinedValue) {
		String status = StringPool.BLANK;

		if (Objects.equals(value, "true")) {
			status = "checked";
		}
		else if (Objects.equals(predefinedValue, "true")) {
			status = "checked";
		}

		return status;
	}

}