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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * <a href="ErrorMarkerTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ErrorMarkerTag extends BodyTagSupport {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		if (Validator.isNotNull(_key) && Validator.isNotNull(_value)) {
			request.setAttribute("liferay-ui:error-marker:key", _key);
			request.setAttribute("liferay-ui:error-marker:value", _value);
		}
		else {
			request.removeAttribute("liferay-ui:error-marker:key");
			request.removeAttribute("liferay-ui:error-marker:value");
		}

		return SKIP_BODY;
	}

	public void setKey(String key) {
		_key = key;
	}

	public void setValue(String value) {
		_value = value;
	}

	private String _key;
	private String _value;

}