/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.ByteArrayFileInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.io.File;
import java.io.InputStream;

/**
 * @author Sergio González
 * @author Matthew Kong
 */
public class TempFileUtil {

	public static String addTempFile(
			long userId, String tempPathName, File file)
		throws PortalException, SystemException {

		String tempFileName = FileUtil.createTempFileName();

		return addTempFile(userId, tempFileName, tempPathName, file);
	}

	public static String addTempFile(
			long userId, String fileName, String tempPathName, File file)
		throws PortalException, SystemException {

		String tempFileName = getTempFileName(userId, fileName, tempPathName);

		DLStoreUtil.addFile(_COMPANY_ID, _REPOSITORY_ID, tempFileName, file);

		return fileName;
	}

	public static String addTempFile(
			long userId, String fileName, String tempPathName,
			InputStream inputStream)
		throws PortalException, SystemException {

		File file = null;

		if (inputStream instanceof ByteArrayFileInputStream) {
			ByteArrayFileInputStream byteArrayFileInputStream =
				(ByteArrayFileInputStream)inputStream;

			file = byteArrayFileInputStream.getFile();
		}

		String tempFileName = getTempFileName(userId, fileName, tempPathName);

		if (file != null) {
			DLStoreUtil.addFile(
				_COMPANY_ID, _REPOSITORY_ID, tempFileName, file);
		}
		else {
			DLStoreUtil.addFile(
				_COMPANY_ID, _REPOSITORY_ID, tempFileName, inputStream);
		}

		return fileName;
	}

	public static String addTempFile(String tempPathName, File file)
		throws PortalException, SystemException {

		return addTempFile(_USER_ID, tempPathName, file);
	}

	public static String addTempFile(
			String fileName, String tempPathName, File file)
		throws PortalException, SystemException {

		return addTempFile(_USER_ID, fileName, tempPathName, file);
	}

	public static void deleteTempFile(
			long userId, String fileName, String tempPathName)
		throws PortalException, SystemException {

		String tempFileName = getTempFileName(userId, fileName, tempPathName);

		deleteTempFile(tempFileName);
	}

	public static void deleteTempFile(String tempFileName)
		throws PortalException, SystemException {

		DLStoreUtil.deleteFile(_COMPANY_ID, _REPOSITORY_ID, tempFileName);
	}

	public static void deleteTempFile(String fileName, String tempPathName)
		throws PortalException, SystemException {

		deleteTempFile(_USER_ID, fileName, tempPathName);
	}

	public static InputStream getTempFileAsStream(String tempFileName)
		throws PortalException, SystemException {

		return DLStoreUtil.getFileAsStream(
			_COMPANY_ID, _REPOSITORY_ID, tempFileName);
	}

	public static String[] getTempFileEntryNames(
		long userId, String tempPathName) {

		try {
			String tempFolderName = getTempFolderName(userId, tempPathName);

			String[] fileNames = DLStoreUtil.getFileNames(
				_COMPANY_ID, _REPOSITORY_ID, tempFolderName);

			for (int i = 0; i < fileNames.length; i++) {
				String fileName = StringUtil.extractLast(
					fileNames[i], StringPool.SLASH);

				fileName = StringUtil.replace(
					fileName, _SUFFIX_TEMP_FILENAME, StringPool.BLANK);

				fileNames[i] = fileName;
			}

			return fileNames;
		}
		catch (Exception e) {
			_log.error(e);
			return new String[0];
		}
	}

	public static String[] getTempFileEntryNames(String tempPathName) {
		return getTempFileEntryNames(_USER_ID, tempPathName);
	}

	public static String getTempFileName(
			long userId, String fileName, String tempPathName)
		throws PortalException {

		validateFileName(fileName);

		StringBundler sb = new StringBundler(3);

		sb.append(getTempFolderName(userId, tempPathName));
		sb.append(fileName);
		sb.append(_SUFFIX_TEMP_FILENAME);

		return sb.toString();
	}

	public static long getTempFileSize(String tempFileName)
		throws PortalException, SystemException {

		return DLStoreUtil.getFileSize(
			_COMPANY_ID, _REPOSITORY_ID, tempFileName);
	}

	protected static String getTempFolderName(
		long userId, String tempPathName) throws PortalException {

		validatePathName(tempPathName);

		StringBundler sb = new StringBundler(5);

		sb.append(_BASE_TEMP_PATHNAME);
		sb.append(tempPathName);
		sb.append(StringPool.SLASH);
		sb.append(userId);
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected static void validateFileName(String name) throws PortalException {
		if ((name == null) || name.contains(StringPool.BACK_SLASH) ||
			name.contains(StringPool.SLASH) ||
			name.contains(File.pathSeparator) ||
			(name.indexOf(_NULL_CHAR) > -1)) {

			throw new TempFileNameException();
		}
	}

	protected static void validatePathName(String pathName)
		throws PortalException {

		if (pathName == null) {
			return;
		}

		if (pathName.indexOf(_NULL_CHAR) > -1) {
			throw new TempFileNameException();
		}

		int pos = pathName.indexOf(StringPool.DOUBLE_PERIOD);
		if (pos > -1) {
			if (pathName.length() == 2) {
				throw new TempFileNameException();
			}

			char slash = StringPool.SLASH.charAt(0);
			char backSlash = StringPool.BACK_SLASH.charAt(0);

			if (pos > 0) {
				char charChecking = pathName.charAt(pos - 1);
				if ((charChecking == slash) || (charChecking == backSlash)) {
					throw new TempFileNameException();
				}
			}

			if ((pos + 2) < pathName.length()) {
				char charChecking = pathName.charAt(pos + 2);
				if ((charChecking == slash) || (charChecking == backSlash)) {
					throw new TempFileNameException();
				}
			}
		}
	}

	private static final String _BASE_TEMP_PATHNAME = "liferay_temp/";

	private static final long _COMPANY_ID = 0;

	private static final char _NULL_CHAR = 0;

	private static final long _REPOSITORY_ID = 0;

	private static final String _SUFFIX_TEMP_FILENAME = "_temp.tmp";

	private static final long _USER_ID = 0;

	private static Log _log = LogFactoryUtil.getLog(TempFileUtil.class);

}