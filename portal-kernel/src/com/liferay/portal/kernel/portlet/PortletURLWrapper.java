/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.portlet;

import java.io.IOException;
import java.io.Writer;

import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

/**
 * <a href="PortletURLWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletURLWrapper implements PortletURL {

	public PortletURLWrapper(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void addProperty(String key, String value) {
		_portletURL.addProperty(key, value);
	}

	public Map<String, String[]> getParameterMap() {
		return _portletURL.getParameterMap();
	}

	public PortletMode getPortletMode() {
		return _portletURL.getPortletMode();
	}

	public WindowState getWindowState() {
		return _portletURL.getWindowState();
	}

	public void removePublicRenderParameter(String name) {
		_portletURL.removePublicRenderParameter(name);
	}

	public void setParameter(String name, String value) {
		_portletURL.setParameter(name, value);
	}

	public void setParameter(String name, String[] values) {
		_portletURL.setParameter(name, values);
	}

	public void setParameters(Map<String, String[]> parameters) {
		_portletURL.setParameters(parameters);
	}

	public void setPortletMode(PortletMode portletMode)
		throws PortletModeException{

		_portletURL.setPortletMode(portletMode);
	}

	public void setProperty(String key, String value) {
		_portletURL.setProperty(key, value);
	}

	public void setSecure(boolean secure) throws PortletSecurityException {
		_portletURL.setSecure(secure);
	}

	public void setWindowState(WindowState windowState)
		throws WindowStateException {

		_portletURL.setWindowState(windowState);
	}

	public String toString() {
		return _portletURL.toString();
	}

	public void write(Writer writer) throws IOException {
		_portletURL.write(writer);
	}

	public void write(Writer writer, boolean escapeXML) throws IOException {
		_portletURL.write(writer, escapeXML);
	}

	private PortletURL _portletURL;

}