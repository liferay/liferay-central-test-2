/** Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.service;

import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactory;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.util.BaseTestCase;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.util.bean.PortletBeanLocatorUtil;

import java.net.URL;

/**
 * <a href="BasePluginsServiceTestCase.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ganesh Ram
 *
 */

public class BasePluginsServiceTestCase extends BaseTestCase {

	protected URL getClassResource(String name) {
		return getClass().getClassLoader().getResource(name);
	}

	protected void setUp() throws Exception {
		System.setProperty("external-properties",
			"portal-plugin-test.properties");

		if (PortletClassLoaderUtil.getClassLoader() == null) {
			PortletClassLoaderUtil.setClassLoader(
				Thread.currentThread().getContextClassLoader());
		}

		if (PortletBeanLocatorUtil.getBeanLocator() == null) {
			PortletBeanLocatorUtil.setBeanLocator(new BeanLocatorImpl());
		}

		super.setUp();

		PortalInstances.addCompanyId(TestPropsValues.COMPANY_ID);

		PrincipalThreadLocal.setName(TestPropsValues.USER_ID);

		User user = UserLocalServiceUtil.getUserById(TestPropsValues.USER_ID);

		_permissionChecker = PermissionCheckerFactory.create(user, true);

		PermissionThreadLocal.setPermissionChecker(_permissionChecker);
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		PermissionCheckerFactory.recycle(_permissionChecker);
	}

	private PermissionChecker _permissionChecker = null;

}