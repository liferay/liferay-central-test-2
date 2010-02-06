/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.aui;

import javax.servlet.jsp.tagext.TagSupport;

/**
 * <a href="ToolTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 */
public class ToolTag extends TagSupport {

	public int doStartTag() {
		if (_icon == null) {
			_icon = _id;
		}

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

	private String _handler;
	private String _icon;
	private String _id;

}