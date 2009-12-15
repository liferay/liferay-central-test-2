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

package com.liferay.taglib.util;

import com.liferay.portal.freemarker.FreeMarkerVariables;
import com.liferay.portal.kernel.freemarker.FreeMarkerContext;
import com.liferay.portal.kernel.freemarker.FreeMarkerEngineUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.velocity.VelocityContext;
import com.liferay.portal.kernel.velocity.VelocityEngineUtil;
import com.liferay.portal.model.Theme;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.velocity.VelocityContextPool;
import com.liferay.portal.velocity.VelocityVariables;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.servlet.HttpRequestHashModel;

import freemarker.template.ObjectWrapper;

import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.tiles.ComponentConstants;
import org.apache.struts.tiles.ComponentContext;

/**
 * <a href="ThemeUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Raymond Augé
 * @author Mika Koivisto
 */
public class ThemeUtil {

	public static void include(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, PageContext pageContext, String page,
			Theme theme)
		throws Exception {

		String extension = theme.getTemplateExtension();

		if (extension.equals("vm")) {
			includeVM(servletContext, request, pageContext, page, theme, true);
		}
		else if (extension.equals("ftl")) {
			includeFTL(servletContext, request, pageContext, page, theme, true);
		}
		else {
			String path =
				theme.getTemplatesPath() + StringPool.SLASH + page;

			includeJSP(servletContext, request, response, path, theme);
		}
	}

	public static String includeFTL(
			ServletContext servletContext, HttpServletRequest request,
			PageContext pageContext, String page, Theme theme, boolean write)
		throws Exception {

		// The servlet context name will be null when the theme is deployed to
		// the root directory in Tomcat. See
		// com.liferay.portal.servlet.MainServlet and
		// com.liferay.portlet.PortletContextImpl for other cases where a null
		// servlet context name is also converted to an empty string.

		String ctxName = GetterUtil.getString(theme.getServletContextName());

		if (VelocityContextPool.get(ctxName) == null) {

			// This should only happen if the FreeMarker template is the first
			// page to be accessed in the system

			VelocityContextPool.put(ctxName, servletContext);
		}

		int pos = page.lastIndexOf(StringPool.PERIOD);

		StringBuilder sb = new StringBuilder();

		sb.append(ctxName);
		sb.append(theme.getFreeMarkerTemplateLoader());
		sb.append(theme.getTemplatesPath());
		sb.append(StringPool.SLASH);
		sb.append(page.substring(0, pos));
		sb.append(".ftl");

		String source = sb.toString();

		if (!FreeMarkerEngineUtil.resourceExists(source)) {
			_log.error(source + " does not exist");

			return null;
		}

		StringWriter stringWriter = new StringWriter();

		FreeMarkerContext freeMarkerContext =
			FreeMarkerEngineUtil.getWrappedStandardToolsContext();

		// FreeMarker variables

		FreeMarkerVariables.insertVariables(freeMarkerContext, request);

		// Theme servlet context

		ServletContext themeServletContext = VelocityContextPool.get(ctxName);

		// liferay:include tag library

		StringServletResponse stringResponse = new StringServletResponse(
			(HttpServletResponse)pageContext.getResponse());

		VelocityTaglib velocityTaglib = new VelocityTaglib(
			servletContext, request, stringResponse, pageContext);

		request.setAttribute(WebKeys.VELOCITY_TAGLIB, velocityTaglib);

		freeMarkerContext.put("themeServletContext", themeServletContext);
		freeMarkerContext.put("taglibLiferay", velocityTaglib);
		freeMarkerContext.put("theme", velocityTaglib);

		// Portal JSP tag library factory

		TaglibFactory portalTaglib = new TaglibFactory(servletContext);

		freeMarkerContext.put("PortalJspTagLibs", portalTaglib);

		// Theme JSP tag library factory

		TaglibFactory themeTaglib = new TaglibFactory(themeServletContext);

		freeMarkerContext.put("ThemeJspTaglibs", themeTaglib);

		// Required by FreeMarker JSP Taglib support

		HttpServletResponse response =
			(HttpServletResponse) pageContext.getResponse();
		HttpRequestHashModel httpRequestModel = new HttpRequestHashModel(
			request, response, ObjectWrapper.DEFAULT_WRAPPER);
		freeMarkerContext.put("Request", httpRequestModel);

		// Merge templates

		FreeMarkerEngineUtil.mergeTemplate(
			source, freeMarkerContext, stringWriter);

		// Print output

		String output = stringWriter.toString();

		if (write) {
			pageContext.getOut().print(output);

			return null;
		}
		else {
			return output;
		}
	}

