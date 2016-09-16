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

package com.liferay.dynamic.data.mapping.form.evaluator.impl.internal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;

/**
 * @author Leonardo Barros
 * @author Marcellus Tavares
 */
public class DDMFormEvaluatorHelperTest {

	@Before
	public void setUp() {
		setUpLanguageUtil();
	}

	@Test
	public void testRequiredValidation() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField = createDDMFormField(
			"field0", "checkbox", FieldConstants.BOOLEAN);

		ddmFormField.setRequired(true);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("false")));

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory, ddmForm, ddmFormValues, null,
				_jsonFactory, LocaleUtil.US);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		Assert.assertEquals(1, ddmFormFieldEvaluationResultMap.size());

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field0", "field0_instanceId");

		Assert.assertEquals(
			"This field is required.",
			ddmFormFieldEvaluationResult.getErrorMessage());
		Assert.assertFalse(ddmFormFieldEvaluationResult.isValid());
	}

	@Test
	public void testRequiredValidationWithHiddenField() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addDDMFormField(
			createDDMFormField("field0", "text", FieldConstants.INTEGER));

		DDMFormField field1DDMFormField = createDDMFormField(
			"field1", "text", FieldConstants.STRING);

		field1DDMFormField.setRequired(true);

		field1DDMFormField.setVisibilityExpression("field0 > 5");

		ddmForm.addDDMFormField(field1DDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("4")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("")));

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory, ddmForm, ddmFormValues, null,
				_jsonFactory, LocaleUtil.US);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		Assert.assertEquals(2, ddmFormFieldEvaluationResultMap.size());

		DDMFormFieldEvaluationResult field1DDMFormFieldEvaluationResult =
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field1", "field1_instanceId");

		Assert.assertEquals(
			StringPool.BLANK,
			field1DDMFormFieldEvaluationResult.getErrorMessage());
		Assert.assertTrue(field1DDMFormFieldEvaluationResult.isValid());
	}

	@Test
	public void testShowHideAndEnableDisableRules() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addDDMFormField(
			createDDMFormField("field0", "text", FieldConstants.DOUBLE));

		ddmForm.addDDMFormField(
			createDDMFormField("field1", "text", FieldConstants.DOUBLE));

		ddmForm.addDDMFormField(
			createDDMFormField("field2", "text", FieldConstants.DOUBLE));

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("30")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("15")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field2_instanceId", "field2", new UnlocalizedValue("10")));

		String condition = "get(fieldAt(\"field0\", 0), \"value\") >= 30";

		List<String> actions = ListUtil.fromArray(
			new String[] {
				"set(fieldAt(\"field1\", 0), \"visible\", false)",
				"set(fieldAt(\"field2\", 0), \"readOnly\", true)"
			});

		DDMFormRule ddmFormRule = new DDMFormRule(condition, actions);

		ddmForm.addDDMFormRule(ddmFormRule);

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory, ddmForm, ddmFormValues, null,
				_jsonFactory, LocaleUtil.US);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		Assert.assertEquals(3, ddmFormFieldEvaluationResultMap.size());

		// Field 0

		DDMFormFieldEvaluationResult
			expectedField0DDMFormFieldEvaluationResult =
				createDDMFormFieldEvaluationResult(
					"field0", "field0_instanceId", StringPool.BLANK, true, 30.,
					true, false, false);

		assertEquals(
			expectedField0DDMFormFieldEvaluationResult,
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field0", "field0_instanceId"));

		// Field 1

		DDMFormFieldEvaluationResult
			expectedField1DDMFormFieldEvaluationResult =
				createDDMFormFieldEvaluationResult(
					"field1", "field1_instanceId", StringPool.BLANK, true, 15.,
					false, false, false);

		assertEquals(
			expectedField1DDMFormFieldEvaluationResult,
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field1", "field1_instanceId"));

		// Field 2

		DDMFormFieldEvaluationResult
			expectedField2DDMFormFieldEvaluationResult =
				createDDMFormFieldEvaluationResult(
					"field2", "field2_instanceId", StringPool.BLANK, true, 10.,
					true, true, false);

		assertEquals(
			expectedField2DDMFormFieldEvaluationResult,
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field2", "field2_instanceId"));
	}

	@Test
	public void testUpdateAndCalculateRule() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addDDMFormField(
			createDDMFormField("field0", "text", FieldConstants.DOUBLE));

		ddmForm.addDDMFormField(
			createDDMFormField("field1", "text", FieldConstants.DOUBLE));

		ddmForm.addDDMFormField(
			createDDMFormField("field2", "text", FieldConstants.DOUBLE));

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("5")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("2")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field2_instanceId", "field2", new UnlocalizedValue("0")));

		String condition =
			"get(fieldAt('field0', 0), 'value') > 0 && " +
				"get(fieldAt('field1', 0), 'value') > 0";

		String action =
			"set(fieldAt('field2', 0), 'value', " +
				"get(fieldAt('field0', 0), 'value') * " +
					"get(fieldAt('field1', 0), 'value'))";

		DDMFormRule ddmFormRule = new DDMFormRule(
			condition, Arrays.asList(action));

		ddmForm.addDDMFormRule(ddmFormRule);

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory, ddmForm, ddmFormValues, null,
				_jsonFactory, LocaleUtil.US);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		Assert.assertEquals(3, ddmFormFieldEvaluationResultMap.size());

		// Field 0

		DDMFormFieldEvaluationResult
			expectedField0DDMFormFieldEvaluationResult =
				createDDMFormFieldEvaluationResult(
					"field0", "field0_instanceId", 5.0);

		assertEquals(
			expectedField0DDMFormFieldEvaluationResult,
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field0", "field0_instanceId"));

		// Field 1

		DDMFormFieldEvaluationResult
			expectedField1DDMFormFieldEvaluationResult =
				createDDMFormFieldEvaluationResult(
					"field1", "field1_instanceId", 2.0);

		assertEquals(
			expectedField1DDMFormFieldEvaluationResult,
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field1", "field1_instanceId"));

		// Field 2

		DDMFormFieldEvaluationResult
			expectedField2DDMFormFieldEvaluationResult =
				createDDMFormFieldEvaluationResult(
					"field2", "field2_instanceId", 10.0);

		assertEquals(
			expectedField2DDMFormFieldEvaluationResult,
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field2", "field2_instanceId"));
	}

	@Test
	public void testValidationExpression() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField = createDDMFormField(
			"field0", "text", FieldConstants.INTEGER);

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setErrorMessage("This field should be zero.");
		ddmFormFieldValidation.setExpression("field0 == 0");

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("1")));

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory, ddmForm, ddmFormValues, null,
				_jsonFactory, LocaleUtil.US);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		Assert.assertEquals(1, ddmFormFieldEvaluationResultMap.size());

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field0", "field0_instanceId");

		Assert.assertEquals(
			"This field should be zero.",
			ddmFormFieldEvaluationResult.getErrorMessage());
		Assert.assertFalse(ddmFormFieldEvaluationResult.isValid());
	}

	@Test
	public void testValidationRule() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addDDMFormField(
			createDDMFormField("field0", "text", FieldConstants.DOUBLE));

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("5")));

		String condition = "get(fieldAt('field0', 0), 'value') <= 10";

		String action =
			"set(fieldAt('field0', 0), 'valid', false, " +
				"'The value should be greater than 10.')";

		DDMFormRule ddmFormRule = new DDMFormRule(
			condition, Arrays.asList(action));

		ddmForm.addDDMFormRule(ddmFormRule);

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory, ddmForm, ddmFormValues, null,
				_jsonFactory, LocaleUtil.US);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		Assert.assertEquals(1, ddmFormFieldEvaluationResultMap.size());

		DDMFormFieldEvaluationResult actualDDMFormFieldEvaluationResult =
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field0", "field0_instanceId");

		Assert.assertEquals(
			"The value should be greater than 10.",
			actualDDMFormFieldEvaluationResult.getErrorMessage());
		Assert.assertEquals(
			false, actualDDMFormFieldEvaluationResult.isValid());
	}

	@Test
	public void testVisibilityExpression() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addDDMFormField(
			createDDMFormField("field0", "text", FieldConstants.INTEGER));

		DDMFormField field1DDMFormField = createDDMFormField(
			"field1", "text", FieldConstants.STRING);

		field1DDMFormField.setVisibilityExpression("field0 > 5");

		ddmForm.addDDMFormField(field1DDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("6")));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("")));

		DDMFormEvaluatorHelper ddmFormEvaluatorHelper =
			new DDMFormEvaluatorHelper(
				null, null, _ddmExpressionFactory, ddmForm, ddmFormValues, null,
				_jsonFactory, LocaleUtil.US);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluatorHelper.evaluate();

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		Assert.assertEquals(2, ddmFormFieldEvaluationResultMap.size());

		DDMFormFieldEvaluationResult field1DDMFormFieldEvaluationResult =
			ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
				"field1", "field1_instanceId");

		Assert.assertTrue(field1DDMFormFieldEvaluationResult.isVisible());
	}

	protected void assertEquals(
		DDMFormFieldEvaluationResult expectedDDMFormFieldEvaluationResult,
		DDMFormFieldEvaluationResult actualDDMFormFieldEvaluationResult) {

		Assert.assertEquals(
			expectedDDMFormFieldEvaluationResult,
			actualDDMFormFieldEvaluationResult);

		// Properties

		Assert.assertEquals(
			expectedDDMFormFieldEvaluationResult.getErrorMessage(),
			actualDDMFormFieldEvaluationResult.getErrorMessage());
		Assert.assertEquals(
			expectedDDMFormFieldEvaluationResult.isValid(),
			actualDDMFormFieldEvaluationResult.isValid());
		Assert.assertEquals(
			expectedDDMFormFieldEvaluationResult.getValue(),
			actualDDMFormFieldEvaluationResult.getValue());
		Assert.assertEquals(
			expectedDDMFormFieldEvaluationResult.isVisible(),
			actualDDMFormFieldEvaluationResult.isVisible());
		Assert.assertEquals(
			expectedDDMFormFieldEvaluationResult.isReadOnly(),
			actualDDMFormFieldEvaluationResult.isReadOnly());
		Assert.assertEquals(
			expectedDDMFormFieldEvaluationResult.isRequired(),
			actualDDMFormFieldEvaluationResult.isRequired());
	}

	protected DDMFormField createDDMFormField(
		String name, String type, String dataType) {

		DDMFormField ddmFormField = new DDMFormField(name, type);

		ddmFormField.setDataType(dataType);

		return ddmFormField;
	}

	protected DDMFormFieldEvaluationResult createDDMFormFieldEvaluationResult(
		String name, String instanceId, Object value) {

		return createDDMFormFieldEvaluationResult(
			name, instanceId, StringPool.BLANK, true, value, true, false,
			false);
	}

	protected DDMFormFieldEvaluationResult createDDMFormFieldEvaluationResult(
		String name, String instanceId, String errorMessage, boolean valid,
		Object value, boolean visible, boolean readOnly, boolean required) {

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(name, instanceId);

		ddmFormFieldEvaluationResult.setErrorMessage(errorMessage);
		ddmFormFieldEvaluationResult.setValid(valid);
		ddmFormFieldEvaluationResult.setValue(value);
		ddmFormFieldEvaluationResult.setVisible(visible);
		ddmFormFieldEvaluationResult.setReadOnly(readOnly);
		ddmFormFieldEvaluationResult.setRequired(required);

		return ddmFormFieldEvaluationResult;
	}

	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		_language = mock(Language.class);

		when(
			_language.get(
				Matchers.eq(Locale.US), Matchers.eq("this-field-is-required"))
		).thenReturn(
			"This field is required."
		);

		languageUtil.setLanguage(_language);
	}

	private final DDMExpressionFactory _ddmExpressionFactory =
		new DDMExpressionFactoryImpl();
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private Language _language;

}