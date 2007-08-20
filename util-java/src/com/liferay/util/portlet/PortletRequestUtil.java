/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.util.portlet;

import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.util.xml.DocUtil;
import com.liferay.util.xml.XMLFormatter;

import java.io.IOException;

import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowStateException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * <a href="PortletRequestUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 *
 */
public class PortletRequestUtil {

	public static String toXML(PortletRequest req, PortletResponse res) {
		String xml = null;

		Document doc = DocumentHelper.createDocument();

		Element request = doc.addElement("request");

		DocUtil.add(request, "container-type", "portlet");
		DocUtil.add(request, "container-namespace", req.getContextPath());
		DocUtil.add(request, "content-type", req.getResponseContentType());
		DocUtil.add(request, "server-name", req.getServerName());
		DocUtil.add(request, "server-port", req.getServerPort());
		DocUtil.add(request, "secure", req.isSecure());
		DocUtil.add(request, "auth-type", req.getAuthType());
		DocUtil.add(request, "remote-user", req.getRemoteUser());
		DocUtil.add(request, "context-path", req.getContextPath());
		DocUtil.add(request, "locale", req.getLocale());
		DocUtil.add(request, "portlet-mode", req.getPortletMode());
		DocUtil.add(request, "portlet-session-id", req.getRequestedSessionId());
		DocUtil.add(request, "scheme", req.getScheme());
		DocUtil.add(request, "window-state", req.getWindowState());

		if (req instanceof RenderRequest) {
			DocUtil.add(request, "action", Boolean.FALSE);
		}
		else if (req instanceof ActionRequest) {
			DocUtil.add(request, "action", Boolean.TRUE);
		}

		if (res instanceof RenderResponse) {
			_toXML((RenderResponse)res, request);
		}

		Element parameters = request.addElement("parameters");

		Enumeration enu = req.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = (String)enu.nextElement();

			Element parameter = parameters.addElement("parameter");

			DocUtil.add(parameter, "name", name);

			String[] values = req.getParameterValues(name);

			for (int i = 0; i < values.length; i++) {
				DocUtil.add(parameter, "value", values[i]);
			}
		}

		try {
			xml = XMLFormatter.toString(doc);
		}
		catch (IOException ioe) {
		}

		return xml;
	}

	private static void _toXML(RenderResponse res, Element request) {
		DocUtil.add(request, "portlet-namespace", res.getNamespace());

		PortletURL url = res.createRenderURL();

		DocUtil.add(request, "render-url", url);

		try {
			url.setWindowState(LiferayWindowState.EXCLUSIVE);

			DocUtil.add(request, "render-url-exclusive", url);
		}
		catch (WindowStateException wse) {
		}

		try {
			url.setWindowState(LiferayWindowState.MAXIMIZED);

			DocUtil.add(request, "render-url-maximized", url);
		}
		catch (WindowStateException wse) {
		}

		try {
			url.setWindowState(LiferayWindowState.MINIMIZED);

			DocUtil.add(request, "render-url-minimized", url);
		}
		catch (WindowStateException wse) {
		}

		try {
			url.setWindowState(LiferayWindowState.NORMAL);

			DocUtil.add(request, "render-url-normal", url);
		}
		catch (WindowStateException wse) {
		}

		try {
			url.setWindowState(LiferayWindowState.POP_UP);

			DocUtil.add(request, "render-url-pop-up", url);
		}
		catch (WindowStateException wse) {
		}
	}

}