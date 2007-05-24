/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.sample.php.portlet;

import com.caucho.quercus.servlet.QuercusServlet;

import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.servlet.StringServletResponse;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.portals.bridges.common.ScriptPostProcess;

/**
 * <a href="PHPPortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class PHPPortlet extends GenericPortlet {

	public static final String PHP_URI_PARAM = "phpURI";

	public void init() throws PortletException {
		editUri = getInitParameter("edit-uri");
		helpUri = getInitParameter("help-uri");
		viewUri = getInitParameter("view-uri");
	}

	public void doDispatch(RenderRequest req, RenderResponse res)
		throws IOException, PortletException {

		String phpUri = req.getParameter(PHP_URI_PARAM);

		if (phpUri != null) {
			processPHP(phpUri, req, res);
		}
		else {
			super.doDispatch(req, res);
		}
	}

	public void doEdit(RenderRequest req, RenderResponse res)
		throws IOException, PortletException {

		if (req.getPreferences() == null) {
			super.doEdit(req, res);
		}
		else {
			processPHP(editUri, req, res);
		}
	}

	public void doHelp(RenderRequest req, RenderResponse res)
		throws IOException, PortletException {

		processPHP(helpUri, req, res);
	}

	public void doView(RenderRequest req, RenderResponse res)
		throws IOException, PortletException {

		processPHP(viewUri, req, res);
	}

	public void processAction(ActionRequest req, ActionResponse res)
		throws IOException, PortletException {
		String phpURI = req.getParameter(PHP_URI_PARAM);
		if (phpURI != null) {
			res.setRenderParameter(PHP_URI_PARAM, phpURI);
		}
	}


	public void destroy() {
		if (_quercusServlet != null) {
			_quercusServlet.destroy();
		}
	}

	protected void processPHP(String phpURI, RenderRequest req, RenderResponse res)
		throws IOException, PortletException {

		try {
			ServletConfig config =
				(ServletConfig) req.getAttribute(
					PortletServlet.PORTLET_SERVLET_CONFIG);

			_initQuercus(config);

			HttpServletRequest httpReq =
				(HttpServletRequest) req.getAttribute(
					PortletServlet.PORTLET_SERVLET_REQUEST);
			HttpServletResponse httpRes =
				(HttpServletResponse) req.getAttribute(
					PortletServlet.PORTLET_SERVLET_RESPONSE);

			PHPServletRequest phpReq =
				new PHPServletRequest(
					httpReq, req, res, getPortletConfig(), config, phpURI);
			
			StringServletResponse phpRes =
				new StringServletResponse(httpRes);

			_quercusServlet.service(phpReq, phpRes);

			String result = phpRes.getString();

			if (phpRes.getContentType().startsWith("text/")) {
				result = _rewriteURLs(result, res.createRenderURL());
			}

			httpRes.getWriter().write(result.toCharArray());
		}
		catch (Exception e) {
			_log.error("Error execuring PHP", e);
		}
	}

	private String _rewriteURLs(String page, PortletURL portletURL) {
		ScriptPostProcess processor = new ScriptPostProcess();

		processor.setInitalPage(new StringBuffer(page));
		processor.postProcessPage(portletURL, PHP_URI_PARAM);

		return processor.getFinalizedPage();
	}

	private synchronized void _initQuercus(ServletConfig config)
		throws PortletException {
		if (_quercusServlet == null) {
			_quercusServlet = new QuercusServlet();

			try {
				_quercusServlet.init(config);
			}
			catch (Exception e) {
				throw new PortletException(e);
			}
		}
	}

	protected String editUri;
	protected String helpUri;
	protected String viewUri;

	private QuercusServlet _quercusServlet;
	private static Log _log = LogFactory.getLog(PHPPortlet.class);

}