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

package com.liferay.support.tomcat.longpoll.comet;

import com.liferay.portal.kernel.longpoll.comet.impl.BaseCometRequestImpl;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author Edward Han
 */
public class CatalinaCometRequest extends BaseCometRequestImpl {

    public CatalinaCometRequest(
		HttpServletRequest httpServletRequest, long timestamp) {

		super(
			PortalUtil.getCompanyId(httpServletRequest),
			httpServletRequest.getPathInfo(), timestamp,
			PortalUtil.getUserId(httpServletRequest));

		_httpServletRequest = httpServletRequest;
    }

    public String getParameter(String name) {
        return _httpServletRequest.getParameter(name);
    }

	public Map getParameterMap() {
		return _httpServletRequest.getParameterMap();
	}

	public Enumeration getParameterNames() {
		return _httpServletRequest.getParameterNames();
	}

	private HttpServletRequest _httpServletRequest;
}