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

package com.liferay.service.access.control.profile;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.access.control.profile.ServiceAccessControlProfile;
import com.liferay.portal.kernel.security.access.control.profile.ServiceAccessControlProfileManager;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.service.access.control.profile.configuration.SACPConfiguration;
import com.liferay.service.access.control.profile.constants.SACPConstants;
import com.liferay.service.access.control.profile.model.SACPEntry;
import com.liferay.service.access.control.profile.service.SACPEntryService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(immediate = true, service = ServiceAccessControlProfileManager.class)
public class ServiceAccessControlProfileManagerImpl
	implements ServiceAccessControlProfileManager {

	@Override
	public String getDefaultApplicationServiceAccessControlProfileName(
		long companyId) {

		SACPConfiguration sacpConfiguration = getSACPConfiguration(companyId);

		if (sacpConfiguration != null) {
			return sacpConfiguration.defaultApplicationSACPEntryName();
		}

		return null;
	}

	@Override
	public String getDefaultUserServiceAccessControlProfileName(
		long companyId) {

		SACPConfiguration sacpConfiguration = getSACPConfiguration(companyId);

		if (sacpConfiguration != null) {
			return sacpConfiguration.defaultUserSACPEntryName();
		}

		return null;
	}

	@Override
	public ServiceAccessControlProfile getServiceAccessControlProfile(
		long companyId, String name) {

		try {
			return toServiceAccessControlProfile(
				_sacpEntryService.getSACPEntry(companyId, name));
		}
		catch (PortalException e) {
			return null;
		}
	}

	@Override
	public List<ServiceAccessControlProfile> getServiceAccessControlProfiles(
		long companyId, int start, int end) {

		return toServiceAccessControlProfiles(
			_sacpEntryService.getCompanySACPEntries(companyId, start, end));
	}

	@Override
	public int getServiceAccessControlProfilesCount(long companyId) {
		return _sacpEntryService.getCompanySACPEntriesCount(companyId);
	}

	protected SACPConfiguration getSACPConfiguration(long companyId) {
		try {
			return _settingsFactory.getSettings(
				SACPConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, SACPConstants.SERVICE_NAME));
		}
		catch (SettingsException se) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get SACP configuration", se);
			}

			return null;
		}
	}

	@Reference(unbind = "-")
	protected void setSACPEntryService(SACPEntryService sacpEntryService) {
		_sacpEntryService = sacpEntryService;
	}

	@Reference
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	protected ServiceAccessControlProfile toServiceAccessControlProfile(
		SACPEntry sacpEntry) {

		if (sacpEntry != null) {
			return new ServiceAccessControlProfileImpl(sacpEntry);
		}

		return null;
	}

	protected List<ServiceAccessControlProfile> toServiceAccessControlProfiles(
		List<SACPEntry> sacpEntries) {

		if (sacpEntries == null) {
			return null;
		}

		List<ServiceAccessControlProfile> serviceAccessControlProfiles =
			new ArrayList<>(sacpEntries.size());

		for (SACPEntry sacpEntry : sacpEntries) {
			serviceAccessControlProfiles.add(
				toServiceAccessControlProfile(sacpEntry));
		}

		return serviceAccessControlProfiles;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceAccessControlProfileManagerImpl.class);

	private SACPEntryService _sacpEntryService;
	private volatile SettingsFactory _settingsFactory;

}