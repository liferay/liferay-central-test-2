/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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