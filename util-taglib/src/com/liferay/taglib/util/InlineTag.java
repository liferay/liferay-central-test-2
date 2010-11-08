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

package com.liferay.taglib.util;

import java.io.IOException;

import java.util.Map;

import javax.servlet.jsp.JspWriter;

/**
 * @author Brian Wing Shun Chan
 */
public class InlineTag extends IncludeTag {

	protected void writeDynamicAttributes(JspWriter jspWriter)
		throws IOException {

		Map<String, Object> dynamicAttributes = getDynamicAttributes();

		if ((dynamicAttributes == null) || dynamicAttributes.isEmpty()) {
			return;
		}

		for (Map.Entry<String, Object> entry : dynamicAttributes.entrySet()) {
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

}