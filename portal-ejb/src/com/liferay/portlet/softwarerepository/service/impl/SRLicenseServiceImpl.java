/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portlet.softwarerepository.model.SRLicense;
import com.liferay.portlet.softwarerepository.service.SRLicenseService;
import com.liferay.portlet.softwarerepository.service.persistence.SRLicenseUtil;

import java.util.List;

/**
 * <a href="SRLicenseServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Jorge Ferrer
 *
 */
public class SRLicenseServiceImpl extends PrincipalBean
	implements SRLicenseService {
	public SRLicense addLicense(
			String name, boolean active, boolean openSource,
			boolean recommended, String url)
		throws PortalException, SystemException {

		long licenseId = CounterLocalServiceUtil.increment(
			SRLicense.class.getName());

		SRLicense license = SRLicenseUtil.create(licenseId);

		license.setName(name);
		license.setActive(active);
		license.setUrl(url);
		license.setOpenSource(openSource);
		license.setRecommended(recommended);

		SRLicenseUtil.update(license);

		return license;
	}

	public void deleteLicense(long licenseId)
		throws PortalException, SystemException {

		SRLicense license = SRLicenseUtil.findByPrimaryKey(licenseId);

		SRLicenseUtil.remove(license.getLicenseId());
	}

	public SRLicense getLicense(long licenseId)
		throws PortalException, SystemException {

		return SRLicenseUtil.findByPrimaryKey(licenseId);
	}

	public List getLicenses()
		throws SystemException {

		return SRLicenseUtil.findAll();
	}

	public List getLicenses(boolean active, boolean recommended)
		throws SystemException {

		return SRLicenseUtil.findByA_R(active, recommended);
	}

	public int getLicensesCount()
		throws SystemException {

		return SRLicenseUtil.countByActive(true) +
			SRLicenseUtil.countByActive(false);
	}

	public int getLicensesCount(boolean active, boolean recommended)
		throws SystemException {

		return SRLicenseUtil.countByA_R(active, recommended);
	}

	public SRLicense updateLicense(
		long licenseId, String name, boolean active, boolean openSource,
		boolean recommended, String url)
		throws PortalException, SystemException {

		SRLicense license =
			SRLicenseUtil.findByPrimaryKey(licenseId);

		license.setName(name);
		license.setActive(active);
		license.setUrl(url);
		license.setOpenSource(openSource);
		license.setRecommended(recommended);

		SRLicenseUtil.update(license);

		return license;
	}

}