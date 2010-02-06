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

package com.liferay.portlet.webproxy;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portlet.RenderResponseImpl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.portletbridge.portlet.PortletBridgePortlet;

/**
 * <a href="WebProxyPortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WebProxyPortlet extends PortletBridgePortlet {

	public void init() {
		try {
			super.init();

			_enabled = true;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}

		if (!_enabled && ServerDetector.isWebLogic() && _log.isInfoEnabled()) {
			_log.info(
				"WebProxyPortlet will not be enabled unless Liferay's " +
					"serializer.jar and xalan.jar files are copied to the " +
						"JDK's endorsed directory");
		}
	}

	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (!_enabled) {
			printError(renderResponse);

			return;
		}

		PortletPreferences preferences = renderRequest.getPreferences();

		String initUrl = preferences.getValue("initUrl", StringPool.BLANK);

		if (Validator.isNull(initUrl)) {
			PortletRequestDispatcher portletRequestDispatcher =
				getPortletContext().getRequestDispatcher(
					StrutsUtil.TEXT_HTML_DIR + "/portal/portlet_not_setup.jsp");

			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
		else {
			super.doView(renderRequest, renderResponse);

			RenderResponseImpl renderResponseImpl =
				(RenderResponseImpl)renderResponse;

			StringServletResponse stringResponse = (StringServletResponse)
				renderResponseImpl.getHttpServletResponse();

			String output = stringResponse.getString();

			output = StringUtil.replace(output, "//pbhs/", "/pbhs/");

			stringResponse.setString(output);
		}
	}

	protected void printError(RenderResponse renderResponse)
		throws IOException {

		renderResponse.setContentType(ContentTypes.TEXT_HTML_UTF8);

		PrintWriter writer = renderResponse.getWriter();

		writer.print(
			"WebProxyPortlet will not be enabled unless Liferay's " +
				"serializer.jar and xalan.jar files are copied to the " +
					"JDK's endorsed directory");

		writer.close();
	}

	private static Log _log = LogFactoryUtil.getLog(WebProxyPortlet.class);

	private boolean _enabled;

}