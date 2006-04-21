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

package com.liferay.portlet.layoutconfiguration.util.velocity;

import com.liferay.portal.model.Portlet;
import com.liferay.portlet.layoutconfiguration.util.RuntimePortletUtil;

import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PortletLogic.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Ivica Cardic
 * @author  Brian Wing Shun Chan
 *
 */
public class PortletLogic extends RuntimeLogic {

	public PortletLogic(ServletContext ctx, HttpServletRequest req,
						HttpServletResponse res, String portletId) {

		this(ctx, req, res, null, null);

		_portletId = Portlet.getRootPortletId(portletId);
		_instanceId = Portlet.getInstanceId(portletId);
	}

	public PortletLogic(ServletContext ctx, HttpServletRequest req,
						HttpServletResponse res, RenderRequest renderRequest,
						RenderResponse renderResponse) {

		_ctx = ctx;
		_req = req;
		_res = res;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public void processContent(StringBuffer sb, Map attributes)
		throws Exception {

		String portletId = (String)attributes.get("name");
		String instanceId = (String)attributes.get("instance");

		if (portletId == null) {
			portletId = _portletId;
			instanceId = _instanceId;
		}

		RuntimePortletUtil.processPortlet(
			sb, _ctx, _req, _res, _renderRequest, _renderResponse, portletId,
			instanceId);
	}

	private static Log _log = LogFactory.getLog(PortletLogic.class);

	private ServletContext _ctx;
	private HttpServletRequest _req;
	private HttpServletResponse _res;
	private RenderRequest _renderRequest;
	private RenderResponse _renderResponse;
	private String _portletId;
	private String _instanceId;

}