/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.rest;

import com.liferay.portal.kernel.rest.RESTActionsManagerUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.servlet.JSONServlet;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Igor Spasic
 */
public class RESTServlet extends JSONServlet {

	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		if (PortalUtil.isMultipartRequest(request)) {
			UploadServletRequest uploadServletRequest =
				new UploadServletRequestImpl(request);

			request = uploadServletRequest;
		}

		String path = GetterUtil.getString(request.getPathInfo());

		if (path.equals("/dump")) {
			dumpMappings(response);
		}
		else {
			super.service(request, response);
		}
	}

	protected void dumpMappings(HttpServletResponse response)
		throws IOException {

		List<String[]> mappings = RESTActionsManagerUtil.dumpMappings();

		StringBundler sb = new StringBundler(mappings.size() * 6 + 2);

		sb.append("<html><body><table border=\"1\">");

		for (String[] mapping : mappings) {
			sb.append("<tr><td>");

			if (mapping[0] == null) {
				sb.append(StringPool.STAR);
			}
			else {
				sb.append(mapping[0]);
			}

			sb.append("</td><td>");
			sb.append(mapping[1]);
			sb.append("</td><td>");

			String classMethodName = mapping[2];

			classMethodName = StringUtil.replace(
				classMethodName, "com.liferay.portal.", "c.l.p.");
			classMethodName = StringUtil.replace(
				classMethodName, "com.liferay.", "c.l.");

			sb.append(classMethodName);
			sb.append("</td></tr>");
		}

		sb.append("</table></body></html>");

		response.setContentType(ContentTypes.TEXT_HTML);
		response.setHeader(
			HttpHeaders.CACHE_CONTROL,
			HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);

		PrintWriter printWriter = response.getWriter();

		printWriter.write(sb.toString());

		printWriter.close();
	}

	protected JSONAction getJSONAction(ServletContext servletContext) {
		ClassLoader portletClassLoader =
			(ClassLoader)servletContext.getAttribute(
				PortletServlet.PORTLET_CLASS_LOADER);

		JSONAction jsonAction = new RESTServiceAction(portletClassLoader);

		jsonAction.setServletContext(servletContext);

		return jsonAction;
	}

}