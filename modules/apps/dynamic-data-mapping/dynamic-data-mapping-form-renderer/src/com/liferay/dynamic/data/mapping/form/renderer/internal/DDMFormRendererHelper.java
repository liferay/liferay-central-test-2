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

package com.liferay.dynamic.data.mapping.form.renderer.internal;

import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRendererConstants;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldRenderer;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeRegistryUtil;
import com.liferay.portlet.dynamicdatamapping.render.DDMFormFieldRenderingContext;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDMFormRendererHelper {

	public DDMFormRendererHelper(
		DDMForm ddmForm, DDMFormRenderingContext ddmFormRenderingContext) {

		_ddmForm = ddmForm;
		_ddmFormFieldsMap = ddmForm.getDDMFormFieldsMap(true);
		_ddmFormRenderingContext = ddmFormRenderingContext;
		_ddmFormValues = ddmFormRenderingContext.getDDMFormValues();
	}

	public Map<String, String> getRenderedDDMFormFieldsMap()
		throws DDMFormRenderingException {

		if (_ddmFormValues != null) {
			return getRenderedDDMFormFieldValues();
		}
		else {
			return getRenderedDDMFormFields();
		}
	}

	public void setExpressionEvaluator(
		ExpressionEvaluator expressionEvaluator) {

		_expressionEvaluator = expressionEvaluator;
	}

	protected DDMFormFieldRenderingContext
		createDDMFormFieldRenderingContext() {

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setChildElementsHTML(StringPool.BLANK);
		ddmFormFieldRenderingContext.setHttpServletRequest(
			_ddmFormRenderingContext.getHttpServletRequest());
		ddmFormFieldRenderingContext.setHttpServletResponse(
			_ddmFormRenderingContext.getHttpServletResponse());
		ddmFormFieldRenderingContext.setLabel(StringPool.BLANK);
		ddmFormFieldRenderingContext.setLocale(
			_ddmFormRenderingContext.getLocale());
		ddmFormFieldRenderingContext.setName(StringPool.BLANK);
		ddmFormFieldRenderingContext.setPortletNamespace(
			_ddmFormRenderingContext.getPortletNamespace());
		ddmFormFieldRenderingContext.setValue(StringPool.BLANK);

		return ddmFormFieldRenderingContext;
	}

	protected String getAffixedDDMFormFieldParameterName(
		String ddmFormFieldParameterName) {

		StringBundler sb = new StringBundler(5);

		sb.append(_ddmFormRenderingContext.getPortletNamespace());
		sb.append(DDMFormRendererConstants.DDM_FORM_FIELD_NAME_PREFIX);
		sb.append(ddmFormFieldParameterName);
		sb.append(
			DDMFormRendererConstants.DDM_FORM_FIELD_LANGUAGE_ID_SEPARATOR);
		sb.append(
			LocaleUtil.toLanguageId(_ddmFormRenderingContext.getLocale()));

		return sb.toString();
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

	protected Map<String, String> getRenderedDDMFormFields()
		throws DDMFormRenderingException {

		Map<String, String> renderedDDMFormFieldsMap = new HashMap<>();

		for (DDMFormField ddmFormField : _ddmForm.getDDMFormFields()) {
			renderedDDMFormFieldsMap.put(
				ddmFormField.getName(),
				renderDDMFormField(ddmFormField, StringPool.BLANK));
		}

		return renderedDDMFormFieldsMap;
	}

	protected Map<String, String> getRenderedDDMFormFieldValues()
		throws DDMFormRenderingException {

		Map<String, String> renderedDDMFormFieldValuesMap = new HashMap<>();

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			_ddmFormValues.getDDMFormFieldValuesMap();

		for (Map.Entry<String, List<DDMFormFieldValue>> entry :
				ddmFormFieldValuesMap.entrySet()) {

			renderedDDMFormFieldValuesMap.put(
				entry.getKey(),
				renderDDMFormFieldValues(entry.getValue(), StringPool.BLANK));
		}

		return renderedDDMFormFieldValuesMap;
	}

	protected boolean isDDMFormFieldVisible(DDMFormField ddmFormField) {
		if (Validator.isNull(ddmFormField.getVisibilityExpression())) {
			return true;
		}

		return _expressionEvaluator.evaluateBooleanExpression(
			ddmFormField.getVisibilityExpression());
	}

	protected String renderDDMFormField(
			DDMFormField ddmFormField,
			DDMFormFieldRenderingContext ddmFormFieldRenderingContext)
		throws DDMFormRenderingException {

		DDMFormFieldType ddmFormFieldType =
			DDMFormFieldTypeRegistryUtil.getDDMFormFieldType(
				ddmFormField.getType());

		if (ddmFormFieldType == null) {
			throw new DDMFormRenderingException(
				"No DDM form field type registered for " +
					ddmFormField.getType());
		}

		try {
			DDMFormFieldRenderer ddmFormFieldRenderer =
				ddmFormFieldType.getDDMFormFieldRenderer();

			String ddmFormFieldHTML = ddmFormFieldRenderer.render(
				ddmFormField, ddmFormFieldRenderingContext);

			boolean visible = isDDMFormFieldVisible(ddmFormField);

			return wrapDDMFormFieldHTML(ddmFormFieldHTML, visible);
		}
		catch (PortalException pe) {
			throw new DDMFormRenderingException(pe);
		}
	}

	protected String renderDDMFormField(
			DDMFormField ddmFormField, String parentDDMFormFieldParameterName)
		throws DDMFormRenderingException {

		String ddmFormFieldParameterName = getDDMFormFieldParameterName(
			ddmFormField.getName(), StringUtil.randomString(), 0,
			parentDDMFormFieldParameterName);

		List<DDMFormField> nestedDDMFormFields =
			ddmFormField.getNestedDDMFormFields();

		StringBundler sb = new StringBundler(nestedDDMFormFields.size());

		for (DDMFormField nestedDDMFormField : nestedDDMFormFields) {
			sb.append(
				renderDDMFormField(
					nestedDDMFormField, ddmFormFieldParameterName));
		}

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			createDDMFormFieldRenderingContext();

		setDDMFormFieldRenderingContextChildElementsHTML(
			sb.toString(), ddmFormFieldRenderingContext);
		setDDMFormFieldRenderingContextLabel(
			ddmFormField.getLabel(), ddmFormFieldRenderingContext);
		setDDMFormFieldRenderingContextName(
			ddmFormFieldParameterName, ddmFormFieldRenderingContext);

		return renderDDMFormField(ddmFormField, ddmFormFieldRenderingContext);
	}

	protected String renderDDMFormFieldValue(
			DDMFormFieldValue ddmFormFieldValue,
			DDMFormFieldRenderingContext ddmFormFieldRenderingContext)
		throws DDMFormRenderingException {

		DDMFormField ddmFormField = _ddmFormFieldsMap.get(
			ddmFormFieldValue.getName());

		setDDMFormFieldRenderingContextLabel(
			ddmFormField.getLabel(), ddmFormFieldRenderingContext);
		setDDMFormFieldRenderingContextValue(
			ddmFormFieldValue.getValue(), ddmFormFieldRenderingContext);

		return renderDDMFormField(ddmFormField, ddmFormFieldRenderingContext);
	}

	protected String renderDDMFormFieldValue(
			DDMFormFieldValue ddmFormFieldValue, int index,
			String parentDDMFormFieldParameterName)
		throws DDMFormRenderingException {

		String ddmFormFieldParameterName = getDDMFormFieldParameterName(
			ddmFormFieldValue.getName(), ddmFormFieldValue.getInstanceId(),
			index, parentDDMFormFieldParameterName);

		Map<String, List<DDMFormFieldValue>> nestedDDMFormFieldValuesMap =
			ddmFormFieldValue.getNestedDDMFormFieldValuesMap();

		StringBundler sb = new StringBundler(
			nestedDDMFormFieldValuesMap.size());

		for (List<DDMFormFieldValue> nestedDDMFormFieldValues :
				nestedDDMFormFieldValuesMap.values()) {

			sb.append(
				renderDDMFormFieldValues(
					nestedDDMFormFieldValues, ddmFormFieldParameterName));
		}

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			createDDMFormFieldRenderingContext();

		setDDMFormFieldRenderingContextChildElementsHTML(
			sb.toString(), ddmFormFieldRenderingContext);
		setDDMFormFieldRenderingContextName(
			ddmFormFieldParameterName, ddmFormFieldRenderingContext);

		return renderDDMFormFieldValue(
			ddmFormFieldValue, ddmFormFieldRenderingContext);
	}

	protected String renderDDMFormFieldValues(
			List<DDMFormFieldValue> ddmFormFieldValues,
			String parentDDMFormFieldParameterName)
		throws DDMFormRenderingException {

		StringBundler sb = new StringBundler(ddmFormFieldValues.size());

		int index = 0;

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			sb.append(
				renderDDMFormFieldValue(
					ddmFormFieldValue, index++,
					parentDDMFormFieldParameterName));
		}

		return sb.toString();
	}

	protected void setDDMFormFieldRenderingContextChildElementsHTML(
		String childElementsHTML,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		ddmFormFieldRenderingContext.setChildElementsHTML(childElementsHTML);
	}

	protected void setDDMFormFieldRenderingContextLabel(
		LocalizedValue label,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<Locale, String> values = label.getValues();

		if (values.isEmpty()) {
			return;
		}

		ddmFormFieldRenderingContext.setLabel(
			label.getString(ddmFormFieldRenderingContext.getLocale()));
	}

	protected void setDDMFormFieldRenderingContextName(
		String ddmFormFieldParameterName,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		String name = getAffixedDDMFormFieldParameterName(
			ddmFormFieldParameterName);

		ddmFormFieldRenderingContext.setName(name);
	}

	protected void setDDMFormFieldRenderingContextValue(
		Value value,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		if (value == null) {
			return;
		}

		ddmFormFieldRenderingContext.setValue(
			value.getString(ddmFormFieldRenderingContext.getLocale()));
	}

	protected String wrapDDMFormFieldHTML(
		String ddmFormFieldHTML, boolean visible) {

		StringBundler sb = new StringBundler(5);

		sb.append("<div class=\"lfr-ddm-form-field-container");

		if (!visible) {
			sb.append(" hidden");
		}

		sb.append("\">");
		sb.append(ddmFormFieldHTML);
		sb.append("</div>");

		return sb.toString();
	}

	private final DDMForm _ddmForm;
	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private final DDMFormRenderingContext _ddmFormRenderingContext;
	private final DDMFormValues _ddmFormValues;
	private ExpressionEvaluator _expressionEvaluator;

}