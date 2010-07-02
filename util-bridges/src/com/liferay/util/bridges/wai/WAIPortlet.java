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

package com.liferay.util.bridges.wai;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortlet;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * <a href="WAIPortlet.java.html"><b><i>View Source</i></b></a>
 * 
 * @author Jorge Ferrer
 * @author Connor McKay
 */
public class WAIPortlet extends LiferayPortlet {

	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		Map<String, String[]> params = new HashMap<String, String[]>(
			renderRequest.getParameterMap());

		String appURL = ParamUtil.getString(
			renderRequest, _APP_URL, renderRequest.getContextPath());

		params.remove(_APP_URL);

		appURL = appURL.concat(HttpUtil.parameterMapToString(params));

		renderRequest.setAttribute(_APP_URL, appURL);

		PortletContext portletContext = getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(_JSP_IFRAME);

		try {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}
	}

	private static final String _APP_URL = "appURL";

	private static final String _JSP_DIR = "/WEB-INF/jsp/liferay/wai";

	private static final String _JSP_IFRAME = _JSP_DIR + "/iframe.jsp";

	private static Log _log = LogFactoryUtil.getLog(WAIPortlet.class);

}