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

import com.liferay.portal.kernel.util.StringPool;

import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * <a href="BufferTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BufferTag extends BodyTagSupport {

	public int doStartTag() {
		return EVAL_BODY_BUFFERED;
	}

	public int doAfterBody() {
		_bodyContentString = getBodyContent().getString();

		return SKIP_BODY;
	}

	public int doEndTag() {
		pageContext.setAttribute(_var, _bodyContentString);

		return EVAL_PAGE;
	}

	public void setVar(String var) {
		_var = var;
	}

	private String _var;
	private String _bodyContentString = StringPool.BLANK;

}