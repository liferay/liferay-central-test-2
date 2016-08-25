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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.rules.functions;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class PropertyGetFunction extends BasePropertyFunction {

	public PropertyGetFunction(
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResults) {

		super(ddmFormFieldEvaluationResults);
	}

	@Override
	public Object evaluate(Object... parameters) {
		String[] fieldNameParts = StringUtil.split(
			parameters[0].toString(), '#');

		String property = parameters[1].toString();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			getDDMFormFieldEvaluationResult(
				fieldNameParts[0], Integer.valueOf(fieldNameParts[1]));

		if (property.equals("readOnly")) {
			return ddmFormFieldEvaluationResult.isReadOnly();
		}
		else if (property.equals("valid")) {
			return ddmFormFieldEvaluationResult.isValid();
		}
		else if (property.equals("value")) {
			return ddmFormFieldEvaluationResult.getValue();
		}
		else if (property.equals("visible")) {
			return ddmFormFieldEvaluationResult.isVisible();
		}

		return null;
	}

}