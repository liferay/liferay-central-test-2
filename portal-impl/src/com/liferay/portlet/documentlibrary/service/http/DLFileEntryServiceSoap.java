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

package com.liferay.portlet.documentlibrary.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;

import java.rmi.RemoteException;

public class DLFileEntryServiceSoap {
	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap addFileEntry(
		long folderId, java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		byte[] bytes, com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileEntry returnValue = DLFileEntryServiceUtil.addFileEntry(folderId,
					name, title, description, extraSettings, bytes,
					serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFileEntry(long folderId, java.lang.String name)
		throws RemoteException {
		try {
			DLFileEntryServiceUtil.deleteFileEntry(folderId, name);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFileEntry(long folderId, java.lang.String name,
		double version) throws RemoteException {
		try {
			DLFileEntryServiceUtil.deleteFileEntry(folderId, name, version);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFileEntryByTitle(long folderId,
		java.lang.String titleWithExtension) throws RemoteException {
		try {
			DLFileEntryServiceUtil.deleteFileEntryByTitle(folderId,
				titleWithExtension);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap[] getFileEntries(
		long folderId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> returnValue =
				DLFileEntryServiceUtil.getFileEntries(folderId);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap getFileEntry(
		long folderId, java.lang.String name) throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileEntry returnValue = DLFileEntryServiceUtil.getFileEntry(folderId,
					name);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap getFileEntryByTitle(
		long folderId, java.lang.String titleWithExtension)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileEntry returnValue = DLFileEntryServiceUtil.getFileEntryByTitle(folderId,
					titleWithExtension);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasFileEntryLock(long folderId, java.lang.String name)
		throws RemoteException {
		try {
			boolean returnValue = DLFileEntryServiceUtil.hasFileEntryLock(folderId,
					name);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.lock.model.Lock lockFileEntry(long folderId,
		java.lang.String name) throws RemoteException {
		try {
			com.liferay.lock.model.Lock returnValue = DLFileEntryServiceUtil.lockFileEntry(folderId,
					name);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.lock.model.Lock lockFileEntry(long folderId,
		java.lang.String name, java.lang.String owner, long expirationTime)
		throws RemoteException {
		try {
			com.liferay.lock.model.Lock returnValue = DLFileEntryServiceUtil.lockFileEntry(folderId,
					name, owner, expirationTime);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.lock.model.Lock refreshFileEntryLock(
		java.lang.String lockUuid, long expirationTime)
		throws RemoteException {
		try {
			com.liferay.lock.model.Lock returnValue = DLFileEntryServiceUtil.refreshFileEntryLock(lockUuid,
					expirationTime);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unlockFileEntry(long folderId, java.lang.String name)
		throws RemoteException {
		try {
			DLFileEntryServiceUtil.unlockFileEntry(folderId, name);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unlockFileEntry(long folderId, java.lang.String name,
		java.lang.String lockUuid) throws RemoteException {
		try {
			DLFileEntryServiceUtil.unlockFileEntry(folderId, name, lockUuid);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap updateFileEntry(
		long folderId, long newFolderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String extraSettings,
		byte[] bytes, com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileEntry returnValue = DLFileEntryServiceUtil.updateFileEntry(folderId,
					newFolderId, name, sourceFileName, title, description,
					extraSettings, bytes, serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean verifyFileEntryLock(long folderId,
		java.lang.String name, java.lang.String lockUuid)
		throws RemoteException {
		try {
			boolean returnValue = DLFileEntryServiceUtil.verifyFileEntryLock(folderId,
					name, lockUuid);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DLFileEntryServiceSoap.class);
}