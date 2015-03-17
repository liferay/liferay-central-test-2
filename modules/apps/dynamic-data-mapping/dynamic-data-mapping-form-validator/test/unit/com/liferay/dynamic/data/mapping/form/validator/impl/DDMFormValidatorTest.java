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

package com.liferay.dynamic.data.mapping.form.validator.impl;

import static org.mockito.Mockito.when;

import com.liferay.dynamic.data.mapping.form.validator.DDMFormValidationException;
import com.liferay.dynamic.data.mapping.form.validator.DDMFormValidator;
import com.liferay.portal.bean.BeanPropertiesImpl;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeRegistry;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeRegistryUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMFormTestUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValidatorTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		setUpBeanPropertiesUtil();
		setUpDDMFormFieldTypeRegistryUtil();
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithDefaultLocaleMissingAsAvailableLocale()
		throws Exception {

		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(createAvailableLocales(LocaleUtil.BRAZIL));
		ddmForm.setDefaultLocale(LocaleUtil.US);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithDuplicateFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmForm.addDDMFormField(
			new DDMFormField("Name1", DDMFormFieldType.TEXT));

		DDMFormField name2DDMFormField = new DDMFormField(
			"Name2", DDMFormFieldType.TEXT);

		name2DDMFormField.addNestedDDMFormField(
			new DDMFormField("Name1", DDMFormFieldType.TEXT));

		ddmForm.addDDMFormField(name2DDMFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithInvalidFieldIndexType() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Text", DDMFormFieldType.TEXT);

		ddmFormField.setIndexType("Invalid");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithInvalidFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"*", DDMFormFieldType.TEXT);

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithNoOptionsSetForFieldOptions()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Select", DDMFormFieldType.SELECT);

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithNullAvailableLocales() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(null);
		ddmForm.setDefaultLocale(LocaleUtil.US);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithNullDefaultLocale() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(null);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test
	public void testValidationWithRegisteredTextFieldType() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Text", DDMFormFieldType.TEXT);

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithUnregisteredFieldType() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Name", "Unregistered Type");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test
	public void testValidationWithValidFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"valid_name", DDMFormFieldType.TEXT);

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithWrongAvailableLocalesSetForFieldOptions()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Select", DDMFormFieldType.SELECT);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel(
			"Value", LocaleUtil.BRAZIL, "Portuguese Label");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithWrongAvailableLocalesSetForLabel()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Text", DDMFormFieldType.TEXT);

		LocalizedValue label = ddmFormField.getLabel();

		label.addString(LocaleUtil.BRAZIL, "Portuguese Label");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithWrongDefaultLocaleSetForFieldOptions()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Select", DDMFormFieldType.SELECT);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel(
			"Value", LocaleUtil.US, "Value Label");

		ddmFormFieldOptions.setDefaultLocale(LocaleUtil.BRAZIL);

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = DDMFormValidationException.class)
	public void testValidationWithWrongDefaultLocaleSetForLabel()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Text", DDMFormFieldType.TEXT);

		LocalizedValue label = ddmFormField.getLabel();

		label.addString(LocaleUtil.US, "Label");

		label.setDefaultLocale(LocaleUtil.BRAZIL);

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	protected Set<Locale> createAvailableLocales(Locale... locales) {
		return DDMFormTestUtil.createAvailableLocales(locales);
	}

	protected void setUpBeanPropertiesUtil() {
		BeanPropertiesUtil beanPropertiesUtil = new BeanPropertiesUtil();

		beanPropertiesUtil.setBeanProperties(new BeanPropertiesImpl());
	}

	protected void setUpDDMFormFieldTypeRegistryUtil() {
		when(
			_ddmFormFieldTypeRegistry.getDDMFormFieldTypeNames()
		).thenReturn(
			new HashSet<String>(
				Arrays.asList(
					new String[] {
						DDMFormFieldType.TEXT, DDMFormFieldType.SELECT}))
		);

		DDMFormFieldTypeRegistryUtil ddmFormFieldTypeRegistryUtil =
			new DDMFormFieldTypeRegistryUtil();

		ddmFormFieldTypeRegistryUtil.setDDMFormFieldTypeRegistry(
			_ddmFormFieldTypeRegistry);
	}

	@Mock
	private DDMFormFieldTypeRegistry _ddmFormFieldTypeRegistry;

	private final DDMFormValidator _ddmFormValidator =
		new DDMFormValidatorImpl();

}