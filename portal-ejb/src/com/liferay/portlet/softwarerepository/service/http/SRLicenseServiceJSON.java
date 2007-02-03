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

package com.liferay.portlet.softwarerepository.service.http;

import com.liferay.portlet.softwarerepository.service.SRLicenseServiceUtil;

import org.json.JSONObject;

/**
 * <a href="SRLicenseServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SRLicenseServiceJSON {
	public static JSONObject addLicense(java.lang.String name,
		java.lang.String url, boolean openSource, boolean active,
		boolean recommended)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.softwarerepository.model.SRLicense returnValue = SRLicenseServiceUtil.addLicense(name,
				url, openSource, active, recommended);

		return SRLicenseJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteLicense(long licenseId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		SRLicenseServiceUtil.deleteLicense(licenseId);
	}

	public static JSONObject getLicense(long licenseId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.softwarerepository.model.SRLicense returnValue = SRLicenseServiceUtil.getLicense(licenseId);

		return SRLicenseJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateLicense(long licenseId,
		java.lang.String name, java.lang.String url, boolean openSource,
		boolean active, boolean recommended)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.softwarerepository.model.SRLicense returnValue = SRLicenseServiceUtil.updateLicense(licenseId,
				name, url, openSource, active, recommended);

		return SRLicenseJSONSerializer.toJSONObject(returnValue);
	}
}