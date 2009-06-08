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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.Isolation;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;

/**
 * <a href="DLService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.liferay.documentlibrary.service.impl.DLServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.documentlibrary.service.DLServiceUtil
 *
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DLService {
	public void addDirectory(long companyId, long repositoryId,
		java.lang.String dirName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void addFile(long companyId, java.lang.String portletId,
		long groupId, long repositoryId, java.lang.String fileName,
		long fileEntryId, java.lang.String properties,
		java.util.Date modifiedDate,
		com.liferay.portal.service.ServiceContext serviceContext,
		java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void addFile(long companyId, java.lang.String portletId,
		long groupId, long repositoryId, java.lang.String fileName,
		long fileEntryId, java.lang.String properties,
		java.util.Date modifiedDate,
		com.liferay.portal.service.ServiceContext serviceContext, byte[] bytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteDirectory(long companyId, java.lang.String portletId,
		long repositoryId, java.lang.String dirName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteFile(long companyId, java.lang.String portletId,
		long repositoryId, java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteFile(long companyId, java.lang.String portletId,
		long repositoryId, java.lang.String fileName, double versionNumber)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public byte[] getFile(long companyId, long repositoryId,
		java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public byte[] getFile(long companyId, long repositoryId,
		java.lang.String fileName, double versionNumber)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getFileNames(long companyId, long repositoryId,
		java.lang.String dirName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getFileSize(long companyId, long repositoryId,
		java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException;

	public void updateFile(long companyId, java.lang.String portletId,
		long groupId, long repositoryId, java.lang.String fileName,
		double versionNumber, java.lang.String sourceFileName,
		long fileEntryId, java.lang.String properties,
		java.util.Date modifiedDate,
		com.liferay.portal.service.ServiceContext serviceContext,
		java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void updateFile(long companyId, java.lang.String portletId,
		long groupId, long repositoryId, java.lang.String fileName,
		double versionNumber, java.lang.String sourceFileName,
		long fileEntryId, java.lang.String properties,
		java.util.Date modifiedDate,
		com.liferay.portal.service.ServiceContext serviceContext, byte[] bytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void updateFile(long companyId, java.lang.String portletId,
		long groupId, long repositoryId, long newRepositoryId,
		java.lang.String fileName, long fileEntryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;
}