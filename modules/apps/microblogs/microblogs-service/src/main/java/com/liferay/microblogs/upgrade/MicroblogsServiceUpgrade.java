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

package com.liferay.microblogs.upgrade;

import com.liferay.microblogs.upgrade.v1_0_1.UpgradeUserNotificationEvent;
import com.liferay.microblogs.upgrade.v1_0_2.UpgradeSocial;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ryan Park
 * @author Manuel de la Peña
 */
@Component(immediate = true)
public class MicroblogsServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.microblogs.service", "0.0.1", "1.0.0",
			new com.liferay.microblogs.upgrade.v1_0_0.UpgradeMicroblogsEntry());

		registry.register(
			"com.liferay.microblogs.service", "1.0.0", "1.0.1",
			new UpgradeUserNotificationEvent());

		registry.register(
			"com.liferay.microblogs.service", "1.0.1", "1.0.2",
			new com.liferay.microblogs.upgrade.v1_0_2.UpgradeMicroblogsEntry(),
			new UpgradeSocial());
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

}