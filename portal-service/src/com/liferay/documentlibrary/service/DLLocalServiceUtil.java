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
 * <a href="DLLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.documentlibrary.service.DLLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.documentlibrary.service.DLLocalService
 *
 */
public class DLLocalServiceUtil {
	public static void addFile(long companyId, java.lang.String portletId,
		long groupId, long repositoryId, java.lang.String fileName,
		long fileEntryId, java.lang.String properties,
		java.util.Date modifiedDate,
		com.liferay.portal.service.ServiceContext serviceContext,
		java.io.InputStream is)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addFile(companyId, portletId, groupId, repositoryId, fileName,
			fileEntryId, properties, modifiedDate, serviceContext, is);
	}

	public static void checkRoot(long companyId)
		throws com.liferay.portal.SystemException {
		getService().checkRoot(companyId);
	}

	public static java.io.InputStream getFileAsStream(long companyId,
		long repositoryId, java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFileAsStream(companyId, repositoryId, fileName);
	}

	public static java.io.InputStream getFileAsStream(long companyId,
		long repositoryId, java.lang.String fileName, double versionNumber)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getFileAsStream(companyId, repositoryId, fileName,
			versionNumber);
	}

	public static boolean hasFile(long companyId, long repositoryId,
		java.lang.String fileName, double versionNumber)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .hasFile(companyId, repositoryId, fileName, versionNumber);
	}

	public static void move(java.lang.String srcDir, java.lang.String destDir)
		throws com.liferay.portal.SystemException {
		getService().move(srcDir, destDir);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String portletId, long groupId, long userId,
		long[] repositoryIds, java.lang.String keywords, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService()
				   .search(companyId, portletId, groupId, userId,
			repositoryIds, keywords, start, end);
	}

	public static void updateFile(long companyId, java.lang.String portletId,
		long groupId, long repositoryId, java.lang.String fileName,
		double versionNumber, java.lang.String sourceFileName,
		long fileEntryId, java.lang.String properties,
		java.util.Date modifiedDate,
		com.liferay.portal.service.ServiceContext serviceContext,
		java.io.InputStream is)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.updateFile(companyId, portletId, groupId, repositoryId, fileName,
			versionNumber, sourceFileName, fileEntryId, properties,
			modifiedDate, serviceContext, is);
	}

	public static void validate(java.lang.String fileName, java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().validate(fileName, file);
	}

	public static void validate(java.lang.String fileName, byte[] bytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().validate(fileName, bytes);
	}

	public static void validate(java.lang.String fileName,
		java.io.InputStream is)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().validate(fileName, is);
	}

	public static void validate(java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().validate(fileName);
	}

	public static void validate(java.lang.String fileName,
		java.lang.String sourceFileName, java.io.InputStream is)
		throws com.liferay.portal.PortalException {
		getService().validate(fileName, sourceFileName, is);
	}

	public static DLLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("DLLocalService is not set");
		}

		return _service;
	}

	public void setService(DLLocalService service) {
		_service = service;
	}

	private static DLLocalService _service;
}