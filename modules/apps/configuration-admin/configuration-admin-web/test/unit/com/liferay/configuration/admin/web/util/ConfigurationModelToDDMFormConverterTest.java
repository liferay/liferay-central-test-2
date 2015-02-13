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

package com.liferay.configuration.admin.web.util;

import com.liferay.configuration.admin.web.model.ConfigurationModel;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.osgi.service.cm.Configuration;
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.ObjectClassDefinition;

/**
 * @author Marcellus Tavares
 */
public class ConfigurationModelToDDMFormConverterTest extends Mockito {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreateDDMFormWithCheckboxField() {
		ObjectClassDefinition objectClassDefinition = mock(
			ObjectClassDefinition.class);

		AttributeDefinition attributeDefinition = mock(
			AttributeDefinition.class);

		whenObjectClassDefinitionGetAttributeDefinitions(
			objectClassDefinition,
			new AttributeDefinition[] {attributeDefinition},
			ObjectClassDefinition.REQUIRED);

		whenAttributeDefinitionGetCardinality(attributeDefinition, 0);
		whenAttributeDefinitionGetID(attributeDefinition, "Boolean");
		whenAttributeDefinitionGetType(
			attributeDefinition, AttributeDefinition.BOOLEAN);

		ConfigurationModel configurationModel = new ConfigurationModel(
			objectClassDefinition, null, null, false);

		ConfigurationModelToDDMFormConverter
			configurationModelToDDMFormConverter =
				new ConfigurationModelToDDMFormConverter(
					configurationModel, _enLocale);

		DDMForm ddmForm = configurationModelToDDMFormConverter.getDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField ddmFormField = ddmFormFieldsMap.get("Boolean");

		Assert.assertNotNull(ddmFormField);
		Assert.assertEquals(DDMFormFieldType.CHECKBOX, ddmFormField.getType());
		Assert.assertEquals("boolean", ddmFormField.getDataType());
		Assert.assertFalse(ddmFormField.isRepeatable());
		Assert.assertFalse(ddmFormField.isRequired());
	}

	@Test
	public void testCreateDDMFormWithIntegerFieldAndPredefinedValue() {
		ObjectClassDefinition objectClassDefinition = mock(
			ObjectClassDefinition.class);

		AttributeDefinition attributeDefinition = mock(
			AttributeDefinition.class);

		whenObjectClassDefinitionGetAttributeDefinitions(
			objectClassDefinition,
			new AttributeDefinition[] {attributeDefinition},
			ObjectClassDefinition.REQUIRED);

		whenAttributeDefinitionGetCardinality(attributeDefinition, 0);
		whenAttributeDefinitionGetID(attributeDefinition, "Integer");
		whenAttributeDefinitionGetType(
			attributeDefinition, AttributeDefinition.INTEGER);

		String randomIntString = String.valueOf(RandomTestUtil.randomInt());

		whenAttributeDefinitionGetDefaultValue(
			attributeDefinition, new String[] {randomIntString});

		ConfigurationModel configurationModel = new ConfigurationModel(
			objectClassDefinition, null, null, false);

		ConfigurationModelToDDMFormConverter
			configurationModelToDDMFormConverter =
				new ConfigurationModelToDDMFormConverter(
					configurationModel, _enLocale);

		DDMForm ddmForm = configurationModelToDDMFormConverter.getDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField ddmFormField = ddmFormFieldsMap.get("Integer");

		Assert.assertNotNull(ddmFormField);
		Assert.assertEquals(DDMFormFieldType.INTEGER, ddmFormField.getType());
		Assert.assertEquals("integer", ddmFormField.getDataType());
		Assert.assertFalse(ddmFormField.isRepeatable());
		Assert.assertTrue(ddmFormField.isRequired());

		LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

		Assert.assertEquals(_enLocale, predefinedValue.getDefaultLocale());
		Assert.assertEquals(
			randomIntString, predefinedValue.getString(_enLocale));
	}

