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

package com.liferay.portal.security.jaas.ext.weblogic;

import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.security.jaas.ext.BasicLoginModule;

import java.security.Principal;

/**
 * <a href="PortalLoginModule.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortalLoginModule extends BasicLoginModule {

	protected Principal getPortalPrincipal(String name) {
		return (Principal)ReflectionUtil.newInstance(_WLS_USER_IMPL, name);
	}

	private static final String _WLS_USER_IMPL =
		"weblogic.security.principal.WLSUserImpl";

}