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

package com.liferay.portlet;

import java.util.Enumeration;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * <a href="PortletURLUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PortletURLUtil {

	public static PortletURL getCurrent(RenderRequest req, RenderResponse res) {
		PortletURL portletURL = res.createRenderURL();

		Enumeration enu = req.getParameterNames();

		while (enu.hasMoreElements()) {
			String param = (String)enu.nextElement();
			String[] values = req.getParameterValues(param);

			portletURL.setParameter(param, values);
		}

		return portletURL;
	}

	public static PortletURL clone(
			PortletURL portletURL, boolean action, RenderResponse res)
		throws PortletException {

		PortletURLImpl portletURLImpl = (PortletURLImpl)portletURL;

		PortletURL newURL = null;

		if (action) {
			newURL = res.createActionURL();
		}
		else {
			newURL = res.createRenderURL();
		}

		newURL.setWindowState(portletURLImpl.getWindowState());
		newURL.setPortletMode(portletURLImpl.getPortletMode());

		newURL.setParameters(portletURLImpl.getParameterMap());

		return newURL;
	}

}