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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

/**
 * <a href="DLFileShortcutImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DLFileShortcutImpl
	extends DLFileShortcutModelImpl implements DLFileShortcut {

	public DLFileShortcutImpl() {
	}

	public DLFolder getFolder() {
		DLFolder folder = null;

		if (getFolderId() > 0) {
			try {
				folder = DLFolderLocalServiceUtil.getFolder(getFolderId());
			}
			catch (Exception e) {
				folder = new DLFolderImpl();

				_log.error(e);
			}
		}
		else {
			folder = new DLFolderImpl();
		}

		return folder;
	}

	public String getToTitle() {
		String toTitle = null;

		try {
			DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
				getGroupId(), getToFolderId(), getToName());

			toTitle = fileEntry.getTitle();
		}
		catch (Exception e) {
			_log.error(e);
		}

		return toTitle;
	}

	private static Log _log = LogFactoryUtil.getLog(DLFileShortcutImpl.class);

}