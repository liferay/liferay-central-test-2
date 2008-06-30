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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.portletcontainer;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.QNameUtil;

import com.sun.portal.container.ChannelMode;
import com.sun.portal.container.ChannelState;
import com.sun.portal.container.ChannelURLType;
import com.sun.portal.container.WindowRequestReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="PortletWindowRequestReader.java.html"><b><i>View Source</i></b></a>
 *
 * @author Deepak Gothe
 * @author Brian Wing Shun Chan
 *
 */
public class PortletWindowRequestReader implements WindowRequestReader {

	public PortletWindowRequestReader(boolean facesPortlet) {
		_facesPortlet = facesPortlet;
	}

	public String getCacheLevel(HttpServletRequest request) {
		return request.getParameter("p_p_cacheability");
	}

	public String getResourceID(HttpServletRequest request) {
		return request.getParameter("p_p_resource_id");
	}

	public ChannelMode readNewPortletWindowMode(HttpServletRequest request) {
		String portletMode = request.getParameter("p_p_mode");

		if (portletMode != null) {
			return new ChannelMode(portletMode);
		}
		else {
			return null;
		}
	}

	public ChannelState readNewWindowState(HttpServletRequest request) {
		String windowState = request.getParameter("p_p_state");

		if (windowState != null) {
			return new ChannelState(windowState);
		}
		else {
			return null;
		}
	}

	public Map<String, String[]> readParameterMap(HttpServletRequest request) {
		Map<String, String[]> parameterMap = new HashMap<String, String[]>();

		String portletId = request.getParameter("p_p_id");

		String namespace = PortalUtil.getPortletNamespace(portletId);

		Set<Map.Entry<String, String[]>> entries =
			request.getParameterMap().entrySet();

		for (Map.Entry<String, String[]> mapEntry : entries) {
			String key = mapEntry.getKey();

			if (PortalUtil.isReservedParameter(key)) {
				continue;
			}

			if (key.startsWith(namespace) && !_facesPortlet) {
				parameterMap.put(
					key.substring(namespace.length()), mapEntry.getValue());

			}
			else if (key.startsWith(
						QNameUtil.PUBLIC_RENDER_PARAMETER_NAMESPACE)) {

				String identifier =
					QNameUtil.getPublicRenderParameterIdentifier(key);

				parameterMap.put(identifier, mapEntry.getValue());
			}
			else {
				parameterMap.put(key, mapEntry.getValue());
			}
		}

		return parameterMap;
	}

	public ChannelURLType readURLType(HttpServletRequest request) {
		String urlType = ParamUtil.getString(request, "p_p_url_type");

		if (urlType.equals("0")) {
			return ChannelURLType.RENDER;
		}
		else if (urlType.equals("1")) {
			return ChannelURLType.ACTION;
		}
		else if (urlType.equals("2")) {
			return ChannelURLType.RESOURCE;
		}
		else {
			return ChannelURLType.RENDER;
		}
	}

	private boolean _facesPortlet;

}