	@Test
	public void testCreateDDMFormWithSelectField() {
		ObjectClassDefinition objectClassDefinition = mock(
			ObjectClassDefinition.class);

		AttributeDefinition attributeDefinition = mock(
			AttributeDefinition.class);

		whenObjectClassDefinitionGetAttributeDefinitions(
			objectClassDefinition,
			new AttributeDefinition[] {attributeDefinition},
			ObjectClassDefinition.REQUIRED);

		whenAttributeDefinitionGetCardinality(attributeDefinition, 0);
		whenAttributeDefinitionGetID(attributeDefinition, "Select");
		whenAttributeDefinitionGetType(
			attributeDefinition, AttributeDefinition.STRING);
		whenAttributeDefinitionGetOptionLabels(
			attributeDefinition, new String[] {"Label 1", "Label 2"});
		whenAttributeDefinitionGetOptionValues(
			attributeDefinition, new String[] {"Value 1", "Value 2"});

		ConfigurationModel configurationModel = new ConfigurationModel(
			objectClassDefinition, null, null, false);

		ConfigurationModelToDDMFormConverter
			configurationModelToDDMFormConverter =
				new ConfigurationModelToDDMFormConverter(
					configurationModel, _enLocale);

		DDMForm ddmForm = configurationModelToDDMFormConverter.getDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField ddmFormField = ddmFormFieldsMap.get("Select");

		Assert.assertNotNull(ddmFormField);
		Assert.assertEquals(DDMFormFieldType.SELECT, ddmFormField.getType());
		Assert.assertEquals("string", ddmFormField.getDataType());
		Assert.assertFalse(ddmFormField.isRepeatable());
		Assert.assertTrue(ddmFormField.isRequired());

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		Assert.assertEquals(_enLocale, ddmFormFieldOptions.getDefaultLocale());

		Set<String> optionValues = ddmFormFieldOptions.getOptionsValues();

		Assert.assertTrue(optionValues.contains("Value 1"));
		Assert.assertTrue(optionValues.contains("Value 2"));

		LocalizedValue value1Labels = ddmFormFieldOptions.getOptionLabels(
			"Value 1");

		Assert.assertEquals(_enLocale, value1Labels.getDefaultLocale());
		Assert.assertEquals("Label 1", value1Labels.getString(_enLocale));

		LocalizedValue value2Labels = ddmFormFieldOptions.getOptionLabels(
			"Value 2");

		Assert.assertEquals(_enLocale, value2Labels.getDefaultLocale());
		Assert.assertEquals("Label 2", value2Labels.getString(_enLocale));
	}

	@Test
	public void testCreateDDMFormWithTextFieldAndPredefinedValue() {
		ObjectClassDefinition objectClassDefinition = mock(
			ObjectClassDefinition.class);

		AttributeDefinition attributeDefinition = mock(
			AttributeDefinition.class);

		whenObjectClassDefinitionGetAttributeDefinitions(
			objectClassDefinition,
			new AttributeDefinition[] {attributeDefinition},
			ObjectClassDefinition.OPTIONAL);

		whenAttributeDefinitionGetCardinality(attributeDefinition, 0);
		whenAttributeDefinitionGetID(attributeDefinition, "Text");
		whenAttributeDefinitionGetType(
			attributeDefinition, AttributeDefinition.STRING);

		Configuration configuration = mock(Configuration.class);

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("Text", "Ella Fitzgerald");

		whenConfigurationGetProperties(configuration, properties);

		ConfigurationModel configurationModel = new ConfigurationModel(
			objectClassDefinition, configuration, null, false);

		ConfigurationModelToDDMFormConverter
			configurationModelToDDMFormConverter =
				new ConfigurationModelToDDMFormConverter(
					configurationModel, _enLocale);

		DDMForm ddmForm = configurationModelToDDMFormConverter.getDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField ddmFormField = ddmFormFieldsMap.get("Text");

		Assert.assertNotNull(ddmFormField);
		Assert.assertEquals(DDMFormFieldType.TEXT, ddmFormField.getType());
		Assert.assertEquals("string", ddmFormField.getDataType());
		Assert.assertFalse(ddmFormField.isRepeatable());
		Assert.assertFalse(ddmFormField.isRequired());

		LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

		Assert.assertEquals(_enLocale, predefinedValue.getDefaultLocale());
		Assert.assertEquals(
			"Ella Fitzgerald", predefinedValue.getString(_enLocale));
	}

	protected void whenAttributeDefinitionGetCardinality(
		AttributeDefinition attributeDefinition, int returnCardinality) {

		when(
			attributeDefinition.getCardinality()
		).thenReturn(
			returnCardinality
		);
	}

	protected void whenAttributeDefinitionGetDefaultValue(
		AttributeDefinition attributeDefinition, String[] returnDefaultValue) {

		when(
			attributeDefinition.getDefaultValue()
		).thenReturn(
			returnDefaultValue
		);
	}

	protected void whenAttributeDefinitionGetID(
		AttributeDefinition attributeDefinition, String returnID) {

		when(
			attributeDefinition.getID()
		).thenReturn(
			returnID
		);
	}

	protected void whenAttributeDefinitionGetOptionLabels(
		AttributeDefinition attributeDefinition, String[] returnOptionLabels) {

		when(
			attributeDefinition.getOptionLabels()
		).thenReturn(
			returnOptionLabels
		);
	}

	protected void whenAttributeDefinitionGetOptionValues(
		AttributeDefinition attributeDefinition, String[] returnOptionValues) {

		when(
			attributeDefinition.getOptionValues()
		).thenReturn(
			returnOptionValues
		);
	}

	protected void whenAttributeDefinitionGetType(
		AttributeDefinition attributeDefinition, int returnType) {

		when(
			attributeDefinition.getType()
		).thenReturn(
			returnType
		);
	}

	protected void whenConfigurationGetProperties(
		Configuration configuration,
		Dictionary<String, Object> returnProperties) {

		when(
			configuration.getProperties()
		).thenReturn(
			returnProperties
		);
	}

	protected void whenObjectClassDefinitionGetAttributeDefinitions(
		ObjectClassDefinition objectClassDefinition,
		AttributeDefinition[] returnAttributeDefinitions, int filter) {

		when(
			objectClassDefinition.getAttributeDefinitions(Matchers.eq(filter))
		).thenReturn(
			returnAttributeDefinitions
		);
	}

	private final Locale _enLocale = LocaleUtil.US;

}