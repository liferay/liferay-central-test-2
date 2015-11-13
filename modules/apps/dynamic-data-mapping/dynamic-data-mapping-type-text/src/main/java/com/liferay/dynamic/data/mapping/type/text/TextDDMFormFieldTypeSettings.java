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

package com.liferay.dynamic.data.mapping.type.text;

import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.registry.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.registry.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.registry.annotations.DDMFormField;

/**
 * @author Lino Alves
*/
@DDMForm
public interface TextDDMFormFieldTypeSettings
	extends DefaultDDMFormFieldTypeSettings {

	@DDMFormField(
		label = "%my-text-field-is",
		optionLabels = {"%single-line", "%multiple-lines"},
		optionValues = {"singleline", "multiline"},
		properties = {
			"inline=true", "setting.category=basic", "setting.weight=2"
		},
		type = "radio"
	)
	public String displayStyle();

	@DDMFormField(
		dataType = "string", label = "%field-tip",
		properties = {"setting.category=advanced", "setting.weight=2"},
		type = "text"
	)
	public LocalizedValue placeholder();

}