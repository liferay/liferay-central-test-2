/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.util.PwdGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class InputTag extends IncludeTag {

	public int doEndTag() throws JspException {
		updateFormValidators();

		return super.doEndTag();
	}

	public int doStartTag() throws JspException {
		addModelValidators();

		return super.doStartTag();
	}

	public void setBean(Object bean) {
		_bean = bean;
	}

	public void setChangesContext(boolean changesContext) {
		_changesContext = changesContext;
	}

	public void setChecked(boolean checked) {
		_checked = checked;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setData(Map<String,Object> data) {
		_data = data;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setField(String field) {
		_field = field;
	}

	public void setFieldParam(String fieldParam) {
		_fieldParam = fieldParam;
	}

	public void setFirst(boolean first) {
		_first = first;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setHelpMessage(String helpMessage) {
		_helpMessage = helpMessage;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setIgnoreRequestValue(boolean ignoreRequestValue) {
		_ignoreRequestValue = ignoreRequestValue;
	}

	public void setInlineField(boolean inlineField) {
		_inlineField = inlineField;
	}

	public void setInlineLabel(String inlineLabel) {
		_inlineLabel = inlineLabel;
	}

	public void setInputCssClass(String inputCssClass) {
		_inputCssClass = inputCssClass;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public void setLast(boolean last) {
		_last = last;
	}

	public void setModel(Class<?> model) {
		_model = model;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOnChange(String onChange) {
		_onChange = onChange;
	}

	public void setOnClick(String onClick) {
		_onClick = onClick;
	}

	public void setPrefix(String prefix) {
		_prefix = prefix;
	}

	public void setSuffix(String suffix) {
		_suffix = suffix;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setValue(Object value) {
		_value = value;
	}

	protected void addModelValidators() {
		Class<?> model = _model;

		if (model == null) {
			model = (Class<?>)pageContext.getAttribute(
				"aui:model-context:model");
		}

		if ((model == null) || Validator.isNotNull(_type)) {
			return;
		}

		String field = _field;

		if (Validator.isNull(field)) {
			field = _name;
		}

		List<Tuple> modelValidators = ModelHintsUtil.getValidators(
			model.getName(), field);

		if (modelValidators == null) {
			return;
		}

		for (Tuple modelValidator : modelValidators) {
			String validatorName = (String)modelValidator.getObject(1);
			String validatorErrorMessage = (String)modelValidator.getObject(2);
			String validatorValue = (String)modelValidator.getObject(3);

			ValidatorTag validatorTag = new ValidatorTag(
				validatorName, validatorErrorMessage, validatorValue);

			addValidatorTag(validatorName, validatorTag);
		}
	}

	protected void addValidatorTag(
		String validatorName, ValidatorTag validatorTag) {

		if (_validators == null) {
			_validators = new HashMap<String, ValidatorTag>();
		}

		_validators.put(validatorName, validatorTag);
	}

	protected void cleanUp() {
		_bean = null;
		_changesContext = false;
		_checked = false;
		_classPK = 0;
		_cssClass = null;
		_data = null;
		_disabled = false;
		_field = null;
		_fieldParam = null;
		_first = false;
		_formName = null;
		_helpMessage = null;
		_id = null;
		_ignoreRequestValue = false;
		_inlineField = false;
		_inlineLabel = null;
		_inputCssClass = null;
		_label = null;
		_languageId = null;
		_last = false;
		_model = null;
		_name = null;
		_onChange = null;
		_onClick = null;
		_prefix = null;
		_suffix = null;
		_title = null;
		_type = null;
		_validators = null;
		_value = null;
	}

	protected String getPage() {
		return _PAGE;
	}

	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	protected void setAttributes(HttpServletRequest request) {
		Object bean = _bean;

		if (bean == null) {
			bean = pageContext.getAttribute("aui:model-context:bean");
		}

		String name = _name;

		if (name.startsWith(DefaultConfigurationAction.PREFERENCES_PREFIX)) {
			name = name.substring(
				DefaultConfigurationAction.PREFERENCES_PREFIX.length(),
				name.length() - 2);
		}

		String field = _field;

		if (Validator.isNull(field)) {
			field = _name;
		}

		String formName = _formName;

		if (formName == null) {
			FormTag formTag = (FormTag)findAncestorWithClass(
				this, FormTag.class);

			if (formTag != null) {
				formName = formTag.getName();
			}
		}

		String id = _id;

		if (Validator.isNull(id)) {
			if (!Validator.equals(_type, "radio")) {
				id = name;
			}
			else {
				id = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);
			}
		}

		String label = _label;

		if (label == null) {
			label = TextFormatter.format(name, TextFormatter.K);
		}

		Class<?> model = _model;

		if (model == null) {
			model = (Class<?>)pageContext.getAttribute(
				"aui:model-context:model");
		}

		boolean required = false;

		if ((_validators != null) && (_validators.get("required") != null)) {
			required = true;
		}

		request.setAttribute("aui:input:bean", bean);
		request.setAttribute(
			"aui:input:changesContext", String.valueOf(_changesContext));
		request.setAttribute("aui:input:checked", String.valueOf(_checked));
		request.setAttribute("aui:input:classPK", String.valueOf(_classPK));
		request.setAttribute("aui:input:cssClass", _cssClass);
		request.setAttribute("aui:input:data", _data);
		request.setAttribute("aui:input:disabled", String.valueOf(_disabled));
		request.setAttribute(
			"aui:input:dynamicAttributes", getDynamicAttributes());
		request.setAttribute("aui:input:field", field);
		request.setAttribute("aui:input:fieldParam", _fieldParam);
		request.setAttribute("aui:input:first", String.valueOf(_first));
		request.setAttribute("aui:input:formName", formName);
		request.setAttribute("aui:input:helpMessage", _helpMessage);
		request.setAttribute("aui:input:id", id);
		request.setAttribute(
			"aui:input:ignoreRequestValue",
			String.valueOf(_ignoreRequestValue));
		request.setAttribute(
			"aui:input:inlineField", String.valueOf(_inlineField));
		request.setAttribute("aui:input:inlineLabel", _inlineLabel);
		request.setAttribute("aui:input:inputCssClass", _inputCssClass);
		request.setAttribute("aui:input:label", label);
		request.setAttribute("aui:input:languageId", _languageId);
		request.setAttribute("aui:input:last", String.valueOf(_last));
		request.setAttribute("aui:input:model", model);
		request.setAttribute("aui:input:name", _name);
		request.setAttribute("aui:input:onChange", _onChange);
		request.setAttribute("aui:input:onClick", _onClick);
		request.setAttribute("aui:input:prefix", _prefix);
		request.setAttribute("aui:input:required", String.valueOf(required));
		request.setAttribute("aui:input:suffix", _suffix);
		request.setAttribute("aui:input:title", _title);
		request.setAttribute("aui:input:type", _type);
		request.setAttribute("aui:input:value", _value);
	}

	protected void updateFormValidators() {
		if (_validators == null) {
			return;
		}

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		Map<String, List<ValidatorTag>> validatorTagsMap =
			(Map<String, List<ValidatorTag>>)request.getAttribute(
				"aui:form:validatorTagsMap");

		List<ValidatorTag> validatorTags = ListUtil.fromCollection(
			_validators.values());

		validatorTagsMap.put(_name, validatorTags);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/html/taglib/aui/input/page.jsp";

	private Object _bean;
	private boolean _changesContext;
	private boolean _checked;
	private long _classPK;
	private String _cssClass;
	private Map<String, Object> _data;
	private boolean _disabled;
	private String _field;
	private String _fieldParam;
	private boolean _first;
	private String _formName;
	private String _helpMessage;
	private String _id;
	private boolean _ignoreRequestValue;
	private boolean _inlineField;
	private String _inlineLabel;
	private String _inputCssClass;
	private String _label;
	private String _languageId;
	private boolean _last;
	private Class<?> _model;
	private String _name;
	private String _onChange;
	private String _onClick;
	private String _prefix;
	private String _suffix;
	private String _title;
	private String _type;
	private Map<String, ValidatorTag> _validators;
	private Object _value;

}