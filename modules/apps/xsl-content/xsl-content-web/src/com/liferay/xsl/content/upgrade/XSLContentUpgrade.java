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

package com.liferay.xsl.content.upgrade;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.service.ReleaseLocalService;
import com.liferay.portal.upgrade.util.UpgradePortletId;
import com.liferay.xsl.content.portlet.XSLContentPortlet;

import java.util.Collections;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true, service = XSLContentUpgrade.class
)
public class XSLContentUpgrade {

	@Reference
	private void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@SuppressWarnings("unused")
	private void unsetReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = null;
	}

	@Activate
	private void upgrade() throws PortalException {
		UpgradePortletId upgradePortletId = new UpgradePortletId() {

			@Override
			protected String[][] getRenamePortletIdsArray() {
				return new String[][] {
					new String[] {
						"102",
						"com_liferay_xsl_content_portlet_XSLContentPortlet"
					}
				};
			}

		};

		_releaseLocalService.updateRelease(
			XSLContentPortlet.class.getName(),
			Collections.<UpgradeProcess>singletonList(upgradePortletId), 1, 1,
			false);
	}

	private ReleaseLocalService _releaseLocalService;

}