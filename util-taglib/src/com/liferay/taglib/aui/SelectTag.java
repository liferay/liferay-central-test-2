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
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class SelectTag extends IncludeTag {

	public void setBean(Object bean) {
		_bean = bean;
	}

	public void setChangesContext(boolean changesContext) {
		_changesContext = changesContext;
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

	public void setFirst(boolean first) {
		_first = first;
	}

	public void setHelpMessage(String helpMessage) {
		_helpMessage = helpMessage;
	}

	public void setId(String id) {
		_id = id;
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

	public void setLast(boolean last) {
		_last = last;
	}

	public void setListType(String listType) {
		_listType = listType;
	}

	public void setListTypeFieldName(String listTypeFieldName) {
		_listTypeFieldName = listTypeFieldName;
	}

	public void setMultiple(boolean multiple) {
		_multiple = multiple;
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

	public void setShowEmptyOption(boolean showEmptyOption) {
		_showEmptyOption = showEmptyOption;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setSuffix(String suffix) {
		_suffix = suffix;
	}

	protected void cleanUp() {
		_bean = null;
		_changesContext = false;
		_cssClass = null;
		_data = null;
		_first = false;
		_helpMessage = null;
		_inlineField = false;
		_inlineLabel = null;
		_inputCssClass = null;
		_id = null;
		_label = null;
		_last = false;
		_listType = null;
		_listTypeFieldName = null;
		_multiple = false;
		_name = null;
		_onChange = null;
		_onClick = null;
		_prefix = null;
		_showEmptyOption = false;
		_suffix = null;
		_title = null;
	}

	protected String getEndPage() {
		return _END_PAGE;
	}

	protected String getStartPage() {
		return _START_PAGE;
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

		String id = _id;

		if (Validator.isNull(id)) {
			id = name;
		}

		String label = _label;

		if (label == null) {
			label = TextFormatter.format(name, TextFormatter.K);
		}

		String listTypeFieldName = _listTypeFieldName;

		if (Validator.isNotNull(_listType) &&
			Validator.isNull(listTypeFieldName)) {

			listTypeFieldName = "typeId";
		}

		request.setAttribute("aui:select:bean", bean);
		request.setAttribute(
			"aui:select:changesContext", String.valueOf(_changesContext));
		request.setAttribute("aui:select:cssClass", _cssClass);
		request.setAttribute("aui:select:data", _data);
		request.setAttribute("aui:select:disabled", String.valueOf(_disabled));
		request.setAttribute(
			"aui:select:dynamicAttributes", getDynamicAttributes());
		request.setAttribute("aui:select:first", String.valueOf(_first));
		request.setAttribute("aui:select:helpMessage", _helpMessage);
		request.setAttribute("aui:select:id", id);
		request.setAttribute(
			"aui:select:inlineField", String.valueOf(_inlineField));
		request.setAttribute("aui:select:inlineLabel", _inlineLabel);
		request.setAttribute("aui:select:inputCssClass", _inputCssClass);
		request.setAttribute("aui:select:label", label);
		request.setAttribute("aui:select:last", String.valueOf(_last));
		request.setAttribute("aui:select:listType", _listType);
		request.setAttribute("aui:select:listTypeFieldName", listTypeFieldName);
		request.setAttribute("aui:select:multiple", String.valueOf(_multiple));
		request.setAttribute("aui:select:name", _name);
		request.setAttribute("aui:select:onChange", _onChange);
		request.setAttribute("aui:select:onClick", _onClick);
		request.setAttribute("aui:select:prefix", _prefix);
		request.setAttribute(
			"aui:select:showEmptyOption", String.valueOf(_showEmptyOption));
		request.setAttribute("aui:select:suffix", _suffix);
		request.setAttribute("aui:select:title", _title);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _END_PAGE = "/html/taglib/aui/select/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/select/start.jsp";

	private Object _bean;
	private boolean _changesContext;
	private String _cssClass;
	private Map<String, Object> _data;
	private boolean _disabled;
	private boolean _first;
	private String _helpMessage;
	private String _id;
	private boolean _inlineField;
	private String _inlineLabel;
	private String _inputCssClass;
	private String _label;
	private boolean _last;
	private String _listType;
	private String _listTypeFieldName;
	private boolean _multiple;
	private String _name;
	private String _onChange;
	private String _onClick;
	private String _prefix;
	private boolean _showEmptyOption;
	private String _suffix;
	private String _title;

}