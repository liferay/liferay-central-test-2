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

package com.liferay.dynamic.data.mapping.form.values.factory.impl;

import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormValuesJSONSerializerImpl;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormValuesJSONSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMFormTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMFormValuesTestUtil;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest({LocaleUtil.class})
@RunWith(PowerMockRunner.class)
public class DDMFormValuesFactoryImplTest extends PowerMockito {

	@Before
	public void setUp() {
		setUpDDMFormValuesJSONSerializerUtil();
		setUpJSONFactoryUtil();
		setUpLocaleUtil();
	}

	@Test
	public void testCreateDefaultWithEmptyRequest() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField nameDDMFormField = DDMFormTestUtil.createTextDDMFormField(
			"Name", true, false, false);

		LocalizedValue namePredefinedValue = createLocalizedValue(
			"Joe", LocaleUtil.US);

		nameDDMFormField.setPredefinedValue(namePredefinedValue);

		DDMFormField phoneDDMFormField = DDMFormTestUtil.createTextDDMFormField(
			"Phone", true, false, false);

		LocalizedValue phonePredefinedValue = createLocalizedValue(
			"123", LocaleUtil.US);

		phoneDDMFormField.setPredefinedValue(phonePredefinedValue);

		nameDDMFormField.addNestedDDMFormField(phoneDDMFormField);

		ddmForm.addDDMFormField(nameDDMFormField);

		DDMFormValues expectedDDMFormValues = createDDMFormValues(
			ddmForm, createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormFieldValue nameDDMFormFieldValue = createDDMFormFieldValue(
			"gatu", "Name", namePredefinedValue);

		nameDDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue("waht", "Phone", phonePredefinedValue));

		expectedDDMFormValues.addDDMFormFieldValue(nameDDMFormFieldValue);

		DDMFormValues actualDDMFormValues = _ddmFormValuesFactory.create(
			new MockHttpServletRequest(), ddmForm);

		List<DDMFormFieldValue> actualDDMFormFieldValues =
			actualDDMFormValues.getDDMFormFieldValues();

		// Name

		DDMFormFieldValue actualNameDDMFormFieldValue =
			actualDDMFormFieldValues.get(0);

		Value actualNameDDMFormFieldValueValue =
			actualNameDDMFormFieldValue.getValue();

		Assert.assertEquals(
			LocaleUtil.US, actualNameDDMFormFieldValueValue.getDefaultLocale());
		Assert.assertEquals(
			"Joe", actualNameDDMFormFieldValueValue.getString(LocaleUtil.US));

		// Phone

		List<DDMFormFieldValue> actualPhoneDDMFormFieldValues =
			actualNameDDMFormFieldValue.getNestedDDMFormFieldValues();

		DDMFormFieldValue actualPhoneDDMFormFieldValue =
			actualPhoneDDMFormFieldValues.get(0);

		Value actualPhoneDDMFormFieldValueValue =
			actualPhoneDDMFormFieldValue.getValue();

