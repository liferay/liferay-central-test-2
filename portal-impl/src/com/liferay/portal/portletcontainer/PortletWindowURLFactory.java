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

import com.liferay.portal.model.Portlet;

import com.sun.portal.container.ChannelMode;
import com.sun.portal.container.ChannelState;
import com.sun.portal.container.ChannelURL;
import com.sun.portal.container.ChannelURLFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="PortletWindowURLFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Deepak Gothe
 *
 */
public class PortletWindowURLFactory implements ChannelURLFactory {

	public PortletWindowURLFactory(HttpServletRequest req,
			Portlet portletModel, ChannelMode newPortletWindowMode,
			ChannelState newWindowState, long plid) {
		_req = req;
		_portlet = portletModel;
		_plid = plid;
		_windowState = newWindowState;
		_portletMode = newPortletWindowMode;
	}

	public ChannelURL createChannelURL() {
		return new PortletWindowURL(
			_req, _portlet, _portletMode, _windowState, _plid);
	}

	public String encodeURL(
			HttpServletRequest req, HttpServletResponse res, String url) {
		return res.encodeURL(url);
	}

	public String getRenderTemplate() {
		throw new RuntimeException("Method not implemented");
	}

	public String getActionTemplate() {
		throw new RuntimeException("Method not implemented");
	}

	public String getResourceTemplate() {
		throw new RuntimeException("Method not implemented");
	}

	public String getSecurityErrorURL() {
		throw new RuntimeException("Method not implemented");
	}

	private HttpServletRequest _req;
	private Portlet _portlet;
	private long _plid;
	private ChannelState _windowState;
	private ChannelMode _portletMode;

}