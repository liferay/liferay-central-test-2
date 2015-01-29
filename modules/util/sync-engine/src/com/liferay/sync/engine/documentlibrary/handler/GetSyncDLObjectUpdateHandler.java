/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.sync.engine.documentlibrary.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.sync.engine.documentlibrary.event.Event;
import com.liferay.sync.engine.documentlibrary.model.SyncDLObjectUpdate;
import com.liferay.sync.engine.documentlibrary.util.FileEventUtil;
import com.liferay.sync.engine.filesystem.Watcher;
import com.liferay.sync.engine.filesystem.WatcherRegistry;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.SyncSiteService;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.IODeltaUtil;

import java.io.IOException;

import java.nio.file.FileSystemException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class GetSyncDLObjectUpdateHandler extends BaseSyncDLObjectHandler {

	public GetSyncDLObjectUpdateHandler(Event event) {
		super(event);
	}

	@Override
	public void processResponse(String response) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		SyncDLObjectUpdate syncDLObjectUpdate = objectMapper.readValue(
			response, new TypeReference<SyncDLObjectUpdate>() {});

		for (SyncFile targetSyncFile : syncDLObjectUpdate.getSyncDLObjects()) {
			processSyncFile(targetSyncFile);
		}

		if (getParameterValue("parentFolderId") == null) {
			SyncSite syncSite = SyncSiteService.fetchSyncSite(
				(Long)getParameterValue("repositoryId"), getSyncAccountId());

			syncSite.setRemoteSyncTime(syncDLObjectUpdate.getLastAccessTime());

			SyncSiteService.update(syncSite);
		}
	}

	protected void addFile(SyncFile syncFile, String filePathName)
		throws Exception {

		Path filePath = Paths.get(filePathName);

		if (Files.exists(filePath) &&
			(syncFile.isFolder() ||
			 !FileUtil.isModified(syncFile, filePath))) {

			return;
		}

		syncFile.setFilePathName(filePathName);
		syncFile.setSyncAccountId(getSyncAccountId());
		syncFile.setUiEvent(SyncFile.UI_EVENT_ADDED_REMOTE);

		if (syncFile.isFolder()) {
			Files.createDirectories(filePath);

			SyncFileService.update(syncFile);

			SyncFileService.updateFileKeySyncFile(syncFile);
		}
		else {
			SyncFileService.update(syncFile);

			downloadFile(syncFile, null, false);
		}
	}

	protected void deleteFile(SyncFile sourceSyncFile, boolean trashed)
		throws Exception {

		if (trashed) {
			sourceSyncFile.setUiEvent(SyncFile.UI_EVENT_TRASHED_REMOTE);
		}
		else {
			sourceSyncFile.setUiEvent(SyncFile.UI_EVENT_DELETED_REMOTE);
		}

		SyncFileService.deleteSyncFile(sourceSyncFile);

		Path sourceFilePath = Paths.get(sourceSyncFile.getFilePathName());

		if (Files.notExists(sourceFilePath)) {
			return;
		}

		final Watcher watcher = WatcherRegistry.getWatcher(getSyncAccountId());

		Files.walkFileTree(
			sourceFilePath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path filePath, IOException ioe)
					throws IOException {

					Files.deleteIfExists(filePath);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult preVisitDirectory(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					watcher.unregisterFilePath(filePath);

					return super.preVisitDirectory(
						filePath, basicFileAttributes);
				}

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Files.deleteIfExists(filePath);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	protected void downloadFile(
		SyncFile syncFile, String sourceVersion, boolean patch) {

		if (patch) {
			FileEventUtil.downloadPatch(
				sourceVersion, getSyncAccountId(), syncFile,
				syncFile.getVersion());
		}
		else {
			FileEventUtil.downloadFile(getSyncAccountId(), syncFile);
		}
	}

	protected boolean isIgnoredFilePath(
		SyncFile syncFile, String filePathName) {

		if (syncFile != null) {
			filePathName = syncFile.getFilePathName();
		}

		return FileUtil.isIgnoredFilePath(FileUtil.getFilePath(filePathName));
	}

	protected void moveFile(
			SyncFile sourceSyncFile, SyncFile targetSyncFile,
			String targetFilePathName)
		throws Exception {

		Path sourceFilePath = Paths.get(sourceSyncFile.getFilePathName());
		Path targetFilePath = Paths.get(targetFilePathName);

		sourceSyncFile = SyncFileService.updateSyncFile(
			targetFilePath, targetSyncFile.getParentFolderId(), sourceSyncFile);

		if (Files.exists(sourceFilePath)) {
			Files.move(sourceFilePath, targetFilePath);
		}
		else if (targetSyncFile.isFolder()) {
			Files.createDirectories(targetFilePath);

			SyncFileService.updateFileKeySyncFile(sourceSyncFile);
		}
		else {
			downloadFile(sourceSyncFile, null, false);
		}

		sourceSyncFile.setModifiedTime(targetSyncFile.getModifiedTime());
		sourceSyncFile.setUiEvent(SyncFile.UI_EVENT_MOVED_REMOTE);

		SyncFileService.update(sourceSyncFile);
	}

	protected void processDependentSyncFiles(SyncFile syncFile) {
		List<SyncFile> dependentSyncFiles = _dependentSyncFilesMap.remove(
			syncFile.getTypePK());

		if (dependentSyncFiles == null) {
			return;
		}

		for (SyncFile dependentSyncFile : dependentSyncFiles) {
			processSyncFile(dependentSyncFile);
		}
	}

	protected void processSyncFile(SyncFile targetSyncFile) {
		SyncFile parentSyncFile = SyncFileService.fetchSyncFile(
			targetSyncFile.getRepositoryId(), getSyncAccountId(),
			targetSyncFile.getParentFolderId());

		if (parentSyncFile == null) {
			queueSyncFile(targetSyncFile.getParentFolderId(), targetSyncFile);

			return;
		}

		String filePathName = "";

		try {
			filePathName = FileUtil.getFilePathName(
				parentSyncFile.getFilePathName(),
				FileUtil.getSanitizedFileName(
					targetSyncFile.getName(), targetSyncFile.getExtension()));

			SyncFile sourceSyncFile = SyncFileService.fetchSyncFile(
				targetSyncFile.getRepositoryId(), getSyncAccountId(),
				targetSyncFile.getTypePK());

			if (isIgnoredFilePath(sourceSyncFile, filePathName) ||
				((sourceSyncFile != null) &&
				 (sourceSyncFile.getModifiedTime() ==
					targetSyncFile.getModifiedTime()))) {

				return;
			}

			targetSyncFile.setFilePathName(filePathName);

			String event = targetSyncFile.getEvent();

			if (event.equals(SyncFile.EVENT_ADD) ||
				event.equals(SyncFile.EVENT_GET) ||
				event.equals(SyncFile.EVENT_RESTORE)) {

				if (sourceSyncFile != null) {
					updateFile(sourceSyncFile, targetSyncFile, filePathName);

					processDependentSyncFiles(sourceSyncFile);

					return;
				}

				addFile(targetSyncFile, filePathName);
			}
			else if (event.equals(SyncFile.EVENT_DELETE)) {
				if (sourceSyncFile == null) {
					return;
				}

				deleteFile(sourceSyncFile, false);
			}
			else if (event.equals(SyncFile.EVENT_MOVE)) {
				if (sourceSyncFile == null) {
					addFile(targetSyncFile, filePathName);

					processDependentSyncFiles(targetSyncFile);

					return;
				}

				moveFile(sourceSyncFile, targetSyncFile, filePathName);
			}
			else if (event.equals(SyncFile.EVENT_TRASH)) {
				if (sourceSyncFile == null) {
					return;
				}

				deleteFile(sourceSyncFile, true);
			}
			else if (event.equals(SyncFile.EVENT_UPDATE)) {
				if (sourceSyncFile == null) {
					addFile(targetSyncFile, filePathName);

					processDependentSyncFiles(targetSyncFile);

					return;
				}

				updateFile(sourceSyncFile, targetSyncFile, filePathName);
			}

			processDependentSyncFiles(targetSyncFile);
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);

			if (e instanceof FileSystemException) {
				String message = e.getMessage();

				if (message.contains("File name too long")) {
					targetSyncFile.setState(SyncFile.STATE_ERROR);
					targetSyncFile.setUiEvent(
						SyncFile.UI_EVENT_FILE_NAME_TOO_LONG);

					SyncFileService.update(targetSyncFile);
				}
			}
		}
	}

	protected void queueSyncFile(long parentFolderId, SyncFile syncFile) {
		List<SyncFile> syncFiles = _dependentSyncFilesMap.get(parentFolderId);

		if (syncFiles == null) {
			syncFiles = new ArrayList<>();

			_dependentSyncFilesMap.put(parentFolderId, syncFiles);
		}

		syncFiles.add(syncFile);
	}

	protected void updateFile(
			SyncFile sourceSyncFile, SyncFile targetSyncFile,
			String filePathName)
		throws Exception {

		String sourceVersion = sourceSyncFile.getVersion();

		boolean filePathChanged = processFilePathChange(
			sourceSyncFile, targetSyncFile);

		sourceSyncFile.setChangeLog(targetSyncFile.getChangeLog());
		sourceSyncFile.setChecksum(targetSyncFile.getChecksum());
		sourceSyncFile.setDescription(targetSyncFile.getDescription());
		sourceSyncFile.setExtension(targetSyncFile.getExtension());
		sourceSyncFile.setExtraSettings(targetSyncFile.getExtraSettings());
		sourceSyncFile.setLockExpirationDate(
			targetSyncFile.getLockExpirationDate());
		sourceSyncFile.setLockUserId(targetSyncFile.getLockUserId());
		sourceSyncFile.setLockUserName(targetSyncFile.getLockUserName());
		sourceSyncFile.setModifiedTime(targetSyncFile.getModifiedTime());
		sourceSyncFile.setSize(targetSyncFile.getSize());
		sourceSyncFile.setUiEvent(SyncFile.UI_EVENT_UPDATED_REMOTE);
		sourceSyncFile.setVersion(targetSyncFile.getVersion());

		SyncFileService.update(sourceSyncFile);

		Path filePath = Paths.get(targetSyncFile.getFilePathName());

		if (filePathChanged && !Files.exists(filePath)) {
			if (targetSyncFile.isFolder()) {
				Path targetFilePath = Paths.get(filePathName);

				Files.createDirectories(targetFilePath);

				SyncFileService.update(sourceSyncFile);

				SyncFileService.updateFileKeySyncFile(sourceSyncFile);
			}
			else {
				downloadFile(sourceSyncFile, null, false);
			}
		}
		else if (targetSyncFile.isFile() &&
				 FileUtil.isModified(targetSyncFile, filePath)) {

			downloadFile(
				sourceSyncFile, sourceVersion,
				!IODeltaUtil.isIgnoredFilePatchingExtension(targetSyncFile));
		}
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		GetSyncDLObjectUpdateHandler.class);

	private final Map<Long, List<SyncFile>> _dependentSyncFilesMap =
		new HashMap<>();

}