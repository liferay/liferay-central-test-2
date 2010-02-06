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

package com.liferay.util.bridges.php;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PortletServletObjectsFactory;
import com.liferay.portal.kernel.servlet.ServletObjectsFactory;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.util.servlet.DynamicServletConfig;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.portals.bridges.common.ScriptPostProcess;

/**
 * <a href="PHPPortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class PHPPortlet extends GenericPortlet {

	public void init(PortletConfig portletConfig) throws PortletException {
		super.init(portletConfig);

		editUri = getInitParameter("edit-uri");
		helpUri = getInitParameter("help-uri");
		viewUri = getInitParameter("view-uri");

		addPortletParams = GetterUtil.getBoolean(
			portletConfig.getInitParameter("add-portlet-params"), true);

		String servletObjectsFactoryName = GetterUtil.getString(
			getInitParameter("servlet-objects-factory"),
			PortletServletObjectsFactory.class.getName());

		try {
			Class<?> servletObjectsFactoryClass = Class.forName(
				servletObjectsFactoryName);

			servletObjectsFactory =
				(ServletObjectsFactory)servletObjectsFactoryClass.newInstance();
		}
		catch (Exception e) {
			throw new PortletException(
				"Unable to instantiate factory" + servletObjectsFactoryName, e);
		}
	}

	public void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		String phpUri = renderRequest.getParameter(_PHP_URI_PARAM);

		if (phpUri != null) {
			processPHP(phpUri, renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	public void doEdit(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (renderRequest.getPreferences() == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			processPHP(editUri, renderRequest, renderResponse);
		}
	}

	public void doHelp(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		processPHP(helpUri, renderRequest, renderResponse);
	}

	public void doView(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		processPHP(viewUri, renderRequest, renderResponse);
	}

	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse) {
		String phpURI = actionRequest.getParameter(_PHP_URI_PARAM);

		if (phpURI != null) {
			actionResponse.setRenderParameter(_PHP_URI_PARAM, phpURI);
		}
	}

	public void destroy() {
		if (quercusServlet != null) {
			quercusServlet.destroy();
		}
	}

	protected synchronized void initQuercus(ServletConfig servletConfig)
		throws PortletException {

		if (quercusServlet == null) {
			try {
				quercusServlet = (HttpServlet)Class.forName(
					_QUERCUS_SERVLET).newInstance();

				Map<String, String> params = new HashMap<String, String>();

				Enumeration<String> enu = servletConfig.getInitParameterNames();

				while (enu.hasMoreElements()) {
					String name = enu.nextElement();

					if (!name.equals("portlet-class")) {
						params.put(name, servletConfig.getInitParameter(name));
					}
				}

				servletConfig = new DynamicServletConfig(servletConfig, params);

				quercusServlet.init(servletConfig);
			}
			catch (Exception e) {
				throw new PortletException(e);
			}
		}
	}

	protected void processPHP(
		String phpURI, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		try {
			ServletConfig servletConfig =
				servletObjectsFactory.getServletConfig(
					getPortletConfig(), renderRequest);

			initQuercus(servletConfig);

			HttpServletRequest request =
				servletObjectsFactory.getServletRequest(renderRequest);
			HttpServletResponse response =
				servletObjectsFactory.getServletResponse(
					renderRequest, renderResponse);

			PHPServletRequest phpRequest = new PHPServletRequest(
				request, servletConfig, renderRequest, renderResponse,
				getPortletConfig(), phpURI, addPortletParams);

			StringServletResponse stringResponse = new StringServletResponse(
				response);

			quercusServlet.service(phpRequest, stringResponse);

			String result = stringResponse.getString();

			if (stringResponse.getContentType().startsWith("text/")) {
				result = rewriteURLs(result, renderResponse.createRenderURL());
			}

			renderResponse.setContentType(stringResponse.getContentType());

			PrintWriter writer = renderResponse.getWriter();

			writer.write(result.toCharArray());
		}
		catch (Exception e) {
			_log.error("Error processing PHP", e);
		}
	}

	protected String rewriteURLs(String page, PortletURL portletURL) {
		ScriptPostProcess processor = new ScriptPostProcess();

		processor.setInitalPage(new StringBuffer(page));
		processor.postProcessPage(portletURL, _PHP_URI_PARAM);

		return processor.getFinalizedPage();
	}

	private static final String _PHP_URI_PARAM = "phpURI";

	private static final String _QUERCUS_SERVLET =
		"com.caucho.quercus.servlet.QuercusServlet";

	private static Log _log = LogFactoryUtil.getLog(PHPPortlet.class);

	protected String editUri;
	protected String helpUri;
	protected String viewUri;
	protected boolean addPortletParams;
	protected ServletObjectsFactory servletObjectsFactory;
	protected HttpServlet quercusServlet;

}