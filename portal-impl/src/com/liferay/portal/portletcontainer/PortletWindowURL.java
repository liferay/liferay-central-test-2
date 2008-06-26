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
import javax.portlet.PortletSecurityException;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PortletWindowURL.java.html"><b><i>View Source</i></b></a>
 * PortletWindowURL provides the concrete implementation of ChannelURL interface
 *
 * @author Deepak Gothe
 *
 */
public class PortletWindowURL implements ChannelURL, Serializable {

	public PortletWindowURL(HttpServletRequest req, Portlet portletModel,
			ChannelMode newPortletWindowMode, ChannelState newWindowState,
			long plid) {
		_portletURLImpl = new PortletURLImpl(
				req, portletModel.getPortletId(), plid,
					PortletRequest.RENDER_PHASE);
		setChannelMode(newPortletWindowMode);
		setWindowState(newWindowState);
	}

	public void setChannelMode(ChannelMode newChannelMode) {
		try {
			_portletURLImpl.setPortletMode(
					PortletAppEngineUtils.getPortletMode(newChannelMode));
		} catch (PortletModeException ex) {
			_log.error(ex);
		}
	}

	public void setWindowState(ChannelState newWindowState) {
		try {
			_portletURLImpl.setWindowState(
					PortletAppEngineUtils.getWindowState(newWindowState));
		} catch (WindowStateException ex) {
			_log.error(ex);
		}
	}

	public void setURLType(ChannelURLType urlType) {
		_urlType = urlType;
		_portletURLImpl.setURLType(getURLType());
		_portletURLImpl.setLifecycle(getLifecycle());
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

	public void addProperty(String name, String value) {
		if (name == null) {
			return;
		}
		_portletURLImpl.addProperty(name, value);
	}

	public void setSecure(boolean secure) {
		try {
			_portletURLImpl.setSecure(secure);
		} catch (PortletSecurityException ex) {
			_log.error(ex);
		}
	}

	public void setCacheLevel(String cacheLevel) {
		_portletURLImpl.setCacheability(cacheLevel);
	}

	public void setResourceID(String resourceID) {
		_portletURLImpl.setResourceID(resourceID);
	}

	public ChannelState getWindowState() {
		return PortletAppEngineUtils.getWindowState(
				_portletURLImpl.getWindowState());
	}

	public ChannelMode getChannelMode() {
		return PortletAppEngineUtils.getPortletMode(
				_portletURLImpl.getPortletMode());
	}

	public ChannelURLType getURLType() {
		return _urlType;
	}

	public Map<String, String[]> getParameters() {
		return _portletURLImpl.getParameterMap();
	}

	public Map<String, List<String>> getProperties() {
		// TODO: Implement properties in PortletURLImpl
		return Collections.EMPTY_MAP;
	}

	public boolean isSecure() {
		return _portletURLImpl.isSecure();
	}

	public String getCacheLevel() {
		return _portletURLImpl.getCacheability();
	}

	@Override
	public String toString() {
		return _portletURLImpl.toString();
	}

	private String getLifecycle() {
		if (ChannelURLType.ACTION.equals(getURLType())) {
			return PortletRequest.ACTION_PHASE;
		} else if (ChannelURLType.RENDER.equals(getURLType())) {
			// to force portal to call container.excuteAction
			return PortletRequest.ACTION_PHASE;
		} else if (ChannelURLType.RESOURCE.equals(getURLType())) {
			return PortletRequest.RESOURCE_PHASE;
		} else {
			return PortletRequest.RENDER_PHASE;
		}
	}

	private static Log _log = LogFactory.getLog(PortletWindowURL.class);

	private ChannelURLType _urlType;
	private PortletURLImpl _portletURLImpl;

}