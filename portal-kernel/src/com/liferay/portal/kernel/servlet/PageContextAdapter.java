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

package com.liferay.portal.kernel.servlet;

import java.io.Writer;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * <a href="PageContextAdapter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class PageContextAdapter extends PageContextWrapper {

	public PageContextAdapter(PageContext pageContext, Writer writer) {
		super(pageContext);
		_jspWriterAdapter = new JspWriterAdapter(writer);
	}

	public JspWriter getOut() {
		return _jspWriterAdapter;
	}

	private JspWriterAdapter _jspWriterAdapter;

}