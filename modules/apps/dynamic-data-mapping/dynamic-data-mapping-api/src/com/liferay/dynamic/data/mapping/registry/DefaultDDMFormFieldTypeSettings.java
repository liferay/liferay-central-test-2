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

package com.liferay.dynamic.data.mapping.registry;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.registry.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.registry.annotations.DDMFormField;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Marcellus Tavares
 */
@DDMForm
public interface DefaultDDMFormFieldTypeSettings
	extends DDMFormFieldTypeSettings {

	@DDMFormField(visibilityExpression = "false")
	public String fieldNamespace();

	@DDMFormField(
		label = "%indexable",
		optionLabels = {
			"%not-indexable", "%indexable-keyword", "%indexable-text"
		},
		optionValues = {StringPool.BLANK, "keyword", "text"},
		type = "select", visibilityExpression = "false"
	)
	public String indexType();

	@DDMFormField(
		label = "%question",
		properties = {
			"placeholder=%type-your-question", "setting.category=basic",
			"setting.weight=4"
		},
		tip = "%type-what-you-want-to-ask", type = "text"
	)
	public LocalizedValue label();

	@DDMFormField(label = "%localizable", visibilityExpression = "false")
	public boolean localizable();

	@DDMFormField(
		label = "%predefined-value",
		properties = {"setting.category=advanced", "setting.weight=1"},
		tip = "%set-the-default-value-of-a-field", type = "text"
	)
	public LocalizedValue predefinedValue();

	@DDMFormField(label = "%read-only", visibilityExpression = "false")
	public boolean readOnly();

	@DDMFormField(
		label = "%repeatable",
		properties = {
			"setting.category=advanced", "setting.weight=3",
			"showAsSwitcher=true"
		}
	)
	public boolean repeatable();

	@DDMFormField(
		label = "%required",
		properties = {
			"setting.category=basic", "setting.weight=1", "showAsSwitcher=true"
		}
	)
	public boolean required();

	@DDMFormField(
		label = "%show-label",
		properties = {
			"setting.category=advanced", "setting.weight=4",
			"showAsSwitcher=true"
		}
	)
	public boolean showLabel();

	@DDMFormField(
		label = "%help-text",
		properties = {
			"placeholder=%add-text-to-help-users-better-understand-what-you-want",
			"setting.category=basic", "setting.weight=3"
		},
		tip = "%type-a-short-comment-to-help-users-understand-the-question",
		type = "text"
	)
	public LocalizedValue tip();

	@DDMFormField(
		dataType = "ddm-validation", label = "%validation",
		properties = {"setting.category=advanced", "setting.weight=5"},
		type = "validation"
	)
	public DDMFormFieldValidation validation();

	@DDMFormField(
		label = "%visibility",
		properties = {"setting.category=advanced", "setting.weight=0"}
	)
	public String visibilityExpression();

}