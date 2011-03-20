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
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.servlet.JSONServlet;
import com.liferay.portal.struts.JSONAction;

import java.io.PrintWriter;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Igor Spasic
 */
public class RESTServlet extends JSONServlet {

	@Override
	public void service(
		HttpServletRequest request, HttpServletResponse response) {

		String path = GetterUtil.getString(request.getPathInfo());

		if (path.equals("/--dump")) {
			dumpMappings(response);
		}
		else {
			super.service(request, response);
		}
	}

	protected void dumpMappings(HttpServletResponse response) {

		StringBuilder out = new StringBuilder();

		List<String[]> mappings = RESTActionsManagerUtil.dumpMappings();

		for (String[] mapping : mappings) {

			out.append(mapping[0] == null ? StringPool.STAR : mapping[0]);
			out.append('\t');
			out.append(mapping[1]);
			out.append(" ---> ");
			out.append(mapping[2]);
			out.append('\n');
		}

		response.setContentType(ContentTypes.TEXT_PLAIN);
		response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");

		try {
			PrintWriter printWriter = response.getWriter();

			printWriter.write(out.toString());

			printWriter.close();
		}
		catch (Exception e) {
		}

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