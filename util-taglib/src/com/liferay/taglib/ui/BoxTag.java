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

import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Theme;
import com.liferay.portal.util.WebKeys;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;
import com.liferay.taglib.util.ThemeUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * <a href="BoxTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BoxTag extends ParamAndPropertyAncestorTagImpl {

	public int doAfterBody() {
		_bodyContentString = getBodyContent().getString();

		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		try {
			ServletContext servletContext = getServletContext();
			HttpServletRequest request = getServletRequest();
			StringServletResponse stringResponse = getServletResponse();

			Theme theme = (Theme)request.getAttribute(WebKeys.THEME);

			// Top

			if (isTheme()) {
				ThemeUtil.include(
					servletContext, request, stringResponse, pageContext,
					getTop(), theme);
			}
			else {
				RequestDispatcher requestDispatcher =
					servletContext.getRequestDispatcher(getTop());

				requestDispatcher.include(request, stringResponse);
			}

			pageContext.getOut().print(stringResponse.getString());

			// Body

			pageContext.getOut().print(_bodyContentString);

			// Bottom

			//res = getServletResponse();
			stringResponse.recycle();

			if (isTheme()) {
				ThemeUtil.include(
					servletContext, request, stringResponse, pageContext,
					getBottom(), theme);
			}
			else {
				RequestDispatcher requestDispatcher =
					servletContext.getRequestDispatcher(getBottom());

				requestDispatcher.include(request, stringResponse);
			}

			pageContext.getOut().print(stringResponse.getString());

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			clearParams();
			clearProperties();
		}
	}

	public int doStartTag() {
		return EVAL_BODY_BUFFERED;
	}

	public String getBottom() {
		return _bottom;
	}

	public String getTop() {
		return _top;
	}

	public boolean isTheme() {
		return false;
	}

	public void setBottom(String bottom) {
		_bottom = bottom;
	}

	public void setTop(String top) {
		_top = top;
	}

	private String _bodyContentString = StringPool.BLANK;
	private String _bottom;
	private String _top;

}