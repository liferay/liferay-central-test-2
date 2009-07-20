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

import com.liferay.portlet.softwarecatalog.service.SCProductVersionServiceUtil;

import java.rmi.RemoteException;

public class SCProductVersionServiceSoap {
	public static com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap addProductVersion(
		long productEntryId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean testDirectDownloadURL,
		boolean repoStoreArtifact, long[] frameworkVersionIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.softwarecatalog.model.SCProductVersion returnValue =
				SCProductVersionServiceUtil.addProductVersion(productEntryId,
					version, changeLog, downloadPageURL, directDownloadURL,
					testDirectDownloadURL, repoStoreArtifact,
					frameworkVersionIds, serviceContext);

			return com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteProductVersion(long productVersionId)
		throws RemoteException {
		try {
			SCProductVersionServiceUtil.deleteProductVersion(productVersionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap getProductVersion(
		long productVersionId) throws RemoteException {
		try {
			com.liferay.portlet.softwarecatalog.model.SCProductVersion returnValue =
				SCProductVersionServiceUtil.getProductVersion(productVersionId);

			return com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap[] getProductVersions(
		long productEntryId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> returnValue =
				SCProductVersionServiceUtil.getProductVersions(productEntryId,
					start, end);

			return com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getProductVersionsCount(long productEntryId)
		throws RemoteException {
		try {
			int returnValue = SCProductVersionServiceUtil.getProductVersionsCount(productEntryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap updateProductVersion(
		long productVersionId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean testDirectDownloadURL,
		boolean repoStoreArtifact, long[] frameworkVersionIds)
		throws RemoteException {
		try {
			com.liferay.portlet.softwarecatalog.model.SCProductVersion returnValue =
				SCProductVersionServiceUtil.updateProductVersion(productVersionId,
					version, changeLog, downloadPageURL, directDownloadURL,
					testDirectDownloadURL, repoStoreArtifact,
					frameworkVersionIds);

			return com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SCProductVersionServiceSoap.class);
}