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

package com.liferay.portal.xmlrpc;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.xmlrpc.response.Response;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

/**
 * <a href="XmlRpcClient.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class XmlRpcClient {

	public XmlRpcClient(String url) {
		_url = url;
	}

	public Response executeMethod(String methodName, Object [] args)
		throws XmlRpcException {

		String xml = invoke(methodName, args);

		return XmlRpcUtil.parseResponse(xml);
	}

	protected String invoke(String methodName, Object[] args)
		throws XmlRpcException {

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler();

			sb.append("XML-RPC invoking " + methodName + " ");

			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					sb.append(args[i]);

					if (i < args.length - 1) {
						sb.append(", ");
					}
				}
			}

			_log.debug(sb.toString());
		}

		String methodXml = XmlRpcUtil.buildMethod(methodName, args);

		try {
			HttpClient client = new HttpClient();

			PostMethod method = new PostMethod(_url);

			RequestEntity requestEntity = new StringRequestEntity(
				methodXml, ContentTypes.TEXT_XML, StringPool.UTF8);

			method.addRequestHeader(
				HttpHeaders.USER_AGENT, ReleaseInfo.getServerInfo());
			method.setRequestEntity(requestEntity);

			client.executeMethod(method);

			String xml = method.getResponseBodyAsString();

			if (_log.isDebugEnabled()) {
				_log.debug("XML-RPC result " + xml);
			}

			return xml;
		}
		catch (Exception e) {
			throw new XmlRpcException(e);
		}
	}

	private String _url;

	private static Log _log = LogFactoryUtil.getLog(XmlRpcClient.class);

}