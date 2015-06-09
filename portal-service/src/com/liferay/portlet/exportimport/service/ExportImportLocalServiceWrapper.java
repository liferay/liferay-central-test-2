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
 * Provides a wrapper for {@link ExportImportLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportLocalService
 * @generated
 */
@ProviderType
public class ExportImportLocalServiceWrapper implements ExportImportLocalService,
	ServiceWrapper<ExportImportLocalService> {
	public ExportImportLocalServiceWrapper(
		ExportImportLocalService exportImportLocalService) {
		_exportImportLocalService = exportImportLocalService;
	}

	@Override
	public java.io.File exportLayoutsAsFile(
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.exportLayoutsAsFile(exportImportConfiguration);
	}

	@Override
	public long exportLayoutsAsFileInBackground(long userId,
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.exportLayoutsAsFileInBackground(userId,
			exportImportConfiguration);
	}

	@Override
	public long exportLayoutsAsFileInBackground(long userId,
		long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.exportLayoutsAsFileInBackground(userId,
			exportImportConfigurationId);
	}

	@Override
	public java.io.File exportPortletInfoAsFile(
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.exportPortletInfoAsFile(exportImportConfiguration);
	}

	@Override
	public long exportPortletInfoAsFileInBackground(long userId,
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.exportPortletInfoAsFileInBackground(userId,
			exportImportConfiguration);
	}

	@Override
	public long exportPortletInfoAsFileInBackground(long userId,
		long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.exportPortletInfoAsFileInBackground(userId,
			exportImportConfigurationId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _exportImportLocalService.getBeanIdentifier();
	}

	@Override
	public void importLayouts(
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		_exportImportLocalService.importLayouts(exportImportConfiguration, file);
	}

	@Override
	public void importLayouts(
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		_exportImportLocalService.importLayouts(exportImportConfiguration,
			inputStream);
	}

	@Override
	public void importLayoutsDataDeletions(
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		_exportImportLocalService.importLayoutsDataDeletions(exportImportConfiguration,
			file);
	}

	@Override
	public long importLayoutsInBackground(long userId,
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.importLayoutsInBackground(userId,
			exportImportConfiguration, file);
	}

	@Override
	public long importLayoutsInBackground(long userId,
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.importLayoutsInBackground(userId,
			exportImportConfiguration, inputStream);
	}

	@Override
	public long importLayoutsInBackground(long userId,
		long exportImportConfigurationId, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.importLayoutsInBackground(userId,
			exportImportConfigurationId, file);
	}

	@Override
	public long importLayoutsInBackground(long userId,
		long exportImportConfigurationId, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.importLayoutsInBackground(userId,
			exportImportConfigurationId, inputStream);
	}

	@Override
	public void importPortletDataDeletions(
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		_exportImportLocalService.importPortletDataDeletions(exportImportConfiguration,
			file);
	}

	@Override
	public void importPortletInfo(
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		_exportImportLocalService.importPortletInfo(exportImportConfiguration,
			file);
	}

	@Override
	public void importPortletInfo(
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		_exportImportLocalService.importPortletInfo(exportImportConfiguration,
			inputStream);
	}

	@Override
	public long importPortletInfoInBackground(long userId,
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.importPortletInfoInBackground(userId,
			exportImportConfiguration, file);
	}

	@Override
	public long importPortletInfoInBackground(long userId,
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.importPortletInfoInBackground(userId,
			exportImportConfiguration, inputStream);
	}

	@Override
	public long importPortletInfoInBackground(long userId,
		long exportImportConfigurationId, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.importPortletInfoInBackground(userId,
			exportImportConfigurationId, file);
	}

	@Override
	public long importPortletInfoInBackground(long userId,
		long exportImportConfigurationId, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.importPortletInfoInBackground(userId,
			exportImportConfigurationId, inputStream);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_exportImportLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portal.kernel.lar.MissingReferences validateImportLayoutsFile(
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.validateImportLayoutsFile(exportImportConfiguration,
			file);
	}

	@Override
	public com.liferay.portal.kernel.lar.MissingReferences validateImportLayoutsFile(
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.validateImportLayoutsFile(exportImportConfiguration,
			inputStream);
	}

	@Override
	public com.liferay.portal.kernel.lar.MissingReferences validateImportPortletInfo(
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.validateImportPortletInfo(exportImportConfiguration,
			file);
	}

	@Override
	public com.liferay.portal.kernel.lar.MissingReferences validateImportPortletInfo(
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.validateImportPortletInfo(exportImportConfiguration,
			inputStream);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ExportImportLocalService getWrappedExportImportLocalService() {
		return _exportImportLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedExportImportLocalService(
		ExportImportLocalService exportImportLocalService) {
		_exportImportLocalService = exportImportLocalService;
	}

	@Override
	public ExportImportLocalService getWrappedService() {
		return _exportImportLocalService;
	}

	@Override
	public void setWrappedService(
		ExportImportLocalService exportImportLocalService) {
		_exportImportLocalService = exportImportLocalService;
	}

	private ExportImportLocalService _exportImportLocalService;
}