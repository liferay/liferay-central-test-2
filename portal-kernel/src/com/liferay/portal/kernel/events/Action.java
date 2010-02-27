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

package com.liferay.portal.kernel.events;

import com.liferay.portal.kernel.util.PortalClassInvoker;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="Action.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class Action {

	public abstract void run(
			HttpServletRequest request, HttpServletResponse response)
		throws ActionException;

	public void run(RenderRequest renderRequest, RenderResponse renderResponse)
		throws ActionException {

		try {
			HttpServletRequest request = _getHttpServletRequest(renderRequest);
			HttpServletResponse response = _getHttpServletResponse(
				renderResponse);

			run(request, response);
		}
		catch (ActionException ae) {
			throw ae;
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	private HttpServletRequest _getHttpServletRequest(
			PortletRequest portletRequest)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			_CLASS, _METHOD_GETHTTPSERVLETREQUEST, portletRequest, false);

		if (returnObj != null) {
			return (HttpServletRequest)returnObj;
		}
		else {
			return null;
		}
	}

	private HttpServletResponse _getHttpServletResponse(
			PortletResponse portletResponse)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			_CLASS, _METHOD_GETHTTPSERVLETRESPONSE, portletResponse, false);

		if (returnObj != null) {
			return (HttpServletResponse)returnObj;
		}
		else {
			return null;
		}
	}

	private static final String _CLASS = "com.liferay.portal.util.PortalUtil";

	private static final String _METHOD_GETHTTPSERVLETREQUEST =
		"getHttpServletRequest";

	private static final String _METHOD_GETHTTPSERVLETRESPONSE =
		"getHttpServletResponse";

}