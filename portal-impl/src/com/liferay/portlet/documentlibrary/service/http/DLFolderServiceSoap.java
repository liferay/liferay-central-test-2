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

import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="DLFolderServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.documentlibrary.model.DLFolderSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.documentlibrary.model.DLFolder}, that is translated to a
 * {@link com.liferay.portlet.documentlibrary.model.DLFolderSoap}. Methods that SOAP cannot
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
 * @see       DLFolderServiceHttp
 * @see       com.liferay.portlet.documentlibrary.model.DLFolderSoap
 * @see       com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil
 * @generated
 */
public class DLFolderServiceSoap {
	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap addFolder(
		long groupId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFolder returnValue = DLFolderServiceUtil.addFolder(groupId,
					parentFolderId, name, description, serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap copyFolder(
		long groupId, long sourceFolderId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFolder returnValue = DLFolderServiceUtil.copyFolder(groupId,
					sourceFolderId, parentFolderId, name, description,
					serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFolder(long folderId) throws RemoteException {
		try {
			DLFolderServiceUtil.deleteFolder(folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFolder(long groupId, long parentFolderId,
		java.lang.String name) throws RemoteException {
		try {
			DLFolderServiceUtil.deleteFolder(groupId, parentFolderId, name);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap getFolder(
		long folderId) throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFolder returnValue = DLFolderServiceUtil.getFolder(folderId);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFolder returnValue = DLFolderServiceUtil.getFolder(groupId,
					parentFolderId, name);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long getFolderId(long groupId, long parentFolderId,
		java.lang.String name) throws RemoteException {
		try {
			long returnValue = DLFolderServiceUtil.getFolderId(groupId,
					parentFolderId, name);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap[] getFolders(
		long groupId, long parentFolderId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> returnValue =
				DLFolderServiceUtil.getFolders(groupId, parentFolderId);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasInheritableLock(long folderId)
		throws RemoteException {
		try {
			boolean returnValue = DLFolderServiceUtil.hasInheritableLock(folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void reindexSearch(long companyId) throws RemoteException {
		try {
			DLFolderServiceUtil.reindexSearch(companyId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unlockFolder(long groupId, long folderId,
		java.lang.String lockUuid) throws RemoteException {
		try {
			DLFolderServiceUtil.unlockFolder(groupId, folderId, lockUuid);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unlockFolder(long groupId, long parentFolderId,
		java.lang.String name, java.lang.String lockUuid)
		throws RemoteException {
		try {
			DLFolderServiceUtil.unlockFolder(groupId, parentFolderId, name,
				lockUuid);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolderSoap updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.documentlibrary.model.DLFolder returnValue = DLFolderServiceUtil.updateFolder(folderId,
					parentFolderId, name, description, serviceContext);

			return com.liferay.portlet.documentlibrary.model.DLFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean verifyInheritableLock(long folderId,
		java.lang.String lockUuid) throws RemoteException {
		try {
			boolean returnValue = DLFolderServiceUtil.verifyInheritableLock(folderId,
					lockUuid);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DLFolderServiceSoap.class);
}