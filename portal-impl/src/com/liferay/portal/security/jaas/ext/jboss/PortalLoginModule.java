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

package com.liferay.portal.security.jaas.ext.jboss;

import com.liferay.portal.kernel.security.jaas.PortalGroup;
import com.liferay.portal.kernel.security.jaas.PortalPrincipal;
import com.liferay.portal.security.jaas.ext.BasicLoginModule;

/**
 * <a href="PortalLoginModule.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortalLoginModule extends BasicLoginModule {

	public boolean commit() {
		boolean commitValue = super.commit();

		if (commitValue) {
			PortalGroup group = new PortalGroup("Roles");

			group.addMember(new PortalPrincipal("users"));

			getSubject().getPrincipals().add(group);
		}

		return commitValue;
	}

}