/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.documentlibrary.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.documentlibrary.model.DLFileEntrySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.documentlibrary.model.DLFileEntry}, that is translated to a
 * {@link com.liferay.portlet.documentlibrary.model.DLFileEntrySoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileEntryServiceHttp
 * @see       com.liferay.portlet.documentlibrary.model.DLFileEntrySoap
 * @see       com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil
 * @generated
 */
public class DLFileEntryServiceSoap {
	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap addFileEntry(
		long groupId, long folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, java.lang.String extraSettings,
		byte[] bytes, com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileEntry returnValue = DLFileEntryServiceUtil.addFileEntry(groupId,
					folderId, name, title, description, changeLog,
					extraSettings, bytes, serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFileEntry(long groupId, long folderId,
		java.lang.String name) throws RemoteException {
		try {
			DLFileEntryServiceUtil.deleteFileEntry(groupId, folderId, name);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFileEntry(long groupId, long folderId,
		java.lang.String name, java.lang.String version)
		throws RemoteException {
		try {
			DLFileEntryServiceUtil.deleteFileEntry(groupId, folderId, name,
				version);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFileEntryByTitle(long groupId, long folderId,
		java.lang.String titleWithExtension) throws RemoteException {
		try {
			DLFileEntryServiceUtil.deleteFileEntryByTitle(groupId, folderId,
				titleWithExtension);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap[] getFileEntries(
		long groupId, long folderId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> returnValue =
				DLFileEntryServiceUtil.getFileEntries(groupId, folderId);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap[] getFileEntries(
		long groupId, long folderId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> returnValue =
				DLFileEntryServiceUtil.getFileEntries(groupId, folderId, start,
					end);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap[] getFileEntries(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> returnValue =
				DLFileEntryServiceUtil.getFileEntries(groupId, folderId, start,
					end, obc);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFileEntriesCount(long groupId, long folderId)
		throws RemoteException {
		try {
			int returnValue = DLFileEntryServiceUtil.getFileEntriesCount(groupId,
					folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap getFileEntry(
		long groupId, long folderId, java.lang.String name)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileEntry returnValue = DLFileEntryServiceUtil.getFileEntry(groupId,
					folderId, name);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap getFileEntryByTitle(
		long groupId, long folderId, java.lang.String titleWithExtension)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileEntry returnValue = DLFileEntryServiceUtil.getFileEntryByTitle(groupId,
					folderId, titleWithExtension);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap getFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileEntry returnValue = DLFileEntryServiceUtil.getFileEntryByUuidAndGroupId(uuid,
					groupId);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersFileEntriesCount(long groupId,
		Long[] folderIds, int status) throws RemoteException {
		try {
			int returnValue = DLFileEntryServiceUtil.getFoldersFileEntriesCount(groupId,
					ListUtil.toList(folderIds), status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap[] getGroupFileEntries(
		long groupId, long userId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> returnValue =
				DLFileEntryServiceUtil.getGroupFileEntries(groupId, userId,
					start, end);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap[] getGroupFileEntries(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> returnValue =
				DLFileEntryServiceUtil.getGroupFileEntries(groupId, userId,
					start, end, obc);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupFileEntriesCount(long groupId, long userId)
		throws RemoteException {
		try {
			int returnValue = DLFileEntryServiceUtil.getGroupFileEntriesCount(groupId,
					userId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasFileEntryLock(long groupId, long folderId,
		java.lang.String name) throws RemoteException {
		try {
			boolean returnValue = DLFileEntryServiceUtil.hasFileEntryLock(groupId,
					folderId, name);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap moveFileEntry(
		long groupId, long folderId, long newFolderId, java.lang.String name,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileEntry returnValue = DLFileEntryServiceUtil.moveFileEntry(groupId,
					folderId, newFolderId, name, serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unlockFileEntry(long groupId, long folderId,
		java.lang.String name) throws RemoteException {
		try {
			DLFileEntryServiceUtil.unlockFileEntry(groupId, folderId, name);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unlockFileEntry(long groupId, long folderId,
		java.lang.String name, java.lang.String lockUuid)
		throws RemoteException {
		try {
			DLFileEntryServiceUtil.unlockFileEntry(groupId, folderId, name,
				lockUuid);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntrySoap updateFileEntry(
		long groupId, long folderId, java.lang.String name,
		java.lang.String sourceFileName, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		boolean majorVersion, java.lang.String extraSettings, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFileEntry returnValue = DLFileEntryServiceUtil.updateFileEntry(groupId,
					folderId, name, sourceFileName, title, description,
					changeLog, majorVersion, extraSettings, bytes,
					serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean verifyFileEntryLock(long groupId, long folderId,
		java.lang.String name, java.lang.String lockUuid)
		throws RemoteException {
		try {
			boolean returnValue = DLFileEntryServiceUtil.verifyFileEntryLock(groupId,
					folderId, name, lockUuid);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DLFileEntryServiceSoap.class);
}