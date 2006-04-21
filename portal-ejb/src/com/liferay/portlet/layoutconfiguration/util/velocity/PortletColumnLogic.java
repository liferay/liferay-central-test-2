/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.layoutconfiguration.util.velocity;

import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.layoutconfiguration.util.RuntimePortletUtil;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="PortletColumnLogic.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Ivica Cardic
 * @author  Brian Wing Shun Chan
 *
 */
public class PortletColumnLogic extends RuntimeLogic {

	public PortletColumnLogic(ServletContext ctx, HttpServletRequest req,
							  HttpServletResponse res) {

		_ctx = ctx;
		_req = req;
		_res = res;
		_themeDisplay = (ThemeDisplay)_req.getAttribute(WebKeys.THEME_DISPLAY);
	}

	public void processContent(StringBuffer sb, Map attributes)
		throws Exception {

		String columnId = (String)attributes.get("id");

		sb.append("<div id=\"layout-column_");
		sb.append(columnId);
		sb.append("\">");

		LayoutTypePortlet layoutTypePortlet =
			_themeDisplay.getLayoutTypePortlet();

		List portlets = layoutTypePortlet.getAllPortlets(columnId);

		for (int i = 0; i < portlets.size(); i++) {
			Portlet portlet = (Portlet)portlets.get(i);

			String rootPortletId = portlet.getRootPortletId();
			String instanceId = portlet.getInstanceId();

			RuntimePortletUtil.processPortlet(
				sb, _ctx, _req, _res, null, null, rootPortletId, instanceId,
				columnId, new Integer(i), new Integer(portlets.size()));
		}

		sb.append("<div class=\"layout-blank-portlet\"></div>");
		sb.append("</div>");
	}

	private ServletContext _ctx;
	private HttpServletRequest _req;
	private HttpServletResponse _res;
	private ThemeDisplay _themeDisplay;

}