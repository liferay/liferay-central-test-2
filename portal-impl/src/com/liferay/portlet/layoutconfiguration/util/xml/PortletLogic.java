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

package com.liferay.portlet.layoutconfiguration.util.xml;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portlet.layoutconfiguration.util.RuntimePortletUtil;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="PortletLogic.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletLogic extends RuntimeLogic {

	public static final String OPEN_TAG = "<runtime-portlet";

	public static final String CLOSE_1_TAG = "</runtime-portlet>";

	public static final String CLOSE_2_TAG = "/>";

	public PortletLogic(
		ServletContext servletContext, HttpServletRequest request,
		HttpServletResponse response, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_servletContext = servletContext;
		_request = request;
		_response = response;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public String getOpenTag() {
		return OPEN_TAG;
	}

	public String getClose1Tag() {
		return CLOSE_1_TAG;
	}

	public void processXML(StringBuilder sb, String xml) throws Exception {
		Document doc = SAXReaderUtil.read(xml);

		Element root = doc.getRootElement();

		String rootPortletId = root.attributeValue("name");
		String instanceId = root.attributeValue("instance");
		String queryString = root.attributeValue("queryString");

		String portletId = rootPortletId;

		if (Validator.isNotNull(instanceId)) {
			portletId += PortletConstants.INSTANCE_SEPARATOR + instanceId;
		}

		RuntimePortletUtil.processPortlet(
			sb, _servletContext, _request, _response, _renderRequest,
			_renderResponse, portletId, queryString);
	}

	private ServletContext _servletContext;
	private HttpServletRequest _request;
	private HttpServletResponse _response;
	private RenderRequest _renderRequest;
	private RenderResponse _renderResponse;

}