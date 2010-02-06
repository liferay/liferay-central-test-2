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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PublicRenderParameter;

/**
 * <a href="PublicRenderParameterImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PublicRenderParameterImpl implements PublicRenderParameter {

	public PublicRenderParameterImpl(
		String identifier, QName qName, PortletApp portletApp) {

		_identifier = identifier;
		_qName = qName;
		_portletApp = portletApp;
	}

	public String getIdentifier() {
		return _identifier;
	}

	public void setIdentifier(String identifier) {
		_identifier = identifier;
	}

	public QName getQName() {
		return _qName;
	}

	public void setQName(QName qName) {
		_qName = qName;
	}

	public PortletApp getPortletApp() {
		return _portletApp;
	}

	public void setPortletApp(PortletApp portletApp) {
		_portletApp = portletApp;
	}

	private String _identifier;
	private QName _qName;
	private PortletApp _portletApp;

}