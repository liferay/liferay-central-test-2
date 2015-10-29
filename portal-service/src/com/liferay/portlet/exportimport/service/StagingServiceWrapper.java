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

package com.liferay.portlet.exportimport.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link StagingService}.
 *
 * @author Brian Wing Shun Chan
 * @see StagingService
 * @generated
 */
@ProviderType
public class StagingServiceWrapper implements StagingService,
	ServiceWrapper<StagingService> {
	public StagingServiceWrapper(StagingService stagingService) {
		_stagingService = stagingService;
	}

	@Override
	public void cleanUpStagingRequest(long stagingRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_stagingService.cleanUpStagingRequest(stagingRequestId);
	}

	@Override
	public long createStagingRequest(long groupId, java.lang.String checksum)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _stagingService.createStagingRequest(groupId, checksum);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _stagingService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portlet.exportimport.lar.MissingReferences publishStagingRequest(
		long stagingRequestId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _stagingService.publishStagingRequest(stagingRequestId,
			exportImportConfiguration);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.exportimport.lar.MissingReferences publishStagingRequest(
		long stagingRequestId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _stagingService.publishStagingRequest(stagingRequestId,
			privateLayout, parameterMap);
	}

	@Override
	public void updateStagingRequest(long stagingRequestId,
		java.lang.String fileName, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {
		_stagingService.updateStagingRequest(stagingRequestId, fileName, bytes);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #publishStagingRequest(long,
	boolean, Map)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.exportimport.lar.MissingReferences validateStagingRequest(
		long stagingRequestId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _stagingService.validateStagingRequest(stagingRequestId,
			privateLayout, parameterMap);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public StagingService getWrappedStagingService() {
		return _stagingService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedStagingService(StagingService stagingService) {
		_stagingService = stagingService;
	}

	@Override
	public StagingService getWrappedService() {
		return _stagingService;
	}

	@Override
	public void setWrappedService(StagingService stagingService) {
		_stagingService = stagingService;
	}

	private StagingService _stagingService;
}