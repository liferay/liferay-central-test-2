/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="RatingsTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoAttributeTag extends IncludeTag {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute(
			"liferay-ui:expando-attribute:className", _className);
		request.setAttribute(
			"liferay-ui:expando-attribute:classPK", String.valueOf(_classPK));
		request.setAttribute(
			"liferay-ui:expando-attribute:editable", String.valueOf(_editable));
		request.setAttribute(
			"liferay-ui:expando-attribute:label", String.valueOf(_label));
		request.setAttribute("liferay-ui:expando-attribute:name", _name);

		return EVAL_BODY_BUFFERED;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	public void setEditable(boolean editable) {
		_editable = editable;
	}

	public void setLabel(boolean label) {
		_label = label;
	}

	public void setName(String name) {
		_name = name;
	}

	private static final String _PAGE = "/html/taglib/ui/expando-attribute/page.jsp";

	private String _className;
	private long _classPK;
	private boolean _editable = false;
	private boolean _label = false;
	private String _name;

}