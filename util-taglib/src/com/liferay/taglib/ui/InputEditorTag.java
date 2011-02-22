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

package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class InputEditorTag extends IncludeTag {

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setEditorImpl(String editorImpl) {
		_editorImpl = editorImpl;
	}

	public void setHeight(String height) {
		_height = height;
	}

	public void setInitMethod(String initMethod) {
		_initMethod = initMethod;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOnChangeMethod(String onChangeMethod) {
		_onChangeMethod = onChangeMethod;
	}

	public void setConfigParams(Map<String, String> configParams) {
		_configParams = configParams;
	}

	public void setToolbarSet(String toolbarSet) {
		_toolbarSet = toolbarSet;
	}

	public void setWidth(String width) {
		_width = width;
	}

	protected void cleanUp() {
		_cssClass = null;
		_editorImpl = null;
		_height = null;
		_initMethod = null;
		_name = null;
		_onChangeMethod = null;
		_configParams = null;
		_toolbarSet = null;
		_width = null;
	}

	protected String getPage() {
		return _PAGE;
	}

	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-ui:input-editor:cssClass", _cssClass);
		request.setAttribute("liferay-ui:input-editor:editorImpl", _editorImpl);
		request.setAttribute("liferay-ui:input-editor:height", _height);
		request.setAttribute("liferay-ui:input-editor:initMethod", _initMethod);
		request.setAttribute("liferay-ui:input-editor:name", _name);
		request.setAttribute(
			"liferay-ui:input-editor:onChangeMethod", _onChangeMethod);
		request.setAttribute(
			"liferay-ui:input-editor:configParams", _configParams);
		request.setAttribute("liferay-ui:input-editor:toolbarSet", _toolbarSet);
		request.setAttribute("liferay-ui:input-editor:width", _width);
	}

	private static final String _PAGE = "/html/taglib/ui/input_editor/page.jsp";

	private String _cssClass;
	private String _editorImpl;
	private String _height;
	private String _initMethod;
	private String _name;
	private String _onChangeMethod;
	private Map<String,String> _configParams;
	private String _toolbarSet;
	private String _width;

}