/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarerepository.service;

/**
 * <a href="SRLicenseLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SRLicenseLocalServiceUtil {
	public static com.liferay.portlet.softwarerepository.model.SRLicense addLicense(
		java.lang.String name, java.lang.String url, boolean openSource,
		boolean active, boolean recommended)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRLicenseLocalService srLicenseLocalService = SRLicenseLocalServiceFactory.getService();

		return srLicenseLocalService.addLicense(name, url, openSource, active,
			recommended);
	}

	public static void deleteLicense(long licenseId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRLicenseLocalService srLicenseLocalService = SRLicenseLocalServiceFactory.getService();
		srLicenseLocalService.deleteLicense(licenseId);
	}

	public static com.liferay.portlet.softwarerepository.model.SRLicense getLicense(
		long licenseId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRLicenseLocalService srLicenseLocalService = SRLicenseLocalServiceFactory.getService();

		return srLicenseLocalService.getLicense(licenseId);
	}

	public static java.util.List getLicenses()
		throws com.liferay.portal.SystemException {
		SRLicenseLocalService srLicenseLocalService = SRLicenseLocalServiceFactory.getService();

		return srLicenseLocalService.getLicenses();
	}

	public static java.util.List getLicenses(int begin, int end)
		throws com.liferay.portal.SystemException {
		SRLicenseLocalService srLicenseLocalService = SRLicenseLocalServiceFactory.getService();

		return srLicenseLocalService.getLicenses(begin, end);
	}

	public static java.util.List getLicenses(boolean active, boolean recommended)
		throws com.liferay.portal.SystemException {
		SRLicenseLocalService srLicenseLocalService = SRLicenseLocalServiceFactory.getService();

		return srLicenseLocalService.getLicenses(active, recommended);
	}

	public static java.util.List getLicenses(boolean active,
		boolean recommended, int begin, int end)
		throws com.liferay.portal.SystemException {
		SRLicenseLocalService srLicenseLocalService = SRLicenseLocalServiceFactory.getService();

		return srLicenseLocalService.getLicenses(active, recommended, begin, end);
	}

	public static int getLicensesCount()
		throws com.liferay.portal.SystemException {
		SRLicenseLocalService srLicenseLocalService = SRLicenseLocalServiceFactory.getService();

		return srLicenseLocalService.getLicensesCount();
	}

	public static int getLicensesCount(boolean active, boolean recommended)
		throws com.liferay.portal.SystemException {
		SRLicenseLocalService srLicenseLocalService = SRLicenseLocalServiceFactory.getService();

		return srLicenseLocalService.getLicensesCount(active, recommended);
	}

	public static java.util.List getProductEntryLicenses(long productEntryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRLicenseLocalService srLicenseLocalService = SRLicenseLocalServiceFactory.getService();

		return srLicenseLocalService.getProductEntryLicenses(productEntryId);
	}

	public static com.liferay.portlet.softwarerepository.model.SRLicense updateLicense(
		long licenseId, java.lang.String name, java.lang.String url,
		boolean openSource, boolean active, boolean recommended)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SRLicenseLocalService srLicenseLocalService = SRLicenseLocalServiceFactory.getService();

		return srLicenseLocalService.updateLicense(licenseId, name, url,
			openSource, active, recommended);
	}
}