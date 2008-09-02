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

package com.liferay.portal.sharepoint;

import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.util.servlet.ServletResponseUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="SharepointDocumentWorkspaceServlet.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Bruno Farache
 *
 */
public class SharepointDocumentWorkspaceServlet extends HttpServlet {

	protected void doPost(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			getDwsMetaDataResponse(request, response);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void getDwsMetaDataResponse(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append("http://schemas.xmlsoap.org/soap/envelope/\">");
		sb.append("<SOAP-ENV:Header/>");
		sb.append("<SOAP-ENV:Body>");
		sb.append("<GetDwsMetaDataResponse xmlns=\"");
		sb.append("http://schemas.microsoft.com/sharepoint/soap/dws/\">");
		sb.append("<GetDwsMetaDataResult>");
		sb.append("</GetDwsMetaDataResult>");
		sb.append("</GetDwsMetaDataResponse>");
		sb.append("</SOAP-ENV:Body>");
		sb.append("</SOAP-ENV:Envelope>");

		response.setContentType(ContentTypes.TEXT_XML_UTF8);

		ServletResponseUtil.write(response, sb.toString());
	}

	private static Log _log = LogFactory.getLog(SharepointServlet.class);

}