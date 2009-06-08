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

package com.liferay.documentlibrary.service;

/**
 * <a href="DLServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.documentlibrary.service.DLService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.documentlibrary.service.DLService
 *
 */
public class DLServiceUtil {
	public static void addDirectory(long companyId, long repositoryId,
		java.lang.String dirName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().addDirectory(companyId, repositoryId, dirName);
	}

	public static void addFile(long companyId, java.lang.String portletId,
		long groupId, long repositoryId, java.lang.String fileName,
		long fileEntryId, java.lang.String properties,
		java.util.Date modifiedDate,
		com.liferay.portal.service.ServiceContext serviceContext,
		java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addFile(companyId, portletId, groupId, repositoryId, fileName,
			fileEntryId, properties, modifiedDate, serviceContext, file);
	}

	public static void addFile(long companyId, java.lang.String portletId,
		long groupId, long repositoryId, java.lang.String fileName,
		long fileEntryId, java.lang.String properties,
		java.util.Date modifiedDate,
		com.liferay.portal.service.ServiceContext serviceContext, byte[] bytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addFile(companyId, portletId, groupId, repositoryId, fileName,
			fileEntryId, properties, modifiedDate, serviceContext, bytes);
	}

	public static void deleteDirectory(long companyId,
		java.lang.String portletId, long repositoryId, java.lang.String dirName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteDirectory(companyId, portletId, repositoryId, dirName);
	}

	public static void deleteFile(long companyId, java.lang.String portletId,
		long repositoryId, java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteFile(companyId, portletId, repositoryId, fileName);
	}

	public static void deleteFile(long companyId, java.lang.String portletId,
		long repositoryId, java.lang.String fileName, double versionNumber)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.deleteFile(companyId, portletId, repositoryId, fileName,
			versionNumber);
	}

	public static byte[] getFile(long companyId, long repositoryId,
		java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFile(companyId, repositoryId, fileName);
	}

	public static byte[] getFile(long companyId, long repositoryId,
		java.lang.String fileName, double versionNumber)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getFile(companyId, repositoryId, fileName, versionNumber);
	}

	public static java.lang.String[] getFileNames(long companyId,
		long repositoryId, java.lang.String dirName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFileNames(companyId, repositoryId, dirName);
	}

	public static long getFileSize(long companyId, long repositoryId,
		java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFileSize(companyId, repositoryId, fileName);
	}

	public static void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		getService().reIndex(ids);
	}

	public static void updateFile(long companyId, java.lang.String portletId,
		long groupId, long repositoryId, java.lang.String fileName,
		double versionNumber, java.lang.String sourceFileName,
		long fileEntryId, java.lang.String properties,
		java.util.Date modifiedDate,
		com.liferay.portal.service.ServiceContext serviceContext,
		java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.updateFile(companyId, portletId, groupId, repositoryId, fileName,
			versionNumber, sourceFileName, fileEntryId, properties,
			modifiedDate, serviceContext, file);
	}

	public static void updateFile(long companyId, java.lang.String portletId,
		long groupId, long repositoryId, java.lang.String fileName,
		double versionNumber, java.lang.String sourceFileName,
		long fileEntryId, java.lang.String properties,
		java.util.Date modifiedDate,
		com.liferay.portal.service.ServiceContext serviceContext, byte[] bytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.updateFile(companyId, portletId, groupId, repositoryId, fileName,
			versionNumber, sourceFileName, fileEntryId, properties,
			modifiedDate, serviceContext, bytes);
	}

	public static void updateFile(long companyId, java.lang.String portletId,
		long groupId, long repositoryId, long newRepositoryId,
		java.lang.String fileName, long fileEntryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.updateFile(companyId, portletId, groupId, repositoryId,
			newRepositoryId, fileName, fileEntryId);
	}

	public static DLService getService() {
		if (_service == null) {
			throw new RuntimeException("DLService is not set");
		}

		return _service;
	}

	public void setService(DLService service) {
		_service = service;
	}

	private static DLService _service;
}