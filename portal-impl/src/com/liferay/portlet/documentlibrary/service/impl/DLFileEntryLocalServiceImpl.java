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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.FileSizeException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.documentlibrary.util.DLIndexerUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MathUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.social.DLActivityKeys;
import com.liferay.portlet.documentlibrary.util.comparator.FileEntryModifiedDateComparator;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.RatingsStats;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.Date;
import java.util.List;

/**
 * <a href="DLFileEntryLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * For DLFileEntries, the naming convention for some of the variables is not
 * very informative, due to legacy code. Each DLFileEntry has a corresponding
 * name and title. The "name" is a unique identifier for a given file and
 * usually follows the format "1234" whereas the "title" is the actual name
 * specified by the user (e.g., "Budget.xls").
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 */
public class DLFileEntryLocalServiceImpl
	extends DLFileEntryLocalServiceBaseImpl {

	public DLFileEntry addFileEntry(
			long userId, long groupId, long folderId, String name, String title,
			String description, String extraSettings, byte[] bytes,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addFileEntry(
			null, userId, groupId, folderId, name, title, description,
			extraSettings, bytes, serviceContext);
	}

	public DLFileEntry addFileEntry(
			long userId, long groupId, long folderId, String name, String title,
			String description, String extraSettings, File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addFileEntry(
			null, userId, groupId, folderId, name, title, description,
			extraSettings, file, serviceContext);
	}

	public DLFileEntry addFileEntry(
			long userId, long groupId, long folderId, String name, String title,
			String description, String extraSettings, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addFileEntry(
			null, userId, groupId, folderId, name, title, description,
			extraSettings, is, size, serviceContext);
	}

	public DLFileEntry addFileEntry(
			String uuid, long userId, long groupId, long folderId, String name,
			String title, String description, String extraSettings,
			byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!PropsValues.WEBDAV_LITMUS) {
			if ((bytes == null) || (bytes.length == 0)) {
				throw new FileSizeException();
			}
		}

		InputStream is = new ByteArrayInputStream(bytes);

		return addFileEntry(
			uuid, userId, groupId, folderId, name, title, description,
			extraSettings, is, bytes.length, serviceContext);
	}

	public DLFileEntry addFileEntry(
			String uuid, long userId, long groupId, long folderId, String name,
			String title, String description, String extraSettings, File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!PropsValues.WEBDAV_LITMUS) {
			if (file == null) {
				throw new FileSizeException();
			}
		}

		try {
			InputStream is = new BufferedInputStream(new FileInputStream(file));

			return addFileEntry(
				uuid, userId, groupId, folderId, name, title, description,
				extraSettings, is, file.length(), serviceContext);
		}
		catch (FileNotFoundException fnfe) {
			throw new FileSizeException();
		}
	}

	public DLFileEntry addFileEntry(
			String uuid, long userId, long groupId, long folderId, String name,
			String title, String description, String extraSettings,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File entry

		User user = userPersistence.findByPrimaryKey(userId);
		folderId = getFolderId(user.getCompanyId(), folderId);
		Date now = new Date();

		if (Validator.isNull(title)) {
			title = name;
		}

		name = String.valueOf(
			counterLocalService.increment(DLFileEntry.class.getName()));

		int status = serviceContext.getStatus();

		validate(groupId, folderId, title, is);

		long fileEntryId = counterLocalService.increment();

		DLFileEntry fileEntry = dlFileEntryPersistence.create(fileEntryId);

		fileEntry.setUuid(uuid);
		fileEntry.setGroupId(groupId);
		fileEntry.setCompanyId(user.getCompanyId());
		fileEntry.setUserId(user.getUserId());
		fileEntry.setUserName(user.getFullName());
		fileEntry.setVersionUserId(user.getUserId());
		fileEntry.setVersionUserName(user.getFullName());
		fileEntry.setCreateDate(now);
		fileEntry.setModifiedDate(now);
		fileEntry.setFolderId(folderId);
		fileEntry.setName(name);
		fileEntry.setTitle(title);
		fileEntry.setDescription(description);

		if (status == StatusConstants.APPROVED) {
			fileEntry.setVersion(DLFileEntryConstants.DEFAULT_VERSION);
		}
		else {
			fileEntry.setVersion(0);
		}

		fileEntry.setSize((int)size);
		fileEntry.setReadCount(DLFileEntryConstants.DEFAULT_READ_COUNT);
		fileEntry.setExtraSettings(extraSettings);

		dlFileEntryPersistence.update(fileEntry, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
				serviceContext.getAddGuestPermissions()) {

			addFileEntryResources(
				fileEntry, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addFileEntryResources(
				fileEntry, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Asset

		updateAsset(
			userId, fileEntry, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// Expando

		ExpandoBridge expandoBridge = fileEntry.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// File

		long repositoryId = getRepositoryId(groupId, folderId);

		dlLocalService.addFile(
			user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
			fileEntry.getGroupId(), repositoryId, name, false, fileEntryId,
			fileEntry.getLuceneProperties(), fileEntry.getModifiedDate(),
			serviceContext, is);

		// Message boards

		if (PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED) {
			mbMessageLocalService.addDiscussionMessage(
				userId, fileEntry.getUserName(), DLFileEntry.class.getName(),
				fileEntryId, StatusConstants.APPROVED);
		}

		// Social

		socialActivityLocalService.addActivity(
			userId, fileEntry.getGroupId(), DLFileEntry.class.getName(),
			fileEntryId, DLActivityKeys.ADD_FILE_ENTRY, StringPool.BLANK, 0);

		// File version

		addFileVersion(user, fileEntry, fileEntry.getVersion(), status);

		// Folder

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

			folder.setLastPostDate(fileEntry.getModifiedDate());

			dlFolderPersistence.update(folder, false);
		}

		// Workflow

		if (serviceContext.isStartWorkflow()) {
			try {
				WorkflowHandlerRegistryUtil.startWorkflowInstance(
					user.getCompanyId(), groupId, userId,
					DLFileEntry.class.getName(), fileEntryId, fileEntry);
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}

		return fileEntry;
	}

	public void addFileEntryResources(
			DLFileEntry fileEntry, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			fileEntry.getCompanyId(), fileEntry.getGroupId(),
			fileEntry.getUserId(), DLFileEntry.class.getName(),
			fileEntry.getFileEntryId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addFileEntryResources(
			DLFileEntry fileEntry, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			fileEntry.getCompanyId(), fileEntry.getGroupId(),
			fileEntry.getUserId(), DLFileEntry.class.getName(),
			fileEntry.getFileEntryId(), communityPermissions, guestPermissions);
	}

	public void addFileEntryResources(
			long fileEntryId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		addFileEntryResources(
			fileEntry, addCommunityPermissions, addGuestPermissions);
	}

	public void addFileEntryResources(
			long fileEntryId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		addFileEntryResources(
			fileEntry, communityPermissions, guestPermissions);
	}

	public DLFileEntry addOrOverwriteFileEntry(
			long userId, long groupId, long folderId, String name,
			String sourceName, String title, String description,
			String extraSettings, File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			dlFileEntryPersistence.findByG_F_T(groupId, folderId, title);

			return updateFileEntry(
				userId, groupId, folderId, folderId, name, sourceName, title,
				description, extraSettings, file, serviceContext);
		}
		catch (NoSuchFileEntryException nsfee) {
			return addFileEntry(
				userId, groupId, folderId, name, title, description,
				extraSettings, file, serviceContext);
		}
	}

	public void deleteFileEntries(long groupId, long folderId)
		throws PortalException, SystemException {

		List<DLFileEntry> fileEntries = dlFileEntryPersistence.findByG_F(
			groupId, folderId);

		for (DLFileEntry fileEntry : fileEntries) {
			deleteFileEntry(fileEntry);
		}
	}

	public void deleteFileEntry(DLFileEntry fileEntry)
		throws PortalException, SystemException {

		// File

		try {
			long repositoryId = getRepositoryId(
				fileEntry.getGroupId(), fileEntry.getFolderId());

			dlService.deleteFile(
				fileEntry.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
				repositoryId, fileEntry.getName());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		// File ranks

		dlFileRankLocalService.deleteFileRanks(
			fileEntry.getFolderId(), fileEntry.getName());

		// File shortcuts

		dlFileShortcutLocalService.deleteFileShortcuts(
			fileEntry.getGroupId(), fileEntry.getFolderId(),
			fileEntry.getName());

		// File versions

		List<DLFileVersion> fileVersions = dlFileVersionPersistence.findByG_F_N(
			fileEntry.getGroupId(), fileEntry.getFolderId(),
			fileEntry.getName());

		for (DLFileVersion fileVersion : fileVersions) {
			dlFileVersionPersistence.remove(fileVersion);
		}

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
			fileEntry.getCompanyId(), fileEntry.getGroupId(),
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// Social

		socialActivityLocalService.deleteActivities(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// Expando

		expandoValueLocalService.deleteValues(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// Asset

		assetEntryLocalService.deleteEntry(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// WebDAVProps

		webDAVPropsLocalService.deleteWebDAVProps(
			DLFileEntry.class.getName(), fileEntry.getPrimaryKey());

		// Resources

		resourceLocalService.deleteResource(
			fileEntry.getCompanyId(), DLFileEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, fileEntry.getFileEntryId());

		// File entry

		dlFileEntryPersistence.remove(fileEntry);
	}

	public void deleteFileEntry(long groupId, long folderId, String name)
		throws PortalException, SystemException {

		deleteFileEntry(groupId, folderId, name, -1);
	}

	public void deleteFileEntry(
			long groupId, long folderId, String name, double version)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = dlFileEntryPersistence.findByG_F_N(
			groupId, folderId, name);

		if (version > 0) {
			try {
				long repositoryId = getRepositoryId(groupId, folderId);

				dlService.deleteFile(
					fileEntry.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
					repositoryId, fileEntry.getName(), version);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}

			dlFileVersionPersistence.removeByG_F_N_V(
				groupId, folderId, name, version);
		}
		else {
			deleteFileEntry(fileEntry);
		}
	}

	public List<DLFileEntry> getCompanyFileEntries(
			long companyId, int start, int end)
		throws SystemException {

		return dlFileEntryPersistence.findByCompanyId(companyId, start, end);
	}

	public List<DLFileEntry> getCompanyFileEntries(
			long companyId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return dlFileEntryPersistence.findByCompanyId(
			companyId, start, end, obc);
	}

	public int getCompanyFileEntriesCount(long companyId)
		throws SystemException {

		return dlFileEntryPersistence.countByCompanyId(companyId);
	}

	public InputStream getFileAsStream(
			long companyId, long userId, long groupId, long folderId,
			String name)
		throws PortalException, SystemException {

		return getFileAsStream(companyId, userId, groupId, folderId, name, 0);
	}

	public InputStream getFileAsStream(
			long companyId, long userId, long groupId, long folderId,
			String name, double version)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = dlFileEntryPersistence.findByG_F_N(
			groupId, folderId, name);

		if (userId > 0) {
			dlFileRankLocalService.updateFileRank(
				groupId, companyId, userId, folderId, name);
		}

		fileEntry.setReadCount(fileEntry.getReadCount() + 1);

		dlFileEntryPersistence.update(fileEntry, false);

		assetEntryLocalService.incrementViewCounter(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		List<DLFileShortcut> fileShortcuts =
			dlFileShortcutPersistence.findByG_TF_TN(groupId, folderId, name);

		for (DLFileShortcut fileShortcut : fileShortcuts) {
			assetEntryLocalService.incrementViewCounter(
				DLFileShortcut.class.getName(),
				fileShortcut.getFileShortcutId());
		}

		long repositoryId = getRepositoryId(groupId, folderId);

		if ((version > 0) && (fileEntry.getVersion() != version)) {
			return dlLocalService.getFileAsStream(
				companyId, repositoryId, name, version);
		}
		else {
			return dlLocalService.getFileAsStream(
				companyId, repositoryId, name, fileEntry.getVersion());
		}
	}

	public List<DLFileEntry> getFileEntries(long groupId, long folderId)
		throws SystemException {

		return dlFileEntryPersistence.findByG_F(groupId, folderId);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end)
		throws SystemException {

		return dlFileEntryPersistence.findByG_F(groupId, folderId, start, end);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlFileEntryPersistence.findByG_F(
			groupId, folderId, start, end, obc);
	}

	public int getFileEntriesCount(long groupId, long folderId)
		throws SystemException {

		return dlFileEntryPersistence.countByG_F(groupId, folderId);
	}

	public DLFileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		return dlFileEntryPersistence.findByPrimaryKey(fileEntryId);
	}

	public DLFileEntry getFileEntry(long groupId, long folderId, String name)
		throws PortalException, SystemException {

		return dlFileEntryPersistence.findByG_F_N(groupId, folderId, name);
	}

	public DLFileEntry getFileEntryByTitle(
			long groupId, long folderId, String title)
		throws PortalException, SystemException {

		return dlFileEntryPersistence.findByG_F_T(groupId, folderId, title);
	}

	public DLFileEntry getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException, SystemException {

		return dlFileEntryPersistence.findByUUID_G(uuid, groupId);
	}

	public int getFoldersFileEntriesCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return dlFileEntryFinder.countByG_F_S(groupId, folderIds, status);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, int start, int end)
		throws SystemException {

		return getGroupFileEntries(
			groupId, start, end, new FileEntryModifiedDateComparator());
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return dlFileEntryPersistence.findByGroupId(groupId, start, end, obc);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return getGroupFileEntries(
			groupId, userId, start, end, new FileEntryModifiedDateComparator());
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (userId <= 0) {
			return dlFileEntryPersistence.findByGroupId(
				groupId, start, end, obc);
		}
		else {
			return dlFileEntryPersistence.findByG_U(
				groupId, userId, start, end, obc);
		}
	}

	public int getGroupFileEntriesCount(long groupId) throws SystemException {
		return dlFileEntryPersistence.countByGroupId(groupId);
	}

	public int getGroupFileEntriesCount(long groupId, long userId)
		throws SystemException {

		if (userId <= 0) {
			return dlFileEntryPersistence.countByGroupId(groupId);
		}
		else {
			return dlFileEntryPersistence.countByG_U(groupId, userId);
		}
	}

	public List<DLFileEntry> getNoAssetFileEntries() throws SystemException {
		return dlFileEntryFinder.findByNoAssets();
	}

	public void reIndex(long fileEntryId) throws SystemException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		DLFileEntry fileEntry = dlFileEntryPersistence.fetchByPrimaryKey(
			fileEntryId);

		if (fileEntry == null) {
			return;
		}

		DLFolder folder = fileEntry.getFolder();

		long companyId = fileEntry.getCompanyId();
		String portletId = PortletKeys.DOCUMENT_LIBRARY;
		long groupId = folder.getGroupId();
		long folderId = folder.getFolderId();
		String fileName = fileEntry.getName();
		String properties = fileEntry.getLuceneProperties();
		Date modifiedDate = fileEntry.getModifiedDate();

		long[] assetCategoryIds = assetCategoryLocalService.getCategoryIds(
			DLFileEntry.class.getName(), fileEntryId);
		String[] assetTagNames = assetTagLocalService.getTagNames(
			DLFileEntry.class.getName(), fileEntryId);

		try {
			if (fileEntry.getVersion() > 0) {
				DLIndexerUtil.updateFile(
					companyId, portletId, groupId, folderId, fileName,
					fileEntryId, properties, modifiedDate, assetCategoryIds,
					assetTagNames);
			}
			else {
				DLIndexerUtil.deleteFile(
					companyId, portletId, folderId, fileName);
			}
		}
		catch (SearchException se) {
			_log.error("Reindexing " + fileEntryId, se);
		}
	}

	public void updateAsset(
			long userId, DLFileEntry fileEntry, long[] assetCategoryIds,
			String[] assetTagNames)
		throws PortalException, SystemException {

		String mimeType = MimeTypesUtil.getContentType(fileEntry.getTitle());

		assetEntryLocalService.updateEntry(
			userId, fileEntry.getGroupId(), DLFileEntry.class.getName(),
			fileEntry.getFileEntryId(), assetCategoryIds, assetTagNames, true,
			null, null, null, null, mimeType, fileEntry.getTitle(),
			fileEntry.getDescription(), null, null, 0, 0, null, false);

		List<DLFileShortcut> fileShortcuts =
			dlFileShortcutPersistence.findByG_TF_TN(
				fileEntry.getGroupId(), fileEntry.getFolderId(),
				fileEntry.getName());

		for (DLFileShortcut fileShortcut : fileShortcuts) {
			assetEntryLocalService.updateEntry(
				userId, fileShortcut.getGroupId(),
				DLFileShortcut.class.getName(),
				fileShortcut.getFileShortcutId(), assetCategoryIds,
				assetTagNames, true, null, null, null, null, mimeType,
				fileEntry.getTitle(), fileEntry.getDescription(), null, null, 0,
				0, null, false);
		}
	}

	public DLFileEntry updateFileEntry(
			long userId, long groupId, long folderId, long newFolderId,
			String name, String sourceFileName, String title,
			String description, String extraSettings, byte[] bytes,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		InputStream is = null;
		long size = 0;

		if ((bytes != null) && (bytes.length > 0)) {
			is = new ByteArrayInputStream(bytes);
			size = bytes.length;
		}

		return updateFileEntry(
			userId, groupId, folderId, newFolderId, name, sourceFileName, title,
			description, extraSettings, is, size, serviceContext);
	}

	public DLFileEntry updateFileEntry(
			long userId, long groupId, long folderId, long newFolderId,
			String name, String sourceFileName, String title,
			String description, String extraSettings, File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			InputStream is = null;
			long size = 0;

			if ((file != null) && (file.length() > 0)) {
				is = new BufferedInputStream(new FileInputStream(file));
				size = file.length();
			}

			return updateFileEntry(
				userId, groupId, folderId, newFolderId, name, sourceFileName,
				title, description, extraSettings, is, size, serviceContext);
		}
		catch (FileNotFoundException fnfe) {
			throw new NoSuchFileException();
		}
	}

	public DLFileEntry updateFileEntry(
			long userId, long groupId, long folderId, long newFolderId,
			String name, String sourceFileName, String title,
			String description, String extraSettings, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File entry

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		if (Validator.isNull(title)) {
			title = sourceFileName;

			if (Validator.isNull(title)) {
				title = name;
			}
		}

		DLFileEntry fileEntry = dlFileEntryPersistence.findByG_F_N(
			groupId, folderId, name);

		validate(
			groupId, folderId, newFolderId, name, title, sourceFileName, is);

		fileEntry.setTitle(title);
		fileEntry.setDescription(description);
		fileEntry.setExtraSettings(extraSettings);

		dlFileEntryPersistence.update(fileEntry, false);

		// Move file entry

		if (folderId != newFolderId) {
			long oldFileEntryId = fileEntry.getFileEntryId();

			if (dlLocalService.hasFile(
					user.getCompanyId(), getRepositoryId(groupId, newFolderId),
					name, 0)) {

				throw new DuplicateFileException(name);
			}

			long newFileEntryId = counterLocalService.increment();

			DLFileEntry newFileEntry = dlFileEntryPersistence.create(
				newFileEntryId);

			newFileEntry.setGroupId(fileEntry.getGroupId());
			newFileEntry.setCompanyId(fileEntry.getCompanyId());
			newFileEntry.setUserId(fileEntry.getUserId());
			newFileEntry.setUserName(fileEntry.getUserName());
			newFileEntry.setVersionUserId(fileEntry.getVersionUserId());
			newFileEntry.setVersionUserName(fileEntry.getVersionUserName());
			newFileEntry.setCreateDate(fileEntry.getCreateDate());
			newFileEntry.setModifiedDate(fileEntry.getModifiedDate());
			newFileEntry.setFolderId(newFolderId);
			newFileEntry.setName(name);
			newFileEntry.setTitle(fileEntry.getTitle());
			newFileEntry.setDescription(fileEntry.getDescription());
			newFileEntry.setVersion(fileEntry.getVersion());
			newFileEntry.setSize(fileEntry.getSize());
			newFileEntry.setReadCount(fileEntry.getReadCount());
			newFileEntry.setExtraSettings(extraSettings);

			dlFileEntryPersistence.update(newFileEntry, false);

			dlFileEntryPersistence.remove(fileEntry);

			List<DLFileVersion> fileVersions =
				dlFileVersionPersistence.findByG_F_N(
					groupId, folderId, name);

			for (DLFileVersion fileVersion : fileVersions) {
				long newFileVersionId = counterLocalService.increment();

				DLFileVersion newFileVersion = dlFileVersionPersistence.create(
					newFileVersionId);

				newFileVersion.setGroupId(fileVersion.getGroupId());
				newFileVersion.setCompanyId(fileVersion.getCompanyId());
				newFileVersion.setUserId(fileVersion.getUserId());
				newFileVersion.setUserName(fileVersion.getUserName());
				newFileVersion.setCreateDate(fileVersion.getCreateDate());
				newFileVersion.setFolderId(newFolderId);
				newFileVersion.setName(name);
				newFileVersion.setVersion(fileVersion.getVersion());
				newFileVersion.setSize(fileVersion.getSize());
				newFileVersion.setStatus(fileVersion.getStatus());
				newFileVersion.setStatusByUserId(userId);
				newFileVersion.setStatusByUserName(user.getFullName());
				newFileVersion.setStatusDate(now);

				dlFileVersionPersistence.update(newFileVersion, false);

				dlFileVersionPersistence.remove(fileVersion);
			}

			dlFileShortcutLocalService.updateFileShortcuts(
				groupId, folderId, name, newFolderId, name);

			// Resources

			Resource resource = resourceLocalService.getResource(
				fileEntry.getCompanyId(), DLFileEntry.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(fileEntry.getPrimaryKey()));

			resource.setPrimKey(String.valueOf(newFileEntryId));

			resourcePersistence.update(resource, false);

			// Asset

			assetEntryLocalService.deleteEntry(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());

			List<DLFileShortcut> fileShortcuts =
				dlFileShortcutPersistence.findByG_TF_TN(
					groupId, folderId, name);

			for (DLFileShortcut fileShortcut : fileShortcuts) {
				assetEntryLocalService.deleteEntry(
					DLFileShortcut.class.getName(),
					fileShortcut.getFileShortcutId());
			}

			// Expando

			expandoValueLocalService.deleteValues(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());

			// File

			long repositoryId = getRepositoryId(groupId, folderId);
			long newRepositoryId = getRepositoryId(groupId, newFolderId);

			dlService.updateFile(
				user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
				newFileEntry.getGroupId(), repositoryId, newRepositoryId, name,
				newFileEntryId);

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
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());

			folderId = newFolderId;
			fileEntry = newFileEntry;
		}

		// Asset

		updateAsset(
			userId, fileEntry, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// Expando

		ExpandoBridge expandoBridge = fileEntry.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Social

		socialActivityLocalService.addActivity(
			userId, fileEntry.getGroupId(), DLFileEntry.class.getName(),
			fileEntry.getFileEntryId(), DLActivityKeys.UPDATE_FILE_ENTRY,
			StringPool.BLANK, 0);

		// File version

		double oldVersion = Math.max(
			fileEntry.getVersion(), fileEntry.getPendingVersion());
		double newVersion = MathUtil.format(oldVersion + 0.1, 1, 1);

		if (is == null) {
			long repositoryId = getRepositoryId(groupId, folderId);

			fileEntry.setVersion(newVersion);

			dlFileEntryPersistence.update(fileEntry, false);

			is = dlLocalService.getFileAsStream(
				user.getCompanyId(), repositoryId, name);

			dlLocalService.updateFile(
				user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
				fileEntry.getGroupId(), repositoryId, name, false, newVersion,
				name, fileEntry.getFileEntryId(),
				fileEntry.getLuceneProperties(), fileEntry.getModifiedDate(),
				serviceContext, is);

			return fileEntry;
		}

		addFileVersion(user, fileEntry, newVersion, serviceContext.getStatus());

		// File entry

		fileEntry.setVersionUserId(user.getUserId());
		fileEntry.setVersionUserName(user.getFullName());
		fileEntry.setModifiedDate(new Date());
		fileEntry.setVersion(newVersion);
		fileEntry.setSize((int)size);

		dlFileEntryPersistence.update(fileEntry, false);

		// File

		long repositoryId = getRepositoryId(groupId, folderId);

		dlLocalService.updateFile(
			user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
			fileEntry.getGroupId(), repositoryId, name, false, newVersion,
			sourceFileName, fileEntry.getFileEntryId(),
			fileEntry.getLuceneProperties(), fileEntry.getModifiedDate(),
			serviceContext, is);

		// Folder

		if (fileEntry.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			DLFolder folder = dlFolderPersistence.findByPrimaryKey(
				fileEntry.getFolderId());

			folder.setLastPostDate(fileEntry.getModifiedDate());

			dlFolderPersistence.update(folder, false);
		}

		return fileEntry;
	}

	public DLFileEntry updateStatus(
			long userId, DLFileVersion fileVersion, boolean reIndex,
			int status)
		throws PortalException, SystemException {

		// File version

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		fileVersion.setStatus(status);
		fileVersion.setStatusByUserId(user.getUserId());
		fileVersion.setStatusByUserName(user.getFullName());
		fileVersion.setStatusDate(now);

		dlFileVersionPersistence.update(fileVersion, false);

		// File entry

		DLFileEntry fileEntry = dlFileEntryLocalService.getFileEntry(
			fileVersion.getGroupId(), fileVersion.getFolderId(),
			fileVersion.getName());

		if ((status == StatusConstants.APPROVED) &&
			(fileEntry.getVersion() < fileVersion.getVersion())) {

			fileEntry.setVersion(fileVersion.getVersion());

			dlFileEntryPersistence.update(fileEntry, false);
		}
		else if ((status != StatusConstants.APPROVED) &&
				 (fileEntry.getVersion() == fileVersion.getVersion())) {

			double newVersion = 0;

			if (fileVersion.getVersion() > 1) {
				List<DLFileVersion> approvedVersions =
					dlFileVersionPersistence.findByG_F_N_S(
						fileEntry.getGroupId(), fileEntry.getFolderId(),
						fileEntry.getName(), StatusConstants.APPROVED);

				if (approvedVersions.size() > 0) {
					newVersion = approvedVersions.get(0).getVersion();
				}
			}

			fileEntry.setPendingVersion(fileVersion.getVersion());
			fileEntry.setVersion(newVersion);

			dlFileEntryPersistence.update(fileEntry, false);
		}

		// Asset

		if ((status == StatusConstants.APPROVED) &&
			(fileEntry.getVersion() == fileVersion.getVersion())) {

			assetEntryLocalService.updateVisible(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId(),
				true);
		}

		if (fileEntry.getVersion() == 0) {
			assetEntryLocalService.updateVisible(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId(),
				false);
		}

		if (reIndex) {
			dlFileEntryLocalService.reIndex(fileEntry.getFileEntryId());
		}

		return fileEntry;
	}

	public DLFileEntry updateStatus(long userId, long fileEntryId, int status)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = getFileEntry(fileEntryId);

		DLFileVersion fileVersion =
			dlFileVersionLocalService.getLatestFileVersion(
				fileEntry.getGroupId(), fileEntry.getFolderId(),
				fileEntry.getName());

		return updateStatus(userId, fileVersion, false, status);
	}

	public DLFileEntry updateStatus(
			long userId, long groupId, long folderId, String name,
			double version, int status)
		throws PortalException, SystemException {

		DLFileVersion fileVersion = dlFileVersionPersistence.findByG_F_N_V(
			groupId, folderId, name, version);

		return updateStatus(userId, fileVersion, true, status);
	}

	protected void addFileVersion(
			User user, DLFileEntry fileEntry, double version, int status)
		throws SystemException {

		Date now = new Date();

		long fileVersionId = counterLocalService.increment();

		DLFileVersion fileVersion = dlFileVersionPersistence.create(
			fileVersionId);

		String versionUserName = GetterUtil.getString(
			fileEntry.getVersionUserName(), fileEntry.getUserName());

		fileVersion.setGroupId(fileEntry.getGroupId());
		fileVersion.setCompanyId(fileEntry.getCompanyId());
		fileVersion.setUserId(user.getUserId());
		fileVersion.setUserName(versionUserName);
		fileVersion.setCreateDate(now);
		fileVersion.setFolderId(fileEntry.getFolderId());
		fileVersion.setName(fileEntry.getName());
		fileVersion.setVersion(version);
		fileVersion.setSize(fileEntry.getSize());
		fileVersion.setStatus(status);
		fileVersion.setStatusByUserId(user.getUserId());
		fileVersion.setStatusByUserName(user.getFullName());
		fileVersion.setStatusDate(now);

		dlFileVersionPersistence.update(fileVersion, false);
	}

	protected long getFolderId(long companyId, long folderId)
		throws SystemException {

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			// Ensure folder exists and belongs to the proper company

			DLFolder folder = dlFolderPersistence.fetchByPrimaryKey(folderId);

			if ((folder == null) || (companyId != folder.getCompanyId())) {
				folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return folderId;
	}

	protected long getRepositoryId(long groupId, long folderId) {
		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return groupId;
		}
		else {
			return folderId;
		}
	}

	protected void validate(
			long groupId, long folderId, long newFolderId, String name,
			String title, String sourceFileName, InputStream is)
		throws PortalException, SystemException {

		if (Validator.isNotNull(sourceFileName)) {
			dlLocalService.validate(title, sourceFileName, is);
		}

		if (folderId != newFolderId) {
			folderId = newFolderId;
		}

		validate(groupId, folderId, name, title);
	}

	protected void validate(
			long groupId, long folderId, String title, InputStream is)
		throws PortalException, SystemException {

		dlLocalService.validate(title, true, is);

		validate(groupId, folderId, null, title);
	}

	protected void validate(
			long groupId, long folderId, String name, String title)
		throws PortalException, SystemException {

		try {
			dlFolderLocalService.getFolder(groupId, folderId, title);

			throw new DuplicateFolderNameException();
		}
		catch (NoSuchFolderException nsfe) {
		}

		try {
			DLFileEntry fileEntry =
				dlFileEntryPersistence.findByG_F_T(groupId, folderId, title);

			if (!fileEntry.getName().equals(name)) {
				throw new DuplicateFileException(title);
			}
		}
		catch (NoSuchFileEntryException nsfee) {
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(DLFileEntryLocalServiceImpl.class);

}