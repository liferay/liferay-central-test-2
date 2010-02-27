/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.service;


/**
 * <a href="SCLicenseLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SCLicenseLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCLicenseLocalService
 * @generated
 */
public class SCLicenseLocalServiceWrapper implements SCLicenseLocalService {
	public SCLicenseLocalServiceWrapper(
		SCLicenseLocalService scLicenseLocalService) {
		_scLicenseLocalService = scLicenseLocalService;
	}

	public com.liferay.portlet.softwarecatalog.model.SCLicense addSCLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.addSCLicense(scLicense);
	}

	public com.liferay.portlet.softwarecatalog.model.SCLicense createSCLicense(
		long licenseId) {
		return _scLicenseLocalService.createSCLicense(licenseId);
	}

	public void deleteSCLicense(long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scLicenseLocalService.deleteSCLicense(licenseId);
	}

	public void deleteSCLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scLicenseLocalService.deleteSCLicense(scLicense);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.softwarecatalog.model.SCLicense getSCLicense(
		long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getSCLicense(licenseId);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getSCLicenses(start, end);
	}

	public int getSCLicensesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getSCLicensesCount();
	}

	public com.liferay.portlet.softwarecatalog.model.SCLicense updateSCLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.updateSCLicense(scLicense);
	}

	public com.liferay.portlet.softwarecatalog.model.SCLicense updateSCLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.updateSCLicense(scLicense, merge);
	}

	public com.liferay.portlet.softwarecatalog.model.SCLicense addLicense(
		java.lang.String name, java.lang.String url, boolean openSource,
		boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.addLicense(name, url, openSource, active,
			recommended);
	}

	public void deleteLicense(long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scLicenseLocalService.deleteLicense(licenseId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCLicense getLicense(
		long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getLicense(licenseId);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getLicenses();
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses(
		boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getLicenses(active, recommended);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses(
		boolean active, boolean recommended, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getLicenses(active, recommended, start,
			end);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getLicenses(start, end);
	}

	public int getLicensesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getLicensesCount();
	}

	public int getLicensesCount(boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getLicensesCount(active, recommended);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getProductEntryLicenses(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getProductEntryLicenses(productEntryId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCLicense updateLicense(
		long licenseId, java.lang.String name, java.lang.String url,
		boolean openSource, boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.updateLicense(licenseId, name, url,
			openSource, active, recommended);
	}

	public SCLicenseLocalService getWrappedSCLicenseLocalService() {
		return _scLicenseLocalService;
	}

	private SCLicenseLocalService _scLicenseLocalService;
}