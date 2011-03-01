/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

	/**
	* Adds the s c license to the database. Also notifies the appropriate model listeners.
	*
	* @param scLicense the s c license to add
	* @return the s c license that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.softwarecatalog.model.SCLicense addSCLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.addSCLicense(scLicense);
	}

	/**
	* Creates a new s c license with the primary key. Does not add the s c license to the database.
	*
	* @param licenseId the primary key for the new s c license
	* @return the new s c license
	*/
	public com.liferay.portlet.softwarecatalog.model.SCLicense createSCLicense(
		long licenseId) {
		return _scLicenseLocalService.createSCLicense(licenseId);
	}

	/**
	* Deletes the s c license with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param licenseId the primary key of the s c license to delete
	* @throws PortalException if a s c license with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSCLicense(long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scLicenseLocalService.deleteSCLicense(licenseId);
	}

	/**
	* Deletes the s c license from the database. Also notifies the appropriate model listeners.
	*
	* @param scLicense the s c license to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSCLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scLicenseLocalService.deleteSCLicense(scLicense);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the s c license with the primary key.
	*
	* @param licenseId the primary key of the s c license to get
	* @return the s c license
	* @throws PortalException if a s c license with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.softwarecatalog.model.SCLicense getSCLicense(
		long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getSCLicense(licenseId);
	}

	/**
	* Gets a range of all the s c licenses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of s c licenses to return
	* @param end the upper bound of the range of s c licenses to return (not inclusive)
	* @return the range of s c licenses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getSCLicenses(start, end);
	}

	/**
	* Gets the number of s c licenses.
	*
	* @return the number of s c licenses
	* @throws SystemException if a system exception occurred
	*/
	public int getSCLicensesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getSCLicensesCount();
	}

	/**
	* Updates the s c license in the database. Also notifies the appropriate model listeners.
	*
	* @param scLicense the s c license to update
	* @return the s c license that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.softwarecatalog.model.SCLicense updateSCLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.updateSCLicense(scLicense);
	}

	/**
	* Updates the s c license in the database. Also notifies the appropriate model listeners.
	*
	* @param scLicense the s c license to update
	* @param merge whether to merge the s c license with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the s c license that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.softwarecatalog.model.SCLicense updateSCLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.updateSCLicense(scLicense, merge);
	}

	/**
	* Gets the Spring bean id for this ServiceBean.
	*
	* @return the Spring bean id for this ServiceBean
	*/
	public java.lang.String getIdentifier() {
		return _scLicenseLocalService.getIdentifier();
	}

	/**
	* Sets the Spring bean id for this ServiceBean.
	*
	* @param identifier the Spring bean id for this ServiceBean
	*/
	public void setIdentifier(java.lang.String identifier) {
		_scLicenseLocalService.setIdentifier(identifier);
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

	public void deleteLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense license)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scLicenseLocalService.deleteLicense(license);
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

	public void setWrappedSCLicenseLocalService(
		SCLicenseLocalService scLicenseLocalService) {
		_scLicenseLocalService = scLicenseLocalService;
	}

	private SCLicenseLocalService _scLicenseLocalService;
}