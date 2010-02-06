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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * <a href="ToggleAreaTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class ToggleAreaTag extends ParamAndPropertyAncestorTagImpl {

	public int doStartTag() throws JspException {
		try {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			request.setAttribute("liferay-ui:toggle-area:id", _id);
			request.setAttribute(
				"liferay-ui:toggle-area:showImage", _showImage);
			request.setAttribute(
				"liferay-ui:toggle-area:hideImage", _hideImage);
			request.setAttribute(
				"liferay-ui:toggle-area:showMessage", _showMessage);
			request.setAttribute(
				"liferay-ui:toggle-area:hideMessage", _hideMessage);
			request.setAttribute(
				"liferay-ui:toggle-area:defaultShowContent",
				String.valueOf(_defaultShowContent));
			request.setAttribute("liferay-ui:toggle-area:stateVar", _stateVar);
			request.setAttribute("liferay-ui:toggle-area:align", _align);

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
			if (!ServerDetector.isResin()) {
				_startPage = null;
				_endPage = null;
				_id = null;
				_showMessage = null;
				_hideMessage = null;
				_defaultShowContent = true;
				_stateVar = null;
				_align = "left";
			}
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

	public void setId(String id) {
		_id = id;
	}

	public void setShowImage(String showImage) {
		_showImage = showImage;
	}

	public void setHideImage(String hideImage) {
		_hideImage = hideImage;
	}

	public void setShowMessage(String showMessage) {
		_showMessage = showMessage;
	}

	public void setHideMessage(String hideMessage) {
		_hideMessage = hideMessage;
	}

	public void setDefaultShowContent(boolean defaultShowContent) {
		_defaultShowContent = defaultShowContent;
	}

	public void setStateVar(String stateVar) {
		_stateVar = stateVar;
	}

	public void setAlign(String align) {
		_align = align;
	}

	private static final String _START_PAGE =
		"/html/taglib/ui/toggle_area/start.jsp";

	private static final String _END_PAGE =
		"/html/taglib/ui/toggle_area/end.jsp";

	private String _startPage;
	private String _endPage;
	private String _id;
	private String _showImage;
	private String _hideImage;
	private String _showMessage;
	private String _hideMessage;
	private boolean _defaultShowContent = true;
	private String _stateVar;
	private String _align = "left";

}