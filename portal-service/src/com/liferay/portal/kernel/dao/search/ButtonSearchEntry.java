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

package com.liferay.portal.kernel.dao.search;

import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 */
public class ButtonSearchEntry extends TextSearchEntry {

	public ButtonSearchEntry(
		String align, String valign, String name, String href) {

		this(align, valign, DEFAULT_COLSPAN, name, href);
	}

	public ButtonSearchEntry(
		String align, String valign, int colspan, String name, String href) {

		super(align, valign, colspan, name, href);
	}

	public void print(PageContext pageContext) throws Exception {
		StringBuilder sb = new StringBuilder();

		sb.append("<input type=\"button\" ");
		sb.append("value=\"");
		sb.append(getName());
		sb.append("\" onClick=\"");
		sb.append(getHref());
		sb.append("\">");

		pageContext.getOut().print(sb.toString());
	}

	public Object clone() {
		return new ButtonSearchEntry(
			getAlign(), getValign(), getColspan(), getName(), getHref());
	}

}