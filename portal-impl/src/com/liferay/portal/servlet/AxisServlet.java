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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="AxisServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AxisServlet extends org.apache.axis.transport.http.AxisServlet {

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

			super.service(request, stringResponse);

			String contentType = stringResponse.getContentType();

			response.setContentType(contentType);

			String content = stringResponse.getString();

			if (contentType.contains(ContentTypes.TEXT_XML)) {
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

	private static final String _INCORRECT_LONG_ARRAY =
		"<complexType name=\"ArrayOf_xsd_long\"><simpleContent><extension/>" +
			"</simpleContent></complexType>";

	private static final String _CORRECT_LONG_ARRAY =
		"<complexType name=\"ArrayOf_xsd_long\"><complexContent>" +
			"<restriction base=\"soapenc:Array\"><attribute ref=\"soapenc:" +
				"arrayType\" wsdl:arrayType=\"soapenc:long[]\"/>" +
					"</restriction></complexContent></complexType>";

	private static final String _INCORRECT_STRING_ARRAY =
		"<complexType name=\"ArrayOf_xsd_string\"><simpleContent><extension/>" +
			"</simpleContent></complexType>";

	private static final String _CORRECT_STRING_ARRAY =
		"<complexType name=\"ArrayOf_xsd_string\"><complexContent>" +
			"<restriction base=\"soapenc:Array\"><attribute ref=\"soapenc:" +
				"arrayType\" wsdl:arrayType=\"soapenc:string[]\"/>" +
					"</restriction></complexContent></complexType>";

	private static Log _log = LogFactoryUtil.getLog(AxisServlet.class);

}