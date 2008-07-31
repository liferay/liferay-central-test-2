/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.webform.service.impl;

import java.rmi.RemoteException;

import com.liferay.documentlibrary.DuplicateDirectoryException;
import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.service.DLService;
import com.liferay.documentlibrary.service.DLServiceFactory;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portlet.webform.service.base.WebFormLocalServiceBaseImpl;

public class WebFormLocalServiceImpl extends WebFormLocalServiceBaseImpl {

	public void deleteUploadedFiles(
			long companyId, String portletId)
		throws PortalException, SystemException {

		DLService dlService = DLServiceFactory.getImpl();

		long repositoryId = getRepositoryId();
		String dirName = getDirName(portletId);

		try {
			dlService.deleteDirectory(companyId, portletId, repositoryId, dirName);
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}
	}

	public byte[] retrieveUploadedFile(
			long companyId, String portletId,
			String fileName)
		throws PortalException, SystemException {

		DLService dlService = DLServiceFactory.getImpl();

		long repositoryId = getRepositoryId();
		String dirName = getDirName(portletId);

		try {
			return dlService.getFile(companyId, repositoryId, dirName + "/" + fileName);
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}
	}

	public void storeUploadedFile(
			long companyId, String portletId,
			String fileName, byte[] bytes)
		throws PortalException, SystemException {

		DLService dlService = DLServiceFactory.getImpl();

		long groupId = getGroupId();
		long repositoryId = getRepositoryId();
		String dirName = getDirName(portletId);

		try {
			try {
				dlService.addDirectory(companyId, repositoryId, dirName);
			}
			catch (DuplicateDirectoryException dde) {
			}

			try {
				dlService.addFile(
					companyId, portletId, groupId, repositoryId,
					dirName + "/" + fileName, StringPool.BLANK,
					new String[0], bytes);
			}
			catch (DuplicateFileException dfe) {
				throw new SystemException(dfe);
			}
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}
	}

	protected long getGroupId() {
		return GroupImpl.DEFAULT_PARENT_GROUP_ID;
	}

	protected long getRepositoryId() {
		return CompanyConstants.SYSTEM;
	}

	protected String getDirName(String portletId) {
		return "webform/" + portletId;
	}
}
