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
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xmlrpc.Fault;
import com.liferay.portal.kernel.xmlrpc.Response;
import com.liferay.portal.kernel.xmlrpc.Success;
import com.liferay.portal.kernel.xmlrpc.XmlRpc;
import com.liferay.portal.kernel.xmlrpc.XmlRpcException;

/**
 * <a href="XmlRpcImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class XmlRpcImpl implements XmlRpc {

	public Fault createFault(int code, String description) {
		return new FaultImpl(code, description);
	}

	public Success createSuccess(String description) {
		return new SuccessImpl(description);
	}

	public Response executeMethod(
			String url, String methodName, Object[] arguments)
		throws XmlRpcException {

		try {
			return doExecuteMethod(url, methodName, arguments);
		}
		catch (Exception e) {
			throw new XmlRpcException(e);
		}
	}

	protected Response doExecuteMethod(
			String url, String methodName, Object[] arguments)
		throws Exception {

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler();

			sb.append("XML-RPC invoking " + methodName + " ");

			if (arguments != null) {
				for (int i = 0; i < arguments.length; i++) {
					sb.append(arguments[i]);

					if (i < arguments.length - 1) {
						sb.append(", ");
					}
				}
			}

			_log.debug(sb.toString());
		}

		String requestXML = XmlRpcParser.buildMethod(methodName, arguments);

		Http.Options options = new Http.Options();

		options.addHeader(HttpHeaders.USER_AGENT, ReleaseInfo.getServerInfo());
		options.setBody(requestXML, ContentTypes.TEXT_XML, StringPool.UTF8);
		options.setLocation(url);
		options.setPost(true);

		String responseXML = HttpUtil.URLtoString(options);

		return XmlRpcParser.parseResponse(responseXML);
	}

	private static Log _log = LogFactoryUtil.getLog(XmlRpcImpl.class);

}