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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Portlet;
import com.liferay.portlet.PortletResponseImpl;
import com.liferay.portlet.PortletURLImplWrapper;

import java.util.Map;

/**
 * <a href="StrutsActionPortletURL.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class StrutsActionPortletURL extends PortletURLImplWrapper {

	public StrutsActionPortletURL(
		PortletResponseImpl portletResponseImpl, long plid, String lifecycle) {

		super(portletResponseImpl, plid, lifecycle);

		_portlet = portletResponseImpl.getPortlet();
		_strutsPath =
			StringPool.SLASH + _portlet.getStrutsPath() + StringPool.SLASH;
	}

	public void setParameter(String name, String value) {
		if (name.equals("struts_action")) {
			if (!value.startsWith(_strutsPath)) {
				int pos = value.lastIndexOf(StringPool.SLASH);

				value = _strutsPath + value.substring(pos + 1, value.length());
			}
		}

		super.setParameter(name, value);
	}

	public void setParameters(Map<String, String[]> params) {
		for (Map.Entry<String, String[]> entry : params.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();

			if (name.equals("struts_action")) {
				for (int i = 0; i < values.length; i++) {
					String value = values[i];

					if (!value.startsWith(_strutsPath)) {
						int pos = value.lastIndexOf(StringPool.SLASH);

						value =
							_strutsPath +
								value.substring(pos + 1, value.length());

						values[i] = value;
					}
				}
			}
		}

		super.setParameters(params);
	}

	private Portlet _portlet;
	private String _strutsPath;

}