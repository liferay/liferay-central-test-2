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
import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <a href="InputPermissionsParamsTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class InputPermissionsParamsTag extends TagSupport {

	public static String doTag(
			String modelName, PageContext pageContext)
		throws Exception {

		Object returnObj = null;

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			MethodWrapper methodWrapper = new MethodWrapper(
				_TAG_CLASS, _TAG_DO_END_METHOD,
				new Object[] {modelName, pageContext}
			);

			returnObj = MethodInvoker.invoke(methodWrapper);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}

		if (returnObj != null) {
			return returnObj.toString();
		}
		else {
			return StringPool.BLANK;
		}
	}

	public int doEndTag() throws JspException {
		try {
			doTag(_modelName, pageContext);

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setModelName(String modelName) {
		_modelName = modelName;
	}

	private static final String _TAG_CLASS =
		"com.liferay.portal.servlet.taglib.ui.InputPermissionsParamsTagUtil";

	private static final String _TAG_DO_END_METHOD = "doEndTag";

	private static Log _log = LogFactoryUtil.getLog(
		InputPermissionsParamsTag.class);

	private String _modelName;

}