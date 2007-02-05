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

package com.liferay.portlet.softwarerepository.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portlet.softwarerepository.model.SRLicense;
import com.liferay.portlet.softwarerepository.service.base.SRLicenseLocalServiceBaseImpl;
import com.liferay.portlet.softwarerepository.service.persistence.SRLicenseUtil;
import com.liferay.portlet.softwarerepository.service.persistence.SRProductEntryUtil;

import java.util.List;

/**
 * <a href="SRLicenseLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
 */
public class SRLicenseLocalServiceImpl extends SRLicenseLocalServiceBaseImpl {

	public SRLicense addLicense(
			String name, String url, boolean openSource, boolean active,
			boolean recommended)
		throws PortalException, SystemException {

		long licenseId = CounterLocalServiceUtil.increment(
			SRLicense.class.getName());

		SRLicense license = SRLicenseUtil.create(licenseId);

		license.setName(name);
		license.setUrl(url);
		license.setOpenSource(openSource);
		license.setActive(active);
		license.setRecommended(recommended);

		SRLicenseUtil.update(license);

		return license;
	}

	public void deleteLicense(long licenseId)
		throws PortalException, SystemException {

		SRLicenseUtil.remove(licenseId);
	}

	public SRLicense getLicense(long licenseId)
		throws PortalException, SystemException {

		return SRLicenseUtil.findByPrimaryKey(licenseId);
	}

	public List getLicenses() throws SystemException {
		return SRLicenseUtil.findAll();
	}

	public List getLicenses(int begin, int end) throws SystemException {
		return SRLicenseUtil.findAll(begin, end);
	}

	public List getLicenses(boolean active, boolean recommended)
		throws SystemException {

		return SRLicenseUtil.findByA_R(active, recommended);
	}

	public List getLicenses(
			boolean active, boolean recommended, int begin, int end)
		throws SystemException {

		return SRLicenseUtil.findByA_R(active, recommended, begin, end);
	}

	public int getLicensesCount() throws SystemException {
		return SRLicenseUtil.countAll();
	}

	public int getLicensesCount(boolean active, boolean recommended)
		throws SystemException {

		return SRLicenseUtil.countByA_R(active, recommended);
	}

	public List getProductEntryLicenses(long productEntryId)
		throws PortalException, SystemException {

		return SRProductEntryUtil.getSRLicenses(productEntryId);
	}

	public SRLicense updateLicense(
			long licenseId, String name, String url, boolean openSource,
			boolean active, boolean recommended)
		throws PortalException, SystemException {

		SRLicense license =
			SRLicenseUtil.findByPrimaryKey(licenseId);

		license.setName(name);
		license.setUrl(url);
		license.setOpenSource(openSource);
		license.setActive(active);
		license.setRecommended(recommended);

		SRLicenseUtil.update(license);

		return license;
	}

}