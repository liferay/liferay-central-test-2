/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <a href="NetvibesServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 * @author Julio Camarero
 */
public class NetvibesServlet extends HttpServlet {

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
				request.setAttribute(WebKeys.NETVIBES, Boolean.TRUE);

				String path = GetterUtil.getString(request.getPathInfo());
				String portletId = path.substring(
					path.indexOf("/-/") + "/-/".length());
				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					PortalUtil.getCompanyId(request), portletId);

				String widgetTitle = portlet.getDisplayName();

				String iconURL = PortalUtil.getPortalURL(request) +
					PortalUtil.getPathContext() + portlet.getIcon();
				System.out.println("icono: "+iconURL) ;

				String widgetJSURL =
					PortalUtil.getPortalURL(request) +
					PortalUtil.getPathContext() + "/html/js/liferay/widget.js";

				String widgetURL =
					request.getRequestURL().toString().replaceFirst(
						PropsValues.NETVIBES_SERVLET_MAPPING,
						PropsValues.WIDGET_SERVLET_MAPPING);

				response.setContentType(ContentTypes.TEXT_XML);

				StringBuilder sb = new StringBuilder();
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
				sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n");
				sb.append("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");
				sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\"\n");
				sb.append("xmlns:widget=\"http://www.netvibes.com/ns/\">\n");

				sb.append("<head>\n");
				sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"http://www.netvibes.com/themes/uwa/style.css\" />\n");
				sb.append("<script type=\"text/javascript\" src=\"http://www.netvibes.com/js/UWA/load.js.php?env=Standalone\"></script>\n");
				sb.append("<title>" + widgetTitle + "</title>\n");
				sb.append("<link rel=\"icon\" type=\"image/png\" href=\"" + iconURL + "\" />\n");
				sb.append("</head>\n");
				sb.append("<body>\n");
				sb.append("  <script src=\"" + widgetJSURL + "\"");
				sb.append("    type=\"text/javascript\"></script>\n");
				sb.append("  <script type=\"text/javascript\">\n");
				sb.append("    Liferay.Widget({url:\"" + widgetURL + "\"});\n");
				sb.append("  </script>\n");
				sb.append("</body>\n");
				sb.append("</html>\n");

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

		String ppid = ParamUtil.getString(request, "p_p_id");

		int pos = path.indexOf("/-/");

		if (Validator.isNull(ppid) && (pos == -1)) {
			return null;
		}

		return path;
	}

	private static Log _log = LogFactory.getLog(NetvibesServlet.class);

}