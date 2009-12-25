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

package com.liferay.portlet.layoutconfiguration.util;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.velocity.VelocityContext;
import com.liferay.portal.kernel.velocity.VelocityEngineUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.PortletDisplayFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.velocity.VelocityVariables;
import com.liferay.portlet.layoutconfiguration.util.velocity.TemplateProcessor;
import com.liferay.portlet.layoutconfiguration.util.xml.RuntimeLogic;

import java.util.Iterator;
import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * <a href="RuntimePortletUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class RuntimePortletUtil {

	public static void processPortlet(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			RenderRequest renderRequest, RenderResponse renderResponse,
			String portletId, String queryString)
		throws Exception {

		processPortlet(
			sb, servletContext, request, response, renderRequest,
			renderResponse, portletId, queryString, null, null, null);
	}

	public static void processPortlet(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			RenderRequest renderRequest, RenderResponse renderResponse,
			String portletId, String queryString, String columnId,
			Integer columnPos, Integer columnCount)
		throws Exception {

		processPortlet(
			sb, servletContext, request, response, renderRequest,
			renderResponse, null, portletId, queryString, columnId, columnPos,
			columnCount, null);
	}

	public static void processPortlet(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, String queryString, String columnId,
			Integer columnPos, Integer columnCount, String path)
		throws Exception {

		processPortlet(
			sb, servletContext, request, response, null, null, portlet,
			portlet.getPortletId(), queryString, columnId, columnPos,
			columnCount, path);
	}

	public static void processPortlet(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			RenderRequest renderRequest, RenderResponse renderResponse,
			Portlet portlet, String portletId, String queryString,
			String columnId, Integer columnPos, Integer columnCount,
			String path)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (portlet == null) {
			portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletId);
		}

		if ((portlet != null) && (portlet.isInstanceable()) &&
			(!portlet.isAddDefaultResource())) {

			String instanceId = portlet.getInstanceId();

			if (Validator.isNotNull(instanceId) &&
				Validator.isPassword(instanceId) &&
				(instanceId.length() == 4)) {

				/*portletId +=
					PortletConstants.INSTANCE_SEPARATOR + instanceId;

				portlet = PortletLocalServiceUtil.getPortletById(
					themeDisplay.getCompanyId(), portletId);*/
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Portlet " + portlet.getPortletId() +
							" is instanceable but does not have a " +
								"valid instance id");
				}

				portlet = null;
			}
		}

		if (portlet == null) {
			return;
		}

		// Capture the current portlet's settings to reset them once the child
		// portlet is rendered

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletDisplay portletDisplayClone = PortletDisplayFactory.create();

		portletDisplay.copyTo(portletDisplayClone);

		PortletConfig portletConfig = (PortletConfig)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		try {
			PortalUtil.renderPortlet(
				sb, servletContext, request, response, portlet, queryString,
				columnId, columnPos, columnCount, path);
		}
		finally {
			portletDisplay.copyFrom(portletDisplayClone);

			portletDisplayClone.recycle();

			_defineObjects(
				request, portletConfig, renderRequest, renderResponse);
		}
	}

	public static String processTemplate(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, PageContext pageContext,
			String velocityTemplateId, String velocityTemplateContent)
		throws Exception {

		return processTemplate(
			servletContext, request, response, pageContext, null,
			velocityTemplateId, velocityTemplateContent);
	}

	public static String processTemplate(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, PageContext pageContext,
			String portletId, String velocityTemplateId,
			String velocityTemplateContent)
		throws Exception {

		if (Validator.isNull(velocityTemplateContent)) {
			return StringPool.BLANK;
		}

		TemplateProcessor processor = new TemplateProcessor(
			servletContext, request, response, portletId);

		VelocityContext velocityContext =
			VelocityEngineUtil.getWrappedStandardToolsContext();

		velocityContext.put("processor", processor);

		// Velocity variables

		VelocityVariables.insertVariables(velocityContext, request);

		// liferay:include tag library

		StringServletResponse stringResponse = new StringServletResponse(
			response);

		MethodWrapper methodWrapper = new MethodWrapper(
			"com.liferay.taglib.util.VelocityTaglib", "init",
			new Object[] {
				servletContext, request, stringResponse, pageContext
			});

		Object velocityTaglib = MethodInvoker.invoke(methodWrapper);

		velocityContext.put("taglibLiferay", velocityTaglib);
		velocityContext.put("theme", velocityTaglib);

		UnsyncStringWriter stringWriter = new UnsyncStringWriter(true);

		try {
			VelocityEngineUtil.mergeTemplate(
				velocityTemplateId, velocityTemplateContent, velocityContext,
				stringWriter);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw e;
		}

		String output = stringWriter.toString();

		Map<String, String> columnsMap = processor.getColumnsMap();

		Iterator<Map.Entry<String, String>> columnsMapItr =
			columnsMap.entrySet().iterator();

		while (columnsMapItr.hasNext()) {
			Map.Entry<String, String> entry = columnsMapItr.next();

			String key = entry.getKey();
			String value = entry.getValue();

			output = StringUtil.replace(output, key, value);
		}

		Map<Portlet, Object[]> portletsMap = processor.getPortletsMap();

		Iterator<Map.Entry<Portlet, Object[]>> portletsMapItr =
			portletsMap.entrySet().iterator();

		while (portletsMapItr.hasNext()) {
			Map.Entry<Portlet, Object[]> entry = portletsMapItr.next();

			Portlet portlet = entry.getKey();
			Object[] value = entry.getValue();

			String queryString = (String)value[0];
			String columnId = (String)value[1];
			Integer columnPos = (Integer)value[2];
			Integer columnCount = (Integer)value[3];

			StringBuilder sb = new StringBuilder();

			processPortlet(
				sb, servletContext, request, response, portlet, queryString,
				columnId, columnPos, columnCount, null);

			output = StringUtil.replace(
				output, "[$TEMPLATE_PORTLET_" + portlet.getPortletId() + "$]",
				sb.toString());
		}

		return output;
	}

	public static String processXML(
			HttpServletRequest request, String content,
			RuntimeLogic runtimeLogic)
		throws Exception {

		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		try {
			request.setAttribute(WebKeys.RENDER_PORTLET_RESOURCE, Boolean.TRUE);

			StringBuilder sb = new StringBuilder();

			int x = 0;
			int y = content.indexOf(runtimeLogic.getOpenTag());

			while (y != -1) {
				sb.append(content.substring(x, y));

				int close1 = content.indexOf(runtimeLogic.getClose1Tag(), y);
				int close2 = content.indexOf(runtimeLogic.getClose2Tag(), y);

				if ((close2 == -1) || ((close1 != -1) && (close1 < close2))) {
					x = close1 + runtimeLogic.getClose1Tag().length();
				}
				else {
					x = close2 + runtimeLogic.getClose2Tag().length();
				}

				runtimeLogic.processXML(sb, content.substring(y, x));

				y = content.indexOf(runtimeLogic.getOpenTag(), x);
			}

			if (y == -1) {
				sb.append(content.substring(x, content.length()));
			}

			return sb.toString();
		}
		finally {
			request.removeAttribute(WebKeys.RENDER_PORTLET_RESOURCE);
		}
	}

	private static void _defineObjects(
		HttpServletRequest request, PortletConfig portletConfig,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		if (portletConfig != null) {
			request.setAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG, portletConfig);
		}

		if (renderRequest != null) {
			request.setAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST, renderRequest);
		}

		if (renderResponse != null) {
			request.setAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE, renderResponse);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(RuntimePortletUtil.class);

}