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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;
import com.liferay.util.PwdGenerator;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

/**
 * <a href="ToggleAreaTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class ToggleAreaTag extends ParamAndPropertyAncestorTagImpl {

	public int doStartTag() throws JspException {
		try {
			ServletRequest req = getServletRequest();

			req.setAttribute("liferay-ui:toggle-area:showKey", _showKey);
			req.setAttribute("liferay-ui:toggle-area:hideKey", _hideKey);
			req.setAttribute(
					"liferay-ui:toggle-area:shown", String.valueOf(_shown));

			if (_name == null) {
				req.setAttribute(
					"liferay-ui:toggle-area:name", PwdGenerator.getPassword());
			}
			else {
				req.setAttribute(
					"liferay-ui:toggle-area:name", _name);
			}

			include(getStartPage());

			return EVAL_BODY_INCLUDE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public int doEndTag() throws JspException {
		try {
			include(getEndPage());

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			_startPage = null;
			_endPage = null;
			_showKey = null;
			_hideKey = null;
			_shown = false;
			_name = null;
		}
	}

	public String getStartPage() {
		if (Validator.isNull(_startPage)) {
			return _START_PAGE;
		}
		else {
			return _startPage;
		}
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	public String getEndPage() {
		if (Validator.isNull(_endPage)) {
			return _END_PAGE;
		}
		else {
			return _endPage;
		}
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setShowKey(String showKey) {
		_showKey = showKey;
	}

	public void setHideKey(String hideKey) {
		_hideKey = hideKey;
	}

	public void setShown(boolean shown) {
		_shown = shown;
	}

	public void setName(String name) {
		_name = name;
	}

	private static final String _START_PAGE = "/html/taglib/ui/toggle_area/start.jsp";

	private static final String _END_PAGE = "/html/taglib/ui/toggle_area/end.jsp";

	private String _startPage;
	private String _endPage;
	private String _showKey;
	private String _hideKey;
	private boolean _shown = false;
	private String _name;

}
