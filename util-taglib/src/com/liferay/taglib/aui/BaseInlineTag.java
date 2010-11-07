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

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.DynamicAttributes;

/**
 * @author Shuyang Zhou
 */
public class BaseInlineTag extends BodyTagSupport implements DynamicAttributes {

	public void setDynamicAttribute(
		String uri, String localName, Object value) {

		_dynamicAttributes.put(localName, value);
	}

	protected void insertDynamicAttributes(JspWriter jspWriter)
		throws IOException {
		if ((_dynamicAttributes == null) || _dynamicAttributes.isEmpty()) {
			return;
		}

		for (Map.Entry<String, Object> entry : _dynamicAttributes.entrySet()) {
			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());

			if (!key.equals("class")) {
				jspWriter.write(key);
				jspWriter.write("=\"");
				jspWriter.write(value);
				jspWriter.write("\" ");
			}
		}

	}

	private Map<String, Object> _dynamicAttributes =
			new HashMap<String, Object>();

}