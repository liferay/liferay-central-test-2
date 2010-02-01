/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * <a href="LanguageTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LanguageTag extends IncludeTag {

	public static final int LIST_ICON = 0;

	public static final int LIST_LONG_TEXT = 1;

	public static final int LIST_SHORT_TEXT = 2;

	public static final int SELECT_BOX = 3;

	public static void doTag(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		doTag(
			_PAGE, _FORM_NAME, _FORM_ACTION, _NAME, null, _DISPLAY_STYLE,
			servletContext, request, response);
	}

	public static void doTag(
			String formName, String formAction, String name,
			String[] languageIds, int displayStyle,
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		doTag(
			_PAGE, formName, formAction, name, languageIds, displayStyle,
			servletContext, request, response);
	}

	public static void doTag(
			String page, String formName, String formAction, String name,
			String[] languageIds, int displayStyle,
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		request.setAttribute("liferay-ui:language:formName", formName);
		request.setAttribute("liferay-ui:language:formAction", formAction);
		request.setAttribute("liferay-ui:language:name", name);

		Locale[] locales = null;

		if ((languageIds == null) || (languageIds.length == 0)) {
			locales = LanguageUtil.getAvailableLocales();
		}
		else {
			locales = LocaleUtil.fromLanguageIds(languageIds);
		}

		request.setAttribute("liferay-ui:language:locales", locales);

		request.setAttribute(
			"liferay-ui:language:displayStyle", String.valueOf(displayStyle));

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(page);

		requestDispatcher.include(request, response);
	}

	public int doEndTag() throws JspException {
		try {
			ServletContext servletContext = getServletContext();
			HttpServletRequest request = getServletRequest();
			StringServletResponse stringResponse = getServletResponse();

			doTag(
				getPage(), _formName, _formAction, _name, _languageIds,
				_displayStyle, servletContext, request, stringResponse);

			pageContext.getOut().print(stringResponse.getString());

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setFormAction(String formAction) {
		_formAction = formAction;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setLanguageIds(String[] languageIds) {
		_languageIds = languageIds;
	}

	public void setDisplayStyle(int displayStyle) {
		_displayStyle = displayStyle;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/ui/language/page.jsp";

	private static final String _FORM_NAME = "fm";

	private static final String _FORM_ACTION = null;

	private static final String _NAME = "languageId";

	private static final int _DISPLAY_STYLE = 0;

	private String _formName = _FORM_NAME;
	private String _formAction = _FORM_ACTION;
	private String _name = _NAME;
	private String[] _languageIds;
	private int _displayStyle = _DISPLAY_STYLE;

}