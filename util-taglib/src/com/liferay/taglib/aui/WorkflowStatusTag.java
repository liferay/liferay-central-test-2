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

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class WorkflowStatusTag extends IncludeTag {

	public void setBean(Object bean) {
		_bean = bean;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setModel(Class<?> model) {
		_model = model;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public void setVersion(String version) {
		_version = version;
	}

	protected void cleanUp() {
		_bean = null;
		_id = null;
		_model = null;
		_status = 0;
		_version = null;
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

		Class<?> model = _model;

		if (model == null) {
			model = (Class<?>)pageContext.getAttribute(
				"aui:model-context:model");
		}

		request.setAttribute("aui:workflow-status:bean", bean);
		request.setAttribute("aui:workflow-status:id", _id);
		request.setAttribute("aui:workflow-status:model", model);
		request.setAttribute(
			"aui:workflow-status:status", String.valueOf(_status));
		request.setAttribute("aui:workflow-status:version", _version);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE =
		"/html/taglib/aui/workflow_status/page.jsp";

	private Object _bean;
	private String _id;
	private Class<?> _model;
	int _status;
	private String _version;

}