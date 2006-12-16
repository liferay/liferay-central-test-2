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

import com.liferay.portlet.softwarerepository.service.SRProductEntryServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="SRProductEntryServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SRProductEntryServiceSoap {
	public static com.liferay.portlet.softwarerepository.model.SRProductEntrySoap addProductEntry(
		java.lang.String plid, java.lang.String repoArtifactId,
		java.lang.String repoGroupId, java.lang.String name,
		java.lang.String type, long[] licenseIds,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL) throws RemoteException {
		try {
			com.liferay.portlet.softwarerepository.model.SRProductEntry returnValue =
				SRProductEntryServiceUtil.addProductEntry(plid, repoArtifactId,
					repoGroupId, name, type, licenseIds, shortDescription,
					longDescription, pageURL);

			return com.liferay.portlet.softwarerepository.model.SRProductEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void deleteProductEntry(long productEntryId)
		throws RemoteException {
		try {
			SRProductEntryServiceUtil.deleteProductEntry(productEntryId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntrySoap getProductEntry(
		long productEntryId) throws RemoteException {
		try {
			com.liferay.portlet.softwarerepository.model.SRProductEntry returnValue =
				SRProductEntryServiceUtil.getProductEntry(productEntryId);

			return com.liferay.portlet.softwarerepository.model.SRProductEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntrySoap[] getProductEntries(
		java.lang.String groupId, int begin, int end) throws RemoteException {
		try {
			java.util.List returnValue = SRProductEntryServiceUtil.getProductEntries(groupId,
					begin, end);

			return com.liferay.portlet.softwarerepository.model.SRProductEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntrySoap[] getProductEntriesByUserId(
		java.lang.String groupId, java.lang.String userId, int begin, int end)
		throws RemoteException {
		try {
			java.util.List returnValue = SRProductEntryServiceUtil.getProductEntriesByUserId(groupId,
					userId, begin, end);

			return com.liferay.portlet.softwarerepository.model.SRProductEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static int getProductEntriesCountByUserId(java.lang.String groupId,
		java.lang.String userId) throws RemoteException {
		try {
			int returnValue = SRProductEntryServiceUtil.getProductEntriesCountByUserId(groupId,
					userId);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static int getProductEntriesCount(java.lang.String groupId)
		throws RemoteException {
		try {
			int returnValue = SRProductEntryServiceUtil.getProductEntriesCount(groupId);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntrySoap updateProductEntry(
		long productEntryId, java.lang.String repoArtifactId,
		java.lang.String repoGroupId, java.lang.String name, long[] licenseIds,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL) throws RemoteException {
		try {
			com.liferay.portlet.softwarerepository.model.SRProductEntry returnValue =
				SRProductEntryServiceUtil.updateProductEntry(productEntryId,
					repoArtifactId, repoGroupId, name, licenseIds,
					shortDescription, longDescription, pageURL);

			return com.liferay.portlet.softwarerepository.model.SRProductEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SRProductEntryServiceSoap.class);
}