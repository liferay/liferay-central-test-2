/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.servlet.UncommittedServletResponse;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.util.servlet.ServletResponseUtil;
import com.liferay.util.xml.XMLFormatter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class AxisServlet extends org.apache.axis.transport.http.AxisServlet {

	public void init(ServletConfig servletConfig) throws ServletException {
		ServletContext servletContext = servletConfig.getServletContext();

		_portletClassLoader = (ClassLoader)servletContext.getAttribute(
			PortletServlet.PORTLET_CLASS_LOADER);

		if (_portletClassLoader == null) {
			super.init(servletConfig);
		}
		else {
			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			try {
				currentThread.setContextClassLoader(_portletClassLoader);

				super.init(servletConfig);
			}
			finally {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public void service(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			PortalInstances.getCompanyId(request);

			String remoteUser = request.getRemoteUser();

			if (_log.isDebugEnabled()) {
				_log.debug("Remote user " + remoteUser);
			}

			if (remoteUser != null) {
				PrincipalThreadLocal.setName(remoteUser);

				long userId = GetterUtil.getLong(remoteUser);

				User user = UserLocalServiceUtil.getUserById(userId);

				PermissionChecker permissionChecker =
					PermissionCheckerFactoryUtil.create(user, true);

				PermissionThreadLocal.setPermissionChecker(permissionChecker);
			}

			StringServletResponse stringResponse = new StringServletResponse(
				response);

			if (_portletClassLoader == null) {
				super.service(request, stringResponse);
			}
			else {
				Thread currentThread = Thread.currentThread();

				ClassLoader contextClassLoader =
					currentThread.getContextClassLoader();

				try {
					currentThread.setContextClassLoader(_portletClassLoader);

					super.service(request, stringResponse);
				}
				finally {
					currentThread.setContextClassLoader(contextClassLoader);
				}
			}

			String contentType = stringResponse.getContentType();

			response.setContentType(contentType);

			String content = stringResponse.getString();

			if (contentType.contains(ContentTypes.TEXT_HTML)) {
				content = _HTML_TOP_WRAPPER.concat(content).concat(
					_HTML_BOTTOM_WRAPPER);
			}
			else if (contentType.contains(ContentTypes.TEXT_XML)) {
				content = fixXml(content);
			}

			ServletResponseUtil.write(
				new UncommittedServletResponse(response),
				content.getBytes(StringPool.UTF8));
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected String fixXml(String xml) throws Exception {
		if (xml.indexOf("<wsdl:definitions") == -1) {
			return xml;
		}

		xml = StringUtil.replace(
			xml,
			new String[] {
				"\r\n",
				"\n",
				"  ",
				"> <",
				_INCORRECT_LONG_ARRAY,
				_INCORRECT_STRING_ARRAY
			},
			new String[] {
				StringPool.BLANK,
				StringPool.BLANK,
				StringPool.BLANK,
				"><",
				_CORRECT_LONG_ARRAY,
				_CORRECT_STRING_ARRAY
			});

		xml = XMLFormatter.toString(xml);

		return xml;
	}

	private static final String _CORRECT_LONG_ARRAY =
		"<complexType name=\"ArrayOf_xsd_long\"><complexContent>" +
			"<restriction base=\"soapenc:Array\"><attribute ref=\"soapenc:" +
				"arrayType\" wsdl:arrayType=\"soapenc:long[]\"/>" +
					"</restriction></complexContent></complexType>";

	private static final String _CORRECT_STRING_ARRAY =
		"<complexType name=\"ArrayOf_xsd_string\"><complexContent>" +
			"<restriction base=\"soapenc:Array\"><attribute ref=\"soapenc:" +
				"arrayType\" wsdl:arrayType=\"soapenc:string[]\"/>" +
					"</restriction></complexContent></complexType>";

	private static final String _HTML_BOTTOM_WRAPPER = "</body></html>";

	private static final String _HTML_TOP_WRAPPER = "<html><body>";

	private static final String _INCORRECT_LONG_ARRAY =
		"<complexType name=\"ArrayOf_xsd_long\"><simpleContent><extension/>" +
			"</simpleContent></complexType>";

	private static final String _INCORRECT_STRING_ARRAY =
		"<complexType name=\"ArrayOf_xsd_string\"><simpleContent><extension/>" +
			"</simpleContent></complexType>";

	private static Log _log = LogFactoryUtil.getLog(AxisServlet.class);

	private ClassLoader _portletClassLoader;

}