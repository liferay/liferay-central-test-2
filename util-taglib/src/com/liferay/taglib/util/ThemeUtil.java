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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Theme;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.velocity.VelocityVariables;
import com.liferay.util.GetterUtil;
import com.liferay.util.servlet.StringServletResponse;
import com.liferay.util.velocity.VelocityContextPool;
import com.liferay.util.velocity.VelocityResourceListener;

import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.tiles.ComponentConstants;
import org.apache.struts.tiles.ComponentContext;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * <a href="ThemeUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 *
 */
public class ThemeUtil {

	public static void include(
			ServletContext ctx, HttpServletRequest req, HttpServletResponse res,
			PageContext pageContext, String page, Theme theme)
		throws Exception {

		String extension = theme.getTemplateExtension();

		if (extension.equals("vm")) {
			includeVM(ctx, req, pageContext, page, theme);
		}
		else {
			String path =
				theme.getTemplatesPath() + StringPool.SLASH + page;

			includeJSP(ctx, req, res, path, theme);
		}
	}

	protected static void includeJSP(
			ServletContext ctx, HttpServletRequest req, HttpServletResponse res,
			String path, Theme theme)
		throws Exception {

		String tilesTitle = _getTilesVariables(req, "title");
		String tilesContent = _getTilesVariables(req, "content");
		boolean tilesSelectable = GetterUtil.getBoolean(
			_getTilesVariables(req, "selectable"));

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		themeDisplay.setTilesTitle(tilesTitle);
		themeDisplay.setTilesContent(tilesContent);
		themeDisplay.setTilesSelectable(tilesSelectable);

		if (theme.isWARFile()) {
			ServletContext themeCtx = ctx.getContext(
				StringPool.SLASH + theme.getServletContextName());

			if (themeCtx == null) {
				_log.error(
					"Theme " + theme.getThemeId() + " cannot find its " +
						"servlet context at " + theme.getServletContextName());
			}
			else {
				RequestDispatcher rd = themeCtx.getRequestDispatcher(path);

				if (rd == null) {
					_log.error(
						"Theme " + theme.getThemeId() + " does not have " +
							path);
				}
				else {
					rd.include(req, res);
				}
			}
		}
		else {
			RequestDispatcher rd = ctx.getRequestDispatcher(path);

			if (rd == null) {
				_log.error(
					"Theme " + theme.getThemeId() + " does not have " + path);
			}
			else {
				rd.include(req, res);
			}
		}
	}

	protected static void includeVM(
			ServletContext ctx, HttpServletRequest req, PageContext pageContext,
			String page, Theme theme)
		throws Exception {

		// Get template

		// The servlet context name will be null when the theme is deployed to
		// the root directory in Tomcat. See
		// com.liferay.portlet.PortletContextImpl where a null servlet context
		// name is also converted to an empty string.

		String ctxName = GetterUtil.getString(theme.getServletContextName());

		if (VelocityContextPool.get(ctxName) == null) {

			// This should only happen if the Velocity template is the first
			// page to be accessed in the system

			VelocityContextPool.put(ctxName, ctx);
		}

		int pos = page.lastIndexOf(StringPool.PERIOD);

		String source =
			ctxName + VelocityResourceListener.SERVLET_SEPARATOR +
				theme.getTemplatesPath() + StringPool.SLASH +
					page.substring(0, pos) + ".vm";

		if (!Velocity.resourceExists(source)) {
			_log.error(source + " does not exist");

			return;
		}

		Template template = Velocity.getTemplate(source);

		StringWriter sw = new StringWriter();

		VelocityContext vc = new VelocityContext();

		// Velocity variables

		VelocityVariables.insertVariables(vc, ctx, req, pageContext);

		// liferay:include tag library

		StringServletResponse stringServletResponse = new StringServletResponse(
			(HttpServletResponse)pageContext.getResponse());

		vc.put(
			"taglibLiferay",
			new VelocityTaglib(ctx, req, stringServletResponse, pageContext));

		// Merge templates

		template.merge(vc, sw);

		// Print output

		pageContext.getOut().print(sw.toString());
	}

	private static String _getTilesVariables(
		HttpServletRequest req, String attributeName) {

		ComponentContext componentContext = (ComponentContext)req.getAttribute(
			ComponentConstants.COMPONENT_CONTEXT);

		String value = null;

		if (componentContext != null) {
			value = (String)componentContext.getAttribute(attributeName);
		}

		return value;
	}

	private static Log _log = LogFactory.getLog(ThemeUtil.class);

}