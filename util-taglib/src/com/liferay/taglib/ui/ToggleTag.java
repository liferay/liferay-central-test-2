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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.ServletContext;
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
		throws Exception {

		doTag(
			_PAGE, id, showImage, hideImage, showMessage, hideMessage,
			defaultShowContent, stateVar, servletContext, request, response);
	}

	public static void doTag(
			String page, String id, String showImage, String hideImage,
			String showMessage, String hideMessage, boolean defaultShowContent,
			String stateVar, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			Object idWrapper = id;

			if (idWrapper == null) {
				idWrapper = new NullWrapper(String.class.getName());
			}

			Object showImageWrapper = showImage;

			if (showImageWrapper == null) {
				showImageWrapper = new NullWrapper(String.class.getName());
			}

			Object hideImageWrapper = hideImage;

			if (hideImageWrapper == null) {
				hideImageWrapper = new NullWrapper(String.class.getName());
			}

			Object showMessageWrapper = showMessage;

			if (showMessageWrapper == null) {
				showMessageWrapper = new NullWrapper(String.class.getName());
			}

			Object hideMessageWrapper = hideMessage;

			if (hideMessageWrapper == null) {
				hideMessageWrapper = new NullWrapper(String.class.getName());
			}

			Object stateVarWrapper = stateVar;

			if (stateVarWrapper == null) {
				stateVarWrapper = new NullWrapper(String.class.getName());
			}

			MethodWrapper methodWrapper = new MethodWrapper(
				_TAG_CLASS, _TAG_DO_END_METHOD,
				new Object[] {
					page, idWrapper, showImageWrapper, hideImageWrapper,
					showMessageWrapper, hideMessageWrapper,
					new BooleanWrapper(defaultShowContent), stateVarWrapper,
					servletContext, request, response
				});

			MethodInvoker.invoke(methodWrapper);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw e;
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
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

	private static final String _TAG_CLASS =
		"com.liferay.portal.servlet.taglib.ui.ToggleTagUtil";

	private static final String _TAG_DO_END_METHOD = "doEndTag";

	private static final String _PAGE = "/html/taglib/ui/toggle/page.jsp";

	private static Log _log = LogFactoryUtil.getLog(ToggleTag.class);

	private String _id;
	private String _showImage;
	private String _hideImage;
	private String _showMessage;
	private String _hideMessage;
	private boolean _defaultShowContent = true;
	private String _stateVar;

}