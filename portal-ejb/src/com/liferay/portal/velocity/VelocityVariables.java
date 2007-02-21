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

package com.liferay.portal.velocity;

import com.liferay.portal.language.LanguageUtil_IW;
import com.liferay.portal.language.UnicodeLanguageUtil_IW;
import com.liferay.portal.model.Theme;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil_IW;
import com.liferay.portal.util.PropsUtil_IW;
import com.liferay.portal.util.ServiceLocator;
import com.liferay.portal.util.SessionClicks_IW;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletConfigImpl;
import com.liferay.portlet.PortletURLFactory;
import com.liferay.util.ArrayUtil_IW;
import com.liferay.util.BrowserSniffer_IW;
import com.liferay.util.GetterUtil;
import com.liferay.util.GetterUtil_IW;
import com.liferay.util.StaticFieldGetter;
import com.liferay.util.StringUtil_IW;
import com.liferay.util.UnicodeFormatter_IW;
import com.liferay.util.Validator;

import java.util.Iterator;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.tiles.ComponentConstants;
import org.apache.struts.tiles.ComponentContext;
import org.apache.velocity.VelocityContext;

/**
 * <a href="VelocityVariables.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class VelocityVariables {

	public static void insertVariables(
		VelocityContext vc, ServletContext ctx, HttpServletRequest req,
		PageContext pageContext) {

		// Request

		vc.put("request", req);

		// Page context

		vc.put("pageContext", pageContext);

		// Portlet config

		PortletConfigImpl portletConfig =
			(PortletConfigImpl)req.getAttribute(WebKeys.JAVAX_PORTLET_CONFIG);

		if (portletConfig != null) {
			vc.put("portletConfig", portletConfig);
		}

		// Render request

		PortletRequest portletRequest =
			(PortletRequest)req.getAttribute(WebKeys.JAVAX_PORTLET_REQUEST);

		if (portletRequest != null) {
			if (portletRequest instanceof RenderRequest) {
				vc.put("renderRequest", portletRequest);
			}
		}

		// Render response

		PortletResponse portletResponse =
			(PortletResponse)req.getAttribute(WebKeys.JAVAX_PORTLET_RESPONSE);

		if (portletResponse != null) {
			if (portletResponse instanceof RenderResponse) {
				vc.put("renderResponse", portletResponse);
			}
		}

		// Theme display

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			Theme theme = themeDisplay.getTheme();

			vc.put("themeDisplay", themeDisplay);
			vc.put("company", themeDisplay.getCompany());
			vc.put("user", themeDisplay.getUser());
			vc.put("realUser", themeDisplay.getRealUser());
			vc.put("layout", themeDisplay.getLayout());
			vc.put("layouts", themeDisplay.getLayouts());
			vc.put("plid", themeDisplay.getPlid());
			vc.put("layoutTypePortlet", themeDisplay.getLayoutTypePortlet());
			vc.put(
				"portletGroupId", new Long(themeDisplay.getPortletGroupId()));
			vc.put("locale", themeDisplay.getLocale());
			vc.put("timeZone", themeDisplay.getTimeZone());
			vc.put("theme", theme);
			vc.put("colorScheme", themeDisplay.getColorScheme());
			vc.put("portletDisplay", themeDisplay.getPortletDisplay());

			// Full templates path

			String ctxName = GetterUtil.getString(
				theme.getServletContextName());

			vc.put(
				"fullTemplatesPath",
				ctxName + VelocityResourceListener.SERVLET_SEPARATOR +
					theme.getTemplatesPath());
		}

		// Tiles attributes

		_insertTilesVariables(vc, pageContext, "tilesTitle", "title");
		_insertTilesVariables(vc, pageContext, "tilesContent", "content");
		_insertTilesVariables(vc, pageContext, "tilesSelectable", "selectable");

		// Helper utilities

		_insertHelperUtilities(vc);

		// Insert custom vm variables

		Map vmVariables = (Map)req.getAttribute(WebKeys.VM_VARIABLES);

		if (vmVariables != null) {
			Iterator itr = vmVariables.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry entry = (Map.Entry)itr.next();

				String key = (String)entry.getKey();
				Object value = entry.getValue();

				if (Validator.isNotNull(key)) {
					vc.put(key, value);
				}
			}
		}
	}

	private static void _insertHelperUtilities(VelocityContext vc) {

		// Array util

		vc.put("arrayUtil", ArrayUtil_IW.getInstance());

		// Browser sniffer

		vc.put(
			"browserSniffer", BrowserSniffer_IW.getInstance());

		// Getter util

		vc.put("getterUtil", GetterUtil_IW.getInstance());

		// Language

		vc.put("languageUtil", LanguageUtil_IW.getInstance());
		vc.put("unicodeLanguageUtil", UnicodeLanguageUtil_IW.getInstance());

		// Portal util

		vc.put("portalUtil", PortalUtil_IW.getInstance());

		// Props util

		vc.put("propsUtil", PropsUtil_IW.getInstance());

		// Portlet URL factory

		vc.put("portletURLFactory", PortletURLFactory.getInstance());

		// Service locator

		vc.put("serviceLocator", ServiceLocator.getInstance());

		// Session clicks

		vc.put("sessionClicks", SessionClicks_IW.getInstance());

		// Static field getter

		vc.put("staticFieldGetter", StaticFieldGetter.getInstance());

		// String util

		vc.put("stringUtil", StringUtil_IW.getInstance());

		// Unicode formatter

		vc.put("unicodeFormatter", UnicodeFormatter_IW.getInstance());
	}

	private static void _insertTilesVariables(
		VelocityContext vc, PageContext pageContext, String attributeId,
		String attributeName) {

		ComponentContext componentContext =
			(ComponentContext)pageContext.getAttribute(
				ComponentConstants.COMPONENT_CONTEXT,
				PageContext.REQUEST_SCOPE);

		if (componentContext != null) {
			Object value = componentContext.getAttribute(attributeName);

			if (value != null) {
				vc.put(attributeId, value);
			}
		}
	}

}