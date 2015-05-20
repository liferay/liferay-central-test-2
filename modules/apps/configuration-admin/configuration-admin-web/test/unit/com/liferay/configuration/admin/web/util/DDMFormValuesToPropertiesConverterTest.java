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
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMFormTestUtil;

import java.util.Dictionary;
import java.util.Locale;
import java.util.Vector;

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
public class DDMFormValuesToPropertiesConverterTest extends Mockito {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		_jsonFactory = new JSONFactoryImpl();
	}

	@Test
	public void testArrayBooleanValues() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_enLocale);
		ddmForm.setDefaultLocale(_enLocale);

		DDMFormField booleanDDMFormField = DDMFormTestUtil.createDDMFormField(
			"Boolean", "Boolean", DDMFormFieldType.CHECKBOX, "boolean", false,
			true, false);

		ddmForm.addDDMFormField(booleanDDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(_enLocale);
		ddmFormValues.setDefaultLocale(_enLocale);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Boolean", "true", _enLocale));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Boolean", "false", _enLocale));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Boolean", "true", _enLocale));

		ObjectClassDefinition objectClassDefinition = mock(
			ObjectClassDefinition.class);

		AttributeDefinition attributeDefinition = mock(
			AttributeDefinition.class);

		Configuration configuration = mock(Configuration.class);

		whenObjectClassDefinitionGetAttributeDefinitions(
			objectClassDefinition,
			new AttributeDefinition[] {attributeDefinition});

		whenAttributeDefinitionGetCardinality(attributeDefinition, 3);
		whenAttributeDefinitionGetID(attributeDefinition, "Boolean");

		ConfigurationModel configurationModel = new ConfigurationModel(
			objectClassDefinition, configuration, null, false);

		DDMFormValuesToPropertiesConverter ddmFormValuesToPropertiesConverter =
			new DDMFormValuesToPropertiesConverter(
				configurationModel, ddmFormValues, _jsonFactory, _enLocale);

		Dictionary<String, Object> properties =
			ddmFormValuesToPropertiesConverter.getProperties();

		Object value = properties.get("Boolean");

		Boolean[] booleanValues = (Boolean[])value;

		Assert.assertEquals(true, booleanValues[0]);
		Assert.assertEquals(false, booleanValues[1]);
		Assert.assertEquals(true, booleanValues[2]);
	}

	@Test
	public void testSimpleBooleanValue() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_enLocale);
		ddmForm.setDefaultLocale(_enLocale);

		DDMFormField booleanDDMFormField = DDMFormTestUtil.createDDMFormField(
			"Boolean", "Boolean", DDMFormFieldType.CHECKBOX, "boolean", false,
			false, false);

		ddmForm.addDDMFormField(booleanDDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(_enLocale);
		ddmFormValues.setDefaultLocale(_enLocale);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Boolean", "true", _enLocale));

		ObjectClassDefinition objectClassDefinition = mock(
			ObjectClassDefinition.class);

		AttributeDefinition attributeDefinition = mock(
			AttributeDefinition.class);

		Configuration configuration = mock(Configuration.class);

		whenObjectClassDefinitionGetAttributeDefinitions(
			objectClassDefinition,
			new AttributeDefinition[] {attributeDefinition});

		whenAttributeDefinitionGetCardinality(attributeDefinition, 0);
		whenAttributeDefinitionGetID(attributeDefinition, "Boolean");

		ConfigurationModel configurationModel = new ConfigurationModel(
			objectClassDefinition, configuration, null, false);

		DDMFormValuesToPropertiesConverter ddmFormValuesToPropertiesConverter =
			new DDMFormValuesToPropertiesConverter(
				configurationModel, ddmFormValues, _jsonFactory, _enLocale);

		Dictionary<String, Object> properties =
			ddmFormValuesToPropertiesConverter.getProperties();

		Assert.assertEquals(true, properties.get("Boolean"));
	}

	@Test
	public void testSimpleIntegerValue() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_enLocale);
		ddmForm.setDefaultLocale(_enLocale);

		DDMFormField integerDDMFormField = DDMFormTestUtil.createDDMFormField(
			"Integer", "Integer", DDMFormFieldType.TEXT, "integer", false,
			false, false);

		ddmForm.addDDMFormField(integerDDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(_enLocale);
		ddmFormValues.setDefaultLocale(_enLocale);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Integer", "42", _enLocale));

		ObjectClassDefinition objectClassDefinition = mock(
			ObjectClassDefinition.class);

		AttributeDefinition attributeDefinition = mock(
			AttributeDefinition.class);

		Configuration configuration = mock(Configuration.class);

		whenObjectClassDefinitionGetAttributeDefinitions(
			objectClassDefinition,
			new AttributeDefinition[] {attributeDefinition});

		whenAttributeDefinitionGetCardinality(attributeDefinition, 0);
		whenAttributeDefinitionGetID(attributeDefinition, "Integer");

		ConfigurationModel configurationModel = new ConfigurationModel(
			objectClassDefinition, configuration, null, false);

		DDMFormValuesToPropertiesConverter ddmFormValuesToPropertiesConverter =
			new DDMFormValuesToPropertiesConverter(
				configurationModel, ddmFormValues, _jsonFactory, _enLocale);

		Dictionary<String, Object> properties =
			ddmFormValuesToPropertiesConverter.getProperties();

		Assert.assertEquals(42, properties.get("Integer"));
	}

	@Test
	public void testSimpleSelectValue() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_enLocale);
		ddmForm.setDefaultLocale(_enLocale);

		DDMFormField integerDDMFormField = DDMFormTestUtil.createDDMFormField(
			"Select", "Select", DDMFormFieldType.SELECT, "integer", false,
			false, false);

		ddmForm.addDDMFormField(integerDDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(_enLocale);
		ddmFormValues.setDefaultLocale(_enLocale);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Select", "[\"42\"]", _enLocale));

		ObjectClassDefinition objectClassDefinition = mock(
			ObjectClassDefinition.class);

		AttributeDefinition attributeDefinition = mock(
			AttributeDefinition.class);

		Configuration configuration = mock(Configuration.class);

		whenObjectClassDefinitionGetAttributeDefinitions(
			objectClassDefinition,
			new AttributeDefinition[] {attributeDefinition});

		whenAttributeDefinitionGetCardinality(attributeDefinition, 0);
		whenAttributeDefinitionGetID(attributeDefinition, "Select");

		ConfigurationModel configurationModel = new ConfigurationModel(
			objectClassDefinition, configuration, null, false);

		DDMFormValuesToPropertiesConverter ddmFormValuesToPropertiesConverter =
			new DDMFormValuesToPropertiesConverter(
				configurationModel, ddmFormValues, _jsonFactory, _enLocale);

		Dictionary<String, Object> properties =
			ddmFormValuesToPropertiesConverter.getProperties();

		Assert.assertEquals(42, properties.get("Select"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testVectorBooleanValues() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_enLocale);
		ddmForm.setDefaultLocale(_enLocale);

		DDMFormField booleanDDMFormField = DDMFormTestUtil.createDDMFormField(
			"Boolean", "Boolean", DDMFormFieldType.CHECKBOX, "boolean", false,
			true, false);

		ddmForm.addDDMFormField(booleanDDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(_enLocale);
		ddmFormValues.setDefaultLocale(_enLocale);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Boolean", "true", _enLocale));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Boolean", "false", _enLocale));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue("Boolean", "true", _enLocale));

		ObjectClassDefinition objectClassDefinition = mock(
			ObjectClassDefinition.class);

		AttributeDefinition attributeDefinition = mock(
			AttributeDefinition.class);

		Configuration configuration = mock(Configuration.class);

		whenObjectClassDefinitionGetAttributeDefinitions(
			objectClassDefinition,
			new AttributeDefinition[] {attributeDefinition});

		whenAttributeDefinitionGetCardinality(attributeDefinition, -3);
		whenAttributeDefinitionGetID(attributeDefinition, "Boolean");

		ConfigurationModel configurationModel = new ConfigurationModel(
			objectClassDefinition, configuration, null, false);

		DDMFormValuesToPropertiesConverter ddmFormValuesToPropertiesConverter =
			new DDMFormValuesToPropertiesConverter(
				configurationModel, ddmFormValues, _jsonFactory, _enLocale);

		Dictionary<String, Object> properties =
			ddmFormValuesToPropertiesConverter.getProperties();

		Object value = properties.get("Boolean");

		Vector<Boolean> booleanValues = (Vector<Boolean>)value;

		Assert.assertEquals(true, booleanValues.get(0));
		Assert.assertEquals(false, booleanValues.get(1));
		Assert.assertEquals(true, booleanValues.get(2));
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		String name, String valueString, Locale locale) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName(name);
		ddmFormFieldValue.setInstanceId(StringUtil.randomString());

		Value value = new LocalizedValue(locale);

		value.addString(locale, valueString);

		ddmFormFieldValue.setValue(value);

		return ddmFormFieldValue;
	}

	protected void whenAttributeDefinitionGetCardinality(
		AttributeDefinition attributeDefinition, int returnCardinality) {

		when(
			attributeDefinition.getCardinality()
		).thenReturn(
			returnCardinality
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

	protected void whenObjectClassDefinitionGetAttributeDefinitions(
		ObjectClassDefinition objectClassDefinition,
		AttributeDefinition[] returnAttributeDefinitions) {

		when(
			objectClassDefinition.getAttributeDefinitions(Matchers.anyInt())
		).thenReturn(
			returnAttributeDefinitions
		);
	}

	private final Locale _enLocale = LocaleUtil.US;
	private JSONFactory _jsonFactory;

}