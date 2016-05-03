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

package com.liferay.sync.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SyncPreferencesLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SyncPreferencesLocalService
 * @generated
 */
@ProviderType
public class SyncPreferencesLocalServiceWrapper
	implements SyncPreferencesLocalService,
		ServiceWrapper<SyncPreferencesLocalService> {
	public SyncPreferencesLocalServiceWrapper(
		SyncPreferencesLocalService syncPreferencesLocalService) {
		_syncPreferencesLocalService = syncPreferencesLocalService;
	}

	@Override
	public boolean isOAuthApplicationAvailable(long oAuthApplicationId) {
		return _syncPreferencesLocalService.isOAuthApplicationAvailable(oAuthApplicationId);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _syncPreferencesLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _syncPreferencesLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public javax.portlet.PortletPreferences getPortletPreferences(
		long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _syncPreferencesLocalService.getPortletPreferences(companyId);
	}

	@Override
	public void enableOAuth(long companyId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_syncPreferencesLocalService.enableOAuth(companyId, serviceContext);
	}

	@Override
	public SyncPreferencesLocalService getWrappedService() {
		return _syncPreferencesLocalService;
	}

	@Override
	public void setWrappedService(
		SyncPreferencesLocalService syncPreferencesLocalService) {
		_syncPreferencesLocalService = syncPreferencesLocalService;
	}

	private SyncPreferencesLocalService _syncPreferencesLocalService;
}