/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.taglib.util;

import com.liferay.portal.freemarker.FreeMarkerVariables;
import com.liferay.portal.kernel.freemarker.FreeMarkerContext;
import com.liferay.portal.kernel.freemarker.FreeMarkerEngineUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.velocity.VelocityContext;
import com.liferay.portal.kernel.velocity.VelocityEngineUtil;
import com.liferay.portal.model.Theme;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.velocity.VelocityContextPool;
import com.liferay.portal.velocity.VelocityVariables;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.servlet.HttpRequestHashModel;

import freemarker.template.ObjectWrapper;

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

		if (extension.equals(_TEMPLATE_EXTENSION_FTL)) {
			includeFTL(servletContext, request, pageContext, page, theme, true);
		}
		else if (extension.equals(_TEMPLATE_EXTENSION_VM)) {
			includeVM(servletContext, request, pageContext, page, theme, true);
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

		StringBundler sb = new StringBundler(7);

		sb.append(ctxName);
		sb.append(theme.getFreeMarkerTemplateLoader());
		sb.append(theme.getTemplatesPath());
		sb.append(StringPool.SLASH);
		sb.append(page.substring(0, pos));
		sb.append(StringPool.PERIOD);
		sb.append(_TEMPLATE_EXTENSION_FTL);

		String source = sb.toString();

		if (!FreeMarkerEngineUtil.resourceExists(source)) {
			_log.error(source + " does not exist");

			return null;
		}

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(true);

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

		// FreeMarker JSP tag library support

		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		HttpRequestHashModel httpRequestHashModel = new HttpRequestHashModel(
			request, response, ObjectWrapper.DEFAULT_WRAPPER);

		freeMarkerContext.put("Request", httpRequestHashModel);

		// Merge templates

		FreeMarkerEngineUtil.mergeTemplate(
			source, freeMarkerContext, unsyncStringWriter);

		if (write) {
			StringBundler sb = unsyncStringWriter.getStringBundler();

			sb.writeTo(pageContext.getOut());

			return null;
		}
		else {
			return unsyncStringWriter.toString();
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

		StringBundler sb = new StringBundler(7);

		sb.append(ctxName);
		sb.append(theme.getVelocityResourceListener());
		sb.append(theme.getTemplatesPath());
		sb.append(StringPool.SLASH);
		sb.append(page.substring(0, pos));
		sb.append(StringPool.PERIOD);
		sb.append(_TEMPLATE_EXTENSION_VM);

		String source = sb.toString();

		if (!VelocityEngineUtil.resourceExists(source)) {
			_log.error(source + " does not exist");

			return null;
		}

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(true);

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

		VelocityEngineUtil.mergeTemplate(
			source, velocityContext, unsyncStringWriter);

		if (write) {
			StringBundler sb = unsyncStringWriter.getStringBundler();

			sb.writeTo(pageContext.getOut());

			return null;
		}
		else {
			return unsyncStringWriter.toString();
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

	private static final String _TEMPLATE_EXTENSION_FTL = "ftl";

	private static final String _TEMPLATE_EXTENSION_VM = "vm";

	private static Log _log = LogFactoryUtil.getLog(ThemeUtil.class);

}