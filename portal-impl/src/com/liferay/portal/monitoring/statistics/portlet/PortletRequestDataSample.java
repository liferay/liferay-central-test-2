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

package com.liferay.portal.monitoring.statistics.portlet;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.monitoring.MonitorNames;
import com.liferay.portal.monitoring.statistics.BaseDataSample;
import com.liferay.portlet.PortletResponseImpl;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * <a href="PortletRequestDataSample.java.html"><b><i>View Source</i></b></a>
 *
 * @author Karthik Sudarshan
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class PortletRequestDataSample extends BaseDataSample {

	public PortletRequestDataSample(
		PortletRequestType requestType, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		PortletResponseImpl portletResponseImpl =
			(PortletResponseImpl)portletResponse;

		Portlet portlet = portletResponseImpl.getPortlet();

		setCompanyId(portlet.getCompanyId());
		setUser(portletRequest.getRemoteUser());
		setNamespace(MonitorNames.PORTLET);
		setName(portlet.getPortletName());
		_portletId = portlet.getPortletId();
		_displayName = portlet.getDisplayName();
		_requestType = requestType;
	}

	public String getDisplayName() {
		return _displayName;
	}

	public String getPortletId() {
		return _portletId;
	}

	public PortletRequestType getRequestType() {
		return _requestType;
	}

	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{displayName=");
		sb.append(_displayName);
		sb.append(", portletId=");
		sb.append(_portletId);
		sb.append(", requestType=");
		sb.append(_requestType);
		sb.append(", ");
		sb.append(super.toString());
		sb.append("}");

		return sb.toString();
	}

	private String _displayName;
	private String _portletId;
	private PortletRequestType _requestType;

}