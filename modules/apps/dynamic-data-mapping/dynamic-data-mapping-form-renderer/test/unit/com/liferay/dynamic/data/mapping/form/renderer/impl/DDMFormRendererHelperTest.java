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

package com.liferay.dynamic.data.mapping.form.renderer.impl;

import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRendererConstants;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.internal.DDMFormRendererHelper;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldRenderer;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeRegistry;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeRegistryUtil;
import com.liferay.portlet.dynamicdatamapping.render.DDMFormFieldRenderingContext;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMFormValuesTestUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest({LocaleUtil.class, StringUtil.class})
@RunWith(PowerMockRunner.class)
public class DDMFormRendererHelperTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpDDMFormFieldTypeRegistryUtil();
		setUpLocaleUtil();
		setUpStringUtil();
	}

	@Test
	public void testGetRenderedDDMFormFieldsMapByDDMFormValues()
		throws Exception {

		DDMForm ddmForm = new DDMForm();

		DDMFormField contactDDMFormField = new DDMFormField("Contact", "text");

		contactDDMFormField.addNestedDDMFormField(
			new DDMFormField("FirstName", "text"));

		contactDDMFormField.addNestedDDMFormField(
			new DDMFormField("LastName", "text"));

		ddmForm.addDDMFormField(contactDDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		DDMFormFieldValue contact1DDMFormFieldValue = createDDMFormFieldValue(
			"asxd", "Contact", "Contact 1");

		contact1DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue("hegt", "FirstName", "Joe"));

		contact1DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue("nahy", "LastName", "Bloggs"));

		ddmFormValues.addDDMFormFieldValue(contact1DDMFormFieldValue);

		DDMFormFieldValue contact2DDMFormFieldValue = createDDMFormFieldValue(
			"uahy", "Contact", "Contact 2");

		contact2DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue("wbuy", "FirstName", "Elza"));

		contact2DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue("aaho", "LastName", "Fitzgerald"));

		ddmFormValues.addDDMFormFieldValue(contact2DDMFormFieldValue);

		String renderedContact1DDMFormFieldValue =
			renderContactDDMFormFieldValue(contact1DDMFormFieldValue, 0);

		String renderedContact2DDMFormFieldValue =
			renderContactDDMFormFieldValue(contact2DDMFormFieldValue, 1);

		String renderedContactDDMFormFieldValues =
			renderedContact1DDMFormFieldValue.concat(
				renderedContact2DDMFormFieldValue);

		DDMFormRenderingContext ddmFormRenderingContext =
			createDDMFormRenderingContext();

		ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

		DDMFormRendererHelper ddmFormRendererHelper = new DDMFormRendererHelper(
			ddmForm, ddmFormRenderingContext);

		Map<String, String> renderedDDMFormFieldsMap =
			ddmFormRendererHelper.getRenderedDDMFormFieldsMap();

		Assert.assertEquals(
			renderedContactDDMFormFieldValues,
			renderedDDMFormFieldsMap.get("Contact"));
	}

	@Test
	public void testGetRenderedDDMFormFieldsMapByDDMFormWithNestedFields()
		throws Exception {

		DDMForm ddmForm = new DDMForm();

		DDMFormField nameDDMFormField = new DDMFormField("Name", "text");
		DDMFormField phoneDDMFormField = new DDMFormField("Phone", "text");
		DDMFormField extDDMFormField = new DDMFormField("Ext", "text");

		phoneDDMFormField.addNestedDDMFormField(extDDMFormField);

		nameDDMFormField.addNestedDDMFormField(phoneDDMFormField);

		ddmForm.addDDMFormField(nameDDMFormField);

		String nameDDMFormFieldParameterName = getDDMFormFieldParameterName(
			"Name", StringPool.BLANK);

		String phoneDDMFormFieldParameterName = getDDMFormFieldParameterName(
			"Phone", nameDDMFormFieldParameterName);

		String extDDMFormFieldFieldParameterName = getDDMFormFieldParameterName(
			"Ext", phoneDDMFormFieldParameterName);

		String renderedExtDDMFormField = renderTextField(
			extDDMFormFieldFieldParameterName, StringPool.BLANK,
			StringPool.BLANK);

		String renderedPhoneDDMFormField = renderTextField(
			phoneDDMFormFieldParameterName, StringPool.BLANK,
			renderedExtDDMFormField);

		String renderedNameDDMFormField = renderTextField(
			nameDDMFormFieldParameterName, StringPool.BLANK,
			renderedPhoneDDMFormField);

		DDMFormRendererHelper ddmFormFieldRendererHelper =
			new DDMFormRendererHelper(ddmForm, createDDMFormRenderingContext());

		Map<String, String> renderedDDMFormFieldsMap =
			ddmFormFieldRendererHelper.getRenderedDDMFormFieldsMap();

		Assert.assertEquals(
			renderedNameDDMFormField, renderedDDMFormFieldsMap.get("Name"));
	}

	@Test
	public void testGetRenderedDDMFormFieldsMapByDDMFormWithoutNestedFields()
		throws Exception {

		DDMForm ddmForm = new DDMForm();

		ddmForm.addDDMFormField(new DDMFormField("Name", "text"));
		ddmForm.addDDMFormField(new DDMFormField("Age", "text"));

		String nameDDMFormFieldParameterName = getDDMFormFieldParameterName(
			"Name", StringPool.BLANK);

		String renderedNameDDMFormField = renderTextField(
			nameDDMFormFieldParameterName, StringPool.BLANK, StringPool.BLANK);

		String ageDDMFormFieldParameterName = getDDMFormFieldParameterName(
			"Age", StringPool.BLANK);

		String renderedAgeDDMFormField = renderTextField(
			ageDDMFormFieldParameterName, StringPool.BLANK, StringPool.BLANK);

		DDMFormRendererHelper ddmFormFieldRendererHelper =
			new DDMFormRendererHelper(ddmForm, createDDMFormRenderingContext());

		Map<String, String> renderedDDMFormFieldsMap =
			ddmFormFieldRendererHelper.getRenderedDDMFormFieldsMap();

		Assert.assertEquals(
			renderedNameDDMFormField, renderedDDMFormFieldsMap.get("Name"));
		Assert.assertEquals(
			renderedAgeDDMFormField, renderedDDMFormFieldsMap.get("Age"));
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		String instanceId, String name, String value) {

		return DDMFormValuesTestUtil.createDDMFormFieldValue(
			instanceId, name, new UnlocalizedValue(value));
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext() {
		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setLocale(_LOCALE);
		ddmFormRenderingContext.setPortletNamespace(_PORTLET_NAMESPACE);

		return ddmFormRenderingContext;
	}

	protected String getAffixedDDMFormFieldParameterName(
		String ddmFormFieldParameterName) {

		StringBundler sb = new StringBundler(5);

		sb.append(_PORTLET_NAMESPACE);
		sb.append(DDMFormRendererConstants.DDM_FORM_FIELD_NAME_PREFIX);
		sb.append(ddmFormFieldParameterName);
		sb.append(
			DDMFormRendererConstants.DDM_FORM_FIELD_LANGUAGE_ID_SEPARATOR);
		sb.append(LocaleUtil.toLanguageId(_LOCALE));

		return sb.toString();
	}

	protected String getDDMFormFieldParameterName(
		String ddmFormFieldName, String parentDDMFormFieldParameterName) {

		return getDDMFormFieldParameterName(
			ddmFormFieldName, _RANDOM_STRING, 0,
			parentDDMFormFieldParameterName);
	}

	protected String getDDMFormFieldParameterName(
		String ddmFormFieldName, String instanceId, int index) {

		return getDDMFormFieldParameterName(
			ddmFormFieldName, instanceId, index, StringPool.BLANK);
	}

	protected String getDDMFormFieldParameterName(
		String ddmFormFieldName, String instanceId, int index,
		String parentDDMFormFieldParameterName) {

		StringBundler sb = new StringBundler(7);

		if (Validator.isNotNull(parentDDMFormFieldParameterName)) {
			sb.append(parentDDMFormFieldParameterName);
			sb.append(DDMFormRendererConstants.DDM_FORM_FIELDS_SEPARATOR);
		}

		sb.append(ddmFormFieldName);
		sb.append(DDMFormRendererConstants.DDM_FORM_FIELD_PARTS_SEPARATOR);
		sb.append(instanceId);
		sb.append(DDMFormRendererConstants.DDM_FORM_FIELD_PARTS_SEPARATOR);
		sb.append(index);

		return sb.toString();
	}

	protected String renderContactDDMFormFieldValue(
		DDMFormFieldValue contactDDMFormFieldValue, int index) {

		String contactDDMFormFieldParameterName = getDDMFormFieldParameterName(
			contactDDMFormFieldValue.getName(),
			contactDDMFormFieldValue.getInstanceId(), index);

		List<DDMFormFieldValue> contactNestedDDMFormFieldValues =
			contactDDMFormFieldValue.getNestedDDMFormFieldValues();

		String renderedContactFirstName = renderContactNestedDDMFormFieldValue(
			contactDDMFormFieldParameterName,
			contactNestedDDMFormFieldValues.get(0));

		String renderedContactLastName = renderContactNestedDDMFormFieldValue(
			contactDDMFormFieldParameterName,
			contactNestedDDMFormFieldValues.get(1));

		String contactRenderedNestedDDMFormFieldValues =
			renderedContactFirstName.concat(renderedContactLastName);

		Value value = contactDDMFormFieldValue.getValue();

		return renderTextField(
			contactDDMFormFieldParameterName, value.getString(_LOCALE),
			contactRenderedNestedDDMFormFieldValues);
	}

	protected String renderContactNestedDDMFormFieldValue(
		String contactDDMFormFieldParameterName,
		DDMFormFieldValue ddmFormFieldValue) {

		String ddmFormFieldParameterName = getDDMFormFieldParameterName(
			ddmFormFieldValue.getName(), ddmFormFieldValue.getInstanceId(), 0,
			contactDDMFormFieldParameterName);

		Value value = ddmFormFieldValue.getValue();

		return renderTextField(
			ddmFormFieldParameterName, value.getString(_LOCALE),
			StringPool.BLANK);
	}

	protected String renderTextField(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		return StringUtil.replace(
			_INPUT_FIELD_TEMPLATE,
			new String[] {
				"$childElementsHTML", "$name", "$value"
			},
			new String[] {
				ddmFormFieldRenderingContext.getChildElementsHTML(),
				ddmFormFieldRenderingContext.getName(),
				ddmFormFieldRenderingContext.getValue()
			}
		);
	}

	protected String renderTextField(
		String ddmFormFieldParameterName, String value,
		String childElementsHTML) {

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		String name = getAffixedDDMFormFieldParameterName(
			ddmFormFieldParameterName);

		ddmFormFieldRenderingContext.setChildElementsHTML(childElementsHTML);
		ddmFormFieldRenderingContext.setName(name);
		ddmFormFieldRenderingContext.setValue(value);

		return renderTextField(ddmFormFieldRenderingContext);
	}

	protected void setUpDDMFormFieldRenderer() throws Exception {
		when(
			_ddmFormFieldRenderer.render(
				Matchers.any(DDMFormField.class),
				Matchers.any(DDMFormFieldRenderingContext.class))
		).then(
			new Answer<String>() {

				@Override
				public String answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Object[] args = invocationOnMock.getArguments();

					DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
						(DDMFormFieldRenderingContext)args[1];

					return renderTextField(ddmFormFieldRenderingContext);
				}

			}
		);
	}

	protected void setUpDDMFormFieldType() {
		when(
			_ddmFormFieldType.getDDMFormFieldRenderer()
		).thenReturn(
			_ddmFormFieldRenderer
		);
	}

	protected void setUpDDMFormFieldTypeRegistryUtil() throws Exception {
		setUpDDMFormFieldRenderer();
		setUpDDMFormFieldType();

		when(
			_ddmFormFieldTypeRegistry.getDDMFormFieldType(Matchers.anyString())
		).thenReturn(
			_ddmFormFieldType
		);

		DDMFormFieldTypeRegistryUtil ddmFormFieldTypeRegistryUtil =
			new DDMFormFieldTypeRegistryUtil();

		ddmFormFieldTypeRegistryUtil.setDDMFormFieldTypeRegistry(
			_ddmFormFieldTypeRegistry);
	}

	protected void setUpLocaleUtil() {
		mockStatic(LocaleUtil.class);

		when(
			LocaleUtil.fromLanguageId("en_US")
		).thenReturn(
			LocaleUtil.US
		);

		when(
			LocaleUtil.toLanguageId(LocaleUtil.US)
		).thenReturn(
			"en_US"
		);
	}

	protected void setUpStringUtil() {
		spy(StringUtil.class);

		when(
			StringUtil.randomString()
		).thenReturn(
			_RANDOM_STRING
		);
	}

	private static final String _INPUT_FIELD_TEMPLATE =
		"<div><input name=\"$name\" value=\"$value\">$childElementsHTML</div>";

	private static final Locale _LOCALE = LocaleUtil.US;

	private static final String _PORTLET_NAMESPACE = "_NAMESPACE_";

	private static final String _RANDOM_STRING = "_RANDOM_";

	@Mock
	private DDMFormFieldRenderer _ddmFormFieldRenderer;

	@Mock
	private DDMFormFieldType _ddmFormFieldType;

	@Mock
	private DDMFormFieldTypeRegistry _ddmFormFieldTypeRegistry;

}