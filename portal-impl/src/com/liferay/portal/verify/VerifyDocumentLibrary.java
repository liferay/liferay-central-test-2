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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLRepositoryLocalServiceUtil;

import java.util.List;

/**
 * @author Raymond Augé
 * @author Douglas Wong
 */
public class VerifyDocumentLibrary extends VerifyProcess {

	protected void doVerify() throws Exception {
		removeOrphanedFileEntries();
		updateAssets();
	}

	protected void removeOrphanedFileEntries() throws Exception {
		List<DLFileEntry> fileEntries =
			DLRepositoryLocalServiceUtil.getOrphanedFileEntries();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + fileEntries.size() +
					" file entries with no group");
		}

		for (DLFileEntry fileEntry : fileEntries) {
			try {
				DLRepositoryLocalServiceUtil.deleteFileEntry(
					fileEntry.getFileEntryId());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to remove file entry " +
							fileEntry.getFileEntryId() + " with group " +
								fileEntry.getGroupId() + ": " + e.getMessage());
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Removed orphaned file entries");
		}
	}

	protected void updateAssets() throws Exception {
		List<DLFileEntry> fileEntries =
			DLRepositoryLocalServiceUtil.getNoAssetFileEntries();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + fileEntries.size() +
					" file entries with no asset");
		}

		for (DLFileEntry fileEntry : fileEntries) {
			try {
				DLRepositoryLocalServiceUtil.updateAsset(
					fileEntry.getUserId(), fileEntry, null, null, null);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to update asset for file entry " +
							fileEntry.getFileEntryId() + ": " + e.getMessage());
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Assets verified for file entries");
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		VerifyDocumentLibrary.class);

}