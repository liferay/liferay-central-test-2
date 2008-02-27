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

package com.liferay.portlet;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Portlet;

import java.util.Map;

import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.ResourceRequest;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ResourceRequestImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ResourceRequestImpl
	extends ClientDataRequestImpl implements ResourceRequest {

	public String getCacheability() {
		return _cacheablity;
	}

	public String getETag() {
		return null;
	}

	public Map<String, String[]> getPrivateRenderParameterMap() {
		return null;
	}

	public String getResourceID() {
		return _resourceID;
	}

	protected ResourceRequestImpl() {
		if (_log.isDebugEnabled()) {
			_log.debug("Creating new instance " + hashCode());
		}
	}

	protected void init(
		HttpServletRequest req, Portlet portlet, InvokerPortlet invokerPortlet,
		PortletContext portletCtx, WindowState windowState,
		PortletMode portletMode, PortletPreferences prefs, long plid) {

		super.init(
			req, portlet, invokerPortlet, portletCtx, windowState, portletMode,
			prefs, plid);

		_cacheablity = ParamUtil.getString(req, "p_p_cacheability");
		_resourceID = req.getParameter("p_p_resource_id");
	}

	protected void recycle() {
		if (_log.isDebugEnabled()) {
			_log.debug("Recycling instance " + hashCode());
		}

		super.recycle();

		_cacheablity = null;
		_resourceID = null;
	}

	private static Log _log = LogFactory.getLog(ResourceRequestImpl.class);

	private String _cacheablity;
	private String _resourceID;

}