	public static void includeJSP(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, String path, Theme theme)
		throws Exception {

		String tilesTitle = _getTilesVariables(request, "title");
		String tilesContent = _getTilesVariables(request, "content");
		boolean tilesSelectable = GetterUtil.getBoolean(
			_getTilesVariables(request, "selectable"));

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		themeDisplay.setTilesTitle(tilesTitle);
		themeDisplay.setTilesContent(tilesContent);
		themeDisplay.setTilesSelectable(tilesSelectable);

		if (theme.isWARFile()) {
			ServletContext themeServletContext = servletContext.getContext(
				theme.getContextPath());

			if (themeServletContext == null) {
				_log.error(
					"Theme " + theme.getThemeId() + " cannot find its " +
						"servlet context at " + theme.getServletContextName());
			}
			else {
				RequestDispatcher requestDispatcher =
					themeServletContext.getRequestDispatcher(path);

				if (requestDispatcher == null) {
					_log.error(
						"Theme " + theme.getThemeId() + " does not have " +
							path);
				}
				else {
					requestDispatcher.include(request, response);
				}
			}
		}
		else {
			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(path);

			if (requestDispatcher == null) {
				_log.error(
					"Theme " + theme.getThemeId() + " does not have " + path);
			}
			else {
				requestDispatcher.include(request, response);
			}
		}
	}

	public static String includeVM(
			ServletContext servletContext, HttpServletRequest request,
			PageContext pageContext, String page, Theme theme, boolean write)
		throws Exception {

		// The servlet context name will be null when the theme is deployed to
		// the root directory in Tomcat. See
		// com.liferay.portal.servlet.MainServlet and
		// com.liferay.portlet.PortletContextImpl for other cases where a null
		// servlet context name is also converted to an empty string.

		String ctxName = GetterUtil.getString(theme.getServletContextName());

		if (VelocityContextPool.get(ctxName) == null) {

			// This should only happen if the Velocity template is the first
			// page to be accessed in the system

			VelocityContextPool.put(ctxName, servletContext);
		}

		int pos = page.lastIndexOf(StringPool.PERIOD);

		StringBuilder sb = new StringBuilder();

		sb.append(ctxName);
		sb.append(theme.getVelocityResourceListener());
		sb.append(theme.getTemplatesPath());
		sb.append(StringPool.SLASH);
		sb.append(page.substring(0, pos));
		sb.append(".vm");

		String source = sb.toString();

		if (!VelocityEngineUtil.resourceExists(source)) {
			_log.error(source + " does not exist");

			return null;
		}

		StringWriter stringWriter = new StringWriter();

		VelocityContext velocityContext =
			VelocityEngineUtil.getWrappedStandardToolsContext();

		// Velocity variables

		VelocityVariables.insertVariables(velocityContext, request);

		// Theme servlet context

		ServletContext themeServletContext = VelocityContextPool.get(ctxName);

		// liferay:include tag library

		StringServletResponse stringResponse = new StringServletResponse(
			(HttpServletResponse)pageContext.getResponse());

		VelocityTaglib velocityTaglib = new VelocityTaglib(
			servletContext, request, stringResponse, pageContext);

		request.setAttribute(WebKeys.VELOCITY_TAGLIB, velocityTaglib);

		velocityContext.put("themeServletContext", themeServletContext);
		velocityContext.put("taglibLiferay", velocityTaglib);
		velocityContext.put("theme", velocityTaglib);

		// Merge templates

		VelocityEngineUtil.mergeTemplate(source, velocityContext, stringWriter);

		// Print output

		String output = stringWriter.toString();

		if (write) {
			pageContext.getOut().print(output);

			return null;
		}
		else {
			return output;
		}
	}

	private static String _getTilesVariables(
		HttpServletRequest request, String attributeName) {

		ComponentContext componentContext =
			(ComponentContext)request.getAttribute(
				ComponentConstants.COMPONENT_CONTEXT);

		String value = null;

		if (componentContext != null) {
			value = (String)componentContext.getAttribute(attributeName);
		}

		return value;
	}

	private static Log _log = LogFactoryUtil.getLog(ThemeUtil.class);

}