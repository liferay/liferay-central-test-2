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

import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 */
public class ToolTag extends TagSupport {

	public int doStartTag() {
		PanelTag parentTag = (PanelTag)findAncestorWithClass(
			this, PanelTag.class);

		parentTag.addToolTag(this);

		return EVAL_PAGE;
	}

	public String getHandler() {
		return _handler;
	}

	public String getIcon() {
		return _icon;
	}

	public String getId() {
		return _id;
	}

	public void setHandler(String handler) {
		_handler = handler;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setId(String id) {
		_id = id;
	}

	protected void cleanUp() {
		_handler = null;
		_icon = null;
		_id = null;
	}

	private String _handler;
	private String _icon;
	private String _id;

}