/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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