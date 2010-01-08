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

package com.liferay.portal.servlet.taglib.ui;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DeterminateKeyGenerator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.SessionClicks;
import com.liferay.portal.util.WebKeys;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * <a href="ToggleTagUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ToggleTagUtil {

	public static void doEndTag(
			String page, String id, String showImage, String hideImage,
			String showMessage, String hideMessage, boolean defaultShowContent,
			String stateVar, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response)
		throws JspException {

		try {
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
			String defaultMessage =
				defaultShowContent ? hideMessage : showMessage;

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
					ToggleTagUtil.class.getName());
			}

			request.setAttribute("liferay-ui:toggle:id", id);
			request.setAttribute("liferay-ui:toggle:showImage", showImage);
			request.setAttribute("liferay-ui:toggle:hideImage", hideImage);
			request.setAttribute("liferay-ui:toggle:showMessage", showMessage);
			request.setAttribute("liferay-ui:toggle:hideMessage", hideMessage);
			request.setAttribute("liferay-ui:toggle:stateVar", stateVar);
			request.setAttribute(
				"liferay-ui:toggle:defaultStateValue", defaultStateValue);
			request.setAttribute(
				"liferay-ui:toggle:defaultImage", defaultImage);
			request.setAttribute(
				"liferay-ui:toggle:defaultMessage", defaultMessage);

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(page);

			requestDispatcher.include(request, response);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new JspException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ToggleTagUtil.class);

}