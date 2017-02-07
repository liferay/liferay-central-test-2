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

package com.liferay.dynamic.data.mapping.internal.test.util;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;

/**
 * @author Lino Alves
 */
public class DDMFormFieldBuilder {

	public DDMFormField build() {
		String name = _fieldName;
		String label = _fieldName;
		String type = DDMFormFieldType.TEXT;
		String dataType = "string";
		boolean localizable = false;
		boolean repeatable = false;
		boolean required = true;

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			name, label, type, dataType, localizable, repeatable, required);

		ddmFormField.setIndexType(_indexType);

		return ddmFormField;
	}

	public void setFieldName(String fieldName) {
		_fieldName = fieldName;
	}

	public void setIndexType(String indexType) {
		_indexType = indexType;
	}

	private String _fieldName;
	private String _indexType;

}