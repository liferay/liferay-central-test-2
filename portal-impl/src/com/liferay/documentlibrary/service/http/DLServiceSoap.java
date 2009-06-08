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

package com.liferay.documentlibrary.service.http;

import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * <a href="DLServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a SOAP utility for the
 * <code>com.liferay.documentlibrary.service.DLServiceUtil</code> service
 * utility. The static methods of this class calls the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.documentlibrary.model.DLSoap</code>. If the method in the
 * service utility returns a <code>com.liferay.documentlibrary.model.DL</code>,
 * that is translated to a <code>com.liferay.documentlibrary.model.DLSoap</code>.
 * Methods that SOAP cannot safely wire are skipped.
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
 * <code>tunnel.servlet.hosts.allowed</code> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.documentlibrary.model.DLSoap
 * @see com.liferay.documentlibrary.service.DLServiceUtil
 * @see com.liferay.documentlibrary.service.http.DLServiceHttp
 *
 */
public class DLServiceSoap {
	public static void addDirectory(long companyId, long repositoryId,
		java.lang.String dirName) throws RemoteException {
		try {
			DLServiceUtil.addDirectory(companyId, repositoryId, dirName);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void addFile(long companyId, java.lang.String portletId,
		long groupId, long repositoryId, java.lang.String fileName,
		long fileEntryId, java.lang.String properties,
		java.util.Date modifiedDate,
		com.liferay.portal.service.ServiceContext serviceContext, byte[] bytes)
		throws RemoteException {
		try {
			DLServiceUtil.addFile(companyId, portletId, groupId, repositoryId,
				fileName, fileEntryId, properties, modifiedDate,
				serviceContext, bytes);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteDirectory(long companyId,
		java.lang.String portletId, long repositoryId, java.lang.String dirName)
		throws RemoteException {
		try {
			DLServiceUtil.deleteDirectory(companyId, portletId, repositoryId,
				dirName);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFile(long companyId, java.lang.String portletId,
		long repositoryId, java.lang.String fileName) throws RemoteException {
		try {
			DLServiceUtil.deleteFile(companyId, portletId, repositoryId,
				fileName);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFile(long companyId, java.lang.String portletId,
		long repositoryId, java.lang.String fileName, double versionNumber)
		throws RemoteException {
		try {
			DLServiceUtil.deleteFile(companyId, portletId, repositoryId,
				fileName, versionNumber);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static byte[] getFile(long companyId, long repositoryId,
		java.lang.String fileName) throws RemoteException {
		try {
			byte[] returnValue = DLServiceUtil.getFile(companyId, repositoryId,
					fileName);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static byte[] getFile(long companyId, long repositoryId,
		java.lang.String fileName, double versionNumber)
		throws RemoteException {
		try {
			byte[] returnValue = DLServiceUtil.getFile(companyId, repositoryId,
					fileName, versionNumber);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String[] getFileNames(long companyId,
		long repositoryId, java.lang.String dirName) throws RemoteException {
		try {
			java.lang.String[] returnValue = DLServiceUtil.getFileNames(companyId,
					repositoryId, dirName);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long getFileSize(long companyId, long repositoryId,
		java.lang.String fileName) throws RemoteException {
		try {
			long returnValue = DLServiceUtil.getFileSize(companyId,
					repositoryId, fileName);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void reIndex(java.lang.String[] ids)
		throws RemoteException {
		try {
			DLServiceUtil.reIndex(ids);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateFile(long companyId, java.lang.String portletId,
		long groupId, long repositoryId, java.lang.String fileName,
		double versionNumber, java.lang.String sourceFileName,
		long fileEntryId, java.lang.String properties,
		java.util.Date modifiedDate,
		com.liferay.portal.service.ServiceContext serviceContext, byte[] bytes)
		throws RemoteException {
		try {
			DLServiceUtil.updateFile(companyId, portletId, groupId,
				repositoryId, fileName, versionNumber, sourceFileName,
				fileEntryId, properties, modifiedDate, serviceContext, bytes);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateFile(long companyId, java.lang.String portletId,
		long groupId, long repositoryId, long newRepositoryId,
		java.lang.String fileName, long fileEntryId) throws RemoteException {
		try {
			DLServiceUtil.updateFile(companyId, portletId, groupId,
				repositoryId, newRepositoryId, fileName, fileEntryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DLServiceSoap.class);
}