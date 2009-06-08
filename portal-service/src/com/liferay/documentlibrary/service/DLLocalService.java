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
 * <a href="DLLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.liferay.documentlibrary.service.impl.DLLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.documentlibrary.service.DLLocalServiceUtil
 *
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DLLocalService {
	public void addFile(long companyId, java.lang.String portletId,
		long groupId, long repositoryId, java.lang.String fileName,
		long fileEntryId, java.lang.String properties,
		java.util.Date modifiedDate,
		com.liferay.portal.service.ServiceContext serviceContext,
		java.io.InputStream is)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void checkRoot(long companyId)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.io.InputStream getFileAsStream(long companyId,
		long repositoryId, java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.io.InputStream getFileAsStream(long companyId,
		long repositoryId, java.lang.String fileName, double versionNumber)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasFile(long companyId, long repositoryId,
		java.lang.String fileName, double versionNumber)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void move(java.lang.String srcDir, java.lang.String destDir)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String portletId, long groupId, long userId,
		long[] repositoryIds, java.lang.String keywords, int start, int end)
		throws com.liferay.portal.SystemException;

	public void updateFile(long companyId, java.lang.String portletId,
		long groupId, long repositoryId, java.lang.String fileName,
		double versionNumber, java.lang.String sourceFileName,
		long fileEntryId, java.lang.String properties,
		java.util.Date modifiedDate,
		com.liferay.portal.service.ServiceContext serviceContext,
		java.io.InputStream is)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void validate(java.lang.String fileName, java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void validate(java.lang.String fileName, byte[] bytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void validate(java.lang.String fileName, java.io.InputStream is)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void validate(java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void validate(java.lang.String fileName,
		java.lang.String sourceFileName, java.io.InputStream is)
		throws com.liferay.portal.PortalException;
}