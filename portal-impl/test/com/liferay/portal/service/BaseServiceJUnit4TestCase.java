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

package com.liferay.portal.service;

import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.TestPropsValues;

import junit.framework.Assert;

/**
 * <a href="BaseServiceJUnit4TestCase.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class BaseServiceJUnit4TestCase extends Assert {

	private static PermissionChecker _permissionChecker = null;

	static {
		try {
			if (System.getProperty("external-properties") == null) {
				System.setProperty(
					"external-properties", "portal-test.properties");
			}

			InitUtil.initWithSpring();

			PortalInstances.addCompanyId(TestPropsValues.COMPANY_ID);

			PrincipalThreadLocal.setName(TestPropsValues.USER_ID);

			User user = UserLocalServiceUtil.getUserById(
				TestPropsValues.USER_ID);

			_permissionChecker = PermissionCheckerFactoryUtil.create(
				user, true);

			PermissionThreadLocal.setPermissionChecker(_permissionChecker);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}