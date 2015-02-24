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

package com.liferay.sync.engine.util;

import ch.securityvision.xattrj.Xattrj;

import com.liferay.sync.engine.documentlibrary.util.FileEventUtil;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.SyncFileService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserDefinedFileAttributeView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class FileUtil {

	public static boolean checksumsEqual(String checksum1, String checksum2) {
		if ((checksum1 == null) || (checksum2 == null) ||
			checksum1.isEmpty() || checksum2.isEmpty()) {

			return false;
		}

		return checksum1.equals(checksum2);
	}

	public static void fireDeleteEvents(Path filePath) throws IOException {
		long startTime = System.currentTimeMillis();

		Files.walkFileTree(
			filePath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					SyncFile syncFile = SyncFileService.fetchSyncFile(
						filePath.toString());

					if (syncFile == null) {
						syncFile = SyncFileService.fetchSyncFile(
							FileUtil.getFileKey(filePath));
					}

					if (syncFile != null) {
						syncFile.setLocalSyncTime(System.currentTimeMillis());

						SyncFileService.update(syncFile);
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					SyncFile syncFile = SyncFileService.fetchSyncFile(
						filePath.toString());

					if (syncFile == null) {
						syncFile = SyncFileService.fetchSyncFile(
							FileUtil.getFileKey(filePath));
					}

					if (syncFile != null) {
						syncFile.setLocalSyncTime(System.currentTimeMillis());

						SyncFileService.update(syncFile);
					}

					return FileVisitResult.CONTINUE;
				}

			}
		);

		List<SyncFile> deletedSyncFiles = SyncFileService.findSyncFiles(
			filePath.toString(), startTime);

		for (SyncFile deletedSyncFile : deletedSyncFiles) {
			if (deletedSyncFile.getTypePK() == 0) {
				SyncFileService.deleteSyncFile(deletedSyncFile, false);

				continue;
			}

			if (deletedSyncFile.isFolder()) {
				FileEventUtil.deleteFolder(
					deletedSyncFile.getSyncAccountId(), deletedSyncFile);
			}
			else {
				FileEventUtil.deleteFile(
					deletedSyncFile.getSyncAccountId(), deletedSyncFile);
			}
		}
	}

	public static String getChecksum(Path filePath) throws IOException {
		if (!isValidChecksum(filePath)) {
			return "";
		}

		InputStream fileInputStream = null;

		try {
			fileInputStream = Files.newInputStream(filePath);

			byte[] bytes = DigestUtils.sha1(fileInputStream);

			return Base64.encodeBase64String(bytes);
		}
		finally {
			StreamUtil.cleanUp(fileInputStream);
		}
	}

	public static long getFileKey(Path filePath) {
		if (!Files.exists(filePath)) {
			return -1;
		}

		try {
			if (OSDetector.isApple()) {
				Xattrj xattrj = getXattrj();

				if (xattrj == null) {
					return -1;
				}

				String fileKey = xattrj.readAttribute(
					filePath.toFile(), "fileKey");

				if (fileKey == null) {
					return -1;
				}

				return Long.parseLong(fileKey);
			}
			else {
				UserDefinedFileAttributeView userDefinedFileAttributeView =
					Files.getFileAttributeView(
						filePath, UserDefinedFileAttributeView.class);

				List<String> list = userDefinedFileAttributeView.list();

				if (!list.contains("fileKey")) {
					return -1;
				}

				ByteBuffer byteBuffer = ByteBuffer.allocate(
					userDefinedFileAttributeView.size("fileKey"));

				userDefinedFileAttributeView.read("fileKey", byteBuffer);

				CharBuffer charBuffer = _CHARSET.decode(
					(ByteBuffer)byteBuffer.flip());

				return Long.parseLong(charBuffer.toString());
			}
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);

			return -1;
		}
	}

	public static Path getFilePath(String first, String... more) {
		FileSystem fileSystem = FileSystems.getDefault();

		return fileSystem.getPath(first, more);
	}

	public static String getFilePathName(String first, String... more) {
		Path filePath = getFilePath(first, more);

		return filePath.toString();
	}

	public static String getNextFilePathName(String filePathName) {
		Path filePath = Paths.get(filePathName);

		Path parentFilePath = filePath.getParent();

		for (int i = 0;; i++) {
			StringBuilder sb = new StringBuilder();

			sb.append(FilenameUtils.getBaseName(filePathName));

			if (i > 0) {
				sb.append(" (");
				sb.append(i);
				sb.append(")");
			}

			String extension = FilenameUtils.getExtension(filePathName);

			if (extension.length() > 0) {
				sb.append(".");
				sb.append(extension);
			}

			String tempFilePathName = FileUtil.getFilePathName(
				parentFilePath.toString(), sb.toString());

			if (SyncFileService.fetchSyncFile(tempFilePathName) == null) {
				Path tempFilePath = Paths.get(tempFilePathName);

				if (!Files.exists(tempFilePath)) {
					return tempFilePathName;
				}
			}
		}
	}

	public static String getSanitizedFileName(
		String fileName, String extension) {

		for (String blacklistChar : PropsValues.SYNC_FILE_BLACKLIST_CHARS) {
			fileName = fileName.replace(blacklistChar, "_");
		}

		for (String blacklistCharLast :
				PropsValues.SYNC_FILE_BLACKLIST_CHARS_LAST) {

			if (blacklistCharLast.startsWith("\\u")) {
				blacklistCharLast = StringEscapeUtils.unescapeJava(
					blacklistCharLast);
			}

			if (fileName.endsWith(blacklistCharLast)) {
				fileName = fileName.substring(0, fileName.length() - 1);
			}
		}

		if ((extension != null) && !extension.isEmpty()) {
			int x = fileName.lastIndexOf(".");

			if ((x == -1) ||
				!extension.equalsIgnoreCase(fileName.substring(x + 1))) {

				fileName += "." + extension;
			}
		}

		if (fileName.length() > 255) {
			int x = fileName.length() - 1;

			if ((extension != null) && !extension.isEmpty()) {
				x = fileName.lastIndexOf(".");
			}

			int y = x - (fileName.length() - 255);

			fileName = fileName.substring(0, y) + fileName.substring(x);
		}

		return fileName;
	}

	public static boolean isHidden(Path filePath) {
		if (!Files.exists(filePath)) {
			return false;
		}

		try {
			return Files.isHidden(filePath);
		}
		catch (IOException ioe) {
			return false;
		}
	}

	public static boolean isIgnoredFilePath(Path filePath) {
		String fileName = String.valueOf(filePath.getFileName());

		if (_syncFileIgnoreNames.contains(fileName) ||
			isOfficeTempFile(fileName, filePath) ||
			(PropsValues.SYNC_FILE_IGNORE_HIDDEN && isHidden(filePath)) ||
			Files.isSymbolicLink(filePath) || fileName.endsWith(".lnk")) {

			return true;
		}

		SyncFile syncFile = SyncFileService.fetchSyncFile(filePath.toString());

		if (syncFile == null) {
			return isIgnoredFilePath(filePath.getParent());
		}

		if (!syncFile.isSystem() &&
			(syncFile.getState() == SyncFile.STATE_UNSYNCED)) {

			return true;
		}

		return false;
	}

	public static boolean isModified(SyncFile syncFile) {
		if (syncFile.getFilePathName() == null) {
			return true;
		}

		Path filePath = Paths.get(syncFile.getFilePathName());

		return isModified(syncFile, filePath);
	}

	public static boolean isModified(SyncFile syncFile, Path filePath) {
		if (filePath == null) {
			return true;
		}

		try {
			if ((syncFile.getSize() > 0) &&
				(syncFile.getSize() != Files.size(filePath))) {

				return true;
			}
		}
		catch (IOException ioe) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(ioe.getMessage(), ioe);
			}
		}

		try {
			FileTime fileTime = Files.getLastModifiedTime(filePath);

			long modifiedTime = syncFile.getModifiedTime();

			if (OSDetector.isUnix()) {
				modifiedTime = modifiedTime / 1000 * 1000;
			}

			if ((fileTime.toMillis() == modifiedTime) &&
				(getFileKey(filePath) == syncFile.getSyncFileId())) {

				return false;
			}
		}
		catch (IOException ioe) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(ioe.getMessage(), ioe);
			}
		}

		try {
			String checksum = getChecksum(filePath);

			return !checksumsEqual(checksum, syncFile.getChecksum());
		}
		catch (IOException ioe) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(ioe.getMessage(), ioe);
			}

			return true;
		}
	}

	public static boolean isValidChecksum(Path filePath) throws IOException {
		if (Files.notExists(filePath) ||
			(Files.size(filePath) >
				PropsValues.SYNC_FILE_CHECKSUM_THRESHOLD_SIZE)) {

			return false;
		}

		return true;
	}

	public static boolean isValidFileName(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			return false;
		}

		for (String blacklistChar : PropsValues.SYNC_FILE_BLACKLIST_CHARS) {
			if (fileName.contains(blacklistChar)) {
				return false;
			}
		}

		for (String blacklistLastChar :
				PropsValues.SYNC_FILE_BLACKLIST_CHARS_LAST) {

			if (blacklistLastChar.startsWith("\\u")) {
				blacklistLastChar = StringEscapeUtils.unescapeJava(
					blacklistLastChar);
			}

			if (fileName.endsWith(blacklistLastChar)) {
				return false;
			}
		}

		String nameWithoutExtension = FilenameUtils.removeExtension(fileName);

		for (String blacklistName : PropsValues.SYNC_FILE_BLACKLIST_NAMES) {
			if (nameWithoutExtension.equalsIgnoreCase(blacklistName)) {
				return false;
			}
		}

		return true;
	}

	public static void moveFile(Path sourceFilePath, Path targetFilePath) {
		checkFilePath(sourceFilePath);

		try {
			Files.move(
				sourceFilePath, targetFilePath,
				StandardCopyOption.REPLACE_EXISTING);
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}
	}

	public static void setModifiedTime(Path filePath, long modifiedTime)
		throws IOException {

		if (!Files.exists(filePath)) {
			return;
		}

		FileTime fileTime = FileTime.fromMillis(modifiedTime);

		Files.setLastModifiedTime(filePath, fileTime);
	}

	public static void writeFileKey(Path filePath, String fileKey) {
		if (OSDetector.isApple()) {
			Xattrj xattrj = getXattrj();

			if (xattrj == null) {
				return;
			}

			File file = filePath.toFile();

			if (!file.canWrite()) {
				file.setWritable(true);
			}

			xattrj.writeAttribute(file, "fileKey", fileKey);
		}
		else {
			File file = filePath.toFile();

			if (!file.canWrite()) {
				file.setWritable(true);
			}

			UserDefinedFileAttributeView userDefinedFileAttributeView =
				Files.getFileAttributeView(
					filePath, UserDefinedFileAttributeView.class);

			try {
				userDefinedFileAttributeView.write(
					"fileKey", _CHARSET.encode(CharBuffer.wrap(fileKey)));
			}
			catch (Exception e) {
				_logger.error(e.getMessage(), e);
			}
		}
	}

	protected static void checkFilePath(Path filePath) {

		// Check to see if the file or folder is still being written to. If
		// it is, wait until the process is finished before making any future
		// modifications. This is used to prevent file system interruptions.

		try {
			while (true) {
				long size1 = FileUtils.sizeOf(filePath.toFile());

				Thread.sleep(1000);

				long size2 = FileUtils.sizeOf(filePath.toFile());

				if (size1 == size2) {
					break;
				}
			}
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}
	}

	protected static Xattrj getXattrj() {
		if (_xattrj != null) {
			return _xattrj;
		}

		try {
			_xattrj = new Xattrj();

			return _xattrj;
		}
		catch (IOException ioe) {
			_logger.error(ioe.getMessage(), ioe);

			return null;
		}
	}

	protected static boolean isOfficeTempFile(String fileName, Path filePath) {
		if (Files.isDirectory(filePath)) {
			return false;
		}

		if (fileName.startsWith("~$") ||
			(fileName.startsWith("~") && fileName.endsWith(".tmp"))) {

			return true;
		}

		return false;
	}

	private static final Charset _CHARSET = Charset.forName("UTF-8");

	private static final Logger _logger = LoggerFactory.getLogger(
		FileUtil.class);

	private static final Set<String> _syncFileIgnoreNames = new HashSet<>(
		Arrays.asList(PropsValues.SYNC_FILE_IGNORE_NAMES));
	private static Xattrj _xattrj;

}