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

package com.liferay.wiki.upgrade;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.service.ReleaseLocalService;
import com.liferay.wiki.upgrade.v1_0_0.UpgradeClassNames;
import com.liferay.wiki.upgrade.v1_0_0.UpgradeLastPublishDate;
import com.liferay.wiki.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.wiki.upgrade.v1_0_0.UpgradePortletPreferences;
import com.liferay.wiki.upgrade.v1_0_0.UpgradePortletSettings;
import com.liferay.wiki.upgrade.v1_0_0.UpgradeWikiPageResource;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(immediate = true, service = WikiServiceUpgrade.class)
public class WikiServiceUpgrade {

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Reference(unbind = "-")
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	@Activate
	protected void upgrade() throws PortalException {
		List<UpgradeProcess> upgradeProcesses = new ArrayList<>();

		upgradeProcesses.add(new UpgradePortletId());

		upgradeProcesses.add(new UpgradePortletPreferences());

		upgradeProcesses.add(new UpgradeClassNames());
		upgradeProcesses.add(new UpgradeLastPublishDate());
		upgradeProcesses.add(new UpgradePortletSettings(_settingsFactory));
		upgradeProcesses.add(new UpgradeWikiPageResource());

		_releaseLocalService.updateRelease(
			"com.liferay.wiki.service", upgradeProcesses, 1, 1, false);
	}

	private ReleaseLocalService _releaseLocalService;
	private SettingsFactory _settingsFactory;

}