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

package com.liferay.portal.sso.openid.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.openid.OpenId;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.sso.openid.configuration.OpenIdConfiguration;
import com.liferay.portal.sso.openid.constants.OpenIdConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.sso.openid.configuration.OpenIdConfiguration",
	immediate = true, service = OpenId.class
)
public class OpenIdImpl implements OpenId {

	@Override
	public boolean isEnabled(long companyId) {
		try {
			OpenIdConfiguration openIdConfiguration =
				_settingsFactory.getSettings(
					OpenIdConfiguration.class,
					new CompanyServiceSettingsLocator(
						companyId, OpenIdConstants.SERVICE_NAME));

			return openIdConfiguration.enabled();
		}
		catch (SettingsException se) {
			_log.error("Unable to get OpenId configuration", se);
		}

		return false;
	}

	@Reference
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	private static final Log _log = LogFactoryUtil.getLog(OpenIdImpl.class);

	private volatile SettingsFactory _settingsFactory;

}