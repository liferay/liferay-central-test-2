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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.servlet.UncommittedServletResponse;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionCheckerFactory;
import com.liferay.portal.security.permission.PermissionCheckerImpl;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.util.servlet.ServletResponseUtil;
import com.liferay.util.xml.XMLFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="AxisServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AxisServlet extends org.apache.axis.transport.http.AxisServlet {

	public void service(HttpServletRequest req, HttpServletResponse res) {
		PermissionCheckerImpl permissionChecker = null;

		try {
			String remoteUser = req.getRemoteUser();

			if (_log.isDebugEnabled()) {
				_log.debug("Remote user " + remoteUser);
			}

			if (remoteUser != null) {
				PrincipalThreadLocal.setName(remoteUser);

				long userId = GetterUtil.getLong(remoteUser);

				User user = UserLocalServiceUtil.getUserById(userId);

				permissionChecker = PermissionCheckerFactory.create(user, true);

				PermissionThreadLocal.setPermissionChecker(permissionChecker);
			}

			StringServletResponse stringServletRes = new StringServletResponse(
				res);

			super.service(req, stringServletRes);

			res.setContentType(stringServletRes.getContentType());

			String content = stringServletRes.getString();

			if (stringServletRes.getContentType().contains("text/xml")) {
				content = fixXml(content);
			}

			ServletResponseUtil.write(
				new UncommittedServletResponse(res),
				content.getBytes(StringPool.UTF8));
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			try {
				PermissionCheckerFactory.recycle(permissionChecker);
			}
			catch (Exception e) {
			}
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

	private static Log _log = LogFactory.getLog(AxisServlet.class);

}