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

package com.liferay.portlet.softwarerepository.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;

import com.liferay.portlet.softwarerepository.service.SRProductVersionServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="SRProductVersionServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SRProductVersionServiceSoap {
	public static com.liferay.portlet.softwarerepository.model.SRProductVersionSoap addProductVersion(
		long productEntryId, java.lang.String version,
		java.lang.String changeLog, long[] frameworkVersionIds,
		java.lang.String downloadPageURL, java.lang.String directDownloadURL,
		boolean repoStoreArtifact) throws RemoteException {
		try {
			com.liferay.portlet.softwarerepository.model.SRProductVersion returnValue =
				SRProductVersionServiceUtil.addProductVersion(productEntryId,
					version, changeLog, frameworkVersionIds, downloadPageURL,
					directDownloadURL, repoStoreArtifact);

			return com.liferay.portlet.softwarerepository.model.SRProductVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void deleteProductVersion(long productVersionId)
		throws RemoteException {
		try {
			SRProductVersionServiceUtil.deleteProductVersion(productVersionId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductVersionSoap getProductVersion(
		long productVersionId) throws RemoteException {
		try {
			com.liferay.portlet.softwarerepository.model.SRProductVersion returnValue =
				SRProductVersionServiceUtil.getProductVersion(productVersionId);

			return com.liferay.portlet.softwarerepository.model.SRProductVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductVersionSoap[] getProductVersions(
		long productEntryId, int begin, int end) throws RemoteException {
		try {
			java.util.List returnValue = SRProductVersionServiceUtil.getProductVersions(productEntryId,
					begin, end);

			return com.liferay.portlet.softwarerepository.model.SRProductVersionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static int getProductVersionsCount(long productEntryId)
		throws RemoteException {
		try {
			int returnValue = SRProductVersionServiceUtil.getProductVersionsCount(productEntryId);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductVersionSoap updateProductVersion(
		long productVersionId, java.lang.String version,
		java.lang.String changeLog, long[] frameworkVersionIds,
		java.lang.String downloadPageURL, java.lang.String directDownloadURL,
		boolean repoStoreArtifact) throws RemoteException {
		try {
			com.liferay.portlet.softwarerepository.model.SRProductVersion returnValue =
				SRProductVersionServiceUtil.updateProductVersion(productVersionId,
					version, changeLog, frameworkVersionIds, downloadPageURL,
					directDownloadURL, repoStoreArtifact);

			return com.liferay.portlet.softwarerepository.model.SRProductVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SRProductVersionServiceSoap.class);
}