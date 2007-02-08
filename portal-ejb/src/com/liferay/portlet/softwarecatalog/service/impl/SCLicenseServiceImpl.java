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

package com.liferay.portlet.softwarecatalog.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portlet.softwarecatalog.model.SCLicense;
import com.liferay.portlet.softwarecatalog.service.SCLicenseLocalServiceUtil;
import com.liferay.portlet.softwarecatalog.service.SCLicenseService;

/**
 * <a href="SCLicenseServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
 */
public class SCLicenseServiceImpl
	extends PrincipalBean implements SCLicenseService {

	public SCLicense addLicense(
			String name, String url, boolean openSource, boolean active,
			boolean recommended)
		throws PortalException, SystemException {

		return SCLicenseLocalServiceUtil.addLicense(
			name, url, openSource, active, recommended);
	}

	public void deleteLicense(long licenseId)
		throws PortalException, SystemException {

		SCLicenseLocalServiceUtil.deleteLicense(licenseId);
	}

	public SCLicense getLicense(long licenseId)
		throws PortalException, SystemException {

		return SCLicenseLocalServiceUtil.getLicense(licenseId);
	}

	public SCLicense updateLicense(
			long licenseId, String name, String url, boolean openSource,
			boolean active, boolean recommended)
		throws PortalException, SystemException {

		return SCLicenseLocalServiceUtil.updateLicense(
			licenseId, name, url, openSource, active, recommended);
	}

}