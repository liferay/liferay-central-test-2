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

package com.liferay.portal.kernel.servlet.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * The difference between this class and
 * {@link javax.servlet.jsp.tagext.TagSupport} is this class is not implementing
 * {@link javax.servlet.jsp.tagext.IterationTag} which will be compiled to a
 * do-while loop. In a complex jsp page with many tags, the loop stack can be
 * very deep which hurts performance a lot.
 * @author Shuyang Zhou
 */
public class TagSupport implements Tag {

	public static Tag findAncestorWithClass(Tag from, Class<?> clazz) {
		boolean isInterface = false;

		if ((from == null) || (clazz == null) ||
			(!Tag.class.isAssignableFrom(clazz) &&
				!(isInterface = clazz.isInterface()))) {
			return null;
		}

		while (true) {
			Tag tag = from.getParent();

			if (tag == null) {
				return null;
			}

			if ((isInterface && clazz.isInstance(tag)) ||
				clazz.isAssignableFrom(tag.getClass())) {
				return tag;
			}
			else {
				from = tag;
			}
		}
	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}

	public Tag getParent() {
		return _parent;
	}

	public void release() {
		_parent = null;
	}

	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	public void setParent(Tag tag) {
		_parent = tag;
	}

	protected PageContext pageContext;

	private Tag _parent;

}