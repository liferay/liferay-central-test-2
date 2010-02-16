/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.service.ServiceContext;

import java.io.File;
import java.io.InputStream;

import java.util.Date;

/**
 * <a href="DLLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DLLocalServiceUtil {

	public static void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, boolean validateFileExtension, long fileEntryId,
			String properties, Date modifiedDate, ServiceContext serviceContext,
			InputStream is)
		throws PortalException, SystemException {

		getService().addFile(
			companyId, portletId, groupId, repositoryId, fileName,
			validateFileExtension, fileEntryId, properties, modifiedDate,
			serviceContext, is);
	}

	public static void checkRoot(long companyId) throws SystemException {
		getService().checkRoot(companyId);
	}

	public static InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return getService().getFileAsStream(companyId, repositoryId, fileName);
	}

	public static InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			double versionNumber)
		throws PortalException, SystemException {

		return getService().getFileAsStream(
			companyId, repositoryId, fileName, versionNumber);
	}

	public static DLLocalService getService() {
		if (_service == null) {
			_service = (DLLocalService)PortalBeanLocatorUtil.locate(
				DLLocalService.class.getName());
		}

		return _service;
	}

	public static boolean hasFile(
			long companyId, long repositoryId, String fileName,
			double versionNumber)
		throws PortalException, SystemException {

		return getService().hasFile(
			companyId, repositoryId, fileName, versionNumber);
	}

	public static void move(String srcDir, String destDir)
		throws SystemException {

		getService().move(srcDir, destDir);
	}

	public static Hits search(
			long companyId, String portletId, long groupId,
			long userId, long[] repositoryIds, String keywords, int start,
			int end)
		throws SystemException {

		return getService().search(
			companyId, portletId, groupId, userId, repositoryIds, keywords,
			start, end);
	}

	public static void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, boolean validateFileExtension,
			double versionNumber, String sourceFileName, long fileEntryId,
			String properties, Date modifiedDate, ServiceContext serviceContext,
			InputStream is)
		throws PortalException, SystemException {

		getService().updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			validateFileExtension, versionNumber, sourceFileName, fileEntryId,
			properties, modifiedDate, serviceContext, is);
	}

	public static void validate(
			String fileName, boolean validateFileExtension, byte[] bytes)
		throws PortalException, SystemException {

		getService().validate(fileName, validateFileExtension, bytes);
	}

	public static void validate(
			String fileName, boolean validateFileExtension, File file)
		throws PortalException, SystemException {

		getService().validate(fileName, validateFileExtension, file);
	}

	public static void validate(
			String fileName, boolean validateFileExtension, InputStream is)
		throws PortalException, SystemException {

		getService().validate(fileName, validateFileExtension, is);
	}

	public static void validate(
			String fileName, String sourceFileName, InputStream is)
		throws PortalException, SystemException {

		getService().validate(fileName, sourceFileName, is);
	}

	public void setService(DLLocalService service) {
		_service = service;
	}

	private static DLLocalService _service;

}