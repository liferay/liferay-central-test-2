/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.softwarecatalog.service.SCLicenseServiceUtil;

import java.rmi.RemoteException;

public class SCLicenseServiceSoap {
	public static com.liferay.portlet.softwarecatalog.model.SCLicenseSoap addLicense(
		java.lang.String name, java.lang.String url, boolean openSource,
		boolean active, boolean recommended) throws RemoteException {
		try {
			com.liferay.portlet.softwarecatalog.model.SCLicense returnValue = SCLicenseServiceUtil.addLicense(name,
					url, openSource, active, recommended);

			return com.liferay.portlet.softwarecatalog.model.SCLicenseSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteLicense(long licenseId) throws RemoteException {
		try {
			SCLicenseServiceUtil.deleteLicense(licenseId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicenseSoap getLicense(
		long licenseId) throws RemoteException {
		try {
			com.liferay.portlet.softwarecatalog.model.SCLicense returnValue = SCLicenseServiceUtil.getLicense(licenseId);

			return com.liferay.portlet.softwarecatalog.model.SCLicenseSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicenseSoap updateLicense(
		long licenseId, java.lang.String name, java.lang.String url,
		boolean openSource, boolean active, boolean recommended)
		throws RemoteException {
		try {
			com.liferay.portlet.softwarecatalog.model.SCLicense returnValue = SCLicenseServiceUtil.updateLicense(licenseId,
					name, url, openSource, active, recommended);

			return com.liferay.portlet.softwarecatalog.model.SCLicenseSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SCLicenseServiceSoap.class);
}