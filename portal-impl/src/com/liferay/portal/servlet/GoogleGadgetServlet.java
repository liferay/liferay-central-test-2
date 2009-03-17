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

package com.liferay.portal.servlet;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="GoogleGadgetServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 */
public class GoogleGadgetServlet extends HttpServlet {

	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			String redirect = getRedirect(request);

			if (redirect == null) {
				PortalUtil.sendError(
					HttpServletResponse.SC_NOT_FOUND,
					new NoSuchLayoutException(), request, response);
			}
			else {
				request.setAttribute(WebKeys.GOOGLE_GADGET, Boolean.TRUE);

				String path = GetterUtil.getString(request.getPathInfo());
				String portletId = path.substring(
					path.indexOf(_SEPARATOR) + _SEPARATOR.length());

				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					PortalUtil.getCompanyId(request), portletId);

				String gagdetTitle = portlet.getDisplayName();

				String widgetJsURL =
					PortalUtil.getPortalURL(request) +
					PortalUtil.getPathContext() + "/html/js/liferay/widget.js";

				String widgetURL =
					request.getRequestURL().toString().replaceFirst(
						PropsValues.GOOGLE_GADGET_SERVLET_MAPPING,
						PropsValues.WIDGET_SERVLET_MAPPING);

				response.setContentType(ContentTypes.TEXT_XML);

				StringBuilder sb = new StringBuilder();
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
				sb.append("<Module>\n");
				sb.append("\t<ModulePrefs title=\"" + gagdetTitle + "\"/>\n");
				sb.append("\t<Content type=\"html\">\n");
				sb.append("\t<![CDATA[\n");
				sb.append("\t\t<script src=\"" + widgetJsURL + "\"");
				sb.append("\t\t\ttype=\"text/javascript\"></script>\n");
				sb.append("\t\t<script type=\"text/javascript\">\n");
				sb.append("\t\t\twindow.Liferay.Widget({url:'" + widgetURL + "'});\n");
				sb.append("\t\t</script>\n");
				sb.append("\t]]>\n");
				sb.append("\t</Content>\n");
				sb.append("</Module>\n");

				response.getOutputStream().print(sb.toString());
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			PortalUtil.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e, request,
				response);
		}
	}

	protected String getRedirect(HttpServletRequest request) {
		String path = GetterUtil.getString(request.getPathInfo());

		if (Validator.isNull(path)) {
			return null;
		}

		int pos = path.indexOf(_SEPARATOR);

		if (pos == -1) {
			return null;
		}

		return path;
	}

	private static final String _SEPARATOR = "/-/";

	private static Log _log = LogFactoryUtil.getLog(GoogleGadgetServlet.class);

}