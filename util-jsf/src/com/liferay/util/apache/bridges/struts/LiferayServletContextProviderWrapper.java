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
package com.liferay.util.apache.bridges.struts;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.portals.bridges.common.ServletContextProvider;

/**
 * <a href="LiferayServletContextProviderWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 *
 */
public class LiferayServletContextProviderWrapper implements
		ServletContextProvider {

	public static final String STRUTS_BRIDGES_CONTEXT_PROVIDER = 
		"STRUTS_BRIDGES_CONTEXT_PROVIDER";

	public ServletContext getServletContext(GenericPortlet portlet) {
		com.liferay.portal.shared.servlet.ServletContextProvider provider = 
			_getProvider(portlet); 
		
		ServletContext ctx = provider.getServletContext(portlet);

		return ctx;
	}

	public HttpServletRequest getHttpServletRequest(GenericPortlet portlet,
			PortletRequest req) {

		com.liferay.portal.shared.servlet.ServletContextProvider provider = 
			_getProvider(portlet); 
		
		HttpServletRequest httpReq = provider.getHttpServletRequest(portlet,
				req);

		return httpReq;
	}

	public HttpServletResponse getHttpServletResponse(GenericPortlet portlet,
			PortletResponse res) {

		com.liferay.portal.shared.servlet.ServletContextProvider provider = 
			_getProvider(portlet); 
		
		HttpServletResponse httpRes = provider.getHttpServletResponse(portlet,
			res);

		return httpRes;
	}

	private com.liferay.portal.shared.servlet.ServletContextProvider 
		_getProvider(GenericPortlet portlet) {
		
		PortletContext portletCtx = portlet.getPortletContext();
		
		if (_provider == null) {
			_provider = (com.liferay.portal.shared.servlet.ServletContextProvider)
				portletCtx.getAttribute(STRUTS_BRIDGES_CONTEXT_PROVIDER);
		}
		
		return _provider; 
	}
	
	private static Log _log = LogFactory.getLog(
		LiferayServletContextProviderWrapper.class);
	
	private com.liferay.portal.shared.servlet.ServletContextProvider _provider;
}
