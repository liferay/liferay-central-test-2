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
		java.lang.String plid, java.lang.String name, java.lang.String type,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws RemoteException {
		try {
			com.liferay.portlet.softwarerepository.model.SRProductEntry returnValue =
				SRProductEntryServiceUtil.addProductEntry(plid, name, type,
					shortDescription, longDescription, pageURL, repoGroupId,
					repoArtifactId, licenseIds, addCommunityPermissions,
					addGuestPermissions);

			return com.liferay.portlet.softwarerepository.model.SRProductEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntrySoap addProductEntry(
		java.lang.String plid, java.lang.String name, java.lang.String type,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions) throws RemoteException {
		try {
			com.liferay.portlet.softwarerepository.model.SRProductEntry returnValue =
				SRProductEntryServiceUtil.addProductEntry(plid, name, type,
					shortDescription, longDescription, pageURL, repoGroupId,
					repoArtifactId, licenseIds, communityPermissions,
					guestPermissions);

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

	public static com.liferay.portlet.softwarerepository.model.SRProductEntrySoap updateProductEntry(
		long productEntryId, java.lang.String name, java.lang.String type,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds)
		throws RemoteException {
		try {
			com.liferay.portlet.softwarerepository.model.SRProductEntry returnValue =
				SRProductEntryServiceUtil.updateProductEntry(productEntryId,
					name, type, shortDescription, longDescription, pageURL,
					repoGroupId, repoArtifactId, licenseIds);

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