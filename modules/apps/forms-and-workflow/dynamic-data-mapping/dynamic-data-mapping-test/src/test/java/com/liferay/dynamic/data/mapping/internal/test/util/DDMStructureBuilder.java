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

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializer;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormFieldJSONObjectTransformerImpl;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormJSONSerializerImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureImpl;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class DDMStructureBuilder {

	public DDMStructure build() {
		DDMStructure ddmStructure = new DDMStructureImpl();

		ddmStructure.setDefinition(_ddmFormJSONSerializer.serialize(_ddmForm));

		ddmStructure.setDDMForm(_ddmForm);

		ddmStructure.setName(RandomTestUtil.randomString());
		ddmStructure.setStructureId(RandomTestUtil.randomLong());

		return ddmStructure;
	}

	public void setDDMForm(DDMForm ddmForm) {
		_ddmForm = ddmForm;
	}

	protected DDMFormFieldJSONObjectTransformerImpl
		createDDMFormFieldJSONObjectTransformer() {

		return new DDMFormFieldJSONObjectTransformerImpl() {
			{
				ddmFormFieldTypeServicesTracker =
					createDDMFormFieldTypeServicesTracker();
				jsonFactory = new JSONFactoryImpl();

				activate();
			}
		};
	}

	protected DDMFormFieldTypeServicesTracker
		createDDMFormFieldTypeServicesTracker() {

		return Mockito.mock(DDMFormFieldTypeServicesTracker.class);
	}

	protected DDMFormJSONSerializer createDDMFormJSONSerializer() {
		return new DDMFormJSONSerializerImpl() {

			{
				ddmFormFieldJSONObjectTransformer =
					createDDMFormFieldJSONObjectTransformer();
				jsonFactory = new JSONFactoryImpl();
			}

		};
	}

	private DDMForm _ddmForm;
	private final DDMFormJSONSerializer _ddmFormJSONSerializer =
		createDDMFormJSONSerializer();

}