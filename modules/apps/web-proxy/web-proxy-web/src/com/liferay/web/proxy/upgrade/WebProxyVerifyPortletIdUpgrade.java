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

package com.liferay.web.proxy.upgrade;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.service.ReleaseLocalService;
import com.liferay.portal.upgrade.util.UpgradePortletId;
import com.liferay.web.proxy.portlet.WebProxyPortlet;

import java.util.Collections;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true, service = WebProxyVerifyPortletIdUpgrade.class
)
public class WebProxyVerifyPortletIdUpgrade {

	@Reference(unbind = "-")
	private void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Activate
	private void upgrade() throws PortalException {
		UpgradePortletId upgradePortletId = new UpgradePortletId() {

			@Override
			protected String[][] getRenamePortletIdsArray() {
				return new String[][] {
					new String[] {
						"66", "com_liferay_web_proxy_portlet_WebProxyPortlet"
					}
				};
			}

		};

		_releaseLocalService.updateRelease(
			WebProxyPortlet.class.getName(),
			Collections.<UpgradeProcess>singletonList(upgradePortletId), 1, 1,
			false);
	}

	private ReleaseLocalService _releaseLocalService;

}