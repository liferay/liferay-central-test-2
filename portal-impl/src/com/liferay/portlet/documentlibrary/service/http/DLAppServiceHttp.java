/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link com.liferay.portlet.documentlibrary.service.DLAppServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLAppServiceSoap
 * @see com.liferay.portal.security.auth.HttpPrincipal
 * @see com.liferay.portlet.documentlibrary.service.DLAppServiceUtil
 * @generated
 */
public class DLAppServiceHttp {
	public static com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		java.lang.String sourceFileName, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"addFileEntry", _addFileEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, sourceFileName, mimeType, title,
					description, changeLog, bytes, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		java.lang.String sourceFileName, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"addFileEntry", _addFileEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, sourceFileName, mimeType, title,
					description, changeLog, file, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		java.lang.String sourceFileName, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"addFileEntry", _addFileEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, sourceFileName, mimeType, title,
					description, changeLog, is, size, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut addFileShortcut(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"addFileShortcut", _addFileShortcutParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, toFileEntryId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.documentlibrary.model.DLFileShortcut)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder addFolder(
		HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"addFolder", _addFolderParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, parentFolderId, name, description,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry addTempFileEntry(
		HttpPrincipal httpPrincipal, long groupId, long folderId,
		java.lang.String fileName, java.lang.String tempFolderName,
		java.io.File file, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"addTempFileEntry", _addTempFileEntryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, fileName, tempFolderName, file, mimeType);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry addTempFileEntry(
		HttpPrincipal httpPrincipal, long groupId, long folderId,
		java.lang.String fileName, java.lang.String tempFolderName,
		java.io.InputStream inputStream, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"addTempFileEntry", _addTempFileEntryParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, fileName, tempFolderName, inputStream, mimeType);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void cancelCheckOut(HttpPrincipal httpPrincipal,
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"cancelCheckOut", _cancelCheckOutParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void checkInFileEntry(HttpPrincipal httpPrincipal,
		long fileEntryId, boolean majorVersion, java.lang.String changeLog,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"checkInFileEntry", _checkInFileEntryParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId, majorVersion, changeLog, serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void checkInFileEntry(HttpPrincipal httpPrincipal,
		long fileEntryId, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"checkInFileEntry", _checkInFileEntryParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId, lockUuid);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void checkInFileEntry(HttpPrincipal httpPrincipal,
		long fileEntryId, java.lang.String lockUuid,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"checkInFileEntry", _checkInFileEntryParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId, lockUuid, serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void checkOutFileEntry(HttpPrincipal httpPrincipal,
		long fileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"checkOutFileEntry", _checkOutFileEntryParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId, serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry checkOutFileEntry(
		HttpPrincipal httpPrincipal, long fileEntryId, java.lang.String owner,
		long expirationTime,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"checkOutFileEntry", _checkOutFileEntryParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId, owner, expirationTime, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder copyFolder(
		HttpPrincipal httpPrincipal, long repositoryId, long sourceFolderId,
		long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"copyFolder", _copyFolderParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, sourceFolderId, parentFolderId, name,
					description, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFileEntry(HttpPrincipal httpPrincipal,
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"deleteFileEntry", _deleteFileEntryParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFileEntryByTitle(HttpPrincipal httpPrincipal,
		long repositoryId, long folderId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"deleteFileEntryByTitle",
					_deleteFileEntryByTitleParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, title);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFileShortcut(HttpPrincipal httpPrincipal,
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"deleteFileShortcut", _deleteFileShortcutParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileShortcutId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFileVersion(HttpPrincipal httpPrincipal,
		long fileEntryId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"deleteFileVersion", _deleteFileVersionParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId, version);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFolder(HttpPrincipal httpPrincipal, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"deleteFolder", _deleteFolderParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(methodKey, folderId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFolder(HttpPrincipal httpPrincipal,
		long repositoryId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"deleteFolder", _deleteFolderParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, parentFolderId, name);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteTempFileEntry(HttpPrincipal httpPrincipal,
		long groupId, long folderId, java.lang.String fileName,
		java.lang.String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"deleteTempFileEntry", _deleteTempFileEntryParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, fileName, tempFolderName);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFileEntries", _getFileEntriesParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFileEntries", _getFileEntriesParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.repository.model.FileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFileEntries", _getFileEntriesParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFileEntries", _getFileEntriesParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, fileEntryTypeId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		long fileEntryTypeId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFileEntries", _getFileEntriesParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, fileEntryTypeId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		long fileEntryTypeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.repository.model.FileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFileEntries", _getFileEntriesParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, fileEntryTypeId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		java.lang.String[] mimeTypes)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFileEntries", _getFileEntriesParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, mimeTypes);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		java.lang.String[] mimeTypes, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.repository.model.FileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFileEntries", _getFileEntriesParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, mimeTypes, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFileEntriesAndFileShortcuts",
					_getFileEntriesAndFileShortcutsParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, status, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<java.lang.Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFileEntriesAndFileShortcutsCount(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		int status) throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFileEntriesAndFileShortcutsCount",
					_getFileEntriesAndFileShortcutsCountParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFileEntriesAndFileShortcutsCount(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		int status, java.lang.String[] mimeTypes)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFileEntriesAndFileShortcutsCount",
					_getFileEntriesAndFileShortcutsCountParameterTypes31);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, status, mimeTypes);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFileEntriesCount(HttpPrincipal httpPrincipal,
		long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFileEntriesCount", _getFileEntriesCountParameterTypes32);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFileEntriesCount(HttpPrincipal httpPrincipal,
		long repositoryId, long folderId, long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFileEntriesCount", _getFileEntriesCountParameterTypes33);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, fileEntryTypeId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFileEntriesCount(HttpPrincipal httpPrincipal,
		long repositoryId, long folderId, java.lang.String[] mimeTypes)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFileEntriesCount", _getFileEntriesCountParameterTypes34);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, mimeTypes);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry getFileEntry(
		HttpPrincipal httpPrincipal, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFileEntry", _getFileEntryParameterTypes35);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry getFileEntry(
		HttpPrincipal httpPrincipal, long groupId, long folderId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFileEntry", _getFileEntryParameterTypes36);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, title);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry getFileEntryByUuidAndGroupId(
		HttpPrincipal httpPrincipal, java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFileEntryByUuidAndGroupId",
					_getFileEntryByUuidAndGroupIdParameterTypes37);

			MethodHandler methodHandler = new MethodHandler(methodKey, uuid,
					groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut getFileShortcut(
		HttpPrincipal httpPrincipal, long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFileShortcut", _getFileShortcutParameterTypes38);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileShortcutId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.documentlibrary.model.DLFileShortcut)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder getFolder(
		HttpPrincipal httpPrincipal, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFolder", _getFolderParameterTypes39);

			MethodHandler methodHandler = new MethodHandler(methodKey, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder getFolder(
		HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFolder", _getFolderParameterTypes40);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, parentFolderId, name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFolders", _getFoldersParameterTypes41);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, parentFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
		boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFolders", _getFoldersParameterTypes42);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, parentFolderId, includeMountFolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
		boolean includeMountFolders, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFolders", _getFoldersParameterTypes43);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, parentFolderId, includeMountFolders, start,
					end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
		boolean includeMountFolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.repository.model.Folder> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFolders", _getFoldersParameterTypes44);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, parentFolderId, includeMountFolders, start,
					end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
		int status, boolean includeMountFolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.repository.model.Folder> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFolders", _getFoldersParameterTypes45);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, parentFolderId, status, includeMountFolders,
					start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFolders", _getFoldersParameterTypes46);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, parentFolderId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.Folder> getFolders(
		HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.repository.model.Folder> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFolders", _getFoldersParameterTypes47);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, parentFolderId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		int status, boolean includeMountFolders, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFoldersAndFileEntriesAndFileShortcuts",
					_getFoldersAndFileEntriesAndFileShortcutsParameterTypes48);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, status, includeMountFolders, start,
					end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<java.lang.Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		int status, boolean includeMountFolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFoldersAndFileEntriesAndFileShortcuts",
					_getFoldersAndFileEntriesAndFileShortcutsParameterTypes49);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, status, includeMountFolders, start,
					end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<java.lang.Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		int status, java.lang.String[] mimeTypes, boolean includeMountFolders,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFoldersAndFileEntriesAndFileShortcuts",
					_getFoldersAndFileEntriesAndFileShortcutsParameterTypes50);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, status, mimeTypes,
					includeMountFolders, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<java.lang.Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		int status, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFoldersAndFileEntriesAndFileShortcutsCount",
					_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes51);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, status, includeMountFolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		int status, java.lang.String[] mimeTypes, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFoldersAndFileEntriesAndFileShortcutsCount",
					_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes52);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, status, mimeTypes,
					includeMountFolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersCount(HttpPrincipal httpPrincipal,
		long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFoldersCount", _getFoldersCountParameterTypes53);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, parentFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersCount(HttpPrincipal httpPrincipal,
		long repositoryId, long parentFolderId, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFoldersCount", _getFoldersCountParameterTypes54);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, parentFolderId, includeMountFolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersCount(HttpPrincipal httpPrincipal,
		long repositoryId, long parentFolderId, int status,
		boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFoldersCount", _getFoldersCountParameterTypes55);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, parentFolderId, status, includeMountFolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersFileEntriesCount(HttpPrincipal httpPrincipal,
		long repositoryId, java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getFoldersFileEntriesCount",
					_getFoldersFileEntriesCountParameterTypes56);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderIds, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		HttpPrincipal httpPrincipal, long groupId, long userId, int start,
		int end) throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getGroupFileEntries", _getGroupFileEntriesParameterTypes57);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		HttpPrincipal httpPrincipal, long groupId, long userId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.repository.model.FileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getGroupFileEntries", _getGroupFileEntriesParameterTypes58);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		HttpPrincipal httpPrincipal, long groupId, long userId,
		long rootFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getGroupFileEntries", _getGroupFileEntriesParameterTypes59);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId, rootFolderId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		HttpPrincipal httpPrincipal, long groupId, long userId,
		long rootFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.repository.model.FileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getGroupFileEntries", _getGroupFileEntriesParameterTypes60);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId, rootFolderId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getGroupFileEntries(
		HttpPrincipal httpPrincipal, long groupId, long userId,
		long rootFolderId, java.lang.String[] mimeTypes, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.repository.model.FileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getGroupFileEntries", _getGroupFileEntriesParameterTypes61);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId, rootFolderId, mimeTypes, status, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupFileEntriesCount(HttpPrincipal httpPrincipal,
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getGroupFileEntriesCount",
					_getGroupFileEntriesCountParameterTypes62);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupFileEntriesCount(HttpPrincipal httpPrincipal,
		long groupId, long userId, long rootFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getGroupFileEntriesCount",
					_getGroupFileEntriesCountParameterTypes63);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId, rootFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupFileEntriesCount(HttpPrincipal httpPrincipal,
		long groupId, long userId, long rootFolderId,
		java.lang.String[] mimeTypes, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getGroupFileEntriesCount",
					_getGroupFileEntriesCountParameterTypes64);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId, rootFolderId, mimeTypes, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.Folder> getMountFolders(
		HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getMountFolders", _getMountFoldersParameterTypes65);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, parentFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.Folder> getMountFolders(
		HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getMountFolders", _getMountFoldersParameterTypes66);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, parentFolderId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.Folder> getMountFolders(
		HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.repository.model.Folder> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getMountFolders", _getMountFoldersParameterTypes67);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, parentFolderId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getMountFoldersCount(HttpPrincipal httpPrincipal,
		long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getMountFoldersCount",
					_getMountFoldersCountParameterTypes68);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, parentFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void getSubfolderIds(HttpPrincipal httpPrincipal,
		long repositoryId, java.util.List<java.lang.Long> folderIds,
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getSubfolderIds", _getSubfolderIdsParameterTypes69);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderIds, folderId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<java.lang.Long> getSubfolderIds(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getSubfolderIds", _getSubfolderIdsParameterTypes70);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<java.lang.Long>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<java.lang.Long> getSubfolderIds(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		boolean recurse)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getSubfolderIds", _getSubfolderIdsParameterTypes71);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, recurse);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<java.lang.Long>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.lang.String[] getTempFileEntryNames(
		HttpPrincipal httpPrincipal, long groupId, long folderId,
		java.lang.String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"getTempFileEntryNames",
					_getTempFileEntryNamesParameterTypes72);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, tempFolderName);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.lang.String[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.Lock lockFileEntry(
		HttpPrincipal httpPrincipal, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"lockFileEntry", _lockFileEntryParameterTypes73);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.model.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.Lock lockFileEntry(
		HttpPrincipal httpPrincipal, long fileEntryId, java.lang.String owner,
		long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"lockFileEntry", _lockFileEntryParameterTypes74);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId, owner, expirationTime);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.model.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.Lock lockFolder(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"lockFolder", _lockFolderParameterTypes75);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.model.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.Lock lockFolder(
		HttpPrincipal httpPrincipal, long repositoryId, long folderId,
		java.lang.String owner, boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"lockFolder", _lockFolderParameterTypes76);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, owner, inheritable, expirationTime);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.model.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry moveFileEntry(
		HttpPrincipal httpPrincipal, long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"moveFileEntry", _moveFileEntryParameterTypes77);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId, newFolderId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryFromTrash(
		HttpPrincipal httpPrincipal, long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"moveFileEntryFromTrash",
					_moveFileEntryFromTrashParameterTypes78);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId, newFolderId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryToTrash(
		HttpPrincipal httpPrincipal, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"moveFileEntryToTrash",
					_moveFileEntryToTrashParameterTypes79);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut moveFileShortcutFromTrash(
		HttpPrincipal httpPrincipal, long fileShortcutId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"moveFileShortcutFromTrash",
					_moveFileShortcutFromTrashParameterTypes80);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileShortcutId, newFolderId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.documentlibrary.model.DLFileShortcut)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut moveFileShortcutToTrash(
		HttpPrincipal httpPrincipal, long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"moveFileShortcutToTrash",
					_moveFileShortcutToTrashParameterTypes81);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileShortcutId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.documentlibrary.model.DLFileShortcut)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder moveFolder(
		HttpPrincipal httpPrincipal, long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"moveFolder", _moveFolderParameterTypes82);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					folderId, parentFolderId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder moveFolderFromTrash(
		HttpPrincipal httpPrincipal, long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"moveFolderFromTrash", _moveFolderFromTrashParameterTypes83);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					folderId, parentFolderId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder moveFolderToTrash(
		HttpPrincipal httpPrincipal, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"moveFolderToTrash", _moveFolderToTrashParameterTypes84);

			MethodHandler methodHandler = new MethodHandler(methodKey, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.Lock refreshFileEntryLock(
		HttpPrincipal httpPrincipal, java.lang.String lockUuid, long companyId,
		long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"refreshFileEntryLock",
					_refreshFileEntryLockParameterTypes85);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					lockUuid, companyId, expirationTime);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.model.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.Lock refreshFolderLock(
		HttpPrincipal httpPrincipal, java.lang.String lockUuid, long companyId,
		long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"refreshFolderLock", _refreshFolderLockParameterTypes86);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					lockUuid, companyId, expirationTime);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.model.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void restoreFileEntryFromTrash(HttpPrincipal httpPrincipal,
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"restoreFileEntryFromTrash",
					_restoreFileEntryFromTrashParameterTypes87);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void restoreFileShortcutFromTrash(
		HttpPrincipal httpPrincipal, long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"restoreFileShortcutFromTrash",
					_restoreFileShortcutFromTrashParameterTypes88);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileShortcutId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void restoreFolderFromTrash(HttpPrincipal httpPrincipal,
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"restoreFolderFromTrash",
					_restoreFolderFromTrashParameterTypes89);

			MethodHandler methodHandler = new MethodHandler(methodKey, folderId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void revertFileEntry(HttpPrincipal httpPrincipal,
		long fileEntryId, java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"revertFileEntry", _revertFileEntryParameterTypes90);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId, version, serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.search.Hits search(
		HttpPrincipal httpPrincipal, long repositoryId, long creatorUserId,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"search", _searchParameterTypes91);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, creatorUserId, status, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.search.Hits search(
		HttpPrincipal httpPrincipal, long repositoryId, long creatorUserId,
		long folderId, java.lang.String[] mimeTypes, int status, int start,
		int end) throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"search", _searchParameterTypes92);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, creatorUserId, folderId, mimeTypes, status,
					start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.search.Hits search(
		HttpPrincipal httpPrincipal, long repositoryId,
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.search.SearchException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"search", _searchParameterTypes93);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, searchContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.search.SearchException) {
					throw (com.liferay.portal.kernel.search.SearchException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.search.Hits search(
		HttpPrincipal httpPrincipal, long repositoryId,
		com.liferay.portal.kernel.search.SearchContext searchContext,
		com.liferay.portal.kernel.search.Query query)
		throws com.liferay.portal.kernel.search.SearchException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"search", _searchParameterTypes94);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, searchContext, query);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.search.SearchException) {
					throw (com.liferay.portal.kernel.search.SearchException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void subscribeFileEntryType(HttpPrincipal httpPrincipal,
		long groupId, long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"subscribeFileEntryType",
					_subscribeFileEntryTypeParameterTypes95);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					fileEntryTypeId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void subscribeFolder(HttpPrincipal httpPrincipal,
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"subscribeFolder", _subscribeFolderParameterTypes96);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unlockFileEntry(HttpPrincipal httpPrincipal,
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"unlockFileEntry", _unlockFileEntryParameterTypes97);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unlockFileEntry(HttpPrincipal httpPrincipal,
		long fileEntryId, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"unlockFileEntry", _unlockFileEntryParameterTypes98);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId, lockUuid);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unlockFolder(HttpPrincipal httpPrincipal,
		long repositoryId, long folderId, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"unlockFolder", _unlockFolderParameterTypes99);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, lockUuid);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unlockFolder(HttpPrincipal httpPrincipal,
		long repositoryId, long parentFolderId, java.lang.String name,
		java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"unlockFolder", _unlockFolderParameterTypes100);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, parentFolderId, name, lockUuid);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsubscribeFileEntryType(HttpPrincipal httpPrincipal,
		long groupId, long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"unsubscribeFileEntryType",
					_unsubscribeFileEntryTypeParameterTypes101);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					fileEntryTypeId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsubscribeFolder(HttpPrincipal httpPrincipal,
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"unsubscribeFolder", _unsubscribeFolderParameterTypes102);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		HttpPrincipal httpPrincipal, long fileEntryId,
		java.lang.String sourceFileName, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion, byte[] bytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"updateFileEntry", _updateFileEntryParameterTypes103);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId, sourceFileName, mimeType, title, description,
					changeLog, majorVersion, bytes, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		HttpPrincipal httpPrincipal, long fileEntryId,
		java.lang.String sourceFileName, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion, java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"updateFileEntry", _updateFileEntryParameterTypes104);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId, sourceFileName, mimeType, title, description,
					changeLog, majorVersion, file, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		HttpPrincipal httpPrincipal, long fileEntryId,
		java.lang.String sourceFileName, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion,
		java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"updateFileEntry", _updateFileEntryParameterTypes105);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId, sourceFileName, mimeType, title, description,
					changeLog, majorVersion, is, size, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry updateFileEntryAndCheckIn(
		HttpPrincipal httpPrincipal, long fileEntryId,
		java.lang.String sourceFileName, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion, java.io.File file,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"updateFileEntryAndCheckIn",
					_updateFileEntryAndCheckInParameterTypes106);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId, sourceFileName, mimeType, title, description,
					changeLog, majorVersion, file, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry updateFileEntryAndCheckIn(
		HttpPrincipal httpPrincipal, long fileEntryId,
		java.lang.String sourceFileName, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String changeLog, boolean majorVersion,
		java.io.InputStream is, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"updateFileEntryAndCheckIn",
					_updateFileEntryAndCheckInParameterTypes107);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId, sourceFileName, mimeType, title, description,
					changeLog, majorVersion, is, size, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut updateFileShortcut(
		HttpPrincipal httpPrincipal, long fileShortcutId, long folderId,
		long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"updateFileShortcut", _updateFileShortcutParameterTypes108);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileShortcutId, folderId, toFileEntryId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.documentlibrary.model.DLFileShortcut)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder updateFolder(
		HttpPrincipal httpPrincipal, long folderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"updateFolder", _updateFolderParameterTypes109);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					folderId, name, description, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean verifyFileEntryCheckOut(HttpPrincipal httpPrincipal,
		long repositoryId, long fileEntryId, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"verifyFileEntryCheckOut",
					_verifyFileEntryCheckOutParameterTypes110);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, fileEntryId, lockUuid);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean verifyFileEntryLock(HttpPrincipal httpPrincipal,
		long repositoryId, long fileEntryId, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"verifyFileEntryLock", _verifyFileEntryLockParameterTypes111);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, fileEntryId, lockUuid);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean verifyInheritableLock(HttpPrincipal httpPrincipal,
		long repositoryId, long folderId, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLAppServiceUtil.class,
					"verifyInheritableLock",
					_verifyInheritableLockParameterTypes112);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					repositoryId, folderId, lockUuid);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DLAppServiceHttp.class);
	private static final Class<?>[] _addFileEntryParameterTypes0 = new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, byte[].class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _addFileEntryParameterTypes1 = new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, java.io.File.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _addFileEntryParameterTypes2 = new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.io.InputStream.class, long.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _addFileShortcutParameterTypes3 = new Class[] {
			long.class, long.class, long.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _addFolderParameterTypes4 = new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _addTempFileEntryParameterTypes5 = new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class, java.io.File.class, java.lang.String.class
		};
	private static final Class<?>[] _addTempFileEntryParameterTypes6 = new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class, java.io.InputStream.class,
			java.lang.String.class
		};
	private static final Class<?>[] _cancelCheckOutParameterTypes7 = new Class[] {
			long.class
		};
	private static final Class<?>[] _checkInFileEntryParameterTypes8 = new Class[] {
			long.class, boolean.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _checkInFileEntryParameterTypes9 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _checkInFileEntryParameterTypes10 = new Class[] {
			long.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _checkOutFileEntryParameterTypes11 = new Class[] {
			long.class, com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _checkOutFileEntryParameterTypes12 = new Class[] {
			long.class, java.lang.String.class, long.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _copyFolderParameterTypes13 = new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteFileEntryParameterTypes14 = new Class[] {
			long.class
		};
	private static final Class<?>[] _deleteFileEntryByTitleParameterTypes15 = new Class[] {
			long.class, long.class, java.lang.String.class
		};
	private static final Class<?>[] _deleteFileShortcutParameterTypes16 = new Class[] {
			long.class
		};
	private static final Class<?>[] _deleteFileVersionParameterTypes17 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _deleteFolderParameterTypes18 = new Class[] {
			long.class
		};
	private static final Class<?>[] _deleteFolderParameterTypes19 = new Class[] {
			long.class, long.class, java.lang.String.class
		};
	private static final Class<?>[] _deleteTempFileEntryParameterTypes20 = new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class
		};
	private static final Class<?>[] _getFileEntriesParameterTypes21 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getFileEntriesParameterTypes22 = new Class[] {
			long.class, long.class, int.class, int.class
		};
	private static final Class<?>[] _getFileEntriesParameterTypes23 = new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFileEntriesParameterTypes24 = new Class[] {
			long.class, long.class, long.class
		};
	private static final Class<?>[] _getFileEntriesParameterTypes25 = new Class[] {
			long.class, long.class, long.class, int.class, int.class
		};
	private static final Class<?>[] _getFileEntriesParameterTypes26 = new Class[] {
			long.class, long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFileEntriesParameterTypes27 = new Class[] {
			long.class, long.class, java.lang.String[].class
		};
	private static final Class<?>[] _getFileEntriesParameterTypes28 = new Class[] {
			long.class, long.class, java.lang.String[].class, int.class,
			int.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFileEntriesAndFileShortcutsParameterTypes29 =
		new Class[] { long.class, long.class, int.class, int.class, int.class };
	private static final Class<?>[] _getFileEntriesAndFileShortcutsCountParameterTypes30 =
		new Class[] { long.class, long.class, int.class };
	private static final Class<?>[] _getFileEntriesAndFileShortcutsCountParameterTypes31 =
		new Class[] { long.class, long.class, int.class, java.lang.String[].class };
	private static final Class<?>[] _getFileEntriesCountParameterTypes32 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getFileEntriesCountParameterTypes33 = new Class[] {
			long.class, long.class, long.class
		};
	private static final Class<?>[] _getFileEntriesCountParameterTypes34 = new Class[] {
			long.class, long.class, java.lang.String[].class
		};
	private static final Class<?>[] _getFileEntryParameterTypes35 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getFileEntryParameterTypes36 = new Class[] {
			long.class, long.class, java.lang.String.class
		};
	private static final Class<?>[] _getFileEntryByUuidAndGroupIdParameterTypes37 =
		new Class[] { java.lang.String.class, long.class };
	private static final Class<?>[] _getFileShortcutParameterTypes38 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getFolderParameterTypes39 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getFolderParameterTypes40 = new Class[] {
			long.class, long.class, java.lang.String.class
		};
	private static final Class<?>[] _getFoldersParameterTypes41 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getFoldersParameterTypes42 = new Class[] {
			long.class, long.class, boolean.class
		};
	private static final Class<?>[] _getFoldersParameterTypes43 = new Class[] {
			long.class, long.class, boolean.class, int.class, int.class
		};
	private static final Class<?>[] _getFoldersParameterTypes44 = new Class[] {
			long.class, long.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFoldersParameterTypes45 = new Class[] {
			long.class, long.class, int.class, boolean.class, int.class,
			int.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFoldersParameterTypes46 = new Class[] {
			long.class, long.class, int.class, int.class
		};
	private static final Class<?>[] _getFoldersParameterTypes47 = new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFoldersAndFileEntriesAndFileShortcutsParameterTypes48 =
		new Class[] {
			long.class, long.class, int.class, boolean.class, int.class,
			int.class
		};
	private static final Class<?>[] _getFoldersAndFileEntriesAndFileShortcutsParameterTypes49 =
		new Class[] {
			long.class, long.class, int.class, boolean.class, int.class,
			int.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFoldersAndFileEntriesAndFileShortcutsParameterTypes50 =
		new Class[] {
			long.class, long.class, int.class, java.lang.String[].class,
			boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes51 =
		new Class[] { long.class, long.class, int.class, boolean.class };
	private static final Class<?>[] _getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes52 =
		new Class[] {
			long.class, long.class, int.class, java.lang.String[].class,
			boolean.class
		};
	private static final Class<?>[] _getFoldersCountParameterTypes53 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getFoldersCountParameterTypes54 = new Class[] {
			long.class, long.class, boolean.class
		};
	private static final Class<?>[] _getFoldersCountParameterTypes55 = new Class[] {
			long.class, long.class, int.class, boolean.class
		};
	private static final Class<?>[] _getFoldersFileEntriesCountParameterTypes56 = new Class[] {
			long.class, java.util.List.class, int.class
		};
	private static final Class<?>[] _getGroupFileEntriesParameterTypes57 = new Class[] {
			long.class, long.class, int.class, int.class
		};
	private static final Class<?>[] _getGroupFileEntriesParameterTypes58 = new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupFileEntriesParameterTypes59 = new Class[] {
			long.class, long.class, long.class, int.class, int.class
		};
	private static final Class<?>[] _getGroupFileEntriesParameterTypes60 = new Class[] {
			long.class, long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupFileEntriesParameterTypes61 = new Class[] {
			long.class, long.class, long.class, java.lang.String[].class,
			int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupFileEntriesCountParameterTypes62 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getGroupFileEntriesCountParameterTypes63 = new Class[] {
			long.class, long.class, long.class
		};
	private static final Class<?>[] _getGroupFileEntriesCountParameterTypes64 = new Class[] {
			long.class, long.class, long.class, java.lang.String[].class,
			int.class
		};
	private static final Class<?>[] _getMountFoldersParameterTypes65 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getMountFoldersParameterTypes66 = new Class[] {
			long.class, long.class, int.class, int.class
		};
	private static final Class<?>[] _getMountFoldersParameterTypes67 = new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getMountFoldersCountParameterTypes68 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getSubfolderIdsParameterTypes69 = new Class[] {
			long.class, java.util.List.class, long.class
		};
	private static final Class<?>[] _getSubfolderIdsParameterTypes70 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getSubfolderIdsParameterTypes71 = new Class[] {
			long.class, long.class, boolean.class
		};
	private static final Class<?>[] _getTempFileEntryNamesParameterTypes72 = new Class[] {
			long.class, long.class, java.lang.String.class
		};
	private static final Class<?>[] _lockFileEntryParameterTypes73 = new Class[] {
			long.class
		};
	private static final Class<?>[] _lockFileEntryParameterTypes74 = new Class[] {
			long.class, java.lang.String.class, long.class
		};
	private static final Class<?>[] _lockFolderParameterTypes75 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _lockFolderParameterTypes76 = new Class[] {
			long.class, long.class, java.lang.String.class, boolean.class,
			long.class
		};
	private static final Class<?>[] _moveFileEntryParameterTypes77 = new Class[] {
			long.class, long.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _moveFileEntryFromTrashParameterTypes78 = new Class[] {
			long.class, long.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _moveFileEntryToTrashParameterTypes79 = new Class[] {
			long.class
		};
	private static final Class<?>[] _moveFileShortcutFromTrashParameterTypes80 = new Class[] {
			long.class, long.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _moveFileShortcutToTrashParameterTypes81 = new Class[] {
			long.class
		};
	private static final Class<?>[] _moveFolderParameterTypes82 = new Class[] {
			long.class, long.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _moveFolderFromTrashParameterTypes83 = new Class[] {
			long.class, long.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _moveFolderToTrashParameterTypes84 = new Class[] {
			long.class
		};
	private static final Class<?>[] _refreshFileEntryLockParameterTypes85 = new Class[] {
			java.lang.String.class, long.class, long.class
		};
	private static final Class<?>[] _refreshFolderLockParameterTypes86 = new Class[] {
			java.lang.String.class, long.class, long.class
		};
	private static final Class<?>[] _restoreFileEntryFromTrashParameterTypes87 = new Class[] {
			long.class
		};
	private static final Class<?>[] _restoreFileShortcutFromTrashParameterTypes88 =
		new Class[] { long.class };
	private static final Class<?>[] _restoreFolderFromTrashParameterTypes89 = new Class[] {
			long.class
		};
	private static final Class<?>[] _revertFileEntryParameterTypes90 = new Class[] {
			long.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _searchParameterTypes91 = new Class[] {
			long.class, long.class, int.class, int.class, int.class
		};
	private static final Class<?>[] _searchParameterTypes92 = new Class[] {
			long.class, long.class, long.class, java.lang.String[].class,
			int.class, int.class, int.class
		};
	private static final Class<?>[] _searchParameterTypes93 = new Class[] {
			long.class, com.liferay.portal.kernel.search.SearchContext.class
		};
	private static final Class<?>[] _searchParameterTypes94 = new Class[] {
			long.class, com.liferay.portal.kernel.search.SearchContext.class,
			com.liferay.portal.kernel.search.Query.class
		};
	private static final Class<?>[] _subscribeFileEntryTypeParameterTypes95 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _subscribeFolderParameterTypes96 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _unlockFileEntryParameterTypes97 = new Class[] {
			long.class
		};
	private static final Class<?>[] _unlockFileEntryParameterTypes98 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _unlockFolderParameterTypes99 = new Class[] {
			long.class, long.class, java.lang.String.class
		};
	private static final Class<?>[] _unlockFolderParameterTypes100 = new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class
		};
	private static final Class<?>[] _unsubscribeFileEntryTypeParameterTypes101 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _unsubscribeFolderParameterTypes102 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _updateFileEntryParameterTypes103 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, boolean.class, byte[].class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFileEntryParameterTypes104 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, boolean.class, java.io.File.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFileEntryParameterTypes105 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, boolean.class, java.io.InputStream.class,
			long.class, com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFileEntryAndCheckInParameterTypes106 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, boolean.class, java.io.File.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFileEntryAndCheckInParameterTypes107 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, boolean.class, java.io.InputStream.class,
			long.class, com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFileShortcutParameterTypes108 = new Class[] {
			long.class, long.class, long.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFolderParameterTypes109 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _verifyFileEntryCheckOutParameterTypes110 = new Class[] {
			long.class, long.class, java.lang.String.class
		};
	private static final Class<?>[] _verifyFileEntryLockParameterTypes111 = new Class[] {
			long.class, long.class, java.lang.String.class
		};
	private static final Class<?>[] _verifyInheritableLockParameterTypes112 = new Class[] {
			long.class, long.class, java.lang.String.class
		};
}