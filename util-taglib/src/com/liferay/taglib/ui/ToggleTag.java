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

import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.DeterminateKeyGenerator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.SessionClicks;
import com.liferay.taglib.util.IncludeTag;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * <a href="ToggleTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ToggleTag extends IncludeTag {

	public static void doTag(
			String id, String showImage, String hideImage, String showMessage,
			String hideMessage, boolean defaultShowContent, String stateVar,
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		doTag(
			_PAGE, id, showImage, hideImage, showMessage, hideMessage,
			defaultShowContent, stateVar, servletContext, request, response);
	}

	public static void doTag(
			String page, String id, String showImage, String hideImage,
			String showMessage, String hideMessage, boolean defaultShowContent,
			String stateVar, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (Validator.isNull(showImage) && Validator.isNull(showMessage)) {
			showImage =
				themeDisplay.getPathThemeImages() + "/arrows/01_down.png";
		}

		if (Validator.isNull(hideImage) && Validator.isNull(hideImage)) {
			hideImage =
				themeDisplay.getPathThemeImages() + "/arrows/01_right.png";
		}

		String defaultStateValue =
			defaultShowContent ? StringPool.BLANK : "none";
		String defaultImage = defaultShowContent ? hideImage : showImage;
		String defaultMessage = defaultShowContent ? hideMessage : showMessage;

		String clickValue = SessionClicks.get(request, id, null);

		if (defaultShowContent) {
			if ((clickValue != null) && (clickValue.equals("none"))) {
				defaultStateValue = "none";
				defaultImage = showImage;
				defaultMessage = showMessage;
			}
			else {
				defaultStateValue = "";
				defaultImage = hideImage;
				defaultMessage = hideMessage;
			}
		}
		else {
			if ((clickValue == null) || (clickValue.equals("none"))) {
				defaultStateValue = "none";
				defaultImage = showImage;
				defaultMessage = showMessage;
			}
			else {
				defaultStateValue = "";
				defaultImage = hideImage;
				defaultMessage = hideMessage;
			}
		}

		if (stateVar == null) {
			stateVar = DeterminateKeyGenerator.generate(
				ToggleTag.class.getName());
		}

		request.setAttribute("liferay-ui:toggle:id", id);
		request.setAttribute("liferay-ui:toggle:showImage", showImage);
		request.setAttribute("liferay-ui:toggle:hideImage", hideImage);
		request.setAttribute("liferay-ui:toggle:showMessage", showMessage);
		request.setAttribute("liferay-ui:toggle:hideMessage", hideMessage);
		request.setAttribute("liferay-ui:toggle:stateVar", stateVar);
		request.setAttribute(
			"liferay-ui:toggle:defaultStateValue", defaultStateValue);
		request.setAttribute("liferay-ui:toggle:defaultImage", defaultImage);
		request.setAttribute(
			"liferay-ui:toggle:defaultMessage", defaultMessage);

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(page);

		requestDispatcher.include(request, response);
	}

	public int doEndTag() throws JspException {
		try {
			ServletContext servletContext = getServletContext();
			HttpServletRequest request = getServletRequest();
			StringServletResponse response = getServletResponse();

			doTag(
				getPage(), _id, _showImage, _hideImage, _showMessage,
				_hideMessage, _defaultShowContent, _stateVar, servletContext,
				request, response);

			pageContext.getOut().print(response.getString());

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
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

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/ui/toggle/page.jsp";

	private String _id;
	private String _showImage;
	private String _hideImage;
	private String _showMessage;
	private String _hideMessage;
	private boolean _defaultShowContent = true;
	private String _stateVar;

}