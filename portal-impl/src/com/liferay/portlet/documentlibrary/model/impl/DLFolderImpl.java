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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="DLFolderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DLFolderImpl extends DLFolderModelImpl implements DLFolder {

	public DLFolderImpl() {
	}

	public List<DLFolder> getAncestors()
		throws PortalException, SystemException {

		List<DLFolder> ancestors = new ArrayList<DLFolder>();

		DLFolder folder = this;

		while (true) {
			if (!folder.isRoot()) {
				folder = folder.getParentFolder();

				ancestors.add(folder);
			}
			else {
				break;
			}
		}

		return ancestors;
	}

	public DLFolder getParentFolder()
		throws PortalException, SystemException {

		if (getParentFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return null;
		}

		return DLFolderLocalServiceUtil.getFolder(getParentFolderId());
	}

	public String getPath() throws PortalException, SystemException {
		StringBuilder sb = new StringBuilder();

		DLFolder folder = this;

		while (true) {
			sb.insert(0, folder.getName());
			sb.insert(0, StringPool.SLASH);

			if (folder.getParentFolderId() !=
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				folder = DLFolderLocalServiceUtil.getFolder(
					folder.getParentFolderId());
			}
			else {
				break;
			}
		}

		return sb.toString();
	}

	public String[] getPathArray() throws PortalException, SystemException {
		String path = getPath();

		// Remove leading /

		path = path.substring(1, path.length());

		return StringUtil.split(path, StringPool.SLASH);
	}

	public boolean isRoot() {
		if (getParentFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return true;
		}
		else {
			return false;
		}
	}

}