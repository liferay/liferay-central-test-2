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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.functions;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Leonardo Barros
 */
@RunWith(MockitoJUnitRunner.class)
public class CallFunctionTest {

	@Test
	public void testAutoSelectOption() throws Exception {
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResults = new HashMap<>();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult("field0", "1");

		ddmFormFieldEvaluationResults.put(
			"field0", Arrays.asList(ddmFormFieldEvaluationResult));

		CallFunction callFunction = new CallFunction(
			null, ddmFormFieldEvaluationResults, null, null);

		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		keyValuePairs.add(new KeyValuePair("key_1", "value_1"));

		callFunction.setDDMFormFieldOptions("field0", keyValuePairs);

		Assert.assertEquals("value_1", ddmFormFieldEvaluationResult.getValue());
	}

	@Test
	public void testNotAutoSelectOption() throws Exception {
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResults = new HashMap<>();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult("field0", "1");

		ddmFormFieldEvaluationResults.put(
			"field0", Arrays.asList(ddmFormFieldEvaluationResult));

		CallFunction callFunction = new CallFunction(
			null, ddmFormFieldEvaluationResults, null, null);

		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		keyValuePairs.add(new KeyValuePair("key_1", "value_1"));
		keyValuePairs.add(new KeyValuePair("key_2", "value_2"));

		callFunction.setDDMFormFieldOptions("field0", keyValuePairs);

		Assert.assertNull(ddmFormFieldEvaluationResult.getValue());
	}

}