		Assert.assertEquals(
			LocaleUtil.US,
			actualPhoneDDMFormFieldValueValue.getDefaultLocale());
		Assert.assertEquals(
			"123", actualPhoneDDMFormFieldValueValue.getString(LocaleUtil.US));
	}

	@Test
	public void testCreateWithLocalizableFields() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("Title", "Content");

		DDMFormValues expectedDDMFormValues = createDDMFormValues(
			ddmForm, createAvailableLocales(LocaleUtil.BRAZIL, LocaleUtil.US),
			LocaleUtil.US);

		expectedDDMFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"wqer", "Title",
				createLocalizedValue("Title", "Titulo", LocaleUtil.US)));

		expectedDDMFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"thsy", "Content",
				createLocalizedValue("Content", "Conteudo", LocaleUtil.US)));

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.addParameter("availableLanguageIds", "en_US,pt_BR");
		httpServletRequest.addParameter(
			"defaultLanguageId", LocaleUtil.toLanguageId(LocaleUtil.US));

		// Title

		httpServletRequest.addParameter("ddm__Title_wqer_0__en_US", "Title");
		httpServletRequest.addParameter("ddm__Title_wqer_0__pt_BR", "Titulo");

		// Content

		httpServletRequest.addParameter(
			"ddm__Content_thsy_0__en_US", "Content");
		httpServletRequest.addParameter(
			"ddm__Content_thsy_0__pt_BR", "Conteudo");

		DDMFormValues actualDDMFormValues = _ddmFormValuesFactory.create(
			httpServletRequest, ddmForm);

		assertEquals(expectedDDMFormValues, actualDDMFormValues);
	}

	@Test
	public void testCreateWithRepeatableAndLocalizableField() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"Title", true, true, false);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues expectedDDMFormValues = createDDMFormValues(
			ddmForm, createAvailableLocales(LocaleUtil.BRAZIL, LocaleUtil.US),
			LocaleUtil.US);

		expectedDDMFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"wqer", "Title",
				createLocalizedValue("Title 1", "Titulo 1", LocaleUtil.US)));

		expectedDDMFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"fahu", "Title",
				createLocalizedValue("Title 2", "Titulo 2", LocaleUtil.US)));

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.addParameter("availableLanguageIds", "en_US,pt_BR");
		httpServletRequest.addParameter(
			"defaultLanguageId", LocaleUtil.toLanguageId(LocaleUtil.US));

		// Title

		httpServletRequest.addParameter("ddm__Title_wqer_0__en_US", "Title 1");
		httpServletRequest.addParameter("ddm__Title_wqer_0__pt_BR", "Titulo 1");
		httpServletRequest.addParameter("ddm__Title_fahu_1__en_US", "Title 2");
		httpServletRequest.addParameter("ddm__Title_fahu_1__pt_BR", "Titulo 2");

		DDMFormValues actualDDMFormValues = _ddmFormValuesFactory.create(
			httpServletRequest, ddmForm);

		assertEquals(expectedDDMFormValues, actualDDMFormValues);
	}

	@Test
	public void testCreateWithRepeatableAndLocalizableNestedField()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField nameDDMFormField = DDMFormTestUtil.createTextDDMFormField(
			"Name", true, true, false);

		DDMFormField phoneDDMFormField = DDMFormTestUtil.createTextDDMFormField(
			"Phone", true, true, false);

		nameDDMFormField.addNestedDDMFormField(phoneDDMFormField);

		ddmForm.addDDMFormField(nameDDMFormField);

		DDMFormValues expectedDDMFormValues = createDDMFormValues(
			ddmForm, createAvailableLocales(LocaleUtil.BRAZIL, LocaleUtil.US),
			LocaleUtil.US);

		DDMFormFieldValue paulDDMFormFieldValue = createDDMFormFieldValue(
			"wqer", "Name",
			createLocalizedValue("Paul", "Paulo", LocaleUtil.US));

		paulDDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"gatu", "Phone",
				createLocalizedValue("12", "34", LocaleUtil.US)));

		paulDDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"hato", "Phone",
				createLocalizedValue("56", "78", LocaleUtil.US)));

		expectedDDMFormValues.addDDMFormFieldValue(paulDDMFormFieldValue);

		DDMFormFieldValue joeDDMFormFieldValue = createDDMFormFieldValue(
			"fahu", "Name",
			createLocalizedValue("Joe", "Joao", LocaleUtil.US));

		joeDDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"jamh", "Phone",
				createLocalizedValue("90", "01", LocaleUtil.US)));

		expectedDDMFormValues.addDDMFormFieldValue(joeDDMFormFieldValue);

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.addParameter("availableLanguageIds", "en_US,pt_BR");
		httpServletRequest.addParameter(
			"defaultLanguageId", LocaleUtil.toLanguageId(LocaleUtil.US));

		// Name

		httpServletRequest.addParameter("ddm__Name_wqer_0__en_US", "Paul");
		httpServletRequest.addParameter("ddm__Name_wqer_0__pt_BR", "Paulo");
		httpServletRequest.addParameter("ddm__Name_fahu_1__en_US", "Joe");
		httpServletRequest.addParameter("ddm__Name_fahu_1__pt_BR", "Joao");

		// Phone

		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Phone_gatu_0__en_US", "12");
		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Phone_gatu_0__pt_BR", "34");
		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Phone_hato_1__en_US", "56");
		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Phone_hato_1__pt_BR", "78");
		httpServletRequest.addParameter(
			"ddm__Name_fahu_1#Phone_jamh_0__en_US", "90");
		httpServletRequest.addParameter(
			"ddm__Name_fahu_1#Phone_jamh_0__pt_BR", "01");

		DDMFormValues actualDDMFormValues = _ddmFormValuesFactory.create(
			httpServletRequest, ddmForm);

		assertEquals(expectedDDMFormValues, actualDDMFormValues);
	}

	@Test
	public void testCreateWithRepeatableAndLocalizableNestedFields()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField nameDDMFormField = DDMFormTestUtil.createTextDDMFormField(
			"Name", true, true, false);

		DDMFormField text1DDMFormField = DDMFormTestUtil.createTextDDMFormField(
			"Text1", true, true, false);

		nameDDMFormField.addNestedDDMFormField(text1DDMFormField);

		DDMFormField text2DDMFormField = DDMFormTestUtil.createTextDDMFormField(
			"Text2", true, true, false);

		nameDDMFormField.addNestedDDMFormField(text2DDMFormField);

		ddmForm.addDDMFormField(nameDDMFormField);

		DDMFormValues expectedDDMFormValues = createDDMFormValues(
			ddmForm, createAvailableLocales(LocaleUtil.BRAZIL, LocaleUtil.US),
			LocaleUtil.US);

		DDMFormFieldValue paulDDMFormFieldValue = createDDMFormFieldValue(
			"wqer", "Name",
			createLocalizedValue("Paul", "Paulo", LocaleUtil.US));

		paulDDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"gatu", "Text1",
				createLocalizedValue(
					"Text1 Paul One", "Text1 Paulo Um", LocaleUtil.US)));

		paulDDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"hayt", "Text1",
				createLocalizedValue(
					"Text1 Paul Two", "Text1 Paulo Dois", LocaleUtil.US)));

		paulDDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"haby", "Text2",
				createLocalizedValue(
					"Text2 Paul One", "Text2 Paulo Um", LocaleUtil.US)));

		paulDDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"makp", "Text2",
				createLocalizedValue(
					"Text2 Paul Two", "Text2 Paulo Dois", LocaleUtil.US)));

		expectedDDMFormValues.addDDMFormFieldValue(paulDDMFormFieldValue);

		DDMFormFieldValue joeDDMFormFieldValue = createDDMFormFieldValue(
			"fahu", "Name",
			createLocalizedValue("Joe", "Joao", LocaleUtil.US));

		joeDDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"banm", "Text1",
				createLocalizedValue(
					"Text1 Joe One", "Text1 Joao Um", LocaleUtil.US)));

		joeDDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"bagj", "Text2",
				createLocalizedValue(
					"Text2 Joe One", "Text2 Joao Um", LocaleUtil.US)));

		expectedDDMFormValues.addDDMFormFieldValue(joeDDMFormFieldValue);

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.addParameter("availableLanguageIds", "en_US,pt_BR");
		httpServletRequest.addParameter(
			"defaultLanguageId", LocaleUtil.toLanguageId(LocaleUtil.US));

		// Name

		httpServletRequest.addParameter("ddm__Name_wqer_0__en_US", "Paul");
		httpServletRequest.addParameter("ddm__Name_wqer_0__pt_BR", "Paulo");
		httpServletRequest.addParameter("ddm__Name_fahu_1__en_US", "Joe");
		httpServletRequest.addParameter("ddm__Name_fahu_1__pt_BR", "Joao");

		// Text 1

		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Text1_gatu_0__en_US", "Text1 Paul One");
		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Text1_gatu_0__pt_BR", "Text1 Paulo Um");
		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Text1_hayt_1__en_US", "Text1 Paul Two");
		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Text1_hayt_1__pt_BR", "Text1 Paulo Dois");
		httpServletRequest.addParameter(
			"ddm__Name_fahu_1#Text1_banm_0__en_US", "Text1 Joe One");
		httpServletRequest.addParameter(
			"ddm__Name_fahu_1#Text1_banm_0__pt_BR", "Text1 Joao Um");

		// Text 2

		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Text2_haby_0__en_US", "Text2 Paul One");
		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Text2_haby_0__pt_BR", "Text2 Paulo Um");
		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Text2_makp_1__en_US", "Text2 Paul Two");
		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Text2_makp_1__pt_BR", "Text2 Paulo Dois");
		httpServletRequest.addParameter(
			"ddm__Name_fahu_1#Text2_bagj_0__en_US", "Text2 Joe One");
		httpServletRequest.addParameter(
			"ddm__Name_fahu_1#Text2_bagj_0__pt_BR", "Text2 Joao Um");

		DDMFormValues actualDDMFormValues = _ddmFormValuesFactory.create(
			httpServletRequest, ddmForm);

		assertEquals(expectedDDMFormValues, actualDDMFormValues);
	}

	@Test
	public void testCreateWithRepeatableAndUnlocalizableNestedFields()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField nameDDMFormField = DDMFormTestUtil.createTextDDMFormField(
			"Name", false, true, false);

		DDMFormField phoneDDMFormField = DDMFormTestUtil.createTextDDMFormField(
			"Phone", false, true, false);

		DDMFormField extDDMFormField = DDMFormTestUtil.createTextDDMFormField(
			"Ext", false, true, false);

		phoneDDMFormField.addNestedDDMFormField(extDDMFormField);

		nameDDMFormField.addNestedDDMFormField(phoneDDMFormField);

		ddmForm.addDDMFormField(nameDDMFormField);

		DDMFormValues expectedDDMFormValues = createDDMFormValues(ddmForm);

		DDMFormFieldValue paulDDMFormFieldValue = createDDMFormFieldValue(
			"wqer", "Name", new UnlocalizedValue("Paul"));

		DDMFormFieldValue paulPhone1DDMFormFieldValue = createDDMFormFieldValue(
			"gatu", "Phone", new UnlocalizedValue("1"));

		paulPhone1DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"jkau", "Ext", new UnlocalizedValue("1.1")));

		paulPhone1DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"amat", "Ext", new UnlocalizedValue("1.2")));

		paulDDMFormFieldValue.addNestedDDMFormFieldValue(
			paulPhone1DDMFormFieldValue);

		DDMFormFieldValue paulPhone2DDMFormFieldValue = createDDMFormFieldValue(
			"hato", "Phone", new UnlocalizedValue("2"));

		paulPhone2DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"hamp", "Ext", new UnlocalizedValue("2.1")));

		paulPhone2DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"xzal", "Ext", new UnlocalizedValue("2.2")));

		paulPhone2DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"kaly", "Ext", new UnlocalizedValue("2.3")));

		paulDDMFormFieldValue.addNestedDDMFormFieldValue(
			paulPhone2DDMFormFieldValue);

		expectedDDMFormValues.addDDMFormFieldValue(paulDDMFormFieldValue);

		DDMFormFieldValue joeDDMFormFieldValue = createDDMFormFieldValue(
			"fahu", "Name", new UnlocalizedValue("Joe"));

		DDMFormFieldValue joePhone1DDMFormFieldValue = createDDMFormFieldValue(
			"jakl", "Phone", new UnlocalizedValue("3"));

		joePhone1DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"bagt", "Ext", new UnlocalizedValue("3.1")));

		joeDDMFormFieldValue.addNestedDDMFormFieldValue(
			joePhone1DDMFormFieldValue);

		expectedDDMFormValues.addDDMFormFieldValue(joeDDMFormFieldValue);

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.addParameter("availableLanguageIds", "en_US");
		httpServletRequest.addParameter(
			"defaultLanguageId", LocaleUtil.toLanguageId(LocaleUtil.US));

		// Name

		httpServletRequest.addParameter("ddm__Name_wqer_0__en_US", "Paul");
		httpServletRequest.addParameter("ddm__Name_fahu_1__en_US", "Joe");

		// Phone

		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Phone_gatu_0__en_US", "1");
		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Phone_hato_1__en_US", "2");
		httpServletRequest.addParameter(
			"ddm__Name_fahu_1#Phone_jakl_0__en_US", "3");

		// Ext

		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Phone_gatu_0#Ext_jkau_0__en_US", "1.1");
		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Phone_gatu_0#Ext_amat_1__en_US", "1.2");
		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Phone_hato_1#Ext_hamp_0__en_US", "2.1");
		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Phone_hato_1#Ext_xzal_1__en_US", "2.2");
		httpServletRequest.addParameter(
			"ddm__Name_wqer_0#Phone_hato_1#Ext_kaly_2__en_US", "2.3");
		httpServletRequest.addParameter(
			"ddm__Name_fahu_1#Phone_jakl_0#Ext_bagt_0__en_US", "3.1");

		DDMFormValues actualDDMFormValues = _ddmFormValuesFactory.create(
			httpServletRequest, ddmForm);

		assertEquals(expectedDDMFormValues, actualDDMFormValues);
	}

	@Test
	public void testCreateWithRepeatableTransientParent() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField separatorDDMFormField = DDMFormTestUtil.createDDMFormField(
			"Separator", "Separator", DDMFormFieldType.SEPARATOR,
			StringPool.BLANK, false, true, false);

		DDMFormField nameDDMFormField = DDMFormTestUtil.createTextDDMFormField(
			"Name", true, false, false);

		separatorDDMFormField.addNestedDDMFormField(nameDDMFormField);

		ddmForm.addDDMFormField(separatorDDMFormField);

		DDMFormValues expectedDDMFormValues = createDDMFormValues(
			ddmForm, createAvailableLocales(LocaleUtil.BRAZIL, LocaleUtil.US),
			LocaleUtil.US);

		DDMFormFieldValue separator1DDMFormFieldValue = createDDMFormFieldValue(
			"wqer", "Separator", null);

		separator1DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"gatu", "Name",
				createLocalizedValue("Joe", "Joao", LocaleUtil.US)));

		expectedDDMFormValues.addDDMFormFieldValue(separator1DDMFormFieldValue);

		DDMFormFieldValue separator2DDMFormFieldValue = createDDMFormFieldValue(
			"haby", "Separator", null);

		separator2DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"hato", "Name",
				createLocalizedValue("Paul", "Paulo", LocaleUtil.US)));

		expectedDDMFormValues.addDDMFormFieldValue(separator2DDMFormFieldValue);

		DDMFormFieldValue separator3DDMFormFieldValue = createDDMFormFieldValue(
			"bajk", "Separator", null);

		separator3DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"fahu", "Name",
				createLocalizedValue("Claude", "Claudio", LocaleUtil.US)));

		expectedDDMFormValues.addDDMFormFieldValue(separator3DDMFormFieldValue);

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.addParameter("availableLanguageIds", "en_US,pt_BR");
			httpServletRequest.addParameter(
				"defaultLanguageId", LocaleUtil.toLanguageId(LocaleUtil.US));

		// Name

		httpServletRequest.addParameter(
			"ddm__Separator_wqer_0#Name_gatu_0__en_US", "Joe");
		httpServletRequest.addParameter(
			"ddm__Separator_wqer_0#Name_gatu_0__pt_BR", "Joao");
		httpServletRequest.addParameter(
			"ddm__Separator_haby_1#Name_hato_0__en_US", "Paul");
		httpServletRequest.addParameter(
			"ddm__Separator_haby_1#Name_hato_0__pt_BR", "Paulo");
		httpServletRequest.addParameter(
			"ddm__Separator_bajk_2#Name_fahu_0__en_US", "Claude");
		httpServletRequest.addParameter(
			"ddm__Separator_bajk_2#Name_fahu_0__pt_BR", "Claudio");

		DDMFormValues actualDDMFormValues = _ddmFormValuesFactory.create(
			httpServletRequest, ddmForm);

		assertEquals(expectedDDMFormValues, actualDDMFormValues);
	}

	protected void assertEquals(
			DDMFormValues expectedDDMFormValues,
			DDMFormValues actualDDMFormValues)
		throws Exception {

		String serializedExpectedDDMFormValues =
			DDMFormValuesJSONSerializerUtil.serialize(expectedDDMFormValues);

		String serializedActualDDMFormValues =
			DDMFormValuesJSONSerializerUtil.serialize(actualDDMFormValues);

		JSONAssert.assertEquals(
			serializedExpectedDDMFormValues, serializedActualDDMFormValues,
			false);
	}

	protected Set<Locale> createAvailableLocales(Locale... locales) {
		return DDMFormValuesTestUtil.createAvailableLocales(locales);
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		String instanceId, String name, Value value) {

		return DDMFormValuesTestUtil.createDDMFormFieldValue(
			instanceId, name, value);
	}

	protected DDMFormValues createDDMFormValues(DDMForm ddmForm) {
		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(LocaleUtil.US);
		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		return ddmFormValues;
	}

	protected DDMFormValues createDDMFormValues(
		DDMForm ddmForm, Set<Locale> availableLocales, Locale defaultLocale) {

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.setAvailableLocales(availableLocales);
		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		return ddmFormValues;
	}

	protected LocalizedValue createLocalizedValue(
		String enValue, Locale defaultLocale) {

		return DDMFormValuesTestUtil.createLocalizedValue(
			enValue, defaultLocale);
	}

	protected LocalizedValue createLocalizedValue(
		String enValue, String ptValue, Locale defaultLocale) {

		return DDMFormValuesTestUtil.createLocalizedValue(
			enValue, ptValue, defaultLocale);
	}

	protected void setUpDDMFormValuesJSONSerializerUtil() {
		DDMFormValuesJSONSerializerUtil ddmFormValuesJSONSerializerUtil =
			new DDMFormValuesJSONSerializerUtil();

		ddmFormValuesJSONSerializerUtil.setDDMFormValuesJSONSerializer(
			new DDMFormValuesJSONSerializerImpl());
	}

	protected void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	protected void setUpLocaleUtil() {
		mockStatic(LocaleUtil.class);

		when(
			LocaleUtil.fromLanguageId("en_US")
		).thenReturn(
			LocaleUtil.US
		);

		when(
			LocaleUtil.fromLanguageId("pt_BR")
		).thenReturn(
			LocaleUtil.BRAZIL
		);

		when(
			LocaleUtil.toLanguageId(LocaleUtil.US)
		).thenReturn(
			"en_US"
		);

		when(
			LocaleUtil.toLanguageId(LocaleUtil.BRAZIL)
		).thenReturn(
			"pt_BR"
		);

		when(
			LocaleUtil.getSiteDefault()
		).thenReturn(
			LocaleUtil.US
		);
	}

	private final DDMFormValuesFactory _ddmFormValuesFactory =
		new DDMFormValuesFactoryImpl();

}