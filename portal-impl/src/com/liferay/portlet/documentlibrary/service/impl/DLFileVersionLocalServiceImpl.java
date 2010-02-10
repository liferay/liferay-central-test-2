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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.base.DLFileVersionLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.util.comparator.FileVersionVersionComparator;

import java.util.List;

/**
 * <a href="DLFileVersionLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public class DLFileVersionLocalServiceImpl
	extends DLFileVersionLocalServiceBaseImpl {

	public DLFileVersion getFileVersion(
			long groupId, long folderId, String name, double version)
		throws PortalException, SystemException {

		return dlFileVersionPersistence.findByG_F_N_V(
			groupId, folderId, name, version);
	}

	public List<DLFileVersion> getFileVersions(
			long groupId, long folderId, String name, int status)
		throws SystemException {

		if (status == StatusConstants.ANY) {
			return dlFileVersionPersistence.findByG_F_N(
				groupId, folderId, name);
		}
		else {
			return dlFileVersionPersistence.findByG_F_N_S(
				groupId, folderId, name, status);
		}
	}

	public DLFileVersion getLatestFileVersion(
			long groupId, long folderId, String name)
		throws PortalException, SystemException {

		List<DLFileVersion> fileVersions = dlFileVersionPersistence.findByG_F_N(
			groupId, folderId, name, 0, 1, new FileVersionVersionComparator());

		if (fileVersions.isEmpty()) {
			throw new NoSuchFileVersionException();
		}

		return fileVersions.get(0);
	}

	public DLFileVersion updateDescription(
			long groupId, long folderId, String name, double version,
			String description)
		throws PortalException, SystemException {

		DLFileVersion fileVersion = dlFileVersionPersistence.findByG_F_N_V(
			groupId, folderId, name, version);

		fileVersion.setDescription(description);

		dlFileVersionPersistence.update(fileVersion, false);

		return fileVersion;
	}

}