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
 * <a href="LegendTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class LegendTag extends IncludeTag {

	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("aui:legend:cssClass", _cssClass);
		request.setAttribute(
			"aui:legend:dynamicAttributes", getDynamicAttributes());
		request.setAttribute("aui:legend:label", _label);
	}

	protected void cleanUp() {
		_cssClass = null;
		_label = null;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setLabel(String label) {
		_label = label;
	}

	protected String getPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/aui/legend/page.jsp";

	private String _cssClass;
	private String _label;

}