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
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.xmlrpc.response.Fault;
import com.liferay.portal.xmlrpc.response.Response;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="XmlRpcServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class XmlRpcServlet extends HttpServlet {

	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		Enumeration<String> paramNames = servletConfig.getInitParameterNames();

		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();

			if (paramName.startsWith("methodName")) {
				String suffix = paramName.substring("methodName".length());

				String methodName = servletConfig.getInitParameter(paramName);
				String className =
					servletConfig.getInitParameter("className" + suffix);

				_methodRegistry.put(
					methodName, (Method)InstancePool.get(className));
			}
		}
	}

	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		Response rpcResponse = null;

		try {
			long companyId = PortalInstances.getCompanyId(request);

			InputStream is = request.getInputStream();

			String xml = StringUtil.read(is);

			Tuple parsedMethod = XmlRpcUtil.parseMethod(xml);

			String methodName = (String)parsedMethod.getObject(0);
			Object[] args = (Object[])parsedMethod.getObject(1);

			rpcResponse = invokeMethod(companyId, methodName, args);
		}
		catch (IOException ioe) {
			rpcResponse = new Fault(
				XmlRpcConstants.NOT_WELL_FORMED, "XML is not well formed");

			if (_log.isDebugEnabled()) {
				_log.debug(ioe, ioe);
			}
		}
		catch (XmlRpcException xre) {
			_log.error(xre, xre);
		}

		if (rpcResponse == null) {
			rpcResponse = new Fault(
				XmlRpcConstants.SYSTEM_ERROR, "Unknown error occurred");
		}

		response.setCharacterEncoding(StringPool.UTF8);
		response.setContentType(ContentTypes.TEXT_XML);
		response.setStatus(HttpServletResponse.SC_OK);

		try {
			ServletResponseUtil.write(response, rpcResponse.toXml());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}

			response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
		}
	}

	protected Response invokeMethod(
			long companyId, String methodName, Object[] args)
		throws XmlRpcException {

		Method method = _methodRegistry.get(methodName);

		if (method == null) {
			return new Fault(
				XmlRpcConstants.REQUESTED_METHOD_NOT_FOUND,
				"Requested method not found");
		}

		if (!method.setArguments(args)) {
			return new Fault(
				XmlRpcConstants.INVALID_METHOD_PARAMETERS,
				"Method arguments are invalid");
		}

		return method.execute(companyId);
	}

	private Map<String, Method> _methodRegistry = new HashMap<String, Method>();

	private static Log _log = LogFactoryUtil.getLog(XmlRpcServlet.class);

}