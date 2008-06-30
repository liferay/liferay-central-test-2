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
import com.liferay.portlet.PortletURLImpl;

import com.sun.portal.container.ChannelMode;
import com.sun.portal.container.ChannelState;
import com.sun.portal.container.ChannelURL;
import com.sun.portal.container.ChannelURLType;
import com.sun.portal.portletcontainer.appengine.PortletAppEngineUtils;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PortletWindowURL.java.html"><b><i>View Source</i></b></a>
 *
 * @author Deepak Gothe
 * @author Brian Wing Shun Chan
 *
 */
public class PortletWindowURL implements ChannelURL, Serializable {

	public PortletWindowURL(
		HttpServletRequest request, Portlet portlet, ChannelState windowState,
		ChannelMode portletMode, long plid) {

		_portletURLImpl = new PortletURLImpl(
			request, portlet.getPortletId(), plid, PortletRequest.RENDER_PHASE);

		setWindowState(windowState);
		setChannelMode(portletMode);
	}

	public void addProperty(String name, String value) {
		if (name == null) {
			return;
		}

		_portletURLImpl.addProperty(name, value);
	}

	public String getCacheLevel() {
		return _portletURLImpl.getCacheability();
	}

	public ChannelMode getChannelMode() {
		return PortletAppEngineUtils.getPortletMode(
			_portletURLImpl.getPortletMode());
	}

	public Map<String, String[]> getParameters() {
		return _portletURLImpl.getParameterMap();
	}

	public Map<String, List<String>> getProperties() {
		return Collections.EMPTY_MAP;
	}

	public ChannelURLType getURLType() {
		return _urlType;
	}

	public ChannelState getWindowState() {
		return PortletAppEngineUtils.getWindowState(
			_portletURLImpl.getWindowState());
	}

	public boolean isSecure() {
		return _portletURLImpl.isSecure();
	}

	public void setCacheLevel(String cacheLevel) {
		_portletURLImpl.setCacheability(cacheLevel);
	}

	public void setChannelMode(ChannelMode portletMode) {
		try {
			_portletURLImpl.setPortletMode(
				PortletAppEngineUtils.getPortletMode(portletMode));
		}
		catch (PortletModeException pme) {
			_log.error(pme);
		}
	}

	public void setParameter(String name, String value) {
		_portletURLImpl.setParameter(name, value);
	}

	public void setParameter(String name, String[] values) {
		_portletURLImpl.setParameter(name, values);
	}

	public void setParameters(Map<String, String[]> parametersMap) {
		_portletURLImpl.setParameters(parametersMap);
	}

	public void setProperty(String name, String value) {
		if (name == null) {
			return;
		}

		_portletURLImpl.setProperty(name, value);
	}

	public void setResourceID(String resourceID) {
		_portletURLImpl.setResourceID(resourceID);
	}

	public void setSecure(boolean secure) {
		_portletURLImpl.setSecure(secure);
	}

	public void setURLType(ChannelURLType urlType) {
		_urlType = urlType;

		_portletURLImpl.setLifecycle(getLifecycle());
		_portletURLImpl.setURLType(getURLType());
	}

	public void setWindowState(ChannelState windowState) {
		try {
			_portletURLImpl.setWindowState(
				PortletAppEngineUtils.getWindowState(windowState));
		}
		catch (WindowStateException wse) {
			_log.error(wse);
		}
	}
	public String toString() {
		return _portletURLImpl.toString();
	}

	protected String getLifecycle() {
		if (ChannelURLType.ACTION.equals(getURLType())) {
			return PortletRequest.ACTION_PHASE;
		}
		else if (ChannelURLType.RENDER.equals(getURLType())) {

			// Force the portal to call executeAction

			return PortletRequest.ACTION_PHASE;
		}
		else if (ChannelURLType.RESOURCE.equals(getURLType())) {
			return PortletRequest.RESOURCE_PHASE;
		}
		else {
			return PortletRequest.RENDER_PHASE;
		}
	}

	private static Log _log = LogFactory.getLog(PortletWindowURL.class);

	private PortletURLImpl _portletURLImpl;
	private ChannelURLType _urlType;

}