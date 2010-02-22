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

import com.liferay.portal.kernel.servlet.PortalIncludeUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.PwdGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * <a href="PanelTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PanelTag extends BodyTagSupport {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		if (Validator.isNull(_id)) {
			_id = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);
		}

 		request.setAttribute("liferay-ui:panel:id", _id);
 		request.setAttribute("liferay-ui:panel:title", _title);
		request.setAttribute(
			"liferay-ui:panel:collapsible", String.valueOf(_collapsible));
 		request.setAttribute("liferay-ui:panel:defaultState", _defaultState);
 		request.setAttribute(
			"liferay-ui:panel:persistState", String.valueOf(_persistState));
 		request.setAttribute(
			"liferay-ui:panel:extended", String.valueOf(_extended));
 		request.setAttribute("liferay-ui:panel:cssClass", _cssClass);

		return EVAL_BODY_BUFFERED;
	}

	public int doAfterBody() {
		BodyContent bodyContent = getBodyContent();

		_bodyContentString = bodyContent.getString();

		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		try {
			PortalIncludeUtil.include(pageContext, getStartPage());

			pageContext.getOut().print(_bodyContentString);

			PortalIncludeUtil.include(pageContext, getEndPage());

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
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

 	public void setTitle(String title) {
		_title = title;
	}

	public void setCollapsible(boolean collapsible) {
		_collapsible = collapsible;
	}

 	public void setDefaultState(String defaultState) {
		_defaultState = defaultState;
	}

	public void setPersistState(boolean persistState) {
		_persistState = persistState;
	}

	public void setExtended(boolean extended) {
		_extended = extended;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	private static final String _START_PAGE = "/html/taglib/ui/panel/start.jsp";

	private static final String _END_PAGE = "/html/taglib/ui/panel/end.jsp";

	private String _startPage;
	private String _endPage;
 	private String _id;
 	private String _title;
	private boolean _collapsible = true;
 	private String _defaultState = "open";
	private boolean _persistState = true;
	private boolean _extended;
 	private String _cssClass = StringPool.BLANK;
 	private String _bodyContentString = StringPool.BLANK;

}