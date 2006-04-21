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

package com.liferay.portal.apache.bridges.common;

import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.PortletContextImpl;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.RenderResponseImpl;
import com.liferay.util.CollectionFactory;
import com.liferay.util.StringUtil;
import com.liferay.util.servlet.ParamFilteringServletRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.portals.bridges.common.ServletContextProvider;

/**
 * <a href="LiferayServletContextProvider.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author  James Schopp
 *
 */
public class LiferayServletContextProvider implements ServletContextProvider {

	public ServletContext getServletContext(GenericPortlet portlet) {
		PortletContextImpl portletCtxImpl =
			(PortletContextImpl)portlet.getPortletContext();

		return portletCtxImpl.getServletContext();
	}

	public HttpServletRequest getHttpServletRequest(
		GenericPortlet portlet, PortletRequest req) {

		HttpServletRequest httpReq = null;

		if (req instanceof RenderRequestImpl) {
			httpReq = ((RenderRequestImpl)req).getHttpServletRequest();
		}
		else {
			httpReq = ((ActionRequestImpl)req).getHttpServletRequest();
		}

		Collection filteredParams = getFilteredParams(portlet);

		if (filteredParams.size() == 0) {
			return httpReq;
		}
		else {
			return new ParamFilteringServletRequest(httpReq, filteredParams);
		}
	}

	public HttpServletResponse getHttpServletResponse(
		GenericPortlet portlet, PortletResponse res) {

		if (res instanceof RenderResponseImpl) {
			return ((RenderResponseImpl)res).getHttpServletResponse();
		}
		else {
			return ((ActionResponseImpl)res).getHttpServletResponse();
		}
	}

	protected Collection getFilteredParams(GenericPortlet portlet) {
		Collection filteredParams =
			(Collection)_filteredParamsPool.get(portlet.getPortletName());

		if (filteredParams == null) {
			String params = portlet.getInitParameter("filtered_params");

			filteredParams = new ArrayList();

			String[] paramsArray = StringUtil.split(params);

			for (int i = 0; i < paramsArray.length; i++) {
				filteredParams.add(paramsArray[i]);
			}

			_filteredParamsPool.put(portlet.getPortletName(), filteredParams);
		}

		return filteredParams;
	}

	private Map _filteredParamsPool = CollectionFactory.getSyncHashMap();

}