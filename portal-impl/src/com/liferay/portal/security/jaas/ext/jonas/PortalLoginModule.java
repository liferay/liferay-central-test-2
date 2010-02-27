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

package com.liferay.portal.security.jaas.ext.jonas;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.security.jaas.ext.BasicLoginModule;

import java.lang.reflect.Method;

import java.security.Principal;

/**
 * <a href="PortalLoginModule.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortalLoginModule extends BasicLoginModule {

	public boolean commit() {
		boolean commitValue = super.commit();

		if (commitValue) {
			getSubject().getPrincipals().add(getPrincipal());
			getSubject().getPrivateCredentials().add(getPassword());

			Principal group = (Principal)ReflectionUtil.newInstance(
				_JGROUP, "Roles");
			Object role = ReflectionUtil.newInstance(_JROLE, "users");

			try {
				Method method = MethodCache.get(
					_JGROUP, "addMember", new Class[] {role.getClass()});

				method.invoke(group, new Object[] {role});
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			getSubject().getPrincipals().add(group);
		}

		return commitValue;
	}

	protected Principal getPortalPrincipal(String name) {
		return (Principal)ReflectionUtil.newInstance(_JPRINCIPAL, name);
	}

	private static final String _JGROUP =
		"org.objectweb.jonas.security.auth.JGroup";

	private static final String _JPRINCIPAL =
		"org.objectweb.jonas.security.auth.JPrincipal";

	private static final String _JROLE =
		"org.objectweb.jonas.security.auth.JRole";

 	private static Log _log = LogFactoryUtil.getLog(PortalLoginModule.class);

}