/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.util.TextFormatter;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="SelectTag.java.html"><b><i>View Source</i></b></a>
 *
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

	public void setLabel(String label) {
		_label = label;
	}

	public void setLast(boolean last) {
		_last = last;
	}

	public void setListType(String listType) {
		_listType = listType;
	}

	public void setName(String name) {
		_name = name;
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
		_first = false;
		_helpMessage = null;
		_inlineField = false;
		_inlineLabel = null;
		_id = null;
		_label = null;
		_last = false;
		_listType = null;
		_name = null;
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

	protected void setAttributes(HttpServletRequest request) {
		Object bean = _bean;

		if (bean == null) {
			bean = pageContext.getAttribute("aui:model-context:bean");
		}

		String id = _id;

		if (Validator.isNull(id)) {
			id = _name;
		}

		String label = _label;

		if (label == null) {
			label = TextFormatter.format(_name, TextFormatter.K);
		}

		request.setAttribute("aui:select:bean", bean);
		request.setAttribute(
			"aui:select:changesContext", String.valueOf(_changesContext));
		request.setAttribute("aui:select:cssClass", _cssClass);
		request.setAttribute("aui:select:disabled", String.valueOf(_disabled));
		request.setAttribute(
			"aui:select:dynamicAttributes", getDynamicAttributes());
		request.setAttribute("aui:select:first", String.valueOf(_first));
		request.setAttribute("aui:select:helpMessage", _helpMessage);
		request.setAttribute(
			"aui:select:inlineField", String.valueOf(_inlineField));
		request.setAttribute("aui:select:inlineLabel", _inlineLabel);
		request.setAttribute("aui:select:id", id);
		request.setAttribute("aui:select:label", label);
		request.setAttribute("aui:select:last", String.valueOf(_last));
		request.setAttribute("aui:select:listType", _listType);
		request.setAttribute("aui:select:name", _name);
		request.setAttribute("aui:select:prefix", _prefix);
		request.setAttribute(
			"aui:select:showEmptyOption", String.valueOf(_showEmptyOption));
		request.setAttribute("aui:select:suffix", _suffix);
		request.setAttribute("aui:select:title", _title);
	}

	private static final String _END_PAGE = "/html/taglib/aui/select/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/select/start.jsp";

	private Object _bean;
	private boolean _changesContext;
	private String _cssClass;
	private boolean _disabled;
	private boolean _first;
	private String _helpMessage;
	private String _id;
	private boolean _inlineField;
	private String _inlineLabel;
	private String _label;
	private boolean _last;
	private String _listType;
	private String _name;
	private String _prefix;
	private boolean _showEmptyOption;
	private String _suffix;
	private String _title;

}