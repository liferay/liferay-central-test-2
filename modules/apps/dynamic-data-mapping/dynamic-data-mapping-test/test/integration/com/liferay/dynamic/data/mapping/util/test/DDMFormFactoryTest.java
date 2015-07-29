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

import com.liferay.portlet.dynamicdatamapping.BaseDDMTestCase;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFactory;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeSettings;
import com.liferay.portlet.dynamicdatamapping.registry.DefaultDDMFormFieldTypeSettings;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFactoryTest extends BaseDDMTestCase {

	@Before
	public void setUp() {
		setUpLanguageUtil();
	}

	@Test
	public void testCreateDDMFormFieldTypeSettingsDDMForm() {
		DDMForm ddmForm = DDMFormFactory.create(DDMFormFieldTypeSettings.class);

		Assert.assertNotNull(ddmForm);

		assertRequiredDDMFormFieldTypeSettings(
			ddmForm.getDDMFormFieldsMap(false));
	}

	@Test
	public void testCreateDefaultDDMFormFieldTypeSettingsDDMForm() {
		DDMForm ddmForm = DDMFormFactory.create(
			DefaultDDMFormFieldTypeSettings.class);

		Assert.assertNotNull(ddmForm);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		assertRequiredDDMFormFieldTypeSettings(ddmFormFieldsMap);

		DDMFormField indexTypeDDMFormField = ddmFormFieldsMap.get("indexType");

		Assert.assertNotNull(indexTypeDDMFormField);
		Assert.assertEquals("string", indexTypeDDMFormField.getDataType());
		Assert.assertEquals("select", indexTypeDDMFormField.getType());

		DDMFormField labelDDMFormField = ddmFormFieldsMap.get("label");

		Assert.assertNotNull(labelDDMFormField);
		Assert.assertEquals("string", labelDDMFormField.getDataType());
		Assert.assertEquals("text", labelDDMFormField.getType());
		Assert.assertEquals(true, labelDDMFormField.isLocalizable());

		DDMFormField localizableDDMFormField = ddmFormFieldsMap.get(
			"localizable");

		Assert.assertNotNull(localizableDDMFormField);
		Assert.assertEquals("boolean", localizableDDMFormField.getDataType());
		Assert.assertEquals("checkbox", localizableDDMFormField.getType());

		DDMFormField predefinedValueDDMFormField = ddmFormFieldsMap.get(
			"predefinedValue");

		Assert.assertNotNull(predefinedValueDDMFormField);
		Assert.assertEquals(
			"string", predefinedValueDDMFormField.getDataType());
		Assert.assertEquals("text", predefinedValueDDMFormField.getType());
		Assert.assertEquals(true, predefinedValueDDMFormField.isLocalizable());

		DDMFormField readOnlyDDMFormField = ddmFormFieldsMap.get("readOnly");

		Assert.assertNotNull(readOnlyDDMFormField);
		Assert.assertEquals("boolean", readOnlyDDMFormField.getDataType());
		Assert.assertEquals("checkbox", readOnlyDDMFormField.getType());

		DDMFormField repeatableDDMFormField = ddmFormFieldsMap.get(
			"repeatable");

		Assert.assertNotNull(repeatableDDMFormField);
		Assert.assertEquals("boolean", repeatableDDMFormField.getDataType());
		Assert.assertEquals("checkbox", repeatableDDMFormField.getType());

		DDMFormField requiredDDMFormField = ddmFormFieldsMap.get("readOnly");

		Assert.assertNotNull(requiredDDMFormField);
		Assert.assertEquals("boolean", requiredDDMFormField.getDataType());
		Assert.assertEquals("checkbox", requiredDDMFormField.getType());

		DDMFormField showLabelDDMFormField = ddmFormFieldsMap.get("showLabel");

		Assert.assertNotNull(showLabelDDMFormField);
		Assert.assertEquals("boolean", showLabelDDMFormField.getDataType());
		Assert.assertEquals("checkbox", showLabelDDMFormField.getType());

		DDMFormField tipDDMFormField = ddmFormFieldsMap.get("tip");

		Assert.assertNotNull(tipDDMFormField);
		Assert.assertEquals("string", tipDDMFormField.getDataType());
		Assert.assertEquals("text", tipDDMFormField.getType());
		Assert.assertEquals(true, tipDDMFormField.isLocalizable());
	}

	protected void assertRequiredDDMFormFieldTypeSettings(
		Map<String, DDMFormField> ddmFormFieldsMap) {

		DDMFormField dataTypeDDMFormField = ddmFormFieldsMap.get("dataType");

		Assert.assertNotNull(dataTypeDDMFormField);
		Assert.assertEquals("string", dataTypeDDMFormField.getDataType());
		Assert.assertEquals("text", dataTypeDDMFormField.getType());
		Assert.assertEquals(false, dataTypeDDMFormField.isLocalizable());

		DDMFormField nameDDMFormField = ddmFormFieldsMap.get("name");

		Assert.assertNotNull(nameDDMFormField);
		Assert.assertEquals("string", nameDDMFormField.getDataType());
		Assert.assertEquals("text", nameDDMFormField.getType());
		Assert.assertEquals(false, nameDDMFormField.isLocalizable());

		DDMFormField typeDDMFormField = ddmFormFieldsMap.get("type");

		Assert.assertNotNull(typeDDMFormField);
		Assert.assertEquals("string", typeDDMFormField.getDataType());
		Assert.assertEquals("text", typeDDMFormField.getType());
		Assert.assertEquals(false, typeDDMFormField.isLocalizable());
	}

}