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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

/**
 * @author Ming-Gih Lam
 */
public abstract class JSONAction extends Action {

	public void setServlet(ActionServlet servlet) {
		super.setServlet(servlet);

		_servletContext = servlet.getServletContext();
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		String servletContextName = ParamUtil.getString(
			request, "servletContextName");

		String currentServletContextName =
			getServletContext().getServletContextName();

		if (Validator.isNotNull(servletContextName) &&
			Validator.isNull(currentServletContextName) ||
			(!currentServletContextName.equals(servletContextName))) {

			BeanLocator beanLocator = PortletBeanLocatorUtil.getBeanLocator(
				servletContextName);

			ServletContext servletContext = beanLocator.getServletContext();

			if (servletContext != null) {
				RequestDispatcher requestDispatcher =
					servletContext.getRequestDispatcher("/json");

				if (requestDispatcher != null) {
					requestDispatcher.forward(request, response);

					return null;
				}
			}
		}

		String callback = ParamUtil.getString(request, "callback");
		String instance = ParamUtil.getString(request, "inst");

		String json = null;

		try {
			json = getJSON(mapping, form, request, response);

			if (Validator.isNotNull(callback)) {
				json = callback + "(" + json + ");";
			}
			else if (Validator.isNotNull(instance)) {
				json = "var " + instance + "=" + json + ";";
			}
		}
		catch (Exception e) {
			PortalUtil.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e, request,
				response);

			return null;
		}

		if (Validator.isNotNull(json)) {
			response.setContentType(ContentTypes.TEXT_JAVASCRIPT);
			response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");

			PrintWriter printWriter = response.getWriter();

			printWriter.write(json);

			printWriter.close();
		}

		return null;
	}

	public abstract String getJSON(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception;

	protected ServletContext _servletContext;

}