/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.secure;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="SecureUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Alexander Chow
 */
public class SecureUtil {

	public static long getBasicAuthUserId(
			HttpServletRequest request, long companyId)
		throws Exception {

		long userId = 0;

		String authorizationHeader = request.getHeader(
			HttpHeaders.AUTHORIZATION);

		if (Validator.isNull(authorizationHeader)) {
			return userId;
		}

		String[] authorizationArray = authorizationHeader.split("\\s+");

		String authorization = authorizationArray[0];
		String credentials = new String(Base64.decode(authorizationArray[1]));

		if (!authorization.equalsIgnoreCase(HttpServletRequest.BASIC_AUTH)) {
			return userId;
		}

		if (companyId <= 0) {
			companyId = PortalInstances.getCompanyId(request);
		}

		String[] loginAndPassword = StringUtil.split(
			credentials, StringPool.COLON);

		String login = loginAndPassword[0].trim();

		String password = null;

		if (loginAndPassword.length > 1) {
			password = loginAndPassword[1].trim();
		}

		// Strip @uid and @sn for backwards compatibility

		if (login.endsWith("@uid")) {
			int pos = login.indexOf("@uid");

			login = login.substring(0, pos);
		}
		else if (login.endsWith("@sn")) {
			int pos = login.indexOf("@sn");

			login = login.substring(0, pos);
		}

		// Try every authentication type

		userId = UserLocalServiceUtil.authenticateForBasic(
			companyId, CompanyConstants.AUTH_TYPE_EA, login, password);

		if (userId > 0) {
			return userId;
		}

		userId = UserLocalServiceUtil.authenticateForBasic(
			companyId, CompanyConstants.AUTH_TYPE_SN, login, password);

		if (userId > 0) {
			return userId;
		}

		userId = UserLocalServiceUtil.authenticateForBasic(
			companyId, CompanyConstants.AUTH_TYPE_ID, login, password);

		return userId;
	}

}