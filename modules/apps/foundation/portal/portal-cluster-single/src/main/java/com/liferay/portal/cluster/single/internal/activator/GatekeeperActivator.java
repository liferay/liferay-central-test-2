/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.cluster.single.internal.activator;

import com.liferay.portal.kernel.util.ReleaseInfo;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true)
public class GatekeeperActivator {

	@Activate
	public void activate(ComponentContext componentContext) {
		String name = ReleaseInfo.getName();

		if (!name.contains("Community")) {
			return;
		}

		componentContext.enableComponent(SingleClusterExecutor.class.getName());
		componentContext.enableComponent(SingleClusterLink.class.getName());
		componentContext.enableComponent(
			SingleClusterMasterExecutor.class.getName());
	}

}