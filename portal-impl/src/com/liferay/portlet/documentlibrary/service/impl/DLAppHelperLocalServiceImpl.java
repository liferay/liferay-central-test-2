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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.base.DLAppHelperLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.social.DLActivityKeys;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.RatingsStats;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Chow
 */
public class DLAppHelperLocalServiceImpl
	extends DLAppHelperLocalServiceBaseImpl {

	public void addFileEntry(
			DLFileEntry fileEntry, DLFileVersion fileVersion,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		long userId = fileEntry.getUserId();
		long groupId = fileEntry.getGroupId();
		long fileEntryId = fileEntry.getFileEntryId();

		// Message boards

		if (PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED) {
			mbMessageLocalService.addDiscussionMessage(
				userId, fileEntry.getUserName(), groupId,
				DLFileEntry.class.getName(), fileEntryId,
				WorkflowConstants.ACTION_PUBLISH);
		}
	}

	public void addFolder(DLFolder folder, ServiceContext serviceContext)
		throws PortalException, SystemException {
	}

	public void deleteFileEntry(DLFileEntry fileEntry)
		throws PortalException, SystemException {

		// File ranks

		dlFileRankLocalService.deleteFileRanks(
			fileEntry.getFolderId(), fileEntry.getName());

		// File shortcuts

		dlFileShortcutLocalService.deleteFileShortcuts(
			fileEntry.getFileEntryId());

		// Asset

		assetEntryLocalService.deleteEntry(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// Social

		socialActivityLocalService.deleteActivities(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());
	}

	public void deleteFolder(DLFolder folder)
		throws PortalException, SystemException {
	}

	public void getFileAsStream(long userId, DLFileEntry fileEntry)
		throws PortalException, SystemException {

		long groupId = fileEntry.getGroupId();
		long companyId = fileEntry.getCompanyId();
		long folderId = fileEntry.getFolderId();
		String name = fileEntry.getName();

		// File rank

		if (userId > 0) {
			dlFileRankLocalService.updateFileRank(
				groupId, companyId, userId, folderId, name,
				new ServiceContext());
		}

		// File read count

		if (PropsValues.DL_FILE_ENTRY_READ_COUNT_ENABLED) {
			assetEntryLocalService.incrementViewCounter(
				userId, DLFileEntry.class.getName(),
				fileEntry.getFileEntryId());

			List<DLFileShortcut> fileShortcuts =
				dlFileShortcutPersistence.findByToFileEntryId(
					fileEntry.getFileEntryId());

			for (DLFileShortcut fileShortcut : fileShortcuts) {
				assetEntryLocalService.incrementViewCounter(
					userId, DLFileShortcut.class.getName(),
					fileShortcut.getFileShortcutId());
			}
		}
	}

	public List<DLFileShortcut> getFileShortcuts(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		List<DLFileShortcut> fileShortcuts = new ArrayList<DLFileShortcut>();

		for (long folderId : folderIds) {
			fileShortcuts.addAll(getFileShortcuts(groupId, folderId, status));
		}

		return fileShortcuts;
	}

	public List<DLFileShortcut> getFileShortcuts(
			long groupId, long folderId, int status)
		throws SystemException {

		return dlFileShortcutPersistence.findByG_F_S(groupId, folderId, status);
	}

	public int getFileShortcutsCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		int count = 0;

		for (long folderId : folderIds) {
			count += getFileShortcutsCount(groupId, folderId, status);
		}

		return count;
	}

	public int getFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		return dlFileShortcutPersistence.countByG_F_S(
			groupId, folderId, status);
	}

	public List<DLFileEntry> getNoAssetFileEntries() throws SystemException {
		return null;
	}

	public void moveFileEntry(
			long userId, long groupId, long folderId, long newFolderId,
			long oldFileEntryId, long newFileEntryId, String name)
		throws PortalException, SystemException {

		// File shortcuts

		dlFileShortcutLocalService.updateFileShortcuts(
			oldFileEntryId, newFileEntryId);

		// Asset

		assetEntryLocalService.deleteEntry(
			DLFileEntry.class.getName(), oldFileEntryId);

		List<DLFileShortcut> fileShortcuts =
			dlFileShortcutPersistence.findByToFileEntryId(oldFileEntryId);

		for (DLFileShortcut fileShortcut : fileShortcuts) {
			assetEntryLocalService.deleteEntry(
				DLFileShortcut.class.getName(),
				fileShortcut.getFileShortcutId());
		}

		// Ratings

		RatingsStats stats = ratingsStatsLocalService.getStats(
			DLFileEntry.class.getName(), oldFileEntryId);

		stats.setClassPK(newFileEntryId);

		ratingsStatsPersistence.update(stats, false);

		long classNameId = PortalUtil.getClassNameId(
			DLFileEntry.class.getName());

		List<RatingsEntry> entries = ratingsEntryPersistence.findByC_C(
			classNameId, oldFileEntryId);

		for (RatingsEntry entry : entries) {
			entry.setClassPK(newFileEntryId);

			ratingsEntryPersistence.update(entry, false);
		}

		// Message boards

		MBDiscussion discussion = mbDiscussionPersistence.fetchByC_C(
			classNameId, oldFileEntryId);

		if (discussion != null) {
			discussion.setClassPK(newFileEntryId);

			mbDiscussionPersistence.update(discussion, false);
		}

		// Social

		socialActivityLocalService.deleteActivities(
			DLFileEntry.class.getName(), oldFileEntryId);
	}

	public void updateAsset(
			long userId, DLFileEntry fileEntry, DLFileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames, String mimeType,
			boolean addDraftAssetEntry, boolean visible)
		throws PortalException, SystemException {

		if (addDraftAssetEntry) {
			assetEntryLocalService.updateEntry(
				userId, fileEntry.getGroupId(), DLFileEntry.class.getName(),
				fileVersion.getFileVersionId(), fileEntry.getUuid(),
				assetCategoryIds, assetTagNames, false, null, null, null, null,
				mimeType, fileEntry.getTitle(), fileEntry.getDescription(),
				null, null, 0, 0, null, false);
		}
		else {
			assetEntryLocalService.updateEntry(
				userId, fileEntry.getGroupId(), DLFileEntry.class.getName(),
				fileEntry.getFileEntryId(), fileEntry.getUuid(),
				assetCategoryIds, assetTagNames, visible, null, null, null,
				null, mimeType, fileEntry.getTitle(),
				fileEntry.getDescription(), null, null, 0, 0, null, false);

			List<DLFileShortcut> fileShortcuts =
				dlFileShortcutPersistence.findByToFileEntryId(
					fileEntry.getFileEntryId());

			for (DLFileShortcut fileShortcut : fileShortcuts) {
				assetEntryLocalService.updateEntry(
					userId, fileShortcut.getGroupId(),
					DLFileShortcut.class.getName(),
					fileShortcut.getFileShortcutId(), fileShortcut.getUuid(),
					assetCategoryIds, assetTagNames, true, null, null, null,
					null, mimeType, fileEntry.getTitle(),
					fileEntry.getDescription(), null, null, 0, 0, null, false);
			}
		}
	}

	public void updateStatus(
			long userId, DLFileEntry fileEntry, DLFileVersion latestFileVersion,
			int status)
		throws PortalException, SystemException {

		if (status == WorkflowConstants.STATUS_APPROVED) {

			// Asset

			if (fileEntry.getVersion().equals(latestFileVersion.getVersion())) {
				if ((latestFileVersion.getVersion() !=
						DLFileEntryConstants.DEFAULT_VERSION)) {

					AssetEntry draftAssetEntry = null;

					try {
						draftAssetEntry = assetEntryLocalService.getEntry(
							DLFileEntry.class.getName(),
							latestFileVersion.getPrimaryKey());

						long[] assetCategoryIds =
							draftAssetEntry.getCategoryIds();
						String[] assetTagNames = draftAssetEntry.getTagNames();

						assetEntryLocalService.updateEntry(
							userId, fileEntry.getGroupId(),
							DLFileEntry.class.getName(),
							fileEntry.getFileEntryId(), fileEntry.getUuid(),
							assetCategoryIds, assetTagNames, true, null, null,
							null, null, draftAssetEntry.getMimeType(),
							fileEntry.getTitle(), fileEntry.getDescription(),
							null, null, 0, 0, null, false);

						assetEntryLocalService.deleteEntry(
							draftAssetEntry.getEntryId());
					}
					catch (NoSuchEntryException nsee) {
					}
				}

				assetEntryLocalService.updateVisible(
					DLFileEntry.class.getName(), fileEntry.getFileEntryId(),
					true);
			}

			// Social

			socialActivityLocalService.addUniqueActivity(
				latestFileVersion.getStatusByUserId(),
				latestFileVersion.getGroupId(),
				latestFileVersion.getCreateDate(), DLFileEntry.class.getName(),
				fileEntry.getFileEntryId(), DLActivityKeys.ADD_FILE_ENTRY,
				StringPool.BLANK, 0);
		}
		else {

			// Asset

			if (Validator.isNull(fileEntry.getVersion())) {
				assetEntryLocalService.updateVisible(
					DLFileEntry.class.getName(), fileEntry.getFileEntryId(),
					false);
			}
		}
	}

}